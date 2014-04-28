package gui;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JSeparator;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import net.miginfocom.swing.MigLayout;

public class MapInitDialog extends JFrame{
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
	
	public MapInitDialog() {
		setTitle("Map Initialization");
		setSize(400,300);
		setLayout(new MigLayout());
		
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
	}

}
