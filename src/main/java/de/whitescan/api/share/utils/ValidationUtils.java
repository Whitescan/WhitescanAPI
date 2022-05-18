package de.whitescan.api.share.utils;

import org.apache.commons.lang3.StringUtils;

/**
 *
 * @author Whitescan
 *
 */
public class ValidationUtils {

	/**
	 * Check if string only contains whitelisted characters.
	 *
	 * @param input - the input string to validate
	 * @return if the input does not contain invalid characters
	 */
	public static boolean validateStringInput(String input) {

		if (StringUtils.isBlank(input))
			return false;

		final String whitelist = "[abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789°^!\\\"$%&/{}()[]=?\\\\@€+*~#<>|,;.:-_ ]";

		for (char c : input.toCharArray()) {
			if (whitelist.indexOf(c) == -1)
				return false;
		}

		return true;

	}

	/**
	 * Validate if string only contains whitelisted characters and is not exceeding the maximal length for this input.
	 *
	 * @param input     - the input string to validate
	 * @param maxLength - the maximum length of the input
	 * @return if the input does not contain invalid characters or exceeds the maximum length
	 */
	public static boolean validateStringInput(String input, int maxLength) {
		return validateStringInput(input) && input.length() <= maxLength;
	}

	/**
	 * Validate duration inputs with max length.
	 *
	 * @param input     - the duration input to validate
	 * @param maxLength - the maximal char length for the input
	 * @return if the input does not contains invalid characters or exceeds the maximum length
	 */
	public static boolean validateDurationInput(String input, int maxLength) {

		if (StringUtils.isBlank(input) || input.length() > maxLength)
			return false;

		final String whitelist = "0123456789smhdwMy";

		for (char c : input.toCharArray()) {
			if (whitelist.indexOf(c) == -1)
				return false;
		}

		return true;

	}

	/**
	 * Validate decimal inputs.
	 *
	 * @param input - the decimal input to validate
	 * @return if the input does not contain invalid characters
	 */
	public static boolean validateDecimalInput(String input) {

		if (StringUtils.isBlank(input))
			return false;

		final String characterWhitelist = "+-.";
		final String numberWhitelist = "0123456789";

		final long plusCounter = StringUtils.countMatches(input, '+');
		final long minusCounter = StringUtils.countMatches(input, '-');
		final long pointCounter = StringUtils.countMatches(input, '.');

		// Only one +, - and . allowed
		if (pointCounter > 1 || plusCounter > 1 || minusCounter > 1)
			return false;

		// Only either + or - allowed
		// Input has to start with a whitelisted char
		if ((plusCounter == 1 && minusCounter == 1) || ((characterWhitelist + numberWhitelist).indexOf(input.charAt(0)) == -1))
			return false;

		// Input should only contain whitelisted numbers at this point
		for (char c : input.toCharArray()) {

			if (c != '+' && c != '-' && c != '.') {
				if (numberWhitelist.indexOf(c) == -1)
					return false;
			}

		}

		return true;
	}

	/**
	 * Validate integer input strings.
	 *
	 * @param input - the integer input to validate.
	 * @return if the input does not contain invalid characters
	 */
	public static boolean validateIntegerInput(String input) {

		if (StringUtils.isBlank(input))
			return false;

		final String characterWhitelist = "+-.";
		final String numberWhitelist = "0123456789";

		final long plusCounter = StringUtils.countMatches(input, '+');
		final long minusCounter = StringUtils.countMatches(input, '-');

		// Only one + and - allowed
		// Only either + or - allowed
		if (plusCounter > 1 || minusCounter > 1 || (plusCounter == 1 && minusCounter == 1))
			return false;

		// Input has to start with a whitelisted char
		if ((characterWhitelist + numberWhitelist).indexOf(input.charAt(0)) == -1)
			return false;

		// Input should only contain whitelisted numbers at this point
		for (char c : input.toCharArray()) {

			if (c != '+' && c != '-' && c != '.') {
				if (numberWhitelist.indexOf(c) == -1)
					return false;
			}

		}

		return true;

	}

	/**
	 * Validate key input strings.
	 *
	 * @param input     - the key input to validate
	 * @param maxLength - the maximum length of the input allowed
	 * @return if the key does not contain or exceeds the maximum char length
	 */
	public static boolean validateKeyInput(String input, int maxLength) {

		if (StringUtils.isBlank(input) || input.length() > maxLength)
			return false;

		final String whitelist = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789üöäßẞ";

		for (char c : input.toCharArray()) {
			if (whitelist.indexOf(c) == -1)
				return false;

		}

		return true;

	}

}
