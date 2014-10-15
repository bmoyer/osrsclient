/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logic;

/**
 *
 * @author ben
 */
public class ZybezOffer {

    private final String itemName, rsn, notes,quantity, price, id, selling;

    public ZybezOffer(String i, String s,String q, String p, String iN, String r, String n) {
        id = i;
        quantity = q;
        price = p;
        itemName = iN;
        notes = n;
        rsn = r;
        selling = s;
        
    }

    public String isSellingOffer(){
        return selling;
    }
    
    public String getRSN(){
        return rsn;
    }
    
    public String getID() {
        return id;
    }

    public String getQuantity() {
        return quantity;
    }

    public String getPrice() {
        return price;
    }

    public String getItemName() {
        return itemName;
    }

    public String getNotes() {
        return notes;
    }

}
