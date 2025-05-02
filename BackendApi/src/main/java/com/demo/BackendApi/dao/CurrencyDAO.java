package com.demo.BackendApi.dao;

public class CurrencyDAO {
    private String code;
    private String name;
    private String rate;

    public CurrencyDAO(String code, String name, String rate) {
        this.code = code;
        this.name = name;
        this.rate = rate;
    }

    public String getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    public String getRate() {
        return rate;
    }
}
