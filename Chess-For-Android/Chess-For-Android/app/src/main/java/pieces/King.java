package pieces;

import java.util.ArrayList;

import chess.Board;
import chess.Coordinate;

public class King extends Piece{
	public King(char color) {
		super(color);
	}
	
	public ArrayList<Coordinate> getMoves(Board board, Coordinate start) {
		ArrayList<Coordinate> moves = new ArrayList<Coordinate>();
		
		moves.add(new Coordinate(start.row + 1, start.col + 1));
		moves.add(new Coordinate(start.row + 1, start.col - 1));
		moves.add(new Coordinate(start.row - 1, start.col + 1));
		moves.add(new Coordinate(start.row - 1, start.col - 1));
		
		moves.add(new Coordinate(start.row + 1, start.col));
		moves.add(new Coordinate(start.row - 1, start.col));
		moves.add(new Coordinate(start.row, start.col + 1));
		moves.add(new Coordinate(start.row, start.col - 1));
		
		deleteIllegal(moves, board, start);
		
		return moves;
	}

	public String toString(){
		return color + "K";
	}
}
