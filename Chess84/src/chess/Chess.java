/**
 * @author Michael DeDreu
 * @author Nikita Kolotov
 */


package chess;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;
public class Chess 
{
	public static Piece[][] board;
	public final static int SIZE = 8;
	public static boolean gamestatus = true;
	
	
	public static void main(String[] args) {
		Chess game = new Chess();
		game.init();
		
		Scanner s = new Scanner(System.in);
		
		game.print();
		int movenum = 1;

		boolean drawOpened = false;
		
		
		outerloop:
		while(gamestatus) {
			String line;
			String[] split;
			boolean validmove = false;
			
			while(!validmove) {
				
				if(movenum % 2 == 0) {
					System.out.print("\n" + "Black's move: ");
					line = s.nextLine();
					System.out.print("\n");
					split = line.split("\\s+");
					
					if(split[0].equals("resign")) {
						game.resign("black");
						gamestatus = false;
						break outerloop;
					}
					
					if(drawOpened) {
						if(split[0].trim().equals("draw")) {
							gamestatus = false;
							break outerloop;
						}
					}
					
					if(split.length == 3) {
						if(split[2].equals("draw?")) {
							drawOpened = true;
						}
					}
					

					int[] a = game.convertTo(split[0]);
					if(board[a[0]][a[1]] == null) {
						System.out.println("Illegal move, try again");
						continue;
					}
					if(board[a[0]][a[1]].color.equals("white")) {
						System.out.println("Illegal move, try again");
					}
						
					else if(game.move(split[0], split[1])) {
						game.promotion("black", split);
						game.check("white");
						game.cast(new ArrayList<String>(Arrays.asList(split)), board);
						game.enPass(new ArrayList<String>(Arrays.asList(split)), board);
						game.checkmate("black", board);
						game.checkmate("white", board);
						game.stalemate("black", board);
						game.stalemate("white", board);
//						if(game.checkmate("black", board));
//						{
//							gamestatus = false;
//						}
						validmove = true;
						game.print();
						//Here right?
						break;
						
					}
					
				} else {
					System.out.print("\n" + "White's move: ");
					line = s.nextLine();
					System.out.print("\n");
					split = line.split("\\s+");
					
					if(split[0].equals("resign")) {
						game.resign("white");
						gamestatus = false;
						break outerloop;
					}
					
					if(drawOpened) {
						if(split[0].trim().equals("draw")) {
							System.out.println("draw");
							gamestatus = false;
							break outerloop;
						}
					}
					
					if(split.length == 3) {
						if(split[2].equals("draw?")) {
							drawOpened = true;
						}
					}
					
					
					
					int[] a = game.convertTo(split[0]);
					if(board[a[0]][a[1]] == null) {
						System.out.println("Illegal move, try again");
						continue;
					}
					if(board[a[0]][a[1]].color.equals("black")) {
						System.out.println("Illegal move, try again");
					}
					
					else if(game.move(split[0], split[1])) {
						game.promotion("white", split);
						game.check("black");
						game.cast(new ArrayList<String>(Arrays.asList(split)), board);
						game.enPass(new ArrayList<String>(Arrays.asList(split)), board);
						game.checkmate("white", board);
						game.checkmate("black", board);
						game.stalemate("black", board);
						game.stalemate("white", board);
//						if(game.checkmate("white", board));
//						{
//							gamestatus = false;
//						}
						validmove = true;
						game.print();
						break;
					}
					
				}
			}
			
			movenum++;
			
		}
		
		s.close();
	}
	
	
	public Piece[][] getBoard()
	{
		return board;
	}
	
	
	/**
	 * move method: take input of string of the location that is to be moved and string of where that piece is to be moved. If the destination
	 * is contained within the list of possible moves for the source, the piece is moved to the destination. Returns true if moved and false if illegal move
	 * 
	 * @param source  Location to be moved
	 * @param destination  Location to be moved too
	 * @return boolean  Whether or not the move was successful
	 */
	public boolean move(String source, String destination) {
		int[] s = convertTo(source);
		if(board[s[0]][s[1]] == null) {
			System.out.println("Illegal move, try again");
			return false;
		}
		
		
		ArrayList<String> moves = board[s[0]][s[1]].getMoves(source, board);
		//Tester:
		//System.out.println(moves);
		if(moves.contains(destination)) {
			int[] dest = convertTo(destination);
			board[dest[0]][dest[1]] = board[s[0]][s[1]];
			board[s[0]][s[1]] = null;
			updateMoveLists(board);
			dontMoveList("white", board);
			dontMoveList("black", board);
			//print();
			return true;
		}
		else {
			System.out.println("Illegal move, try again");
			return false;
		}
	

		//To make a move all you have to do is break the input into substrings and 
		//just find the second "destination" substring in the first "origin" locations
		//moveList. If it exists in the moveList then it is a valid move. 
		//I have yet to include special cases for castelling and stuff
	}
	
	
	
