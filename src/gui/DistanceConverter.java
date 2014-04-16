package gui;


public class DistanceConverter {
	
	private double MILES_TO_PIXELS;
	private double PIXELS_TO_MILES;
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
		
		FindConstants();
	}
	public static double convertDMStoDecimal(int degrees, int minutes, int seconds){
		return degrees + minutes/60.0 + seconds/3600.0;
	}

	public void FindConstants(){
		MILES_TO_PIXELS = yWindowSize/(topLeftLong-botRightLong);
		PIXELS_TO_MILES = 1/MILES_TO_PIXELS;
	}
	
	public void DegMinSecToDec(int degrees, int minutes, int seconds){
		
	}
	
	public int LatToYPixel(double latitude){
		return 0; 		
	}
	
	public int LongToXPixel(double longitude){
		return 0;		
	}
	
	

}
