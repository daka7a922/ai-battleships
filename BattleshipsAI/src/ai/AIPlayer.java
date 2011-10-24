package ai;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Observable;

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
				int[] a = this.random();
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
			List<String> ships = new ArrayList<String>();
			SolveInfo inf = this.p.solve("ship(S).");
			while(inf != null && inf.isSuccess()) {
				Term term = inf.getVarValue("S");
				ships.add(term.toString());
				try {
					inf = this.p.solveNext();
				} catch (NoMoreSolutionException e) {
					inf = null;
				}
			}
			for(String st : ships) {
				SolveInfo i = this.p.solve("ship(" + st + "), partOf(" + st + ", X), xEdge(X, Y), partOf(" + st + ", Y).");
				while(i != null && i.isSuccess()) {
					Term x = i.getVarValue("X");
					Term y = i.getVarValue("Y");
					System.out.println("X = " + x.toString() + ", Y = " + y.toString());
					try {
						i = this.p.solveNext();
					} catch (NoMoreSolutionException e) {
						i = null;
					}
				}
				SolveInfo j = this.p.solve("ship(" + st + "), partOf(" + st + ", X), yEdge(X, Y), partOf(" + st + ", Y).");
				while(j != null && j.isSuccess()) {
					Term x = j.getVarValue("X");
					Term y = j.getVarValue("Y");
					System.out.println("X = " + x.toString() + ", Y = " + y.toString());
					try {
						j = this.p.solveNext();
					} catch (NoMoreSolutionException e) {
						j = null;
					}
				}
			}
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
					p.addTheory(new Theory("xEdge(x" + i + "" + j + ", x" + (i - 1) + "" + j + ")."));
				}
				if(j + 1 < 10) {
					p.addTheory(new Theory("edge(x" + i + "" + j + ", x" + i + "" + (j + 1) + ")."));
					p.addTheory(new Theory("yEdge(x" + i + "" + j + ", x" + i + "" + (j + 1) + ")."));
				}
				if(j - 1 >= 0) {
					p.addTheory(new Theory("edge(x" + i + "" + j + ", x" + i + "" + (j - 1) + ")."));
					p.addTheory(new Theory("yEdge(x" + i + "" + j + ", x" + i + "" + (j - 1) + ")."));
				}
			}
		}
		p.addTheory(new Theory("empty(Y) :- shipPart(X), sunk(X), edge(X, Y), not(shipPart(Y))."));
		p.addTheory(new Theory("candidate(Y) :- ship(S), not(sunk(S)), partOf(S, X), edge(X, Y), not(empty(Y)), not(shipPart(Y)), not(xDirection(S)), not(yDirection(S))."));
		p.addTheory(new Theory("candidate(Y) :- ship(S), not(sunk(S)), partOf(S, X), xEdge(X, Y), not(empty(Y)), not(shipPart(Y)), xDirection(S), not(yDirection(S))."));
		p.addTheory(new Theory("candidate(Y) :- ship(S), not(sunk(S)), partOf(S, X), yEdge(X, Y), not(empty(Y)), not(shipPart(Y)), yDirection(S), not(xDirection(S))."));
		p.addTheory(new Theory("xDirection(S) :- ship(S), partOf(S, X), xEdge(X, Y), partOf(S, Y)."));
		p.addTheory(new Theory("yDirection(S) :- ship(S), partOf(S, X), yEdge(X, Y), partOf(S, Y)."));
		} catch (InvalidTheoryException e) {
			e.printStackTrace();
		}	
	}
	
	private int[] random() {
		int[] result = new int[2];
		result[0] = (int)((Math.random()) * 10);
		result[1] = (int)((Math.random()) * 10);
		return result;
	}
}