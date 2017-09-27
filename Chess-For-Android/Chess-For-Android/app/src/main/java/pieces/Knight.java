package pieces;

import java.util.ArrayList;

import chess.*;

public class Knight extends Piece{
	public Knight(char color) {
		super(color);
	}
	
	public ArrayList<Coordinate> getMoves(Board board, Coordinate start){
		ArrayList<Coordinate> moves = new ArrayList<Coordinate>();
		moves.add(new Coordinate(start.row + 2, start.col + 1));
		moves.add(new Coordinate(start.row + 2, start.col - 1));
		
		moves.add(new Coordinate(start.row - 2, start.col + 1));
		moves.add(new Coordinate(start.row - 2, start.col - 1));
		
		moves.add(new Coordinate(start.row + 1, start.col + 2));
		moves.add(new Coordinate(start.row + 1, start.col - 2));
		
		moves.add(new Coordinate(start.row - 1, start.col + 2));
		moves.add(new Coordinate(start.row - 1, start.col - 2));
		
		deleteIllegal(moves, board, start);
		
		return moves;
	}
	
	public String toString(){
		return color + "N";
	}
}
