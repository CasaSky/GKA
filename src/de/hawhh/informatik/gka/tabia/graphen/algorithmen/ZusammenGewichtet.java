package de.hawhh.informatik.gka.tabia.graphen.algorithmen;

import de.hawhh.informatik.gka.tabia.graphen.material.JungGraph;
public class ZusammenGewichtet
{
	static boolean istzusammen;
	
	public static boolean istzusammenUndGewichtet(JungGraph graph)
	{
		if (graph.getMygraph().getEdgeCount() == graph.getMygraph().getVertexCount() -1 && graph.getReferenz().equals("#attributed #weighted"))
			istzusammen = true;
		
		return istzusammen;
	}

}
