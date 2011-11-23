package ai;

import alice.tuprolog.NoMoreSolutionException;
import alice.tuprolog.Prolog;
import alice.tuprolog.SolveInfo;
import alice.tuprolog.Theory;

public class GetProbableFields {
	
	private Prolog p;

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		GetProbableFields t = new GetProbableFields();
		t.initializeKnowledgeBase(2,2);
		t.getPossibilities();
	}
	
	private void initializeKnowledgeBase(int x, int y) {
		this.p = new Prolog();
		try {
			// Edges between fields and direction of edge
			for(int i = 0; i < x; i++) {
				for(int j = 0; j < y; j++) {
					p.addTheory(new Theory("field(n" + i + "x" + j + ")."));	
					System.out.println("field(n" + i + "x" + j + ").");
					//
					if(i + 1 < x) {
						p.addTheory(new Theory("xEdge(n" + i + "x" + j + ", n" + (i + 1) + "x" + j + ")."));
						System.out.println("xEdge(n" + i + "x" + j + ", n" + (i + 1) + "x" + j + ").");
					}
					if(i - 1 >= 0) {
						p.addTheory(new Theory("xEdge(n" + i + "x" + j + ", n" + (i - 1) + "x" + j + ")."));
						System.out.println("xEdge(n" + i + "x" + j + ", n" + (i - 1) + "x" + j + ").");
					}
					if(j + 1 < y) {
						p.addTheory(new Theory("yEdge(n" + i + "x" + j + ", n" + i + "x" + (j + 1) + ")."));
						System.out.println("yEdge(n" + i + "x" + j + ", n" + i + "x" + (j + 1) + ").");
					}
					if(j - 1 >= 0) {
						p.addTheory(new Theory("yEdge(n" + i + "x" + j + ", n" + i + "x" + (j - 1) + ")."));
						System.out.println("yEdge(n" + i + "x" + j + ", n" + i + "x" + (j - 1) + ").");
					}
				}
			}
			//p.addTheory(new Theory("edge(X, Y) :- yEdge(X, Y)."));
			//p.addTheory(new Theory("edge(X, Y) :- xEdge(X, Y)."));
			
			
			// Ships
			//p.addTheory(new Theory("field1(A) :- field(A), not("));
			//p.addTheory(new Theory("field2(A) :- field(A)"));
			//p.addTheory(new Theory("partOfShip(A) :- ship1l1(A)."));
			//p.addTheory(new Theory("ship1l1(A) :- partOfShip1(A)."));
			//p.addTheory(new Theory("ship2l1(A) :- partOfShip2(A)."));
			p.addTheory(new Theory("partOfShip1(A) :- not(empty(A)), field(A)."));
			System.out.println("partOfShip1(A) :- not(empty(A)), field(A).");
			p.addTheory(new Theory("partOfShip2(A) :- not(empty(A)), field(A)."));
			System.out.println("partOfShip2(A) :- not(empty(A)), field(A).");
			p.addTheory(new Theory("not(partOfShip1(A)) :- partOfShip1(B)."));
			System.out.println("not(partOfShip1(A)) :- partOfShip1(B).");
			p.addTheory(new Theory("not(partOfShip2(A)) :- partOfShip2(B)."));
			System.out.println("not(partOfShip2(A)) :- partOfShip2(B).");
			//p.addTheory(new Theory("not(partOfShip2(A)) :- partOfShip1(B)."));
			//p.addTheory(new Theory("not(partOfShip1(A)) :- partOfShip2(B)."));
			//p.addTheory(new Theory("empty(X) :- partOfShip(A), not(partOfShip(X)), yEdge(A, X)."));
			//p.addTheory(new Theory("empty(X) :- partOfShip(A), xEdge(A, X)."));
			//p.addTheory(new Theory("not(partOfShip(A)) :- empty(A)."));
			//p.addTheory(new Theory("partOfShip(n1x1)."));
			//p.addTheory(new Theory("partOfShip(n2x2)."));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	void getPossibilities() {
		try {
			System.out.println("#### Solution ####");
			//SolveInfo solution = this.p.solve("ship1l1(A), ship2l1(B).");
			SolveInfo solution = this.p.solve("partOfShip1(A), partOfShip1(B).");
			while(solution != null && solution.isSuccess()) {				
				//Term term = solution.getVarValue("A");
				System.out.println(solution.getVarValue("A").toString() + " " + solution.getVarValue("B").toString());
				try {
					solution = this.p.solveNext();
				} catch (NoMoreSolutionException e) {
					solution = null;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
