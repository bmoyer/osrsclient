/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package rsclient.toolbar;

import java.awt.Color;
import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPopupMenu;
/**
 *
 * @author ben
 */
    
public class HelpMenu extends JPopupMenu {
    
    public HelpMenu(){ 
        setBackground(Color.black); 
        setLightWeightPopupEnabled( false );
        add(new JMenuItem(new AbstractAction("About") {
            public void actionPerformed(ActionEvent e) {
               JOptionPane.showMessageDialog(null, 
                       "Developed by Ben Moyer.\nFor more information, please visit www.github.com/bmoyer",
                       "About", JOptionPane.INFORMATION_MESSAGE);
            }
        }));
        

    
}
}
