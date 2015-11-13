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
import java.util.ArrayList;
import java.util.Random;
import limmen.hangman.util.BadProtocolException;
import limmen.hangman.util.Command;
import limmen.hangman.util.Protocol;
import limmen.hangman_server.model.HangMan;
/**
 * Class that handles a clientconnection.
 * @author kim
 */
public class ClientHandler implements Runnable {
    
    private ObjectInputStream in;
    private ObjectOutputStream out = null;
    private final Socket clientSocket;
    private boolean running;
    private HangMan game;
    private int score;
    private final ArrayList<String> words;
    private final Random random;
    
    /**
     * Class constructor
     * @param clientSocket socket connection
     * @param words list of words to use in the hangman game
     */
    public ClientHandler(Socket clientSocket, ArrayList<String> words){
        this.clientSocket = clientSocket;
        this.words = words;
        this.score = 0;
        this.random = new Random();        
    }
    
    /**
     * Readmessages from client and respond.
     */
    @Override
    public void run() {
        running = true;
        setup();
        while(running){
            try{
                Protocol msg = read();
                //sleep(5); //simulate network latency
                if(msg != null){
                    switch (msg.getCommand()) {
                        case START:
                            startGame();
                            break;
                        case GUESS:
                            respond(getResult(msg));
                            break;
                        case RESTART:
                            restart();
                            break;
                        case NEWWORD:
                            newWord();
                            break;
                    }
                }
            }
            catch(BadProtocolException e){
                terminate();
            }
            
        }
    }
    /*
    * Simulates the guess from the client and responds.
    */    
    private Protocol getResult(Protocol guess){
        if(game == null)
            return null;
        Protocol result;
        if(game.guess(guess.getGuess()))
            result = game.next("You guessed: " + guess.getGuess() + " which was a hit");
        else
            result = game.next("You guessed: " + guess.getGuess() + " which was a miss");
        switch (result.getCommand()) {
            case GAMEOVER:
                score = game.getScore();
                game = null;
                break;
            case CONGRATULATIONS:
                score = game.getScore();
                game = null;
                break;
        }
        return result;
    }
    /*
    * Gives the user a new word.
    */
    private void newWord(){
        if(game != null){
           score--;
           startGame();
        }
        else{
            startGame();
        }
    }
    /*
    * Restarts the game.
    */
    private void restart(){
        score = 0;
        startGame();
    } 
    /*
    * Starts a new game.
    */
    private void startGame(){
        game = new HangMan(score, getRandomWord());
        Protocol msg = new Protocol(Command.RESULT, score, game.getAttemptsLeft(), game.getState(), "Welcome to the hangman game, I wish you goodluck!");
        respond(msg);
    }
    /*
    * Gets a random word from our "library" of words.
    */
    private String getRandomWord(){
        int i = random.nextInt(words.size());
        System.out.println("The word is: " + words.get(i));
        return words.get(i);
    }
    /*
    * Tryes to setup in and out streams to the client connection.
    */
    void setup(){
        try {
            in = new ObjectInputStream(clientSocket.getInputStream());
            out = new ObjectOutputStream(clientSocket.getOutputStream());
        } catch (IOException e) {
            System.out.println(e.toString());
            cleanUp();
            terminate();
        }
    }
    /*
    * Reads the inputstream for the client connection.
    */
    Protocol read() throws BadProtocolException{
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
        
        if (msg instanceof Protocol) {
            return (Protocol) msg;
        }
        else{
            throw new BadProtocolException();
        }
    }
    
    /**
     * Responds to client.
     * @param msg msg to send to client
     */
    public void respond(Protocol msg){
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
     * Closes the client-connection.
     */
    public void cleanUp(){
        try {
            out.close();
            in.close();
            clientSocket.close();
        } catch (IOException ioe) {
            running = false;
        }
    }
    
    /**
     * Terminates this thread.
     */
    public void terminate(){
        running = false;
    }
    /*
    * For simulating network latency
    */
    public void sleep(int seconds){
        try {
            Thread.sleep(1000 * seconds);       
        } catch(InterruptedException ex) {
            Thread.currentThread().interrupt();
        }
    }
    
}