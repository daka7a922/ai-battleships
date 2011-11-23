package ai;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Observable;
import model.AttackResult;
import model.Field;

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
	
	protected Prolog p;
	protected Coordinate lastAttack;
	protected int shipsFound;
	
	@SuppressWarnings("unchecked")
	public AIPlayer(HashMap<Integer, Integer> shipNumbers) {
		super((HashMap<Integer, Integer>)shipNumbers.clone(), "AI Player");
		this.initializeKnowledgeBase();
	}
	
	@SuppressWarnings("unchecked")
	public AIPlayer(HashMap<Integer, Integer> shipNumbers, String playerName) {
		super((HashMap<Integer, Integer>)shipNumbers.clone(), playerName);
		this.initializeKnowledgeBase();
	}

	@Override
	public Coordinate nextMove() {
		while(true) {
			SolveInfo s;
			List<String> list = this.searchForCandidates();
			if(list.size() > 0) {
				while(true) {
					//there is a hit but not yet sunk ship.
					int r = (int)((Math.random()) * list.size());
					String c = list.get(r);
					Coordinate co = new Coordinate(Integer.parseInt(c.substring(1, 2)), Integer.parseInt(c.substring(2)));
					this.lastAttack = co;
					if(this.checkCoordinate(co)) {
						return co;
					}
				}
			}
			else {
				//what to do if there isn't a concrete evidence for a ship.
				HashMap<Integer, List<String>> candidates = this.findShipCandidates();
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
	
	protected boolean checkCoordinate(Coordinate co) {
		return true;
	}
	
	protected List<String> searchForCandidates() {
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
	
	protected int[] choseField(HashMap<Integer, List<String>> list) {
		int[] result = new int[2];
		result[0] = (int)(Math.random() * 10);
		result[1] = (int)(Math.random() * 10);
		return result;
	}
	
	protected HashMap<Integer, List<String>> findShipCandidates() {
		HashMap<Integer, List<String>> candidates = new HashMap<Integer, List<String>>();
		if(this.shipNumbers.get(5) > 0) {
			List<String> five = this.findFive();
			candidates.put(5, five);
		}
		if(this.shipNumbers.get(4) > 0) {
			List<String> four = this.findFour();
			candidates.put(4, four);
		}
		if(this.shipNumbers.get(3) > 0) {
			List<String> three = this.findThree();
			candidates.put(3, three);
		}
		if(this.shipNumbers.get(2) > 0) {
			List<String> two = this.findTwo();
			candidates.put(2, two);
		}
		return candidates;
	}
	
	protected List<String> findFive() {
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
	
	protected List<String> findFour() {
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
	
	protected List<String> findThree() {
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
	
	protected List<String> findTwo() {
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
	
	protected void markFieldsAroundShip(String s) {
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

	protected void initializeKnowledgeBase() {
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

	@Override
	public void update(Observable arg0, Object arg1) {
		AttackResult ar = (AttackResult)arg1;
		if(ar.getResult() == Field.EMPTY) {
			try {
				this.p.addTheory(new Theory(new Theory("empty(x" + this.lastAttack.getxPosition() + "" + this.lastAttack.getyPosition()) + ")."));
			} catch (InvalidTheoryException e) {
				e.printStackTrace();
			}
		} else if(ar.getResult() == Field.HIT) {
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
		} else if(ar.getResult() == Field.SUNK) {
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
}
