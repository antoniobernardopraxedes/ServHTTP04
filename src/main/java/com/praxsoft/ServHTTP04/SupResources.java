package com.praxsoft.ServHTTP04;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.praxsoft.ServHTTP04.Dados.*;
import static com.praxsoft.ServHTTP04.SupService.*;

@RestController
public class SupResources {

    @Autowired
    private static int ContAtualizacao = 0;
    private static int ContMsgCoAP = 0;

    @GetMapping(value = "/supcom")
    public ResponseEntity<?> supHtml(@RequestHeader(value = "User-Agent") String userAgent) {
        String nomeArquivo = "TabelaSupNova.html";
        String caminho = getDiretorioRecursos() + "/sup/";
        String arquivoTxt = LeArquivoTexto(caminho, nomeArquivo);
        return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.valueOf("text/html")).body(arquivoTxt);
    }

    @GetMapping(value = "tabelasupjsnova.js")
    public ResponseEntity<?> EnviaJavascript() {
        String caminho = getDiretorioRecursos() + "/sup/js/";
        String arquivoTxt = LeArquivoTexto(caminho, "tabelasupjsnova.js");
        return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.valueOf("text/javascript")).body(arquivoTxt);
    }

    @GetMapping(value = "style.css")
    public ResponseEntity<?> EnviaCss() {
        String caminho = getDiretorioRecursos() + "/sup/css/";
        String arquivoTxt = LeArquivoTexto(caminho, "style.css");
        return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.valueOf("text/css")).body(arquivoTxt);
    }

    @GetMapping(value = "/local001.xml")
    public ResponseEntity<?> atualizaVariaveis() throws Exception {
        String Msg = "";
        String CmdEx = "";

        if (!isOpLocal()) { // Se é operação através do Agente
            CmdEx = "Agente MQTT Normal - ServHTTP04";
            if (MQTT.RecAtualizacaoBroker) {
                Msg = " - Recebidas " + MQTT.NumMsgRecBroker + " Mensagens de Atualização do Agente ";
                MQTT.RecAtualizacaoBroker = false;
                MQTT.NumMsgRecBroker = 0;
                ContAtualizacao = 0;
            } else {
                ContAtualizacao = ContAtualizacao + 1;
                if (ContAtualizacao > 5) {
                    XML.CarregaVariaveisFalha();
                    Msg = " - Falha de Atualização";
                    CmdEx = "Falha de Atualização do Agente";
                    if (ContAtualizacao == 20) {
                        MQTT.IniciaAssinante();
                        ContAtualizacao = 0;
                    }
                }
            }
        }
        else { // Se é operação local
            CmdEx = "Operação Local - ServHTTP04";
            ContAtualizacao = 0;
            MQTT.RecAtualizacaoBroker = true;
        }
        Terminal("Recebida Requisição de Atualização do Cliente" + Msg, false);

        return ResponseEntity
                .status(HttpStatus.OK)
                .contentType(MediaType.valueOf("application/xml"))
                .body(XML.MontaMensagem(CmdEx));
    }

    @PostMapping(value = "/cmd={id}")
    public ResponseEntity<?> RecComando(@PathVariable("id") String id) {
        escreveNumComando(StringToInt(id));
        if (!isOpLocal()) {
            if (valorNumComando() == 20) {
                Terminal("Reinicia Assinatura no Agente MQTT", true);
                ImprimeLinhaBranco(true);
                MQTT.IniciaAssinante();
            }
            else {
                try {
                    String Cliente = "WebComando1";
                    String Usuario = "usuario3";
                    String Senha = "senha3";
                    String Topico = "IdComando";
                    int qos = 0;
                    byte[] MsgBin = new byte[4];
                    MsgBin[0] = 1;
                    MsgBin[1] = 2;
                    MsgBin[2] = (byte) valorNumComando();
                    MsgBin[3] = 0;
                    MQTT.Pub(MQTT.Broker, Cliente, Usuario, Senha, Topico, qos, MsgBin);
                } catch (Exception e) {
                }
            }
        }
        return ResponseEntity
                .status(HttpStatus.OK)
                .contentType(MediaType.valueOf("application/xml"))
                .body(XML.MontaMensagem("Comando: " + IdComando(valorNumComando())));
    }

}
