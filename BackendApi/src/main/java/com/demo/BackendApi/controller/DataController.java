package com.demo.BackendApi.controller;

import com.demo.BackendApi.model.Currency;
import com.demo.BackendApi.repository.CurrencyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/data")
public class DataController {
    @Autowired
    private CurrencyRepository repository;

    @GetMapping
    public List<Currency> getAll() {
        return repository.findAll();
    }

    @PostMapping
    public ResponseEntity<Currency> add(@RequestBody Currency currency) {
        Currency savedCurrency = repository.save(currency);
        return new ResponseEntity<>(savedCurrency, HttpStatus.CREATED);
    }

    @PutMapping("/{code}")
    public ResponseEntity<Currency> update(@PathVariable String code, @RequestBody Currency updated) {
        Optional<Currency> existingCurrency = repository.findById(code);
        if (existingCurrency.isPresent()) {
            Currency c = existingCurrency.get();
            c.setSymbol(updated.getSymbol());
            c.setRate(updated.getRate());
            c.setDescription(updated.getDescription());
            c.setRate_float(updated.getRate_float());
            Currency updatedCurrency = repository.save(c);
            return new ResponseEntity<>(updatedCurrency, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{code}")
    public ResponseEntity<Void> delete(@PathVariable String code) {
        if (repository.existsById(code)) {
            repository.deleteById(code);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
