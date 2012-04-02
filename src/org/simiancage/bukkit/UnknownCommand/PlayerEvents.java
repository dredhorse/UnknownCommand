package com.LRFLEW.bukkit.UC;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

import org.bukkit.ChatColor;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerListener;

public class PlayerEvents extends PlayerListener {
	
	private final UnknownCommand plugin;
	private Set<String> commands;
	private ArrayList<String> UCmsg = null;
	private Random rand = new Random();
	
	public PlayerEvents(UnknownCommand instance) {
		plugin = instance;
	}
	
	public void setComs () {
		Set<String> temp = new HashSet<String>();
		File file = null;
		InputStream dflt = null;
		InputStreamReader dfltReader = null;
		FileReader fileReader = null;
		FileOutputStream newFile = null;
		BufferedReader bufferedReader = null;
		try {
			fileReader = new FileReader(plugin.getDataFolder() + File.separator + "Commands.txt");
			try {
				bufferedReader = new BufferedReader(fileReader);
				String line = null;
				while ((line = bufferedReader.readLine()) != null) {
					temp.add(line);
				}
				bufferedReader.close();
				fileReader.close();
			} catch (IOException e) {
				System.out.println("[UC] couldn't read Commands.txt.  Setting to default");
				temp.clear();
				dfltReader = new InputStreamReader(this.getClass().getClassLoader().getResourceAsStream("Commands.txt"));
				bufferedReader = new BufferedReader(dfltReader);
				String line = null;
				try {
					while ((line = bufferedReader.readLine()) != null) {
						temp.add(line);
					}
				} catch (IOException e1) {
					System.out.println("[UC] Cannot read default commands file.  This is a critical error.  " + 
							"Message user LRFLEW on bukkit.org if you see this");
				}
			} finally {
				try {
					fileReader.close();
				} catch (IOException e) {}
			}
		} catch (FileNotFoundException e) {
			System.out.println("[UC] couldn't find Commands.txt.  Creating a new file with default settings");
			file = plugin.getDataFolder();
			try {
				file.mkdir();
				file = new File(plugin.getDataFolder() + File.separator + "Commands.txt");
				file.createNewFile();
				newFile = new FileOutputStream(file);
				dflt = this.getClass().getClassLoader().getResourceAsStream("Commands.txt");
				byte[] buffer = new byte[dflt.available()];
				for (int i = 0; i != -1; i = dflt.read(buffer)) {
					newFile.write(buffer, 0, i);
				}
				temp.clear();
				dfltReader = new InputStreamReader(dflt);
				bufferedReader = new BufferedReader(dfltReader);
				String line = null;
				while ((line = bufferedReader.readLine()) != null) {
					temp.add(line);
				}
				dflt.close();
				dfltReader.close();
				bufferedReader.close();
			} catch (IOException e1) {
				System.out.println("[UC] cannot make new file.  " +
						"Either I don't have enough permissions to see the file or you need to change the permissions of the folder.  " +
						"Loading default settings...");
				dfltReader = new InputStreamReader(this.getClass().getClassLoader().getResourceAsStream("Commands.txt"));
				bufferedReader = new BufferedReader(dfltReader);
				String line = null;
				try {
					while ((line = bufferedReader.readLine()) != null) {
						temp.add(line);
					}
				} catch (IOException e2) {
					System.out.println("[UC] Cannot read default file.  This is a critical error.  " + 
							"Message user LRFLEW on bukkit.org if you see this");
				}
			}
			
		}
		this.commands = temp;
	}
	
	public void setMsg () {
		ArrayList<String> temp = new ArrayList<String>();
		
		File file = null;
		InputStream dflt = null;
		InputStreamReader dfltReader = null;
		FileReader fileReader = null;
		FileOutputStream newFile = null;
		BufferedReader bufferedReader = null;
		try {
			fileReader = new FileReader(plugin.getDataFolder() + File.separator + "Messages.txt");
			try {
				bufferedReader = new BufferedReader(fileReader);
				String line = null;
				while ((line = bufferedReader.readLine()) != null) {
					temp.add(colorize(line));
				}
				bufferedReader.close();
				fileReader.close();
			} catch (IOException e) {
				System.out.println("[UC] couldn't read Messages.txt.  Setting to default");
				temp.clear();
				dfltReader = new InputStreamReader(this.getClass().getClassLoader().getResourceAsStream("Messages.txt"));
				bufferedReader = new BufferedReader(dfltReader);
				String line = null;
				try {
					while ((line = bufferedReader.readLine()) != null) {
						temp.add(colorize(line));
					}
				} catch (IOException e1) {
					System.out.println("[UC] Cannot read default messages file.  This is a critical error.  " + 
							"Message user LRFLEW on bukkit.org if you see this");
				}
			} finally {
				try {
					fileReader.close();
				} catch (IOException e) {}
			}
		} catch (FileNotFoundException e) {
			System.out.println("[UC] couldn't find Messages.txt.  Creating a new file with default settings");
			file = plugin.getDataFolder();
			try {
				file.mkdir();
				file = new File(plugin.getDataFolder() + File.separator + "Messages.txt");
				file.createNewFile();
				newFile = new FileOutputStream(file);
				dflt = this.getClass().getClassLoader().getResourceAsStream("Messages.txt");
				byte[] buffer = new byte[dflt.available()];
				for (int i = 0; i != -1; i = dflt.read(buffer)) {
					newFile.write(buffer, 0, i);
				}
				temp.clear();
				dfltReader = new InputStreamReader(dflt);
				bufferedReader = new BufferedReader(dfltReader);
				String line = null;
				while ((line = bufferedReader.readLine()) != null) {
					temp.add(colorize(line));
				}
				dflt.close();
				dfltReader.close();
				bufferedReader.close();
			} catch (IOException e1) {
				System.out.println("[UC] cannot make new file.  " +
						"Either I don't have enough permissions to see the file or you need to change the permissions of the folder.  " +
						"Loading default settings...");
				dfltReader = new InputStreamReader(this.getClass().getClassLoader().getResourceAsStream("Commands.txt"));
				bufferedReader = new BufferedReader(dfltReader);
				String line = null;
				try {
					while ((line = bufferedReader.readLine()) != null) {
						temp.add(colorize(line));
					}
				} catch (IOException e2) {
					System.out.println("[UC] Cannot read default messages file.  This is a critical error.  " + 
							"Message user LRFLEW on bukkit.org if you see this");
				}
			}
			
		}
		
		UCmsg = temp;
	}
	
	public String colorize (String string) {
		return string.
		replace("\\\\", "\u0000").
		replace("\\g", "\u0007").
		replace("\\G", "\u0007").
		replace("\\", "\u00A7").
		replace("\u0000", "\\");
	}
	
	@Override
	public void onPlayerCommandPreprocess (PlayerCommandPreprocessEvent event) {
		String split = event.getMessage();
		if (split.charAt(0) == '/') split = split.replaceFirst("/", "");
		split = split.split(" ")[0];
		if ((plugin.getServer().getPluginCommand(split) == null) && !commands.contains(split)) {
			if (UCmsg == null || UCmsg.size() == 0) {
				event.getPlayer().sendRawMessage(ChatColor.RED + "Unknown Command");
			} else if (UCmsg.size() == 1) {
				event.getPlayer().sendRawMessage(UCmsg.get(0).replaceAll("\u0007", "/" + split));
			} else {
				event.getPlayer().sendRawMessage(UCmsg.get(rand.nextInt(UCmsg.size())).replace("\u0007", "/" + split));
			}
			event.setCancelled(true);
		}
	}
	
}
