package ai;

import alice.tuprolog.InvalidTheoryException;
import alice.tuprolog.MalformedGoalException;
import alice.tuprolog.NoMoreSolutionException;
import alice.tuprolog.NoSolutionException;
import alice.tuprolog.Prolog;
import alice.tuprolog.SolveInfo;
import alice.tuprolog.Term;
import alice.tuprolog.Theory;

public class Test {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Prolog p = new Prolog();

		try {
			p.addTheory(new Theory("field(n0)."));
			p.addTheory(new Theory("field(n1)."));
			//p.addTheory(new Theory("field(n0x1)."));
			//p.addTheory(new Theory("field(n1x1)."));	
			p.addTheory(new Theory("ship(A) :- not(empty(A)), field(A)."));
			p.addTheory(new Theory("empty(n1) :- ship(n0)."));
			p.addTheory(new Theory("empty(n0) :- ship(n1)."));
		} catch (InvalidTheoryException e) {
			e.printStackTrace();
		}
		try {
			SolveInfo inf = p.solve("ship(A).");
			while(inf != null && inf.isSuccess()) {
				try {
					System.out.println(inf.getVarValue("A").toString());
					try {
						inf = p.solveNext();
					} catch (NoMoreSolutionException e) {
						inf = null;
					}
				} catch (NoSolutionException e) {
					e.printStackTrace();
				}
			}
		} catch (MalformedGoalException e) {
			e.printStackTrace();
		}

		System.out.println("Ready");
	}

}
