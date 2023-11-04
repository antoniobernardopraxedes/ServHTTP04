package com.praxsoft.ServHTTP04;

import com.google.gson.Gson;
import java.io.IOException;
import java.net.*;
import static com.praxsoft.ServHTTP04.SupService.*;

public class Dados {

    private static UTRAM utr = new UTRAM();
    private static CC cc1 = new CC();
    private static CC cc2 = new CC();
    private static CAQ caq = new CAQ();
    private static CEN cen = new CEN();

    private static int ContMsgCoAP;
    private static int numComando = 0;
    private static String MsgComando = "";

    public static int incContMsgCoAP() {
        ContMsgCoAP++;
        if (ContMsgCoAP >= 256) {
            ContMsgCoAP = 0;
        }
        return ContMsgCoAP;
    }

    public static int valorNumComando() {
        return numComando;
    }

    public static void escreveNumComando(int num) {
        numComando = num;
    }

    public static void zeraNumComando() {
        numComando = 0;
    }

    public static void EscreveMsgComando(String Msg) {
        MsgComando = Msg;
    }

    public static String LeMsgComando() {
        return MsgComando;
    }

    //******************************************************************************************************************
    // Autor: Antonio Bernardo de Vasconcellos Praxedes                                                                *
    //	                                                                                                               *
    // Nome do Método: MontaJsonUTR                                                                                    *
    //	                                                                                                               *
    // Funcao: monta a mensagem JSON com as informações da UTR (Unidade Terminal Remota) a partir das variáveis lidas  *
    //         do Concentrador Arduino Mega.                                                                           *
    //                                                                                                                 *
    // Entrada: não tem                                                                                                *
    // Saida: String com a mensagem JSON                                                                               *
    //******************************************************************************************************************
    //
    public static String MontaJsonUTR(String cmdEx) {

        if (!utr.estComConcMega || !utr.estComUtr) {
            utr.cmdEx = cmdEx;
            utr.hora = 0;
            utr.minuto = 0;
            utr.segundo = 0;
            utr.dia = 0;
            utr.mes = 0;
            utr.ano = 0;

            utr.modoOp = "-----";
            utr.modoCom = "-----";
            utr.modoCtrl1 = "-----";
            utr.modoCtrl = "-----";
            utr.vRede = 0;
            utr.estRede = "-----";
            utr.vBat = 0;
            utr.vmBat = 0;
            utr.tBat = 0;
            utr.energiaCg1 = "-----";
            utr.energiaCg2 = "-----";
            utr.energiaCg3 = "-----";
            utr.energiaCg4 = "-----";
            utr.iCg3 = 0;
            utr.iTotCg24v = 0;
            utr.wTotCg24v = 0;
            utr.iCirCc = 0;
            utr.wCirCC = 0;
            utr.estFontesCC = "-----";

            utr.estInvD = "-----";
            utr.iEInvD = 0;
            utr.wEInvD = 0;
            utr.vSInvD = 0;
            utr.iSInvD = 0;
            utr.wSInvD = 0;
            utr.tDInvD = 0;
            utr.tTInvD = 0;
            utr.estInvE = "-----";
            utr.iEInvE = 0;
            utr.wEInvE = 0;
            utr.vSInvE = 0;
            utr.iSInvE = 0;
            utr.wSInvE = 0;
            utr.tDInvE = 0;
            utr.tTInvE = 0;

            utr.estCxAzul = "-----";
            utr.nivCxAzul = "-----";
            utr.estBomba = "-----";
            utr.estDjBoia = "-----";
            utr.estDjBomba = "-----";
            utr.tmpBombaLig = 0;
        }
        Gson gson = new Gson();
        String MsgJson = gson.toJson(utr);
        return(MsgJson);

    } // Fim do Método

    //******************************************************************************************************************
    // Autor: Antonio Bernardo de Vasconcellos Praxedes  /  Data: 30/08/2023                                           *
    //	                                                                                                               *
    // Nome do Método: MontaJsonCC1                                                                                    *
    //	                                                                                                               *
    // Funcao: monta a mensagem JSON com as informações do Controlador de Carga 1 a partir das variáveis lidas do      *
    //         Concentrador Arduino Mega.                                                                              *
    //                                                                                                                 *
    // Entrada: não tem                                                                                                *
    // Saida: String com a mensagem JSON                                                                               *
    //******************************************************************************************************************
    //
    public static String MontaJsonCC1() {

        if (!utr.estComConcMega || !cc1.estComCc) {
            cc1.vECc = 0;
            cc1.iECc = 0;
            cc1.wECc = 0;
            cc1.vSCc = 0;
            cc1.iSCc = 0;
            cc1.wSCc = 0;
            cc1.tbat = 0;
        }
        Gson gson = new Gson();
        String MsgJson = gson.toJson(cc1);
        return(MsgJson);

    } // Fim do Método

