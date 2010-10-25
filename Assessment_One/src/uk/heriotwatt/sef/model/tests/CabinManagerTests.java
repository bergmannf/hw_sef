/**
 * 
 */
package uk.heriotwatt.sef.model.tests;

import static org.junit.Assert.*;

import java.util.LinkedList;

import junit.framework.Assert;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.mockito.Mockito.*;

import uk.heriotwatt.sef.model.Cabin;
import uk.heriotwatt.sef.model.CabinFileHandler;
import uk.heriotwatt.sef.model.CabinManager;
import uk.heriotwatt.sef.model.CabinNotFoundException;
import uk.heriotwatt.sef.model.Condition;
import uk.heriotwatt.sef.model.Facilities;
import uk.heriotwatt.sef.model.Name;
import uk.heriotwatt.sef.model.NoCabinsException;

/**
 * @author Florian Bergmann
 *
 */
public class CabinManagerTests {

	private CabinManager cabMan;
	private Cabin mockin;
	private Cabin mockin2;
	
	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		CabinFileHandler mockHandler = mock(CabinFileHandler.class);
		when(mockHandler.readFromFile()).thenReturn(new LinkedList());
		cabMan = new CabinManager(mockHandler);
		mockin = mock(Cabin.class);
		mockin2 = mock(Cabin.class);
	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
	}

	/**
	 * Test method for {@link uk.heriotwatt.sef.model.CabinManager#addCabin(uk.heriotwatt.sef.model.Cabin)}.
	 */
	@Test
	public void testAddCabin() {
		Cabin cab = new Cabin(1, new int[] {2,3}, 55.0, Facilities.EN_SUITE, new Name("Test", "Test", "Test"), Condition.GOOD);
		cabMan.addCabin(cab);
		Assert.assertEquals(1, cabMan.getNumberOfCabins());
	}

	/**
	 * Test method for {@link uk.heriotwatt.sef.model.CabinManager#getCabinAtIndex(int)}.
	 */
	@Test
	public void testGetCabinAtIndex() {
		Cabin cab = new Cabin(1, new int[] {2,3}, 55.0, Facilities.EN_SUITE, new Name("Test", "Test", "Test"), Condition.GOOD);
		cabMan.addCabin(cab);
		Cabin cabIndex = cabMan.getCabinAtIndex(0);
		Assert.assertEquals(cabIndex, cab);
	}
	
	@Test(expected=IndexOutOfBoundsException.class)
	public void testGetCabinAtIndexWrongIndex() {
		cabMan.getCabinAtIndex(1);
	}

	/**
	 * Test method for {@link uk.heriotwatt.sef.model.CabinManager#findCabinByCabinNumber(int)}.
	 */
	@Test
	public void testFindCabinByCabinNumber() {
		Cabin cab = new Cabin(1, new int[] {2,3}, 55.0, Facilities.EN_SUITE, new Name("Test", "Test", "Test"), Condition.GOOD);
		cabMan.addCabin(cab);
		Cabin cabFound = null;
		try {
			cabFound = cabMan.findCabinByCabinNumber(1);
		} catch (CabinNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Assert.assertEquals(cab, cabFound);
	}
	
	@Test(expected=CabinNotFoundException.class)
	public void testFindCabinByCabinNumberWrongNumber() throws CabinNotFoundException {
		cabMan.findCabinByCabinNumber(1);
	}

	/**
	 * Test method for {@link uk.heriotwatt.sef.model.CabinManager#getMaximumPossibleIncome()}.
	 */
	@Test
	public void testGetMaximumPossibleIncome() {
		when(mockin.getCost()).thenReturn(50.0);
		when(mockin2.getCost()).thenReturn(40.0);
		cabMan.addCabin(mockin);
		cabMan.addCabin(mockin2);
		double getMaxIncome = cabMan.getMaximumPossibleIncome();
		Assert.assertEquals(90, getMaxIncome, 0);
	}

	/**
	 * Test method for {@link uk.heriotwatt.sef.model.CabinManager#getCheapestCabinCost()}.
	 */
	@Test
	public void testGetCheapestCabinCost() {
		when(mockin.getCost()).thenReturn(50.0);
		when(mockin2.getCost()).thenReturn(40.0);
		cabMan.addCabin(mockin);
		cabMan.addCabin(mockin2);
		double cheapest = 0;
		try {
			cheapest = cabMan.getCheapestCabinCost();
		} catch (NoCabinsException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Assert.assertEquals(40, cheapest, 0);
	}

	@Test(expected=NoCabinsException.class)
	public void testGetCheapestCabinCostNoCabins() throws NoCabinsException {
		cabMan.getCheapestCabinCost();
	}
	
	/**
	 * Test method for {@link uk.heriotwatt.sef.model.CabinManager#getExpensiveCabinCost()}.
	 */
	@Test
	public void testGetExpensiveCabinCost() {
		when(mockin.getCost()).thenReturn(50.0);
		when(mockin2.getCost()).thenReturn(40.0);
		cabMan.addCabin(mockin);
		cabMan.addCabin(mockin2);
		double mostExpensive = 0;
		try {
			mostExpensive = cabMan.getExpensiveCabinCost();
		} catch (NoCabinsException e) {
			// TODO: handle exception
		}
		Assert.assertEquals(50, mostExpensive, 0);
	}
	
	@Test(expected=NoCabinsException.class)
	public void testGetExpensiveCabinCostNoCabins() throws NoCabinsException {
		cabMan.getExpensiveCabinCost();
	}
}
