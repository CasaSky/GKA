package de.hawhh.informatik.gka.tabia.graphen.algorithmen;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Set;

import de.hawhh.informatik.gka.tabia.graphen.material.BigGraph;
import de.hawhh.informatik.gka.tabia.graphen.material.JungGraph;
import de.hawhh.informatik.gka.tabia.graphen.material.MyOwnEdge;
import de.hawhh.informatik.gka.tabia.graphen.material.MyOwnVertex;
import de.hawhh.informatik.gka.tabia.graphen.service.Laufzeit;
import de.hawhh.informatik.gka.tabia.graphen.werkzeug.JungWerkzeug;

public class Prim 
{
	private JungGraph _originGraph;
	private JungGraph _spannbaum;
	private int kantenGewichtSumme=0;
	private PriorityQueue<MyOwnVertex> queue;
	private Laufzeit laufzeit;
	private MyOwnVertex start;
	private Set<MyOwnVertex> selectedKnoten ;
	private int _verticesCount;
	private Map<MyOwnVertex, Integer> distance; // Die Entfernung vom Source zum Target
	private Map<MyOwnVertex, MyOwnVertex> predecessor; // Target - Source

	public Prim(JungGraph graph)
	{
		assert graph != null : "Vorbedingung verletzt: graph != null";
		assert ZusammenGewichtet.istzusammenUndGewichtet(graph) == true : "Vorbedingung verletzt: graph ist nicht zusammenhängend!";

		_originGraph = graph;
		_verticesCount = _originGraph.getMygraph().getVertexCount();
		distance = new HashMap<>();
		queue = new PriorityQueue<MyOwnVertex>(new knotenComparator(distance)); // sortiere nach der Entfernung in der Map
		_spannbaum = new JungGraph(graph.getReferenz());
		laufzeit = new Laufzeit();
		selectedKnoten = new HashSet<MyOwnVertex>();
		predecessor = new HashMap<>();
	}
    
	public MyOwnVertex randomKnoten()
	{
		int i = (int) (Math.random() * (_verticesCount - 0) + 0);
		Object[] array = _originGraph.vertexSet().toArray();
		return (MyOwnVertex) array[i];
	}
    
	public void start()
	{
		// ------------ Initialisierung
		laufzeit.start();
		start = randomKnoten(); // ein beliebigen Knoten als Start Knoten festlegen
		distance.put(start, 0); // setze die Entfernung vom Start auf 0
		predecessor.put(start, start); // Nachfolger
		queue.offer(start);	// Start Knoten in Queue einfügen
		selectedKnoten.add(start); // markiere start als besuchter Knoten

		while (!queue.isEmpty())
		{
			MyOwnVertex source = queue.poll();
			selectedKnoten.add(source);
			// Gibt alle Benachbarten vom Source
			Collection<MyOwnVertex> neighbors = _originGraph.getMygraph().getNeighbors(source);
			for (MyOwnVertex target : neighbors)
			{	
				if (selectedKnoten.contains(target)) // wenn ja, wurde schon bearbeitet, nicht nehmen
					continue;
				
				MyOwnEdge e = minimalEdge(source, target); // suche die minimale Kante zwischen Source und Target
				int kantenGewicht = e.getGewicht();
				// Wenn Target nicht in distance vorhanden ist, dann fügen wir den ein und source, wenn nicht, es wird geprüft ob sein Distance Value  > als das Kanten Gewicht
				if (!distance.containsKey(target) || distance.get(target) > kantenGewicht)
				{	
					distance.put(target, kantenGewicht);
					predecessor.put(target, source);
				}
								
				// Füge Target nur ein wenn der nicht in der Queue enthalten ist
				if (!queue.contains(target))
					queue.offer(target);
				else
				{
					// Fals vorhanden, denn löschen und neu einfügen
					queue.remove(target);
					queue.offer(target);
				}
			}
		}
		laufzeit.stop();
		erstelleSpannBaum();
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
			assert source != null : "Vorbedingung verletzt: source != null";
			
			if (source!=null && !v.equals(source))
			{
				kantenGewichtSumme += distance.get(v);
				_spannbaum.kanteEinfuegen(predecessor.get(v), v, distance.get(v));
			}
		}
	}
	public JungGraph spannbaum()
	{
		assert _spannbaum != null : "Vorbedingung verletzt: _spannbaum != null";
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
		Prim prim = new Prim(biggraph.graph());
		prim.start();
		System.out.println(prim.laufzeit().toString());
		@SuppressWarnings("unused")
		JungWerkzeug werkzeug1 = new JungWerkzeug(biggraph.graph());
		@SuppressWarnings("unused")
		JungWerkzeug werkzeug2 = new JungWerkzeug(prim.spannbaum());
	}

}
