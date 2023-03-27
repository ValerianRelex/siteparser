package task_1.service;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.ProxyAuthenticationStrategy;
import org.apache.http.util.EntityUtils;
import task_1.model.Country;
import task_1.model.Number;
import task_1.model.ResponseModel;

public class ConnectionService {
    private static final String URL_FREE_COUNTRY = "https://onlinesim.io/api/getFreeCountryList";
    private static final String URL_FREE_PHONE = "https://onlinesim.io/api/getFreePhoneList?country=";

    //    private static String PROXY_HOST = "142.132.168.249";
    //    private static int PROXY_PORT = 58823;
    //    private static String PROXY_USER = "vb7igdwfj4";
    //    private static String PROXY_PASSWORD = "xl0sb8c6jpg72z3";

    private CloseableHttpClient httpClient;
    private HttpGet request;

    public ConnectionService() {
	init();
    }

    // я так думаю, что лучше бы вынести этот метод в отдельный класс
    private void init() {
	//        HttpHost proxy = new HttpHost(PROXY_HOST, PROXY_PORT);
	//
	//        CredentialsProvider credentialsProvider = new BasicCredentialsProvider();
	//        credentialsProvider.setCredentials(new AuthScope(PROXY_HOST, PROXY_PORT),
	//                        new UsernamePasswordCredentials(PROXY_USER, PROXY_PASSWORD));

	RequestConfig config = RequestConfig.custom()
			//                        .setProxy(proxy)
			.setConnectTimeout(5000).setSocketTimeout(5000).setAuthenticationEnabled(true).build();

	request = new HttpGet();
	request.setConfig(config);

	HttpClientBuilder builder = HttpClientBuilder.create().setDefaultRequestConfig(config)
			//                        .setDefaultCredentialsProvider(credentialsProvider)
			.setProxyAuthenticationStrategy(new ProxyAuthenticationStrategy());
	//                        .setRoutePlanner(new DefaultProxyRoutePlanner(proxy));

	httpClient = builder.build();
    }

    public Map<String, List<Number>> getCountryNumbers() throws IOException, URISyntaxException {
	Map<String, List<Number>> countryNumbers = new HashMap<>();
	getCountryListFromUrl(new URI(URL_FREE_COUNTRY)).stream().forEach(country -> {
	    int id = country.getId();
	    URI uri = URI.create(URL_FREE_PHONE + id);
	    countryNumbers.put(country.getName(), getNumbersFromUrl(uri));
	});
	return countryNumbers;
    }

    private List<Number> getNumbersFromUrl(URI url) {
	final HttpGet getRequest = new HttpGet(url);
	String jsonString;
	List<Number> numbers = Collections.emptyList();
	try {
	    jsonString = EntityUtils.toString(httpClient.execute(getRequest).getEntity());
	    numbers = new ObjectMapper().readValue(jsonString, ResponseModel.class).getNumbers();
	} catch (JsonProcessingException e) {
	    e.printStackTrace();
	} catch (IOException e) {
	    e.printStackTrace();
	}
	return numbers;
    }

    private List<Country> getCountryListFromUrl(URI url) throws IOException {
	final HttpGet getRequest = new HttpGet(url);
	final String jsonString = EntityUtils.toString(httpClient.execute(getRequest).getEntity());
	return new ObjectMapper().readValue(jsonString, ResponseModel.class).getCountryList();
    }
}