	/**
	 * This method first checks to see if moved piece is a pawn, then that is has moved all the way across the board. If so, the pawn is promoted to the requested
	 * piece if inputed and otherwise promoted to a queen.
	 * @param color Color of piece being moved
	 * @param input Line input of move
	 */
	public void promotion(String color, String[] input) {
		int[] s = convertTo(input[1]);
		if(board[s[0]][s[1]] instanceof Pawn) {
			
			if(color.equals("white") && s[0] == 0) {
				
				if(input.length == 3) {	
					switch(input[2]) {
					
					case "N":
						board[s[0]][s[1]] = null;
						board[s[0]][s[1]] = new Knight("white");
						break;
						
					case "R":
						board[s[0]][s[1]] = null;
						board[s[0]][s[1]] = new Rooke("white");
						break;
						
					case "B":
						board[s[0]][s[1]] = null;
						board[s[0]][s[1]] = new Bishop("white");
						break;
						
					case "Q":
						board[s[0]][s[1]] = null;
						board[s[0]][s[1]] = new Queen("white");
						break;
						
					default:
						board[s[0]][s[1]] = null;
						board[s[0]][s[1]] = new Queen("white");
					}					
				}
				else {
					board[s[0]][s[1]] = null;
					board[s[0]][s[1]] = new Queen("white");
				}
			}
			else if(color.equals("black") && s[0] == 7) {
				if(input.length == 3) {	
					switch(input[2]) {
					
					case "N":
						board[s[0]][s[1]] = null;
						board[s[0]][s[1]] = new Knight("black");
						break;
						
					case "R":
						board[s[0]][s[1]] = null;
						board[s[0]][s[1]] = new Rooke("black");
						break;
						
					case "B":
						board[s[0]][s[1]] = null;
						board[s[0]][s[1]] = new Bishop("black");
						break;
						
					case "Q":
						board[s[0]][s[1]] = null;
						board[s[0]][s[1]] = new Queen("black");
						break;
						
					default:
						board[s[0]][s[1]] = null;
						board[s[0]][s[1]] = new Queen("black");
					}					
				}
				else {
					board[s[0]][s[1]] = null;
					board[s[0]][s[1]] = new Queen("black");
				}
			}
		}
	}
	
	
	/**
	 * enPass method: Checks for the conditions of an En Passant to occure, if they do then it will automatically finish the move and adjust the board
	 *  
	 *  @param action line input
	 *  @param bord The playing board
	 * 
	 */
	public void enPass(ArrayList<String> action, Piece[][] bord)
	{
		//If the first argument is empty, and the second argument is a pawn that moved 2 spots: check for enPass
		int[] loca1 = convertTo(action.get(0));
		int[] loca2 = convertTo(action.get(1));
		
		if(bord[loca1[0]][loca1[1]] == null && bord[loca2[0]][loca2[1]] instanceof Pawn)
		{
			//Maybe enpassant
			if(bord[loca2[0]][loca2[1]].color.equals("white"))
			{
				
				if(loca2[1]-1 > -1)
				{
					if(bord[loca2[0]+1][loca2[1]-1] instanceof Pawn && bord[loca2[0]+1][loca2[1]-1].color.equals("black"))
					{
						//EN PASSANT
	//					System.out.println("Enpassant");
						bord[loca2[0]][loca2[1]] = null;
						bord[loca2[0]+1][loca2[1]-1] = null;
						bord[loca2[0]+1][loca2[1]] = new Pawn("black");
					}
				}
				if(loca2[1]+1 < 8)
				{
					if(bord[loca2[0]+1][loca2[1]+1] instanceof Pawn && bord[loca2[0]+1][loca2[1]+1].color.equals("black") && bord[loca2[0]+1][loca2[1]] == null)
					{
						//EN PASSANT
		//				System.out.println("Enpassant");
						bord[loca2[0]][loca2[1]] = null;
						bord[loca2[0]+1][loca2[1]+1] = null;
						bord[loca2[0]+1][loca2[1]] = new Pawn("black");
					}
				}
			}
			else
			{
				if(loca2[1]-1 > -1)
				{
					if(bord[loca2[0]-1][loca2[1]-1] instanceof Pawn && bord[loca2[0]-1][loca2[1]-1].color.equals("white"))
					{
						//EN PASSANT
			//			System.out.println("Enpassant");
						bord[loca2[0]][loca2[1]] = null;
						bord[loca2[0]-1][loca2[1]-1] = null;
						bord[loca2[0]-1][loca2[1]] = new Pawn("white");
					}
				}
				if(loca2[1]+1 < 8)
				{
					if(bord[loca2[0]-1][loca2[1]+1] instanceof Pawn && bord[loca2[0]-1][loca2[1]+1].color.equals("white") && bord[loca2[0]-1][loca2[1]] == null)
					{
						//EN PASSANT
				//		System.out.println("Enpassant");
						bord[loca2[0]][loca2[1]] = null;
						bord[loca2[0]-1][loca2[1]+1] = null;
						bord[loca2[0]-1][loca2[1]] = new Pawn("white");
					}
				}
			}
		}
		
	}
	
	
	public void cast(ArrayList<String> locs, Piece[][] bord)
	{
		int[] loc = new int[2];
		loc = convertTo(locs.get(1));
		if(!(bord[loc[0]][loc[1]] instanceof King))
		{
			return;
		}
		
		if(locs.get(0).equals("e1") && locs.get(1).equals("g1"))
		{
	//		System.out.println("Castle");
			bord[7][7] = null;
			bord[7][5] = new Rooke("white");
		}
		if(locs.get(0).equals("e1") && locs.get(1).equals("c1"))
		{
	//		System.out.println("Castle");
			bord[7][0] = null;
			bord[7][3] = new Rooke("white");
		}
		if(locs.get(0).equals("e8") && locs.get(1).equals("g8"))
		{
	//		System.out.println("Castle");
			bord[0][7] = null;
			bord[0][5] = new Rooke("black");
		}
		if(locs.get(0).equals("e8") && locs.get(1).equals("c8"))
		{
	//		System.out.println("Castle");
			bord[0][7] = null;
			bord[0][5] = new Rooke("black");
		}
		return;
	}
	
