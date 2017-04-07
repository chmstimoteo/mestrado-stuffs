package aco.fundamentals;

import java.util.ArrayList;

import aco.IACOHeuristicResult;

public class ACOGeneralHeuristicResult implements IACOHeuristicResult
{
    public ACOGeneralHeuristicResult(ArrayList<String> visitedOrederedAntFood, Double heuristicValue)
    {
        this.VisitedOrderedAntFoods = visitedOrederedAntFood;
        this.HeuristicValue = heuristicValue;
    }

    public ArrayList<String> VisitedOrderedAntFoods;
    public Double HeuristicValue;

    public IACOHeuristicResult Clone()
    {
        ArrayList<String> visitedOrederedAntFood = null;
        if (this.VisitedOrderedAntFoods != null && this.VisitedOrderedAntFoods.size() > 0)
        {
            visitedOrederedAntFood = new ArrayList<String>();
            for (String item : this.VisitedOrderedAntFoods)
                visitedOrederedAntFood.add(item);
        }

        Double h = null;

        if (this.HeuristicValue != null)
            h = new Double(this.HeuristicValue.doubleValue());

        ACOGeneralHeuristicResult clone = new ACOGeneralHeuristicResult(visitedOrederedAntFood, h);

        return clone;
    }

    public ArrayList<String> GetVisitedOrderedAntFoods()
    {
        return this.VisitedOrderedAntFoods;
    }

	public Double getHeuristicValue() 
	{
		return this.HeuristicValue;
	}
}
