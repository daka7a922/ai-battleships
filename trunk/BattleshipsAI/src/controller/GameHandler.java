package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JOptionPane;

import ai.AIAdvancedStatisticsPlayer;
import ai.AIBrutForcePlayer;
import ai.AIPlayer;
import ai.AIStatisticsPlayer;
import ai.IPlayer;
import ai.MediumPlayer;
import ai.RandomPlayer;

import view.Playground;

import model.Coordinate;
import model.Field;
import model.Ship;

/**
 * this controller is responsible for the coordination of the game.
 * so it is responsible for setting up the playground (by placing the 
 * ships), to inizialize a new game and a new player and to coordinate
 * the game flow.
 * 
 * @author Jakob
 *
 */
public class GameHandler extends Observable implements Observer {
	
	/** represents the playground. (model) */
	private Field field;
	
	/** a list of the ships. (model) */
	private List<Ship> ships;
	
	/** the controller for handling the settings. */
	private SettingsHandler settingsHandler;
	
	/** the controller for handling the statistics. */
	private StatisticsHandler statisticsHandler;
	
	/** the view for the playground. */
	private Playground playground;
	
	/** the player that plays the game. */
	private IPlayer player;
	
	private int shots;
	
	private String playerName;
	
	private static final String[] playerNames = {"Random Player", "Medium Player", "AI Player", "Statistical Player", "Advanced Statistical Player", "Brut Force Player"};
	
	
	/**
	 * constructor. 
	 * 
	 * @param playground the view of the playground.
	 * @param settingsHandler the controller for handling the settings.
	 */
	public GameHandler(Playground playground, SettingsHandler settingsHandler, StatisticsHandler statisticsHandler) {
		this.ships = new ArrayList<Ship>();
		this.settingsHandler = settingsHandler;
		this.statisticsHandler = statisticsHandler;
		this.playground = playground;
		this.field = new Field();
		this.field.addObserver(playground);
		this.settingsHandler.addObserver(this);
		this.addActionListener();
	}
	
	/**
	 * adds the action listeners to the buttons of the playground view.
	 */
	private void addActionListener() {
		this.addFieldButtonListener();
		this.addPlaceShipsButtonListener();
		this.addNextMoveButtonListener();
		this.addRunThroughButtonListener();
	}
	
	/**
	 * adds the controller to the field buttons.
	 *
	 */
	//TODO remove!?
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
	
	/**
	 * add the listener to the place ships button.
	 */
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
	
	/**
	 * add the listener to the next move button.
	 */
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
	
	/**
	 * add the listener to the run through button.
	 */
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
	
	/**
	 * method for placing the ships. It specifies an inner threshold that is
	 * responsible for avoiding infinite loops (they can occur if there are
	 * many ships -> sometimes constellations are created where the remaining
	 * ships never can be placed due to inefficient space allocations.)
	 */
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
	
	/**
	 * supports the place Ships method and is responsible for checking whether
	 * a ship can be placed by starting at the specified field.
	 * 
	 * @param x the x coordinate of the field.
	 * @param y the y coordinate of the field.
	 * @param direction the choosen direction.
	 * @param length the length of the ship.
	 * @return true if there is enough space, false if the ship would cross the playgrounds boundaries.
	 */
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
	
	/**
	 * is called if the checkSpace method evaluated a field to true. This method
	 * checks if the ship would collide with another ship.
	 * 
	 * @param x x coordinate of the field.
	 * @param y y coordinate of the field.
	 * @param direction the chosen direction.
	 * @param length the length of the ship.
	 * @return true if there isn't a collision, false otherwise.
	 */
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
	
	/**
	 * checks if a ship is placed at a specific field.
	 * 
	 * @param x x coordinate of the field.
	 * @param y y coordinate of the field.
	 * @return true if there is a ship, false otherwise.
	 */
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
	
	/**
	 * if a ship passed all checks, it is created in this method.
	 * 
	 * @param x x coordinate of the starting field.
	 * @param y y coordinate of the starting field.
	 * @param direction the chosen direction.
	 * @param length the length of the ship.
	 */
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
	
	/**
	 * starts a game by initializing a new player.
	 */
	private void startGame() {
		if(this.settingsHandler.getSettings().getPlayer() == 0) {
			this.player = new RandomPlayer(this.settingsHandler.getSettings().getShipNumbers());
			this.addObserverToGameHandler(this.player);
		} else if(this.settingsHandler.getSettings().getPlayer() == 1) {
			this.player = new MediumPlayer(this.settingsHandler.getSettings().getShipNumbers());
			this.addObserverToGameHandler(this.player);
		} else if(this.settingsHandler.getSettings().getPlayer() == 2) {
			this.player = new AIPlayer(this.settingsHandler.getSettings().getShipNumbers());
			this.addObserverToGameHandler(this.player);
		} else if(this.settingsHandler.getSettings().getPlayer() == 3) {
			this.player = new AIStatisticsPlayer(this.settingsHandler.getSettings().getShipNumbers());
			this.addObserverToGameHandler(this.player);
		} else if(this.settingsHandler.getSettings().getPlayer() == 4) {
			this.player = new AIAdvancedStatisticsPlayer(this.settingsHandler.getSettings().getShipNumbers());
			this.addObserverToGameHandler(this.player);
		} else if(this.settingsHandler.getSettings().getPlayer() == 5) {
			this.player = new AIBrutForcePlayer(this.settingsHandler.getSettings().getShipNumbers());
			this.addObserverToGameHandler(this.player);
		}
		this.shots = 0;
		this.playerName = playerNames[this.settingsHandler.getSettings().getPlayer()];
	}
	
	/**
	 * simulates the whole game until the last ship is sunk.
	 */
	public void runThrough() {
		while(!this.nextMove()) {
			//run
		}
	}
	
	/**
	 * simulates the next step of the player.
	 * @return true if all ships are sunk after the move, false otherwise.
	 */
	public boolean nextMove() {
		this.shots++;
		this.attack(player.nextMove());
		for(Ship s : this.ships) {
			if(!s.sunk()) {
				return false;
			}
		}
		this.statisticsHandler.addStatistic(this.playerName, this.shots, (this.shots - this.getShipFieldNumber()));
		return true;
	}
	
	/**
	 * simulates the attack and changes the model according to the
	 * consequences of the attack. After that it sends an instance
	 * of AttackResult to the observer (the player).
	 * @param c
	 */
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
	
	/**
	 * adds an observer.
	 * @param o the observer to add.
	 */
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
	
	private int getShipFieldNumber() {
		int result = 0;
		for(int i : this.settingsHandler.getSettings().getShipNumbers().keySet()) {
			result = result + (i * this.settingsHandler.getSettings().getShipNumbers().get(i));
		}
		return result;
	}
}
