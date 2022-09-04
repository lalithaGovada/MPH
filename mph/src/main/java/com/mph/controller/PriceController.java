package com.mph.controller;

import com.mph.model.Price;
import com.mph.service.IPriceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/price")
public class PriceController {

    @Autowired
    private IPriceService priceService;

    @RequestMapping("/latest-price/{instrument-name}")
    public Price getLatestPriceByInstrumentName(@PathVariable("instrument-name") String instrumentName) {
        return priceService.getLatestPriceByInstrumentName(instrumentName);
    }

    @RequestMapping("/latest-price")
    public Price getLatestPrice() {
        return priceService.getLatestPrice();
    }

    @RequestMapping("/all")
    public List<Price> getLatestPriceByInstrumentName()  {
        return priceService.getAllPrices();
    }
}