package com.lib.Network.Buffers;

import com.lib.Enums.PaymentType;

public class PaymentBuffer {
    public Long customerID;
    public Long orderID;
    public Double price;
    public Double payedPrice;
    public int deductedLoyaltyPoints;
    public int addedLoyaltyPoints;
    public PaymentType paymentType;
    public boolean hasFailed = false;
}
