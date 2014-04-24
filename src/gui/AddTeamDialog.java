package gui;

import gui.AddDialog.Panel;
import gui.AddDialog.TypePanel;

import java.awt.GridLayout;

public class AddTeamDialog extends AddDialog{

	public AddTeamDialog() {
		setTitle("Add Search Team");
		setSize(400,300);
		setLayout(new GridLayout(6,1));
		this.add(new Panel("Name"));
		this.add(new TypePanel());
		this.add(new Panel("Latitude"));
		this.add(new Panel("Longitute"));
		this.add(new Panel("Heading"));
		this.add(new Panel("Speed"));
		
		this.add(new ButtonPanel());
	}

}
