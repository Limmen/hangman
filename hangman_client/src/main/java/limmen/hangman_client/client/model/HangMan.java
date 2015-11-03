/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package limmen.hangman_client.client.model;

/**
 *
 * @author kim
 */
public class HangMan {
    
    private int attemptsleft;
    private int score;
    
    public HangMan(){
        attemptsleft = 5;
        score = 0;
    }
    
    
    public void setAttempts(int attempts){
        this.attemptsleft = attempts;
    }
    public void setScore(int score){
        this.score = score;
    }
    
    public int getAttemptsLeft(){
        return this.attemptsleft;
    }
    
    public int getScore(){
        return this.score;
    }
    public String getWord(){
        return "------";
    }   
}
