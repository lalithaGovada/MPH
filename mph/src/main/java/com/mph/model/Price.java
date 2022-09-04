package com.mph.model;

import lombok.Builder;
import lombok.Data;
import org.springframework.lang.NonNull;

import java.time.LocalDateTime;

@Data
@Builder
public class Price {

    private Long id;
    private String instrumentName;
    private Double bid;
    private Double ask;
    private LocalDateTime date;

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Price) {
            return (this.instrumentName.equals(((Price) obj).instrumentName));
        }
        return false;
    }
}