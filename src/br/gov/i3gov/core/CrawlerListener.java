package br.gov.i3gov.core;

import br.gov.i3gov.crawler.links.Link;

import com.gargoylesoftware.htmlunit.Page;

/**
 * Interface para receber os eventos de p�gina encontrada pelo crawler.
 * A classe interessada em processar as p�ginas encontradas pelo crawler
 * devem implementar esta interface, e o objeto criado com esta classe
 * � registrada como um componente, usando o m�todo <code>setCrawlerListener</code> da
 * interface UnitCrawler. Quando uma p�gina for encontrada o m�todo
 * <code>processPage</code> � executado.
 * 
 *  @see UnitCrawler
 *  
 *  @author Tiago Santos
 *  @version 1.0 30/07/10
 *  @since 1.0
 */
public interface CrawlerListener {
	
	/**
	 * Invocado quando uma p�gina web � encontrada
	 */
	public void processPage(Page page, Link link);

}
