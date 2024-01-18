package org.example.tic_tac_toe;

import javafx.scene.control.Button;

public class Game {
    // _______________________________________________________________________________________________________________________________
    static Boolean existedEmptySquare(Button[] buttons) {   // Check if there are empty squares in the board or not.
        for (int i = 0; i < 9; i++)
            if (buttons[i].getText().equals(" "))
                return true;
        return false;
    }
    // _______________________________________________________________________________________________________________________________
    static Boolean winGame(Button[] buttons) {
        for (int i = 0; i < 7; i+=3)       // Check rows.
            if (!buttons[i].getText().equals(" ") && buttons[i].getText().equals(buttons[i + 1].getText()) &&
                    buttons[i].getText().equals(buttons[i + 2].getText()))
                return true;

        for (int i = 0; i < 3; i++)         //Check columns.
            if (!buttons[i].getText().equals(" ") && buttons[i].getText().equals(buttons[i + 3].getText()) &&
                    buttons[i].getText().equals(buttons[i + 6].getText()))
                return true;

        return (!buttons[4].getText().equals(" ") && buttons[4].getText().equals(buttons[0].getText()) // Check diagonals.
                && buttons[4].getText().equals(buttons[8].getText())) ||
                (!buttons[4].getText().equals(" ") && buttons[4].getText().equals(buttons[2].getText())
                        && buttons[4].getText().equals(buttons[6].getText()));
    }
}