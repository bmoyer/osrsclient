/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rsclient.hiscores;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.text.NumberFormat;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EtchedBorder;
import logic.Calculate;
import logic.RuneScapeAccount;
import net.miginfocom.swing.MigLayout;
import org.pushingpixels.trident.Timeline;
import rsclient.coregui.LengthRestrictedDocument;

/**
 *
 * @author ben
 */
public class HiscoresPanel extends JPanel implements StatRolloverListener {

    private JTextField usernameField;
    private JLabel xpDisplay, rsnLabel;
    private LevelsPanel levelsDisplayPanel;
    private RuneScapeAccount account;
    private JButton searchButton;
    private LevelInfoPanel levelInfoPanel;
    private Timeline rolloverTimeline;
    private ActionListener fireQuery;

    public HiscoresPanel() {
        account =  new RuneScapeAccount("");
        
        setup();
        setupListeners();
        setupAnimation();

        searchButton.addActionListener(fireQuery);
        usernameField.addActionListener(fireQuery);

    }

    @Override
    public void onRolledOver(LevelScorePanel skillPanel) {
        
        if(!account.isValidAccount()){
            return;
        }

        String skill = skillPanel.getSkill();

        String capitalizedSkill = Character.toUpperCase(skill.charAt(0)) + skill.substring(1);
        int levelInt = account.hiscores.get(skill).level;
        int rankInt = account.hiscores.get(skill).rank;
        int experienceInt = account.hiscores.get(skill).experience;
        String rankStr = NumberFormat.getIntegerInstance().format(rankInt);
        String experienceStr = NumberFormat.getIntegerInstance().format(experienceInt);
        if (rankInt < 0){
            rankStr = "Unranked";
        }
        if (!skill.equalsIgnoreCase("combat") && !skill.equalsIgnoreCase("overall")) {
            Integer x = Calculate.xpForLevel(levelInt + 1) - experienceInt;
            String xptl = NumberFormat.getIntegerInstance().format(x);

            //calculates the percentage of the way to the next level,
            //with 0% being 0 xp past the user's current level.
            double progressToLevel
                    = ((double) experienceInt - (double) Calculate.xpForLevel(levelInt))
                    / ((double) Calculate.xpForLevel(levelInt + 1) - (double) Calculate.xpForLevel(levelInt));

            levelInfoPanel.setInfo(capitalizedSkill, rankStr, experienceStr, xptl, (int) (progressToLevel * 100));

        } else if (skill.equalsIgnoreCase("overall")) {
            levelInfoPanel.setInfo(capitalizedSkill, rankStr, experienceStr, "", 0);
        } else {
            levelInfoPanel.setInfo("", "", "", "", 0);
        }
    }

    @Override
    public void onRolledOff(LevelScorePanel skillPanel) {
        levelInfoPanel.setInfo("", "", "", "", 0);
        levelInfoPanel.resetProgressBar();
    }

    private void setup() {
        this.setLayout(new MigLayout("ins 5,center"));
        this.setBackground(Color.BLACK);
        usernameField = new JTextField();
        usernameField.setDocument(new LengthRestrictedDocument(12));
        usernameField.setBackground(new Color(101, 101, 101));
        usernameField.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.RAISED));

        xpDisplay = new JLabel();
        rsnLabel = new JLabel("RSN:");
        rsnLabel.setForeground(Color.white);
        rsnLabel.setFont(new Font(rsnLabel.getFont().getFontName(), Font.BOLD, rsnLabel.getFont().getSize()));

        levelsDisplayPanel = new LevelsPanel();
        levelsDisplayPanel.setRolloverListener(this);

        levelInfoPanel = new LevelInfoPanel();

        searchButton = new JButton();
        searchButton.setIcon(new javax.swing.ImageIcon(getClass().getClassLoader().getResource("resources/searchiconsquare3.png")));
        //searchButton.setIcon(new javax.swing.ImageIcon(getClass().getClassLoader().getResource("resources/bwsearch2.png")));
	searchButton.setBorderPainted(false);
        searchButton.setFocusPainted(false);
        searchButton.setContentAreaFilled(false);

        add(rsnLabel, "cell 0 0, gap 0, align left");
        add(usernameField, "width 60%, cell 1 0,align left, ");
        add(searchButton, "cell 2 0,align right ");
        add(levelsDisplayPanel, "width 100%, height 20%, cell 0 1, center,spanx");
        add(levelInfoPanel, "width 100%, height 15%, cell 0 2, center, spanx");
    }

    private void setupAnimation() {
        rolloverTimeline = new Timeline(usernameField);
        rolloverTimeline.addPropertyToInterpolate("background", usernameField.getBackground(), new Color(121, 121, 121));
        rolloverTimeline.setDuration(150);
        
        usernameField.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                rolloverTimeline.play();
            }

            @Override
            public void mouseExited(MouseEvent e) {
                rolloverTimeline.playReverse();
            }
        });

    }

    private void setupListeners() {
        fireQuery = new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent ae) {
                account = new RuneScapeAccount(usernameField.getText());

                Runnable r1 = new Runnable() {
                    public void run() {
                        try {
                            rsnLabel.setText("loading...");
                            account.loadStats();
                        } catch (IOException ex) {}
                        if(account.isValidAccount()){
                            rsnLabel.setText("RSN:");
                            levelsDisplayPanel.updateLevels(account);
                        } else {
                            rsnLabel.setText("INVALID:");
                            levelsDisplayPanel.nullLevels();
                        }
                    }
                };
                new Thread(r1).start();
            }

        };
    }
}
