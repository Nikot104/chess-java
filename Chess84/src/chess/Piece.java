/**
 * @author Michael DeDreu
 * @author Nikita Kolotov
 */


package chess;

import java.util.ArrayList;

public abstract class Piece {
	
	/**
	 * Abstract class Piece for all chess pieces. 
	 * 
	 */
	public String color;
	public ArrayList<String> moveList;
	public ArrayList<String> dontList;
	public boolean move() {
		return true;
	}
	public ArrayList<String> getMoves(String loc, Piece[][] bord){
		ArrayList<String> fin = new ArrayList<String>();
		return fin;
	}
	
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
	
}

//Done
class Pawn extends Piece {
	
	ArrayList<String> moveList = new ArrayList<String>();
	
	public Pawn(String color) {
		this.color = color;
	}
	
	public String toString() {
		if(color.equals("white")) {
			return "wp";
		} else {
			return "bp";
		}
	}
	
	@Override
	public ArrayList<String> getMoves(String loc, Piece[][] bord)
	{
		
		ArrayList<String> fin = new ArrayList<String>();
		int[] loca = new int[2];
		loca = convertTo(loc);
		int[] test = new int[2];
		
//		System.out.println("loc:" + loc);
	//	System.out.println("tra:" + loca[0] + " " + loca[1]);
		if(this.color.equals("white"))
		{
			//Up1
			if(loca[0]-1 > -1 && bord[loca[0]-1][loca[1]] == null)
			{
				test[0] = loca[0]-1;
				test[1] = loca[1];
				fin.add(convertBack(test));
			}
			//Up2
			if(loca[0] == 6 && bord[loca[0]-1][loca[1]] == null &&  bord[loca[0]-2][loca[1]] == null)
			{
				test[0] = loca[0]-2;
				test[1] = loca[1];
				fin.add(convertBack(test));
			}
			//Up1Left1
			if(loca[1]-1 > -1 && loca[0] -1 > -1 && bord[loca[0]-1][loca[1]-1] != null && !bord[loca[0]-1][loca[1]-1].color.equals(this.color))
			{
				test[0] = loca[0]-1;
				test[1] = loca[1]-1;
				fin.add(convertBack(test));
			}
			//Up1Right1
			if(loca[1]+1 < 8 && loca[0] -1 > -1 && bord[loca[0]-1][loca[1]+1] != null &&!bord[loca[0]-1][loca[1]+1].color.equals(this.color))
			{
				test[0] = loca[0]-1;
				test[1] = loca[1]+1;
				fin.add(convertBack(test));
			}
		}
		else
		{
			//Down1
			if(loca[0]+1 < 8 && bord[loca[0]+1][loca[1]] == null)
			{
				test[0] = loca[0]+1;
				test[1] = loca[1];
				fin.add(convertBack(test));
			}
			//Down2
			if(loca[0] == 1 && bord[loca[0]+1][loca[1]] == null &&  bord[loca[0]+2][loca[1]] == null)
			{
				test[0] = loca[0]+2;
				test[1] = loca[1];
				fin.add(convertBack(test));
			}
			//Down1Left1
			if(loca[1]-1 > -1 && loca[0] +1 < 8 && bord[loca[0]+1][loca[1]-1] != null  && !bord[loca[0]+1][loca[1]-1].color.equals(this.color))
			{
				test[0] = loca[0]+1;
				test[1] = loca[1]-1;
				fin.add(convertBack(test));
			}
			//Down1Right1
			if(loca[1]+1 < 8 && loca[0] +1 < 8 && bord[loca[0]+1][loca[1]+1] != null && !bord[loca[0]+1][loca[1]+1].color.equals(this.color))
			{
				test[0] = loca[0]+1;
				test[1] = loca[1]+1;
				fin.add(convertBack(test));
			}
		}
		return fin;
	}
	
}

//Done
class King extends Piece {
	
	ArrayList<String> moveList = new ArrayList<String>();
	ArrayList<String> dontList = new ArrayList<String>();
	boolean castleRight = false;
	boolean castleLeft = false;
	
