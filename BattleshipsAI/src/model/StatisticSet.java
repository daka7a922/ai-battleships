package model;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import ai.IPlayer;

public class StatisticSet {

	// #### object variables ####
	 
	StatisticSetKey key;
	private List<Statistic> statistics;
	
	// #### constructor ####
	
	public StatisticSet(StatisticSetKey key) {
		this.key = key;
		this.statistics = new ArrayList<Statistic>();
	}
	
	// #### getter methods for shots characteristics ###
	
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
		int result = this.statistics.get(0).getShots();
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
	
	// #### getter methods for fault characteristics ###
	
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
		int result = this.statistics.get(0).getFaults();
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
		double result =  (this.statistics.get(0).getFaults() * 100) / this.statistics.get(0).getShots();
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
	
	// #### getter methods for time characteristics ###
	
	public long getMaxTime() {
		long result = 0;
		for(Statistic s : this.statistics) {
			if(s.getTime() > result) {
				result = s.getTime();
			}
		}
		return result;
	}
	
	public long getMinTime() {
		long result = this.statistics.get(0).getTime();
		for(Statistic s : this.statistics) {
			if(s.getTime() < result) {
				result = s.getTime();
			}
		}
		return result;
	}
	
	public long getAverageTime() {
		long result = 0;
		for(Statistic s : this.statistics) {
			result = result + s.getTime();
		}
		return (result / this.statistics.size());
	}

	// #### general getter methods ####
	
	public String getPlayerName() {
		try {
			return this.key.getPlayerClass().getConstructor(new Class[]{HashMap.class}).newInstance(new Object[] {new HashMap<Integer, Integer>()}).getPlayerName();
		} catch (NoSuchMethodException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	public Class<? extends IPlayer> getPlayerClass() {
		return this.key.getPlayerClass();
	}
	
	public int getSize() {
		return this.statistics.size();
	}
		
	public HashMap<Integer, Integer> getShipNumbers() {
		return this.key.getShipNumbers();
	}
	
	// #### setter methods ####
	
	public void addStatistic(Statistic statistic) {
		this.statistics.add(statistic);
	}
	
	public List<Statistic> getStatistics() {
		return this.statistics;
	}
}
