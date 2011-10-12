package model;

import java.util.HashMap;

public class Settings {
	
	private HashMap<Integer, Integer> shipNumbers;
	private int player;
	
	public Settings() {
		this.shipNumbers = new HashMap<Integer, Integer>();
	}
	
	public HashMap<Integer, Integer> getShipNumbers() {
		return this.shipNumbers;
	}
	
	public void setShipNumbers(HashMap<Integer, Integer> shipNumbers) {
		this.shipNumbers = shipNumbers;
	}

	public int getPlayer() {
		return this.player;
	}
	
	public void setPlayer(int player) {
		this.player = player;
	}
}
