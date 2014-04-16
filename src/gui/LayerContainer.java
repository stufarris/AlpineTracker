package gui;

import java.awt.Dimension;
import java.util.ArrayList;
import javax.swing.JLayeredPane;

public class LayerContainer extends JLayeredPane {

	private static final long serialVersionUID = -4286033690300002446L;
	
	private int height, width;
	private ArrayList<Layer> layers;
	
	public LayerContainer(int imageWidth, int imageHeight) {
		this.height = imageHeight;
		this.width = imageWidth;
		layers = new ArrayList<Layer>();
		this.setPreferredSize(new Dimension(width, height));
	}
	
	public void updateLayers() {
		this.removeAll();
		
		for (int i = 0; i < layers.size(); i++) {
			this.add(layers.get(i), new Integer(i));
			layers.get(i).setBounds(0, 0, width, height);
		}
		
		this.revalidate();
	}
	
	public void addLayer(Layer layer) {
		this.layers.add(layer);
	}
	
	public void removeLayer(Layer layer) {
		this.layers.remove(layer);
	}

}
