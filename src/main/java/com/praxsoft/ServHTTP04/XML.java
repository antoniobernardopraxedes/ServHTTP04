package com.praxsoft.ServHTTP04;

import com.google.gson.Gson;

import static com.praxsoft.ServHTTP04.SupService.*;

public class XML {

    private static UTRAM utr = new UTRAM();
    private static CC cc1 = new CC();
    private static CC cc2 = new CC();
    private static CAQ caq = new CAQ();
    private static CEN cen = new CEN();
    private static Gson gson = new Gson();

    private static String msgJsonUtr;
    private static String msgJsonCc1;
    private static String msgJsonCc2;
    private static String msgJsonCaq;
    private static String msgJsonCen;


    public static String getMsgJsonUtr() {
        return (msgJsonUtr);
    }

    public static String getMsgJsonCc1() {
        return (msgJsonCc1);
    }

    public static String getMsgJsonCc2() {
        return (msgJsonCc2);
    }

    public static String getMsgJsonCaq() {
        return (msgJsonCaq);
    }

    public static String getMsgJsonCen() {
        return (msgJsonCen);
    }


    //******************************************************************************************************************
    // Nome do Método: CarregaVariaveis                                                                                *
    //	                                                                                                               *
    // Funcao: carrega as variáveis de um objeto (UTR, CC!, CC2, CAQ ou CEN) a partir da mensagem JSON recebida do     *
    //         broker                                                                                                  *
    //                                                                                                                 *
    // Entrada: String topico que define o objeto a ter as variáveis atualizadas e a mensagem JSON recebida do broker  *
    //          em ng                                                                                                  *
    //                                                                                                                 *
    // Saida: não tem                                                                                                  *
    //******************************************************************************************************************
    //
    public static void CarregaVariaveis(String topico, String MsgJson) {

        if (topico.equals("DadosUTR")) {
            utr = gson.fromJson(MsgJson, UTRAM.class);
        }
        if (topico.equals("DadosCC1")) {
            cc1 = gson.fromJson(MsgJson, CC.class);
        }
        if (topico.equals("DadosCC2")) {
            cc2 = gson.fromJson(MsgJson, CC.class);
        }
        if (topico.equals("DadosCAQ")) {
            caq = gson.fromJson(MsgJson, CAQ.class);
        }
        if (topico.equals("DadosCEN")) {
            cen = gson.fromJson(MsgJson, CEN.class);
        }
    }

    //******************************************************************************************************************
    // Nome do Método: CarregaVariaveisFalha                                                                           *
    //	                                                                                                               *
    // Funcao: carrega as variáveis de um objeto (UTR, CC!, CC2, CAQ ou CEN) com null ou 0.0 em condição de falha      *
    //         broker                                                                                                  *
    //                                                                                                                 *
    // Entrada: não tem                                                                                                *
    //                                                                                                                 *
    // Saida: não tem                                                                                                  *
    //******************************************************************************************************************
    //
    public static void CarregaVariaveisFalha() {

        UTRAM utrf = new UTRAM();
        CC cc1f = new CC();
        CC cc2f = new CC();
        CAQ caqf = new CAQ();
        CEN cenf = new CEN();

        utr = utrf;
        cc1 = cc1f;
        cc2 = cc2f;
        caq = caqf;
        cen = cenf;
    }

    //******************************************************************************************************************
    // Nome do Método: CarregaVariaveisLocal                                                                           *
    //	                                                                                                               *
    // Funcao: chama o Concentrador Arduino Mega, o Controlador de Água Quente e o Concentrador Arduino Uno  e         *
    //         carrega as variáveis a partir das mensagens binárias recebidas                                          *
    //                                                                                                                 *
    // Entrada: endereço IP do Concentrador, endereço ip do controlador de água quente, porta UDP, contador de         *
    //          mensagens CoAP e comando com o comando recebido                                                        *
    //                                                                                                                 *
    // Saida: não tem                                                                                                  *
    //******************************************************************************************************************
    //
    public static void CarregaVariaveisLocal(int ContMsgCoAP, int Comando) {

        Dados.EnvRecMsgConcMega(ContMsgCoAP, Comando);
        msgJsonUtr = Dados.MontaJsonUTR("Operação Local - ServHTTP04");
        msgJsonCc1 = Dados.MontaJsonCC1();
        msgJsonCc2 = Dados.MontaJsonCC2();

        msgJsonCaq = Dados.MontaJsonCAQ();
        msgJsonCen = Dados.MontaJsonCEN();

        utr = gson.fromJson(msgJsonUtr, UTRAM.class);
        cc1 = gson.fromJson(msgJsonCc1, CC.class);
        cc2 = gson.fromJson(msgJsonCc2, CC.class);
        caq = gson.fromJson(msgJsonCaq, CAQ.class);
        cen = gson.fromJson(msgJsonCen, CEN.class);

    }

