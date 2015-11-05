/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package limmen.hangman.util;

import java.io.Serializable;

/**
 *
 * @author kim
 */
public class Protocol implements Serializable {

    private Command command;
    private int score;
    private int attempts;
    private String state;
    private String log;
    private String guess;
    
    public Protocol(Command command){
        this.command = command;
    }
    public Protocol(Command command, String guess){
        this.command = command;
        this.guess = guess;
    }
    public Protocol(Command command, int score, int attempts, String state, String log){
        this.command = command;
        this.score = score;
        this.attempts = attempts;
        this.state = state;
        this.log = log;
    }
    public Command getCommand() {
        return command;
    }
    public int getScore() {
        return score;
    }
    public int getAttemptsLeft() {
        return attempts;
    }
    public String getState() {
        return state;
    }
    public String getLog() {
        return log;
    }
    public String getGuess() {
        return guess;
    }
    
}
