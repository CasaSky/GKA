package de.hawhh.informatik.gka.tabia.graphen.algorithmen;

import java.util.Collection;
import java.util.Comparator;
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

public class PrimAlgorithm 
{
	private JungGraph _originGraph;
	private Collection<MyOwnVertex> _knotenSammlung;
	private Collection<MyOwnEdge> _kantenSammlung;
	private JungGraph _spannbaum; // Graph T
	private int kantenGewichtSumme=0;
	private PriorityQueue<MyOwnVertex> queue;
	private Laufzeit laufzeit;
	private MyOwnVertex start;
	private Set<MyOwnVertex> selectedKnoten ;
	private int _verticesCount;
	private Map<MyOwnVertex, Integer> distance;
	private Map<MyOwnVertex, MyOwnVertex> predecessor;

	public PrimAlgorithm(JungGraph graph)
	{
		_originGraph = graph;
		_knotenSammlung = _originGraph.vertexSet();
		_verticesCount = _knotenSammlung.size();
		//_knotenAnzahl = graph.getMygraph().getVertexCount();
		_kantenSammlung = new HashSet<>();
		distance = new HashMap<>();
		queue = new PriorityQueue<MyOwnVertex>(new NodeCompator(distance));
		//queue.addAll(_knotenSammlung);
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
		Object[] array = _knotenSammlung.toArray();
		return (MyOwnVertex) array[i];
	}
    
	public void start()
	{
		laufzeit.start();
		start = randomKnoten(); // ein beliebigen Knoten als Start Knoten festlegen
		
		distance.put(start, 0);
		predecessor.put(start, start);
		queue.offer(start);
		selectedKnoten.add(start); // markiere start als besuchter Knoten

		while (!queue.isEmpty())
		{
			MyOwnVertex source = queue.poll();
			selectedKnoten.add(source);
			
			// füge vertex in erg. grah
			
			//selectedKnoten.add(source);
			Collection<MyOwnVertex> _neighborsSammlung = _originGraph.getMygraph().getNeighbors(source);
			for (MyOwnVertex v : _neighborsSammlung)
			{	
				if (selectedKnoten.contains(v))
					continue;
				
				MyOwnEdge e = minimalEdge(source, v);
				int kantenGewicht = e.getGewicht();
				if (!distance.containsKey(v) || distance.get(v) > kantenGewicht)
				{
					distance.put(v, kantenGewicht);
					predecessor.put(v, source);
				}
				
				// ------- hier wird die Kante gewählt
				
				if (!queue.contains(v))
					queue.offer(v);
				else
				{
					queue.remove(v);
					queue.offer(v);
				}
				// Füge die Kante hinzu
				_kantenSammlung.add(e);
			}
		}
		laufzeit.stop();
		erstelleSpannBaum();
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
	public void erstelleSpannBaum()
	{
		for (MyOwnVertex v : _knotenSammlung)
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
		PrimAlgorithm prim = new PrimAlgorithm(biggraph.graph());
		prim.start();
		System.out.println(prim.laufzeit().toString());
		//JungWerkzeug werkzeug1 = new JungWerkzeug(biggraph.graph());
		//JungWerkzeug werkzeug2 = new JungWerkzeug(prim.spannbaum());
	}

}
