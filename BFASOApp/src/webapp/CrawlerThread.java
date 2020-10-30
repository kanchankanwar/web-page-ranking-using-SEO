package webapp;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class CrawlerThread implements Runnable {
	
	
	public void run() {
		try {
			List<String> links = Files.readAllLines(Paths.get("d:\\links.txt"));
			System.out.println(""+links);
			String []rootPages = new String[links.size()];
			rootPages = links.toArray(rootPages);
			LinkGrabber grabber = new LinkGrabber(rootPages);
			grabber.startCrawling();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
}
