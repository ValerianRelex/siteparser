package task_2.service;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.NicelyResynchronizingAjaxController;
import com.gargoylesoftware.htmlunit.SilentCssErrorHandler;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.DomNodeList;
import com.gargoylesoftware.htmlunit.html.HtmlAnchor;
import com.gargoylesoftware.htmlunit.html.HtmlDivision;
import com.gargoylesoftware.htmlunit.html.HtmlElement;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.javascript.SilentJavaScriptErrorListener;

public class PriceParser {
    private final static String URL_FOR_PARSING = "https://onlinesim.io/price-list";
    private final static String XPATH_COUNTRY_BLOCK = "//div[@class='col-md-2 col-xs-6 country-block no-padding'] [@style='overflow-x: scroll;']";
    private final static String XPATH_PRICE_LIST_BLOCK = "//div[@class='service-block']";
    private final static String XPATH_COUNTRY_NAME_BLOCK = "//div[@class='col-md-12 text-center no-padding']";

    private final BrowserVersion browserVersion = BrowserVersion.CHROME;
    private final SilentCssErrorHandler cssErrorHandler = new SilentCssErrorHandler();
    private final int javaScriptWaitSecs = 3000;
    private final boolean supportInsecureSSL = true;
    private final boolean haltOnJSError = false;

    private final WebClient webClient;

    public PriceParser() {
	try (final WebClient webClient = new WebClient(browserVersion)) {
	    webClient.getOptions().setJavaScriptEnabled(true);
	    webClient.getOptions().setThrowExceptionOnScriptError(haltOnJSError);
	    webClient.getOptions().setUseInsecureSSL(supportInsecureSSL);
	    webClient.getOptions().setCssEnabled(false);
	    webClient.setCssErrorHandler(cssErrorHandler);
	    webClient.getOptions().setAppletEnabled(false);
	    webClient.waitForBackgroundJavaScript(javaScriptWaitSecs);
	    webClient.setAjaxController(new NicelyResynchronizingAjaxController());
	    webClient.setJavaScriptErrorListener(new SilentJavaScriptErrorListener());
	    this.webClient = webClient;
	}
    }

    public Map<String, Map<String, String>> parse() {
	try {
	    HtmlPage startPage = webClient.getPage(URL_FOR_PARSING);
	    Map<String, Map<String, String>> pricesList = getPricesListForAllCountry(startPage);

	    //===
	    // Здесь реализую построение джейсончика. И вторым этапом - возврат джейсончика. И вывод его на страничку
	    //===

	    // вывод значений ключа в консоль
	    //	    for (Map.Entry<String, Map<String, String>> entry : pricesList.entrySet()) {
	    //		System.out.println(entry.getKey() + " : " + entry.getValue());
	    //	    }
	    return pricesList;
	} catch (IOException e) {
	    throw new RuntimeException(e.getCause());
	}
    }

    private Map<String, Map<String, String>> getPricesListForAllCountry(HtmlPage startPage) {
	Map<String, Map<String, String>> pricesList = new HashMap<>();
	List<HtmlDivision> allCountryPath = startPage.getByXPath(XPATH_COUNTRY_BLOCK);

	allCountryPath.forEach(countryPath -> {
	    HtmlAnchor countryLink = (HtmlAnchor) countryPath.getElementsByTagName("a").get(0);
	    try {
		HtmlPage countryPage = countryLink.click();
		// TODO: может стоит использовать класс Optional для обработки ситуаций с NULL !?
		String countryName = getCountryName(countryPage);
		Map<String, String> priceList = getPriceList(countryPage);
		pricesList.put(countryName, priceList);
		countryPage.remove();
	    } catch (IOException e) {
		e.printStackTrace();
	    }
	});
	return pricesList;
    }

    private Map<String, String> getPriceList(HtmlPage page) {
	final List<HtmlDivision> serviceBlock = page.getByXPath(XPATH_PRICE_LIST_BLOCK);
	final Map<String, String> priceList = new HashMap<>();
	final String[] serviceName = new String[1];

	serviceBlock.forEach(htmlDivision -> {
	    DomNodeList<HtmlElement> span = htmlDivision.getElementsByTagName("span");
	    span.forEach(element -> {
		String key = element.getAttributes().getNamedItem("class").getNodeValue();
		if ("price-name".equalsIgnoreCase(key)) {
		    serviceName[0] = element.getVisibleText();
		}
		if ("price-text".equalsIgnoreCase(key)) {
		    priceList.put(serviceName[0], element.getVisibleText());
		}
	    });
	});
	return priceList;
    }

    private String getCountryName(HtmlPage page) {
	List<HtmlDivision> country = page.getByXPath(XPATH_COUNTRY_NAME_BLOCK);

	return country.stream().map(c -> c.getElementsByTagName("span").get(0).getVisibleText()).findFirst()
			.orElse(null);
    }
}
