package gui;

import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Image;
import java.awt.MediaTracker;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.net.URL;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.BorderFactory;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.border.EtchedBorder;
import javax.swing.filechooser.FileNameExtensionFilter;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import net.miginfocom.swing.MigLayout;

public class MainWindow extends JFrame {

	private static final long serialVersionUID = 682966950509743864L;

	private TeamLayer teamLayer;
	private MapLayer mapLayer;

	private JPanel infoBar;
	private JScrollPane scrollPane;
	private TeamAndMarkerDisplay teamDisplay;

	private static final int INFO_BAR_HEIGHT = 15;

	private DistanceConverter converter;

	private Image map;
	private Image teamHikerIcon, teamDogsIcon, teamHelicopterIcon;
	private Image markerBlack, markerBlue, markerGreen, markerOrange, markerPurple, markerTeal, markerWhite, markerYellow;

	private double topLeftLat;
	private double topLeftLong;
	private double botRightLat;
	private double botRightLong;
	private boolean cornersRecieved;

	private Timer timer = new Timer();
	private JLabel timeLabel;

	private DateTime currentTime;
	
	DateTimeFormatter clockTime = DateTimeFormat.forPattern("hh:mm:ssa");

	// Files for loaded images
	private static final String dogImage = "/team_dog.png";
	private static final String hikerImage = "/team_hiker.png";
	private static final String heliImage = "/team_helicopter.png";
	private static final String blackImage = "/marker_black.png";
	private static final String blueImage = "/marker_blue.png";
	private static final String greenImage = "/marker_green.png";
	private static final String orangeImage = "/marker_orange.png";
	private static final String purpleImage = "/marker_purple.png";
	private static final String tealImage = "/marker_teal.png";
	private static final String whiteImage = "/marker_white.png";
	private static final String yellowImage = "/marker_yellow.png";

	// File for loaded map
	File mapFile;

	public MainWindow() {

		// Load icons for teams and markers
		teamDogsIcon = loadIcon(getClass().getResource(dogImage));
		teamHikerIcon = loadIcon(getClass().getResource(hikerImage));
		teamHelicopterIcon = loadIcon(getClass().getResource(heliImage));
		markerBlack = loadIcon(getClass().getResource(blackImage));
		markerBlue = loadIcon(getClass().getResource(blueImage));
		markerGreen = loadIcon(getClass().getResource(greenImage));
		markerOrange = loadIcon(getClass().getResource(orangeImage));
		markerPurple = loadIcon(getClass().getResource(purpleImage));
		markerTeal = loadIcon(getClass().getResource(tealImage));
		markerWhite = loadIcon(getClass().getResource(whiteImage));
		markerYellow = loadIcon(getClass().getResource(yellowImage));

		// Set timer frequency to 1 second (update frequency)
		timer.schedule(new Updater(), 0, 1000);
		timeLabel = new JLabel();

		// JFrame setup
		setTitle("RealRescue");
		setSize(600, 600);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLayout(new MigLayout());
		setExtendedState(java.awt.Frame.MAXIMIZED_BOTH);
		setJMenuBar(generateMenu());

		// Set up bottom info bar and map
		setUpInfoBar();

		scrollPane = new JScrollPane();
		teamDisplay = new TeamAndMarkerDisplay();
		teamDisplay.getAddButton().addActionListener(new AddRemoveUpdateListener());
		teamDisplay.getRemoveButton().addActionListener(new AddRemoveUpdateListener());
		teamDisplay.getUpdateButton().addActionListener(new AddRemoveUpdateListener());
		teamDisplay.getAddButton().setEnabled(false);
		teamDisplay.getRemoveButton().setEnabled(false);
		teamDisplay.getUpdateButton().setEnabled(false);
		teamLayer = null;
		mapLayer = null;

		addComponents();



	}

	//used when closing program to remove components
	public void removeComponents() {
		remove(scrollPane);
		remove(infoBar);
		remove(teamDisplay);
	}

	//used to add components
	public void addComponents() {
		add(scrollPane, "grow, push");
		add(teamDisplay, "grow, width 20%!, wrap");
		add(infoBar, "span 2, grow, wrap");
		revalidate();
	}

