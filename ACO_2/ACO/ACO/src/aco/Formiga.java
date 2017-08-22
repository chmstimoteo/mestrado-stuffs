package aco;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

public class Formiga {

	private double fitness;
	private Boolean[] tabuList;
	private ArrayList<Node> nodesPercorridos;
	private ArrayList<Integer> arraysVisitados;
	private ArrayList<Trilha> trilhasPercorridas;
	
	public final int numeroCidades = 30;

	public Formiga() {
		this.nodesPercorridos = new ArrayList<Node>();
		this.fitness = 100000000;
		this.arraysVisitados = new ArrayList<Integer>();
		this.trilhasPercorridas = new ArrayList<Trilha>();
		tabuList = new Boolean[numeroCidades];
		limparTabuList();
	}
	
	public void limparTabuList() {
		for (int i = 0; i < tabuList.length; i++) {
			tabuList[i] = false;
		}
	}
	
	public void marcarTabuList(int posicao) {
		tabuList[posicao]=true;
	}
	
	public boolean tabuListMarcado(int posicao){
		return tabuList[posicao];
	}
	
	public ArrayList<Trilha> getTrilhasPercorridas() {
		return trilhasPercorridas;
	}
	public void setTrilhasPercorridas(ArrayList<Trilha> trilhasPercorridas) {
		this.trilhasPercorridas = trilhasPercorridas;
	}
	public Node currentNode(){
		return nodesPercorridos.get(nodesPercorridos.size()-1);
	}
	public ArrayList<Node> getNodesPercorridos() {
		return nodesPercorridos;
	}
	public void setNodesPercorridos(ArrayList<Node> nodesPercorridos) {
		this.nodesPercorridos = nodesPercorridos;
	}
	public double getFitness() {
		return fitness;
	}
	public void setFitness(double fitness) {
		this.fitness = fitness;
	}
	public ArrayList<Integer> getArraysVisitados() {
		return arraysVisitados;
	}
	public void setArraysVisitados(ArrayList<Integer> arraysVisitados) {
		this.arraysVisitados = arraysVisitados;
	}
	public void clear(){
		this.trilhasPercorridas.clear();
		this.arraysVisitados.clear();
		this.nodesPercorridos.clear();
		this.fitness = 100000000;
		this.limparTabuList();
	}

	public Boolean[] getTabuList() {
		return this.tabuList;
	}
	
}
