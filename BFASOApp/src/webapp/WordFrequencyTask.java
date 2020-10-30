package webapp;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.*;
import java.util.concurrent.Callable;



@SuppressWarnings("deprecation")
public class WordFrequencyTask extends SearchTask implements Callable<TaskResponse> {
Connection con = DBConnection.getDBConnection();
	/*
	 * Constructor 
	 * 
	 * @param terms words 
	 */
	public WordFrequencyTask(final String terms)
	{
		super(terms);
	}
	
	
	/*
	 * The Callable method to do processing
	 * 
	 * @return results
	 * @see java.util.concurrent.Callable#call()
	 */
	
	@Override
	public TaskResponse call() throws Exception {
		// TODO Auto-generated method stub
		System.out.println("Word frequency task started");
		final TaskResponse taskResponse = new TaskResponse();
		try
		{
			final ResultSet result = executeQuery(searchTerms);
			final Map<String, Double> returnMap = convertToUrlTotalWords(result);
			
			taskResponse.taskClass = this.getClass();
			taskResponse.resultMap = NormalizationFunctions.normalizeMap(returnMap, false);
			System.out.println("Word frequncy task stopped");
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
		return taskResponse;
	}

	/*
	 * Convert to URL total words
	 * @param result neo4j result
	 * @return map
	 */
	private Map<String, Double> convertToUrlTotalWords(final ResultSet result) throws SQLException
	{
		
		 ResultSetMetaData md = result.getMetaData();
		 int columns = md.getColumnCount();
		final Map<String, Double> uniqueUrls = new HashMap<>();
		
		Map<String, Object> row = new HashMap<>();
		
			double wordCount = 0.0;
			String currentUrl = null;
			while(result.next())
			{
				for (int i = 1; i <= columns; i++) {
				       row.put(md.getColumnName(i), result.getObject(i));
				}
				
				for(final Map.Entry<String, Object> entry : row.entrySet())
				{
					if(entry.getKey().equals("url"))
						currentUrl = entry.getValue().toString();
					if(entry.getKey().equals("word"))
						wordCount++;
				}
				if(uniqueUrls.containsKey(currentUrl))
					uniqueUrls.put(currentUrl, uniqueUrls.get(currentUrl)+wordCount);
				else
					uniqueUrls.put(currentUrl, wordCount);
			
			}
			
		return uniqueUrls;
	}
}