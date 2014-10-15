/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logic;

import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.pircbotx.exception.IrcException;
import rsclient.chat.ChatConfigPanel;
import rsclient.chat.ChatMainPane;
import rsclient.chat.NetworkSession;
/*
 *
 * @author ben
 */

public class IrcManager {

    NetworkSession networkSession;
    ChatMainPane mainIrcPanel;

    public IrcManager() {
        //called from gui
        //mainIrcPanel = ircPanel;
    }

    public void addConnection(String nick, ArrayList<String> chanNames) {
        networkSession = new NetworkSession(nick, chanNames, this);
    }

    public void startConnections() throws IOException, IrcException {
        networkSession.startSession();
    }

    public void messageReceived(String channel, String message, String user) {
        mainIrcPanel.addMessageToPanel(user, channel, message);
    }

    public void onUserInput(String str) {
        String lexed[] = str.split("\\s+");
        if (lexed.length == 2 && lexed[0].equals("/join")) {
            if (mainIrcPanel.getMessagePanels().containsKey(lexed[1])) {
                mainIrcPanel.clearInputField();
                mainIrcPanel.switchChanPanels(lexed[1]);
            } else {
                mainIrcPanel.clearInputField();
                joinChannel(lexed[1]);
            }
        } else {
            mainIrcPanel.clearInputField();
            networkSession.sendMessage(mainIrcPanel.getCurrentChannel(), str);
        }

    }

    public void onJoinEvent(Object[] users) {
        for (Object o : users) {
            System.out.println();
        }
    }

    public void setIrcPanel(ChatMainPane ircPanel) {
        mainIrcPanel = ircPanel;
    }

    public void joinChannel(final String chanName) {

        mainIrcPanel.addChanPanel(chanName);
        mainIrcPanel.switchChanPanels(chanName);
        networkSession.joinChannel(chanName);

    }
}
