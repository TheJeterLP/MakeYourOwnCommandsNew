package de.JeterLP.MakeYourOwnCommands;

import de.JeterLP.MakeYourOwnCommands.Command.CommandManager;
import java.util.List;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 *
 * @author JeterLP
 */
public class MyocCommand implements CommandExecutor {
        
        private final Main main = Main.getInstance();

        /**
         * Executes the /myoc command.
         * @param sender
         * @param cmd
         * @param commandlabel
         * @param args
         * @return true: if the command was successfully executed
         */
        @Override
        public boolean onCommand(CommandSender sender, Command cmd, String commandlabel, String[] args) {
                if (cmd.getName().equalsIgnoreCase("myoc")) {
                        if (args.length != 1) return false;
                        
                        if (args[0].equalsIgnoreCase("reload")) {
                                if (sender.hasPermission("myoc.reload") || sender.hasPermission("myoc.*")) {
                                        Bukkit.getServer().getPluginManager().disablePlugin(Main.getInstance());
                                        Bukkit.getServer().getPluginManager().enablePlugin(Main.getInstance());
                                        sender.sendMessage("§aSuccesfully reloaded!");
                                        return true;
                                } else {
                                        sender.sendMessage("§4You dont have permission!");
                                        return true;
                                }
                        } else if (args[0].equalsIgnoreCase("list")) {
                                if (sender.hasPermission("myoc.list") || sender.hasPermission("myoc.*")) {
                                        sender.sendMessage("§5Commands:");
                                        for (de.JeterLP.MakeYourOwnCommands.Command.Command command : CommandManager.getCommands()) {
                                                sender.sendMessage("    §a" + command.getCommand());
                                        }
                                        return true;
                                } else {
                                        sender.sendMessage("§4You dont have permission!");
                                        return true;
                                }
                        } else {
                                return false;
                        }
                }
                return false;
        }
}