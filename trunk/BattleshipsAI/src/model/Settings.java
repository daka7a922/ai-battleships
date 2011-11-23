package model;

import java.util.HashMap;

import ai.IPlayer;

/**
 * the model representation of the settings.
 * 
 * @author Jakob
 *
 */
public class Settings {
	
	/** the number of ships of different lengths. */
	private HashMap<Integer, Integer> shipNumbers;
	
	private Class<? extends IPlayer> playerClass;
	
	/**
	 * constructor.
	 */
	public Settings() {
		this.shipNumbers = new HashMap<Integer, Integer>();
	}

	/**
	 * constructor.
	 */
	public Settings(Class<? extends IPlayer> playerClass, HashMap<Integer, Integer> shipNumbers) {
		this.playerClass = playerClass;
		this.shipNumbers = shipNumbers;
		
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
	public Class<? extends IPlayer> getPlayerClass() {
		return this.playerClass;
	}
	
	/**
	 * the setter for the player type.
	 * 
	 * @param player the new player type number.
	 */
	public void setPlayerClass(Class<? extends IPlayer> playerClass) {
		this.playerClass = playerClass;
	}
	
	/** Generates number of fields occupied by ships */
	public int getShipFieldNumber() {
		int result = 0;
		for(int i : this.shipNumbers.keySet()) {
			result = result + (i * this.shipNumbers.get(i));
		}
		return result;
	}

	/** Generates number ships */
	public int getShipCount() {
		int result = 0;
		for(int i : this.shipNumbers.keySet()) {
			result = result + this.shipNumbers.get(i);
		}
		return result;
	}
	
	@Override
	public boolean equals(Object o) {
		if (playerClass.equals(((Settings)o).playerClass) && shipNumbers.equals(((Settings)o).shipNumbers)) {
			return true;
		}
		return false;
	}
	
	@Override
    public int hashCode() {
        return (playerClass + this.shipNumbers.toString()).hashCode();
    }
	
	@Override
	public String toString() {
		return "{Player=" + this.playerClass + ", ShipNumbers="+ this.shipNumbers + "}";
	}
}
