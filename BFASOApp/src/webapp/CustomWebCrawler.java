package webapp;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;


public class CustomWebCrawler {

	Connection con = DBConnection.getDBConnection();
	
	public boolean  shouldVisit(final String url)
	{
		return !NodeConstants.FILTERS.matcher(url.toLowerCase()).matches();
	}
	
	public void visit(final Document document)
	{
		try {
			final String url = document.location();
			System.out.println("URL : "+ url);
			
			String text = document.text();

			List<String> links = new ArrayList<String>();
			Elements elements = document.select("a[href]");
			for(Element el : elements)
				links.add(el.absUrl("href"));
			
			int pageid = GetSetIds.getPageid();
			PreparedStatement ps = con.prepareStatement("insert into page(ID, url) values(" + pageid + ", '" + url + "')");
			ps.executeUpdate();
			
			final List<String> words = cleanAndSplitString(text);
			int index = 0;
			for(final String word : words)
			{
				int wordid = GetSetIds.getWordid();
				PreparedStatement ps1 = con.prepareStatement("insert into words(ID, word, index1) values(" + wordid + ", '" + word + "', " + index + ")");
				ps1.executeUpdate();
				PreparedStatement ps2 = con.prepareStatement("insert into contain(wordid, source) values(" + wordid + ", " + pageid + ")");
				ps2.executeUpdate();
				index++;
			}
			
			for(String link : links)
			{
				link = link.replace("+", "");
				if(link.length()>4)
				{
					System.out.println("Linking to "+link);
					int linkid = GetSetIds.getLinkid();
					PreparedStatement ps3 = con.prepareStatement("insert into links(ID, url) values(" + linkid + ", '" + link + "')");
					ps3.executeUpdate();
					PreparedStatement ps4 = con.prepareStatement("insert into linkto(source, destination) values(" + pageid + ", " + linkid + ")");
					ps4.executeUpdate();
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	private static List<String> cleanAndSplitString(final String input)
	{
		if(input != null)
		{
			final String[] dic = input.toLowerCase().replaceAll("\\p{Punct}", "").replaceAll("\\p{Digit}", "").split("\\s+");
			return Arrays.asList(dic);
		}
		return new ArrayList<>();
	}
}
