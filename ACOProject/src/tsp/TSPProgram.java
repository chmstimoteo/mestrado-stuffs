package tsp;

import java.util.ArrayList;

import aco.IACOController;
import aco.fundamentals.EACODomain;
import aco.fundamentals.EColonyAlgorithm;
import aco.fundamentals.parameters.ColonyParameters;
import aco.fundamentals.parameters.ColonyParametersFactory;

public class TSPProgram 
{
	public static void main(String[] args)
    {
		ArrayList<TSPPoint> cities = TSPPoint.Build8Faces();

        IACOController controller = new TSPACOController(cities);
        ColonyParameters parameters = ColonyParametersFactory.BuildColonyParameterSample(EColonyAlgorithm.AntColonySystem, EACODomain.BestWay);
        controller.Simulate(parameters);
    }
}