	//creates the file menu found at the top of the window and adds items for adding teams, closing, etc.
	public JMenuBar generateMenu() {

		JMenu fileMenu = new JMenu("File");
		JMenuItem loadMap = new JMenuItem("Load Map");
		loadMap.addActionListener(new LoadMapListener());
		JMenuItem saveMap = new JMenuItem("Save Map");
		saveMap.setEnabled(false);

		fileMenu.add(loadMap);
		fileMenu.add(saveMap);

		JMenuBar mainMenu = new JMenuBar();
		mainMenu.add(fileMenu);


		return mainMenu;

	}

	//creates layer container and adds the map and team layer to the container
	public void setUpLayerContainer() {
		mapLayer = new MapLayer(this.map);
		teamLayer = new TeamLayer(mapLayer.getWidth(), mapLayer.getHeight());
		LayerContainer layers = new LayerContainer(mapLayer.getWidth(), mapLayer.getHeight());

		layers.addLayer(mapLayer);
		layers.addLayer(teamLayer);
		layers.updateLayers();

		scrollPane = new JScrollPane(layers);
	}

	//creates the infoBar found at the bottom of the screen which shows the time
	public void setUpInfoBar() {
		infoBar = new JPanel(new MigLayout());
		infoBar.setPreferredSize(new Dimension(this.getWidth(), INFO_BAR_HEIGHT));
		infoBar.add(timeLabel, "push");
		infoBar.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED));
	}


	private class LoadMapListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent arg0) {
			JFileChooser chooser = new JFileChooser();
			FileNameExtensionFilter filter = new FileNameExtensionFilter(
					"Image files", "jpg", "gif", "png");
			chooser.setFileFilter(filter);
			int returnVal = chooser.showOpenDialog(getMainWindow());
			if(returnVal == JFileChooser.APPROVE_OPTION) {
				mapFile = chooser.getSelectedFile();
				MapInitDialog mapInitDialog = new MapInitDialog(converter, getMainWindow());
				mapInitDialog.setVisible(true);
				if(cornersRecieved == true){
					loadNewMap();
					teamDisplay.getAddButton().setEnabled(true);
					teamDisplay.getRemoveButton().setEnabled(true);
					teamDisplay.getUpdateButton().setEnabled(true);
				}
			}
		}

	}


	private class AddRemoveUpdateListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent arg0) {
			if (arg0.getSource() == teamDisplay.getAddButton()) {
				// Display add dialog, get info from it, add thing
				AddDialog dialog = new AddDialog(teamLayer, converter, getMainWindow());
				dialog.setVisible(true);
			} else if (arg0.getSource() == teamDisplay.getRemoveButton()) {
				if (teamDisplay.teamTabIsSelected()) {
					if (teamDisplay.getSelectedTeamIndex() > -1) {
						teamLayer.removeTeam(teamLayer.getTeams().get(teamDisplay.getSelectedTeamIndex()));
						teamLayer.updateTeams();
						teamDisplay.updateTeamTable(teamLayer.getTeams());
					}
				}
				if (teamDisplay.markerTabIsSelected()) {
					if (teamDisplay.getSelectedMarkerIndex() > -1) {
						teamLayer.removeMarker(teamLayer.getMarkers().get(teamDisplay.getSelectedMarkerIndex()));
						teamDisplay.updateMarkerTable(teamLayer.getMarkers());
					}
				}
			} else if (arg0.getSource() == teamDisplay.getUpdateButton()) {
				if (
						(teamDisplay.teamTabIsSelected() && teamDisplay.getSelectedTeamIndex() > -1) ||
						(teamDisplay.markerTabIsSelected() && teamDisplay.getSelectedMarkerIndex() > -1)
						) {
					UpdateDialog dialog = new UpdateDialog(teamLayer, converter, teamDisplay);
					dialog.setVisible(true);
				} else {
					JOptionPane.showMessageDialog(null, "Please select a team or marker first.", "Error", JOptionPane.ERROR_MESSAGE);
				}

			}
		}

	}

	//utilized when user loads a new map when the program starts
	public void loadNewMap() {
		// Load image file
		MediaTracker tracker = new MediaTracker(this);
		map = Toolkit.getDefaultToolkit().getImage(mapFile.getPath());
		tracker.addImage(map, 0);
		try {
			tracker.waitForID(0);
		} catch (InterruptedException e) { e.printStackTrace(); }
		
		// This will happen when the second dialog box is completed
		//		double topLeftLat = DistanceConverter.convertDMStoDecimal(39, 45, 0);
		//		double topLeftLong = DistanceConverter.convertDMStoDecimal(106, 0, 0);
		//		double botRightLat = DistanceConverter.convertDMStoDecimal(39, 37, 30);
		//		double botRightLong = DistanceConverter.convertDMStoDecimal(105, 52, 30);
		setConverter(new DistanceConverter(topLeftLat, topLeftLong, botRightLat, botRightLong, map.getWidth(null), map.getHeight(null)));

		removeComponents();
		setUpLayerContainer();
		addComponents();

	}

	public void setConverter(DistanceConverter converter) {
		this.converter = converter;
	}

	//loads icon
	public Image loadIcon(URL imageLocation) {
		MediaTracker tracker = new MediaTracker(this);
		Image image = Toolkit.getDefaultToolkit().getImage(imageLocation);
		tracker.addImage(image, 0);
		try {
			tracker.waitForID(0);
			return image;
		} catch (InterruptedException e) {  return null; }
	}

	private class Updater extends TimerTask {

		@Override
		public void run() {
			EventQueue.invokeLater(new Runnable() {

				@Override
				public void run() {
					// Should be called every second
					currentTime = new DateTime();
					updateInfoBar();
					if (teamLayer != null) {
						teamLayer.updateTeams();
						teamDisplay.updateTeamTable(teamLayer.getTeams());
						teamDisplay.updateMarkerTable(teamLayer.getMarkers());
					}
					repaint();

				}
			});
		}
	}

	//gets current times and updates the bar at the bottom of the screen
	public void updateInfoBar() {
		String timeString = "";
		timeString += "Current time: ";
		timeString += currentTime.getMonthOfYear() + "/";
		timeString += currentTime.getDayOfMonth() + "/";
		timeString += currentTime.getYearOfCentury() + ", ";
		timeString += clockTime.print(currentTime);

		timeLabel.setText(timeString);
	}

	public MainWindow getMainWindow() {
		return this;
	}



	public static void main(String[] args) {
		//		 Set to system look and feel
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

	//sets latitude of top left corner of map
	public void setTopLeftLat(double topLeftLat) {
		this.topLeftLat = topLeftLat;
	}

	//sets top left longitude of map
	public void setTopLeftLong(double topLeftLong) {
		this.topLeftLong = topLeftLong;
	}

	//sets bottom right latitude of map
	public void setBotRightLat(double botRightLat) {
		this.botRightLat = botRightLat;
	}

	//sets bottom right longitude of map
	public void setBotRightLong(double botRightLong) {
		this.botRightLong = botRightLong;
	}

	//sets flag if corners are inputed
	public void setCornersRecieved(boolean cornersRecieved) {
		this.cornersRecieved = cornersRecieved;
	}

	//returns hiker team image
	public Image getTeamHikerIcon() {
		return teamHikerIcon;
	}

	//returns dog team image
	public Image getTeamDogsIcon() {
		return teamDogsIcon;
	}

	//returns helicopter team image
	public Image getTeamHelicopterIcon() {
		return teamHelicopterIcon;
	}

	public Image getMarkerBlack() {
		return markerBlack;
	}

	public Image getMarkerBlue() {
		return markerBlue;
	}

	public Image getMarkerGreen() {
		return markerGreen;
	}

	public Image getMarkerOrange() {
		return markerOrange;
	}

	public Image getMarkerPurple() {
		return markerPurple;
	}

	public Image getMarkerTeal() {
		return markerTeal;
	}

	public Image getMarkerWhite() {
		return markerWhite;
	}

	public Image getMarkerYellow() {
		return markerYellow;
	}
	
	




}
