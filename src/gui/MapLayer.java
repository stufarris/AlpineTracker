package gui;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;

public class MapLayer extends Layer {

	private static final long serialVersionUID = -831097041156312476L;
	private Image map;

	public MapLayer(Image map) {
		super(map.getWidth(null), map.getHeight(null));
		this.map = map;
		this.setPreferredSize(new Dimension(map.getWidth(null), map.getHeight(null)));
	}

	public void paintComponent(Graphics g) {
		g.drawImage(map, 0, 0, null);
	}

}
