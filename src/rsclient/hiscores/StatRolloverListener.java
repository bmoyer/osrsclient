/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rsclient.hiscores;

/**
 *
 * @author ben
 */
public interface StatRolloverListener {

    public void onRolledOver(LevelScorePanel skillPanel);

    public void onRolledOff(LevelScorePanel skillPanel);



}
