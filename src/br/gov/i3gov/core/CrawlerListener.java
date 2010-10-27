package br.gov.i3gov.core;

import br.gov.i3gov.crawler.links.Link;

import com.gargoylesoftware.htmlunit.Page;

/**
 * Interface para receber os eventos de página encontrada pelo crawler.
 * A classe interessada em processar as páginas encontradas pelo crawler
 * devem implementar esta interface, e o objeto criado com esta classe
 * é registrada como um componente, usando o método <code>setCrawlerListener</code> da
 * interface UnitCrawler. Quando uma página for encontrada o método
 * <code>processPage</code> é executado.
 * 
 *  @see UnitCrawler
 *  
 *  @author Tiago Santos
 *  @version 1.0 30/07/10
 *  @since 1.0
 */
public interface CrawlerListener {
	
	/**
	 * Invocado quando uma página web é encontrada
	 */
	public void processPage(Page page, Link link);

}
