package edu.sdccd.cisc191.template;
import java.io.*;
import java.net.*;


public class TicTacToeClient{
    public static void main(String[] args) {
        try (Socket socket = new Socket("localhost", 4652)) {
            System.out.println("Connected to Tic Tac Toe Server.");

            // Create input and output streams for communication with the server
            DataInputStream input = new DataInputStream(socket.getInputStream());
            DataOutputStream output = new DataOutputStream(socket.getOutputStream());

            // Simulate sending moves to the server
            for (int i = 0; i < 3; i++) {
                // Send move to the server (this would be based on actual gameplay in a real implementation)
                output.writeUTF("Move " + (i + 1));  // Sending move
                output.flush();

                // Receive the updated score from the server
                String serverResponse = input.readUTF();
                System.out.println("The Server says: " + serverResponse);

                if (serverResponse.equals("Game Over")) {
                    break;
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
