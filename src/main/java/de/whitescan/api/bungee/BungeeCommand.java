package de.whitescan.api.bungee;

import java.util.ArrayList;
import java.util.List;

import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;
import net.md_5.bungee.api.plugin.TabExecutor;

/**
 *
 * @author Whitescan
 *
 */
public abstract class BungeeCommand extends Command implements TabExecutor {

	public BungeeCommand(String name, String permission, String[] aliases, String permissionMessage) {
		super(name, permission, aliases);
		setPermissionMessage(permissionMessage);
	}

	public BungeeCommand(String name, String permission, String noPermissionMessage) {
		super(name, permission, new String[] {});
		setPermissionMessage(noPermissionMessage);
	}

	@Override
	public void execute(CommandSender sender, String[] args) {

		if (hasPermission(sender)) {
			executeCommand(sender, args);

		} else {
			sender.sendMessage(new TextComponent(getPermissionMessage()));
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

	protected abstract List<String> tabComplete(ProxiedPlayer actor, String[] args);

}
