package gui;

import java.util.ArrayList;

import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.table.TableColumn;

public class InfoBar extends JTabbedPane{
	private static final long serialVersionUID = 1L;

	InfoBar(){
		String[] columnNames = {"First Name",
				"Last Name",
				"Sport",
				"# of Years",
		"Vegetarian"};

		Object[][] data = {{"Mary", "Campione","Snowboarding", new Integer(5), new Boolean(false)},
							{"Alison", "Huml","Rowing", new Integer(3), new Boolean(true)},
							{"Kathy", "Walrath","Knitting", new Integer(2), new Boolean(false)},
							{"Sharon", "Zakhour","Speed reading", new Integer(20), new Boolean(true)},
							{"Philip", "Milne","Pool", new Integer(10), new Boolean(false)}};

		JTable markerTable = new JTable(data, columnNames);
		JScrollPane scrollPane = new JScrollPane(markerTable);
		this.addTab("Markers", markerTable);
		this.addTab("Teams", new TeamTable());
	}

	public class MarkerTable extends JTable{
		private static final long serialVersionUID = 1L;
		MarkerTable(){
			
		}
	}

	public class TeamTable extends JTable{
		private static final long serialVersionUID = 1L;
		TeamTable(){
			TableColumn nameColumn = new TableColumn();
		}
	}
}