    //******************************************************************************************************************
    // Autor: Antonio Bernardo de Vasconcellos Praxedes  /  Data: 30/08/2023                                           *
    //	                                                                                                               *
    // Nome do Método: MontaJsonCC2                                                                                    *
    //	                                                                                                               *
    // Funcao: monta a mensagem JSON com as informações do Controlador de Carga 2 a partir das variáveis lidas do      *
    //         Concentrador Arduino Mega.                                                                              *
    //                                                                                                                 *
    // Entrada: não tem                                                                                                *
    // Saida: String com a mensagem JSON                                                                               *
    //******************************************************************************************************************
    //
    public static String MontaJsonCC2() {

        if (!utr.estComConcMega || !cc2.estComCc) {
            cc2.vECc = 0;
            cc2.iECc = 0;
            cc2.wECc = 0;
            cc2.vSCc = 0;
            cc2.iSCc = 0;
            cc2.wSCc = 0;
            cc2.tbat = 0;
        }
        Gson gson = new Gson();
        String MsgJson = gson.toJson(cc2);

        return(MsgJson);

    } // Fim do Método

    //******************************************************************************************************************
    // Nome do Método: EnvRecMsgConcMega                                                                               *
    //                                                                                                                 *
    // Funcao: envia uma mensagem de requisição e recebe a mensagem de resposta do Controlador Arduino Mega            *
    //         em Protocolo CoAP                                                                                       *
    //                                                                                                                 *
    // Byte |           0         |      1       |      2      |        3        |        4        |        5        | *
    // Bit  | 7 6 | 5 4 | 3 2 1 0 |  7654  3210  |  7654 3210  | 7 6 5 4 3 2 1 0 | 7 6 5 4 3 2 1 0 | 7 6 5 4 3 2 1 0 | *
    //      | Ver |Tipo |  Token  | Código (c.m) | Message ID  |      Option     |   Payload ID    |                   *
    //                                                                                                                 *
    // Ver (Versão) = 01 (O número da versão do protocolo CoAP é fixo)  / TKL (Token) = 0000 (não é usado)             *
    // Tipo (de Mensagem): 00 Confirmável (CON) / 01 Não-Confirmável (NON) / 10 Reconhecimento (ACK) / 11 Reset (RST)  *
    //                                                                                                                 *
    // Códigos de Solicitação: 0000 0000 EMPTY / 0000 0001 GET   / 0000 0010 POST / 0000 0011 PUT / 0000 0100 DELETE   *
    //                                                                                                                 *
    // Códigos de Resposta (Sucesso): 0100 0001 Created / 0100 0010 Deleted / 0100 0011 Valid / 0100 0100 Changed      *
    //                                0100 0101 Content                                                                *
    //                                                                                                                 *
    // Códigos de Erro Cliente: 1000 0000 Bad Request / 1000 0001 Unauthorized / 1000 0010 Bad Option                  *
    //                          1000 0011 Forbidden / 1000 0100 Not Found / 1000 0101 Method Not Allowed               *
    //                          1000 0110 Not Acceptable / 1000 1100 Request Entity Incomplete                         *
    //                                                                                                                 *
    // Códigos de Erro Servidor: 1010 0000 Internal Server Error / 1010 0001 Not Implemented / 1010 0010 Bad Gateway   *
    //                           1010 0011 Service Unavailable / 1010 0100 Gateway Timeout                             *
    //                           1010 0101 Proxying Not Supported                                                      *
    //                                                                                                                 *
    // Message ID (Identificação da mensagem): inteiro de 16 bits sem sinal Mensagem Enviada e Mensagem de Resposta    *
    //                                         com mesmo ID                                                            *
    //                                                                                                                 *
    // Option (Opções) = 0000 0000 (não é usado) / Identificador de Início do Payload: 1111 1111                       *
    //******************************************************************************************************************
    //
    public static void EnvRecMsgConcMega(int ContMsgCoAP, int Comando) {

        String EndIP = "192.168.0.150";
        int Porta = 5683;
        String URI = "estados";
        int TamMsgRspCoAP = 320;
        byte [] MsgRecCoAP = new byte[TamMsgRspCoAP];

        try {
            byte[] MsgReqCoAP = new byte[32];

            int TamURI = URI.length();
            byte[] DH = LeDataHora();

            MsgReqCoAP[0] = 0x40;                    // Versão = 01 / Tipo = 00 / Token = 0000
            MsgReqCoAP[1] = 0x01;                    // Código de Solicitação: 0.01 GET
            ContMsgCoAP = ContMsgCoAP + 1;           // Incrementa o Identificador de mensagens
            MsgReqCoAP[2] = ByteHigh(ContMsgCoAP);   // Byte Mais Significativo do Identificador da Mensagem
            MsgReqCoAP[3] = ByteLow(ContMsgCoAP);    // Byte Menos Significativo do Identificador da Mensagem
            MsgReqCoAP[4] = (byte) (0xB0 + TamURI);  // Delta: 11 - Primeira Opcao 11: Uri-path e Núm. Bytes da URI
            int j = 5;
            for (int i = 0; i < TamURI; i++) {       // Carrega os codigos ASCII da URI
                char Char = URI.charAt(i);
                int ASCII = Char;
                MsgReqCoAP[i + 5] = (byte) ASCII;
                j = j + 1;
            }
            MsgReqCoAP[j++] = (byte) 0x11;    // Delta: 1 - Segunda Opcao (11 + 1 = 12): Content-format e Núm. Bytes (1)
            MsgReqCoAP[j++] = 42;             // Codigo da Opcao Content-format: application/octet-stream
            MsgReqCoAP[j++] = -1;             // Identificador de Inicio do Payload (255)
            MsgReqCoAP[j++] = (byte)Comando;  // Carrega o Código do Comando no Payload
            MsgReqCoAP[j++] = DH[0];          // Carrega a Hora do Computador no Payload
            MsgReqCoAP[j++] = DH[1];          // Carrega a Minuto do Computador no Payload
            MsgReqCoAP[j++] = DH[2];          // Carrega a Segundo do Computador no Payload
            MsgReqCoAP[j++] = DH[3];          // Carrega a Dia do Computador no Payload
            MsgReqCoAP[j++] = DH[4];          // Carrega a Mes do Computador no Payload
            MsgReqCoAP[j++] = DH[5];          // Carrega a Ano do Computador no Payload
            int TamCab = j;                   // Carrega o número de bytes do cabeçalho

            DatagramSocket clientSocket = new DatagramSocket();
            InetAddress IPAddress = InetAddress.getByName(EndIP);
            clientSocket.setSoTimeout(5000);
            DatagramPacket sendPacket = new DatagramPacket(MsgReqCoAP, TamCab, IPAddress, Porta);
            DatagramPacket receivePacket = new DatagramPacket(MsgRecCoAP, TamMsgRspCoAP);
            clientSocket.send(sendPacket);
            Terminal("Enviada Requisicao CoAP para o Controlador", false);

            // Espera a Mensagem CoAP de Resposta do Concentrador Arduino Mega
            try {
                clientSocket.receive(receivePacket);
                utr.estComConcMega = true;
                CarregaVarConcArdMega(MsgRecCoAP);
                Terminal("Recebida Mensagem CoAP do Controlador", false);
                clientSocket.close();
            } catch (java.net.SocketTimeoutException e) {
                clientSocket.close();
                utr.estComConcMega = false;
                Terminal("O Arduino Mega Concentrador não Respondeu ", false);
            }

        } catch (IOException err) {
            Terminal("Erro na Rotina de Comunicação com o Concentrador Arduino Mega: " + err, false);
        }
    } // Fim do Método

