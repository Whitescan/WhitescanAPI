package de.whitescan.api.share;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ClickEvent.Action;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.chat.hover.content.Text;

/**
 *
 * @author Whitescan
 *
 */
public class AncientMessageService {

	/**
	 * Pattern for HEX codes
	 */
	private static final Pattern HEX_PATTERN = Pattern.compile("#[a-fA-F0-9]{6}");

	/**
	 * Message used when actor is laking the permission to access a command section
	 */
	public static final TextComponent NO_PERMISSION = createErrorMessage("You are not allowed to do that...");

	/**
	 * Message used when the actor must be a player
	 */
	public static final TextComponent PLAYER_ONLY = createErrorMessage("This feature is restricted to players...");

	/**
	 * Message used when the target player could not be found
	 */
	public static final TextComponent TARGET_NOT_FOUND = createErrorMessage("This player is unkown to this server...");

	/**
	 * Message used when the target player exists, but is not available to the
	 * sender
	 */
	public static final TextComponent TARGET_NOT_AVAILABLE = createErrorMessage(
			"This player is currently unavailable...");

	/**
	 * Message used when target is the actor
	 */
	public static final TextComponent TARGET_IS_SENDER = createErrorMessage("You cannot interact with yourself...");

	/**
	 * Message used when a feature is disabled
	 */
	public static final TextComponent FEATURE_DISABLED = createErrorMessage("This feature is currently unavailable...");

	public static final TextComponent LOCKED = new TextComponent(
			"§4A Systemerror occured.\n\n§cThe server is currently unable to process requests!\n\n§cPlease contact the discord support!");

	/**
	 * Method used when displaying custom messages
	 *
	 * @param message - The message to create
	 * @return <TextComponent>
	 */
	public static TextComponent createMessage(String message) {
		TextComponent textComponent = new TextComponent("§e" + message);
		return textComponent;
	}

	public static TextComponent createMessage(String message, ClickEvent.Action action, String clickText,
			String hoverText) {
		TextComponent textComponent = new TextComponent(message);
		textComponent.setClickEvent(new ClickEvent(action, clickText));
		textComponent.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new Text(hoverText)));
		return textComponent;
	}

	/**
	 * Method used when displaying a custom error message
	 *
	 * @param message - The message
	 * @return <TextComponent>
	 */
	public static TextComponent createErrorMessage(String message) {
		TextComponent textComponent = new TextComponent("§c" + message);
		return textComponent;
	}

	/**
	 * Method used when displaying a custom warning message
	 *
	 * @param message - The message
	 * @return <TextComponent>
	 */
	public static TextComponent createWarningMessage(String message) {
		TextComponent textComponent = new TextComponent("§c" + message);
		return textComponent;
	}

	/**
	 * Translate HEX codes in chat components.
	 *
	 * @param input
	 * @param formatColors - should format codes also be converted?
	 * @return formatted string
	 */
	public static String translateHexCodes(String input, boolean formatColors) {

		if (formatColors)
			input = ChatColor.translateAlternateColorCodes('&', input);

		Matcher matcher = HEX_PATTERN.matcher(input);

		while (matcher.find()) {
			String color = input.substring(matcher.start(), matcher.end());
			input = input.replace(color, ChatColor.of(color) + "");
			matcher = HEX_PATTERN.matcher(input);
		}

		return input;

	}

	/**
	 * Message used when an input string contains invalid characters
	 */
	public static TextComponent createInvalidStringInputMessage() {
		return createErrorMessage("This input only accepts characters of the english keyboard-layout!");
	}

	/**
	 * Message used when an input string contains invalid characters
	 */
	public static TextComponent createInvalidStringInputMessage(int size) {
		return createErrorMessage("This input only accepts characters of the english keyboard-layout and a maximum of "
				+ size + " characters!");
	}

	/**
	 * Message used when an input key contains invalid characters
	 */
	public static TextComponent createInvalidKeyInputMessage(int size) {
		return createErrorMessage(
				"This input only accepts non-special characters of the english keyboard-layout and a maximum of " + size
						+ " characters!");
	}

	/**
	 * Message used when input duration contains invalid characters
	 */
	public static TextComponent createInvalidDurationInputMessage() {
		return createErrorMessage("This input only accepts durations!");
	}

	public static TextComponent createInvalidDurationInputMessage(int size) {
		return createErrorMessage("This input only accepts durations with a size of " + size + " characters!");
	}

	/**
	 * Message used when input is not a decimal number
	 */
	public static TextComponent createInvalidDecimalInputMessage() {
		return createErrorMessage("This input only accepts decimal numbers!");
	}

	public static TextComponent createInvalidIntegerInputMessage() {
		return createErrorMessage("This input only accepts integers!");
	}

	/**
	 * Method used when displaying a syntax error message
	 *
	 * @param help - The help context
	 * @return <TextComponent>
	 */
	public static TextComponent createSyntaxErrorMessage(String help) {
		TextComponent textComponent = createErrorMessage("Wrong arguments, try §a" + help);
		textComponent.setClickEvent(new ClickEvent(Action.SUGGEST_COMMAND, help));
		return textComponent;
	}

	public static TextComponent createCommandTextComponent(String message, String command) {

		if (command.startsWith("/"))
			command = "/" + command;

		return createMessage(message, ClickEvent.Action.RUN_COMMAND, command, "§a§lClick here §eto run §c" + command);

	}

	public static TextComponent createUrlTextComponent(String message, String url) {
		return createMessage(message, ClickEvent.Action.OPEN_URL, url, "§a§lClick here §eto open URL §c" + url);
	}

}
