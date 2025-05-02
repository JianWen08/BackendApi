package com.demo.BackendApi.service;

import com.demo.BackendApi.model.Currency;
import com.demo.BackendApi.repository.CurrencyRepository;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.annotation.PostConstruct;
import java.io.IOException;

@Service
public class ApiService {

    private final RestTemplate restTemplate;
    private final CurrencyRepository coindeskRepository;
    private final String apiUrl = "https://kengp3.github.io/blog/coindesk.json";

    @Autowired
    public ApiService(RestTemplateBuilder restTemplateBuilder, CurrencyRepository coindeskRepository) {
        this.restTemplate = restTemplateBuilder.build();
        this.coindeskRepository = coindeskRepository;
    }

    @PostConstruct
    public void fetchDataAndSaveToDatabase() {
        try {
            String response = restTemplate.getForObject(apiUrl, String.class);
            ObjectMapper mapper = new ObjectMapper();
            JsonNode root = mapper.readTree(response);
            JsonNode bpiNode = root.get("bpi");

            if (bpiNode != null && bpiNode.isObject()) {
                bpiNode.fields().forEachRemaining(entry -> {
                    String code = entry.getKey();
                    JsonNode currencyData = entry.getValue();
                    if (currencyData.isObject()) {
                        String symbol = currencyData.get("symbol").asText();
                        String rate = currencyData.get("rate").asText();
                        String description = currencyData.get("description").asText();
                        String rateFloat = currencyData.get("rate_float").asText();

                        Currency coindesk = new Currency(code, symbol, rate, description, rateFloat);
                        coindeskRepository.save(coindesk);
                    } else {
                        System.err.println("Error: Currency data for " + code + " is not a JSON object.");
                    }
                });
                System.out.println("API 資料已成功載入到 H2 資料庫。");
            } else {
                System.out.println("API 回應中找不到 'bpi' 節點或它不是一個 JSON 物件。");
            }

        } catch (IOException e) {
            System.err.println("Error fetching or processing API data: " + e.getMessage());
        }
    }
}