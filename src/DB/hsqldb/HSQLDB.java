package DB.hsqldb;

import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import Interface.CryptWithMD5;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import DB.DB;


public class HSQLDB extends DB {
	
	//private static HSQLDB instance = null;
	private static Connection connection;
	
	private static final Logger logger = LoggerFactory.getLogger(HSQLDB.class);

    public HSQLDB(String username, String password) throws Exception {
        super();
        Class.forName("org.hsqldb.jdbcDriver");
        logger.info("Connect to database");
        try {
            connection = DriverManager.getConnection("jdbc:hsqldb:lib/ldb",    // filename
					username,            // username
					CryptWithMD5.cryptWithMD5(password));        // password
            logger.trace("Connection set");
        } catch (Exception e) {
            logger.error("No connection!");
            throw new Exception();
        }
    }
/*
	public static HSQLDB getInstance() throws Exception {	// Singleton-Pattern
		logger.trace("Instance requested");
		if (instance == null) {
			logger.trace("First time. New instance created");
			instance = new HSQLDB();
		}
		if (instance == null) logger.error("HSQLDB-instance is null");
		else logger.trace("Returning HSQLDB-instance");
		return instance;
	}
*/
	public synchronized void commit() throws SQLException {
		logger.info("Commit DB transactions");
		connection.commit();
//		closeAndReconnect();	// if a normal commit doesn't save persistently
	}

//	private void closeAndReconnect() throws SQLException {
//		logger.trace("DB shutdown");
//		Statement statement = connection.createStatement();
//		statement.execute("shutdown");
//		logger.trace("Close DB connection");
//		connection.close();
//		logger.trace("Reopen DB-connection");
//		connection = DriverManager.getConnection("jdbc:hsqldb:"
//				+"vair_db",	// filename
//				"fh",		// username
//				"");		// password
//		if (connection == null) logger.error("No connection!");
//		else logger.trace("Connection set");
//	}
	
	public synchronized void shutdown() throws SQLException {
		logger.info("DB shutdown");
		Statement statement = connection.createStatement();
		statement.execute("shutdown");
		connection.close();
		//instance = null;
	}
	
	public synchronized ResultSet query(String sqlExpression) throws SQLException {
		logger.trace("query: "+sqlExpression);
		return connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE).executeQuery(sqlExpression);
	}
	
	public synchronized void update(String sqlExpression) throws SQLException {
		logger.trace("update: "+sqlExpression);
		Statement statement = null;
		statement = connection.createStatement();
		int result = statement.executeUpdate(sqlExpression); 
		statement.close();
		if (result != 0) {
			throw new SQLException("Error in expression: "+sqlExpression);
		}
	}
	
	public synchronized PreparedStatement prepareStatement(String sqlExpression) throws SQLException {
		logger.trace("prepare statement: "+sqlExpression);
		return connection.prepareStatement(sqlExpression);
	}
		
}
