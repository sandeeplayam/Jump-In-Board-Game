import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Test;

public class SolverTest {


	@Test
	public void testSolve() {
		
		Board b = new Board(1);
		Solver solver = new Solver(b);
		solver.solve(b,b.getPieces().get(1),new ArrayList<Integer>());
		b.reset();
		for (int z = 0; z < solver.getSol().size(); z += 4) {
			b.move((int) solver.getSol().get(z), (int) solver.getSol().get(z + 1), (int) solver.getSol().get(z + 2),
					(int) solver.getSol().get(z + 3), 1);

		}
		
		assertTrue(b.checkWin());
		
		 b = new Board(3);
		 Solver s2 = new Solver(b);
		s2.solve(b,b.getPieces().get(1),new ArrayList<Integer>());
		b.reset();
		for (int z = 0; z < s2.getSol().size(); z += 4) {
			b.move((int) s2.getSol().get(z), (int) s2.getSol().get(z + 1), (int) s2.getSol().get(z + 2),
					(int) s2.getSol().get(z + 3), 1);

		}
		assertTrue(b.checkWin());

	}
	
	@Test 
	public void testFindSolution() {
		
		Board b = new Board(1);
		Solver solver = new Solver(b);
		ArrayList<Integer> solution = solver.findSolution();
		ArrayList<Integer> test = new ArrayList<Integer>();
		test.add(4);
		test.add(2);
		test.add(2);
		test.add(2);
		
		test.add(2);
		test.add(2);
		test.add(0);
		test.add(2);
		
		test.add(0);
		test.add(1);
		test.add(0);
		test.add(4);
		
		test.add(0);
		test.add(2);
		test.add(2);
		test.add(2);
		
		assertEquals(test,solution);
		
	}



	

}