	public King(String color) {
		this.color = color;
		
	}
	
	
	public String toString() {
		if(color.equals("white")) {
			return "wK";
		} else {
			return "bK";
		}
	}
	
	@Override
	public ArrayList<String> getMoves(String loc, Piece[][] bord)
	{
		ArrayList<String> fin = new ArrayList<String>();
		int[] loca = new int[2];
		loca = convertTo(loc);
		int[] test = new int[2];
		
		//North
		if(loca[0]-1 > -1 && bord[loca[0]-1][loca[1]] == null)
		{
			test[0] = loca[0]-1;
			test[1] = loca[1];
			fin.add(convertBack(test));
		}
		else if(loca[0]-1 > -1 && !bord[loca[0]-1][loca[1]].color.equals(this.color))
		{
			test[0] = loca[0]-1;
			test[1] = loca[1];
			fin.add(convertBack(test));
		}
		//NorthEast
		if(loca[0]-1 > -1 && loca[1]+1 < 8 && bord[loca[0]-1][loca[1]+1] == null)
		{
			test[0] = loca[0]-1;
			test[1] = loca[1]+1;
			fin.add(convertBack(test));
		}
		else if(loca[0]-1 > -1 && loca[1]+1 < 8 && !bord[loca[0]-1][loca[1]+1].color.equals(this.color))
		{
			test[0] = loca[0]-1;
			test[1] = loca[1]+1;
			fin.add(convertBack(test));
		}
		//East
		if(loca[1]+1 < 8 && bord[loca[0]][loca[1]+1] == null)
		{
			test[0] = loca[0];
			test[1] = loca[1]+1;
			fin.add(convertBack(test));
		}
		else if(loca[1]+1 < 8 && !bord[loca[0]][loca[1]+1].color.equals(this.color))
		{
			test[0] = loca[0];
			test[1] = loca[1]+1;
			fin.add(convertBack(test));
		}
		//SouthEast
		if(loca[0]+1 < 8 && loca[1]+1 < 8 && bord[loca[0]+1][loca[1]+1] == null)
		{
			test[0] = loca[0]+1;
			test[1] = loca[1]+1;
			fin.add(convertBack(test));
		}
		else if(loca[0]+1 < 8 && loca[1]+1 < 8 && !bord[loca[0]+1][loca[1]+1].color.equals(this.color))
		{
			test[0] = loca[0]+1;
			test[1] = loca[1]+1;
			fin.add(convertBack(test));
		}
		//South
		if(loca[0]+1 < 8 && bord[loca[0]+1][loca[1]] == null)
		{
			test[0] = loca[0]+1;
			test[1] = loca[1];
			fin.add(convertBack(test));
		}
		else if(loca[0]+1 < 8 && !bord[loca[0]+1][loca[1]].color.equals(this.color))
		{
			test[0] = loca[0]+1;
			test[1] = loca[1];
			fin.add(convertBack(test));
		}
		//SouthWest
		if(loca[0]+1 < 8 && loca[1]-1 > -1 && bord[loca[0]+1][loca[1]-1] == null)
		{
			test[0] = loca[0]+1;
			test[1] = loca[1]-1;
			fin.add(convertBack(test));
		}
		else if(loca[0]+1 < 8 && loca[1]-1 > -1 && !bord[loca[0]+1][loca[1]-1].color.equals(this.color))
		{
			test[0] = loca[0]+1;
			test[1] = loca[1]-1;
			fin.add(convertBack(test));
		}
		//West
		if(loca[1]-1 > -1 && bord[loca[0]][loca[1]-1] == null)
		{
			test[0] = loca[0];
			test[1] = loca[1]-1;
			fin.add(convertBack(test));
		}
		else if(loca[1]-1 > -1 && !bord[loca[0]][loca[1]-1].color.equals(this.color))
		{
			test[0] = loca[0];
			test[1] = loca[1]-1;
			fin.add(convertBack(test));
		}
		//NorthWest
		if(loca[0]-1 > -1 && loca[1]-1 > -1 && bord[loca[0]-1][loca[1]-1] == null)
		{
			test[0] = loca[0]-1;
			test[1] = loca[1]-1;
			fin.add(convertBack(test));
		}
		else if(loca[0]-1 > -1 && loca[1]-1 > -1 &&!bord[loca[0]-1][loca[1]-1].color.equals(this.color))
		{
			test[0] = loca[0]-1;
			test[1] = loca[1]-1;
			fin.add(convertBack(test));
		}
		fin.addAll(castle(loc,bord));
		
		return fin;
	}
	
