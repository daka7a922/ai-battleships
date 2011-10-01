package model;

import java.util.Observable;

public class Field extends Observable {
	
	private int[][] fields;
	
	public Field() {
		this.fields = new int[10][10];
		for(int i = 0; i < 10; i++) {
			for(int j = 0; j < 10; j++) {
				fields[i][j] = 0;
			}
		}
	}
	
	public int[][] getFields() {
		return this.fields;
	}
	
	public void setField(int x, int y, int value) {
		this.fields[x][y] = value;
		this.setChanged();
		this.notifyObservers();
		this.clearChanged();
	}

}
