package aco.fundamentals.maps;

import java.util.ArrayList;
import java.util.Random;

import aco.IACOController;
import aco.IACOHeuristicResult;
import aco.IAntFood;
import aco.fundamentals.Ant;
import aco.fundamentals.EColonyAlgorithm;
import aco.fundamentals.IAntColonyMap;
import aco.fundamentals.Location;
import aco.fundamentals.Trail;
import aco.fundamentals.parameters.ColonyParameters;

public abstract class AntColonyMap implements IAntColonyMap
{
	public AntColonyMap(ColonyParameters parameters, IACOController controller)
    {
        this._Parameters = parameters;
        this._Controller = controller;
    }
	
	public abstract EColonyAlgorithm getAlgorithm();
	protected ColonyParameters _Parameters = null;
    private IACOController _Controller = null;
    protected Random _R = new Random(123456);
    protected double _Tau0;
    public ArrayList<Location> Locations;
    public ArrayList<Ant> Ants;
    public Ant BestIterationAnt;
    public Ant BestSoFarAnt;
    public ArrayList<Trail> AllTrails;
    
	public ArrayList<Ant> getAnts() 
	{
		return this.Ants;
	}
	public Ant getBestSoFarAnt() 
	{
		return this.BestSoFarAnt;
	}
    public IACOHeuristicResult getBestHeuristicResult()
    {
    	return
	        this.BestSoFarAnt != null ?
	            this.BestSoFarAnt.HeuristicResult
	            :
	            null;
    }
    
    public void BuildLocationsTrails(ArrayList<IAntFood> antFood)
    {
        this.AllTrails = new ArrayList<Trail>();
        this.Locations = new ArrayList<Location>();

        for (IAntFood food : antFood)
            this.Locations.add(new Location(food));

        Trail auxTrail = null;
        for (Location fromLoc : this.Locations)
        {
            fromLoc.Trails = new ArrayList<Trail>();

            for (Location toLoc : this.Locations)
            {
                auxTrail = null;

                if (toLoc.Id == fromLoc.Id)
                    continue;

                if (toLoc.Trails != null)
                {
                	for (Trail t : toLoc.Trails) 
                	{
						if (t.L2.Id == fromLoc.Id)
						{
							auxTrail = t;
							break;
						}
					}
                }

                if (auxTrail == null)
                {
                    auxTrail = new Trail(fromLoc, toLoc);
                    this.AllTrails.add(auxTrail);
                }

                fromLoc.Trails.add(auxTrail);
            }
        }
    }
    //arthur*** alterado
    public void DropAnts()
    {
        this.Ants = new ArrayList<Ant>();
        for (int i = 0; i < this._Parameters.ColonySize; i++)
            this.Ants.add(new Ant());

        int fromLocationIdx = -1;
        
        Location aux = null;

        for (Ant ant : this.Ants)
        {
            ant.EmptyTrailsList();

            do
            {
            	fromLocationIdx = _R.nextInt(this.Locations.size());
                aux = this.Locations.get(fromLocationIdx);

            } while (aux == null);

            if (this._Parameters.Domain.getAntsVisitOnlyPartOfTheLocations())
            {
            	do
            	{
            		ant.LocationsToVisit = _R.nextInt(this.Locations.size() + 1);            		
            	} while (ant.LocationsToVisit <= 1); //tem que visitar mais de um lugar porque o feromônio é associado à trilha entre os lugares, e não ao lugar em si
            }
            else
            	ant.LocationsToVisit = this.Locations.size();
            
            ant.WalkToLocation(aux);
        }
    }
    public void EvaluateSolutions()
    {
        for (Ant ant : this.Ants)
            ant.ProccessHeuristic(this._Controller);

        //armazena a melhor solução
        double lesserDistance = Double.MAX_VALUE;// this.Ants.Min(a => a.HeuristicResult.HeuristicValue.Value);
        this.BestIterationAnt = null; //this.Ants.Find(a => a.HeuristicResult.HeuristicValue.Value == lesserDistance);
        
        Ant tempBestAnt = null;
        for (Ant a : this.Ants) 
        {
        	if (a.HeuristicResult.getHeuristicValue().doubleValue() < lesserDistance)
        	{
        		tempBestAnt = a;
        		lesserDistance = a.HeuristicResult.getHeuristicValue().doubleValue();
        	}
		}
        this.BestIterationAnt = tempBestAnt;
        

        double oldBestSoFarAntDistance =
            this.BestSoFarAnt != null ?
            this.BestSoFarAnt.HeuristicResult.getHeuristicValue().doubleValue()
            :
            	Double.MAX_VALUE;

        if (BestIterationAnt.HeuristicResult.getHeuristicValue().doubleValue() <= oldBestSoFarAntDistance)
            this.BestSoFarAnt = BestIterationAnt.Clone();
    }
    
