package aco.fundamentals.behavior;
import aco.fundamentals.EACODomain;

public class BestWayDomainBehavior extends DomainBehavior
{
	public boolean getAntsVisitOnlyPartOfTheLocations() {
		return false;
	}
	public EACODomain getDomain() {
		return EACODomain.BestWay;
	}
	public EStopCondition getStopCondition() {
		return EStopCondition.LimitIterations;
	}
}