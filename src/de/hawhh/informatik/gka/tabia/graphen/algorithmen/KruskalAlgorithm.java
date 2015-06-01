package de.hawhh.informatik.gka.tabia.graphen.algorithmen;

import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

import de.hawhh.informatik.gka.tabia.graphen.material.JungGraph;
import de.hawhh.informatik.gka.tabia.graphen.material.MyOwnEdge;
import de.hawhh.informatik.gka.tabia.graphen.material.MyOwnVertex;
import de.hawhh.informatik.gka.tabia.graphen.werkzeug.JungWerkzeug;


public class KruskalAlgorithm
{
	private List<MyOwnEdge> _kantenSet;
	private JungGraph _originGraph;
	private JungGraph _minimalgeruest;
	
	
	public KruskalAlgorithm(JungGraph graph)
	{
		assert graph != null : "Vorbedingung verletzt: graph != null";
		_originGraph = graph;
		_kantenSet = new LinkedList<>(_originGraph.edgeSet());
		Collections.sort(_kantenSet, new MyComparator());
		_minimalgeruest = new JungGraph(graph.getReferenz());
		kopiereKnoten();
	}

	// Eigene Klasse für das Sortieren in der Queue
    public class MyComparator implements Comparator<MyOwnEdge>  
    {
    	public MyComparator()
		{
		}
        @Override
        public int compare(MyOwnEdge e1, MyOwnEdge e2) 
        {
        	int a = e1.getGewicht();
        	int b = e2.getGewicht();
        	return Integer.compare(a, b);
        }
    };
    
    public void kopiereKnoten()
    {
    	for (MyOwnVertex v : _originGraph.getVertices())
    	{
    		_minimalgeruest.knotenEinfuegen(v.getName(), v.getAttribute());
    	}
    }
    public void start()
    {
    	for (MyOwnEdge e : _kantenSet)
    	{	
    		MyOwnVertex source = e.source();
    		MyOwnVertex target = e.target();
    		ASternAlgorithm astern = new ASternAlgorithm(_minimalgeruest, source, target);
    		if (astern.start())
    			continue;
    		else
    			_minimalgeruest.kanteEinfuegen(source, target, e.getGewicht());
    	}
    }
    
	public static void main(String[] args)
	{
		JungGraph graph = new JungGraph("#attributed #weighted");
		graph.kanteEinfuegen(new MyOwnVertex("a", 0), new MyOwnVertex("b", 0), 5);
		graph.kanteEinfuegen(new MyOwnVertex("b", 0), new MyOwnVertex("c", 0), 1);
		graph.kanteEinfuegen(new MyOwnVertex("c", 0), new MyOwnVertex("f", 0), 2);
		graph.kanteEinfuegen(new MyOwnVertex("a", 0), new MyOwnVertex("d", 0), 6);
		graph.kanteEinfuegen(new MyOwnVertex("d", 0), new MyOwnVertex("e", 0), 4);
		graph.kanteEinfuegen(new MyOwnVertex("f", 0), new MyOwnVertex("e", 0), 3);
		KruskalAlgorithm kruskal = new KruskalAlgorithm(graph);
		kruskal.start();
		@SuppressWarnings("unused")
		JungWerkzeug werkzeug1 = new JungWerkzeug(graph);
		@SuppressWarnings("unused")
		JungWerkzeug werkzeug2 = new JungWerkzeug(kruskal._minimalgeruest);
	}
	public JungGraph minimalgeruest()
	{
		return _minimalgeruest;
	}

}
