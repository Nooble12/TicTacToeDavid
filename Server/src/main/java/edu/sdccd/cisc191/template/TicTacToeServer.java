package edu.sdccd.cisc191.template;
import java.io.*;
import java.net.*;

public class TicTacToeServer {

    private static int Xwins = 0;
    private static int Owins = 0;

    public static void main(String[] args) {
        try (ServerSocket serverSocket = new ServerSocket(4652)) {
            System.out.println("Tic Tac Toe Server is running...");

            while (true) {
                // Wait for client connection
                Socket clientSocket = serverSocket.accept();
                System.out.println("Client has connected.");

                // Create input and output streams for communication with the client
                DataInputStream input = new DataInputStream(clientSocket.getInputStream());
                DataOutputStream output = new DataOutputStream(clientSocket.getOutputStream());

                // Server handles the game logic
                handleGame(input, output);

                // Close the connection after the game is over
                clientSocket.close();
                System.out.println("Client disconnected.");
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Simulated method to check the game status and send updated scores
    private static void handleGame(DataInputStream input, DataOutputStream output) throws IOException {
        boolean gameOver = false;
        while (!gameOver) {
            // Simulate receiving game move from client (this would be more detailed in a real game)
            String clientMove = input.readUTF();  // read client's move
            System.out.println("Received move from client: " + clientMove);

            // Simulate checking game result and updating the score
            String result = checkGameResult();  // Implement actual game logic here
            if (result.equals("X")) {
                Xwins++;
            } else if (result.equals("O")) {
                Owins++;
            }

            // Send the updated score to the client
            output.writeUTF("Score: X = " + Xwins + ", O = " + Owins);
            output.flush();

            if (result.equals("GameOver")) {
                gameOver = true;
                output.writeUTF("Game Over");
                output.flush();
            }
        }
    }

    // Simulated game result checking method
    private static String checkGameResult() {
        // Here you would implement actual logic to check the game result.
        // For simplicity, we'll assume the game is in progress and return a simulated result.
        return "X";  // This can be "X", "O", or "GameOver"
    }
}
