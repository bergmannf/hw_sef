package uk.heriotwatt.sef;

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
		
		cabMan.addCabin(cab1);
		cabMan.printAllCabins();
		
		cabMan.printDetailsForCabinNumber(1);
		
	}

}