	//Castle
	public ArrayList<String> castle(String loc, Piece[][] bord)
	{
		ArrayList<String> fin = new ArrayList<String>();
		int[] loca = new int[2];
		loca = convertTo(loc);
		int[] test = new int[2];

		castleLeft = false;
		castleRight = false;
		
		if(bord[loca[0]][loca[1]].color.equals("white"))
		{
			if(loca[0] == 7 && loca[1] == 4)
			{
				//King Spawn
				//Queen O-O-O
				if(bord[7][0] instanceof Rooke && bord[7][0].color.equals("white"))
				{
					if(bord[7][1] == null && bord[7][2] == null && bord[7][3] == null)
					{
						castleLeft = true;
						test[0] = 7;
						test[1] = 2;
						fin.add(convertBack(test));
					}
				}
				if(bord[7][7] instanceof Rooke && bord[7][7].color.equals("white"))
				{
					if(bord[7][5] == null && bord[7][6] == null)
					{
						castleRight = true;
						test[0] = 7;
						test[1] = 6;
						fin.add(convertBack(test));
					}
				}
			}
			else
			{
				return fin;
			}
		}
		else
		{
			if(loca[0] == 0 && loca[1] == 4)
			{
				//King Spawn
				if(bord[0][0] instanceof Rooke && bord[0][0].color.equals("black"))
				{
					if(bord[0][1] == null && bord[0][2] == null && bord[0][3] == null)
					{
						castleLeft = true;
						test[0] = 0;
						test[1] = 2;
						fin.add(convertBack(test));
					}
				}
				if(bord[0][7] instanceof Rooke && bord[0][7].color.equals("black"))
				{
					if(bord[0][5] == null && bord[0][6] == null)
					{
						castleRight = true;
						test[0] = 0;
						test[1] = 6;
						fin.add(convertBack(test));
					}
				}
			}
			else
			{
				return fin;
			}
		}	
		
		fin.removeIf(convertBack(loca)::equals);
		
		return fin;
	}
}

//Done
class Queen extends Piece {
	
	ArrayList<String> moveList = new ArrayList<String>();
	
	public Queen(String color) {
		this.color = color;
	}
	
	public String toString() {
		if(color.equals("white")) {
			return "wQ";
		} else {
			return "bQ";
		}
	}
	
