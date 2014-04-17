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

	public DistanceConverter() {
		// TODO Auto-generated constructor stub
	}
	
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

	public void findConstants(){
		LAT_TO_YPIXELS = yWindowSize/(topLeftLat-botRightLat);
		LONG_TO_XPIXELS = xWindowSize/(topLeftLong-botRightLong);
		
		MILES_TO_LAT = 69.047;
		MILES_TO_LONG = 69.047*Math.cos(topLeftLat);
	}
	
	public int latDistanceToYPixel(double latitude){
		return (int) ((topLeftLat - latitude) * LAT_TO_YPIXELS); 		
	}
	
	public int longDistanceToXPixel(double longitude){
		return (int) ((topLeftLong - longitude) * LONG_TO_XPIXELS); 		
	}
	
	public int longChange(double speedMPH, int heading, int refreshRateInMillis){
		int angleDeg = 90 - heading;
		double angleRad = (angleDeg * Math.PI) / 180.0;
		double xSpeedMPH = speedMPH * Math.cos(angleRad);
		double xSpeedMPMilli = xSpeedMPH/3600000.0;
		double miles = xSpeedMPMilli * refreshRateInMillis;
		int longChange = (int) (miles * MILES_TO_LONG);
		return longChange;
	}	
	
	public int latChange(double speedMPH, int heading, int refreshRateInMillis){
		int angleDeg = 90 - heading;
		double angleRad = (angleDeg * Math.PI) / 180.0;
		double ySpeedMPH = speedMPH * Math.sin(angleRad);
		double ySpeedMPMilli = ySpeedMPH/3600000.0;
		double miles = ySpeedMPMilli * refreshRateInMillis;
		int latChange = (int) (miles * MILES_TO_LAT);
		return latChange;
	}	

}
