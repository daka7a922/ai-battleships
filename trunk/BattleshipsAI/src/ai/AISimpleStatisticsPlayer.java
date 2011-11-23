package ai;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class AISimpleStatisticsPlayer extends AIPlayer {

	public AISimpleStatisticsPlayer(HashMap<Integer, Integer> shipNumbers) {
		super(shipNumbers, "Simple Statistical Player");
	}

	@Override
	protected int[] choseField(HashMap<Integer, List<String>> hashMap) {
		int[] result = new int[2];
		List<String> list = new ArrayList<String>();
		for(int i = 2; i <= 5; i++) {
			List<String> l = hashMap.get(i);
			if(l != null) {
				list.addAll(l);
			}
		}
		HashMap<String, Integer> fields = new HashMap<String, Integer>();
		for(String s : list) {
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
		result[0] = Integer.parseInt(fieldList.get(0).substring(1, 2));
		result[1] = Integer.parseInt(fieldList.get(0).substring(2, 3));
		return result;
	}
}
