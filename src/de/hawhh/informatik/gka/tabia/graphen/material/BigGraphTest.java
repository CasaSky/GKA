package de.hawhh.informatik.gka.tabia.graphen.material;

import static org.junit.Assert.*;

import java.util.Collection;

import org.junit.Test;

import de.hawhh.informatik.gka.tabia.graphen.algorithmen.AStern;
import de.hawhh.informatik.gka.tabia.graphen.algorithmen.Dijkstra;

public class BigGraphTest
{

	// mehrere kleine Graphen
	@Test
	public void testAlgorithms()
	{
		for (int i=1; i<=5; i++)
		{
		BigGraph big = new BigGraph("#undirected");
		big.generateGraph();
		MyOwnVertex target = new MyOwnVertex("0",0);
		Collection<MyOwnVertex> next = big.graph().getMygraph().getNeighbors(target); 
		Object[] array = next.toArray();
		if(array.length!=0)
		{
		MyOwnVertex source = ((MyOwnVertex) array[0]);
		Dijkstra di = new Dijkstra(big.graph(), source, target);
		AStern astern = new AStern(big.graph(), source, target);
		di.start();
		astern.start();
		assertEquals(di.shortPath(), astern.shortPath());
		assertEquals(di.getLength(), astern.getLength());
		}
		else
			System.out.println("In Test "+i+" gibt es keinen Weg!");
		}
	}
	
	// Big Graph mit 100 Knoten und 6000 Kanten
	@Test
	public void testBigGraph()
	{
		BigGraph big = new BigGraph("#undirected", 100, 6000);
		big.generateGraph();
		MyOwnVertex target = new MyOwnVertex("0",0);
		Collection<MyOwnVertex> next = big.graph().getMygraph().getNeighbors(target); 
		Object[] array = next.toArray();
		if(array.length!=0)
		{
		MyOwnVertex source = ((MyOwnVertex) array[0]);
		Dijkstra di = new Dijkstra(big.graph(), source, target);
		AStern astern = new AStern(big.graph(), source, target);
		di.start();
		astern.start();
		di.start();
		assertEquals(di.shortPath(), astern.shortPath());
		assertEquals(di.getLength(), astern.getLength());
		System.out.println("Test Big Graph");
		System.out.println("Counter Distkra: "+di.counter());
		System.out.println("Counter A*: "+astern.counter());
		}
		else
			System.out.println("In Big Graph, gibt es keinen Weg!");
	}

}
