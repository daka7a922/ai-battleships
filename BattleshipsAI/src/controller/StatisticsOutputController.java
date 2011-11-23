package controller;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;

import ai.IPlayer;

public class StatisticsOutputController extends AbstractStatisticsController {
	
	BufferedWriter out;

	public StatisticsOutputController() {
		super();
		try {
			// Create file 
			FileWriter fstream = new FileWriter("D:\\GTITZE\\SemWeb-Teamprojekt\\Results.txt");
			this.out = new BufferedWriter(fstream);	
				this.out.write("\"PlayerName\"\t\"ShipNumbers\"\t\"Shots\"\t\"Fault\"\t\"Time\"");
				this.out.newLine();
		} catch (IOException e) {
			//Catch exception if any
			System.err.println("Error: " + e.getMessage());
		}
	}
	@Override
	public void output() {
		/*for (Settings key : this.statistics.keySet()) {
			for (Statistic statistic : this.statistics.get(key).getStatistics()) {
				System.out.println(this.statistics.get(key).getPlayerName() + ", " + this.statistics.get(key).getShipNumbers() 
						+ " Shots," + statistic.getShots() + " Faults," + statistic.getFaults() + " Time," + statistic.getTime() + " ms");
			}
		}*/	
	}
	
	@Override
	public void addStatistic(Class<? extends IPlayer> playerClass, String playerName, HashMap<Integer, Integer> shipNumbers, int shots, int faults, long duration) {
		super.addStatistic(playerClass, playerName, shipNumbers, shots, faults, duration);
		String strOutput = "\"" + playerName + "\"\t\"" + shipNumbers
				+ "\"\t" + shots + "\t" + faults + "\t" + duration + "";

		try{
			this.out.write(strOutput);
			this.out.newLine();
		}catch (Exception e){
			//Catch exception if any
			System.err.println("Error: " + e.getMessage());
		}
		
	}

	@Override
	public void end() {
		try {
			//Close the output stream
			this.out.close();
		} catch (IOException e) {
			//Catch exception if any
			System.err.println("Error: " + e.getMessage());
		}
	}

}
