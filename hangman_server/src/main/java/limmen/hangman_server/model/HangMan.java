/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package limmen.hangman_server.model;

import limmen.hangman.util.CommunicationProtocol;
import limmen.hangman.util.Congratulations;
import limmen.hangman.util.GameOver;
import limmen.hangman.util.Result;

/**
 *
 * @author kim
 */
public class HangMan {
    
    private int attemptsleft;
    private int score;
    private String word;
    private String state;
    
    public HangMan(){
        attemptsleft = 5;
        score = 0;
    }
    public HangMan(int attempts, int score, String word){
        attemptsleft = attempts;
        this.score = score;
        this.word  = word;
        this.state = hide(word);        
    }
    
    public void setWord(String word){
        this.word = word;
        this.state = hide(word);
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
    public String getState(){
        return state;
    }
    private String hide(String word){
        return word.replaceAll(".*", "_");
    }
    private boolean guess(String guess){
        if(guess.equalsIgnoreCase(word)){
            state = word;
            score++;
            
            return true;
        }
        else{
            boolean hit = false;
            if(guess.length() < 2){
                char g = guess.charAt(0);
                char[] wordArray = word.toCharArray();
                char[] stateArray = state.toCharArray();
                for(int i = 0; i < wordArray.length; i++){
                    if(g == wordArray[i]){
                        stateArray[i] = g;
                        hit = true;
                    }
                    else
                        stateArray[i] = '_';
                }
                this.state = new String(stateArray);                
            }
            if(!hit)
                attemptsleft--;
            return hit;
        }
    }
    
    public CommunicationProtocol next(){
        if(attemptsleft < 1)
            return new GameOver();        
        if(word.equals(state))
            return new Congratulations();
        else
            return new Result(this.score, this.attemptsleft, this.state, "");
    }
}
