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
        String sql = "INSERT INTO Purchase_History(product_name, seller_name,buyer_id, quantity,price) VALUES(?,?,?,?,?)";
        PreparedStatement st  = connection.prepareStatement(sql);
        st.setString(1, purchaseRecord.getProductName());
        st.setString(2, purchaseRecord.getSellerName());
        st.setInt(3, purchaseRecord.getBuyerID());
        st.setInt(4, purchaseRecord.getQuantity());
        st.setFloat(5, purchaseRecord.getPrice());
        st.executeUpdate();
    }
}