	@Override
	public ArrayList<String> getMoves(String loc, Piece[][] bord)
	{
		ArrayList<String> fin = new ArrayList<String>();
		int[] loca = new int[2];
		loca = convertTo(loc);
		int[] test = new int[2];
		
		
		int i = 1;
		//NorthWest
		while((loca[0]-i) > -1 && (loca[1]-i) > -1)
		{
			if(bord[loca[0]-i][loca[1]-i] == null)
			{
				test[0] = loca[0]-i;
				test[1] = loca[1]-i;
				fin.add(convertBack(test));
			}
			else if(!bord[loca[0]-i][loca[1]-i].color.equals(this.color))
			{
				test[0] = loca[0]-i;
				test[1] = loca[1]-i;
				fin.add(convertBack(test));
				break;
			}
			else
			{
				break;
			}
			i++;
		}
		i = 1;
		//NorthEast
		while((loca[0]-i) > -1 && (loca[1]+i) < 8)
		{
			if(bord[loca[0]-i][loca[1]+i] == null)
			{
				test[0] = loca[0]-i;
				test[1] = loca[1]+i;
				fin.add(convertBack(test));
			}
			else if(!bord[loca[0]-i][loca[1]+i].color.equals(this.color))
			{
				test[0] = loca[0]-i;
				test[1] = loca[1]+i;
				fin.add(convertBack(test));
				break;
			}
			else
			{
				break;
			}
			i++;
		}
		i = 1;
		//SouthEast
		while((loca[0]+i) < 8 && (loca[1]+i) < 8)
		{
			if(bord[loca[0]+i][loca[1]+i] == null)
			{
				test[0] = loca[0]+i;
				test[1] = loca[1]+i;
				fin.add(convertBack(test));
			}
			else if(!bord[loca[0]+i][loca[1]+i].color.equals(this.color))
			{
				test[0] = loca[0]+i;
				test[1] = loca[1]+i;
				fin.add(convertBack(test));
				break;
			}
			else
			{
				break;
			}
			i++;
		}
		i = 1;
		//SouthWest
		while((loca[0]+i) < 8 && (loca[1]-i) > -1)
		{
			if(bord[loca[0]+i][loca[1]-i] == null)
			{
				test[0] = loca[0]+i;
				test[1] = loca[1]-i;
				fin.add(convertBack(test));
			}
			else if(!bord[loca[0]+i][loca[1]-i].color.equals(this.color))
			{
				test[0] = loca[0]+i;
				test[1] = loca[1]-i;
				fin.add(convertBack(test));
				break;
			}
			else
			{
				break;
			}
			i++;
		}
		
		//End of Diagonals
		
		//UP
		for(i = loca[0]+1; i < 8; i++)
		{
			if(bord[i][loca[1]] == null)
			{
				test[0] = i;
				test[1] = loca[1];
				fin.add(convertBack(test));
			}
			else if(!bord[i][loca[1]].color.equals(this.color))
			{
				test[0] = i;
				test[1] = loca[1];
				fin.add(convertBack(test));
				break;
			}
			else
			{
				break;
			}
		}
		//DOWN
		for(i = loca[0]-1; i > -1; i--)
		{
			if(bord[i][loca[1]] == null)
			{
				test[0] = i;
				test[1] = loca[1];
				fin.add(convertBack(test));
			}
			else if(!bord[i][loca[1]].color.equals(this.color))
			{
				test[0] = i;
				test[1] = loca[1];
				fin.add(convertBack(test));
				break;
			}
			else
			{
				break;
			}
		}
		//RIGHT
		for(i = loca[1]+1; i < 8; i++)
		{
			if(bord[loca[0]][i] == null)
			{
				test[0] = loca[0];
				test[1] = i;
				fin.add(convertBack(test));
			}
			else if(!bord[loca[0]][i].color.equals(this.color))
			{
				test[0] = loca[0];
				test[1] = i;
				fin.add(convertBack(test));
				break;
			}
			else
			{
				break;
			}
		}
		//RIGHT
		for(i = loca[1]-1; i > -1; i--)
		{
			if(bord[loca[0]][i] == null)
			{
				test[0] = loca[0];
				test[1] = i;
				fin.add(convertBack(test));
			}
			else if(!bord[loca[0]][i].color.equals(this.color))
			{
				test[0] = loca[0];
				test[1] = i;
				fin.add(convertBack(test));
				break;
			}
			else
			{
				break;
			}
		}
						
		return fin;
	}
	
}

//Done
class Rooke extends Piece {
	
	ArrayList<String> moveList = new ArrayList<String>();
	
	public Rooke(String color) {
		this.color = color;
	}
	
	public String toString() {
		if(color.equals("white")) {
			return "wR";
		} else {
			return "bR";
		}
	}
	
