package gui;

import java.awt.Dialog;
import java.awt.EventQueue;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JSeparator;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import org.joda.time.DateTime;

import teams.Marker;
import teams.SearchTeam;
import teams.SearchTeam.TeamType;
import net.miginfocom.swing.MigLayout;

//This component is a window used to add teams and markers to the map.

public class AddDialog extends JDialog {
	
	private TeamLayer teamLayer;
	private DistanceConverter converter;
	private MainWindow mainWindow;

	private JLabel typeLabel = new JLabel("Type:");
	private JComboBox typeSelect;
	private JLabel nameLabel = new JLabel("Name:");
	private JTextField nameField = new JTextField();
	private JLabel latLabel = new JLabel("Latitude");
	private JLabel lonLabel = new JLabel("Longitude");
	private JTextField latDegField, latMinField, latSecField;
	private JTextField lonDegField, lonMinField, lonSecField;
	private JLabel searchTeamLabel = new JLabel("Search Team:");
	private JComboBox searchTeamSelect;
	private JLabel headingLabel = new JLabel("Heading:");
	private JTextField headingField;
	private JLabel speedLabel = new JLabel("Speed:");
	private JTextField speedField;
	private JLabel colorLabel = new JLabel("Color:");
	private JComboBox colorSelect;
	private JButton okButton = new JButton("OK");
	private JButton cancelButton = new JButton("Cancel");

	private final static String[] types = {"Team", "Marker"};
	private final static String[] teamTypes = {"Hikers", "Dogs", "Helicopter"};
	private final static String[] colors = {"Black", "Blue", "Green", "Orange", "Purple", "Teal", "White", "Yellow"};

	public AddDialog(TeamLayer teamLayer, DistanceConverter converter, MainWindow mainWindow) {
		//This constructor creates the window and adds all the necesary GUI components
		setModalityType(Dialog.ModalityType.APPLICATION_MODAL);
		this.teamLayer = teamLayer;
		this.converter = converter;
		this.mainWindow = mainWindow;
		
		
		setTitle("Add an Item");
		setSize(400, 350);
		setLayout(new MigLayout());
		setLocationRelativeTo(null);
		typeSelect = new JComboBox<String>(types);
		typeSelect.addItemListener(new ItemChangeListener());

		latDegField = new JTextField();
		latMinField = new JTextField();
		latSecField = new JTextField();
		lonDegField = new JTextField();
		lonMinField = new JTextField();
		lonSecField = new JTextField();

		searchTeamSelect = new JComboBox<String>(teamTypes);
		headingField = new JTextField();
		speedField = new JTextField();
		
		colorSelect = new JComboBox<String>(colors);
		colorSelect.setEnabled(false);
		

		cancelButton.addActionListener(new CancelListener());
		okButton.addActionListener(new okListener());
		addComponents();
	}

	//This function adds the components to the JDialog and uses 3rd party libraries for formatting.
	public void addComponents() {
		add(typeLabel);
		add(typeSelect, "span, wrap");
		add(nameLabel);
		add(nameField, "grow, push, span, wrap");
		add(new JSeparator(SwingConstants.HORIZONTAL), "grow, span, wrap");
		add(latLabel, "span, wrap");
		add(new JLabel("Degrees:"));
		add(latDegField, "grow, push");
		add(new JLabel("Minutes:"));
		add(latMinField, "grow, push");
		add(new JLabel("Seconds:"));
		add(latSecField, "grow, push, wrap");
		add(new JSeparator(SwingConstants.HORIZONTAL), "grow, span, wrap");
		add(lonLabel, "span, wrap");
		add(new JLabel("Degrees:"));
		add(lonDegField, "grow, push");
		add(new JLabel("Minutes:"));
		add(lonMinField, "grow, push");
		add(new JLabel("Seconds:"));
		add(lonSecField, "grow, push, wrap");
		add(new JSeparator(SwingConstants.HORIZONTAL), "grow, span, wrap");
		add(searchTeamLabel);
		add(searchTeamSelect, "span, wrap");
		add(headingLabel);
		add(headingField, "grow, push, span, wrap");
		add(speedLabel);
		add(speedField, "grow, push, span, wrap");
		add(colorLabel);
		add(colorSelect, "span, wrap");
		add(okButton);
		add(cancelButton);
	}

