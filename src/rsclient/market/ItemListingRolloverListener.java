/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rsclient.market;

/**
 *
 * @author ben
 */
public interface ItemListingRolloverListener {

    public void onRolledOver(ItemResultPanel itemPanel);

    public void onRolledOff(ItemResultPanel itemPanel);
    
    public void onClicked(ItemResultPanel itemPanel);

}
