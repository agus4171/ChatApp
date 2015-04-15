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
    private String username;
            
    public Reader(Socket sock, String username) throws IOException
    {
        this.username = username;
        this.sock = sock;
        br = new BufferedReader(new InputStreamReader(sock.getInputStream()));
    }
        
    public void setChatModel(DefaultListModel chatMsg)
    {
        this.chatMsg = chatMsg;
    }
    
    public void setFriendModel(DefaultListModel friendList)
    {
        this.friends = friendList;
    }
    
    @Override
    public void run()
    {
        while (true)
        {
            try {
                msg = br.readLine();
                System.out.println(msg);
                if (msg.length() != 0)
                {
                    if (msg.charAt(0) == ';')
                    {
                        System.out.println("dapat list user yg aktif:");
                        System.out.println(msg);
                        String[] tmp = msg.split(";");
                        friends.clear();
                        for (int i = 0; i < tmp.length; i++)
                        {
                            if (tmp[i].equals(this.username)) 
                            {
                                String tmp2 = tmp[i]+" (you!)";
                                friends.addElement(tmp2);
                            }
                            else
                            {
                                friends.addElement(tmp[i]);
                            }
                        }
                    }
                    else
                    {
                        System.out.println("pesan masuk: "+msg);
                        chatMsg.addElement(msg);
                    }
                }
            } catch (IOException ex) {
                Logger.getLogger(Reader.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
}
