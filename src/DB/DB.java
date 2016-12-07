package DB;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import DB.hsqldb.HSQLDB;

public abstract class DB {

	static DB instance = null;
	
	protected DB() {
		// may have additional initializations, also for derived classes
	}
	
	public static DB getInstance() throws Exception {	// Factory Pattern
		DB database;
		// ---
		// If additional databases are used, in the following line
		// the concrete database can be chosen at runtime e.g.
		// by a switch-case-statement
		database = HSQLDB.getInstance();
		// database = MYSQL.getInstance();
		// database = MSSQL.getInstance();
		// ---
		return database;
	}
	
	public abstract void commit() throws SQLException;
	
	public abstract void shutdown() throws SQLException;
	
	public abstract void update(String expression) throws SQLException;
	
	public abstract ResultSet query(String expression) throws SQLException;

	protected abstract PreparedStatement prepareStatement(String statement) throws SQLException;

}
