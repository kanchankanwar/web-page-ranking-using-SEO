package webapp;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.*;
import java.util.concurrent.Callable;

import org.graphstream.graph.Node;


@SuppressWarnings("deprecation")
public class DocumentLocationTask extends SearchTask implements Callable<TaskResponse>{
Connection con = DBConnection.getDBConnection();
	
	/*
	 * Constructor
	 * 
	 * @param terms words
	 */
	public DocumentLocationTask(final String terms)
	{
		super(terms);
	}
	
	
	/**
	 * Method executes DocumentLocation Task and retrieves the index of Url.
	 * @see java.util.concurrent.Callable#call()
	 */
	@Override
	public TaskResponse call() throws Exception {
		// TODO Auto-generated method stub
		final TaskResponse response = new TaskResponse();
		try
		{
			System.out.println("Document location task started");
			final ResultSet result = executeQuery(searchTerms);
			final Map<String, Double> returnMap = convertToUrlTotalWords(result);

			response.taskClass = this.getClass();
			response.resultMap = NormalizationFunctions.normalizeMap(returnMap, true);
			System.out.println("Document location task stopped");
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return response;
	}

	/**
	 * Following method checks for the smaller index from given results and returns the smallest index possible
	 * @param result
	 * @return smallest index from database
	 * @throws SQLException 
	 */
	private Map<String, Double> convertToUrlTotalWords(final ResultSet result) throws SQLException
	{
		final Map<String, Double> uniqueUrls = new HashMap<>();
		Map<String, Object> row = new HashMap<>();
		ResultSetMetaData md = result.getMetaData();
		 int columns = md.getColumnCount();
			while(result.next())
			{
				for (int i = 1; i <= columns; i++) {
				       row.put(md.getColumnName(i), result.getObject(i));
				}
				String currentUrl = null;
				double smallIndex = Double.MAX_VALUE;
				
				for(final Map.Entry<String, Object> entry : row.entrySet())
				{
						if(entry.getKey().equals("url"))
							currentUrl = entry.getValue().toString();
						else if(entry.getKey().equals("index1"))
						{
							double index = Double.parseDouble(entry.getValue().toString());
							index = index == 0.0 ? 1 : index;
							if(index < smallIndex)
								smallIndex = index;
						}
					
				}
				uniqueUrls.put(currentUrl, smallIndex);
			}
		
		return uniqueUrls;
	}
	
}
