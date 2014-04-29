package gui;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.border.EtchedBorder;

import teams.SearchTeam;
import net.miginfocom.swing.MigLayout;

public class TeamAndMarkerDisplay extends JPanel {
	private static final long serialVersionUID = 1L;

	private JButton addButton, removeButton, updateButton;

	private JTabbedPane tabs;

	private DisplayTable teamTable, markerTable;

	private final static String[] TEAM_HEADERS = {"Name", "Type", "Latitude", "Longitude", "Heading", "Speed", "Time Created"};
	private final static String[] MARKER_HEADERS = {"Name", "Latitude", "Longitude", "Time Created"};
	private final static int DEFAULT_TABLE_ROWS = 0;

	//creates tabbed pane on the right side to contain the tables displaying marker and team information
	public TeamAndMarkerDisplay() {
		this.setLayout(new MigLayout("wrap 3"));
		this.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED));
		addButton = new JButton("Add");
		removeButton = new JButton("Remove");
		updateButton = new JButton("Update");

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

	//getAddButton
	public JButton getAddButton() {
		return addButton;
	}

	//gets the remove button
	public JButton getRemoveButton() {
		return removeButton;
	}

	//returns the update button.
	public JButton getUpdateButton() {
		return updateButton;
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
	
	public boolean teamTabIsSelected() {
		if (tabs.getSelectedIndex() == 0) {
			return true;
		} else {
			return false;
		}
	}
	
	public boolean markerTabIsSelected() {
		if (tabs.getSelectedIndex() == 1) {
			return true;
		} else {
			return false;
		}
	}

}
