package com.example.huntermorabito.chessandroid69;

import android.content.Intent;
import android.graphics.Path;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.io.*;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

import chess.*;
import pieces.Piece;
//import chess.Coordinate;
//import chess.Move;
//import chess.RecordedGame;

/**
 * Home activity, in charge of serializing and deserializing data.
 * Goes to play main game and recorded game actions
 */

public class HomeScreen extends AppCompatActivity {
    public static final String EXTRA_RECORDED_GAMES = "EXTRA_RECORDED_GAMES";

    public static ArrayList<RecordedGame> recordedGames;

    Button playB, recordedGamesB;

    /**
     * Retrieves recorded games from serialization,
     * sets actions to buttons that navigate activities
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);

        //intent to main games screen
        final Intent playGame = new Intent(this, MainGame.class);
        final Intent choosePlayback = new Intent(this, ChoosePlayback.class);

        //clearSer();

        //if the app is opened for the first time
        //retrieve saved games
        if(recordedGames == null) {
            recordedGames = deserializeGames();
        }

        try {
            //get recorded game from the game over screen
            Intent gameOver = getIntent();
            RecordedGame newRecordedGame = (RecordedGame) gameOver.getSerializableExtra(GameOver.EXTRA_RECORDED);
            //if protects against the first time the app opens
            if (newRecordedGame != null) {
                Log.d("Pass", "gameadded");
                //add game to saved serialized games
                recordedGames.add(newRecordedGame);
                serializeGames(recordedGames);
            }
        } catch (Exception e) {
            Log.d("Err", "NOTCAUGHT");
        }

        if (!recordedGames.isEmpty()) {
            for (RecordedGame game : recordedGames) {
                Log.d("RecordedGameTime", game.getName());
            }
        }

        playB = (Button) findViewById(R.id.playB);
        //starts MainGame to play chess
        playB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(playGame);
            }
        });
        //goes to ChoosePlayBack Activity
        recordedGamesB = (Button) findViewById(R.id.recordedGamesB);
        recordedGamesB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {choosePlayback.removeExtra(EXTRA_RECORDED_GAMES);
                }catch(Exception e){}
                choosePlayback.putExtra(EXTRA_RECORDED_GAMES, recordedGames);
                startActivity(choosePlayback);
            }
        });
    }

    /**
     * serializes saved games in current games array
     */

    public void serializeGames(ArrayList<RecordedGame> games) {
        try {
            File newFile = new File(getFilesDir().getPath() + "/recordings.ser");
            //Log.d("ser", newFile.toString());
            FileOutputStream fileOut = new FileOutputStream(newFile);
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            //flushes previous file
            out.flush();
            //rewrites current array of saved games
            out.writeObject(games);
            out.close();
            fileOut.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     *  //retrieves games from serialized file
     *  Only called once during program
     * @return recorded past games
     */


    public ArrayList<RecordedGame> deserializeGames() {
        ArrayList<RecordedGame> newSavedGames = new ArrayList<RecordedGame>();
        try {
            File newFile = new File(getFilesDir().getPath() + "/recordings.ser");
            FileInputStream fileIn = new FileInputStream(newFile);
            ObjectInputStream in = new ObjectInputStream(fileIn);
            newSavedGames = (ArrayList<RecordedGame>) in.readObject();
            //Log.d("Serializer", RecordedGame.get(0).getName());
            in.close();
            fileIn.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        return newSavedGames;
    }

    /**
     * Refreshes serialization file, used in testing
     */
    public void clearSer() {
        serializeGames(new ArrayList<RecordedGame>());
    }
}
