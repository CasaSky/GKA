package de.hawhh.informatik.gka.tabia.graphen.algorithmen;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import de.hawhh.informatik.gka.tabia.graphen.material.JungGraph;
import de.hawhh.informatik.gka.tabia.graphen.material.MyOwnEdge;
import de.hawhh.informatik.gka.tabia.graphen.material.MyOwnVertex;
import de.hawhh.informatik.gka.tabia.graphen.service.Laufzeit;
import de.hawhh.informatik.gka.tabia.graphen.werkzeug.JungWerkzeug;
import de.hawhh.informatik.gka.tabia.graphen.material.BigGraph;

public class KruskalAlgorithm
{
	private List<MyOwnEdge> _kantenSet;
	private JungGraph _originGraph;
	private JungGraph _minimalgeruest;
	private Laufzeit laufzeit;
	private int kantenGewichtSumme;
	
	
	public KruskalAlgorithm(JungGraph graph)
	{
		assert graph != null : "Vorbedingung verletzt: graph != null";
		_originGraph = graph;
		_kantenSet = new LinkedList<>(_originGraph.edgeSet());
		Collections.sort(_kantenSet, new kantenComparator());
		_minimalgeruest = new JungGraph(graph.getReferenz());
		kopiereKnoten();
		laufzeit = new Laufzeit();
	}

    public void kopiereKnoten()
    {
    	for (MyOwnVertex v : _originGraph.getVertices())
    	{
    		_minimalgeruest.knotenEinfuegen(v.getName(), v.getAttribute());
    	}
    }
    public void start()
    {
    	laufzeit.start();
    	for (MyOwnEdge e : _kantenSet)
    	{	
    		MyOwnVertex source = e.source();
    		MyOwnVertex target = e.target();
    		ASternAlgorithm astern = new ASternAlgorithm(_minimalgeruest, source, target);
    		if (astern.start())
    			continue;
    		
    		_minimalgeruest.kanteEinfuegen(source, target, e.getGewicht());
    	}
    	laufzeit.stop();
    }
    
    public Laufzeit laufzeit()
    {
    	return laufzeit;
    }
    
	public static void main(String[] args)
	{
		BigGraph biggraph = new BigGraph("#attributed #weighted", 1000, 6000);
		biggraph.generateGraph();
		biggraph.show();
		KruskalAlgorithm kruskal = new KruskalAlgorithm(biggraph.graph());
		kruskal.start();
		System.out.println(kruskal.laufzeit().toString());
		@SuppressWarnings("unused")
		JungWerkzeug werkzeug1 = new JungWerkzeug(biggraph.graph());
		@SuppressWarnings("unused")
		JungWerkzeug werkzeug2 = new JungWerkzeug(kruskal._minimalgeruest);
	}
	public JungGraph minimalgeruest()
	{
		return _minimalgeruest;
	}

}
