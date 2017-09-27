package com.example.huntermorabito.chessandroid69;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import chess.*;

/**
 * Activity appears after someone wins, resigns, or a draw is made
 */
public class GameOver extends AppCompatActivity {

    public final static String EXTRA_RECORDED = "EXTRA_RECORDED";

    RecordedGame thisGame;

    EditText gameName;
    TextView winnerText;
    Button saveB, homeB;

    /**
     * Assigns buttons on screen and handles the saved games name and sends this back
     * to the home screen to be updated
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_over);

        final Intent homeScreen =  new Intent(this, HomeScreen.class);

        Intent mainGame = getIntent();
        final String winner = mainGame.getStringExtra(MainGame.EXTRA_WINNER);
        thisGame = (RecordedGame)mainGame.getSerializableExtra(MainGame.EXTRA_RECORDED);

        //Log.d("RecordedGame", thisGame.getDate());

        winnerText = (TextView)findViewById(R.id.winnerText);
        winnerText.setText(winner);
        gameName = (EditText)findViewById(R.id.gameName);

        saveB = (Button)findViewById(R.id.saveB);
        saveB.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if(gameName.getText().toString().equals("")){
                    thisGame.setName("Untitled");
                }else{
                    thisGame.setName(gameName.getText().toString());
                }
                try {homeScreen.removeExtra(EXTRA_RECORDED);
                }catch(Exception e){}
                homeScreen.putExtra(EXTRA_RECORDED, thisGame);
                startActivity(homeScreen);

            }
        });
        homeB = (Button)findViewById(R.id.homeB);
        homeB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(homeScreen);
            }
        });

    }
}
