package br.gov.i3gov.core;

import br.gov.i3gov.crawler.links.BufferLinks;

/**
 * 
 * Unidade básica do Crawler Web.
 * 
 * @see CrawlerListener
 * @see BufferLinks
 * 
 * @author Tiago Santos
 * @version 1.0 30/07/10
 * @since 1.0
 * 
 */

public interface UnitCrawler extends Runnable {

	/**
	 *  Seta o objeto ouvi as páginas encontradas por essa unidade de crawler
	 */
	public void setCrawlerListener(CrawlerListener listener);
	
	/**
	 * Seta o buffer de links utilizados por essa unidade de crawler
	 */
	public void setBufferLinks(BufferLinks bufferLinks);
	
	public void setMaxDepth(int maxDepth);
	
	public void setDebug(boolean debug);
	
	public void addFilterContentType(String contentType);

}
