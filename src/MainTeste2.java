import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigInteger;
import java.net.URL;
import java.net.URLConnection;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Date;

import org.apache.pdfbox.pdfparser.PDFParser;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDDocumentInformation;
import org.apache.pdfbox.util.PDFTextStripper;

import br.gov.i3gov.core.CrawlerListener;
import br.gov.i3gov.crawler.links.BufferLinks;
import br.gov.i3gov.crawler.links.Link;
import br.gov.i3gov.crawler.links.LinkImpl;
import br.gov.i3gov.crawler.links.RAMQueueBufferLinks;
import br.gov.i3gov.crawler.tools.MultiThreadCrawler;
import br.gov.i3gov.crawler.tools.MultiThreadCrawlerImpl;

import com.gargoylesoftware.htmlunit.Page;
import com.gargoylesoftware.htmlunit.html.HtmlPage;


public class MainTeste2 implements CrawlerListener{

	private static long numPages = 0;
	private static long totalBytes = 0;
	private static long tempoTotal = 0;
	private static long tempoBase = 0;
	
	BufferedWriter bw;

	public static void main(String[] args) throws IOException {
		
		MainTeste2 teste = new MainTeste2();
		teste.bw = new BufferedWriter(new FileWriter("urls.out"));
				
		BufferLinks bufferLinks = new RAMQueueBufferLinks();
		Link link = new LinkImpl();
		link.setUrl("http://www.anac.gov.br/imprensa/");
		link.setDepth(new Long(0));
		link.setRotulo(new Long(2));
		bufferLinks.push(link);
		bufferLinks.addFiltro("http://www.anac.gov.br/imprensa.*", new Long(2));
		
	/*	Link link2 = new LinkImpl();
		link2.setUrl("http://extranet.agricultura.gov.br/pubacs_cons/!ap_detalhe_noticia_cons_web");
		link2.setDepth(new Long(0));
		link2.setRotulo(new Long(1));
		bufferLinks.push(link2);
		bufferLinks.addFiltro("http://extranet.agricultura.gov.br.*", new Long(1));*/
		
		tempoBase = (new Date()).getTime();
		
		MultiThreadCrawler crawler = new MultiThreadCrawlerImpl();
		crawler.setBufferLinks(bufferLinks);
		crawler.setMaxThreads(32);
		crawler.addCrawlerListener(teste);
		crawler.setMaxDepth(1);
		crawler.setDebug(true);
		crawler.addFilterContentType("text/html");
		crawler.setDebug(false);
		crawler.startCrawler();
		
		
		teste.bw.close();
	}


	synchronized public void processPage(Page page, Link link) {
	
		
		try {
			numPages++;
			System.out.println("INFO : numPages = " + numPages);
			System.out.println("INFO : url = " + link.getUrl());
			System.out.println("INFO : depth = " + link.getDepth());
			System.out.println("INFO : contentType = " + link.getContentType());
			System.out.println("INFO : rotulo = " + link.getRotulo());
		
			/*URL url = new URL(link.getUrl());
			URLConnection connection = url.openConnection();
			PDFParser pdf = new PDFParser(connection.getInputStream());
			pdf.parse();
			PDDocument doc = pdf.getPDDocument();
			PDFTextStripper text = new PDFTextStripper();
			System.out.println(text.getText(doc));
			PDDocumentInformation info = doc.getDocumentInformation();
		      System.out.println( "Page Count=" + doc.getNumberOfPages() );
		      System.out.println( "Title=" + info.getTitle() );
		      System.out.println( "Author=" + info.getAuthor() );
		      System.out.println( "Subject=" + info.getSubject() );
		      System.out.println( "Keywords=" + info.getKeywords() );
		      System.out.println( "Creator=" + info.getCreator() );
		      System.out.println( "Producer=" + info.getProducer() );
		      System.out.println( "Creation Date=" + info.getCreationDate().toString() );
		      System.out.println( "Modification Date=" + info.getModificationDate().toString());
		      System.out.println( "Trapped=" + info.getTrapped() );      
			doc.close();*/
			
		} catch (Exception e) {
			
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
