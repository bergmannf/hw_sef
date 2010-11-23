package uk.heriotwatt.sef.view;

import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JTree;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;

import net.miginfocom.swing.MigLayout;

public class MainWindow extends JFrame {

	private JPanel bookingPanel;
	private JPanel locationOverviewPanel;
	private JPanel locationDetails;
	private JLabel idLabel;
	private JTextField idTextField;
	private JLabel sizeLabel;
	private JTextField sizeTextField;
	private JTree locationsTree;
	private JMenuBar menuBar;
	private JMenu menu;

	public MainWindow()
	{
		this.initializeComponents();
	}

	private void initializeComponents() {
		initializeMenuBar();
		
		JPanel panel = new JPanel();
		panel.setLayout(new BorderLayout());
		locationOverviewPanel = new JPanel();
		this.initializeLocationTree(locationOverviewPanel);
		locationDetails = new JPanel(new MigLayout());
		this.initializeLocationDetails(locationDetails);
		bookingPanel = new JPanel(new MigLayout());
		this.initializeBookingPanel(bookingPanel);
		
		panel.add(locationOverviewPanel, BorderLayout.WEST);
		panel.add(locationDetails, BorderLayout.CENTER);
		panel.add(bookingPanel, BorderLayout.SOUTH);
		
		this.add(panel);
		this.setSize(400, 400);
	}

	private void initializeMenuBar() {
		menuBar = new JMenuBar();
		menu = new JMenu("File");
		menuBar.add(menu);
		this.setJMenuBar(menuBar);
	}

	private void initializeLocationDetails(JPanel locationDetails) {
		Border border = new TitledBorder("Location details:");
		locationDetails.setBorder(border);
		idLabel = new JLabel("Id:");
		locationDetails.add(idLabel);
		idTextField = new JTextField();
		locationDetails.add(idTextField, "wrap");
		sizeLabel = new JLabel("Size:");
		locationDetails.add(sizeLabel);
		sizeTextField = new JTextField();
		locationDetails.add(sizeTextField, "wrap");
	}

	private void initializeBookingPanel(JPanel bookingPanel) {
		Border border = new TitledBorder("Booking:");
		bookingPanel.setBorder(border);
		
	}

	private void initializeLocationTree(JPanel panel) {
		Border border = new TitledBorder("Locations:");
		panel.setBorder(border);
		locationsTree = new JTree();
		panel.add(locationsTree);
		
	}
}
