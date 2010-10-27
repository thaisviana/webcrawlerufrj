package br.gov.i3gov.core;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.Vector;

import br.gov.i3gov.crawler.links.BufferLinks;
import br.gov.i3gov.crawler.links.Link;
import br.gov.i3gov.crawler.links.LinkImpl;

import com.gargoylesoftware.htmlunit.FailingHttpStatusCodeException;
import com.gargoylesoftware.htmlunit.HttpMethod;
import com.gargoylesoftware.htmlunit.Page;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.WebRequest;
import com.gargoylesoftware.htmlunit.WebResponse;
import com.gargoylesoftware.htmlunit.html.HtmlAnchor;
import com.gargoylesoftware.htmlunit.html.HtmlPage;

@SuppressWarnings("unchecked")
public class UnitCrawlerImpl implements UnitCrawler{

	private static int TIMEOUT = 1000*60*2;
	
	private long id;
	private BufferLinks bufferLinks = null;
	private CrawlerListener crawlerListener = null;
	private WebClient webClient = null;
	private int maxDepth;
	private boolean debug = false;
	private List<String> contentTypes = new Vector<String>(); 
	
	public UnitCrawlerImpl(long id)
	{
		this.id = id;
		webClient = new WebClient();
		webClient.setCssEnabled(false);
		webClient.setJavaScriptEnabled(false);
		webClient.setActiveXNative(false);
		webClient.setAppletEnabled(false);
		webClient.setPopupBlockerEnabled(false);
		webClient.setThrowExceptionOnScriptError(false);
		webClient.setTimeout(TIMEOUT);
		maxDepth = -1;
	}
	

	public void run() {
		
		while(true)
		{	
			Link link = null;
			
			synchronized (bufferLinks) {
				
				while(bufferLinks.isEmpty() && bufferLinks.inProcess())
				{
					try {
						bufferLinks.wait();

					} catch (InterruptedException e) {
				
						e.printStackTrace();
					}
				}
				
				DEBUG("Thread " + Thread.currentThread().getId() + " - número de urls  no buffer = " + bufferLinks.getNumberLinksVisited());
				
				if(!bufferLinks.inProcess())
					break;
				
				link = bufferLinks.pop();
				bufferLinks.notifyProcess();
			}
			
			try {
				
				Page page = null;
				link.setContentType(getContentType(link));
				
				if(isLinkHTML(link) == true)
				{
					DEBUG("downloading... " + link.getUrl() + " (depth = " + link.getDepth() + ")");
					page = webClient.getPage(link.getUrl());
					DEBUG("download da página " + link.getUrl() + " efetuada");

					if((maxDepth < 0 || maxDepth > link.getDepth()) && page instanceof HtmlPage)
					{
						HtmlPage htmlPage = (HtmlPage) page;
						
						List<HtmlAnchor> anchors = htmlPage.getAnchors();
						
						for(HtmlAnchor anchor: anchors)
						{
							Link subLink = new LinkImpl();
							subLink.setUrl(webClient.expandUrl(new URL(link.getUrl()), anchor.getHrefAttribute()).toString());
							subLink.setDepth(link.getDepth() + 1);
							subLink.setRotulo(link.getRotulo());
							bufferLinks.push(subLink);
							
						}
					}
				}
				
				if(isLinkContentTypeValid(link) == true)
				{
					crawlerListener.processPage(page, link);
				}
				
				
			} catch (FailingHttpStatusCodeException e) {
				// TODO Auto-generated catch block
				//e.printStackTrace();
			} catch (MalformedURLException e) {
				// TODO Auto-generated catch block
				//e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				//e.printStackTrace();
			} catch (NullPointerException e)
			{
				//e.printStackTrace();
			} catch (Exception e)
			{
				
			}
			
			bufferLinks.freeProcess();
		}
		
		DEBUG("Thread " + Thread.currentThread().getId() + " encerrou");
	}
	
	private boolean isLinkHTML(Link link) throws IOException
	{
		if(link.getContentType() != null && link.getContentType().trim().equalsIgnoreCase("text/html") == false)
		{
			return false;
		}
		
		return true;
	}
	
	private boolean isLinkContentTypeValid(Link link) throws IOException
	{
		if(contentTypes.isEmpty())
			return true;
		
		for(String contentType: contentTypes)
		{
			if(link.getContentType().trim().equalsIgnoreCase(contentType) == true)
				return true;
		}
		
		return false;
	}
	
	private String getContentType(Link link) throws Exception
	{
		WebRequest request = new WebRequest(new URL(link.getUrl()));
		request.setHttpMethod(HttpMethod.HEAD);
		WebResponse response = webClient.getWebConnection().getResponse(request);
		
		return response.getContentType();
	}


	public void setBufferLinks(BufferLinks bufferLinks) {
	
		this.bufferLinks = bufferLinks;
		
	}

	public void setCrawlerListener(CrawlerListener listener) {
	
		this.crawlerListener = listener;
		
	}


	public void setMaxDepth(int maxDepth) {
		
		this.maxDepth = maxDepth;
		
	}
	
	private void DEBUG(String msg)
	{
		if(debug)
			System.err.println("LOG (WebCrawler-Thread + " + Thread.currentThread().getId() + " ): " + msg);
	}


	public void setDebug(boolean debug) {
		
		this.debug = debug;
		
	}


	public void addFilterContentType(String contentType) {
		
		contentTypes.add(contentType);
		
	}

}