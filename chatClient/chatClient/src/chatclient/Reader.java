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
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultListModel;

/**
 *
 * @author kromatin
 */
public class Reader extends Thread{
    private Socket sock;
    private BufferedReader br;
    private String msg;
    private DefaultListModel chatMsg;
    private DefaultListModel friends;
            
    public Reader(Socket sock) throws IOException
    {
        this.sock = sock;
        br = new BufferedReader(new InputStreamReader(sock.getInputStream()));
    }
        
    public void setChatModel(DefaultListModel chatMsg)
    {
        this.chatMsg = chatMsg;
    }
    
    public void setFriendModel(DefaultListModel friends)
    {
        this.friends = friends;
    }
    
    @Override
    public void run()
    {
        while(true){
            try {
                msg = br.readLine();
                if (msg.charAt(0) == ';')
                {
                    System.out.println("get the user list");
                    System.out.println(msg.charAt(0));
                    String[] tmp = msg.split(";");
                    for (int i = 0; i < tmp.length; i++)
                    {
                        friends.addElement(tmp[i]);
                    }
                    
                }
            } catch (IOException ex) {
                Logger.getLogger(Reader.class.getName()).log(Level.SEVERE, null, ex);
            }
            System.out.println(msg);
            chatMsg.addElement(msg);
        }
    }
}
