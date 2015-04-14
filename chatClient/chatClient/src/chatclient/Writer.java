/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chatclient;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.Socket;

/**
 *
 * @author kromatin
 */
public class Writer {
    private Socket sock;
    private BufferedWriter bw;
    
    public Writer(Socket sock) throws IOException
    {
        this.sock = sock;
        bw = new BufferedWriter(new OutputStreamWriter(sock.getOutputStream()));
    }
    
    public void write(String msg) throws IOException
    {
        bw.write(msg);
        bw.newLine();
        bw.flush();
    }
}
