package model;

import java.util.HashMap;
import java.util.List;

/**
 * model representation of a ship.
 * 
 * @author Jakob
 *
 */
public class Ship {
	
	/** hashMap contains the coordinates of a ship. the associated Boolean is true, if this field is hit, false otherwise. */
	private HashMap<Coordinate, Boolean> ship;
	
	/**
	 * constructor.
	 * 
	 * @param coordinates the coordinates of the ship.
	 */
	public Ship(List<Coordinate> coordinates) {
		this.ship = new HashMap<Coordinate, Boolean>();
		for(int i = 0; i < coordinates.size(); i++) {
			this.ship.put(coordinates.get(i), false);
		}
	}
	
	/**
	 * getter for the ship.
	 * 
	 * @return the hashmap representing the ship and its status.
	 */
	public HashMap<Coordinate, Boolean> getShip() {
		return this.ship;
	}
	
	/**
	 * checks whether an attack hits this ship.
	 * 
	 * @param c the coordinate that is attacked.
	 * @return true if the coordinate is included in the ship, false otherwise.
	 */
	public boolean hit(Coordinate c) {
		for(Coordinate co : this.ship.keySet()) {
			if(co.getxPosition() == c.getxPosition() && co.getyPosition() == c.getyPosition() && !this.ship.get(co)) {
				this.ship.put(co, true);
				return true;
			}
		}
		return false;
	}
	
	/**
	 * checks whether a ship is sunk (all Booleans are true).
	 * @return true if the ship is sunk, false otherwise.
	 */
	public boolean sunk() {
		if(this.ship.values().contains(false)) {
			return false;
		} else {
			return true;
		}
	}
}
