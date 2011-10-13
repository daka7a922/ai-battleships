package controller;

import java.util.ArrayList;
import java.util.List;

import model.StatisticSet;

import view.Statistics;

public class StatisticsHandler {
	
	private Statistics statisticsView;
	private List<StatisticSet> statistics;
	
	public StatisticsHandler(Statistics statisticsView) {
		this.statisticsView = statisticsView;
		this.statistics = new ArrayList<StatisticSet>();
	}
	
	public void addStatistic(String playerName, int shots, int faults) {
		boolean foundPlayer = false;
		List<String> st = new ArrayList<String>();
		List<Integer> allShotsMax = new ArrayList<Integer>();
		List<Integer> allShotsMin = new ArrayList<Integer>();
		List<Double> allShotsAvg = new ArrayList<Double>();
		List<Integer> maxAbsolute = new ArrayList<Integer>();
		List<Integer> minAbsolute = new ArrayList<Integer>();
		List<Double> avgAbsolute = new ArrayList<Double>();
		List<Double> maxRelative = new ArrayList<Double>();
		List<Double> minRelative = new ArrayList<Double>();
		List<Double> avgRelative = new ArrayList<Double>();
		for(StatisticSet s : this.statistics) {
			if(s.getPlayerName().equals(playerName)) {
				s.addStatistic(shots, faults);
				foundPlayer = true;
			}
		}
		for(StatisticSet s : this.statistics) {
			if(!st.contains(s.getPlayerName())) {
				st.add(s.getPlayerName());
				allShotsMax.add(s.getMaxShots());
				allShotsMin.add(s.getMinShots());
				allShotsAvg.add(s.getAverageShots());
				maxAbsolute.add(s.getMaxFaults());
				minAbsolute.add(s.getMinFaults());
				avgAbsolute.add(s.getAverageFaults());
				maxRelative.add(s.getMaxRelativeFaults());
				minRelative.add(s.getMinRelativeFaults());
				avgRelative.add(s.getAverageRelativeFaults());
			}
		}
		if(!foundPlayer) {
			StatisticSet ss = new StatisticSet(playerName);
			this.statistics.add(ss);
			ss.addStatistic(shots, faults);
			st.add(playerName);
			allShotsMax.add(ss.getMaxShots());
			allShotsMin.add(ss.getMinShots());
			allShotsAvg.add(ss.getAverageShots());
			maxAbsolute.add(ss.getMaxFaults());
			minAbsolute.add(ss.getMinFaults());
			avgAbsolute.add(ss.getAverageFaults());
			maxRelative.add(ss.getMaxRelativeFaults());
			minRelative.add(ss.getMinRelativeFaults());
			avgRelative.add(ss.getAverageRelativeFaults());
		}
		this.statisticsView.setAllShotsPanel(st, allShotsMax, allShotsMin, allShotsAvg);
		this.statisticsView.setAbsoluteFaultsPanel(st, maxAbsolute, minAbsolute, avgAbsolute);
		this.statisticsView.setRelativeFaultsPanel(st, maxRelative, minRelative, avgRelative);
	}
}
