package com.shopstantlyeshop.routes;

import com.dialogflow.request.JsonRequest;
import com.dialogflow.response.Data;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.lib.Enums.PaymentType;
import com.lib.Network.Buffers.InventoryBuffer;
import com.lib.Network.Buffers.PaymentBuffer;
import com.lib.Network.Buffers.ProductBuffer;
import com.lib.Network.Buffers.ShipmentBuffer;
import com.shopstantlyeshop.model.*;
import com.shopstantlyeshop.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RestController
public class OrderController {
    private static String trackingID;
    @Autowired
    private OrdersRepository ordersRepository;
    @Autowired
    private CustomersRepository customersRepository;
    @Autowired
    private OrderItemsRepository orderItemsRepository;
    @Autowired
    private CartsRepository cartsRepository;
    @Autowired
    private PreferencesRepository preferencesRepository;
    @Autowired
    private ObjectMapper objectMapper;

    /**
     * Business Process of OrderManagement
     *
     * @param jsonRequest JSON Object from Google Assistant (Dialogflow)
     * @return JSON Message Object
     */
    @PostMapping(value = "/order", consumes = "application/json", produces = "application/json")
    public ObjectNode createOrder(@RequestBody JsonRequest jsonRequest) {
        ObjectNode response = objectMapper.createObjectNode();

        // Retrieve Customers and Carts
        Customers customers = retrieveCustomers();
        Carts carts = customers.getCarts();

        switch (jsonRequest.getResult().getAction().toString()) {
            case "item.add":
                Products products = preferencesRepository.findByName(jsonRequest.getResult().getParameters().getProduct()).getProducts();
                if (products != null) {
                    if (carts.getProductsList().stream().noneMatch(dbProduct -> dbProduct.getName().equals(products.getName()))) {
                        carts.getProductsList().add(products);
                        cartsRepository.save(carts);
                        response.put("speech", "Alright, I've added " + products.getName() + " to your cart. Anything else?");
                    } else {
                        response.put("speech", "Could not add " + products.getName() + " to cart. It has already been added.");
                    }
                } else {
                    response.put("speech", "Sorry, we do not have the product " + jsonRequest.getResult().getParameters().getProduct());
                }
                break;
            case "check_out":
                Orders order = new Orders();
                order.setCustomers(customers);

                // sets PaymentType
                if (jsonRequest.getResult().getParameters().getPaymentType().equals("credit card")) {
                    order.setPaymentType(PaymentType.CREDITCARD);
                } else {
                    order.setPaymentType(PaymentType.LOYALTYPOINTS);
                }

                // Create order item out of product
                final int orderAmount = 1;
                List<OrderItems> orderItemsList = new ArrayList<>();
                for (Products p : carts.getProductsList()) {
                    OrderItems orderItem = new OrderItems(orderAmount, p.getPrice(), order, p);
                    orderItemsList.add(orderItem);

                    order.getOrderItemsList().add(orderItem);
                    order.setPrice(order.getPrice() + orderItem.getPrice());
                }
                new Thread(() -> orderItemsRepository.saveAll(orderItemsList)).start();
                order = ordersRepository.save(order); // cannot be in new Thread, because ID is needed afterwards

                // Service Calls
                PaymentBuffer paymentBuffer = (PaymentBuffer) callService(createPaymentBuffer(order), 8081, "payment");
                if (paymentBuffer.hasFailed) {
                    System.err.println("Payment was not successful");
                    response.put("speech", "Payment could not be processed. Try again later.");
                    return response;
                } else {
                    callService(createInventoryBuffer(order), 8082, "inventory");
                    callService(createShipmentBuffer(order), 8083, "shipment");

                    clearCart(carts);
                    response.put("displayText", "Payed with Credit Card: Fr. " + paymentBuffer.payedPrice +
                            "\nDeducted Loyalty Points " + paymentBuffer.deductedLoyaltyPoints + "\nReceived Loyalty Points: " + paymentBuffer.addedLoyaltyPoints);
                    Data responseData = new Data("Track your parcel", "Click me", "https://www.post.ch/static/Post/IT/FDS/post.jpg", "Post",
                            "http://www.post.ch/swisspost-tracking?formattedParcelCodes=" + trackingID, "Thank you for your order! Your packing slip will be sent to your e-mail address shortly." +
                            "\n\nYou can already track your parcel online.", true);
                    response.putPOJO("data", responseData);
                }
                break;
            default:
                response.put("speech", "Didn't know what to do.");
                break;
        }
        System.out.println(new Date(System.currentTimeMillis()) + "\n" + response.toString());
        return response;
    }

