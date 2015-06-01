package de.hawhh.informatik.gka.tabia.graphen.algorithmen;

import static org.junit.Assert.*;

import java.io.IOException;

import org.junit.Test;

import de.hawhh.informatik.gka.tabia.graphen.daten.DateiManager;
import de.hawhh.informatik.gka.tabia.graphen.material.JungGraph;
import de.hawhh.informatik.gka.tabia.graphen.material.MyOwnVertex;

public class MyBFSTest
{
	JungGraph mygraph = new JungGraph("#directed");

	@Test
	public void testMyBFS()
	{
		mygraph.kanteEinfuegen(new MyOwnVertex("a"), new MyOwnVertex("b"),0);
		MyOwnVertex start= new MyOwnVertex("a");
		MyOwnVertex ende= new MyOwnVertex("b");
		MyBFS mybfs = new MyBFS(mygraph, start, ende);
		assertEquals(mygraph, mybfs.getMygraph());
		assertEquals(start, mybfs.getStartvertex());
		assertEquals(ende, mybfs.getEndvertex());
	}

	@Test
	public void testSucheTarget()
	{
		mygraph.kanteEinfuegen(new MyOwnVertex("a"), new MyOwnVertex("b"),0);
		mygraph.kanteEinfuegen(new MyOwnVertex("a"), new MyOwnVertex("c"),0);
		mygraph.kanteEinfuegen(new MyOwnVertex("a"), new MyOwnVertex("d"),0);
		mygraph.kanteEinfuegen(new MyOwnVertex("a"), new MyOwnVertex("g"),0);
		mygraph.kanteEinfuegen(new MyOwnVertex("d"), new MyOwnVertex("e"),0);
		mygraph.kanteEinfuegen(new MyOwnVertex("d"), new MyOwnVertex("f"),0);
		mygraph.kanteEinfuegen(new MyOwnVertex("g"), new MyOwnVertex("x"),0);

		MyOwnVertex start= new MyOwnVertex("a");
		MyOwnVertex ende= new MyOwnVertex("x");
		MyBFS mybfs = new MyBFS(mygraph, start, ende);
		assertTrue(mybfs.sucheTarget(start));
		System.out.println("Der Weg ist: "+mybfs.getShortPath()+ " Der Länge: "+mybfs.getCounter());
	}
	
	@Test
	public void testSucheTarger2()
	{
		DateiManager manager = new DateiManager();
		String pfad = "C:/Users/Talal/Documents/Workspace/Aufgabe1/bin/bsp1.graph";
		try {
			manager.dateiLesen(pfad);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		JungGraph mygraph2;
		mygraph2 = new JungGraph(manager.getGraphReference());
		mygraph2.listVEToGraph(manager.getList());
		MyOwnVertex start= new MyOwnVertex("a");
		MyOwnVertex ende= new MyOwnVertex("h");
		MyBFS mybfs = new MyBFS(mygraph2, start, ende);
		assertTrue(mybfs.sucheTarget(start));
		System.out.println("Der Weg ist: "+mybfs.getShortPath()+ " Der Länge: "+mybfs.getCounter());
	}

}
