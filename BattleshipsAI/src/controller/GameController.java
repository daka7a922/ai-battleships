package controller;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Observable;
import model.AttackResult;
import model.Coordinate;
import model.Field;
import model.Settings;
import model.Ship;
import model.StatisticSet;
import ai.IPlayer;

public class GameController extends Observable {
	
	/** the player that plays the game. */
	private IPlayer player;

	/** the settings of the game (player class and ship numbers). */
	private Settings settings;
	
	/** a list of the ships. (model) */
	private List<Ship> ships;
	
	/** the controller for handling the statistics. */
	private IStatisticsController statisticsController;
	
	/** represents the playground. (model) */
	private Field field;
	
	/** the shots made in the current game. */
	private int shots;
	
	private int time;
	
	
	/**
	 * constructor. 
	 * 
	 * @param playground the view of the playground.
	 * @param settingsHandler the controller for handling the settings.
	 */	
	public GameController(IStatisticsController statisticsController) {
		this.field = new Field();
		this.statisticsController = statisticsController;
		//this.settingsHandler.addObserver(this);
	}
	
	/**
	 * method for playing multiple games with the predefined player.
	 */
	public int playGames(int count) {
		int successCount = 0;
		for (int i = 0; i < count; i++) {
			if (this.playGame()) successCount++;			
		}
		return successCount;
	}
	
	/**
	 * method for playing complete game.
	 */
	public Boolean playGame() {
		try {
			placeShips();
			runThrough();
			return true;
		} catch (NullPointerException e) {
			return false;
		}
		
	}
	
	/**
	 * method for placing the ships. It specifies an inner threshold that is
	 * responsible for avoiding infinite loops (they can occur if there are
	 * many ships -> sometimes constellations are created where the remaining
	 * ships never can be placed due to inefficient space allocations.)
	 */
	public Boolean placeShips() throws NullPointerException {
		//Instantiate Player
		try {
			this.setPlayer(this.settings.getPlayerClass().getConstructor(new Class[]{HashMap.class})
					.newInstance(new Object[]{this.settings.getShipNumbers()}));
		} catch (NoSuchMethodException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//false if less than two ships
		if (this.settings.getShipCount() <= 1) return false;		
		this.time = 0;
		int threshold = 1000;
		ships = new ArrayList<Ship>();
		field.reset();
		//iterate over the different ship lenghts
		for(int a : this.settings.getShipNumbers().keySet()) {
			//iterate corresponding to the amount of ships for each length 
			for(int i = 0; i < this.settings.getShipNumbers().get(a); i++) {
				while(threshold > 0) {
					threshold--; //decrease loop counter to prevent infinity loop
					//choose random field & direction
					int x = (int)(Math.random() * 10);
					int y = (int)(Math.random() * 10);
					int direction = (int)(Math.random() * 4);
					//check if there is space for a ship
					if(this.checkSpace(x, y, direction, a) && this.checkCollisions(x, y, direction, a)) {
						//create ship and break while loop
						this.createShip(x, y, direction, a);
						break;
					}
				}
			}
		}
		//restart process if not finished
		if(threshold == 0) {
			this.placeShips();
		}
		this.shots = 0;
		return true;
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
			this.field.setValue(x + (i * xSign), y + (i*ySign), Field.UNKNOWN_SHIP);
		}
		this.ships.add(new Ship(list));
	}
	
	/**
	 * simulates the whole game until the last ship is sunk.
	 */
	public void runThrough() throws NullPointerException {
		//Do next move until no ship is left (not sunk)
		while(!this.nextMove()) {
			//run
		}
	}
	
	/**
	 * simulates the next step of the player.
	 * @return true if all ships are sunk after the move, false otherwise.
	 */
	public boolean nextMove() throws NullPointerException {
		//Increase shot count
		this.shots++;
		
		//Get coordinate for next move from player and attack it
		long startTimeNextMove = System.currentTimeMillis();
		this.attack(this.player.nextMove());
		long endTimeNextMove = System.currentTimeMillis();
		this.time += endTimeNextMove - startTimeNextMove;
		
		//Check if there is any ship left (not sunk)
		for(Ship s : this.ships) {
			if(!s.sunk()) {
				return false;
			}
		}
		
		//Add statistic results to statistic set
		this.statisticsController.addStatistic(this.settings.getPlayerClass(), this.player.getPlayerName(), this.settings.getShipNumbers(),
				this.shots, (this.shots - this.settings.getShipFieldNumber()),
				this.time);
		return true;
	}
	
	/**
	 * simulates the attack and changes the model according to the
	 * consequences of the attack. After that it sends an instance
	 * of AttackResult to the observer (the player).
	 * @param c
	 */
	private void attack(Coordinate c) {
		int result = Field.EMPTY;
		
		int shipLength = 0;

		//Check if field was not yet hit
		if(this.field.getValue(c.getxPosition(),c.getyPosition())==Field.UNKNOWN ||
				this.field.getValue(c.getxPosition(),c.getyPosition())==Field.UNKNOWN_SHIP) {
			//Iterate over ships
			for(Ship s : this.ships) {
				//Check if ship is hit
				if(s.hit(c)) {
					result = Field.HIT;
					if(s.sunk()) {
						result = Field.SUNK;
						shipLength = s.getShip().size();
						for(Coordinate cc : s.getShip().keySet()) {
							this.field.setValue(cc, Field.SUNK);
						}
					}
				}
			}
			this.field.setValue(c, result);
            this.setChanged();
            this.notifyObservers(new AttackResult(result, shipLength));
            this.clearChanged();
		}
		/*else {
			return null;
		}*/
	}
	
	public void reset() {
		this.field.reset();
		if (!(this.ships==null)) this.ships.clear();
	}
	
	// #### getter for object variables ####
	
	public void setPlayer(IPlayer player) {
		this.player = player;
		this.deleteObservers();
		this.addObserver(this.player);
	}
	
	public IPlayer getPlayer() {
		return this.player;
	}
	
	public Field getField() {
		return this.field;
	};
	
	public StatisticSet getStatistics() {
		return null;
	}
	
	public void setSettings(Settings settings) {
		this.settings = settings;
	}
	
	public Settings getSettings() {
		return this.settings;
	}
}