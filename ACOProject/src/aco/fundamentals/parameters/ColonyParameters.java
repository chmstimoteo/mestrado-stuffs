package aco.fundamentals.parameters;

import aco.fundamentals.EACODomain;
import aco.fundamentals.EColonyAlgorithm;
import aco.fundamentals.behavior.DomainBehavior;
import aco.fundamentals.behavior.DomainBehaviorFactory;

public abstract class ColonyParameters 
{
	public ColonyParameters(EACODomain domain)
    {
        this.Domain = DomainBehaviorFactory.Create(domain);
    }
	
	public Integer LimitIterations;
    public abstract EColonyAlgorithm getAlgorithm();
    public int ColonySize;
    public double PheromoneConstant;
    public double GlobalEvaporationCoefficient;
    public double Alpha;
    public double Beta;
    public double PercentageOfSuccess;
    public DomainBehavior Domain;
}
