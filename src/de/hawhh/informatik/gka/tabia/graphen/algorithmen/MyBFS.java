package de.hawhh.informatik.gka.tabia.graphen.algorithmen;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import de.hawhh.informatik.gka.tabia.graphen.material.JungGraph;
import de.hawhh.informatik.gka.tabia.graphen.material.MyOwnVertex;

public class MyBFS
{
	private JungGraph mygraph;
	private MyOwnVertex startvertex;
	private MyOwnVertex endvertex;
    private int counter=0;
    private String weg="";
    boolean found;
    private int degree=0;
    private Map<MyOwnVertex, Integer> nachbarn;
	private ArrayList<MyOwnVertex> path;

    public MyBFS(JungGraph graph, MyOwnVertex start, MyOwnVertex ende)
    {
    	assert graph != null : "Vorbedingung verletzt: graph != null";
    	assert start != null : "Vorbedingung verletzt: startKnoten != null";
    	assert ende != null : "Vorbedingung verletzt: EndKnoten != null";
    	this.mygraph = graph;
    	this.startvertex = start;
    	this.endvertex = ende;
    	this.weg=start.getName();
    	nachbarn = new HashMap<>();
    	nachbarn.put(start,degree);
    	degree++;
    	path = new ArrayList<>();
    }
    
    // Sucht ob es ein Weg von Source nach Target gibt
    public boolean sucheTarget(MyOwnVertex selekted)
    {
    	if (selekted.equals(endvertex)) // Wenn das Target erreicht ist, dann gibt es einen Weg
    		return true;
    	else 
    	{
    	Collection<MyOwnVertex> neighbours = mygraph.getMygraph().getSuccessors(selekted); // Suche alle benachbarten vom Source
    	for (MyOwnVertex v : neighbours)
    	{
    		nachbarn.put(v,degree); // Fuege alle Benachbarten mit einem Index
    		degree++;
    		if (v.equals(endvertex)) // Soll einer der Benachbarten das Target sein
    		{
    			System.out.println("Fertig");
    	    	sucheShortPath(endvertex); // Suche nach dem kürzesten Weg
    	    	System.out.println(nachbarn.toString()); // Debug
    	    	found=true; // Gefunden
    			return true;
    		}
    	}
    	// Wenn keiner der Benachbarten das Target ist, dann wiederhole das gleiche mit allen Benachbarten.
    	for (MyOwnVertex v : neighbours)
    	{
    		sucheTarget(v); // Die Suche mit je ein von den Benachbarten
    	}
    	return found;
    	}
    }
    public boolean sucheShortPath(MyOwnVertex t)
    {
    	if (!t.equals(startvertex))
    	{
    		MyOwnVertex target = t;	
    		Collection<MyOwnVertex> predecessor = mygraph.getMygraph().getPredecessors(target);
    		for (MyOwnVertex v : predecessor)
    		{
       			 path.add(v);
    			 return sucheShortPath(v);
    		}
    	}
		return false;
    }

	public JungGraph getMygraph()
	{
		return mygraph;
	}

	public MyOwnVertex getStartvertex()
	{
		return startvertex;
	}

	public MyOwnVertex getEndvertex()
	{
		return endvertex;
	}


	public int getCounter()
	{
		return counter;
	}

	public String getWeg()
	{
		return weg;
	}
	
	public ArrayList<MyOwnVertex> getShortPath()
	{
		return path;
	}
}