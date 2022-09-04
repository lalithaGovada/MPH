package com.mph.service;

import com.mph.model.Price;

import java.util.List;

public interface IPriceService {
    List<Price> getAllPrices();
    Price getLatestPrice();
    Price getLatestPriceByInstrumentName(String instrumentName);
}