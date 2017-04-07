package aco.fundamentals.maps;

import aco.IACOController;
import aco.fundamentals.EColonyAlgorithm;
import aco.fundamentals.IAntColonyMap;
import aco.fundamentals.parameters.ACSParameters;
import aco.fundamentals.parameters.ASParameters;
import aco.fundamentals.parameters.ColonyParameters;
import aco.fundamentals.parameters.EASParameters;

public class AntColonyMapFactory 
{
	public static IAntColonyMap Create(ColonyParameters parameters, IACOController controller)
    {
        IAntColonyMap map = null;

        if (parameters.getAlgorithm() == EColonyAlgorithm.AntSystem)
            map = new ASAntColonyMap((ASParameters)parameters, controller);
        else if (parameters.getAlgorithm() == EColonyAlgorithm.ElitistAntSystem)
            map = new EASAntColonyMap((EASParameters)parameters, controller);
        else if (parameters.getAlgorithm() == EColonyAlgorithm.AntColonySystem)
            map = new ACSAntColonyMap((ACSParameters)parameters, controller);

        return map;
    }
}
