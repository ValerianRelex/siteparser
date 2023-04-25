package task2.service;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.databind.ObjectMapper;
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
    private static final String URL_FOR_PARSING = "https://onlinesim.io/price-list";
    private static final String XPATH_COUNTRY_BLOCK = "//div[@class='col-md-2 col-xs-6 country-block no-padding'] [@style='overflow-x: scroll;']";
    private static final String XPATH_PRICE_LIST_BLOCK = "//div[@class='service-block']";
    private static final String XPATH_COUNTRY_NAME_BLOCK = "//div[@class='col-md-12 text-center no-padding']";

    private static final BrowserVersion BROWSER_VERSION = BrowserVersion.CHROME;
    private final SilentCssErrorHandler cssErrorHandler = new SilentCssErrorHandler();
    private static final int JAVA_SCRIPT_WAIT_SECS = 3000;
    private static final boolean SUPPORT_INSECURE_SSL = true;
    private static final boolean HALT_ON_JS_ERROR = false;

    private final WebClient webClient;

    public PriceParser() {
	try (final WebClient webClientInit = new WebClient(BROWSER_VERSION)) {
	    webClientInit.getOptions().setJavaScriptEnabled(true);
	    webClientInit.getOptions().setThrowExceptionOnScriptError(HALT_ON_JS_ERROR);
	    webClientInit.getOptions().setUseInsecureSSL(SUPPORT_INSECURE_SSL);
	    webClientInit.getOptions().setCssEnabled(false);
	    webClientInit.setCssErrorHandler(cssErrorHandler);
	    webClientInit.getOptions().setAppletEnabled(false);
	    webClientInit.waitForBackgroundJavaScript(JAVA_SCRIPT_WAIT_SECS);
	    webClientInit.setAjaxController(new NicelyResynchronizingAjaxController());
	    webClientInit.setJavaScriptErrorListener(new SilentJavaScriptErrorListener());
	    this.webClient = webClientInit;
	}
    }

    public String parse() {
	try {
	    final HtmlPage startPage = webClient.getPage(URL_FOR_PARSING);
	    final Map<String, Map<String, Float>> pricesList = getPricesListForAllCountry(startPage);
	    final ObjectMapper mapper = new ObjectMapper();
	    final String jsonString = mapper.writeValueAsString(pricesList);
	    final String jsonStringForConsole = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(pricesList);
	    // TODO: оставить вывод в консольку, пока не реализую вывод с помощью JSP
	    System.out.println(jsonStringForConsole);
	    return jsonString;
	} catch (IOException e) {
	    System.err.println(e.getMessage());
	    return e.getMessage();
	}
    }

    private Map<String, Map<String, Float>> getPricesListForAllCountry(HtmlPage startPage) {
	Map<String, Map<String, Float>> pricesList = new HashMap<>();
	List<HtmlDivision> allCountryPath = startPage.getByXPath(XPATH_COUNTRY_BLOCK);

	allCountryPath.forEach(countryPath -> {
	    HtmlAnchor countryLink = (HtmlAnchor) countryPath.getElementsByTagName("a").get(0);
	    try {
		HtmlPage countryPage = countryLink.click();
		// TODO: может стоит использовать класс Optional для обработки ситуаций с NULL !?
		String countryName = getCountryName(countryPage);
		Map<String, Float> priceList = getPriceList(countryPage);
		pricesList.put(countryName, priceList);
		countryPage.remove();
	    } catch (IOException e) {
		e.printStackTrace();
	    }
	});
	return pricesList;
    }

    private Map<String, Float> getPriceList(HtmlPage page) {
	final List<HtmlDivision> serviceBlock = page.getByXPath(XPATH_PRICE_LIST_BLOCK);
	final Map<String, Float> priceList = new HashMap<>();
	final String[] serviceName = new String[1];

	serviceBlock.forEach(htmlDivision -> {
	    DomNodeList<HtmlElement> span = htmlDivision.getElementsByTagName("span");
	    span.forEach(element -> {
		String key = element.getAttributes().getNamedItem("class").getNodeValue();
		if ("price-name".equalsIgnoreCase(key)) {
		    serviceName[0] = element.getVisibleText();
		}
		if ("price-text".equalsIgnoreCase(key)) {
		    priceList.put(serviceName[0], Float.valueOf(element.getVisibleText()));
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
