package aco.fundamentals.parameters;

import aco.fundamentals.EACODomain;
import aco.fundamentals.EColonyAlgorithm;

public class ASParameters extends ColonyParameters
{
	public ASParameters(EACODomain domain)
    {
        super(domain);
    }
	public EColonyAlgorithm getAlgorithm() 
	{
		return EColonyAlgorithm.AntSystem;
	}

}
