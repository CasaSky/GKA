package de.hawhh.informatik.gka.tabia.graphen.service;

public class Laufzeit
{
	private long before;
	private long after;
	private double laufzeitS;
	
	public void start()
	{
		before = System.currentTimeMillis();
	}
	
	public void stop()
	{
		after = System.currentTimeMillis();
		laufzeitS = (after-before)/1000.0;
	}
	
	public String toString()
	{
		return "Laufzeit in S: "+laufzeitS;
	}
}
