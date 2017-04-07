package aco;

import java.util.ArrayList;

public interface IACOHeuristicResult 
{
	Double getHeuristicValue();
	IACOHeuristicResult Clone();
	ArrayList<String> GetVisitedOrderedAntFoods();
}
