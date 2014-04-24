package gui;

import java.awt.Color;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

import teams.SearchTeam.TeamType;

public class AddTeamDialog extends JFrame{

	private String name;
	private double latitude;
	private double longitude;
	private int heading;
	private TeamType type;

	public AddTeamDialog(){
		setTitle("Add Search Team");
		setSize(400,300);
		setLayout(new GridLayout(6,1));
		this.add(new Panel("Name"));
		this.add(new TypePanel());
		this.add(new Panel("Latitude"));
		this.add(new Panel("Longitute"));
		this.add(new Panel("Heading"));
		this.add(new Panel("Speed"));
	}
	
	private class Panel extends JPanel{
		JLabel label;
		private JTextField textField;
		
		public Panel(String labelTitle){
			setLayout(new GridLayout(1,2));
			this.setBorder(BorderFactory.createLineBorder(Color.black));
			
			label = new JLabel(labelTitle);
			textField = new JTextField();
			
			this.add(label);
			this.add(textField);
		}
	}

	private class TypePanel extends JPanel{
		JLabel label;
		private JComboBox<String> comboBox;
		
		public TypePanel(){
			setLayout(new GridLayout(1,2));
			this.setBorder(BorderFactory.createLineBorder(Color.black));
			
			label = new JLabel("Type");
			comboBox = new JComboBox<String>();
			
			this.add(label);
			this.add(comboBox);
		}
	}
	
	public void populateComboBox(JComboBox<String> box){
		
	}
}