	@Override
	public ArrayList<String> getMoves(String loc, Piece[][] bord)
	{
		
		ArrayList<String> fin = new ArrayList<String>();
		int[] loca = new int[2];
		loca = convertTo(loc);
		int[] test = new int[2];
		
		//Down
		for(int i = loca[0]+1; i < 8; i++)
		{
			if(bord[i][loca[1]] == null)
			{
				test[0] = i;
				test[1] = loca[1];
				fin.add(convertBack(test));
			}
			else if(!bord[i][loca[1]].color.equals(this.color))
			{
				test[0] = i;
				test[1] = loca[1];
				fin.add(convertBack(test));
				break;
			}
			else
			{
				break;
			}
		}
		//Up
		for(int i = loca[0]-1; i > -1; i--)
		{
			if(bord[i][loca[1]] == null)
			{
				test[0] = i;
				test[1] = loca[1];
				fin.add(convertBack(test));
			}
			else if(!bord[i][loca[1]].color.equals(this.color))
			{
				test[0] = i;
				test[1] = loca[1];
				fin.add(convertBack(test));
				break;
			}
			else
			{
				break;
			}
		}
		//RIGHT
		for(int i = loca[1]+1; i < 8; i++)
		{
			if(bord[loca[0]][i] == null)
			{
				test[0] = loca[0];
				test[1] = i;
				fin.add(convertBack(test));
			}
			else if(!bord[loca[0]][i].color.equals(this.color))
			{
				test[0] = loca[0];
				test[1] = i;
				fin.add(convertBack(test));
				break;
			}
			else
			{
				break;
			}
		}
		//RIGHT
		for(int i = loca[1]-1; i > -1; i--)
		{
			if(bord[loca[0]][i] == null)
			{
				test[0] = loca[0];
				test[1] = i;
				fin.add(convertBack(test));
			}
			else if(!bord[loca[0]][i].color.equals(this.color))
			{
				test[0] = loca[0];
				test[1] = i;
				fin.add(convertBack(test));
				break;
			}
			else
			{
				break;
			}
		}
		
		return fin;
	}
}

//Done
class Knight extends Piece {
	
	ArrayList<String> moveList = new ArrayList<String>();
	
	public Knight(String color) {
		this.color = color;
	}
	
	public String toString() {
		if(color.equals("white")) {
			return "wN";
		} else {
			return "bN";
		}
	}
	
	@Override
	public ArrayList<String> getMoves(String loc, Piece[][] bord)
	{
		ArrayList<String> fin = new ArrayList<String>();
		int[] loca = new int[2];
		loca = convertTo(loc);
		int[] test = new int[2];
		
		
		//Up2Left1
		if((loca[0]+2 < 8 && loca[1]-1 > -1) && (bord[loca[0]+2][loca[1]-1] == null || !bord[loca[0]+2][loca[1]-1].color.equals(this.color)))
		{
			test[0] = loca[0]+2;
			test[1] = loca[1]-1;
			fin.add(convertBack(test));
		}
		//Up2Right1
		if((loca[0]+2 < 8 && loca[1]+1 < 8) && (bord[loca[0]+2][loca[1]+1] == null || !bord[loca[0]+2][loca[1]+1].color.equals(this.color)))
		{
			test[0] = loca[0]+2;
			test[1] = loca[1]+1;
			fin.add(convertBack(test));

		}
		//Right2Up1
		if((loca[0]+1 < 8 && loca[1]+2 < 8) && (bord[loca[0]+1][loca[1]+2] == null || !bord[loca[0]+1][loca[1]+2].color.equals(this.color)))
		{
			test[0] = loca[0]+1;
			test[1] = loca[1]+2;
			fin.add(convertBack(test));

		}
		//Right2Down1
		if((loca[0]-1 > -1 && loca[1]+2 < 8) && (bord[loca[0]-1][loca[1]+2] == null || !bord[loca[0]-1][loca[1]+2].color.equals(this.color)))
		{
			test[0] = loca[0]-1;
			test[1] = loca[1]+2;
			fin.add(convertBack(test));

		}
		//Down2Right1
		if((loca[0]-2 > -1 && loca[1]+1 < 8) && (bord[loca[0]-2][loca[1]+1] == null || !bord[loca[0]-2][loca[1]+1].color.equals(this.color)))
		{
			test[0] = loca[0]-2;
			test[1] = loca[1]+1;
			fin.add(convertBack(test));

		}
		//Down2Left1
		if((loca[0]-2 > -1 && loca[1]-1 > -1) && (bord[loca[0]-2][loca[1]-1] == null || !bord[loca[0]-2][loca[1]-1].color.equals(this.color)))
		{
			test[0] = loca[0]-2;
			test[1] = loca[1]-1;
			fin.add(convertBack(test));

		}
		//Left2Down1
		if((loca[0]-1 > -1 && loca[1]-2 > -1) && (bord[loca[0]-1][loca[1]-2] == null || !bord[loca[0]-1][loca[1]-2].color.equals(this.color)))
		{
			test[0] = loca[0]-1;
			test[1] = loca[1]-2;
			fin.add(convertBack(test));

		}
		//Left2Up1
		if((loca[0]+1 < 8 && loca[1]-2 > -1) && (bord[loca[0]+1][loca[1]-2] == null || !bord[loca[0]+1][loca[1]-2].color.equals(this.color)))
		{
			test[0] = loca[0]+1;
			test[1] = loca[1]-2;
			fin.add(convertBack(test));

		}		
		return fin;
	}
}

