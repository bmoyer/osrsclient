/*
 * To change this license header, choose License Headers in Project Properties.
}

 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package rsclient.notes;

import javax.swing.JPanel;
import javax.swing.JTextArea;
import net.miginfocom.swing.MigLayout;
import rsclient.panelplugins.CombatCalcPanel;

/**
 *
 * @author ben
 */
public class NotesPanel extends JPanel {
    
    public NotesPanel(){
        super(new MigLayout());
        
        JTextArea notewindow = new JTextArea();
        notewindow.setLineWrap(true);  
        add(notewindow, "height 200, width 200, dock center");
	//add(new CombatCalcPanel(), "height 200, width 200");
    }
    
    
}
