import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Date;

import br.gov.i3gov.core.CrawlerListener;
import br.gov.i3gov.crawler.links.BufferLinks;
import br.gov.i3gov.crawler.links.Link;
import br.gov.i3gov.crawler.links.LinkImpl;
import br.gov.i3gov.crawler.links.RAMQueueBufferLinks;
import br.gov.i3gov.crawler.tools.MultiThreadCrawler;
import br.gov.i3gov.crawler.tools.MultiThreadCrawlerImpl;

import com.gargoylesoftware.htmlunit.Page;
import com.gargoylesoftware.htmlunit.html.HtmlPage;



public class MainTeste implements CrawlerListener{
	
	private static long numPages = 0;
	private static long totalBytes = 0;
	private static long tempoTotal = 0;
	private static long tempoBase = 0;
	
	BufferedWriter bw;

	public static void main(String[] args) throws IOException {
		
		MainTeste teste = new MainTeste();
		teste.bw = new BufferedWriter(new FileWriter("urls.out"));
				
		BufferLinks bufferLinks = new RAMQueueBufferLinks();
		Link link = new LinkImpl();
		link.setUrl("http://www.mct.gov.br/");
		link.setDepth(new Long(0));
		link.setRotulo(new Long(2));
		bufferLinks.push(link);
		bufferLinks.addFiltro("http://www.mct.gov.br.*", new Long(2));
		
		Link link2 = new LinkImpl();
		link2.setUrl("http://extranet.agricultura.gov.br/pubacs_cons/!ap_detalhe_noticia_cons_web");
		link2.setDepth(new Long(0));
		link2.setRotulo(new Long(1));
		bufferLinks.push(link2);
		bufferLinks.addFiltro("http://extranet.agricultura.gov.br.*", new Long(1));
		
		tempoBase = (new Date()).getTime();
		
		MultiThreadCrawler crawler = new MultiThreadCrawlerImpl();
		crawler.setBufferLinks(bufferLinks);
		crawler.setMaxThreads(32);
		crawler.addCrawlerListener(teste);
		crawler.setMaxDepth(1);
		crawler.setDebug(true);
		crawler.startCrawler();
		
		teste.bw.close();
	}


	synchronized public void processPage(Page page, Link link) {
	
		
		try {
		/*	long memoriaTotal = Runtime.getRuntime().totalMemory();
			long memoriaLivre = Runtime.getRuntime().freeMemory();
			long porcentagemUsada = ((memoriaTotal - memoriaLivre)*100)/memoriaTotal;
			
			System.out.println("Memoria Total = " + memoriaTotal/(1024*1024) + "Mb");
			System.out.println("Memória Disponivel = " + memoriaLivre/(1024*1024) + "Mb");
			System.out.println("Memória Utilizada = " + (memoriaTotal - memoriaLivre)/(1024*1024) + "Mb");
			System.out.println("Porcentagem de memória utilizada = " + porcentagemUsada + "%");
			*/
			numPages++;
			Date dateCurrent = new Date();
			long size = page.getWebResponse().getContentAsBytes().length;
			tempoTotal = dateCurrent.getTime() - tempoBase;
			totalBytes += size;
			long velocidadeMedia = (totalBytes/(tempoTotal/1000))/1024;
			String relatorio = " - " + dateCurrent + " - " + size +" bytes  - " + totalBytes + " bytes" + " - " + velocidadeMedia + " Kb/s - " + tempoTotal/1000 + "s";
			String log = "( Thread : " + Thread.currentThread().getId() + " )" + numPages + " - " + page.getWebResponse().getRequestSettings().getUrl().toString() + relatorio + " md5 = " + getTextoMD5(page)+ "\n";
			
			bw.write(log);
			bw.flush();
			System.out.print(log);
		} catch (IOException e) {
			System.exit(0);
			e.printStackTrace();
		}		
	}
	
	private String getTextoMD5(Page page)
	{
		String texto = "";
		
		if(page instanceof HtmlPage)
		{
			HtmlPage html = (HtmlPage)page;
			
			texto = md5(html.asText());
		}
		
		return texto;
	}
	
	private String md5(String senha){  
		  
		MessageDigest md = null;
		
		try {  
			md = MessageDigest.getInstance("MD5");  
		} catch (NoSuchAlgorithmException e) {  
		
			e.printStackTrace();  
		}  
		
		BigInteger hash = new BigInteger(1, md.digest(senha.getBytes()));  
		
		return hash.toString(16);  
	} 
}

