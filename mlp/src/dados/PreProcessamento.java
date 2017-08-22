package dados;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class PreProcessamento {

    ArrayList<double[][]> dados;
    private static ArrayList<double[][]> maxMin = new ArrayList<double[][]>();
    private static final double a = 0.1;
    private static final double b = 0.9;

    public PreProcessamento(ArrayList<double[][]> dados) {
        this.dados = dados;
    }

    public static void normalizar(ArrayList<double[][]> dados) {
        PreProcessamento.getMaxMin(dados);
        for (int i = 0; i < dados.size(); i++) {
            for (int j = 0; j < dados.get(i)[0].length; j++) {
                double xMax = maxMin.get(i)[j][0];
                double xMin = maxMin.get(i)[j][1];
                for (int k = 0; k < dados.get(i).length; k++) {
                    dados.get(i)[k][j] = (((b - a) * (dados.get(i)[k][j] - xMin)) / (xMax - xMin)) + a;
                }
            }
        }
    }

    public static void desnormalizar(double[][] dados, int tipo) {//tipo = 0 => entrada  tipo = 1 => saida
        double xMax = 0.0;
        double xMin = 0.0;

        for (int i = 0; i < dados.length; i++) {//linhas
            for (int j = 0; j < dados[0].length; j++) {//colunas
                xMax = maxMin.get(tipo)[j][0];
                xMin = maxMin.get(tipo)[j][1];
                dados[i][j] = (((dados[i][j] - a) * (xMax - xMin))/(b-a)) + xMin;
            }
        }
    }

    static void getMaxMin(ArrayList<double[][]> dados) {
        double max;
        double min;
        for (int t = 0; t < dados.size(); t++) {
            double[][] retorno = new double[dados.get(t)[0].length][2];
            for (int i = 0; i < dados.get(t)[0].length; i++) {
                 max = (double)dados.get(t)[0][i];
                 min = (double)dados.get(t)[0][i];
                
                for (int j = 1; j < dados.get(t).length; j++) {
                   if ((double)dados.get(t)[j][i] > max)
                        max = dados.get(t)[j][i];
                    if ((double)dados.get(t)[j][i] < min)
                        min = dados.get(t)[j][i];
                }
                retorno[i][0] = max;
                retorno[i][1] = min;
            }
            PreProcessamento.maxMin.add(retorno);
        }
    }

    public static void misturar(ArrayList<double[][]> dados) {
        double[][] retornoEntradas = dados.get(0).clone();
        double[][] retornoSaidas = dados.get(1).clone();
        ArrayList<Integer> indices = new ArrayList<Integer>();
        int indice = dados.get(0).length - 1;
        while (indice >= 0) {
            indices.add(indice);
            indice--;
        }
        for (int i = 0; i < dados.get(0).length; i++) {
            indice = (int) Math.round(Math.random() * (indices.size() - 1));
            indice = indices.remove(indice);

            dados.get(0)[i] = retornoEntradas[indice];
            dados.get(1)[i] = retornoSaidas[indice];
        }

    }

    private static double arredondar(double valor) {
        DecimalFormat formato = new DecimalFormat("0.0");
        Double retorno = Double.parseDouble((formato.format(valor)).replace(",", "."));
        return retorno;
    }
}
