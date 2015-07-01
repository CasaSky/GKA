package de.hawhh.informatik.gka.tabia.graphen.material;
import java.util.List;

public final class Eulerkreis
{

	public static boolean hatEulertour(JungGraph graph)
	{
//		boolean isEulerkreis=true;
//		for (MyOwnVertex v : graph.vertexSet())
//		{
//			Collection<MyOwnVertex> neighbors = graph.getNeighbors(v);
//			if (neighbors.size() % 2 != 0)
//				return false;
//			
//		}
//		return isEulerkreis;
		for (MyOwnVertex v : graph.getVertices())
		{
			if (graph.grad(v) % 2 != 0)
				return false;
		}
		return true;
	}
	
	public static boolean isEulerKreis(List<MyOwnEdge> kantenFolge, JungGraph graph)
	{
		// Voraussetzung : der Graph soll mindestens eine Eulertour haben sollen
		if (hatEulertour(graph))
		{
			int size = kantenFolge.size(); 		// Länge der KantenFolge
			// Die KantenFolge soll jede Kante des Graphen nur einmal enthalten
			if (size == graph.edgeSet().size())
			{
				System.out.println("passt");
				return connected(kantenFolge);
			}
		}
		return false;
	}
	
	// Geschlossene Kantenfolge, Wenn der Source der AnfangsKante das Target der ZielKante ist 
	public static boolean connected(List<MyOwnEdge> kantenFolge)
	{
		int size = kantenFolge.size();
		MyOwnEdge startEdge = kantenFolge.get(0);
		MyOwnEdge finishEdge = kantenFolge.get(size-1);
		MyOwnVertex s1 = startEdge.source();
		MyOwnVertex t1 = startEdge.target();
		MyOwnVertex s2 = finishEdge.source();
		MyOwnVertex t2 = finishEdge.target();
		System.out.println("A "+s1.getName()+" B "+t1.getName()+" C "+s2.getName()+" D "+t2.getName());
		return (s1.equals(s2) || s1.equals(t2)) || (t1.equals(s2) || t1.equals(t2));
	}
}
