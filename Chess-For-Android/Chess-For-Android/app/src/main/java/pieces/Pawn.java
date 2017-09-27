package pieces;

import java.util.ArrayList;

import chess.*;

public class Pawn extends Piece {

	public Pawn(char color) {
		super(color);
	}

	public ArrayList<Coordinate> getMoves(Board board, Coordinate start) {

		ArrayList<Coordinate> moves = new ArrayList<Coordinate>();
		
		int offset = 1;
		if(color == 'w'){
			offset = -1;
		}
		
		if (!moved) {
			if (board.space[start.row + (1*offset)][start.col].piece == null
					&& board.space[start.row + (2*offset)][start.col].piece == null) {
				moves.add(new Coordinate(start.row + (2*offset), start.col));
			}
		}
		
		if (board.space[start.row + (1*offset)][start.col].piece == null) {
			moves.add(new Coordinate(start.row + (1*offset), start.col));
		}
		
		if (start.col == 0) {
			if (board.space[start.row + (1*offset)][start.col + 1].piece != null) {
				moves.add(new Coordinate(start.row + (1*offset), start.col + 1));
			}
		} else if (start.col == 7) {// check if pawn is in col h
			if (board.space[start.row + (1*offset)][start.col - 1].piece != null) {
				moves.add(new Coordinate(start.row + (1*offset), start.col - 1));
			}
		} else {
			if (board.space[start.row + (1*offset)][start.col + 1].piece != null) {
				moves.add(new Coordinate(start.row + (1*offset), start.col + 1));
			}
			if (board.space[start.row + (1*offset)][start.col - 1].piece != null) {
				moves.add(new Coordinate(start.row + (1*offset), start.col - 1));
			}
		}
		
		deleteIllegal(moves, board, start);
		
		return moves;
	}

	public String toString() {
		return color + "p";
	}
}
