/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logic;

import java.util.ArrayList;
import java.util.Arrays;

/**
 *
 * @author ben
 */
public class Parse {
    
    public static ArrayList<String> parseIrcChannels(String string){
        string = string.replace(" ", "");
        ArrayList<String> chanList = new ArrayList(Arrays.asList(string.split(",")));
        
        return chanList;
    }
    
}
