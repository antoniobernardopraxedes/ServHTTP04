package com.praxsoft.ServHTTP04;


import org.eclipse.paho.client.mqttv3.*;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

import static com.praxsoft.ServHTTP04.SupService.*;

public class MQTT {

    public static boolean RecAtualizacaoBroker;
    public static int NumMsgRecBroker = 0;
    private static boolean FalhaConexaoSub;

    public static String Broker   = "tcp://200.98.140.180:1883";
    private static String IdCliente = "WebScada010";
    private static String NomeUsuario = "usuario1";
    private static String Senha = "senha1";
    private static int Qos = 0;
    private static String Topico1 = "DadosUTR";
    private static String Topico2 = "DadosCC1";
    private static String Topico3 = "DadosCC2";
    private static String Topico4 = "DadosCAQ";
    private static String Topico5 = "DadosCEN";


    //******************************************************************************************************************
    // Nome da Método: AssinanteSupervisao5Topicos                                                                     *
    //                                                                                                                 *
    // Funcao: efetua a conexão com o broker e aguarda a publicação das informações de supervisão no formato enviado   *
    //         pelo publicador do Raspberry PI 3 da oficina.                                                           *
    //                                                                                                                 *
    // Entrada: endereço do broker, nome do cliente, nome de usuário, senha, qos, os 5 tópicos em sequência            *
    //                                                                                                                 *
    // Saida: o método XNL.CarregaVariaveis recebe a mensagem recebida do broker e carrega nas variáveis do objeto     *
    //        correspondente ao tópico recebido.                                                                       *
    //        VG.ContAtualizacao é o contador de atualização que é zerado quando uma mensagem válida é recebida        *
    //        VG.RecAtualizacaoBroker é ums flag que indica recebimento de mensagem de atualização do broker           *
    //        VG.NumMsgRecBroker é um contador que é incrementado sempre que uma mensagem válida é recebida            *
    //******************************************************************************************************************
    //
    public static void AssinanteSupervisaoJson5Topicos(String Agente, String Cliente, String Usuario, String Senha,  int Qos,
                                                       String Topico1,String Topico2,String Topico3,String Topico4,String Topico5) throws Exception {

        MqttClient ClienteMqtt = new MqttClient(Agente, Cliente, new MemoryPersistence());
        MqttConnectOptions OpcoesConexao = new MqttConnectOptions();
        OpcoesConexao.setCleanSession(true);
        OpcoesConexao.setUserName(Usuario);
        OpcoesConexao.setPassword(Senha.toCharArray());
        OpcoesConexao.setConnectionTimeout(30);
        OpcoesConexao.setKeepAliveInterval(15);
        OpcoesConexao.setMaxReconnectDelay(45);
        OpcoesConexao.setAutomaticReconnect(true);

        if (ClienteMqtt.isConnected()) {
            ClienteMqtt.disconnect();
            ClienteMqtt.close();
        }

        ClienteMqtt.setCallback(new MqttCallback() {
            @Override
            public void connectionLost(Throwable causa) {
                Terminal("Falha de Conexão do Assinante de Supervisão JSON: " + causa.getMessage(), false);
                FalhaConexaoSub = true;
            }
            @Override
            public void messageArrived(String topico, MqttMessage mensagem) throws Exception {

                byte[] MsgBytes = mensagem.getPayload();
                String MsgString = "";
                for (int i = 0; i < MsgBytes.length; i++) {
                    MsgString = MsgString + (char)MsgBytes[i];
                }
                XML.CarregaVariaveis(topico, MsgString);
                RecAtualizacaoBroker = true;
                NumMsgRecBroker++;
            }
            @Override
            public void deliveryComplete(IMqttDeliveryToken token) {

                Terminal("deliveryComplete---------" + token.isComplete(), false);
            }
        });
        ClienteMqtt.connect(OpcoesConexao);
        if (ClienteMqtt.isConnected()) {
            FalhaConexaoSub = false;
            ClienteMqtt.subscribe(Topico1, Qos);
            ClienteMqtt.subscribe(Topico2, Qos);
            ClienteMqtt.subscribe(Topico3, Qos);
            ClienteMqtt.subscribe(Topico4, Qos);
            ClienteMqtt.subscribe(Topico5, Qos);
        }

    } // Fim do Método


