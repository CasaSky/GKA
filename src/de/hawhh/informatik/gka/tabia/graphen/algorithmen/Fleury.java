package de.hawhh.informatik.gka.tabia.graphen.algorithmen;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Set;

import de.hawhh.informatik.gka.tabia.graphen.material.EulerkreisProperties;
import de.hawhh.informatik.gka.tabia.graphen.material.JungGraph;
import de.hawhh.informatik.gka.tabia.graphen.material.MyOwnEdge;
import de.hawhh.informatik.gka.tabia.graphen.material.MyOwnVertex;
import de.hawhh.informatik.gka.tabia.graphen.material.RandomEulerGraph;
import de.hawhh.informatik.gka.tabia.graphen.service.Random;

// Eingabe EulerGraph
// Ausgabe Eulerkreis als Kanten Folge
public class Fleury
{
	private JungGraph graph;
	private List<MyOwnEdge> delEdges;
	private MyOwnVertex start;
	private Queue<MyOwnVertex> queue;
	
	public Fleury(JungGraph graph)
	{
		assert EulerkreisProperties.istZusammenHaengend(graph) : "Vorbedingung verletzt: Eulerkreis";
		this.graph = graph;
		this.delEdges = new LinkedList<MyOwnEdge>();
		this.queue = new LinkedList<>();
	}
		
	public void start()
	{
		if (!EulerkreisProperties.hatEulertour(graph)) return;
		
		start = getRandomStart();
		queue.offer(start);
		
		while(!queue.isEmpty())
		{
			MyOwnVertex source = queue.poll();
			// Outgoing Edge-Set
			Collection<MyOwnEdge> outgouingEdges = new LinkedHashSet<>(graph.getOutEdges(source));
			// Existing Edge-Set = {Outgoing Edge-Set} \ {Deleted Edge-Set}
			outgouingEdges.removeAll(delEdges);
			
			MyOwnEdge e = null;
			if(outgouingEdges.size() > 1)
			{
				for(MyOwnEdge edge : outgouingEdges){
					
					// If this edge is not a bridge edge , then found valid edge
					if(advancedBFS(source, start, edge)){
						e = edge;
						break;
					}
				}
			}
			else
			{
				for(MyOwnEdge edge : outgouingEdges)
				{
					e = edge;
				}
			}
			
			if(e == null) break;
			
			// Merke die Kante als gelöscht
			delEdges.add(e);
						
			MyOwnVertex target;
			if (!e.target().equals(source))
				target = e.target();
			else
				target = e.source();
			
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
						edgesBetween.removeAll(delEdges); // da delEdges nicht mehr im Graphen existieren
						if(edgesBetween.isEmpty()) continue;
						// Ignoriere die Kante, die entfernt werden muss, so kommt man nicht zum Start
						// Ignoriere den Weg, wenn es nur eine Kante gibt und die jenige, die entfernt werden muss
						if(edgesBetween.contains(edgeToIgnore) && edgesBetween.size() == 1) continue;
				// --------------
						
				if(successor.equals(target)) return true;
				
				if(!queue.contains(successor)) queue.offer(successor);
			}
		}
		return false;
	}

	public MyOwnVertex getRandomStart()
	{
		List<MyOwnVertex> knoten = new ArrayList<>(graph.vertexSet());
		int randomIndex;
		MyOwnVertex target;
		randomIndex = Random.generateRandomNumber(graph.getVertices().size(),1);
		target = knoten.get(randomIndex-1);
		return target;
	}
	
	public List<MyOwnEdge> getVisitedEdges()
	{
		return delEdges;
	}
	
	public static void main(String[] args)
	{
		RandomEulerGraph randomGraph = new RandomEulerGraph(8);
		randomGraph.generateGraph();
		randomGraph.show();
		Fleury fleury = new Fleury(randomGraph.graph());
		fleury.start();
		List<MyOwnEdge> kantenFolge = new LinkedList<>(fleury.getVisitedEdges());
		System.out.println(kantenFolge);
	}
}