	/**
	 * method resign: takes parameter of color that is resigning and announces the winning of the other team
	 * 
	 * @param color Color of player resigning
	 */
	public void resign(String color) {
		if(color.equals("white")) {
			System.out.println("Black wins!");
		}
		else {
			System.out.println("White wins!");
		}
	}
	
	
	
	/**
	 * check method: after each move check is called to run through all of the lists of possible moves of the opponents pieces.
	 * 		if the King of string color is in the list, it is under threat and check is called. 
	 * @param color Color of player looking for Check
	 */
	public void check(String color) { 
		int[] kingsLocation = new int[2];
		ArrayList<String> masterlist = new ArrayList<String>();
		for(int i=0; i<SIZE; i++) {
			for(int j=0; j<SIZE; j++) {
				if(color.equals("white")) {
					if(board[i][j] == null) {
						continue;
					}
					else if(!(board[i][j] instanceof King) && board[i][j].color.equals("black")) {
						int[] temp = new int[2];
						temp[0] = i;
						temp[1] = j;
						masterlist.addAll(board[i][j].getMoves(convertBack(temp), board));
					} 
					else if((board[i][j] instanceof King) && board[i][j].color.equals("white")) {
						kingsLocation[0] = i;
						kingsLocation[1] = j;
					}
				}
				
				else {
					if(board[i][j] == null) {
						continue;
					}
					else if(!(board[i][j] instanceof King) && board[i][j].color.equals("white")) {
						int[] temp = new int[2];
						temp[0] = i;
						temp[1] = j;
						masterlist.addAll(board[i][j].getMoves(convertBack(temp), board));
					} 
					else if((board[i][j] instanceof King) && board[i][j].color.equals("black")) {
						kingsLocation[0] = i;
						kingsLocation[1] = j;
					}
				}
			}
		}
		
		String king = convertBack(kingsLocation);
		if(masterlist.contains(king)) {
			System.out.println("\nCheck\n");
		} 
		
	}
	/**
	 * stalemate method: Checks to make sure the stalemate conditions are met, if they are then signals game over
	 * 
	 * @param color Color of current player
	 * @param bord The playing board
	 * @return void 
	 */
	public boolean stalemate(String color, Piece[][] bord) 
	{
		int i = 0;
		int j = 0;
		ArrayList<String> kingList = new ArrayList<String>();
		String loc = "";
		ArrayList<String> dont = new ArrayList<String>();
		int[] test = new int[2];
		updateMoveLists(bord);
		ArrayList<String> victim = new ArrayList<String>();

		
		for(i = 0; i < 8; i++)
		{
			for(j = 0; j < 8; j++)
			{
				if(bord[i][j] instanceof King && bord[i][j].color.equals(color))
				{
					kingList.addAll(bord[i][j].moveList);
					test[0] = i;
					test[1] = j;
					loc = convertBack(test);
					dontMoveList(convertBack(test), bord);
					//North
					if(i-1 > -1)
					{
						test[0] = i-1;
						test[1] = j;
						kingList.add(convertBack(test));
						//NorthEast
						if(j+1 < 8)
						{
							test[0] = i-1;
							test[1] = j+1;
							kingList.add(convertBack(test));
						}
						//NorthWest
						if(j-1 > -1)
						{
							test[0] = i-1;
							test[1] = j-1;
							kingList.add(convertBack(test));
						}
					}
					//South
					if(i+1 < 8)
					{	
						
						test[0] = i+1;
						test[1] = j;
						kingList.add(convertBack(test));
						//SouthEast
						if(j+1 < 8)
						{
							test[0] = i+1;
							test[1] = j+1;
							kingList.add(convertBack(test));
						}
						//SouthWest
						if(j-1 > -1)
						{
							test[0] = i+1;
							test[1] = j-1;
							kingList.add(convertBack(test));
						}
					}
					//West
					if(j-1 > -1)
					{
						test[0] = i;
						test[1] = j-1;
						kingList.add(convertBack(test));
					}
					//East
					if(j+1 < 8)
					{
						test[0] = i;
						test[1] = j+1;
						kingList.add(convertBack(test));
					}
				}
				else if(bord[i][j] != null && bord[i][j].color.equals(color))
				{
					victim.addAll(bord[i][j].moveList);
				}
				
				if(bord[i][j] != null && bord[i][j].color!=(color))
				{
					dont.addAll(bord[i][j].moveList);
				}
				
			}
			
		}
		//EndFor
		
//		System.out.println(color + " has " + kingList);
//		System.out.println(color + " cant " + dont);

		
		ArrayList<String> remove = new ArrayList<String>();
		remove.addAll(kingList);
		for(String item : kingList)
		{
			if(dont.contains(item))
			{
				remove.remove(item);
				remove.remove(item);
			}
		}
		
		boolean inCheck;
		if(dont.contains(loc))
		{
			inCheck = true;
		}
		else
		{
			inCheck = false;
		}
		
		boolean save = false;		
		
		//System.out.println("Victim:" + victim);
		if(victim.isEmpty())
		{
			save = false;
		}
		else
		{
			save = true;
		}
		
		
	//	System.out.println("remove: " + remove);
		if(remove.isEmpty() && !inCheck  && !save)
		{
			//System.out.println("Checkmate");
			if(color.equals("white"))
			{
				System.out.println("Stalemate");
			}
			else
			{
				System.out.println("Stalemate");
			}
			gamestatus = false;
			return true;
		}
		return false;
	}
	/**
	 * checkmate method: Checks for the victory conditions of a checkmate, signals a victory if found
	 *  
	 *  
	 * @param color Color of player
	 * @param bord Playing board
	 * @return void
	 */
	public boolean checkmate(String color, Piece[][] bord) 
	{
		int i = 0;
		int j = 0;
		ArrayList<String> kingList = new ArrayList<String>();
		ArrayList<String> kingDont = new ArrayList<String>();
		String loc = "";
		ArrayList<String> dont = new ArrayList<String>();
		int[] test = new int[2];
		updateMoveLists(bord);
				
		for(i = 0; i < 8; i++)
		{
			for(j = 0; j < 8; j++)
			{
				if(bord[i][j] instanceof King && bord[i][j].color.equals(color))
				{
					kingList.addAll(bord[i][j].moveList);
					kingDont.addAll(bord[i][j].dontList);
					test[0] = i;
					test[1] = j;
					loc = convertBack(test);
					dontMoveList(convertBack(test), bord);
					//North
					if(i-1 > -1)
					{
						test[0] = i-1;
						test[1] = j;
						if(bord[i-1][j] == null)
						kingList.add(convertBack(test));
						//NorthEast
						if(j+1 < 8)
						{
							test[0] = i-1;
							test[1] = j+1;
							if(bord[i-1][j+1] == null)
							kingList.add(convertBack(test));
						}
						//NorthWest
						if(j-1 > -1)
						{
							test[0] = i-1;
							test[1] = j-1;
							if(bord[i-1][j-1] == null)
							kingList.add(convertBack(test));
						}
					}
					//South
					if(i+1 < 8)
					{	
						
						test[0] = i+1;
						test[1] = j;
						if(bord[i+1][j] == null)

						kingList.add(convertBack(test));
						//SouthEast
						if(j+1 < 8)
						{
							test[0] = i+1;
							test[1] = j+1;
							if(bord[i+1][j+1] == null)
							kingList.add(convertBack(test));
						}
						//SouthWest
						if(j-1 > -1)
						{
							test[0] = i+1;
							test[1] = j-1;
							if(bord[i+1][j-1] == null)
							kingList.add(convertBack(test));
						}
					}
					//West
					if(j-1 > -1)
					{
						test[0] = i;
						test[1] = j-1;
						if(bord[i][j-1] == null)
						kingList.add(convertBack(test));
					}
					//East
					if(j+1 < 8)
					{
						test[0] = i;
						test[1] = j+1;
						if(bord[i][j+1] == null)
						kingList.add(convertBack(test));
					}
				}
				
			
				if(bord[i][j] != null && bord[i][j].color!=(color))
				{
					dont.addAll(bord[i][j].moveList);
				}
				
			}
			
		}
		//EndFor
		
//		System.out.println(color + " has " + kingList);
//		System.out.println(color + " cant " + dont);

		
		ArrayList<String> remove = new ArrayList<String>();
		remove.addAll(kingList);
		for(String item : kingList)
		{
			if(dont.contains(item))
			{
				remove.remove(item);
				remove.remove(item);
			}
		}
		
		boolean inCheck;
		if(dont.contains(loc))
		{
			inCheck = true;
		}
		else
		{
			inCheck = false;
		}
		
	//	System.out.println("remove:" + remove);
	//	System.out.println(inCheck);
		
	//	System.out.println("remove: " + remove);
		if(remove.isEmpty() && inCheck)
		{
			//System.out.println("Checkmate");
			if(color.equals("white"))
			{
				System.out.println("Black wins");
			}
			else
			{
				System.out.println("White wins");
			}
			gamestatus = false;
			return true;
		}
		return false;
	}
	
