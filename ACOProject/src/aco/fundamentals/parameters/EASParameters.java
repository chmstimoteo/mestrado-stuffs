package aco.fundamentals.parameters;

import aco.fundamentals.EACODomain;
import aco.fundamentals.EColonyAlgorithm;

public class EASParameters extends ColonyParameters 
{
	public EASParameters(EACODomain domain)
    {
        super(domain);
    }
	
	public double BestSoFarTourReinforcement;
	
	public EColonyAlgorithm getAlgorithm() 
	{
		return EColonyAlgorithm.ElitistAntSystem;
	}
}
