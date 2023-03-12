package service;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.databind.ObjectMapper;
import model.Country;
import model.Number;
import model.ResponseModel;
import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.ProxyAuthenticationStrategy;
import org.apache.http.impl.conn.DefaultProxyRoutePlanner;
import org.apache.http.util.EntityUtils;

public class ConnectionService
{
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
                        .setConnectTimeout(5000)
                        .setSocketTimeout(5000)
                        .setAuthenticationEnabled(true)
                        .build();

        request = new HttpGet();
        request.setConfig(config);

        HttpClientBuilder builder = HttpClientBuilder.create()
                        .setDefaultRequestConfig(config)
//                        .setDefaultCredentialsProvider(credentialsProvider)
                        .setProxyAuthenticationStrategy(new ProxyAuthenticationStrategy());
//                        .setRoutePlanner(new DefaultProxyRoutePlanner(proxy));

        httpClient = builder.build();
    }

    public Map<String, List<Number>> getCountryNumbers() throws IOException, URISyntaxException
    {
        // получим список стран и их коды.
        List<Country> countryList = getCountryListFromUrl(new URI(URL_FREE_COUNTRY));

        Map<String, List<Number>> countryNumbers = new HashMap<>();
        for (int i = 0; i < countryList.size(); i++) {

            int id = countryList.get(i).getId();
            countryNumbers.put(countryList.get(i).getName(), getNumbersFromUrl(new URI(URL_FREE_PHONE + id)));
        }

        return countryNumbers;
    }

    private List<Number> getNumbersFromUrl(URI url) throws IOException {
        return getResponseModel(getResponse(url)).getNumbers();
    }

    private List<Country> getCountryListFromUrl(URI url) throws IOException {
        return getResponseModel(getResponse(url)).getCountryList();
    }

    private ResponseModel getResponseModel(CloseableHttpResponse response) throws IOException {
        String jsonString = EntityUtils.toString(response.getEntity());
        ResponseModel responseModel = new ObjectMapper().readValue(jsonString, ResponseModel.class);
        return responseModel;
    }

    private CloseableHttpResponse getResponse(URI uri) throws IOException {
        request.setURI(uri);
        return httpClient.execute(request);
    }
}
