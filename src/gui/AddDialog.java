package gui;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JSeparator;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import net.miginfocom.swing.MigLayout;

public class AddDialog extends JFrame {
	
	private JLabel typeLabel = new JLabel("Type:");
	private JComboBox typeSelect;
	private JLabel nameLabel = new JLabel("Name:");
	private JTextField nameField = new JTextField();
	private JLabel latLabel = new JLabel("Latitude");
	private JLabel lonLabel = new JLabel("Longitude");
	private JTextField latDegField, latMinField, latSecField;
	private JTextField lonDegField, lonMinField, lonSecField;
	private JButton okButton = new JButton("OK");
	private JButton cancelButton = new JButton("Cancel");
	
	private final static String[] types = {"Team", "Marker"};
	private final static String[] teamTypes = {"Hikers", "Dogs", "Helicopter"};
	
	public AddDialog() {
		setTitle("Add an Item");
		setSize(400, 200);
		setLayout(new MigLayout());
		setLocationRelativeTo(null);
		typeSelect = new JComboBox<String>(types);
		
		latDegField = new JTextField();
		latMinField = new JTextField();
		latSecField = new JTextField();
		lonDegField = new JTextField();
		lonMinField = new JTextField();
		lonSecField = new JTextField();
		
		
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
	}
	
}
