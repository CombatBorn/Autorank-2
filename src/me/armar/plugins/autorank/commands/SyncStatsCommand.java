package me.armar.plugins.autorank.commands;

import me.armar.plugins.autorank.Autorank;

import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class SyncStatsCommand implements CommandExecutor {

	private final Autorank plugin;

	public SyncStatsCommand(final Autorank instance) {
		plugin = instance;
	}

	@Override
	public boolean onCommand(final CommandSender sender, final Command cmd,
			final String label, final String[] args) {

		if (!plugin.getCommandsManager().hasPermission("autorank.syncstats",
				sender))
			return true;

		if (!plugin.getHookedStatsPlugin().isEnabled()) {
			sender.sendMessage(ChatColor.RED + "Stats is not enabled!");
			return true;
		}

		int count = 0;

		// Sync playtime of every player
		for (final String entry : plugin.getPlaytimes().getKeys()) {

			final OfflinePlayer p = plugin.getServer().getOfflinePlayer(entry);
			
			// Time is stored in seconds
			final int statsPlayTime = plugin.getHookedStatsPlugin()
					.getNormalStat("time_played",
							p.getName(), null);

			if (statsPlayTime <= 0) {
				continue;
			}

			// Update time
			plugin.getPlaytimes().setLocalTime(entry, Math.round(statsPlayTime / 60));

			// Increment count
			count++;
		}

		if (count == 0) {
			sender.sendMessage(ChatColor.GREEN
					+ "Could not sync stats. Run command again!");
		} else {
			sender.sendMessage(ChatColor.GREEN
					+ "Time has succesfully been updated for all entries.");
		}
		return true;
	}

}
