package edu.ccm.tictactoe;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import java.lang.Integer;
import android.util.Log;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private static final String TAG = "MainActivity";

    public static final int NUM_ROWS = 3;
    public static final int NUM_COLS = 3;

    private final Button[][] buttons = new Button[NUM_ROWS][NUM_COLS];
    private boolean player1Turn;
    private int roundCount = 0;

    private final Score score = new Score();

    private TextView player1Score;
    private TextView player2Score;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        player1Score = findViewById(R.id.player1score);
        player2Score = findViewById(R.id.player2score);

        for(int row = 0; row<NUM_ROWS; row++){
            for(int col = 0; col<NUM_COLS; col++){
                String buttonID = "button_" + row + col;
                int resID = getResources().getIdentifier(buttonID, "id", getPackageName());
                buttons[row][col] = (Button) findViewById(resID);
                buttons[row][col].setOnClickListener(this);
            }
        }
    }

    @SuppressLint("SetTextI18n")
    @Override
    protected void onStart() {
        super.onStart();
        player1Turn = true;
        player1Score.setText(Integer.toString(score.getPlayer1()));
        player2Score.setText(Integer.toString(score.getPlayer2()));
        resetBoard();
    }

    @Override
    public void onClick(View view) {
        if(!((Button) view).getText().toString().equals("")) {
            return;
        }

        String buttonID = view.getResources().getResourceEntryName(view.getId());

        if(player1Turn){
            ((Button) view).setText("X");
            ((Button) view).setTextSize(60);
            player1Turn = false;

        } else{
            ((Button) view).setText("O");
            ((Button) view).setTextSize(60);
            player1Turn = true;
        }
        roundCount++;

        int x = Integer.parseInt(buttonID.substring(buttonID.length()-2,buttonID.length()-1));
        int y = Integer.parseInt(buttonID.substring(buttonID.length()-1));
        Log.d(TAG, "onClick: " + x + " " + y);
        if(checkForWin(x,y)) {
            Intent intent = new Intent(this, WinScreen.class);
            if(player1Turn) {
                //player 2 won
                score.player2Win();
                intent.putExtra("player", "O");
            } else {
                //player 1 won
                score.player1Win();
                intent.putExtra("player", "X");
            }
            intent.putExtra("win",true);
            intent.putExtra("player1", score.getPlayer1());
            intent.putExtra("player2", score.getPlayer2());
            startActivityForResult(intent,0);
        } else if(roundCount == 9){
            Intent intent = new Intent(this, WinScreen.class);
            intent.putExtra("win",false);
            intent.putExtra("player1", score.getPlayer1());
            intent.putExtra("player2", score.getPlayer2());
            startActivityForResult(intent,0);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 0) {
            if (data.getBooleanExtra("mainMenu", false)) {
                finish();
                Intent intent = new Intent(MainActivity.this, StartGameMenu.class);
                startActivity(intent);
            }
        }
    }

    public boolean checkForWin(int x, int y){
        //Check Rows
        if(x-1 >= 0){
            if(buttons[x][y].getText().equals(buttons[x-1][y].getText())){
                if(x+1 < buttons.length){
                    if(buttons[x][y].getText().equals(buttons[x + 1][y].getText())){
                        return true;
                    }
                } else {
                    if(buttons[x][y].getText().equals(buttons[x - 2][y].getText())){
                        return true;
                    }
                }
            }
        } else {
            if(buttons[x][y].getText().equals(buttons[x+1][y].getText()) && buttons[x][y].getText().equals(buttons[x+2][y].getText())){
                return true;
            }
        }

        //Check Column
        if(y-1 >= 0){
            if(buttons[x][y].getText().equals(buttons[x][y-1].getText())){
                if(y+1 < buttons[0].length){
                    if(buttons[x][y].getText().equals(buttons[x][y+1].getText())){
                        return true;
                    }
                } else {
                    if(buttons[x][y].getText().equals(buttons[x][y-2].getText())){
                        return true;
                    }
                }
            }
        } else {
            if(buttons[x][y].getText().equals(buttons[x][y + 1].getText()) && buttons[x][y].getText().equals(buttons[x][y + 2].getText())){
                return true;
            }
        }

        //Top left click cross check
        if(x == 0 && y == 0){
            Log.d(TAG, "checkForWin: TOP LEFT");
            if(buttons[x][y].getText().equals(buttons[x+1][y+1].getText()) && buttons[x][y].getText().equals(buttons[x+2][y+2].getText())){
                return true;
            }
        }

        //Top right click cross check
        if(x == 0 && y == buttons[0].length-1){
            Log.d(TAG, "checkForWin: TOP RIGHT");
            if(buttons[x][y].getText().equals(buttons[x+1][y-1].getText()) && buttons[x][y].getText().equals(buttons[x+2][y-2].getText())){
                return true;
            }
        }

        //Bottom right click cross check
        if(x == buttons.length-1 && y == 0){
            Log.d(TAG, "checkForWin: BOTTOM RIGHT");
            if(buttons[x][y].getText().equals(buttons[x-1][y+1].getText()) && buttons[x][y].getText().equals(buttons[x-2][y+2].getText())){
                return true;
            }
        }

        //Bottom right click cross check
        if(x == buttons.length-1 && y == buttons[0].length-1){
            Log.d(TAG, "checkForWin: BOTTOM RIGHT");
            if(buttons[x][y].getText().equals(buttons[x-1][y-1].getText()) && buttons[x][y].getText().equals(buttons[x-2][y-2].getText())){
                return true;
            }
        }

        //Center click cross check
        if(x == 1 && y == 1){
            Log.d(TAG, "checkForWin: CENTER");
            if ((buttons[x][y].getText().equals(buttons[x-1][y+1].getText()) && buttons[x][y].getText().equals(buttons[x+1][y-1].getText())) ||
                    buttons[x][y].getText().equals(buttons[x-1][y-1].getText()) && buttons[x][y].getText().equals(buttons[x+1][y+1].getText())){
                return true;
            }
        }

        return false;
    }

    private void resetBoard()  {
        for(int row = 0; row<NUM_ROWS; row++){
            for(int col = 0; col<NUM_COLS; col++){
                buttons[row][col].setText("");
            }
        }
        roundCount = 0;
    }
}