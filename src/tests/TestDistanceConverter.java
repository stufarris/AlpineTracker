package tests;

import static org.junit.Assert.*;
import junit.framework.Assert;
import gui.DistanceConverter;

import org.junit.Before;
import org.junit.Test;

public class TestDistanceConverter {

	private DistanceConverter distanceConverter;
//	
//	@Before
//	public void setUp() {
//		distanceConverter = new DistanceConverter();
//	}
	
	@Test
	public void testConvertDMStoDecimal(){
		double expected = 36.0;
		double actual = DistanceConverter.convertDMStoDecimal(36, 0, 0);
		Assert.assertEquals(expected, actual);
		
		expected = 36.266666666666666;
		actual = DistanceConverter.convertDMStoDecimal(36, 15, 60);
		Assert.assertEquals(expected, actual);
		
		expected = 78.0111111111111;
		actual = DistanceConverter.convertDMStoDecimal(77, 59, 100);
		Assert.assertEquals(expected, actual);
	}
	
	@Test
	public void testLatDistanceToPixel(){
		//all of these are the actual values from the loveland quadrangle we used
		double topLeftLat = DistanceConverter.convertDMStoDecimal(39, 45, 0);
		double topLeftLong = DistanceConverter.convertDMStoDecimal(106, 0, 0);
		double botRightLat = DistanceConverter.convertDMStoDecimal(39, 37, 30);
		double botRightLong = DistanceConverter.convertDMStoDecimal(105, 52, 30);
		int xWindowSize = 1600;
		int yWindowSize = 2071;
		distanceConverter = new DistanceConverter(topLeftLat, topLeftLong, botRightLat, botRightLong, xWindowSize, yWindowSize);
		
		int expected = 690; 
		int actual = distanceConverter.latDistanceToYPixel(DistanceConverter.convertDMStoDecimal(39, 42, 30));
		Assert.assertTrue(Math.abs(expected - actual) < 10 );
		
		expected = 533; 
		actual = distanceConverter.longDistanceToXPixel(DistanceConverter.convertDMStoDecimal(105, 57, 30));
		Assert.assertTrue(Math.abs(expected - actual) < 10 );
	}
}
