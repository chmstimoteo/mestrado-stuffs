package aco.fundamentals.maps;

import java.util.ArrayList;

import aco.IACOController;
import aco.fundamentals.Ant;
import aco.fundamentals.EColonyAlgorithm;
import aco.fundamentals.Trail;
import aco.fundamentals.parameters.ColonyParameters;
import aco.fundamentals.parameters.EASParameters;

public class EASAntColonyMap extends AntColonyMap 
{
	public EASAntColonyMap(ColonyParameters parameters, IACOController controller) 
	{
		super(parameters, controller);
	}

	public void BuildPaths() 
	{
		super._BuildPaths();
	}

	public void DropPheromones() 
	{
		super._DropPheromones();
		
		//reforço adicional pela formiga b-s-f
        double depositedPheromones = this._Parameters.PheromoneConstant / BestSoFarAnt.HeuristicResult.getHeuristicValue().doubleValue();
        for (Trail trail : this.BestSoFarAnt.getCrossedTrails())
        {
            trail.Pheromones +=
                ((EASParameters)this._Parameters).BestSoFarTourReinforcement * depositedPheromones;
        }
	}

	public void SetTauValue() 
	{
		super._SetTauValue();
	}

	protected Trail _ApplySpecificRuleOnChoosingTrail(
			ArrayList<Trail> feasibleTrails, Ant currentIterationAnt) 
	{
		return null;
	}

	protected void _UpdateLocalPheromoneOnChoosedTrail(Trail trail) 
	{
		
	}

	public EColonyAlgorithm getAlgorithm() 
	{
		return EColonyAlgorithm.ElitistAntSystem;
	}
}
