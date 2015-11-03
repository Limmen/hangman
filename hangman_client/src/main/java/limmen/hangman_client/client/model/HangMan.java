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
    private String word;
    
    public HangMan(){
        attemptsleft = 5;
        score = 0;
        word = "_ _ _ _ _";
    }
    public HangMan(int attempts, int score, String word){
        attemptsleft = attempts;
        this.score = score;
        this.word  = word;
        
    }
    public String spacify(String word){
        return word.replace("", " ").trim();
    }
    public void setWord(String word){
        this.word = word;
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
        return word;
    }   
}
