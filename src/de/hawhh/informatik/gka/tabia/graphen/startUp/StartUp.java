package de.hawhh.informatik.gka.tabia.graphen.startUp;

import java.util.Scanner;

import de.hawhh.informatik.gka.tabia.graphen.werkzeug.JungWerkzeug;

public class StartUp
{

	public static void main(String[] args)
	{
		System.out.println("Gib den Dateinnamen ein:");
		Scanner input = new Scanner(System.in);
		//String eingabe = input.next();
		@SuppressWarnings("unused")
		//JungWerkzeug werkzeug = new JungWerkzeug("bspGraphen/bsp"+eingabe+".graph");
		JungWerkzeug werkzeug = new JungWerkzeug("bspGraphen/bsp6.graph");
		input.close();
	}

}
