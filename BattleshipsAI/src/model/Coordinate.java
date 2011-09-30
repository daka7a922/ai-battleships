package model;

public class Coordinate {
	
	private int xPosition;
	private int yPosition;
	
	public Coordinate(int xPosition, int yPosition) {
		this.xPosition = xPosition;
		this.yPosition = yPosition;
	}

	/**
	 * @return the xPosition
	 */
	public int getxPosition() {
		return xPosition;
	}

	/**
	 * @return the yPosition
	 */
	public int getyPosition() {
		return yPosition;
	}
}
