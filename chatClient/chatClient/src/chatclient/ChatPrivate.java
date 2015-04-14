/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chatclient;

import javax.swing.DefaultListModel;

/**
 *
 * @author kromatin
 */
public class ChatPrivate extends Thread{
    private String username;
    private privChat win;
    private String user_target;
    private Writer sockw;
    private Reader sockr;
    private DefaultListModel chatMsg;
    
    public ChatPrivate(String username, String user_target, Writer sockw, Reader sockr)
    {
        this.username = username;
        this.user_target = user_target;
        this.sockw = sockw;
        this.sockr = sockr;
    }
    
    public void setUsername(String username)
    {
        this.username = username;
    }
    
    public void addMsg(String msg)
    {
        chatMsg.addElement(msg);
    }
    
    @Override
    public void run()
    {
        chatMsg = new DefaultListModel();
        win = new privChat();
        win.setUsername(username);
        win.setChatModel(chatMsg);
        win.setReader(sockr);
        win.setWriter(sockw);
        win.setUserTarget(user_target);
        chatMsg.removeAllElements();
    }
}
