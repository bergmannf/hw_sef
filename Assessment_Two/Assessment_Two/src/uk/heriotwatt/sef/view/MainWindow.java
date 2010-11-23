package uk.heriotwatt.sef.view;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedList;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;

import net.miginfocom.swing.MigLayout;

public class MainWindow extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2899688509417738813L;
	private JPanel bookingPanel;
	private JPanel locationOverviewPanel;
	private JPanel locationDetails;
	private JLabel idLabel;
	private JTextField idTextField;
	private JLabel sizeLabel;
	private JTextField sizeTextField;
	private JMenuBar menuBar;
	private JMenu fileMenu;
	
	private List<JButton> locationButtons;
	private JPanel searchPanel;
	private JTextField searchTextField;
	private JComboBox categoryComboBox;
	private JButton searchButton;
	private JTextField numberOfNightsTextField;
	private JComboBox locationComboBox;
	private JButton bookButton;

	public MainWindow()
	{
		this.initializeComponents();
	}

	private void initializeComponents() {
		initializeMenuBar();
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		JPanel panel = new JPanel();
		panel.setLayout(new BorderLayout());
		searchPanel = new JPanel();
		this.initializeSearchPanel(searchPanel);
		locationOverviewPanel = new JPanel();
		this.initializeLocationsPanel(locationOverviewPanel);
		locationDetails = new JPanel(new MigLayout());
		this.initializeLocationDetails(locationDetails);
		bookingPanel = new JPanel(new MigLayout());
		this.initializeBookingPanel(bookingPanel);
		
		panel.add(searchPanel, BorderLayout.NORTH);
		panel.add(locationOverviewPanel, BorderLayout.WEST);
		panel.add(locationDetails, BorderLayout.CENTER);
		panel.add(bookingPanel, BorderLayout.SOUTH);
		
		this.add(panel);
		this.setSize(400, 400);
	}

	private void initializeSearchPanel(JPanel searchPanel2) {
		Border border = new TitledBorder("Search Locations:");
		searchPanel2.setBorder(border);
		
		GridLayout gl = new GridLayout(0,3,5,0);
		searchPanel2.setLayout(gl);
		
		searchTextField = new JTextField();
		categoryComboBox = new JComboBox();
		searchButton = new JButton("Search", new ImageIcon(getClass().getResource("../resources/magnifier.png")));
		searchPanel2.add(searchTextField);
		searchPanel2.add(categoryComboBox);
		searchPanel2.add(searchButton);
	}

	private void initializeMenuBar() {
		menuBar = new JMenuBar();
		
		/*
		 * Creating the file submenu.
		 */
		fileMenu = new JMenu("File");
		JMenuItem exitItem = new JMenuItem("Close");
		exitItem.setIcon(new ImageIcon(getClass().getResource("../resources/door_out.png")));
		exitItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				System.exit(NORMAL);
			}
		});
		
		fileMenu.add(exitItem);
		
		/*
		 * Creating the edit submenu.
		 */
		JMenu editMenu = new JMenu("Edit");
		JMenuItem addLocationMenuItem = new JMenuItem("Add Location");
		addLocationMenuItem.setIcon(new ImageIcon(getClass().getResource("../resources/map_add.png")));
		JMenu orderMenu = new JMenu("Order by:");
		orderMenu.setIcon(new ImageIcon(getClass().getResource("../resources/sort_ascending.png")));
		JMenuItem orderByIdItem = new JMenuItem("Id");
		orderByIdItem.setIcon(new ImageIcon(getClass().getResource("../resources/key.png")));
		orderByIdItem.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				
			}
		});
		
		JMenuItem orderByCostItem = new JMenuItem("Price");
		orderByCostItem.setIcon(new ImageIcon(getClass().getResource("../resources/money_dollar.png")));
		orderByCostItem.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				
			}
		});
		
		JMenuItem showSummaryItem = new JMenuItem("Show Summary");
		showSummaryItem.setIcon(new ImageIcon(getClass().getResource("../resources/table.png")));
		showSummaryItem.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				
			}
		});
		
		editMenu.add(addLocationMenuItem);
		orderMenu.add(orderByIdItem);
		orderMenu.add(orderByCostItem);
		editMenu.add(orderMenu);
		editMenu.add(showSummaryItem);
		
		menuBar.add(fileMenu);
		menuBar.add(editMenu);
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
		
		GridLayout gl = new GridLayout(0,3,5,0);
		bookingPanel.setLayout(gl);
		
		numberOfNightsTextField = new JTextField();
		locationComboBox = new JComboBox();
		bookButton = new JButton("Book", new ImageIcon(getClass().getResource("../resources/money.png")));
		bookingPanel.add(locationComboBox);
		bookingPanel.add(numberOfNightsTextField);
		bookingPanel.add(bookButton);
	}

	private void initializeLocationsPanel(JPanel panel) {
		// TODO: Use a JScrollpane.
		this.locationButtons = new LinkedList<JButton>();
		panel.setLayout(new BoxLayout(locationOverviewPanel, BoxLayout.PAGE_AXIS));
		Border border = new TitledBorder("Locations:");
		panel.setBorder(border);
		panel.add(new JButton("C1"));
		panel.add(new JButton("C2"));
	}
	
	public void setLocationBooked(boolean isBooked)
	{
		
	}
}
