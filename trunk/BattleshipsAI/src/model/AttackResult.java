package model;

/**
 * Contains all information a player needs to know about
 * his last attack. Is sent from the controller to the
 * currently active player.
 * 
 * @author Jakob
 *
 */
public class AttackResult {
	
	/** if a ship was sunk, than this field contains the length of the ship. */
	private int shipLength;
	
	private int result;
	
	
	/**
	 * constructor. 
	 * 
	 * @param hit the value for the hit field.
	 * @param sunk the value for the sunk field.
	 * @param shipLength the value for the shipLength field.
	 */
	public AttackResult(int result, int shipLength) {
		this.shipLength = shipLength;
		this.result = result;
	}
	
	/**
	 * getter for shipLength.
	 * 
	 * @return the shipLength.
	 */
	public int getShipLength() {
		return this.shipLength;
	}

	/**
	 * getter result.
	 * 
	 * @return the result.
	 */
	public int getResult() {
		return this.result;
	}
	
	
}
