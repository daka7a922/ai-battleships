package model;

import java.util.Observable;

/**
 * represents the playground in the model.
 * 
 * @author Jakob
 *
 */
public class Field extends Observable {

	public final static int UNKNOWN = 0;
	public final static int UNKNOWN_SHIP = 1;
	public final static int EMPTY = 2;
	public final static int HIT = 3;
	public final static int SUNK = 4;
	
	private int[][] fields;
	
	/**
	 * constructor.
	 */
	public Field() {
		this.fields = new int[10][10];
		this.reset();
	}
	
	/**
	 * getter for the field.
	 * 
	 * @return the field.
	 */
	public int[][] getAsArray() {
		return this.fields;
	}
	
	/**
	 * getter for the field value.
	 * 
	 * @param x x coordinate.
	 * @param y y coordinate.
	 * @return the field value as int.
	 */
	public int getValue(int x, int y) {
		return this.fields[x][y];
	}
	
	/**
	 * getter for the field value.
	 * 
	 * @param c coordinate.
	 * @return the field value as int.
	 */
	public int getValue(Coordinate c) {
		return getValue(c.getxPosition(), c.getyPosition());
	}
	
	/**
	 * setter for a specific field.
	 * 
	 * @param x x coordinate.
	 * @param y y coordinate.
	 * @param value the value to add to this field.
	 */
	public void setValue(int x, int y, int value) {
		this.fields[x][y] = value;
		this.setChanged();
		this.notifyObservers();
		this.clearChanged();
	}
	
	/**
	 * setter for a specific field.
	 * 
	 * @param c coordinate.
	 * @param value the value to add to this field.
	 */
	public void setValue(Coordinate c, int value) {
		setValue(c.getxPosition(), c.getyPosition(), value);
	}
	
	/**
	 * resets the field, sets all values to 0.
	 */
	public void reset() {
		for(int i = 0; i < 10; i++) {
			for(int j = 0; j < 10; j++) {
				this.fields[i][j] = Field.UNKNOWN;
			}
		}
	}
	
	/**
	 * just for testing purposes.
	 */
	@SuppressWarnings("unused")
	private void print() {
		System.out.print(this.toString() + "\n");
	}
	
	@Override
	public String toString() {
		String s = new String();
		for(int i = 0; i < 10; i++) {
			s += "{";
			for(int j = 0; j < 10; j++) {
				if(j!=0) s += ",";
				s += this.fields[i][j];
			}
			s += "}\n";
		}		
		return s;
	}
}
