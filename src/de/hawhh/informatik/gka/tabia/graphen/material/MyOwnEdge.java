package de.hawhh.informatik.gka.tabia.graphen.material;

public class MyOwnEdge
{
	private int gewicht;
	private MyOwnVertex _source;
	private MyOwnVertex _target;
	
	public MyOwnEdge(MyOwnVertex source, MyOwnVertex target, int gewicht)
	{
		_source = source;
		_target = target;
		this.gewicht=gewicht;
	}
	
	@Override
    public String toString() // Debuging
    { 
        return ""+gewicht;
    }

	public int getGewicht()
	{
		return gewicht;
	} 
	public MyOwnVertex source()
	{
		return _source;
	}
	
	public MyOwnVertex target()
	{
		return _target;
	}
}
