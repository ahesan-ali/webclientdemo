package com.rbc.bbp0.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class ExchangeRate {
    private String exchangeRateCard;
    private String currencyPairId;
    private String Status;
}
