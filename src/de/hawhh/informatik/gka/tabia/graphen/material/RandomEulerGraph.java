package de.hawhh.informatik.gka.tabia.graphen.material;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import de.hawhh.informatik.gka.tabia.graphen.werkzeug.JungWerkzeug;

public class RandomEulerGraph
{

	private JungGraph graph;
	private int maxKnoten;
	private Collection<MyOwnVertex> knotenSet;
	
	public RandomEulerGraph(String referenz, int maxKnoten)
	{
		assert referenz != null : "Vorbedingung verletzt: referenz != null";
		graph = new JungGraph(referenz);
		this.maxKnoten = maxKnoten;
	}
	
	public RandomEulerGraph(String referenz)
	{
		graph = new JungGraph(referenz);
		this.maxKnoten = generateRandomNumber(3, 100);
	}
	
	public JungGraph graph()
	{
		return graph;
	}
	
	public Collection<MyOwnVertex> knotenSet()
	{
		return knotenSet;
	}
	
	// Generiert n zufälligen Knoten und fügt die im Graphen ein
	public void generateRandomKnoten()
	{
		for (int i=1; i< maxKnoten; i++)
		{
			int temp;
			MyOwnVertex v;
			do
			{
				temp = generateRandomNumber(maxKnoten*3, 1);
				 v = new MyOwnVertex(""+temp,temp);
			} while (graph.getVertices().contains(v));
			graph.knotenEinfuegen(Integer.toString(temp),temp);
		}
		// das Target
		graph.knotenEinfuegen("0", 0);
	}
	
	public void generateKnoten()
	{
		for (int i=1; i<= maxKnoten; i++)
		{
			graph.knotenEinfuegen(""+i);
		}
		// das Target
		//graph.knotenEinfuegen("0", 0);
	}
	
	// Generiert eine Random Number zwichen min und max
	public int generateRandomNumber(int max, int min)
	{
		return (int) (Math.random() * (max - min) + min);
	}
	
	public int generateRandomOddNumber(int max, int min)
	{
		if (max % 2 == 0) --max;
		if (min % 2 == 0) ++min;
		int random_No = min + 2*(int)(Math.random()*((max-min)/2+1));
		return random_No;
	}
	
	public ArrayList<MyOwnVertex> alleUngeradeKnoten()
	{
		ArrayList<MyOwnVertex> ungeradeKnoten = new ArrayList<MyOwnVertex>();
		for (MyOwnVertex v : graph.vertexSet())
		{
			if (graph.grad(v) % 2 != 0)
			{
				ungeradeKnoten.add(v);
			}
		}
		//System.out.println(ungeradeKnoten);
		return ungeradeKnoten;
	}
	
	public MyOwnVertex getRandomVertex(ArrayList<MyOwnVertex> list)
	{
			int randomIndex;
			MyOwnVertex target;
			randomIndex = generateRandomNumber(list.size(),0);
			target = list.get(randomIndex);
		
		return target;
	}
	
	// Erstellt einen Graphen mit n zufälligen Knoten und n zufälligen Kanten
	public void generateGraph()
	{

		generateKnoten();
		
		ArrayList<MyOwnVertex> list = new ArrayList<MyOwnVertex>(graph.vertexSet());
		
		// erzeugt ein zusammenhängenden Graphen
		for (int i=0; i<list.size()-1; i++)
		{
			graph.kanteEinfuegen(list.get(i), list.get(i+1), 0);
		}
		graph.kanteEinfuegen(list.get(list.size()-1), list.get(0), 0);
		
		// Fügt eine Kante von allen Knoten mit einem randomisierten Knoten hinzu
		for (MyOwnVertex v : graph.vertexSet())
		{
			MyOwnVertex target = getRandomVertex(v);
			graph.kanteEinfuegen(v, target, 0);
		}
		
		// Falls irgendwelche Knoten mit einem ungeraden Grad existieren, füge eine kante zwischen diese Knoten
		ArrayList<MyOwnVertex> ungeradeKnoten = alleUngeradeKnoten();
		while(!ungeradeKnoten.isEmpty())
		{
			MyOwnVertex source = getRandomVertex(ungeradeKnoten);
			ungeradeKnoten.remove(source);
			MyOwnVertex target = getRandomVertex(ungeradeKnoten);
			ungeradeKnoten.remove(target);
			MyOwnEdge edge = new MyOwnEdge(source, target, 0);
			// Falls eine Kante zwischen source und target vorhanden ist, wechsele source und target damit eine Mehrfach erstellt werden kann
			if (!graph.containsEdge(edge))
				graph.kanteEinfuegen(source, target, 0);
			else
				graph.kanteEinfuegen(target, source, 0);
		}
	}
	
	// Holt einen zufälligen Knoten aus dem Graphen
	public MyOwnVertex getRandomVertex(MyOwnVertex v)
	{
		knotenSet = graph.getVertices();
		Object[] array = knotenSet.toArray();
		int randomIndex;
		MyOwnVertex target;
		do
		{
			randomIndex = generateRandomNumber(maxKnoten,0);
			target = (MyOwnVertex) array[randomIndex];
		}while (target.equals(v));
		return target;
	}
	
	// Erstele eine GUI
	public void show ()
	{
		@SuppressWarnings("unused")
		JungWerkzeug werkzeug = new JungWerkzeug(graph);
	}
	
	public static void main(String[] args)
	{
		RandomEulerGraph random = new RandomEulerGraph("#undirected", 10);
		random.generateGraph();
		random.show();
	}
	
}
