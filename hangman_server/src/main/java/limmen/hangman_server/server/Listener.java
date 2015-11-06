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
 *
 * @author kim
 */
public class Listener implements Runnable {

    private ServerSocket serverSocket;
    private int PORT = 9999;
    private boolean running;
    private final ArrayList<String> words;
    
    /**
     *
     * @param server
     */
    public Listener(ArrayList<String> words){
        this.words = words;
    }
    
    /**
     *
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
     *
     */
    public void cleanUp(){
        try{
            serverSocket.close();   
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }
    
    /**
     *
     */
    public void terminate(){
        running = false;
    }
    
    
}