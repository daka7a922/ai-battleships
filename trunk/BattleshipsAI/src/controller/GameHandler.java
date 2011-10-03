package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JOptionPane;

import ai.IPlayer;
import ai.RandomPlayer;

import view.Playground;

import model.Coordinate;
import model.Field;
import model.Settings;
import model.Ship;

public class GameHandler {
	
	private Field field;
	private List<Ship> ships;
	private Settings settings;
	private Playground playground;
	private IPlayer player;
	
	public GameHandler(Settings settings, Playground playground) {
		this.ships = new ArrayList<Ship>();
		this.settings = settings;
		this.playground = playground;
		this.field = new Field();
		this.field.addObserver(playground);
		this.addActionListener();
		this.placeShips();
	}
	
	private void addActionListener() {
		this.addFieldButtonListener();
		this.addPlaceShipsButtonListener();
		this.addStartButtonListener();
	}
	
	private void addFieldButtonListener() {
		for(int x = 0; x < 10; x++) {
			for(int y = 0; y < 10; y++) {
				final int a = x;
				final int b = y;
				this.playground.addFieldButtonListener(x, y, new ActionListener() {

					@Override
					public void actionPerformed(ActionEvent e) {
						JOptionPane.showMessageDialog(null,"Clicked on: " + a + ", " + b,"Titel", JOptionPane.PLAIN_MESSAGE);
					}
					
				});
			}
		}
	}
	
	private void addPlaceShipsButtonListener() {
		this.playground.addPlaceShipsButtonListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				ships = new ArrayList<Ship>();
				field.reset();
				placeShips();
			}
		});
	}
	
	private void addStartButtonListener() {
		this.playground.addStartButtonListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				startGame();
			}
		});
	}
	
	private void placeShips() {
		for(int a : settings.getShipNumbers().keySet())
		for(int i = 0; i < this.settings.getShipNumbers().get(a); i++) {
			boolean foundShip = false;
			while(!foundShip) {
			int x = (int)(Math.random() * 10);
			int y = (int)(Math.random() * 10);
			int direction = (int)(Math.random() * 4);
			if(this.checkSpace(x, y, direction, a)) {
				if(this.checkCollisions(x, y, direction, a)) {
					this.createShip(x, y, direction, a);
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
				if(this.shipPlaced(x + i, y + 1) || this.shipPlaced(x + i, y - 1) || this.shipPlaced(x + i, y)) {
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
				if(this.shipPlaced(x - i, y + 1) || this.shipPlaced(x - i, y - 1) || this.shipPlaced(x - i, y)) {
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
				if(this.shipPlaced(x + 1, y + i) || this.shipPlaced(x - 1, y + i) || this.shipPlaced(x, y + i)) {
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
				if(this.shipPlaced(x + 1, y - i) || this.shipPlaced(x - 1, y - i) || this.shipPlaced(x, y -i)) {
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
			this.field.setField(x + (i * xSign), y + (i*ySign), 1);
		}
		this.ships.add(new Ship(list));
	}
	
	private void startGame() {
		this.player = new RandomPlayer();
		boolean allSunk = false;
		while(!allSunk) {
			this.attack(player.nextMove());
			boolean foundNoShip = true;
			for(Ship s : ships) {
				if(!s.sunk()) {
					foundNoShip = false;
				}
			}
			allSunk = foundNoShip;
		}
	}
	
	private void attack(Coordinate c) {
		boolean hit = false;
		for(Ship s : this.ships) {
			if(s.hit(c)) {
				hit = true;
				this.field.setField(c.getxPosition(), c.getyPosition(), 3);
				if(s.sunk()) {
					for(Coordinate cc : s.getShip().keySet()) {
						this.field.setField(cc.getxPosition(), cc.getyPosition(), 4);
					}
				}
			}
		}
		if(!hit) {
			this.field.setField(c.getxPosition(), c.getyPosition(), 2);
		}
	}
}
