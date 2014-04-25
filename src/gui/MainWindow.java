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

import teams.SearchTeam;
import teams.SearchTeam.TeamType;

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
	private Image teamHiker, teamDogs, teamHelicopter;

	public Image getTeamHiker() {
		return teamHiker;
	}

	public Image getTeamDogs() {
		return teamDogs;
	}

	public Image getTeamHelicopter() {
		return teamHelicopter;
	}



	private Timer timer = new Timer();
	private JLabel timeLabel;

	private DateTime currentTime;

	// Files for loaded images
	private static final String dogImage = "/team_dog.png";
	private static final String hikerImage = "/team_hiker.png";
	private static final String heliImage = "/team_helicopter.png";

	// File for loaded map
	File mapFile;

	public MainWindow() {

		// Load icons for teams and markers
		teamDogs = loadIcon(getClass().getResource(dogImage));
		teamHiker = loadIcon(getClass().getResource(hikerImage));
		teamHelicopter = loadIcon(getClass().getResource(heliImage));

		// Set timer frequency to 1 second (update frequency)
		timer.schedule(new Updater(), 0, 1000);
		timeLabel = new JLabel();

		// JFrame setup
		setTitle("RealRescue");
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
		teamLayer = null;
		mapLayer = null;
		
		addComponents();

	}
	
	public void removeComponents() {
		remove(scrollPane);
		remove(infoBar);
		remove(teamDisplay);
	}
	
	public void addComponents() {
		add(scrollPane, "grow, push");
		add(teamDisplay, "grow, width 20%!, wrap"); //TODO: this will need to add a member variable in the near future
		add(infoBar, "span 2, grow, wrap");
		revalidate();
	}

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

	public void setUpLayerContainer() {
		mapLayer = new MapLayer(this.map);
		teamLayer = new TeamLayer(mapLayer.getWidth(), mapLayer.getHeight());
		LayerContainer layers = new LayerContainer(mapLayer.getWidth(), mapLayer.getHeight());

		
		teamLayer.addTeam(new SearchTeam("Test Team",
				DistanceConverter.convertDMStoDecimal(39, 44, 30),
				DistanceConverter.convertDMStoDecimal(105, 59, 0), currentTime, teamDogs, TeamType.DOGS, converter));
		teamLayer.addTeam(new SearchTeam("Test Team",
				DistanceConverter.convertDMStoDecimal(39, 40, 30),
				DistanceConverter.convertDMStoDecimal(105, 55, 0), currentTime, teamHiker, TeamType.HIKERS, converter));
		teamLayer.addTeam(new SearchTeam("Test Team",
				DistanceConverter.convertDMStoDecimal(39, 43, 0),
				DistanceConverter.convertDMStoDecimal(105, 57, 0), currentTime, teamHelicopter, TeamType.HELICOPTER, converter));
		
		teamLayer.getTeams().get(0).setHeading(90);
		teamLayer.getTeams().get(0).setSpeed(10);
		teamLayer.getTeams().get(1).setHeading(345);
		teamLayer.getTeams().get(1).setSpeed(5);
		teamLayer.getTeams().get(2).setHeading(90);
		teamLayer.getTeams().get(2).setSpeed(50);

		layers.addLayer(mapLayer);
		layers.addLayer(teamLayer);
		layers.updateLayers();

		scrollPane = new JScrollPane(layers);
	}

	public void setUpInfoBar() {
		infoBar = new JPanel(new MigLayout());
		infoBar.setPreferredSize(new Dimension(this.getWidth(), INFO_BAR_HEIGHT));
		infoBar.add(timeLabel, "push");
		infoBar.add(new JLabel("This panel can display more relevant info later on."), "wrap");
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
				loadNewMap();
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
					//TODO remove marker
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

	public void loadNewMap() {
		// Load image file
		MediaTracker tracker = new MediaTracker(this);
		map = Toolkit.getDefaultToolkit().getImage(mapFile.getPath());
		tracker.addImage(map, 0);
		try {
			tracker.waitForID(0);
		} catch (InterruptedException e) { e.printStackTrace(); }


		// This will happen when the second dialog box is completed
		double topLeftLat = DistanceConverter.convertDMStoDecimal(39, 45, 0);
		double topLeftLong = DistanceConverter.convertDMStoDecimal(106, 0, 0);
		double botRightLat = DistanceConverter.convertDMStoDecimal(39, 37, 30);
		double botRightLong = DistanceConverter.convertDMStoDecimal(105, 52, 30);
		converter = new DistanceConverter(topLeftLat, topLeftLong, botRightLat, botRightLong, map.getWidth(null), map.getHeight(null));
		
		removeComponents();
		setUpLayerContainer();
		addComponents();
	}

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
					}
					repaint();

				}
			});
		}
	}

	public void updateInfoBar() {
		String timeString = "";
		timeString += "Current time: ";
		timeString += currentTime.getMonthOfYear() + "/";
		timeString += currentTime.getDayOfMonth() + "/";
		timeString += currentTime.getYearOfCentury() + ", ";
		timeString += currentTime.getHourOfDay() + ":";
		timeString += currentTime.getMinuteOfHour() + ":";
		timeString += currentTime.getSecondOfMinute();

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

}