	/**
	 * init method: when the program runs, init is first called and creates the appropriate piece objects and place them in the correct spots
	 * 
	 *
	 */
	
	public void init() {
		board = new Piece[SIZE][SIZE];
		
		for(int i=0; i<SIZE; i++) {
			board[1][i] = new Pawn("black");
		}
		
		for(int i=0; i<SIZE; i++) {
			board[6][i] = new Pawn("white");
		}
		
		board[0][0] = new Rooke("black");
		board[0][7] = new Rooke("black");
		
		board[7][0] = new Rooke("white");
		board[7][7] = new Rooke("white");
		
		board[0][1] = new Knight("black");
		board[0][6] = new Knight("black");
		
		board[7][1] = new Knight("white");
		board[7][6] = new Knight("white");
		
		board[0][2] = new Bishop("black");
		board[0][5] = new Bishop("black");
		
		board[7][2] = new Bishop("white");
		board[7][5] = new Bishop("white");
		
		board[0][3] = new Queen("black");
		board[0][4] = new King("black");
		
		board[7][3] = new Queen("white");
		board[7][4] = new King("white");
		
	}
	
	
	
	/**
	 * method print: this method is called to print the 2d array of pieces (the board)
	 * 
	 */
	
	
	public void print() {
		int row = 8;
		
		for(int i=0; i<SIZE; i++) {
			
			for(int j=0; j<SIZE; j++) {
				
				if(board[i][j] == null) {
					
					if(i%2 == 0 && j%2 != 0) {
						System.out.print("## ");
					} 
					
					else if(i%2 != 0 && j%2 == 0) {
						System.out.print("## ");
					} 
					
					else {
						System.out.print("   ");
					}

				} 
				
				else {
					System.out.print(board[i][j].toString() + " ");
				}
				
			}
			System.out.print(row);
			System.out.print("\n");
			row--;
		}
		System.out.println("a  b  c  d  e  f  g  h");
		
		
		//EVERYTHING BELOW HERE IS TEST CODE
		
		//These three methods should be called every time after a move is made.
		//updateMoveLists(board);
		//dontMoveList("white", board);
		//dontMoveList("black", board);

		//To make a move all you have to do is break the input into substrings and 
		//just find the second "destination" substring in the first "origin" locations
		//moveList. If it exists in the moveList then it is a valid move. 
		//I have yet to include special cases for castelling and stuff
		
		//System.out.println("Black Knight:" + board[0][1].moveList); //2 Valid moves
		//System.out.println("Black Rook:" + board[0][0].moveList); //No valid moves
		//System.out.println("BKing Dont:" + board[0][4].dontList); //All valid moves for enemy pieces with pawns being a special case

	}
	
