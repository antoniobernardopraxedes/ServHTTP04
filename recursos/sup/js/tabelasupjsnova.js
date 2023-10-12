    //******************************************************************************************************************
    //                                          Programa Principal Javascript                                          *
    //******************************************************************************************************************
    //
    ContAtualAuto = 0;
    ModoComando = "Remoto";
		var ErroSrv = 0;
    loadXMLDoc("local001.xml");
    setInterval(loadXMLDoc, 3000, "local001.xml");

    //******************************************************************************************************************
    // Nome da Funcao Javascript: loadXMLDoc()                                                                         *
    // Função: solicita ao Servidor um Recurso pelo Metodo GET. A resposta do servidor eh sempre o arquivo XML         *
    //          com o valor atualizado de todas as variaveis de supervisao                                             *
    // Entrada: String com o nome do Recurso. Se nao for inserida a string com o nome do recurso (valor = undefined),  *
    //          solicita apenas a atualizacao dos valores das variaveis.                                               *
    //******************************************************************************************************************
    //
    function loadXMLDoc(recurso) {

		  var Metodo = 0;
      if ((recurso.charAt(0) == "c") && (recurso.length == 8)) {
        Metodo = 1;
      }

  	  var xhttp = new XMLHttpRequest();

  	  if (Metodo === 0) {
        xhttp.open("GET", recurso, false);
      }
      if (Metodo == 1) {
        xhttp.open("POST", recurso, false);
      }

      try {
        xhttp.send();
		    if (xhttp.status == 200)  { // Se foi lido corretamente um arquivo HTTP formato XML,
          document.getElementById("comsrv").innerHTML = "Normal";
          document.getElementById("comsrv").style.color = "blue";
          var xmlRec = xhttp.responseXML;
		      CarregaVariaveis_GERAL(xmlRec);
		      CarregaVariaveis_AGUA(xmlRec);
		      CarregaVariaveis_GERCONS(xmlRec);
		      CarregaVariaveis_INV(xmlRec);
          CarregaVariaveis_AQ(xmlRec);
					CarregaVariaveis_Kron(xmlRec);
		      document.getElementById("erro").innerHTML = " ";

				}
        else {
          //document.getElementById("comsrv").innerHTML = "Falha";
          //document.getElementById("comsrv").style.color = "red";
          CarregaVariaveis_GERAL_Falha();
          CarregaVariaveis_AGUA_Falha();
          CarregaVariaveis_GERCONS_Falha();
          CarregaVariaveis_INV_Falha();
          CarregaVariaveis_AQ_Falha();
          CarregaVariaveis_Kron_Falha();
        }
      } catch(err) {
        document.getElementById("comsrv").innerHTML = "Falha";
        document.getElementById("comsrv").style.color = "red";
        document.getElementById("erro").innerHTML = err;
				CarregaVariaveis_GERAL_Falha();
        CarregaVariaveis_AGUA_Falha();
        CarregaVariaveis_GERCONS_Falha();
        CarregaVariaveis_INV_Falha();
        CarregaVariaveis_AQ_Falha();
        CarregaVariaveis_Kron_Falha();
      }
    }


    //******************************************************************************************************************
    // Nome da Funcao Javascript: CarregaVariaveis_GERAL                                                               *
    // Função: carrega na tabela HTML as variaveis de supervisao da seção GERAL lidas do arquivo XML                   *
    // Entrada: variavel com o arquivo XML recebido do Servidor                                                        *
    //******************************************************************************************************************
    //
    function CarregaVariaveis_GERAL(ArqVarXML) {

			var i = 0;
      var geral = ArqVarXML.getElementsByTagName("GERAL");

      valor = geral[i].getElementsByTagName("COMCNV")[0].childNodes[0].nodeValue;

      valor = geral[i].getElementsByTagName("COMCNC")[0].childNodes[0].nodeValue;
      document.getElementById("comcnc").innerHTML = valor;
      document.getElementById("comcnc").style.color = CorFonte1(valor);

      valor = geral[i].getElementsByTagName("COMUTR")[0].childNodes[0].nodeValue;
      document.getElementById("comutr").innerHTML = valor;
      document.getElementById("comutr").style.color = CorFonte1(valor);

      valor = geral[i].getElementsByTagName("COMCC1")[0].childNodes[0].nodeValue;
      document.getElementById("comcc1").innerHTML = valor;
      document.getElementById("comcc1").style.color = CorFonte1(valor);

      valor = geral[i].getElementsByTagName("COMCC2")[0].childNodes[0].nodeValue;
      document.getElementById("comcc2").innerHTML = valor;
      document.getElementById("comcc2").style.color = CorFonte1(valor);

      valor = geral[i].getElementsByTagName("CLK")[0].childNodes[0].nodeValue;
      document.getElementById("clk").innerHTML = valor;
      valor = geral[i].getElementsByTagName("DATA")[0].childNodes[0].nodeValue;
      document.getElementById("data").innerHTML = valor;

      valor = geral[i].getElementsByTagName("CMDEX")[0].childNodes[0].nodeValue;
      document.getElementById("cmdex").innerHTML = valor;
      if (valor.charAt(0) == "F") {
        document.getElementById("cmdex").style.color = "red";
      }
      else {
        document.getElementById("cmdex").style.color = "blue";
      }

      valor = geral[i].getElementsByTagName("MDOP")[0].childNodes[0].nodeValue;
      document.getElementById("mdop").innerHTML = valor;
      ModoComando = geral[i].getElementsByTagName("MDCOM")[0].childNodes[0].nodeValue;
      document.getElementById("mdcom").innerHTML = ModoComando;
      //valor = geral[i].getElementsByTagName("MDCT1")[0].childNodes[0].nodeValue;
      //document.getElementById("mdct1").innerHTML = valor;
      //valor = geral[i].getElementsByTagName("MDCT234")[0].childNodes[0].nodeValue;
      //document.getElementById("mdct234").innerHTML = valor;
      valor = geral[i].getElementsByTagName("ENCG1")[0].childNodes[0].nodeValue;
      document.getElementById("encg1").innerHTML = valor;
      valor = geral[i].getElementsByTagName("ENCG2")[0].childNodes[0].nodeValue;
      document.getElementById("encg2").innerHTML = valor;
      valor = geral[i].getElementsByTagName("ENCG3")[0].childNodes[0].nodeValue;
      document.getElementById("encg3").innerHTML = valor;
      //valor = geral[i].getElementsByTagName("ICG3")[0].childNodes[0].nodeValue;
      //document.getElementById("icg3").innerHTML = valor;
      valor = geral[i].getElementsByTagName("VBAT")[0].childNodes[0].nodeValue;
      document.getElementById("vbat").innerHTML = valor + " V";
      valor = geral[i].getElementsByTagName("VREDE")[0].childNodes[0].nodeValue;
      document.getElementById("vrede").innerHTML = valor + " V";
      valor = geral[i].getElementsByTagName("ESTVRD")[0].childNodes[0].nodeValue;
      document.getElementById("estvrd").innerHTML = valor;
      //valor = geral[i].getElementsByTagName("TBAT")[0].childNodes[0].nodeValue;
      //document.getElementById("tbat").innerHTML = valor;
      //valor = geral[i].getElementsByTagName("SDBAT")[0].childNodes[0].nodeValue;
      //document.getElementById("sdbat").innerHTML = valor;

    }

    //******************************************************************************************************************
    // Nome da Funcao Javascript: CarregaVariaveis_GERAL_Falha                                                         *
    // Função: em caso de falha na leitura da página HTML do servidor, carrega nas variaveis da tabela HTML a          *
    //         indicação de falha: -------                                                                             *
    // Entrada: não tem                                                                                                *
    //******************************************************************************************************************
    //
    function CarregaVariaveis_GERAL_Falha() {

      valor = "-------";
      document.getElementById("comcnc").innerHTML = valor;
      document.getElementById("comcnc").style.color = valor;
      document.getElementById("comutr").innerHTML = valor;
      document.getElementById("comcc1").innerHTML = valor;
      document.getElementById("comcc2").innerHTML = valor;
      document.getElementById("clk").innerHTML = valor;
      document.getElementById("data").innerHTML = valor;
      document.getElementById("cmdex").innerHTML = valor;
      document.getElementById("mdop").innerHTML = valor;
      document.getElementById("mdcom").innerHTML = valor;
      document.getElementById("encg1").innerHTML = valor;
      document.getElementById("encg2").innerHTML = valor;
      document.getElementById("encg3").innerHTML = valor;
      //document.getElementById("icg3").innerHTML = valor;
      document.getElementById("vbat").innerHTML = valor;
      document.getElementById("vrede").innerHTML = valor;
      document.getElementById("estvrd").innerHTML = valor;
      //document.getElementById("tbat").innerHTML = valor;

    }


    //******************************************************************************************************************
    // Nome da Funcao Javascript: CarregaVariaveis_AGUA                                                                *
    // Função: carrega na tabela HTML as variaveis de supervisao da seção GERAL lidas do arquivo XML                   *
    // Entrada: variavel com o arquivo XML recebido do Servidor                                                        *
    //******************************************************************************************************************
    //
    function CarregaVariaveis_AGUA(ArqVarXML) {
      var i = 0;
      var agua = ArqVarXML.getElementsByTagName("AGUA");
      valor = agua[i].getElementsByTagName("ESTCXAZ")[0].childNodes[0].nodeValue;
      document.getElementById("estcxaz").innerHTML = valor;
      valor = agua[i].getElementsByTagName("NIVCXAZ")[0].childNodes[0].nodeValue;
      document.getElementById("nivcxaz").innerHTML = valor;
      valor = agua[i].getElementsByTagName("ESTBMB")[0].childNodes[0].nodeValue;
      document.getElementById("estbmb").innerHTML = valor;
      valor = agua[i].getElementsByTagName("ESTDJB")[0].childNodes[0].nodeValue;
      document.getElementById("estdjb").innerHTML = valor;
      valor = agua[i].getElementsByTagName("ESTDJRB")[0].childNodes[0].nodeValue;
      document.getElementById("estdjrb").innerHTML = valor;
      valor = agua[i].getElementsByTagName("ENBMB")[0].childNodes[0].nodeValue;
      document.getElementById("encg4").innerHTML = valor;
      valor = agua[i].getElementsByTagName("TMPBL")[0].childNodes[0].nodeValue;
      document.getElementById("tmpbl").innerHTML = valor;
      valor = agua[i].getElementsByTagName("VZBMB")[0].childNodes[0].nodeValue;
      document.getElementById("vzbmb").innerHTML = valor;

    }

    //******************************************************************************************************************
    // Nome da Funcao Javascript: CarregaVariaveis_AGUA_Falha                                                          *
    // Função: em caso de falha na leitura da página HTML do servidor, carrega nas variaveis da tabela HTML a          *
    //         indicação de falha: -------                                                                             *
    // Entrada: não tem                                                                                                *
    //******************************************************************************************************************
    //
    function CarregaVariaveis_AGUA_Falha() {
      valor = "-------";
      document.getElementById("estcxaz").innerHTML = valor;
      document.getElementById("nivcxaz").innerHTML = valor;
      document.getElementById("estbmb").innerHTML = valor;
      document.getElementById("estdjb").innerHTML = valor;
      document.getElementById("estdjrb").innerHTML = valor;
      document.getElementById("encg4").innerHTML = valor;
      document.getElementById("tmpbl").innerHTML = valor;
      document.getElementById("vzbmb").innerHTML = valor;
    }

    //******************************************************************************************************************
    // Nome da Funcao Javascript: CarregaVariaveis_GERCONS                                                             *
    // Função: carrega na tabela HTML as variaveis de supervisao da seção GERCONS lidas do arquivo XML                 *
    // Entrada: variavel com o arquivo XML recebido do Servidor                                                        *
    //******************************************************************************************************************
    //
    function CarregaVariaveis_GERCONS(ArqVarXML) {
      var i = 0;
      var gercons = ArqVarXML.getElementsByTagName("GERCONS");

      // Controlador de Carga 1 (CC1)
      valor = gercons[i].getElementsByTagName("VP12")[0].childNodes[0].nodeValue;
      document.getElementById("vp12").innerHTML = valor + " V";
      valor = gercons[i].getElementsByTagName("IS12")[0].childNodes[0].nodeValue;
      document.getElementById("is12").innerHTML = valor + " A";
      valor = gercons[i].getElementsByTagName("VBAT1")[0].childNodes[0].nodeValue;
      document.getElementById("vbat1").innerHTML = valor + " V";
      valor = gercons[i].getElementsByTagName("ISCC1")[0].childNodes[0].nodeValue;
      document.getElementById("iscc1").innerHTML = valor + " A";
      valor = gercons[i].getElementsByTagName("WSCC1")[0].childNodes[0].nodeValue;
      document.getElementById("wscc1").innerHTML = valor + " W";
      valor = gercons[i].getElementsByTagName("TBAT1")[0].childNodes[0].nodeValue;
      document.getElementById("tbat1").innerHTML = valor + "\u00BAC";

      // Controlador de Carga 2 (CC2)
      valor = gercons[i].getElementsByTagName("VP34")[0].childNodes[0].nodeValue;
      document.getElementById("vp34").innerHTML = valor + " V";
      valor = gercons[i].getElementsByTagName("IS34")[0].childNodes[0].nodeValue;
      document.getElementById("is34").innerHTML = valor + " A";
      valor = gercons[i].getElementsByTagName("VBAT2")[0].childNodes[0].nodeValue;
      document.getElementById("vbat2").innerHTML = valor + " V";
      valor = gercons[i].getElementsByTagName("ISCC2")[0].childNodes[0].nodeValue;
      document.getElementById("iscc2").innerHTML = valor + " A";
      valor = gercons[i].getElementsByTagName("WSCC2")[0].childNodes[0].nodeValue;
      document.getElementById("wscc2").innerHTML = valor + " W";
      valor = gercons[i].getElementsByTagName("TBAT2")[0].childNodes[0].nodeValue;
      document.getElementById("tbat2").innerHTML = valor + "\u00BAC";

      // Geração e Consumo Totais e Cargas CC
      valor = gercons[i].getElementsByTagName("ITOTCG")[0].childNodes[0].nodeValue;
      document.getElementById("itotcg").innerHTML = valor + " A";
      valor = gercons[i].getElementsByTagName("WTOTCG")[0].childNodes[0].nodeValue;
      document.getElementById("wtotcg").innerHTML = valor + " W";
      valor = gercons[i].getElementsByTagName("ESTFT1")[0].childNodes[0].nodeValue;
      document.getElementById("estft1").innerHTML = valor;
      valor = gercons[i].getElementsByTagName("ICIRCC")[0].childNodes[0].nodeValue;
      document.getElementById("icircc").innerHTML = valor + " A";
      valor = gercons[i].getElementsByTagName("WCIRCC")[0].childNodes[0].nodeValue;
      document.getElementById("wcircc").innerHTML = valor + " W";
    }

    //******************************************************************************************************************
    // Nome da Funcao Javascript: CarregaVariaveis_GERCONS_Falha                                                       *
    // Função: em caso de falha na leitura da página HTML do servidor, carrega nas variaveis da tabela HTML a          *
    //         indicação de falha: -------                                                                             *
    // Entrada: não tem                                                                                                *
    //******************************************************************************************************************
    //
    function CarregaVariaveis_GERCONS_Falha() {

      valor = "-------";
      document.getElementById("vp12").innerHTML = valor;
      document.getElementById("is12").innerHTML = valor;
      document.getElementById("vbat1").innerHTML = valor;
      document.getElementById("iscc1").innerHTML = valor;
      document.getElementById("wscc1").innerHTML = valor;
      document.getElementById("tbat1").innerHTML = valor;
      document.getElementById("vp34").innerHTML = valor;
      document.getElementById("is34").innerHTML = valor;
      document.getElementById("iscc2").innerHTML = valor;
      document.getElementById("vbat2").innerHTML = valor;
      document.getElementById("wscc2").innerHTML = valor;
      document.getElementById("tbat2").innerHTML = valor;
      document.getElementById("itotcg").innerHTML = valor;
      document.getElementById("wtotcg").innerHTML = valor;
      document.getElementById("estft1").innerHTML = valor;
      document.getElementById("icircc").innerHTML = valor;
      document.getElementById("wcircc").innerHTML = valor;

    }

    //******************************************************************************************************************
    // Nome da Funcao Javascript: CarregaVariaveis_INV                                                                 *
    // Função: carrega na tabela HTML as variaveis de supervisao da seção INV lidas do arquivo XML                     *
    // Entrada: variavel com o arquivo XML recebido do Servidor                                                        *
    //******************************************************************************************************************
    //
    function CarregaVariaveis_INV(ArqVarXML) {
      var i = 0;
      var inv = ArqVarXML.getElementsByTagName("INV");

          valor = inv[i].getElementsByTagName("ESTIV2")[0].childNodes[0].nodeValue;
          document.getElementById("estiv2").innerHTML = valor;
          valor = inv[i].getElementsByTagName("WEIV2")[0].childNodes[0].nodeValue;
          document.getElementById("weiv2").innerHTML = valor + " W";
          valor = inv[i].getElementsByTagName("VSIV2")[0].childNodes[0].nodeValue;
          document.getElementById("vsiv2").innerHTML = valor + " V";
          valor = inv[i].getElementsByTagName("TDIV2")[0].childNodes[0].nodeValue;
          document.getElementById("tdiv2").innerHTML = valor + "\u00BAC";
          valor = inv[i].getElementsByTagName("TTIV2")[0].childNodes[0].nodeValue;
          document.getElementById("ttiv2").innerHTML = valor + "\u00BAC";
          valor = inv[i].getElementsByTagName("ESTIV1")[0].childNodes[0].nodeValue;
          document.getElementById("estiv1").innerHTML = valor;
          valor = inv[i].getElementsByTagName("WEIV1")[0].childNodes[0].nodeValue;
          document.getElementById("weiv1").innerHTML = valor + " W";
          valor = inv[i].getElementsByTagName("VSIV1")[0].childNodes[0].nodeValue;
          document.getElementById("vsiv1").innerHTML = valor + " V";
          valor = inv[i].getElementsByTagName("TDIV1")[0].childNodes[0].nodeValue;
          document.getElementById("tdiv1").innerHTML = valor + "\u00BAC";
          valor = inv[i].getElementsByTagName("TTIV1")[0].childNodes[0].nodeValue;
          document.getElementById("ttiv1").innerHTML = valor + "\u00BAC";
    }

    //******************************************************************************************************************
    // Nome da Funcao Javascript: CarregaVariaveis_INV_Falha                                                           *
    // Função: em caso de falha na leitura da página HTML do servidor, carrega nas variaveis da tabela HTML a          *
    //         indicação de falha: -------                                                                             *
    // Entrada: não tem                                                                                                *
    //******************************************************************************************************************
    //
    function CarregaVariaveis_INV_Falha() {
          valor = "-------";
          document.getElementById("estiv2").innerHTML = valor;
          document.getElementById("weiv2").innerHTML = valor;
          document.getElementById("vsiv2").innerHTML = valor;
          document.getElementById("tdiv2").innerHTML = valor;
          document.getElementById("ttiv2").innerHTML = valor;
          document.getElementById("estiv1").innerHTML = valor;
          document.getElementById("weiv1").innerHTML = valor;
          document.getElementById("vsiv1").innerHTML = valor;
          document.getElementById("tdiv1").innerHTML = valor;
          document.getElementById("ttiv1").innerHTML = valor;
    }

  //********************************************************************************************************************
  // Nome da Funcao Javascript: CarregaVariaveis_AQ                                                                    *
  //                                                                                                                   *
  // Função: solicita o arquivo XML com as variaveis de supervisao de agua que                                         *
  // Entrada: variavel com o arquivo XML recebido do Servidor                                                          *
  //********************************************************************************************************************
  //
  function CarregaVariaveis_AQ(ArqVarXML) {
    var i = 0;
    var aguaq = ArqVarXML.getElementsByTagName("AGUAQ");
    valor = aguaq[i].getElementsByTagName("ESTUAQ")[0].childNodes[0].nodeValue;
    document.getElementById("estuaq").innerHTML = valor;
    document.getElementById("estuaq").style.color = CorFonte1(valor);
    valor = aguaq[i].getElementsByTagName("ESTBAQ")[0].childNodes[0].nodeValue;
    document.getElementById("estbaq").innerHTML = valor;
    valor = aguaq[i].getElementsByTagName("ESTAQ")[0].childNodes[0].nodeValue;
    document.getElementById("estaq").innerHTML = valor;
    valor = aguaq[i].getElementsByTagName("TEMPBL")[0].childNodes[0].nodeValue;
    document.getElementById("tempbl").innerHTML = valor + " \u00BAC";
    valor = aguaq[i].getElementsByTagName("TEMPPS")[0].childNodes[0].nodeValue;
    document.getElementById("tempps").innerHTML = valor + " \u00BAC";
    valor = aguaq[i].getElementsByTagName("TMPBL")[0].childNodes[0].nodeValue;
    document.getElementById("tmpbaql").innerHTML = valor;

  }

  //********************************************************************************************************************
  // Nome da Funcao Javascript: CarregaVariaveis_AQ_Falha                                                              *
  //                                                                                                                   *
  // Função: coloca os valores de falha de servidor nas variáveis                                                      *
  // Entrada: não tem                                                                                                  *
  //********************************************************************************************************************
  //
  function CarregaVariaveis_AQ_Falha() {
    valor = "-------";
    document.getElementById("estuaq").innerHTML = valor;
    document.getElementById("estbaq").innerHTML = valor;
    document.getElementById("estaq").innerHTML = valor;
    document.getElementById("tempbl").innerHTML = valor;
    document.getElementById("tempps").innerHTML = valor;
    document.getElementById("tmpbaql").innerHTML = valor;
  }

  //********************************************************************************************************************
  // Nome da Funcao Javascript: CarregaVariaveis_Kron                                                                  *
  // Função: carrega na tabela HTML as variaveis de supervisao da seção GERAL lidas do arquivo XML                     *
  // Entrada: variavel com o arquivo XML recebido do Servidor                                                          *
  //********************************************************************************************************************
  //
  function CarregaVariaveis_Kron(ArqVarXML) {
    var i = 0;
    var kron = ArqVarXML.getElementsByTagName("KRON");
    estcconcard = kron[i].getElementsByTagName("ESTCCA")[0].childNodes[0].nodeValue;
    document.getElementById("estcconcard").innerHTML = estcconcard;
    document.getElementById("estcconcard").style.color = CorFonte1(estcconcard);
    //valor = kron[i].getElementsByTagName("CORRENTEI")[0].childNodes[0].nodeValue;
    //document.getElementById("correntei").innerHTML = valor;
    valor = kron[i].getElementsByTagName("POTENCIAI")[0].childNodes[0].nodeValue;
    document.getElementById("potenciai").innerHTML = valor + " W";
    estck = kron[i].getElementsByTagName("ESTCK")[0].childNodes[0].nodeValue;
    valor = kron[i].getElementsByTagName("TENSAOK")[0].childNodes[0].nodeValue;
    document.getElementById("tensaok").innerHTML = valor + " V";
    //valor = kron[i].getElementsByTagName("CORRENTEK")[0].childNodes[0].nodeValue;
    //document.getElementById("correntek").innerHTML = valor;
    valor = kron[i].getElementsByTagName("POTATIVK")[0].childNodes[0].nodeValue;
    document.getElementById("potativk").innerHTML = valor + " W";
    valor = kron[i].getElementsByTagName("FATPOTK")[0].childNodes[0].nodeValue;
    document.getElementById("fatpotk").innerHTML = valor;
    valor = kron[i].getElementsByTagName("ENERGIAK")[0].childNodes[0].nodeValue;
    document.getElementById("energiak").innerHTML = valor + " KWh";
    valor = kron[i].getElementsByTagName("ENERGIANK")[0].childNodes[0].nodeValue;
    document.getElementById("energiank").innerHTML = valor + " KWh";
  }

  //******************************************************************************************************************
  // Nome da Funcao Javascript: CarregaVariaveis_Kron_Falha                                                          *
  // Função: em caso de falha na leitura da página HTML do servidor, carrega nas variaveis da tabela HTML a          *
  //         indicação de falha: -------                                                                             *
  // Entrada: não tem                                                                                                *
  //******************************************************************************************************************
  //
  function CarregaVariaveis_Kron_Falha() {
    valor = "-------";
    document.getElementById("estcconcard").innerHTML = valor;
    document.getElementById("potenciai").innerHTML = valor;
    document.getElementById("tensaok").innerHTML = valor;
    document.getElementById("potativk").innerHTML = valor;
    document.getElementById("fatpotk").innerHTML = valor;
    document.getElementById("energiak").innerHTML = valor;
    document.getElementById("energiank").innerHTML = valor;
  }


  //******************************************************************************************************************
  // Nome da Funcao Javascript: CorFonte1                                                                            *
  // Função: muda a cor da fonte do texto: Normal: azul ou Alerta: vermelho                                          *
  // Entrada: String "Normal" muda o texto para azul. Caso contrário muda o texto para vermelho                      *
  //******************************************************************************************************************
  //
  function CorFonte1(val) {
    if (val == "Normal") {
      return("blue");
    }
    else {
      return("red");
    }
  }
