package com.example.huntermorabito.chessandroid69;

import android.content.Intent;
import android.graphics.Rect;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Random;

import chess.*;
import pieces.*;

/**
 * Main Game activity holds the space where the two players play Chess
 */

public class MainGame extends AppCompatActivity {

    public final static String EXTRA_WINNER = "EXTRA_WINNER";
    public final static String EXTRA_RECORDED = "EXTRA_RECORDED";

    String firstTag;
    String secondTag;
    boolean isWhiteTurn, undoTriggered, drawOffered, clickEvent;

    Board board;
    ArrayList<Move> moves;

    TextView turnText, messageText;
    Button undoB, aiB, drawB, resignB;
    ImageView firstSpace, secondSpace;

    ImageView a7,a6;

    /**
     * Creates the chess board and assigns onClick events to buttons
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_game);

        moves = new ArrayList<Move>();

        isWhiteTurn = true;
        undoTriggered = false;
        drawOffered = false;



        board = new Board();
        board.init();
        drawBoard();

        turnText = (TextView) findViewById(R.id.turnText);
        messageText = (TextView) findViewById(R.id.messageText);
        turnText.setText("Whites Turn");
        messageText.setText("");

        undoB = (Button) findViewById(R.id.undoB);
        undoB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                undoClicked();
            }
        });

        aiB = (Button) findViewById(R.id.aiB);
        aiB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                aiClicked();
            }
        });

        drawB = (Button) findViewById(R.id.drawB);
        drawB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawClicked();
            }
        });
        resignB = (Button) findViewById(R.id.resignB);
        resignB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resignClicked();
            }
        });

        firstTag = "";
        secondTag = "";

        /*a7 = (ImageView)findViewById(R.id.a7);
        a7.setOnTouchListener(spaceListener);
        a6 = (ImageView)findViewById(R.id.a6);
        a6.setOnTouchListener(spaceListener);*/
    }

    /**
     * Action assigned to all spaces
     * @param space
     */
    public void spaceClicked(View space) {
        if (firstTag == "") {
            firstTag = space.getTag().toString();
            firstSpace = (ImageView) space;
            messageText.setText("");
        } else {
            secondTag = space.getTag().toString();
            //Log.d("tag", firstTag);
            //Log.d("tag", secondTag);
            Coordinate start = new Coordinate(firstTag);
            Coordinate end = new Coordinate(secondTag);
            if (!Action.movePiece(isWhiteTurn, board, start, end, "")) {
                //Log.d("tag", "Illegal move, try again");
                messageText.setText("Invalid Move!");
            } else {
                updateMove(start, end);
            }

            firstTag = "";
            secondTag = "";
        }



    }

    /**
     * tries the move to see if it is a valid set of coordingates.
     * Will also update move
     */
    public void tryMove(){
        String startTag = firstSpace.getTag().toString();
        String endTag = secondSpace.getTag().toString();

        Coordinate start = new Coordinate(startTag);
        Coordinate end = new Coordinate(endTag);

        if (!Action.movePiece(isWhiteTurn, board, start, end, "")) {
            //Log.d("tag", "Illegal move, try again");
            messageText.setText("Invalid Move!");
        } else {
            updateMove(start, end);
        }

        firstSpace = null;
        secondSpace = null;
    }

    /**
     * Assigned to Undo Button. Undos last move on board and resets players turn
     */
    public void undoClicked() {
        if (!undoTriggered) {
            try {
                board.undo();
                drawBoard();
                updateTurn();
                moves.remove(moves.size()-1);
                undoTriggered = true;
            } catch (Exception e) {}
        }
    }

    /**
     * Assigned to AI button. Randomly moves a piece on the board
     */
    public void aiClicked() {
        while (true) {
            Random rand = new Random();
            int randRow = rand.nextInt((7 - 0) + 1);
            int randCol = rand.nextInt((7 - 0) + 1);
            Coordinate start = new Coordinate(randRow, randCol);
            randRow = rand.nextInt((7 - 0) + 1);
            randCol = rand.nextInt((7 - 0) + 1);
            Coordinate end = new Coordinate(randRow, randCol);

            if (Action.movePiece(isWhiteTurn, board, start, end, "")) {
                updateMove(start, end);
                return;
            }
        }
    }

    /**
     * Assigned to draw button. If clicked twice, game ends in draw
     */

    public void drawClicked() {
        if (drawOffered) {
            gameOver("Draw!");
        } else {
            messageText.setText("Draw Offered!");
            drawOffered = true;
        }
    }

    /**
     * Assigned to Resign button. Ends the game with the opponent as the winner
     */
    public void resignClicked() {
        String winner = "";
        if(isWhiteTurn){
            winner = "Black Wins!";
        }else{
            winner = "White Wins!";
        }
        gameOver(winner);
    }

    /**
     * Ends the game and goes to the GameOver Activity
     * @param winner
     */
    public void gameOver(String winner){
        RecordedGame thisGame = new RecordedGame("", winner, moves);
        Intent intent = new Intent(this, GameOver.class);
        intent.putExtra(EXTRA_RECORDED, thisGame);
        intent.putExtra(EXTRA_WINNER, winner);
        startActivity(intent);
    }

    /**
     * Updates whos turn it is in the game
     */
    public void updateTurn() {
        isWhiteTurn = !isWhiteTurn;
        if (isWhiteTurn) {
            turnText.setText("White's Turn");
        } else {
            turnText.setText("Black's Turn");
        }
    }

    /**
     * Once recieving a valid coordinate set, Updates the board
     * @param start
     * @param end
     */

    public void updateMove(Coordinate start, Coordinate end) {
        if (drawOffered) {
            drawOffered = false;
        }
        board.update(start, end, "");
        drawBoard();
        if (Winner.inCheck(board, isWhiteTurn)) {
            if (Winner.checkmate(board, isWhiteTurn)) {
                messageText.setText("Checkmate");
                if (isWhiteTurn) {
                    gameOver("White Wins!");
                } else {
                    gameOver("Black Wins!");
                }
            } else {
                messageText.setText("Check");
            }
        }

        updateTurn();
        moves.add(new Move(start, end));
        undoTriggered = false;
    }

    /**
     * Draws the board after each turn
     */
    private void drawBoard() {
        char file = 'a';
        int rank = 8;

        for (int row = 0; row < 8; row++) {

            for (int col = 0; col < 8; col++) {
                String currentTag = (String) (Character.toString(file) + Integer.toString(rank));
                int viewID = getResources().getIdentifier(currentTag, "id", getPackageName());
                ImageView currentSpace = (ImageView) findViewById(viewID);

                //Log.d("Tag",(String)(Character.toString(file) + Integer.toString(rank)));

                Piece currentPiece = board.space[row][col].piece;
                if (currentPiece == null) {
                    currentSpace.setImageResource(R.drawable.nulli);
                } else if (currentPiece.toString().equals("wp")) {
                    currentSpace.setImageResource(R.drawable.wpawn);
                } else if (currentPiece.toString().equals("bp")) {
                    currentSpace.setImageResource(R.drawable.bpawn);
                } else if (currentPiece.toString().equals("wR")) {
                    currentSpace.setImageResource(R.drawable.wrook);
                } else if (currentPiece.toString().equals("bR")) {
                    currentSpace.setImageResource(R.drawable.brook);
                } else if (currentPiece.toString().equals("wN")) {
                    currentSpace.setImageResource(R.drawable.wknight);
                } else if (currentPiece.toString().equals("bN")) {
                    currentSpace.setImageResource(R.drawable.bknight);
                } else if (currentPiece.toString().equals("wB")) {
                    currentSpace.setImageResource(R.drawable.wbishop);
                } else if (currentPiece.toString().equals("bB")) {
                    currentSpace.setImageResource(R.drawable.bbishop);
                } else if (currentPiece.toString().equals("wQ")) {
                    currentSpace.setImageResource(R.drawable.wqueen);
                } else if (currentPiece.toString().equals("bQ")) {
                    currentSpace.setImageResource(R.drawable.bqueen);
                } else if (currentPiece.toString().equals("wK")) {
                    currentSpace.setImageResource(R.drawable.wking);
                } else if (currentPiece.toString().equals("bK")) {
                    currentSpace.setImageResource(R.drawable.bking);
                }
                file = (char) (file + 1);
            }
            rank = rank - 1;
            file = 'a';

        }

    }

    View.OnTouchListener spaceListener = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View view, MotionEvent motionEvent) {
            switch (motionEvent.getAction()) {
                case MotionEvent.ACTION_DOWN:

                    Log.d("Touch", "touched down");
                    Log.d("Touch", view.getTag().toString());

                    if(clickEvent) {
                        Log.d("Touch", "successful click");
                        secondSpace = (ImageView)view;
                        tryMove();
                    }else{
                        Log.d("Touch", "first Set");
                        firstSpace = (ImageView)view;
                    }

                    break;

                case MotionEvent.ACTION_UP:

                    Log.d("Touch", "touched up");
                    Log.d("Touch", view.getTag().toString());

                    if(firstSpace == view){
                        Log.d("Touch", "is click Event");
                        clickEvent = true;
                    }else if(clickEvent){
                        Log.d("Touch", "end click event");
                        clickEvent = false;
                    }else{
                        Log.d("Touch", "successful drag");
                        secondSpace = (ImageView)view;
                        tryMove();
                    }

                    break;
            }
            return false;
        }
    };
}