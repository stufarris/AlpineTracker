package gui;

import java.awt.Dimension;

import javax.swing.JPanel;

public class Layer extends JPanel {

	private static final long serialVersionUID = 1L;
	private int width, height;

	public Layer(int imageWidth, int imageHeight) {
		this.width = imageWidth;
		this.height = imageHeight;
		this.setPreferredSize(new Dimension(width, height));
		this.setOpaque(false);
	}
	
	public int getWidth() {
		return width;
	}
	
	public int getHeight() {
		return height;
	}
	
}
