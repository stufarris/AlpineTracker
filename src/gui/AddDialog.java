package gui;

import java.awt.Color;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

import teams.SearchTeam.TeamType;

public class AddDialog extends JFrame{

	private String name;
	private double latitude;
	private double longitude;
	private int heading;
	private TeamType type;
	
	public AddDialog(){
	}
	
	protected class Panel extends JPanel{
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

	protected class TypePanel extends JPanel{
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
	
	protected class ButtonPanel extends JPanel{
		private JButton addButton;
		private JButton cancelButton;
		
		public ButtonPanel(){
			this.setLayout(new GridLayout(1,2));
			
			addButton = new JButton("Add");
			cancelButton = new JButton("Cancel");
			
			this.add(addButton);
			this.add(cancelButton);
		}

	}
	public void populateComboBox(JComboBox<String> box){
		
	}
}
