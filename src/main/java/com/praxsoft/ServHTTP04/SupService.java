package com.praxsoft.ServHTTP04;

import org.springframework.stereotype.Service;

import java.io.*;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.StringTokenizer;

@Service
public class SupService {

    private static boolean opLocal = false;
    private static boolean verbose = false;
    private static boolean inicio = true;
    private static boolean publicador = false;
    private static int comando = 0;
    private static String idCliente;

    private static String diretorioBd = "/home/bernardo/bd/";
    public static String diretorioRecursos = "recursos/";

    //private static int numMaxUsuarios = 4;
    private static int numUsuarios = 4;
    private static final String[] nomeUsuario = {"usuario1", "usuario2", "usuario3", "usuario4"};
    private static final String[] senhaUsuario = {"senha1=1", "senha2=2", "senha3=3", "senha4=4"};

    public static boolean isOpLocal() { return opLocal; }

    public static boolean isVerbose() { return verbose; }

    public static boolean isInicio() { return inicio; }
    public static void setInicio(boolean Inicio) { inicio = Inicio; }

    public static int ValorComando() { return comando; }
    public static void setComando(int Comando) { comando = Comando; }

    public static String ValorIdCliente() { return idCliente; }
    public static void setIdCliente(String IdCliente) { idCliente = IdCliente; }

    public static String getDiretorioBd() { return diretorioBd; }

    public static String getDiretorioRecursos() { return diretorioRecursos; }

    public static int getNumUsuarios() { return numUsuarios; }

    public static String getNomeUsuario(int i) { return nomeUsuario[i]; }

    public static String getSenhaUsuario(int i) { return senhaUsuario[i]; }

    public static boolean isPublicador() { return publicador; }


    //******************************************************************************************************************
    // Nome do Método: LeConfiguracao()                                                                                *
    //	                                                                                                               *
    // Funcao: lê o arquivo de configuração e carrega nos atributos                                                    *
    //                                                                                                                 *
    // Entrada: não tem                                                                                                *
    // Saida: não tem                                                                                                  *
    //******************************************************************************************************************
    //
    public static boolean LeConfiguracao() {
        boolean resultado = false;
        String caminho = "recursos/";
        String nomeArquivo = "srvhttp03.cnf";

        String arquivoConf = LeArquivoTexto(caminho, nomeArquivo);

        if (arquivoConf != null) {

            opLocal = false;
            if (LeParametro(arquivoConf, "ModoOp:").equals("local")) {
                opLocal = true;
            }

            verbose = false;
            if (LeParametro(arquivoConf, "Verbose:").equals("true")) {
                verbose = true;
            }
            idCliente = LeParametro(arquivoConf, "IdCliente:");
            diretorioBd = LeParametro(arquivoConf, "DiretorioBD:");
            diretorioRecursos = LeParametro(arquivoConf, "DiretorioRecursos:");

            publicador = false;
            if (LeParametro(arquivoConf, "Publicador:").equals("true")) {
                publicador = true;
            }

            resultado = true;
        }
        return resultado;
    }

    //******************************************************************************************************************
    // Nome do Método: MostraDadosConfiguracao                                                                         *
    //                                                                                                                 *
    // Funcao: imprime no terminal as informações de configuração                                                      *
    //                                                                                                                 *
    // Entrada: não tem                                                                                                *
    // Saida: não tem                                                                                                  *
    //******************************************************************************************************************
    //
    public static void MostraDadosConfiguracao() {
        if (verbose) {
            System.out.println("Informações do arquivo de configuração");
            if (opLocal) {
                System.out.println("Modo de Operação Local");
            } else {
                System.out.println("Modo de Operação Remoto (Nuvem)");
                System.out.println("Cliente do Agente: " + idCliente);
            }
            System.out.println("Diretorio de recursos: " + diretorioRecursos);
            System.out.println("");
        }

    }

    //******************************************************************************************************************
    // Nome do Método: LeArquivoTexto                                                                                  *
    //	                                                                                                               *
    // Funcao: lê um arquivo texto (sequência de caracteres).                                                          *
    //                                                                                                                 *
    // Entrada: string com o caminho do arquivo e string com o nome do arquivo                                         *
    //                                                                                                                 *
    // Saida: String com o arquivo texto lido. Se ocorrer falha na leitura, o método retorna null                      *
    //******************************************************************************************************************
    //
    public static String LeArquivoTexto(String caminho, String nomeArquivo) {

        File arquivo = new File(caminho + nomeArquivo);
        String arquivoLido = "";
        try {
            BufferedReader br = new BufferedReader(new FileReader(arquivo));
            String linha;
            while ((linha = br.readLine()) != null) {
                arquivoLido = arquivoLido + linha + "\n";
            }
            return arquivoLido;

        } catch (IOException e) {
            return null;
        }
    }

