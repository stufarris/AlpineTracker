package search.teams;

abstract public class SearchTeam {
	private String teamName;
	private int speed;
	private int xLocation;
	private int yLocation;
	private int heading;
	
	public SearchTeam(String teamName, int xLocation, int yLocation){
//		super();
//		this.teamName = teamName;
//		this.xLocation = xLocation;
//		this.yLocation = yLocation;
	}
	
	abstract public void updateLocation();
	
	public void inputLocation(int xLocation, int yLocation){
//		this.xLocation = xLocation;
//		this.yLocation = yLocation;
	}
	
	public void inputHeading(int heading){
//		this.heading = heading;
	}
	
}
