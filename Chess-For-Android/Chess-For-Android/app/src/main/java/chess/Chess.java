package chess;

//import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * This class is where the main function is held, and where the program is run and
 * terminates when a winner is found 
 * @author Hunter Morabito
 * @author Joshua Kim
 */

public class Chess {
	
	/**
	 * Essentially serves as the manager for the game. It is the class where the main method exists
	 * It calls upon init in the Board class and initializes everything. Also, assists in determining
	 * the current move and the winner.
	 * 
	 * @param String[] args
	 * @return void
	 * @throws FileNotFoundException (for Scanning in files for System.in)
	 */

	public static void main(String[] args) throws FileNotFoundException {
		// TODO Auto-generated method stub
		int winner = 0; // 0 is no winner, 1 is white win, 2 is black win, 3 is
						// draw
		boolean drawOffered = false;
		boolean isWhiteTurn = true;
		Coordinate illegalCoordinate = new Coordinate(-1, -1);

		Scanner sc = new Scanner(System.in);

		Board board = new Board();
		board.init();
		board.printBoard();

		while (winner == 0) {
			
			if (isWhiteTurn)
				System.out.print("White's Move: ");
			else
				System.out.print("Black's Move: ");
			String command = sc.nextLine();
			System.out.println();

			// resign command
			if (command.equals("resign") && isWhiteTurn) {
				winner = 2;
				break;
			} else if (command.equals("resign")) {
				winner = 1;
				break;
			}

			// draw command
			if (command.equals("draw") && drawOffered) {
				winner = 3;
				break;
			} else if (drawOffered) {
				drawOffered = false;
			}

			// parse line
			String[] spaces = command.split(" ");
			String pawnCommand = "";
			// enters if there is another command
			if (spaces.length == 3) {
				// draw asked for
				if (spaces[2].equals("draw?")) {
					drawOffered = true;
					continue;
				}
				// else its a pawn command
				pawnCommand = spaces[2];
			} else if (spaces.length != 2) {// not enough commands entered
				System.out.println("Illegal move, try again\n");
				continue;
			}

			Coordinate start = new Coordinate(spaces[0]);
			Coordinate end = new Coordinate(spaces[1]);
			if (start.equals(illegalCoordinate) || end.equals(illegalCoordinate)) {
				System.out.println("Illegal move, try again\n");
				continue;
			}

			// check that action is legal
			if (!Action.movePiece(isWhiteTurn, board, start, end, pawnCommand)) {
				System.out.println("Illegal move, try again\n");
				continue;
			}

			board.update(start, end, pawnCommand);
			board.printBoard();

			if (Winner.inCheck(board, isWhiteTurn)) {
				if (Winner.checkmate(board, isWhiteTurn)) {
					System.out.println("Checkmate\n");
					if(isWhiteTurn){
						winner = 1;
						break;
					}else{
						winner = 2;
						break;
					}
				} else {
					System.out.println("Check\n");
				}
			}

			isWhiteTurn = !isWhiteTurn;
		}

		if (winner == 1) {
			System.out.println("White wins");
		} else if (winner == 2) {
			System.out.println("Black wins");
		} else if (winner == 3) {
			System.out.println("Draw");
		}
		sc.close();

	}
}
