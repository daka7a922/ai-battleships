package controller;

import java.util.ArrayList;
import java.util.List;

import model.Coordinate;
import model.Ship;

public class GameHandler {
	
	private List<Ship> ships;
	private int five = 3;
	private int four = 2;
	private int three = 2;
	private int two = 2;
	
	public GameHandler() {
		this.ships = new ArrayList<Ship>();
		this.placeShips();
	}
	
	private void placeShips() {
		for(int i = 0; i < this.five; i++) {
			boolean foundShip = false;
			while(!foundShip) {
			int x = (int)(Math.random() * 10);
			int y = (int)(Math.random() * 10);
			int direction = (int)(Math.random() * 4);
			if(this.checkSpace(x, y, direction, 5)) {
				if(this.checkCollisions(x, y, direction, 5)) {
					this.createShip(x, y, direction, 5);
					foundShip = true;
				}
			}
			}
		}
	}
	
	private boolean checkSpace(int x, int y, int direction, int length) {
		if(direction == 0) {
			if(x + (length - 1) <= 9) {
				return true;
			}
		} else if(direction == 1) {
			if(y + (length - 1) <= 9) {
				return true;
			} 
		} else if(direction == 2) {
			if(x - (length - 1) >= 0) {
				return true;
			}
		} else {
			if(y - (length - 1) >= 0) {
				return true;
			} 
		}
		return false;
	}
	
	private boolean checkCollisions(int x, int y, int direction, int length) {
		if(direction == 0) {
			if(this.shipPlaced(x-1, y)) {
				return false;
			}
			for(int i = 0; i < length; i++) {
				if(this.shipPlaced(x + i, y + 1) || this.shipPlaced(x + i, y - 1)) {
					return false;
				}
			}
			if(this.shipPlaced(x + length, y)) {
				return false;
			}
		} else if(direction == 2) {
			if(this.shipPlaced(x+1, y)) {
				return false;
			}
			for(int i = 0; i < length; i++) {
				if(this.shipPlaced(x - i, y + 1) || this.shipPlaced(x - i, y - 1)) {
					return false;
				}
			}
			if(this.shipPlaced(x - length, y)) {
				return false;
			}
		} else if(direction == 1) {
			if(this.shipPlaced(x, y-1)) {
				return false;
			}
			for(int i = 0; i < length; i++) {
				if(this.shipPlaced(x + 1, y + i) || this.shipPlaced(x - 1, y + i)) {
					return false;
				}
			}
			if(this.shipPlaced(x, y + length)) {
				return false;
			}
		} else if(direction == 3) {
			if(this.shipPlaced(x, y+1)) {
				return false;
			}
			for(int i = 0; i < length; i++) {
				if(this.shipPlaced(x + 1, y - i) || this.shipPlaced(x - 1, y - i)) {
					return false;
				}
			}
			if(this.shipPlaced(x, y - length)) {
				return false;
			}
		}
		return true;
	}
	
	private boolean shipPlaced(int x, int y) {
		if(x < 0 || x > 9 || y < 0 || y > 9) return false;
		for(Ship s : this.ships) {
			for(Coordinate c : s.getShip().keySet()) {
				if(c.getxPosition() == x && c.getyPosition() == y) {
					return true;
				}
			}
		}
		return false;
	}
	
	private void createShip(int x, int y, int direction, int length) {
		int xSign;
		int ySign;
		if(direction == 0) {
			xSign = 1;
			ySign = 0;
		} else if(direction == 1) {
			xSign = 0;
			ySign = 1;
		} else if(direction == 2) {
			xSign = -1;
			ySign = 0;
		} else {
			xSign = 0;
			ySign = -1;
		}
		List<Coordinate> list = new ArrayList<Coordinate>();
		for(int i = 0; i < length; i++) {
			list.add(new Coordinate(x + (i * xSign), y + (i * ySign)));
			System.out.println((x + (i * xSign)) + ", " + (y + (i*ySign)));
		}
		System.out.println();
		this.ships.add(new Ship(list));
	}
}