    /**
     * Clears the cart of a customer
     *
     * @param carts Carts to be cleared
     */
    private void clearCart(Carts carts) {
        // Removes products from cart
        System.out.println("Cleared cart");
        carts.setProductsList(new ArrayList<>());
        cartsRepository.save(carts);
    }

    /**
     * Retrieves Customers from Database if existing or creates new one
     *
     * @return Customers object
     */
    private Customers retrieveCustomers() {
        Customers customers = customersRepository.findById((long) 251).get();
        if (customers == null) {
            customers = new Customers();
            customers = customersRepository.save(customers);

            Carts carts = new Carts();
            carts.setCustomers(customers);
            cartsRepository.save(carts);

            customers.setCarts(carts);
            customers = customersRepository.save(customers);
        }
        return customers;
    }

    /**
     * Calls the microservice
     *
     * @param buffer Buffer that needs to be sent
     * @param port   Port of the receiving application
     * @param appName Name of the receiving application
     * @return boolean
     */
    private Object callService(Object buffer, int port, String appName) {
        final String uri = "https://shopstantly-" + appName + ".herokuapp.com/" + appName; // @heroku
        // final String uri = "http://localhost:" + port + "/" + appName; // @localhost
        RestTemplate restTemplate = new RestTemplate();
        System.out.println("Called Service: " + uri);

        Object result = null;
        if ("payment".equals(appName)) {
            result = restTemplate.postForObject(uri, buffer, PaymentBuffer.class);
        } else if ("inventory".equals(appName)) {
            result = restTemplate.postForObject(uri, buffer, InventoryBuffer.class);
        } else if ("shipment".equals(appName)) {
            result = restTemplate.postForObject(uri, buffer, ShipmentBuffer.class);
            trackingID = ((ShipmentBuffer) result).trackingID;
        }

        System.out.println(result);
        return result;
    }

    /**
     * Creates a PaymentBuffer
     *
     * @param order Order that will be processed
     * @return PaymentBuffer
     */
    private PaymentBuffer createPaymentBuffer(Orders order) {
        // PaymentService: Pay for Order
        PaymentBuffer paymentBuffer = new PaymentBuffer();
        paymentBuffer.price = order.getPrice();
        paymentBuffer.paymentType = order.getPaymentType();
        paymentBuffer.orderID = order.getID();
        paymentBuffer.customerID = order.getCustomers().getID();
        return paymentBuffer;
    }

    /**
     * Creates a InventoryBuffer
     *
     * @param order Order that will be processed
     * @return InventoryBuffer
     */
    private InventoryBuffer createInventoryBuffer(Orders order) {
        // InventoryService: Pick products from stock and create packing slip
        InventoryBuffer inventoryBuffer = new InventoryBuffer();
        inventoryBuffer.orderID = order.getID();
        inventoryBuffer.customerMail = order.getCustomers().getEmail();
        for (OrderItems orderItems : order.getOrderItemsList()) {
            ProductBuffer productBuffer = new ProductBuffer();
            productBuffer.productID = orderItems.getProducts().getID();
            productBuffer.orderAmount = orderItems.getAmount();
            productBuffer.name = orderItems.getProducts().getName();
            productBuffer.price = orderItems.getPrice();
            inventoryBuffer.productBufferList.add(productBuffer);
        }
        return inventoryBuffer;
    }

    /**
     * Creates a ShipmentBuffer
     *
     * @param order Order that will be processed
     * @return ShipmentBuffer
     */
    private ShipmentBuffer createShipmentBuffer(Orders order) {
        // ShippingService: Create fake trackingId
        ShipmentBuffer shipmentBuffer = new ShipmentBuffer();
        shipmentBuffer.orderID = order.getID();
        return shipmentBuffer;
    }
}