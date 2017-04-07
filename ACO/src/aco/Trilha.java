package aco;

public class Trilha {

	private double valorFerormonio;
	private double distanciaEuclidiana;
	private Node noOrigem;
	private Node noDestino;
	
	public Trilha(double valorFerormonio, Node noOrigem, Node noDestino) {
		super();
		this.valorFerormonio = valorFerormonio;
		this.noOrigem = noOrigem;
		this.noDestino = noDestino;
		this.distanciaEuclidiana = distanciaEuclidiana(noOrigem.getPosicao(), noDestino.getPosicao());
	}
	
	public double distanciaEuclidiana(){
		return this.distanciaEuclidiana;
	}
	
	private double distanciaEuclidiana (Double[] posicao1, Double[] posicao2){
		double xd, yd;
		xd = posicao1[0]-posicao2[0];
		yd = posicao1[1]-posicao2[1];
		double euclidiana= Math.sqrt((Math.pow(xd,2) + Math.pow(yd,2)));
		return euclidiana;
	}
	
	public Node getNoOrigem() {
		return noOrigem;
	}
	public void setNoOrigem(Node noOrigem) {
		this.noOrigem = noOrigem;
	}
	public double getValorFeromonio() {
		return valorFerormonio;
	}
	public void setValorFeromonio(double valorFerormonio) {
		this.valorFerormonio = valorFerormonio;
	}
	public Node getNoDestino() {
		return noDestino;
	}
	public void setNoDestino(Node noDestino) {
		this.noDestino = noDestino;
	}
	
}
