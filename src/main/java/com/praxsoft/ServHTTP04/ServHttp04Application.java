package com.praxsoft.ServHTTP04;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import static com.praxsoft.ServHTTP04.Dados.*;
import static com.praxsoft.ServHTTP04.SupService.*;

@SpringBootApplication
public class ServHttp04Application implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(ServHttp04Application.class, args);
	}

	@Override
	public void run(String... args) throws Exception {

		LeConfiguracao();
		ImprimeLinhaBranco(true);
		MostraDadosConfiguracao();
		MostraUsuarios();

		String broker   = "tcp://200.98.140.180:1883";
		String username = "usuario2";
		String password = "senha2";
		int qos = 0;

		if (isOpLocal()) {
			ImprimeLinhaBranco(true);
			String msg = "Programa Iniciado - Modo Local";
			if (isPublicador()) {
				msg = msg + " - Publicador Habilitado";
			}
			Terminal(msg, true);

			byte DH [] = new byte[6];
			byte Segundo;
			byte SegundoAnterior;
			DH = SupService.LeDataHora();
			Segundo = DH[2];
			SegundoAnterior = Segundo;
			int cont = 0;
			int cont1 = 9;
			boolean fim = false;
			int Periodo = 4;

			while (true) {
				DH = LeDataHora();
				Segundo = DH[2];
				if (Segundo != SegundoAnterior) {
					cont1 = cont1 + 1;
					SegundoAnterior = Segundo;
				}
				if (cont1 >= 10) {
					try {
						setInicio(true);
						cont1 = 0;
						if (isPublicador()) {
							Terminal("Iniciando Publicação MQTT JSON para o Agente com Múltiplos Tópicos", true);
						}

						while (!fim) {
							DH = LeDataHora();
							Segundo = DH[2];
							if (Segundo != SegundoAnterior) {
								cont = cont + 1;
								SegundoAnterior = Segundo;
							}
							if (cont >= Periodo) {
								cont = 0;
								if (valorNumComando() == 0) {
									XML.CarregaVariaveisLocal(incContMsgCoAP(), 0);
								}
								else {
									Terminal("Comando Recebido: " + IdComando(valorNumComando()), true);
									XML.CarregaVariaveisLocal(incContMsgCoAP(), valorNumComando());
									zeraNumComando();
								}

								if (isPublicador()) {
									MQTT.Pub5topicos(broker, "ServLocal01", username, password, qos,
											"DadosUTR", "DadosCC1", "DadosCC2", "DadosCAQ", "DadosCEN",
											XML.getMsgJsonUtr().getBytes(),
											XML.getMsgJsonCc1().getBytes(),
											XML.getMsgJsonCc2().getBytes(),
											XML.getMsgJsonCaq().getBytes(),
											XML.getMsgJsonCen().getBytes());
								}
							}
						}
					} catch(Exception e) {
						Terminal("Erro: " + e, true);
					}
				} // if (cont1 >= 10)
			} // while (true)
		}
		else {
			ImprimeLinhaBranco(true);
			MQTT.IniciaAssinante();
			Terminal("Programa Iniciado - Modo Agente MQTT", true);
		}
	}

}