    //******************************************************************************************************************
    // Nome do Método: MontaMensagem()                                                                                 *
    //	                                                                                                               *
    // Funcao: monta uma String com a mensagem XML de resposta inserindo o valor das variáveis                         *
    //                                                                                                                 *
    // Entrada: array String com as Tags dos Níveis 0, 1 e 2 e os valores das variáveis de supervisão                  *
    //                                                                                                                 *
    // Saida: String com a mensagem XML                                                                                *
    //******************************************************************************************************************
    //
    public static String MontaMensagem(String cmdEx) {

        utr.cmdEx = cmdEx;

        // Carrega na StringXML Array os Tags de Níveis 0,1,e 2 e as variáveis de supervisão
        String MsgXMLArray[][][][] = new String[1][10][30][2];
        int IdNv0 = 0;
        int IdNv1 = 0;
        int i = 0;
        MsgXMLArray[IdNv0][IdNv1][0][0] = "LOCAL001";	MsgXMLArray[IdNv0][IdNv1][0][1] = "06";

        IdNv1 = 1;
        MsgXMLArray[IdNv0][IdNv1][i][0] = "GERAL";		MsgXMLArray[IdNv0][IdNv1][i++][1] = "20";

        MsgXMLArray[IdNv0][IdNv1][i][0] = "COMCNV";		MsgXMLArray[IdNv0][IdNv1][i++][1] = "Normal";
        MsgXMLArray[IdNv0][IdNv1][i][0] = "COMCNC";		MsgXMLArray[IdNv0][IdNv1][i++][1] = ImpEstCom(utr.estComConcMega);
        MsgXMLArray[IdNv0][IdNv1][i][0] = "COMUTR";		MsgXMLArray[IdNv0][IdNv1][i++][1] = ImpEstCom(utr.estComUtr);
        MsgXMLArray[IdNv0][IdNv1][i][0] = "COMCC1";		MsgXMLArray[IdNv0][IdNv1][i++][1] = ImpEstCom(cc1.estComCc);
        MsgXMLArray[IdNv0][IdNv1][i][0] = "COMCC2";		MsgXMLArray[IdNv0][IdNv1][i++][1] = ImpEstCom(cc2.estComCc);
        MsgXMLArray[IdNv0][IdNv1][i][0] = "CLK";		MsgXMLArray[IdNv0][IdNv1][i++][1] = ImpHora(utr.hora, utr.minuto, utr.segundo);
        MsgXMLArray[IdNv0][IdNv1][i][0] = "DATA";		MsgXMLArray[IdNv0][IdNv1][i++][1] = ImpData(utr.dia, utr.mes, utr.ano);
        MsgXMLArray[IdNv0][IdNv1][i][0] = "CMDEX";		MsgXMLArray[IdNv0][IdNv1][i++][1] = utr.cmdEx;
        MsgXMLArray[IdNv0][IdNv1][i][0] = "MDOP";		MsgXMLArray[IdNv0][IdNv1][i++][1] = utr.modoOp;
        MsgXMLArray[IdNv0][IdNv1][i][0] = "MDCOM";		MsgXMLArray[IdNv0][IdNv1][i++][1] = utr.modoCom;
        MsgXMLArray[IdNv0][IdNv1][i][0] = "MDCT1";		MsgXMLArray[IdNv0][IdNv1][i++][1] = utr.modoCtrl1;
        MsgXMLArray[IdNv0][IdNv1][i][0] = "MDCT3";	    MsgXMLArray[IdNv0][IdNv1][i++][1] = utr.modoCtrl;
        MsgXMLArray[IdNv0][IdNv1][i][0] = "ENCG1";		MsgXMLArray[IdNv0][IdNv1][i++][1] = utr.energiaCg1;
        MsgXMLArray[IdNv0][IdNv1][i][0] = "ENCG2";		MsgXMLArray[IdNv0][IdNv1][i++][1] = utr.energiaCg2;
        MsgXMLArray[IdNv0][IdNv1][i][0] = "ENCG3";		MsgXMLArray[IdNv0][IdNv1][i++][1] = utr.energiaCg3;
        MsgXMLArray[IdNv0][IdNv1][i][0] = "ICG3";		MsgXMLArray[IdNv0][IdNv1][i++][1] = utr.iCg3 + "";
        MsgXMLArray[IdNv0][IdNv1][i][0] = "VBAT";		MsgXMLArray[IdNv0][IdNv1][i++][1] = utr.vBat + "";
        MsgXMLArray[IdNv0][IdNv1][i][0] = "VREDE";		MsgXMLArray[IdNv0][IdNv1][i++][1] = utr.vRede + "";
        MsgXMLArray[IdNv0][IdNv1][i][0] = "ESTVRD";		MsgXMLArray[IdNv0][IdNv1][i++][1] = utr.estRede;
        MsgXMLArray[IdNv0][IdNv1][i][0] = "TBAT";		MsgXMLArray[IdNv0][IdNv1][i++][1] = utr.tBat + "";

        IdNv1 = 2; // Grupo de 08 Variáveis de Informação da Bomba do Poço e da Caixa Azul
        MsgXMLArray[IdNv0][IdNv1][0][0] = "AGUA";		MsgXMLArray[IdNv0][IdNv1][0][1] = "08";

        MsgXMLArray[IdNv0][IdNv1][1][0] = "ESTCXAZ";	MsgXMLArray[IdNv0][IdNv1][1][1] = utr.estCxAzul;
        MsgXMLArray[IdNv0][IdNv1][2][0] = "NIVCXAZ";	MsgXMLArray[IdNv0][IdNv1][2][1] = utr.nivCxAzul;
        MsgXMLArray[IdNv0][IdNv1][3][0] = "ESTBMB";		MsgXMLArray[IdNv0][IdNv1][3][1] = utr.estBomba;
        MsgXMLArray[IdNv0][IdNv1][4][0] = "ESTDJB";		MsgXMLArray[IdNv0][IdNv1][4][1] = utr.estDjBoia;
        MsgXMLArray[IdNv0][IdNv1][5][0] = "ESTDJRB";	MsgXMLArray[IdNv0][IdNv1][5][1] = utr.estDjBomba;
        MsgXMLArray[IdNv0][IdNv1][6][0] = "ENBMB";		MsgXMLArray[IdNv0][IdNv1][6][1] = utr.energiaCg4;
        MsgXMLArray[IdNv0][IdNv1][7][0] = "TMPBL";		MsgXMLArray[IdNv0][IdNv1][7][1] = FormAnaHora(utr.tmpBombaLig);
        MsgXMLArray[IdNv0][IdNv1][8][0] = "VZBMB";		MsgXMLArray[IdNv0][IdNv1][8][1] = caq.vazaoBombaPoco + "";

        IdNv1 = 3; // Grupo de 19 Variáveis de Informação da Geração Solar e do Consumo
        MsgXMLArray[IdNv0][IdNv1][0][0] = "GERCONS";	MsgXMLArray[IdNv0][IdNv1][0][1] = "19";

        MsgXMLArray[IdNv0][IdNv1][1][0] = "VP12";		MsgXMLArray[IdNv0][IdNv1][1][1] = cc1.vECc + "";
        MsgXMLArray[IdNv0][IdNv1][2][0] = "IS12";		MsgXMLArray[IdNv0][IdNv1][2][1] = cc1.iECc + "";
        MsgXMLArray[IdNv0][IdNv1][3][0] = "VBAT1";		MsgXMLArray[IdNv0][IdNv1][3][1] = cc1.vSCc + "";
        MsgXMLArray[IdNv0][IdNv1][4][0] = "ISCC1";		MsgXMLArray[IdNv0][IdNv1][4][1] = cc1.iSCc + "";
        MsgXMLArray[IdNv0][IdNv1][5][0] = "WSCC1";		MsgXMLArray[IdNv0][IdNv1][5][1] = cc1.wSCc + "";
        MsgXMLArray[IdNv0][IdNv1][6][0] = "TBAT1";		MsgXMLArray[IdNv0][IdNv1][6][1] = cc1.tbat + "";
        MsgXMLArray[IdNv0][IdNv1][7][0] = "VP34";		MsgXMLArray[IdNv0][IdNv1][7][1] = cc2.vECc + "";
        MsgXMLArray[IdNv0][IdNv1][8][0] = "IS34";		MsgXMLArray[IdNv0][IdNv1][8][1] = cc2.iECc + "";
        MsgXMLArray[IdNv0][IdNv1][9][0] = "VBAT2";		MsgXMLArray[IdNv0][IdNv1][9][1] = cc2.vSCc + "";
        MsgXMLArray[IdNv0][IdNv1][10][0] = "ISCC2";		MsgXMLArray[IdNv0][IdNv1][10][1] = cc2.iSCc + "";
        MsgXMLArray[IdNv0][IdNv1][11][0] = "WSCC2";		MsgXMLArray[IdNv0][IdNv1][11][1] = cc2.wSCc + "";
        MsgXMLArray[IdNv0][IdNv1][12][0] = "TBAT2";		MsgXMLArray[IdNv0][IdNv1][12][1] = cc2.tbat + "";
        MsgXMLArray[IdNv0][IdNv1][13][0] = "ITOTCG";	MsgXMLArray[IdNv0][IdNv1][13][1] = utr.iTotCg24v + "";
        MsgXMLArray[IdNv0][IdNv1][14][0] = "WTOTCG";	MsgXMLArray[IdNv0][IdNv1][14][1] = utr.wTotCg24v + "";
        MsgXMLArray[IdNv0][IdNv1][15][0] = "ESTFT1";	MsgXMLArray[IdNv0][IdNv1][15][1] = utr.estFontesCC;
        MsgXMLArray[IdNv0][IdNv1][16][0] = "ESTFT2";	MsgXMLArray[IdNv0][IdNv1][16][1] = utr.estFontesCC;
        MsgXMLArray[IdNv0][IdNv1][17][0] = "ICIRCC";	MsgXMLArray[IdNv0][IdNv1][17][1] = utr.iCirCc + "";
        MsgXMLArray[IdNv0][IdNv1][18][0] = "WCIRCC";	MsgXMLArray[IdNv0][IdNv1][18][1] = utr.wCirCC + "";
        MsgXMLArray[IdNv0][IdNv1][19][0] = "WTOTGRCC";	MsgXMLArray[IdNv0][IdNv1][19][1] = utr.wTotGerCC + "";

        IdNv1 = 4; // Grupo de 16 Variáveis de Informação dos Inversores 1 e 2
        MsgXMLArray[IdNv0][IdNv1][0][0] = "INV";		MsgXMLArray[IdNv0][IdNv1][0][1] = "16";

        MsgXMLArray[IdNv0][IdNv1][1][0] = "ESTIV2";		MsgXMLArray[IdNv0][IdNv1][1][1] = utr.estInvD;
        MsgXMLArray[IdNv0][IdNv1][2][0] = "IEIV2";		MsgXMLArray[IdNv0][IdNv1][2][1] = utr.iEInvD + "";
        MsgXMLArray[IdNv0][IdNv1][3][0] = "WEIV2";		MsgXMLArray[IdNv0][IdNv1][3][1] = utr.wEInvD + "";
        MsgXMLArray[IdNv0][IdNv1][4][0] = "VSIV2";		MsgXMLArray[IdNv0][IdNv1][4][1] = utr.vSInvD + "";
        MsgXMLArray[IdNv0][IdNv1][5][0] = "ISIV2";		MsgXMLArray[IdNv0][IdNv1][5][1] = utr.iSInvD + "";
        MsgXMLArray[IdNv0][IdNv1][6][0] = "WSIV2";		MsgXMLArray[IdNv0][IdNv1][6][1] = utr.wSInvD + "";
        MsgXMLArray[IdNv0][IdNv1][7][0] = "TDIV2";		MsgXMLArray[IdNv0][IdNv1][7][1] = utr.tDInvD + "";
        MsgXMLArray[IdNv0][IdNv1][8][0] = "TTIV2";		MsgXMLArray[IdNv0][IdNv1][8][1] = utr.tTInvD + "";

        MsgXMLArray[IdNv0][IdNv1][9][0] = "ESTIV1";		MsgXMLArray[IdNv0][IdNv1][9][1] = utr.estInvE;
        MsgXMLArray[IdNv0][IdNv1][10][0] = "IEIV1";		MsgXMLArray[IdNv0][IdNv1][10][1] = utr.iEInvE + "";
        MsgXMLArray[IdNv0][IdNv1][11][0] = "WEIV1";		MsgXMLArray[IdNv0][IdNv1][11][1] = utr.wEInvE + "";
        MsgXMLArray[IdNv0][IdNv1][12][0] = "VSIV1";		MsgXMLArray[IdNv0][IdNv1][12][1] = utr.vSInvE + "";
        MsgXMLArray[IdNv0][IdNv1][13][0] = "ISIV1";		MsgXMLArray[IdNv0][IdNv1][13][1] = utr.iSInvE + "";
        MsgXMLArray[IdNv0][IdNv1][14][0] = "WSIV1";		MsgXMLArray[IdNv0][IdNv1][14][1] = utr.wSInvE + "";
        MsgXMLArray[IdNv0][IdNv1][15][0] = "TDIV1";		MsgXMLArray[IdNv0][IdNv1][15][1] = utr.tDInvE + "";
        MsgXMLArray[IdNv0][IdNv1][16][0] = "TTIV1";		MsgXMLArray[IdNv0][IdNv1][16][1] = utr.tTInvE + "";

        IdNv1 = 5; // Grupo de 6 Variáveis de Informação da Água Quente
        MsgXMLArray[IdNv0][IdNv1][0][0] = "AGUAQ";		MsgXMLArray[IdNv0][IdNv1][0][1] = "06";

        MsgXMLArray[IdNv0][IdNv1][1][0] = "ESTUAQ"; 	MsgXMLArray[IdNv0][IdNv1][1][1] = ImpEstCom(caq.estComAq);
        MsgXMLArray[IdNv0][IdNv1][2][0] = "ESTBAQ"; 	MsgXMLArray[IdNv0][IdNv1][2][1] = caq.estBombaAguaQuente;
        MsgXMLArray[IdNv0][IdNv1][3][0] = "ESTAQ";		MsgXMLArray[IdNv0][IdNv1][3][1] = caq.estAquecedor;
        MsgXMLArray[IdNv0][IdNv1][4][0] = "TEMPBL";		MsgXMLArray[IdNv0][IdNv1][4][1] = caq.tempBoiler + "";
        MsgXMLArray[IdNv0][IdNv1][5][0] = "TEMPPS";		MsgXMLArray[IdNv0][IdNv1][5][1] = caq.tempPlacaSolar + "";
        MsgXMLArray[IdNv0][IdNv1][6][0] = "TMPBL";		MsgXMLArray[IdNv0][IdNv1][6][1] = FormAnaHora(caq.tempoBombaAqLigada);

        IdNv1 = 6; // Grupo de 11 Variáveis de Medidas do Multimedidor Kron
        MsgXMLArray[IdNv0][IdNv1][0][0] = "KRON";		MsgXMLArray[IdNv0][IdNv1][0][1] = "11";

        MsgXMLArray[IdNv0][IdNv1][1][0] = "ESTCCA";		MsgXMLArray[IdNv0][IdNv1][1][1] = ImpEstCom(cen.estComCncUno);
        MsgXMLArray[IdNv0][IdNv1][2][0] = "ESTCK";		MsgXMLArray[IdNv0][IdNv1][2][1] = ImpEstCom(cen.estComKron);
        MsgXMLArray[IdNv0][IdNv1][3][0] = "TENSAOK";	MsgXMLArray[IdNv0][IdNv1][3][1] = cen.tensaoK + "";
        MsgXMLArray[IdNv0][IdNv1][4][0] = "CORRENTEK";	MsgXMLArray[IdNv0][IdNv1][4][1] = cen.correnteRedeK + "";
        MsgXMLArray[IdNv0][IdNv1][5][0] = "POTATIVK";	MsgXMLArray[IdNv0][IdNv1][5][1] = cen.potenciaAtivaRedeK + "";
        MsgXMLArray[IdNv0][IdNv1][6][0] = "FATPOTK";	MsgXMLArray[IdNv0][IdNv1][6][1] = cen.fatorPotenciaRedeK + "";
        MsgXMLArray[IdNv0][IdNv1][7][0] = "ENERGIAK";	MsgXMLArray[IdNv0][IdNv1][7][1] = cen.energiaAtivaPosRedeK + "";
        MsgXMLArray[IdNv0][IdNv1][8][0] = "ENERGIANK";	MsgXMLArray[IdNv0][IdNv1][8][1] = cen.energiaAtivaNegRedeK + "";
        MsgXMLArray[IdNv0][IdNv1][9][0] = "TENSAOEI";	MsgXMLArray[IdNv0][IdNv1][9][1] = cen.tensaoEntradaRede + "";
        MsgXMLArray[IdNv0][IdNv1][10][0] = "CORRENTEI";	MsgXMLArray[IdNv0][IdNv1][10][1] = cen.correnteInvOnGrid + "";
        MsgXMLArray[IdNv0][IdNv1][11][0] = "POTENCIAI";	MsgXMLArray[IdNv0][IdNv1][11][1] = cen.potenciaInvOnGrid + "";

        // Retorna a Mensagem XML completa em formato de String
        String MsgXML = StringXML(MsgXMLArray) + "  ";
        return (MsgXML);
    }

