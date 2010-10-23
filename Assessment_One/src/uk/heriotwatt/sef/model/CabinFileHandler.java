package uk.heriotwatt.sef.model;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

public class CabinFileHandler {
	
	private String pathToReadFile;
	private String pathToReportFile;
	
	public CabinFileHandler(String pathReadFile, String pathWriteFile)
	{
		this.pathToReadFile = pathReadFile;
		this.pathToReportFile = pathWriteFile;
	}
	
	public void writeToFile(String sb)
	{
		try {
			File file = new File(pathToReportFile);
			PrintWriter pw = new PrintWriter(file);
			pw.write(sb);
			pw.flush();
			pw.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public List<Cabin> readFromFile()
	{
		// TODO Ignore the comments in a file. (Denoted by #)
		List<Cabin> cabinList = new LinkedList<Cabin>();
		try {
			File file = new File(this.pathToReadFile);
			Scanner scanner = new Scanner(file);
			while (scanner.hasNext()) {
				String nextLine = scanner.nextLine();
				if (nextLine.trim().startsWith("#")) {
					 System.out.println("Ignoring a commented out line.");
				}
				else {
					Cabin cabin = this.createCabin(nextLine);
					cabinList.add(cabin);
				}
			}
		} catch (FileNotFoundException e)
		{
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return cabinList;
	}

	public Cabin createCabin(String nextLine) {
		Cabin cabin = null;
		try {
			String[] splitList = nextLine.split(",");
			int cabinNumber = Integer.parseInt(splitList[0]);
			double size = Double.parseDouble(splitList[1]);
			Facilities facilities = Facilities.valueOf(splitList[2]);
			Condition condition = Condition.valueOf(splitList[3]);
			Name name = new Name(splitList[4], splitList[5], splitList[6]);
			int[] beds = new int[splitList.length - 7];
			for (int i = 7; i < splitList.length; i++) {
				beds[i-7] = Integer.parseInt(splitList[i]);
			}
			cabin = new Cabin(cabinNumber, beds, size, facilities, name, condition);
		} catch (NumberFormatException e) {
			System.out.println("There was an error when parsing a number!");
			e.printStackTrace();
		}
		catch (IllegalArgumentException e)
		{
			System.out.println("A provided argument was not the expected type.");
			e.printStackTrace();
		}
		return cabin;
	}

}
