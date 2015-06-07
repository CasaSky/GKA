package de.hawhh.informatik.gka.tabia.graphen.material;

public class MyOwnVertex
{
    private String name;
    private int attribute;
    private boolean withattribute;
    private int kantenGewicht;
	private int omega = Integer.MAX_VALUE;
	private MyOwnVertex vorgaenger;
	private int vorgaengerKantenGewicht;
    
	public MyOwnVertex()
    {}
    
    public MyOwnVertex(String name, int attribute) 
    {
        this.name = name;
        this.attribute = attribute;
        this.withattribute = true;
        this.kantenGewicht = omega;
        this.vorgaenger = null;
        this.vorgaengerKantenGewicht = 0;
    }
    
    public MyOwnVertex(String name, int attribute, int kantenGewicht) 
    {
        this.name = name;
        this.attribute = attribute;
        this.withattribute = true;
        this.kantenGewicht = kantenGewicht;
    }
    
    public MyOwnVertex(String name) 
    {
        this.name = name;
    }
    
    public String toString() // Debuging
    { 
    	if (withattribute())
    		return name+":"+attribute;
    	else return name;
    }
    
    @Override
    public int hashCode()
    {
        final int prime = 31;
        int result = 1;
        result = prime * result + attribute;
        result = prime * result + ((name == null) ? 0 : name.hashCode());
        return result;
    }
    
    public boolean equals(Object o)
    {
    	if (o instanceof MyOwnVertex) {
			return name.equals(((MyOwnVertex)o).getName());
		}
    	return false;
    }
    
	public String getName()
	{
		return name;
	}
	
	public int getAttribute()
	{
		return attribute;
	}
	
	public void setName(String name)
	{
		this.name = name;
	}
	
	public void setAttribute(int attribute)
	{
		this.attribute = attribute;
	} 
	
	public boolean withattribute()
	{
		return withattribute;
	}
	
    public int getKantenGewicht()
	{
		return kantenGewicht;
	}

	public void setKantenGewicht(int kantenGewicht)
	{
		this.kantenGewicht = kantenGewicht;
	}

	public void setVorgaenger(MyOwnVertex vorgaenger)
	{
		this.vorgaenger = vorgaenger;
	}

	public MyOwnVertex getVorgaenger()
	{
		return vorgaenger;
	}

	public int getVorgaengerKantenGewicht()
	{
		return vorgaengerKantenGewicht;
	}

	public void setVorgaengerKantenGewicht(int vorgaengerKantenGewicht)
	{
		this.vorgaengerKantenGewicht = vorgaengerKantenGewicht;
	}
	
}
