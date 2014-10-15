/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rsclient.chat;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ListModel;
import javax.swing.UIManager;
import javax.swing.border.Border;
import javax.swing.border.EtchedBorder;
import javax.swing.text.DefaultCaret;
import logic.IrcManager;
import net.miginfocom.swing.MigLayout;
import org.pircbotx.User;

/**
 *
 * @author ben
 */
public class ChatMainPane extends JPanel {

    JTextArea messagewindow;
    JTextField inputfield;
    JTextArea userlist;
    JList chanlist;
    ListModel curChans;
    JScrollPane messagescroll, userscroll, chanscroll;
    private ActionListener inputListener;
    String currentChannel;
    Font ircFont;

    HashMap<String, JTextArea> messagePanels = new HashMap();
    HashMap<String, JTextArea> userPanels = new HashMap();
    IrcPanel mainPanel;
    IrcManager manager;

    public ChatMainPane(IrcPanel ircPanel) {
        this.ircFont = new Font("SansSerif", Font.PLAIN, 12);
        this.mainPanel = ircPanel;
        this.setLayout(new MigLayout("ins 10"));
        setup();
        setupListeners();

        inputfield.addActionListener(inputListener);
    }

    private void setup() {
        UIManager.put("Tree.rendererFillBackground", false);
        Border loweredbevel = BorderFactory.createEtchedBorder(EtchedBorder.RAISED);
        DefaultListModel<String> curChans = new DefaultListModel<>();
        messagewindow = new JTextArea();
        messagewindow.setLineWrap(true);
        inputfield = new JTextField();
        userlist = new JTextArea();
        chanlist = new JList(curChans);

        chanscroll = new JScrollPane(chanlist, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        messagescroll = new JScrollPane(messagewindow, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        userscroll = new JScrollPane(userlist, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

        chanscroll.setBorder(loweredbevel);
        messagescroll.setBorder(loweredbevel);
        userscroll.setBorder(loweredbevel);
        inputfield.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.RAISED, new Color(51, 51, 51), new Color(51, 51, 51)));

        inputfield.setBackground(new Color(71, 71, 71));
        messagewindow.setBackground(new Color(71, 71, 71));
        userlist.setBackground(new Color(71, 71, 71));
        chanlist.setBackground(new Color(71, 71, 71));

        chanlist.setForeground(Color.white);
        messagewindow.setForeground(Color.white);
        messagewindow.setFont(ircFont);
        inputfield.setForeground(Color.white);
        messagewindow.setText("");

        messagewindow.setEditable(false);
        inputfield.setCaretColor(Color.black);

        chanlist.setForeground(Color.white);

        add(chanscroll, "cell 0 0, growx, growy, height 100%,width 15%, align left, spany, ");
        add(messagescroll, "growy, cell 1 0, width 68%, height 80%, align center, align left");
        add(userscroll, "grow y, cell 2 0, width 17%, height 80%, align left");
        add(inputfield, "growx, cell 1 1, spanx,  width 68%,height 20,align left");

        // messagescroll.setViewportView((messagewindow2));
    }

    public void switchChanPanels(String chanName) {
        userscroll.setViewportView(userPanels.get(chanName));
        messagescroll.setViewportView(messagePanels.get(chanName));
        messagePanels.get(chanName).setCaretPosition(messagePanels.get(chanName).getDocument().getLength());
        currentChannel = chanName;
    }

    public void addChanPanel(String chanName) {

        JTextArea messagePanel = new JTextArea();
        messagePanel.setLineWrap(true);
        messagePanel.setBackground(new Color(71, 71, 71));
        messagePanel.setForeground(Color.white);
        messagePanel.setFont(ircFont);
        messagePanel.setEditable(false);
        DefaultCaret dc = (DefaultCaret) messagePanel.getCaret();
        dc.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);

        JTextArea userPanel = new JTextArea();
        userPanel.setBackground(new Color(71, 71, 71));

        messagePanels.put(chanName, messagePanel);
        userPanels.put(chanName, userPanel);
       
    }

    public void addMessageToPanel(String user, String chanName, String message) {
        //Called by IrcManager
        String time = new SimpleDateFormat("HH:mm").format(new Date());
        messagePanels.get(chanName).append("\n[" + time + "] <" + user + "> " + message);
        messagePanels.get(chanName).setCaretPosition(messagePanels.get(chanName).getDocument().getLength());
    }
    
    public void updateUsers(ArrayList<User> users) {
        for (User u : users) {
            userPanels.get(currentChannel).append("\n" + u.toString());
        }
    }

    public void setIrcManager(IrcManager mgr) {
        manager = mgr;
    }

    public void clearInputField() {
        inputfield.setText("");
    }

    public HashMap<String, JTextArea> getMessagePanels() {
        return messagePanels;
    }

    public String getCurrentChannel() {
        return currentChannel;
    }

    private void setupListeners() {
        inputListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                manager.onUserInput(inputfield.getText());
//                 if("/test".equals(inputfield.getText())){
//                    addChanPanels("#pixelz");
//                    switchChanPanels("#pixelz");
//                 }
            }
        };
    }

}
