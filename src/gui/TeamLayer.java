package gui;

import java.awt.Graphics;
import java.util.ArrayList;

import teams.SearchTeam;

public class TeamLayer extends Layer {

	private static final long serialVersionUID = -3091212122097231088L;
	private ArrayList<SearchTeam> teams;
	
	public TeamLayer(int imageWidth, int imageHeight) {
		super(imageWidth, imageHeight);
		teams = new ArrayList<SearchTeam>();
	}
	
	public void addTeam(SearchTeam s) {
		teams.add(s);
	}
	
	public void removeTeam(SearchTeam s) {
		teams.remove(s);
	}
	
	public void paintComponent(Graphics g) {
		// draw teams
		for(SearchTeam t : teams) {
			t.draw(g);
		}
	}

}
