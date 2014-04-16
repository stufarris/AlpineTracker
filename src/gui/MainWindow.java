package gui;

import java.awt.Dimension;
import java.awt.Image;
import java.awt.MediaTracker;
import java.awt.Toolkit;
import java.net.URL;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

import net.miginfocom.swing.MigLayout;

public class MainWindow extends JFrame {
	
	private static final long serialVersionUID = 682966950509743864L;

	private JPanel infoBar;
	
	private JScrollPane scrollPane;
	
	private static final int INFO_BAR_HEIGHT = 20;
	
	private Image image;
	
	public MainWindow() {
		setTitle("RealRescue");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLayout(new MigLayout());
		setExtendedState(java.awt.Frame.MAXIMIZED_BOTH);
		setJMenuBar(generateMenu());
		infoBar = new JPanel();
		infoBar.setPreferredSize(new Dimension(this.getWidth(), INFO_BAR_HEIGHT));
		infoBar.add(new JLabel("Latitude and Longitude information, as well as time and other info will display here"));
		
		
		setUpMapContainer();
		
		add(scrollPane, "push, grow, wrap");
		add(infoBar, "growx, wrap");
	}
	
	public JMenuBar generateMenu() {
		
		JMenu markerMenu = new JMenu("Markers");
		JMenuItem markerAdd = new JMenuItem("Add a Marker");
		JMenuItem markerInfo = new JMenuItem("Marker Info");
		markerMenu.add(markerAdd);
		markerMenu.add(markerInfo);
		
		JMenu teamsMenu = new JMenu("Teams");
		JMenuItem teamsAdd = new JMenuItem("Add a Team");
		JMenuItem teamsUpdate = new JMenuItem("Update a Team");
		teamsMenu.add(teamsAdd);
		teamsMenu.add(teamsUpdate);
		
		
		JMenu mapsMenu = new JMenu("Maps");
		JMenuItem mapsLoad = new JMenuItem("Load Map");
		mapsMenu.add(mapsLoad);
		
		JMenuBar mainMenu = new JMenuBar();
		mainMenu.add(markerMenu);
		mainMenu.add(teamsMenu);
		mainMenu.add(mapsMenu);
		
		
		return mainMenu;
		
	}
	
	public void setUpMapContainer() {
		loadImage(getClass().getResource("/loveland.jpg"));
		MapLayer mapLayer = new MapLayer(this.image);
		TeamLayer teamLayer = new TeamLayer(mapLayer.getWidth(), mapLayer.getHeight());
		LayerContainer layers = new LayerContainer(mapLayer.getWidth(), mapLayer.getHeight());
		
		layers.addLayer(mapLayer);
		layers.addLayer(teamLayer);
		layers.updateLayers();
		
		scrollPane = new JScrollPane(layers);
	}
	
	public void loadImage(URL imageLocation) {
		MediaTracker tracker = new MediaTracker(this);
		this.image = Toolkit.getDefaultToolkit().getImage(imageLocation);
		tracker.addImage(image, 0);
		try {
			tracker.waitForID(0);
		} catch (InterruptedException e) {  return; }
	}

	public static void main(String[] args) {
		// Set to system look and feel
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} 
		catch (Exception e) {
			// handle exception
		}

		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				MainWindow w = new MainWindow();
				w.setVisible(true);
			}
		});

	}

}
