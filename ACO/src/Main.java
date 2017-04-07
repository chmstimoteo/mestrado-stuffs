import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

import aco.AcoAlgorithm;
import aco.AcoOperacoes;
import aco.Trilha;
import aco.Formiga;
import aco.Graph;
import aco.Node;


public class Main {

	public static void main(String[] args) {

		ArquivoUtil arquivos = new ArquivoUtil();
		//ArrayList<Double[]> vetores = arquivos.readTSP("oliver30.tsp");
		//ArrayList<Double[]> vetores = arquivos.readTSP("att48.tsp");
		ArrayList<Double[]> vetores = arquivos.readTSP("eil51.tsp");

		int iteracoes = 1000;
		for (int j = 0 ; j < 1 ; j++){
			AcoAlgorithm aco = new AcoAlgorithm(vetores, "eil51.tsp");
			aco.run(iteracoes);
			System.out.println("FIM");
		}
	}

}
