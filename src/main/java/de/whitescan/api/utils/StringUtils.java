package de.whitescan.api.utils;

import java.util.List;
import java.util.stream.Collectors;

/**
 *
 * @author Whitescan
 *
 */
public class StringUtils {

	/**
	 * Used to join SharSequenzes with a range.
	 *
	 * @param array      the array of values to join together, may be null
	 * @param separator  the separator character to use, null treated as ""
	 * @param startIndex the first index to start joining from. It is an error to
	 *                   pass in an end index past the end of the array
	 * @param endIndex   the index to stop joining from (exclusive). It is an error
	 *                   to pass in an end index past the end of the array
	 * @return the joined String, <code>null</code> if null array input
	 */
	public static String join(Object[] array, String separator, int startIndex, int endIndex) {

		if (array == null)
			return null;

		if (separator == null)
			separator = "";

		int bufSize = (endIndex - startIndex);

		if (bufSize <= 0)
			return "";

		bufSize *= ((array[startIndex] == null ? 16 : array[startIndex].toString().length()) + separator.length());

		StringBuffer buf = new StringBuffer(bufSize);

		for (int i = startIndex; i < endIndex; i++) {

			if (i > startIndex)
				buf.append(separator);

			if (array[i] != null)
				buf.append(array[i]);

		}

		return buf.toString();

	}

	/**
	 * Get all matching entries of a string list.
	 *
	 * @param list    - the input list
	 * @param pattern - the input pattern
	 * @return all entries that match the pattern
	 */
	public static List<String> getMatchingEntries(List<String> list, String pattern) {
		return list.stream().filter(entry -> entry.startsWith(pattern)).collect(Collectors.toList());
	}

}
