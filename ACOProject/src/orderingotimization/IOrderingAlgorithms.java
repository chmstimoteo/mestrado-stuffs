package orderingotimization;

public interface IOrderingAlgorithms 
{
	//Recebe o algoritmo, a quantidade de threads e o nome do arquivo e retorna o tempo de processamento.
	//aten��o: o tempo de processamento n�o deve considerar a leitura do arquivo. Somente o processamento do algoritmo e as threads.
	double ProccessAlgorithm(EOrderingAlgorithm algorithm, int numberOfThreads, String sourceFile);
}
