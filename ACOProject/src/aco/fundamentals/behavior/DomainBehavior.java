package aco.fundamentals.behavior;

import aco.fundamentals.EACODomain;

public abstract class DomainBehavior
{
    public abstract boolean getAntsVisitOnlyPartOfTheLocations();
    public abstract EStopCondition getStopCondition();
    public abstract EACODomain getDomain();
}
