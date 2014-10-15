package rsloader;

import java.applet.Applet;
import java.applet.AppletContext;
import java.applet.AppletStub;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import rsreflection.Reflector;

/***
 *@author Xel
 *@version 1.0
 *@project RSLoader
 *@file Loader.java
 *@date 18.10.2013
 *@time 10.43.48
 */
public class Loader implements AppletStub{
	//insane declarations
	public enum Game{OSRS, RS3, CLASSIC};
	public static final HashMap<String, String> Parameters = new HashMap<String, String>();
	public Game game;
	public URL GamePack;
	public Applet applet;
	public String gameUrl;
	public String MClass;
	public Reflector loader;
        
	public Loader(Game g)
	{
		game = g;
		if(game == Game.OSRS)
			gameUrl = "http://oldschool69.runescape.com/";
		else if(game == Game.RS3)
			gameUrl = "http://world1.runescape.com/";
		else
			gameUrl = "http://classic2.runescape.com/plugin.js?param=o0,a0,s0";
		
		try {
			GetParams(new URL(gameUrl));
                        loader  = new Reflector(new URL[]{GamePack});
			applet = (Applet)loader.loadClass(MClass).newInstance();
			applet.setStub(this);
			applet.init();
			applet.start();
		} catch (IOException | InstantiationException | IllegalAccessException | ClassNotFoundException e1) {
			e1.printStackTrace();
		}
	}
	
	public void GetParams(URL url) throws IOException
	{
		BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream()));
		String line;
		List<String> params = new ArrayList<String>();
		while((line = reader.readLine()) != null)
		{
			if(line.contains("param name"))
				params.add(line);
			if(GamePack == null) //retarted block
				if(line.contains("archive"))
					if(game != Game.CLASSIC)
						GamePack = new URL(gameUrl + line.substring(line.indexOf("archive=") + 8, line.indexOf(" ');")));
					else
						GamePack = new URL("http://classic2.runescape.com/" + line.substring(line.indexOf("archive=") + 8, line.indexOf(" code")));
			if(MClass == null)
				if(line.contains("code="))
                                                
						MClass = line.substring(line.indexOf("code=") + 5, line.indexOf(".class"));
		}
		reader.close();
		
		for(String s : params)
		{
			Parameters.put(GetParamName(s), GetParamValue(s));
		}
	}
	
	public String GetParamName(String param)
	{
		try{
			int StartIndex = param.indexOf("<param name=\"") + 13;
			int EndIndex = param.indexOf("\" value");
			return param.substring(StartIndex, EndIndex);
		}catch(StringIndexOutOfBoundsException e)//classic handles some differently so why not just catch it =P
		{
			int StartIndex = param.indexOf("<param name=") + 12;
			int EndIndex = param.indexOf(" value");
			return param.substring(StartIndex, EndIndex);
		}
	}
	
	public String GetParamValue(String param)
	{
		try{
			int StartIndex = param.indexOf("value=\"") + 7;
			int EndIndex = param.indexOf("\">');");
			return param.substring(StartIndex, EndIndex);
		}catch(StringIndexOutOfBoundsException e)//and again :D
		{
			int StartIndex = param.indexOf("value=") + 6;
			int EndIndex = param.indexOf(">');");
			return param.substring(StartIndex, EndIndex);
		}
	}

	@Override
	public void appletResize(int arg0, int arg1) {
	}

	@Override
	public AppletContext getAppletContext() {
		return null;
	}

	@Override
	public URL getCodeBase() {
		try
		{
			if(game == Game.OSRS)
				return new URL("http://oldschool1.runescape.com/");
			else if(game == Game.RS3)
				return new URL("http://world1.runescape.com/");
			else
				return new URL("http://classic2.runescape.com/");
		}catch(MalformedURLException e)
		{
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public URL getDocumentBase() {
		try
		{
			if(game == Game.OSRS)
				return new URL("http://oldschool1.runescape.com/");
			else if(game == Game.RS3)
				return new URL("http://world1.runescape.com/");
			else
				return new URL("http://classic2.runescape.com/");
		}catch(MalformedURLException e)
		{
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public String getParameter(String arg0) {
		return Parameters.get(arg0);
	}

	@Override
	public boolean isActive() {
		return false;
	}
	
	
}
