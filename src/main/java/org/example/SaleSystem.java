package org.example;

import java.sql.SQLException;
import java.util.List;

public class SaleSystem {
    private PurchaseHistory purchaseHistory;
    public SaleSystem(){
        this.purchaseHistory =new PurchaseHistory();
    }

        public PurchaseRecord processPurchase(Product product, Seller seller, int quantity, int buyerID) throws SQLException {
            if (product.getQuantity() < quantity) {
                System.out.println("BNot enough product.");
                return null;
            }
            seller.saleProduct(product, quantity);

            PurchaseRecord record = new PurchaseRecord(product, seller,quantity, buyerID);
            purchaseHistory.addPurchase(record);  // Связь с PurchaseHistory
//            System.out.println("The sale ended succsesful!");
            return record;
        }
        public PurchaseHistory getPurchaseHistory() {
        return purchaseHistory;
        }
    }

