package task2.model;

import java.util.Map;

// TODO: delMe
public class PricesList {
    private Map<String, Map<String, String>> pricesList;

    public PricesList(Map<String, Map<String, String>> pricesList) {
	this.pricesList = pricesList;
    }

    public void setPricesList(Map<String, Map<String, String>> pricesList) {
	this.pricesList = pricesList;
    }

    public Map<String, Map<String, String>> getPricesList() {
	return pricesList;
    }
}
