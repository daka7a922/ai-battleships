package model;

import java.util.HashMap;

/**
 * the model representation of the settings.
 * 
 * @author Jakob
 *
 */
public class Settings {
	
	/** the number of ships of different lengths. */
	private HashMap<Integer, Integer> shipNumbers;
	
	/** the number of the player type (0 = random, 1 = medium, 2 = AI). */
	private int player;
	
	/**
	 * constructor.
	 */
	public Settings() {
		this.shipNumbers = new HashMap<Integer, Integer>();
	}
	
	/**
	 * getter for the ship numbers.
	 * 
	 * @return the number of ships.
	 */
	public HashMap<Integer, Integer> getShipNumbers() {
		return this.shipNumbers;
	}
	
	/**
	 * the setter for ship numbers.
	 * 
	 * @param shipNumbers the new hashmap for the field shipNumbers.
	 */
	public void setShipNumbers(HashMap<Integer, Integer> shipNumbers) {
		this.shipNumbers = shipNumbers;
	}

	/**
	 * the getter for the player type.
	 * 
	 * @return number of playertype.
	 */
	public int getPlayer() {
		return this.player;
	}
	
	/**
	 * the setter for the player type.
	 * 
	 * @param player the new player type number.
	 */
	public void setPlayer(int player) {
		this.player = player;
	}
}
