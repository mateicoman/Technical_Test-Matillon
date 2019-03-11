package Test2;

import java.util.List;
import java.sql.*;
import java.util.ArrayList;
import java.util.Scanner;
public class Test2 
{

	public static void main(String[] args) 
	{
		Connection conn = null;
		int department_id =0;

		List<Integer> position_id = new ArrayList<Integer>();
		
		Scanner sc = new Scanner(System.in);
		System.out.println("Enter the department: ");
		String department = sc.nextLine();
		System.out.println("Enter the pay type: ");
		String payType = sc.nextLine();
		System.out.println("Enter the pay education level: ");
		String educLevel = sc.nextLine();
		
		
		
		
		try
		{
			final String url = "jdbc:mysql://mysql-technical-test.cq5i4y35n9gg.eu-west-1.rds.amazonaws.com/foodmart";
			final String username = "technical_test";
			final String password = "HopefullyProspectiveDevsDontBreakMe";
			conn = DriverManager.getConnection(url, username, password);
			
			
			
			PreparedStatement firstStatement;
			String firstQuery = "select * from foodmart.department where department_description = ?";
			firstStatement = conn.prepareStatement(firstQuery);
			firstStatement.setString(1, department);
			ResultSet myResultSet1 = firstStatement.executeQuery();
			while(myResultSet1.next())
			{
				department_id = myResultSet1.getInt("department_id");
			}
			
			PreparedStatement secondStatement;
			String secondQuery = "select * from foodmart.position where pay_type = ?";
			secondStatement = conn.prepareStatement(secondQuery);
			secondStatement.setString(1, payType);
			ResultSet myResultSet2 = secondStatement.executeQuery();
			while(myResultSet2.next())
			{
				position_id.add(myResultSet2.getInt("position_id"));
			}
//			
//			for(int i=0;i<position_id.size();i++){
//			    System.out.println(position_id.get(i));
//			} 
			
			PreparedStatement finalStatement;
			String finalQuery = "select * from foodmart.employee where department_id = ? "
					+ "union "
					+ "select * from foodmart.employee where position_id in (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?) "
					+ "union "
					+ "select * from foodmart.employee where education_level = ?";
			finalStatement = conn.prepareStatement(finalQuery);
			finalStatement.setString(1, department);
			for(int i = 2; i<=17;i++)
			{
				finalStatement.setInt(i, position_id.get(i-2));
			}
			finalStatement.setString(18, educLevel);
			ResultSet myResultSet3 = finalStatement.executeQuery();
			ResultSetMetaData rsmd = myResultSet3.getMetaData();
			int columnsNumber = rsmd.getColumnCount();
			while(myResultSet3.next())
			{
				for (int i = 1; i <= columnsNumber; i++) 
					{
			           if (i > 1) System.out.print(",  ");
			           String columnValue = myResultSet3.getString(i);
			           System.out.print(columnValue + " " + rsmd.getColumnName(i));
			           
			     	}
				System.out.print("");
			}
		}
		
		catch (SQLException e)
	    {
	        throw new RuntimeException("Cannot connect the database!", e);
	    } 
	    finally 
	    {
	        System.out.println("Closing the connection.");
	        if (conn != null) try { conn.close(); } catch (SQLException ignore) {}
	    }
		

	}

}
