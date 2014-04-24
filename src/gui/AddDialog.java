package gui;

import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

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
	private JLabel searchTeamLabel = new JLabel("Search Team:");
	private JComboBox searchTeamSelect;
	private JLabel headingLabel = new JLabel("Heading:");
	private JTextField headingField;
	private JLabel speedLabel = new JLabel("Speed:");
	private JTextField speedField;
	private JButton okButton = new JButton("OK");
	private JButton cancelButton = new JButton("Cancel");
	
	private final static String[] types = {"Team", "Marker"};
	private final static String[] teamTypes = {"Hikers", "Dogs", "Helicopter"};
	
	public AddDialog() {
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
		
		cancelButton.addMouseListener(new ClickListener());
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
	
	public class ClickListener implements MouseListener{

		@Override
		public void mouseClicked(MouseEvent arg0) {
			// TODO Auto-generated method stub
			setVisible(false);
		}

		@Override
		public void mouseEntered(MouseEvent arg0) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseExited(MouseEvent arg0) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mousePressed(MouseEvent arg0) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseReleased(MouseEvent arg0) {
			// TODO Auto-generated method stub
			
		}
		
	}
	
}
