package de.hawhh.informatik.gka.tabia.graphen.material;

import java.util.Collection;

import javax.swing.JOptionPane;

import de.hawhh.informatik.gka.tabia.graphen.algorithmen.AStern;
import de.hawhh.informatik.gka.tabia.graphen.algorithmen.Dijkstra;
import de.hawhh.informatik.gka.tabia.graphen.daten.DateiManager;
import de.hawhh.informatik.gka.tabia.graphen.werkzeug.JungWerkzeug;

// Mehrfach Kanten sind erlaubt!
public class BigGraph 
{
	private JungGraph graph;
	private int maxKnoten;
	private int kantenAnzahl;
	private Collection<MyOwnVertex> knotenSet;
	
	public BigGraph(String referenz, int maxKnoten, int kantenAnzahl)
	{
		graph = new JungGraph(referenz);
		this.maxKnoten = maxKnoten;
		this.kantenAnzahl = kantenAnzahl;
	}
	
	public BigGraph(String referenz)
	{
		graph = new JungGraph(referenz);
		this.maxKnoten = generateRandomNumber(3, 100);
		this.kantenAnzahl = generateRandomNumber(1, 50);
	}
	
	public JungGraph graph()
	{
		return graph;
	}
	
	public Collection<MyOwnVertex> knotenSet()
	{
		return knotenSet;
	}
	
	// Generiert n zufälligen Knoten und fügt die im Graphen ein
	public void generateKnoten()
	{
		for (int i=1; i< maxKnoten; i++)
		{
			int temp;
			MyOwnVertex v;
			do
			{
				temp = generateRandomNumber(maxKnoten*3, 1);
				 v = new MyOwnVertex(""+temp,temp);
			} while (graph.getVertices().contains(v));
			graph.knotenEinfuegen(Integer.toString(temp),temp);
		}
		// das Target
		graph.knotenEinfuegen("0", 0);
	}
	
	// Generiert eine Random Number zwichen min und max
	public int generateRandomNumber(int max, int min)
	{
		return (int) (Math.random() * (max - min) + min);
	}
	
	// Erstellt einen Graphen mit n zufälligen Knoten und n zufälligen Kanten
	public void generateGraph()
	{

		generateKnoten();
		// ----------------------
		for (int i = 1; i <= kantenAnzahl; i++)
		{
			MyOwnVertex a;
			MyOwnVertex b;
			do
			{
				a = getRandomVertex();
				b = getRandomVertex();
			} while (graph.getMygraph().findEdge(a, b) != null);
			int gewicht;
			// Das Gewicht der Kante hängt von der größten Heuristic der beiden Knoten ab
			if (a.getAttribute() < b.getAttribute())
			{
				// Das Gewicht soll über die Heuristic Größe liegen
				gewicht = generateRandomNumber(2*b.getAttribute(),b.getAttribute());
			}
			else
				gewicht = generateRandomNumber(2*a.getAttribute(),a.getAttribute());
			
			// Fügt eine Kante zwischen zwei zufälligen Knoten und das geschätzte Gewicht ein
			graph.kanteEinfuegen(a,b , gewicht);
		}
		
	}
	
	// Holt einen zufälligen Knoten aus dem Graphen
	public MyOwnVertex getRandomVertex()
	{
		knotenSet = graph.getVertices();
		Object[] array = knotenSet.toArray();
		int randomIndex = generateRandomNumber(maxKnoten,1);
		return (MyOwnVertex) array[randomIndex];
	}
	
	// Erstele eine GUI
	public void show ()
	{
		@SuppressWarnings("unused")
		JungWerkzeug werkzeug = new JungWerkzeug(graph);
	}
	
	public void createFiveGraphs()
	{
		DateiManager manager = new DateiManager();
		String data = "\r\n ";
		for (int i=1; i<=5; i++)
		{
		BigGraph big = new BigGraph("#undirected");
		big.generateGraph();
		big.show();
		MyOwnVertex target = new MyOwnVertex("0",0);
		Collection<MyOwnVertex> next = big.graph().getMygraph().getNeighbors(target); 
		Object[] array = next.toArray();
		if (array.length!=0)
		{
		MyOwnVertex source = ((MyOwnVertex) array[0]);
		Dijkstra di = new Dijkstra(big.graph(), source, target);
		AStern astern = new AStern(big.graph(), source, target);
		di.start();
		astern.start();
		data += "Dijktra ShortPath: "+di.shortPath()+" A* ShortPath: "+astern.shortPath()+"\r\n ";
		data += "Dijktra Length: "+di.getLength()+" A* Length: "+astern.getLength()+"\r\n ";
		data += "Dijktra Counter: "+di.counter()+" A* Counter: "+astern.counter()+"\r\n ";
		data +="------------------------------------------------"+"\r\n";
		}
		else
			data +="Es gibt keinen Weg bei Graph "+i+"\r\n";
		}
		String i = JOptionPane.showInputDialog(null, "Unter welchen Namen möchten Sie speichern:");
		manager.saveResults(data, i);
	}
	
	public static void main(String[] args)
	{	
		BigGraph big = new BigGraph("#undirected", 10, 20);
		big.generateGraph();
		big.show();
		MyOwnVertex target = new MyOwnVertex("0",0);
		Collection<MyOwnVertex> next = big.graph().getMygraph().getNeighbors(target); 
		Object[] array = next.toArray();
		if (array.length!=0)
		{
		MyOwnVertex source = ((MyOwnVertex) array[0]);
		System.out.println(target.getName());
		System.out.println(source.getName());
		Dijkstra di = new Dijkstra(big.graph(), source, target);
		AStern astern = new AStern(big.graph(), source, target);
		di.start();
		astern.start();
		di.start();
		System.out.println("Die Länge: "+di.getLength());
		System.out.println("Der kürzeste Weg: "+di.shortPath());
		}
		else
			System.out.println("Es gibt keinen Weg!");
	
	}
}