    //******************************************************************************************************************
    // Nome da Método: CarregaVarConcArdMega                                                                           *
    //                                                                                                                 *
    // Funcao: recebe como entrada um array de bytes de uma mensagem CoAP recebida do Concentrador Arduino Mega        *
    //         e carrega nas variáveis do programa.                                                                    *
    //                                                                                                                 *
    // Entrada: array byte[] com a mensagem binária protocolo CoAP recebida                                            *
    //                                                                                                                 *
    // Saida: nao tem                                                                                                  *
    //******************************************************************************************************************
    //
    public static void CarregaVarConcArdMega(byte[] receiveData1) {

        utr.hora = receiveData1[21];
        utr.minuto = receiveData1[22];
        utr.segundo = receiveData1[23];
        utr.dia = receiveData1[24];
        utr.mes = receiveData1[25];
        utr.ano = receiveData1[26];

        if (utr.estComConcMega) {
            utr.estComUtr = receiveData1[27] > 0;
            cc1.estComCc = receiveData1[28] > 0;
            cc2.estComCc = receiveData1[29] > 0;

            if (utr.estComUtr) {

                boolean EstRede = receiveData1[42] > 0;
                boolean HabCarga1 = receiveData1[46] > 0;
                boolean HabCarga2 = receiveData1[47] > 0;
                boolean HabCarga3 = receiveData1[48] > 0;
                boolean HabCarga4 = receiveData1[49] > 0;
                boolean EstadoCarga3 = receiveData1[53] > 0;
                boolean FontesCCLigadas = receiveData1[73] > 0;
                boolean EstadoInversor1 = receiveData1[51] > 0;
                boolean DJEINV1 = receiveData1[37] > 0;
                boolean EstadoInversor2 = receiveData1[52] > 0;
                boolean BoiaCxAzul = receiveData1[39] > 0;

                // Le o estado das saidas digitais
                int NumSd = 32;
                int[] SD = new int[NumSd];
                int k = 112;
                for (int i = 0; i < NumSd; i++){
                    SD[i] = receiveData1[k];
                    k = k + 1;
                }

                if (receiveData1[43] > 0) { utr.modoOp = "Normal"; } else { utr.modoOp = "Economia"; }
                if (receiveData1[44] > 0) { utr.modoCom = "Remoto"; } else { utr.modoCom = "Local"; }
                if (receiveData1[55] > 0) { utr.modoCtrl1 = "Automatico"; } else { utr.modoCtrl1 = "Manual"; }
                if (receiveData1[45] > 0) { utr.modoCtrl = "Hab"; } else { utr.modoCtrl = "     "; }
                if (receiveData1[38] > 0) { utr.estDjBoia = "Ligado"; } else { utr.estDjBoia = "Desligado"; }
                if (receiveData1[40] > 0) { utr.estBomba = "Ligada"; } else { utr.estBomba = "Desligada"; }

                // Carrega as variaveis com os valores das saidas digitais da UTR1
                boolean Iv1Lig = SD[10] > 0; // Iv2Lig = SD[10] > 0;
                boolean Iv2Lig = SD[1] > 0;  // Iv1Lig = SD[1] > 0;
                boolean Carga4Inv2 = SD[3] > 0;
                if (Iv2Lig) { utr.estInvD = "Ligado"; } else { utr.estInvD = "Desligado"; }
                if (Iv1Lig) { utr.estInvE = "Ligado"; } else { utr.estInvE = "Desligado"; }

                if (SD[17] > 0) { utr.energiaCg1 = "Inversor 1"; }
                else { if (HabCarga1) { utr.energiaCg1 = "Rede (Inv)"; } else { utr.energiaCg1 = "Rede"; } }

                if (SD[0] > 0) { utr.energiaCg2 = "Inversor 1"; }
                else { if (HabCarga2) { utr.energiaCg2 = "Rede (Inv)"; } else { utr.energiaCg2 = "Rede"; } }

                if (SD[2] > 0) { utr.energiaCg3 = "Inversor 1"; }
                else { utr.energiaCg3 = "Rede"; }

                if (EstRede) {
                    if (receiveData1[41] > 0) { utr.estDjBomba = "Ligado"; } else { utr.estDjBomba = "Desligado"; }
                }
                else {
                    utr.estDjBomba = "Falta CA";
                }

                int EstadoCxAz = receiveData1[72];
                utr.estCxAzul = "";
                utr.nivCxAzul = "";
                switch (EstadoCxAz) {
                    case 0:
                        utr.estCxAzul = "Indefinido";
                        utr.nivCxAzul = "Indefinido";
                    break;

                    case 1:
                        utr.estCxAzul = "Precisa Encher";
                        utr.nivCxAzul = "Baixo";
                    break;

                    case 2:
                        utr.estCxAzul = "Precisa Encher";
                        utr.nivCxAzul = "Normal";
                    break;

                    case 3:
                        utr.estCxAzul = "Cheia";
                        utr.nivCxAzul = "Normal";
                    break;

                    case 4:
                        utr.estCxAzul = "Falha Boia";
                        utr.nivCxAzul = "";
                    break;

                    case 5:
                        utr.estCxAzul = "Falha Boia";
                        utr.nivCxAzul = "";
                    break;
                }

                // Energia Bomba de Água do Poço
                utr.energiaCg4 = "Rede";
                if (Carga4Inv2) { utr.energiaCg4 = "Inversor 1"; }
                else { if (HabCarga4) { utr.energiaCg4 = "Rede (Hab)"; } }

                if (EstRede) {
                    if (FontesCCLigadas) { utr.estFontesCC = "Ligadas"; } else { utr.estFontesCC = "Desligadas"; }
                }
                else {
                    utr.estFontesCC = "Falta CA";
                }

                // Le as Medidas de 2 bytes da mensagem recebida
                int NumMed = 48;
                double[] Med = new double[NumMed];
                k = 160;
                for (byte i = 0; i < NumMed; i++) {
                    Med[i] = DoisBytesInt(receiveData1[k], receiveData1[k + 1]);
                    k = k + 2;
                }

                // Carrega as medidas lidas do Concentrador Arduino Mega nas variaveis
                utr.vBat = Med[0] / 100.0;         // Tensão do Banco de Baterias
                utr.tDInvD = Med[2] / 100.0;       // Temperatura do Driver do Inversor 2 (8)
                utr.iCirCc = Med[3] / 1000.0;      // Corrente Total dos Circuitos CC
                utr.vSInvD = Med[4] / 100.0;       // Tensão de Saída do Inversor 2
                utr.vSInvE = Med[6] / 100.0;       // Tensão de Saída do Inversor 1
                utr.vRede = Med[5] / 100.0;        // Tensão da Rede
                utr.tTInvD = Med[7] / 100.0;       // Temperatura do Transformador do Inversor 2 (9)
                utr.tDInvE = Med[8] / 100.0;       // Temperatura do Driver do Inversor 1 (2)
                utr.tTInvE = Med[9] / 100.0;       // Temperatura do Transformador do Inversor 1 (7)
                utr.iSInvE = (Med[10] * 7) / 1000; // Corrente de Saída do Inversor 1 (13)
                utr.iEInvE = Med[12] / 100.0;      // Corrente de Entrada do Inversor 1 (15)
                utr.iSInvD = Med[13] / 1000.0;     // Corrente de Saída do Inversor 2 (10)
                utr.iCg3 = Med[14] / 1000.0;       // Corrente Carga 3
                utr.iEInvD = Med[15] / 100.0;      // Corrente de Entrada do Inversor 2 (12)
                utr.vmBat = Med[16] / 100.0;       // Tensão Média Estendida do Banco de Baterias
                utr.tmpBombaLig = (int) Med[17];   // Tempo da Bomba Ligada

                cc1.vECc = Med[18] / 100.0;     // 0x3100 - PV array voltage 1
                cc1.iECc = Med[19] / 100.0;     // 0x3101 - PV array current 1
                cc1.wECc = Med[20] / 100.0;     // 0x3102 - PV array power 1
                cc1.vSCc = Med[21] / 100.0;     // 0x3104 - Battery voltage 1
                cc1.iSCc = Med[22] / 100.0;     // 0x3105 - Battery charging current 1
                cc1.wSCc = Med[23] / 100.0;     // 0x3106 - Battery charging power 1
                cc1.tbat = Med[24] / 100.0;     // 0x3110 - Battery Temperature 1

                cc2.vECc = Med[26] / 100.0;     // 0x3100 - PV array voltage 2
                cc2.iECc = Med[27] / 100.0;     // 0x3101 - PV array current 2
                cc2.wECc = Med[28] / 100.0;     // 0x3102 - PV array power 2
                cc2.vSCc = Med[29] / 100.0;     // 0x3104 - Battery voltage 2
                cc2.iSCc = Med[30] / 100.0;     // 0x3105 - Battery charging current 2
                cc2.wSCc = Med[31] / 100.0;     // 0x3106 - Battery charging power 2 (Med[45])
                cc2.tbat = Med[32] / 100.0;     // 0x3110 - Battery Temperature 1

                utr.wCirCC = Med[35] / 100.0;   // Potencia Consumida pelos Circuitos de 24Vcc

                if (EstRede) { if (utr.vRede > 190.0) { utr.estRede = "Normal"; } else { utr.estRede = "Baixa"; } }
                else { utr.estRede = "Falta CA"; }

                if (!Iv1Lig) {         // Se o Inversor 1 estiver desligado,
                    utr.iEInvE = 0;    // zera a corrente de entrada
                    utr.vSInvE = 0;    // zera a tensão de saída
                    utr.iSInvE = 0;    // zera a corrente de saída
                }
                if (!Iv2Lig) {         // Se o Inversor 2 estiver desligado, zera a tensão de saída
                    utr.iEInvD = 0;    // zera a corrente de entrada
                    utr.vSInvD = 0;    // zera a tensão de saída
                    utr.iSInvD = 0;    // zera a corrente de saída
                }

                // Variáveis Calculadas
                utr.wEInvD = FormataDouble2CD(utr.vBat * utr.iEInvD);    // Potencia de Entrada do Inversor 2 (Med[38])
                utr.wSInvD = FormataDouble2CD(utr.vSInvD * utr.iSInvD);  // Potencia de Saida do Inversor 2 (Med[39])
                utr.wEInvE = FormataDouble2CD(utr.vBat * utr.iEInvE);    // Potência de Entrada do Inversor 1 (Med[41])
                utr.wSInvE = FormataDouble2CD(utr.vSInvE * utr.iSInvE);  // Potencia de Saida do Inversor 1 (Med[42])
                utr.iTotCg24v = FormataDouble2CD(utr.iEInvE + utr.iEInvD + utr.iCirCc);  // Corrente Total Consumida pelas Cargas
                utr.wTotCg24v = FormataDouble2CD(utr.wEInvE + utr.wEInvD + utr.wCirCC);  // Potência Total Consumida pelas Cargas
                utr.wTotGerCC = cc1.wSCc + cc2.wSCc;
            }
        }

    } // Fim do Método

