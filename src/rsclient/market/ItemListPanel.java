/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rsclient.market;

import java.awt.Color;
import java.util.ArrayList;
import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.border.Border;
import javax.swing.border.EtchedBorder;
import logic.ZybezItemListing;
import net.miginfocom.swing.MigLayout;

/**
 *
 * @author ben
 */
public class ItemListPanel extends JPanel implements ItemListingRolloverListener {

    ItemResultPanel offerPanels[] = new ItemResultPanel[5];
    private ItemResultPanel selectedPanel = null;
    private ItemListingRolloverListener rolloverListener;
    public boolean itemsFound;

    private final Border selectedBorder = BorderFactory.createEtchedBorder(Color.white, Color.blue);

    public ItemListPanel() {
        setup();

        for (int x = 0; x < 5; x++) {
            offerPanels[x] = new ItemResultPanel();
            offerPanels[x].setRolloverListener(this);
        }

        int i = 0;
        for (ItemResultPanel j : offerPanels) {

            add(j, "height 20%, width 100%, spanx, growx, cell 0 " + i);
            i++;
        }
    }

    public void populateResultPanels(ArrayList<ZybezItemListing> itemsFound) {

        int c = 0;
        for (ZybezItemListing z : itemsFound) {

            if (c < offerPanels.length) {
                offerPanels[c].setDetails(z);
                offerPanels[c].setOffers(z.getOfferList());
                System.out.println(z.getItemName());
                c++;

            } else {
                return;
            }
        }

    }
    
    public void clearResultPanels(){
        if(selectedPanel != null){
            selectedPanel.deselectPanel();
        }
        for(int i = 0; i < 5; i++){
            offerPanels[i].clearContents();
        }
    }
    
    public void setSelectedPanel(ItemResultPanel itemPanel){
        
        if(selectedPanel != null){
            selectedPanel.deselectPanel();
        }
        selectedPanel = itemPanel;
        
        itemPanel.selectPanel();
    }


    public void setup() {
        setLayout(new MigLayout("ins 2, fill"));
        setBackground(new Color(51, 51, 51));
        setBorder(BorderFactory.createEtchedBorder(EtchedBorder.RAISED));

    }

    public void setRolloverListener(ItemListingRolloverListener rolloverListener) {
        this.rolloverListener = rolloverListener;
    }

    @Override
    public void onRolledOver(ItemResultPanel itemPanel) {
        //rolloverListener.onRolledOver(itemPanel);
    }

    @Override
    public void onRolledOff(ItemResultPanel itemPanel) {
        //rolloverListener.onRolledOff(itemPanel);
    }

    @Override
    public void onClicked(ItemResultPanel itemPanel) {
        rolloverListener.onClicked(itemPanel);
    }

}
