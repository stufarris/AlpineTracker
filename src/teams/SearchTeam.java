package teams;

import gui.DistanceConverter;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.geom.GeneralPath;
import java.util.ArrayList;

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
	private ArrayList<Point> searchedPoints;

	private float pathWidth = 25;

	public enum TeamType{HIKERS, DOGS, HELICOPTER};

	public SearchTeam(String teamName, double latitude, double longitude, DateTime timeCreated, Image icon, TeamType type, DistanceConverter converter){
		this.teamName = teamName;
		this.latitude = latitude;
		this.longitude = longitude;
		this.timeCreated = new DateTime(timeCreated);
		this.icon = icon;
		this.type = type;
		this.converter = converter;
		searchedPoints = new ArrayList<Point>();
		searchedPoints.add(new Point(converter.longDistanceToXPixel(longitude), converter.latDistanceToYPixel(latitude)));
	}

	public void setLocation(double latitude, double longitude) {
		this.latitude = latitude;
		this.longitude = longitude;
		searchedPoints.add(new Point(converter.longDistanceToXPixel(longitude), converter.latDistanceToYPixel(latitude)));
	}

	public void updateLocation() {
		this.latitude += converter.latChange(speed, heading, 1000);
		this.longitude += converter.longChange(speed, heading, 1000);
		searchedPoints.add(new Point(converter.longDistanceToXPixel(longitude), converter.latDistanceToYPixel(latitude)));
	}

	public void setSpeed(double speed) {
		this.speed = speed;
	}

	public void setHeading(int heading) {
		this.heading = heading;
	}

	public String getTeamName() {
		return teamName;
	}

	public double getSpeed() {
		return speed;
	}

	public double getLongitude() {
		return longitude;
	}

	public double getLatitude() {
		return latitude;
	}

	public int getHeading() {
		return heading;
	}

	public DateTime getTimeCreated() {
		return timeCreated;
	}

	public TeamType getType() {
		return type;
	}

	public void inputHeading(int heading){
		//		this.heading = heading;
	}

	public void draw(Graphics g) {
		
		drawTrail(g);

		// Draw Icon
		g.drawImage(icon, converter.longDistanceToXPixel(longitude) - 16, converter.latDistanceToYPixel(latitude) - 35, null);

	}

	private GeneralPath makeTrail() {
		GeneralPath trail = new GeneralPath(GeneralPath.WIND_EVEN_ODD, this.searchedPoints.size());

		trail.moveTo(this.searchedPoints.get(0).x, this.searchedPoints.get(0).y);

		for (int i = 1; i < this.searchedPoints.size(); i++) {
			trail.lineTo(this.searchedPoints.get(i).x, this.searchedPoints.get(i).y);
		};

		return trail;
	}

	private void drawTrail(Graphics g) {
		
		Graphics2D g2 = (Graphics2D)g;
		
		g2.setRenderingHint(
			    RenderingHints.KEY_ANTIALIASING,
			    RenderingHints.VALUE_ANTIALIAS_ON);
		
		// Draw Path
		BasicStroke line = new BasicStroke(pathWidth, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND);

		g2.setStroke(line);
		Color transRed = new Color(255, 15, 15, 90);
		g2.setColor(transRed);
		g2.draw(makeTrail());
	}

}
