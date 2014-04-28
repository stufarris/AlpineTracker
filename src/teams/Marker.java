package teams;

import gui.DistanceConverter;

import java.awt.Image;

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

}
