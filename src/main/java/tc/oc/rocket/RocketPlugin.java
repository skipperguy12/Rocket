package tc.oc.rocket;

import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import com.google.common.collect.Lists;

public final class RocketPlugin extends JavaPlugin {
    public final List<Rocket> rockets = Lists.newArrayList();
    public boolean ENABLED = true;

    private static RocketPlugin i;

    public void onLoad() {
        RocketPlugin.i = this;
    }

    public static RocketPlugin get() {
        return RocketPlugin.i;
    }

    @Override
    public void onDisable() {
    }

    @Override
    public void onEnable() {
    	this.getServer().getPluginManager().registerEvents(new RocketListener(this), this);
    	this.getServer().getScheduler().scheduleSyncRepeatingTask(this, new RocketTask(this), 0, RocketConfig.TICK_UPDATE_DELAY);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(!sender.hasPermission(RocketConfig.ADMIN_PERMISSION)) {
            sender.sendMessage(ChatColor.RED + "No permission");
            return true;
        }

        if(args.length == 0) {
            sender.sendMessage(ChatColor.RED + "Usage: /rocket [toggle|reset]");
            return true;
        }

        if(args[0].equals("reset")) {
            for(World world : Bukkit.getWorlds()) {
                for(Player player1 : world.getPlayers()) {
                    for(Player player2 : world.getPlayers()) {
                        if(player1 == player2) continue;
                        player1.showPlayer(player2);
                    }
                }
            }

            sender.sendMessage(ChatColor.GREEN + "Rockets reset");
        }

        if(args[0].equals("toggle")) {
            this.ENABLED = !this.ENABLED;

            sender.sendMessage(ChatColor.GOLD + "Rockets have been " + (this.ENABLED ? ChatColor.GREEN : ChatColor.RED) + (this.ENABLED ? "enabled" : "disabled"));
        }

        return true;
    }
}
