/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package limmen.hangman.util;

import java.io.Serializable;

/**
 * Protocol class.
 * Specifies the communication protocol between client and server
 * @author kim
 */
public class Protocol implements Serializable {

    private final Command command;
    private int score;
    private int attempts;
    private String state;
    private String log;
    private String guess;
    
    /**
     * Class constructor for simple command
     * @param command command that defines what type of instance this is
     */
    public Protocol(Command command){
        this.command = command;
    }

    /**
     * Class constructor for message including a guess to the server
     * @param command command that defines what type of instance this is
     * @param guess guess for a letter/word that's going to be sent to the server.
     */
    public Protocol(Command command, String guess){
        this.command = command;
        this.guess = guess;
    }

    /**
     * Class constructor for message including game-info.
     * @param command command that defines what type of instance this is
     * @param score score between server/client
     * @param attempts attemptsleft for client
     * @param state current view of the word for the client
     * @param log gamelog
     */
    public Protocol(Command command, int score, int attempts, String state, String log){
        this.command = command;
        this.score = score;
        this.attempts = attempts;
        this.state = state;
        this.log = log;
    }

    /**
     * Getter for command
     * @return command the command that defines the instance
     */
    public Command getCommand() {
        return command;
    }

    /**
     * Getter for score
     * @return score the score between client and server
     */
    public int getScore() {
        return score;
    }

    /**
     * Getter for attemptsLeft
     * @return attempts number of attempts left
     */
    public int getAttemptsLeft() {
        return attempts;
    }

    /**
     * Getter for state
     * @return state current view of the word for the client
     */
    public String getState() {
        return state;
    }

    /**
     * Getter for log
     * @return log gamelog
     */
    public String getLog() {
        return log;
    }

    /**
     * Getter for guess
     * @return guess the guess for a letter/word
     */
    public String getGuess() {
        return guess;
    }
    
}
