package aco.fundamentals;

import java.util.Random;

public class TrailsSorter
{
	private static Random _R = new Random(123456789);
	
	public static int Compare(Trail x, Trail y)
    {
        boolean areEquals =
            (x != null && y != null && x.equals(y))
            || (x == null && y == null);

        return areEquals ?
            0 :
            (_R.nextDouble() > 0.5 ? 1 : -1);
    }
}
