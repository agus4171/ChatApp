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
     */
    
    public static void main(String[] args) throws IOException {
        // TODO code application logic here
        String ipdest;
        int portdest;
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in) {});
        Socket sock;
        
        System.out.println("masukkan IP tujuan");
        ipdest = br.readLine();
        System.out.println("masukkan port tujuan");
        portdest = Integer.parseInt( br.readLine());
        
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
    }
    
}
