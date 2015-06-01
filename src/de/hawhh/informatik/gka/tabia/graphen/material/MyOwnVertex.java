package de.hawhh.informatik.gka.tabia.graphen.material;

public class MyOwnVertex
{
    private String name;
    private int attribute;
    private boolean withattribute;
    
    public MyOwnVertex()
    {}
    
    public MyOwnVertex(String name, int attribute) 
    {
        this.name = name;
        this.attribute = attribute;
        this.withattribute = true;
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
}