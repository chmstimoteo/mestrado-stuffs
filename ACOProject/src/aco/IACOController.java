package aco;

import java.util.ArrayList;

import aco.fundamentals.parameters.ColonyParameters;

public interface IACOController 
{
	IACOHeuristicResult ProccessHeuristic(ArrayList<IAntFood> antFood);
    IACOHeuristicResult Simulate(ColonyParameters parameters);
}
