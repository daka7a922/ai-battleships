package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Observable;
import java.util.Observer;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JOptionPane;

import ai.IPlayer;
import ai.MediumPlayer;
import ai.RandomPlayer;

import view.Playground;

import model.Coordinate;
import model.Field;
import model.Settings;
import model.Ship;

public class GameHandler extends Observable implements Observer {
	
	private Field field;
	private List<Ship> ships;
	private SettingsHandler settingsHandler;
	private Playground playground;
	private IPlayer player;
	private Thread t;
	
	public GameHandler(Playground playground, SettingsHandler settingsHandler) {
		this.ships = new ArrayList<Ship>();
		this.settingsHandler = settingsHandler;
		this.playground = playground;
		this.field = new Field();
		this.field.addObserver(playground);
		this.settingsHandler.addObserver(this);
		this.addActionListener();
	}
	
	private void addActionListener() {
		this.addFieldButtonListener();
		this.addPlaceShipsButtonListener();
		this.addNextMoveButtonListener();
		this.addRunThroughButtonListener();
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
				playground.setNextMoveButtonEnabled(true);
				playground.setRunThroughButtonEnabled(true);
				placeShips();
				startGame();
			}
		});
	}
	
	private void addNextMoveButtonListener() {
		this.playground.addNextMoveButtonListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if(nextMove()) {
					playground.setNextMoveButtonEnabled(false);
					playground.setRunThroughButtonEnabled(false);
				}
			}
		});
	}
	
	private void addRunThroughButtonListener() {
		this.playground.addRunThroughButtonListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				playground.setNextMoveButtonEnabled(false);
				playground.setRunThroughButtonEnabled(false);
				runThrough();
			}
		});
	}
	
	private void placeShips() {
		int threshold = 1000;
		ships = new ArrayList<Ship>();
		field.reset();
		for(int a : settingsHandler.getSettings().getShipNumbers().keySet())
		for(int i = 0; i < this.settingsHandler.getSettings().getShipNumbers().get(a); i++) {
			boolean foundShip = false;
			while(!foundShip && threshold > 0) {
				threshold--;
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
		if(threshold == 0) {
			this.placeShips();
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
		if(this.settingsHandler.getSettings().getPlayer() == 0) {
			this.player = new RandomPlayer(this.settingsHandler.getSettings().getShipNumbers());
			this.addObserverToGameHandler(this.player);
		} else if(this.settingsHandler.getSettings().getPlayer() == 1) {
			this.player = new MediumPlayer(this.settingsHandler.getSettings().getShipNumbers());
			this.addObserverToGameHandler(this.player);
		} else if(this.settingsHandler.getSettings().getPlayer() == 2) {
			//TODO
		}
	}
	
	public void runThrough() {
		while(!this.nextMove()) {
			//run
		}
	}
	
	public boolean nextMove() {
		this.attack(player.nextMove());
		for(Ship s : this.ships) {
			if(!s.sunk()) {
				return false;
			}
		}
		return true;
	}
	
	private void attack(Coordinate c) {
		boolean hit = false;
		boolean sunk = false;
		int shipLength = 0;
		for(Ship s : this.ships) {
			if(s.hit(c)) {
				hit = true;
				this.field.setField(c.getxPosition(), c.getyPosition(), 3);
				if(s.sunk()) {
					sunk = true;
					shipLength = s.getShip().size();
					for(Coordinate cc : s.getShip().keySet()) {
						this.field.setField(cc.getxPosition(), cc.getyPosition(), 4);
					}
				}
			}
		}
		if(!hit) {
			this.field.setField(c.getxPosition(), c.getyPosition(), 2);
		}
		this.setChanged();
		this.notifyObservers(new AttackResult(hit, sunk, shipLength));
		this.clearChanged();
	}
	
	private void addObserverToGameHandler(Observer o) {
		this.deleteObservers();
		this.addObserver(o);
	}

	@Override
	public void update(Observable arg0, Object arg1) {
		HashMap<Integer, Integer> map = this.settingsHandler.getSettings().getShipNumbers();
		int sum = 0;
		for(int x : map.keySet()) {
			sum = sum + map.get(x);
		}
		if(sum > 1) {
			this.playground.setPlaceShipsButtonEnabled(true);
			this.playground.setNextMoveButtonEnabled(true);
			this.playground.setRunThroughButtonEnabled(true);
			this.placeShips();
			this.startGame();
		} else {
			this.playground.setPlaceShipsButtonEnabled(false);
			this.playground.setNextMoveButtonEnabled(false);
			this.playground.setRunThroughButtonEnabled(false);
		}
	}
}
