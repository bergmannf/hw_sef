package uk.heriotwatt.sef.model;

/**
 * Exception to clarify the missing of a cabin.
 * 
 * @author florian
 *
 */
public class CabinNotFoundException extends Exception {

	/**
	 * Generated serialVersionUID to allow serialisation.
	 */
	private static final long serialVersionUID = -7740644730079198039L;

	public CabinNotFoundException(String msg) {
		super(msg);
	}
}
