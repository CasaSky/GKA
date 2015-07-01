package de.hawhh.informatik.gka.tabia.graphen.algorithmen;

import static org.junit.Assert.*;

import java.io.IOException;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import org.junit.Test;

import de.hawhh.informatik.gka.tabia.graphen.daten.DateiManager;
import de.hawhh.informatik.gka.tabia.graphen.material.Eulerkreis;
import de.hawhh.informatik.gka.tabia.graphen.material.JungGraph;
import de.hawhh.informatik.gka.tabia.graphen.material.MyOwnEdge;
import de.hawhh.informatik.gka.tabia.graphen.material.MyOwnVertex;
import de.hawhh.informatik.gka.tabia.graphen.material.RandomEulerGraph;
import de.hawhh.informatik.gka.tabia.graphen.werkzeug.JungWerkzeug;

public class EuleurkeissucheTest
{
	DateiManager manager;
	JungGraph graph = new JungGraph("#undirected");
	


	public void leseEin(String pfad)
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
	public void testEulerGraph6()
	{
		String pfad = "C:/Users/talal_000/Dropbox/Java/EclipseWorkspace/bspGraphen/bsp6.graph";
		leseEin(pfad);
		graph.listToGraph(manager.getList());
		List<MyOwnEdge> kantenFolge = new LinkedList<>(graph.edgeSet());
		//TODO Kanten sind unsortiert !!Eulertour nichtvorhanden
		//assertTrue(Eulerkreis.isEulerKreis(kantenFolge, graph));
		assertTrue(Eulerkreis.hatEulertour(graph));
	}
	
	@Test
	public void testEulerGraph5()
	{
		String pfad = "C:/Users/talal_000/Dropbox/Java/EclipseWorkspace/bspGraphen/bsp5.graph";
		leseEin(pfad);
		graph.listToGraph(manager.getList());
		List<MyOwnEdge> kantenFolge = new LinkedList<>(graph.edgeSet());
		assertFalse(Eulerkreis.isEulerKreis(kantenFolge, graph));
	}
	
	@Test
	public void testRandomEulerGraph1()
	{
		RandomEulerGraph random = new RandomEulerGraph("#undirected", 20);
		random.generateGraph();
		assertTrue(Eulerkreis.hatEulertour(graph));
	}
	
	@Test
	public void testRandomEulerGraph2()
	{
		RandomEulerGraph random = new RandomEulerGraph("#undirected", 100);
		random.generateGraph();
		// TODO ------------------------------ is euler kreis muss verwendet werden
		assertTrue(Eulerkreis.hatEulertour((random.graph())));
	}
	
	@Test
	public void testFleuryRandomGraph()
	{
		RandomEulerGraph randomGraph = new RandomEulerGraph("#undirected", 15);
		randomGraph.generateGraph();
		Fleury fleury = new Fleury(randomGraph.graph());
		fleury.start();
		List<MyOwnEdge> kantenFolge = new LinkedList<>(fleury.getVisitedEdges());
		System.out.println(kantenFolge);
		assertTrue(Eulerkreis.isEulerKreis(kantenFolge, randomGraph.graph()));
	}
	
	@Test
	public void testFleuryBeispiel6()
	{
		System.out.println("bsp6");
		String pfad = "C:/Users/talal_000/Dropbox/Java/EclipseWorkspace/bspGraphen/bsp6.graph";
		leseEin(pfad);
		graph.listToGraph(manager.getList());
		Fleury fleury = new Fleury(graph);
		fleury.start();
		List<MyOwnEdge> kantenFolge = new LinkedList<>(fleury.getVisitedEdges());
		System.out.println(kantenFolge);
		assertTrue(Eulerkreis.isEulerKreis(kantenFolge, graph));
	}
	
	@Test
	public void testFleuryBeispiel5()
	{
		System.out.println("bsp5");
		String pfad = "C:/Users/talal_000/Dropbox/Java/EclipseWorkspace/bspGraphen/bsp5.graph";
		leseEin(pfad);
		graph.listToGraph(manager.getList());
		Fleury fleury = new Fleury(graph);
		fleury.start();
		List<MyOwnEdge> kantenFolge = new LinkedList<>(fleury.getVisitedEdges());
		assertFalse(Eulerkreis.isEulerKreis(kantenFolge, graph));
	}
	
}
