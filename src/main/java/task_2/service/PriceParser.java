package task_2.service;

import java.io.IOException;
import java.net.MalformedURLException;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.NicelyResynchronizingAjaxController;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlPage;

public class PriceParser
{
    private static String URL_PRICE_LIST = "http://onlinesim.io/price-list";

    public PriceParser() {

    }

    public String startParse() {

	try (final WebClient webClient = new WebClient(BrowserVersion.CHROME)) {
	    webClient.getOptions().setJavaScriptEnabled(true);
	    webClient.getOptions().setCssEnabled(false);
	    webClient.getOptions().setUseInsecureSSL(true);
	    webClient.getOptions().setThrowExceptionOnFailingStatusCode(false);
	    webClient.getCookieManager().setCookiesEnabled(true);
	    webClient.setAjaxController(new NicelyResynchronizingAjaxController());
	    webClient.getOptions().setThrowExceptionOnScriptError(false);
	    webClient.getCookieManager().setCookiesEnabled(true);

	    final HtmlPage page = webClient.getPage(URL_PRICE_LIST);



	} catch (MalformedURLException e) {
	    e.printStackTrace();
	} catch (IOException e) {
	    e.printStackTrace();
	}

	//	Elements elementsByClass1 = document.getElementsByClass("price-name");
//	Elements elementsByClass2 = document.getElementsByClass("price-text");
//	Element index = document.getElementById("index");
//	Element country = document.getElementById("country-1");
//	Elements elementsByClass = document.getElementsByClass("btn");

	return String.valueOf("test"); // заглушка
    }
}
