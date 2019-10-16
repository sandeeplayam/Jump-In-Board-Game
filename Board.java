
import java.util.*;

public class Board{

	private Slot[][] board;
	private ArrayList<Hole> Holes;
	private short numRabbits;
	
	public Slot[][] getboard(){
		return board;
	}

	public String toStringBoard() {
		String b="";
		for(int i=0;i<5;i++) {
			for(int j = 0;j<5;j++) {

				b+= board[i][j].getType();
				b+= " ";
			}
			b+="\n";
		}
		return b;
	}
	
	public boolean checkWin() {
		int occupiedHoles=0;
		
		for(Hole h: Holes) {
			
			if(h.hasRabbit()) {
				occupiedHoles++;
			}
		}
		
		if(occupiedHoles==numRabbits) {
			return true;
		}
		
		return false;
	}
	
	public void move(Slot s, String direction) {
		
		
		if(s instanceof Rabbit) {

			//number of available moves in given direction
			int go = 0;
		
			//Get number of Slots rabbit can jump over then change rabbit's position
			switch(direction) {
				
			case "up":
					//System.out.println("up");
					//get number of moves(number of obstacles + 1)
				go = this.canHop((s.getX()-1),s.getY(),direction) +1;
					//If no obstacles do nothing
				if(go==1) {
					System.out.println("No obstacle to hop over");
					return;
				}else if(go==0) {
					System.out.println("Can't hop out of board, there's no escape :)");
					return;
				}
				
					//Declare rabbit position in Board
					if(board[s.getX()-go][s.getY()] instanceof Hole) {
						board[s.getX()-go][s.getY()].addRabbit(s);
					}else {
						this.changeStatus(s.getX()-go,s.getY(),s);
						
					}
					
					//clear rabbits old position
					if(board[s.getX()][s.getY()]instanceof Hole) {
						
						
						((Hole)board[s.getX()][s.getY()]).removeGamePiece();
						
						
					}else {
						
						this.changeStatus(s.getX(),s.getY(), new Slot());
					}

					
					s.setPos(s.getX()-go,s.getY());
					
					break;
				case "down":
					//System.out.println("down");
					go = this.canHop((s.getX()+1),s.getY(),direction)+1;
					//System.out.println("go: "+go);
					if(go==1) {
						System.out.println("No obstacle to hop over");
						return;
						
					}else if(go==0) {
						System.out.println("Can't hop out of board, there's no escape :)");
						return;
					}
					
					//Declare rabbit position in Board
					if(board[s.getX()+go][s.getY()] instanceof Hole) {
						board[s.getX()+go][s.getY()].addRabbit(s);
					}else {
						this.changeStatus(s.getX()+go,s.getY(),s);
						
					}
					
					//clear rabbits old position
					if(board[s.getX()][s.getY()]instanceof Hole) {
						
						
						((Hole)board[s.getX()][s.getY()]).removeGamePiece();
						
						
					}else {
						
						this.changeStatus(s.getX(),s.getY(), new Slot());
					}

					
					s.setPos(s.getX()+go,s.getY());
					
					break;
				case "right":
					go = this.canHop(s.getX(),(s.getY()+1),direction)+1;
					if(go==1) {
						System.out.println("No obstacle to hop over");
						return;
					}else if(go==0) {
						System.out.println("Can't hop out of board, there's no escape :)");
						return;
					}
					
					//Declare rabbit position in Board
					if(board[s.getX()][s.getY()+go] instanceof Hole) {
						board[s.getX()][s.getY()+go].addRabbit(s);
					}else {
						this.changeStatus(s.getX(),s.getY()+go,s);
						
					}
					
					//clear rabbits old position
					if(board[s.getX()][s.getY()]instanceof Hole) {
						
						
						((Hole)board[s.getX()][s.getY()]).removeGamePiece();
						
						
					}else {
						
						this.changeStatus(s.getX(),s.getY(), new Slot());
					}

					
					s.setPos(s.getX(),s.getY()+go);
					break;
				case "left":
					go = this.canHop(s.getX(),(s.getY()-1),direction)+1;
					if(go==1) {
						System.out.println("No obstacle to hop over");
						return;
					}else if(go==0) {
						System.out.println("Can't hop out of board, there's no escape :)");
						return;
					}
					
					//Declare rabbit position in Board
					if(board[s.getX()][s.getY()-go] instanceof Hole) {
						board[s.getX()][s.getY()-go].addRabbit(s);
					}else {
						this.changeStatus(s.getX(),s.getY()-go,s);
						
					}
					
					//clear rabbits old position
					if(board[s.getX()][s.getY()]instanceof Hole) {
						
						
						((Hole)board[s.getX()][s.getY()]).removeGamePiece();
						
						
					}else {
						
						this.changeStatus(s.getX(),s.getY(), new Slot());
					}

					
					s.setPos(s.getX(),s.getY()-go);
					break;
				default:
					System.out.println("That's not a proper direction, use (up, down, left, right)");
				}
				
		}else if(s instanceof Fox) {
			
			boolean go;
			int tempX =-1;
			int tempY=-1;
			switch(direction){	
			
			case "up":
				
				if(!((Fox)s).getVertical()) {
					System.out.println("Can't go that direction");
					return;
				//if fox is going backwards	
				}else if(s.getX()>((Fox)s).getTailX()) {
					go = this.canSlide(((Fox) s).getTailX()-1, ((Fox) s).getTailY(), direction);
					tempX = s.getX();
					//tempY = Y;
				}else {
					go = this.canSlide(s.getX()-1, s.getY(), direction);
					tempX = ((Fox) s).getTailX();
				}
				
				if(go) {
					
					//Declare fox position in Board
					this.changeStatus(((Fox) s).getTailX()-1,s.getY(), s);
					this.changeStatus(s.getX()-1,s.getY(), s);
					//clear fox old position
					this.changeStatus(tempX,s.getY(), new Slot());
					//update fox coordinates
					s.setX(s.getX()-1,s.getY(),s.getTailX(-1), s.getTailY());
				
					
				}else {
					
					System.out.println("No space to slide");
					return;
				}

				break;
				
			case "down":
				
				if(!((Fox)s).getVertical()) {
					System.out.println("Can't go that direction");
					return;
				//fox going backward	
				}else if(s.getX()<((Fox)s).getTailX()) {
					tempX = s.getX();
					go = this.canSlide(((Fox) s).getTailX()+1, ((Fox) s).getTailY(), direction);
				}else {
					tempX = ((Fox) s).getTailX();
					go = this.canSlide(s.getX()+1, s.getY(), direction);
				}
				
				if(go) {
					//Declare fox position in Board
					this.changeStatus(((Fox) s).getTailX()+1,s.getY(), s);
					this.changeStatus(s.getX()+1,s.getY(), s);
					//clear fox old position
					this.changeStatus(tempX,s.getY(), new Slot());
				
					s.setX(s.getX()+1,s.getY(),s.getTailX()+1, s.getTailY());
				
				}else {
					
					System.out.println("No space to slide");
					return;
				}
				
				break;
			
			case "right":
				
				if(((Fox)s).getVertical()) {
					System.out.println("Can't go that direction");
					return;
				//fox going backward	
				}else if(s.getY()<((Fox)s).getTailY()) {
					tempY = s.getY();
					go = this.canSlide(((Fox) s).getTailX(), ((Fox) s).getTailY()+1, direction);			
				}else {		
					tempY = ((Fox) s).getTailY();
					go = this.canSlide(s.getX(), s.getY()+1, direction);
				}
				
				if(go) {
					//Declare new fox position in Board
					this.changeStatus(((Fox) s).getTailX(),((Fox) s).getTailY()+1, s);
					this.changeStatus(s.getX(),s.getY()+1, s);
					//clear old fox  position
					this.changeStatus(s.getX(),tempY, new Slot());
					
					
					s.setY(s.getX(),(s.getY()+1),s.getTailX(), s.getTailY()+1);
					
				}else {
					
					System.out.println("No space to slide");
					return;
				}
				
				break;
				
			case "left":
				
				if(((Fox)s).getVertical()) {
					System.out.println("Can't go that direction");
					return;
				//fox going backward	
				}else if(s.getY()>((Fox) s).getTailY()) {
					tempY = s.getY();
					go = this.canSlide(((Fox) s).getTailX(), ((Fox) s).getTailY()-1, direction);
				
				}else {
					tempY = ((Fox) s).getTailY();
					go = this.canSlide(s.getX(), s.getY()-1, direction);
				}
				
				if(go) {
					//Declare fox position in Board
					this.changeStatus(((Fox) s).getTailX(),((Fox) s).getTailY()-1, s);
					this.changeStatus(s.getX(),s.getY()-1, s);
					//clear fox old position
					this.changeStatus(s.getX(),tempY, new Slot());
				
				
					s.setY(s.getX(),(s.getY()-1),s.getTailX(), s.getTailY()-1);
					
				}else {
					
					System.out.println("No space to slide");
					return;
				}
			
				
				break;
			default:
				System.out.println("That's not a proper direction, use (up, down, left, right)");
			}
			
			
			
		}else {
			System.out.println("Can't move this type of piece");
		}
			
		
		
		
	}
	
