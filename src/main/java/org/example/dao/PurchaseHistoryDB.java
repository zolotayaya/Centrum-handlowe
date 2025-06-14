package org.example.dao;

import org.example.model.PurchaseRecord;
import org.example.database.Database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class PurchaseHistoryDB {
    private Connection connection ;
    public PurchaseHistoryDB() {
        this.connection = Database.getConnection();
    }
    public void updateHistory(PurchaseRecord purchaseRecord) throws SQLException {
        System.out.println("Updating purchase history in Database");

        String sql = "INSERT INTO Purchase_History(product_name, seller_name,buyer_id, quantity,price) VALUES(?,?,?,?,?)";
        System.out.println("Updating purchase history in Database");

        PreparedStatement st  = connection.prepareStatement(sql);
        st.setString(1, purchaseRecord.getProductName());
        System.out.println("Product name" + purchaseRecord.getSellerName());
        st.setString(2, purchaseRecord.getSellerName());
        System.out.println("Seller name" + purchaseRecord.getSellerName());
        st.setInt(3, purchaseRecord.getBuyerID());
        st.setInt(4, purchaseRecord.getQuantity());
        st.setFloat(5, purchaseRecord.getPrice());
        System.out.println("Updated");
        st.executeUpdate();
    }
}
