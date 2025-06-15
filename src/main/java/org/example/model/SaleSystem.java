package org.example.model;

import java.sql.SQLException;

public class SaleSystem {
    private PurchaseHistory purchaseHistory;
    public SaleSystem(){
        this.purchaseHistory =new PurchaseHistory();
    }

        public void processPurchase(Product product, Seller seller, int quantity, int buyerID) throws SQLException {
            if (product.getQuantity() < quantity) {
                System.out.println("Not enough product.");
//                return null;
            }
            seller.saleProduct(product, quantity);
            PurchaseRecord record = new PurchaseRecord(product, seller,quantity, buyerID);
            purchaseHistory.addPurchase(record);  // Zwiazek z PurchaseHistory
            System.out.println("The sale ended succsesful!");
//            return record;
        }
        public PurchaseHistory getPurchaseHistory() {
        return purchaseHistory;
        }

    }

