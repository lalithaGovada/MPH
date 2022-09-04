package com.mph.validator;

import com.mph.exception.ValidationException;
import com.mph.util.Const;
import org.springframework.util.StringUtils;

public class MessageValidator {

    public static void validate(String[] msg) {
       //I. 106, II. EUR/USD, III. 1.1000,IV. 1.2000,V. 01-06-2020 12:01:01:001
        // if message don't have 5 strings it should throw error
        if (msg.length != 5) throw new ValidationException(Const.ERROR_MSG_PARAMS_SIZE);
        // Not null checks for all fields
        if (!StringUtils.hasText(msg[0])) throw new ValidationException(Const.ERROR_MSG_PARAMS_ID);

        if (!StringUtils.hasText(msg[1])) throw new ValidationException(Const.ERROR_MSG_PARAMS_NAME);

        if (!StringUtils.hasText(msg[2])) throw new ValidationException(Const.ERROR_MSG_PARAMS_BID);

        if (!StringUtils.hasText(msg[3])) throw new ValidationException(Const.ERROR_MSG_PARAMS_ASK);

        if (!StringUtils.hasText(msg[4])) throw new ValidationException(Const.ERROR_MSG_PARAMS_DATE);
    }
}
