/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package rsclient.coregui;

import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 *
 * @author ben
 */
public class LoadingPanel extends JPanel{
    
    public LoadingPanel(){
        JLabel loadingLabel = new JLabel("OSRS Loading, please wait.\nThank you for choosing Luna!");
        loadingLabel.setLocation(200,765/2);
        add(loadingLabel);
        
    }
}
