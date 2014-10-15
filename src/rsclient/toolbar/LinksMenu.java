/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package rsclient.toolbar;

import java.awt.Color;
import java.awt.Desktop;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.net.URL;
import javax.swing.AbstractAction;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;

/**
 *
 * @author ben
 */
public class LinksMenu extends JPopupMenu {
    
    public LinksMenu(){
        setFont(new Font("Liberation Sans", Font.PLAIN, 11));
        setBackground(Color.black); 
        setLightWeightPopupEnabled( false );
        add(new JMenuItem(new AbstractAction("Zybez Market") {
            public void actionPerformed(ActionEvent e) {
                openWebpage("http://forums.zybez.net/runescape-2007-prices");
            }
        }));
        add(new JMenuItem(new AbstractAction("OSRS Wiki") {
            public void actionPerformed(ActionEvent e) {
                openWebpage("http://2007.runescape.wikia.com/wiki/2007scape_Wiki");
            }
        }));
        add(new JMenuItem(new AbstractAction("OSRS Home") {
            public void actionPerformed(ActionEvent e) {
                openWebpage("http://oldschool.runescape.com/");
            }
        }));
                add(new JMenuItem(new AbstractAction("World Map") {
            public void actionPerformed(ActionEvent e) {
                openWebpage("http://www.runescape.com/img/rsp777/gamewin/runescape-map-24-july-07.jpg");
            }
        }));
                add(new JMenuItem(new AbstractAction("Luna Source Code") {
            public void actionPerformed(ActionEvent e) {
                openWebpage("http://github.com/bmoyer/osrsclient");
            }
        }));
    
}
    
    public static void openWebpage(String urlString) {
    try {
        Desktop.getDesktop().browse(new URL(urlString).toURI());
    } catch (Exception e) {
        e.printStackTrace();
    }
}
}