    //*****************************************************************************************************************
    // Nome da Método: AssinanteComando                                                                               *
    //                                                                                                                *
    // Funcao: efetua a conexão com o broker, verifica se houve publicação em um tópico e desconecta do broker        *
    //                                                                                                                *
    // Entrada: endereço do broker, nome do cliente, nome de usuário, senha, tópico, qos                              *
    //                                                                                                                *
    // Saida: VG.Comando com o número do comando recebido                                                             *
    //*****************************************************************************************************************
    //
    public static void AssinanteComando(String agente, String cliente, String usuario, String senha, String topico, int qos) throws Exception {

        MqttClient ClienteMqtt = new MqttClient(agente, cliente, new MemoryPersistence());

        MqttConnectOptions OpcoesConexao = new MqttConnectOptions();
        OpcoesConexao.setCleanSession(true);
        OpcoesConexao.setUserName(usuario);
        OpcoesConexao.setPassword(senha.toCharArray());
        OpcoesConexao.setConnectionTimeout(30);
        OpcoesConexao.setKeepAliveInterval(15);
        OpcoesConexao.setMaxReconnectDelay(45);
        OpcoesConexao.setAutomaticReconnect(true);

        if (ClienteMqtt.isConnected()) { ClienteMqtt.disconnect(); }

        ClienteMqtt.connect(OpcoesConexao);
        if (ClienteMqtt.isConnected()) {

            ClienteMqtt.setCallback(new MqttCallback() {
                @Override
                public void connectionLost(Throwable causa) {
                    String Msg = "Falha de Conexão do Assinante de Comando: " + causa.getMessage();
                    if (SupService.isVerbose()) {
                        System.out.println(ImprimeHoraData(LeDataHora(), false) + " - " + Msg);
                        try {
                            ClienteMqtt.disconnect();
                        } catch(Exception e) {	}
                        Terminal("Conexão de Assinante de Comando com o Agente Encerrada", false);
                    }
                }
                @Override
                public void messageArrived(String topic, MqttMessage mensagem) throws Exception {
                    byte[] MsgCom = mensagem.getPayload();
                    if (SupService.isInicio()) {
                        SupService.setInicio(false);
                    }
                    else {
                        if ((MsgCom[0] == 1) && (MsgCom[1] == 2) && (MsgCom[3] == 0)) {
                            SupService.setComando(MsgCom[2]);
                            System.out.println("");
                            Terminal("Recebida Mensagem de Comando: " + IdComando(SupService.ValorComando()), true);
                        }
                    }
                }
                @Override
                public void deliveryComplete(IMqttDeliveryToken token) {
                    String Msg = "deliveryComplete---------" + token.isComplete();
                    if (SupService.isVerbose()) {
                        System.out.println(ImprimeHoraData(LeDataHora(), false) + " - " + Msg);
                    }
                    try {
                        ClienteMqtt.disconnect();
                    } catch(Exception e) {

                    }
                    Terminal("Conexão de Assinante de Comando com o Agente Encerrada", false);
                }
            });

            ClienteMqtt.subscribe(topico, qos);
            Terminal("Assinante de Comando Conectado:  Cliente: " + cliente + " / Tópico Requisitado: " + topico, false);
        }

    } // Fim do Método


    //******************************************************************************************************************
    // Nome da Método: Pub                                                                                             *
    //                                                                                                                 *
    // Funcao: efetua a conexão com o broker, publica uma mensagem em um tópico e desconecta do broker                 *
    //                                                                                                                 *
    // Entrada: endereço do broker, nome do cliente, nome de usuário, senha, tópico, qos, mensagem em bytes            *
    //                                                                                                                 *
    // Saida: não tem                                                                                                  *
    //                                                                                                                 *
    //******************************************************************************************************************
    //
    public static void Pub(String Agente, String Cliente, String Usuario, String Senha, String Topico, int qos, byte[] MsgBin) throws Exception {

        MqttMessage msg = new MqttMessage();
        MemoryPersistence persistence = new MemoryPersistence();
        MqttClient ClienteMqtt = new MqttClient(Agente, Cliente, persistence);
        MqttConnectOptions OpcoesConexao = new MqttConnectOptions();
        OpcoesConexao.setCleanSession(true);
        OpcoesConexao.setUserName(Usuario);
        OpcoesConexao.setPassword(Senha.toCharArray());
        OpcoesConexao.setConnectionTimeout(60);
        OpcoesConexao.setKeepAliveInterval(45);

        if (ClienteMqtt.isConnected()) { ClienteMqtt.disconnect(); }

        Terminal("Conectando ao Agente: " + Agente + " para Publicação de Informações de Supervisão", false);
        ClienteMqtt.connect(OpcoesConexao);

        if (ClienteMqtt.isConnected()) {
            Terminal("Conectado ao Agente " + Agente + " / Usuário " + Usuario + " Autorizado", false);
        }
        else {
            Terminal("Falha na Conexão com o Agente", false);
        }
        msg.setQos(qos);
        msg.setRetained(true);
        msg.setPayload(MsgBin);
        ClienteMqtt.publish(Topico, msg);
        Terminal("Publicando Mensagem: Tópico = " + Topico + " / Número de Bytes = " + MsgBin.length, false);
        ClienteMqtt.disconnect();
        Terminal("Conexão de Publicador com o Agente Encerrada", false);

    } // Fim do Método


