package com.lib.Network.Buffers;

import java.util.ArrayList;
import java.util.List;

public class InventoryBuffer {
    public List<ProductBuffer> productBufferList = new ArrayList<ProductBuffer>();
    public long orderID;
    public String customerMail;
}
