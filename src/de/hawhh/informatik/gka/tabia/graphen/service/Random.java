package de.hawhh.informatik.gka.tabia.graphen.service;

public final class Random
{
	// Generiert eine Random Number zwichen min und max
	public static int generateRandomNumber(int max, int min)
	{
		return (int) (Math.random() * (max - min) + min);
	}
}
