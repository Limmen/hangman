/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package limmen.hangman.util;

/**
 * Command enum.
 * Specifies a list of commands.
 * 
 * @author kim
 */
public enum Command {

    /**
     * Client-Command for initiating a hangman game with the server
     */
    START,

    /**
     * Client-Command for guessing a letter or word
     */
    GUESS,

    /**
     * Client-Command for restarting the game
     */
    RESTART,

    /**
     * Server-Command for sending result to client
     */
    RESULT,

    /**
     *  Server-Command for sending a congratulations message when client wins
     */
    CONGRATULATIONS,

    /**
     * Server-Command for telling the client that he/she has lost.
     */
    GAMEOVER,

    /**
     * Client-Command for requesting a new word
     */
    NEWWORD;    
}
