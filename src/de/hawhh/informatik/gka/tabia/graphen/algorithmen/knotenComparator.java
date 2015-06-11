package de.hawhh.informatik.gka.tabia.graphen.algorithmen;

import java.util.Comparator;
import java.util.Map;

import de.hawhh.informatik.gka.tabia.graphen.material.MyOwnVertex;

public class knotenComparator implements Comparator<MyOwnVertex>  
{
    	Map<MyOwnVertex, Integer> distance;
    	
    	public knotenComparator(Map<MyOwnVertex, Integer> distance)
		{
    		this.distance = distance;
		}
        @Override
        public int compare(MyOwnVertex o1, MyOwnVertex o2) 
        {
        	int v1 = distance.get(o1);
        	int v2 = distance.get(o2);
        	return Integer.compare(v1, v2);
        }
}
