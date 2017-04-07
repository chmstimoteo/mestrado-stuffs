package common;

import java.util.Date;

public class JavaTimeSpan 
{	
	public double Days;
	public double Hours;
	public double Minutes;
	public double Seconds;
	
	public static JavaTimeSpan SubtractDates(Date start, Date end)
	{
		JavaTimeSpan timespan = new JavaTimeSpan(); 
		
		long diffInSeconds = (end.getTime() - start.getTime()) / 1000;

	    timespan.Seconds = (diffInSeconds >= 60 ? diffInSeconds % 60 : diffInSeconds);
	    timespan.Minutes = (diffInSeconds = (diffInSeconds / 60)) >= 60 ? diffInSeconds % 60 : diffInSeconds;
	    timespan.Hours = (diffInSeconds = (diffInSeconds / 60)) >= 24 ? diffInSeconds % 24 : diffInSeconds;
	    timespan.Days = (diffInSeconds = (diffInSeconds / 24));

	    return timespan;
	}
}
