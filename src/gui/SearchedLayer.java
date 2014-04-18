package gui;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Composite;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.geom.Ellipse2D;
import java.util.ArrayList;

public class SearchedLayer extends Layer {

	private static final long serialVersionUID = -7373970641169204519L;
	
	private ArrayList<Point> searchedPoints = new ArrayList<Point>();
	private static final int CIRCLE_RADIUS = 10;

	public SearchedLayer(int imageWidth, int imageHeight) {
		super(imageWidth, imageHeight);
	}

	private AlphaComposite makeComposite(float alpha) {
		int type = AlphaComposite.SRC_OVER;
		return(AlphaComposite.getInstance(type, alpha));
	}
	
	public void addSearchedPoint(int x, int y) {
		searchedPoints.add(new Point(x, y));
	}

	private void drawSearchedAreas(Graphics2D g2d, float alpha) {
		Composite originalComposite = g2d.getComposite();
		g2d.setPaint(Color.RED);
		g2d.setComposite(makeComposite(alpha));
		// Draw things
		for (Point p : searchedPoints) {
			g2d.fill(new Ellipse2D.Double(p.x - CIRCLE_RADIUS, p.y - CIRCLE_RADIUS, 2 * CIRCLE_RADIUS, 2 * CIRCLE_RADIUS));
		}
		g2d.setComposite(originalComposite);
	}
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D)g;
		drawSearchedAreas(g2d, (float)0.1);
	}

}
