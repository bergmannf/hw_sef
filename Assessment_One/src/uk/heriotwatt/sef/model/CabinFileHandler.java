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

	/**
	 * Creates a new cabinfilehandler object from the provided arguments.
	 * @param pathReadFile The path of the file of cabins.
	 * @param pathWriteFile The path of the file to write the reports to.
	 */
	public CabinFileHandler(String pathReadFile, String pathWriteFile) {
		this.pathToReadFile = pathReadFile;
		this.pathToReportFile = pathWriteFile;
	}

	/**
	 * Attempts to write a provided string to a file.
	 * @param sb The string to be written to a file.
	 */
	public void writeToFile(String sb) {
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

	/**
	 * Attempts to read a list of cabins from a file.
	 * @return A new list of cabin objects.
	 */
	public List<Cabin> readFromFile() {
		List<Cabin> cabinList = new LinkedList<Cabin>();
		try {
			File file = new File(this.pathToReadFile);
			Scanner scanner = new Scanner(file);
			while (scanner.hasNext()) {
				String nextLine = scanner.nextLine();
				if (nextLine.trim().startsWith("#")) {
					// Ignoring commented out lines.
					System.out.println("Ignoring a commented out line.");
				} else {
					try {
						Cabin cabin = this.createCabin(nextLine);
						cabinList.add(cabin);
					} catch (IllegalArgumentException e) {
						e.printStackTrace();
					}
				}
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return cabinList;
	}

	/**
	 * Attempts to parse a cabin from a provided string that must comply to the
	 * following format:
	 * CabinNumber,Size,Facilities,Condition,Forename,Middlename,Surname,BedsRoom1,BedsRoom2,...,[BedsRoomN]
	 * @param nextLine
	 *            The string containing all arguments.
	 * @return A cabin object created from the provided arguments.
	 * @throws IllegalArgumentException
	 *             If the provided string does not comply to the needed format.
	 */
	public Cabin createCabin(String nextLine) {
		Cabin cabin = null;
		String[] splitList = nextLine.split(",");
		String errorString = "";

		int cabinNumber = 0;
		double size = 0;
		Facilities facilities = null;
		Condition condition = null;
		Name name;
		int[] beds;
		try {
			cabinNumber = Integer.parseInt(splitList[0]);
		} catch (NumberFormatException e) {
			errorString += splitList[0];
		}
		try {
			size = Double.parseDouble(splitList[1]);
		} catch (NumberFormatException e) {
			errorString += ", " + splitList[1];
		}
		try {
			facilities = Facilities.valueOf(splitList[2]);
		} catch (IllegalArgumentException e) {
			errorString += ", " + splitList[2];
		}
		try {
			condition = Condition.valueOf(splitList[3]);
		} catch (IllegalArgumentException e) {
			errorString += ", " + splitList[3];
		}
		name = new Name(splitList[4], splitList[5], splitList[6]);
		beds = new int[splitList.length - 7];
		for (int i = 7; i < splitList.length; i++) {
			try {
				beds[i - 7] = Integer.parseInt(splitList[i]);
			} catch (Exception e) {
				errorString += ", " + beds[i - 7];
			}
		}
		if (errorString.length() == 0) {
			cabin = new Cabin(cabinNumber, beds, size, facilities, name,
					condition);
			return cabin;
		} else {
			throw new IllegalArgumentException(
					String.format(
							"There were errors parsing the line:\n%s\nThe following arguments were violating the format:%s",
							nextLine, errorString));
		}
	}

}
