/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package limmen.hangman_server.model;

import javax.xml.transform.Result;
import limmen.hangman.util.Command;
import limmen.hangman.util.Protocol;

/**
 *
 * @author kim
 */
public class HangMan {
    
    private int attemptsleft;
    private int score;
    private String word;
    private String state;
    public boolean gameover = false;
    
    public HangMan(int score, String word){
        attemptsleft = 5;
        this.score = score;
        this.word  = word;
        this.state = hide(word);    
    }
    public HangMan(int attempts, int score, String word){
        attemptsleft = attempts;
        this.score = score;
        this.word  = word;
        this.state = hide(word);        
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
        return word.replaceAll(".", "_");
    }
    public boolean guess(String guess){
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
    
    public Protocol next(String log){
        if(attemptsleft < 1){
            gameover = true;
            return new Protocol(Command.GAMEOVER, this.score, this.attemptsleft, word, "GAME OVER \nyour attempts is up. The correct word is:" + word);
        }            
        if(word.equals(state)){
            gameover = true;
            return new Protocol(Command.CONGRATULATIONS, this.score, this.attemptsleft, word, "CONGRATULATIONS \n You guessed the corred word: " + word);
        }
        else
            return new Protocol(Command.RESULT, this.score, this.attemptsleft, this.state, log);
    }
}
