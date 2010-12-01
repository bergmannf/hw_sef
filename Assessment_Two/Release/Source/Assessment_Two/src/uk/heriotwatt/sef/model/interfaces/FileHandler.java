package uk.heriotwatt.sef.model.interfaces;

import java.util.List;

public interface FileHandler<T> {
	
	public void writeToFile(List<T> list);
	
	public List<T> readFromFile(T creationT);

}
