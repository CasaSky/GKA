package de.hawhh.informatik.gka.tabia.graphen.algorithmen;

import static org.junit.Assert.*;

import org.junit.Test;

import de.hawhh.informatik.gka.tabia.graphen.material.BigGraph;

public class PrimFiboHeapTest
{

	@Test
	public void testPrimFibo_Prim_100_600()
	{
		BigGraph biggraph = new BigGraph("#attributed #weighted", 100, 600);
		biggraph.generateGraph();
		PrimFiboHeap primfibo = new PrimFiboHeap(biggraph.graph());
		primfibo.start();
		Prim prim = new Prim(biggraph.graph());
		prim.start();
		assertEquals(prim.kantenGewichtSumme(),primfibo.kantenGewichtSumme());	
	}
	
	@Test
	public void testPrimFibo_Prim_1000_6000()
	{
		BigGraph biggraph = new BigGraph("#attributed #weighted", 1000, 6000);
		biggraph.generateGraph();
		PrimFiboHeap primfibo = new PrimFiboHeap(biggraph.graph());
		primfibo.start();
		Prim prim = new Prim(biggraph.graph());
		prim.start();
		assertEquals(prim.kantenGewichtSumme(),primfibo.kantenGewichtSumme());	
	}
	
	@Test
	public void testPrimFibo_Prim_10000_60000()
	{
		BigGraph biggraph = new BigGraph("#attributed #weighted", 10000, 60000);
		biggraph.generateGraph();
		PrimFiboHeap primfibo = new PrimFiboHeap(biggraph.graph());
		primfibo.start();
		Prim prim = new Prim(biggraph.graph());
		prim.start();
		assertEquals(prim.kantenGewichtSumme(),primfibo.kantenGewichtSumme());	
	}

}
