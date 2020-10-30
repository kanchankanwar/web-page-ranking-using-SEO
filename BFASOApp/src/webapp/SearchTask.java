package webapp;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;



public class SearchTask {

	protected final String searchTerms;
	Connection con = DBConnection.getDBConnection();
	
	
	/*
	 * Constructor
	 * @param terms words
	 */
	public SearchTask(final String terms)
	{
		this.searchTerms = terms;
	}
	
	protected ResultSet executeQuery(final String words) throws SQLException
	{
		PreparedStatement ps = con.prepareStatement("SELECT DISTINCT page.url, words.word, words.index1 from page, words, contain where page.ID = contain.source AND words.ID = contain.wordid AND words.word LIKE '%" + words + "%'");
		ResultSet result = ps.executeQuery();
		return result;
	}
	
	
	protected ResultSet executeQuery(int fakeParam,final String... words) throws SQLException
	{
		PreparedStatement ps = con.prepareStatement("SELECT DISTINCT page.url, COUNT(words.word) as words from page, words, contain where page.ID = contain.source AND words.ID = contain.wordid AND words.word LIKE '%" + words + "%'");
		ResultSet result = ps.executeQuery();
		return result;
	}
	
	/*
	 * Format array of strings for in statement
	 * 
	 * @param args array to format
	 * @return formatted string
	 */
	/*protected String formatArray(final String... args)
	{
		final String format = new String(new char[args.length]).replace("\0", "'%s',");
		final String result = String.format(format, args);
		
		return result.substring(0, result.length() - 1);
	}*/
}