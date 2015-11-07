/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package limmen.hangman_server.model;

import limmen.hangman.util.Command;
import limmen.hangman.util.Protocol;

/**
 * Class that contains game-info and logic for a HangMan game.
 * @author kim
 */
public class HangMan {
    
    private int attemptsleft;
    private int score;
    private final String word;
    private String state;
    private boolean gameover = false;
    private boolean win = false;
    
    /**
     * Class constructor.
     * @param score
     * @param word
     */
    public HangMan(int score, String word){
        attemptsleft = 5;
        this.score = score;
        this.word  = word;
        this.state = hide(word);    
    }

    /**
     * Class constructor with specified number of attempts (default is 5)
     * @param attempts number of attempts
     * @param score current score between user and server
     * @param word word that the user is to guess
     */
    public HangMan(int attempts, int score, String word){
        attemptsleft = attempts;
        this.score = score;
        this.word  = word;
        this.state = hide(word);        
    }        

    /**
     * Getter for attempts
     * @return attemptsleft the number of attemptsleft for the user to guess
     */
    public int getAttemptsLeft(){
        return attemptsleft;
    }    

    /**
     * Getter for score
     * @return score the score between the client and server
     */
    public int getScore(){
        return score;
    }

    /**
     * Getter for word
     * @return word the word that the player is to guess
     */
    public String getWord(){
        return word;
    }
    /**
     * Getter for gameover
     * @return gameover boolean that tells wether the game is over or not
     */
    public boolean getGameOver(){
        return gameover;
    }
    /**
     * Getter for win
     * @return win boolean that tells wether user have won or not
     */
    public boolean getWin(){
        return win;
    }

    /**
     * Getter for state
     * @return state the users view of the word
     */
    public String getState(){
        return state;
    }
    /*
    * Creates the default-state (hides the real word).
    */
    private String hide(String word){
        return word.replaceAll(".", "_");
    }

    /**
     * Guess a letter or whole word
     * @param guess string to guess
     * @return boolean wether the guess was successful or not
     */
    public boolean guess(String guess){
        if(guess.equalsIgnoreCase(word)){
            state = word;            
            return true;
        }
        else{
            boolean hit = false;
            if(guess.length() < 2){
                char g = guess.charAt(0);
                char[] wordArray = word.toCharArray();
                char[] stateArray = state.toCharArray();
                for(int i = 0; i < wordArray.length; i++){
                    if(Character.toUpperCase(g) == Character.toUpperCase(wordArray[i])){
                        stateArray[i] = g;
                        hit = true;
                    }
                }
                this.state = new String(stateArray);                
            }
            if(!hit)
                attemptsleft--;
            return hit;
        }
    }
    
    /**
     * Determines the next action (wether we should finish up the game 
     * and send congratulations/gameover or continue).
     * @param log the log to send to client if the game is not over
     * @return Protocol to send to client
     */
    public Protocol next(String log){
        if(attemptsleft < 1){
            gameover = true;
            score--;
            return new Protocol(Command.GAMEOVER, this.score, this.attemptsleft, word, "GAME OVER \nyour attempts is up. The correct word is:" + word);
        }
        if(word.equalsIgnoreCase(state)){
            score++;
            win = true;
            return new Protocol(Command.CONGRATULATIONS, this.score, this.attemptsleft, word, "CONGRATULATIONS \n You guessed the corred word: " + word);
        }
        else
            return new Protocol(Command.RESULT, this.score, this.attemptsleft, this.state, log);
    }
}
