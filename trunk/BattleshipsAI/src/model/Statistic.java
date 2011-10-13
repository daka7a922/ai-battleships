package model;

public class Statistic {
	
	private int shots;
	private int faults;
	
	public Statistic(int shots, int faults) {
		this.shots = shots;
		this.faults = faults;
	}
	
	public int getShots() {
		return this.shots;
	}
	
	public int getFaults() {
		return this.faults;
	}
}
