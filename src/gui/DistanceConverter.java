package gui;


public class DistanceConverter {
	
	private double LAT_TO_YPIXELS;
	private double LONG_TO_XPIXELS;
	private double topLeftLat;
	private double topLeftLong;
	private double botRightLat;
	private double botRightLong;
	private int xWindowSize;
	private int yWindowSize;
	
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
	}
	
	public int latDistanceToYPixel(double latitude){
		return (int) ((topLeftLat - latitude) * LAT_TO_YPIXELS); 		
	}
	
	public int longDistanceToXPixel(double longitude){
		return (int) ((topLeftLong - longitude) * LONG_TO_XPIXELS); 		
	}

	
	

}
