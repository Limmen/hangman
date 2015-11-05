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
 *
 * @author kim
 */
public class Startup {
    
    private static final ArrayList<String> words = new ArrayList<String>();
    public Startup(){
        
    }
    
    public static void main(String[] args){
        try {
            Scanner s = new Scanner(new File("/usr/share/dict/words"));
            while (s.hasNextLine())
                words.add(s.nextLine());
            System.out.format("Read %d words\n", words.size());
        } catch (FileNotFoundException e) {
            e.printStackTrace(System.err);
        }
        new Thread(new Listener(words)).start();
    }
    
}
