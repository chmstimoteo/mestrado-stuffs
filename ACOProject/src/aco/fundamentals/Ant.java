package aco.fundamentals;

import java.util.ArrayList;
import java.util.Collections;

import aco.IACOController;
import aco.IACOHeuristicResult;
import aco.IAntFood;

public class Ant 
{
	private Tour _CurrentTour;
    private Location _StartCity;
    
    public ArrayList<Trail> getCrossedTrails()
    {
    	return
	        this._CurrentTour != null ?
	        this._CurrentTour.Trails
	        :
	        null;
    }
    
    public Location CurrentLocation;
    public Integer LocationsToVisit;
    private int _getVisitedLocations()
    {
    	int visited = -1;

        if (this._CurrentTour != null && this._CurrentTour.Trails != null && this._CurrentTour.Trails.size() > 0)
            visited = this._CurrentTour.Trails.size() + 1; //O "+1" é porque a cidade inicial não adiciona trilha
        else if (this._StartCity != null)
            visited = 1;
        else 
            visited = 0;

        return visited;
    }
    public IACOHeuristicResult HeuristicResult;
    
    public void ProccessHeuristic(IACOController controller)
    {
        ArrayList<Location> locations = this.GetOrderedLocations();
        ArrayList<IAntFood> food = null;

        if (locations != null && locations.size() > 0)
        {
        	food = new ArrayList<IAntFood>();
        	
        	for (Location l : locations) 
        	{
				food.add(l.AntFood);
			}
        }

        this.HeuristicResult = controller.ProccessHeuristic(food);
    }
    
    public void EmptyTrailsList()
    {
        this._CurrentTour = new Tour();
        this.CurrentLocation = null;
    }
    public void WalkToLocation(Location newLocation)
    {
        Trail walkTrail = null;

        if (this.CurrentLocation != null)
        {
        	for (Trail t : this.CurrentLocation.Trails) 
        	{
        		if ((t.L2.Id == newLocation.Id)
                   ||
                   (t.L1.Id == newLocation.Id))
        		{
        			walkTrail = t;
        			break;
        		}
			}
        }
        else
            this._StartCity = newLocation;

        this.CurrentLocation = newLocation;

        if (walkTrail != null)
            this._CurrentTour.Trails.add(walkTrail);
    }
    
    public void WalkToLocation(Trail trail)
    {
        this.WalkToLocation(
            trail.L1.Id == this.CurrentLocation.Id ?
            trail.L2 : trail.L1);
    }
    
    public ArrayList<Trail> GetFeasibleTrails()
    {
        try
        {
            //este try catch é necessário por que o .NET ta cagando
            //na verificação do Equals para dois objetos de mesma referência.
            //esse bug ocorre por motivos insanos e obscuros.
            Collections.sort(this.CurrentLocation.Trails);
        }
        catch (Exception e)
        { }

        //carrega os lugares não visitados
        ArrayList<Trail> feasibleWays = new ArrayList<Trail>();

        if (this._getVisitedLocations() < this.LocationsToVisit)
        {
        	if (this.getCrossedTrails() == null || this.getCrossedTrails().size() == 0)
                return this.CurrentLocation.Trails;

        	 //carrega lugares ja visitados
            ArrayList<Integer> visitedLocationsIds = new ArrayList<Integer>();
            for (Trail t : this.getCrossedTrails())
            {
                if (!visitedLocationsIds.contains(t.L1.Id))
                    visitedLocationsIds.add(t.L1.Id);
                if (!visitedLocationsIds.contains(t.L2.Id))
                    visitedLocationsIds.add(t.L2.Id);
            }

            for (Trail trail : this.CurrentLocation.Trails)
            {
                if (!visitedLocationsIds.contains(trail.L1.Id) ||
                    !visitedLocationsIds.contains(trail.L2.Id))
                    feasibleWays.add(trail);
            }

            //Se ja tiver percorrido todas as cidades, retornar para a cidade natal
            if (feasibleWays.size() == 0 && this._StartCity != null)
            {
            	for (Trail t : this.CurrentLocation.Trails) 
            	{
					if (t.L1.Id == this._StartCity.Id ||
                        t.L2.Id == this._StartCity.Id)
                        {
                        	feasibleWays.add(t);
                        	break;
                        }
				}

                this._StartCity = null;
            }
        }

        return feasibleWays;
    }
    
    public ArrayList<Location> GetOrderedLocations()
    {
    	ArrayList<Location> orderedLocations =
            new ArrayList<Location>();

        if (this.getCrossedTrails().size() > 1)
        {
            Trail currentTrail = null;
            Trail nextTrail = null;

            //preencher a lista de orderedLocations com os from's de cada trilha
            for (int i = 0; i < this.getCrossedTrails().size() - 1; i++)
            {
                currentTrail = this.getCrossedTrails().get(i);
                nextTrail = this.getCrossedTrails().get(i + 1);

                if (currentTrail.L1.Id == nextTrail.L1.Id || currentTrail.L1.Id == nextTrail.L2.Id) //L1 é o "To" ? então adiciona o L2 primeiro
                {
                    orderedLocations.add(currentTrail.L2);
                }
                else
                {
                    orderedLocations.add(currentTrail.L1);
                }
            }

            //adicionando os dois últimos locations (é pra adicionar o to)
            int count = this.getCrossedTrails().size();
            Location lastLocationInserted = orderedLocations.get(orderedLocations.size() - 1);

            orderedLocations.add( //adiciona o "To" do ultimo location ordenado inserido
                this.getCrossedTrails().get(count - 2).L1.Id == lastLocationInserted.Id ?
                this.getCrossedTrails().get(count - 2).L2
                :
                this.getCrossedTrails().get(count - 2).L1);

            lastLocationInserted = orderedLocations.get(orderedLocations.size() - 1);

            orderedLocations.add( //adiciona o "To" do ultimo location da última trilha
                this.getCrossedTrails().get(count - 1).L1.Id == lastLocationInserted.Id ?
                this.getCrossedTrails().get(count - 1).L2
                :
                this.getCrossedTrails().get(count - 1).L1);
        }
        else if (this.getCrossedTrails().size() == 1)
        {
            if (this.getCrossedTrails().get(0).L1 != null)
                orderedLocations.add(this.getCrossedTrails().get(0).L1);

            if (this.getCrossedTrails().get(0).L2 != null)
                orderedLocations.add(this.getCrossedTrails().get(0).L2);
        }
        else
            orderedLocations.add(this.CurrentLocation);

        return orderedLocations;
    }
    public Ant Clone()
    {
        Ant ant = new Ant();
        ant._CurrentTour = new Tour();
        ant._CurrentTour.Trails = new ArrayList<Trail>(this._CurrentTour.Trails);
        ant.HeuristicResult = this.HeuristicResult.Clone();
        ant.LocationsToVisit = this.LocationsToVisit;
        ant.CurrentLocation = this.CurrentLocation;
        ant._StartCity = this._StartCity;

        return ant;
    }
}
