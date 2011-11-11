package ai;

import java.util.HashMap;

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
	
	private String playerName;
	
	/**represents the playground. */
	protected int[][] field;
	
	/** represents the number of ships. */
	protected HashMap<Integer, Integer> shipNumbers;	
	
	/**
	 * the constructor.
	 * 
	 * @param shipNumbers the number of ships of different lengths.
	 */
	@SuppressWarnings("unchecked")
	public AbstractPlayer(HashMap<Integer, Integer> shipNumbers, String playerName) {
		this.field = new int[10][10];
		this.playerName = playerName;
		this.shipNumbers = shipNumbers;
		//this.shipNumbers.clear();

		initializeField();
	}
	
	void initializeField() {
		for(int i = 0; i < 10; i++) {
			for(int j = 0; j < 10; j++) {
				this.field[i][j] = 0;
			}
		}
	}
	
	@Override
	public String getPlayerName() {
		return this.playerName;
	}
	
	@Override
	public void reset() {
		initializeField();
	}
}
