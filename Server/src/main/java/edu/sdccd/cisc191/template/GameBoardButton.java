package edu.sdccd.cisc191.template;
import javafx.scene.control.Button;

public class GameBoardButton extends Button {

    private int row;
    private int column;
    private TicTacToe buttons;

    private TicTacToe gameBoard;

    // constructor created to initialize the button with its position and the game board reference
    public GameBoardButton(int row, int col, TicTacToe gameBoard) {
        // Assign the row and column variables to the respective fields
        this.row = row;
        this.column = column;

        // Stores the game board reference to interact with the game logic

        this.gameBoard=gameBoard;
    }
    // Method to handle the logic after a button has been clicked
    public void handleButtonClick(){
        setText(gameBoard.getCurrentTurn());

        // Disable the button after it's been clicked
        setDisable(true);

        //gameBoard.Check();
        gameBoard.Check();

        // Calling the switch method which switches between players in the game
        gameBoard.SwitchTurn();

//        gameBoard.restartButton();






    }

}