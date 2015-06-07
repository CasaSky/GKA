package de.hawhh.informatik.gka.tabia.graphen.algorithmen;

import java.util.Comparator;

import de.hawhh.informatik.gka.tabia.graphen.material.MyOwnEdge;

public class kantenComparator implements Comparator<MyOwnEdge>  
{
    	public kantenComparator()
		{
		}
        @Override
        public int compare(MyOwnEdge e1, MyOwnEdge e2) 
        {
        	int a = e1.getGewicht();
        	int b = e2.getGewicht();
        	return Integer.compare(a, b);
        }
    
}
