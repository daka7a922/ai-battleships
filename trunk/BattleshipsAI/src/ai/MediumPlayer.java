package ai;

import java.util.HashMap;
import java.util.Observable;

import controller.AttackResult;

import model.Coordinate;

public class MediumPlayer extends AbstractPlayer {

	private Coordinate lastAttack;
	private Coordinate lastHit;
	private boolean foundShip;
	private boolean horizontal;
	private boolean vertical;
	
	public MediumPlayer(HashMap<Integer, Integer> shipNumbers) {
		super(shipNumbers);
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
		this.print();
	}
	
	private Coordinate prepareAttack() {
		if(!this.horizontal && !this.vertical) {
			this.searchDirection(lastHit.getxPosition(), lastHit.getyPosition());
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
			//int[] x = random();
			//return new Coordinate(x[0], x[1]);
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
	
	private void searchDirection(int x, int y) {
		if((this.checkField(x + 1, y) && this.checkField(x, y)) || (this.checkField(x, y) && this.checkField(x - 1, y))) {
			this.vertical = true;
		}
		if((this.checkField(x, y + 1) && this.checkField(x, y)) || (this.checkField(x, y) && this.checkField(x, y - 1))) {
			this.horizontal = true;
		}
	}
	
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
	
	private void markShip() {
		for(int x = 0; x < 10; x++) {
			for(int y = 0; y < 10; y++) {
				if(this.field[x][y] == 3) {
					this.markField(x, y);
				}
			}
		}
	}
	
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
	
	private int[] random() {
		int x = ((int)(Math.random() * 10));
		int y = ((int)(Math.random() * 10));
		int[] z = {x, y};
		return z;
	}
	
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
