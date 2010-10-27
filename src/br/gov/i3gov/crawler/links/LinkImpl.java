package br.gov.i3gov.crawler.links;

public class LinkImpl implements Link{

	private String url = null;
	private Long depth = null;
	private String contentType = null;
	private Long rotulo = null;
	
	
	public Long getDepth() {
		// TODO Auto-generated method stub
		return depth;
	}

	
	public String getUrl() {
		// TODO Auto-generated method stub
		return url;
	}


	public void setDepth(Long deep) {
		
		this.depth = deep;
		
	}


	public void setUrl(String url) {
		
		this.url = url.trim();
	}
	
	public int hashCode()
	{
		return url.hashCode();
	}
	

	public boolean equals(Object obj)
	{	
		if(obj == null)
			return false;
		
		if(url == null)
			return false;
		
		if(obj instanceof LinkImpl)
		{
			LinkImpl link = (LinkImpl) obj;
			
			return url.equals(link.getUrl());
		}
	
		return false;
	}


	public String getContentType() {
		return contentType;
	}


	public void setContentType(String contentType) {
		this.contentType = contentType;
	}


	public Long getRotulo() {
		// TODO Auto-generated method stub
		return rotulo;
	}


	public void setRotulo(Long rotulo) {

		this.rotulo = rotulo;
		
	}

}
