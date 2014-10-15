/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rsclient.hiscores;

import java.awt.Color;
import java.util.HashMap;
import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.border.EtchedBorder;
import logic.RuneScapeAccount;
import net.miginfocom.swing.MigLayout;
import rsclient.coregui.Style;

/**
 *
 * @author ben
 */
public class LevelsPanel extends JPanel implements StatRolloverListener {

    private static final String[] skillnames
            = {"attack", "hitpoints", "mining",
                "strength", "agility", "smithing",
                "defense", "herblore", "fishing",
                "ranged", "thieving", "cooking",
                "prayer", "crafting", "firemaking",
                "magic", "fletching", "woodcutting",
                "runecrafting", "slayer", "farming",
                "construction", "hunter",
                "overall", "combat"};

    private final HashMap<String, LevelScorePanel> skillPanels;
    private StatRolloverListener rolloverListener;

    public LevelsPanel() {
        skillPanels = new HashMap<String, LevelScorePanel>();
        setup();

    }

    private void setup() {
        this.setLayout(new MigLayout(" center, fill, ins 0"));
        this.setBackground(new Color(51, 51, 51));
        this.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.RAISED));
        int i = 0;
        for (int row = 0; row < 7; row++) {
            for (int col = 0; col < 3; col++) {
                LevelScorePanel skillPanel = new LevelScorePanel(skillnames[i], Style.getDefaultStyle());
                skillPanels.put(skillnames[i], skillPanel);
                skillPanel.setRolloverListener(this);
                this.add(skillPanel, "cell " + col + " " + row + ",grow, gap 0,");
                i++;
            }
        }

        for (int row = 7; row < 8; row++) {
            for (int col = 0; col < 2; col++) {
                LevelScorePanel skillPanel = new LevelScorePanel(skillnames[i], Style.getDefaultStyle());
                skillPanels.put(skillnames[i], skillPanel);
                skillPanel.setRolloverListener(this);
                this.add(skillPanel, "cell " + col + " " + row + ",grow, gap 0, ");
                i++;

            }
        }

        for (int row = 8; row < 9; row++) {
            for (int col = 0; col < 2; col++) {
                LevelScorePanel skillPanel = new LevelScorePanel(skillnames[i], Style.getDefaultStyle());
                skillPanels.put(skillnames[i], skillPanel);
                skillPanel.setRolloverListener(this);
                this.add(skillPanel, "cell " + col + " " + row + ",gap 40, align left ,spanx");
                i++;
            }
        }

    }

    public void updateLevels(RuneScapeAccount account) {
        for (String skill : skillnames) {
            skillPanels.get(skill).updateLevelStat(account);
        }
    }

    void nullLevels() {
        for (String skill : skillnames) {
            skillPanels.get(skill).nullLevelStat();
        }
    }

    public void setRolloverListener(StatRolloverListener rolloverListener) {
        this.rolloverListener = rolloverListener;
    }

    @Override
    public void onRolledOver(LevelScorePanel skillPanel) {
        rolloverListener.onRolledOver(skillPanel);
    }

    @Override
    public void onRolledOff(LevelScorePanel skillPanel) {
        rolloverListener.onRolledOff(skillPanel);
    }

}
