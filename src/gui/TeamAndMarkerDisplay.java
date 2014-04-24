package gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.border.EtchedBorder;

import teams.SearchTeam;
import net.miginfocom.swing.MigLayout;

public class TeamAndMarkerDisplay extends JPanel {
	private static final long serialVersionUID = 1L;
	
	// TODO: This panel needs to hold three buttons and a JTabbedPane
	
	

	private JButton addButton, removeButton, updateButton;
	
	private AddTeamDialog addTeamDialog;
	
	private JTabbedPane tabs;
	
	private DisplayTable teamTable, markerTable;
	
	private final static String[] TEAM_HEADERS = {"Name", "Type", "Latitude", "Longitude", "Heading", "Speed", "Time Created"};
	private final static String[] MARKER_HEADERS = {"Name", "Latitude", "Longitude", "Time Created"};
	private final static int DEFAULT_TABLE_ROWS = 0;

	public TeamAndMarkerDisplay() {
		this.setLayout(new MigLayout("wrap 3"));
		this.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED));
		addButton = new JButton("Add");
		addButton.addActionListener(new ButtonListener());
		removeButton = new JButton("Remove");
		removeButton.addActionListener(new ButtonListener());
		updateButton = new JButton("Update");
		updateButton.addActionListener(new ButtonListener());
		
		teamTable = new DisplayTable(TEAM_HEADERS, DEFAULT_TABLE_ROWS);
		markerTable = new DisplayTable(MARKER_HEADERS, DEFAULT_TABLE_ROWS);
		
		tabs = new JTabbedPane();
		tabs.addTab("Teams", teamTable);
		tabs.addTab("Markers", markerTable);
		
		this.add(addButton, "aligny top");
		this.add(removeButton, "aligny top");
		this.add(updateButton, "aligny top");
		this.add(tabs, "grow, span, aligny top, height :95%:");
		
	}
	
	public void updateTeamTable(ArrayList<SearchTeam> teams) {
		teamTable.setTableData(teams);
	}
	
	public int getSelectedTeamIndex() {
		return teamTable.getSelectedRow();
	}
	
	public int getSelectedMarkerIndex() {
		return markerTable.getSelectedRow();
	}
	
	class ButtonListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			if(e.getSource() == addButton){
				addTeamDialog = new AddTeamDialog();
				addTeamDialog.setVisible(true);
			}			
		}		
	}

	
}
