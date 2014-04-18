package gui;

import java.util.ArrayList;

import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.table.TableColumn;

public class InfoBar extends JTabbedPane{
	private static final long serialVersionUID = 1L;

	InfoBar(){
		this.addTab("Markers", new MarkerTable());
		this.addTab("Teams", new TeamTable());
	}
	
	public class MarkerTable extends JTable{
		private static final long serialVersionUID = 1L;
		MarkerTable(){
			String[] markers = new String [3];
			markers[0] = "hello";
			markers[1] = "espen";
			markers[2] = "test";
			TableColumn test = new TableColumn();
			test.setIdentifier("test");
			this.addColumn(test);
		}
	}
	
	public class TeamTable extends JTable{
		private static final long serialVersionUID = 1L;
		TeamTable(){
			
		}
	}
}
