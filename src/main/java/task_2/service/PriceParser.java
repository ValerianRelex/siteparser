package task_2.service;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.NicelyResynchronizingAjaxController;
import com.gargoylesoftware.htmlunit.SilentCssErrorHandler;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.WebClientOptions;
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
    private BrowserVersion browserVersion = BrowserVersion.CHROME;
    /**
     * {@link WebClient#waitForBackgroundJavaScript(long)}
     *
     * @default 15000
     */
    private int javaScriptWaitSecs = 3000;

    /**
     * Connect to servers that have any SSL certificate
     *
     * @see WebClientOptions#setUseInsecureSSL(boolean)
     */
    private boolean supportInsecureSSL = true;

    /**
     * If false will ignore JavaScript errors
     *
     * @default false
     */
    private boolean haltOnJSError = false;

    private static final SilentCssErrorHandler cssErrhandler = new SilentCssErrorHandler();

    public Map<String, Map<String, String>> parse() {
	try (final WebClient webClient = new WebClient(browserVersion)) {

	    webClient.getOptions().setJavaScriptEnabled(true);
	    webClient.getOptions().setThrowExceptionOnScriptError(haltOnJSError);
	    webClient.getOptions().setUseInsecureSSL(supportInsecureSSL);
	    webClient.getOptions().setCssEnabled(false);
	    webClient.setCssErrorHandler(cssErrhandler);
	    webClient.getOptions().setAppletEnabled(false);
	    webClient.waitForBackgroundJavaScript(javaScriptWaitSecs);
	    webClient.setAjaxController(new NicelyResynchronizingAjaxController());
	    webClient.setJavaScriptErrorListener(new SilentJavaScriptErrorListener());

	    final HtmlPage startPage = webClient.getPage(URL_FOR_PARSING);
	    Map<String, Map<String, String>> pricesList = getPricesListForAllCountry(startPage);

	    //===
	    // Здесь реализую построение джейсончика. И вторым этапом - возврат джейсончика. И вывод его на страничку
	    //===

	    // вывод значений ключа в консоль
	    for (Map.Entry<String, Map<String, String>> entry : pricesList.entrySet()) {
		System.out.println(entry.getKey() + " : " + entry.getValue());
	    }
	    return pricesList;
	} catch (Exception e) {
	    throw new RuntimeException(e);
	}
    }

    private Map<String, Map<String, String>> getPricesListForAllCountry(HtmlPage startPage) {
	Map<String, Map<String, String>> pricesList = new HashMap<>();

	// запрос - возврат списка стран
	List<HtmlDivision> allCountryPath = startPage.getByXPath(XPATH_COUNTRY_BLOCK);
	// получим кнопки стран, для нажатий
	List<DomNodeList<HtmlElement>> domNodeLists = allCountryPath.stream()
			.map(elem -> elem.getElementsByTagName("a")).collect(Collectors.toList());

	domNodeLists.forEach(htmlElements -> {
	    HtmlAnchor htmlElement = (HtmlAnchor) htmlElements.get(0);
	    try {
		HtmlPage page = htmlElement.click();
		// Название страны будет ключом в результативной коллекции Map<String, Map<String, String>>
		String countryName = getCountryName(page);
		// Получаем значение ключа - которое представляет прайс-лист для конкретного города.
		Map<String, String> priceList = getPriceList(page);
		// Заполним коллекцию, которую в последующем преобразуем в JSON и отдадим на вывод
		pricesList.put(countryName, priceList);

		page.remove();
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
