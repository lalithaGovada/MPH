package com.mph.consumer;

import com.mph.helper.MessageProcessHelper;
import com.mph.model.Price;
import com.mph.service.IPriceService;
import com.mph.service.PriceService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.util.ReflectionTestUtils;


import java.util.List;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class MessageConsumerTest {

    @Autowired
    private MessageProcessHelper messageProcessHelper;
    @Autowired
    private IPriceService priceService;
    @InjectMocks
    private MessageConsumer consumer;


     @Test
    void testConsumer() {
         /* To test single message*/
        ReflectionTestUtils.setField(consumer, "messageProcessHelper", messageProcessHelper);
        String message = "106,EUR/USD, 1.1000,1.2000,01-06-2020 12:01:01:001";
        consumer.onMessage(message);
       Assertions.assertEquals(1, priceService.getAllPrices().size());
    }
    @Test
    void testConsumerMultipleMessages() {
        /* To test multiple message*/
        ReflectionTestUtils.setField(consumer, "messageProcessHelper", messageProcessHelper);
        String message =  "106,EUR/USD,1.1000,1.2000,01-06-2020 12:01:01:001\n" +
                          "107,EUR/JPY,119.60,119.90,01-06-2020 12:01:02:002\n"+
                          "108,GBP/USD,1.2500,1.2560,01-06-2020 12:01:02:002\n"+
                          "109,GBP/USD,1.2499,1.2561,01-06-2020 12:01:02:100\n"+
                          "110,EUR/JPY,119.61,119.91,01-06-2020 12:01:02:110";
        consumer.onMessage(message);
        Assertions.assertEquals(5, priceService.getAllPrices().size());

        /* Test case I- get Latest Price*/
        Price price = priceService.getLatestPrice();
        //System.out.println("Test case I - search by Instrument Name ="+price.getInstrumentName() +" id="+price.getId());
        Assertions.assertEquals("EUR/JPY", price.getInstrumentName());
        System.out.println("Ask = " + price.getAsk());
        System.out.println("Bid = " + price.getBid());

        /* Test case II- get Latest Price by Instrument Name*/
        Price byInstrumentName = priceService.getLatestPriceByInstrumentName("GBP/USD");
        //System.out.println("Test case II - search by Instrument Name ="+byInstrumentName.getInstrumentName() +" id="+byInstrumentName.getId());
        Assertions.assertEquals("GBP/USD", byInstrumentName.getInstrumentName());
        System.out.println("Ask = " + byInstrumentName.getAsk());
        System.out.println("Bid = " + byInstrumentName.getBid());


        /* Test case III- get all prices*/
        List<Price> allPrice = priceService.getAllPrices();
        //System.out.println(" ~~~  Test case III - get all messages ~~~");
       allPrice.forEach(i->{
           //System.out.println(" InstrumentName="+i.getInstrumentName() +" id="+i.getId());
           System.out.println("Ask = " + i.getAsk());
           System.out.println("Bid = " + i.getBid());
       } );


    }
}