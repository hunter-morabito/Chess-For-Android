package pieces;

import java.util.ArrayList;

import chess.Board;
import chess.Coordinate;

public class Bishop extends Piece{
	public Bishop(char color) {
		super(color);
	}
	
	public ArrayList<Coordinate> getMoves(Board board, Coordinate start){
		ArrayList<Coordinate> moves = new ArrayList<Coordinate>();
		
		moves = diagonalMoves(moves, board, start);
		
		deleteIllegal(moves, board, start);
		
		return moves;
	}

	public String toString(){
		return color + "B";
	}
}
