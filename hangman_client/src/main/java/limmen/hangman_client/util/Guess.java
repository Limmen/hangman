/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package limmen.hangman_client.util;

/**
 *
 * @author kim
 */
public class Guess implements CommunicationProtocol {
    
    private String guess;
    
    public Guess(String guess){
        this.guess = guess;
    }
    
    public void setGuess(String guess){
        this.guess = guess;
    }
    
    public void getGuess(String guess){
        this.guess = guess;
    }
    
}
