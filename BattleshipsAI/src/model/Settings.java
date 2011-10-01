package model;

import java.util.HashMap;

public class Settings {
	
	private HashMap<Integer, Integer> shipNumbers;
	
	public Settings() {
		this.shipNumbers = new HashMap<Integer, Integer>();
		this.shipNumbers.put(5, 1);
		this.shipNumbers.put(4, 2);
		this.shipNumbers.put(3, 1);
		this.shipNumbers.put(2, 2);
	}
	
	public HashMap<Integer, Integer> getShipNumbers() {
		return this.shipNumbers;
	}

}
