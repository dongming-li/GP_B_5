package main;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Scanner;

public class jdbcConnection 
{
	public static void main(String args[]) throws SQLException
	{
		String 		userName	= "dbu309gpb5";
		String 		passWord 	= "!tvDzTDx";
		String 		dbURL 		= "jdbc:mysql://mysql.cs.iastate.edu:3306/db309gpb5?useSSL=false";
		Connection 	conn 		= null;
	
		conn = openDBConnection(userName, passWord, dbURL, conn);
		
	    Scanner reader = new Scanner(System.in);
	    System.out.println("Enter a your username: ");
	    String username = reader.next();
	    System.out.println("Enter a your password: ");
	    String password = reader.next();
		
	    loginUser(username, password, conn);
	    
	    try 
	    {
			conn.close();
		} 
	    catch (SQLException ex) 
	    {
            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLState: " + ex.getSQLState());
            System.out.println("VendorError: " + ex.getErrorCode());
		}
	    reader.close();	    
	}
	
	public static Connection openDBConnection(String userName, String passWord, String dbURL, Connection connection)
	{
        try 
        {
            Class.forName("com.mysql.jdbc.Driver").newInstance();
        } catch (Exception ex) 
        {
            System.out.println(ex);
        }
        try 
        {
            connection = DriverManager.getConnection(dbURL, userName, passWord);
        } 
        catch (SQLException ex) 
        {
            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLState: " + ex.getSQLState());
            System.out.println("VendorError: " + ex.getErrorCode());
        }
        
        return connection;
	}
	public static void printResultSet(ResultSet rs) throws SQLException
	{
	    ResultSetMetaData rsmd = rs.getMetaData();
	    int columnsNumber = rsmd.getColumnCount();
	    while (rs.next()) 
	    {
	        for (int i = 1; i <= columnsNumber; i++) 
	        {
	            if (i > 1) System.out.print(",  ");
	            String columnValue = rs.getString(i);
	            System.out.print(columnValue);
	        }
	        System.out.println("");
	    }
	}
	public static void printArrayList(ArrayList<String> list)
	{
		for(int i = 0; i < list.size(); i++)
		{
			System.out.println(list.get(i));
		}
	}
	public static ResultSet executeQuery(String query, Connection conn)
	{
		ResultSet rs = null;
		Statement stmt = null;
		try 
		{
		    stmt = conn.createStatement();
		    rs = stmt.executeQuery(query);

		    if (stmt.execute(query)) 
		    {
		        rs = stmt.getResultSet();
		    }
		}
		catch (SQLException ex)
		{
		    System.out.println("SQLException: " + ex.getMessage());
		    System.out.println("SQLState: " + ex.getSQLState());
		    System.out.println("VendorError: " + ex.getErrorCode());
		}
		return rs;
	}
	public static ArrayList<String> getUserNames(Connection conn) throws SQLException
	{
		ResultSet rs = null;
		Statement stmt = null;
		ArrayList<String> users = new ArrayList<String>();
		
		try 
		{
		    stmt = conn.createStatement();
		    rs = stmt.executeQuery("SELECT userName from Users");

		}
		catch (SQLException ex)
		{
		    System.out.println("SQLException: " + ex.getMessage());
		    System.out.println("SQLState: " + ex.getSQLState());
		    System.out.println("VendorError: " + ex.getErrorCode());
		}
		
	    ResultSetMetaData rsmd = rs.getMetaData();
	    int columnsNumber = rsmd.getColumnCount();
	    
	    while (rs.next()) 
	    {
	        for (int i = 1; i <= columnsNumber; i++) 
	        {
	            String columnValue = rs.getString(i);
	            users.add(columnValue);
	        }
	    }
		return users;
	}
	public static ArrayList<String> getPassWords(Connection conn) throws SQLException
	{
		ResultSet rs = null;
		Statement stmt = null;
		ArrayList<String> passwords = new ArrayList<String>();
		
		try 
		{
		    stmt = conn.createStatement();
		    rs = stmt.executeQuery("SELECT userPass from Users");

		}
		catch (SQLException ex)
		{
		    System.out.println("SQLException: " + ex.getMessage());
		    System.out.println("SQLState: " + ex.getSQLState());
		    System.out.println("VendorError: " + ex.getErrorCode());
		}
		
	    ResultSetMetaData rsmd = rs.getMetaData();
	    int columnsNumber = rsmd.getColumnCount();
	    
	    while (rs.next()) 
	    {
	        for (int i = 1; i <= columnsNumber; i++) 
	        {
	            String columnValue = rs.getString(i);
	            passwords.add(columnValue);
	        }
	    }
		return passwords;
	}
	public static ArrayList<String> getUsersAndPasswords(Connection conn) throws SQLException
	{
		ResultSet rs = null;
		Statement stmt = null;
		ArrayList<String> usersAndPasswords = new ArrayList<String>();
		
		try 
		{
		    stmt = conn.createStatement();
		    rs = stmt.executeQuery("SELECT userName, userPass from Users");

		}
		catch (SQLException ex)
		{
		    System.out.println("SQLException: " + ex.getMessage());
		    System.out.println("SQLState: " + ex.getSQLState());
		    System.out.println("VendorError: " + ex.getErrorCode());
		}
		
	    ResultSetMetaData rsmd = rs.getMetaData();
	    int columnsNumber = rsmd.getColumnCount();
	    
	    while (rs.next()) 
	    {
	        for (int i = 1; i <= columnsNumber; i++) 
	        {
	            String columnValue = rs.getString(i);
	            usersAndPasswords.add(columnValue);
	        }
	    }
		
		return usersAndPasswords;
	}
	public static Boolean doesUserExist(Connection conn, String userName) throws SQLException
	{
	    ArrayList<String> userLogins = getUserNames(conn);
	    Boolean exists = null;
		
	    for(int i = 0; i < userLogins.size(); i++)
	    {
	    	if(userName.equals(userLogins.get(i)))
	    	{
	    		exists = true;
	    		break;
	    	}
	    	else
	    	{
	    		exists = false;
	    	}
	    }
	    return exists;
	}
	public static int loginUser(String userName, String passWord, Connection conn) throws SQLException
	{
		if(doesUserExist(conn, userName) == true)
		{
			ArrayList<String> usersAndPasswords = getUsersAndPasswords(conn);
			
			for(int i = 0; i < usersAndPasswords.size(); i++)
			{
				if(userName.equals(usersAndPasswords.get(i)))
				{
					i++;
					if(passWord.equals(usersAndPasswords.get(i))) 
					{
						System.out.println("Login was successful for user " + userName);
					}
					else
					{
						System.out.println("Incorrect Password, remember that passwords are case sensitive");
					}
				}
			}
		}
		else
		{
			System.out.println("No user found with name: " + userName + ", you'll need to make an account");
		}
		return 0;
	}





}











