package uk.heriotwatt.sef.model;

public abstract class Location {

	protected String id;
	protected boolean isBooked;
	protected int bookings;
	protected double size;
	
	public Location(String number, double size, int bookings2) {
		super();
		this.id = number;
		this.size = size;
		this.bookings = bookings2;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public boolean isBooked() {
		return isBooked;
	}

	public void setBooked(boolean isBooked) {
		this.isBooked = isBooked;
	}

	public int getBookings() {
		return bookings;
	}

	public void setBookings(int bookings) {
		this.bookings = bookings;
	}

	public double getSize() {
		return size;
	}

	public void setSize(double size) {
		this.size = size;
	}

	public abstract double getCost();
	
	public String toString()
	{
		String string = String.format("Number: %s.\nBookings: %d.\nBooked: %b\nSize: %d", this.id, this.bookings, this.isBooked, this.size);
		return string;
	}
}
