package gui;

import java.awt.EventQueue;
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
import net.miginfocom.swing.MigLayout;

public class UpdateDialog extends JFrame {

	private TeamLayer teamLayer;
	private DistanceConverter converter;
	TeamAndMarkerDisplay teamAndMarkerDisplay;

	private JLabel latLabel = new JLabel("Latitude");
	private JLabel lonLabel = new JLabel("Longitude");
	private JTextField latDegField, latMinField, latSecField;
	private JTextField lonDegField, lonMinField, lonSecField;
	private JLabel headingLabel = new JLabel("Heading:");
	private JTextField headingField;
	private JLabel speedLabel = new JLabel("Speed:");
	private JTextField speedField;
	private JButton okButton = new JButton("OK");
	private JButton cancelButton = new JButton("Cancel");

	public UpdateDialog(TeamLayer teamLayer, DistanceConverter converter, TeamAndMarkerDisplay teamAndMarkerDisplay) {
		this.teamLayer = teamLayer;
		this.converter = converter;
		this.teamAndMarkerDisplay = teamAndMarkerDisplay;

		setTitle("Update an Item");
		setSize(400, 220);
		setLayout(new MigLayout());
		setLocationRelativeTo(null);

		latDegField = new JTextField();
		latMinField = new JTextField();
		latSecField = new JTextField();
		lonDegField = new JTextField();
		lonMinField = new JTextField();
		lonSecField = new JTextField();
		headingField = new JTextField();
		speedField = new JTextField();

		cancelButton.addActionListener(new CancelListener());
		okButton.addActionListener(new okListener());
		addComponents();
	}

	public void addComponents() {
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
		add(headingLabel);
		add(headingField, "grow, push, span, wrap");
		add(speedLabel);
		add(speedField, "grow, push, span, wrap");
		add(okButton);
		add(cancelButton);
	}

	public class okListener implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent arg0) {



			int latDegNum, latMinNum, latSecNum, lonDegNum, lonMinNum, lonSecNum, headingNum;
			double speedNum = 0;

			String latDeg = latDegField.getText();
			String latMin = latMinField.getText();
			String latSec = latSecField.getText();
			String lonDeg = lonDegField.getText();
			String lonMin = lonMinField.getText();
			String lonSec = lonSecField.getText();
			String heading = headingField.getText();
			String speed = speedField.getText();

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
			if (isDouble(speed, "Please enter a numerical value for speed.")) speedNum = Double.parseDouble(speed);
			else return;

			double latToPass = DistanceConverter.convertDMStoDecimal(latDegNum, latMinNum, latSecNum);
			double lonToPass = DistanceConverter.convertDMStoDecimal(lonDegNum, lonMinNum, lonSecNum);
			
			setVisible(false);

			if (teamAndMarkerDisplay.teamTabIsSelected()) {
				int index = teamAndMarkerDisplay.getSelectedTeamIndex();
				teamLayer.getTeams().get(index).setHeading(headingNum);
				teamLayer.getTeams().get(index).setLocation(latToPass, lonToPass);
			} else if (teamAndMarkerDisplay.markerTabIsSelected()) {
				//markerLayer.update
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

	public class CancelListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent arg0) {
			setVisible(false);
		}
	}

}

