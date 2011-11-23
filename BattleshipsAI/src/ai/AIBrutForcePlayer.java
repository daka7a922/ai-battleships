package ai;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import alice.tuprolog.MalformedGoalException;
import alice.tuprolog.NoMoreSolutionException;
import alice.tuprolog.NoSolutionException;
import alice.tuprolog.SolveInfo;

public class AIBrutForcePlayer extends AIPlayer {
	
	private static final int timeout = 10000;

	public AIBrutForcePlayer(HashMap<Integer, Integer> shipNumbers) {
		super(shipNumbers, "Brut Force Player");
	}
	
	@Override
	protected int[] choseField(List<String> list) {
		int[] result = new int[2];
		HashMap<String, Integer> fields = new HashMap<String, Integer>();
		for(String s : list) {
			//System.out.println(s);
			String[] array = s.split(" ");
			for(String a : array) {
				if(!fields.keySet().contains(a)) {
					fields.put(a, 1);
				} else {
					fields.put(a, fields.get(a) + 1);
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
	
	@Override
	protected List<String> findShipCandidates() {
		String query = "";
		List<String> variableList = new ArrayList<String>();
		List<String> candidates = new ArrayList<String>();
		try {
			for (int i = this.shipNumbers.get(5); i > 0; i--) {
				if(query != "") query += ", ";
				variableList.add("F"+i+"F1");
				variableList.add("F"+i+"F2");
				variableList.add("F"+i+"F3");
				variableList.add("F"+i+"F4");
				variableList.add("F"+i+"F5");
				query += "five(F"+i+"F1, F"+i+"F2, F"+i+"F3, F"+i+"F4, F"+i+"F5)";
			}
			for (int i = this.shipNumbers.get(4); i > 0; i--) {
				if(query != "") query += ", ";
				variableList.add("V"+i+"F1");
				variableList.add("V"+i+"F2");
				variableList.add("V"+i+"F3");
				variableList.add("V"+i+"F4");
				query += "four(V"+i+"F1, V"+i+"F2, V"+i+"F3, V"+i+"F4)";
			}
			for (int i = this.shipNumbers.get(3); i > 0; i--) {
				if(query != "") query += ", ";
				variableList.add("D"+i+"F1");
				variableList.add("D"+i+"F2");
				variableList.add("D"+i+"F3");
				query += "three(D"+i+"F1, D"+i+"F2, D"+i+"F3)";
			}
			for (int i = this.shipNumbers.get(2); i > 0; i--) {
				if(query != "") query += ", ";
				variableList.add("Z"+i+"F1");
				variableList.add("Z"+i+"F2");
				query += "two(Z"+i+"F1, Z"+i+"F2)";
			}
			query += ".";
			SolveInfo inf = this.p.solve(query);
			
			int countdown = timeout;
			while(inf != null && inf.isSuccess() && countdown > 0) { 
				countdown--;
				String candidateDesc = "";
				for(String variable : variableList) {
					candidateDesc += inf.getVarValue(variable).toString() + " ";
				}
				candidates.add(candidateDesc);	
				try {
					inf = this.p.solveNext();
				} catch (NoMoreSolutionException e) {
					inf = null;
				}
			}
		} catch (NoSolutionException e1) {
			e1.printStackTrace();
		} catch (MalformedGoalException e) {
			e.printStackTrace();
		}
		return candidates;
	}

}
