package task1.service;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.util.EntityUtils;
import task1.model.Country;
import task1.model.Number;
import task1.model.ResponseModel;
import task1.util.HttpUtil;

public class ConnectionService {
    private static final String URL_FREE_COUNTRY = "https://onlinesim.io/api/getFreeCountryList";
    private static final String URL_FREE_PHONE = "https://onlinesim.io/api/getFreePhoneList?country=";
    private final CloseableHttpClient httpClient;

    public ConnectionService() {
	this.httpClient = HttpUtil.getClient();
    }

    public Map<String, List<Number>> getCountryNumbers() {
	Map<String, List<Number>> countryNumbers = new HashMap<>();
	List<Country> countryList = null;
	try {
	    countryList = getCountryListFromUrl(new URI(URL_FREE_COUNTRY));
	} catch (URISyntaxException e) {
	    e.printStackTrace();
	}
	if (Objects.isNull(countryList)) {
	    return Collections.emptyMap();
	}
	countryList.forEach(country -> {
	    URI uri = URI.create(URL_FREE_PHONE + country.getId());
	    countryNumbers.put(country.getName(), getNumbersFromUrl(uri));
	});
	return countryNumbers;
    }

    private List<Number> getNumbersFromUrl(URI url) {
	final HttpGet request = new HttpGet(url);
	List<Number> numbers = Collections.emptyList();
	try (CloseableHttpResponse response = httpClient.execute(request)) {
	    final String jsonString = EntityUtils.toString(response.getEntity());
	    numbers = new ObjectMapper().readValue(jsonString, ResponseModel.class).getNumbers();
	} catch (IOException e) {
	    e.printStackTrace();
	}
	return numbers;
    }

    private List<Country> getCountryListFromUrl(URI url) {
	final HttpGet request = new HttpGet(url);
	List<Country> countryList = Collections.emptyList();
	try (CloseableHttpResponse response = httpClient.execute(request)) {
	    final String jsonString = EntityUtils.toString(response.getEntity());
	    countryList = new ObjectMapper().readValue(jsonString, ResponseModel.class).getCountryList();
	} catch (IOException e) {
	    e.printStackTrace();
	}
	return countryList;
    }
}
