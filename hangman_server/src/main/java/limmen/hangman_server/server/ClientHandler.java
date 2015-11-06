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
 *
 * @author kim
 */
public class ClientHandler implements Runnable {
    
    private ObjectInputStream in;
    private ObjectOutputStream out = null;
    private final Socket clientSocket;
    private boolean running;
    private HangMan game;
    private int score;
    private ArrayList<String> words;
    private Random random;
    
    /**
     *
     * @param clientSocket
     * @param words
     */
    public ClientHandler(Socket clientSocket, ArrayList<String> words){
        this.clientSocket = clientSocket;
        this.words = words;
        this.score = 0;
        this.random = new Random();        
    }
    
    /**
     *
     */
    @Override
    public void run() {
        running = true;
        setup();
        while(running){
            try{
                Protocol msg = read();
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
                game = null;
                break;
            case CONGRATULATIONS:
                game = null;
                break;
        }
        return result;
    }
    private void newWord(){
        if(game.gameover){
            score = game.getScore();
            startGame();
        }
        else{
            score--;
            startGame();
        }
    }
    private void restart(){
        score = 0;
        startGame();
    } 
    private void startGame(){
        game = new HangMan(score, getRandomWord());
        Protocol msg = new Protocol(Command.RESULT, score, game.getAttemptsLeft(), game.getState(), "Welcome to the hangman game, I wish you goodluck!");
        respond(msg);
    }
    private String getRandomWord(){
        int i = random.nextInt(words.size());
        System.out.println("Random word is: " + words.get(i));
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
     *
     * @param msg
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
     *
     */
    public void cleanUp(){
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
        this.running = false;
    }
    
}