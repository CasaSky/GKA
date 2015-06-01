package de.hawhh.informatik.gka.tabia.graphen.daten;

import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.StringTokenizer;
import java.util.regex.Pattern;

import javax.swing.JOptionPane;


/*
 * Autor Talal Tabia
 * Semester SS 2015
 * Praktikum GKA
 * DateiManager Klasse zum einlesen-und speichern einer Textdatei mit Hilfe einer Liste
 */
public class DateiManager
{
	private ArrayList<String> listVE; // Verbindungen Liste
	private String graphReference; // Reference des Graphen
	//private final static Pattern pattern = Pattern.compile("[הצüa-zA-Z:0-9]*,[הצüa-zA-Z:0-9]*");
	//private final static Pattern pattern2 = Pattern.compile("[הצüa-zA-Z:]*-?[0-9]+,[הצüa-zA-Z:]*-?[0-9]+");
	//private final static Pattern undirectedpatt = Pattern.compile("[הצüa-zA-Z:0-9]*[,הצüa-zA-Z:0-9]*");
	private final static Pattern refpattern = Pattern.compile("#[a-zA-Z]*");
	private Scanner loadScanner;

	public DateiManager()
	{
		listVE=new ArrayList<String>();
		graphReference = new String();
	}
	
	public DateiManager(ArrayList<String> list)
	{
		this.listVE=list;
	}
	
	// Einlesen einer bestimmten Textdatei und die Daten in list speichern.
	public void dateiLesen(String pfad) throws IOException
	{
		assert pfad != null : "Vorbedingung verletzt: pfad != null";
		FileReader file = new FileReader(pfad);
		loadScanner = new Scanner(file);
		
		// Wenn eine Referenz der Form # vorhanden ist, merken
		if(loadScanner.hasNext(refpattern))
			graphReference=loadScanner.nextLine();
		else // Wenn nicht, ist die Referenz undirected
			graphReference="#undirected";
		
		
		while (loadScanner.hasNext())
		{
			String str = loadScanner.nextLine();
			//System.out.println(str);
				splitB(str,",");
				ArrayList<String > list = new ArrayList<>(listVE);
				listVE= new ArrayList<>();
				for(String s : list)
				splitB(s, ":");
		}
		
//		while (loadScanner.hasNext(undirectedpatt) || loadScanner.hasNext(pattern))
//		{	
//			String str = loadScanner.nextLine();
//				splitB(str, ",");
//		}

		loadScanner.close();
		//System.out.println("Die gelesene VE Liste: "+listVE);
		//System.out.println("Die Referenz des Graphen ist: "+graphReference);
	}
	
	//Speichern die Daten von list in einer bestimmten Textdatei
	public void saveResults(String data, String i)
	{
		assert data != null : "Vorbedingung verletzt: data != null";
		PrintWriter pwriter = null;
		try {
			pwriter = new PrintWriter(new BufferedWriter(new FileWriter("bspGraphen/"+i+".text")));
			//for(String s : listVE)
			pwriter.println(data);
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, "Fehler beim Einlesen der Datei",
					"Fehlermeldung", JOptionPane.ERROR_MESSAGE);
		} finally {
			if (pwriter !=null){
				pwriter.flush();
				pwriter.close();
			}
		}
	}
	
	public void splitB(String str, String delimiter)
	{
		// Splitten nach kommata
		StringTokenizer st = new StringTokenizer(str, delimiter);
		while(st.hasMoreElements())
		{
			listVE.add(""+st.nextToken().replaceAll(" ", ""));
			
		}

	}
	//main
	public static void main(String[] args)
	{
		DateiManager m = new DateiManager();
		try {
			m.dateiLesen("C:/Users/Talal/Documents/Workspace/Aufgabe1/bin/bsp2.graph");
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, "Fehler beim Einlesen der Datei",
					"Fehlermeldung", JOptionPane.ERROR_MESSAGE);
		}
		//System.out.println(m.toString());
		
		//m.dateiSpeichern("./test2.txt");
	}
	
	public ArrayList<String> getList()
	{
		return listVE;
	}
	
	public String getGraphReference()
	{
		return graphReference;
	}
	
	@Override
	public String toString()
	{
		String text = "Die VE List: ";
		for(String string : listVE)
		text+=","+string;
		return text;
	}

}
