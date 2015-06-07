package de.hawhh.informatik.gka.tabia.graphen.algorithmen;

import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Set;

import de.hawhh.informatik.gka.tabia.graphen.material.JungGraph;
import de.hawhh.informatik.gka.tabia.graphen.material.MyOwnEdge;
import de.hawhh.informatik.gka.tabia.graphen.material.MyOwnVertex;
import de.hawhh.informatik.gka.tabia.graphen.service.Laufzeit;
import de.hawhh.informatik.gka.tabia.graphen.werkzeug.JungWerkzeug;

public class PrimAlgorithm 
{
	private JungGraph _originGraph;
	private Collection<MyOwnVertex> _knotenSammlung;
	private List<MyOwnEdge> _kantenSammlung;
	private JungGraph _spannbaum; // Graph T
	private int kantenGewichtSumme=0;
	private PriorityQueue<MyOwnVertex> queue;
	private Laufzeit laufzeit;
	private MyOwnVertex start;
	private Set<MyOwnVertex> selectedKnoten ;

	public PrimAlgorithm(JungGraph graph, MyOwnVertex start)
	{
		_originGraph = graph;
		_knotenSammlung = _originGraph.vertexSet();
		//_knotenAnzahl = graph.getMygraph().getVertexCount();
		_kantenSammlung = new LinkedList<>(_originGraph.edgeSet());
			Collections.sort(_kantenSammlung, new kantenComparator()); // braucht man nicht
		queue = new PriorityQueue<MyOwnVertex>(new NodeCompator());
		queue.addAll(_knotenSammlung);
		_spannbaum = new JungGraph(graph.getReferenz());
		laufzeit = new Laufzeit();
		this.start = start;
		selectedKnoten = new HashSet<MyOwnVertex>();
	}
	// Eigene Klasse für das Sortieren in der Queue
    public class NodeCompator implements Comparator<MyOwnVertex>  
    {
    	public NodeCompator()
		{
		}
        @Override
        public int compare(MyOwnVertex o1, MyOwnVertex o2) 
        {
        	int v1 = o1.getKantenGewicht();
        	int v2 = o2.getKantenGewicht();
        	return Integer.compare(v1, v2);
        }
    };
    
//	public MyOwnVertex randomKnoten()
//	{
//		int i = (int) (Math.random() * (_knotenAnzahl - 0) + 0);
//		MyOwnVertex[] array = (MyOwnVertex[]) _knotenSammlung.toArray();
//		return array[i];
//	}
    
	
	// --------------------------Kante mit zwei Knoten
	public void start()
	{
		laufzeit.start();
		queue.remove(start);
		start.setKantenGewicht(0);
		queue.add(start);
		start.setVorgaenger(start);
		selectedKnoten.add(start);
		while (!queue.isEmpty())
		{
			MyOwnVertex source = queue.poll();
			System.out.println("Poll "+source.getName());
			MyOwnVertex target;
			Collection<MyOwnEdge> _neighborsSammlung=_originGraph.getMygraph().getIncidentEdges(source);
			for (MyOwnEdge e : _neighborsSammlung)
			{
				MyOwnVertex tempTarget1 = e.source();
				MyOwnVertex tempTarget2 = e.target();
				if (source!=tempTarget1)
					target = tempTarget1;
				else
					target = tempTarget2;
				System.out.println("Target"+ target.getName());
				if (!(queue.contains(target) && (target.getKantenGewicht() > e.getGewicht())))
					continue;
				queue.remove(target);
				System.out.println("Omega"+source.getKantenGewicht()+"Source"+source.getName());
				kantenGewichtSumme=e.getGewicht();
				System.out.println("Für Knoten "+target.getName()+" "+ e.getGewicht());
				//if (target.getKantenGewicht() > kantenGewichtSumme)
				//{
				target.setVorgaenger(source);
				target.setKantenGewicht(kantenGewichtSumme);
				queue.add(target);
				target.setVorgaengerKantenGewicht(e.getGewicht());
				selectedKnoten.add(target);
				//}
				//_spannbaum.kanteEinfuegen(source, target, e.getGewicht());
			}
		}
		erstelleSpannBaum();
		laufzeit.stop();
	}

	public void erstelleSpannBaum()
	{
		for (MyOwnVertex v :selectedKnoten)
		_spannbaum.kanteEinfuegen(v, v.getVorgaenger(), v.getVorgaengerKantenGewicht());
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
		JungGraph graph = new JungGraph("#attributed #weighted");
		graph.kanteEinfuegen(new MyOwnVertex("a", 0), new MyOwnVertex("b", 0), 5);
		graph.kanteEinfuegen(new MyOwnVertex("b", 0), new MyOwnVertex("c", 0), 1);
		graph.kanteEinfuegen(new MyOwnVertex("c", 0), new MyOwnVertex("f", 0), 2);
		graph.kanteEinfuegen(new MyOwnVertex("a", 0), new MyOwnVertex("d", 0), 6);
		graph.kanteEinfuegen(new MyOwnVertex("d", 0), new MyOwnVertex("e", 0), 4);
		graph.kanteEinfuegen(new MyOwnVertex("f", 0), new MyOwnVertex("e", 0), 3);
		PrimAlgorithm prim = new PrimAlgorithm(graph, new MyOwnVertex("f", 0));
		prim.start();
		System.out.println("Summe: "+prim.getKantenGewichtSumme());
		System.out.println("Die Laufzeit des Algorithmus beträgt: "+prim.laufzeit().toString()+" ms");
		@SuppressWarnings("unused")
		JungWerkzeug werkzeug1 = new JungWerkzeug(graph);
		@SuppressWarnings("unused")
		JungWerkzeug werkzeug2 = new JungWerkzeug(prim.spannbaum());
	}

}
