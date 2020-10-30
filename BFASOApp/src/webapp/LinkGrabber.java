package webapp;

import java.net.URL;
import java.util.*;

import org.jsoup.Connection.Response;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class LinkGrabber {

	private String[] rootPages;
	private final int MAX_PAGES_TO_CRAWL = 100;
	private String protocol = null, domain = null;
	private List<String> pageToVisit = null;
	private Set<String> pagesVisited = null;

	/**
	 * Constructor
	 * 
	 * @param rootPage
	 *            starting page of web page to crawl
	 */
	public LinkGrabber(String[] rootPages) {
		this.rootPages = rootPages;
		pageToVisit = new LinkedList<String>();
		pagesVisited = new HashSet<String>();
	}

	/**
	 * Starts the crawler to get links and all details
	 */
	public void startCrawling() {
		try {
			int individualCounter = 0;
			for (int i = 0; i < rootPages.length; i++) {
				if (this.pagesVisited.size() > MAX_PAGES_TO_CRAWL)
					break;
				String rootPage = rootPages[i];
				CustomWebCrawler crawler = new CustomWebCrawler();
				if (rootPage.endsWith("/") || rootPage.endsWith("#"))
					rootPage = rootPage.substring(0, rootPage.length() - 1);
				try {
					Response response = Jsoup.connect(rootPage)
							.userAgent("test").timeout(10000)
							.ignoreHttpErrors(true).execute();

					int statusCode = response.statusCode();
					if (statusCode == 200
							&& response.contentType().contains("text/html")
							&& crawler.shouldVisit(rootPage)) {
						protocol = new URL(rootPage).getHost();
						domain = protocol + "://" + new URL(rootPage).getHost();
						pagesVisited.add(rootPage);
						Document document = Jsoup.connect(rootPage).get();
						Elements elements = document.select("a[href]");
						for (Element element : elements)
							pageToVisit.add(element.absUrl("href"));
						crawler.visit(document);
						int failedAttempts = 0;
						while (individualCounter <= 10) {
							// while (this.pagesVisited.size() <
							// MAX_PAGES_TO_CRAWL) {
							String currentUrl = getNextUrl();
							if (currentUrl != null) {
								try {
									int responseCode = checkStatus(currentUrl);
									if (crawler.shouldVisit(currentUrl)) {
										String currentDomain = protocol + "://"
												+ new URL(currentUrl).getHost();
										if (responseCode == 200
												&& currentDomain.equals(domain)) {
											Document doc = Jsoup.connect(
													currentUrl).get();
											crawler.visit(doc);
											System.out.println("Here I am...");
											pagesVisited.add(currentUrl);
											Elements el = doc.select("a[href]");
											for (Element e : el)
												pageToVisit.add(e
														.absUrl("href"));
											System.out.print(currentUrl
													+ " found " + el.size()
													+ " links\n");
											individualCounter++;
											failedAttempts = 0;
										}
									}
									failedAttempts++;
									if (failedAttempts > 10)
										break;
								} catch (Exception e) {
									e.printStackTrace();
								}
								System.out.println("Pages visited:"
										+ pagesVisited.size());

							} else {
								System.out
										.println("No URL found crawl. Crawler is stopping now. Total Crawled links:"
												+ (this.pagesVisited.size()));
							}
						}
						individualCounter = 0;
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Check the status of URL which we are going to crawl. The method throws
	 * exception if the url is not valid.
	 * 
	 * @param url
	 * @return status code of URL
	 */
	public int checkStatus(String url) {
		int statusCode = 0;
		try {
			Response response = Jsoup.connect(url).userAgent("test agent")
					.timeout(10000).ignoreHttpErrors(true).execute();
			if (!response.contentType().contains("text/html")) {
				System.out.println("Page content type:"
						+ response.contentType());
				throw new Exception();
			}
			statusCode = response.statusCode();
		} catch (Exception e) {
			e.printStackTrace();
			System.out.print("Invalid page:" + url + "\n");
		}
		return statusCode;
	}

	/**
	 * Method removes a link from pageToVisit list and checks whether it is
	 * already visited or not
	 * 
	 * @return url to crawl.
	 */
	public String getNextUrl() {
		String nextUrl = null;
		do {
			nextUrl = this.pageToVisit.remove(0);
		} while (this.pagesVisited.contains(nextUrl));
		return nextUrl;
	}
}