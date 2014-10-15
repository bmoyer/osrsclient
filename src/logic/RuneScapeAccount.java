/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author ben Code from the FOSS RSIRCBot project was used in this class.
 * Project can be found at:
 * https://github.com/Talon876/RSIRCBot/blob/master/src/org/nolat/rsircbot
 */
package logic;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;

public class RuneScapeAccount {

    private boolean isValidAccount;
    public String username;
    int overallLevel = 0;
    private final String[] skillNames = {"overall", "attack", "defense", "strength", "hitpoints",
        "ranged", "prayer", "magic", "cooking", "woodcutting", "fletching",
        "fishing", "firemaking", "crafting", "smithing", "mining", "herblore",
        "agility", "thieving", "slayer", "farming", "runecrafting", "hunter", "construction"};

    public final HashMap<String, RuneScapeLevel> hiscores = new HashMap();

    String baseUrl = "http://services.runescape.com/m=hiscore_oldschool/index_lite.ws?player=";

    public RuneScapeAccount(String u) {

        username = u;
        isValidAccount = false;
    }

    public void loadStats() throws IOException {

        String rsn = username.replaceAll(" ", "_");
        BufferedReader in = null;
        try {
            URL hiscoreUrl = new URL(baseUrl + username);
            URLConnection yc = hiscoreUrl.openConnection();
            in = new BufferedReader(new InputStreamReader(yc.getInputStream()));
            String inputLine;
            int c = 0;
            while ((inputLine = in.readLine()) != null) {
                String[] tokens = inputLine.split(",");
                if (tokens.length == 3) {
                    RuneScapeLevel level = new RuneScapeLevel(Integer.parseInt(tokens[0]), Integer.parseInt(tokens[1]), Integer.parseInt(tokens[2]));
                    if (c <= skillNames.length) {
                        hiscores.put(skillNames[c], level);
                    }
                    c++;
                }
            }
            RuneScapeLevel combatLevel = new RuneScapeLevel(0, (int) getCombatLevel(), 0);
            hiscores.put("combat", combatLevel);
            isValidAccount = true;
        } catch (IOException ex) {
            ex.printStackTrace();
        } finally {
            if (in != null) {
                in.close();
            }
        }

    }

    public static boolean isValidRSN(String s) {
        if (s.length() > 12) {
            return false;
        }
        //determine if any non-alphanumeric characters are in the string, indicating invalid RSN
        return !s.replace("-", "").replace(" ", "").replace("_", "").matches("^.*[^a-zA-Z0-9 ].*$");
    }

    public double getCombatLevel() {

        return Calculate.calculateCombatLevel(hiscores.get("attack").level, hiscores.get("strength").level,
                hiscores.get("defense").level, hiscores.get("prayer").level, hiscores.get("ranged").level,
                hiscores.get("magic").level, hiscores.get("hitpoints").level);
    }

    public boolean isValidAccount() {
        return isValidAccount;
    }

}
