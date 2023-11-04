package com.praxsoft.ServHTTP04;

public class UTRAM {

    // Estados de Comunicação
    public boolean estComConcMega;
    public boolean estComUtr;

    // Data, Hora e Mensagem de Estado
    public int hora;
    public int minuto;
    public int segundo;
    public int dia;
    public int mes;
    public int ano;
    public String cmdEx;

    // Estados e Medidas Gerais
    public String modoOp;
    public String modoCom;
    public String modoCtrl1;
    public String modoCtrl;
    public double vRede;
    public String estRede;
    public double vBat;
    public double vmBat;
    public double tBat;
    public String energiaCg1;
    public String energiaCg2;
    public String energiaCg3;
    public String energiaCg4;
    public double iCg3;
    public double iTotCg24v;
    public double wTotCg24v;
    public double iCirCc;
    public double wCirCC;
    public double wTotGerCC;
    public String estFontesCC;

    // EStados e Medidas dos Inversores 1 e 2
    public String estInvD;
    public double iEInvD;
    public double wEInvD;
    public double vSInvD;
    public double iSInvD;
    public double wSInvD;
    public double tDInvD;
    public double tTInvD;
    public String estInvE;
    public double iEInvE;
    public double wEInvE;
    public double vSInvE;
    public double iSInvE;
    public double wSInvE;
    public double tDInvE;
    public double tTInvE;

    // Estados e Medidas da Caixa d'Água e da Bonba do Poço
    public String estCxAzul;
    public String nivCxAzul;
    public String estBomba;
    public String estDjBoia;
    public String estDjBomba;
    public int tmpBombaLig;
}
