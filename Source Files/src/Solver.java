import java.awt.Color;
import java.awt.Point;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;

public class Solver {

	private ArrayList<ArrayList<Integer>> attempts2;
	private ArrayList<Integer> solution;
	private Queue<ArrayList<Integer>> q;
	private HashSet<Color> exceptions;
	private HashMap<Slot, Point> lastpos;
	private HashMap<Slot, Point> foxTail;
	private boolean moveMade;
	private Color lastFox;
	private boolean allChecked;
	private int levelNumber;

	private Board check;

	public Solver(Board b) {
		levelNumber = 1;
		allChecked = false;
		q = new LinkedList<ArrayList<Integer>>();
		exceptions = new HashSet<Color>();
		lastpos = new HashMap<>();
		foxTail = new HashMap<>();
		moveMade = false;
		solution = new ArrayList<Integer>();
		attempts2 = new ArrayList<ArrayList<Integer>>();
		this.check = b;
		lastFox = Color.PINK;

	}

	public void solve(Board b, Slot start, ArrayList<Integer> current) {

		Iterator<Color> exceptionsItr = exceptions.iterator();
		// if first try
		if (attempts2.isEmpty()) {
			allChecked = true;
		}
		moveMade = false;
		System.out.println("Start " + start.getX() + " " + start.getY());
		// System.out.println(board.getBoard()[start.getX()][start.getY()].getClass());
		System.out.println(check.getBoard()[start.getX()][start.getY()].getClass());
		ArrayList<Integer> attempt = new ArrayList<Integer>();
		if (!current.isEmpty()) {
			System.out.println("curr" + current);
			for (int z = 0; z < current.size(); z += 4) {

				check.move(current.get(z), current.get(z + 1), current.get(z + 2), current.get(z + 3), 1);
				System.out.println("setup ");
				System.out.println("move " + current.get(z) + " " + current.get(z + 1));
				System.out.println("to " + current.get(z + 2) + " " + current.get(z + 3));

			}
			attempt.addAll(current);

		} else {
			System.out.println("empty");
			lastpos.put(start, new Point(start.getX(), start.getY()));
			if (start instanceof Fox) {
				foxTail.put(start, new Point(((Fox) start).getTailX(), ((Fox) start).getTailY()));
			}

			q.add(new ArrayList<Integer>(attempt));
		}

		// System.out.println(check.getBoard()[1][1].getClass());
		// System.out.println(check.getBoard()[2][1].getClass());
		// ArrayList<Integer> parents = new ArrayList<Integer>();
		System.out.println("startx+y " + start.getX() + " " + start.getY());
		System.out.println("first " + q);
		// attempt.add(9);// 9 will be used to define moving a different piece opposed
		// to moving the same
		// piece over and over again

		while (solution.isEmpty() && !q.isEmpty()) {

			boolean used = false;// boolean to see if object was used as an obstacle

			System.out.println("LEVEL NUMBER " + levelNumber);

			int checkX = current.size() - 2;
			int checkY = current.size() - 1;

			System.out.println(current);
			System.out.println(checkX);
			System.out.println(checkY);
			if (checkX >= 0 && check.getBoard()[current.get(checkX)][current.get(checkY)].getClass() == Fox.class) {

				//System.out.println("last mover is fox");
				lastFox = ((Fox) check.getBoard()[current.get(checkX)][current.get(checkY)]).getColor();// last fox
																										// moved
																										// according to
																										// sequence of
																										// moves
			} else if (checkX >= 0) {// rabbit made last move
			//	System.out.println("last mover is rab");

				lastFox = Color.pink;
				int loopx = 0, loopy = 0;

				if (current.get(checkY) > current.get(checkY - 2)) {// going right

					loopx = 1;

				} else if (current.get(checkY) < current.get(checkY - 2)) {// going left
					loopx = -1;

				} else if (current.get(checkX) > current.get(checkX - 2)) {// going down
					loopy = 1;
				} else if (current.get(checkX) < current.get(checkX - 2)) {// going up
					loopy = -1;
				}

				if (current.get(checkX) == current.get(checkX - 2)) {// going left right

					for (int d = current.get(checkY - 2); d != current.get(checkY) - loopx;) {
						d += loopx;
//						System.out.println("d");
//						System.out.println(d);
//						System.out.println(gox + (x * rabMoves.get(i)));
						// System.out.println(check.getBoard()[current.get(checkX)][d].getClass());
						if (check.getBoard()[current.get(checkX)][d].getClass() == Rabbit.class
								&& start.getClass() == Rabbit.class
								&& ((Rabbit) check.getBoard()[current.get(checkX)][d]).getColor()
										.equals(((Rabbit) start).getColor())) {

							used = true;
						} else if (check.getBoard()[current.get(checkX)][d] instanceof Hole
								&& ((Hole) check.getBoard()[current.get(checkX)][d]).hasRabbit()) {

							if (start.getClass() == Rabbit.class
									&& ((Rabbit) ((Hole) check.getBoard()[current.get(checkX)][d]).getGamePiece())
											.getColor().equals(((Rabbit) start).getColor())) {
								used = true;
							}

						} else if (check.getBoard()[current.get(checkX)][d].getClass() == Fox.class
								&& start.getClass() == Fox.class && ((Fox) check.getBoard()[current.get(checkX)][d])
										.getColor().equals(((Fox) start).getColor())) {
							used = true;
						}
					}
				} else {
					System.out.println(current);
					System.out.println(checkX);
					System.out.println(loopy);
					for (int d = current.get(checkX - 2); d != current.get(checkX) - loopy;) {
						d += loopy;
//							System.out.println("d");
//							System.out.println(d);
//							System.out.println(gox + (x * rabMoves.get(i)));
						if (start.getClass() == Rabbit.class
								&& check.getBoard()[d][current.get(checkY)].getClass() == Rabbit.class
								&& ((Rabbit) check.getBoard()[d][current.get(checkY)]).getColor()
										.equals(((Rabbit) start).getColor())) {

							used = true;
						} else if (start.getClass() == Rabbit.class
								&& check.getBoard()[d][current.get(checkY)] instanceof Hole
								&& ((Hole) check.getBoard()[d][current.get(checkY)]).hasRabbit()) {

							if (((Rabbit) ((Hole) check.getBoard()[d][current.get(checkY)]).getGamePiece()).getColor()
									.equals(((Rabbit) start).getColor())) {
								used = true;
							}

						} else if (check.getBoard()[d][current.get(checkY)].getClass() == Fox.class
								&& start.getClass() == Fox.class && ((Fox) check.getBoard()[d][current.get(checkY)])
										.getColor().equals(((Fox) start).getColor())) {
							used = true;
						}
					}

				}

			}

			int spaces = 0;

			if (start.getClass() == Rabbit.class || (start.getClass() == Hole.class && ((Hole) start).hasRabbit())) {

//				if(start.getClass()==Hole.class) {
//					start = ((Hole)start).getGamePiece();
//				}

				// foxmove = 0;

				int dir = 1;
				ArrayList<Integer> rabMoves = new ArrayList<Integer>();

				while (dir < 5) {

					spaces = ((Rabbit) start).canHop(check.getBoard(), start.getX(), start.getY(), dir, 0);

					if (spaces > 1) {

						rabMoves.add(dir);
						rabMoves.add(spaces);
					}

					// foxMoves.add(9);//to distinguish amount of spaces in different directions

					dir++;
				}

				System.out.println("rabmoves" + rabMoves);

				if (!rabMoves.isEmpty()) {
					System.out.println("before add " + attempt);
					attempt.add(start.getX());
					attempt.add(start.getY());
					System.out.println("add move " + attempt);

					int x = 1;
					int y = 1;
					for (int i = 0; i < rabMoves.size(); i++) {
						System.out.println("i " + i);
//						System.out.println("i "+i);
//						System.out.println("i " + i);
//						System.out.println(start.getX() + " " + start.getY());
						if (i % 2 == 0) {// if checking direction value in the list
							switch (rabMoves.get(i)) {
							case 1:// up
								x = -1;
								y = 0;
								break;
							case 2:
								x = 1;
								y = 0;
								break;
							case 3:
								y = 1;
								x = 0;
								break;
							case 4:
								y = -1;
								x = 0;
								break;

							}

						} else {// checking spaces

							int gox = start.getX();
							int goy = start.getY();

							// if piece is trying to backtrack
							System.out.println(lastpos);
//							System.out.println("exce " + exceptions);
////							System.out.println(lastpos);
//							System.out.println("if contains"
//									+ (gox + (x * rabMoves.get(i)) + " & " + (goy + (y * rabMoves.get(i)))));

							Rabbit temp = new Rabbit(0, 0, Color.white);

							if (start.getClass() == Rabbit.class) {

								temp = (Rabbit) start;

							} else {

								temp = ((Rabbit) ((Hole) start).getGamePiece());

							}

							// Color cPiece = ((Rabbit) temp).getColor();

							if (temp.canUndo() && temp.undo(check.getBoard())) {
								System.out.println("Canundo");

								// temp.undo(check.getBoard());
								int tempx = temp.getX();
								int tempy = temp.getY();
								// System.out.println("old xny " + tempx + " " + tempy);
								temp.redo(check.getBoard());

//								System.out.println((gox + (x * rabMoves.get(i))));
//								System.out.println(goy + (y * rabMoves.get(i)));

								if (tempx == (gox + (x * rabMoves.get(i))) && tempy == (goy + (y * rabMoves.get(i)))) {/// if
									/// backtracking

									System.out.println("backtracking");

//							if (lastpos.containsKey(start) && (lastpos.get(start).x == (gox + (x * rabMoves.get(i)))
//									&& (lastpos.get(start).y == (goy + (y * rabMoves.get(i)))))) {

//									boolean canBack = false;
//
//									exceptionsItr = exceptions.iterator();
//									while (exceptionsItr.hasNext()) {
//										Color c = (Color) exceptionsItr.next();
//
//										if (c != null && c.equals(cPiece)) {
//
//											exceptionsItr.remove();
//											canBack = true;
//											System.out.println("canback");
//										}
//
//									}

//								for (Color c : exceptions) {
//									if (c != null && c.equals(cPiece)) {
//
//										exceptions.remove(c);
//										canBack = true;
//										System.out.println("canback");
//									}
//
//								}

									if (!used) {
										System.out.println("canotback");
										rabMoves.remove(i - 1);
										rabMoves.remove(i - 1);
										i -= 2;
										continue;
									}
								}
							}
							System.out.println("continued?");
							used = false;
							moveMade = true;

							// System.out.println("att "+attempt);

//							System.out.println("try" + attempts);
//							if (goy == goy + (y * rabMoves.get(i))) {// if going up/down
//
//								for (int d = gox + x; d != gox + (x * rabMoves.get(i)); d += x) {
////									System.out.println("d");
////									System.out.println(d);
////									System.out.println(gox + (x * rabMoves.get(i)));
//									if (check.getBoard()[d][goy] instanceof Rabbit) {
//
//										exceptions.add(((Rabbit) check.getBoard()[d][goy]).getColor());
//									} else if (check.getBoard()[d][goy] instanceof Fox) {
//										exceptions.add(((Fox) check.getBoard()[d][goy]).getColor());
//									} else if (check.getBoard()[d][goy] instanceof Hole
//											&& ((Hole) check.getBoard()[d][goy]).hasRabbit()) {
//
//										exceptions.add(
//												((Rabbit) ((Hole) check.getBoard()[d][goy]).getGamePiece()).getColor());
//
//									}
//								}
//
//							} else {
//
//								for (int d = goy + y; d != goy + (y * rabMoves.get(i)); d += y) {
//
//									if (check.getBoard()[gox][d] instanceof Rabbit) {
//
//										exceptions.add(((Rabbit) check.getBoard()[gox][d]).getColor());
//									} else if (check.getBoard()[gox][d] instanceof Fox) {
//										exceptions.add(((Fox) check.getBoard()[gox][d]).getColor());
//									} else if (check.getBoard()[gox][d] instanceof Hole
//											&& ((Hole) check.getBoard()[gox][d]).hasRabbit()) {
//
//										exceptions.add(
//												((Rabbit) ((Hole) check.getBoard()[gox][d]).getGamePiece()).getColor());
//
//									}
//								}
//
//							}

//							System.out.println("beforeq " + q);
							attempt.add(gox + (x * rabMoves.get(i)));
							attempt.add(goy + (y * rabMoves.get(i)));
							System.out.println("Addattempt " + attempt);
							q.add(new ArrayList<Integer>(attempt));
							lastFox = Color.PINK;
							System.out.println("qadd " + q);

							check.reset();

//							System.out.println("attenpt" + attempt);
							System.out.println(rabMoves);
							for (int z = 0; z < attempt.size(); z += 4) {
								check.move((int) attempt.get(z), (int) attempt.get(z + 1), (int) attempt.get(z + 2),
										(int) attempt.get(z + 3), 1);
//								System.out.println("move " + (int) attempt.get(z) + " " + (int) attempt.get(z + 1));
//								System.out.println("to " + (int) attempt.get(z + 2) + " " + (int) attempt.get(z + 3));

							}

							if (check.checkWin()) {
								this.solution = attempt;
								// return attempt;
								return;

							}
//							// System.out.println("atter"+attempts);
							attempt.remove(attempt.size() - 1);
							attempt.remove(attempt.size() - 1);
							if (check.canUndo()) {
								check.undo();
							}

						}

					}

//					if(moveMade) {
//						attempts2.add(q.poll());
//
//					}

				}

			} else if (start.getClass() == Fox.class && !lastFox.equals(((Fox) start).getColor())) {

				lastFox = ((Fox) start).getColor();

				// foxmove++;
				int dir = 1;
				ArrayList<Integer> foxMoves = new ArrayList<Integer>();
				Fox tempfox = (Fox) start;
				while (dir < 5) {

					if (tempfox.forward(dir)) {

						spaces = (tempfox.canSlide(check.getBoard(), start.getX(), start.getY(), dir, 0));// uses head

					} else {// use tail
						spaces = (tempfox.canSlide(check.getBoard(), tempfox.getTailX(), tempfox.getTailY(), dir, 0));
					}

//					System.out.println(start.getX() + " " + start.getY());
//					System.out.println("dir " + dir);
//					System.out.println("spaces " + spaces);
					if (((dir < 3) && ((Fox) start).getVertical()) || ((dir > 2) && (!((Fox) start).getVertical()))) {// If
																														// going
																														// proper
																														// direction

						if (spaces > 0) {
							foxMoves.add(dir);
							foxMoves.add(spaces);
						}
					}

					// foxMoves.add(9);//to distinguish amount of spaces in different directions

					dir++;
				}

				System.out.println("foxmoves " + foxMoves);
				if (!foxMoves.isEmpty()) {

					attempt.add(start.getX());
					attempt.add(start.getY());

					int x = 1;
					int y = 1;
					for (int i = 0; i < foxMoves.size(); i++) {
						if (i % 2 == 0) {// if checking direction value in the list
							switch (foxMoves.get(i)) {
							case 1:// up
								x = -1;
								y = 0;
								break;
							case 2:
								x = 1;
								y = 0;
								break;
							case 3:
								y = 1;
								x = 0;
								break;
							case 4:
								y = -1;
								x = 0;
								break;

							}

						} else {// checking spaces
							boolean canBack = false;

							for (int n = 1; n <= foxMoves.get(i); n++) {
								System.out.println("n " + n);
								// parents.add(index);
								int gox = start.getX();
								int goy = start.getY();

//								System.out.println(lastpos);
//								System.out.println(foxTail);
//								System.out.println("if " + lastpos.containsKey(start) + " & " + one + " & " + two
//										+ " & " + three + " & " + four);

								// if piece is trying to backtrack
//								if (lastpos.containsKey(start)) {
								int goTx = ((Fox) start).getTailX();
								int goTy = ((Fox) start).getTailY();

								if (!((Fox) start).forward(foxMoves.get(i - 1))) {
									gox = ((Fox) start).getTailX();
									goy = ((Fox) start).getTailY();
									goTx = ((Fox) start).getX();
									goTy = ((Fox) start).getY();

								}

								if (((Fox) start).canUndo() && ((Fox) start).undo(check.getBoard())) {
									System.out.println("canFundo");
									// ((Fox) start).undo(check.getBoard());
									int tempx = start.getX();
									int tempy = start.getY();
									int tempTx = ((Fox) start).getTailX();
									int tempTy = ((Fox) start).getTailY();
									((Fox) start).redo(check.getBoard());

									boolean one = (tempx == (gox + (x * n)));
									boolean two = (tempy == (goy + (y * n)));
									boolean three = (tempTx == (goTx + (x * n)));
									boolean four = (tempTy == (goTy + (y * n)));

									if (one && two && three && four) {// if backtracking
										System.out.println("backtracking");
//										Color cPiece = ((Fox) start).getColor();
//
//										exceptionsItr = exceptions.iterator();
//										while (exceptionsItr.hasNext()) {
//											Color c = (Color) exceptionsItr.next();
//
//											if (c != null && c.equals(cPiece)) {
//
//												exceptionsItr.remove();
//												canBack = true;
//												System.out.println("canback");
//											}
//
//										}

//										for (Color c : exceptions) {
//											if (c != null && c.equals(cPiece)) {
//
//												exceptions.remove(c);
//												canBack = true;
//											}
//										}

										if (!used) {

											foxMoves.remove(i - 1);
											foxMoves.remove(i - 1);
											i -= 2;

											break;
										}

									}
								}
								used = false;
								moveMade = true;

//								// add child nodes
//								q.add(gox + (x * n));
//								q.add(goy + (y * n));

								if ((gox + (x * n) <= 0) || (goy + (y * n) <= 0) || (gox + (x * n) >= 5)
										|| (goy + (y * n) >= 5)) {

									break;
								}
								attempt.add(gox + (x * n));
								attempt.add(goy + (y * n));
								System.out.println(attempt);
								q.add(new ArrayList<Integer>(attempt));

								System.out.println("qadd " + q);
								check.reset();

								for (int z = 0; z < attempt.size(); z += 4) {
									check.move((int) attempt.get(z), (int) attempt.get(z + 1), (int) attempt.get(z + 2),
											(int) attempt.get(z + 3), 1);

								}
								if (check.checkWin()) {
									this.solution = attempt;
									return;
								}
								attempt.remove(attempt.size() - 1);
								attempt.remove(attempt.size() - 1);
								if (check.canUndo()) {
									check.undo();
								}
								if (canBack) {
									continue;
								}

							}

						}

					}

//					if(moveMade) {
//						attempts2.add(q.poll());
//
//					}

				}

			}

			// lastpos.put(start, new Point(start.getX(),start.getY()));
			System.out.println("lastq " + q);
			System.out.println("lastempt" + attempt);
			lastpos.put(start, new Point(start.getX(), start.getY()));
			if (start instanceof Fox) {
				foxTail.put(start, new Point(((Fox) start).getTailX(), ((Fox) start).getTailY()));
			}
//			if (parents.isEmpty()) {
//				parents.addAll(children);
//			}

			System.out.println("moveMade? " + moveMade);
			System.out.println("allchecked? " + allChecked);
			System.out.println(attempts2);
			if (allChecked) {
				levelNumber++;
				exceptions.clear();

				if (attempts2.isEmpty()) {// if first try
					attempts2.add(q.poll());
				}
				allChecked = false;
				for (Slot s : check.getPieces()) {

					System.out.println(s);

					check.reset();
//						System.out.println("index " + index);
//						System.out.println("parents " + parents);
					this.solve(check, s, q.peek());

					// solution = this.solve(board, s, attempts.get(index + g));

				}
				allChecked = true;
				attempts2.add(q.poll());
				levelNumber++;
				exceptions.clear();

				if (!q.isEmpty()) {
					current = q.peek();
				}
				for (int z = 0; z < current.size(); z += 4) {
//						System.out.println("moveanimal " + check.getBoard()[current.get(z)][current.get(z + 1)]);
//						System.out.println("to " + check.getBoard()[current.get(z + 2)][current.get(z + 3)]);
					check.move(current.get(z), current.get(z + 1), current.get(z + 2), current.get(z + 3), 1);
//						System.out.println("setup ");
//						System.out.println("move " + current.get(z) + " " + current.get(z + 1));
//						System.out.println("to " + current.get(z + 2) + " " + current.get(z + 3));

				}

				attempt.clear();
				attempt.addAll(current);
				System.out.println("at end curr " + current);

			} else {
				break;
			}

			// allChecked =true;

//				allChecked = true;
//				parents.clear();
//				parents.addAll(children);
//				children.clear();

		}
//	 level++;
//	 	System.out.println(level);
//	 	allChecked=true;
		// this.solve(check, start, attempts.get(attempts.size() - level));

	}

	public ArrayList<Integer> getSol() {
		return this.solution;
	}
}
