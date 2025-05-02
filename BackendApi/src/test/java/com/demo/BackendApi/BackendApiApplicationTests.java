package com.demo.BackendApi;

import com.demo.BackendApi.dao.CurrencyDAO;
import com.demo.BackendApi.service.BackendService;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.text.ParseException;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class BackendApiApplicationTests {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Test
    void testGetTransformedData_validBpi() throws IOException, ParseException {
        // 1. 準備測試輸入 (模擬包含 bpi 節點的 JSON)
        String jsonString = "{\"bpi\": {\"USD\": {\"rate\": \"57,756.298\"}, \"GBP\": {\"rate\": \"43,984.02\"}}}";
        JsonNode jsonNode = objectMapper.readTree(jsonString);

        // 2. 執行被測試的方法
        BackendService service = new BackendService();
        Map<String, Object> result = service.getTransformedData(jsonNode);

        // 3. 驗證結果
        assertNotNull(result);
        assertNotNull(result.get("updateTime"));
        assertTrue(result.get("updateTime") instanceof String);

        List<CurrencyDAO> currencies = (List<CurrencyDAO>) result.get("currencies");
        assertNotNull(currencies);
        assertEquals(2, currencies.size());

        // 驗證 USD 資料
        CurrencyDAO usd = currencies.stream().filter(c -> c.getCode().equals("USD")).findFirst().orElse(null);
        assertNotNull(usd);
        assertEquals("USD", usd.getCode());
        assertEquals("美金", usd.getName());
        assertEquals("57,756.298", usd.getRate());

        // 驗證 GBP 資料
        CurrencyDAO gbp = currencies.stream().filter(c -> c.getCode().equals("GBP")).findFirst().orElse(null);
        assertNotNull(gbp);
        assertEquals("GBP", gbp.getCode());
        assertEquals("英鎊", gbp.getName());
        assertEquals("43,984.02", gbp.getRate());
    }

    @Test
    void testGetTransformedData_unknownCurrency() throws IOException, ParseException {
        // 1. 準備包含未知幣別的測試輸入
        String jsonString = "{\"bpi\": {\"USD\": {\"rate\": \"1.0\"}, \"JPY\": {\"rate\": \"130.0\"}}}";
        JsonNode jsonNode = objectMapper.readTree(jsonString);

        // 2. 執行被測試的方法
        BackendService service = new BackendService();
        Map<String, Object> result = service.getTransformedData(jsonNode);

        // 3. 驗證結果
        List<CurrencyDAO> currencies = (List<CurrencyDAO>) result.get("currencies");
        assertNotNull(currencies);
        assertEquals(2, currencies.size());

        // 驗證 JPY 資料
        CurrencyDAO jpy = currencies.stream().filter(c -> c.getCode().equals("JPY")).findFirst().orElse(null);
        assertNotNull(jpy);
        assertEquals("JPY", jpy.getCode());
        assertEquals("未知", jpy.getName());
        assertEquals("130.0", jpy.getRate());
    }

    @Test
    void testGetTransformedData_emptyBpi() throws IOException, ParseException {
        // 1. 準備 bpi 為空的測試輸入
        String jsonString = "{\"bpi\": {}}";
        JsonNode jsonNode = objectMapper.readTree(jsonString);

        // 2. 執行被測試的方法
        BackendService service = new BackendService();
        Map<String, Object> result = service.getTransformedData(jsonNode);

        // 3. 驗證結果
        assertNotNull(result);
        assertNotNull(result.get("updateTime"));
        List<CurrencyDAO> currencies = (List<CurrencyDAO>) result.get("currencies");
        assertNotNull(currencies);
        assertTrue(currencies.isEmpty());
    }

    @Test
    void testGetTransformedData_missingBpi() throws IOException, ParseException {
        // 1. 準備不包含 bpi 節點的測試輸入
        String jsonString = "{\"time\": {\"updated\": \"May 1, 2025 09:49:00 UTC\"}}";
        JsonNode jsonNode = objectMapper.readTree(jsonString);

        // 2. 執行被測試的方法
        BackendService service = new BackendService();
        Map<String, Object> result = service.getTransformedData(jsonNode);

        // 3. 驗證結果
        assertNotNull(result);
        assertNotNull(result.get("updateTime"));
        List<CurrencyDAO> currencies = (List<CurrencyDAO>) result.get("currencies");
        assertNotNull(currencies);
        assertTrue(currencies.isEmpty()); // 如果沒有 bpi 節點，預期 currencies 列表為空
    }

    @Test
    void testGetTransformedData_nullInput() throws IOException, ParseException {
        // 1. 準備 null 輸入
        JsonNode jsonNode = null;

        // 2. 執行被測試的方法
        BackendService service = new BackendService();
        Map<String, Object> result = service.getTransformedData(jsonNode);

        // 3. 驗證結果 (根據你的方法實現，預期行為是返回空 Map)
        assertNotNull(result);
        assertTrue(result.isEmpty()); // 假設 null 輸入返回空 Map
    }
}