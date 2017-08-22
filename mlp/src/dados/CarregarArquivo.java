package dados;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.util.ArrayList;
import java.util.StringTokenizer;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CarregarArquivo {

    FileReader arquivo;
    BufferedReader leitor;
    StringTokenizer token;
    String caminho;

    public CarregarArquivo(String caminho) {
        this.caminho = caminho;
    }

    public ArrayList<double[][]> carregar(int entradas, int saidas, String token){
        ArrayList<double[][]> retorno = new ArrayList<double[][]>();        
        String linha = null;
        try {
            this.arquivo = new FileReader(this.caminho);
            this.leitor = new BufferedReader(this.arquivo);

            File arquivoLeitura = new File(this.caminho);
            long tamanhoArquivo = arquivoLeitura .length(); 
            FileInputStream fs = new FileInputStream(arquivoLeitura);
            DataInputStream in = new DataInputStream(fs);
            LineNumberReader lineRead = new LineNumberReader(new InputStreamReader(in));
            lineRead.skip(tamanhoArquivo);
            // conta o numero de linhas do arquivo, comeca com zero, por isso adiciona 1
            int numLinhas = lineRead.getLineNumber() + 1;
            double[][] input = new double[numLinhas][entradas];
            double[][] output = new double[numLinhas][saidas];

            int cont = 0;
            while((linha = this.leitor.readLine()) != null){
            	//System.out.println("linha: "+linha);
                this.token = new StringTokenizer(linha,token);
                String dados = null;
                double[] tempEntradas;
                double[] tempSaidas;
                while(this.token.hasMoreTokens()){
                    tempEntradas = new double[entradas];
                    tempSaidas = new double[saidas];
                    for (int i = 0; i < (entradas+saidas); i++) {
                        if(i < entradas){
                            dados = this.token.nextToken();
                            tempEntradas[i] = (Double.parseDouble(dados));
                         }else{
                            dados = this.token.nextToken();
                            tempSaidas[i-entradas] = (Double.parseDouble(dados));
                        }
                    }
                    input[cont] = tempEntradas.clone();
                    output[cont] = tempSaidas.clone();
                    cont++;
                }
            }
            retorno.add(input);
            retorno.add(output);
        } catch (Exception ex) {
            Logger.getLogger(CarregarArquivo.class.getName()).log(Level.SEVERE, null, ex);
        }
        return retorno;
    }

}
