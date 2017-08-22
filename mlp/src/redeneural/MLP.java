package redeneural;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class MLP {

	double[][] entradasTreino;
	double[][] saidasTreino;
	double[][] entradasValidacao;
	double[][] saidasValidacao;
	int maxCiclos;
	int qtdEscondidos;
	int qtdEscondidos2;
	double alpha;
	double beta;
	double EMI;
	double EMQ;
	double EMIV;
	double EMQV;
	double EMA;
	double EMAT;
	double EMQT;
	double NEMA;
	double EPA;
	double EPMA;
	ArrayList<ArrayList<Neuronio>> neuronios;
	ArrayList<ArrayList<Double>> erros;//0-erros do treinamento, 1-erros da validacao
	ArrayList<ArrayList<Neuronio>> melhorCaso;
	int qtdAcerto;
	int ciclo;
	double melhorEMQV;
	double melhorEMA;
	double melhorNEMA;
	boolean previsao;
	double EMAV;

	public MLP(double[][] entradasTreino, double[][] saidasTreino, double[][] entradasValidacao, double[][] saidasValidacao, double alpha, double beta, int maxCiclos, int qtdEscondidos, int qtdEscondidos2, boolean previsao) {
		this.entradasTreino = entradasTreino;
		this.saidasTreino = saidasTreino;
		this.entradasValidacao = entradasValidacao;
		this.saidasValidacao = saidasValidacao;
		this.alpha = alpha;
		this.beta = beta;
		this.maxCiclos = maxCiclos;
		this.qtdEscondidos = qtdEscondidos;
		this.qtdEscondidos2 = qtdEscondidos2;
		this.neuronios = new ArrayList<ArrayList<Neuronio>>();
		this.melhorCaso = new ArrayList<ArrayList<Neuronio>>();
		this.EMI = 0.0;
		this.EMQ = 10.0;
		this.melhorEMQV = 10.0;
		this.melhorEMA = 1000.0;
		this.melhorNEMA = 1000.0;
		this.erros = new ArrayList<ArrayList<Double>>();
		this.erros.add(new ArrayList<Double>());//array de erros de treinamento
		this.erros.add(new ArrayList<Double>());//array de erros de validacao
		this.qtdAcerto = 0;
		this.ciclo = 0;
		this.previsao = previsao;
		this.EPA = 0.0;
		this.EPMA = 0.0;
		this.EMA = 1000.0;
		this.EMAT = 1000.0;
		this.NEMA = 190.0;
		this.initBP();
	}
	
	public double getEMA() {
		return EMA;
	}

	public double getEPA() {
		return EPA;
	}

	public double getEPMA() {
		return EPMA;
	}

	public ArrayList<ArrayList<Double>> getErros() {
		return erros;
	}

	//inicio da rede para rodar com BackPropagation
	private void initBP() {
		ArrayList<Neuronio> camadaEntrada = new ArrayList<Neuronio>();
		for (int i = 0; i < this.entradasTreino[0].length; i++) {
			camadaEntrada.add(new Neuronio(0.0));
			camadaEntrada.get(i).gerarPesosAleatorios(this.qtdEscondidos);
		}
		camadaEntrada.add(0, new Neuronio(1.0));//inserindo o Bias
		camadaEntrada.get(0).gerarPesosAleatorios(this.qtdEscondidos);

		ArrayList<Neuronio> camadaSaida = new ArrayList<Neuronio>();
		for (int i = 0; i < this.saidasTreino[0].length; i++) {
			camadaSaida.add(new Neuronio(0.0));//SE FOR NECESSARIO IMPRIMIR OS PESOS EH PRECISO CRIAR NULOS
			camadaSaida.get(i).gerarPesosAleatorios(1);
		}

		ArrayList<Neuronio> camadaEscondida2 = new ArrayList<Neuronio>();
		for (int i = 0; i < this.qtdEscondidos2; i++) {
			camadaEscondida2.add(new Neuronio(0.0));
			camadaEscondida2.get(i).gerarPesosAleatorios(camadaSaida.size());
		}
		camadaEscondida2.add(0, new Neuronio(1.0));//inserindo o Bias
		camadaEscondida2.get(0).gerarPesosAleatorios(camadaSaida.size());

		ArrayList<Neuronio> camadaEscondida = new ArrayList<Neuronio>();
		for (int i = 0; i < this.qtdEscondidos; i++) {
			camadaEscondida.add(new Neuronio(0.0));
			camadaEscondida.get(i).gerarPesosAleatorios(this.qtdEscondidos2);
		}
		camadaEscondida.add(0, new Neuronio(1.0));//inserindo o Bias
		camadaEscondida.get(0).gerarPesosAleatorios(this.qtdEscondidos2);

		this.neuronios.add(camadaEntrada);
		this.neuronios.add(camadaEscondida);
		this.neuronios.add(camadaEscondida2);
		this.neuronios.add(camadaSaida);
		this.definirMelhorCaso();
		this.treinarBP();
	}

	private void definirMelhorCaso() {
		this.melhorCaso = new ArrayList<ArrayList<Neuronio>>();
		this.melhorEMQV = this.EMQ;
		for (int i = 0; i < this.neuronios.size(); i++) {
			this.melhorCaso.add(new ArrayList<Neuronio>());
			for (int j = 0; j < this.neuronios.get(i).size(); j++) {
				this.melhorCaso.get(i).add((Neuronio) this.neuronios.get(i).get(j).copy());
			}
		}
	}

//	private double aplicarFuncaoAtivacao(double x) {//funcao gaussiana
//		double y = 0.0;
//		y = Math.pow(Math.E, -(Math.pow(x, 2))/1);
//		return y;
//	}
//
//	private double derivarFuncaoAtivacao(double y) {//derivada para a funcao gaussiana
//		double retorno = 0.0;
//		retorno = -2 * Math.pow(Math.E, -(Math.pow(y, 2))) * y;
//		return retorno;
//	}
	
	private double aplicarFuncaoAtivacao(double x) {//funcao sigmoidal logistica
		double y = 0.0;
		y = 1 / (1 + (Math.pow(Math.E, -(x))));
		return y;
	}

	private double derivarFuncaoAtivacao(double y) {//derivada para a funcao sigmoidal logistica
		double retorno = 0.0;
		retorno = y * (1 - y);
		return retorno;
	}
	
	private double aplicarFuncaoAtivacaoSaida(double x) {//funcao sigmoidal logistica
		double y = 0.0;
		y = 1 / (1 + (Math.pow(Math.E, -(x))));
		return y;
	}

	private double derivarFuncaoAtivacaoSaida(double y) {//derivada para a funcao sigmoidal logistica
		double retorno = 0.0;
		retorno = y * (1 - y);
		return retorno;
	}
	
//	private double aplicarFuncaoAtivacao(double x) {//funcao tangente hiperb[olica
//		double y = 0.0;
//		y = ((Math.pow(Math.E, x)) - (Math.pow(Math.E, -x))) / ((Math.pow(Math.E, x)) + (Math.pow(Math.E, -x)));
//		return y;
//	}
//
//	private double derivarFuncaoAtivacao(double y) {//derivada para a funcao tangente hiperbolica
//		double retorno = 0.0;
//		retorno = Math.pow((2 / ((Math.pow(Math.E, y)) + (Math.pow(Math.E, -y))) ), 2);
//		return retorno;
//	}
	
	
	private void definirValores(double[] valores, ArrayList<Neuronio> camada) {
		if (valores.length != camada.size()) {
			for (int i = 1; i < camada.size(); i++) {//Comeca em 1 para pular o bias que já está definido com 1.0
				camada.get(i).setValor(valores[i - 1]);
			}
		} else {
			for (int i = 0; i < camada.size(); i++) {
				camada.get(i).setValor(valores[i]);
			}
		}
	}

	private void aplicarFaseFoward(double[] entradas) {

		ArrayList<Neuronio> camadaEntrada = this.neuronios.get(0);
		ArrayList<Neuronio> camadaEscondida = this.neuronios.get(1);
		ArrayList<Neuronio> camadaEscondida2 = this.neuronios.get(2);
		ArrayList<Neuronio> camadaSaida = this.neuronios.get(3);
		double somatorio = 0.0;
		double[] fnet = new double[this.qtdEscondidos];//tamanho de fnet eh o tamanho da camada escondida -1(bias) = this.qtdEscondidos
		double[] fnet2 = new double[this.qtdEscondidos2];//tamanho de fnet eh o tamanho da camada escondida -1(bias) = this.qtdEscondidos
		double[] fnetSaida = new double[camadaSaida.size()];
		Neuronio neuronio;

		this.definirValores(entradas, camadaEntrada);

		//Calcular fnet para a entrada
		for (int i = 0; i < camadaEscondida.size() - 1/*bias*/; i++) {
			somatorio = 0.0;
			for (int j = 0; j < camadaEntrada.size(); j++) {
				neuronio = camadaEntrada.get(j);
				somatorio += (neuronio.getValor() * neuronio.getPesos()[i]);

			}
			fnet[i] = this.aplicarFuncaoAtivacao(somatorio);
		}
		this.definirValores(fnet, camadaEscondida);

		//Calcular fnet para a camada escondida 2
		for (int i = 0; i < camadaEscondida2.size() - 1/*bias*/; i++) {
			somatorio = 0.0;
			for (int j = 0; j < camadaEscondida.size(); j++) {
				neuronio = camadaEscondida.get(j);
				somatorio += (neuronio.getValor() * neuronio.getPesos()[i]);

			}
			fnet2[i] = this.aplicarFuncaoAtivacao(somatorio);
		}
		this.definirValores(fnet2, camadaEscondida2);

		//Calcular fnet para a saida
		for (int m = 0; m < camadaSaida.size(); m++) {
			somatorio = 0.0;
			for (int n = 0; n < camadaEscondida2.size(); n++) {
				neuronio = camadaEscondida2.get(n);
				somatorio += (neuronio.getValor() * neuronio.getPesos()[m]);
			}
			fnetSaida[m] = this.aplicarFuncaoAtivacaoSaida(somatorio);
		}
		this.definirValores(fnetSaida, camadaSaida);

	}

	//TODO: Verificar se o BIAS entra na contabilidade dos deltas
	private void aplicarFaseBackPropagation(double[] desejados) {

		ArrayList<Neuronio> camadaEntrada = this.neuronios.get(0);
		ArrayList<Neuronio> camadaEscondida = this.neuronios.get(1);
		ArrayList<Neuronio> camadaEscondida2 = this.neuronios.get(2);
		ArrayList<Neuronio> camadaSaida = this.neuronios.get(3);
		double[] deltaSaida = new double[camadaSaida.size()];
		double[] deltaEscondida = new double[this.qtdEscondidos];
		double[] deltaEscondida2 = new double[this.qtdEscondidos2];
		double erro = 0.0;
		Neuronio neuronio;
		this.EMI = 0.0;

		//Erro Medio Quadratico para a camada de saida
		for (int i = 0; i < camadaSaida.size(); i++) {
			neuronio = camadaSaida.get(i);
			erro = desejados[i] - neuronio.getValor();
			this.EMI += Math.pow(erro, 2.0);
			deltaSaida[i] = (this.derivarFuncaoAtivacaoSaida(neuronio.getValor()) * erro);
		}
		this.EMQ += this.EMI / camadaSaida.size();

		//Erro Medio Quadratico para a camada de escondida2
		for (int j = 1; j < camadaEscondida2.size(); j++) {
			erro = 0.0;
			neuronio = camadaEscondida2.get(j);
			for (int k = 0; k < camadaSaida.size(); k++) {
				erro += deltaSaida[k] * neuronio.getPesos()[k];
			}
			deltaEscondida2[j - 1] = (this.derivarFuncaoAtivacao(neuronio.getValor()) * erro);
		}

		//Erro Medio Quadratico para a camada de escondida
		for (int j = 1; j < camadaEscondida.size(); j++) {
			erro = 0.0;
			neuronio = camadaEscondida.get(j);
			for (int k = 0; k < camadaEscondida2.size()-1; k++) {
				erro += deltaEscondida2[k] * neuronio.getPesos()[k];
			}
			deltaEscondida[j - 1] = (this.derivarFuncaoAtivacao(neuronio.getValor()) * erro);
		}

		/*AJUSTE DOS PESOS*/
		//AJUSTE CAMADA ESCONDIDA2
		for (int m = 0; m < camadaEscondida2.size(); m++) {
			camadaEscondida2.get(m).atualizarPesos(deltaSaida, this.alpha, this.beta);
		}

		//AJUSTE CAMADA ESCONDIDA
		for (int m = 0; m < camadaEscondida.size(); m++) {
			camadaEscondida.get(m).atualizarPesos(deltaEscondida2, this.alpha, this.beta);
		}

		//AJUSTE CAMADA ENTRADA
		for (int n = 0; n < camadaEntrada.size(); n++) {
			camadaEntrada.get(n).atualizarPesos(deltaEscondida, this.alpha, this.beta);
		}
		/*FIM DE AJUSTE DOS PESOS*/
	}


	private void treinarBP() {
		boolean flagTreino = true;
		boolean flagDiferenca = false;
		boolean flagAumento = false;
		int qtdAumento = 0;
		int qtdCTE = 0;

		while (flagTreino) {
			for (int j = 0; j < this.entradasTreino.length; j++) {
				this.aplicarFaseFoward(this.entradasTreino[j]);
				this.aplicarFaseBackPropagation(this.saidasTreino[j]);
			}
			this.validarTreinamento();
			this.EMQ /= this.saidasTreino.length;
			this.EMQV /= this.saidasValidacao.length;
			this.erros.get(0).add(this.EMQ);
			this.erros.get(1).add(this.EMQV);

			if (this.ciclo > 0) {
				double EMQVAtual = this.erros.get(1).get(this.erros.get(1).size() - 1);
				double EMQVAnterior = this.erros.get(1).get(this.erros.get(1).size() - 2);
				double diferenca = (EMQVAnterior - EMQVAtual);
				diferenca = (diferenca / EMQVAnterior);
				if (Math.abs(diferenca) < 0.001) {
					if (!flagDiferenca) {//faz com que o contador seja zerado no primeiro acontecimento do caso
						qtdCTE = 0;
						flagDiferenca = true;
						//System.out.println("EMQVAtual: " + EMQVAtual + " <> this.melhorEMQV: " + this.melhorEMQV);
						if (EMQVAtual < this.melhorEMQV) {
							this.definirMelhorCaso();
						}
					}
					qtdCTE++;
					if (qtdCTE == 30) {//contara 30 x consecutivas
						//System.out.println("Diferenca <<<<<");
						flagTreino = false;
					}


				} else if (EMQVAtual > EMQVAnterior) {

					if (!flagAumento) {//faz com que o contador seja zerado no primeiro acontecimento do caso
						qtdAumento = 0;
						flagAumento = true;
					}
					qtdAumento++;
					if (qtdAumento == 30) {//contara 30 x consecutivas
						//System.out.println("Aumento <<<<<");
						flagTreino = false;
					}
				} else if (this.ciclo >= this.maxCiclos) {
					//System.out.println("MaxCiclos <<<<<");
					flagTreino = false;
					if (EMQVAtual < this.melhorEMQV) {
						this.definirMelhorCaso();
					}
				} else {
					flagAumento = false;
					flagDiferenca = false;
					if (EMQVAtual < this.melhorEMQV) {
						this.definirMelhorCaso();
					}
				}
			}
			this.EMQ = 0.0;
			this.EMQV = 0.0;
			this.ciclo++;

		}
		//System.out.println("Total de Ciclos: " + this.ciclo);

	}

	private void validarTreinamento() {
		for (int i = 0; i < this.entradasValidacao.length; i++) {
			this.aplicarFaseFoward(this.entradasValidacao[i]);
			this.calcularErroValidacao(this.saidasValidacao[i]);
		}


	}

	private void calcularErroValidacao(double[] desejados) {
		double erro = 0.0;
		double desejado = 0.0;
		double calculado = 0.0;
		this.EMIV = 0.0;
		for (int i = 0; i < desejados.length; i++) {
			desejado = desejados[i];
			calculado = this.neuronios.get(this.neuronios.size() - 1).get(i).getValor();
			erro = desejado - calculado;
			this.EMIV += Math.pow(erro, 2.0);
		}
		this.EMQV += this.EMIV / desejados.length;
	}

	public void testar(double[][] entradasTeste, double[][] saidasTeste) {
		Neuronio neuroTemp;
		Neuronio melhorTemp;
		for (int i = 0; i < this.neuronios.size(); i++) {
			for (int j = 0; j < this.neuronios.get(i).size(); j++) {
				neuroTemp = this.neuronios.get(i).get(j);
				melhorTemp = this.melhorCaso.get(i).get(j);
				neuroTemp.setPesos(melhorTemp.getPesos());
				neuroTemp.setPesosAntigos(melhorTemp.getPesosAntigos());
				neuroTemp.setValor(melhorTemp.getValor());
			}
		}

		//System.out.println("****Teste****");
		double[][] calculadas = new double[saidasTeste.length][saidasTeste[0].length];
		for (int i = 0; i < entradasTeste.length; i++) {
			this.aplicarFaseFoward(entradasTeste[i]);
			if (!this.previsao) {
				calculadas[i] = this.retornarSaidasClassificacao();
				this.verificarClassificacao(calculadas[i], saidasTeste[i]);
			} else {
				calculadas[i] = this.retornarSaidasPrevisao();
			}
		}
		if (!this.previsao) {
			System.out.println("indice Acerto: " + this.qtdAcerto);
			System.out.println("Total Exemplos: " + entradasTeste.length);
			double porcentagem = ((double) this.qtdAcerto / (double) entradasTeste.length);
			System.out.println("Acerto(%): " + (porcentagem));
		} else {
			this.verificarPrevisao(calculadas, saidasTeste);
		}


	}

	private double[] retornarSaidasPrevisao() {
		ArrayList<Neuronio> camadaSaida = this.neuronios.get(3);
		double[] saidas = new double[camadaSaida.size()];
		for (int i = 0; i < camadaSaida.size(); i++) {
			saidas[i] = camadaSaida.get(i).getValor();
		}
		return saidas;
	}

	private void verificarPrevisao(double[][] calculadas, double[][] desejadas) {
		this.EPA = 0.0;
		this.EMA = 0.0;
		for (int i = 0; i < calculadas.length; i++) {
			imprimirArray(calculadas[i], desejadas[i]);
			for (int j = 0; j < calculadas[0].length; j++) {
				//System.out.println("desejado: "+desejadas[i][j]+" <> calculado: "+calculadas[i][j]);
				this.EMA +=  Math.abs(desejadas[i][j] - calculadas[i][j]);
				this.EPA += (Math.abs(desejadas[i][j] - calculadas[i][j])) / desejadas[i][j];
			}

		}
		this.EMA /= calculadas.length; 
		System.out.println("EMA Previsao: " + this.EMA);
		this.EPMA = (this.EPA * 100)/ calculadas.length;
		//System.out.println("EPMA Previsao: " + this.EPMA);
	}

	private double[] retornarSaidasClassificacao() {
		ArrayList<Neuronio> camadaSaida = this.neuronios.get(2);
		double[] saidas = new double[camadaSaida.size()];
		int idMax = 0;
		double temp = camadaSaida.get(0).getValor();
		if (camadaSaida.size() > 1) {
			for (int i = 1; i < camadaSaida.size(); i++) {
				if (camadaSaida.get(i).getValor() > temp) {
					temp = camadaSaida.get(i).getValor();
					idMax = i;
				}
			}
			for (int j = 0; j < saidas.length; j++) {
				if (j == idMax) {//BUG QUANDO NAO HOUVER NORMALIZACAO DEVE-SE MUDAR AS SAIDAS PARA 1.0 E 0.0
					saidas[j] = 0.9;
				} else {
					saidas[j] = 0.1;
				}
			}
		} else {
			if (temp < 0.4) {
				saidas[0] = 0.1;
			} else {
				saidas[0] = 0.9;
			}
		}
		return saidas;
	}

	private void verificarClassificacao(double[] calculado, double[] desejado) {
		int temp = 0;
		for (int i = 0; i < calculado.length; i++) {
			if (calculado[i] == desejado[i])
				temp++;
		}
	}

	private double arredondar(double valor) {
		DecimalFormat formato = new DecimalFormat("0.0000000000");
		Double retorno = Double.parseDouble((formato.format(valor)).replace(",", "."));
		return retorno;
	}

	private void imprimirArray(double[] calculado, double[] desejado) {
		int temp = 0;
		String impressaoCalculado = "calculado: ";
		String impressaoDesejado = "desejado : ";
		for (int i = 0; i < calculado.length; i++) {
			impressaoCalculado += calculado[i] + ",";
			impressaoDesejado += desejado[i] + ",";
		}
		//System.out.println(impressaoCalculado);
		//System.out.println(impressaoDesejado);

	}

	private void imprimirPesos() {
		for (int i = 0; i < this.neuronios.size() - 1; i++) {
			for (int j = 0; j < this.neuronios.get(i).size(); j++) {
				//System.out.println("neuronio[" + i + "][" + j + "]: " + this.neuronios.get(i).get(j).imprimirPesos());
			}
		}
	}
}
