import java.io.*;
import java.util.ArrayList;
import java.util.Random;

public class Main {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

		FileInputStream f;
		try {
			f = new FileInputStream("C://Users//carlos//workplace//workspace//peril2013rf10matlab.txt");
			InputStreamReader reader = new InputStreamReader(f);
			BufferedReader buff = new BufferedReader (reader);
			ArrayList<String> textos = new ArrayList<String>();
			String line = buff.readLine();
			
			while((line = buff.readLine())!=null){
				//System.out.println(line);
				textos.add(line);
			}
			
			int count = textos.size();
			ArrayList<String> retornoEntradas = (ArrayList<String>)textos.clone();
	        ArrayList<Integer> indices = new ArrayList<Integer>();
	        int indice = count - 1;
	        while (indice >= 0) {
	            indices.add(indice);
	            indice--;
	        }
	        for (int i = 0; i < textos.size(); i++) {
	            indice = (int) Math.round(Math.random() * (indices.size() - 1));
	            indice = indices.remove(indice);
	            textos.set(i, retornoEntradas.get(indice));
	        }
	        
	        for (String string : textos) {
				System.out.println(string);
			}
			

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
