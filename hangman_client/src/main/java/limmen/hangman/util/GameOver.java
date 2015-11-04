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
public class GameOver implements CommunicationProtocol, Serializable {
    
    private int score;
    private int attemptsleft;
    private String state;
    private String log;

    public GameOver(int score, int attemptsleft, String state, String log){
        this.score = score;
        this.attemptsleft = attemptsleft;
        this.state = state;
        this.log = log;
    }  

    public int getScore() {
        return score;
    }

    public int getAttemptsleft() {
        return attemptsleft;
    }

    public String getState() {
        return state;
    }

    public String getLog() {
        return log;
    }
    
}
