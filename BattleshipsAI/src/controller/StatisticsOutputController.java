package controller;

import model.Statistic;
import model.StatisticSetKey;

public class StatisticsOutputController extends AbstractStatisticsController {

	@Override
	public void output() {
		for (StatisticSetKey key : this.statistics.keySet()) {
			for (Statistic statistic : this.statistics.get(key).getStatistics()) {
				System.out.println(this.statistics.get(key).getPlayerName() + ", " + this.statistics.get(key).getShipNumbers() 
						+ ": Shots=" + statistic.getShots() + ", Faults=" + statistic.getFaults() + ", Time=" + statistic.getTime() + "ms");
			}
		}		
	}
}
