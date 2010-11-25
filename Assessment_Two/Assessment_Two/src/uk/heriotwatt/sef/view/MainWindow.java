package uk.heriotwatt.sef.view;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import javax.swing.BoxLayout;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;

import net.miginfocom.swing.MigLayout;
import uk.heriotwatt.sef.model.Cabin;
import uk.heriotwatt.sef.model.CabinNotFoundException;
import uk.heriotwatt.sef.model.Location;
import uk.heriotwatt.sef.model.LocationAlreadyBookedException;
import uk.heriotwatt.sef.model.LocationManager;
import uk.heriotwatt.sef.model.Plot;

public class MainWindow extends JFrame implements ActionListener, Observer {

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
	private JTextField priceTextField;
	private JLabel priceLabel;

	private LocationManager manager;
	private JPanel plotDetails;
	private JPanel cabinDetails;
	private JTextField conditionTextField;
	private JTextField facilitiesTextField;
	private JTextField roomTextField;
	private JTextField ownerTextField;
	private JTextField electricityTextField;
	private JTextField bookingsTextField;

	public MainWindow(LocationManager manager) {
		this.manager = manager;
		this.manager.addObserver(this);
		this.initializeComponents();
		this.displayLocations(manager.getLocations());
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
		JScrollPane scrollPane = new JScrollPane(locationOverviewPanel);
		locationDetails = new JPanel(new MigLayout("", "[][grow][][grow]"));
		this.initializeLocationDetails(locationDetails);
		bookingPanel = new JPanel(new MigLayout());
		this.initializeBookingPanel(bookingPanel);

		panel.add(searchPanel, BorderLayout.NORTH);
		panel.add(scrollPane, BorderLayout.WEST);
		panel.add(locationDetails, BorderLayout.CENTER);
		panel.add(bookingPanel, BorderLayout.SOUTH);

		this.add(panel);
		this.setSize(400, 400);
	}

