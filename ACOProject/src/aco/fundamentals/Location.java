package aco.fundamentals;

import java.util.ArrayList;

import aco.IAntFood;

public class Location 
{
	public Location(IAntFood antFood)
    {
        this.Id = ++_IncrId;
        this.AntFood = antFood;
    }
	
	private static int _IncrId = 0;
	public int Id;
    public IAntFood AntFood;
    public ArrayList<Trail> Trails;
}
