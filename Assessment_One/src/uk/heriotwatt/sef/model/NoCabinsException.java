package uk.heriotwatt.sef.model;

/**
 * Shows that no cabins are currently loaded.
 * 
 * @author florian
 *
 */
public class NoCabinsException extends Exception {

	/**
	 * Generated serialVersionUID to allow serialisation.
	 */
	private static final long serialVersionUID = 2274177224545932291L;

	public NoCabinsException(String msg) {
		super(msg);
	}
}
