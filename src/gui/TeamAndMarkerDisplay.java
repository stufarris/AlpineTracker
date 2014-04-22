package gui;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.border.EtchedBorder;
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
}
