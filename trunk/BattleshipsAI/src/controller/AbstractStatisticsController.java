package controller;

import java.util.HashMap;

import model.Settings;
import model.Statistic;
import model.StatisticSet;
import ai.IPlayer;

public abstract class AbstractStatisticsController implements
		IStatisticsController {

	protected HashMap<Settings, StatisticSet> statistics;
	
	public AbstractStatisticsController() {
		this.statistics = new HashMap<Settings, StatisticSet>();
	}
	
	@Override
	public void addStatistic(Class<? extends IPlayer> playerClass, String playerName, HashMap<Integer, Integer> shipNumbers, int shots, int faults, long duration) {
		//Put the statistic set in the list
		@SuppressWarnings("unchecked")
		Settings key = new Settings(playerClass, (HashMap<Integer, Integer>)shipNumbers.clone());
		if (this.statistics.get(key) == null) {
			this.statistics.put(key, new StatisticSet(key));
		}
		Statistic s2 = new Statistic(shots, faults, duration);
		this.statistics.get(key).addStatistic(s2);
	}

}
