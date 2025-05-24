package org.example;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;
public class Brand {
    private String name;
    private Seller expert;
    private List<Seller> selers = new ArrayList<>();
    public Product addProduct(String name,int id,float price,String description,int quantity){
        Product product = new Product();
        product.setName(name);
        product.setId(id);
        product.setPrice(price);
        product.setDescription(description);
        product.setQuantity(quantity);
        return product;
    }

     public List<Seller> addSellers(Product product) throws SQLException {
        Database db = new Database();
        Connection conn = db.getConnection();
        int id = product.getId();
        int selerId;
        String sql1 = "SELECT * FROM sellers WHERE department_id= ?";
        PreparedStatement ps = conn.prepareStatement(sql1);
        ps.setInt(1,id);
        ResultSet rs = ps.executeQuery();
        while(rs.next()){
            selerId = rs.getInt("unik_id");
            Seller seller = new Seller();
            seller.createSellers(selerId);
            seller.saleProduct(product,selerId);
            selers.add(seller);
        }
        return selers;
     }
}
