package gui;

import java.awt.Dimension;

import javax.swing.JPanel;

public class Layer extends JPanel {

	private static final long serialVersionUID = 1L;
	private int width, height;

	//creates a new layer and sets width and height
	public Layer(int imageWidth, int imageHeight) {
		this.width = imageWidth;
		this.height = imageHeight;
		this.setPreferredSize(new Dimension(width, height));
		this.setOpaque(false);
	}
	
	//getter for width
	public int getWidth() {
		return width;
	}
	
	//getter for height
	public int getHeight() {
		return height;
	}
	
}
