
import java.util.ArrayList;

import aco.AcoAlgorithm;


public class Main {

	public static void main(String[] args) {

		ArquivoUtil arquivos = new ArquivoUtil();
		ArrayList<Double[]> vetores = arquivos.readTSP("oliver30.tsp");
		//ArrayList<Double[]> vetores = arquivos.readTSP("att48.tsp");
		//ArrayList<Double[]> vetores = arquivos.readTSP("eil51.tsp");

		int iteracoes = 2000;
		for (int j = 0 ; j < 1 ; j++){
			AcoAlgorithm aco = new AcoAlgorithm(vetores, "oliver30.tsp");
			aco.run(iteracoes);
			System.out.println("FIM");
		}
	}

}