	public void addPiece(Slot s, int x, int y) {
		
		if(s instanceof Hole) {
			Holes.add((Hole) s);
		}else if(s instanceof Rabbit) {
			numRabbits ++;
		}
		board [x][y]=s;
	}
	
	
	public Board() {
		
		
	//	Rabbits = new ArrayList<Rabbit>();
	//	Foxes = new ArrayList<Fox>();
		board = new Slot[5][5];
		Holes = new ArrayList<Hole>();
		
		for(int i=0;i<5;i++) {
			for(int j = 0;j<5;j++) {
				board [i][j] = new Slot();
				
			}
		}
		
	}
	
	//recursion
	public int canHop(int row, int col, String direction) {
		
		if(row<0 || col<0 || row>=board.length ||col>=board.length) {
			
			
			return -1;
		}
		//if there's an obstacle, check if there are more obstacles ahead
		if(! (board [row][col] instanceof Slot || board [row][col] instanceof Hole)) {
			
			
			switch(direction){
			case "up":					
				return canHop(row-1,col,"up")+1;
			case "down":
				return canHop(row+1,col,"down")+1;
			case "right":
				return canHop(row,col+1,"right")+1;
			case "left":
				return canHop(row,col-1,"left")+1;
			}
		//stop recursion when no more obstacles available	
		}else {
			
			return 0;
		}
		return 0;
	}
	
