package kk.databasesManager.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.sun.rowset.CachedRowSetImpl;

public class DBUtil {
//	private static final String JDBC_DRIVER = "org.postgresql.Driver";
	
	private static Connection conn = null;
	
	private static final String connStr = "jdbc:postgresql://localhost:5432/postgres?user=postgres&password=makaron";
	
	private static void dbConnect() {
//		try {
//			//Class.forName(JDBC_DRIVER);
//			DriverManager.registerDriver(new org.postgresql.Driver());
//		} catch (SQLException e) {
//			System.out.println("There is no Postgresql driver.");
//			e.printStackTrace();
//		}
//		System.out.println("Driver registed.");
		try {
			conn = DriverManager.getConnection(connStr);
		} catch (SQLException e) {
			System.out.println("Connection failed.");
			e.printStackTrace();
		}
		System.out.println("Connected to database.");
	}
	
	private static void dbDisconnect() {
		try {
			if (conn != null && !conn.isClosed()) {
				conn.close();
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static ResultSet dbExecuteQuery(String queryStmt) throws SQLException {
		Statement stmt = null;
		ResultSet resultSet = null;
		CachedRowSetImpl crsi = null;
		dbConnect();
		System.out.println("---- BEGIN -----");
		System.out.println("Query: " + queryStmt);
		try {
			stmt = conn.createStatement();
			resultSet = stmt.executeQuery(queryStmt);
			
			crsi = new CachedRowSetImpl();
			crsi.populate(resultSet);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if (resultSet != null) {
				resultSet.close();
			}
			if (stmt != null) {
				stmt.close();
			}
			dbDisconnect();
			System.out.println("---- END -----");
		}
		return crsi;
	}
	
	public static void dbExecuteUpdate(String sqlStmt) throws SQLException {
		Statement stmt = null;
		dbConnect();
		System.out.println("---- BEGIN -----");
		System.out.println("Query: " + sqlStmt + "\n");
		try {
			stmt = conn.createStatement();
			stmt.executeUpdate(sqlStmt);
		} catch (SQLException e) {
			System.out.println("Error was occured in dbExecuteUpdate. SQL: " + sqlStmt);
			e.printStackTrace();
		} finally {
			if (stmt != null) {
				stmt.close();
			}
			dbDisconnect();
			System.out.println("---- END -----");
		}
	}
	
	public static void terminateConnection(String databaseName) {
		try {
			dbExecuteQuery("SELECT pg_terminate_backend(pid) FROM pg_stat_activity WHERE datname = '" + databaseName + "' AND pid <> pg_backend_pid()");
		} catch (SQLException e) {
			System.out.println("Error occured while terminating connection.");
			e.printStackTrace();
		}
	}
}