    //******************************************************************************************************************
    // Autor: Antonio Bernardo de Vasconcellos Praxedes  /  Data: 01/09/2023                                           *
    //	                                                                                                               *
    // Nome do Método: MontaJsonCAQ                                                                                    *
    //	                                                                                                               *
    // Funcao: monta a mensagem JSON com as informações do Controlador Arduino de Agua Quente a partir das variáveis   *
    //         lidas do mesmo controlador.                                                                             *
    //                                                                                                                 *
    // Entrada: não tem                                                                                                *
    // Saida: mensagem JSON em formato binário                                                                         *
    //******************************************************************************************************************
    //
    public static String MontaJsonCAQ() {

        EnvRecMsgCtrlAQ();
        if (!caq.estComAq) {
            caq.estBombaAguaQuente = "-----";
            caq.estAquecedor =  "-----";
            caq.tempBoiler = 0;
            caq.tempPlacaSolar = 0;
            caq.tempoBombaAqLigada = 0;
            caq.vazaoBombaPoco = 0;
        }
        Gson gson = new Gson();
        String MsgJson = gson.toJson(caq);
        return(MsgJson);

    } // Fim do Método

    //******************************************************************************************************************
    // Nome do Método: EnvRecMsgCtrlAQ                                                                                 *
    //                                                                                                                 *
    // Funcao: envia uma mensagem de requisição e recebe a mensagem de resposta do Controlador de Água Quente          *
    //         em formato binário                                                                                      *
    //                                                                                                                 *
    // Entrada: não tem                                                                                                *
    // Saída: não tem                                                                                                  *
    //******************************************************************************************************************
    //
    public static void EnvRecMsgCtrlAQ() {

        String EndIP = "192.168.0.155";
        int Porta = 100;
        int TamMsgRsp = 84;

        try {
            byte[] MsgReq = new byte[16];
            byte[] MsgBinRec = new byte[TamMsgRsp];
            int TamCab = 8;
            MsgReq[0]= 1;

            DatagramSocket clientSocket = new DatagramSocket();
            InetAddress IPAddress = InetAddress.getByName(EndIP);
            clientSocket.setSoTimeout(2000);
            DatagramPacket sendPacket = new DatagramPacket(MsgReq, TamCab, IPAddress, Porta);
            clientSocket.send(sendPacket);
            Terminal("Enviada Requisicao Binaria para o Controlador de Água Quente", false);

            // Espera a Mensagem Binária de Resposta. Se a mensagem de resposta  for recebida, carrega nas variáveis
            try {
                DatagramPacket receivePacket = new DatagramPacket(MsgBinRec, TamMsgRsp);
                clientSocket.receive(receivePacket);
                caq.estComAq = true;
                Terminal("Recebida Mensagem Binaria do Controlador de Água Quente", false);

                if (MsgBinRec[73] > 0) { caq.estBombaAguaQuente = "Ligada"; }
                else { caq.estBombaAguaQuente = "Desligada"; }

                if (MsgBinRec[72] > 0) { caq.estAquecedor = "Ligado"; }
                else { caq.estAquecedor = "Desligado"; }

                caq.tempBoiler = TwoBytetoInt(MsgBinRec[48], MsgBinRec[49]) / 100.0;
                caq.tempPlacaSolar = TwoBytetoInt(MsgBinRec[51], MsgBinRec[52]) / 100.0;
                caq.tempoBombaAqLigada = TwoBytetoInt(MsgBinRec[66], MsgBinRec[67]);
                caq.vazaoBombaPoco = FormataDouble2CD(TwoBytetoInt(MsgBinRec[76], MsgBinRec[77]) / 4.8);
            }
            catch(java.net.SocketTimeoutException e) {
                caq.estComAq = false;
                Terminal("Erro: o Controlador de Água Quente nao Respondeu", false);
            }
            clientSocket.close();
        }
        catch (IOException err) {
            caq.estComAq = false;
            Terminal("Erro na Rotina EnvRecMsgSrv: " + err, false);
        }
    }