    //******************************************************************************************************************
    // Nome do Método: LeByte                                                                                          *
    //	                                                                                                               *
    // Funcao: lê um arquivo binário (sequência de bytes).                                                             *
    //                                                                                                                 *
    // Entrada: string com o caminho do arquivo e string com o nome do arquivo                                         *
    // Saida: array com a sequência de bytes do arquivo lido. Se ocorrer falha na leitura, o método retorna null.      *
    //******************************************************************************************************************
    //
    public static byte[] LeByte(String caminho, String nomeArquivo) {

        try {
            File arquivo = new File(caminho + nomeArquivo);
            FileInputStream arquivoByte = null;
            int numBytesArquivo = (int)arquivo.length();
            byte[] arrayByteArquivo = new byte[numBytesArquivo];

            arquivoByte = new FileInputStream(arquivo);
            arquivoByte.read(arrayByteArquivo);
            arquivoByte.close();
            return arrayByteArquivo;

        } catch (IOException e) {
            byte[] arrayErro = new byte[0];
            return arrayErro;
        }
    }

    //******************************************************************************************************************
    // Nome do Método: EscreveArquivoTexto                                                                             *
    //	                                                                                                               *
    // Funcao: escreve um arquivo texto                                                                                *
    //                                                                                                                 *
    // Entrada: string com o nome do caminho e do arquivo e texto a ser escrito no arquivo                             *
    // Saida: = boolean operação realizada = true / operação não realizada = false                                     *
    //******************************************************************************************************************
    //
    public static boolean EscreveArquivoTexto(String caminho, String nomeArquivo, String conteudo) {
        boolean resultado;
        try {
            PrintWriter out = new PrintWriter(new FileWriter(caminho + nomeArquivo));
            out.write(conteudo);
            out.close();
            resultado = true;
        } catch (IOException e) {
            resultado = false;
        }
        return resultado;
    }

    //******************************************************************************************************************
    // Nome do Método: RenomeiaArquivo                                                                                 *
    //	                                                                                                               *
    // Funcao: renomeia um arquivo                                                                                     *
    //                                                                                                                 *
    // Entrada: string com o caminho, string com o nome velho do arquivo e string com o nome novo do arquivo           *
    // Saida: = boolean se a operação foi realizada = true / se não foi realizada = false                              *
    //******************************************************************************************************************
    //
    public static boolean RenomeiaArquivo(String caminho, String nomeVelho, String nomeNovo) {
        boolean resultado;
        File arquivo1 = new File(caminho + nomeVelho);
        File arquivo2 = new File(caminho + nomeNovo);

        if (arquivo2.exists()) {
            resultado = false;
        }
        else {
            resultado = arquivo1.renameTo(arquivo2);
        }
        return resultado;
    }

    //******************************************************************************************************************
    // Nome do Método: ExisteArquivo                                                                                   *
    //	                                                                                                               *
    // Funcao: verifica se o arquivo existe                                                                            *
    //                                                                                                                 *
    // Entrada: string com o caminho e string com o nome do arquivo                                                    *
    // Saida: = boolean - true se o arquivo existe                                                                     *
    //******************************************************************************************************************
    //
    public static boolean ExisteArquivo(String Caminho, String NomeArquivo) {
        File Arquivo = new File(Caminho + NomeArquivo);
        return (Arquivo.exists());
    }

    //******************************************************************************************************************
    // Nome do Método: ApagaArquivo                                                                                    *
    //	                                                                                                               *
    // Funcao: apaga um arquivo                                                                                        *
    //                                                                                                                 *
    // Entrada: string com o caminho e string com o nome do arquivo                                                    *
    // Saida: = boolean - true se a operação foi realizada                                                             *
    //******************************************************************************************************************
    //
    public static boolean ApagaArquivo(String Caminho, String NomeArquivo) {
        File arquivo = new File(Caminho + NomeArquivo);
        return (arquivo.delete());
    }

    //******************************************************************************************************************
    // Nome do Método: TipoArquivo                                                                                     *
    //	                                                                                                               *
    // Funcao: verifica o tipo do arquivo pela extensão                                                                *
    //                                                                                                                 *
    // Entrada: string com o nome do arquivo                                                                           *
    // Saida: = string com o tipo do arquivo                                                                           *
    //******************************************************************************************************************
    //
    public static String TipoArquivo(String NomeArquivo) {
        String tipo = null;

        if (NomeArquivo.endsWith(".htm")  ||  NomeArquivo.endsWith(".html")) { tipo = "text/html"; }
        if (NomeArquivo.endsWith(".js")) { tipo = "text/javascript"; }
        if (NomeArquivo.endsWith(".css")) { tipo = "text/css"; }
        if (NomeArquivo.endsWith(".jpg")  ||  NomeArquivo.endsWith(".jpeg")) { tipo = "image/jpeg"; }
        if (NomeArquivo.endsWith(".gif")) { tipo = "image/gif"; }
        if (NomeArquivo.endsWith(".png")) { tipo = "image/png"; }
        if (NomeArquivo.endsWith(".bmp")) { tipo = "image/bmp"; }
        if (NomeArquivo.endsWith(".txt")) { tipo = "text/plain"; }

        return(tipo);
    }

