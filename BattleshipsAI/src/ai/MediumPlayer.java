package ai;

import java.util.HashMap;
import java.util.Observable;


import model.AttackResult;
import model.Coordinate;

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

	public static final String pName = "Medium Player 1";
	
	/** stores the coordinate of the last attack. */
	private Coordinate lastAttack;
	
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
		if(!this.foundShip) {
			boolean found = false;
			while(!found) {
				int[] z = this.random();
				if(this.field[z[0]][z[1]] == 0) {
					c = new Coordinate(z[0], z[1]);
					found = true;
				}
			}
		} else {
			c = this.prepareAttack();
		}
		this.lastAttack = c;		
		return c;
	}

	@Override
	public void update(Observable arg0, Object arg1) {
		AttackResult a = (AttackResult)arg1;
		if(a.isHit()) {
			if(!a.isSunk()) {
				this.field[this.lastAttack.getxPosition()][this.lastAttack.getyPosition()] = 2;
				this.foundShip = true;
				this.lastHit = lastAttack;
				
			} else {
				this.field[this.lastAttack.getxPosition()][this.lastAttack.getyPosition()] = 3;
				this.markShip();
				this.foundShip = false;
				this.horizontal = false;
				this.vertical = false;
			}
		}  else {
			this.field[lastAttack.getxPosition()][this.lastAttack.getyPosition()] = 1;
		}
	}
	
	/**
	 * is called whenever there is ship that is hit but not sunk. Tries either
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
					if(lastHit.getxPosition() + 1 <= 9 && this.field[lastHit.getxPosition() + 1][lastHit.getyPosition()] == 0) {
						return new Coordinate(lastHit.getxPosition() + 1, lastHit.getyPosition());
					}
					break;
				case 1:
					if(lastHit.getxPosition() - 1 >= 0 && this.field[lastHit.getxPosition() - 1][lastHit.getyPosition()] == 0) {
						return new Coordinate(lastHit.getxPosition() - 1, lastHit.getyPosition());
					}
					break;
				case 2:
					if(lastHit.getyPosition() + 1 <= 9 && this.field[lastHit.getxPosition()][lastHit.getyPosition() + 1] == 0) {
						return new Coordinate(lastHit.getxPosition(), lastHit.getyPosition() + 1);
					}
					break;
				case 3:
					if(lastHit.getyPosition() - 1 >= 0 && this.field[lastHit.getxPosition()][lastHit.getyPosition() - 1] == 0) {
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
				if(xBound && yBound && this.field[cx + x][cy + y] == 0) {
					return new Coordinate(cx + x, cy + y);
				} else if((!xBound || !yBound) || this.field[cx + x][cy + y] == 1) {
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
			if(this.field[x][y] == 2) {
				return true;
			} else {
				return false;
			}
		}
	}
	
	/**
	 * mark all neighboured fields of a ship that is sunk so that these
	 * fields won't be attacked later, because they cannot contain a ship.
	 */
	private void markShip() {
		for(int x = 0; x < 10; x++) {
			for(int y = 0; y < 10; y++) {
				if(this.field[x][y] == 3) {
					this.markField(x, y);
				}
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
	private void markField(int x, int y) {
		boolean yMaxBound = (y + 1 <= 9);
		boolean yMinBound = (y - 1 >= 0);
		boolean xMaxBound = (x + 1 <= 9);
		boolean xMinBound = (x - 1 >= 0);
		this.field[x][y] = 3;
		if(yMaxBound) {
			if(this.field[x][y + 1] != 2) {
				this.field[x][y + 1] = 1;
			} else {
				this.markField(x, y + 1);
			}
		}
		if(yMinBound) {
			if(this.field[x][y - 1] != 2) {
				this.field[x][y - 1] = 1;
			} else {
				this.markField(x, y - 1);
			}
		}
		if(xMaxBound) {
			if(this.field[x + 1][y] != 2) {
				this.field[x + 1][y] = 1;
			} else {
				this.markField(x + 1, y);
			}
		}
		if(xMinBound) {
			if(this.field[x - 1][y] != 2) {
				this.field[x - 1][y] = 1;
			} else {
				this.markField(x - 1, y);
			}
		}
	}
	
	/**
	 * if there isn't a hit but not sunk ship, the player attacks a random
	 * field that is computed in this method.
	 * 
	 * @return int[0] is the x coordinate, int[1] the y coordinate. avoids attacks
	 * on fields that already have been attacked or can impossibly contain a ship.
	 */
	private int[] random() {
		int x = ((int)(Math.random() * 10));
		int y = ((int)(Math.random() * 10));
		int[] z = {x, y};
		return z;
	}
	
	/**
	 * just for test purposes.
	 */
	@SuppressWarnings("unused")
	private void print() {
		for(int i = 0; i < 10; i++) {
			for(int j = 0; j < 10; j++) {
				System.out.print(this.field[i][j] + " ");
			}
			System.out.println();
		}
		System.out.println();
	}
}
