package de.whitescan.api.bungee;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
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

	@Getter
	private TextComponent noPermissionMessage;

	public BungeeCommand(String name, String permission, String[] aliases, TextComponent noPermissionMessage) {
		super(name, permission, aliases);
		this.noPermissionMessage = noPermissionMessage;
		setPermissionMessage(noPermissionMessage.getText());
	}

	public BungeeCommand(String name, String permission, TextComponent noPermissionMessage) {
		super(name, permission, new String[] {});
		this.noPermissionMessage = noPermissionMessage;
		setPermissionMessage(noPermissionMessage.getText());
	}

	@Override
	public void execute(CommandSender sender, String[] args) {

		if (hasPermission(sender)) {
			executeCommand(sender, args);

		} else {
			sender.sendMessage(getNoPermissionMessage());
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
