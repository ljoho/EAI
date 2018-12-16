package com.lib.Network.Buffers;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;

public class InventoryBuffer {
    @JsonProperty(value = "productBufferList", required = true)
    public List<ProductBuffer> productBufferList = new ArrayList<ProductBuffer>();

    @JsonProperty(value = "orderID", required = true)
    public Long orderID;
    
    @JsonProperty(value = "customerMail", required = true)
    public String customerMail;
}
