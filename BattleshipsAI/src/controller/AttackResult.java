package controller;

public class AttackResult {
	
	private boolean hit;
	private boolean sunk;
	
	public AttackResult(boolean hit, boolean sunk) {
		this.hit = hit;
		this.sunk = sunk;
	}

	/**
	 * @return the hit
	 */
	public boolean isHit() {
		return hit;
	}

	/**
	 * @return the sunk
	 */
	public boolean isSunk() {
		return sunk;
	}
}