	public class ItemChangeListener implements ItemListener{
		@Override
		public void itemStateChanged(ItemEvent e) {
			JComboBox cb = (JComboBox)e.getSource();
			String selectedType = (String)cb.getSelectedItem();
			if(selectedType == "Marker"){
				searchTeamSelect.setEnabled(false);
				headingField.setEnabled(false);
				speedField.setEnabled(false);
				colorSelect.setEnabled(true);
			}
			else if(selectedType == "Team"){
				searchTeamSelect.setEnabled(true);
				headingField.setEnabled(true);
				speedField.setEnabled(true);
				colorSelect.setEnabled(false);
			}
		}
	}
	
	//If "ok" button is pressed, this function makes sure that all the necessary JTextFields
	// have text and that the text in them is valid input.
	public class okListener implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent arg0) {
			
			String selection = typeSelect.getSelectedItem().toString();
			String teamType = searchTeamSelect.getSelectedItem().toString();
			
			if (selection.equals("Team")){
				int latDegNum, latMinNum, latSecNum, lonDegNum, lonMinNum, lonSecNum, headingNum;
				double speedNum = 0;

				String name = nameField.getText();
				String latDeg = latDegField.getText();
				String latMin = latMinField.getText();
				String latSec = latSecField.getText();
				String lonDeg = lonDegField.getText();
				String lonMin = lonMinField.getText();
				String lonSec = lonSecField.getText();
				String heading = headingField.getText();
				String speed = speedField.getText();
				
				if (name.length() == 0){
					JOptionPane.showMessageDialog(null, "Please enter a team name.", "Error", JOptionPane.ERROR_MESSAGE);
					return;
				}
				if (isInteger(latDeg, "Please enter a numerical value for latitude degrees.")) latDegNum = Integer.parseInt(latDeg);
				else return;
				if (isInteger(latMin, "Please enter a numerical value for latitude minutes.")) latMinNum = Integer.parseInt(latMin);
				else return;
				if (isInteger(latSec, "Please enter a numerical value for latitude seconds.")) latSecNum = Integer.parseInt(latSec);
				else return;
				if (isInteger(lonDeg, "Please enter a numerical value for longitude degrees.")) lonDegNum = Integer.parseInt(lonDeg);
				else return;
				if (isInteger(lonMin, "Please enter a numerical value for longitude minutes.")) lonMinNum = Integer.parseInt(lonMin);
				else return;
				if (isInteger(lonSec, "Please enter a numerical value for longitude seconds.")) lonSecNum = Integer.parseInt(lonSec);
				else return;
				if (isInteger(heading, "Please enter a numerical value for heading.")) headingNum = Integer.parseInt(heading);
				else return;
				if (isDouble(speed, "Please enter a decimal value for speed.")) speedNum = Double.parseDouble(speed);
				else return;
				
				double latToPass = DistanceConverter.convertDMStoDecimal(latDegNum, latMinNum, latSecNum);
				double lonToPass = DistanceConverter.convertDMStoDecimal(lonDegNum, lonMinNum, lonSecNum);
				
				//
				
				setVisible(false);
				
				SearchTeam s;
				
				if (teamType.equals("Helicopter")){
					s = new SearchTeam(name, latToPass, lonToPass, new DateTime(), mainWindow.getTeamHelicopterIcon(), TeamType.HELICOPTER, converter);
				} else if (teamType.equals("Dogs")){
					s = new SearchTeam(name, latToPass, lonToPass, new DateTime(), mainWindow.getTeamDogsIcon(), TeamType.DOGS, converter);
				} else if (teamType.equals("Hikers")){
					s = new SearchTeam(name, latToPass, lonToPass, new DateTime(), mainWindow.getTeamHikerIcon(), TeamType.HIKERS, converter);
				} else {
					s = null;
				}
				
				s.setHeading(headingNum);
				s.setSpeed(speedNum);
				
				teamLayer.addTeam(s);
				
				
			} else if (selection.equals("Marker")){
				int latDegNum, latMinNum, latSecNum, lonDegNum, lonMinNum, lonSecNum = 0;
				
				String name = nameField.getText();
				String latDeg = latDegField.getText();
				String latMin = latMinField.getText();
				String latSec = latSecField.getText();
				String lonDeg = lonDegField.getText();
				String lonMin = lonMinField.getText();
				String lonSec = lonSecField.getText();
				
				if (name.length() == 0){
					JOptionPane.showMessageDialog(null, "Please enter a team name.", "Error", JOptionPane.ERROR_MESSAGE);
					return;
				}
				if (isInteger(latDeg, "Please enter a numerical value for latitude degrees.")) latDegNum = Integer.parseInt(latDeg);
				else return;
				if (isInteger(latMin, "Please enter a numerical value for latitude minutes.")) latMinNum = Integer.parseInt(latMin);
				else return;
				if (isInteger(latSec, "Please enter a numerical value for latitude seconds.")) latSecNum = Integer.parseInt(latSec);
				else return;
				if (isInteger(lonDeg, "Please enter a numerical value for longitude degrees.")) lonDegNum = Integer.parseInt(lonDeg);
				else return;
				if (isInteger(lonMin, "Please enter a numerical value for longitude minutes.")) lonMinNum = Integer.parseInt(lonMin);
				else return;
				if (isInteger(lonSec, "Please enter a numerical value for longitude seconds.")) lonSecNum = Integer.parseInt(lonSec);
				else return;
				
				setVisible(false);
				
				Marker m = null;
				
				switch((String)colorSelect.getSelectedItem()) {
				case("Black"):
					m = new Marker(name, DistanceConverter.convertDMStoDecimal(lonDegNum,lonMinNum,lonSecNum), DistanceConverter.convertDMStoDecimal(latDegNum,latMinNum,latSecNum), new DateTime(), mainWindow.getMarkerBlack(), converter);
				break;
				case("Blue"):
					m = new Marker(name, DistanceConverter.convertDMStoDecimal(lonDegNum,lonMinNum,lonSecNum), DistanceConverter.convertDMStoDecimal(latDegNum,latMinNum,latSecNum), new DateTime(), mainWindow.getMarkerBlue(), converter);
				break;
				case("Green"):
					m = new Marker(name, DistanceConverter.convertDMStoDecimal(lonDegNum,lonMinNum,lonSecNum), DistanceConverter.convertDMStoDecimal(latDegNum,latMinNum,latSecNum), new DateTime(), mainWindow.getMarkerGreen(), converter);
				break;
				case("Orange"):
					m = new Marker(name, DistanceConverter.convertDMStoDecimal(lonDegNum,lonMinNum,lonSecNum), DistanceConverter.convertDMStoDecimal(latDegNum,latMinNum,latSecNum), new DateTime(), mainWindow.getMarkerOrange(), converter);
				break;
				case("Purple"):
					m = new Marker(name, DistanceConverter.convertDMStoDecimal(lonDegNum,lonMinNum,lonSecNum), DistanceConverter.convertDMStoDecimal(latDegNum,latMinNum,latSecNum), new DateTime(), mainWindow.getMarkerPurple(), converter);
				break;
				case("Teal"):
					m = new Marker(name, DistanceConverter.convertDMStoDecimal(lonDegNum,lonMinNum,lonSecNum), DistanceConverter.convertDMStoDecimal(latDegNum,latMinNum,latSecNum), new DateTime(), mainWindow.getMarkerTeal(), converter);
				break;
				case("White"):
					m = new Marker(name, DistanceConverter.convertDMStoDecimal(lonDegNum,lonMinNum,lonSecNum), DistanceConverter.convertDMStoDecimal(latDegNum,latMinNum,latSecNum), new DateTime(), mainWindow.getMarkerWhite(), converter);
				break;
				case("Yellow"):
					m = new Marker(name, DistanceConverter.convertDMStoDecimal(lonDegNum,lonMinNum,lonSecNum), DistanceConverter.convertDMStoDecimal(latDegNum,latMinNum,latSecNum), new DateTime(), mainWindow.getMarkerYellow(), converter);
				break;
				
				}
				
				
				teamLayer.getMarkers().add(m);
			}
			
		}
		
		//checks to make sure that the string provided by the user is actually an integer
		private Boolean isInteger(String s, String errorMessage) {
			try {
				Integer.parseInt(s);
			} catch(NumberFormatException nfe) {
				JOptionPane.showMessageDialog(null, errorMessage, "Error", JOptionPane.ERROR_MESSAGE);
				return false;
			}
			return true;
		}
		
		//checks to make sure that the string provided by the user is actually a double
		private Boolean isDouble(String s, String errorMessage) {
			try {
				Double.parseDouble(s);
			} catch(NumberFormatException nfe) {
				JOptionPane.showMessageDialog(null, errorMessage, "Error", JOptionPane.ERROR_MESSAGE);
				return false;
			}
			return true;
		}
	}
	
	//checks if user clicks "cancel" and if so, hides window
	public class CancelListener implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent arg0) {
			setVisible(false);
		}
	}
}
