import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Test;

public class SolverTest {

	@Test
	public void testSolve() {
		Board b = new Board(1);
		Solver solver = new Solver(b);
//		solver.solve(b,b.getGamePieces().get(1),new ArrayList<Integer>());
		solver.findSolution();
		b.reset();
		for (int z = 0; z < solver.getSol().size(); z += 4) {
			b.move((int) solver.getSol().get(z), (int) solver.getSol().get(z + 1), (int) solver.getSol().get(z + 2),
					(int) solver.getSol().get(z + 3), 1);

		}

		assertTrue(b.checkWin());

		b = new Board(3);
		Solver s2 = new Solver(b);
//		s2.solve(b,b.getGamePieces().get(1),new ArrayList<Integer>());
		s2.findSolution();
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
		solver.findSolution();
		ArrayList<Integer> solution = solver.getSol();
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

		assertEquals(test, solution);
	}
}
