package uk.heriotwatt.sef.model;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

public class CabinFileHandler {
	
	private String pathToFile;
	
	public CabinFileHandler(String path)
	{
		this.pathToFile = path;
	}
	
	public void writeToFile()
	{
		
	}
	
	public List<Cabin> readFromFile()
	{
		List<Cabin> cabinList = new LinkedList<Cabin>();
		try {
			File file = new File(this.pathToFile);
			Scanner scanner = new Scanner(file);
			while (scanner.hasNext()) {
				String nextLine = scanner.nextLine();
				Cabin cabin = this.createCabin(nextLine);
				cabinList.add(cabin);
			}
			return cabinList;
		} catch (FileNotFoundException e)
		{
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private Cabin createCabin(String nextLine) {
		Cabin cabin;
		try {
			String[] splitList = nextLine.split(",");
			int cabinNumber = Integer.parseInt(splitList[0]);
			double size = Double.parseDouble(splitList[1]);
			Facilities facilities = Facilities.valueOf(splitList[2]);
			Condition condition = Condition.valueOf(splitList[3]);
			Name name = new Name(splitList[4], splitList[5], splitList[6]);
			Date buildDate = new Date(splitList[7]);
			int[] beds = new int[splitList.length - 8];
			for (int i = 8; i < splitList.length; i++) {
				beds[i-8] = Integer.parseInt(splitList[i]);
			}
			Cabin cabin = new Cabin(cabinNumber, beds, size, facilities, name, buildDate, condition);
			return cabin;
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
