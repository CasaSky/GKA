package de.hawhh.informatik.gka.tabia.graphen.algorithmen;

import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

import org.jgrapht.util.FibonacciHeap;
import org.jgrapht.util.FibonacciHeapNode;

import de.hawhh.informatik.gka.tabia.graphen.material.BigGraph;
import de.hawhh.informatik.gka.tabia.graphen.material.JungGraph;
import de.hawhh.informatik.gka.tabia.graphen.material.MyOwnEdge;
import de.hawhh.informatik.gka.tabia.graphen.material.MyOwnVertex;
import de.hawhh.informatik.gka.tabia.graphen.service.Laufzeit;
import de.hawhh.informatik.gka.tabia.graphen.werkzeug.JungWerkzeug;

public class PrimFiboHeap
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
	private Map<MyOwnVertex,FibonacciHeapNode<MyOwnVertex>> verticesInFiboHeap;

	public PrimFiboHeap(JungGraph graph)
	{
		assert graph != null : "Vorbedingung verletzt: graph != null";
		
		_originGraph = graph;
		_verticesCount = _originGraph.getMygraph().getVertexCount();
		distance = new HashMap<>();
		fiboheap = new FibonacciHeap<MyOwnVertex>();
		_spannbaum = new JungGraph(graph.getReferenz());
		laufzeit = new Laufzeit();
		selectedKnoten = new HashSet<MyOwnVertex>();
		predecessor = new HashMap<>();
		verticesInFiboHeap = new LinkedHashMap<>();
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
		assert array[i] != null : "Vorbedingung verletzt: array[i] != null";
		return (MyOwnVertex) array[i];
	}
    
	public void start()
	{
		laufzeit.start();
		start = randomKnoten(); // ein beliebigen Knoten als Start Knoten festlegen
		
		distance.put(start, 0); // setze die Entfernung vom Start auf 0
		predecessor.put(start, start); // Nachfolger
		insertNodeToFiboHeap(start);
		selectedKnoten.add(start); // markiere start als besuchter Knoten

		while (!fiboheap.isEmpty())
		{
			MyOwnVertex source = deleteMin();
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
								
				if (!verticesInFiboHeap.containsKey(target)) 
					insertNodeToFiboHeap(target);
				else
					decreaseKey(target);
			}
		}
		laufzeit.stop();
		erstelleSpannBaum();
	}

	private MyOwnVertex deleteMin()
	{
		MyOwnVertex node = fiboheap.removeMin().getData();
		assert node != null : "Vorbedingung verletzt: node != null";
		
		verticesInFiboHeap.remove(node);
		return node;
	}
	
    public void insertNodeToFiboHeap(MyOwnVertex node) 
    {
    	assert node != null : "Vorbedingung verletzt: node != null";
    	assert distance.get(node) != null : "Vorbedingung verletzt: distance.get(node) != null";
    	
        int key = distance.get(node);
        FibonacciHeapNode<MyOwnVertex> fiboheapnode = new FibonacciHeapNode<MyOwnVertex>(node);
        
        assert fiboheapnode != null : "Vorbedingung verletzt: fiboheapnode != null";
        
    	verticesInFiboHeap.put(node,fiboheapnode);
        fiboheap.insert(fiboheapnode, key);
    }
    
    public void decreaseKey(MyOwnVertex node)
    {	
    	assert node != null : "Vorbedingung verletzt: node != null";
    	assert distance.get(node) != null : "Vorbedingung verletzt: distance.get(node) != null";
    	
    	int key = distance.get(node);
    	FibonacciHeapNode<MyOwnVertex> fibonode = verticesInFiboHeap.get(node);
    	
    	assert fibonode != null : "Vorbedingung verletzt: fibonode != null";
    	
    	fiboheap.decreaseKey(fibonode, key);
    }

	public MyOwnEdge minimalEdge(MyOwnVertex v1, MyOwnVertex v2)
	{
		assert v1 != null : "Vorbedingung verletzt: v1 != null";
		assert v2 != null : "Vorbedingung verletzt: v2 != null";
		
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
			if (source!=null && !v.equals(source))
			{
				kantenGewichtSumme += distance.get(v);
				_spannbaum.kanteEinfuegen(predecessor.get(v), v, distance.get(v));
			}
		}
	}
	public JungGraph spannbaum()
	{
		assert _spannbaum != null : "Vorbedingung verletzt: _s != null";
		return _spannbaum;
	}
	
	public int kantenGewichtSumme()
	{
		assert kantenGewichtSumme >= 0 : "Vorbedingung verletzt: kantenGewichtSumme >= 0";
		return kantenGewichtSumme;
	}
	
	public Laufzeit laufzeit()
	{
		assert laufzeit != null : "Vorbedingung verletzt: laufzeit != null";
		return laufzeit;
	}

	public static void main(String[] args)
	{
		BigGraph biggraph = new BigGraph("#attributed #weighted", 10, 30);
		biggraph.generateGraph();
		//biggraph.show();
		PrimFiboHeap prim = new PrimFiboHeap(biggraph.graph());
		prim.start();
		System.out.println(prim.laufzeit().toString());
		@SuppressWarnings("unused")
		JungWerkzeug werkzeug1 = new JungWerkzeug(biggraph.graph());
		@SuppressWarnings("unused")
		JungWerkzeug werkzeug2 = new JungWerkzeug(prim.spannbaum());
	}

}
