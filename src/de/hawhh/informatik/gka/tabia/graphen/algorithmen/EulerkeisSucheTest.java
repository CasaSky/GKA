package de.hawhh.informatik.gka.tabia.graphen.algorithmen;

import static org.junit.Assert.*;

import java.io.IOException;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import org.junit.Test;

import de.hawhh.informatik.gka.tabia.graphen.daten.DateiManager;
import de.hawhh.informatik.gka.tabia.graphen.material.EulerkreisProperties;
import de.hawhh.informatik.gka.tabia.graphen.material.JungGraph;
import de.hawhh.informatik.gka.tabia.graphen.material.MyOwnEdge;
import de.hawhh.informatik.gka.tabia.graphen.material.MyOwnVertex;
import de.hawhh.informatik.gka.tabia.graphen.material.RandomEulerGraph;
import de.hawhh.informatik.gka.tabia.graphen.werkzeug.JungWerkzeug;

public class EulerkeisSucheTest
{
	DateiManager manager;
	JungGraph graph = new JungGraph("#undirected");
	String pfad6 = "C:/Users/talal_000/Dropbox/Java/EclipseWorkspace/bspGraphen/bsp6.graph";
	String pfad5 = "C:/Users/talal_000/Dropbox/Java/EclipseWorkspace/bspGraphen/bsp5.graph";

	


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
		leseEin(pfad6);
		graph.listToGraph(manager.getList());
		List<MyOwnEdge> kantenFolge = new LinkedList<>(graph.edgeSet());
		//TODO Kanten sind unsortiert !!Eulertour nichtvorhanden
		//assertTrue(Eulerkreis.isEulerKreis(kantenFolge, graph));
		assertTrue(EulerkreisProperties.isEulerGraph(graph));
	}
	
	@Test
	public void testEulerGraph5()
	{
		leseEin(pfad5);
		graph.listToGraph(manager.getList());
		List<MyOwnEdge> kantenFolge = new LinkedList<>(graph.edgeSet());
		assertFalse(EulerkreisProperties.isEulerKreis(kantenFolge, graph));
	}
	
	@Test
	public void testRandomEulerGraph1()
	{
		RandomEulerGraph random = new RandomEulerGraph(20);
		random.generateGraph();
		assertTrue(EulerkreisProperties.hatEulertour(graph));
	}
	
	@Test
	public void testRandomEulerGraph2()
	{
		RandomEulerGraph random = new RandomEulerGraph(100);
		random.generateGraph();
		// TODO ------------------------------ is euler kreis muss verwendet werden
		assertTrue(EulerkreisProperties.hatEulertour((random.graph())));
	}
	
	@Test
	public void testFleuryAufRandomGraph()
	{
		RandomEulerGraph randomGraph = new RandomEulerGraph(15);
		randomGraph.generateGraph();
		Fleury fleury = new Fleury(randomGraph.graph());
		fleury.start();
		List<MyOwnEdge> kantenFolge = new LinkedList<>(fleury.getVisitedEdges());
		assertTrue(EulerkreisProperties.isEulerKreis(kantenFolge, randomGraph.graph()));
	}
	
	@Test
	public void testFleuryAufBeispiel6()
	{
		leseEin(pfad6);
		graph.listToGraph(manager.getList());
		Fleury fleury = new Fleury(graph);
		fleury.start();
		List<MyOwnEdge> kantenFolge = new LinkedList<>(fleury.getVisitedEdges());
		assertTrue(EulerkreisProperties.isEulerKreis(kantenFolge, graph));
	}
	
	@Test
	public void testFleuryAufBeispiel5()
	{
		leseEin(pfad5);
		graph.listToGraph(manager.getList());
		Fleury fleury = new Fleury(graph);
		fleury.start();
		List<MyOwnEdge> kantenFolge = new LinkedList<>(fleury.getVisitedEdges());
		assertFalse(EulerkreisProperties.isEulerKreis(kantenFolge, graph));
	}
	
}
