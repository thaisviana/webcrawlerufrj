package br.gov.i3gov.crawler.tools;

import br.gov.i3gov.core.CrawlerListener;
import br.gov.i3gov.crawler.links.BufferLinks;

public interface MultiThreadCrawler {
	
	public void startCrawler();
	
	public void addCrawlerListener(CrawlerListener listener);
	
	public void setMaxThreads(int numThreads);
	
	public void setMaxDepth(int maxDepth);
	
	public void setBufferLinks(BufferLinks bufferLinks);
	
	public void setDebug(boolean debug);

	public void addFilterContentType(String contentType);
}
