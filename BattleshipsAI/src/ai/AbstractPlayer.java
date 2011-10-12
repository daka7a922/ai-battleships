package ai;

import java.util.HashMap;

public abstract class AbstractPlayer implements IPlayer {

	public AbstractPlayer(HashMap<Integer, Integer> shipNumbers) {
		this.shipNumbers = (HashMap<Integer, Integer>)shipNumbers.clone();
		this.shipNumbers.clear();
		for(int x : shipNumbers.keySet()) {
			this.shipNumbers.put(x, shipNumbers.get(x));
		}
		this.field = new int[10][10];
		for(int i = 0; i < 10; i++) {
			for(int j = 0; j < 10; j++) {
				this.field[i][j] = 0;
			}
		}
	}
	
	protected int[][] field;
	protected HashMap<Integer, Integer> shipNumbers;
}
