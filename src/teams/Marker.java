package teams;

import gui.DistanceConverter;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;

import org.joda.time.DateTime;

public class Marker {
	private String markerName;
	private double longitude;
	private double latitude;
	private DateTime timeCreated;
	private Image icon;
	
	private DistanceConverter converter;
	

	public Marker(String markerName, double longitude, double latitude,
			DateTime timeCreated, Image icon, DistanceConverter converter) {
		this.markerName = markerName;
		this.longitude = longitude;
		this.latitude = latitude;
		this.timeCreated = timeCreated;
		this.icon = icon;
		this.converter = converter;
	}
	
	public void draw(Graphics g) {
		// Draw Icon
		g.drawImage(icon, converter.longDistanceToXPixel(longitude) - 16, converter.latDistanceToYPixel(latitude) - 35, null);
	}
	
	public String getMarkerName() {
		return markerName;
	}

	public double getLongitude() {
		return longitude;
	}

	public double getLatitude() {
		return latitude;
	}

	public DateTime getTimeCreated() {
		return timeCreated;
	}
	
	public void setLocation(double latitude, double longitude) {
		this.latitude = latitude;
		this.longitude = longitude;
	}

	

}
