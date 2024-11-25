package edu.sdccd.cisc191.template;

import edu.sdccd.edu.cisc191.ServerRequest;

import java.io.*;
import java.net.*;
import java.util.LinkedList;

public class TicTacToeServer
{

    private static final int PORT = 1234;
    private static boolean isServerRunning = true;
    public static LinkedList<String> globalLinkedList = new LinkedList<String>();

    public static void main (String[] args)
    {
        try(ServerSocket serverSocket = new ServerSocket(PORT))
        {
            while(isServerRunning)
            {
                //listens for client
                Socket socket = serverSocket.accept();
                handleClient(socket);
            }
        } catch (IOException e)
        {
            System.out.println("Server exception: " + e);
        }
    }

    public static void handleClient(Socket inSocket) {
        try (ObjectInputStream inputStream = new ObjectInputStream(inSocket.getInputStream());
             ObjectOutputStream outputStream = new ObjectOutputStream(inSocket.getOutputStream())) {

            ServerRequest update = (ServerRequest) inputStream.readObject();

                // Handle different request types
                switch (update.getRequestType())
                {
                    case "UPDATE_WIN": // Update the win log
                        globalLinkedList.add(update.getMessage());
                        outputStream.writeObject("Win log updated");
                        break;

                    case "GET_WIN_LOG": // Send the win log to the client
                        outputStream.writeObject(globalLinkedList);
                        break;

                    default:
                        outputStream.writeObject("Unknown request type");
                outputStream.flush();
            }

        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            try {
                inSocket.close();
            } catch (IOException e) {
                System.out.println("Error closing input stream");
            }
        }
    }

}
