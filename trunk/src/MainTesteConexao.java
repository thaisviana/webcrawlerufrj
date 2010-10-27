import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import com.gargoylesoftware.htmlunit.FailingHttpStatusCodeException;
import com.gargoylesoftware.htmlunit.HttpMethod;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.WebRequestSettings;
import com.gargoylesoftware.htmlunit.WebResponse;
import com.gargoylesoftware.htmlunit.WebResponseImpl;


public class MainTesteConexao {

	/**
	 * @param args
	 * @throws IOException 
	 * @throws MalformedURLException 
	 * @throws FailingHttpStatusCodeException 
	 */
	public static void main(String[] args) throws FailingHttpStatusCodeException, MalformedURLException, IOException {
		
		WebClient webClient = new WebClient();
		webClient.setCssEnabled(false);
		webClient.setJavaScriptEnabled(false);
		webClient.setActiveXNative(false);
		webClient.setAppletEnabled(false);
		webClient.setPopupBlockerEnabled(false);
		webClient.setThrowExceptionOnScriptError(false);
		//webClient.getWebConnection().
		
		String url = "http://www.irs.gov/pub/irs-pdf/fw4.pdf";
		WebRequestSettings request = new WebRequestSettings(new URL(url));
		request.setHttpMethod(HttpMethod.HEAD);
		WebResponse response = webClient.getWebConnection().getResponse(request);
		//request.get
		System.out.println(response.getContentType());
		System.out.println(response.getResponseHeaders());
		System.out.println("download em andamento");
		webClient.getPage(url);
		System.out.println("download efetuado");
		
		/*Page page = webClient.getPage(request);
		
		System.out.println(page.getWebResponse().getContentType());
		if(page instanceof HtmlPage)
			System.out.println(((HtmlPage) page).asText());
		else
			System.out.println(page.getClass());
*/
	}

}
