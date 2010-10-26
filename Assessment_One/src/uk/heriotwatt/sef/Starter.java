package uk.heriotwatt.sef;

import uk.heriotwatt.sef.model.Cabin;
import uk.heriotwatt.sef.model.CabinFileHandler;
import uk.heriotwatt.sef.model.CabinManager;
import uk.heriotwatt.sef.model.Condition;
import uk.heriotwatt.sef.model.Facilities;
import uk.heriotwatt.sef.model.Name;

public class Starter {

	/**
	 * @param args No arguments expected.
	 */
	public static void main(String[] args) {
		CabinFileHandler cfh = new CabinFileHandler("./Cabins.csv", "./CabinReports.txt");
		CabinManager cabMan = new CabinManager(cfh);

		cabMan.printReportsToFile();

	}

}
