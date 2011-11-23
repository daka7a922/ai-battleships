package controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import model.Settings;
import model.StatisticSet;
import view.StatisticsView;

public class StatisticsViewController extends AbstractStatisticsController {

	private StatisticsView statisticsView;
	
	public StatisticsViewController(StatisticsView statisticsView) {
		super();
		this.statisticsView = statisticsView;
	}
	
	@Override
	public void output() {
		List<String> playerNameList= new ArrayList<String>();
		List<HashMap<Integer, Integer>> shipNumbersList = new ArrayList<HashMap<Integer, Integer>>();
		List<Integer> allShotsMax = new ArrayList<Integer>();
		List<Integer> allShotsMin = new ArrayList<Integer>();
		List<Double> allShotsAvg = new ArrayList<Double>();
		List<Integer> maxAbsolute = new ArrayList<Integer>();
		List<Integer> minAbsolute = new ArrayList<Integer>();
		List<Double> avgAbsolute = new ArrayList<Double>();
		List<Double> maxRelative = new ArrayList<Double>();
		List<Double> minRelative = new ArrayList<Double>();
		List<Double> avgRelative = new ArrayList<Double>();
		List<Long> maxTime = new ArrayList<Long>();
		List<Long> minTime = new ArrayList<Long>();
		List<Long> avgTime = new ArrayList<Long>();
		List<Integer> countList = new ArrayList<Integer>();
		
		for(Settings sk : this.statistics.keySet()) {
			StatisticSet s = this.statistics.get(sk);
			playerNameList.add(s.getPlayerName());
			shipNumbersList.add(sk.getShipNumbers());
			allShotsMax.add(s.getMaxShots());
			allShotsMin.add(s.getMinShots());
			allShotsAvg.add(s.getAverageShots());
			maxAbsolute.add(s.getMaxFaults());
			minAbsolute.add(s.getMinFaults());
			avgAbsolute.add(s.getAverageFaults());
			maxRelative.add(s.getMaxRelativeFaults());
			minRelative.add(s.getMinRelativeFaults());
			avgRelative.add(s.getAverageRelativeFaults());
			maxTime.add(s.getMaxTime());
			minTime.add(s.getMinTime());
			avgTime.add(s.getAverageTime());
			countList.add(s.getSize());
		}
		
		this.statisticsView.setAllShotsPanel(playerNameList, allShotsMax, allShotsMin, allShotsAvg);
		this.statisticsView.setAbsoluteFaultsPanel(playerNameList, maxAbsolute, minAbsolute, avgAbsolute);
		this.statisticsView.setRelativeFaultsPanel(playerNameList, maxRelative, minRelative, avgRelative);
		this.statisticsView.setTimePanel(playerNameList, maxTime, minTime, avgTime);
		this.statisticsView.setLabelsPanel(playerNameList, shipNumbersList, countList);
		
	}

	@Override
	public void end() {
		// TODO Auto-generated method stub
		
	}
}
