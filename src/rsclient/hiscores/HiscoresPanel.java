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

        setup();
        setupListeners();
        setupAnimation();

        searchButton.addActionListener(fireQuery);
        usernameField.addActionListener(fireQuery);

    }

    @Override
    public void onRolledOver(LevelScorePanel skillPanel) {

        String skill = skillPanel.getSkill();

        if (!skill.equalsIgnoreCase("combat") && !skill.equalsIgnoreCase("overall")) {

            String capitalizedSkill = Character.toUpperCase(skill.charAt(0)) + skill.substring(1);

            String rank = NumberFormat.getIntegerInstance().format(account.hiscores.get(skill).rank);
            String experience = NumberFormat.getIntegerInstance().format(account.hiscores.get(skill).experience);

            Integer x = Calculate.xpForLevel(account.hiscores.get(skill).level + 1) - account.hiscores.get(skill).experience;
            String xptl = NumberFormat.getIntegerInstance().format(x);

            //calculates the percentage of the way to the next level,
            //with 0% being 0 xp past the user's current level.
            double progressToLevel
                    = ((double) account.hiscores.get(skill).experience - (double) Calculate.xpForLevel(account.hiscores.get(skill).level))
                    / ((double) Calculate.xpForLevel(account.hiscores.get(skill).level + 1) - (double) Calculate.xpForLevel(account.hiscores.get(skill).level));

            levelInfoPanel.setInfo(capitalizedSkill, rank, experience, xptl, (int) (progressToLevel * 100));

        } else if (skill.equalsIgnoreCase("overall")) {
            String capitalizedSkill = Character.toUpperCase(skill.charAt(0)) + skill.substring(1);
            String rank = NumberFormat.getIntegerInstance().format(account.hiscores.get(skill).rank);
            String experience = NumberFormat.getIntegerInstance().format(account.hiscores.get(skill).experience);
            levelInfoPanel.setInfo(capitalizedSkill, rank, experience, "", 0);

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
        usernameField = new JTextField();
        usernameField.setDocument(new LengthRestrictedDocument(12));
        usernameField.setBackground(new Color(51, 51, 51));
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
        rolloverTimeline.addPropertyToInterpolate("background", usernameField.getBackground(), new Color(91, 91, 91));
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
                        } catch (IOException ex) {
                            rsnLabel.setText("none");
                            levelsDisplayPanel.nullLevels();
                        } finally {
                            rsnLabel.setText("RSN:");
                        }
                        if (!account.isValidAccount()) {
                            levelsDisplayPanel.nullLevels();
                        }
                        levelsDisplayPanel.updateLevels(account);
                    }
                };
                new Thread(r1).start();
            }

        };
    }
}
