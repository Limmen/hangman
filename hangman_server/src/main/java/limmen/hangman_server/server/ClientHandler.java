/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/
package limmen.hangman_server.server;

import com.sun.xml.internal.ws.api.message.Message;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OptionalDataException;
import java.net.Socket;
import limmen.hangman_server.util.CommunicationProtocol;
import limmen.hangman_server.util.Guess;
import limmen.hangman_server.util.Restart;
import limmen.hangman_server.util.Start;
/**
 *
 * @author kim
 */
public class ClientHandler implements Runnable {
    
    private ObjectInputStream in;
    private ObjectOutputStream out = null;
    private final Socket clientSocket;
    private boolean running;
    
    /**
     *
     * @param clientSocket
     * @param server
     */
    public ClientHandler(Socket clientSocket){
        this.clientSocket = clientSocket;
    }
    
    /**
     *
     */
    @Override
    public void run() {
        running = true;
        setup();
        
        while(running){
            CommunicationProtocol msg = read();
            if(msg != null){
                
                if(msg instanceof Guess){
                    
                }
                if(msg instanceof Restart){
                    
                }
                if(msg instanceof Start){
                    
                }
                
            }
            
        }
    }
    
    void setup(){
        try {
            in = new ObjectInputStream(clientSocket.getInputStream());
            out = new ObjectOutputStream(clientSocket.getOutputStream());
        } catch (IOException e) {
            System.out.println(e.toString());
            cleanUp();
            terminate();
            return;
        }
    }
    
    CommunicationProtocol read(){
        Object msg;
        try {
            msg = in.readObject();
        } catch (ClassNotFoundException cnfe) {
            cleanUp();
            terminate();
            return null;
        } catch (OptionalDataException ode) {
            cleanUp();
            terminate();
            return null;
        } catch (IOException ioe) {
            cleanUp();
            terminate();
            return null;
        }
        
        if (msg instanceof CommunicationProtocol) {
            return (CommunicationProtocol) msg;
        }
        else{
            return null;
        }
    }
    
    /**
     *
     * @param msg
     */
    public void respond(Message msg){
        try {
            out.writeObject(msg);
            out.flush();
        } catch (IOException e) {
            System.out.println(e.toString());
            cleanUp();
            terminate();
        }
    }
    
    /**
     *
     */
    public void cleanUp(){
        try {
            out.close();
            in.close();
            clientSocket.close();
        } catch (IOException ioe) {
            
        }
    }
    
    /**
     *
     */
    public void terminate(){
        this.running = false;
    }
    
}