    public abstract void SetTauValue();
    protected void _SetTauValue()
    {
        _Tau0 =
            1 / (this._Parameters.GlobalEvaporationCoefficient * this.BestSoFarAnt.HeuristicResult.getHeuristicValue().doubleValue());
    }
    public void SetInitialPheromones()
    {
        //this.AllTrails.ForEach(t => t.Pheromones = _Tau0);
    	
    	for (Trail t : this.AllTrails) 
    	{
    		t.Pheromones = _Tau0;
		}
    }
    public void EvaporatePheromones()
    {
    	for (Trail t : this.AllTrails)
        {
            t.Pheromones = (1 - this._Parameters.GlobalEvaporationCoefficient) * t.Pheromones;

            if (t.Pheromones < this._Tau0)
                t.Pheromones = this._Tau0;
        };
    }
    public abstract void DropPheromones();
    protected void _DropPheromones()
    {
        double depositedPheromones = 0;

        for (Ant ant : this.Ants)
        {
            depositedPheromones = this._Parameters.PheromoneConstant / ant.HeuristicResult.getHeuristicValue().doubleValue();

            for (Trail trail : ant.getCrossedTrails())
                trail.Pheromones += depositedPheromones;
        }
    }
    public abstract void BuildPaths();
    protected void _BuildPaths()
    {
    	ArrayList<Trail> feasibleTrails = null;

        Trail choosed = null;

        for (Ant ant : this.Ants)
        {
            feasibleTrails = null;

            while ((feasibleTrails = ant.GetFeasibleTrails()).size() > 0) //enquanto houver Locations não percorridos
            {
                choosed = this._ApplySpecificRuleOnChoosingTrail(feasibleTrails, ant);

                if (choosed == null)
                    choosed = this._ApplyGeneralRuleOnChoosingTrail(feasibleTrails, ant);

                this._UpdateLocalPheromoneOnChoosedTrail(choosed);
            }
        }
    }
    protected abstract Trail _ApplySpecificRuleOnChoosingTrail(ArrayList<Trail> feasibleTrails, Ant currentIterationAnt);
    protected abstract void _UpdateLocalPheromoneOnChoosedTrail(Trail trail);
    protected Trail _ApplyGeneralRuleOnChoosingTrail(ArrayList<Trail> feasibleTrails, Ant currentIterationAnt)
    {
        Trail choosed = null;

        double probabilityFound = 0;
        
        double value = ((double)_R.nextInt(101)) / ((double)100);
        double counter = 0;
        double probabilitySum = 0;//feasibleTrails.Sum(t => (Math.Pow(t.Pheromones, this._Parameters.Alpha)));
        
        for (Trail t : feasibleTrails) 
        {
        	probabilitySum += (Math.pow(t.Pheromones, this._Parameters.Alpha)); 
		}

        for (Trail trail : feasibleTrails)
        {
            probabilityFound =
                (Math.pow(trail.Pheromones, this._Parameters.Alpha))
                /
                probabilitySum;

            counter += probabilityFound;
            if (counter >= value)
            {
                currentIterationAnt.WalkToLocation(trail);
                choosed = trail;
                break;
            }
        }

        return choosed;
    }
}
