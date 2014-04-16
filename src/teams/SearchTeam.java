package teams;

import java.awt.Graphics;

abstract public class SearchTeam {
	private String teamName;
	private double speed;
	private double longitude;
	private double latitude;
	private int heading;
	
	public SearchTeam(String teamName, int latitude, int longitude){
		this.teamName = teamName;
		this.latitude = latitude;
		this.longitude = longitude;
	}
	
	public void setLocation(double latitude, double longitude) {
		this.latitude = latitude;
		this.longitude = longitude;
	}
	
	public void updateLocation() {
		
	}
	
	public void inputHeading(int heading){
//		this.heading = heading;
	}
	
	public abstract void draw(Graphics g);
	
}
