package br.gov.i3gov.crawler.tools;

import java.util.List;
import java.util.Vector;

import br.gov.i3gov.core.CrawlerListener;
import br.gov.i3gov.core.UnitCrawler;
import br.gov.i3gov.core.UnitCrawlerImpl;
import br.gov.i3gov.crawler.links.BufferLinks;

public class MultiThreadCrawlerImpl implements MultiThreadCrawler{

	private CrawlerListener listener;
	private BufferLinks bufferLinks;
	private int numThreads;
	private int maxDepth = -1;
	private boolean debug = false;
	private List<String> contentTypes = new Vector<String>(); 
	

	public void addCrawlerListener(CrawlerListener listener) {
		
		this.listener = listener;
		
	}


	public void setBufferLinks(BufferLinks bufferLinks) {
	
		this.bufferLinks = bufferLinks;
		
	}


	public void setMaxThreads(int numThreads) {
	
		this.numThreads = numThreads;
	}

	
	public void startCrawler() {
		
		Thread[] t = new Thread[numThreads];
		
		for(int i = 0; i < numThreads; i++)
		{
			UnitCrawler crawl = new UnitCrawlerImpl(i);
			crawl.setBufferLinks(bufferLinks);
			crawl.setCrawlerListener(listener);
			crawl.setMaxDepth(maxDepth);
			crawl.setDebug(debug);
			for(String contentType: contentTypes)
				crawl.addFilterContentType(contentType);
			t[i] = new Thread(crawl);	
			t[i].start();
		}
		
		for(int i = 0; i < numThreads; i++)
		{
			try {
				t[i].join();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
		
	/*	while(bufferLinks.inProcess())
		{
			try {
				Thread.sleep(1000*2);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}				
		} */
	}


	public void setMaxDepth(int maxDepth) {
		
		this.maxDepth = maxDepth;
		
	}


	public void setDebug(boolean debug) {
		
		this.debug = debug;
		
	}


	public void addFilterContentType(String contentType) {
	
		contentTypes.add(contentType);
		
	}

}
