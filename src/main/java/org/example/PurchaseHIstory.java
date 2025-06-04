package org.example;
import java.util.*;
public class PurchaseHistory {

    private List<PurchaseRecord> purchases = new ArrayList<>();

        public void addPurchase(PurchaseRecord record) {
            purchases.add(record);
        }

        public void displayHistory() {
            for (PurchaseRecord pr : purchases) {
                System.out.println(pr);
            }
        }

        public List<PurchaseRecord> getAll() {
            return purchases;
        }
    }


