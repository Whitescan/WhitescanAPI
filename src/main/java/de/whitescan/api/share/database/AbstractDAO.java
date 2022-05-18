package de.whitescan.api.share.database;

import java.util.logging.Logger;

/**
 *
 * @author Whitescan
 *
 */
public abstract class AbstractDAO {

	protected Logger logger;
	protected AbstractDatabase database;

	public AbstractDAO(Logger logger, AbstractDatabase database) {
		this.logger = logger;
		this.database = database;
		checkTables();
	}

	public abstract void checkTables();

}
