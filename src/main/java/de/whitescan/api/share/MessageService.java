package de.whitescan.api.share;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ClickEvent.Action;
import net.md_5.bungee.api.chat.TextComponent;

/**
 *
 * @author Whitescan
 *
 */
public class MessageService {

	/**
	 * Pattern for HEX codes
	 */
	public static final Pattern HEX_PATTERN = Pattern.compile("#[a-fA-F0-9]{6}");

	/**
	 * Root Prefix
	 */
	private static final String PROXY_PREFIX = "§4§lE§8§ln§4§ld§8§lu§4§lr§8§li§4§lo§8§ln §f§l» §e";

	/**
	 * Prefix for errors
	 */
	private static final String ERROR_PREFIX = PROXY_PREFIX + "§4Error! §c";

	/**
	 * Prefix for warnings
	 */
	private static final String WARNING_PREFIX = PROXY_PREFIX + "§4Warning! §c";

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
	 * Message used when the target player exists, but is not available to the sender
	 */
	public static final TextComponent TARGET_UNAVAILABLE = createErrorMessage("This player is currently unavailable...");

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
		TextComponent textComponent = new TextComponent(PROXY_PREFIX + message);
		return textComponent;
	}

	/**
	 * Method used when displaying a custom error message
	 *
	 * @param message - The message
	 * @return <TextComponent>
	 */
	public static TextComponent createErrorMessage(String message) {
		TextComponent textComponent = new TextComponent(ERROR_PREFIX + message);
		return textComponent;
	}

	/**
	 * Method used when displaying a custom warning message
	 *
	 * @param message - The message
	 * @return <TextComponent>
	 */
	public static TextComponent createWarningMessage(String message) {
		TextComponent textComponent = new TextComponent(WARNING_PREFIX + message);
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
		return createErrorMessage("Diese Eingabe lässt nur Symbole des deutschen Tastatur-Layouts zu!");
	}

	/**
	 * Message used when an input string contains invalid characters
	 */
	public static TextComponent createInvalidStringInputMessage(int size) {
		return createErrorMessage("Diese Eingabe lässt nur Symbole des deutschen Tastatur-Layouts mit maximal " + size + " Zeichen zu!");
	}

	/**
	 * Message used when an input key contains invalid characters
	 */
	public static TextComponent createInvalidKeyInputMessage(int size) {
		return createErrorMessage("Diese Eingabe lässt nur Buchstaben(a-Z, üöäßẞ) und Zahlen(0-9) mit maximal " + size + " Zeichen zu!");
	}

	/**
	 * Message used when input duration contains invalid characters
	 */
	public static TextComponent createInvalidDurationInputMessage() {
		return createErrorMessage("This input only accepts durations!");
	}

	/**
	 * Message used when input is not a decimal number
	 */
	public static TextComponent createInvalidDecimalInputMessage() {
		return createErrorMessage("This input only accepts decimal numbers!");
	}

	public static BaseComponent createInvalidIntegerInputMessage() {
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

}
