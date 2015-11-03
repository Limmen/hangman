/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/
package limmen.hangman_server.server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OptionalDataException;
import java.net.Socket;
import limmen.hangman_server.model.HangMan;
import limmen.hangman.util.CommunicationProtocol;
import limmen.hangman.util.Guess;
import limmen.hangman.util.Restart;
import limmen.hangman.util.Result;
import limmen.hangman.util.Start;
/**
 *
 * @author kim
 */
public class ClientHandler implements Runnable {
    
    private ObjectInputStream in;
    private ObjectOutputStream out = null;
    private final Socket clientSocket;
    private boolean running;
    private HangMan game;
    
    /**
     *
     * @param clientSocket
     * @param server
     */
    public ClientHandler(Socket clientSocket){
        this.clientSocket = clientSocket;
    }
    
    /**
     *
     */
    @Override
    public void run() {
        running = true;
        setup();
        
        
        while(running){
            CommunicationProtocol msg = read();            
            if(msg != null){
                if(msg instanceof Guess){
                    Result result = getResult((Guess) msg);
                }
                if(msg instanceof Restart){
                    restart();
                }
                if(msg instanceof Start){
                    startGame();
                }
                
            }
            
        }
    }
    
    
    private Result getResult(Guess guess){
        return null;
    }
    private void restart(){
        
    } 
    private void startGame(){
        String word = getRandomWord();
        this.game = new HangMan();
        game.setWord(word);
        Result res = new Result(game.getScore(), game.getAttemptsLeft(), game.getState(), "Welcome to the hangman game, I wish you goodluck!");
        respond(res);
    }
    private String getRandomWord(){
        return "PROGRAMMING";
    }
    
    void setup(){
        try {
            in = new ObjectInputStream(clientSocket.getInputStream());
            out = new ObjectOutputStream(clientSocket.getOutputStream());
        } catch (IOException e) {
            System.out.println(e.toString());
            cleanUp();
            terminate();
            return;
        }
    }
    
    CommunicationProtocol read(){
        Object msg;
        try {
            msg = in.readObject();
        } catch (ClassNotFoundException cnfe) {
            cleanUp();
            terminate();
            return null;
        } catch (OptionalDataException ode) {
            cleanUp();
            terminate();
            return null;
        } catch (IOException ioe) {
            cleanUp();
            terminate();
            return null;
        }
        
        if (msg instanceof CommunicationProtocol) {
            return (CommunicationProtocol) msg;
        }
        else{
            return null;
        }
    }
    
    /**
     *
     * @param msg
     */
    public void respond(CommunicationProtocol msg){
        System.out.println("Server responding");
        try {
            out.writeObject(msg);
            out.flush();
        } catch (IOException e) {
            System.out.println(e.toString());
            cleanUp();
            terminate();
        }
    }
    
    /**
     *
     */
    public void cleanUp(){
        System.out.println("Server cleaning up");
        try {
            out.close();
            in.close();
            clientSocket.close();
        } catch (IOException ioe) {
            
        }
    }
    
    /**
     *
     */
    public void terminate(){
        System.out.println("Server terminating");
        this.running = false;
    }
    
}