/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rsclient.coregui;

import java.awt.Color;
import java.awt.Image;
import java.awt.Toolkit;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.UIManager;
import net.miginfocom.swing.MigLayout;
import org.pircbotx.exception.IrcException;
import rsclient.toolbar.MainToolBar;
import rsloader.Loader;
import rsloader.Loader.Game;
import rsreflection.Reflector;

/**
 *
 * @author ben
 */
public class RSClient {

	public static Reflector reflector = null;

	public static void main(String[] args) throws IrcException, IOException {

		Toolkit.getDefaultToolkit().setDynamicLayout(true);
		System.setProperty("sun.awt.noerasebackground", "true");
		JFrame.setDefaultLookAndFeelDecorated(true);
		JDialog.setDefaultLookAndFeelDecorated(true);

		try {
			UIManager.setLookAndFeel("de.muntjak.tinylookandfeel.TinyLookAndFeel");

		} catch (Exception e) {
			e.printStackTrace();
		}

		initUI();
	}

	public static void initUI() {
		JFrame mainwnd = new JFrame("Luna - Open source OSRS Client");
		Image icon = Toolkit.getDefaultToolkit().getImage("resources/lunaicon.png");
		mainwnd.setIconImage(icon);
		
	
		MainToolBar toolbar = new MainToolBar();
    
		mainwnd.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		JPanel mainpanel = new JPanel(new MigLayout("fill, insets 10"));
		mainpanel.setBackground(Color.black);
		mainpanel.add(toolbar, "dock north, growx, gap 0");
		toolbar.setVisible(true);

		mainwnd.getContentPane().add(mainpanel);
		JPanel gamepanel = new JPanel(new MigLayout(" gap 0, ins 0,"));
		gamepanel.setBackground(Color.gray);
		gamepanel.setSize(765, 503);

		boolean debug = false;

		gamepanel.setVisible(true);
		mainpanel.add(gamepanel, "height 503:503:503,width 765:765:765, cell 0 0, grow 0");
		gamepanel.setVisible(true);

		JPanel sidepanel = new JPanel(new MigLayout("ins 0"));
		JPanel bottompanel = new JPanel(new MigLayout("ins 0"));
		sidepanel.setBackground(Color.black);
		bottompanel.setBackground(Color.black);

		bottompanel.add(new BottomPane(), "height 200, width 765, cell 0 0,spanx, push, growx ");
		sidepanel.add(new SidePane(), "width 250, height 503, cell 0 0, spany,push, growy");
		mainpanel.add(bottompanel, "height 200, width 765,cell 0 1, growy, spany ");
		mainpanel.add(sidepanel, "width 250, height 503, cell 1 0,growy, spany ");

		mainpanel.setVisible(true);
		mainwnd.pack();
		mainwnd.setVisible(true);

		mainwnd.pack();

		if (debug) {
			gamepanel.add(new JPanel(), "width 765, height 503, cell 0 0");

			//GameSession game = new GameSession();
			//reflector.setHooks(HookImporter.readJSON("/home/ben/hooks.json"));
			//IRCSession mainsesh = new IRCSession();
			//mainsesh.connect("irc.swiftirc.net");
			//mainsesh.joinChannel("#night");
                        
                        
		}
		if (!debug) {
			try {
				LoadingPanel l = new LoadingPanel();

				final Loader loader = new Loader(Game.OSRS);
				gamepanel.add(l, "width 765, height 503, cell 0 0");
				gamepanel.add(loader.applet, "width 765, height 503, dock north");
				loader.applet.setLayout(null);
				loader.applet.setBounds(0, 0, 765, 503);
				gamepanel.remove(l);
				loader.applet.resize(765, 503);
				reflector = loader.loader;

			} catch (IllegalArgumentException ex) {
				Logger.getLogger(RSClient.class.getName()).log(Level.SEVERE, null, ex);

			}

		}

	}
}
