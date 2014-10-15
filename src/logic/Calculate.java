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
public class Calculate {
    
    //Credits to Talon876/RSIRCBOT project for this function.
    public static double calculateCombatLevel(int atk, int str, int def, int prayer, int range, int magic, int hp) {

        double baseLvl = (def + hp + Math.floor(prayer / 2)) * 0.25;

        double meleeLvl = (atk + str) * 0.325;
        double rangerLvl = Math.floor(range * 1.5) * 0.325;
        double mageLvl = Math.floor(magic * 1.5) * 0.325;

        baseLvl = baseLvl + Math.max(meleeLvl, (Math.max(rangerLvl, mageLvl)));

        return baseLvl;
    }
    
    public static int xpForLevel(int level) {
        double value = 0;
        for (int i = 1; i < level; i++) {
            value += Math.floor(i + 300f * Math.pow(2f, i / 7f));
        }
        value = (int) Math.floor(.25 * value);
        return (int) value;
    }
    
    
}
