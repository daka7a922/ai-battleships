

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import controller.GameController;
import controller.IStatisticsController;
import controller.StatisticsOutputController;

import model.Settings;

import ai.AIAdvancedStatisticsPlayer;
import ai.AIBrutForcePlayer;
import ai.AIPlayer;
import ai.AIStatisticsPlayer;
import ai.IPlayer;
import ai.MediumPlayer;
import ai.RandomPlayer;

public class MainEvaluationController {
	
	private static final int runs = 10;
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		System.out.println("START " + System.currentTimeMillis());

		IStatisticsController statisticsController = new StatisticsOutputController();
		GameController gameController = new GameController(statisticsController);
		
		List<Integer[]> shipNumbersList = new ArrayList<Integer[]>();
		shipNumbersList.add(new Integer[]{0,0,0,2});
		shipNumbersList.add(new Integer[]{0,0,2,0});
		shipNumbersList.add(new Integer[]{0,2,0,0});
		shipNumbersList.add(new Integer[]{2,0,0,0});
		shipNumbersList.add(new Integer[]{0,0,1,2});
		shipNumbersList.add(new Integer[]{0,1,0,2});
		shipNumbersList.add(new Integer[]{1,0,0,2});
		shipNumbersList.add(new Integer[]{0,0,2,2});
		shipNumbersList.add(new Integer[]{0,1,1,2});
		shipNumbersList.add(new Integer[]{0,2,0,2});
		shipNumbersList.add(new Integer[]{1,1,0,2});
		shipNumbersList.add(new Integer[]{2,0,0,2});
		shipNumbersList.add(new Integer[]{1,1,1,1});
		shipNumbersList.add(new Integer[]{1,1,1,2});
		shipNumbersList.add(new Integer[]{1,1,2,1});
		shipNumbersList.add(new Integer[]{1,2,1,1});
		shipNumbersList.add(new Integer[]{2,1,1,1});
		
		List<Class<? extends IPlayer>> playersList = new ArrayList<Class<? extends IPlayer>>();
		playersList.add(RandomPlayer.class);
		playersList.add(MediumPlayer.class);
		playersList.add(AIPlayer.class);
		playersList.add(AIStatisticsPlayer.class);
		playersList.add(AIAdvancedStatisticsPlayer.class);
		playersList.add(AIBrutForcePlayer.class);
		
		
		
		for (Class<? extends IPlayer> playerClass : playersList) {
			for (Integer[] shipNumbersArray : shipNumbersList) {
				//Create HashMap for shipNumbers
				HashMap<Integer, Integer> map = new HashMap<Integer, Integer>();
				map.put(5,shipNumbersArray[3]);
				map.put(4,shipNumbersArray[2]);
				map.put(3,shipNumbersArray[1]);
				map.put(2,shipNumbersArray[0]);
				
				//Initialize Settings
				gameController.setSettings(new Settings(playerClass, map));

				//Play
				for (int i = 0; i < MainEvaluationController.runs; i++) {
					gameController.playGame();
				}
			}
		}
		System.out.println("FINISHED " + System.currentTimeMillis());
		statisticsController.end();
	}
}
