package aco;

import java.util.ArrayList;

public class Node {
	
	private Double[] posicao;
	private int numeroNo;

	public Node(Double[] posicao,int numeroNo) {
		super();
		this.posicao = posicao;
		this.numeroNo = numeroNo;
	}
	
	public Double[] getPosicao() {
		return posicao;
	}
	public void setPosicao(Double[] dicionario) {
		this.posicao = dicionario;
	}
	public int getNumeroNo() {
		return numeroNo;
	}
	public void setNumeroNo(int numeroNo) {
		this.numeroNo = numeroNo;
	}
}
