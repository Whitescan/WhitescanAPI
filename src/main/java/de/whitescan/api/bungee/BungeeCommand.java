package de.whitescan.api.bungee;

import java.util.ArrayList;

import de.whitescan.api.share.AncientMessageService;
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

	public BungeeCommand(String name, String permission) {
		super(name, permission, new String[] {});
	}

	@Override
	public void execute(CommandSender sender, String[] args) {

		if (hasPermission(sender)) {
			executeCommand(sender, args);

		} else {
			sender.sendMessage(AncientMessageService.NO_PERMISSION);
		}

	}

	protected abstract void executeCommand(CommandSender sender, String[] args);

	@Override
	public Iterable<String> onTabComplete(CommandSender sender, String[] args) {

		if (sender instanceof ProxiedPlayer actor && hasPermission(sender)) {
			return tabComplete(actor, args);
		}

		return new ArrayList<>();

	}

	protected abstract Iterable<String> tabComplete(ProxiedPlayer actor, String[] args);

}
