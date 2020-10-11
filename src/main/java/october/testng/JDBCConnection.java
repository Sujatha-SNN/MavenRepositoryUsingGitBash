package october.testng;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Arrays;

public class JDBCConnection {
	// JDBC driver name and database URL
	static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
	static final String DB_URL = "jdbc:mysql://localhost:3306/TESTLEAF";
	// jdbc:postgresql://127.0.0.1:5432/test", "postgres", "password"

	// Database credentials
	static final String USER = "root";
	static final String PASS = "password";

	public static void main(String[] args) {
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		try {
			// STEP 2: Register JDBC driver
			Class.forName("com.mysql.cj.jdbc.Driver");

			// STEP 3: Open a connection
			System.out.println("Connecting to a selected database...");
			conn = DriverManager.getConnection(DB_URL, USER, PASS);
			System.out.println("Connected database successfully...");


			  // STEP 4: Execute a Select query
			System.out.println("Creating statement...");
			stmt = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
			String sql = "select * from student";
			rs = stmt.executeQuery(sql);
			int size = 0;
			boolean b = rs.last();
			if (b)
			{
				size = rs.getRow();
			}
			String results[][] = new String[size][3];
			System.out.println(size);
			rs.beforeFirst();
			// STEP 5: Extract data from result set
			while (rs.next()) { // Retrieve by column name
				int id =rs.getInt("student_id");
				String name = rs.getString("name");
				String major = rs.getString("major");
				results[rs.getRow() - 1][0] = Integer.toString(id);
				results[rs.getRow() - 1][1] = name;
				results[rs.getRow() - 1][2] = major;
				// Display values System.out.print("Student ID: " + id);
				System.out.print(", Name: " + name);
				System.out.print(", Major: " + major);
				System.out.println("\n");
			}
			System.out.println(Arrays.deepToString(results));

			// STEP 4: Execute a Update query
			//System.out.println("Creating statement...");
			// Update Query
			/*String updateQuery = "Update student set major='Physics' where name ='Sara'";
			PreparedStatement prepStmt = conn.prepareStatement(updateQuery);*/

			// Insert query

			 /* String insertQuery =
			  "INSERT INTO student (student_id, name, major) VALUES (?,?,?)";
			  PreparedStatement prepStmt = conn.prepareStatement(insertQuery);

			  prepStmt.setInt(1,17);
			  prepStmt.setString(2,"SSN");
			  prepStmt.setString(3,"Microbiology");


			int executeUpdate = prepStmt.executeUpdate();
			System.out.println(executeUpdate);*/

		} catch (SQLException se) {
			// Handle errors for JDBC
			se.printStackTrace();
		} catch (Exception e) {
			// Handle errors for Class.forName
			e.printStackTrace();
		} finally {
			// finally block used to close resources
			try {
				if (stmt != null)
					stmt.close();
				if (rs != null)
					rs.close();
			} catch (SQLException se) {
			} // do nothing
			try {
				if (conn != null)
					conn.close();
			} catch (SQLException se) {
				se.printStackTrace();
			} // end finally try
		} // end try
		System.out.println("Goodbye!");
	}// end main
}// end JDBCExample
