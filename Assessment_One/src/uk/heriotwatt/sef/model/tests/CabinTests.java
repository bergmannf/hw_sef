package uk.heriotwatt.sef.model.tests;


import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import uk.heriotwatt.sef.model.Cabin;
import uk.heriotwatt.sef.model.Condition;
import uk.heriotwatt.sef.model.Facilities;
import uk.heriotwatt.sef.model.Name;
import uk.heriotwatt.sef.model.PriceMapping;

public class CabinTests {
	
	private Cabin cabin;

	@Before
	public void setUp() throws Exception {
		cabin = new Cabin();
	}
	
	@Test
	public void testSetNumberOfBeds()
	{
		int[] numberToSet = new int[] {2};
		cabin.setNumberOfBeds(numberToSet);
		int[] numberSet = cabin.getNumberOfBeds();
		Assert.assertArrayEquals(numberToSet, numberSet);
		
		numberToSet = new int[] {2, 2, 4};
		cabin.setNumberOfBeds(numberToSet);
		numberSet = cabin.getNumberOfBeds();
		Assert.assertArrayEquals(numberToSet, numberToSet);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testSetNumberOfBedsWithTooFewBeds()
	{
		int[] numberToSet = new int[] {1};
		cabin.setNumberOfBeds(numberToSet);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testSetNumberOfBedsWithTooManyBeds()
	{
		int[] numberToSet = new int[] {9};
		cabin.setNumberOfBeds(numberToSet);
	}
	
	@Test
	public void testCostForFacilites()
	{
		PriceMapping pm = new PriceMapping();
		
		Facilities fac = Facilities.EN_SUITE;
		Condition con = Condition.FAIR;
		int[] beds = new int[] {2,2};
		double size = 49.99;
		
		double basePrice = 10;
		double conPrice = pm.getConditionPrice(con);
		double facPrice = pm.getFacilityPrice(fac);
		double sizePrice = pm.getSizeModifier(size);
		
		double expectedPrice = basePrice + conPrice + facPrice + sizePrice + 5 * (2/4);
		
		Cabin cab = new Cabin(1, beds,size, fac, new Name("Ho", "ho", "ho"), con);
		
		double price = cab.getCost();
		
		Assert.assertEquals(expectedPrice, price, 0);
	}

}