	private void initializeSearchPanel(JPanel searchPanel2) {
		Border border = new TitledBorder("Search Locations:");
		searchPanel2.setBorder(border);

		GridLayout gl = new GridLayout(0, 3, 5, 0);
		searchPanel2.setLayout(gl);

		searchTextField = new JTextField();
		categoryComboBox = new JComboBox();
		categoryComboBox.addItem("Id");
		categoryComboBox.addItem("Price");
		categoryComboBox.addItem("Area");
		categoryComboBox.addItem("Booked");
		searchButton = new JButton("Search", new ImageIcon(
				"resources/magnifier.png"));
		searchButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				String searchString = searchTextField.getText();
				String category = categoryComboBox.getSelectedItem().toString();
				List<Location> searchedList = manager.searchLocations(searchString, category);
				displayLocations(searchedList);
			}
		});
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
		exitItem.setIcon(new ImageIcon("resources/door_out.png"));
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
		addLocationMenuItem.setIcon(new ImageIcon("resources/map_add.png"));
		JMenu orderMenu = new JMenu("Order by:");
		orderMenu.setIcon(new ImageIcon("resources/sort_ascending.png"));
		JMenuItem orderByIdItem = new JMenuItem("Id");
		orderByIdItem.setIcon(new ImageIcon("resources/key.png"));
		orderByIdItem.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				manager.orderLocations(true);
			}
		});

		JMenuItem orderByCostItem = new JMenuItem("Price");
		orderByCostItem.setIcon(new ImageIcon("resources/money_dollar.png"));
		orderByCostItem.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				manager.orderLocations(false);
			}
		});

		JMenuItem showSummaryItem = new JMenuItem("Show Summary");
		showSummaryItem.setIcon(new ImageIcon("resources/table.png"));
		showSummaryItem.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO: Create a new window containing a JTable.
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
		locationDetails.add(idTextField, "growx");
		JLabel bookingsLabel = new JLabel("Bookings:");
		locationDetails.add(bookingsLabel);
		bookingsTextField = new JTextField();
		locationDetails.add(bookingsTextField, "wrap, growx");
		sizeLabel = new JLabel("Size:");
		locationDetails.add(sizeLabel);
		sizeTextField = new JTextField();
		locationDetails.add(sizeTextField, "growx");
		priceLabel = new JLabel("Price:");
		priceTextField = new JTextField();
		locationDetails.add(priceLabel);
		locationDetails.add(priceTextField, "growx, wrap");

		cabinDetails = new JPanel(new MigLayout("", "[][grow][][grow]"));

		Border cabinBorder = new TitledBorder("Cabin details:");
		cabinDetails.setBorder(cabinBorder);
		Component conditionLabel = new JLabel("Condition:");
		cabinDetails.add(conditionLabel);
		conditionTextField = new JTextField();
		cabinDetails.add(conditionTextField, "growx");
		JLabel facilitiesLabel = new JLabel("Facilities:");
		cabinDetails.add(facilitiesLabel);
		facilitiesTextField = new JTextField();
		cabinDetails.add(facilitiesTextField, "growx, wrap");
		JLabel cabinRoomsLabel = new JLabel("Rooms:");
		cabinDetails.add(cabinRoomsLabel);
		roomTextField = new JTextField();
		cabinDetails.add(roomTextField, "growx");
		JLabel ownerLabel = new JLabel("Owner:");
		cabinDetails.add(ownerLabel);
		ownerTextField = new JTextField();
		cabinDetails.add(ownerTextField, "growx");

		plotDetails = new JPanel(new MigLayout("", "[][grow][][grow]"));
		Border plotBorder = new TitledBorder("Plot details:");
		plotDetails.setBorder(plotBorder);
		JLabel electricityJLabel = new JLabel("Electricity:");
		plotDetails.add(electricityJLabel);
		electricityTextField = new JTextField();
		plotDetails.add(electricityTextField, "growx");

		locationDetails.add(cabinDetails, "grow, wrap, span 4");
		locationDetails.add(plotDetails, "grow, span 4");
	}

	private void initializeBookingPanel(JPanel bookingPanel) {
		Border border = new TitledBorder("Booking:");
		bookingPanel.setBorder(border);

		GridLayout gl = new GridLayout(0, 3, 5, 0);
		bookingPanel.setLayout(gl);

		numberOfNightsTextField = new JTextField();
		locationComboBox = new JComboBox();
		bookButton = new JButton("Book", new ImageIcon("resources/money.png"));
		bookButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				String bookedLocation = locationComboBox.getSelectedItem()
						.toString();
				String numberOfNights = numberOfNightsTextField.getText();
				try {
					int nights = Integer.parseInt(numberOfNights);
					manager.bookLocation(bookedLocation, nights);
				} catch (NumberFormatException e2) {
					e2.printStackTrace();
				} catch (LocationAlreadyBookedException e1) {
					e1.printStackTrace();
				}

			}
		});
		bookingPanel.add(locationComboBox);
		bookingPanel.add(numberOfNightsTextField);
		bookingPanel.add(bookButton);
	}

	private void initializeLocationsPanel(JPanel panel) {
		this.locationButtons = new LinkedList<JButton>();
		panel.setLayout(new BoxLayout(locationOverviewPanel,
				BoxLayout.PAGE_AXIS));
		Border border = new TitledBorder("Locations:");
		panel.setBorder(border);
	}

	public void setLocationBooked(JButton button, boolean isBooked) {
		if (isBooked) {
			Icon bookedIcon = new ImageIcon("resources/flag_red.png");
			button.setIcon(bookedIcon);
		} else {
			Icon freeIcon = new ImageIcon("resources/flag_green.png");
			button.setIcon(freeIcon);
		}
	}

	private void displayLocations(List<Location> locations) {
		locationButtons.clear();
		this.locationOverviewPanel.removeAll();
		for (Location location : locations) {
			JButton button = new JButton(location.getId());
			this.setLocationBooked(button, location.isBooked());
			button.addActionListener(this);
			locationButtons.add(button);
			this.locationOverviewPanel.add(button);

			this.locationComboBox.addItem(location.getId());
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Object source = e.getSource();
		if (source instanceof JButton) {
			JButton button = (JButton) source;
			String id = button.getText();
			this.displayLocation(id);
		}

	}

	private void displayLocation(String id) {
		Location location;
		try {
			location = manager.findLocationById(id);
			this.idTextField.setText(location.getId());
			this.bookingsTextField.setText(String.valueOf(location.getBookings()));
			String cost = String.valueOf(location.getCost());
			this.priceTextField.setText(cost);
			String size = String.valueOf(location.getSize());
			this.sizeTextField.setText(size);
			if (location instanceof Cabin){
				Cabin cab = (Cabin) location;
				this.facilitiesTextField.setText(cab.getFacilities().toString());
				this.conditionTextField.setText(cab.getCondition().toString());
				this.ownerTextField.setText(cab.getOwner().getInitPeriodLast());
				this.roomTextField.setText(String.valueOf(cab.getBeds()));
				this.clearPlotFields();
			}
			else {
				Plot plot = (Plot) location;
				this.electricityTextField.setText(String.valueOf(plot.isHasElectricity()));
				this.clearCabinFields();
			}
		} catch (CabinNotFoundException e) {
			e.printStackTrace();
		}
	}

	private void clearPlotFields() {
		this.electricityTextField.setText("");		
	}

	private void clearCabinFields() {
		this.facilitiesTextField.setText("");
		this.conditionTextField.setText("");
		this.ownerTextField.setText("");
		this.roomTextField.setText("");
	}

	@Override
	public void update(Observable o, Object arg) {
		this.reloadData();
	}

	private void reloadData() {
		this.displayLocations(this.manager.getLocations());
	}
}
