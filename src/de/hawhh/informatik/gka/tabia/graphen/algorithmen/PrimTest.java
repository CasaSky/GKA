package de.hawhh.informatik.gka.tabia.graphen.algorithmen;

import static org.junit.Assert.*;

import org.junit.Test;

import de.hawhh.informatik.gka.tabia.graphen.material.BigGraph;

public class PrimTest
{
	@Test
	public void testPrim_Kruskal_100_600()
	{
		BigGraph biggraph = new BigGraph("#attributed #weighted", 100, 600);
		biggraph.generateGraph();
		Prim prim = new Prim(biggraph.graph());
		prim.start();
		Kruskal kruskal = new Kruskal(biggraph.graph());
		kruskal.start();
		assertEquals(kruskal.kantenGewichtSumme(),prim.kantenGewichtSumme());	
	}
	
	@Test
	public void testPrim_Kruskal_1000_6000()
	{
		BigGraph biggraph = new BigGraph("#attributed #weighted", 1000, 6000);
		biggraph.generateGraph();
		Prim prim = new Prim(biggraph.graph());
		prim.start();
		Kruskal kruskal = new Kruskal(biggraph.graph());
		kruskal.start();
		assertEquals(kruskal.kantenGewichtSumme(),prim.kantenGewichtSumme());	
	}
	
	@Test
	public void testPrim_Kruskal_2000_8000()
	{
		BigGraph biggraph = new BigGraph("#attributed #weighted", 2000, 8000);
		biggraph.generateGraph();
		Prim prim = new Prim(biggraph.graph());
		prim.start();
		Kruskal kruskal = new Kruskal(biggraph.graph());
		kruskal.start();
		assertEquals(kruskal.kantenGewichtSumme(),prim.kantenGewichtSumme());	
	}

}