    //******************************************************************************************************************
    // Nome do Método: LeParagrafos                                                                                    *
    //	                                                                                                               *
    // Funcao: lê um arquivo texto (sequência de caracteres) e retorna um array de strings com os parágrafos.          *
    //         No arquivo lido, deve haver uma linha vazia entre um parágrafo e outro.                                 *
    //                                                                                                                 *
    // Entrada: string com o caminho do arquivo e string com o nome do arquivo                                         *
    //                                                                                                                 *
    // Saida: array de string com os parágrafos do arquivo texto lido. Se ocorrer falha na leitura, retorna null       *
    //******************************************************************************************************************
    //
    public static List<String> LeParagrafos(String caminho, String nomeArquivo) {

        File arquivo = new File(caminho + nomeArquivo);
        List<String> arrayListParagrafo = new ArrayList<>();
        String paragrafo = "";
        try {
            BufferedReader br = new BufferedReader(new FileReader(arquivo));
            int i = 0;
            String linha;
            while ((linha = br.readLine()) != null) {
                if (linha.equals("")) {
                    arrayListParagrafo.add(paragrafo);
                    paragrafo = linha;
                }
                else {
                    paragrafo = paragrafo + linha;
                }

            }
            return arrayListParagrafo;

        } catch (IOException e) {
            return null;
        }
    }

    //******************************************************************************************************************
    // Nome do Método: LeParametro                                                                                     *
    //                                                                                                                 *
    // Funcao: procura um token em um arquivo texto e retorna o parâmetro que está após o token                        *
    //                                                                                                                 *
    // Entrada: string com o arquivo texto e string com o token                                                        *
    //                                                                                                                 *
    // Saida: string com o parâmetro lido após o token                                                                 *
    //******************************************************************************************************************
    //
    public static String LeParametro(String arquivo, String token){
        int Indice = arquivo.lastIndexOf(token);
        int indiceF = arquivo.length() - 1;
        String parametro = null;
        if (Indice >= 0) {
            Indice = Indice + token.length() + 1;
            String Substring = arquivo.substring(Indice, indiceF);
            StringTokenizer parseToken = new StringTokenizer(Substring);
            parametro = parseToken.nextToken();
        }
        return parametro;
    }

    //******************************************************************************************************************
    // Nome do Método: LeCampo                                                                                         *
    //                                                                                                                 *
    // Funcao: procura um token em um arquivo texto e retorna o campo que está após o token até o próximo CR/LF        *
    //                                                                                                                 *
    // Entrada: string com o arquivo texto e string com o token                                                        *
    //                                                                                                                 *
    // Saida: string com o parâmetro lido após o token                                                                 *
    //******************************************************************************************************************
    //
    public static String LeCampo(String arquivo, String token) {
        String campo;
        try {
            int indiceToken = arquivo.indexOf(token);
            if (indiceToken > 0) {
                int indiceAposToken = indiceToken + token.length();

                String arquivoAposToken = arquivo.substring(indiceAposToken, arquivo.length());
                int indiceCRLF = arquivoAposToken.indexOf("\n");

                return arquivoAposToken.substring(1, indiceCRLF);
            }
            else {
                return "null";
            }
        } catch (Exception e) {
            return "null";
        }
    }

    //******************************************************************************************************************
    // Nome do Método: LeCampoHTML                                                                                     *
    //                                                                                                                 *
    // Funcao: procura um token em um arquivo texto e retorna o campo que está após o token entre os caracteres < e >  *
    //                                                                                                                 *
    // Entrada: string com o arquivo texto e string com o token                                                        *
    //                                                                                                                 *
    // Saida: string com o campo lido após o token                                                                     *
    //******************************************************************************************************************
    //
    public static String LeCampoHTML(String arquivo, String token) {
        String campo = null;
        int fimArquivo = arquivo.length();
        int indiceToken = arquivo.indexOf(token);
        if (indiceToken > 0) {
            int indiceAposToken = indiceToken + token.length();
            for (int i = indiceAposToken; i < fimArquivo; i++) {
                if (arquivo.charAt(i) == '<') {
                    //System.out.println("i = " + i);
                    for (int k = i + 1; k < fimArquivo; k++) {
                        if (arquivo.charAt(k) == '>') {
                            //System.out.println("k = " + k);
                            campo = arquivo.substring(i, k + 1);
                            //System.out.println("Campo: " + campo);
                            k = fimArquivo;
                            i = fimArquivo;
                        }
                    }
                }
            }
        }
        return campo;
    }

    //******************************************************************************************************************
    // Nome do Método: MostraUsuarios                                                                                  *
    //                                                                                                                 *
    // Funcao: imprime no terminal os nomes e as senhas dos usuários cadastrados                                       *
    //                                                                                                                 *
    // Entrada: não tem                                                                                                *
    //                                                                                                                 *
    // Saida: não tem                                                                                                  *
    //******************************************************************************************************************
    //
    public static void MostraUsuarios() {
        if (verbose) {
            System.out.print("Informações dos usuários: ");
            System.out.println(numUsuarios + " usuários");
            for (int i = 0; i <= numUsuarios - 1; i++) {
                System.out.println("Usuário " + i + ": " + nomeUsuario[i] + " - Senha: " + senhaUsuario[i]);
            }
            System.out.println("");
        }
    }

