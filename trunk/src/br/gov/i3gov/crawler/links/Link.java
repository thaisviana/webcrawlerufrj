package br.gov.i3gov.crawler.links;

public interface Link {
	
	public String getUrl();
	
	public Long getDepth();
	
	public void setUrl(String url);
	
	public void setDepth(Long deep);
	
	public boolean equals(Object obj);
	
	public String getContentType();
	
	public void setContentType(String contentType);
	
	public Long getRotulo();
	
	public void setRotulo(Long rotulo);

}
