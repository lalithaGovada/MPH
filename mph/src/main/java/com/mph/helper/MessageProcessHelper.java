package com.mph.helper;

import com.mph.dao.PriceDao;
import com.mph.exception.ValidationException;
import com.mph.model.Price;
import com.mph.validator.MessageValidator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.temporal.ChronoField;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Component
@Slf4j
public class MessageProcessHelper {

    @Value("${app.props.commission.bid}")
    private Double bidCommission;

    @Value("${app.props.commission.ask}")
    private Double askCommission;

    @Autowired
    private PriceDao priceDao;

    public void process(String message) {
        log.info("START - process (message = {})", message);
        if (!StringUtils.hasText(message)) return;

        log.info("process - message conversion starting");
        List<Price> prices = convert(message);
        log.info("process - processed messages size = {}", prices.size());

        log.info("process - saving processed messages");
        priceDao.saveOrUpdate(prices);
        log.info("END - process");
    }

    List<Price> convert(String message) {
        log.info("START - convert (message = {})", message);
        List<Price> prices = new ArrayList<>();
        String[] msg = message.split("\n"); //each message could have 1 or more lines in it
        if (msg.length > 0) {
            Arrays.stream(msg).forEach(m -> convertToObj(m, prices));
        }
        log.info("END - convert (message = {})", message);
        return prices;
    }

    private void convertToObj(String message, List<Price> prices) {
        log.info("START - convertToObj (message = {})", message);//106, EUR/USD, 1.1000,1.2000,01-06-2020 12:01:01:001
        String[] temp = message.split(",");

        try {
            MessageValidator.validate(temp);
        } catch (ValidationException ex) {
            log.error("ERROR - convertToObj message validation failed, {}", ex.getMessage());
            return;
        }

        try {
            DecimalFormat decimalFormat = new DecimalFormat("0.00");
            decimalFormat.setRoundingMode(RoundingMode.HALF_UP);

            Price price = Price.builder()
                    .id(Long.valueOf(temp[0].trim()))
                    .instrumentName(temp[1].trim())
                    .bid(Double.valueOf(decimalFormat.format(addCommission(Double.valueOf(temp[2].trim()), true))))
                    .ask(Double.valueOf(decimalFormat.format(addCommission(Double.valueOf(temp[3].trim()), false))))
                    .date(dateFormatter(temp[4]))
                    .build();

            log.info("commission processed for message = {}, bid = {}, ask = {}", message, price.getBid(), price.getAsk());
            prices.add(price);
        } catch (NumberFormatException ex) {
            log.error("ERROR - convertObj unable to process message = {}, due to error = {}", message, ex.getMessage());
        } catch (Exception ex) {
            log.error("ERROR - convertObj unable to process (message = {}, due to error = {})", message, ex.getMessage());
        }
        log.info("END - convertToObj (message = {})", message);
    }

    private Double addCommission(Double amt, boolean isBid) {
        if (isBid) {
            return amt - amt % bidCommission;
        } else {
            return amt + amt % askCommission;
        }
    }

    private LocalDateTime dateFormatter(String dateStr) { //01-06-2020 12:01:01:001
        StringBuilder sb = new StringBuilder(dateStr.trim());
        sb.setCharAt(sb.lastIndexOf(":"), '.');

        return LocalDateTime.parse(sb.toString(), new DateTimeFormatterBuilder().append(DateTimeFormatter.ofPattern("dd-MM-yyyy"))
                .appendLiteral(' ')
                .appendPattern("HH:mm:ss")
                .appendFraction(ChronoField.NANO_OF_SECOND, 1, 9, true)
                .toFormatter());
    }
}
