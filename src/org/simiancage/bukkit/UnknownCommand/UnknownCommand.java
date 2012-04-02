package org.simiancage.bukkit.UnknownCommand;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public class UnknownCommand extends JavaPlugin {
	private final PlayerEvents playerListener = new PlayerEvents(this);

	public void onDisable() {

        // NOTE: All registered events are automatically unregistered when a plugin is disabled
		PluginDescriptionFile pdfFile = this.getDescription();
		System.out.println( pdfFile.getName() + " says Goodbye!" );
    }

    public void onEnable() {

    	playerListener.setComs();
    	playerListener.setMsg();

    	// Register our events
        PluginManager pm = getServer().getPluginManager();
        pm.registerEvents(playerListener, this);

        // EXAMPLE: Custom code, here we just output some info so we can check all is well
        PluginDescriptionFile pdfFile = this.getDescription();
        System.out.println( pdfFile.getName() + " version " + pdfFile.getVersion() + " is enabled!" );
    }

    @Override
	public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
    	if (cmd.getName().equals("uc")) {
    		if (args.length > 0 && args[0].equalsIgnoreCase("reload")) {
    			if (sender instanceof Player) {
    				Player player = (Player)sender;
    				if (!player.isOp()) {
                        player.sendMessage("[UC] You are not OP!");
    					return false;
    				}
    				player.sendMessage("[UC] Commands and Messages reset");
    			}
    			playerListener.setComs();
    			playerListener.setMsg();
    			System.out.println("[UC] Commands and Messages reset");
    			return true;
    		}
            sender.sendMessage("[UC] You need to use /uc reload to reload the config!");
            return true;
    	}
		return false;
	}

}
