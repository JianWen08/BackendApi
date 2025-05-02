package com.demo.BackendApi.model;

import javax.persistence.*;

@Entity
@Table(name = "currency")
public class Currency {

    @Id
    @Column(name = "code")
    private String code;
    @Column(name = "symbol")
    private String symbol;
    @Column(name = "rate")
    private String rate;
    @Column(name = "description")
    private String description;
    @Column(name = "rate_float")
    private String rate_float;

    public Currency() {
    }

    public Currency(String code, String symbol, String rate, String description, String rate_float) {
        this.code = code;
        this.symbol = symbol;
        this.rate = rate;
        this.description = description;
        this.rate_float = rate_float;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public String getRate() {
        return rate;
    }

    public void setRate(String rate) {
        this.rate = rate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getRate_float() {
        return rate_float;
    }

    public void setRate_float(String rate_float) {
        this.rate_float = rate_float;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

}
