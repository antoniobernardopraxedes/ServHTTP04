package com.praxsoft.ServHTTP04;

import com.google.gson.Gson;

import java.io.IOException;
import java.net.*;

import static com.praxsoft.ServHTTP04.SupService.*;

public class Dados {

    // Estados de Comunicação
    private static boolean EstComCncMega;
    private static boolean EstComUTR;
    private static boolean EstComCC1;
    private static boolean EstComCC2;
    private static int ContMsgCoAP;
    private static int numComando = 0;
    private static String MsgComando = "";

    // Data e Hora
    private static int Hora;
    private static int Minuto;
    private static int Segundo;
    private static int Dia;
    private static int Mes;
    private static int Ano;
    private static String CmdEx;

    // Estados e Medidas Gerais
    private static boolean EstRede;
    private static boolean MdOp;
    private static boolean MdCom;
    private static boolean MdCtrl1;
    private static boolean MdCtrl;
    private static boolean HabCarga1;
    private static boolean HabCarga2;
    private static boolean HabCarga3;
    private static boolean HabCarga4;
    private static boolean EstadoInversor1;
    private static boolean EstadoInversor2;
    private static boolean EstadoCarga3;

    private static boolean EnergiaCarga1;
    private static boolean EnergiaCarga2;
    private static boolean EnergiaCarga3;

    private static boolean FontesCCLigadas;

    private static double Icarga3;       // Corrente Carga 3 (Geladeira)
    private static double VRede;         // Tensão da Rede
    private static double VBat;          // Tensão do Banco de Baterias
    private static double VMBat;         // Tensão Média Estendida do Banco de Baterias
    private static double ICircCC;       // Corrente Total dos Circuitos CC
    private static double WCircCC;       // Potência Total dos Circuitos CC
    private static double ITotCg;        // Corrente Total Consumida pelas Cargas
    private static double WTotCg;        // Potência Total Consumida pelas Cargas
    private static double IFonteCC;      // Corrente de Saída da Fonte CC
    private static double WFonteCC;      // Potência de Saída da Fonte CC
    private static double IBat;          // Corrente de Carga / Descarga do Banco de Baterias
    private static double WBat;          // Potência de Carga / Descarga do Banco de Baterias
    private static double TBat;          // Temperatura do Banco de Baterias
    private static int SDBat;	         // Valor de Saude das Baterias
    private static double IFontesCC12;   // Corrente de Saída das Fontes CC1 e CC2

    // Estados Água
    private static boolean CircBoia;
    private static boolean BoiaCxAzul;
    private static boolean CircBomba;
    private static boolean AlRedeBomba;
    private static boolean BombaLigada;
    private static boolean CxAzNvBx;
    private static boolean EdCxAzCheia;
    private static int EstadoCxAz;
    private static int TempoBombaLigada;

    // Medidas da UTR2 - Comunicação com os Controladores de Carga
    private static double VP12;          	  // 0x3100 - PV array voltage 1
    private static double IS12;            	  // 0x3101 - PV array current 1
    private static double WS12;            	  // 0x3102 - PV array power 1
    private static double VBat1;           	  // 0x3104 - Battery voltage 1
    private static double ISCC1;           	  // 0x3105 - Battery charging current 1
    private static double WSCC1;           	  // 0x3106 - Battery charging power 1
    private static double TBat1;

    private static double VP34;               // 0x3100 - PV array voltage 2
    private static double IS34;               // 0x3101 - PV array current 2
    private static double WS34;               // 0x3102 - PV array power 2
    private static double VBat2;              // 0x3104 - Battery voltage 2
    private static double ISCC2;              // 0x3105 - Battery charging current 2
    private static double WSCC2;              // 0x3106 - Battery charging power 2 (Med[45])
    private static double TBat2;

    // Estados e Medidas do Inversor 2
    private static boolean Iv2Lig;             // Estado: true = > Inversor 2 Ligado
    private static double IEIv2;                  // Corrente de Entrada
    private static double WEIv2;                  // Potência de Entrada
    private static double VSIv2;                  // Tensão de Saída
    private static double ISInv2;                 // Corrente de Saída
    private static double WSInv2;                 // Potência de Saída
    private static double TDInv2;                 // Temperatura do Driver
    private static double TTInv2;                 // Temperatura do Transformador

    // Estados e Medidas do Inversor 1
    private static boolean Iv1Lig;             // Estado: true = > Inversor 1 Ligado
    private static boolean DJEINV1;            //
    private static double IEIv1;          		   // Corrente de Entrada do Inversor 1
    private static double WEIv1;             	   // Potência de Entrada do Inversor 1
    private static double VSIv1;                  // Tensão de Saída do Inversor 1
    private static double ISInv1;                 // Corrente de Saída do Inversor 1
    private static double WSInv1;                 // Potência de Saída do Inversor 1
    private static double TDInv1;                 // Temperatura do Driver do Inversor 1
    private static double TTInv1;                 // Temperatura do Transformador do Inversor 1

