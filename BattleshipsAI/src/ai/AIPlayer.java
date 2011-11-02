package ai;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Observable;
import java.util.SortedMap;
import java.util.TreeMap;

import controller.AttackResult;

import alice.tuprolog.InvalidTheoryException;
import alice.tuprolog.MalformedGoalException;
import alice.tuprolog.NoMoreSolutionException;
import alice.tuprolog.NoSolutionException;
import alice.tuprolog.Prolog;
import alice.tuprolog.SolveInfo;
import alice.tuprolog.Term;
import alice.tuprolog.Theory;

import model.Coordinate;

public class AIPlayer extends AbstractPlayer {

	private Prolog p;
	private Coordinate lastAttack;
	private int shipsFound;
	
	public AIPlayer(HashMap<Integer, Integer> shipNumbers) {
		super(shipNumbers);
		this.initializeKnowledgeBase();
	}

	@Override
	public Coordinate nextMove() {
		while(true) {
			SolveInfo s;
			List<String> list = this.searchForCandidates();
			if(list.size() > 0) {
				//there is a hit but not yet sunk ship.
				int r = (int)((Math.random()) * list.size());
				String c = list.get(r);
				Coordinate co = new Coordinate(Integer.parseInt(c.substring(1, 2)), Integer.parseInt(c.substring(2)));
				this.lastAttack = co;
				return co;
			}
			else {
				//what to do if there isn't a concrete evidence for a ship.
				List<String> candidates = this.findShipCandidates();
				int[] a = this.choseField(candidates);
				String var = "x" + a[0] + "" + a[1];
				try {
					s = p.solve("not(shipPart(" + var + ")), not(sunk(" + var + ")), not(empty(" + var + ")).");
					if(s.isSuccess()) {
						Coordinate c = new Coordinate(a[0], a[1]);
						this.lastAttack = c;
						return c;
					}
				} catch (MalformedGoalException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	private List<String> searchForCandidates() {
		List<String> result = new ArrayList<String>();
		try {
			SolveInfo s = this.p.solve("candidate(X).");
			while(s != null && s.isSuccess()) {
				Term term = s.getVarValue("X");
				result.add(term.toString());
				try {
					s = this.p.solveNext();
				} catch (NoMoreSolutionException e) {
					s = null;
				}
			}
		} catch (MalformedGoalException e) {
			e.printStackTrace();
		} catch (NoSolutionException e) {
			e.printStackTrace();
		}
		return result;
	}

	@Override
	public void update(Observable arg0, Object arg1) {
		AttackResult ar = (AttackResult)arg1;
		if(!ar.isHit()) {
			try {
				this.p.addTheory(new Theory(new Theory("empty(x" + this.lastAttack.getxPosition() + "" + this.lastAttack.getyPosition()) + ")."));
			} catch (InvalidTheoryException e) {
				e.printStackTrace();
			}
		} else if(ar.isHit() && !ar.isSunk()) {
			try {
				this.p.addTheory(new Theory(new Theory("shipPart(x" + this.lastAttack.getxPosition() + "" + this.lastAttack.getyPosition()) + ")."));
				try {
					SolveInfo inf = this.p.solve("ship(S), edge(x" + this.lastAttack.getxPosition() + "" + this.lastAttack.getyPosition() + ", Y), partOf(S, Y).");
					Term t;
					try {
						t = inf.getVarValue("S");
						String st = "partOf(" + t.toString() + ", x" + this.lastAttack.getxPosition() + "" + this.lastAttack.getyPosition() + "). shipPart(x" + this.lastAttack.getxPosition() + "" + this.lastAttack.getyPosition() + ").";
						this.p.addTheory(new Theory(st));
					} catch (NoSolutionException e) {
						String s = "ship(s" + this.shipsFound + "). partOf(s" + this.shipsFound++ + ", x" + this.lastAttack.getxPosition() + "" + this.lastAttack.getyPosition() + "). shipPart(x" + this.lastAttack.getxPosition() + "" + this.lastAttack.getyPosition() + ").";
						this.p.addTheory(new Theory(s));
					}	
				} catch (MalformedGoalException e) {
					e.printStackTrace();
				} 
			} catch (InvalidTheoryException e) {
				e.printStackTrace();
			}
		} else {
			try {
				int n = this.shipNumbers.get(ar.getShipLength());
				this.shipNumbers.put(ar.getShipLength(),  n - 1);
				this.p.addTheory(new Theory(new Theory("sunk(x" + this.lastAttack.getxPosition() + "" + this.lastAttack.getyPosition()) + ")."));
				try {
					SolveInfo inf = this.p.solve("ship(S), edge(X, x" + this.lastAttack.getxPosition() + "" + this.lastAttack.getyPosition() + "), " + "partOf(S, X).");
					Term t = inf.getVarValue("S");
					p.addTheory(new Theory("partOf(" + t.toString() + ", x" + this.lastAttack.getxPosition() + "" + this.lastAttack.getyPosition() + "). sunk(" + t.toString() + "). shipPart(x" + this.lastAttack.getxPosition() + "" + this.lastAttack.getyPosition() + ")."));
					this.markFieldsAroundShip(t.toString());
				} catch (MalformedGoalException e) {
					e.printStackTrace();
				} catch (NoSolutionException e) {
					e.printStackTrace();
				}
			} catch (InvalidTheoryException e) {
				e.printStackTrace();
			}
		}
	}
	
	private void markFieldsAroundShip(String s) {
		try {
			SolveInfo inf = this.p.solve("partOf(" + s + ", X), edge(X, Y), not(shipPart(Y)), not(empty(Y)).");
			while(inf != null && inf.isSuccess()) {
				Term t = inf.getVarValue("Y");
				String theory = "empty(" + t.toString() + ").";
				this.p.addTheory(new Theory(theory));
				try {
					inf = this.p.solveNext();
				} catch (NoMoreSolutionException e) {
					inf = null;
				}
			}
		} catch (MalformedGoalException e) {
			e.printStackTrace();
		} catch (NoSolutionException e) {
			e.printStackTrace();
		} catch (InvalidTheoryException e) {
			e.printStackTrace();
		}
	}
	
	private List<String> findShipCandidates() {
		List<String> candidates = new ArrayList<String>();
		if(this.shipNumbers.get(5) > 0) {
			List<String> five = this.findFive();
			candidates.addAll(five);
		}
		if(this.shipNumbers.get(4) > 0) {
			List<String> four = this.findFour();
			candidates.addAll(four);
		}
		if(this.shipNumbers.get(3) > 0) {
			List<String> three = this.findThree();
			candidates.addAll(three);
		}
		if(this.shipNumbers.get(2) > 0) {
			List<String> two = this.findTwo();
			candidates.addAll(two);
		}
		return candidates;
	}
	
	private List<String> findFive() {
		List<String> five = new ArrayList<String>();
		try {
			SolveInfo inf = this.p.solve("five(A, B, C, D, E).");
			while(inf != null && inf.isSuccess()) { 
				five.add(inf.getVarValue("A").toString() + 
								" " + 
								inf.getVarValue("B").toString() + 
								" " + 
								inf.getVarValue("C").toString() + 
								" " + 
								inf.getVarValue("D").toString() + 
								" " + 
								inf.getVarValue("E").toString());		
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
		return five;
	}
	
	private List<String> findFour() {
		List<String> four = new ArrayList<String>();
		try {
			SolveInfo inf = this.p.solve("four(A, B, C, D).");
			while(inf != null && inf.isSuccess()) { 
				four.add(inf.getVarValue("A").toString() + 
								" " + 
								inf.getVarValue("B").toString() + 
								" " + 
								inf.getVarValue("C").toString() + 
								" " + 
								inf.getVarValue("D").toString());	
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
		return four;
	}
	
	private List<String> findThree() {
		List<String> three = new ArrayList<String>();
		try {
			SolveInfo inf = this.p.solve("three(A, B, C).");
			while(inf != null && inf.isSuccess()) { 
				three.add(inf.getVarValue("A").toString() + 
								" " + 
								inf.getVarValue("B").toString() + 
								" " + 
								inf.getVarValue("C").toString());	
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
		return three;
	}
	
	private List<String> findTwo() {
		List<String> two = new ArrayList<String>();
		try {
			SolveInfo inf = this.p.solve("two(A, B).");
			while(inf != null && inf.isSuccess()) { 
				two.add(inf.getVarValue("A").toString() + 
								" " + 
								inf.getVarValue("B").toString());	
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
		return two;
	}

	private void initializeKnowledgeBase() {
		this.p = new Prolog();
		this.shipsFound = 0;
		try {
		for(int i = 0; i < 10; i++) {
			for(int j = 0; j < 10; j++) {
				if(i + 1 < 10) {
					p.addTheory(new Theory("edge(x" + i + "" + j + ", x" + (i + 1) + "" + j + ")."));
					p.addTheory(new Theory("xEdge(x" + i + "" + j + ", x" + (i + 1) + "" + j + ")."));
				}
				if(i - 1 >= 0) {
					p.addTheory(new Theory("edge(x" + i + "" + j + ", x" + (i - 1) + "" + j + ")."));
					//p.addTheory(new Theory("xEdge(x" + i + "" + j + ", x" + (i - 1) + "" + j + ")."));
				}
				if(j + 1 < 10) {
					p.addTheory(new Theory("edge(x" + i + "" + j + ", x" + i + "" + (j + 1) + ")."));
					p.addTheory(new Theory("yEdge(x" + i + "" + j + ", x" + i + "" + (j + 1) + ")."));
				}
				if(j - 1 >= 0) {
					p.addTheory(new Theory("edge(x" + i + "" + j + ", x" + i + "" + (j - 1) + ")."));
					//p.addTheory(new Theory("yEdge(x" + i + "" + j + ", x" + i + "" + (j - 1) + ")."));
				}
			}
		}
		p.addTheory(new Theory("empty(Y) :- shipPart(X), sunk(X), edge(X, Y), not(shipPart(Y))."));
		p.addTheory(new Theory("candidate(Y) :- ship(S), not(sunk(S)), partOf(S, X), edge(X, Y), not(empty(Y)), not(shipPart(Y)), not(xDirection(S)), not(yDirection(S))."));
		p.addTheory(new Theory("candidate(Y) :- ship(S), not(sunk(S)), partOf(S, X), xEdge(X, Y), not(empty(Y)), not(shipPart(Y)), xDirection(S), not(yDirection(S))."));
		p.addTheory(new Theory("candidate(Y) :- ship(S), not(sunk(S)), partOf(S, X), yEdge(X, Y), not(empty(Y)), not(shipPart(Y)), yDirection(S), not(xDirection(S))."));
		p.addTheory(new Theory("candidate(Y) :- ship(S), not(sunk(S)), partOf(S, X), xEdge(Y, X), not(empty(Y)), not(shipPart(Y)), xDirection(S), not(yDirection(S))."));
		p.addTheory(new Theory("candidate(Y) :- ship(S), not(sunk(S)), partOf(S, X), yEdge(Y, X), not(empty(Y)), not(shipPart(Y)), yDirection(S), not(xDirection(S))."));
		p.addTheory(new Theory("xDirection(S) :- ship(S), partOf(S, X), xEdge(X, Y), partOf(S, Y)."));
		p.addTheory(new Theory("yDirection(S) :- ship(S), partOf(S, X), yEdge(X, Y), partOf(S, Y)."));
		p.addTheory(new Theory("two(A, B) :- xEdge(A, B), not(shipPart(A)), not(shipPart(B)), not(empty(A)), not(empty(B))."));
		p.addTheory(new Theory("two(A, B) :- yEdge(A, B), not(shipPart(A)), not(shipPart(B)), not(empty(A)), not(empty(B))."));
		p.addTheory(new Theory("three(A, B, C) :- xEdge(A, B), xEdge(B, C), not(shipPart(A)), not(shipPart(B)), not(shipPart(C)), not(empty(A)), not(empty(B)), not(empty(C))."));
		p.addTheory(new Theory("three(A, B, C) :- yEdge(A, B), yEdge(B, C), not(shipPart(A)), not(shipPart(B)), not(shipPart(C)), not(empty(A)), not(empty(B)), not(empty(C))."));
		p.addTheory(new Theory("four(A, B, C, D) :- xEdge(A, B), xEdge(B, C), xEdge(C, D), not(shipPart(A)), not(shipPart(B)), not(shipPart(C)), not(shipPart(D)), not(empty(A)), not(empty(B)), not(empty(C)), not(empty(D))."));
		p.addTheory(new Theory("four(A, B, C, D) :- yEdge(A, B), yEdge(B, C), yEdge(C, D), not(shipPart(A)), not(shipPart(B)), not(shipPart(C)), not(shipPart(D)), not(empty(A)), not(empty(B)), not(empty(C)), not(empty(D))."));
		p.addTheory(new Theory("five(A, B, C, D, E) :- xEdge(A, B), xEdge(B, C), xEdge(C, D), xEdge(D, E), not(shipPart(A)), not(shipPart(B)), not(shipPart(C)), not(shipPart(D)), not(shipPart(E)), not(empty(A)), not(empty(B)), not(empty(C)), not(empty(D)), not(empty(E))."));
		p.addTheory(new Theory("five(A, B, C, D, E) :- yEdge(A, B), yEdge(B, C), yEdge(C, D), yEdge(D, E), not(shipPart(A)), not(shipPart(B)), not(shipPart(C)), not(shipPart(D)), not(shipPart(E)), not(empty(A)), not(empty(B)), not(empty(C)), not(empty(D)), not(empty(E))."));
		} catch (InvalidTheoryException e) {
			e.printStackTrace();
		}	
	}
	
	private int[] choseField(List<String> list) {
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
