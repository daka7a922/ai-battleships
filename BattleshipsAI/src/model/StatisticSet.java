package model;

import java.util.ArrayList;
import java.util.List;

public class StatisticSet {

	private String playerName;
	private List<Statistic> statistics;
	
	public StatisticSet(String playerName) {
		this.playerName = playerName;
		this.statistics = new ArrayList<Statistic>();
	}
	
	public int getMaxShots() {
		int result = 0;
		for(Statistic s : this.statistics) {
			if(s.getShots() > result) {
				result = s.getShots();
			}
		}
		return result;
	}
	
	public int getMinShots() {
		int result = 100;
		for(Statistic s : this.statistics) {
			if(s.getShots() < result) {
				result = s.getShots();
			}
		}
		return result;
	}
	
	public double getAverageShots() {
		double result = 0;
		for(Statistic s : this.statistics) {
			result = result + s.getShots();
		}
		return (result / this.statistics.size());
	}
	
	public int getMaxFaults() {
		int result = 0;
		for(Statistic s : this.statistics) {
			if(s.getFaults() > result) {
				result = s.getFaults();
			}
		}
		return result;
	}
	
	public int getMinFaults() {
		int result = 100;
		for(Statistic s : this.statistics){
			if(s.getFaults() < result) {
				result = s.getFaults();
			}
		}
		return result;
	}
	
	public double getAverageFaults() {
		double result = 0;
		for(Statistic s : this.statistics) {
			result = result + s.getFaults();
		}
		return (result / this.statistics.size());
	}
	
	public double getMaxRelativeFaults() {
		double result = 0;
		for(Statistic s : this.statistics) {
			double x = ((s.getFaults() * 100) / s.getShots());
			if(x > result) {
				result = x;
			}
		}
		return result;
	}
	
	public double getMinRelativeFaults() {
		double result = 100;
		for(Statistic s : this.statistics) {
			double x = ((s.getFaults() * 100) / s.getShots());
			if(x < result) {
				result = x;
			}
		}
		return result;
	}
	
	public double getAverageRelativeFaults() {
		double result = 0;
		for(Statistic s : this.statistics) {
			result = result + ((s.getFaults() * 100) / s.getShots());
		}
		return (result / this.statistics.size());
	}
	
	public String getPlayerName() {
		return this.playerName;
	}
	
	public void addStatistic(int shots, int faults) {
		this.statistics.add(new Statistic(shots, faults));
	}
	
	public int getSize() {
		return this.statistics.size();
	}
}
