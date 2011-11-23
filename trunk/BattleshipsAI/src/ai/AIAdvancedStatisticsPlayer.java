package ai;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import model.Coordinate;

public class AIAdvancedStatisticsPlayer extends AIPlayer {
	
	private List<List<Coordinate>> allCoordinates;
	private List<List<Coordinate>> priorCoordinates;

	public AIAdvancedStatisticsPlayer(HashMap<Integer, Integer> shipNumbers) {
		super(shipNumbers, "Advanced Statistical Player");
	}
	
	@Override
	protected int[] choseField(HashMap<Integer, List<String>> list) {
		int index = 0;
		double probability = 0;
		for(int i = 2; i <= 5; i++) {
			List<String> l = list.get(i);
			double p = 0;
			if(l != null) {
				p = (((this.shipNumbers.get(i)) / (double)l.size()) * 100);
				System.out.println(this.shipNumbers.get(i));
				System.out.println(l.size());
				System.out.println(i);
			}
			if(p >= probability && l != null) {
				System.out.println(i + ": " + p);
				index = i;
				probability = p;
			}
		}
		HashMap<String, Integer> hm = new HashMap<String, Integer>();
		this.allCoordinates = new ArrayList<List<Coordinate>>();
		this.priorCoordinates = new ArrayList<List<Coordinate>>();
		for(int i : list.keySet()) {
			List<String> l = list.get(i);
			for(String s : l) {
				String[] array = s.split(" ");
				List<Coordinate> c = new ArrayList<Coordinate>();
				for(String a : array) {
					c.add(new Coordinate(Integer.parseInt(a.substring(1, 2)), Integer.parseInt(a.substring(2))));
					if(i == index) {
						if(!hm.keySet().contains(a)) {
							hm.put(a, 1);
						} else {
							int x = hm.get(a);
							hm.put(a, x + 1);
						}
					}
				}
				this.allCoordinates.add(c);
				if(i == index) {
					this.priorCoordinates.add(c);
				}
			}
		}
		List<String> fieldList = new ArrayList<String>();
		while(hm.keySet().size() > 0) {
			int max = 0;
			String field = "";
			for(String str : hm.keySet()) {
				if(hm.get(str) > max) {
					max = hm.get(str);
					field = str;
				}
			}
			hm.remove(field);
			fieldList.add(field);
		}
		int[] result = new int[2];
		System.out.println();
		result[0] = Integer.parseInt(fieldList.get(0).substring(1, 2));
		result[1] = Integer.parseInt(fieldList.get(0).substring(2, 3));
		return result;
	}
	
	@Override
	protected boolean checkCoordinate(Coordinate co) {
		int i = this.priorCoordinates.get(0).size();
		if(this.shipNumbers.get(i) == this.priorCoordinates.size()) {
			for(List<Coordinate> c : this.priorCoordinates) {
				boolean last = false;
				boolean next = false;
				for(Coordinate cc : c) {
					if(cc.getxPosition() == co.getxPosition() && cc.getyPosition() == co.getyPosition()) {
						next = true;
					}
					if(cc.getxPosition() == this.lastAttack.getxPosition() && cc.getyPosition() == this.lastAttack.getyPosition()) {
						last = true;
					}
				}
				if(last && next) {
					return true;
				}
			}
			return false;
		}
		for(List<Coordinate> c : this.allCoordinates) {
			boolean last = false;
			boolean next = false;
			for(Coordinate cc : c) {
				if(cc.getxPosition() == co.getxPosition() && cc.getyPosition() == co.getyPosition()) {
					next = true;
				}
				if(cc.getxPosition() == this.lastAttack.getxPosition() && cc.getyPosition() == this.lastAttack.getyPosition()) {
					last = true;
				}
			}
			if(last && next) {
				return true;
			}
		}
		return false;
	}
}
