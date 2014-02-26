package de.JeterLP.MakeYourOwnCommands.utils;

import de.JeterLP.MakeYourOwnCommands.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

/**
 * @author TheJeterLP
 * @deprecated use CommandManager class.
 */
@Deprecated
public class CommandUtils {

        private final FileConfiguration config = Main.getInstance().getConfig();
        private final SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss");

        /**
         * @deprecated Use CommandManager class.
         */
        @Deprecated
        public String replaceValues(String message, Player player, String[] args) {
                message = message.replaceAll("%sender%", player.getName())
                                .replaceAll("%realtime%", format.format(new Date()))
                                .replaceAll("%onlineplayers%", String.valueOf(Bukkit.getOnlinePlayers().length))
                                .replaceAll("%world%", player.getWorld().getName())
                                .replace("%cmd%", message);
                String names = "";
                for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {
                        if (names.isEmpty() || names.equalsIgnoreCase("")) {
                                names = onlinePlayer.getName();
                        } else {
                                names = names + "\n" + onlinePlayer.getName();
                        }
                }
                message = message.replace("%online", names);
                int length = args.length;
                for (int i = 1; i < length; i++) {
                        String arg = args[i];
                        message = message.replaceAll("%", "");
                        message = message.replaceAll("arg" + i, arg);
                }
                message = message.replaceAll("&((?i)[0-9a-fk-or])", "§$1");
                return message;
        }

        /**
         * @deprecated Use CommandManager class.
         */
        @Deprecated
        public String getNoPermissionMessage(Player player) {
                String msg = config.getString("NoPermissionMessage");
                msg = msg.replaceAll("&((?i)[0-9a-fk-or])", "§$1");
                msg = msg.replaceAll("%player%", player.getName());
                return msg;
        }

        /**
         * @deprecated Use CommandManager class.
         */
        @Deprecated
        public boolean isRegistered(String command) {
                for (String commands : config.getConfigurationSection("Commands").getKeys(false)) {
                        if (commands.equalsIgnoreCase(command)) {
                                return true;
                        }
                }
                return false;
        }

        /**
         * @deprecated Use CommandManager class.
         */
        @Deprecated
        public String getCommandIsBlockedMessage(Player player, String world, String command) {
                String msg = config.getString("CommandIsBlocked");
                msg = msg.replaceAll("&((?i)[0-9a-fk-or])", "§$1");
                msg = msg.replaceAll("%player%", player.getName());
                msg = msg.replaceAll("%cmd%", command);
                msg = msg.replaceAll("%world%", world);
                return msg;
        }

        /**
         * @deprecated use CommandManager class.
         */
        @Deprecated
        public boolean isBlocked(String command, String world) {
                List<String> blocked = config.getStringList("BlockedWorlds." + world);
                return blocked != null && blocked.contains(command);
        }
}
