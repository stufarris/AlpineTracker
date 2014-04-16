package gui;

import java.awt.Color;
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
	
	public void paintComponent(Graphics g) {
		g.setColor(Color.RED);
		g.fillOval(100, 100, 10, 10);
		// draw teams
		for(SearchTeam t : teams) {
			t.draw(g);
		}
	}

}
