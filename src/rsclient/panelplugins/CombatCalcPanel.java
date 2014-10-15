/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rsclient.panelplugins;

import java.awt.Color;
import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EtchedBorder;
import net.miginfocom.swing.MigLayout;

/**
 *
 * @author ben
 */
public class CombatCalcPanel extends JPanel {

	JTextField attackLevelField, strengthLevelField, defenseLevelField,
		rangedLevelField, magicLevelField, prayerLevelField, hitpointsLevelField;
	
	//Used to load a combat calculator into any extendable panel
	//This calculator should remain fairly resistant to panel size variations. 
	public CombatCalcPanel() {
		setup();
	}

	public void setup() {
		setLayout(new MigLayout("ins 5, left"));
		this.setBackground(new Color(52, 52, 52));
		this.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.RAISED));
		
		attackLevelField = new JTextField();
		strengthLevelField = new JTextField();
		defenseLevelField = new JTextField();
		rangedLevelField = new JTextField();
		magicLevelField = new JTextField();
		prayerLevelField = new JTextField();
		hitpointsLevelField = new JTextField();

		this.add(attackLevelField, "width 15%, height 15%, cell 0 0");
		this.add(rangedLevelField, "width 15%, height 15%, cell 1 0");
		this.add(strengthLevelField, "width 15%, height 15%, cell 2 0");
		this.add(defenseLevelField, "width 15%, height 15%, cell 0 1");
		this.add(magicLevelField, "width 15%, height 15%, cell 1 1");
		this.add(prayerLevelField, "width 15%, height 15%, cell 2 1");
		this.add(hitpointsLevelField, "width 15%, height 15%, cell 0 2");
	}
}
