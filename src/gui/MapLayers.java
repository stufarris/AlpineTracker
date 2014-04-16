package gui;

import java.awt.Dimension;
import javax.swing.JLayeredPane;

public class MapLayers extends JLayeredPane {

	private static final long serialVersionUID = -4286033690300002446L;
	
	public MapLayers(int imageWidth, int imageHeight, MapLayerMap map, MapLayerTeam team) {
		this.setPreferredSize(new Dimension(imageWidth, imageHeight));
		this.add(map, new Integer(50));
		this.add(team, new Integer(100));
		map.setBounds(0, 0, imageWidth, imageHeight);
		team.setBounds(0, 0, imageWidth, imageHeight);
	}

}
