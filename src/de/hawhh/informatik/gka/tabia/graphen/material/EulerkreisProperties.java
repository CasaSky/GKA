package de.hawhh.informatik.gka.tabia.graphen.material;
import java.util.List;

public final class EulerkreisProperties
{

	// Ist der Graph zusammenhaengend und hat er eine Eulertour
	public static boolean isEulerGraph(JungGraph graph)
	{
		assert graph != null : "Vorbedingung verletzt: graph != null";
		 return (hatEulertour(graph) && istZusammenHaengend(graph));
	}
	
	// Ein Graph hat mindestens eine Eulertour, wenn jeder seiner Knoten einen geraden Grad hat
	public static boolean hatEulertour(JungGraph graph)
	{
		for (MyOwnVertex v : graph.getVertices())
		{
			if (graph.grad(v) % 2 != 0)
				return false;
		}
		return true;
	}
	
	public static boolean isEulerKreis(List<MyOwnEdge> kantenFolge, JungGraph graph)
	{
		if (kantenFolge.isEmpty() || kantenFolge.size() <= 2)
			return false;
		// Voraussetzung : der Graph soll mindestens eine Eulertour haben sollen
		if (isEulerGraph(graph))
		{
			int size = kantenFolge.size(); 		// Länge der KantenFolge
			// Die KantenFolge soll jede Kante des Graphen nur einmal enthalten

			if (size == graph.edgeSet().size())
				return isConnected(kantenFolge);
		}
		return false;
	}
	
	// Geschlossene Kantenfolge, Wenn die erste und die letzte Kanten einen gemeinsamen Knoten haben
	public static boolean isConnected(List<MyOwnEdge> kantenFolge)
	{
		int size = kantenFolge.size();
		MyOwnEdge startEdge = kantenFolge.get(0);
		MyOwnEdge finishEdge = kantenFolge.get(size-1);
		MyOwnVertex s1 = startEdge.source();
		MyOwnVertex t1 = startEdge.target();
		MyOwnVertex s2 = finishEdge.source();
		MyOwnVertex t2 = finishEdge.target();
		//System.out.println(" A "+s1.getName()+" B "+t1.getName()+" C "+s2.getName()+" D "+t2.getName());
		return (s1.equals(s2) || s1.equals(t2)) || (t1.equals(s2) || t1.equals(t2));
	}
	
	public static boolean istZusammenHaengend(JungGraph graph)
	{
		assert graph != null : "Vorbedingung verletzt: graph != null";
		return (graph.getMygraph().getEdgeCount() > graph.getMygraph().getVertexCount() -1 && graph.getReferenz().equals("#undirected"));		
	}
}