    private String msgArqNaoEncontrado(String nomeArquivo) {

        return ("<p></p><h3>File not found: " + nomeArquivo + "</h3>");
    }

    //*****************************************************************************************************************
    // Nome do Método: LeDataHora                                                                                     *
    //                                                                                                                *
    // Funcao: le a data e hora do relogio do computador                                                              *
    //                                                                                                                *
    // Entrada: não tem                                                                                               *
    //                                                                                                                *
    // Saida: array com 6 Bytes: [0] = Hora, [1] = Minuto, [2] = Segundo, [3] = Dia, [4] = Mês, [5] = Ano             *                                                                                 *
    //                                                                                                                *
    //*****************************************************************************************************************
    //
    public static byte[] LeDataHora() {

        DateFormat dfor = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        Date obj = new Date();
        String DataHora = dfor.format(obj); // 04/08/2021 17:22:48
        // 0123456789012345678
        // 0000000000111111111

        byte DH [] = new byte[7];
        int Dia = 10 * Character.digit(DataHora.charAt(0), 10) + Character.digit(DataHora.charAt(1), 10);
        int Mes = 10 * Character.digit(DataHora.charAt(3), 10) + Character.digit(DataHora.charAt(4), 10);
        int AnoCentenas = 10 * Character.digit(DataHora.charAt(6), 10) + Character.digit(DataHora.charAt(7), 10);
        int AnoUnidades = 10 * Character.digit(DataHora.charAt(8), 10) + Character.digit(DataHora.charAt(9), 10);

        int Hora = 10 * Character.digit(DataHora.charAt(11), 10) + Character.digit(DataHora.charAt(12), 10);
        int Minuto = 10 * Character.digit(DataHora.charAt(14), 10) + Character.digit(DataHora.charAt(15), 10);
        int Segundo = 10 * Character.digit(DataHora.charAt(17), 10) + Character.digit(DataHora.charAt(18), 10);

        DH[0] = ByteLow(Hora);         // Hora
        DH[1] = ByteLow(Minuto);       // Minuto
        DH[2] = ByteLow(Segundo);      // Segundo
        DH[3] = ByteLow(Dia);          // Dia
        DH[4] = ByteLow(Mes);          // Mes
        DH[5] = ByteLow(AnoUnidades);  // Ano (Unidades)
        DH[6] = ByteLow(AnoCentenas);  // Ano (Centenas)

        return(DH);
    }

    //*****************************************************************************************************************
    // Nome do Método: LeDataHoraNTP                                                                                  *
    //                                                                                                                *
    // Funcao: le a data e hora do relogio de um servidor NTP                                                         *
    //                                                                                                                *
    // Entrada: não tem                                                                                               *
    //                                                                                                                *
    // Saida: array com 6 Bytes: [0] = Hora, [1] = Minuto, [2] = Segundo, [3] = Dia, [4] = Mês, [5] = Ano             *                                                                                 *
    //                                                                                                                *
    //*****************************************************************************************************************
    //
    static byte[] LeDataHoraNTP() {

        String EndIP = "132.163.97.5";
        int Porta = 123;

        byte[] MsgReqNTP = new byte[16];
        int TamMsgReq = MsgReqNTP.length;

        byte[] MsgRecNTP = new byte[16];
        int TamMsgRecNTP = MsgRecNTP.length;

        try {

            DatagramSocket clientSocket = new DatagramSocket();
            InetAddress IPAddress = InetAddress.getByName(EndIP);
            clientSocket.setSoTimeout(1000);
            DatagramPacket sendPacket = new DatagramPacket(MsgReqNTP, TamMsgReq, IPAddress, Porta);
            DatagramPacket receivePacket = new DatagramPacket(MsgRecNTP, TamMsgRecNTP);

            clientSocket.send(sendPacket);
            Terminal("Enviada Requisicao NTP", false);

            // Espera a Mensagem CoAP de Resposta.
            try {
                clientSocket.receive(receivePacket);      // Se a mensagem de resposta  for recebida corretamente,

                Terminal("Recebida Mensagem CoAP do Controlador", false);
                clientSocket.close();

            } catch (java.net.SocketTimeoutException e) { // Se o dispositivo não respondeu,

                Terminal(" - Erro: o Dispositivo nao Respondeu ", false);
                clientSocket.close();
            }

        } catch (IOException err) {

        }
        byte DH [] = new byte[7];
        return(DH);
    }


    //*****************************************************************************************************************
    // Nome do Método: ImprimeHoraData                                                                                *
    //                                                                                                                *
    // Funcao: gera uma string com a data e a hora recebida em um array de bytes                                      *
    //                                                                                                                *
    // Entrada: Array[6] [0] = Hora, [1] = Minutos, [2] = Segundos, [3] = Dia, [4] = Mês, [5] = Ano, [6] = Ano        *
    //                                                                                                                *
    // Saida: string no formato HH:MM:SS - DD/MM/AAAA                                                                 *                                                                                 *
    //                                                                                                                *
    //*****************************************************************************************************************
    //
    static String ImprimeHoraData(byte [] DH, boolean Opcao) {

        String Msg = "";
        if (DH[0] < 10) { Msg = Msg + "0"; }
        Msg = Msg + DH[0] + ":";
        if (DH[1] < 10) { Msg = Msg + "0"; }
        Msg = Msg + DH[1] + ":";
        if (DH[2] < 10) { Msg = Msg + "0"; }
        Msg = Msg + DH[2];

        if (Opcao) {
            Msg = Msg + " - ";
            if (DH[3] < 10) { Msg = Msg + "0"; }
            Msg = Msg + DH[3] + "/";
            if (DH[4] < 10) { Msg = Msg + "0"; }
            Msg = Msg + DH[4] + "/" + DH[5] + DH[6];
        }

        return(Msg);
    }

