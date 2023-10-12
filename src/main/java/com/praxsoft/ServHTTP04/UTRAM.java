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
    public String estFontesCC;

    // EStados e Medidas dos Inversores 1 e 2
    public String estInv2;
    public double iEInv2;
    public double wEInv2;
    public double vSInv2;
    public double iSInv2;
    public double wSInv2;
    public double tDInv2;
    public double tTInv2;
    public String estInv1;
    public double iEInv1;
    public double wEInv1;
    public double vSInv1;
    public double iSInv1;
    public double wSInv1;
    public double tDInv1;
    public double tTInv1;

    // Estados e Medidas da Caixa d'Água e da Bonba do Poço
    public String estCxAzul;
    public String nivCxAzul;
    public String estBomba;
    public String estDjBoia;
    public String estDjBomba;
    public int tmpBombaLig;
}
