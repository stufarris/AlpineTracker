package teams;

import gui.DistanceConverter;
import gui.SearchedLayer;

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
	private SearchedLayer searchedLayer;
	
	public enum TeamType{HIKERS, DOGS, HELICOPTER};
	
	public SearchTeam(String teamName, double latitude, double longitude, DateTime timeCreated, Image icon, TeamType type, DistanceConverter converter
			, SearchedLayer searchedLayer){
		this.teamName = teamName;
		this.latitude = latitude;
		this.longitude = longitude;
		this.timeCreated = new DateTime(timeCreated);
		this.icon = icon;
		this.type = type;
		this.converter = converter;
		this.searchedLayer = searchedLayer;
		this.searchedLayer.addSearchedPoint(converter.longDistanceToXPixel(longitude), converter.latDistanceToYPixel(latitude));
	}
	
	public void setLocation(double latitude, double longitude) {
		this.latitude = latitude;
		this.longitude = longitude;
		searchedLayer.addSearchedPoint(converter.longDistanceToXPixel(longitude), converter.latDistanceToYPixel(latitude));
	}
	
	public void updateLocation() {
		this.latitude += converter.latChange(speed, heading, 1000);
		this.longitude += converter.longChange(speed, heading, 1000);
		searchedLayer.addSearchedPoint(converter.longDistanceToXPixel(longitude), converter.latDistanceToYPixel(latitude));
	}
	
	public void setSpeed(double speed) {
		this.speed = speed;
	}

	public void setHeading(int heading) {
		this.heading = heading;
	}

	public void inputHeading(int heading){
//		this.heading = heading;
	}
	
	public void draw(Graphics g) {
		// TODO Add offset for icons? :)
		g.drawImage(icon, converter.longDistanceToXPixel(longitude) - 16, converter.latDistanceToYPixel(latitude) - 35, null);
		
	}
	
}
