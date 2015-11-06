/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package limmen.hangman.util;

/**
 *
 * @author kim
 */
public class BadProtocolException extends Exception {

    /**
     * Creates a new instance of <code>BadProtocolException</code> without
     * detail message.
     */
    public BadProtocolException() {
    }

    /**
     * Constructs an instance of <code>BadProtocolException</code> with the
     * specified detail message.
     *
     * @param msg the detail message.
     */
    public BadProtocolException(String msg) {
        super(msg);
    }
}
