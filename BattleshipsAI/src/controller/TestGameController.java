package controller;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import view.MainFrame;
import view.PlaygroundView;
import view.SettingsView;
import view.StatisticsView;

import ai.AIPlayer;
import ai.IPlayer;
import ai.RandomPlayer;
import ai.MediumPlayer;

public class TestGameController {
	/**
	 * @param args
	 */
	public static void main(String[] args) {

		IStatisticsController statisticsController = new StatisticsOutputController();
		GameController gameController = new GameController(statisticsController);
		IPlayer player;
		
		List<Integer[]> shipNumbersList = new ArrayList<Integer[]>();
		shipNumbersList.add(new Integer[]{1,2,3,4});
		//shipNumbersList.add(new Integer[]{1,2,3,4});
		//shipNumbersList.add(new Integer[]{1,2,3,4});
		//shipNumbersList.add(new Integer[]{1,2,3,4});
		
		List<Class<? extends IPlayer>> playersList = new ArrayList<Class<? extends IPlayer>>();
		playersList.add(RandomPlayer.class);
		playersList.add(MediumPlayer.class);
		
		
		for (Class<? extends IPlayer> playerClass : playersList) {
			for (Integer[] shipNumbersArray : shipNumbersList) {
				//Create HashMap for shipNumbers
				HashMap<Integer, Integer> map = new HashMap<Integer, Integer>();
				map.put(5,shipNumbersArray[3]);
				map.put(4,shipNumbersArray[2]);
				map.put(3,shipNumbersArray[1]);
				map.put(2,shipNumbersArray[0]);
				
				//Instantiate Player
				try {
					player = playerClass.getConstructor(new Class[]{HashMap.class}).newInstance(new Object[] {map});
					gameController.setPlayer(player);
					gameController.setShipNumbers(map);
					gameController.playGames(10);
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
			}
		}
		statisticsController.output();
	}
}
