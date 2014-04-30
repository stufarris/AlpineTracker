package gui;

import java.util.ArrayList;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import net.miginfocom.swing.MigLayout;

import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import teams.Marker;
import teams.SearchTeam;

public class DisplayTable extends JPanel {

	private static final long serialVersionUID = 1L;

	private DefaultTableModel model;
	JTable table;
	String[] columnNames;
	DateTimeFormatter clockTime = DateTimeFormat.forPattern("hh:mma");


	//this constructor is for creating the tables on the right side of the screen
	//where information can be viewed about the search teams and markers
	public DisplayTable(String[] columnNames, int numOfRows) {

		this.columnNames = columnNames;

		this.setLayout(new MigLayout());

		model = new DefaultTableModel(columnNames, numOfRows);
		table = new JTable(model) {
			private static final long serialVersionUID = 1L;
			@Override
			public boolean isCellEditable(int arg0, int arg1) {
				return false;
			}
		};
		table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		JScrollPane scrollPane = new JScrollPane(table);
		add(scrollPane);
	}

	//sets information for each team and marker in the table
	public void setTableData(ArrayList<SearchTeam> teams) {
		while (model.getRowCount() != teams.size()) {
			if (model.getRowCount() > teams.size()) {
				model.removeRow(model.getRowCount()-1);
			}
			if (model.getRowCount() < teams.size()) {
				model.addRow(new Object[columnNames.length]);
			}
		}
		for (int i = 0; i < teams.size(); i++) {
			
			String latitude = "";
			String longitude = "";
			
			int[] latitudeInt = DistanceConverter.getDMSfromDecimal(teams.get(i).getLatitude());
			int[] longitudeInt = DistanceConverter.getDMSfromDecimal(teams.get(i).getLongitude());
			
			latitude += latitudeInt[0];
			latitude += "º";
			latitude += latitudeInt[1];
			latitude += "'";
			latitude += latitudeInt[2];
			latitude += "\"";
			
			longitude += longitudeInt[0];
			longitude += "º";
			longitude += longitudeInt[1];
			longitude += "'";
			longitude += longitudeInt[2];
			longitude += "\"";
			
			table.setValueAt(teams.get(i).getTeamName(), i, 0);
			table.setValueAt(teams.get(i).getType().toString(), i, 1);
			table.setValueAt(latitude, i, 2);
			table.setValueAt(longitude, i, 3);
			table.setValueAt(teams.get(i).getHeading(), i, 4);
			table.setValueAt(teams.get(i).getSpeed(), i, 5);
			table.setValueAt(clockTime.print(teams.get(i).getTimeCreated()), i, 6);
		}
	}
	//getter for rows
	public void setMarkerTableData(ArrayList<Marker> markers) {
		while (model.getRowCount() != markers.size()) {
			if (model.getRowCount() > markers.size()) {
				model.removeRow(model.getRowCount()-1);
			}
			if (model.getRowCount() < markers.size()) {
				model.addRow(new Object[columnNames.length]);
			}
		}
		for (int i = 0; i < markers.size(); i++) {
			table.setValueAt(markers.get(i).getMarkerName(), i, 0);
			table.setValueAt(markers.get(i).getLatitude(), i, 1);
			table.setValueAt(markers.get(i).getLongitude(), i, 2);
			table.setValueAt(clockTime.print(markers.get(i).getTimeCreated()), i,3);
		}
	}
	
	public int getSelectedRow() {
		return table.getSelectedRow();
	}

}
