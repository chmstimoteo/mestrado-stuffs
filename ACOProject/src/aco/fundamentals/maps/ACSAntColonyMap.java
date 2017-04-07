package aco.fundamentals.maps;

import java.util.ArrayList;

import aco.IACOController;
import aco.fundamentals.Ant;
import aco.fundamentals.EColonyAlgorithm;
import aco.fundamentals.Trail;
import aco.fundamentals.parameters.ACSParameters;
import aco.fundamentals.parameters.ColonyParameters;

public class ACSAntColonyMap extends AntColonyMap 
{
	public ACSAntColonyMap(ColonyParameters parameters, IACOController controller) 
	{
		super(parameters, controller);
	}

	public void BuildPaths() 
	{
		super._BuildPaths();
	}

	public void DropPheromones() 
	{
		for (Trail trail : this.BestSoFarAnt.getCrossedTrails())
        {
            trail.Pheromones +=
                (this._Parameters.PheromoneConstant / this.BestSoFarAnt.HeuristicResult.getHeuristicValue().doubleValue())
                *
                this._Parameters.GlobalEvaporationCoefficient
                ;
        }
	}

	public void SetTauValue() 
	{
		_Tau0 =
            1 / (this.Locations.size() * this.BestSoFarAnt.HeuristicResult.getHeuristicValue().doubleValue());
	}

	protected Trail _ApplySpecificRuleOnChoosingTrail(
			ArrayList<Trail> feasibleTrails, Ant currentIterationAnt) 
	{
		Trail choosed = null;

        double intensificateFound = this._R.nextDouble();
        ACSParameters parameters = (ACSParameters)this._Parameters;

        if (parameters.IntensificateProbability >= intensificateFound)
        {
            //intensificação (exploitation)
            double maxPheromoneRate = 0; //feasibleTrails.Max(t => t.Pheromones);
            
            for (Trail t : feasibleTrails) 
            {
				if (t.Pheromones > maxPheromoneRate)
					maxPheromoneRate = t.Pheromones;
			}
            
            ArrayList<Trail> trails = new ArrayList<Trail>();
            for (Trail t : feasibleTrails)
                if (t.Pheromones == maxPheromoneRate)
                    trails.add(t);

            currentIterationAnt.WalkToLocation(choosed = trails.get(_R.nextInt(trails.size())));
        }

        return choosed;
	}

	protected void _UpdateLocalPheromoneOnChoosedTrail(Trail trail) 
	{
		//atualizar o feromônio localmente do 'choosed'.
		if (trail != null)
        {
			ACSParameters parameters = (ACSParameters)this._Parameters;
			
            trail.Pheromones =
                ((1 - parameters.LocalEvaporationCoefficient) * trail.Pheromones)
                +
                (parameters.LocalEvaporationCoefficient * this._Tau0);

            if (trail.Pheromones < this._Tau0)
                trail.Pheromones = this._Tau0;
        }
	}

	public EColonyAlgorithm getAlgorithm() 
	{
		return EColonyAlgorithm.AntColonySystem;
	}
}