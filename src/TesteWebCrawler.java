import br.gov.i3gov.core.CrawlerListener;
import br.gov.i3gov.crawler.links.BufferLinks;
import br.gov.i3gov.crawler.links.Link;
import br.gov.i3gov.crawler.links.LinkImpl;
import br.gov.i3gov.crawler.links.RAMQueueBufferLinks;
import br.gov.i3gov.crawler.tools.MultiThreadCrawler;
import br.gov.i3gov.crawler.tools.MultiThreadCrawlerImpl;

import com.gargoylesoftware.htmlunit.Page;
import com.gargoylesoftware.htmlunit.html.HtmlPage;


public class TesteWebCrawler implements CrawlerListener{

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		TesteWebCrawler teste = new TesteWebCrawler();
		
		/* Cria link inicial para a navega��o web*/
		Link link = new LinkImpl();
		link.setUrl("http://www.mct.gov.br/");
		link.setDepth(new Long(0));
		
		/* Cria a buffer que cont�m os links a serem visitados */
		BufferLinks bufferLinks = new RAMQueueBufferLinks();
		
		/* Adiciona ao buffer o(s) link(s) iniciais para a navega��o na web */
		bufferLinks.push(link);
		
		/*express�o regular que indica quais urls devem ser navegadas. Nesse caso todas as urls deste dom�nio ser�o visitadas*/
		bufferLinks.addFiltro("http://www.mct.gov.br.*");
		
		/* Criar o crawler de busca e adiciona a ele o buffer a ser utilizado */
		MultiThreadCrawler crawler = new MultiThreadCrawlerImpl();
		crawler.setBufferLinks(bufferLinks);
		
		/* Seta o n�mero de threads usadas pelo crawler */
		crawler.setMaxThreads(32);
		
		/* Adiciona a classe ouvinte que ser�  respons�vel por receber as p�ginas visitadas */
		crawler.addCrawlerListener(teste);
		
		/* Adiciona o tipo de p�gina a ser visitado pelo crawler. O default � que todos os tipos sejam visistados */
		crawler.addFilterContentType("text/html");
		
		/* Inicia o crawler */
		crawler.startCrawler();

	}

	/* Este m�todo deve ser implementado pela classe que escuta o crawler 
	 * 
	 * A cada p�gina processada pelo crawler este m�todo � chamado.
	 * */
	
	public void processPage(Page page, Link link) {
		
		/*Imprime a url, o tipo e o conte�do das p�ginas html visitadas*/
		
		System.out.println(link.getUrl());
		System.out.println(link.getContentType());
		System.out.println(((HtmlPage)page).asText());
		System.out.println("*********************************************");
	}

}
