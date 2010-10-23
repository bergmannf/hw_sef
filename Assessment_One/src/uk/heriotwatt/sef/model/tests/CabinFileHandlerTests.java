package uk.heriotwatt.sef.model.tests;

import junit.framework.Assert;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import uk.heriotwatt.sef.model.Cabin;
import uk.heriotwatt.sef.model.CabinFileHandler;

public class CabinFileHandlerTests {
	
	private CabinFileHandler fileHandler;

	@Before
	public void setUp() throws Exception {
		this.fileHandler = new CabinFileHandler("", "");
	}

	@After
	public void tearDown() throws Exception {
	}
	
	@Test
	public void testCreateCabin()
	{
		String toParse = "1,10,EN_SUITE,IN_SHAMBLES,John,Jack,MasterMind,2,2,2,2";
		Cabin cabin = fileHandler.createCabin(toParse);
		Assert.assertNotNull(cabin);
	}

	@Test
	public void testCreateCabinFaultyValues()
	{
		String toParse = "1,10,EN_SITE,IN_SHAMBLES,John,Jack,MasterMind,2,2,2,2";
		Cabin cabin = fileHandler.createCabin(toParse);
	}
}
