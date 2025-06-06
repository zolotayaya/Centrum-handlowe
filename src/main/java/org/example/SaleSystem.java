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
                System.out.println("Not enough product.");
                return null;
            }
            seller.saleProduct(product, quantity);
//            System.out.println("Seller ");
            product.getBrand().getDepartment().getManager().updateIncome(product);

            PurchaseRecord record = new PurchaseRecord(product, seller,quantity, buyerID);
            purchaseHistory.addPurchase(record);  // Zwiazek z PurchaseHistory
//            System.out.println("The sale ended succsesful!");
            return record;
        }
        public PurchaseHistory getPurchaseHistory() {
        return purchaseHistory;
        }
    }

