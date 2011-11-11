package model;

import java.util.HashMap;

public class Statistic {
	
	private int shots;
	private int faults;
	private long time;
	
	public Statistic(int shots, int faults, long time) {
		this.shots = shots;
		this.faults = faults;
		this.time = time;
	}
	
	public int getShots() {
		return this.shots;
	}
	
	public int getFaults() {
		return this.faults;
	}
	
	public long getTime() {
		return this.time;
	}
	
	@Override
	public String toString() {
		return "{Shots=" + this.shots + ", Faults="+ this.faults + ", Time=" + this.time + "ms}";
	}
	
	
}