    //*****************************************************************************************************************
    // Nome do Método: Terminal                                                                                       *
    //                                                                                                                *
    // Funcao: imprime uma mensagem no Terminal                                                                       *
    //                                                                                                                *
    // Entrada: string com a mensagem e a flag de habilitação (verbose)                                               *
    //                                                                                                                *
    // Saida: não tem                                                                                                 *
    //*****************************************************************************************************************
    //
    public static void Terminal(String Msg, boolean Opcao) {
        if (verbose || Opcao) {
            System.out.println(ImprimeHoraData(LeDataHora(), false) + " - " + Msg);
        }
    }


    //******************************************************************************************************************
    // Nome do Método: IdComando                                                                                       *
    //                                                                                                                 *
    // Funcao: retorna uma string com a identificação do comando                                                       *
    //                                                                                                                 *
    // Entrada: número de identificação do comando                                                                     *
    // Saida: string com a identificação do comando                                                                    *
    //******************************************************************************************************************
    //
    public static String IdComando(int Comando) {
        String StrComando = "";

        if (Comando == 2)  { StrComando = "Acerto Relogio"; }
        if (Comando == 3)  { StrComando = "Modo Economia"; }
        if (Comando == 4)  { StrComando = "Modo Normal"; }
        if (Comando == 16) { StrComando = "Manual Carga 1"; }
        if (Comando == 17) { StrComando = "Automatico Carga 1"; }
        if (Comando == 5)  { StrComando = "Desabilita Carga 3 falta CA"; }
        if (Comando == 6)  { StrComando = "Habilita Carga 3 falta CA"; }
        if (Comando == 7) { StrComando = "Carga 1 para Inversor"; }
        if (Comando == 8) { StrComando = "Carga 1 para Rede"; }
        if (Comando == 9) { StrComando = "Carga 2 para Inversor"; }
        if (Comando == 10) { StrComando = "Carga 2 para Rede"; }
        if (Comando == 11) { StrComando = "Carga 3 para Inversor"; }
        if (Comando == 12) { StrComando = "Carga 3 para Rede"; }
        if (Comando == 13) { StrComando = "Habilita Carga 4"; }
        if (Comando == 14) { StrComando = "Desabilita Carga 4"; }
        if (Comando == 15) { StrComando = "Apaga Indicadores de Falha"; }
        if (Comando == 20) { StrComando = "Reinicia Assinatura Agente"; }

        return(StrComando);
    }

    //******************************************************************************************************************
    // Nome do Método: BytetoInt                                                                                       *
    //                                                                                                                 *
    // Funcao: converte um valor byte para inteiro (conversao sem sinal)                                               *
    // Entrada: valor byte  (-128 a +127)                                                                              *
    // Saida: valor (inteiro) sempre positivo de 0 a 255                                                               *
    //******************************************************************************************************************
    //
    public static int BytetoInt(byte valor) {
        if (valor < 0) {
            return(256 + valor);
        }
        else {
            return(valor);
        }
    }

    //*****************************************************************************************************************
    // Nome do Método: DoisBytetoInt                                                                                  *
    //                                                                                                                *
    // Funcao: converte dois bytes em sequencia de um array para inteiro (conversao sem sinal)                        *
    // Entrada: dois bytes consecutivos (LSB e MSB) sem sinal de 0 a 255                                              *
    // Saida: valor (inteiro) sempre positivo de 0 a 65535                                                            *
    //*****************************************************************************************************************
    //
    public static int DoisBytesInt(int LSByte, int MSByte) {
        int lsb;
        int msb;
        if (LSByte < 0) { lsb = LSByte + 256; }
        else { lsb = LSByte; }
        if (MSByte < 0) { msb = MSByte + 256; }
        else { msb = MSByte; }
        return (lsb + 256*msb);
    }

    //*****************************************************************************************************************
    // Nome do Método: TwoBytetoInt                                                                                   *
    //                                                                                                                *
    // Funcao: converte dois bytes em sequencia de um array para inteiro (conversao sem sinal)                        *
    // Entrada: dois bytes consecutivos (LSB e MSB) sem sinal de 0 a 255                                              *
    // Saida: valor (inteiro) sempre positivo de 0 a 65535                                                            *
    //*****************************************************************************************************************
    //
    public static int TwoBytetoInt(byte LSByte, byte MSByte) {
        int lsb;
        int msb;
        if (LSByte < 0) { lsb = LSByte + 256; }
        else { lsb = LSByte; }
        if (MSByte < 0) { msb = MSByte + 256; }
        else { msb = MSByte; }
        return (lsb + 256*msb);
    }

