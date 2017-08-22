

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class ArquivoUtil {
	
	/**
	 * @
	 * @param Caminho do arquivo que contem a posicao das cidades
	 * @return Um array contendo todas as posicoes das cidades descritas no arquivo texto
	 */
	
	public ArrayList<Double[]> readTSP (String caminhoArquivo){

		File file = new File (caminhoArquivo);
		BufferedReader br = null;
		ArrayList<Double[]> lista = new ArrayList<Double[]>();
		try {
			br = new BufferedReader(new FileReader(file));
			while (br.ready()) {
				lista.add(this.stringToDouble(br.readLine()));
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return lista;
	}
	
	private Double[] stringToDouble (String vetor){
		String[] temp = vetor.split(" ");
		Double[] retorno = new Double[temp.length];
		for (int i = 0; i < temp.length; i++) {
			retorno[i] = Double.parseDouble(temp[i]);
		}
		
		return retorno;
	}

}