//Done
class Bishop extends Piece {
	
	ArrayList<String> moveList = new ArrayList<String>();
	
	public Bishop(String color) {
		this.color = color;
	}
	
	public String toString() {
		if(color.equals("white")) {
			return "wB";
		} else {
			return "bB";
		}
	}
	
	@Override
	public ArrayList<String> getMoves(String loc, Piece[][] bord)
	{
		ArrayList<String> fin = new ArrayList<String>();
		int[] loca = new int[2];
		loca = convertTo(loc);
		int[] test = new int[2];
		
		
		int i = 1;
		//NorthWest
		while((loca[0]-i) > -1 && (loca[1]-i) > -1)
		{
			if(bord[loca[0]-i][loca[1]-i] == null)
			{
				test[0] = loca[0]-i;
				test[1] = loca[1]-i;
				fin.add(convertBack(test));
			}
			else if(!bord[loca[0]-i][loca[1]-i].color.equals(this.color))
			{
				test[0] = loca[0]-i;
				test[1] = loca[1]-i;
				fin.add(convertBack(test));
				break;
			}
			else
			{
				break;
			}
			i++;
		}
		i = 1;
		//NorthEast
		while((loca[0]-i) > -1 && (loca[1]+i) < 8)
		{
			if(bord[loca[0]-i][loca[1]+i] == null)
			{
				test[0] = loca[0]-i;
				test[1] = loca[1]+i;
				fin.add(convertBack(test));
			}
			else if(!bord[loca[0]-i][loca[1]+i].color.equals(this.color))
			{
				test[0] = loca[0]-i;
				test[1] = loca[1]+i;
				fin.add(convertBack(test));
				break;
			}
			else
			{
				break;
			}
			i++;
		}
		i = 1;
		//SouthEast
		while((loca[0]+i) < 8 && (loca[1]+i) < 8)
		{
			if(bord[loca[0]+i][loca[1]+i] == null)
			{
				test[0] = loca[0]+i;
				test[1] = loca[1]+i;
				fin.add(convertBack(test));
			}
			else if(!bord[loca[0]+i][loca[1]+i].color.equals(this.color))
			{
				test[0] = loca[0]+i;
				test[1] = loca[1]+i;
				fin.add(convertBack(test));
				break;
			}
			else
			{
				break;
			}
			i++;
		}
		i = 1;
		//SouthWest
		while((loca[0]+i) < 8 && (loca[1]-i) > -1)
		{
			if(bord[loca[0]+i][loca[1]-i] == null)
			{
				test[0] = loca[0]+i;
				test[1] = loca[1]-i;
				fin.add(convertBack(test));
			}
			else if(!bord[loca[0]+i][loca[1]-i].color.equals(this.color))
			{
				test[0] = loca[0]+i;
				test[1] = loca[1]-i;
				fin.add(convertBack(test));
				break;
			}
			else
			{
				break;
			}
			i++;
		}
		
		return fin;
	}
}



