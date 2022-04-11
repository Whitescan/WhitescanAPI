package de.whitescan.api.bungee;

import java.util.ArrayList;

import de.whitescan.api.share.MessageService;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;
import net.md_5.bungee.api.plugin.TabExecutor;

/**
 * 
 * @author Whitescan
 *
 */
public abstract class BungeeCommand extends Command implements TabExecutor {

	public BungeeCommand(String name, String permission, String[] aliases) {
		super(name, permission, aliases);
	}

	@Override
	public void execute(CommandSender sender, String[] args) {

		if (hasPermission(sender)) {
			executeCommand(sender, args);

		} else {
			sender.sendMessage(MessageService.NO_PERMISSION);
		}

	}

	protected abstract void executeCommand(CommandSender sender, String[] args);

	@Override
	public Iterable<String> onTabComplete(CommandSender sender, String[] args) {

		if (sender instanceof ProxiedPlayer && sender.hasPermission(getPermission())) {
			return tabComplete(sender, args);
		}

		return new ArrayList<String>();

	}

	protected abstract Iterable<String> tabComplete(CommandSender sender, String[] args);

}
