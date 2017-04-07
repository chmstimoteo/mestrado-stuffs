package aco.fundamentals.behavior;

import aco.fundamentals.EACODomain;

public class SubsetSelectionDomainBehavior extends DomainBehavior
{
	public boolean getAntsVisitOnlyPartOfTheLocations() {
		return true;
	}
	public EACODomain getDomain() {
		return EACODomain.SubsetSelection;
	}
	public EStopCondition getStopCondition() {
		return EStopCondition.LimitIterations;
	}
}