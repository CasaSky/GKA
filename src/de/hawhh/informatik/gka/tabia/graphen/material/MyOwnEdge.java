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
		return source()+" <-> "+target();
        //return ""+gewicht;
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

	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + ((_source == null) ? 0 : _source.hashCode());
		result = prime * result + ((_target == null) ? 0 : _target.hashCode());
		result = prime * result + gewicht;
		return result;
	}

	@Override
	public boolean equals(Object obj)
	{
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		MyOwnEdge other = (MyOwnEdge) obj;
		if (_source == null)
		{
			if (other._source != null)
				return false;
		} else if (!_source.equals(other._source))
			return false;
		if (_target == null)
		{
			if (other._target != null)
				return false;
		} else if (!_target.equals(other._target))
			return false;
		if (gewicht != other.gewicht)
			return false;
		return true;
	}
}
