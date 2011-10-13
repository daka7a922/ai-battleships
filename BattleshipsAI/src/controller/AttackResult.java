package controller;

/**
 * Contains all information a player needs to know about
 * his last attack. Is sent from the controller to the
 * currently active player.
 * 
 * @author Jakob
 *
 */
public class AttackResult {
	
	/** true if the last attack resulted in a hit, false otherwise. */
	private boolean hit;
	
	/** true if the last attack resulted in a sunk ship, false otherwise. */
	private boolean sunk;
	
	/** if a ship was sunk, than this field contains the length of the ship. */
	private int shipLength;
	
	
	/**
	 * constructor. 
	 * 
	 * @param hit the value for the hit field.
	 * @param sunk the value for the sunk field.
	 * @param shipLength the value for the shipLength field.
	 */
	public AttackResult(boolean hit, boolean sunk, int shipLength) {
		this.hit = hit;
		this.sunk = sunk;
		this.shipLength = shipLength;
	}

	/**
	 * getter for hit.
	 * 
	 * @return the hit.
	 */
	public boolean isHit() {
		return hit;
	}

	/**
	 * getter for sunk.
	 * 
	 * @return the sunk.
	 */
	public boolean isSunk() {
		return sunk;
	}
	
	/**
	 * getter for shipLength.
	 * 
	 * @return the shipLength.
	 */
	public int getShipLength() {
		return this.shipLength;
	}
}
