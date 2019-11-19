import java.awt.Color;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

public class Solver {

	private ArrayList<ArrayList<Integer>> attempts2;
	private ArrayList<Integer> solution;
	private Queue<ArrayList<Integer>> q;
	private Color lastFox;
	private boolean allChecked;
	private Board check;

	public Solver(Board b) {
		allChecked = false;
		q = new LinkedList<ArrayList<Integer>>();
		solution = new ArrayList<Integer>();
		attempts2 = new ArrayList<ArrayList<Integer>>();
		this.check = b;
		lastFox = Color.PINK;

	}
	
	public void solve(Board b, Slot start, ArrayList<Integer> current) {

		// if first try
		if (attempts2.isEmpty()) {
			allChecked = true;
		}
		
		ArrayList<Integer> attempt = new ArrayList<Integer>();
		
		if (!current.isEmpty()) {//if not the first try 
			//setup the board using list of given moves (current)
			for (int z = 0; z < current.size(); z += 4) {

				check.move(current.get(z), current.get(z + 1), current.get(z + 2), current.get(z + 3), 1);
			

			}
			//add current list of moves to attempt being made
			attempt.addAll(current);

		} else {//first move or node
		
			//add current attempt to queue
			q.add(new ArrayList<Integer>(attempt));
		}
	
		//keep searching until queue is empty(no more moves to check) or solution is found
		while (solution.isEmpty() && !q.isEmpty()) {

			boolean used = false;// boolean to see if object was used as an obstacle

			//index of last X and Y coordinates an object moved to
			int checkX = current.size() - 2;
			int checkY = current.size() - 1;

			//if last piece moved was a fox
			if (checkX >= 0 && check.getBoard()[current.get(checkX)][current.get(checkY)].getClass() == Fox.class) {

				//set color of lastFox moved 
				lastFox = ((Fox) check.getBoard()[current.get(checkX)][current.get(checkY)]).getColor();
			} else if (checkX >= 0) {// rabbit made last move

				//set lastFox color to a color a fox hopefully cannot have
				lastFox = Color.pink;
				
				//initialize variables that are added to x and y coordinates
				int loopx = 0, loopy = 0;

				//check which direction the rabbit was hoping
				if (current.get(checkY) > current.get(checkY - 2)) {// going right

					loopx = 1;

				} else if (current.get(checkY) < current.get(checkY - 2)) {// going left
					loopx = -1;

				} else if (current.get(checkX) > current.get(checkX - 2)) {// going down
					loopy = 1;
				} else if (current.get(checkX) < current.get(checkX - 2)) {// going up
					loopy = -1;
				}

				if (current.get(checkX) == current.get(checkX - 2)) {// going left or right

					//check if in the last move, a rabbit jumped over the current piece (used it as an obstacle)
					for (int d = current.get(checkY - 2); d != current.get(checkY) - loopx;) {
						d += loopx;

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
			
					
			    //if going up or down
				} else {
					
					//check if in the last move, a rabbit jumped over the current piece (used it as an obstacle)
					for (int d = current.get(checkX - 2); d != current.get(checkX) - loopy;) {
						d += loopy;
			
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

			//if object making a move is a rabbit (that may be in a hole)
			if (start.getClass() == Rabbit.class || (start.getClass() == Hole.class && ((Hole) start).hasRabbit())) {

				//get list of possible moves
				ArrayList<Integer> rabMoves = ((Rabbit)start).possibleMoves(check);

				//if moves are possible
				if (!rabMoves.isEmpty()) {
					//add current position to current attempt
					attempt.add(start.getX());
					attempt.add(start.getY());

					//initialize variables used for traversing the board
					int x = 1;
					int y = 1;
					//check list of moves
					for (int i = 0; i < rabMoves.size(); i++) {
	
						if (i % 2 == 0) {// if checking direction value in the list
							switch (rabMoves.get(i)) {
							case 1:// up
								x = -1;
								y = 0;
								break;
							case 2://down
								x = 1;
								y = 0;
								break;
							case 3://right
								y = 1;
								x = 0;
								break;
							case 4://left
								y = -1;
								x = 0;
								break;

							}

						} else {// checking number of spaces that can be jumped to

							//initial position
							int gox = start.getX();
							int goy = start.getY();

							Rabbit temp = new Rabbit(0, 0, Color.white);

							//set temp rabbit
							if (start.getClass() == Rabbit.class) {

								temp = (Rabbit) start;

							//set temp rabbit from hole
							} else {

								temp = ((Rabbit) ((Hole) start).getGamePiece());

							}


							//if rabbit can undo and undo was successful (boolean undo, undos then returns if the undo was successful)
							if (temp.canUndo() && temp.undo(check.getBoard())) {

								//record position of rabbit after undo
								int tempx = temp.getX();
								int tempy = temp.getY();
								//redo to current state
								temp.redo(check.getBoard());

								//if coordinates of undo are the same as the coordinates of the current move then rabbit is trying to backtrack
								if (tempx == (gox + (x * rabMoves.get(i))) && tempy == (goy + (y * rabMoves.get(i)))) {

									//if rabbit wasn't used as an obstacle, then it's not allowed to backtrack
									if (!used) {
										//remove move from list
										rabMoves.remove(i - 1);
										rabMoves.remove(i - 1);
										//decrement counter index
										i -= 2;
										//check next possible move
										continue;
									}
								}
							}
							//move possible so reset boolean 
							used = false;

							//add destination coordinates in current attempt
							attempt.add(gox + (x * rabMoves.get(i)));
							attempt.add(goy + (y * rabMoves.get(i)));
							//add current attempt to queue
							q.add(new ArrayList<Integer>(attempt));
							//change lastFox color to a color not given to a fox, so it's allowed to make a move next turn
							lastFox = Color.PINK;
							//reset the board to original state
							check.reset();

							//loop to make all moves in current attempt
							for (int z = 0; z < attempt.size(); z += 4) {
								check.move((int) attempt.get(z), (int) attempt.get(z + 1), (int) attempt.get(z + 2),
										(int) attempt.get(z + 3), 1);

							}

							//if attempt resulted in a win, record moves as solution and return
							if (check.checkWin()) {
								this.solution = attempt;
								return;

							}
							//remove destination coordinates from attempt to leave space for next possible move by the same piece
							attempt.remove(attempt.size() - 1);
							attempt.remove(attempt.size() - 1);
							//undo last move if possible
							if (check.canUndo()) {
								check.undo();
							}

						}

					}

				}

			//if current piece is a fox and isn't the same fox that made the previous move	
			} else if (start.getClass() == Fox.class && !lastFox.equals(((Fox) start).getColor())) {

				//set lastFox color to current fox's color
				lastFox = ((Fox) start).getColor();
				//get list of possible moves
				ArrayList<Integer> foxMoves = ((Fox) start).possibleMoves(check);
				
				//if move possible
				if (!foxMoves.isEmpty()) {

					//add current position
					attempt.add(start.getX());
					attempt.add(start.getY());
					//initialize values for traversing the board
					int x = 1;
					int y = 1;
					for (int i = 0; i < foxMoves.size(); i++) {
						if (i % 2 == 0) {// if checking direction value in the list
							switch (foxMoves.get(i)) {
							case 1:// up
								x = -1;
								y = 0;
								break;
							case 2://down
								x = 1;
								y = 0;
								break;
							case 3://right
								y = 1;
								x = 0;
								break;
							case 4://left
								y = -1;
								x = 0;
								break;

							}

						} else {// checking spaces					

							//loop for the amount of spaces a fox can move
							for (int n = 1; n <= foxMoves.get(i); n++) {
								//initialize current position
								int gox = start.getX();
								int goy = start.getY();
								
								int goTx = ((Fox) start).getTailX();
								int goTy = ((Fox) start).getTailY();

								//if fox is not going forward, switch head and tail
								if (!((Fox) start).forward(foxMoves.get(i - 1))) {
									gox = ((Fox) start).getTailX();
									goy = ((Fox) start).getTailY();
									goTx = ((Fox) start).getX();
									goTy = ((Fox) start).getY();

								}

								//if fox can undo and undo is successful
								if (((Fox) start).canUndo() && ((Fox) start).undo(check.getBoard())) {
									//record position of fox after undo
									int tempx = start.getX();
									int tempy = start.getY();
									int tempTx = ((Fox) start).getTailX();
									int tempTy = ((Fox) start).getTailY();
									//redo the undo
									((Fox) start).redo(check.getBoard());

									//check if coordinates are same as the move's destination coordinates
									boolean one = (tempx == (gox + (x * n)));
									boolean two = (tempy == (goy + (y * n)));
									boolean three = (tempTx == (goTx + (x * n)));
									boolean four = (tempTy == (goTy + (y * n)));

									if (one && two && three && four) {// if backtracking

										//if fox wasn't used as an obstacle
										if (!used) {
											//remove move and decrement index
											foxMoves.remove(i - 1);
											foxMoves.remove(i - 1);
											i -= 2;
											//check next move
											break;
										}

									}
								}
								
								//move possible so reset boolean
								used = false;

								//if destination coordinates are outside the board, check next move
								if ((gox + (x * n) <= 0) || (goy + (y * n) <= 0) || (gox + (x * n) >= 5)
										|| (goy + (y * n) >= 5)) {

									break;
								}

								//add destination to attempt
								attempt.add(gox + (x * n));
								attempt.add(goy + (y * n));
								//add attempt to queue
								q.add(new ArrayList<Integer>(attempt));
								//reset the board
								check.reset();

								//loop to make moves in current attempt
								for (int z = 0; z < attempt.size(); z += 4) {
									check.move((int) attempt.get(z), (int) attempt.get(z + 1), (int) attempt.get(z + 2),
											(int) attempt.get(z + 3), 1);

								}
								//if attempt resulted in solution, record it and return
								if (check.checkWin()) {
									this.solution = attempt;
									return;
								}
								//remove last destination to leave space for next possible move by the same piece
								attempt.remove(attempt.size() - 1);
								attempt.remove(attempt.size() - 1);
								
								//undo the move if possible
								if (check.canUndo()) {
									check.undo();
								}

							}

						}

					}
				}

			}
			
			//if all children were checked (true for the first try by default)
			if (allChecked) {

				if (attempts2.isEmpty() && !q.isEmpty()) {// if first try and queue is not empty
					//remove attempt from queue and add to list attempts
					attempts2.add(q.poll());
				}
				// reset all children checked
				allChecked = false;
				//for all the pieces in the board
				for (Slot s : check.getPieces()) {
					//reset the board
					check.reset();
					//piece tries to make a move given the attempt in the head of the queue 
					this.solve(check, s, q.peek());

				}
				//if all pieces checked
				allChecked = true;
				
				//remove current attempt from queue and add to list of attempts
				//and get next attempt from queue
				if (q.size()>1) {
					attempts2.add(q.poll());
					current = q.peek();
				}
				
				//setup the board given the current attempt
				for (int z = 0; z < current.size(); z += 4) {

					check.move(current.get(z), current.get(z + 1), current.get(z + 2), current.get(z + 3), 1);

				}
				
				//clear attempt and add the current attempt then loop back
				attempt.clear();
				attempt.addAll(current);

			//if all children aren't checked break out of recursion	
			} else {
				break;
			}

		}

	}

	public ArrayList<Integer> getSol() {
		return this.solution;
	}

	public ArrayList<Integer> findSolution() {
		
		for (Slot piece : check.getPieces()) {
			
			
			if (piece instanceof Rabbit && !((Rabbit) piece).possibleMoves(check).isEmpty()) {
				
				this.solve(check, piece, new ArrayList<Integer>());
				
			} else if (piece instanceof Fox && !((Fox) piece).possibleMoves(check).isEmpty()) {
				
				this.solve(check, piece, new ArrayList<Integer>());
				
			} else if (piece instanceof Hole && ((Hole) piece).hasRabbit()
					
					&& !((Rabbit) ((Hole) piece).getGamePiece()).possibleMoves(check).isEmpty()) {
				
				this.solve(check, piece, new ArrayList<Integer>());
			}
		}
		
		
		return this.getSol();
	}
}
