/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logic;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

/**
 *
 * @author ben This class is used to represent an item returned as a possible
 * match when performing a Zybez Market search. Specific offers for an
 * individual item are kept in offerList.
 */
public class ZybezItemListing {

    private String itemName, imageURL, averagePrice, itemID;
    private ArrayList<ZybezOffer> offerList = new ArrayList();
    private int numOffers;
    private final String apiURL = "http://forums.zybez.net/runescape-2007-prices/api/item/";
    private String listingJsonString;

    public ZybezItemListing(String id) {

        itemID = id;
        setItemData();
        setItemOffers();
    }

    private void setItemData() {
        
        try {
            listingJsonString = getJsonString();
            JSONObject o = (JSONObject) JSONValue.parse(listingJsonString);
            
            itemName = o.get("name").toString();
            imageURL = o.get("image").toString();
            averagePrice = o.get("average").toString();
            
            
        } catch (IOException ex) {
            Logger.getLogger(ZybezItemListing.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
    
    private void setItemOffers(){
        
        JSONObject o = (JSONObject) JSONValue.parse(listingJsonString);
        String offerString  = o.get("offers").toString();
        JSONArray array = (JSONArray) JSONValue.parse(offerString);
        
        numOffers = array.size();
        for(int i = 0; i < numOffers; i ++){
            JSONObject o2 = (JSONObject) JSONValue.parse(array.get(i).toString());
            
            ZybezOffer z = new ZybezOffer(itemID, o2.get("selling").toString(),
            o2.get("quantity").toString(), o2.get("price").toString(), itemName, o2.get("rs_name").toString(), o2.get("notes").toString());
            
            offerList.add(z);
        }
        
        
    }

    //returns Json string for offers for this item
    private String getJsonString() throws MalformedURLException, IOException {
        URL url = new URL(apiURL + itemID);
        URLConnection urlConnection = url.openConnection();
        urlConnection.addRequestProperty("User-Agent", "Mozilla/5.0");
        BufferedReader in = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
        //Put JSON data into String message
        String message = org.apache.commons.io.IOUtils.toString(in);
        return message;
    }

    public String getItemID() {
        return itemID;
    }

    public String getItemName() {
        return itemName;
    }

    public String getImageURL() {
        return imageURL;
    }

    public String getAveragePrice() {
        return averagePrice;
    }

    public ArrayList<ZybezOffer> getOfferList() {
        return offerList;
    }

}
