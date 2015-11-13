/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package limmen.hangman_server.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

/**
 * Listener class. Listens on a port and creates clienthandlers to handle
 * the connections
 * @author kim
 */
public class Listener implements Runnable {

    private ServerSocket serverSocket;
    private final int PORT;
    private boolean running;
    private final ArrayList<String> words;
    
    /**
     * Class constructor
     * @param words list of words that the clienthandlers will use.
     */
    public Listener(ArrayList<String> words){
        this.words = words;
        this.PORT = 9999;
    }
    
    /**
     * Class constructor
     * @param words list of words that the clienthandlers will use.
     * @param port portnumber to listen on
     */
    public Listener(ArrayList<String> words, int port){
        this.words = words;
        this.PORT = port;
    }
    
    /**
     * Run method.
     * Listen for a port and create clientHandlers
     */
    @Override
    public void run() {
        running = true;
        try
        {
            serverSocket = new ServerSocket(PORT);
            while (running)
            {
                Socket clientSocket = serverSocket.accept();
                new Thread(new ClientHandler(clientSocket, words)).start();
            }            
        } catch (IOException e)
        {
            System.err.println("Could not listen on port: " + PORT);
            cleanUp();
            terminate();
        }
    }
    
    /**
     * Clean up the serversocket.
     */
    public void cleanUp(){
        try{
            serverSocket.close();   
        }
        catch(Exception e){
            running = false;
        }
    }
    
    /**
     * Terminates this thread
     */
    public void terminate(){
        running = false;
    }
    
    
}