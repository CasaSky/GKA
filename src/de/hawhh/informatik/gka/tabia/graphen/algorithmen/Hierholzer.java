package de.hawhh.informatik.gka.tabia.graphen.algorithmen;

import java.util.ArrayList;
import java.util.HashSet;
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
	
	public MyOwnVertex getRandomVertex(HashSet<MyOwnEdge> visited)
	{

	    List<MyOwnVertex> allVertices = new LinkedList<MyOwnVertex>();
	    allVertices.addAll(graph.getVertices());
	    
        Object[] array = nichtFertigeKnoten(allVertices ,visited);
        
        int randomIndex;
        MyOwnVertex target;
        randomIndex = generateRandomNumber(array.length,1);
        target = (MyOwnVertex) array[randomIndex];
        
        return target;
	}
	
	private Object[] nichtFertigeKnoten(List<MyOwnVertex> allVertices, HashSet<MyOwnEdge> visited)
    {
        List<MyOwnVertex> nichtFertigeKnoten = new LinkedList<MyOwnVertex>();
        int usedCounter = 0;
        
        for(MyOwnVertex v : allVertices)
        {
            for(MyOwnEdge e : visited)
            {
                if(e.target() == v || e.source() == v)
                {
                    ++usedCounter;
                }   
            }
            if(usedCounter < graph.grad(v))
            {
                nichtFertigeKnoten.add(v);
            }
        }
        return nichtFertigeKnoten.toArray();
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
<<<<<<< HEAD
		System.out.println("size"+edges.size());
		randomIndex = generateRandomNumber(edges.size()-1,0);
=======
		randomIndex = generateRandomNumber(edges.size(),0);
>>>>>>> origin/master
		MyOwnEdge edge = edges.get(randomIndex);
		
		return edge;
	}
	
	public List<MyOwnVertex> start()
	{	
		List<MyOwnEdge> kantenliste = new LinkedList<MyOwnEdge>();
		kantenliste.addAll(graph.edgeSet());
		List<MyOwnVertex> vertex = new LinkedList<MyOwnVertex>();
		
		if(EulerkreisProperties.isEulerKreis(kantenliste, graph))
		{
			MyOwnVertex aktiv;
			MyOwnEdge edge;
				
			do
			{				
			    /*if(Eulerkreis.isEulerKreis(tourEdges, graph))
			    {
			        break;
			    }
			    else
			    {*/
    			    start = getRandomVertex(visitedEdges);
    	            aktiv = start;
    	            System.out.print(start.toString()+ " ");
    			    
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
//			    }
			}
			while(!EulerkreisProperties.isEulerKreis(tourEdges, graph));
			
			return tourVertices;
		}
		return vertex;
	}
	
	public static void main(String[] args)
	{
<<<<<<< HEAD
		RandomEulerGraph testgraph = new RandomEulerGraph(5);
		testgraph.generateGraph();
=======
		RandomEulerGraph testgraph = new RandomEulerGraph("#undirected", 5);
		testgraph.generateGraph();
		testgraph.show();
>>>>>>> origin/master
		Hierholzer algorithmus = new Hierholzer(testgraph.graph());
		List<MyOwnVertex> list = new ArrayList<MyOwnVertex>();
		list = algorithmus.start();
		if (list !=null)
		System.out.println(list.toString());
	}
}
