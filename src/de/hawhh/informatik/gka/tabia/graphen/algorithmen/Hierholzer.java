package de.hawhh.informatik.gka.tabia.graphen.algorithmen;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;

import de.hawhh.informatik.gka.tabia.graphen.material.Eulerkreis;
import de.hawhh.informatik.gka.tabia.graphen.material.JungGraph;
import de.hawhh.informatik.gka.tabia.graphen.material.MyOwnEdge;
import de.hawhh.informatik.gka.tabia.graphen.material.MyOwnVertex;
import de.hawhh.informatik.gka.tabia.graphen.material.RandomEulerGraph;

public class Hierholzer
{
	private JungGraph graph;
	private HashSet<MyOwnEdge> visitedEdges;
	private List<MyOwnVertex> subtourVertices;
	private List<MyOwnEdge> subtourEdges;
	private List<MyOwnVertex> tourVertices;
	private List<MyOwnEdge> tourEdges;
	private MyOwnVertex start;

	
	public Hierholzer(JungGraph graph)
	{
		this.graph = graph;
		this.visitedEdges = new HashSet<MyOwnEdge>();
		this.subtourVertices = new LinkedList<MyOwnVertex>();
		this.subtourEdges = new LinkedList<MyOwnEdge>();
		this.tourVertices = new LinkedList<MyOwnVertex>();
		this.tourEdges = new LinkedList<MyOwnEdge>();
	}
	
	// Generiert eine Random Number zwichen min und max
	public int generateRandomNumber(int max, int min)
	{
		return (int) (Math.random() * (max - min) + min);
	}
	
	public MyOwnVertex getRandomStart()
	{

		Object[] array = graph.getVertices().toArray();
		int randomIndex;
		MyOwnVertex target;
		randomIndex = generateRandomNumber(graph.getVertices().size(),1);
		target = (MyOwnVertex) array[randomIndex-1];
		return target;
	}
	
	public MyOwnEdge getRandomUnvisitedEdge(MyOwnVertex vertex)
	{
		Object[] array = graph.edgeSet().toArray();
		List<MyOwnEdge> edges = new LinkedList<MyOwnEdge>();
		for(int i = 0; i < array.length; i++)
		{
			MyOwnEdge target;
			target = (MyOwnEdge) array[i];
			if(!visitedEdges.contains(target))
			{
				if(target.source() == vertex || target.target() == vertex)
				{
					edges.add(target);
				}
			}
		}
		int randomIndex;
		randomIndex = generateRandomNumber(edges.size(),1);
		MyOwnEdge edge = edges.get(randomIndex);
		return edge;
	}
	
	public List<MyOwnVertex> start()
	{	
		List<MyOwnEdge> kantenliste = new LinkedList<MyOwnEdge>();
		kantenliste.addAll(graph.edgeSet());
		
		if(Eulerkreis.isEulerKreis(kantenliste, graph))
		{
			MyOwnVertex aktiv;
			MyOwnEdge edge;
			
			do
			{
				start = getRandomStart();
				aktiv = start;
				
				do
				{
					edge = getRandomUnvisitedEdge(aktiv);
					visitedEdges.add(edge);
					subtourVertices.add(aktiv);
					subtourEdges.add(edge);
					
					if(edge.target() == aktiv)
					{
						aktiv = edge.source();
					}
					else
					{
						aktiv = edge.target();
					}
				}
				while(aktiv != start);
				
				tourVertices.addAll(subtourVertices);
				tourEdges.addAll(subtourEdges);
			}
			while(!Eulerkreis.isEulerKreis(tourEdges, graph));
			
			return tourVertices;
		}
		return null;
	}
	
	public static void main(String[] args)
	{
		RandomEulerGraph testgraph = new RandomEulerGraph("#undirected", 5);
		Hierholzer algorithmus = new Hierholzer(testgraph.graph());
		System.out.println(algorithmus.start().toString());
	}
}
