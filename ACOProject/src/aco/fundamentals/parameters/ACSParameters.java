package aco.fundamentals.parameters;

import aco.fundamentals.EACODomain;
import aco.fundamentals.EColonyAlgorithm;

public class ACSParameters extends ColonyParameters
{
	public ACSParameters(EACODomain domain)
    {
        super(domain);
    }
	
	public double IntensificateProbability;
	public double LocalEvaporationCoefficient;
	
	public EColonyAlgorithm getAlgorithm() 
	{	
		return EColonyAlgorithm.AntColonySystem;
	}
}
