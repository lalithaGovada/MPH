package com.mph.dao;

import com.mph.model.Price;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Component
@Slf4j
public class PriceDao implements IPriceDao {

    private final List<Price> prices;

    @Autowired
    public PriceDao() {
        prices = new ArrayList<>();
    }

    @Override
    public void saveOrUpdate(List<Price> prices) {
        prices.forEach(this::saveOrUpdate);
    }

    @Override
    public void saveOrUpdate(Price price) {
        log.info("START - PriceDao saveOrUpdate");
        if (!changeCompare(price)) {
            log.info("PriceDao - no existing record found");
            prices.add(price);
        }
        log.info("END - PriceDao saveOrUpdate");
    }
    private boolean changeCompare(Price price) {
        log.info("START - PriceDao changeCompare");
        for (Price obj: prices) {
            return obj.equals(price);
        }
        log.info("END - PriceDao changeCompare");
        return false;
    }
    @Override
    public List<Price> getAllPriceList() {
        return prices;
    }

    @Override
    public Price getLatestPrice() {
        return prices.stream().max(Comparator.comparing(Price::getDate)).orElse(null);
    }

    @Override
    public Price getLatestPriceByInstrumentName(String name) {
        return prices
                .stream()
                .filter(p -> p.getInstrumentName().equals(name))
                .max(Comparator.comparing(Price::getDate))
                .orElse(null);
    }


}
