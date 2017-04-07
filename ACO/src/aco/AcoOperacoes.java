package aco;

import java.util.ArrayList;
import java.util.Random;

public class AcoOperacoes {

	public final double Q = 100;
	public final double alpha = 1;
	public final double beta = 5;
	private final double taxaDecaimento = 0.5;

	public AcoOperacoes () {
		super();
	}

	public void reducaoFeromonio(Graph grafo){
		double valorAntigoFeromonio = 0;
		for (Trilha trilha : grafo.trilhas){
			valorAntigoFeromonio = trilha.getValorFeromonio();
			trilha.setValorFeromonio((1-taxaDecaimento)*valorAntigoFeromonio);
		}
	}

	private int proximoNo (Formiga formiga, Graph grafo){
		//montar a roleta
		//para todos os nos
		double probabilidadeTotal = 0;
		Node noAtual = formiga.currentNode();
		ArrayList<Node> nodesValidos= grafo.listNodesPossiveis(formiga.getTabuList(), noAtual);
		int i;
		Double[] probabilidadeNo  = new Double[nodesValidos.size()];
		for (i = 0; i < probabilidadeNo.length; i++) {
			Trilha trilha = grafo.findTrilhaCombinacao(noAtual, nodesValidos.get(i));
			probabilidadeTotal += (Math.pow(trilha.getValorFeromonio(),alpha))*
					(Math.pow(1/trilha.distanciaEuclidiana(), beta));
			probabilidadeNo[i] = (Math.pow(trilha.getValorFeromonio(),alpha))*
					(Math.pow(1/trilha.distanciaEuclidiana(), beta));
		}
		int qtNosLivres = i;
		//se nao houver nos disponiveis
		if (qtNosLivres == 0){
			return formiga.getArraysVisitados().get(0);
		}

		//selecionar o item da roleta
		Random random = new Random();
		double vlRandom = random.nextDouble();
		int posicao = 0;
		double valorTemp = 0;
		for (i = 0; i < qtNosLivres; i++) {
			valorTemp += probabilidadeNo[i]/probabilidadeTotal; 
			if (vlRandom < valorTemp){
				posicao = i;
				break;
			}
		}
		
		return nodesValidos.get(posicao).getNumeroNo();
	}

	public void realizarTour (Graph grafo, Formiga formiga){
		//calcular o caminho de uma formiga
		int numberNodestoVisit = grafo.nodes.size()-1;
		//percorrer o grafo ate a visitar todos os nos
		Node proximoNode = null;
		int proximoNoNum = 0;
		Node noAtual = null;
		Node noInicial = formiga.currentNode();
		int qtdVisitados = 0;
		while (numberNodestoVisit != 0){
			qtdVisitados = formiga.getNodesPercorridos().size();
			noAtual = formiga.currentNode();
			formiga.marcarTabuList(noAtual.getNumeroNo());
			proximoNoNum = proximoNo(formiga,grafo);
			proximoNode = grafo.getNodes().get(proximoNoNum);
			//adiciona o caminho na lista dos percorrido, adiciona na lista dos nos visitados, 
			//adiciona nas trilhas percorridas
			formiga.getNodesPercorridos().add(proximoNode);
			formiga.getArraysVisitados().add(proximoNode.getNumeroNo());
			Trilha tPercorrida = grafo.findTrilhaCombinacao(noAtual, proximoNode);
			formiga.getTrilhasPercorridas().add(tPercorrida);
			numberNodestoVisit--;
		}
		//Adicionando a última trilha que volta ao Node Inicial
		Node noFinal = formiga.currentNode();
		Trilha trilhaUltima = grafo.findTrilhaCombinacao(noInicial, noFinal);
		formiga.getTrilhasPercorridas().add(trilhaUltima);
		
		//seta o fitness da formiga no final
		formiga.setFitness(this.antFitnessAfterTour(formiga));
	}

	public double antFitnessAfterTour(Formiga formiga) {
		double fitness = 0;
		ArrayList<Trilha> trilhasPercorridas = formiga.getTrilhasPercorridas();
		for (Trilha trilha : trilhasPercorridas) {
			fitness += trilha.distanciaEuclidiana();
		}
		return fitness;
	}

	public void atualizarFeromonio (Formiga formiga){
		ArrayList<Trilha> trilhasPercorridas = formiga.getTrilhasPercorridas();
		double fitness = 0, valorAntigoFeromonio = 0, deltaFeromonio = 0;
		int i, j;
		for (Trilha trilha : trilhasPercorridas) {
			fitness = formiga.getFitness();
			valorAntigoFeromonio = trilha.getValorFeromonio();
			deltaFeromonio = Q/fitness;
			trilha.setValorFeromonio(valorAntigoFeromonio+deltaFeromonio);
		}
	}

	public void inicializar(Graph grafo, Formiga formiga){
		Double num_sorteado = Math.random()*(grafo.nodes.size()-1);
		Node no_sorteado = grafo.nodes.get(num_sorteado.intValue());
		ArrayList<Node> inicial = new ArrayList<Node>();
		ArrayList<Integer> inicial_int = new ArrayList<Integer>();
		inicial_int.add(num_sorteado.intValue());
		inicial.add(no_sorteado);
		formiga.setArraysVisitados(inicial_int);
		formiga.setNodesPercorridos(inicial);
		formiga.limparTabuList();
		formiga.marcarTabuList(no_sorteado.getNumeroNo());
	}

}
