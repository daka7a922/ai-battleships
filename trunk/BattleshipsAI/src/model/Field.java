package model;

public class Field {
	
	private int[][] fields;
	
	public Field() {
		for(int i = 0; i < 10; i++) {
			for(int j = 0; j < 10; j++) {
				fields[i][j] = 0;
			}
		}
	}

}
