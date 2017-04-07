package aco;

import java.util.ArrayList;

import aco.fundamentals.ACOSimulationStatus;
import aco.fundamentals.EColonyAlgorithm;
import aco.fundamentals.IAntColonyMap;
import aco.fundamentals.maps.AntColonyMapFactory;
import aco.fundamentals.parameters.ACSParameters;
import aco.fundamentals.parameters.ColonyParameters;
import aco.fundamentals.parameters.EASParameters;

public class ACOColony 
{
	public ACOColony(ColonyParameters parameters, IACOController controller)
    {
        this._Parameters = parameters;
        this._Controller = controller;
        this.Validate();
    }
	
	private IAntColonyMap _Map = null;
    private ColonyParameters _Parameters = null;
    private IACOController _Controller = null;
    private ACOSimulationStatus _SimulationStatus = null;
    
    public boolean Validate()
    {
    	ArrayList<String> msgs = new ArrayList<String>();
    	
        if (this._Parameters == null)
        	msgs.add("Os parâmetros de configuração da colônia de formigas não foram fornecidos");
        else
        {
            if (this._Parameters.Alpha <= 0)
            	msgs.add("Alfa deve ser maior que zero.");

            if (this._Parameters.ColonySize <= 0)
            	msgs.add("A colônia de formigas deve ter pelo menos uma formiga.");

            if (this._Parameters.GlobalEvaporationCoefficient <= 0 || this._Parameters.GlobalEvaporationCoefficient > 1)
            	msgs.add("O coeficiente de evaporação deve ser maior que zero e menor ou igual a 1.");

            if (this._Parameters.PercentageOfSuccess <= 0 || this._Parameters.PercentageOfSuccess > 1)
            	msgs.add("A porcentagem para indicação de sucesso deve ser maior que zero e menor ou igual a 1.");

            if (this._Parameters.PheromoneConstant < 0)
            	msgs.add("A força do feromônio deve ser maior que zero.");

            if (this._Parameters.getAlgorithm() == EColonyAlgorithm.ElitistAntSystem)
            {
                EASParameters easParams = (EASParameters)this._Parameters;

                if (easParams.BestSoFarTourReinforcement <= 0)
                	msgs.add("O reforço do feromônio pela formiga elitista na melhor solução global deve ser maior que zero.");
            }
            else if (this._Parameters.getAlgorithm() == EColonyAlgorithm.AntColonySystem)
            {
                ACSParameters acsParams = (ACSParameters)this._Parameters;

                if (acsParams.IntensificateProbability < 0 || acsParams.IntensificateProbability > 1)
                	msgs.add("A intensificação na escolha do caminho deve ser maior que 0 e menor ou igual a 1.");
            }
        }
        
        if (msgs.size() != 0)
        {
        	for (String s : msgs) 
        	{
        		System.out.println(s);
			}
        }
        
        return msgs.size() == 0;
    }
    
    public IACOHeuristicResult Simulate(ArrayList<IAntFood> antFood)
    {
    	if (!this.Validate())
    		return null;
    	
    	this._Map = AntColonyMapFactory.Create(this._Parameters, this._Controller);
        this._SimulationStatus = new ACOSimulationStatus(this._Map, this._Parameters);
        this._Map.BuildLocationsTrails(antFood); //cria as trilhas que partem de cada Location

        System.out.println("Iniciando processamento da colônia configurada");

        do
        {
            this._SimulationStatus.StoreSimulationData(); //armazena dados gerais da simulação
            System.out.println("[Info] Iteração " + this._SimulationStatus.CurrentIteration + ".");

            this._Map.DropAnts(); //dropa formigas nos Locations
            this._Map.BuildPaths(); //formigas constroem soluções
            this._Map.EvaluateSolutions(); //avalia as soluções encontradas

            if (this._SimulationStatus.CurrentIteration == 1)
            {
                this._Map.SetTauValue();
                this._Map.SetInitialPheromones();
            }

            this._Map.EvaporatePheromones();
            this._Map.DropPheromones();

        } while (!this._SimulationStatus.Converged());

        if (this._Parameters.LimitIterations <= this._SimulationStatus.CurrentIteration)
        	System.out.println(
                "[Info] Final do processamento da colônia de formigas devido ao critério de limite de " + this._Parameters.LimitIterations.doubleValue() + " iterações.");
        else
        	System.out.println("[Info] Final do processamento da colônia de formigas devido à convergência da solução!");
        
        IACOHeuristicResult result = this._Map.getBestSoFarAnt().HeuristicResult;
        
        System.out.println("\n----------------------------------\nResultado:\n");
        
        if (result != null)
        {
        	for (String item : result.GetVisitedOrderedAntFoods())
                System.out.println(item);        	
        }
        else
    		System.out.println("[Null]");

        

        return result;
    }
}
