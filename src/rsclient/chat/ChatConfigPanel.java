/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rsclient.chat;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import logic.Parse;
import net.miginfocom.swing.MigLayout;
import org.pircbotx.exception.IrcException;

/**
 *
 * @author ben
 */
public class ChatConfigPanel extends JPanel {

	private JTextField nickField, chanField;
	private JButton connectButton;
	private JLabel nickLabel, chanLabel;
	private ActionListener fireConnect;
	private final IrcPanel mainIrcPanel;

	public ChatConfigPanel(IrcPanel ircPanel) {
		mainIrcPanel = ircPanel;

		this.setLayout(new MigLayout("ins 10"));
		setup();
		setupListeners();

		connectButton.addActionListener(fireConnect);
	}

	private void setup() {
		//setBackground(Color.black);
		nickField = new JTextField();
		chanField = new JTextField();
		nickLabel = new JLabel("Nick:");
		chanLabel = new JLabel("Channels:");
		connectButton = new JButton("Connect");

		add(nickLabel, "cell 0 0");
		add(chanLabel, "cell 0 1");
		add(nickField, "cell 1 0,  width 10%,  ");
		add(chanField, "cell 1 1, width 10%,");
		add(connectButton, "cell 0 2");
	}

	private void setupListeners() {
		fireConnect = new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent ae) {
				ArrayList<String> joinChannels = Parse.parseIrcChannels(chanField.getText());
				mainIrcPanel.addMainPanel();
				for (String s : joinChannels) {
					mainIrcPanel.chatMainPanel.addChanPanel(s);

				}
				mainIrcPanel.chatMainPanel.switchChanPanels(joinChannels.get(joinChannels.size() - 1));

				Runnable r1 = new Runnable() {
					public void run() {

						try {
							mainIrcPanel.startChat(nickField.getText(), Parse.parseIrcChannels(chanField.getText()));

						} catch (IOException ex) {
							Logger.getLogger(ChatConfigPanel.class.getName()).log(Level.SEVERE, null, ex);
						} catch (IrcException ex) {
							Logger.getLogger(ChatConfigPanel.class.getName()).log(Level.SEVERE, null, ex);
						}
					}
				};

				new Thread(r1).start();

			}
		};
	}

}
