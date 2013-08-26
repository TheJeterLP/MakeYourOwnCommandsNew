package de.JeterLP.MakeYourOwnCommands.commands;

import de.JeterLP.MakeYourOwnCommands.Main;
import java.util.List;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

/**
 *
 * @author JeterLP
 */
public class myoc implements CommandExecutor {

    private Main main;

    public myoc(Main main) {
        this.main = main;
    }

    /**
     * <p>This method reloads the config if /myoc reload was typed</p>
     *
     * @param sender
     * @param cmd
     * @param commandlabel
     * @param args
     * @return
     */
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String commandlabel, String[] args) {
        if (cmd.getName().equalsIgnoreCase("myoc")) {
            if (args.length == 0) {
                sender.sendMessage("§e " + main.prefix + "by §aJeterLP §eversion: §c" + main.getDescription().getVersion());
                return true;
            }
            if (args[0].equalsIgnoreCase("reload")) {
                if (sender.hasPermission("myoc.reload") || sender.hasPermission("myoc.*")) {
                    main.reloadConfig();
                    sender.sendMessage("§aSuccesfully reloaded!");
                    return true;
                } else {
                    sender.sendMessage("§4You dont have permission!");
                    return true;
                }
            } else if (args[0].equalsIgnoreCase("list")) {
                if (sender.hasPermission("myoc.list") || sender.hasPermission("myoc.*")) {
                    sender.sendMessage("§eMakeYourOwnCommands: §cCommands:");
                    for (String commands : main.getConfig().getConfigurationSection("commands").getKeys(false)) {
                        List<String> messages = main.getConfig().getStringList("commands." + commands + ".messages");
                        sender.sendMessage("§a" + commands + ":");
                        for (int i = 0; i < messages.size(); i++) {
                            sender.sendMessage("  " + messages.get(i).replaceAll("&((?i)[0-9a-fk-or])", "§$1"));
                            
                        }
                    }
                    sender.sendMessage("§eMakeYourOwnCommands: §cAliases:");
                    for (String alias : main.getConfig().getConfigurationSection("aliases").getKeys(false)) {
                        sender.sendMessage("§a" + alias + ":");
                        sender.sendMessage("  §e" + main.getConfig().getString("aliases." + alias + ".execute"));
                    }
                    sender.sendMessage("§eMakeYourOwnCommands: §cTeleportations:");
                    for (String tpcmd : main.getConfig().getConfigurationSection("Teleportations").getKeys(false)) {
                        sender.sendMessage("§a" + tpcmd);
                    }
                    return true;
                } else {
                    sender.sendMessage("§4You dont have permission!");
                    return true;
                }
            } else {
                sender.sendMessage("§cUsage: §e/myoc <reload|list>");
                return true;
            }
        }
        return false;
    }
}