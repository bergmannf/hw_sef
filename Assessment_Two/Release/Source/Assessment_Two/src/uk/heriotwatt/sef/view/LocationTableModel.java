package uk.heriotwatt.sef.view;

import java.util.Arrays;
import java.util.List;
import java.util.Vector;

import javax.swing.table.AbstractTableModel;

import uk.heriotwatt.sef.model.Cabin;
import uk.heriotwatt.sef.model.Location;
import uk.heriotwatt.sef.model.Plot;

public class LocationTableModel extends AbstractTableModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8059706006230392340L;
	private Vector<String> columnNames;
	private Vector<Location> columns;

	public LocationTableModel(List<Location> columns) {
		String[] columnName = { "Id", "Size", "Booked", "Price", "Condition",
				"Facilities", "Rooms", "Owner", "Electricity" };
		this.columnNames = new Vector<String>();
		for (String string : columnName) {
			this.columnNames.add(string);
		}
		this.columns = new Vector<Location>();
		for (Location location : columns) {
			this.columns.add(location);
		}
	}

	@Override
	public int getColumnCount() {
		return this.columnNames.size();
	}

	public String getColumnName(int col) {
		return columnNames.get(col).toString();
	}

	@Override
	public int getRowCount() {
		return this.columns.size();
	}

	@Override
	public Object getValueAt(int arg0, int arg1) {
		Location loc = columns.get(arg0);
		if (arg1 < 4) {
			switch (arg1) {
			case 0:
				return loc.getId();
			case 1:
				return loc.getSize();
			case 2:
				return loc.isBooked();
			case 3:
				return loc.getCost();
			default:
				break;
			}
		} else if (arg1 < 8) {
			if (loc instanceof Cabin) {
				Cabin cab = (Cabin) loc;
				switch (arg1) {
				case 4:
					return cab.getCondition().toString();
				case 5:
					return cab.getFacilities().toString();
				case 6:
					return cab.getBeds();
				case 7:
					return cab.getOwner().getInitPeriodLast();
				default:
					break;
				}
			} else {
				return "--";
			}
		} else {
			if (loc instanceof Plot) {
				Plot plot = (Plot) loc;
				switch (arg1) {
				case 8:
					return plot.isHasElectricity();
				default:
					break;
				}
			} else {
				return "--";
			}
		}
		return "--";
	}

	@Override
	public Class getColumnClass(int c) {
		return getValueAt(0, c).getClass();
	}

}
