/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chatclient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

/**
 *
 * @author kromatin
 */
public class Connection {
    private String ipdest = "";
    private int portdest = 0;
    private BufferedReader br = new BufferedReader(new InputStreamReader(System.in) {});
    private Socket sock;
    
    public Connection(String ipdest, int portdest)
    {
        this.ipdest = ipdest;
        this.portdest = portdest;
    }
    
    public void startConnection() throws IOException
    {
        sock = new Socket(this.ipdest, this.portdest);
        System.out.println("terhubung dengan server " +ipdest+ "pada port "+portdest);
    }
    
    public Socket getSocket()
    {
        return this.sock;
    }
}
