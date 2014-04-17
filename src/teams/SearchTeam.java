package teams;

import gui.DistanceConverter;

import java.awt.Graphics;
import java.awt.Image;
import org.joda.time.DateTime;

public class SearchTeam {
	private String teamName;
	private double speed;
	private double longitude;
	private double latitude;
	private int heading;
	private DateTime timeCreated;
	private Image icon;
	private TeamType type;
	private DistanceConverter converter;
	
	public enum TeamType{HIKERS, DOGS, HELICOPTER};
	
	public SearchTeam(String teamName, double latitude, double longitude, DateTime timeCreated, Image icon, TeamType type, DistanceConverter converter){
		this.teamName = teamName;
		this.latitude = latitude;
		this.longitude = longitude;
		this.timeCreated = new DateTime(timeCreated);
		this.icon = icon;
		this.type = type;
		this.converter = converter;
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
	
	public void draw(Graphics g) {
		// TODO Add offset for icons? :)
		g.drawImage(icon, converter.longDistanceToXPixel(longitude), converter.latDistanceToYPixel(latitude), null);
		
	}
	
}
