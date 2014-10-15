/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rsclient.toolbar;

import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JToolBar;
import net.miginfocom.swing.MigLayout;

/**
 *
 * @author ben
 */
public class MainToolBar extends JToolBar {

    public MainToolBar() {
        setLayout(new MigLayout());
        setFloatable(false);
        setBackground(new Color(24, 24, 24));
        setRollover(true);

        ToolBarButton optionsButton = new ToolBarButton("Options");
        ToolBarButton linksButton = new ToolBarButton("Links");
        ToolBarButton screenshotButton = new ToolBarButton("Screenshot");
        ToolBarButton helpButton = new ToolBarButton("Help");

        final LinksMenu linksmenu = new LinksMenu();
        final HelpMenu helpmenu = new HelpMenu();

        linksButton.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                linksmenu.show(e.getComponent(), e.getX(), e.getY());
            }
        });

        helpButton.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                helpmenu.show(e.getComponent(), e.getX(), e.getY());
            }
        });

        add(optionsButton);
        add(linksButton);
        add(screenshotButton);
        add(helpButton);

    }
}
