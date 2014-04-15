package gui;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.MediaTracker;
import java.awt.Toolkit;
import java.net.URL;

import javax.swing.JPanel;


public class MapDisplayPanel extends JPanel {

	private static final long serialVersionUID = -831097041156312476L;
	private Image map;

	public MapDisplayPanel(URL imageLocation) {
		MediaTracker tracker = new MediaTracker(this);
		map = Toolkit.getDefaultToolkit().getImage(imageLocation);
		tracker.addImage(map, 0);
		try {
			tracker.waitForID(0);
		} catch (InterruptedException e) {  return; }
		this.setPreferredSize(new Dimension(map.getWidth(null), map.getHeight(null)));
	}

	public void paintComponent(Graphics g) {
		g.drawImage(map, 0, 0, null);
	}

}
