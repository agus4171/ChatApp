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
import javax.swing.DefaultListModel;

/**
 *
 * @author kromatin
 */
public class ChatClient {

    /**
     * @param args the command line arguments
     * @throws java.io.IOException
     */
    
    public static void main(String[] args) throws IOException, InterruptedException {
        // TODO code application logic here
        String ipdest, username;
        int portdest;
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in) {});
        Socket sock;
        
        findServer find = new findServer();
        find.setVisible(true);
        
        boolean tmp;
        int i = 0;
        while (true)
        {
            tmp = find.getReady();
            if (tmp == true) break;
            i++;
            if (i>50) i=0;
        }
        ipdest = find.getIpDest();
        portdest = find.getPortDest();
        username = find.getUsername();
        Connection conn = new Connection(ipdest, portdest);
        conn.startConnection();
        sock = conn.getSocket();
        Writer sockw = new Writer(sock);
        Reader sockr = new Reader(sock);
        
        chatWindow win = new chatWindow();    
        win.setVisible(true);
        DefaultListModel chatMsg = new DefaultListModel();
        DefaultListModel friends = new DefaultListModel();
        win.setChatModel(chatMsg);
        win.setFriendsModel(friends);
        win.setReader(sockr);
        win.setWriter(sockw);
        chatMsg.removeAllElements();
        friends.removeAllElements();
        sockr.setChatWindow(win);
        sockr.setChatModel(chatMsg);
        sockr.start();
        sockw.write("100:user:"+username);
    }
    
}
