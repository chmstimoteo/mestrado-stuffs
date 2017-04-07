package aco.fundamentals;

import java.util.Date;

import aco.fundamentals.parameters.ColonyParameters;

import common.JavaTimeSpan;

public class ACOSimulationStatus 
{
	public ACOSimulationStatus(IAntColonyMap map, ColonyParameters parameters)
    {
        this._Map = map;
        this._Parameters = parameters;
    }

    private ColonyParameters _Parameters = null;
    private IAntColonyMap _Map = null;
    public int CurrentIteration;
    public JavaTimeSpan ElapsedTime;
    public Date SimulationBeginning;
    private int _CounterSamePath = 0;
    
    public int GetStatusBarIterationProportion()
    {
        double value = 0;

        if (this._Parameters != null && this._Parameters.LimitIterations != null)
            value = Math.round(((double)100) / (double)this._Parameters.LimitIterations.intValue());

        //Ou seja, caso o limite de iterações não seja definido, a barra de progresso
        //crescerá simbólicamente de uma em uma unidade de progresso
        if (value == 0) 
            value = 1;

        return (int)value;
    }
    public void StoreSimulationData()
    {
        this.CurrentIteration++; // contador de iterações. Serve pra nada.
        if (this.ElapsedTime == null)
            this.ElapsedTime = new JavaTimeSpan();

        if (this.SimulationBeginning == null)
            this.SimulationBeginning = new Date();

        this.ElapsedTime = 
        		JavaTimeSpan.SubtractDates(
        				this.SimulationBeginning, 
        				new Date());
    }
    
    public boolean Converged()
    {
        if (this._Map.getBestSoFarAnt() == null)
            return false;
        else if (this._Parameters.LimitIterations != null &&
            this._Parameters.LimitIterations != null &&
            this.CurrentIteration >= this._Parameters.LimitIterations.intValue())
            return true;

        if (this._Parameters.Domain.getDomain() == EACODomain.BestWay)
        {
            //Verifica se todas as formigas estão seguindo o mesmo caminho
            double bestSolutionCounter = 0;
            boolean followedSamePath = false;
            
            for (Ant ant : this._Map.getAnts()) 
            {
            	followedSamePath = false;

                for (Trail trail : ant.getCrossedTrails())
                {
                    for (Trail bsfTrail : this._Map.getBestSoFarAnt().getCrossedTrails())
                    {
                        if (bsfTrail.Id == trail.Id)
                        {
                            followedSamePath = true;
                            break;
                        }
                    }

                    if (!followedSamePath)
                        break;
                }

                if (followedSamePath)
                    bestSolutionCounter++;
    		}

            double bestSolutionPercentage =
                bestSolutionCounter / (double)(this._Map.getAnts().size());

            if (bestSolutionPercentage >= this._Parameters.PercentageOfSuccess)
                return ++this._CounterSamePath >= 10;

            this._CounterSamePath = 0;
        }

        return false;
    }
}
