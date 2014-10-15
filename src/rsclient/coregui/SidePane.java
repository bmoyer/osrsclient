/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rsclient.coregui;

import rsclient.hiscores.HiscoresPanel;
import rsclient.market.MarketPanel;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import rsclient.panelplugins.BasePluginPanel;

/**
 *
 * @author ben
 */
public class SidePane extends JTabbedPane {

    public SidePane() {
        setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT);

        ImageIcon statsicon = new ImageIcon(
                getClass().getClassLoader().getResource("resources/statsicon.png"));

        ImageIcon shopicon = new ImageIcon(
                getClass().getClassLoader().getResource("resources/shopicon.png"));

        ImageIcon graphicon = new ImageIcon(
                getClass().getClassLoader().getResource("resources/graphicon.png"));

        ImageIcon toolsicon = new ImageIcon(
                getClass().getClassLoader().getResource("resources/toolsicon.png"));

        addTab(null, statsicon, new HiscoresPanel());

        addTab(null, shopicon, new MarketPanel());

        addTab(null, graphicon, new JPanel());

        addTab(null, toolsicon, new BasePluginPanel());

    }
}
