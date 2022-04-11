package de.whitescan.api.share;

import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import java.util.UUID;

/**
 *
 * @author Whitescan
 *
 */
public class CooldownManager {

	/**
	 * Containing all unique id's that are on cooldown
	 */
	private final Map<UUID, Long> cooldowns = new HashMap<>();

	/**
	 * The java.util.Timer to cleanup cooldowns.
	 */
	private final Timer timer = new Timer();

	/**
	 * The amount of seconds until the entry gets cleaned.
	 */
	private final long seconds;

	public CooldownManager(long seconds) {
		this.seconds = seconds;
	}

	/**
	 * Get the cooldown of the provided uuid.
	 *
	 * @param uniqueId - the target uuid
	 * @return the cooldown of the uuid or 0 if there is no cooldown
	 */
	public long getCooldown(UUID uniqueId) {
		return cooldowns.getOrDefault(uniqueId, (long) 0);
	}

	/**
	 * Add a cooldown to the provided uuid.
	 *
	 * @param uniqueId - the target uuid
	 */
	public void setCooldown(UUID uniqueId) {

		cooldowns.put(uniqueId, System.currentTimeMillis());

		timer.schedule(new TimerTask() {

			@Override
			public void run() {
				cooldowns.remove(uniqueId);
			}

		}, seconds * 1000);

	}

	/**
	 * Check if the entry is on cooldown
	 *
	 * @param uniqueId - the target uuid
	 * @return true if the entry has a cooldown, false if not
	 */
	public boolean hasCooldown(UUID uniqueId) {
		return getCooldown(uniqueId) > 0;
	}

	/**
	 * Get the time that's left of the cooldown for display purposes.
	 *
	 * @param uniqueId - the target uuid
	 * @return the time left (in millis) until the cooldown is up.
	 */
	public long getTimeLeft(UUID uniqueId) {
		return System.currentTimeMillis() - getCooldown(uniqueId);
	}

}