    //******************************************************************************************************************
    // Nome do Método: MontaJsonCEN                                                                                    *
    //	                                                                                                               *
    // Funcao: monta a mensagem JSON com as informações do Controlador Arduino de Energia a partir das variáveis       *
    //         lidas do Concentrador Arduino Mega.                                                                     *
    //                                                                                                                 *
    // Entrada: não tem                                                                                                *
    //                                                                                                                 *
    // Saida: mensagem JSON em formato binário                                                                         *
    //******************************************************************************************************************
    //
    public static String MontaJsonCEN() {

        String IPConcArd = "192.168.0.152";
        int PortaUDP = 5683;
        int funcao = 4;
        int RegTensaoFase1 = 16;
        int RegCorrenteFase1 = 22;
        int RegPotAtivFase1 = 0x1C;
        int RegFatPotFase1 = 46;
        int RegEnAtivaPos = 52;
        int RegEnAtivaNeg = 56;

        cen.tensaoK = FormataDouble2CD(ModbusKron(IPConcArd, PortaUDP, funcao, RegTensaoFase1));
        cen.correnteRedeK = FormataDouble2CD(ModbusKron(IPConcArd, PortaUDP, funcao, RegCorrenteFase1));
        cen.potenciaAtivaRedeK = FormataDouble2CD(ModbusKron(IPConcArd, PortaUDP, funcao, RegPotAtivFase1));
        cen.fatorPotenciaRedeK = FormataDouble2CD(ModbusKron(IPConcArd, PortaUDP, funcao, RegFatPotFase1));
        cen.energiaAtivaPosRedeK = FormataDouble2CD(ModbusKron(IPConcArd, PortaUDP, funcao, RegEnAtivaPos));
        cen.energiaAtivaNegRedeK = FormataDouble2CD(ModbusKron(IPConcArd, PortaUDP, funcao, RegEnAtivaNeg));

        double tensao = (25.9935 * cen.medida1) / 100.0;
        cen.tensaoEntradaRede = FormataDouble2CD(tensao);

        double corrente = (6.1 * cen.medida2) / 1000.0;
        if (corrente < 0.09) {
            corrente = 0.0;
        }
        cen.correnteInvOnGrid = FormataDouble2CD(corrente);
        cen.potenciaInvOnGrid = FormataDouble2CD(tensao * corrente);

        Gson gson = new Gson();
        String MsgJson = gson.toJson(cen);

        return(MsgJson);

    } // Fim do Método

