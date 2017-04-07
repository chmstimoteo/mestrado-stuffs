package tsp;

import java.util.ArrayList;

import aco.ACOColony;
import aco.IACOController;
import aco.IACOHeuristicResult;
import aco.IAntFood;
import aco.fundamentals.ACOGeneralHeuristicResult;
import aco.fundamentals.parameters.ColonyParameters;

public class TSPACOController implements IACOController
{
    public TSPACOController(ArrayList<TSPPoint> cities)
    {
        this.Cities = cities;
    }

    public ArrayList<TSPPoint> Cities;
    
    //M�todo que inicia a simula��o
    public IACOHeuristicResult Simulate(ColonyParameters parameters)
    {
        if (this.Cities == null || this.Cities.size() == 0)
            return null;

        ACOColony colony = new ACOColony(parameters, this);
        ArrayList<IAntFood> food = new ArrayList<IAntFood>(); //this.Cities.ConvertAll<IAntFood>(c => (IAntFood)c);
        
        for (IAntFood c : this.Cities) 
        	food.add(c);
        
        IACOHeuristicResult result = colony.Simulate(food);

        return result;
    }

    //M�todo chamado por cada formiga em cada itera��o para avaliar a solu��o encontrada
    //no caso do TSP, o que esse m�todo faz � calcular a dist�ncia percorrida
    public IACOHeuristicResult ProccessHeuristic(ArrayList<IAntFood> antFood)
    {
    	ArrayList<TSPPoint> points = new ArrayList<TSPPoint>();// antFood.ConvertAll<TSPPoint>(f => (TSPPoint)f);

    	for (IAntFood f : antFood) 
    		points.add((TSPPoint)f);
    	
        double result = TSPPoint.CalculateDistanceBetweenCities(points);
        ArrayList<String> visitedPlaces = new ArrayList<String>();// points.ConvertAll<string>(p => p.ToString());
        
        for (TSPPoint p : points) 
        	visitedPlaces.add(p.toString());

        return new ACOGeneralHeuristicResult(visitedPlaces, result);
    }
}
