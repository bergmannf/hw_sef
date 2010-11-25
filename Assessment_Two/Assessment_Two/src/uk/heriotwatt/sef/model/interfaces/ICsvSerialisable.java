package uk.heriotwatt.sef.model.interfaces;

public interface ICsvSerialisable<T> {

	public String csvRepresentation();
	
	public T createFromString(String string);
}
