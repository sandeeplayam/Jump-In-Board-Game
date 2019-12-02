
import java.awt.Color;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;

public class Solver {

	private ArrayList<Integer> solution;
	private Queue<ArrayList<Integer>> q;
	private Color lastPiece;
	private boolean allChecked;
	private Board check;
	private HashMap<MovingPiece, Boolean> exceptions;

	public Solver(Board b) {
		allChecked = false;
		q = new LinkedList<ArrayList<Integer>>();
		solution = new ArrayList<Integer>();
		this.check = b;
		lastPiece = Color.PINK;
		exceptions = new HashMap<MovingPiece, Boolean>();

	}

	// setup the board given the current attempt, add exceptions, and check if piece
	// used as obstacle
	public void moveSetupCheck(Board b, ArrayList<Integer> current, Slot start) {

		for (int z = 0; z < current.size(); z += 4) {

			// piece moved so reset it being used as an obstacle
			if (b.getBoard()[current.get(z)][current.get(z + 1)] instanceof Hole) {

				exceptions.put((MovingPiece) ((Hole) b.getBoard()[current.get(z)][current.get(z + 1)]).getGamePiece(),
						false);

				lastPiece = ((MovingPiece) ((Hole) b.getBoard()[current.get(z)][current.get(z + 1)]).getGamePiece())
						.getColor();

			} else {
				exceptions.put((MovingPiece) b.getBoard()[current.get(z)][current.get(z + 1)], false);

				lastPiece = ((MovingPiece) b.getBoard()[current.get(z)][current.get(z + 1)]).getColor();
			}

			// check if any objects used as obstacles
			int loop = getDirectionIndex(z, z + 1, current);

			if (current.get(z) == current.get(z + 2)) {// going left or right

				this.checkUsedPieces(false, b, start, z, loop, z + 1, current);

			} else {// if going up or down

				this.checkUsedPieces(true, b, start, z + 1, loop, z, current);

			}

			b.move(current.get(z), current.get(z + 1), current.get(z + 2), current.get(z + 3), 1);

		}

	}

	public boolean checkIfMovesWin(ArrayList<Integer> moves, Board b) {
		// loop to make all moves in current attempt
		for (int z = 0; z < moves.size(); z += 4) {
			b.move((int) moves.get(z), (int) moves.get(z + 1), (int) moves.get(z + 2), (int) moves.get(z + 3), 1);

		}

		// if attempt resulted in a win, record moves as solution and return
		if (b.checkWin()) {
			this.solution = moves;
			return true;

		}
		return false;
	}

	public void checkUsedPieces(boolean vertical, Board b, Slot start, int coordinate, int direction, int initial,
			ArrayList<Integer> current) {

		for (int d = current.get(initial); d != current.get(initial + 2) - direction;) {

			d += direction;

			if (vertical && b.getBoard()[d][current.get(coordinate)] instanceof MovingPiece) {

				if (((MovingPiece) b.getBoard()[d][current.get(coordinate)]).getColor()
						.equals(((MovingPiece) start).getColor())) {

					if (b.getBoard()[d][current.get(coordinate)] instanceof Hole
							&& ((Hole) b.getBoard()[d][current.get(coordinate)]).hasRabbit()) {

						exceptions.put((MovingPiece) ((Hole) b.getBoard()[d][current.get(coordinate)]).getGamePiece(),
								true);
					} else {
						exceptions.put((MovingPiece) start, true);
					}
				}

			} else if (!vertical && b.getBoard()[current.get(coordinate)][d] instanceof MovingPiece) {

				if (((MovingPiece) b.getBoard()[current.get(coordinate)][d]).getColor()
						.equals(((MovingPiece) start).getColor())) {

					if (b.getBoard()[current.get(coordinate)][d] instanceof Hole
							&& ((Hole) b.getBoard()[current.get(coordinate)][d]).hasRabbit()) {

						exceptions.put((MovingPiece) ((Hole) b.getBoard()[current.get(coordinate)][d]).getGamePiece(),
								true);
					} else {
						exceptions.put((MovingPiece) start, true);
					}
				}
			}
		}
	}

	public int getDirectionIndex(int x, int y, ArrayList<Integer> move) {

		int index = 0;

		// check which direction the rabbit was hoping
		if (move.get(y) < move.get(y + 2) || move.get(x) < move.get(x + 2)) {// going right or down

			index = 1;

		} else if (move.get(y) > move.get(y + 2) || move.get(x) > move.get(x + 2)) {// going left or up

			index = -1;
		}

		return index;

	}