    //******************************************************************************************************************
    // Nome do Método: ModbusKron                                                                                      *
    //                                                                                                                 *
    // Funcao: envia uma mensagem de requisição de uma medida para o Concentrador Arduino Uno e recebe a mensagem de   *
    //          resposta do Multimedidor através do Concentrador Arduíno Uno                                           *
    //                                                                                                                 *
    // Entrada: endereço IP do Concentrador Arduino, Porta UDP, Função, Endereço do Registro Modbus e Verbose          *
    //                                                                                                                 *
    // Saida: double - valor da medida lido dos registros em formato IEEE754 convertido                                *
    //******************************************************************************************************************
    //
    private static double ModbusKron(String EndIP, int Porta, int funcao, int EndReg) {
        int TamMsgReq = 32;
        int TamMsgRsp = 64;
        byte[] MsgReq = new byte[TamMsgReq];
        byte[] MsgBinRec = new byte[TamMsgRsp];
        double Valor = 0;
        cen.estComKron = false;

        try {
            MsgReq[8] = 1;               // Endereço do Multimedidor
            MsgReq[9] = (byte) funcao;   // Função MODBUS

            if (funcao == 16) {
                EndReg = 2;
            }

            MsgReq[10] = 0;              // Campo 1: Função 4 = Registro Inicial (MSB)
            MsgReq[11] = (byte) EndReg;  // Campo 2: Função 4 = Registro Inicial (LSB)
            MsgReq[12] = 0;              // Campo 3: Função 4 = Número de Registros (MSB)
            MsgReq[13] = 2;              // Campo 4: Função 4 = Número de Registros (LSB)

            MsgReq[14] = 4;              // Campo 5 = Número de Bytes ( 4 )
            MsgReq[15] = 0;              // Campo 6 ( F2 )  - Valor a programar (RTC)
            MsgReq[16] = 0;              // Campo 7 ( F1 )  - Valor a programar (RTC)
            MsgReq[17] = 32;             // Campo 8 ( F0 )  - Valor a programar (RTC)
            MsgReq[18] = 65;             // Campo 9 ( EXP ) - Valor a programar (RTC)

            DatagramSocket clientSocket = new DatagramSocket();
            InetAddress IPAddress = InetAddress.getByName(EndIP);
            clientSocket.setSoTimeout(2000);

            int cont = 0;
            while ((!cen.estComCncUno || !cen.estComKron) && (cont < 3)) {

                cont++;
                DatagramPacket sendPacket = new DatagramPacket(MsgReq, TamMsgReq, IPAddress, Porta);
                clientSocket.send(sendPacket);
                Terminal("Enviada requisição Modbus Kron - Reg = " + EndReg + " (" + cont + ")", false);

                // Espera a Mensagem Binária de Resposta. Se a mensagem de resposta  for recebida, carrega nas variáveis
                try {
                    DatagramPacket receivePacket = new DatagramPacket(MsgBinRec, TamMsgRsp);
                    clientSocket.receive(receivePacket);
                    cen.estComCncUno = true;
                    int NumBytesMsg = MsgBinRec[4];

                    if (NumBytesMsg >= 9) {
                        int EndRec = MsgBinRec[8];
                        int FuncaoRec = MsgBinRec[9];
                        int NumBytes = MsgBinRec[10];

                        if ((EndRec == 1) && (FuncaoRec == funcao) && (NumBytes == 4)) {
                            cen.estComKron = true;
                            Terminal("Recebida mensagem Kron com " + NumBytesMsg + " Bytes", false);

                            byte f2 = MsgBinRec[11];
                            byte f1 = MsgBinRec[12];
                            byte f0 = MsgBinRec[13];
                            byte exp = MsgBinRec[14];
                            Valor = ConverteIEEE754PfValor(f0, f1, f2, exp);

                            cen.medida0 = TwoBytetoInt(MsgBinRec[32], MsgBinRec[33]);
                            cen.medida1 = TwoBytetoInt(MsgBinRec[34], MsgBinRec[35]);
                            cen.medida2 = TwoBytetoInt(MsgBinRec[36], MsgBinRec[37]);
                            cen.medida3 = TwoBytetoInt(MsgBinRec[38], MsgBinRec[39]);
                            cen.medida4 = TwoBytetoInt(MsgBinRec[40], MsgBinRec[41]);
                            cen.medida5 = TwoBytetoInt(MsgBinRec[42], MsgBinRec[43]);
                        } else {
                            cen.estComKron = false;
                            Valor = 0.0;
                        }
                    } else {
                        cen.estComKron = false;
                        Valor = 0.0;
                    }
                } catch (java.net.SocketTimeoutException e) {
                    clientSocket.close();
                    cen.estComCncUno = false;
                    Valor = 0.0;
                }
            }
        }
        catch(IOException err) {
            Terminal("Erro na Rotina EnvRecMsgSrv: " + err, false);
            Valor = 0.0;
        }
        return (Valor);

    } // Fim do Método

