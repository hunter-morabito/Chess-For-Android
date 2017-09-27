package com.example.huntermorabito.chessandroid69;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import chess.Board;
import chess.RecordedGame;
import pieces.Piece;

/**
 * Activity that holds a board and is passed a RecordedGame
 * object to play back to user
 */
public class RunPlayback extends AppCompatActivity {

    RecordedGame selectedGame;
    Board board;
    Button nextB, endB;
    TextView winnerText, nameText;

    int moveNumber;

    /**
     * Assigns button functions
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_run_playback);

        final Intent homeScreen =  new Intent(this, HomeScreen.class);

        Intent choosePlayback = getIntent();
        selectedGame = (RecordedGame)choosePlayback.getSerializableExtra(ChoosePlayback.EXTRA_SELECTED_GAME);
        Log.d("SelectedGame", selectedGame.toString());

        board = new Board();
        board.init();
        drawBoard();

        moveNumber = 0;

        nameText = (TextView)findViewById(R.id.nameText);
        nameText.setText(selectedGame.getName());
        winnerText = (TextView)findViewById(R.id.winnerText);

        nextB = (Button)findViewById(R.id.nextB);
        nextB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(moveNumber != selectedGame.getMoves().size()) {
                    nextBClicked();
                    moveNumber++;
                }else{
                    winnerText.setText(selectedGame.getWinner());
                }
            }
        });
        endB = (Button)findViewById(R.id.endB);
        endB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(homeScreen);
            }
        });

    }

    /**
     * Assigned to the next button to play the next move that was made in the game
     */

    public void nextBClicked(){
        board.update(selectedGame.getMoves().get(moveNumber).getStart(), selectedGame.getMoves().get(moveNumber).getEnd(), "");
        drawBoard();
    }

    /**
     * Draws the board after a move has been made
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
}
