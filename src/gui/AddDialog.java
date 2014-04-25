package gui;

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
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JSeparator;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import org.joda.time.DateTime;

import teams.SearchTeam;
import teams.SearchTeam.TeamType;
import net.miginfocom.swing.MigLayout;

public class AddDialog extends JFrame {
	
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
	private JButton okButton = new JButton("OK");
	private JButton cancelButton = new JButton("Cancel");
	
	Image teamDogs, teamHikers, teamHelicopter;

	private final static String[] types = {"Team", "Marker"};
	private final static String[] teamTypes = {"Hikers", "Dogs", "Helicopter"};

	public AddDialog(TeamLayer teamLayer, DistanceConverter converter, MainWindow mainWindow) {
		this.teamLayer = teamLayer;
		this.converter = converter;
		this.mainWindow = mainWindow;
		this.teamDogs = mainWindow.getTeamDogs();
		this.teamHikers = mainWindow.getTeamHiker();
		this.teamHelicopter = mainWindow.getTeamHelicopter();
		
		
		setTitle("Add an Item");
		setSize(400, 300);
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

		cancelButton.addActionListener(new CancelListener());
		okButton.addActionListener(new okListener());
		addComponents();
	}

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
			}
			else if(selectedType == "Team"){
				searchTeamSelect.setEnabled(true);
				headingField.setEnabled(true);
				speedField.setEnabled(true);
			}
		}
	}

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
					s = new SearchTeam(name, latToPass, lonToPass, new DateTime(), mainWindow.getTeamHelicopter(), TeamType.HELICOPTER, converter);
				} else if (teamType.equals("Dogs")){
					s = new SearchTeam(name, latToPass, lonToPass, new DateTime(), mainWindow.getTeamDogs(), TeamType.DOGS, converter);
				} else if (teamType.equals("Hikers")){
					s = new SearchTeam(name, latToPass, lonToPass, new DateTime(), mainWindow.getTeamHiker(), TeamType.HIKERS, converter);
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
				
				//create new marker here
			}
			
		}

		private Boolean isInteger(String s, String errorMessage) {
			try {
				Integer.parseInt(s);
			} catch(NumberFormatException nfe) {
				JOptionPane.showMessageDialog(null, errorMessage, "Error", JOptionPane.ERROR_MESSAGE);
				return false;
			}
			return true;
		}
		
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

	public class CancelListener implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent arg0) {
			setVisible(false);
		}
	}
}
