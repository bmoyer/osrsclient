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
 * @author ben
 */
public class ZybezQuery {

    private final String itemRequested;
    private final String apiURL = "http://forums.zybez.net/runescape-2007-prices/api/";
    private final ArrayList<String> itemIDList;
    private ArrayList<ZybezItemListing> itemList;

    public ZybezQuery(String s) {
        itemRequested = s;
        itemIDList = getItemIDList();
        createItemListings();
        
    }

    public ArrayList<ZybezItemListing> getItemListings() {

        ArrayList<ZybezItemListing> listings = new ArrayList();

        for (String itemID : itemIDList) {
            listings.add(new ZybezItemListing(itemID));
        }

        return listings;
    }

    public void createItemListings() {

        itemList = new ArrayList();

        for (String itemID : itemIDList) {
            itemList.add(new ZybezItemListing(itemID));
        }
    }

    public ArrayList<String> getItemIDList() {

        ArrayList<String> itemIDs = new ArrayList();

        try {
            String s = getJsonString();
            JSONArray array = (JSONArray) JSONValue.parse(s);
            int numItems = array.size();

            for (int i = 0; i < numItems; i++) {
                JSONObject o = ((JSONObject) array.get(i));
                itemIDs.add(o.get("id").toString());
                
            }
           
        } catch (IOException ex) {
            Logger.getLogger(ZybezQuery.class.getName()).log(Level.SEVERE, null, ex);
        }
        return itemIDs;
    }

    private String getJsonString() throws MalformedURLException, IOException {
        URL url = new URL(apiURL + itemRequested);
        URLConnection urlConnection = url.openConnection();
        urlConnection.addRequestProperty("User-Agent", "Mozilla/5.0");
        BufferedReader in = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));

        //Put JSON data into String message
        String message = org.apache.commons.io.IOUtils.toString(in);
        return message;
    }

}
