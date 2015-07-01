package de.hawhh.informatik.gka.tabia.graphen.material;

import java.util.ArrayList;
import java.util.Collection;

import edu.uci.ics.jung.graph.DirectedSparseMultigraph;
import edu.uci.ics.jung.graph.Graph;
import edu.uci.ics.jung.graph.UndirectedSparseMultigraph;

public class JungGraph
{
	private Graph<MyOwnVertex, MyOwnEdge> mygraph;
	private String referenz;
	
	public JungGraph(String referenz)
	{
		this.referenz = referenz;
		mygraph = erstelleGraphNachReferenz(this.referenz);
	}
	
	public int grad(MyOwnVertex v)
	{
		return mygraph.degree(v);
	}
	
	//fügt einen Knoten an dem Graphen hinzu
	public void knotenEinfuegen(String name, int attribute)
	{
		mygraph.addVertex(new MyOwnVertex(name, attribute));
	}
	
	//fügt eine kante an zwei Knoten hinzu, wo bei es muss gepfrügt werden ob die Knoten zu erst enthalten sind, wenn nicht, die müssen erstellt werden
	public void kanteEinfuegen(MyOwnVertex v1, MyOwnVertex v2, int gewicht)
	{
		mygraph.addEdge(new MyOwnEdge(v1,v2,gewicht), v1, v2);
	}
	
	public void listToGraph(ArrayList<String> list)
	{
	    if (getReferenz().equals("#weighted") || getReferenz().equals("#directed #weighted"))
		    listWToGraph(list);
	    else if (getReferenz().equals("#attributed #weighted"))
	    	listAToGraph(list);
	    else
	    	listVEToGraph(list);
	}
	public void listVEToGraph(ArrayList<String> list)
	{
		int counter=0;
		do
		{
			kanteEinfuegen(new MyOwnVertex(list.get(counter)), new MyOwnVertex(list.get(counter+1)), 1);
			counter+=2;
		}while(counter<list.size());
	}
	
	public void listWToGraph(ArrayList<String> list)
	{
		int counter=0;
		do
		{
			kanteEinfuegen(new MyOwnVertex(list.get(counter)), new MyOwnVertex(list.get(counter+1)), Integer.parseInt(list.get(counter+2)));
			counter+=3;
		}while(counter<list.size());
	}
	
	public void listAToGraph(ArrayList<String> list)
	{
		int counter=0;
		do
		{
			kanteEinfuegen(new MyOwnVertex(list.get(counter), Integer.parseInt(list.get(counter+1))), new MyOwnVertex(list.get(counter+2), Integer.parseInt(list.get(counter+3))), Integer.parseInt(list.get(counter+4)));
			counter+=5;
		}while(counter<list.size());
	}
	
	public Graph<MyOwnVertex, MyOwnEdge> erstelleGraphNachReferenz(String referenz)
	{
		if (referenz.equals("#directed") || referenz.equals("#directed #weighted"))
			setMygraph(new DirectedSparseMultigraph<MyOwnVertex, MyOwnEdge>());
		else if (referenz.equals("#undirected") || referenz.equals("#weighted") || referenz.equals("#attributed #weighted"))
			setMygraph(new UndirectedSparseMultigraph<MyOwnVertex, MyOwnEdge>());
		return getMygraph();
	}
	
	//gibt einen Graphen zurück
	public Graph<MyOwnVertex, MyOwnEdge> getMygraph()
	{
		assert mygraph != null : "Nachbedingung verletzt: graph != null";
		return mygraph;
	}
	
	//ändert den Graphen
	public void setMygraph(Graph<MyOwnVertex, MyOwnEdge> graph)
	{
		assert graph != null : "Vorbedingung verletzt: graph != null";
		this.mygraph = graph;
	}

	public Collection<MyOwnVertex> vertexSet()
	{
		return mygraph.getVertices();
	}

	public Collection<MyOwnEdge> edgeSet()
	{
		return mygraph.getEdges();
	}
	
	public boolean containsEdge(MyOwnEdge edge)
	{
		return mygraph.containsEdge(edge);
	}
	
	public String getReferenz()
	{
		return referenz;
	}

	public Collection<MyOwnVertex> getNeighbors(MyOwnVertex v)
	{
		return mygraph.getNeighbors(v);
	}
	
	public Collection<MyOwnVertex> getVertices()
	{
		return mygraph.getVertices();
	}

	public void knotenEinfuegen(String string)
	{
		mygraph.addVertex(new MyOwnVertex(string));
		
	}
}
