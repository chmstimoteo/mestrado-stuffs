package aco.fundamentals;

public class Trail implements Comparable<Trail>
{
	public Trail(Location l1, Location l2)
    {
        this.Id = ++_IncrId;
        this.Pheromones = 0.005;
        this.L1 = l1;
        this.L2 = l2;
    }
	
	private static int _IncrId = 0;
	public Location L1;
	public Location L2;
	public double Pheromones;
    public int Id;
	
	public int compareTo(Trail o) 
	{	
		return
			TrailsSorter.Compare(this, o);
	}
}
