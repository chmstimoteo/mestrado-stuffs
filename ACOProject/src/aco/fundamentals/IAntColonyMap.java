package aco.fundamentals;

import java.util.ArrayList;

import aco.IAntFood;

public interface IAntColonyMap 
{
	void BuildLocationsTrails(ArrayList<IAntFood> antFood);
    void BuildPaths();
    void DropAnts();
    void EvaluateSolutions();
    void SetInitialPheromones();
    void SetTauValue();
    void DropPheromones();
    void EvaporatePheromones();
    Ant getBestSoFarAnt();
    ArrayList<Ant> getAnts();
}
