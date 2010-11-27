/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package uk.heriotwatt.sef.view;

import java.awt.BorderLayout;
import java.util.List;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;
import uk.heriotwatt.sef.model.Location;

/**
 *
 * @author Florian Bergmann
 */
public class OverviewWindow extends JFrame {

	private JTable table;
	private JScrollPane scrollPane;

	public OverviewWindow(List<Location> list) {
		this.initializeComponents(list);
	}

	private void initializeComponents(List<Location> list) {
		JPanel panel = new JPanel(new BorderLayout());
		this.initializeOverviewPanel(panel, list);
		this.add(panel);
		this.setSize(400, 400);
	}

	private void initializeOverviewPanel(JPanel panel, List<Location> list) {
		Border border = new TitledBorder("Overview:");
		panel.setBorder(border);
		table = new JTable(new LocationTableModel(list));
		scrollPane = new JScrollPane(table);
		table.setFillsViewportHeight(true);
		
		panel.add(scrollPane, BorderLayout.CENTER);
	}
}
