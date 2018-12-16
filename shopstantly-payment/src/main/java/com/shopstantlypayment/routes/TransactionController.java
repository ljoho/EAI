package com.shopstantlypayment.routes;

import com.lib.Enums.PaymentType;
import com.lib.Network.Buffers.PaymentBuffer;
import com.shopstantlypayment.model.Customers;
import com.shopstantlypayment.model.Transactions;
import com.shopstantlypayment.repository.CustomersRepository;
import com.shopstantlypayment.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.UUID;

@RestController
public class TransactionController {
    private final double LOYALTYPOINTSMULTIPLIER = 0.1;
    @Autowired
    private TransactionRepository transactionRepository;
    @Autowired
    private CustomersRepository customersRepository;

    /**
     * Business Process of Payment
     *
     * @param req Receives filled PaymentBuffer
     * @return PaymentBuffer
     */
    @PostMapping("/payment")
    public PaymentBuffer startPaymentProcess(@Valid @RequestBody PaymentBuffer req) {
        Customers customers = retrieveCustomer(req.customerID);
        int availableLoyaltyPoints = customers.getLoyaltyPoints();
        int priceInLoyaltyPoints = (int) ((req.price * LOYALTYPOINTSMULTIPLIER));

        if (req.paymentType == PaymentType.CREDITCARD) {
            customers.setLoyaltyPoints(availableLoyaltyPoints + priceInLoyaltyPoints);
            req.addedLoyaltyPoints = priceInLoyaltyPoints;
            req.payedPrice = req.price;
        } else if (req.paymentType == PaymentType.LOYALTYPOINTS) {
            if (availableLoyaltyPoints >= priceInLoyaltyPoints) {
                customers.setLoyaltyPoints(availableLoyaltyPoints - priceInLoyaltyPoints);
                req.deductedLoyaltyPoints = priceInLoyaltyPoints;
            } else {
                // Difference between availableLoyaltyPoints and priceInLoyaltyPoints will be payed with CreditCard
                customers.setLoyaltyPoints(0);
                req.payedPrice = req.price - (availableLoyaltyPoints / LOYALTYPOINTSMULTIPLIER);
                req.deductedLoyaltyPoints = availableLoyaltyPoints;
            }
        }

        // Transaction fail --> customer gets money back...
        try {
            Transactions transaction = new Transactions(req, customers);
            transaction.setTransactionId(generateString());
            transactionRepository.save(transaction);

            customers.getTransactions().add(transaction);
            customersRepository.save(customers);

        } catch (Exception e) {
            e.fillInStackTrace();
            revertPayment(req, customers, priceInLoyaltyPoints, availableLoyaltyPoints);
            req.hasFailed = true;
        }
        return req;
    }

    /**
     * Retrieves Curstomer from database
     *
     * @param customerID The id of the corresponding Customer
     * @return Customers object
     */
    private Customers retrieveCustomer(Long customerID) {
        Customers customers = customersRepository.findByCustomersID(customerID);

        // First time Customer buys something
        if (customers == null) {
            customers = new Customers(customerID);
            customers = customersRepository.save(customers);
        }
        return customers;
    }

    /**
     * Reverts the complete Payment including LoyaltyPoints
     *
     * @param req Receives filled PaymentBuffer
     * @param customers Customers
     * @param priceInLoyaltyPoints Price of order in loyaltyPoints
     * @param availableLoyaltyPoints available loyaltyPoints of customer
     */
    private void revertPayment(PaymentBuffer req, Customers customers, int priceInLoyaltyPoints, int availableLoyaltyPoints) {
        if (req.paymentType == PaymentType.CREDITCARD) {
            customers.setLoyaltyPoints(customers.getLoyaltyPoints() - priceInLoyaltyPoints);
            System.err.println("*** Payment failed! *** \nLoyaltyPoints removed: " + priceInLoyaltyPoints);
        } else if (req.paymentType == PaymentType.LOYALTYPOINTS) {
            if (customers.getLoyaltyPoints() == 0) {
                customers.setLoyaltyPoints(availableLoyaltyPoints);
                System.err.println("*** Payment failed! *** \nLoyaltyPoints added: " + availableLoyaltyPoints);
            } else {
                customers.setLoyaltyPoints(availableLoyaltyPoints + priceInLoyaltyPoints);
                System.err.print("*** Payment failed! *** \nLoyaltyPoints loyaltyPoints: " + priceInLoyaltyPoints + "\n\n");
            }
        }
        customersRepository.save(customers);
    }

    /**
     * Generates a random String
     * Adapted from https://stackoverflow.com/questions/41107/how-to-generate-a-random-alpha-numeric-string
     * @return String
     */
    public static String generateString() {
        return UUID.randomUUID().toString().replaceAll("-", "").substring(0, 10);
    }

}