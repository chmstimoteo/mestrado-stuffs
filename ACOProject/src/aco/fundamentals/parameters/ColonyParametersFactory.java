package aco.fundamentals.parameters;

import aco.fundamentals.EACODomain;
import aco.fundamentals.EColonyAlgorithm;

public class ColonyParametersFactory
{
    public static ColonyParameters Create(EColonyAlgorithm algorithm, EACODomain domain)
    {
        ColonyParameters parameters = null;

        if (algorithm == EColonyAlgorithm.AntSystem)
        	parameters = new ASParameters(domain);
        else if (algorithm == EColonyAlgorithm.ElitistAntSystem)
        	parameters = new EASParameters(domain);
        else if (algorithm == EColonyAlgorithm.AntColonySystem)
        	parameters = new ACSParameters(domain);

        return parameters;
    }
    
    public static ColonyParameters BuildColonyParameterSample(EColonyAlgorithm algorithm, EACODomain domain)
    {
        ColonyParameters parameters = ColonyParametersFactory.Create(algorithm, domain);

        if (algorithm == EColonyAlgorithm.AntSystem)
        {
            parameters.Alpha = 1;
            parameters.Beta = 5;
            parameters.ColonySize = 10;
            parameters.GlobalEvaporationCoefficient = 0.5;
            parameters.LimitIterations = 100;
            parameters.PercentageOfSuccess = 0.9;
            parameters.PheromoneConstant = 1;
        }
        else if (algorithm == EColonyAlgorithm.ElitistAntSystem)
        {
            parameters.Alpha = 1;
            parameters.Beta = 5;
            parameters.ColonySize = 10;
            parameters.GlobalEvaporationCoefficient = 0.5;
            parameters.LimitIterations = 100;
            parameters.PercentageOfSuccess = 0.9;
            parameters.PheromoneConstant = 1;
            ((EASParameters)parameters).BestSoFarTourReinforcement = 0.2;
        }
        else if (algorithm == EColonyAlgorithm.AntColonySystem)
        {
            parameters.Alpha = 0.1;
            parameters.Beta = 2;
            parameters.ColonySize = 10;
            parameters.GlobalEvaporationCoefficient = 0.1;
            ((ACSParameters)parameters).IntensificateProbability = 0.9;
            parameters.LimitIterations = 100;
            ((ACSParameters)parameters).LocalEvaporationCoefficient = 0.1;
            parameters.PercentageOfSuccess = 0.9;
            parameters.PheromoneConstant = 1;
        }

        return parameters;
    }
}
