package de.hawhh.informatik.gka.tabia.graphen.algorithmen;

import static org.junit.Assert.*;

import org.junit.Test;

import de.hawhh.informatik.gka.tabia.graphen.material.BigGraph;

public class PrimTest
{
	@Test
	public void testPrim()
	{
		BigGraph biggraph = new BigGraph("#attributed #weighted", 100, 600);
		biggraph.generateGraph();
		Prim prim = new Prim(biggraph.graph());
		prim.start();
		Kruskal kruskal = new Kruskal(biggraph.graph());
		kruskal.start();
		assertEquals(kruskal.kantenGewichtSumme(),prim.kantenGewichtSumme());	
		
	}

}
