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
public class Guess implements CommunicationProtocol, Serializable {
    
    private String guess;
    
    public Guess(String guess){
        this.guess = guess;
    }
    
    public void setGuess(String guess){
        this.guess = guess;
    }
    
    public String getGuess(){
        return guess;
    }
    
}
