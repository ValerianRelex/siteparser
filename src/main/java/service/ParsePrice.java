package service;

import java.io.IOException;
import java.net.Authenticator;
import java.net.InetSocketAddress;
import java.net.PasswordAuthentication;
import java.net.Proxy;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class ParsePrice
{
    private static String PROXY_HOST = "142.132.168.249";
    private static int PROXY_PORT = 58823;
    private static String PROXY_USER = "vb7igdwfj4";
    private static String PROXY_PASSWORD = "xl0sb8c6jpg72z3";

    public String startParse() throws IOException, Exception {
	String url = "http://onlinesim.io/price-list";

//	Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress(PROXY_HOST, PROXY_PORT));
//
//	Authenticator.setDefault(new Authenticator() {
//	    protected PasswordAuthentication getPasswordAuthentication() {
//		return new PasswordAuthentication(PROXY_USER, PROXY_PASSWORD.toCharArray());
//	    }
//	});
//
	Connection connection = Jsoup.connect(url)
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
