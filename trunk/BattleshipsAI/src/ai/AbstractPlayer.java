package ai;

import java.util.HashMap;

import model.Coordinate;
import model.Field;

/**
 * Interface for a concrete player class that includes the two
 * main fields field and shipNumbers that can help to represent
 * the current state of the game. It also includes the common
 * constructor for every type of player.
 * 
 * @author Jakob
 *
 */
public abstract class AbstractPlayer implements IPlayer {
	
	public final String playerName;
	
	/**represents the playground. */
	protected Field field;
	
	/** represents the number of ships. */
	protected HashMap<Integer, Integer> shipNumbers;	
	
	/** stores the coordinate of the last attack. */
	protected Coordinate lastAttack;
	
	/**
	 * the constructor.
	 * 
	 * @param shipNumbers the number of ships of different lengths.
	 */
	public AbstractPlayer(HashMap<Integer, Integer> shipNumbers, String playerName) {
		this.field = new Field();
		this.playerName = playerName;
		this.shipNumbers = shipNumbers;
	}

	@Override
	public String getPlayerName() {
		return this.playerName;
	}
	
	//TODO: Document
	/**
	 * if there isn't a hit but not sunk ship, the player attacks a random
	 * field that is computed in this method.
	 * 
	 * @return Coordinate is coordinate, int[1] the y coordinate. avoids attacks
	 * on fields that already have been attacked or can impossibly contain a ship.
	 */
	Coordinate random() {
		while(true) {
			Coordinate c = new Coordinate((int)(Math.random() * 10), (int)(Math.random() * 10));
			if(this.field.getValue(c) == Field.UNKNOWN) {
				return c;
			}
		}
	}
}
