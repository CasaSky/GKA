package de.hawhh.informatik.gka.tabia.graphen.service;

public class Laufzeit
{
	private long before;
	private long after;
	private double laufzeitMS;
	private double laufzeitS;
	
	public void start()
	{
		before = System.nanoTime();
	}
	
	public void stop()
	{
		after = System.nanoTime();
		laufzeitMS = (after-before)/1000000;
		laufzeitS = (after-before)/1000000000.0;
	}
	
	public String toString()
	{
		return "Laufzeit in MS: "+laufzeitMS+"\nLaufzeit in S: "+laufzeitS;
	}
}
