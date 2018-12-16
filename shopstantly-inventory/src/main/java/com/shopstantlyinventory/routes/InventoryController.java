package com.shopstantlyinventory.routes;

import com.lib.Network.Buffers.InventoryBuffer;
import com.lib.Network.Buffers.ProductBuffer;
import com.shopstantlyinventory.helpers.MailHelper;
import com.shopstantlyinventory.helpers.PdfHelper;
import com.shopstantlyinventory.model.Products;
import com.shopstantlyinventory.repository.ProductsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.io.ByteArrayOutputStream;

@RestController
public class InventoryController {
    @Autowired
    private PdfHelper pdfHelper;
    @Autowired
    private ProductsRepository productsRepository;

    /**
     * Business Process of Inventory
     *
     * @param req complete Inventory Buffer
     * @return Inventory Buffer
     */
    @PostMapping("/inventory")
    public InventoryBuffer createPackingSlip(@Valid @RequestBody InventoryBuffer req) {
        for (ProductBuffer productBuffer : req.productBufferList) {
            Products product = productsRepository.findByProductsID(productBuffer.productID);
            if (product != null) {
                // Assumption: Stock of a product will never be below 0
                if (product.getStock() <= productBuffer.orderAmount) {
                    product.setStock(product.getStock() + productBuffer.orderAmount + 100);
                }
                product.setStock(product.getStock() - productBuffer.orderAmount);
                productsRepository.save(product);
            }
        }

        // Create PackingSlip
        new Thread(() -> {
            ByteArrayOutputStream attachment = pdfHelper.createPdf(req);
            MailHelper.SendMail(req.customerMail, "ShopStantly order " + req.orderID, "Thank you for your purchase!" +
                    "Please find attached your your latest packing slip\n\nKind regards\n\nShopStantly", attachment, "Order " + req.orderID + ".pdf");
        }).start();
        return req;
    }
}