package de.hawhh.informatik.gka.tabia.graphen.algorithmen;

import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Set;

import de.hawhh.informatik.gka.tabia.graphen.material.JungGraph;
import de.hawhh.informatik.gka.tabia.graphen.material.MyOwnVertex;

public class DijkstraAlgorithm
{
	private int counter;
	private JungGraph graph; // gelesener Graph
	private Map<MyOwnVertex, Integer> mapEntfernung; // die Kürzeste Enfernung
	private Map<MyOwnVertex, MyOwnVertex> mapNextFirst; // Nachfolger von !! Kürzester Weg
	private MyOwnVertex anfang; // Source
	private MyOwnVertex target; // Target
    private Queue<MyOwnVertex> queue; // Warteschlange der Knoten die noch bearbeitet wurden
    private Set<MyOwnVertex> closedList; // Merkt die bearbeitete Knoten vor
    private LinkedList<MyOwnVertex> shortPath; // Speichert den kürzesten Weg
    //private int length; // Länge des Kürzesten Weges

	public Queue<MyOwnVertex> getQueue()
	{
		return queue;
	}

	public JungGraph graph()
	{
		return graph;
	}

	public MyOwnVertex getAnfang()
	{
		return anfang;
	}

	public MyOwnVertex getTarget()
	{
		return target;
	}

	public void setQueue(Queue<MyOwnVertex> queue)
	{
		this.queue = queue;
	}

	public Map<MyOwnVertex, Integer> mapEntfernung()
	{
		return mapEntfernung;
	}

	public Map<MyOwnVertex, MyOwnVertex> mapNextFirst()
	{
		return mapNextFirst;
	}

	public DijkstraAlgorithm(JungGraph graph, MyOwnVertex source, MyOwnVertex target)
	{
		this.graph = graph;
		this.anfang = source;
		this.target = target;
		mapEntfernung = new HashMap<>();
		queue = new PriorityQueue<MyOwnVertex>(new NodeCompator(mapEntfernung));	
		mapNextFirst = new HashMap<MyOwnVertex,MyOwnVertex>();
		closedList = new HashSet<>();
		shortPath = new LinkedList<>() ;
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
        	int v1 = map.get(o1);
        	int v2 = map.get(o2);
        	return Integer.compare(v1, v2);
        }
    };
	
	public boolean start()
	{
		mapEntfernung.put(anfang, 0); // Der Anfangknoten nimmt die Entfernung 0
		mapNextFirst.put(anfang, anfang); // Der Anfangsknoten ist Nachfolger von sich selbst
		queue.offer(anfang); // Insert Anfangsknoten in der Warteschlange
		
		while(!queue.isEmpty()) // Abbruch Bedingung falls die Queue leer ist -> Es gibt keinen Weg von Source zum Target
		{ 
			MyOwnVertex source = queue.poll(); // Entfernt den ersten Knoten in der Queue !! es ist immer der Knoten mit der kürzesten Entfernung
			
			closedList.add(source); // Merke den gelöchten Knoten aus der Queue als bearbeitet
			
			if (target.equals(source)) {shortPath(target); return true;} // Falls der bearbeitete Knoten das Target ist -> Ziel erreicht
			
			counter++;
			
			for (MyOwnVertex successor : graph.getMygraph().getSuccessors(source)) // Gehe über alle Succesors vom Source
			{
				if(closedList.contains(successor)) continue; // geh eine Iteration weiter Falls der Nachfolger schon in der Bearbeitungsliste drin ist
				
				counter++;
				// Wenn nicht, rechne die neue Entfernung vom Nachfolger
				int tempWeg = mapEntfernung.get(source) + graph.getMygraph().findEdge(source, successor).getGewicht();
				
				// Falls der Nachfolger in der Queue ist und die neue Entfernung grösser gleich die alte Entfernung -> gehe eine Iteration weiter
				if (queue.contains(successor) && mapEntfernung.get(successor) <= tempWeg) continue;
				
				// Wenn nicht, füge die neue Entfernung hinzu und der kürzeste Nachfolger
				mapEntfernung.put(successor, tempWeg);
				mapNextFirst.put(successor, source);
				
				// Wenn der Nachfolger nicht in der Warteschleife ist -> füge diesen ein
				if (!queue.contains(successor))	queue.offer(successor);
			}
		}
		return false;
	}
	
	public void shortPath(MyOwnVertex vertex)
	{
		shortPath.add(vertex); // fügt einen Knoten (Weg)
		if (!vertex.equals(anfang)) shortPath(mapNextFirst.get(vertex)); // Wenn der aktuelle Knoten nicht der anfang ist, dann nehme seinen Nachfolger
	}
	
	public LinkedList<MyOwnVertex> shortPath()
	{
		Collections.reverse(shortPath);
		 return shortPath;
	}
	
	public int getLength()
	{
		return mapEntfernung.get(target);
	}

	@Override
	public String toString()
	{
		return shortPath.toString();
	}
	
	public int counter()
	{
		return counter;
	}
	
	public static void main(String[] args)
	{
		JungGraph graph = new JungGraph("#directed #weighted");
		graph.kanteEinfuegen(new MyOwnVertex("a"), new MyOwnVertex("b"), 5);
		graph.kanteEinfuegen(new MyOwnVertex("b"), new MyOwnVertex("c"), 1);
		graph.kanteEinfuegen(new MyOwnVertex("c"), new MyOwnVertex("f"), 1);
		graph.kanteEinfuegen(new MyOwnVertex("a"), new MyOwnVertex("d"), 1);
		graph.kanteEinfuegen(new MyOwnVertex("d"), new MyOwnVertex("e"), 1);
		

		DijkstraAlgorithm di = new DijkstraAlgorithm(graph, new MyOwnVertex("a"), new MyOwnVertex("e"));
		boolean found = di.start();
		System.out.println("Gefunden: "+ found);
		System.out.println("X ist Nachfolger von Y: "+di.mapNextFirst.toString());
		System.out.println("Entf"+di.mapEntfernung.toString());
		System.out.println("Der kürzeste Weg ist: "+di.toString());
		
	}
}