	public void solve(Board b, Slot start, ArrayList<Integer> current) {

		// if first try
		if (q.isEmpty()) {
			allChecked = true;
		}

		ArrayList<Integer> attempt = new ArrayList<Integer>();

		if (!current.isEmpty()) {// if not the first try

			// setup the board using list of given moves (current), and check
			// exceptions/obstacles
			this.moveSetupCheck(b, current, start);

			// add current list of moves to attempt being made
			attempt.addAll(current);

		} else {// first move or node

			// add current attempt to queue
			q.add(new ArrayList<Integer>(attempt));
		}

		// keep searching until queue is empty(no more moves to check) or solution is
		// found
		while (solution.isEmpty() && !q.isEmpty()) {

			System.out.println(attempt);

			if (start.getClass() == Fox.class && lastPiece.equals(((Fox) start).getColor())) {
				// skip

			} else {
				// get list of possible moves
				ArrayList<Integer> Moves = ((MovingPiece) start).possibleMoves(b);

				// if moves are possible
				if (!Moves.isEmpty()) {

					// add current position to current attempt
					attempt.add(start.getX());
					attempt.add(start.getY());

					// initialize variables used for traversing the board
					int x = 1;
					int y = 1;
					// check list of moves
					for (int i = 0; i < Moves.size(); i++) {

						if (i % 2 == 0) {// if checking direction value in the list
							switch (Moves.get(i)) {
							case 1:// up
								x = -1;
								y = 0;
								break;
							case 2:// down
								x = 1;
								y = 0;
								break;
							case 3:// right
								y = 1;
								x = 0;
								break;
							case 4:// left
								y = -1;
								x = 0;
								break;

							}

						} else {// checking number of spaces that can be jumped to

							// loop for the amount of spaces a fox can move
							for (int n = Moves.get(i); n > 0; n--) {
								// initial position
								int gox = start.getX();
								int goy = start.getY();

								// set rabbit from hole
								if (start.getClass() == Hole.class) {

									start = ((Rabbit) ((Hole) start).getGamePiece());

								} else if (start.getClass() == Fox.class && !((Fox) start).forward(Moves.get(i - 1))) {
									gox = ((Fox) start).getTailX();
									goy = ((Fox) start).getTailY();

								}

								// if destination coordinates are outside the board, check next move
								if ((gox + (x * n)) < 0 || (goy + (y * n)) < 0 || (gox + (x * n)) > 4
										|| (goy + (y * n)) > 4) {

									break;
								}

								// if rabbit can undo and undo was successful (boolean undo, undos then returns
								// if the undo was successful)
								if (start.getClass() != Fox.class && ((MovingPiece) start).canUndo()
										&& ((MovingPiece) start).undo(b.getBoard())) {

									// record position of rabbit after undo
									int tempx = start.getX();
									int tempy = start.getY();
									// redo to current state
									((MovingPiece) start).redo(b.getBoard());

									// if coordinates of undo are the same as the coordinates of the current move
									// then rabbit is trying to backtrack
									if (tempx == (gox + (x * n)) && tempy == (goy + (y * n))) {

										if (exceptions.get((MovingPiece) start).booleanValue() == false) {

											// remove move from list
											Moves.remove(i - 1);
											Moves.remove(i - 1);
											// decrement counter index
											i -= 2;
											// check next possible move
											break;

										}

									}
								}

								// add destination coordinates in current attempt
								attempt.add(gox + (x * n));
								attempt.add(goy + (y * n));
								// add current attempt to queue
								q.add(new ArrayList<Integer>(attempt));
								// change lastFox color to a color not given to a fox, so it's allowed to make a
								// move next turn
								lastPiece = ((MovingPiece) start).getColor();
								// reset the board to original state
								b.reset();

								if (this.checkIfMovesWin(attempt, b)) {
									return;
								}

								// remove destination coordinates from attempt to leave space for next possible
								// move by the same piece
								attempt.remove(attempt.size() - 1);
								attempt.remove(attempt.size() - 1);
								// undo last move if possible
								if (b.canUndo()) {
									b.undo();
								}

								if (start.getClass() != Fox.class) {
									break;
								}

							}
						}

					}

				}
			}

			// if all children were checked (true for the first try by default)
			if (allChecked) {

				if (!q.isEmpty() && q.peek().isEmpty()) {// if first try and queue is not empty
					// remove attempt from queue and add to list attempts
					q.poll();
				}
				// reset all children checked
				allChecked = false;

				if (!q.isEmpty()) {
					// for all the pieces in the board
					for (MovingPiece s : b.getGamePieces()) {

						exceptions.put(s, false);
						// reset the board
						b.reset();
						// piece tries to make a move given the attempt in the head of the queue
						this.solve(b, (Slot) s, q.peek());

					}
					// if all pieces checked
					allChecked = true;

					if (q.size() > 0) {
						// remove current attempt from queue and add to list of attempts
						// and get next attempt from queue
						q.poll();
						if (!q.isEmpty()) {
							current = q.peek();

							b.reset();

							// setup the board given the current attempt
							this.moveSetupCheck(b, current, start);

							// clear attempt and add the current attempt then loop back
							attempt.clear();
							attempt.addAll(current);
						}
					}
				}

				// if all children aren't checked break out of recursion
			} else {
				break;
			}

		}

	}

	public ArrayList<Integer> getSol() {
		return this.solution;
	}

	public ArrayList<Integer> findSolution() {

		ArrayList<Integer> answer = new ArrayList<Integer>();

		for (MovingPiece piece : check.getGamePieces()) {

			exceptions.put(piece, false);
			this.solution.clear();
			check.reset();

			if (!piece.possibleMoves(check).isEmpty()) {

				if (piece instanceof Hole) {

					this.solve(check, ((Hole) piece).getGamePiece(), new ArrayList<Integer>());

				} else {
					this.solve(check, (Slot) piece, new ArrayList<Integer>());
				}
			}

			if (answer.isEmpty()) {
				answer = new ArrayList<Integer>(this.getSol());
			} else if (!this.getSol().isEmpty() && this.getSol().size() < answer.size()) {
				answer = new ArrayList<Integer>(this.getSol());
			}

			q.clear();
			exceptions.clear();
			lastPiece = Color.PINK;
		}

		return answer;
	}
}
