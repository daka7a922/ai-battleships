package ai;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class AIStatisticsPlayer extends AIPlayer {

	public AIStatisticsPlayer(HashMap<Integer, Integer> shipNumbers) {
		super(shipNumbers);
	}

	@Override
	protected int[] choseField(List<String> list) {
		int[] result = new int[2];
		HashMap<String, Integer> fields = new HashMap<String, Integer>();
		for(String s : list) {
			System.out.println(s);
			String[] array = s.split(" ");
			for(String a : array) {
				if(!fields.keySet().contains(a)) {
					fields.put(a, 1);
				} else {
					int x = fields.get(a);
					fields.put(a, x + 1);
				}
			}
		}
		List<String> fieldList = new ArrayList<String>();
		while(fields.keySet().size() > 0) {
			int max = 0;
			String field = "";
			for(String str : fields.keySet()) {
				if(fields.get(str) > max) {
					max = fields.get(str);
					field = str;
				}
			}
			fields.remove(field);
			fieldList.add(field);
		}
		System.out.println();
		result[0] = Integer.parseInt(fieldList.get(0).substring(1, 2));
		result[1] = Integer.parseInt(fieldList.get(0).substring(2, 3));
		return result;
	}
}
