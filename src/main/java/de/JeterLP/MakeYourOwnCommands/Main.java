package de.JeterLP.MakeYourOwnCommands;

import de.JeterLP.MakeYourOwnCommands.Events.CommandListener;
import de.JeterLP.MakeYourOwnCommands.commands.myoc;
import de.JeterLP.MakeYourOwnCommands.utils.ConfigFile;
import de.JeterLP.MakeYourOwnCommands.utils.MYOClogger;
import de.JeterLP.MakeYourOwnCommands.utils.MetricsChecker;
import de.JeterLP.MakeYourOwnCommands.utils.Updatechecker;
import net.milkbowl.vault.permission.Permission;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * @author JeterLP
 */
public class Main extends JavaPlugin {

    public final String prefix = "[MakeYourOwnCommands] ";
    private ConfigFile loader = null;
    private Updatechecker checker = null;
    private MetricsChecker mchecker = null;
    public FileConfiguration config;
    public static Permission perms = null;
    public boolean useVault = false;

    /**
     * This method loads the plugin
     */
    @Override
    public void onEnable() {
        MYOClogger.log(MYOClogger.Type.INFO, "(by JeterLP" + " Version: " + getDescription().getVersion() + ") loading...");
        loader = new ConfigFile(this);
        loader.loadConfig();
        if (this.checkVault()) {
            setupPermissions();
            this.useVault = true;
            MYOClogger.log(MYOClogger.Type.INFO, "Vault was found! Successfully hooked into: " + perms.getName());
        } else {
            MYOClogger.log(MYOClogger.Type.INFO, "Vault was not found! Vault-support is disabled...");
        }
        checker = new Updatechecker(this);
        checker.checkUpdate();
        mchecker = new MetricsChecker(this);
        mchecker.checkMetrics();
        getCommand("myoc").setExecutor(new myoc(this));
        getServer().getPluginManager().registerEvents(new CommandListener(this), this);
        MYOClogger.log(MYOClogger.Type.INFO, "(by JeterLP" + " Version: " + getDescription().getVersion() + ") is now enabled.");
    }

    /**
     * This method logs that the plugin was disabled
     */
    @Override
    public void onDisable() {
        MYOClogger.log(MYOClogger.Type.INFO, "(by JeterLP" + " Version: " + getDescription().getVersion() + ") is now disabled.");
    }

    private boolean setupPermissions() {
        RegisteredServiceProvider<Permission> rsp = getServer().getServicesManager().getRegistration(Permission.class);
        perms = rsp.getProvider();
        return perms != null;
    }

    public boolean checkVault() {
        Plugin plugin = Bukkit.getServer().getPluginManager().getPlugin("Vault");
        if (plugin == null) {
            return false;
        }
        return true;
    }
}