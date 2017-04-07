package aco.fundamentals.maps;

import java.util.ArrayList;

import aco.IACOController;
import aco.fundamentals.Ant;
import aco.fundamentals.EColonyAlgorithm;
import aco.fundamentals.Trail;
import aco.fundamentals.parameters.ColonyParameters;

public class ASAntColonyMap extends AntColonyMap 
{
	public ASAntColonyMap(ColonyParameters parameters, IACOController controller) 
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
		return EColonyAlgorithm.AntSystem;
	}
}
