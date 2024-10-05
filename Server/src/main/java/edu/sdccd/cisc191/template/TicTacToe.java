package edu.sdccd.cisc191.template;

// import statements
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.chart.Chart;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.scene.control.Button;

import java.io.*;


public class TicTacToe extends Application{

    private GameBoardLabel Xscore = new GameBoardLabel();
    private GameBoardLabel Oscore = new GameBoardLabel();
    private GameBoardLabel Turn = new GameBoardLabel();
    private int Xwins = 0;
    private int Owins = 0;


    public static Button[][] board = new Button[3][3];; //array representation of the game board
    private boolean x = true; // tracks whether it's X's or O'x turn
    public Button[][] buttons = new Button[3][3];


    boolean gameOver = false;
    private String currentPlayer = "X";
    private Chart primayStage;

    // launches the JavaFX application
    public static void main(String[] args) {
        // launches the application
        launch(args);
    }

    public void updateHeader() {
        // update labels
        // changes the text depending how many fishes or guesses are remaining in
        // the game
        Xscore.setText("X: " +  Xwins);
        Oscore.setText("O: " +  Owins);
        Turn.setText("Turn: " + getCurrentTurn());



    }

    public void start(Stage primayStage) {

        Button restartButton = new Button("Restart");
        restartButton.setOnAction(event ->{
            restart();
        });
        gameOver = false;

        // SAVE GAME BUTTON
        Button saveButton = new Button("Save Game");
        saveButton.setOnAction(event -> saveGame());

        // LOAD GAME BUTTON
        Button loadButton = new Button("Load Game");
        loadButton.setOnAction(event -> loadGame());

        GridPane grid = new GridPane();
        BorderPane borderPane = new BorderPane();
        borderPane.setCenter(grid);
        HBox hbox = new HBox(Xscore, Oscore, Turn, restartButton, saveButton, loadButton);
        borderPane.setTop(hbox);
        x = !x;

        updateHeader();

        // nested for loop to create a 3X3 grid of buttons
        for(int row = 0; row < 3; row++){
            for(int column = 0; column < 3; column++){
                GameBoardButton button = new GameBoardButton(row, column, this);


                // size of each button
                button.setMinSize(160,150);
                // set the font size of the text to 50
                button.setStyle("-fx-font-size: 50;");

                // Create final variables for row and column
                final int r = row;
                final int c = column;

                // Sets the action event for when the button is clicked
                button.setOnAction(event -> {
                    button.handleButtonClick();
                    TicTacToe.board[r][c] = button;
                    // stores the clicked button in the corresponding position
                    //buttons[r][c] = button;
                });
                grid.add(button,column,row);
                buttons[row][column]=button;


            }
        }

        Scene scene = new Scene(borderPane,470, 490);

        // Sets the title of the game window
        primayStage.setTitle("Tic -  Tac - Toe");

        // Set the scene of the primary stage to the one containing the buttons
        primayStage.setScene(scene);

        // Show the game window
        primayStage.show();


    } // end start();


    // Save the game state to a file
    private void saveGame() {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("tic_tac_toe_save.dat"))) {
            // Save the board state
            String[][] boardState = new String[3][3];
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    boardState[i][j] = buttons[i][j].getText();
                }
            }
            out.writeObject(boardState);

            out.writeBoolean(x);  // Save whose turn it is
            out.writeInt(Xwins);      // Save X's score
            out.writeInt(Owins);      // Save O's score
            System.out.println("Game saved successfully.");
        } catch (IOException e) {
            System.out.println("Error saving the game: " + e.getMessage());
        }
    }
    private void loadGame() {
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream("tic_tac_toe_save.dat"))) {
            // Load the board state
            String[][] boardState = (String[][]) in.readObject();
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    buttons[i][j].setText(boardState[i][j]);
                }
            }
            x = in.readBoolean();  // Load whose turn it is
            Xwins = in.readInt();      // Load X's score
            Owins = in.readInt();      // Load O's score
            updateHeader();
            System.out.println("Game loaded successfully.");
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Error loading the game: " + e.getMessage());
        }
    }

    public void restart() {
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {
                buttons[row][col].setText("");  // Clear the text on each button
                buttons[row][col].setDisable(false); // Re-enable the button
            }
        }
        x = true;
        updateHeader();
    }


    public String getCurrentTurn(){
        String turn;
        if(x){
            turn="X";
        }
        else{
            turn="0";
        }
        return turn;
    }


    public void SwitchTurn(){

        x=!x;
    }

public void disableBoard() {
    for (int row = 0; row < 3; row++) {
        for (int col = 0; col < 3; col++) {
            buttons[row][col].setDisable(true);
        }
    }
}
    public void Check(){
        int count=0;


        //rows
        for(int row = 0; row < 3; row++){
            if(buttons[row][0].getText().equals("")){
                continue;
            }
            if(buttons[row][0].getText().equals(buttons[row][1].getText()) && buttons[row][0].getText().equals(buttons[row][2].getText())){
                //do something
                disableBoard();
                if(getCurrentTurn().equals("X")){
                    Xwins++;
                }else{
                    Owins++;
                }
                System.out.println(getCurrentTurn()+ " wins");
                return;
            }
        }


        //columns
        for(int col = 0; col < 3; col++){
            if(buttons[0][col].getText().equals("")){
                continue;
            }
            if(buttons[0][col].getText().equals(buttons[1][col].getText()) && buttons[0][col].getText().equals(buttons[2][col].getText())){
                //do something

                disableBoard();
                if(getCurrentTurn().equals("X")){
                    Xwins++;
                }else{
                    Owins++;
                }
                System.out.println(getCurrentTurn()+ " wins");
                return;
            }
        }


        //diagonal 1
        if(buttons[0][0].getText().equals(buttons[1][1].getText()) && buttons[0][0].getText().equals(buttons[2][2].getText()) && !(buttons[0][0].getText().equals(""))){
            //do something

            disableBoard();
            if(getCurrentTurn().equals("X")){
                Xwins++;
            }else{
                Owins++;
            }
            System.out.println(getCurrentTurn()+ " wins");
            return;
        }


        //diagonal 2
        if(buttons[0][2].getText().equals(buttons[1][1].getText()) && buttons[0][2].getText().equals(buttons[2][0].getText()) && !(buttons[0][2].getText().equals(""))){
            //do something

            disableBoard();
            if(getCurrentTurn().equals("X")){
                Xwins++;
            }else{
                Owins++;
            }
            System.out.println(getCurrentTurn()+ " wins");
            return;
        }


        //tie
        for(int i=0;i<3;i++){
            for(int j=0;j<3;j++){
                if(!(buttons[i][j].getText().equals(""))){
                    count++;

                }
            }
        }
        if(count==9){
            //do something
            System.out.println("tie");
            disableBoard();
        }
    }




}


