package br.gov.i3gov.crawler.links;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.Stack;
import java.util.Vector;

import javax.sql.rowset.spi.SyncResolver;

/**
 * 
 * @author Tiago
 * 
 * Classe que implmenta o Buffer de links em memória RAM
 *
 */
public class RAMBufferLinks implements BufferLinks{
	
	Stack<Link> linksNãoVisitados;
	Set<Link> linksEncontrados;
	List<String> filtros;
	long numberThreads;
	
	public RAMBufferLinks()
	{
		linksNãoVisitados = new Stack<Link>();
		linksEncontrados = new HashSet<Link>();
		filtros = new Vector<String>();
		numberThreads = 0;
	
	}

	synchronized public Link pop() {
		
		return linksNãoVisitados.pop();
	}

	
	synchronized public void push(Link link) {
		
		if(validateLink(link))
		{
			if(!linksEncontrados.contains(link))
			{
				linksNãoVisitados.push(link);
				linksEncontrados.add(link);
				notify();
			}
		}	
	}

	
	synchronized public boolean isEmpty() {
		
		return linksNãoVisitados.empty();
	}

	
	synchronized public void freeProcess() {
		// TODO Auto-generated method stub
		numberThreads--;
		if(numberThreads <= 0)
			notifyAll();
	}


	synchronized public boolean inProcess() {
		
		if(!isEmpty())
			return true;
		
		return numberThreads > 0;
	}


	synchronized public void notifyProcess() {
	
		numberThreads++;
		
	}
	
	synchronized public long getNumberLinksVisited()
	{
		return linksEncontrados.size() - linksNãoVisitados.size();
	}


	synchronized  public void addFiltro(String expRegular) {
		
		filtros.add(expRegular);
	}
	
	synchronized private boolean validateLink(Link link)
	{
		boolean value = false;
		
		if(filtros.size() == 0)
			return true;
		
		for(String filtro: filtros)
		{
			value = link.getUrl().matches(filtro);
			
			if(value == true) 
				break;
		}
		
		return value;
	}
  
	synchronized private String md5(String senha){  
		  
		MessageDigest md = null;
		
		try {  
			md = MessageDigest.getInstance("MD5");  
		} catch (NoSuchAlgorithmException e) {  
		
			e.printStackTrace();  
		}  
		
		BigInteger hash = new BigInteger(1, md.digest(senha.getBytes()));  
		
		return hash.toString(16);  
	}

	public void addFiltro(String expRegular, Long rotulo) {

		filtros.add(expRegular);
		
	}	
}