    //******************************************************************************************************************
    // Nome do Método: StringXML()                                                                                     *
    //	                                                                                                               *
    // Funcao: monta uma String com a mensagem XML de resposta inserindo o valor das variáveis                         *
    //                                                                                                                 *
    // Entrada: array String com as Tags dos Níveis 0, 1 e 2 e os valores das variáveis de supervisão                  *
    // Saida: String com a mensagem XML                                                                                *
    //******************************************************************************************************************
    //
    private static String StringXML(String MsgXMLArray[][][][]) {

        String MsgXML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n";
        MsgXML = MsgXML + "<" + MsgXMLArray[0][0][0][0] + ">\n";

        char Dezena = MsgXMLArray[0][0][0][1].charAt(0);
        char Unidade = MsgXMLArray[0][0][0][1].charAt(1);
        int NmTagNv1 = TwoCharToInt(Dezena, Unidade);

        for (int i = 1; i <= NmTagNv1; i++){
            MsgXML = MsgXML + "  <" + MsgXMLArray[0][i][0][0] + ">\n";
            char DzNumVar = MsgXMLArray[0][i][0][1].charAt(0);
            char UnNumVar = MsgXMLArray[0][i][0][1].charAt(1);
            int NumVar = TwoCharToInt(DzNumVar, UnNumVar);

            for (int j = 1; j <= NumVar; j++){
                MsgXML = MsgXML + "    <"+MsgXMLArray[0][i][j][0]+">" +
                        MsgXMLArray[0][i][j][1] +
                        "</"+MsgXMLArray[0][i][j][0]+">\n";
            }
            MsgXML = MsgXML + "  </" + MsgXMLArray[0][i][0][0] + ">\n";
        }
        MsgXML = MsgXML + "</" + MsgXMLArray[0][0][0][0] + "> \n";

        return(MsgXML);

    } // Fim do Método
}
