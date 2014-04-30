package gui;


public class DistanceConverter {
	
	private double LAT_TO_YPIXELS;
	private double LONG_TO_XPIXELS;
	private double MILES_TO_LAT;
	private double MILES_TO_LONG;
//	private double LAT_TO_MILES;
//	private double LONG_TO_MILES;
	private double topLeftLat;
	private double topLeftLong;
	private double botRightLat;
	private double botRightLong;
	private int xWindowSize;
	private int yWindowSize;
	
	//called when map is loaded and sets the latitude and longitude boundaries for the map loaded
	public DistanceConverter(double topLeftLat, double topLeftLong, double botRightLat, double botRightLong, int xWindowSize, int yWindowSize) {
		this.topLeftLat = topLeftLat;
		this.topLeftLong = topLeftLong;
		this.botRightLat = botRightLat;
		this.botRightLong = botRightLong;
		this.xWindowSize = xWindowSize;
		this.yWindowSize = yWindowSize;
		
		findConstants();
	}
	public static double convertDMStoDecimal(int degrees, int minutes, int seconds){
		return degrees + minutes/60.0 + seconds/3600.0;
	}
	
	//constants used for finding the ratios from pixels to latitude, pixels to longitude, miles to latitude,
	//miles to longitude, etc...
	public void findConstants(){
		LAT_TO_YPIXELS = yWindowSize/(topLeftLat-botRightLat);
		LONG_TO_XPIXELS = xWindowSize/(topLeftLong-botRightLong);
		
		MILES_TO_LAT = 1/69.047;
		double topLeftLatRadian = (topLeftLat * Math.PI) / 180.0;
		MILES_TO_LONG = 1/(69.047*Math.cos(topLeftLatRadian));
	}
	
	//calculates a "vertical" distance in pixels given the change in latitude as a parameter
	public int latDistanceToYPixel(double latitude){
		return (int) ((topLeftLat - latitude) * LAT_TO_YPIXELS); 		
	}
	
	//calculates a "horizontal" distance in pixels given the change in longitude as a parameter
	public int longDistanceToXPixel(double longitude){
		return (int) ((topLeftLong - longitude) * LONG_TO_XPIXELS); 		
	}
	
	//calculates the change in longitude for a team based on their speed, heading, and refresh rate.
	public double longChange(double speedMPH, int heading, int refreshRateInMillis){
		double angleDeg = 90.0 - (double) heading;
		double angleRad = (angleDeg * Math.PI) / 180.0;
		double xSpeedMPH = speedMPH * Math.cos(angleRad);
		double xSpeedMPMilli = xSpeedMPH/3600000.0;
		double miles = xSpeedMPMilli * refreshRateInMillis;
		double longChange = (miles * MILES_TO_LONG);
		return -1 * longChange; // -1 since in western hemisphere
	}	
	
	//calculates the change in latitude for a team based on their speed, heading, and refresh rate.
	public double latChange(double speedMPH, int heading, int refreshRateInMillis){
		double angleDeg = 90.0 - (double) heading;
		double angleRad = (angleDeg * Math.PI) / 180.0;
		double ySpeedMPH = speedMPH * Math.sin(angleRad);
		double ySpeedMPMilli = ySpeedMPH/3600000.0;
		double miles = ySpeedMPMilli * refreshRateInMillis;
		double latChange = (miles * MILES_TO_LAT);
		return latChange;
	}	
	
	
	public static int[] getDMSfromDecimal(double value) {
		int[] result = new int[3];
		
		result[0] = (int)value;
		value -= result[0];
		value *=60;
		result[1] = (int)value;
		value -= result[1];
		value *= 60;
		result[2] = (int)value;
		
		return result;
	}

}
