package edu.ccm.tictactoe;

public class Score {
    private int player1 = 0;
    private int player2 = 0;

    public void player1Win(){
        player1++;
    }

    public void player2Win(){
        player2++;
    }

    public int getPlayer1(){
        return player1;
    }

    public int getPlayer2(){
        return player2;
    }
}
