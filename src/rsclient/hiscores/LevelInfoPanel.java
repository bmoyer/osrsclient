/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rsclient.hiscores;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.border.EtchedBorder;
import net.miginfocom.swing.MigLayout;
import org.pushingpixels.trident.Timeline;

/**
 *
 * @author ben
 */
public class LevelInfoPanel extends JPanel {

    private JLabel skillLabel, rankLabel, xpLabel, xpToLevelLabel;
    private JLabel skill, rank, xp, xpToLevel;
    private JProgressBar xpToLevelBar;
    private Timeline rolloverTimeline;

    private Timeline numberline;
    private int number = 5;

    public LevelInfoPanel() {
        setup();
        setupAnimation();

    }

    public void setInfo(String s, String r, String x, String xtl, Integer ptl) {
        skill.setText(s);
        rank.setText(r);
        xp.setText(x);
        xpToLevel.setText(xtl);

        rolloverTimeline.addPropertyToInterpolate("value", 0, ptl);
        xpToLevelBar.setString(Integer.toString(ptl) + "%");
        updateProgressBar();
    }

    public void updateProgressBar() {
        rolloverTimeline.play();

    }

    public void resetProgressBar() {
        rolloverTimeline.addPropertyToInterpolate("value", xpToLevelBar.getValue(), 0);
        rolloverTimeline.play();
    }

    private void setup() {

        this.setLayout(new MigLayout("ins 05, gapx 5, align left, "));
        setBackground(new Color(51, 51, 51));
        setBorder(BorderFactory.createEtchedBorder(EtchedBorder.RAISED));

        skillLabel = new JLabel("SKILL:");
        rankLabel = new JLabel("RANK:");
        xpLabel = new JLabel("XP:");
        xpToLevelLabel = new JLabel("XP TO LEVEL:");
        xpToLevelBar = new JProgressBar(0);
        xpToLevelBar.setStringPainted(true);

        skill = new JLabel("");
        rank = new JLabel("");
        xp = new JLabel("");
        xpToLevel = new JLabel("");

        skillLabel.setFont(new Font(skillLabel.getFont().getFontName(), Font.BOLD, skillLabel.getFont().getSize()));
        rankLabel.setFont(new Font(skillLabel.getFont().getFontName(), Font.BOLD, rankLabel.getFont().getSize()));
        xpLabel.setFont(new Font(skillLabel.getFont().getFontName(), Font.BOLD, xpLabel.getFont().getSize()));
        xpToLevelLabel.setFont(new Font(skillLabel.getFont().getFontName(), Font.BOLD, xpToLevelLabel.getFont().getSize()));

        skill.setFont(new Font(skillLabel.getFont().getFontName(), Font.BOLD, skillLabel.getFont().getSize()));
        rank.setFont(new Font(skillLabel.getFont().getFontName(), Font.BOLD, rankLabel.getFont().getSize()));
        xp.setFont(new Font(skillLabel.getFont().getFontName(), Font.BOLD, xpLabel.getFont().getSize()));
        xpToLevel.setFont(new Font(skillLabel.getFont().getFontName(), Font.BOLD, xpToLevelLabel.getFont().getSize()));

        skillLabel.setForeground(Color.white);
        rankLabel.setForeground(Color.white);
        xpLabel.setForeground(Color.white);
        xpToLevelLabel.setForeground(Color.white);

        skill.setForeground(Color.white);
        rank.setForeground(Color.white);
        xp.setForeground(Color.white);
        xpToLevel.setForeground(Color.white);
        
       xpToLevelBar.setForeground(Color.white);

        add(skillLabel, "cell 0 0,spanx ");
        add(rankLabel, "cell 0 1,spanx ");
        add(xpLabel, "cell 0 2, gap 0,spanx");
        add(xpToLevelLabel, "cell 0 3, gap 0,spanx ");

        add(skill, "cell 1 0,spanx, gap 45");
        add(rank, "cell 1 1, spanx, gap 45");
        add(xp, "cell 1 2,spanx, gap 25");
        add(xpToLevel, "cell 1 3, spanx, gap 100");
        add(xpToLevelBar, "cell 0 4,spanx, width 100%  ");
    }

    private void setupAnimation() {
        rolloverTimeline = new Timeline(xpToLevelBar);
        rolloverTimeline.setDuration(70);

    }

}
