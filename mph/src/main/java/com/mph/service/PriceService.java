package com.mph.service;

import com.mph.dao.IPriceDao;
import com.mph.model.Price;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PriceService implements IPriceService {

    @Autowired
    private IPriceDao priceDao;

    @Override
    public List<Price> getAllPrices() {
        return priceDao.getAllPriceList();
    }

    @Override
    public Price getLatestPrice() {
        return priceDao.getLatestPrice();
    }

    @Override
    public Price getLatestPriceByInstrumentName(String instrumentName) {
        return priceDao.getLatestPriceByInstrumentName(instrumentName);
    }
}
