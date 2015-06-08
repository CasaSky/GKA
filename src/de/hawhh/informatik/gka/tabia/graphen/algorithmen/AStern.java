package de.hawhh.informatik.gka.tabia.graphen.algorithmen;

import java.util.Comparator;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;

import de.hawhh.informatik.gka.tabia.graphen.material.JungGraph;
import de.hawhh.informatik.gka.tabia.graphen.material.MyOwnVertex;

public class AStern extends Dijkstra
{	
	private Queue<MyOwnVertex> queue2;
	
	public AStern(JungGraph graph, MyOwnVertex source, MyOwnVertex target)
	{
		super(graph, source, target);
		// Redifinition der Queue
		queue2 = new PriorityQueue<MyOwnVertex>(new NodeCompator(mapEntfernung()));	
		setQueue(queue2);
	}
	
	// Eigene Klasse für das Sortieren in der Queue
    public class NodeCompator implements Comparator<MyOwnVertex>  
    {
    	private Map<MyOwnVertex, Integer> map; // es wird nach der MapEntfernung sortiert 

    	public NodeCompator(Map<MyOwnVertex, Integer> map)
		{
    		this.map=map;
		}
        @Override
        public int compare(MyOwnVertex o1, MyOwnVertex o2) 
        {
        	int v1 = map.get(o1) + o1.getAttribute();
        	int v2 = map.get(o2) + o2.getAttribute();
        	return Integer.compare(v1, v2);
        }
    };
	
	public static void main(String[] args)
	{
		JungGraph graph = new JungGraph("#directed #weighted");
		graph.kanteEinfuegen(new MyOwnVertex("a"), new MyOwnVertex("b"), 5);
		graph.kanteEinfuegen(new MyOwnVertex("b"), new MyOwnVertex("c"), 1);
		graph.kanteEinfuegen(new MyOwnVertex("c"), new MyOwnVertex("f"), 1);
		graph.kanteEinfuegen(new MyOwnVertex("a"), new MyOwnVertex("d"), 1);
		graph.kanteEinfuegen(new MyOwnVertex("d"), new MyOwnVertex("e"), 1);
		

		AStern astern = new AStern(graph, new MyOwnVertex("a"), new MyOwnVertex("e"));
		boolean found = astern.start();
		System.out.println("Gefunden: "+ found);
		System.out.println("X ist Nachfolger von Y: "+astern.mapNextFirst().toString());
		System.out.println("Entf"+astern.mapEntfernung().toString());
		System.out.println("Der kürzeste Weg ist: "+astern.toString());
		
	}

}
