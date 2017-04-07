package aco.fundamentals.behavior;

import aco.fundamentals.EACODomain;

public class DomainBehaviorFactory
{
    public static DomainBehavior Create(EACODomain domain)
    {
        DomainBehavior behavior = null;

        if (domain == EACODomain.SubsetSelection)
            behavior = new SubsetSelectionDomainBehavior();
        else if (domain == EACODomain.BestWay)
            behavior = new BestWayDomainBehavior();

        return behavior;
    }
}