    //*****************************************************************************************************************
    // Nome do Método: ThreeBytetoInt                                                                                 *
    //                                                                                                                *
    // Funcao: converte tres bytes em sequencia de um array para inteiro (conversao sem sinal)                        *
    // Entrada: dois bytes consecutivos (LSB e MSB) sem sinal de 0 a 255                                              *
    // Saida: valor (inteiro) sempre positivo de 0 a 65535                                                            *
    //*****************************************************************************************************************
    //
    public static int ThreeBytetoInt(int LSByte, int MSByte, int HSByte) {
        int lsb;
        int msb;
        int hsb;
        if (LSByte < 0) { lsb = LSByte + 256; }
        else { lsb = LSByte; }
        if (MSByte < 0) { msb = MSByte + 256; }
        else { msb = MSByte; }
        if (HSByte < 0) { hsb = HSByte + 256; }
        else { hsb = HSByte; }
        return (lsb + 256*msb + 65536*hsb);
    }

    //*****************************************************************************************************************
    // Nome do Método: FormAnaHora                                                                                    *
    //                                                                                                                *
    // Funcao: converte um inteiro positivo para uma string no formato HH:MM:SS  (hora:minuto:segundo)                *
    // Entrada: valor inteiro em segundos                                                                             *
    // Saida: string do numero no formato "parte inteira","duas casas decimais"                                       *
    //*****************************************************************************************************************
    //
    static String FormAnaHora(int valor) {
        int Hora = valor / 3600;
        int Minuto = ((valor - (Hora * 3600)) / 60);
        int Segundo = valor - (Minuto * 60) - (Hora * 3600);
        String HMS = "";
        if (Hora < 10) {
            HMS = "0";
        }
        HMS = (HMS + Hora + ":");
        if (Minuto < 10) {
            HMS = HMS + "0";
        }
        HMS = (HMS + Minuto + ":");
        if (Segundo < 10) {
            HMS = HMS + "0";
        }
        HMS = HMS + Segundo;

        return (HMS);
    }

    //*****************************************************************************************************************
    // Nome do Método: CharToByte                                                                                     *
    //                                                                                                                *
    // Funcao: converte um caracter numerico em um valor numerico de 0 a 9                                            *
    // Entrada: caracter: '0' a '9'                                                                                   *
    // Saida: byte (valor numerico de 0 a 9)                                                                          *
    //*****************************************************************************************************************
    //
    static int CharToByte(char caracter) {
        byte Num = 10;
        switch (caracter) {
            case '0': Num = 0;
                break;
            case '1': Num = 1;
                break;
            case '2': Num = 2;
                break;
            case '3': Num = 3;
                break;
            case '4': Num = 4;
                break;
            case '5': Num = 5;
                break;
            case '6': Num = 6;
                break;
            case '7': Num = 7;
                break;
            case '8': Num = 8;
                break;
            case '9': Num = 9;
                break;
        }
        return (Num);
    }

    //*****************************************************************************************************************
    // Nome do Método: CharToInt                                                                                      *
    //                                                                                                                *
    // Funcao: converte um caracter numerico em um valor numerico de 0 a 9                                            *
    // Entrada: caracter: '0' a '9'                                                                                   *
    // Saida: int (valor numerico de 0 a 9)                                                                           *
    //*****************************************************************************************************************
    //
    static int ChToInt(char caracter) {
        int Num = 10;
        switch (caracter) {
            case '0': Num = 0;
                break;
            case '1': Num = 1;
                break;
            case '2': Num = 2;
                break;
            case '3': Num = 3;
                break;
            case '4': Num = 4;
                break;
            case '5': Num = 5;
                break;
            case '6': Num = 6;
                break;
            case '7': Num = 7;
                break;
            case '8': Num = 8;
                break;
            case '9': Num = 9;
                break;
        }
        return (Num);
    }

    //*****************************************************************************************************************
    // Nome do Método: TwoCharToByte                                                                                  *
    //                                                                                                                *
    // Funcao: converte dois caracteres numericos em um valor numerico de 00 a 99                                     *
    // Entrada: caracteres dezena e unidade ('0' a '9')                                                               *
    // Saida: byte (valor numerico de 00 a 99)                                                                        *
    //*****************************************************************************************************************
    //
    //static int TwoCharToByte(char Ch10, char Ch1) {
    //	int Num = 10*CharToByte(Ch10) + CharToByte(Ch1);
    //	return ((byte)Num);
    //}

    //*****************************************************************************************************************
    // Nome do Método: TwoCharToInt                                                                                  *
    //                                                                                                                *
    // Funcao: converte dois caracteres numericos em um valor numerico de 00 a 99                                     *
    // Entrada: caracteres dezena e unidade ('0' a '9')                                                               *
    // Saida: int (valor numerico de 00 a 99)                                                                        *
    //*****************************************************************************************************************
    //
    static int TwoCharToInt(char Ch10, char Ch1) {
        int Num = 10*CharToByte(Ch10) + CharToByte(Ch1);
        return (Num);
    }

