package me.armar.plugins.autorank.commands;

import me.armar.plugins.autorank.Autorank;
import me.armar.plugins.autorank.AutorankTools;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ForceCheckCommand implements CommandExecutor {

	private final Autorank plugin;

	public ForceCheckCommand(final Autorank instance) {
		plugin = instance;
	}

	@Override
	public boolean onCommand(final CommandSender sender, final Command cmd,
			final String label, final String[] args) {

		if (!plugin.getCommandsManager().hasPermission("autorank.forcecheck",
				sender))
			return true;

		if (args.length != 2) {
			sender.sendMessage(ChatColor.RED + "Incorrect command usage!");
			sender.sendMessage(ChatColor.YELLOW
					+ "Usage: /ar forcecheck <player>");
			return true;
		}

		final String target = args[1];
		final Player targetPlayer = plugin.getServer().getPlayer(target);

		if (targetPlayer == null) {
			sender.sendMessage(ChatColor.RED + "Player " + target
					+ " is not online!");
			return true;
		}

		if (AutorankTools.isExcluded(targetPlayer)) {
			sender.sendMessage(ChatColor.RED
					+ "This player is excluded from ranking!");
			return true;
		}

		// Check the player
		plugin.getPlayerChecker().checkPlayer(targetPlayer);

		// Let checker know that we checked.
		sender.sendMessage(ChatColor.GREEN + targetPlayer.getName()
				+ " checked!");

		return true;
	}

}
