import java.sql.*;
import java.util.Scanner;

public class ExtraCredit
{
    public static void main(String [] args) throws SQLException 
    {
	Scanner input = new Scanner(System.in);
	Connection c = null;
    Statement stmt = null;
    ResultSet statement = null;
    
    //Setting up connection: 
    try {
      Class.forName("org.sqlite.JDBC");
      c = DriverManager.getConnection("jdbc:sqlite:chinook.db");
    } 
    catch (Exception e){
      System.err.println( e.getClass().getName() + ": " + e.getMessage() );
      System.exit(0);
    }
    int tracks [] = new int [100];
    int count = 0;
    int choice = 0;
    while (choice != 5)
    {
		System.out.print("What action would you like to peform?\n"
				+ "1. Identify marketable population and material.\n"
				+ "2. Simple Track recommender\n"
				+ "3. Top sellers by revenue\n"
				+ "4. Top Sellers by Volume\n"
				+ "5. Exit Program"
				+ "Action: ");
		
		choice = input.nextInt();
		String trivial = input.nextLine();
    	System.out.println();
    	
    	//Identify marketable population and material
		if (choice == 1)
		{
			//prompted for an Artist name.
			System.out.print("Enter a State: ");
			String state = input.nextLine();
			c.setAutoCommit(false);
			stmt = c.createStatement();
			//query
			statement = stmt.executeQuery( "SELECT distinct al.albumid, al.name, c.name "
										+ "FROM ALBUM al, CUSTOMER c "
										+ "WHERE ;");
			int currentarid = 0;
			while ( statement.next()){
				String input_state = statement.getString("State");
			    String  name = statement.getString("Name");
			    int alId = statement.getInt("AlbumId");
			    String  title = statement.getString("Title");
			    String firstname = statement.getString("First Name");
			    String lastname = statement.getString("Last Name");
			   //User input can be AC/DC, ac/dc or AC/dc and the output should still be the same
			    if (input_state.toLowerCase().equals(state.toLowerCase())){
			    	//Output
			    System.out.println( "==========================State: " + input_state + "=============================");
				    System.out.println( "Album Title: \"" + title + "\", Album ID: " + alId);
				    System.out.println( "Customer name: " + firstname + lastname);
				    count++;
			    }
			}
			//Prints "No Results" if none are returned
			if (count == 0) System.out.print("No Results");
		}
		//Simple Track recommender
		else if (choice == 2)
		{
			//prompted for customer id
			System.out.print("Enter Customer ID: ");
			int user_cutomerId = input.nextInt();
			c.setAutoCommit(false);
			stmt = c.createStatement();
			//query to find the customers Purchase History the user is looking for
			 statement = stmt.executeQuery( "SELECT DISTINCT al.title "
					 					+ "From Album al, Track T, InvoiceLine IL AND al.albumid = T.albumid "
					 					+ "WHERE IL.trackid = T.trackid"
					 					+ "GROUP BY T.title"
					 					+ "HAVING COUNT (*) >2");
			count = 0;
			//Output
			while ( statement.next() ) 
			{
			    String  artist_name = statement.getString("Artist Name");
			    int customer_id = statement.getInt("Customer ID");
			    int track_id = statement.getInt("Track_id");
			    int purchased_tracks_id = statement.getInt("Purchased Tracks ID");

			    if (customer_id == user_cutomerId && (track_id == purchased_tracks_id && purchased_tracks_id >2) )
			    {
				    System.out.println( "Artist Name: \"" + artist_name);
				    System.out.println();
				    count++;
			    }
			}
			//Prints "No Results" if none are return
			if (count == 0)
			{
				System.out.print("No Results");
			}
		}
		
	//Top sellers by revenue
    else if (choice == 3)
    {
    	//prompted for artist name:
	    System.out.print("Enter Artist Name: ");
		int user_artist_name = input.nextInt();
		c.setAutoCommit(false);
		stmt = c.createStatement();
		//Query to find the track id the suer is looking for
		statement = stmt.executeQuery( "SELECT a.name "
									+ "FROM Artist a, Track T, InvoiceLine IL"
									+ "WHERE T.unitprice = (IL.unitprice * IL.quantity) AND"
									+ "T.trackid = IL.trackid");
		
		//Output:
		while ( statement.next() ) {
			String artist_name = statement.getString("Artist Name");
			double unit_price = statement.getDouble("Unit Price");
			int quantity = statement.getInt("Quantity");
			int track_id = statement.getInt("Track Id");
			
			if(track_id == (quantity*track_id))
			{
				System.out.println("Artist Name:" + user_artist_name);
				count++;
			}
			if (count == 0)
			{
				System.out.print("No Results");
			}

		}
    }
	//Top Sellers by Volume
    else if (choice == 4)
    {
    	//prompted for artist name:
	    System.out.print("Enter Artist Name: ");
		int user_artist_name = input.nextInt();
		c.setAutoCommit(false);
		stmt = c.createStatement();
		//Query to find the track id the suer is looking for
		statement = stmt.executeQuery( "SELECT a.name "
									+ "FROM Artist a, Track T, InvoiceLine IL"
									+ "WHERE T.trackid = IL.trackid"
									+ "GROUP BY IL.quantity"
									+ "HAVING COUNT (*) < IL.quantity");
		
		//Output:
		while ( statement.next() ) {
			String artist_name = statement.getString("Artist Name");
			int quantity1 = statement.getInt("Quantity");
			int quantity2 = statement.getInt ("Quantity");
			if(quantity1 > quantity2)
			{
				System.out.println("Artist Name:" + user_artist_name);
				count++;
			}
			if (count == 0)
			{
				System.out.print("No Results");
			}
    }

    	 statement.close();
    	 stmt.close();
    	 c.close();
    }
  }
 }
}





