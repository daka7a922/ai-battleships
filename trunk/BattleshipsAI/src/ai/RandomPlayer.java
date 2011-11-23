package ai;

import java.util.HashMap;
import java.util.Observable;


import model.AttackResult;
import model.Coordinate;
import model.Field;

/**
 * Dummy player, that attacks pure randomly. The only intelligent
 * part is that it does not attack fields twice.
 * 
 * @author Jakob
 *
 */
public class RandomPlayer extends AbstractPlayer {
	/**
	 * constructor. 
	 * 
	 * @param shipNumbers the number of ships.
	 */
	@SuppressWarnings("unchecked")
	public RandomPlayer(HashMap<Integer, Integer> shipNumbers) {
		super((HashMap<Integer, Integer>)shipNumbers.clone(), "Random Player");
	}
	
	@Override
	public Coordinate nextMove() {
		Coordinate c = this.random();
		this.lastAttack = c;
		return c;
	}

	@Override
	public void update(Observable o, Object arg) {
		AttackResult a = (AttackResult)arg;
		if(a.getResult() == Field.SUNK) {
			this.shipNumbers.put(a.getShipLength(), this.shipNumbers.get(a.getShipLength())-1);
		}
		this.field.setValue(this.lastAttack, a.getResult());
	}
	
	public HashMap<Integer, Integer> getShipNumbers() {
		return this.shipNumbers;
	}
}
