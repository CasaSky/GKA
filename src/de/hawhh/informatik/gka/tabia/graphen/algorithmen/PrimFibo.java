package de.hawhh.informatik.gka.tabia.graphen.algorithmen;

import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.jgrapht.util.FibonacciHeap;

import de.hawhh.informatik.gka.tabia.graphen.material.BigGraph;
import de.hawhh.informatik.gka.tabia.graphen.material.JungGraph;
import de.hawhh.informatik.gka.tabia.graphen.material.MyOwnEdge;
import de.hawhh.informatik.gka.tabia.graphen.material.MyOwnVertex;
import de.hawhh.informatik.gka.tabia.graphen.service.Laufzeit;

public class PrimFibo
{

	private JungGraph _originGraph;
	private JungGraph _spannbaum;
	private int kantenGewichtSumme=0;
	private FibonacciHeap<MyOwnVertex> fiboheap;
	private Laufzeit laufzeit;
	private MyOwnVertex start;
	private Set<MyOwnVertex> selectedKnoten ;
	private int _verticesCount;
	private Map<MyOwnVertex, Integer> distance; // Die Entfernung vom Source zum Target
	private Map<MyOwnVertex, MyOwnVertex> predecessor; // Target - Source

	public PrimFibo(JungGraph graph)
	{
		_originGraph = graph;
		_verticesCount = _originGraph.getMygraph().getVertexCount();
		distance = new HashMap<>();
		fiboheap = new FibonacciHeap<MyOwnVertex>();
		_spannbaum = new JungGraph(graph.getReferenz());
		laufzeit = new Laufzeit();
		selectedKnoten = new HashSet<MyOwnVertex>();
		predecessor = new HashMap<>();
	}
	// Eigene Klasse für das Sortieren in der Queue
    public class NodeCompator implements Comparator<MyOwnVertex>  
    {
    	Map<MyOwnVertex, Integer> distance;
    	public NodeCompator(Map<MyOwnVertex, Integer> distance)
		{
    		this.distance = distance;
		}
        @Override
        public int compare(MyOwnVertex o1, MyOwnVertex o2) 
        {
        	int v1 = distance.get(o1);
        	int v2 = distance.get(o2);
        	return Integer.compare(v1, v2);
        }
    };
    
	public MyOwnVertex randomKnoten()
	{
		int i = (int) (Math.random() * (_verticesCount - 0) + 0);
		Object[] array = _originGraph.vertexSet().toArray();
		return (MyOwnVertex) array[i];
	}
    
	public void start()
	{
		laufzeit.start();
		start = randomKnoten(); // ein beliebigen Knoten als Start Knoten festlegen
		
		distance.put(start, 0); // setze die Entfernung vom Start auf 0
		predecessor.put(start, start); // Nachfolger
		// TODO queue.offer(start);
		selectedKnoten.add(start); // markiere start als besuchter Knoten

		while (!fiboheap.isEmpty())
		{
			MyOwnVertex source = fiboheapMin();
			fiboheap.removeMin();
			selectedKnoten.add(source);
			// Gibt alle Benachbarten vom Source
			Collection<MyOwnVertex> neighbors = _originGraph.getMygraph().getNeighbors(source);
			for (MyOwnVertex target : neighbors)
			{	
				if (selectedKnoten.contains(target)) // wenn ja, wurde schon bearbeitet, nicht nehmen
					continue;
				
				MyOwnEdge e = minimalEdge(source, target);
				int kantenGewicht = e.getGewicht();
				if (!distance.containsKey(target) || distance.get(target) > kantenGewicht)
				{
					distance.put(target, kantenGewicht);
					predecessor.put(target, source);
				}
								
				//TODO if (!fiboheap.contains(target)) fiboheap.insert(target);
				else
				{
					// TODO queue.remove(target);
					// TODO queue.offer(target);
				}
			}
		}
		laufzeit.stop();
		erstelleSpannBaum();
	}

	private MyOwnVertex fiboheapMin()
	{
		//TODO
		return null;
	}

	public MyOwnEdge minimalEdge(MyOwnVertex v1, MyOwnVertex v2)
	{
		MyOwnEdge smallestEdge = null;
		Collection<MyOwnEdge> edgeset = _originGraph.getMygraph().findEdgeSet(v1, v2);
		
		for (MyOwnEdge e : edgeset)
		{
			if (smallestEdge==null)
				smallestEdge = e;
			else if (e.getGewicht() < smallestEdge.getGewicht())
				smallestEdge = e;
		}
		return smallestEdge;
	}
	
	private void erstelleSpannBaum()
	{
		for (MyOwnVertex v : selectedKnoten)
		{
			MyOwnVertex source = predecessor.get(v);
			if (source!=null)
			_spannbaum.kanteEinfuegen(predecessor.get(v), v, distance.get(v));
		}
	}
	public JungGraph spannbaum()
	{
		return _spannbaum;
	}
	
	public int getKantenGewichtSumme()
	{
		return kantenGewichtSumme;
	}
	
	public Laufzeit laufzeit()
	{
		return laufzeit;
	}

	public static void main(String[] args)
	{
		BigGraph biggraph = new BigGraph("#attributed #weighted", 10000, 20000);
		biggraph.generateGraph();
		//biggraph.show();
		PrimFibo prim = new PrimFibo(biggraph.graph());
		prim.start();
		System.out.println(prim.laufzeit().toString());
		//JungWerkzeug werkzeug1 = new JungWerkzeug(biggraph.graph());
		//JungWerkzeug werkzeug2 = new JungWerkzeug(prim.spannbaum());
	}

}
