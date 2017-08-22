package aco;

import java.util.ArrayList;
import java.util.Random;

public class Graph {

	public ArrayList<Node> nodes;
	public ArrayList<Trilha> trilhas;
	public final double cInicial = 2.0;

	public Graph(ArrayList<Double[]> posicoes){
		//inicializacao do problema
		int numPosicoes = posicoes.size();
		this.nodes = new ArrayList<Node>();
		this.trilhas = new ArrayList<Trilha>();
		//criar os nos referentes ao numero de posicoes
		for (int i = 0; i < numPosicoes; i++) {
			nodes.add(new Node(posicoes.get(i),i));
		}
		
		//criando todas as trilhas e adicionando feromonio
		criar_trilhas();
	}
	
	public ArrayList<Node> getNodes() {
		return nodes;
	}
	public void setNodes(ArrayList<Node> nodes) {
		this.nodes = nodes;
	}

	public ArrayList<Trilha> listTrilhas(Node node) {
		ArrayList<Trilha> lista = new ArrayList<Trilha>();
		for (Trilha t : this.trilhas) {
			if (t.getNoOrigem().equals(node))
				lista.add(t);
		}
		return lista;
	}
	
	public Trilha findTrilha(Node origem, Node destino){
		ArrayList<Trilha> listaOrigem = listTrilhas(origem);
		Trilha result = null;
		for (Trilha trilha : listaOrigem) {
			if (trilha.getNoDestino().equals(destino))
				result = trilha;
		}
		return result;
	}
	
	private void criar_trilhas() {
		for (Node nodeOrigem : this.nodes) {
			for (Node nodeDestino : this.nodes) {
				if(findTrilha(nodeOrigem, nodeDestino) == null){
					if (findTrilha(nodeDestino, nodeOrigem) == null){
						if (! nodeOrigem.equals(nodeDestino))
							this.trilhas.add(new Trilha(cInicial, nodeOrigem, nodeDestino));
					}
				}
			}
		}
	}

	public ArrayList<Node> listNodesPossiveis(Boolean[] tabuList, Node noAtual) {
		ArrayList<Node> result = new ArrayList<Node>();
		Trilha trilha = null;
		for (Node nodeItem : this.nodes) {
			if((trilha = findTrilha(noAtual, nodeItem)) != null){
				//Se o node nao foi visitado
				if (!tabuList[trilha.getNoDestino().getNumeroNo()].booleanValue())
				result.add(nodeItem);
			}
			if ((trilha = findTrilha(nodeItem, noAtual)) != null){
				//Se o node nao foi visitado
				if (!tabuList[trilha.getNoOrigem().getNumeroNo()].booleanValue())
				result.add(nodeItem);
			}
		}
		return result;
		
	}

	public Trilha findTrilhaCombinacao(Node item1, Node item2) {
		Trilha t = this.findTrilha(item1, item2);
		if (t == null)
			t = this.findTrilha(item2, item1);
		return t;
	}

}
