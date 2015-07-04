package de.hawhh.informatik.gka.tabia.graphen.material;

import java.util.ArrayList;
import java.util.Collection;

import de.hawhh.informatik.gka.tabia.graphen.werkzeug.JungWerkzeug;
import de.hawhh.informatik.gka.tabia.graphen.service.Random;

public class RandomEulerGraph
{
	private JungGraph graph;
	private int maxKnoten;
	private Collection<MyOwnVertex> knotenSet;
	
	public RandomEulerGraph(int maxKnoten)
	{
		graph = new JungGraph("#undirected");
		this.maxKnoten = maxKnoten;
	}
	
	public RandomEulerGraph(String referenz)
	{
		graph = new JungGraph(referenz);
		this.maxKnoten = Random.generateRandomNumber(3, 100);
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
				temp = Random.generateRandomNumber(maxKnoten*3, 1);
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
		return ungeradeKnoten;
	}
	
	public MyOwnVertex getRandomVertex(ArrayList<MyOwnVertex> list)
	{
			int randomIndex;
			MyOwnVertex target;
			randomIndex = Random.generateRandomNumber(list.size(),0);
			target = list.get(randomIndex);
		
		return target;
	}
	
	// Erstellt einen Graphen mit n zufälligen Knoten und n zufälligen Kanten
	public void generateGraph()
	{

		generateKnoten();
		
		ArrayList<MyOwnVertex> list = new ArrayList<MyOwnVertex>(graph.vertexSet());
		
		// Vorbedingung jeder Knoten erreichbar
		for (int i=0; i< list.size()-1; i++)
		{
			graph.kanteEinfuegen(list.get(i), list.get(i+1), 0);
		}
		graph.kanteEinfuegen(list.get(0), list.get(list.size()-1), 0);
		
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
			// Falls eine Kante zwischen source und target vorhanden ist, wechsele source und target damit eine Mehrfach erstellt werden kann
				MyOwnEdge e = graph.getMygraph().findEdge(source, target);
				if (e == null)
					graph.kanteEinfuegen(source, target, 0);
				else
					graph.kanteEinfuegen(target, source, 0);
		}
		assert EulerkreisProperties.isEulerGraph(graph) : "Vorbedingung verletzt: EulerkreisProperties.isEulerGraph(graph)";
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
			randomIndex = Random.generateRandomNumber(maxKnoten,0);
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
		RandomEulerGraph random = new RandomEulerGraph(10);
		random.generateGraph();
		random.show();
	}
	
}
