package redeneural;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import dados.Bases;
import dados.Dados;
import graficos.Grafico;


public class Main {

	public static void main(String[] args) {
		Dados dados = null;
		MLP mlp = null;
		single_run(mlp, dados);
		//statistical_run(mlp, dados);
	}

	//BONS VALORES!
	private static void single_run(MLP mlp, Dados dados) {
		dados = new Dados(Bases.PERIL_GAMMA);
		mlp = new MLP(dados.getEntradasTreinamento().getArray(),//array de entradas de treinamento
				dados.getSaidasTreinamento().getArray(),//array de saidas de treinamento
				dados.getEntradasValidacao().getArray(),//array de entradas de validacao
				dados.getSaidasValidacao().getArray(),//array de saidas de validacao
				0.04,//valor de alpha
				0.26,//valor de beta
				600,//maximo de ciclos
				52,//quatidade de neuronios escondida
				13,//quatidade de neuronios escondida
				true);//booleano para definir se é previsao(true) ou classificacao(false)
		mlp.testar(dados.getEntradasTeste().getArray(), dados.getSaidasTeste().getArray());
		//System.out.println("erros: " + mlp.getErros());
		//Grafico grafico = new Grafico(mlp.getErros());
		/*
		 * summary(mlp) => PERIL
   Min. 1st Qu.  Median    Mean 3rd Qu.    Max. 
 0.1390  0.1434  0.1454  0.1453  0.1474  0.1503 
    +- 50% EPMA
    
      PERIL_GAMMA
      Min. 1st Qu.  Median    Mean 3rd Qu.    Max. 
0.09649 0.10040 0.10180 0.10150 0.10310 0.10540
     +- 15% EPMA 
		 */
	}

	private static void statistical_run(MLP mlp, Dados dados) {
		/*Teste Estatistico*/
		int totalTestes = 30;
		String nameFErrors = "mlp.errors";
		String nameFEma = "mlp.ema";
		int alpha=5, beta=1, qtdeNeuronios = 14;
		//for (int alpha = 5; alpha < 10; alpha++) {
			//for (int beta = 1; beta < 6; beta++) {
				//for (int qtdeNeuronios = 10; qtdeNeuronios <= 20; qtdeNeuronios+=10) {
					StringBuilder sb = new StringBuilder();
					sb.append("iteracao erroTreinamento erroValidacao\n");
					//String nameFErrors = "mlp_" +alpha+"_"+beta+"_"+qtdeNeuronios+".errors";
					//String nameFEma = "mlp_" +alpha+"_"+beta+"_"+qtdeNeuronios+".ema";
					File ferrors = new File(nameFErrors);
					StringBuilder sb1 = new StringBuilder();
					sb1.append("amostra melhorEMA\n");
					File femas = new File(nameFEma);
					FileWriter fw, fw1 = null;
					for (int i = 0; i < totalTestes; i++) {
						dados = new Dados(Bases.PERIL_GAMMA);
						mlp = new MLP(dados.getEntradasTreinamento().getArray(),//array de entradas de treinamento
								dados.getSaidasTreinamento().getArray(),//array de saidas de treinamento
								dados.getEntradasValidacao().getArray(),//array de entradas de validacao
								dados.getSaidasValidacao().getArray(),//array de saidas de validacao
								alpha*0.1,//valor de alpha
								beta*0.1,//valor de beta
								600,//maximo de ciclos
								qtdeNeuronios,//quatidade de neuronios escondida
								qtdeNeuronios/2,
								true);//booleano para definir se é previsao(true) ou classificacao(false)
						mlp.testar(dados.getEntradasTeste().getArray(), dados.getSaidasTeste().getArray());
						escreverErros(sb, mlp.getErros());
						sb1.append(i+ " ");
						escreverEMAs(sb1, mlp.melhorEMA);
						mlp = null;
					}

					try {
						fw = new FileWriter(ferrors,true);
						fw.write(sb.toString());
						fw.close();
						fw1 = new FileWriter(femas,true);
						fw1.write(sb1.toString());
						fw1.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				//}
			//}
		//}
		/*Fim Teste Estatistico*/
	}

	private static void escreverEMAs(StringBuilder sb1, double melhorEMA) {
		sb1.append(melhorEMA + "\n");
	}

	private static void escreverErros(StringBuilder sb, ArrayList<ArrayList<Double>> erros) {
		int i = 0;
		for (ArrayList<Double> arrayList : erros) {
			sb.append(i+ " " +arrayList.get(0)+ " " + arrayList.get(1) + "\n");
			i++;
		}
	}
}
