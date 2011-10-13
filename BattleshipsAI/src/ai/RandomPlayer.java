package ai;

import java.util.HashMap;
import java.util.Observable;

import controller.AttackResult;

import model.Coordinate;

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
	public RandomPlayer(HashMap<Integer, Integer> shipNumbers) {
		super(shipNumbers);
	}
	
	@Override
	public Coordinate nextMove() {
		while(true) {
			Coordinate c = new Coordinate((int)(Math.random() * 10), (int)(Math.random() * 10));
			if(this.field[c.getxPosition()][c.getyPosition()] == 0) {
				this.field[c.getxPosition()][c.getyPosition()] = 1;
				return c;
			}
		}
	}

	@Override
	public void update(Observable o, Object arg) {
		AttackResult a = (AttackResult)arg;
		if(a.isHit()) {
			if(!a.isSunk()) {
				System.out.println("Treffer");
			} else {
				int i = this.shipNumbers.get(a.getShipLength());
				this.shipNumbers.put(a.getShipLength(), i-1);
				System.out.println("Treffer versenkt (" + a.getShipLength() + ")");
				System.out.print("Verbleibende Schiffe: ");
				for(int x : this.shipNumbers.keySet()) {
					System.out.print(x + "(" + this.shipNumbers.get(x) + ") ");
				}
				System.out.println();
			}
		} else {
			System.out.println("Daneben");
		}
	}
	

}
