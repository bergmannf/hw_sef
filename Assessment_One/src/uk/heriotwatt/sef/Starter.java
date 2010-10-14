package uk.heriotwatt.sef;

import java.util.Date;

import uk.heriotwatt.sef.model.Cabin;
import uk.heriotwatt.sef.model.CabinManager;
import uk.heriotwatt.sef.model.Condition;
import uk.heriotwatt.sef.model.Facilities;
import uk.heriotwatt.sef.model.Name;

public class Starter {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		CabinManager cabMan = new CabinManager();
		
		Cabin cab1 = new Cabin();
		cab1.setOwner(new Name("Jim", "Hap", "Azard"));
		cab1.setCabinNumber(1);
		cab1.setNumberOfBeds(new int[] {2,2});
		cab1.setFacilities(Facilities.EN_SUITE);
		cab1.setCondition(Condition.IN_SHAMBLES);
		cab1.setSize(35.0);
		
		Cabin cab2 = new Cabin(2, new int[] {2,2,2}, 50.0, Facilities.SEPERATE_BATHROOM, new Name("John", "Sly","Rambo"), new Date(), Condition.PERFECT);
		
		cabMan.addCabin(cab1);
		cabMan.addCabin(cab2);
		cabMan.printAllCabins();
		
		cabMan.printDetailsForCabinNumber(1);
		cabMan.printDetailsForCabinNumber(2);
		
		cabMan.printReportsToFile();
		
	}

}
