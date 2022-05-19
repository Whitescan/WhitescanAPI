package de.whitescan.api.utils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.concurrent.TimeUnit;

/**
 *
 * @author Whitescan
 *
 */
public class DateTimeUtils {

	public static final DateTimeFormatter ENGLISH_FORMAT = DateTimeFormatter.ofPattern("MM.dd.yyyy HH:mm:ss");

	/**
	 * Used when trying to display a large amount of milliseconds.
	 *
	 * @param time - The milliseconds to display
	 * @return a human readable representation of milliseconds
	 */
	public static String getHumanReadableFromMillis(long time) {

		long hours = TimeUnit.MILLISECONDS.toHours(time);
		long minutes = TimeUnit.MILLISECONDS.toMinutes(time);
		long seconds = TimeUnit.MILLISECONDS.toSeconds(time);

		String display = "";

		if (hours > 0) {
			display = display + hours + " hours ";
		} else if (minutes > 0) {
			display = display + minutes + " minutes ";
		} else if (seconds > 0) {
			display = display + seconds + " seconds ";
		}

		return display.trim();

	}

	/**
	 * Get a displayable string of the {@link java.time.LocalDateTime} object.
	 *
	 * @param input - the input
	 * @return a readable and displayable string
	 */
	public static String getDateTimeDisplay(LocalDateTime input) {

		LocalDateTime tempDateTime = LocalDateTime.from(input);
		LocalDateTime now = LocalDateTime.now();

		long years = tempDateTime.until(now, ChronoUnit.YEARS);
		tempDateTime = tempDateTime.plusYears(years);

		long months = tempDateTime.until(now, ChronoUnit.MONTHS);
		tempDateTime = tempDateTime.plusMonths(months);

		long days = tempDateTime.until(now, ChronoUnit.DAYS);
		tempDateTime = tempDateTime.plusDays(days);

		long hours = tempDateTime.until(now, ChronoUnit.HOURS);
		tempDateTime = tempDateTime.plusHours(hours);

		long minutes = tempDateTime.until(now, ChronoUnit.MINUTES);
		tempDateTime = tempDateTime.plusMinutes(minutes);

		long seconds = tempDateTime.until(now, ChronoUnit.SECONDS);
		tempDateTime = tempDateTime.plusSeconds(seconds);

		String display = "";

		if (years > 0) {
			display = years + " years ";
		}

		if (months > 0) {
			display = display + months + " months ";
		}

		if (days > 0) {
			display = display + days + " days ";
		}

		if (hours > 0) {
			display = display + hours + " hours ";
		}

		if (minutes > 0) {
			display = display + minutes + " minutes ";
		}

		if (seconds > 0) {
			display = display + seconds + " seconds ";
		}

		return display;

	}

	public static long getSecondDuration(LocalDateTime dateTime) {
		long h = dateTime.getHour();
		long m = dateTime.getMinute();
		long s = dateTime.getSecond();
		return (h * 3600) + (m * 60) + s;
	}

}
