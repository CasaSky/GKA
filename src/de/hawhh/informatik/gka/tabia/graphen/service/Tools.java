package de.hawhh.informatik.gka.tabia.graphen.service;

import java.util.ArrayList;

import de.hawhh.informatik.gka.tabia.graphen.material.MyOwnVertex;

public final class Tools
{
	private static ArrayList<String> attlist;
	private static ArrayList<String> undiweightlist;
	private static ArrayList<MyOwnVertex> vertex;
	private static String patternchar = "[הצüa-zA-Z]*";
	private static String patternnum = "[0-9]*";
	
	public static ArrayList<String> generateAttlist(ArrayList<String> list)
	{
		attlist=new ArrayList<>();
		int counter=0;
		Boolean marker=false;
		do 
		{
			if (!marker)
			{
				attlist.add(list.get(counter));
				counter+=2;
				marker=true;
			}
			else
			{
				attlist.add(list.get(counter));
				counter+=3;
				marker=false;
			}
			
		} while (counter<list.size());
		System.out.println(attlist);
		return attlist;
	}
	
	public static ArrayList<String> generateUndiweightlist(ArrayList<String> list)
	{
		undiweightlist=new ArrayList<>();
		int counter=0;
		int marker=0;
		do 
		{
			if (marker==0)
			{
				undiweightlist.add(list.get(counter));
				counter++;
				marker++;
			}
			else
			{
				undiweightlist.add(list.get(counter));
				counter+=2;
				marker=0;
			}
			
		} while (counter<list.size());
		System.out.println(undiweightlist);
		return undiweightlist;
	}
	
	public static ArrayList<MyOwnVertex> generateVertex(ArrayList<String> list)
	{
		vertex=new ArrayList<>();
		MyOwnVertex v;
		String s="";
		int i=0;
		int counter=0;
		do
		{
			if (list.get(counter).matches(patternchar))
			{
				s = list.get(counter);
				counter++;
			}
			if (list.get(counter).matches(patternnum))
			{
				i=Integer.parseInt(list.get(counter+1));
				counter++;
			}
			v = new MyOwnVertex(s,i);
			vertex.add(v);
		}while(counter<list.size());
		return vertex;
	}

	public static ArrayList<String> getAttlist()
	{
		return attlist;
	}

	public static ArrayList<String> getUndiweightlist()
	{
		return undiweightlist;
	}
}
