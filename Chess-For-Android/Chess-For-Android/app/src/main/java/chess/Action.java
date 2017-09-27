package chess;

import java.util.ArrayList;


import pieces.*;

/**
 * This class consists of the specific actions or movement performed by the 
 * pieces that are still alive in the game. It contains several static methods for
 * basic movements, as well as special actions like enpassment and castling. 
 * 
 * @author Hunter Morabito
 * @author Joshua Kim
 */


public class Action {
	
	/**
	 * Gets the possible moves for a piece, by populating an ArrayList full of Coordinates,
	 * which are the possible spots a current piece can move to. For all methods in this class,
	 * if a move is not valid, the message will be directed toward the current player onto the output 
	 * line. 
	 * 
	 * @param boolean isWhiteTurn, Board board, Coordinate start, Coordinate end, String pc
	 * @return boolean 
	 */

	public static boolean movePiece(boolean isWhiteTurn, Board board, Coordinate start, Coordinate end, String pc) {
		Piece selPiece = board.space[start.row][start.col].piece;

		// space has no piece
		if (selPiece == null)
			return false;

		// check the user has the right to move this piece
		if (isWhiteTurn) {
			if (selPiece.color != 'w') {
				return false;
			}
		} else {
			if (selPiece.color != 'b') {
				return false;
			}
		}

		if (specialMove(board, start, end)) {
			return true;
		}

		// gets possible moves for selected piece
		ArrayList<Coordinate> moves = selPiece.getMoves(board, start);

		// check that end coordinates are a legal move for this piece
		if (moves.contains(end)) {
			board.update(start, end, "");
			if(!Winner.inCheck(board, !isWhiteTurn)) {
				board.undo();
				return true;
			}
			board.undo();
		}

		return false;
	}

	/**
	 * Goes about determining which special move was called such as enpasse or castling.
	 * It does so by checking to the right and left positions of where it was called and by 
	 * which piece.
	 * 
	 * This method goes about calling enPasse and castleClear methods implemented later on.
	 * 
	 * @param Board board, Coordinate start, Coordinate end
	 * @return boolean
	 */
	
	private static boolean specialMove(Board board, Coordinate start, Coordinate end) {
		Piece selPiece = board.space[start.row][start.col].piece;

		// en passent
		if (selPiece instanceof Pawn) {
			// for white pieces
			if ((selPiece.color == 'w' && start.row == 3) || ((selPiece.color == 'b' && start.row == 4))) {
				int offset = 1;
				if (selPiece.color == 'w') {
					offset = -1;
				}
				// check right
				if (start.col != 7) {
					if (enPasse(board, start, end, selPiece, offset, 1)) {
						return true;
					}
				}
				// check left
				if (start.col != 0) {
					if (enPasse(board, start, end, selPiece, offset, -1)) {
						return true;
					}
				}
			}
		}

		// castleing
		if (selPiece instanceof King) {
			if (!selPiece.moved) {
				// moving to the left
				if(castleingClear(board, start, end)){
					if (end.col == (start.col + 2)) {
						board.space[end.row][7].piece = null;
						board.space[end.row][5].piece = new Rook(selPiece.color);
						board.space[end.row][5].piece.moved = true;
					}else{
						board.space[end.row][0].piece = null;
						board.space[end.row][2].piece = new Rook(selPiece.color);
						board.space[end.row][2].piece.moved = true;
					}
					return true;
				}
			}
		}

		return false;
	}
	
	/**
	 * This method goes about carrying out the actual process of enpassement.
	 * 
	 * @param Board board, Coordinate start, Coordinate end, Piece selPiece, int offset, int direction
	 * @return boolean
	 */

	private static boolean enPasse(Board board, Coordinate start, Coordinate end, Piece selPiece, int offset,
			int direction) {
		Coordinate right = new Coordinate(start.row, start.col + direction);
		Coordinate rightStart = new Coordinate(start.row + (2 * offset), start.col + direction);
		if (board.lastUpdatedStart.equals(rightStart)) {
			if (board.lastUpdatedEnd.equals(right)) {
				if ((board.space[right.row][right.col].piece.color != selPiece.color)
						&& board.space[right.row][right.col].piece instanceof Pawn) {
					if ((end.row == (start.row + (1 * offset))) && (end.col == (start.col + direction))) {
						board.space[right.row][right.col].piece = null;
						return true;
					}
				}
			}
		}
		return false;
	}
	
	/**
	 * Similarly, this method goes about carrying out the actual process of castling.
	 * 
	 * @param Board board, Coordinate start, Coordinate end
	 * @return boolean
	 */

	private static boolean castleingClear(Board board, Coordinate start, Coordinate end) {
		if ((end.row == (start.row))) {
			int colPstart = -1;
			int colPend = -1;
			
			if (end.col == (6)) {
				if (board.space[end.row][7].piece != null) {
					if (!board.space[end.row][7].piece.moved) {
						colPstart = 5;
						colPend = 7;
					}
				}
			} else if (end.col == (1)) {
				if (board.space[end.row][0].piece != null) {
					if (!board.space[end.row][0].piece.moved) {
						colPstart = 1;
						colPend = 4;
					}
				}
			}
			
			if (colPstart != -1) {
					for (int i = colPstart; i < colPend; i++) {
						if (board.space[start.row][i].piece != null) {
							return false;
						}
					}
					return true;
			}
		}

		return false;
	}

}