package task1.util;

import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.ProxyAuthenticationStrategy;
import org.apache.http.impl.conn.DefaultProxyRoutePlanner;

public class HttpUtil {
    private static final String PROXY_HOST = "142.132.168.249";
    private static final int PROXY_PORT = 58823;
    private static final String PROXY_USER = "vb7igdwfj4";
    private static final String PROXY_PASSWORD = "xl0sb8c6jpg72z3";
    private static final int TIME_OUT = 5000;

    private HttpUtil() {
    }

    public static CloseableHttpClient getClient() {
	RequestConfig config = RequestConfig.custom().setConnectTimeout(TIME_OUT).setSocketTimeout(TIME_OUT)
			.setAuthenticationEnabled(true).build();

	HttpClientBuilder builder = HttpClientBuilder.create().setDefaultRequestConfig(config)
			.setProxyAuthenticationStrategy(new ProxyAuthenticationStrategy());

	return builder.build();
    }

    public static CloseableHttpClient getClientWithProxy() {
	HttpHost proxy = new HttpHost(PROXY_HOST, PROXY_PORT);

	CredentialsProvider credentialsProvider = new BasicCredentialsProvider();
	credentialsProvider.setCredentials(new AuthScope(PROXY_HOST, PROXY_PORT),
			new UsernamePasswordCredentials(PROXY_USER, PROXY_PASSWORD));

	RequestConfig config = RequestConfig.custom().setProxy(proxy).setConnectTimeout(TIME_OUT)
			.setSocketTimeout(TIME_OUT).setAuthenticationEnabled(true).build();

	HttpClientBuilder builder = HttpClientBuilder.create().setDefaultRequestConfig(config)
			.setDefaultCredentialsProvider(credentialsProvider)
			.setProxyAuthenticationStrategy(new ProxyAuthenticationStrategy())
			.setRoutePlanner(new DefaultProxyRoutePlanner(proxy));

	return builder.build();
    }
}