    //*****************************************************************************************************************
    // Nome do Método: FourCharToInt                                                                                  *
    //                                                                                                                *
    // Funcao: converte quatro caracteres numericos em um valor numerico de 0000 a 9999                               *
    // Entrada: caracteres milhar, centena, dezena e unidade ('0' a '9')                                              *
    // Saida: int (valor numerico de 0000 a 9999)                                                                     *
    //*****************************************************************************************************************
    //
    static int FourCharToInt(char Ch1000, char Ch100, char Ch10, char Ch1) {
        int Num = 1000*CharToByte(Ch1000) + 100*CharToByte(Ch100) + 10*CharToByte(Ch10) + CharToByte(Ch1);
        return (Num);
    }

    //*****************************************************************************************************************
    // Nome do Método: StringToInt                                                                                    *
    //                                                                                                                *
    // Funcao: converte uma string de até quatro caracteres numericos em um valor numerico de 0000 a 9999             *
    // Entrada: string com um numero entre 0 e 9999                                                                   *
    // Saida: int (valor numerico de 0000 a 9999 correspondente à string de entrada)                                  *
    //*****************************************************************************************************************
    //
    public static int StringToInt(String StNm) {
        int Num = 0;
        int TamNum = StNm.length();

        if (TamNum == 1) {
            Num = ChToInt(StNm.charAt(0));
        }
        if (TamNum == 2) {
            Num = 10*ChToInt(StNm.charAt(0)) + ChToInt(StNm.charAt(1));
        }
        if (TamNum == 3) {
            Num = 100*ChToInt(StNm.charAt(0)) + 10*ChToInt(StNm.charAt(1)) + ChToInt(StNm.charAt(2));
        }
        if (TamNum == 4) {
            Num = 1000*ChToInt(StNm.charAt(0))+100*ChToInt(StNm.charAt(1))+10*ChToInt(StNm.charAt(2))+ChToInt(StNm.charAt(3));
        }
        return (Num);
    }

    //*****************************************************************************************************************
    // Nome do Método: ByteLow                                                                                        *
    //                                                                                                                *
    // Funcao: obtem o byte menos significativo de um valor inteiro                                                   *
    // Entrada: valor inteiro                                                                                         *
    // Saida: byte menos significativo                                                                                *
    //*****************************************************************************************************************
    //
    public static byte ByteLow(int valor) {
        int BH = valor / 256;
        int BL = valor - 256*BH;
        return ((byte)BL);
    }

    //*****************************************************************************************************************
    // Nome do Método: ByteHigh                                                                                       *
    //                                                                                                                *
    // Funcao: obtem o byte mais significativo de um valor inteiro                                                    *
    // Entrada: valor inteiro                                                                                         *
    // Saida: byte mais significativo                                                                                 *
    //                                                                                                                *
    //*****************************************************************************************************************
    //
    public static byte ByteHigh(int valor) {
        int BH = valor / 256;
        return ((byte)BH);
    }

    //*****************************************************************************************************************
    // Nome do Método: ImpHora                                                                                        *
    //                                                                                                                *
    // Funcao: gera umna string com hora:minuto:segundo dia/mes/ano                                                   *
    // Entrada: valor hora, minuto, segundo, dia, mes, ano                                                            *
    // Saida: byte mais significativo                                                                                 *
    //                                                                                                                *
    //*****************************************************************************************************************
    //
    static String ImpHora(int hora, int minuto, int segundo) {

        String Msg = "";
        if (hora < 10) { Msg = Msg + "0"; }
        Msg = Msg + hora + ":";

        if (minuto < 10) { Msg = Msg + "0"; }
        Msg = Msg + minuto + ":";

        if (segundo < 10) { Msg = Msg + "0"; }
        Msg = Msg + segundo;

        return(Msg);
    }

    //*****************************************************************************************************************
    // Nome do Método: ImpData                                                                                        *
    //                                                                                                                *
    // Funcao: gera umna string com hora:minuto:segundo dia/mes/ano                                                   *
    // Entrada: valor hora, minuto, segundo, dia, mes, ano                                                            *
    // Saida: byte mais significativo                                                                                 *
    //                                                                                                                *
    //*****************************************************************************************************************
    //
    static String ImpData(int dia, int mes, int ano) {

        String Msg = "";
        if (dia < 10) { Msg = Msg + "0"; }
        Msg = Msg + dia + "/";

        if (mes < 10) { Msg = Msg + "0"; }
        Msg = Msg + mes + "/" + ano + " ";

        return(Msg);
    }

    //*****************************************************************************************************************
    // Nome do Método: FormAna                                                                                        *
    //                                                                                                                *
    // Funcao: converte um inteiro positivo em formato x100 para uma string com parte inteira e duas casas decimais   *
    // Entrada: valor inteiro no formato x100                                                                         *
    // Saida: string do numero no formato "parte inteira","duas casas decimais"                                       *
    //                                                                                                                *
    //*****************************************************************************************************************
    //
    static String FormAna(int valor) {
        int inteiro;
        int decimal;
        inteiro = valor / 100;
        decimal = valor - 100*inteiro;
        String Valor = (inteiro + ",");
        if (decimal > 9) {
            Valor = Valor + decimal;
        }
        else {
            Valor = Valor + "0" + decimal;
        }
        return (Valor);
    }

