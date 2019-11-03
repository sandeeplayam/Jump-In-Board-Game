/**
 * Class that contains a main method that created an instance of the board class
 * allowing to test the move methods. Should be deleted after done testing as
 * this class was just for testing purposes
 * 
 * @author Tharsan Sivathasan
 *
 */
public class TestModel {

	public static void main(String[] args) {
		TestModel game = new TestModel();
		game.play();
	}

	public TestModel() {

	}

	public void play() {
		Board board = new Board(2);
		System.out.println(toStrind2DArray());

		// Challenge 1
		/*
		 * System.out.println(board.toStringBoard()); board.move(4,2,2,2);
		 * System.out.println(board.toStringBoard()); board.move(2,2,0,2);
		 * System.out.println(board.toStringBoard()); board.move(0,1,0,4);
		 * System.out.println(board.toStringBoard()); board.move(0,2,2,2);
		 * System.out.println(board.toStringBoard());
		 */

		// Challenge 2
		System.out.println(board.toStringBoard());
		board.move(3, 1, 4, 1);
		System.out.println(board.toStringBoard());
	}

	public String toStrind2DArray() {
		String b = "";
		for (int i = 0; i < 5; i++) {
			for (int j = 0; j < 5; j++) {
				b += i + "" + j;
				b += " ";
			}
			b += "\n";
		}
		return b;
	}

}