	public void dontMoveList(String color, Piece[][] bord)
	{
		ArrayList<String> sideList = new ArrayList<String>();
		int i = 0;
		int j = 0;
		for(i = 0; i < 8; i++)
		{
			for(j = 0; j < 8; j++)
			{
				if(bord[i][j] != null && board[i][j].color.equals(color))
				{
					
					//Special Case: Pawns
					if(board[i][j] instanceof Pawn)
					{
						ArrayList<String> danger = new ArrayList<String>();
						int[] test = new int[2];
						test[0] = i;
						test[1] = j;
						if(bord[i][j].color.equals("black"))
						{
							if(j == 0)
							{
								test[0] = i+1;
								test[1] = j+1;
								danger.add(convertBack(test));
							}
							else if(j == 7)
							{
								test[0] = i+1;
								test[1] = j-1;
								danger.add(convertBack(test));

							}
							else
							{
								test[0] = i+1;
								test[1] = j+1;
								danger.add(convertBack(test));

								test[0] = i+1;
								test[1] = j-1;
								danger.add(convertBack(test));

							}
							
						}
						else
						{
							if(j == 0)
							{
								test[0] = i-1;
								test[1] = j+1;
								danger.add(convertBack(test));
							}
							else if(j == 7)
							{
								test[0] = i-1;
								test[1] = j-1;
								danger.add(convertBack(test));

							}
							else
							{
								test[0] = i-1;
								test[1] = j+1;
								danger.add(convertBack(test));

								test[0] = i-1;
								test[1] = j-1;
								danger.add(convertBack(test));

							}
						}
						sideList.addAll(danger);	
					}
					else
					{
						sideList.addAll(bord[i][j].moveList);
					}
				}
			}
		}
		
		for(i = 0; i < 8; i++)
		{
			for(j = 0; j < 8; j++)
			{
				if(bord[i][j] != null && !board[i][j].color.equals(color) && bord[i][j] instanceof King)
				{
					bord[i][j].dontList = sideList;
				}
			}
			
		}
	
		
	}
	public void updateMoveLists(Piece[][] bord)
	{
		int i = 0;
		int j = 0;
		for(i = 0; i < 8; i++)
		{
			for(j = 0; j < 8; j++)
			{
				if(bord[i][j] != null)
				{
					int[] test = new int[2];
					test[0] = i;
					test[1] = j;
					
					bord[i][j].moveList = bord[i][j].getMoves(convertBack(test), board);
				}
			}
		}
	}
	/**
	 * convertBack method: The program uses 2 kinds of location indicators, strings and arrays. This converts the arrays into strings
	 * 
	 * @param item location to be converted
	 * @return  location
	 */
	public String convertBack(int[] item)
	{
		
		String fin = "";
		switch(item[1])
		{
		case 0:
			fin += 'a';
			break;
		case 1:
			fin += 'b';
			break;
		case 2:
			fin += 'c';
			break;
		case 3:
			fin += 'd';
			break;
		case 4:
			fin += 'e';
			break;
		case 5:
			fin += 'f';
			break;
		case 6:
			fin += 'g';
			break;
		case 7:
			fin += 'h';
			break;
		}
		switch(item[0])
		{
		case 0:
			fin += '8';
			break;
		case 1:
			fin += '7';
			break;
		case 2:
			fin += '6';
			break;
		case 3:
			fin += '5';
			break;
		case 4:
			fin += '4';
			break;
		case 5:
			fin += '3';
			break;
		case 6:
			fin += '2';
			break;
		case 7:
			fin += '1';
			break;
		}
		
		return fin;
	}
	/**
	 * convertTo method: The program uses 2 kinds of location indicators, strings and arrays. This converts the strings used as input into arrays of ints
	 * 	  
	 * @param item location to be converted
	 * @return array of location
	 */
	public int[] convertTo(String item)
	{
		
		int[] arr = new int[]{-1,-1};
		switch(item.charAt(0))
		{
		case 'a':
			arr[1] = 0;
			break;
		case 'b':
			arr[1] = 1;
			break;
		case 'c':
			arr[1] = 2;
			break;
		case 'd':
			arr[1] = 3;
			break;
		case 'e':
			arr[1] = 4;
			break;
		case 'f':
			arr[1] = 5;
			break;
		case 'g':
			arr[1] = 6;
			break;
		case 'h':
			arr[1] = 7;
			break;
		}
		switch(item.charAt(1))
		{
		case '1':
			arr[0] = 7;
			break;
		case '2':
			arr[0] = 6;
			break;
		case '3':
			arr[0] = 5;
			break;
		case '4':
			arr[0] = 4;
			break;
		case '5':
			arr[0] = 3;
			break;
		case '6':
			arr[0] = 2;
			break;
		case '7':
			arr[0] = 1;
			break;
		case '8':
			arr[0] = 0;
			break;
		}
		
		return arr;
	}
	
}

