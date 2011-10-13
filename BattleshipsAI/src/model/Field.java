package model;

import java.util.Observable;

/**
 * represents the playground in the model.
 * 
 * @author Jakob
 *
 */
public class Field extends Observable {
	
	/** the playground as array of integers. */
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
	public int[][] getFields() {
		return this.fields;
	}
	
	/**
	 * setter for a specific field.
	 * 
	 * @param x x coordinate.
	 * @param y y coordinate.
	 * @param value the value to add to this field.
	 */
	public void setField(int x, int y, int value) {
		this.fields[x][y] = value;
		this.setChanged();
		this.notifyObservers();
		this.clearChanged();
	}
	
	/**
	 * resets the field, sets all values to 0.
	 */
	public void reset() {
		for(int i = 0; i < 10; i++) {
			for(int j = 0; j < 10; j++) {
				this.setField(i, j, 0);
			}
		}
	}
	
	/**
	 * just for testing purposes.
	 */
	@SuppressWarnings("unused")
	private void print() {
		for(int i = 0; i < 10; i++) {
			for(int j = 0; j < 10; j++) {
				System.out.print(this.fields[i][j] + " ");
			}
			System.out.println();
		}
		System.out.println();
	}
}
