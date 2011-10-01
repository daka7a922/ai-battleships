package ai;

import java.util.Observable;

import controller.AttackResult;

import model.Coordinate;

public class RandomPlayer extends AbstractPlayer {
	
	public RandomPlayer() {
		super();
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
		//do nothing
	}
	

}
