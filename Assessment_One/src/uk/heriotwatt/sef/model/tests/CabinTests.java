package uk.heriotwatt.sef.model.tests;


import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import uk.heriotwatt.sef.model.Cabin;
import uk.heriotwatt.sef.model.Facilities;
import uk.heriotwatt.sef.model.PriceList;

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
		cabin.setFacilities(Facilities.EN_SUITE);
		double result = cabin.getCost();
		double expResult = 10.0 + PriceList.VERY_EXPENSIVE.cost();
		Assert.assertEquals(expResult, result, 0.0);
	}

}