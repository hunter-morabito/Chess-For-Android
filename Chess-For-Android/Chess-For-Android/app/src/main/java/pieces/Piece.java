package pieces;

import java.util.ArrayList;

import chess.Board;
import chess.Coordinate;

/**
 * This class is the superclass for all the pieces. It contains two public fields: 
 * char color and char moved, which help determine the color of the piece and whether it has moved
 * since the game has begun, respectively. The Piece class has several subclasses such as Knight, Rook, 
 * Queen, etc. 
 * 
 * It also contains many useful methods to assist in the movement of each piece.
 * 
 * @author Hunter Morabito
 * @author Joshua Kim
 */
public class Piece {

	public char color;
	public boolean moved;

	public Piece(){
		this.color = 'w';
		this.moved = false;
	}
	
	/**
	 * Constructor for the Piece class
	 * @param char color
	 * @return Piece object
	 */
	

	public Piece(char color) {
		this.color = color;
		this.moved = false;
	}
	
	/**
	 * Gets the possible moves for a certain piece and places the possible
	 * Coordinates it can move to into an ArrayList. 
	 * 
	 * @param Board board, Coordinate start
	 * @return ArrayList<Coordinates>
	 */

	public ArrayList<Coordinate> getMoves(Board board, Coordinate start) {
		ArrayList<Coordinate> moves = new ArrayList<Coordinate>();
		return moves;
	}
	
	/**
	 * Populates an array list with the possible straight moves a piece can perform.
	 * 
	 * @param ArrayList<Coordinates> moves, Board board, Coordinate start
	 * @return ArrayList<Coordinate>
	 */
	
	public ArrayList<Coordinate> straightMoves(ArrayList<Coordinate> moves, Board board, Coordinate start) {
		int i = start.col - 1;

		// return horizonal left moves
		while (i > -1) {
			if (board.space[start.row][i].piece == null) {
				moves.add(new Coordinate(start.row, i));
			} else {
				moves.add(new Coordinate(start.row, i));
				break;
			}
			i--;
		}

		// return horizontal right moves
		i = start.col + 1;
		while (i < 8) {
			if (board.space[start.row][i].piece == null) {
				moves.add(new Coordinate(start.row, i));
			} else {
				moves.add(new Coordinate(start.row, i));
				break;
			}
			i++;
		}

		// return vertical forward up
		i = start.row - 1;
		while (i > -1) {
			if (board.space[i][start.col].piece == null) {
				moves.add(new Coordinate(i, start.col));
			} else {
				moves.add(new Coordinate(i, start.col));
				break;
			}
			i--;
		}

		// return vertical down moves
		i = start.row + 1;
		while (i < 8) {
			if (board.space[i][start.col].piece == null) {
				moves.add(new Coordinate(i, start.col));
			} else {
				moves.add(new Coordinate(i, start.col));
				break;
			}
			i++;
		}

		return moves;
	}
	
	/**
	 * Similar functionality as the method before except with diagnol moves
	 * 
	 * @param ArrayList<Coordinates> moves, Board board, Coordinate start
	 * @return ArrayList<Coordinate> 
	 */
	
	public ArrayList<Coordinate> diagonalMoves(ArrayList<Coordinate> moves, Board board, Coordinate start) {
		// return up left moves
		int row = start.row - 1;
		int col = start.col - 1;
		while (row > -1 && col > -1) {
			if (board.space[row][col].piece == null) {
				moves.add(new Coordinate(row, col));
			} else {
				moves.add(new Coordinate(row, col));
				break;
			}
			row--;
			col--;
		}

		// return up right moves
		row = start.row - 1;
		col = start.col + 1;
		while (row > -1 && col < 8) {
			if (board.space[row][col].piece == null) {
				moves.add(new Coordinate(row, col));
			} else {
				moves.add(new Coordinate(row, col));
				break;
			}
			row--;
			col++;
		}

		// return down left moves
		row = start.row + 1;
		col = start.col - 1;
		while (row < 8 && col > -1) {
			if (board.space[row][col].piece == null) {
				moves.add(new Coordinate(row, col));
			} else {
				moves.add(new Coordinate(row, col));
				break;
			}
			row++;
			col--;
		}

		// return down right moves
		row = start.row + 1;
		col = start.col + 1;
		while (row < 8 && col < 8) {
			if (board.space[row][col].piece == null) {
				moves.add(new Coordinate(row, col));
			} else {
				moves.add(new Coordinate(row, col));
				break;
			}
			row++;
			col++;
		}

		return moves;

	}
	
	/**
	 * A method used to get rid of moves that are out of bounds of the board, or moves that
	 * are spaces containing the similar team
	 * 
	 * @param Arraylist<Coordinate> moves, Board board, Coordinate start
	 * @return ArrayList<Coordinate>
	 */

	public ArrayList<Coordinate> deleteIllegal(ArrayList<Coordinate> moves, Board board, Coordinate start) {
		for (int i = 0; i < moves.size(); i++) {
			if (!inBounds(moves.get(i))) {
				moves.remove(i);
				i--;
			} else if (board.space[moves.get(i).row][moves.get(i).col].piece != null) {
				if (board.space[start.row][start.col].piece.color == board.space[moves.get(i).row][moves
						.get(i).col].piece.color) {
					moves.remove(i);
					i--;
				}
			}
		}
		return moves;
	}
	
	/**
	 * A private method that determines whether a piece's movement is inbounds of the board
	 * 
	 * @param Coordinate move
	 * @return boolean 
	 */

	private static boolean inBounds(Coordinate move) {
		if ((move.row > -1) && (move.row < 8) && (move.col > -1) && (move.col < 8)) {
			return true;
		}
		return false;
	}
	
	/**
	 * Simple method that prints
	 * @return String
	 */

	public String toString() {
		return "";
	}
}
