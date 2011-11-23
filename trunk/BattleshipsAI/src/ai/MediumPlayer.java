package ai;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Observable;


import model.AttackResult;
import model.Coordinate;
import model.Field;

/**
 * the medium player is able to focus on a ship once he hits
 * it the first time. after that he tries to find a second 
 * field of this ship by trying one of the max. 4 neighboured
 * fields. As soon he finds the direction of the placement
 * (horizontal or vertical) he tries to sink the ship. He also
 * marks all fields that have a common edge with a "ship-field",
 * so it doesn't attack them as soon he found out where the ship
 * is placed. The approach is quite simple but makes this type
 * of player still powerful.
 * 
 * @author Jakob
 *
 */
public class MediumPlayer extends AbstractPlayer {
	
	/** stores the coordinate of the field where the last hit of a ship was performed. */
	private Coordinate lastHit;
	
	/** is set to true as soon the player hits a ship and didn't sink it. */
	private boolean foundShip;
	
	/** is true if a hit but not sunk ship is placed horizontal (and the player knows it). */
	private boolean horizontal;
	
	/** is true if a hit but not sunk ship is placed vertical (and the player knows it). */
	private boolean vertical;
	
	
	/**
	 * constructor.
	 * 
	 * @param shipNumbers the number of ships of different lengths.
	 */
	public MediumPlayer(HashMap<Integer, Integer> shipNumbers) {
		super(shipNumbers, "Medium Player");
	}

	@Override
	public Coordinate nextMove() {
		Coordinate c = new Coordinate(0, 0);
		//No ship found that did not sink yet: Try to find new ship
		if(!this.foundShip) {
			c = this.random();
		}
		//Else: Try to find rest of the ship 
		else {
			c = this.prepareAttack();
		}
		this.lastAttack = c;		
		return c;
	}

	@Override
	public void update(Observable arg0, Object arg1) {
		AttackResult a = (AttackResult)arg1;
		if(a.getResult() == Field.HIT) {
			this.foundShip = true;
			this.lastHit = lastAttack;
		} else if (a.getResult() == Field.SUNK) {
			this.markField(this.lastAttack);
			
			this.foundShip = false;
			this.horizontal = false;
			this.vertical = false;
			this.lastHit = lastAttack;
		}
		this.field.setValue(this.lastAttack, a.getResult());
	}
	
	/**
	 * is called whenever there is ship that is hit, but not sunk. Tries 
	 * to find out the direction the ship is placed and to hit it again.
	 * @return
	 */
	private Coordinate prepareAttack() {
		if(!this.horizontal && !this.vertical) {
			this.searchDirection();
		}
		if(!this.horizontal && !this.vertical) {
			while(true) {
				int a = ((int)(Math.random() * 4));
				switch(a) {
				case 0:
					if(lastHit.getxPosition() + 1 <= 9 && this.field.getValue(lastHit.getxPosition() + 1, lastHit.getyPosition()) == Field.UNKNOWN) {
						return new Coordinate(lastHit.getxPosition() + 1, lastHit.getyPosition());
					}
					break;
				case 1:
					if(lastHit.getxPosition() - 1 >= 0 && this.field.getValue(lastHit.getxPosition() - 1, lastHit.getyPosition()) == Field.UNKNOWN) {
						return new Coordinate(lastHit.getxPosition() - 1, lastHit.getyPosition());
					}
					break;
				case 2:
					if(lastHit.getyPosition() + 1 <= 9 && this.field.getValue(lastHit.getxPosition(), lastHit.getyPosition() + 1) == Field.UNKNOWN) {
						return new Coordinate(lastHit.getxPosition(), lastHit.getyPosition() + 1);
					}
					break;
				case 3:
					if(lastHit.getyPosition() - 1 >= 0 && this.field.getValue(lastHit.getxPosition(), lastHit.getyPosition() - 1) == Field.UNKNOWN) {
						return new Coordinate(lastHit.getxPosition(), lastHit.getyPosition() - 1);
					}
					break;
				}
			}
		} else {
			int cx = lastHit.getxPosition();
			int cy = lastHit.getyPosition();
			int x;
			int y;
			if(this.horizontal) {
				x = 0;
				y = 1;
			} else {
				x = 1;
				y = 0;
			}
			while(true) {
				boolean xBound = (0 <= (cx + x) && 9 >= (cx + x));
				boolean yBound = (0 <= (cy + y) && 9 >= (cy +y ));
				if(xBound && yBound && this.field.getValue(cx + x, cy + y) == Field.UNKNOWN) {
					return new Coordinate(cx + x, cy + y);
				} else if((!xBound || !yBound) || this.field.getValue(cx + x, cy + y) == Field.EMPTY) {
					x = x * (-1);
					y = y * (-1);
				} else {
					cx = cx + x;
					cy = cy + y;
				}
			} 
		}
	}
	
	/**
	 * tries to find out the direction a ship is placed.
	 */
	private void searchDirection() {
		int x = this.lastHit.getxPosition();
		int y = this.lastHit.getyPosition();
		if((this.checkField(x + 1, y) && this.checkField(x, y)) || (this.checkField(x, y) && this.checkField(x - 1, y))) {
			this.vertical = true;
		}
		if((this.checkField(x, y + 1) && this.checkField(x, y)) || (this.checkField(x, y) && this.checkField(x, y - 1))) {
			this.horizontal = true;
		}
	}
	
	/**
	 * tries to find out if a field contains a hit but not sunk ship.
	 * 
	 * @param x x coordinate of the field.
	 * @param y y coordinate of the field.
	 * @return true if it contains a hit but not sunk ship and false if it doesn't
	 * contain such a ship or if the field is out of playground's bounds.
	 */
	private boolean checkField(int x, int y) {
		if(x < 0 || x > 9 || y < 0 || y > 9) {
			return false;
		} else {
			if(this.field.getValue(x, y) == Field.HIT) {
				return true;
			} else {
				return false;
			}
		}
	}
	
	/**
	 * supports the markShip method. Checks for every ship-field
	 * how many neighbours it has and marks them to avoid unnecessary attacks
	 * in the future.
	 * 
	 * @param x x coordinate of the field.
	 * @param y y coordinate of the field.
	 */
	private void markField(Coordinate c) {
		List<Coordinate> cList = new ArrayList<Coordinate>();
		if(c.getxPosition() + 1 <= 9) cList.add(new Coordinate(c.getxPosition() + 1, c.getyPosition())); //xMaxBound
		if(c.getxPosition() - 1 >= 0) cList.add(new Coordinate(c.getxPosition() - 1, c.getyPosition())); //xMinBound
		if(c.getyPosition() + 1 <= 9) cList.add(new Coordinate(c.getxPosition(), c.getyPosition() + 1)); //yMaxBound
		if(c.getyPosition() - 1 >= 0) cList.add(new Coordinate(c.getxPosition(), c.getyPosition() - 1)); //yMinBound
		this.field.setValue(c, Field.SUNK);
		for (Coordinate cc : cList) {
			switch (this.field.getValue(cc)) {
			case Field.UNKNOWN: 
				this.field.setValue(cc, Field.EMPTY); 
				break;
			case Field.HIT: 
				this.markField(cc);
			}
		}
	}
	
	/**
	 * just for test purposes.
	 */
	@SuppressWarnings("unused")
	private void print() {
		System.out.print(this.field);
	}
}