    //*****************************************************************************************************************
    // Nome do Método: FrmAna                                                                                         *
    //                                                                                                                *
    // Funcao: converte um inteiro positivo em formato x100 para uma string com parte inteira e duas casas decimais   *
    // Entrada: valor inteiro no formato x100 e unidade em string                                                     *
    // Saida: string do numero no formato "parte inteira","duas casas decimais"                                       *
    //                                                                                                                *
    //*****************************************************************************************************************
    //
    static String FrmAna(int valor, String Unidade) {
        int inteiro;
        int decimal;
        inteiro = valor / 100;
        decimal = valor - 100*inteiro;
        String Valor = (inteiro + ",");
        if (decimal > 9) {
            Valor = Valor + decimal;
        }
        else {
            Valor = Valor + "0" + decimal;
        }
        return (Valor + Unidade);
    }

    //*****************************************************************************************************************
    // Nome do Método: FrmAnaDouble                                                                                   *
    //                                                                                                                *
    // Funcao: converte um inteiro positivo em formato x100 para uma string com parte inteira e duas casas decimais   *
    // Entrada: valor inteiro no formato x100 e unidade em string                                                     *
    // Saida: string do numero no formato "parte inteira","duas casas decimais"                                       *
    //                                                                                                                *
    //*****************************************************************************************************************
    //
    static String FrmAnaDouble(double valor, String Unidade) {

        DecimalFormat df = new DecimalFormat("0.00");
        String Valor = (df.format(valor) + " ");
        return (Valor + Unidade);
    }

    //*****************************************************************************************************************
    // Nome do Método: FrmAna3                                                                                        *
    //                                                                                                                *
    // Funcao: converte um inteiro positivo em formato x1000 para uma string com parte inteira e tres casas decimais  *
    // Entrada: valor inteiro no formato x1000 e unidade em string                                                    *
    // Saida: string do numero no formato "parte inteira","tres casas decimais"                                       *
    //                                                                                                                *
    //*****************************************************************************************************************
    //
    static String FrmAna3(int valor, String Unidade) {
        int inteiro;
        int decimal;
        inteiro = valor / 1000;
        decimal = valor - 1000 * inteiro;
        String Valor = (inteiro + ",");
        if (decimal > 90) {
            Valor = Valor + decimal;
        }
        else {
            Valor = Valor + "0" + decimal;
        }
        return (Valor + Unidade);
    }

    //*****************************************************************************************************************
    // Nome do Método: FormAna3                                                                                       *
    //                                                                                                                *
    // Funcao: converte um inteiro positivo em formato x1000 para uma string com parte inteira e tres casas decimais  *
    // Entrada: valor inteiro no formato x1000                                                                        *
    // Saida: string do numero no formato "parte inteira","tres casas decimais"                                       *
    //                                                                                                                *
    //*****************************************************************************************************************
    //
    static String FormAna3(int valor) {
        int inteiro;
        int decimal;
        inteiro = valor / 1000;
        decimal = valor - 1000 * inteiro;
        String Valor = (inteiro + ",");
        if (decimal > 90) {
            Valor = Valor + decimal;
        }
        else {
            Valor = Valor + "0" + decimal;
        }
        return (Valor);
    }

    //*****************************************************************************************************************
    // Nome do Método: FormAnaInt                                                                                     *
    //                                                                                                                *
    // Funcao: converte um inteiro positivo para uma string                                                           *
    // Entrada: valor inteiro no formato x100                                                                         *
    // Saida: string do numero no formato "parte inteira","duas casas decimais"                                       *
    //                                                                                                                *
    //*****************************************************************************************************************
    //
    static String FormAnaInt(int valor) {

        String Valor = (valor + " ");

        return (Valor);
    }

    //*****************************************************************************************************************
    // Nome do Método: FormAnaInt                                                                                     *
    //                                                                                                                *
    // Funcao: converte um inteiro positivo para uma string                                                           *
    // Entrada: valor inteiro no formato x100                                                                         *
    // Saida: string do numero no formato "parte inteira","duas casas decimais"                                       *
    //                                                                                                                *
    //*****************************************************************************************************************
    //
    static String FrmAnaInt(int valor, String Unidade) {

        String Valor = (valor + Unidade);

        return (Valor);
    }

    public static String ImpEstCom(boolean EstCom) {

        String est = "";
        if (EstCom) {
            est = "Normal";
        }
        else {
            est = "Falha";
        }
        return(est);

    }

    public static double FormataDouble2CD(double valor) {

        int ValorIntx100 = (int)(valor * 100.0);
        double ValorDoublex100 = ValorIntx100;

        return(ValorDoublex100 / 100.0);

    }

    public static void ImprimeLinhaBranco(boolean opcao) {
        if (isOpLocal() || opcao) {
            System.out.println("");
        }
    }
}
