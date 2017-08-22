package dados;

import Jama.Matrix;
import java.util.ArrayList;

public class Dados {

	//private static Dados dados;
	private Matrix entradasTreinamento;
	private Matrix saidasTreinamento;
	private Matrix entradasValidacao;
	private Matrix saidasValidacao;
	private Matrix entradasTeste;
	private Matrix saidasTeste;
	private CarregarArquivo carregador;
	private ArrayList<double[][]> matrizes;


	//Singleton
//	public static Dados getInstance(String base) {
//		if (dados == null) {
//			dados = new Dados(base);
//		}
//		return dados;
//	}

	//Constructor
	public Dados(String base) {
		carregarBase(base);
		/**************************************************
		 *     Pre-Processamento e DivisÃ£o dos dados      *
		 **************************************************/
		//Normalização Linear na entrada
		if(base.equals(Bases.CARD))
			PreProcessamento.normalizar(matrizes);

		PreProcessamento.misturar(matrizes);
		Matrix entradas = new Matrix(matrizes.get(0));
		Matrix saidas = new Matrix(matrizes.get(1));

		int indiceTreinamento = (int) (entradas.getRowDimension() * 0.5);//Pegar 50%
		entradasTreinamento = entradas.getMatrix(0, indiceTreinamento - 1, 0, (entradas.getColumnDimension() - 1)).copy();
		saidasTreinamento = saidas.getMatrix(0, indiceTreinamento - 1, 0, (saidas.getColumnDimension() - 1)).copy();
		
		int indiceValidacao = (int) (indiceTreinamento + (indiceTreinamento / 2));//Pegar 25%
		entradasValidacao = entradas.getMatrix(indiceTreinamento, indiceValidacao - 1, 0, (entradas.getColumnDimension() - 1)).copy();
		saidasValidacao = saidas.getMatrix(indiceTreinamento, indiceValidacao - 1, 0, (saidas.getColumnDimension() - 1)).copy();
		
		entradasTeste = entradas.getMatrix(indiceValidacao, entradas.getRowDimension() - 1, 0, (entradas.getColumnDimension() - 1)).copy();
		saidasTeste = saidas.getMatrix(indiceValidacao, saidas.getRowDimension() - 1, 0, (saidas.getColumnDimension() - 1)).copy();
		/**************************************************
		 *   Fim Pre-Processamento e DivisÃ£o dos dados    *
		 **************************************************/
	}

	public void carregarBase(String base) {
		if (base.equals(Bases.CANCER)) {
			carregador = new CarregarArquivo("cancer1.txt");
			matrizes = carregador.carregar(9, 2, ";");
		} else if (base.equals(Bases.CURUAUNA)) {
			carregador = new CarregarArquivo("CuruaUna 5X2.txt");
			matrizes = carregador.carregar(5, 2, ";");
		} else if (base.equals(Bases.DIABETES)) {
			carregador = new CarregarArquivo("diabetes1.txt");
			matrizes = carregador.carregar(8, 2, ";");
		} else if (base.equals(Bases.IRIS)) {
			carregador = new CarregarArquivo("Iris.txt");
			matrizes = carregador.carregar(4, 3, ";");
		} else if (base.equals(Bases.JORADAO14X12)) {
			carregador = new CarregarArquivo("Jordao Preparado 14X12.txt");
			matrizes = carregador.carregar(14, 12, ";");
		} else if (base.equals(Bases.JORDAO5X2)) {
			carregador = new CarregarArquivo("Jordao Preparado 5X2.txt");
			matrizes = carregador.carregar(5, 2, ";");
		}else if (base.equals(Bases.CAL_HOUSING)) {
			carregador = new CarregarArquivo("cal_housing.txt");
			matrizes = carregador.carregar(8, 1, ";");
		}else if (base.equals(Bases.CARD)) {
			carregador = new CarregarArquivo("card.txt");
			matrizes = carregador.carregar(51, 2, ";");
		}else if (base.equals(Bases.HORSE)) {
			carregador = new CarregarArquivo("horse.txt");
			matrizes = carregador.carregar(58, 3, ";");   
		}else if (base.equals(Bases.IBOVESPA)) {
			carregador = new CarregarArquivo("ibovespa.txt");
			matrizes = carregador.carregar(5, 1, ";");
		}else if (base.equals(Bases.PERIL)) {
			carregador = new CarregarArquivo("peril.txt");
			matrizes = carregador.carregar(12, 1, ";"); 
		}else if (base.equals(Bases.PERIL_BS)) {
			carregador = new CarregarArquivo("blackswan.txt");
			matrizes = carregador.carregar(12, 1, ";");
		}else if (base.equals(Bases.PERIL_LOGN)) {
			carregador = new CarregarArquivo("peril_logn.txt");
			matrizes = carregador.carregar(12, 1, ";"); 
		}else if (base.equals(Bases.PERIL_BS_LOGN)) {
			carregador = new CarregarArquivo("blackswan_logn.txt");
			matrizes = carregador.carregar(12, 1, ";");
		}else if (base.equals(Bases.PERIL_GAMMA)) {
			carregador = new CarregarArquivo("peril_gamma.txt");
			matrizes = carregador.carregar(12, 1, ";"); 
		}else if (base.equals(Bases.PERIL_BS_GAMMA)) {
			carregador = new CarregarArquivo("blackswan_gamma.txt");
			matrizes = carregador.carregar(12, 1, ";"); 
		}else if (base.equals(Bases.PERIL_2013)) {
			carregador = new CarregarArquivo("peril2013rf0.txt");
			matrizes = carregador.carregar(33, 1, ";"); 
		}
	}

	private void imprimirDados() {
		/*************************************************
		 *             Impressao dos dados                *
		 **************************************************/
		entradasTreinamento.print(5, 2);
		//System.out.println("////////////////////////////////////////////////");
		saidasTreinamento.print(5, 5);

		//System.out.println(">>>>>>>Validacao<<<<<<<<");
		entradasValidacao.print(5, 2);
		saidasValidacao.print(5, 2);

		//System.out.println(">>>>>>>Teste<<<<<<<<");
		entradasTeste.print(5, 2);
		saidasTeste.print(5, 2);

	}

	public Matrix getEntradasTeste() {
		return entradasTeste;
	}

	public Matrix getEntradasTreinamento() {
		return entradasTreinamento;
	}

	public Matrix getEntradasValidacao() {
		return entradasValidacao;
	}

	public Matrix getSaidasTeste() {
		return saidasTeste;
	}

	public Matrix getSaidasTreinamento() {
		return saidasTreinamento;
	}

	public Matrix getSaidasValidacao() {
		return saidasValidacao;
	}
}
