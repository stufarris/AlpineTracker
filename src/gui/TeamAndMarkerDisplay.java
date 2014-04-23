package gui;

import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.border.EtchedBorder;

import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import teams.SearchTeam;
import net.miginfocom.swing.MigLayout;

public class TeamAndMarkerDisplay extends JPanel {
	private static final long serialVersionUID = 1L;
	
	// TODO: This panel needs to hold three buttons and a JTabbedPane
	
	private JButton addButton, removeButton, updateButton;
	
	private JTabbedPane tabs;

	public TeamAndMarkerDisplay() {
		this.setLayout(new MigLayout("wrap 3"));
		this.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED));
		addButton = new JButton("Add");
		removeButton = new JButton("Remove");
		updateButton = new JButton("Update");
		
		tabs = new JTabbedPane();
		tabs.addTab("Teams", new JPanel()); // Adds an empty JPanel, this panel will display teams eventually
		tabs.addTab("Markers", new JPanel());
		
		this.add(addButton, "aligny top");
		this.add(removeButton, "aligny top");
		this.add(updateButton, "aligny top");
		this.add(tabs, "grow, span, aligny top");
	}
	
	public void update(ArrayList<SearchTeam> teams) {
		tabs.removeAll();
		tabs.add("Teams", new TeamDisplayPanel(teams));
		tabs.revalidate();
	}
	
	private class TeamDisplayPanel extends JPanel {

		private static final long serialVersionUID = 4763584397474866932L;

		String[] columnNames = {"Name", "Type", "Latitude", "Longitude", "Heading", "Speed", "Time Entered"};
		
		Object[][] data;
		
		JTable table;
		
		DateTimeFormatter clockTime = DateTimeFormat.forPattern("hh:mma");
		
		public TeamDisplayPanel(ArrayList<SearchTeam> teams) {
			data = new Object[teams.size()][columnNames.length];
			for (int i = 0; i < teams.size(); i++) {
				data[i][0] = teams.get(i).getTeamName();
				data[i][1] = teams.get(i).getType().toString();
				data[i][2] = teams.get(i).getLatitude();
				data[i][3] = teams.get(i).getLongitude();
				data[i][4] = teams.get(i).getHeading();
				data[i][5] = teams.get(i).getSpeed();
				data[i][6] = clockTime.print(teams.get(i).getTimeCreated());
			}
			table = new JTable(data, columnNames);
			JScrollPane scroller = new JScrollPane(table);
			add(scroller);
		}
	}
}
