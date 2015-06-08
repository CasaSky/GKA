package de.hawhh.informatik.gka.tabia.graphen.algorithmen;

import static org.junit.Assert.*;

import java.io.IOException;
import java.util.ArrayList;

import org.junit.Test;

import de.hawhh.informatik.gka.tabia.graphen.daten.DateiManager;
import de.hawhh.informatik.gka.tabia.graphen.material.JungGraph;
import de.hawhh.informatik.gka.tabia.graphen.material.MyOwnVertex;

public class ASternTest 
{

	JungGraph graph = new JungGraph("#directed #weighted");
	String pfad = "C:/Users/talal_000/Dropbox/Java/EclipseWorkspace/bspGraphen/bsp3.graph";
	DateiManager manager;
	JungGraph graph3;
	AStern astern;
	
	public void leseEin()
	{
		manager = new DateiManager();
		try
		{
			manager.dateiLesen(pfad);
		} catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	

	@Test
	public void testKonstruktor()
	{
		MyOwnVertex anfang = new MyOwnVertex("a");
		MyOwnVertex ende = new MyOwnVertex("e");
		astern = new AStern(graph, anfang, ende);
		assertEquals(anfang, astern.getAnfang());
		assertEquals(ende, astern.getTarget());
		assertEquals(graph, astern.graph());
	}
	
	@Test
	public void testAlgorithmus1()
	{
		graph.kanteEinfuegen(new MyOwnVertex("a"), new MyOwnVertex("b"), 5);
		graph.kanteEinfuegen(new MyOwnVertex("b"), new MyOwnVertex("c"), 1);
		graph.kanteEinfuegen(new MyOwnVertex("c"), new MyOwnVertex("f"), 1);
		graph.kanteEinfuegen(new MyOwnVertex("a"), new MyOwnVertex("d"), 1);
		graph.kanteEinfuegen(new MyOwnVertex("d"), new MyOwnVertex("e"), 1);
		// Beispiel für einen Graphen mit einem Weg von a nach e, wobei der kürzest Weg ist über a-d-e

		astern = new AStern(graph, new MyOwnVertex("a"), new MyOwnVertex("e"));
		assertTrue(astern.start());
		ArrayList<MyOwnVertex> shortPath = new ArrayList<>();
		shortPath.add(new MyOwnVertex("a"));
		shortPath.add(new MyOwnVertex("d"));
		shortPath.add(new MyOwnVertex("e"));
		assertEquals(shortPath, astern.shortPath());
		System.out.println("Der kürzeste Weg von a nach e ist: "+astern.shortPath().toString());
		assertEquals(2, astern.getLength());
	}
	
	@Test
	public void testMuensterHamburg()
	{
		leseEin();
		graph3 = new JungGraph(manager.getGraphReference());
		graph3.listAToGraph(manager.getList());

		// Beispiel 3

		astern = new AStern(graph3, new MyOwnVertex("Münster",237), new MyOwnVertex("Hamburg",0));
		assertTrue(astern.start());
		ArrayList<MyOwnVertex> shortPath = new ArrayList<>();
		shortPath.add(new MyOwnVertex("Münster",237));
		shortPath.add(new MyOwnVertex("Bremen",95));
		shortPath.add(new MyOwnVertex("Hamburg",0));
		assertEquals(shortPath, astern.shortPath());
		System.out.println("Der kürzeste Weg von Münster nach Hamburg ist: "+astern.shortPath().toString());
		assertEquals(300, astern.getLength());
	}
	
	@Test
	public void testMindenHamburg()
	{
		leseEin();
		graph3 = new JungGraph(manager.getGraphReference());
		graph3.listAToGraph(manager.getList());

		// Beispiel 3

		astern = new AStern(graph3, new MyOwnVertex("Minden",157), new MyOwnVertex("Hamburg",0));
		assertTrue(astern.start());
		ArrayList<MyOwnVertex> shortPath = new ArrayList<>();
		shortPath.add(new MyOwnVertex("Minden",157));
		shortPath.add(new MyOwnVertex("Walsrode",81));
		shortPath.add(new MyOwnVertex("Hamburg",0));
		assertEquals(shortPath, astern.shortPath());
		System.out.println("Der kürzeste Weg von Minden nach Hamburg ist: "+astern.shortPath().toString());
		assertEquals(227, astern.getLength());
	}
	
	@Test
	public void testHusumHamburg()
	{
		leseEin();
		graph3 = new JungGraph(manager.getGraphReference());
		graph3.listAToGraph(manager.getList());

		// Beispiel 3

		astern = new AStern(graph3, new MyOwnVertex("Husum",120), new MyOwnVertex("Hamburg",0));
		assertTrue(astern.start());
		ArrayList<MyOwnVertex> shortPath = new ArrayList<>();
		shortPath.add(new MyOwnVertex("Husum",120));
		shortPath.add(new MyOwnVertex("Kiel",86));
		shortPath.add(new MyOwnVertex("Uelzen",94));
		shortPath.add(new MyOwnVertex("Rotenburg",63));
		shortPath.add(new MyOwnVertex("Soltau",63));
		shortPath.add(new MyOwnVertex("Buxtehude",20));
		shortPath.add(new MyOwnVertex("Hamburg",0));
		assertEquals(shortPath, astern.shortPath());
		System.out.println("Der kürzeste Weg von Husum nach Hamburg ist: "+astern.shortPath().toString());
		assertEquals(518, astern.getLength());
	}
	
	@Test
	public void testDijkstra()
	{
		leseEin();
		graph3 = new JungGraph(manager.getGraphReference());
		graph3.listAToGraph(manager.getList());
		MyOwnVertex source = new MyOwnVertex("Münster",237);
		MyOwnVertex target = new MyOwnVertex("Hamburg",0);
		
		astern = new AStern(graph3, source, target);
		astern.start();
		ArrayList<MyOwnVertex> shortPath = new ArrayList<>();
		shortPath.add(new MyOwnVertex("Münster",237));
		shortPath.add(new MyOwnVertex("Bremen",95));
		shortPath.add(new MyOwnVertex("Hamburg",0));
		assertEquals(shortPath, astern.shortPath());
		
	}

}
