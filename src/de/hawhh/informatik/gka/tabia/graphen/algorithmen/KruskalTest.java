package de.hawhh.informatik.gka.tabia.graphen.algorithmen;

import static org.junit.Assert.*;

import org.junit.Test;

import de.hawhh.informatik.gka.tabia.graphen.material.BigGraph;

public class KruskalTest
{
	@Test
	public void testKruskal()
	{
		BigGraph biggraph = new BigGraph("#attributed #weighted", 10, 20);
		biggraph.generateGraph();
		Kruskal kruskal = new Kruskal(biggraph.graph());
		kruskal.start();
		Prim prim = new Prim(biggraph.graph());
		prim.start();
		assertEquals(kruskal.kantenGewichtSumme(),prim.kantenGewichtSumme());
	}

}
