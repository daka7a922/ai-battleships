package controller;

import java.util.HashMap;

import view.StatisticsView;

import model.Statistic;
import model.StatisticSet;
import model.StatisticSetKey;

import ai.IPlayer;

public abstract class AbstractStatisticsController implements
		IStatisticsController {

	protected HashMap<StatisticSetKey, StatisticSet> statistics;
	
	public AbstractStatisticsController() {
		this.statistics = new HashMap<StatisticSetKey, StatisticSet>();
	}
	
	@Override
	public void addStatistic(Class<? extends IPlayer> playerClass, String playerName, HashMap<Integer, Integer> shipNumbers, int shots, int faults, long duration) {
		//Put the statistic set in the list
		StatisticSetKey key = new StatisticSetKey(playerClass, shipNumbers);
		if (this.statistics.get(key) == null) {
			this.statistics.put(key, new StatisticSet(key));
		}
		Statistic s2 = new Statistic(shots, faults, duration);
		this.statistics.get(key).addStatistic(s2);
	}

}
