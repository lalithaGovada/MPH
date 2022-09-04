package com.mph.dao;

import com.mph.model.Price;

import java.util.List;

public interface IPriceDao {
    void saveOrUpdate(List<Price> prices);
    void saveOrUpdate(Price price);
    List<Price> getAllPriceList();
    Price getLatestPrice();
    Price getLatestPriceByInstrumentName(String name);
}
