package task_2.service;

import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.ProxyAuthenticationStrategy;
import org.apache.http.util.EntityUtils;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class PriceParser
{
    private static String PROXY_HOST = "142.132.168.249";
    private static int PROXY_PORT = 58823;
    private static String PROXY_USER = "vb7igdwfj4";
    private static String PROXY_PASSWORD = "xl0sb8c6jpg72z3";

    private static CloseableHttpClient httpClient;

    private static String URL_PRICE_LIST = "http://onlinesim.io/price-list";

    public PriceParser() {
	RequestConfig config = RequestConfig.custom()
			.setConnectTimeout(5000).setSocketTimeout(5000).setAuthenticationEnabled(true).build();

	HttpClientBuilder builder = HttpClientBuilder.create().setDefaultRequestConfig(config)
			.setProxyAuthenticationStrategy(new ProxyAuthenticationStrategy());

	httpClient = builder.build();
    }

    public String startParse() throws Exception {
	final HttpGet getRequest = new HttpGet(URL_PRICE_LIST);

	CloseableHttpResponse httpResponse = httpClient.execute(getRequest);

	String responseString = EntityUtils.toString(httpResponse.getEntity());

	System.out.println(responseString);

	Connection connection = Jsoup.connect(URL_PRICE_LIST)
			.ignoreContentType(true)
			.userAgent("Mozilla");

	Document document = connection.get();

	document.getElementsContainingText("");

	Elements elementsByClass1 = document.getElementsByClass("price-name");

	Elements elementsByClass2 = document.getElementsByClass("price-text");

	Element index = document.getElementById("index");

	Element country = document.getElementById("country-1");

	Elements elementsByClass = document.getElementsByClass("btn");

	// ======== !!!!!!!!!! ==============

	Elements allElements = document.getAllElements();

	Elements elements = allElements.nextAll();

	Elements elements1 = elements.nextAll();

	// ======== !!!!!!!!!! ==============


	Elements title = document.getElementsByAttributeStarting("title");

	Elements elementsContainingText = document.getElementsContainingText("price-name");

//	Elements elementsByClass3 = index.getElementsByClass("col-md-2 col-xs-6 country-block no-padding");


	return document.toString(); // заглушка
    }
}
