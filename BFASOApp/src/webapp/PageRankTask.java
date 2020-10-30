package webapp;

import java.util.concurrent.Callable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.*;

import org.graphstream.algorithm.PageRank;
import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;
import org.graphstream.graph.implementations.SingleGraph;

@SuppressWarnings("deprecation")
public class PageRankTask extends SearchTask implements Callable<TaskResponse>{
	Connection con = DBConnection.getDBConnection();
	 Map<String, Double> uniqueUrls = new HashMap<>();
	/**
	 * Constructor
	 * 
	 * @param terms words
	 */
	public PageRankTask(final String terms)
	{
		super(terms);
	}
	
	/**
	 * Method retrieves all the nodes containing words we have passed and their related nodes.
	 * @throws SQLException 
	 */
	protected ResultSet executeQuery(final String words) throws SQLException
	{
		PreparedStatement ps = con.prepareStatement("SELECT DISTINCT links.url AS lnk, page.url AS pg, words.word FROM links, page, linkto, words, contain WHERE page.ID = contain.source AND words.ID = contain.wordid AND words.word LIKE '%" + words + "%' AND linkto.source = page.ID AND linkto.destination = links.ID");
		ResultSet result = ps.executeQuery();
		System.out.println("In Execute query...");
		return result;
	}
	
	/**
	 * The callable method to do the processing
	 * 
	 * @see java.util.concurrent.Callable#call()
	 */
	@Override
	public TaskResponse call() throws Exception {
		// TODO Auto-generated method stub
		final TaskResponse response = new TaskResponse();
		try
		{
			System.out.println("Pagerank task started ");
			final ResultSet result = executeQuery(searchTerms);
			final Map<String, Double> returnMap = convertToUrlTotalWords(result);
			response.taskClass = this.getClass();
			response.resultMap = NormalizationFunctions.normalizeMap(returnMap, true);
			System.out.println("Page rank task stopped");
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return response;
	}
	
	/**
	 * Method create a simple graph of nodes which contains inlinks and outlinks and it is passed to calculate
	 * the page rank of each link.
	 * @param result
	 * @return
	 * @throws SQLException 
	 */
	private Map<String, Double> convertToUrlTotalWords(ResultSet result) throws SQLException {
        
        ResultSetMetaData md = result.getMetaData();
        int colcount = md.getColumnCount();
        final Graph g = new SingleGraph("rank", false, true);
       // final Iterator<Node> pageIterator = result.columnAs("related");
        while (result.next()) {
        	final Node node = g.addNode(""+result.getRow());
                final String source = result.getString("pg");
                node.addAttribute("source", source);
                uniqueUrls.put(source, 0.0);
                final String destination = result.getString("lnk");
                node.addAttribute("destination", destination);
                g.addEdge(String.valueOf(node.getId()), source, destination, true);
            
        }

        // calculate page rank of each url with given graph
        computeAndSetPageRankScores(uniqueUrls, g);
        return uniqueUrls;
    }

	/**
     * Compute score
     *
     * @param uniqueUrls urls
     * @param graph      the graph of all links
     */
    private void computeAndSetPageRankScores(Map<String, Double> uniqueUrls, final Graph graph) {
        final PageRank pr = new PageRank();
        pr.init(graph);
        pr.compute();

        for (final Map.Entry<String, Double> entry : uniqueUrls.entrySet()) {
            final double score = 100 * pr.getRank(graph.getNode(entry.getKey()));
            entry.setValue(score);
        }
    }
}
