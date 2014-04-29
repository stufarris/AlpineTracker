package gui;

import java.awt.Dialog;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import gui.AddDialog.CancelListener;
import gui.AddDialog.okListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JSeparator;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import net.miginfocom.swing.MigLayout;

public class MapInitDialog extends JDialog{
	private JLabel infoLabel = new JLabel("Enter top left and bottom right corners of the map.");
	private JLabel topLeftLabel = new JLabel("Top Left Corner");
	private JLabel botRightLabel = new JLabel("Bottom Right Corner");
	private JLabel topLatLabel = new JLabel("Latitude");
	private JLabel botLatLabel = new JLabel("Latitude");
	private JLabel topLonLabel = new JLabel("Longitude");
	private JLabel botLonLabel = new JLabel("Longitude");
	private JTextField topLatDegField, topLatMinField, topLatSecField;
	private JTextField botLatDegField, botLatMinField, botLatSecField;
	private JTextField topLonDegField, topLonMinField, topLonSecField;
	private JTextField botLonDegField, botLonMinField, botLonSecField;
	
	private JButton okButton = new JButton("OK");
	private JButton cancelButton = new JButton("Cancel");
	
	private DistanceConverter converter;
	private MainWindow mainWindow;
	
	public MapInitDialog(DistanceConverter converter, MainWindow mainWindow) {
		setTitle("Map Initialization");
		setSize(400,300);
		setLayout(new MigLayout());
		setLocationRelativeTo(null);
		setModalityType(Dialog.ModalityType.APPLICATION_MODAL);
		
		topLatDegField = new JTextField();
		topLatMinField = new JTextField();
		topLatSecField = new JTextField();
		botLatDegField = new JTextField();
		botLatMinField = new JTextField();
		botLatSecField = new JTextField();
		topLonDegField = new JTextField();
		topLonMinField = new JTextField();
		topLonSecField = new JTextField();
		botLonDegField = new JTextField();
		botLonMinField = new JTextField();
		botLonSecField = new JTextField();
		
		cancelButton.addActionListener(new CancelListener());
		okButton.addActionListener(new okListener());
		
		this.mainWindow = mainWindow;
		
		addComponents();
	}
	
	public void addComponents() {
		add(infoLabel, "span , wrap 4");
		add(new JSeparator(SwingConstants.HORIZONTAL), "grow, span, wrap");
		add(topLeftLabel, "span, wrap");
		add(topLatLabel, "span, wrap");
		add(new JLabel("Degrees:"));
		add(topLatDegField, "grow, push");
		add(new JLabel("Minutes:"));
		add(topLatMinField, "grow, push");
		add(new JLabel("Seconds:"));
		add(topLatSecField, "grow, push, wrap");
		add(topLonLabel, "span, wrap");
		add(new JLabel("Degrees:"));
		add(topLonDegField, "grow, push");
		add(new JLabel("Minutes:"));
		add(topLonMinField, "grow, push");
		add(new JLabel("Seconds:"));
		add(topLonSecField, "grow, push, wrap");
		add(new JSeparator(SwingConstants.HORIZONTAL), "grow, span, wrap");
		add(botRightLabel, "span, wrap");
		add(botLatLabel, "span, wrap");
		add(new JLabel("Degrees:"));
		add(botLatDegField, "grow, push");
		add(new JLabel("Minutes:"));
		add(botLatMinField, "grow, push");
		add(new JLabel("Seconds:"));
		add(botLatSecField, "grow, push, wrap");
		add(botLonLabel, "span, wrap");
		add(new JLabel("Degrees:"));
		add(botLonDegField, "grow, push");
		add(new JLabel("Minutes:"));
		add(botLonMinField, "grow, push");
		add(new JLabel("Seconds:"));
		add(botLonSecField, "grow, push, wrap");
		
		add(okButton);
		add(cancelButton);
	}
	
	public class okListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent arg0) {
			int topLatDegNum, topLatMinNum, topLatSecNum, topLonDegNum, topLonMinNum, topLonSecNum;
			int botLatDegNum, botLatMinNum, botLatSecNum, botLonDegNum, botLonMinNum, botLonSecNum;

			String topLatDeg = topLatDegField.getText();
			String topLatMin = topLatMinField.getText();
			String topLatSec = topLatSecField.getText();
			String topLonDeg = topLonDegField.getText();
			String topLonMin = topLonMinField.getText();
			String topLonSec = topLonSecField.getText();
			
			String botLatDeg = botLatDegField.getText();
			String botLatMin = botLatMinField.getText();
			String botLatSec = botLatSecField.getText();
			String botLonDeg = botLonDegField.getText();
			String botLonMin = botLonMinField.getText();
			String botLonSec = botLonSecField.getText();			

			if (isInteger(topLatDeg, "Please enter a numerical value for top left latitude degrees.")) topLatDegNum = Integer.parseInt(topLatDeg);
			else return;
			if (isInteger(topLatMin, "Please enter a numerical value for top left latitude minutes.")) topLatMinNum = Integer.parseInt(topLatMin);
			else return;
			if (isInteger(topLatSec, "Please enter a numerical value for top left latitude seconds.")) topLatSecNum = Integer.parseInt(topLatSec);
			else return;
			if (isInteger(topLonDeg, "Please enter a numerical value for top left longitude degrees.")) topLonDegNum = Integer.parseInt(topLonDeg);
			else return;
			if (isInteger(topLonMin, "Please enter a numerical value for top left longitude minutes.")) topLonMinNum = Integer.parseInt(topLonMin);
			else return;
			if (isInteger(topLonSec, "Please enter a numerical value for top left longitude seconds.")) topLonSecNum = Integer.parseInt(topLonSec);
			else return;
			
			if (isInteger(botLatDeg, "Please enter a numerical value for bottom right latitude degrees.")) botLatDegNum = Integer.parseInt(botLatDeg);
			else return;
			if (isInteger(botLatMin, "Please enter a numerical value for bottom right latitude minutes.")) botLatMinNum = Integer.parseInt(botLatMin);
			else return;
			if (isInteger(botLatSec, "Please enter a numerical value for bottom right latitude seconds.")) botLatSecNum = Integer.parseInt(botLatSec);
			else return;
			if (isInteger(botLonDeg, "Please enter a numerical value for bottom right longitude degrees.")) botLonDegNum = Integer.parseInt(botLonDeg);
			else return;
			if (isInteger(botLonMin, "Please enter a numerical value for bottom right longitude minutes.")) botLonMinNum = Integer.parseInt(botLonMin);
			else return;
			if (isInteger(botLonSec, "Please enter a numerical value for bottom right longitude seconds.")) botLonSecNum = Integer.parseInt(botLonSec);
			else return;
			
			mainWindow.setTopLeftLat(DistanceConverter.convertDMStoDecimal(topLatDegNum, topLatMinNum, topLatSecNum));
			mainWindow.setTopLeftLong(DistanceConverter.convertDMStoDecimal(topLonDegNum, topLonMinNum, topLonSecNum));
			mainWindow.setBotRightLat(DistanceConverter.convertDMStoDecimal(botLatDegNum, botLatMinNum, botLatSecNum));
			mainWindow.setBotRightLong(DistanceConverter.convertDMStoDecimal(botLonDegNum, botLonMinNum, botLonSecNum));
		
			
			mainWindow.setCornersRecieved(true);			
			setVisible(false);
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
	
	}
	
	public class CancelListener implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent arg0) {
			mainWindow.setCornersRecieved(false);
			setVisible(false);
		}
	}

}
