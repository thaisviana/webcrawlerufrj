package br.gov.i3gov.crawler.links;

import com.sun.swing.internal.plaf.synth.resources.synth;

/**
 * 
 * @author Tiago
 * 
 * Interface Responsável por gerenciar o buffer de links descobertos pelos crawler
 *
 */
public interface BufferLinks {
	
	/**
	 * 
	 * @param link
	 * 
	 * Método responsável por adicionar um link ao buffer de links
	 */
	public void push(Link link);
	
	/**
	 * Método Responsável por retornar e descartar um link do buffer de links
	 * 
	 * @return
	 */
	public Link pop();
	
	/**
	 * Retorn true se há links no buffer e false caso contrário
	 * 
	 * @return
	 */
	public boolean isEmpty();
	
	public boolean inProcess();
	
	public void notifyProcess();
	
	public void freeProcess();
	
	public long getNumberLinksVisited();
	
	public void addFiltro(String expRegular);
	
	public void addFiltro(String expRegular, Long rotulo);

}
