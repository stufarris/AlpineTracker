package gui;

import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Image;
import java.awt.MediaTracker;
import java.awt.Toolkit;
import java.net.URL;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

import org.joda.time.DateTime;

import teams.SearchTeam;
import teams.SearchTeam.TeamType;

import net.miginfocom.swing.MigLayout;

public class MainWindow extends JFrame {

	private static final long serialVersionUID = 682966950509743864L;

	private JPanel infoBar;

	private JScrollPane scrollPane;

	private static final int INFO_BAR_HEIGHT = 20;

	private DistanceConverter converter;

	private Image map;
	private Image team_hiker, team_dogs, team_helicopter;

	private Timer timer = new Timer();
	private JLabel timeLabel;

	private DateTime currentTime;

	private static final String dogImage = "/team_dog.png";
	private static final String hikerImage = "/team_hiker.png";
	private static final String heliImage = "/team_helicopter.png";

	public MainWindow() {

		timer.schedule(new Updater(), 0, 1000);
		timeLabel = new JLabel();

		setTitle("RealRescue");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLayout(new MigLayout());
		setExtendedState(java.awt.Frame.MAXIMIZED_BOTH);
		setJMenuBar(generateMenu());
		infoBar = new JPanel(new MigLayout());
		infoBar.setPreferredSize(new Dimension(this.getWidth(), INFO_BAR_HEIGHT));
		infoBar.add(timeLabel, "push, wrap");


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
		map = loadImage(getClass().getResource("/loveland.jpg"));
		team_dogs = loadImage(getClass().getResource(dogImage));
		team_hiker = loadImage(getClass().getResource(hikerImage));
		team_helicopter = loadImage(getClass().getResource(heliImage));


		// should be set on load image, will change depending on map
		double topLeftLat = DistanceConverter.convertDMStoDecimal(39, 45, 0);
		double topLeftLong = DistanceConverter.convertDMStoDecimal(106, 0, 0);
		double botRightLat = DistanceConverter.convertDMStoDecimal(39, 37, 30);
		double botRightLong = DistanceConverter.convertDMStoDecimal(105, 52, 30);
		converter = new DistanceConverter(topLeftLat, topLeftLong, botRightLat, botRightLong, map.getWidth(null), map.getHeight(null));


		MapLayer mapLayer = new MapLayer(this.map);
		TeamLayer teamLayer = new TeamLayer(mapLayer.getWidth(), mapLayer.getHeight());
		LayerContainer layers = new LayerContainer(mapLayer.getWidth(), mapLayer.getHeight());

		teamLayer.addTeam(new SearchTeam("Test Team",
				DistanceConverter.convertDMStoDecimal(39, 44, 30),
				DistanceConverter.convertDMStoDecimal(105, 59, 0), currentTime, team_dogs, TeamType.DOGS, converter));
		teamLayer.addTeam(new SearchTeam("Test Team",
				DistanceConverter.convertDMStoDecimal(39, 40, 30),
				DistanceConverter.convertDMStoDecimal(105, 55, 0), currentTime, team_hiker, TeamType.HIKERS, converter));
		teamLayer.addTeam(new SearchTeam("Test Team",
				DistanceConverter.convertDMStoDecimal(39, 43, 0),
				DistanceConverter.convertDMStoDecimal(105, 57, 0), currentTime, team_helicopter, TeamType.HELICOPTER, converter));
		

		layers.addLayer(mapLayer);
		layers.addLayer(teamLayer);
		layers.updateLayers();

		scrollPane = new JScrollPane(layers);
	}

	public Image loadImage(URL imageLocation) {
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
