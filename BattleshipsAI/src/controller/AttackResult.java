package controller;

public class AttackResult {
	
	private boolean hit;
	private boolean sunk;
	private int shipLength;
	
	public AttackResult(boolean hit, boolean sunk, int shipLength) {
		this.hit = hit;
		this.sunk = sunk;
		this.shipLength = shipLength;
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
	
	public int getShipLength() {
		return this.shipLength;
	}
}
