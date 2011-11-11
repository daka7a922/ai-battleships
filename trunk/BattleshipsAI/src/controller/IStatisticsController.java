package controller;

import java.util.HashMap;

import ai.IPlayer;

public interface IStatisticsController {
	void addStatistic(Class<? extends IPlayer> playerClass, String playerName, HashMap<Integer, Integer> shipNumbers, int shots, int faults, long duration);
	void output();
}
