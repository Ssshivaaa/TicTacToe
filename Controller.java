package TicTacToe;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.net.URL;
import java.util.Arrays;

public class Controller {

    @FXML
    Button b00, b01, b02, b10, b11, b12, b20, b21, b22, start, exit;

    @FXML
    Label state, tic, tac, toe;

    private char[][] board = new char[3][3];
    private String bId;
    private boolean turn;
    private int c1, c2, c3, c4, draw;


    public void turn(ActionEvent e) {
        Button b = (Button) e.getSource();
        bId = b.getId();


        if (turn) {
            b.setText("O");
            board[Integer.parseInt(bId.substring(1, 2))][Integer.parseInt(bId.substring(2, 3))] = 'o';
            b.setStyle("-fx-text-fill: #ff7068;");
            playSound("o.wav");
        } else {
            b.setText("X");
            board[Integer.parseInt(bId.substring(1, 2))][Integer.parseInt(bId.substring(2, 3))] = 'x';
            b.setStyle("-fx-text-fill: #70a8cd;");
            playSound("x.wav");
        }


        b.setDisable(true);

        check();

        turn = !turn;
    }

    public void playSound(String filename) {
        URL path = getClass().getResource("sounds/" + filename);
        try {
            Clip sound = AudioSystem.getClip();
            sound.open(AudioSystem.getAudioInputStream(path));
            sound.start();
        } catch (Exception e) {
            System.out.println("No sound");
        }
    }

    public void start() {
        Button[] bArr = new Button[]{b00, b01, b02, b10, b11, b12, b20, b21, b22};
        c1 = 0;
        c2 = 0;
        c3 = 0;
        c4 = 0;
        draw = 0;

        start.setVisible(false);
        exit.setVisible(false);
        state.setVisible(false);
        tic.setVisible(true);
        tac.setVisible(true);
        toe.setVisible(true);

        for (char[] cA : board) {
            Arrays.fill(cA, ' ');
        }

        for (Button b : bArr) {
            b.setDisable(false);
            b.setText("");
        }

        turn = false;
    }

    private int counter(int i, int j, int c) {

        if (board[i][j] == 'x') {
            c++;
        } else if (board[i][j] == 'o') {
            c--;
        }

        return c;
    }

    private void gameOver(char c) {
        Button[] bArr = new Button[]{b00, b01, b02, b10, b11, b12, b20, b21, b22};

        start.setVisible(true);
        exit.setVisible(true);
        state.setVisible(true);
        tic.setVisible(false);
        tac.setVisible(false);
        toe.setVisible(false);

        for (Button b : bArr) {
            b.setDisable(true);
        }

        state.setText(c + " WINS!");

        if (c == 'X') {
            state.setStyle("-fx-text-fill: #70a8cd;");

        } else {
            state.setStyle("-fx-text-fill: #ff7068;");
        }

        if (c == '-') {
            state.setText("DRAW!");
            state.setStyle("-fx-text-fill: #75cd5a;");
        }


    }

    public void exit() {
        System.exit(0);
    }


    private void check() {

        c1 = 0;
        c2 = 0;
        for (int i = 0; i < 3; i++) {
            c3 = 0;
            c4 = 0;
            for (int j = 0; j < 3; j++) {

                if (i + j == 2) {        //Checking opposite diagonal
                    c2 = counter(i, j, c2);

                    if (c2 == 3) {gameOver('X'); return;}
                    if (c2 == -3) {gameOver('O'); return;}
                }

                if (i == j) {           //Checking main diagonal
                    c1 = counter(i, j, c1);

                    if (c1 == 3) {gameOver('X'); return;}
                    if (c1 == -3) {gameOver('O'); return;}
                }

                c3 = counter(i, j, c3);  //Checking rows

                if (c3 == 3) {gameOver('X'); return;}
                if (c3 == -3) {gameOver('O'); return;}
                c4 = counter(j, i, c4); //Checking columns

                if (c4 == 3) {gameOver('X'); return;}
                if (c4 == -3) {gameOver('O'); return;}
            }

        }

        draw++;

        if (draw == 9) {
            gameOver('-');
        }

    }
}
