package model;

import java.util.HashMap;
import java.util.List;

public class Ship {
	
	private HashMap<Coordinate, Boolean> ship;
	
	public Ship(List<Coordinate> coordinates) {
		this.ship = new HashMap<Coordinate, Boolean>();
		for(int i = 0; i < coordinates.size(); i++) {
			this.ship.put(coordinates.get(i), false);
		}
	}
	
	public HashMap<Coordinate, Boolean> getShip() {
		return this.ship;
	}
	
	public boolean hit(Coordinate c) {
		for(Coordinate co : this.ship.keySet()) {
			if(co.getxPosition() == c.getxPosition() && co.getyPosition() == c.getyPosition() && !this.ship.get(co)) {
				this.ship.put(co, true);
				return true;
			}
		}
		return false;
	}
	
	public boolean sunk() {
		if(this.ship.values().contains(false)) {
			return false;
		} else {
			return true;
		}
	}
}
