package gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.JPanel;

public class MapLayerTeam extends JPanel {

	private static final long serialVersionUID = -3091212122097231088L;
	
	public MapLayerTeam(int imageWidth, int imageHeight) {
		this.setPreferredSize(new Dimension(imageWidth, imageHeight));
		this.setOpaque(false);
	}
	
	public void paintComponent(Graphics g) {
		g.setColor(Color.RED);
		g.fillOval(100, 100, 10, 10);
		// draw team
	}

}