    // Estados e Medidas Água Quente
    private static boolean EstComAQ;
    private static int EstBombaAQ;			      // Estado da Bomba de Água Quente
    private static int EstAquecedor;	          // Estado do Aquecedor do Boiler
    private static double TemperaturaBoiler; 	  // Temperatura do Boiler
    private static double TemperaturaPlaca; 	  // Temperatura da Placa Solar
    private static int TempoBombaAQLigada;        // Tempo da Bomba Ligada
    private static double VazaoBombaPoco;         // Vazão da Bomba do Poço em litros por minuto
    private static double TotalVazaoBombaPoco;

    // Estados e Medidas do Concentrador Arduino Uno e do Multimedidor Kron
    private static boolean EstComConcArd;
    private static boolean EstComKron;
    private static double TensaoKron;
    private static double CorrenteKron;
    private static double PotenciaAtivaKron;
    private static double PotenciaReativaKron;
    private static double FrequenciaKron;
    private static double FatorPotenciaKron;
    private static double EnergiaAtivaPositivaKron;
    private static double EnergiaAtivaNegativaKron;

    private static byte[] MsgValor = new byte[45];
    private static byte[] Medidas = new byte[12];

    // Medidas do Concentrador Arduino
    private static int Medida00;
    private static int Medida01;
    private static int Medida02;
    private static int Medida03;
    private static int Medida04;
    private static int Medida05;
    private static int ContChamadas = 0;

    // Medidas obtidas das entradas analógicas do Concentrador Arduino
    private static double TensaoEntradaRede;
    private static double CorrenteInvOnGrid;
    private static double PotenciaInvOnGrid;

