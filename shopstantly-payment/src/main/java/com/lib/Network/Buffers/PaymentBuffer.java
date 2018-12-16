package com.lib.Network.Buffers;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.lib.Enums.PaymentType;

public class PaymentBuffer {
    @JsonProperty(value = "customerID", required = true)
    public Long customerID;

    @JsonProperty(value = "orderID", required = true)
    public Long orderID;

    @JsonProperty(value = "price", required = true)
    public Double price;

    @JsonProperty("payedPrice")
    public Double payedPrice;

    @JsonProperty("deductedLoyaltyPoints")
    public Integer deductedLoyaltyPoints;

    @JsonProperty("addedLoyaltyPoints")
    public Integer addedLoyaltyPoints;

    @JsonProperty(value = "paymentType", required = true)
    public PaymentType paymentType;

    @JsonProperty(value = "hasFailed", defaultValue = "false")
    public Boolean hasFailed = false;
}
