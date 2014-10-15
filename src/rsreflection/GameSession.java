/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package rsreflection;

import rsclient.coregui.RSClient;

/**
 *
 * @author ben
 */
public class GameSession {
    
    public GameSession(){
	    
	    
    }
    
    public String getSelectedItem(){
        return (String)RSClient.reflector.getFieldValueByDetails("client", "it");
    }
    
    public int[] getExperienceList(){
	    return (int[])RSClient.reflector.getFieldValueByDetails("client", "it");
    }
    
}
