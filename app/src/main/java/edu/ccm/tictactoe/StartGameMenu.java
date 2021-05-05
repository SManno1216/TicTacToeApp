package edu.ccm.tictactoe;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;

public class StartGameMenu extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_game_menu);
    }

    public void startGameClick(View view){
        finish();
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    public void howPlayClick(View view){
        Intent intent = new Intent(this, HowToPlay.class);
        startActivity(intent);
    }

    public void websiteClick(View view){
        Uri website = Uri.parse("https://en.wikipedia.org/wiki/Tic-tac-toe");
        Intent intent = new Intent(Intent.ACTION_VIEW, website);
        startActivity(intent);
    }
}