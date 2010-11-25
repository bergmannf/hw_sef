package uk.heriotwatt.sef.model;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

import org.apache.log4j.Logger;

import uk.heriotwatt.sef.model.interfaces.FileHandler;
import uk.heriotwatt.sef.model.interfaces.ICsvSerialisable;

public class GenericFileHandler<T extends ICsvSerialisable<T>> implements FileHandler<T> {

	private final Logger logger = Logger.getLogger(getClass());
	
	private String filePath;
	
	public GenericFileHandler(String filePath)
	{
		this.filePath = filePath;
	}
	
	@Override
	public void writeToFile(List<T> list) {
		try {
			File file = new File(this.filePath);
			Writer w = new FileWriter(file);
			BufferedWriter bw = new BufferedWriter(w);
			for (T t : list) {
				bw.write(t.csvRepresentation());
			}
		} catch (FileNotFoundException e) {
			logger.error("File not found exception: " + e.toString());
		} catch (IOException e) {
			logger.error("IO-exception: " + e.toString());
		}
	}

	@Override
	public List<T> readFromFile(T creationT) {
		List<T> tList = new LinkedList<T>();
		try {
			File file = new File(this.filePath);
			Scanner scanner = new Scanner(file);
			while (scanner.hasNext()) {
				String nextLine = scanner.nextLine();
				if (nextLine.trim().startsWith("#")) {
					System.out.println("Ignoring a commented out line.");
				} else {
					try {
						T t = creationT.createFromString(nextLine);
						tList.add(t);
					} catch (IllegalArgumentException e) {
						logger.error("IllegalArgumentException when creating object:" + e.toString());
					}
				}
			}
		} catch (FileNotFoundException e) {
			logger.error("File not found exception: " + e.toString());
		}
		return tList;
	}

}