    //******************************************************************************************************************
    // Nome da Método: Pub5topicos                                                                                     *
    //                                                                                                                 *
    // Funcao: efetua a conexão com o broker, publica uma mensagem em um tópico e desconecta do broker                 *
    //                                                                                                                 *
    // Entrada: endereço do broker, nome do cliente, nome de usuário, senha, tópicos, qos, mensagens em bytes          *
    //                                                                                                                 *
    // Saida: não tem                                                                                                  *
    //                                                                                                                 *
    //******************************************************************************************************************
    //
    public static void Pub5topicos(String Agente, String Cliente, String Usuario, String Senha, int qos,
                                   String Topico1, String Topico2, String Topico3, String Topico4, String Topico5,
                                   byte[] MsgBin1, byte[] MsgBin2, byte[] MsgBin3, byte[] MsgBin4, byte[] MsgBin5) throws Exception {

        MqttMessage msg = new MqttMessage();
        MemoryPersistence persistence = new MemoryPersistence();
        MqttClient ClienteMqtt = new MqttClient(Agente, Cliente, persistence);
        MqttConnectOptions OpcoesConexao = new MqttConnectOptions();
        OpcoesConexao.setCleanSession(true);
        OpcoesConexao.setUserName(Usuario);
        OpcoesConexao.setPassword(Senha.toCharArray());
        OpcoesConexao.setConnectionTimeout(30);
        OpcoesConexao.setKeepAliveInterval(15);

        if (ClienteMqtt.isConnected()) { ClienteMqtt.disconnect(); }

        Terminal("Conectando ao Agente: " + Agente + " para Publicação de Informações de Supervisão", false);
        ClienteMqtt.connect(OpcoesConexao);

        if (ClienteMqtt.isConnected()) {
            Terminal("Conectado ao Agente " + Agente + " / Usuário " + Usuario + " Autorizado", false);
        }
        else {
            Terminal("Falha na Conexão com o Agente", false);
        }

        msg.setQos(qos);
        msg.setRetained(true);

        msg.setPayload(MsgBin1);
        ClienteMqtt.publish(Topico1, msg);
        Terminal("Publicando Mensagem: Tópico = " + Topico1 + " / Número de Bytes = " + MsgBin1.length, false);

        msg.setPayload(MsgBin2);
        ClienteMqtt.publish(Topico2, msg);
        Terminal("Publicando Mensagem: Tópico = " + Topico2 + " / Número de Bytes = " + MsgBin2.length, false);

        msg.setPayload(MsgBin3);
        ClienteMqtt.publish(Topico3, msg);
        Terminal("Publicando Mensagem: Tópico = " + Topico3 + " / Número de Bytes = " + MsgBin3.length, false);

        msg.setPayload(MsgBin4);
        ClienteMqtt.publish(Topico4, msg);
        Terminal("Publicando Mensagem: Tópico = " + Topico4 + " / Número de Bytes = " + MsgBin4.length, false);

        msg.setPayload(MsgBin5);
        ClienteMqtt.publish(Topico5, msg);
        Terminal("Publicando Mensagem: Tópico = " + Topico5 + " / Número de Bytes = " + MsgBin5.length, false);

        ClienteMqtt.disconnect();
        Terminal("Conexão de Publicador com o Agente Encerrada", false);

    } // Fim do Método


    //******************************************************************************************************************
    // Nome do Método: IniciaAssinanteMqtt()                                                                           *
    //	                                                                                                               *
    // Funcao: inicia o Assinante MQTT no broker solicitando as informações de 5 tópicos                               *
    //                                                                                                                 *
    // Entrada: não tem                                                                                                *
    //                                                                                                                 *
    // Saida: não tem                                                                                                  *
    //******************************************************************************************************************
    //
    public static void IniciaAssinante() {

        IdCliente = ValorIdCliente();

        try {
            AssinanteSupervisaoJson5Topicos(Broker,IdCliente,NomeUsuario,Senha,Qos,Topico1,Topico2,Topico3,Topico4,Topico5);
            Terminal("Assinante conectado ao agente: " + Broker + " Cliente: " + IdCliente + " Usuario: " + NomeUsuario + " Senha: " + Senha, true);
            Terminal("Tópicos Requisitados: " + Topico1 + " " + Topico2 + " " + Topico3 + " " + Topico4 + " " + Topico5, true);
        } catch (Exception e) {
            Terminal("Falha ao fazer a Assinatura no Agente: " + e, true);
        }
    }

}
