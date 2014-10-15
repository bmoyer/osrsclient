/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rsclient.chat;

import java.io.IOException;
import java.util.ArrayList;
import javax.swing.JPanel;
import logic.IrcManager;
import net.miginfocom.swing.MigLayout;
import org.pircbotx.exception.IrcException;

/**
 *
 * @author ben
 */
public class IrcPanel extends JPanel {

    ChatConfigPanel chatConfigPanel;
    ChatMainPane chatMainPanel;
    IrcManager ircManager;

    public IrcPanel() {
        this.setLayout(new MigLayout("ins 0, "));

        setup();
    }

    private void setup() {
        chatConfigPanel = new ChatConfigPanel(this);
        chatMainPanel = new ChatMainPane(this);
        //this.add(chatMainPanel, "cell 0 0, spanx, width 100%, growy, height 100%");
        this.add(chatConfigPanel, "cell 0 0, spanx, width 100%");
        ircManager = new IrcManager();
    }

    public void addMainPanel() {
        this.remove(chatConfigPanel);
        this.add(chatMainPanel, "cell 0 0, spanx, width 100%, growy, height 100%");
    }
    
    public void startChat(String nick, ArrayList<String> chanList) throws IOException, IrcException{
        chatMainPanel.setIrcManager(ircManager);
        ircManager.setIrcPanel(chatMainPanel);
        ircManager.addConnection(nick, chanList);
        ircManager.startConnections();
    }

}
