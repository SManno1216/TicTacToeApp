package edu.ccm.tictactoe;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class WinScreen extends AppCompatActivity {

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_win_screen);

        TextView playerText = findViewById(R.id.playerText);
        TextView winText = findViewById(R.id.winStatusText);
        playerText.setVisibility(View.GONE);

        TextView player1Score = findViewById(R.id.player1WinScore);
        TextView player2Score = findViewById(R.id.player2WinScore);
        player1Score.setText(Integer.toString(getIntent().getExtras().getInt("player1")));
        player2Score.setText(Integer.toString(getIntent().getExtras().getInt("player2")));

        Intent intent = getIntent();
        boolean winStatus = intent.getExtras().getBoolean("win");

        String win;
        if(winStatus){
            win = "Wins!";
        } else {
            win = "Draw!";
        }

        String player;
        if(winStatus){
            player = intent.getExtras().getString("player");
            playerText.setVisibility(View.VISIBLE);
            playerText.setText(player);
        }
        winText.setText(win);
    }

    public void playAgainClick(View view){
        Intent intent = new Intent();
        intent.putExtra("mainMenu",false);
        setResult(0,intent);
        finish();
    }

    public void mainMenuClick(View view){
        Intent intent = new Intent();
        intent.putExtra("mainMenu",true);
        setResult(0,intent);
        finish();
    }
}