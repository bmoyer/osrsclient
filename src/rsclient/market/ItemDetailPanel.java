/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rsclient.market;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JToggleButton;
import javax.swing.border.EtchedBorder;
import logic.ZybezOffer;
import net.miginfocom.swing.MigLayout;

/**
 *
 * @author ben
 */
public class ItemDetailPanel extends JPanel {

    private JToggleButton wtbButton, wtsButton;
    private JScrollPane listScroller;
    private JLabel itemSelectedLabel;
    private JTable offerTable;
    private OfferModel offerModel;
    private ArrayList<ZybezOffer> currentOffers;
    private ActionListener toggleListener;

    public ItemDetailPanel() {
        setup();
        addButtonListeners();

    }

    public void showBuyingOffers(ArrayList<ZybezOffer> offerList) {
        offerModel.clearRows();
        String itemName = null;

        for (ZybezOffer z : offerList) {
            String buyOrSell;
            itemName = z.getItemName();

            if (z.isSellingOffer().equals("1")) {
                buyOrSell = "S";
                offerModel.addRow(buyOrSell, z.getQuantity(), z.getPrice(), z.getRSN());
            }
        }
        itemSelectedLabel.setText(itemName);
    }

    public void showSellingOffers(ArrayList<ZybezOffer> offerList) {
        offerModel.clearRows();
        String itemName = null;

        for (ZybezOffer z : offerList) {
            String buyOrSell;
            itemName = z.getItemName();

            if (z.isSellingOffer().equals("0")) {
                buyOrSell = "B";
                offerModel.addRow(buyOrSell, z.getQuantity(), z.getPrice(), z.getRSN());
            }
        }
        itemSelectedLabel.setText(itemName);
    }

    public void updateOffers(ArrayList<ZybezOffer> offerList) {
        currentOffers = offerList;

        offerModel.clearRows();
        String itemName = null;

        for (ZybezOffer z : offerList) {
            String buyOrSell;
            itemName = z.getItemName();

            if (z.isSellingOffer().equals("1")) {
                buyOrSell = "S";
            } else {
                buyOrSell = "B";
            }

            offerModel.addRow(buyOrSell, z.getQuantity(), z.getPrice(), z.getRSN());
        }
        itemSelectedLabel.setText(itemName);
        wtbButton.setSelected(true);
        wtsButton.setSelected(true);

    }

    private void addButtonListeners() {

        toggleListener = new ActionListener() {
            public void actionPerformed(ActionEvent ev) {
                filterOffers();
            }
        };

        wtbButton.addActionListener(toggleListener);
        wtsButton.addActionListener(toggleListener);

    }

    private void filterOffers() {
        if (wtbButton.isSelected() && wtsButton.isSelected()) {
            updateOffers(currentOffers);
        }

        if (wtbButton.isSelected() & !wtsButton.isSelected()) {
            showBuyingOffers(currentOffers);
        }
        if (!wtbButton.isSelected() && wtsButton.isSelected()) {
            showSellingOffers(currentOffers);
        }

        if (!wtbButton.isSelected() & !wtsButton.isSelected()) {
            offerModel.clearRows();
        }
    }
    
    //resets 
    private void resetToggleButtons(){
        
    }

    private void setup() {
        setLayout(new MigLayout("ins 0,align center, "));
        setBackground(new Color(51, 51, 51));
        setBorder(BorderFactory.createEtchedBorder(EtchedBorder.RAISED));

        wtbButton = new JToggleButton("WTB");
        wtsButton = new JToggleButton("WTS");
        wtbButton.setSelected(true);
        wtsButton.setSelected(true);
        itemSelectedLabel = new JLabel();
        itemSelectedLabel.setMaximumSize(new Dimension(160, 50));

        offerModel = new OfferModel();
        offerTable = new JTable(offerModel);

        //Setting up ideal column widths
        offerTable.getColumnModel().getColumn(0).setPreferredWidth(17);
        offerTable.getColumnModel().getColumn(1).setPreferredWidth(50);
        offerTable.getColumnModel().getColumn(3).setPreferredWidth(110);

        listScroller = new JScrollPane(offerTable, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

        add(wtbButton, "cell 0 2,gapy 0");
        add(wtsButton, "cell 1 2, align right, spanx");
        add(new JLabel("Item:"), "cell 0 5, gapy 0, align left ");
        add(itemSelectedLabel, "cell 1 5, gapy 0, align left");
        add(listScroller, "cell 0 4,width 100%,height 100%,spanx");

    }

}
