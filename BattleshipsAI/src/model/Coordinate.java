package model;

/**
 * represents a coordinate with x and y value.
 * 
 * @author Jakob
 *
 */
public class Coordinate {
	
	/** the x coordinate. */
	private int xPosition;
	
	/** the y coordinate. */
	private int yPosition;
	
	/**
	 * constructor.
	 * 
	 * @param xPosition the x coordinate.
	 * @param yPosition the y coordinate.
	 */
	public Coordinate(int xPosition, int yPosition) {
		this.xPosition = xPosition;
		this.yPosition = yPosition;
	}

	/**
	 * getter for the x coordinate.
	 * 
	 * @return the xPosition
	 */
	public int getxPosition() {
		return xPosition;
	}

	/**
	 * getter for the y coordinate.
	 * 
	 * @return the yPosition
	 */
	public int getyPosition() {
		return yPosition;
	}
	
	@Override
	public String toString() {
		return "(" + this.getxPosition() + ", " + this.getyPosition() + ")";
	}
}
