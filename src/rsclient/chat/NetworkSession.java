/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rsclient.chat;

import java.io.IOException;
import java.util.ArrayList;
import logic.IrcManager;
import org.pircbotx.Configuration;
import org.pircbotx.PircBotX;
import org.pircbotx.exception.IrcException;
import org.pircbotx.hooks.ListenerAdapter;
import org.pircbotx.hooks.events.ConnectEvent;
import org.pircbotx.hooks.events.JoinEvent;
import org.pircbotx.hooks.events.MessageEvent;
import org.pircbotx.hooks.events.PrivateMessageEvent;

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
		manager.onJoinEvent(event.getChannel().getUsers().toArray());

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
