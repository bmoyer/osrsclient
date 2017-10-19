/*
 * To change this license header, choose License Headers in Project Properties.
}

 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package rsclient.notes;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import net.miginfocom.swing.MigLayout;
import rsclient.panelplugins.CombatCalcPanel;

/**
 *
 * @author ben
 */
public class NotesPanel extends JPanel {
    public JTextArea notewindow;
    private float initFontSize = 20;
    
    public NotesPanel(){
        super(new MigLayout());
        setBackground(Color.BLACK);
        JPanel buttonsPanel = new JPanel();
        buttonsPanel.setLayout(new MigLayout());
        buttonsPanel.setBackground(Color.BLACK);
        JButton increaseFontSize = new JButton("+");
        JButton decreaseFontSize = new JButton("-");
        buttonsPanel.add(increaseFontSize, "wrap, growx");
        buttonsPanel.add(decreaseFontSize, "growx");
        notewindow = new JTextArea();
        JScrollPane scrollPane = new JScrollPane(notewindow);
        scrollPane.setBackground(Color.black);
        notewindow.setLineWrap(true); 
        notewindow.setBackground(new Color(221, 221, 221));
        notewindow.setFont(notewindow.getFont().deriveFont(initFontSize));
        add(scrollPane, "height 200, width 200, dock center");
	//add(new CombatCalcPanel(), "height 200, width 200");
        increaseFontSize.addActionListener(new AddListener());
        decreaseFontSize.addActionListener(new SubListener());
       // add(increaseFontSize);
        //add(decreaseFontSize);
        add(buttonsPanel, "growy");
        
    }
    
    private class AddListener implements ActionListener {
        public void actionPerformed(ActionEvent e){
            Font f = notewindow.getFont();
            if(f.getSize() < 30){
                Font f2 = new Font(f.getFontName(), f.getStyle(), f.getSize() + 2);
                notewindow.setFont(f2);
            }
        } 
    }
    
    private class SubListener implements ActionListener {
        public void actionPerformed(ActionEvent e){
            Font f = notewindow.getFont();
            if(f.getSize() > 12){
                Font f2 = new Font(f.getFontName(), f.getStyle(), f.getSize() - 2);
                notewindow.setFont(f2);
            }
        } 
    }
}
