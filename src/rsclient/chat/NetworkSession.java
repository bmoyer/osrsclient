/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rsclient.chat;

import com.google.common.collect.ImmutableSortedSet;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import logic.IrcManager;
import logic.Parse;
import org.pircbotx.Configuration;
import org.pircbotx.PircBotX;
import org.pircbotx.User;
import org.pircbotx.exception.IrcException;
import org.pircbotx.hooks.ListenerAdapter;
import org.pircbotx.hooks.WaitForQueue;
import org.pircbotx.hooks.events.ConnectEvent;
import org.pircbotx.hooks.events.JoinEvent;
import org.pircbotx.hooks.events.MessageEvent;
import org.pircbotx.hooks.events.PrivateMessageEvent;
import org.pircbotx.hooks.events.UserListEvent;

/**
 *
 * @author ben
 */
public class NetworkSession extends ListenerAdapter<PircBotX> {

	private final PircBotX bot;
	private final IrcManager manager;

	public NetworkSession(String nick, ArrayList<String> chanNames, IrcManager mgr) {
		Configuration.Builder<PircBotX> builder = new Configuration.Builder<>().
			setName(nick).
			setAutoNickChange(true).
			setMessageDelay(50).
			setServer("irc.swiftirc.net", 6667).
			setAutoReconnect(true).
			addListener(this);
		for (String s : chanNames) {
			builder.addAutoJoinChannel(s);
		}
		manager = mgr;
		bot = new PircBotX(builder.buildConfiguration());

	}

	public void startSession() throws IOException, IrcException {
		bot.startBot();
	}

	@Override
	public void onMessage(MessageEvent<PircBotX> event) throws Exception {
		//Handles only channelwide mesages.
		String channel = event.getChannel().getName();
		String message = event.getMessage();
		String user = event.getUser().getNick();
		long timestamp = event.getTimestamp();

		manager.messageReceived(channel, message, user);
	}

	@Override
	public void onPrivateMessage(PrivateMessageEvent<PircBotX> event) throws Exception {
		String channel = event.getUser().getNick();
		String message = event.getMessage();
		manager.messageReceived(channel, message, channel);
	}

	@Override
	public void onJoin(JoinEvent<PircBotX> event) throws Exception {

		Runnable r1 = new Runnable() {
			public void run() {
				WaitForQueue queue = new WaitForQueue(bot);
				UserListEvent currentEvent = null;
				try {
					currentEvent = queue.waitFor(UserListEvent.class);
				} catch (InterruptedException ex) {
					Logger.getLogger(NetworkSession.class.getName()).log(Level.SEVERE, null, ex);
				}
				manager.onJoinEvent(currentEvent.getChannel().getUsers().toArray());
			}
		};

		if (!event.getUser().getNick().equalsIgnoreCase(bot.getNick())) {
			//System.out.println("someone else joined the channel");
		} else {
			//System.out.println("you joined the channel");
			new Thread(r1).start();
		}

//		Runnable r1 = new Runnable() {
//			public void run() {
//				WaitForQueue queue = new WaitForQueue(bot);
//				UserListEvent currentEvent = null;
//				try {
//					currentEvent = queue.waitFor(UserListEvent.class);
//				} catch (InterruptedException ex) {
//					Logger.getLogger(NetworkSession.class.getName()).log(Level.SEVERE, null, ex);
//				}
//				manager.onJoinEvent(currentEvent.getChannel().getUsers().toArray());
//			}
//		};
//
//		new Thread(r1).start();
	}

	public void joinChannel(String chanName) {
		bot.sendIRC().joinChannel(chanName);
	}

	public void sendMessage(String chanName, String message) {
		bot.sendIRC().message(chanName, message);
		manager.messageReceived(chanName, message, bot.getNick());

	}

	@Override
	public void onConnect(ConnectEvent<PircBotX> event) throws Exception {
		super.onConnect(event); //To change body of generated methods, choose Tools | Templates.
	}

}
