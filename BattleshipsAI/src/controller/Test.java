package controller;

import java.util.HashMap;

import ai.AIPlayer;
import ai.IPlayer;

import model.Settings;
import model.StatisticSet;
import model.StatisticSetKey;

public class Test {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		
		// TODO Auto-generated method stub
		HashMap<StatisticSetKey, Integer> map = new HashMap<StatisticSetKey, Integer>();
		HashMap<Integer, Integer> map1 = new HashMap<Integer, Integer>();
		map1.put(1, 1);
		StatisticSetKey si1 = new StatisticSetKey(AIPlayer.class, map1);
		HashMap<Integer, Integer> map2 = new HashMap<Integer, Integer>();
		map2.put(1, 1);
		StatisticSetKey si2 = new StatisticSetKey(AIPlayer.class, map2);
		map.put(si1, 1);
		System.out.println(map1.equals(map2) + " " + AIPlayer.class.equals(AIPlayer.class) + " " + si1.equals(si2));
		System.out.println(map.get(si2));
		
	}

}
