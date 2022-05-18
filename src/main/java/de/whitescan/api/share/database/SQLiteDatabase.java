package de.whitescan.api.share.database;

import java.sql.DriverManager;
import java.util.logging.Logger;

import de.whitescan.api.internal.AbstractDatabase;

/**
 *
 * @author Whitescan
 *
 */
public class SQLiteDatabase extends AbstractDatabase {

	public SQLiteDatabase(Logger logger, String database, String prefix) {
		super(logger, database, prefix);
		connect();
	}

	@Override
	protected void connect() {

		try {

			this.connection = DriverManager.getConnection("jdbc:sqlite:" + database + ".db");
			logger.info("Connection to database: " + database + " was successful!");

		} catch (Exception e) {
			logger.severe("Connection to database: " + database + " failed! Please check credentials before reporting this as an issue...");
			e.printStackTrace();
		}

	}

}
