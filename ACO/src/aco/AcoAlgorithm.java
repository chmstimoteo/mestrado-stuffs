
package aco;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

public class AcoAlgorithm {

	private ArrayList<Formiga> formigas;
	private Graph grafo;
	private String nomeArquivo;
	private AcoOperacoes operacoes;
	public final int qtdFormigas = 51;

	public Graph getGrafo() {
		return grafo;
	}

	public void setGrafo(Graph grafo) {
		this.grafo = grafo;
	}

	public AcoAlgorithm(ArrayList<Double[]> posicoes,String nomeArquivo){
		//inicializa as formigas
		this.formigas = new ArrayList<Formiga>();
		for (int i = 0; i < qtdFormigas; i++) {
			formigas.add(new Formiga());
		}

		//inicializa o grafo
		grafo = new Graph(posicoes);
		operacoes = new AcoOperacoes();

		this.nomeArquivo = nomeArquivo;
	}

	private void init() {
		for (int i = 0; i < qtdFormigas; i++) {
			operacoes.inicializar(grafo, formigas.get(i));
		}
	}

	public void run (int iteracoes){

		double tempFitness = 0;
		int i = 0;
		int iteracaoMenor = 0;
		StringBuilder sb = new StringBuilder();
		StringBuilder nodeBranching = new StringBuilder();
		FileWriter fw = null;
		double menorFitnessTemp = 10000000;
		ArrayList<Node> melhorCaminhoEncontrado = null;

		while (i<iteracoes){
			init();
			if (i%200 == 0){
				sb.append("Iteracao" + i + " ");
				sb.append(menorFitnessTemp + "\n");
			}
			for (Formiga formiga : formigas){
				operacoes.realizarTour(grafo, formiga);
				//marca o menor fitness encontrado
				if (menorFitnessTemp > formiga.getFitness()){
					menorFitnessTemp = formiga.getFitness();
					System.out.println("Menor fitness ate agora: " + menorFitnessTemp + " na iteracao " + i);
					melhorCaminhoEncontrado = (ArrayList<Node>) formiga.getNodesPercorridos().clone();
					ArrayList<Integer> nodesMelhorCaminho = formiga.getArraysVisitados();
					for (Integer integer : nodesMelhorCaminho) {
						System.out.print(integer.intValue() + " ");
					}
					System.out.println("");
					iteracaoMenor = i;
				}
			}
			operacoes.reducaoFeromonio(grafo);
			/*
			Esse trecho de codigo eh referente a uma alteracao que deixa a taxa de evaporacao se modificando com o passar do tempo

			System.out.println("Evap: " + (float)i/iteracoes);
			operacao.reducaoFerormonio(grafo,(float)i/iteracoes);			
			menorFitnessTemp = 0;
			 */
			//atualizacao do feromonio
			for (Formiga formiga : formigas){
				operacoes.atualizarFeromonio(formiga);
				if (i%200 == 0){
					nodeBranching.append("Iteracao"+i+"\n");
					ArrayList<Integer> paths = formiga.getArraysVisitados();
					for (Integer integer : paths) {
						nodeBranching.append(integer.intValue() + " ");
					}
					nodeBranching.append("\n");
				}
				formiga.clear();
			}
			i++;
		}

		System.out.println("Melhor fitness encotrado foi " + menorFitnessTemp + "  na iteracao " + iteracaoMenor);

		File fitnessSaida = new File(nomeArquivo + ".fitness");
		File pathSaida = new File(nomeArquivo + ".paths");

		try {
			fw = new FileWriter(fitnessSaida,true);
			fw.write(sb.toString());
			fw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		try {
			fw = new FileWriter(pathSaida,true);
			fw.write(nodeBranching.toString());
			fw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

}