    //******************************************************************************************************************
    // Nome do Método: ConverteIEEE754PfValor                                                                          *
    //                                                                                                                 *
    // Funcao: converte um número em formato IEE754 de 32 bits (4 bytes) em um valor tipo double                       *
    //                                                                                                                 *
    // Entrada: 4 bytes do formato IEEE754 - f0, f1, f2 (mantissa) e exp (expoente)                                    *
    //                                                                                                                 *
    // Saida: valor convertido em formato double                                                                       *
    //******************************************************************************************************************
    //
    public static double ConverteIEEE754PfValor(int f0, int F1, int F2, int exp) {

        int LsbExp = (f0 & 0x80) / 128;
        boolean Sinal = (exp & 0x80) > 0;
        int EXP = ((exp & 0x7F) * 2 + LsbExp) - 0x7F;
        int F0 = (f0 & 0x7F);

        boolean bit01 = (F0 & 0x40) > 0;
        boolean bit02 = (F0 & 0x20) > 0;
        boolean bit03 = (F0 & 0x10) > 0;
        boolean bit04 = (F0 & 0x08) > 0;
        boolean bit05 = (F0 & 0x04) > 0;
        boolean bit06 = (F0 & 0x02) > 0;
        boolean bit07 = (F0 & 0x01) > 0;
        boolean bit08 = (F1 & 0x80) > 0;
        boolean bit09 = (F1 & 0x40) > 0;
        boolean bit10 = (F1 & 0x20) > 0;
        boolean bit11 = (F1 & 0x10) > 0;
        boolean bit12 = (F1 & 0x08) > 0;
        boolean bit13 = (F1 & 0x04) > 0;
        boolean bit14 = (F1 & 0x02) > 0;
        boolean bit15 = (F1 & 0x01) > 0;
        boolean bit16 = (F2 & 0x80) > 0;
        boolean bit17 = (F2 & 0x40) > 0;
        boolean bit18 = (F2 & 0x20) > 0;
        boolean bit19 = (F2 & 0x10) > 0;
        boolean bit20 = (F2 & 0x08) > 0;
        boolean bit21 = (F2 & 0x04) > 0;
        boolean bit22 = (F2 & 0x02) > 0;
        boolean bit23 = (F2 & 0x01) > 0;

        double mantissa = 1;
        if (bit01) { mantissa = mantissa + 0.5; }
        if (bit02) { mantissa = mantissa + 1.0/Math.pow(2, 2); }
        if (bit03) { mantissa = mantissa + 1.0/Math.pow(2, 3); }
        if (bit04) { mantissa = mantissa + 1.0/Math.pow(2, 4); }
        if (bit05) { mantissa = mantissa + 1.0/Math.pow(2, 5); }
        if (bit06) { mantissa = mantissa + 1.0/Math.pow(2, 6); }
        if (bit07) { mantissa = mantissa + 1.0/Math.pow(2, 7); }
        if (bit08) { mantissa = mantissa + 1.0/Math.pow(2, 8); }
        if (bit09) { mantissa = mantissa + 1.0/Math.pow(2, 9); }
        if (bit10) { mantissa = mantissa + 1.0/Math.pow(2, 10); }
        if (bit11) { mantissa = mantissa + 1.0/Math.pow(2, 11); }
        if (bit12) { mantissa = mantissa + 1.0/Math.pow(2, 12); }
        if (bit13) { mantissa = mantissa + 1.0/Math.pow(2, 13); }
        if (bit14) { mantissa = mantissa + 1.0/Math.pow(2, 14); }
        if (bit15) { mantissa = mantissa + 1.0/Math.pow(2, 15); }
        if (bit16) { mantissa = mantissa + 1.0/Math.pow(2, 16); }
        if (bit17) { mantissa = mantissa + 1.0/Math.pow(2, 17); }
        if (bit18) { mantissa = mantissa + 1.0/Math.pow(2, 18); }
        if (bit19) { mantissa = mantissa + 1.0/Math.pow(2, 19); }
        if (bit20) { mantissa = mantissa + 1.0/Math.pow(2, 20); }
        if (bit21) { mantissa = mantissa + 1.0/Math.pow(2, 21); }
        if (bit22) { mantissa = mantissa + 1.0/Math.pow(2, 22); }
        if (bit23) { mantissa = mantissa + 1.0/Math.pow(2, 23); }

        double Valor = mantissa * Math.pow(2, EXP);
        if (!Sinal) { mantissa = -mantissa; }

        return(Valor);
    }

}