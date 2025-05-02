package com.demo.BackendApi.controller;

import com.demo.BackendApi.service.BackendService;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.net.URL;
import java.text.ParseException;
import java.util.Map;

@RestController
@RequestMapping("/coindesk")
public class CurrencyController {

    @Autowired
    private BackendService service;

    @GetMapping("/raw")
    public JsonNode getRaw() throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readTree(new URL("https://kengp3.github.io/blog/coindesk.json"));
    }

    @GetMapping("/transformed")
    public Map<String, Object> getTransformed() throws IOException, ParseException {
        String url = "https://kengp3.github.io/blog/coindesk.json";
        ObjectMapper mapper = new ObjectMapper();
        JsonNode root = mapper.readTree(new URL(url));
        return service.getTransformedData(root);
    }
}