	public boolean canSlide(int row, int col, String direction){
		
		
		if(row<0 || col<0 || row>= board.length ||col>=board.length) {
			return false;
			
		}else if(board [row][col] instanceof Slot || board [row][col] instanceof Hole){
		
			return true;
			
		}else{
			
			return false;
		
		}
	}
	
	public void changeStatus(int x, int y, Slot s) {
		board[x][y] =s;	
	}

	public static void main(String[] args) {
		
		/*
		int size =5;
		boolean BoardDone =false;
		//Create scanner object
		Scanner challenge = new Scanner(System.in);
		
		
		System.out.println("Enter challege number");
		int chal = challenge.nextInt();
		
		Board g = new Board(size,chal);
		String b =g.toStringBoard();
		System.out.println(b);
		
		System.out.println("Make a move as follows: 'action' 'description' 'direction'");
		System.out.print("                       ");
		System.out.println("Rabbit-> hop orange left");
		System.out.print("                       ");
		System.out.println("Fox-> slide big up");
		System.out.println("Go ahead: ");
		
		Scanner Board = new Scanner(System.in);
		do {
			
		String move = Board.nextLine();
		
		if(move.equals("quit")) {
			System.out.println("Nice try, better luck next time.");
			break;
		}
		
		g.checkCommand(move); 
		BoardDone =g.didIwin();
		
		}while(!BoardDone);
		Board.close();
		challenge.close();
		*/
	}
}
