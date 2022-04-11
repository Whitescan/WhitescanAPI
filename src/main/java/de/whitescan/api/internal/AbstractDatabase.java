package de.whitescan.api.internal;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.logging.Logger;

import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

/**
 *
 * @author Whitescan
 *
 */
@RequiredArgsConstructor
public abstract class AbstractDatabase {

	@NonNull
	protected Logger logger;

	@NonNull
	protected String database;

	@Getter
	@NonNull
	protected String prefix;

	protected Connection connection;

	protected abstract void connect();

	public void close() {

		try {

			if (connection != null) {
				connection.close();
				logger.info("Connection to database: " + database + " was closed.");
			}

		} catch (SQLException e) {
			logger.warning("Connection to database: " + database + " could not close!");
			e.printStackTrace();
		}

	}

	public Connection getConnection() {

		try {
			if (connection == null || connection.isClosed()) {
				connect();
			}
		} catch (SQLException e) {
			logger.severe("Could not retreive a connection from database: " + database + "! This is most likely a follow up error.");
			e.printStackTrace();
		}

		return connection;
	}

}
