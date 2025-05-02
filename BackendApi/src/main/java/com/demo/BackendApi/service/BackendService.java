package com.demo.BackendApi.service;

import com.demo.BackendApi.dao.CurrencyDAO;
import com.demo.BackendApi.repository.CurrencyRepository;
import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.text.ParseException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class BackendService {
    @Autowired
    private CurrencyRepository repository;

    public Map<String, Object> getTransformedData(JsonNode json) throws IOException, ParseException {
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        String updateTime = now.format(formatter);
        if (json == null) {
            return new HashMap<>();
        }
        List<CurrencyDAO> list = new ArrayList<>();
        JsonNode bpi = json.path("bpi");
        Map<String,String> currency = new HashMap<>();
        currency.put("USD","美金");
        currency.put("GBP","英鎊");
        currency.put("EUR","歐元");
        bpi.fields().forEachRemaining(entry -> {
            String code = entry.getKey();
            String rate = entry.getValue().path("rate").asText();
            String zh = currency.get(code);
            if (zh == null){
                zh = "未知";
            }
            list.add(new CurrencyDAO(code, zh, rate));
        });

        Map<String, Object> result = new HashMap<>();
        result.put("updateTime", updateTime);
        result.put("currencies", list);
        return result;
    }
}
