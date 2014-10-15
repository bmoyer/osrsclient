/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rsclient.market;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.Border;
import javax.swing.border.EtchedBorder;
import logic.ZybezQuery;
import net.miginfocom.swing.MigLayout;
import org.pushingpixels.trident.Timeline;

/**
 *
 * @author ben
 */
public class MarketPanel extends JPanel implements ItemListingRolloverListener {

    JTextField itemInputField;
    ItemListPanel itemDisplayPanel;
    ItemDetailPanel itemDetailPanel;
    JLabel itemLabel;
    JButton searchButton;
    ZybezQuery query;
    private Timeline rolloverTimeline;
    private ActionListener fireQuery;

    public MarketPanel() {
        setup();
        setupListeners();
        setupAnimation();

        searchButton.addActionListener(fireQuery);
        itemInputField.addActionListener(fireQuery);

    }

    private void setup() {
        setLayout(new MigLayout("ins 5, center"));
        Border loweredbevel = BorderFactory.createEtchedBorder(EtchedBorder.RAISED);
        Font f = new Font(new JLabel().getFont().getFontName(), Font.BOLD, new JLabel().getFont().getSize() + 2);
        ImageIcon searchicon = new ImageIcon(
                getClass().getClassLoader().getResource("resources/searchicon20.png"));

        itemDetailPanel = new ItemDetailPanel();
        itemInputField = new JTextField();
        itemLabel = new JLabel("ITEM:");
        searchButton = new JButton();

        itemDisplayPanel = new ItemListPanel();
        itemInputField.setBorder(loweredbevel);
        itemInputField.setBackground(new Color(51, 51, 51));
        itemLabel.setForeground(Color.white);
        itemLabel.setFont(new Font(itemLabel.getFont().getFontName(), Font.BOLD, itemLabel.getFont().getSize()));

        searchButton.setIcon(new javax.swing.ImageIcon(getClass().getClassLoader().getResource("resources/searchiconsquare3.png")));
        searchButton.setBorderPainted(false);
        searchButton.setFocusPainted(false);
        searchButton.setContentAreaFilled(false);

        itemDisplayPanel.setRolloverListener(this);

        add(itemLabel, "cell 0 0, gap 0, align left");
        add(searchButton, "cell 2 0,align right ");
        add(itemInputField, "width 60%, cell 1 0,align left,");
        add(itemDisplayPanel, "width 100%, height 30%, cell 0 1, center,spanx");
        //30% height on itemDetailPanel leaves ample room at the bottom
        //for a future offer-watcher.
        add(itemDetailPanel, "width 100%, height 50%, cell 0 2, center, spanx");

    }

    private void setupAnimation() {
        rolloverTimeline = new Timeline(itemInputField);
        rolloverTimeline.addPropertyToInterpolate("background", itemInputField.getBackground(), new Color(91, 91, 91));
        rolloverTimeline.setDuration(150);
        itemInputField.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                rolloverTimeline.play();
            }

            @Override
            public void mouseExited(MouseEvent e) {
                rolloverTimeline.playReverse();
            }
        });
    }

    @Override
    public void onRolledOver(ItemResultPanel itemPanel) {
        //
    }

    @Override
    public void onRolledOff(ItemResultPanel itemPanel) {
        //
    }

    //Clicking ItemResultPanel calls a function in ItemListPanel which calls
    //this function.  This function populates the grid of offers for a given item.
    @Override
    public void onClicked(ItemResultPanel itemPanel) {
        itemDisplayPanel.setSelectedPanel(itemPanel);
        
        itemDetailPanel.updateOffers(itemPanel.getOffers());
        

    }

    private void setupListeners() {
        fireQuery = new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent ae) {
                String itemName = itemInputField.getText();
                itemDisplayPanel.clearResultPanels();
                Runnable r1 = new Runnable() {
                    public void run() {
                        try {
                            itemLabel.setText("loading..");
                            query = new ZybezQuery(itemInputField.getText().replace(" ", "_"));
                            itemDisplayPanel.populateResultPanels(query.getItemListings());
                        } finally {
                            itemLabel.setText("ITEM:");
                        }
                    }
                };
                new Thread(r1).start();
            }
        };
    }

}