    public static boolean ModoComandoRemoto() {
        return (MdCom);
    }

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
    // Autor: Antonio Bernardo de Vasconcellos Praxedes  /  Data: 01/09/2023                                           *
    //	                                                                                                               *
    // Nome do Método: MontaJsonUTR                                                                                    *
    //	                                                                                                               *
    // Funcao: monta a mensagem JSON com as informações da UTR (Unidade Terminal Remota) a partir das variáveis lidas  *
    //         do Concentrador Arduino Mega.                                                                           *
    //                                                                                                                 *
    // Entrada: não tem                                                                                                *
    //                                                                                                                 *
    // Saida: String com a mensagem JSON                                                                               *
    //******************************************************************************************************************
    //
    public static String MontaJsonUTR(String cmdEx) {

        UTRAM utr = new UTRAM();

        // Estados Gerais da UTR
        String StrMdOp = "";				// Modo de Operação (Economia / Normal)
        String StrMdCom = "";				// Modo de Comando (Local / Remoto)
        String StrMdCtrl1 = "";				// Modo de Controle da Carga 1 (Manual / Automatico)
        String StrMdCtrl = "";				// Modo de Controle das Cargas 2,3 e 4 (Manual / Automatico)
        String StrEstRede = "";

        String StrEnergiaCarga1 = "";  		// Energia Carga 1 (Rede / Inversor 2) (CT2Inv)
        String StrEnergiaCarga2 = "";  		// Energia Carga 2 (Rede / Inversor 2) (CT1Inv)
        String StrEnergiaCarga3 = "";  		// Energia Carga 3 (Rede / Inversor 2) (CT3Inv)
        String StrEnergiaCarga4 = "";		// Energia Carga 4 (Rede / Inversor 1) (Iv1Lig)

        String StrEstFontesCC = "";			// Estado das Fonte CA - 24Vcc 1 (Desligadas / Ligadas)

        // Estados dos Inversores 1 e 2
        String StrEstIv2 = "";
        String StrEstIv1 = "";

        // Estados da Caixa d'Água e da Bomba do Poço
        String StrEstCxAzul = "";			// Estado da Caixa d'Água (Precisa Encher / Cheia)
        String StrNivCxAzul = "";			// Nível da Caixa d'Água (Normal /Baixo)
        String StrEstBomba = "";			// Estado da Bomba (Desligada / Ligada)
        String StrEstDJBoia = "";			// Estado do Disjuntor da Boia (Desligado / Ligado)
        String StrEstDJBomba = "";			// Estado do Disjuntor da Bomba (Desligado / Ligado)

        // Informações provenientes do Concentrador Arduíno Mega: UTR, Controlador de Carga 1 e Controlador de Carga 2
        if (EstComCncMega) {

            // Estados de Comunicação
            utr.estComConcMega = EstComCncMega;
            utr.estComUtr = EstComUTR;
            utr.cmdEx = cmdEx;

            if (EstComUTR) {    // Estado de Comunicação: Concentrador Arduíno Mega - UTR

                StrEstRede = "";            // Estado de Tensão da Rede
                if (EstRede) {
                    if (VRede > 190.0) {
                        StrEstRede = "Normal";
                    }
                    else {
                        StrEstRede = "Baixa";
                    }
                }
                else {
                    StrEstRede = "Falta CA";
                }

                if (MdOp) {                         // Modo de Operação
                    StrMdOp = "Normal";
                }
                else {
                    StrMdOp = "Economia";
                }

                if (MdCom) {                        // Modo de Comando
                    StrMdCom = "Remoto";
                }
                else {
                    StrMdCom = "Local";
                }

                if (MdCtrl1) {                      // Modo de Controle Carga 1
                    StrMdCtrl1 = "Automatico";
                }
                else {
                    StrMdCtrl1 = "Manual";
                }

                if (MdCtrl) {                       // Modo de Controle Cargas 2, 3 e 4
                    StrMdCtrl = "Automatico";
                }
                else {
                    StrMdCtrl = "Manual";
                }

                StrEnergiaCarga1 = "Rede";          // Energia Carga 1
                if (EnergiaCarga1) {
                    StrEnergiaCarga1 = "Inversor 1";
                }
                else {
                    if (HabCarga1) {
                        StrEnergiaCarga1 = "Rede (Hab)";
                    }
                }

                StrEnergiaCarga2 = "Rede";          // Energia Carga 2
                if (EnergiaCarga2) {
                    StrEnergiaCarga2 = "Inversor 1";
                }
                else {
                    if (HabCarga2) {
                        StrEnergiaCarga2 = "Rede (Hab)";
                    }
                }

                StrEnergiaCarga3 = "Rede";          // Energia Carga 3
                if (EnergiaCarga3) {
                    StrEnergiaCarga3 = "Inversor 2";
                }
                else {
                    if (HabCarga3) {
                        StrEnergiaCarga3 = "Rede (Hab)";
                    }
                }

                StrEstCxAzul = "";
                StrNivCxAzul = "";
                switch (EstadoCxAz) {
                    case 0:  //  EstadoCxAz = 0 => Estado da Caixa Azul = Indefinido
                        StrEstCxAzul = "Indefinido";
                        StrNivCxAzul = "Indefinido";
                        break;

                    case 1:  //  EstadoCxAz = 1 => Estado da Caixa Azul = Precisa Encher Nivel Baixo
                        StrEstCxAzul = "Precisa Encher";
                        StrNivCxAzul = "Baixo";
                        break;

                    case 2:  //  EstadoCxAz = 2 => Estado da Caixa Azul = Precisa Encher Nivel Normal
                        StrEstCxAzul = "Precisa Encher";
                        StrNivCxAzul = "Normal";
                        break;

                    case 3:  //  EstadoCxAz = 3 => Estado da Caixa Azul = Cheia
                        StrEstCxAzul = "Cheia";
                        StrNivCxAzul = "Normal";
                        break;

                    case 4:  //  EstadoCxAz = 4 => Estado da Caixa Azul = Falha de Sinalizacao 1
                        StrEstCxAzul = "Falha Boia";
                        StrNivCxAzul = "";
                        break;

                    case 5:  // EstadoCxAz = 5 => Estado da Caixa Azul = Falha de Sinalizacao 2
                        StrEstCxAzul = "Falha Boia";
                        StrNivCxAzul = "";
                        break;
                }

                if (CircBoia) {
                    StrEstDJBoia = "Ligado";
                }
                else {
                    StrEstDJBoia = "Desligado";
                }

                if (EstRede) {
                    if (AlRedeBomba) {
                        StrEstDJBomba = "Ligado";
                    }
                    else {
                        StrEstDJBomba = "Desligado";
                    }
                }
                else {
                    StrEstDJBomba = "Falta CA";
                }

                // Energia Bomba de Água do Poço
                StrEnergiaCarga4 = "Rede";
                if (Iv2Lig) {
                    StrEnergiaCarga4 = "Inversor 1";
                }
                else {
                    if (HabCarga4) {
                        StrEnergiaCarga4 = "Rede (Hab)";
                    }
                }

                if (CircBomba) {
                    StrEstBomba = "Ligada";
                }
                else {
                    StrEstBomba = "Desligada";
                }

                StrEstFontesCC = "";            		 // Estado das Fontes CC1 e CC2
                if (EstRede) {                           // Se a tensao da Rede esta OK,
                    if (FontesCCLigadas) {               // e se a fonte CC1 está fornecendo tensão,
                        StrEstFontesCC = "Ligadas";    	 // Carrega a mensagem de que a fonte CC1 está ligada
                    }
                    else {                             	 // Se a fonte CC1 não está fornecendo tensão,
                        StrEstFontesCC = "Desligadas"; 	 // Carrega a mensagem de que a fonte CC1 está desligada
                    }
                }
                else {
                    StrEstFontesCC = "Falta CA";
                }

                StrEstIv2 = "Desligado";
                if (Iv2Lig) {
                    StrEstIv2 = "Ligado";
                }

                StrEstIv1 = "Desligado";
                if (Iv1Lig) {
                    StrEstIv1 = "Ligado";
                }

                // Data, Hora e Mensagem de Estado
                utr.hora = Hora;
                utr.minuto = Minuto;
                utr.segundo = Segundo;
                utr.dia = Dia;
                utr.mes = Mes;
                utr.ano = Ano;
                utr.cmdEx = CmdEx;

                // Estados e Medidas Gerais
                utr.modoOp = StrMdOp;
                utr.modoCom = StrMdCom;
                utr.modoCtrl1 = StrMdCtrl1;
                utr.modoCtrl = StrMdCtrl;
                utr.vRede = FormataDouble2CD(VRede);
                utr.estRede = StrEstRede;
                utr.vBat = FormataDouble2CD(VBat);
                utr.tBat = FormataDouble2CD(TBat);
                utr.energiaCg1 = StrEnergiaCarga1;
                utr.energiaCg2 = StrEnergiaCarga2;
                utr.energiaCg3 = StrEnergiaCarga3;
                utr.energiaCg4 = StrEnergiaCarga4;
                utr.iCg3 = FormataDouble2CD(Icarga3);
                utr.iTotCg24v = FormataDouble2CD(ITotCg);
                utr.wTotCg24v = FormataDouble2CD(WTotCg);
                utr.iCirCc = FormataDouble2CD(ICircCC);
                utr.wCirCC = FormataDouble2CD(WCircCC);
                utr.estFontesCC = StrEstFontesCC;

                // Estados e Medidas dos Inversores 1 e 2
                utr.estInv2 = StrEstIv2;
                utr.iEInv2 = FormataDouble2CD(IEIv2);
                utr.wEInv2 = FormataDouble2CD(WEIv2);
                utr.vSInv2 = FormataDouble2CD(VSIv2);
                utr.iSInv2 = FormataDouble2CD(ISInv2);
                utr.wSInv2 = FormataDouble2CD(WSInv2);
                utr.tDInv2 = FormataDouble2CD(TDInv2);
                utr.tTInv2 = FormataDouble2CD(TTInv2);
                utr.estInv1 = StrEstIv1;
                utr.iEInv1 = FormataDouble2CD(IEIv1);
                utr.wEInv1 = FormataDouble2CD(WEIv1);
                utr.vSInv1 = FormataDouble2CD(VSIv1);
                utr.iSInv1 = FormataDouble2CD(ISInv1);
                utr.wSInv1 = FormataDouble2CD(WSInv1);
                utr.tDInv1 = FormataDouble2CD(TDInv1);
                utr.tTInv1 = FormataDouble2CD(TTInv1);

                // Estados e Medidas da Caixa d'Água e da Bonba do Poço
                utr.estCxAzul = StrEstCxAzul;
                utr.nivCxAzul = StrNivCxAzul;
                utr.estBomba = StrEstBomba;
                utr.estDjBoia = StrEstDJBoia;
                utr.estDjBomba = StrEstDJBomba;
                utr.tmpBombaLig = TempoBombaLigada;

            } // if (EstComUTR)
        } // if (EstComCncMega)
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
    //                                                                                                                 *
    // Saida: String com a mensagem JSON                                                                               *
    //******************************************************************************************************************
    //
    public static String MontaJsonCC1() {

        CC cc1 = new CC();
        cc1.estComConcMega = EstComCncMega;
        if (EstComCncMega) {
            cc1.estComCc = EstComCC1;
            if (EstComCC1) {
                cc1.vECc = VP12;
                cc1.iECc = IS12;
                cc1.vSCc = VBat1;
                cc1.iSCc = ISCC1;
                cc1.wSCc = WSCC1;
                cc1.tbat = TBat1;
            }
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
    //                                                                                                                 *
    // Saida: String com a mensagem JSON                                                                               *
    //******************************************************************************************************************
    //
    public static String MontaJsonCC2() {

        CC cc2 = new CC();
        cc2.estComConcMega = EstComCncMega;
        if (EstComCncMega) {
            cc2.estComCc = EstComCC2;
            if (EstComCC2) {
                cc2.vECc = VP34;
                cc2.iECc = IS34;
                cc2.vSCc = VBat2;
                cc2.iSCc = ISCC2;
                cc2.wSCc = WSCC2;
                cc2.tbat = TBat2;
            }
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
            byte DH[] = new byte[6];
            DH = LeDataHora();

            MsgReqCoAP[0] = 0x40;                       // Versão = 01 / Tipo = 00 / Token = 0000
            MsgReqCoAP[1] = 0x01;                       // Código de Solicitação: 0.01 GET
            ContMsgCoAP = ContMsgCoAP + 1;              // Incrementa o Identificador de mensagens
            MsgReqCoAP[2] = ByteHigh(ContMsgCoAP); // Byte Mais Significativo do Identificador da Mensagem
            MsgReqCoAP[3] = ByteLow(ContMsgCoAP);  // Byte Menos Significativo do Identificador da Mensagem
            MsgReqCoAP[4] = (byte) (0xB0 + TamURI);     // Delta: 11 - Primeira Opcao 11: Uri-path e Núm. Bytes da URI
            int j = 5;
            for (int i = 0; i < TamURI; i++) {          // Carrega os codigos ASCII da URI
                char Char = URI.charAt(i);
                int ASCII = (int) Char;
                MsgReqCoAP[i + 5] = (byte) ASCII;
                j = j + 1;
            }
            MsgReqCoAP[j] = (byte) 0x11;    // Delta: 1 - Segunda Opcao (11 + 1 = 12): Content-format e Núm. Bytes (1)
            j = j + 1;
            MsgReqCoAP[j] = 42;             // Codigo da Opcao Content-format: application/octet-stream
            j = j + 1;
            MsgReqCoAP[j] = -1;             // Identificador de Inicio do Payload (255)
            j = j + 1;
            MsgReqCoAP[j] = (byte)Comando;  // Carrega o Código do Comando no Payload
            j = j + 1;
            MsgReqCoAP[j] = DH[0];          // Carrega a Hora do Computador no Payload
            j = j + 1;
            MsgReqCoAP[j] = DH[1];          // Carrega a Minuto do Computador no Payload
            j = j + 1;
            MsgReqCoAP[j] = DH[2];          // Carrega a Segundo do Computador no Payload
            j = j + 1;
            MsgReqCoAP[j] = DH[3];          // Carrega a Dia do Computador no Payload
            j = j + 1;
            MsgReqCoAP[j] = DH[4];          // Carrega a Mes do Computador no Payload
            j = j + 1;
            MsgReqCoAP[j] = DH[5];          // Carrega a Ano do Computador no Payload
            j = j + 1;
            int TamCab = j;                 // Carrega o número de bytes do cabeçalho

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
                EstComCncMega = true;
                CarregaVarConcArdMega(MsgRecCoAP);
                Terminal("Recebida Mensagem CoAP do Controlador", false);
                clientSocket.close();

            } catch (java.net.SocketTimeoutException e) {
                clientSocket.close();
                EstComCncMega = false;
                Terminal("O Arduino Mega Concentrador não Respondeu ", false);
            }

        } catch (IOException err) {
            Terminal("Erro na Rotina de Comunicação com o Concentrador Arduino Mega: " + err, false);
            //EstComCncMega = false;
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

        Hora = receiveData1[21];
        Minuto = receiveData1[22];
        Segundo = receiveData1[23];
        Dia = receiveData1[24];
        Mes = receiveData1[25];
        Ano = receiveData1[26];

        // Estados de Comunicação
        EstComUTR = receiveData1[27] > 0;
        EstComCC1 = receiveData1[28] > 0;
        EstComCC2 = receiveData1[29] > 0;

        EstRede = receiveData1[42] > 0;
        MdOp = receiveData1[43] > 0;
        MdCom = receiveData1[44] > 0;
        MdCtrl1 = receiveData1[55] > 0;
        MdCtrl = receiveData1[45] > 0;
        HabCarga1 = receiveData1[46] > 0;
        HabCarga2 = receiveData1[47] > 0;
        HabCarga3 = receiveData1[48] > 0;
        HabCarga4 = receiveData1[49] > 0;
        EstadoCarga3 = receiveData1[53] > 0;
        FontesCCLigadas = receiveData1[73] > 0;

        // Le o estado das saidas digitais
        int NumSd = 32;
        int[] SD = new int[NumSd];
        int k = 112;
        for (int i = 0; i < NumSd; i++){
            SD[i] = receiveData1[k];
            k = k + 1;
        }

        // Carrega as variaveis com os valores das saidas digitais da UTR1
        Iv1Lig = SD[10] > 0; // Iv2Lig = SD[10] > 0;
        Iv2Lig = SD[1] > 0; // Iv1Lig = SD[1] > 0;
        EnergiaCarga1 = SD[17] > 0;
        EnergiaCarga2 = SD[0] > 0;
        EnergiaCarga3 = SD[2] > 0;

        // Estados dos Inversores 1 e 2
        EstadoInversor1 = receiveData1[51] > 0;
        DJEINV1 = receiveData1[37] > 0;
        EstadoInversor2 = receiveData1[52] > 0;

        // Estados da Caixa Azul
        CircBoia = receiveData1[38] > 0;
        BoiaCxAzul = receiveData1[39] > 0;
        CircBomba = receiveData1[40] > 0;
        AlRedeBomba = receiveData1[41] > 0;
        CxAzNvBx = receiveData1[69] > 0;
        EdCxAzCheia = receiveData1[70] > 0;
        BombaLigada = receiveData1[54] > 0;
        EstadoCxAz = receiveData1[72];

        // Le as Medidas de 2 bytes da mensagem recebida
        int NumMed = 48;
        double[] Med = new double[NumMed];
        k = 160;
        for (byte i = 0; i < NumMed; i++){
            Med[i] = DoisBytesInt(receiveData1[k], receiveData1[k + 1]);
            k = k + 2;
        }

        // Carrega as medidas lidas do Concentrador Arduino Mega nas variaveis
        VBat        	 =  Med[0] / 100.0;      // Tensão do Banco de Baterias
        TDInv2     		 =  Med[2] / 100.0;      // Temperatura do Driver do Inversor 2 (8)
        ICircCC    		 =  Med[3] / 1000.0;     // Corrente Total dos Circuitos CC
        VSIv2      		 =  Med[4] / 100.0;      // Tensão de Saída do Inversor 2 (Invertido)
        VSIv1      		 =  Med[6] / 100.0;      // Tensão de Saída do Inversor 1 (Invertido)
        VRede      		 =  Med[5] / 100.0;      // Tensão da Rede
        TTInv2     		 =  Med[7] / 100.0;      // Temperatura do Transformador do Inversor 2 (9)
        TDInv1     		 =  Med[8] / 100.0;      // Temperatura do Driver do Inversor 1 (2)
        TTInv1     		 =  Med[9] / 100.0;   	 // Temperatura do Transformador do Inversor 1 (7)
        ISInv1     		 = (Med[10] * 7) / 1000; // Corrente de Saída do Inversor 1 (13)
        IFonteCC   		 =  Med[11] / 100.0;     // Corrente de Saída da Fonte CC
        IEIv1      		 =  Med[12] / 100.0;   	 // Corrente de Entrada do Inversor 1 (15)
        ISInv2     		 =  Med[13] / 1000.0;  	 // Corrente de Saída do Inversor 2 (10)
        Icarga3    		 =  Med[14] / 1000.0;    // Corrente Carga 3 (Geladeira)
        IEIv2      		 =  Med[15] / 100.0;   	 // Corrente de Entrada do Inversor 2 (12)
        VMBat       	 =  Med[16] / 100.0;     // Tensão Média Estendida do Banco de Baterias
        TempoBombaLigada =  (int)Med[17];        // Tempo da Bomba Ligada
        VP12        	 =  Med[18] / 100.0;     // 0x3100 - PV array voltage 1
        IS12       		 =  Med[19] / 100.0;     // 0x3101 - PV array current 1
        WS12        	 =  Med[20] / 100.0;     // 0x3102 - PV array power 1
        VBat1       	 =  Med[21] / 100.0;     // 0x3104 - Battery voltage 1
        ISCC1        	 =  Med[22] / 100.0;     // 0x3105 - Battery charging current 1
        WSCC1        	 =  Med[23] / 100.0;     // 0x3106 - Battery charging power 1
        TBat1         	 =  Med[24] / 100.0;     // 0x3110 - Battery Temperature 1
        VP34         	 =  Med[26] / 100.0;     // 0x3100 - PV array voltage 2
        IS34         	 =  Med[27] / 100.0;     // 0x3101 - PV array current 2
        WS34         	 =  Med[28] / 100.0;     // 0x3102 - PV array power 2
        VBat2        	 =  Med[29] / 100.0;     // 0x3104 - Battery voltage 2
        ISCC2        	 =  Med[30] / 100.0;     // 0x3105 - Battery charging current 2
        WSCC2        	 =  Med[31] / 100.0;     // 0x3106 - Battery charging power 2 (Med[45])
        TBat2         	 =  Med[32] / 100.0;     // 0x3110 - Battery Temperature 1
        WCircCC      	 =  Med[35] / 100.0;     // Potencia Consumida pelos Circuitos de 24Vcc
        WFonteCC     	 =  Med[36] / 100.0;     // Potencia Fornecida pela Fonte 24Vcc
        IBat         	 =  Med[37] / 100.0;     // Corrente de Carga ou Descarga do Banco de Baterias

        if (!Iv1Lig) {                      	 // Se o Inversor 1 estiver desligado,
            IEIv1 = 0;                      	 // zera a corrente de entrada
            VSIv1 = 0;                      	 // zera a tensão de saída
            ISInv1 = 0;                     	 // zera a corrente de saída
        }
        if (!Iv2Lig) {                      	 // Se o Inversor 2 estiver desligado, zera a tensão de saída
            IEIv2 = 0;                      	 // zera a corrente de entrada
            VSIv2 = 0;                      	 // zera a tensão de saída
            ISInv2 = 0;                     	 // zera a corrente de saída
        }

        // Variáveis Calculadas
        WBat = (VBat * IBat)/100;	        // Potência de Carga/Descarga do Banco de Baterias
        ITotCg = IEIv1 + IEIv2 + ICircCC; 	// Corrente Total Consumida pelas Cargas
        WTotCg =  WEIv1 + WEIv2 + WCircCC;	// Potência Total Consumida pelas Cargas
        WEIv2 = (VBat * IEIv2);             // Potencia de Entrada do Inversor 2 (Med[38])
        WSInv2 = (VSIv2 * ISInv2);          // Potencia de Saida do Inversor 2 (Med[39])
        WEIv1 = (VBat * IEIv1);             // Potência de Entrada do Inversor 1 (Med[41])
        WSInv1 = (VSIv1 * ISInv1);	        // Potencia de Saida do Inversor 1 (Med[42])

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
    //                                                                                                                 *
    // Saida: mensagem JSON em formato binário                                                                         *
    //******************************************************************************************************************
    //
    public static String MontaJsonCAQ() {

        EnvRecMsgCtrlAQ();
        String StrEstBombaAQ = "";			// Estado da Bomba de Água Quente (Desligada / Ligada)
        String StrEstAquecedor = "";		// Estado do Aquecedor Elétrico do Boiler (Desligado / Ligado)

        CAQ ControladorAguaQuente = new CAQ();

        if (EstComAQ) {
            StrEstBombaAQ = "Desligada";
            if (EstBombaAQ == 1) {
                StrEstBombaAQ = "Ligada";
            }
            else {
                StrEstBombaAQ = "Desligada";
            }

            StrEstAquecedor = "Desligado";
            if (EstAquecedor == 1) {
                StrEstAquecedor = "Ligado";	}
            else {
                StrEstAquecedor = "Desligado";
            }
            ControladorAguaQuente.estComAq = EstComAQ;
            ControladorAguaQuente.tempBoiler = TemperaturaBoiler;
            ControladorAguaQuente.tempPlacaSolar = TemperaturaPlaca;
            ControladorAguaQuente.estBombaAguaQuente = StrEstBombaAQ;
            ControladorAguaQuente.estAquecedor = StrEstAquecedor;
            ControladorAguaQuente.tempoBombaAqLigada = TempoBombaAQLigada;
            ControladorAguaQuente.vazaoBombaPoco = FormataDouble2CD(VazaoBombaPoco);
        }

        Gson gson = new Gson();
        String MsgJson = gson.toJson(ControladorAguaQuente);
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
            String MsgTerm = "Enviada Requisicao Binaria para o Controlador de Água Quente";
            Terminal(MsgTerm, false);

            // Espera a Mensagem Binária de Resposta. Se a mensagem de resposta  for recebida, carrega nas variáveis
            try {
                DatagramPacket receivePacket = new DatagramPacket(MsgBinRec, TamMsgRsp);
                clientSocket.receive(receivePacket);
                Terminal("Recebida Mensagem Binaria do Controlador de Água Quente", false);

                EstComAQ = true;
                EstBombaAQ = BytetoInt(MsgBinRec[73]);
                EstAquecedor = BytetoInt(MsgBinRec[72]);
                TemperaturaBoiler = TwoBytetoInt(MsgBinRec[48], MsgBinRec[49]) / 100.0;
                TemperaturaPlaca = TwoBytetoInt(MsgBinRec[51], MsgBinRec[52]) / 100.0;
                TempoBombaAQLigada = TwoBytetoInt(MsgBinRec[66], MsgBinRec[67]);
                VazaoBombaPoco = TwoBytetoInt(MsgBinRec[76], MsgBinRec[77]) / 4.8;
                TotalVazaoBombaPoco = ThreeBytetoInt(MsgBinRec[69], MsgBinRec[70], MsgBinRec[71]);
            }
            catch(java.net.SocketTimeoutException e) {
                EstComAQ = false;
                Terminal("Erro: o Controlador de Água Quente nao Respondeu", false);
            }
            clientSocket.close();
        }
        catch (IOException err) {
            EstComAQ = false;
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

        CEN ControladorEnergia = new CEN();
        String IPConcArd = "192.168.0.152";
        int PortaUDP = 5683;
        int funcao = 4;
        int RegTensaoFase1 = 16;
        int RegCorrenteFase1 = 22;
        int RegPotAtivFase1 = 0x1C;
        int RegFatPotFase1 = 46;
        int RegEnAtivaPos = 52;
        int RegEnAtivaNeg = 56;

        ControladorEnergia.tensaoK = FormataDouble2CD(ModbusKron(IPConcArd, PortaUDP, funcao, RegTensaoFase1));
        ControladorEnergia.correnteRedeK = FormataDouble2CD(ModbusKron(IPConcArd, PortaUDP, funcao, RegCorrenteFase1));
        ControladorEnergia.potenciaAtivaRedeK = FormataDouble2CD(ModbusKron(IPConcArd, PortaUDP, funcao, RegPotAtivFase1));
        ControladorEnergia.fatorPotenciaRedeK = FormataDouble2CD(ModbusKron(IPConcArd, PortaUDP, funcao, RegFatPotFase1));
        ControladorEnergia.energiaAtivaPosRedeK = FormataDouble2CD(ModbusKron(IPConcArd, PortaUDP, funcao, RegEnAtivaPos));
        ControladorEnergia.energiaAtivaNegRedeK = FormataDouble2CD(ModbusKron(IPConcArd, PortaUDP, funcao, RegEnAtivaNeg));

        double tensao = (25.9935 * Medida01) / 100.0;
        ControladorEnergia.tensaoEntradaRede = FormataDouble2CD(tensao);

        double corrente = (6.1 * Medida02) / 1000.0;
        if (corrente < 0.09) {
            corrente = 0.0;
        }

        ControladorEnergia.correnteInvOnGrid = FormataDouble2CD(corrente);
        ControladorEnergia.potenciaInvOnGrid = FormataDouble2CD(tensao * corrente);
        ControladorEnergia.EstComCncUno = EstComConcArd;
        ControladorEnergia.EstComKron = EstComKron;

        Gson gson = new Gson();
        String MsgJson = gson.toJson(ControladorEnergia);

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
        double Valor = 0.0;
        int cont = 0;
        EstComKron = false;

        while ((!EstComKron) && (cont < 3)) {
            cont = cont + 1;
            try {

                MsgReq[8]= 1;	          // Endereço do Multimedidor
                MsgReq[9]= (byte)funcao;  // Função MODBUS

                if (funcao == 16) {	EndReg = 2; }

                MsgReq[10]= 0;	            // Campo 1: Função 4 = Registro Inicial (MSB)
                MsgReq[11]= (byte)EndReg;	// Campo 2: Função 4 = Registro Inicial (LSB)
                MsgReq[12]= 0;	            // Campo 3: Função 4 = Número de Registros (MSB)
                MsgReq[13]= 2;	            // Campo 4: Função 4 = Número de Registros (LSB)

                MsgReq[14]= 4;	            // Campo 5 = Número de Bytes ( 4 )
                MsgReq[15]= 0;  	        // Campo 6 ( F2 )  - Valor a programar (RTC)
                MsgReq[16]= 0;              // Campo 7 ( F1 )  - Valor a programar (RTC)
                MsgReq[17]= 32;             // Campo 8 ( F0 )  - Valor a programar (RTC)
                MsgReq[18]= 65;	            // Campo 9 ( EXP ) - Valor a programar (RTC)

                DatagramSocket clientSocket = new DatagramSocket();
                InetAddress IPAddress = InetAddress.getByName(EndIP);
                clientSocket.setSoTimeout(2000);
                DatagramPacket sendPacket = new DatagramPacket(MsgReq, TamMsgReq, IPAddress, Porta);
                clientSocket.send(sendPacket);

                Terminal("Enviada mensagem de requisição para o Multimedidor Kron", false);

                // Espera a Mensagem Binária de Resposta. Se a mensagem de resposta  for recebida, carrega nas variáveis
                try {
                    DatagramPacket receivePacket = new DatagramPacket(MsgBinRec, TamMsgRsp);
                    clientSocket.receive(receivePacket);
                    EstComConcArd = true;
                    MsgValor[0] = 0;

                    int NumBytesMsg = MsgBinRec[4];
                    if (NumBytesMsg >= 9) {

                        int EndRec = MsgBinRec[8];
                        int FuncaoRec = MsgBinRec[9];
                        int NumBytes = MsgBinRec[10];

                        if ((EndRec == 1) && (FuncaoRec == funcao) && (NumBytes == 4)) {
                            EstComKron = true;
                            Terminal("Recebida mensagem Kron com " + NumBytesMsg + " Bytes" , false);

                            byte f2 = MsgBinRec[11];
                            byte f1 = MsgBinRec[12];
                            byte f0 = MsgBinRec[13];
                            byte exp = MsgBinRec[14];
                            Valor = ConverteIEEE754PfValor(f0, f1, f2, exp);

                            MsgValor[0] = 1;              // Estado de Comunicação com o Multimedidor: OK = 1.
                            MsgValor[1] = MsgBinRec[11];  // f2
                            MsgValor[2] = MsgBinRec[12];  // f1
                            MsgValor[3] = MsgBinRec[13];  // f0
                            MsgValor[4] = MsgBinRec[14];  // exp

                            Medida00 = TwoBytetoInt(MsgBinRec[32], MsgBinRec[33]);
                            Medida01 = TwoBytetoInt(MsgBinRec[34], MsgBinRec[35]);
                            Medida02 = TwoBytetoInt(MsgBinRec[36], MsgBinRec[37]);
                            Medida03 = TwoBytetoInt(MsgBinRec[38], MsgBinRec[39]);
                            Medida04 = TwoBytetoInt(MsgBinRec[40], MsgBinRec[41]);
                            Medida05 = TwoBytetoInt(MsgBinRec[42], MsgBinRec[43]);

                            Medidas[0] = MsgBinRec[32];
                            Medidas[1] = MsgBinRec[33];
                            Medidas[2] = MsgBinRec[34];
                            Medidas[3] = MsgBinRec[35];
                            Medidas[4] = MsgBinRec[36];
                            Medidas[5] = MsgBinRec[37];
                            Medidas[6] = MsgBinRec[38];
                            Medidas[7] = MsgBinRec[39];
                            Medidas[8] = MsgBinRec[40];
                            Medidas[9] = MsgBinRec[41];
                            Medidas[10] = MsgBinRec[42];
                            Medidas[11] = MsgBinRec[43];
                        }
                        else {
                            EstComKron = false;
                            MsgValor[0] = 0;
                        }
                    }
                    else {
                        EstComKron = false;
                        MsgValor[0] = 0;
                    }
                }
                catch(java.net.SocketTimeoutException e) {
                    clientSocket.close();
                    EstComConcArd = false;
                    MsgValor[0] = 0;
                }
            }
            catch (IOException err) {
                Terminal("Erro na Rotina EnvRecMsgSrv: " + err, false);
                Valor = 70000.0;  // Código de retorno de erro na rotina
            }
        } // while
        return(Valor);

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