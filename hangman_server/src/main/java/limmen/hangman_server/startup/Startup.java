/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/
package limmen.hangman_server.startup;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;
import limmen.hangman_server.server.Listener;

/**
 * Startup class for Hangman-Server
 * @author kim
 */
public class Startup {
    
    private static final ArrayList<String> words = new ArrayList();
    
    /**
     *
     */
    public Startup(){
        
    }
    
    /**
     * Main method and the entrypoint of the program.
     * Reades /usr/share/dict/words and creates a arraylist of the words.
     * @param args specifies port number. Optional.
     */
    @SuppressWarnings("ResultOfObjectAllocationIgnored")
    public static void main(String[] args){
        try {
            Scanner s = new Scanner(new File("/usr/share/dict/words"));
            while (s.hasNextLine())
                words.add(s.nextLine());
        } catch (FileNotFoundException e) {
            e.printStackTrace(System.err);
        }
        if(args.length < 1)
            new Thread(new Listener(words)).start();
        else
            new Thread(new Listener(words, Integer.parseInt(args[0])));
    }
    
}
