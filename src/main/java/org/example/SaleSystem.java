package org.example;

import java.sql.Connection;

public class SaleSystem {
    private PurchaseHistory purchaseHistory;
    public SaleSystem(){
        this.purchaseHistory =new PurchaseHistory();
    }
    public PurchaseRecord processPurchase(Product product, Seller seller, int quantity, int buyerID) {
        if (product.getQuantity() < quantity) {
            System.out.println("BNot enough product.");
            return null;
        }

        //product.updateQuantity(quantity);
        seller.saleProduct(product, quantity);

        PurchaseRecord record = new PurchaseRecord(product, seller, buyerID);
        purchaseHistory.addPurchase(record);
        System.out.println("The sale ended succsesful!");
        return record;
    }
}

