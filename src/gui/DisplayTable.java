package gui;

import java.util.ArrayList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import net.miginfocom.swing.MigLayout;

import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

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
			table.setValueAt(teams.get(i).getTeamName(), i, 0);
			table.setValueAt(teams.get(i).getType().toString(), i, 1);
			table.setValueAt(teams.get(i).getLatitude(), i, 2);
			table.setValueAt(teams.get(i).getLongitude(), i, 3);
			table.setValueAt(teams.get(i).getHeading(), i, 4);
			table.setValueAt(teams.get(i).getSpeed(), i, 5);
			table.setValueAt(clockTime.print(teams.get(i).getTimeCreated()), i, 6);
		}
	}

	//getter for rows
	public int getSelectedRow() {
		return table.getSelectedRow();
	}

}
