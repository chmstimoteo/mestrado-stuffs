package tsp;

import java.util.ArrayList;

import aco.IAntFood;

public class TSPPoint implements IAntFood
{
    public TSPPoint(int x, int y)
    {
        this.Id = _IncrId++;

        this.X = x;
        this.Y = y;
    }

    public int Id;
    public int X;
    public int Y;
    private static int _IncrId = 0;
    
    public String toString()
    {
    	return "Id: " + Id + "; Posição: (" + X + ", " + Y + ")";
    }

	public int getId() 
	{
		return this.Id;
	}
	
	public static double CalculateDistanceBetweenCities(ArrayList<TSPPoint> points)
    {
        double result = 0;

        TSPPoint p1 = null;
        TSPPoint p2 = null;

        {
            double tempResult = 0;
            for (int i = 0; i <= points.size() - 2; i++)
            {
                p1 = points.get(i);
                p2 = points.get(i + 1);

                tempResult = Math.pow(p1.X - p2.X, 2) + Math.pow(p1.Y - p2.Y, 2);
                tempResult = Math.sqrt(tempResult);
                result += tempResult;
            }
        }

        return result;
    }
	
	public static ArrayList<TSPPoint> BuildSquare()
    {
		ArrayList<TSPPoint> list = new ArrayList<TSPPoint>();

        list.add(new TSPPoint(0, 0));
        list.add(new TSPPoint(100, 0));
        list.add(new TSPPoint(0, 100));
        list.add(new TSPPoint(100, 100));

        return list;
    }

    public static ArrayList<TSPPoint> Build8Faces()
    {
    	ArrayList<TSPPoint> list = new ArrayList<TSPPoint>();

        list.add(new TSPPoint(0, 30)); //1
        list.add(new TSPPoint(30, 0)); //8
        list.add(new TSPPoint(0, 60)); //2
        list.add(new TSPPoint(90, 60)); //5
        list.add(new TSPPoint(30, 90)); //3
        list.add(new TSPPoint(60, 0)); //7
        list.add(new TSPPoint(60, 90)); //4
        list.add(new TSPPoint(90, 30)); //6

        return list;
    }
}
