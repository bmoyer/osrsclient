/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rsclient.coregui;

import rsclient.notes.NotesPanel;
import rsclient.chat.ChatMainPane;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import rsclient.chat.IrcPanel;

/**
 *
 * @author ben
 */
public class BottomPane extends JTabbedPane {

    public BottomPane() {
        setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT);
        setTabPlacement(RIGHT);

        ImageIcon settingsicon = new ImageIcon(
                getClass().getClassLoader().getResource("resources/image2020.png"));

        ImageIcon notesicon = new ImageIcon(
                getClass().getClassLoader().getResource("resources/pencil.png"));

        ImageIcon chaticon = new ImageIcon(
                getClass().getClassLoader().getResource("resources/chaticon.png"));

        ImageIcon miscicon = new ImageIcon(
                getClass().getClassLoader().getResource("resources/miscicon.png"));

        
        addTab(null, notesicon, new NotesPanel());
        addTab(null, chaticon, new IrcPanel());
        addTab(null, miscicon, new JPanel());
        addTab(null, settingsicon, new JPanel());

    }

}
