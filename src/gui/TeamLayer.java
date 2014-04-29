package gui;

import java.awt.Graphics;
import java.util.ArrayList;

import org.joda.time.DateTime;

import teams.Marker;
import teams.SearchTeam;

public class TeamLayer extends Layer {

	private static final long serialVersionUID = -3091212122097231088L;
	private ArrayList<SearchTeam> teams;
	private ArrayList<Marker> markers;
	
	public TeamLayer(int imageWidth, int imageHeight) {
		super(imageWidth, imageHeight);
		teams = new ArrayList<SearchTeam>();
		markers = new ArrayList<Marker>();
	}
	
	public void addTeam(SearchTeam s) {
		teams.add(s);
	}
	
	public void removeTeam(SearchTeam s) {
		teams.remove(s);
	}
	
	public void updateTeams() {
		for(SearchTeam s : teams) {
			s.updateLocation();
		}
	}
	
	public void paintComponent(Graphics g) {
		// draw teams
		for(SearchTeam t : teams) {
			t.draw(g);
		}
		//draw markers
		for(Marker m : markers){
			m.draw(g);
		}
	}

	public ArrayList<SearchTeam> getTeams() {
		return teams;
	}

	public ArrayList<Marker> getMarkers() {
		return markers;
	}

}
