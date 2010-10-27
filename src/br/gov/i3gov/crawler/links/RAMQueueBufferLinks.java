package br.gov.i3gov.crawler.links;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;
import java.util.Vector;

import org.apache.commons.collections.map.HashedMap;

/**
 * 
 * @author Tiago
 * 
 * Classe que implmenta o Buffer de links em mem�ria RAM
 *
 */
public class RAMQueueBufferLinks implements BufferLinks{
	
	private final Long ROTULO_DEFAULT = new Long(0);
	
	Queue<Link> linksN�oVisitados;
	Set<Link> linksEncontrados;
	Map<Long, List<String>> filtros;
	long numberThreads;
	
	public RAMQueueBufferLinks()
	{
		linksN�oVisitados = new LinkedList<Link>();
		linksEncontrados = new HashSet<Link>();
		filtros = new HashMap<Long, List<String>>();
		filtros.put(ROTULO_DEFAULT, new Vector<String>());
		
		numberThreads = 0;
	
	}
	

	synchronized public Link pop() {
		
		return linksN�oVisitados.poll();
	}


	synchronized public void push(Link link) {
		
		if(validateLink(link))
		{
			link.setUrl(link.getUrl().replaceAll("#.*", ""));
			if(!linksEncontrados.contains(link))
			{
				linksN�oVisitados.add(link);
				linksEncontrados.add(link);
				notify();
			}
		}	
	}

	
	synchronized public boolean isEmpty() {
		
		return linksN�oVisitados.isEmpty();
	}


	synchronized public void freeProcess() {
		// TODO Auto-generated method stub
		numberThreads--;
		if(numberThreads <= 0)
			notifyAll();
	}


	synchronized public boolean inProcess() {
		
		System.out.println("Thread " + Thread.currentThread().getId() + " : numberThreads -> " + numberThreads);
		
		if(!isEmpty())
			return true;
		
		return numberThreads > 0;
	}


	synchronized public void notifyProcess() {
	
		numberThreads++;
		
	}
	
	
	synchronized public long getNumberLinksVisited()
	{
		return linksN�oVisitados.size();
		//return linksEncontrados.size() - linksN�oVisitados.size();
	}


	synchronized  public void addFiltro(String expRegular) {
		
		filtros.get(ROTULO_DEFAULT).add(expRegular);
	}
	
	synchronized  public void addFiltro(String expRegular, Long rotulo) {
		
		List<String> filtro = filtros.get(rotulo);
		if(filtro == null)
		{
			filtro = new Vector<String>();
			filtros.put(rotulo, filtro);
		}
		
		filtro.add(expRegular);
	}
	
	synchronized private boolean validateLink(Link link)
	{
		boolean value = false;
		
		List<String> filtros;
		
		if(link.getRotulo() == null)
			filtros = this.filtros.get(ROTULO_DEFAULT);
		else
			filtros = this.filtros.get(link.getRotulo());
			
		
		if(filtros == null || filtros.size() == 0)
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
}

