package de.hawhh.informatik.gka.tabia.graphen.algorithmen;

import java.util.LinkedList;
import java.util.List;

import de.hawhh.informatik.gka.tabia.graphen.material.EulerkreisProperties;
import de.hawhh.informatik.gka.tabia.graphen.material.JungGraph;
import de.hawhh.informatik.gka.tabia.graphen.material.MyOwnEdge;
import de.hawhh.informatik.gka.tabia.graphen.material.MyOwnVertex;
import de.hawhh.informatik.gka.tabia.graphen.material.RandomEulerGraph;

public class Hierholzer
{
	private JungGraph graph;
	private List<MyOwnEdge> visitedEdges;
	private MyOwnEdge randomUnvisitedEdge;
	private MyOwnVertex randomStart;
	private List<MyOwnVertex> subtourVertices;
	private List<MyOwnEdge> subtourEdges;
	private List<MyOwnVertex> tourVertices;
	private List<MyOwnEdge> tourEdges;
	private MyOwnVertex start;
	List<MyOwnVertex> nichtFertigeKnoten;

	
	public Hierholzer(JungGraph graph)
	{
		this.graph = graph;
		this.visitedEdges = new LinkedList<MyOwnEdge>();
		this.subtourVertices = new LinkedList<MyOwnVertex>();
		this.subtourEdges = new LinkedList<MyOwnEdge>();
		this.tourVertices = new LinkedList<MyOwnVertex>();
		this.tourEdges = new LinkedList<MyOwnEdge>();
		this.randomUnvisitedEdge = null;
		this.randomStart = null;
	}
	
	// Generiert eine Random Number zwichen min und max
	public int generateRandomNumber(int max, int min)
	{
		return (int) (Math.random() * (max - min) + min);
	}
	
	public List<MyOwnVertex> nichtFertigeKnoten(List<MyOwnVertex> allVertices, List<MyOwnEdge> visited)
	{
		nichtFertigeKnoten = new LinkedList<MyOwnVertex>();
		int counter = 0;
		
		for(MyOwnVertex v : allVertices)
		{
			for(MyOwnEdge e : visited)
			{
				if(e.source().equals(v) || e.target().equals(v))
				{
					++counter;
				}
			}
			if(graph.grad(v) > counter)
			{
				nichtFertigeKnoten.add(v);
			}
		}
		
		
		return nichtFertigeKnoten;
	}
	
	public void searchRandomStart()
	{

		List<MyOwnVertex> allVertices = new LinkedList<MyOwnVertex>(graph.getVertices());
		
		List<MyOwnVertex> nichtFertigeKnoten= nichtFertigeKnoten(allVertices, visitedEdges);
		int randomIndex;
		
		if(nichtFertigeKnoten.size() > 0)
		{
			randomIndex = generateRandomNumber(nichtFertigeKnoten.size(),0);
			randomStart = (MyOwnVertex) nichtFertigeKnoten.get(randomIndex);
		}
	}
	
	public void searchRandomUnvisitedEdge(MyOwnVertex vertex)
	{
		List<MyOwnEdge> edgeList = new LinkedList<MyOwnEdge>(graph.edgeSet());
		List<MyOwnEdge> unvisitedEdges = new LinkedList<MyOwnEdge>();
		
		for(int i = 0; i < edgeList.size(); i++)
		{
			MyOwnEdge target;
			target = edgeList.get(i);
			if(!visitedEdges.contains(target))
			{
				if(target.source().equals(vertex) || target.target().equals(vertex))
				{
					unvisitedEdges.add(target);
				}
			}
		}
		
		int randomIndex;
		if(unvisitedEdges.size() > 0)
		{
			randomIndex = generateRandomNumber(unvisitedEdges.size(),0);
			randomUnvisitedEdge = unvisitedEdges.get(randomIndex);
		}
	}
	
	public void start()
	{			
		if(EulerkreisProperties.isEulerGraph(graph))
		{
			MyOwnVertex aktiv;
			MyOwnEdge edge;
			
			do
			{
				searchRandomStart();
				start = getRandomStart();
				aktiv = start;
				
				do
				{
					searchRandomUnvisitedEdge(aktiv);
					edge = getRandomUnvisitedEdge();
					visitedEdges.add(edge);
					
					subtourVertices.add(aktiv);
					subtourEdges.add(edge);
					
					if(edge.target().equals(aktiv))
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
				tourVertices.add(null);
				tourEdges.addAll(subtourEdges); //null entfernen und eulertour einfügen, rekursiv.
				tourEdges.add(null);
			}
			while(nichtFertigeKnoten.size() > 0);
		}
		else
		{
			System.err.println("Dies ist kein Eulergraph");
		}
	}
	
	public List<MyOwnVertex> getTourVertices()
	{
		return tourVertices;
	}
	
	public MyOwnEdge getRandomUnvisitedEdge()
	{
		return randomUnvisitedEdge;
	}
	
	public MyOwnVertex getRandomStart()
	{
		return randomStart;
	}
	
	public static void main(String[] args)
	{
		RandomEulerGraph testgraph = new RandomEulerGraph(5);
		testgraph.generateGraph();
		testgraph.show();
		Hierholzer algorithmus = new Hierholzer(testgraph.graph());
		algorithmus.start();
		System.out.println(algorithmus.getTourEdges().toString());
	}

	public List<MyOwnEdge> getVisitedEdges()
	{
		return visitedEdges;
	}

	public List<MyOwnEdge> getTourEdges()
	{
		return tourEdges;
	}
}
