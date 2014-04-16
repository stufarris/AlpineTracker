package gui;

import java.awt.Color;
import java.awt.Graphics;

public class TeamLayer extends Layer {

	private static final long serialVersionUID = -3091212122097231088L;
	
	public TeamLayer(int imageWidth, int imageHeight) {
		super(imageWidth, imageHeight);
	}
	
	public void paintComponent(Graphics g) {
		g.setColor(Color.RED);
		g.fillOval(100, 100, 10, 10);
		// draw team
	}

}
