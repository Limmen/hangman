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
public class Congratulations implements CommunicationProtocol, Serializable {
    
    private int score;
    private int attemptsleft;
    private String state;
    private String log;

    public Congratulations(int score, int attemptsleft, String state, String log){
        this.score = score;
        this.attemptsleft = attemptsleft;
        this.state = state;
        this.log = log;
    }
}
