package de.hawhh.informatik.gka.tabia.graphen.algorithmen;
import static org.junit.Assert.assertTrue;

import java.util.ArrayDeque;
import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Set;

import de.hawhh.informatik.gka.tabia.graphen.material.Eulerkreis;
import de.hawhh.informatik.gka.tabia.graphen.material.JungGraph;
import de.hawhh.informatik.gka.tabia.graphen.material.MyOwnEdge;
import de.hawhh.informatik.gka.tabia.graphen.material.MyOwnVertex;
import de.hawhh.informatik.gka.tabia.graphen.material.RandomEulerGraph;


public class Fleury
{
	private JungGraph graph;
	private List<MyOwnEdge> visitedEdges;
	private MyOwnVertex start;
	private Queue<MyOwnVertex> queue;
	//private JungGraph eulertour;
	
	public Fleury(JungGraph graph)
	{
		this.graph = graph;
		this.visitedEdges = new LinkedList<MyOwnEdge>();
		this.queue = new LinkedList<>();
		//this.eulertour = new JungGraph("#undirected");
	}
	
	// Generiert eine Random Number zwichen min und max
	public int generateRandomNumber(int max, int min)
	{
		return (int) (Math.random() * (max - min) + min);
	}
	
	public MyOwnVertex getRandomStart()
	{

		Object[] array = graph.getVertices().toArray();
		int randomIndex;
		MyOwnVertex target;
		randomIndex = generateRandomNumber(graph.getVertices().size(),1);
		target = (MyOwnVertex) array[randomIndex-1];
		return target;
	}
	
	// Ergibt eine Kante zurückt, die ausgehend von einem Knoten, wobei der Graph noch immer verbunden bleibt, wenn die entfernt wird
	public MyOwnEdge getIncidentEdge(MyOwnVertex source)
	{
		MyOwnEdge incidentEdge = null;
		Collection<MyOwnEdge> edges = graph.getMygraph().getOutEdges(source);
		MyOwnVertex target;
		for (MyOwnEdge e : edges)
		{
			if (e.target()!=source)
				target = e.target();
			else 
				target = e.source();
			if (!advancedBFS(target, start, e))
				continue;
			incidentEdge = e;
			break;
		}
		if (incidentEdge==null)
			System.out.println("Edge: null");

		else
		System.out.println("Edge: "+incidentEdge.getGewicht());
		return incidentEdge;
	}
	
	public void start()
	{
		start = getRandomStart();
		queue.offer(start);
		System.out.println(start.getName());
		while(!queue.isEmpty())
		{
			MyOwnVertex source = queue.poll();
			// Outgoing Edge-Set
			Collection<MyOwnEdge> outgouingEdges = new LinkedHashSet<>(graph.getMygraph().getOutEdges(source));
			// Existing Edge-Set = {Outgoing Edge-Set} \ {Deleted Edge-Set}
			outgouingEdges.removeAll(visitedEdges);
			
			MyOwnEdge e = null;
			if(outgouingEdges.size() > 1){
				for(MyOwnEdge aEdge : outgouingEdges){
					
					// If this edge is not a bridge edge , then found valid edge
					if(advancedBFS(source, start, aEdge)){
						e = aEdge;
						break;
					}
				}
			}else{
				
				for(MyOwnEdge aEdge : outgouingEdges){
					e = aEdge;
				}
			}
			
			if(e == null) break;
			
			// Note the Edge as deleted
			visitedEdges.add(e);
						
			MyOwnVertex target;
			if (!e.target().equals(source))
				target = e.target();
			else
					target = e.source();
			//eulertour.kanteEinfuegen(source, target, 0);
			//System.out.println("Source: "+source+"Target: "+target);

			queue.offer(target);
		}
	}
	
	private boolean advancedBFS(MyOwnVertex startnode, MyOwnVertex target, MyOwnEdge edgeToIgnore){
		
		if(startnode.equals(target)) return true;
		
		Queue<MyOwnVertex> queue = new ArrayDeque<MyOwnVertex>();
		Set<MyOwnVertex> visited = new HashSet<MyOwnVertex>();
		
		queue.offer(startnode);
		
		while(!queue.isEmpty()){

			MyOwnVertex currentVertex = queue.poll();
			visited.add(currentVertex);
			
			for(MyOwnVertex successor : graph.getMygraph().getSuccessors(currentVertex)){
				
				if(visited.contains(successor)) continue;
				
				
				// advanced step
				Collection<MyOwnEdge> edgesBetween = new HashSet<>(graph.getMygraph().findEdgeSet(currentVertex, successor));
				edgesBetween.removeAll(visitedEdges);
				
				if(edgesBetween.isEmpty()) continue;
				if(edgesBetween.contains(edgeToIgnore) && edgesBetween.size() == 1) continue;
				
				if(successor.equals(target)) return true;
				
				if(!queue.contains(successor)) queue.offer(successor);
			}
		}
		return false;
	}

//	public JungGraph getEulertour()
//	{
//		return eulertour;
//	}

	public List<MyOwnEdge> getVisitedEdges()
	{
		return visitedEdges;
	}
	
	public static void main(String[] args)
	{
		RandomEulerGraph randomGraph = new RandomEulerGraph("#undirected", 8);
		randomGraph.generateGraph();
		randomGraph.show();
		Fleury fleury = new Fleury(randomGraph.graph());
		fleury.start();
		List<MyOwnEdge> kantenFolge = new LinkedList<>(fleury.getVisitedEdges());
		System.out.println(kantenFolge);
	}
}
