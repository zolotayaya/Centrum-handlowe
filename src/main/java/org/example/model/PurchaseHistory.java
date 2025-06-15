package org.example.model;
import org.example.dao.PurchaseHistoryDB;

import java.sql.SQLException;
import java.util.*;
public class PurchaseHistory {
    private  List<PurchaseRecord> purchases = new ArrayList<>();
    private PurchaseHistoryDB purchaseHistoryDB = new PurchaseHistoryDB();
        public void addPurchase(PurchaseRecord record) throws SQLException {
            purchases.add(record);
            purchaseHistoryDB.updateHistory(record);
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

