package org.example;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;
public class Department {
    private String name;
    private Brand brand;
    private List<Product> products;
    private List<Empoyee> empoyees;
    public List<Product> getProductsByBrande(int brand) throws SQLException {
        Database db = new Database();
        Connection conn = db.getConnection();
        String sql = "SELECT * FROM products WHERE department_id = ?";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setInt(1,brand);
        ResultSet rs = ps.executeQuery();
        List<Product> lista = new ArrayList<>();
        Brand newBrand = new Brand();
        while(rs.next()){
            String product = rs.getString("name");
            float price = rs.getFloat("price");
            String description = rs.getString("description");
            int quantity = rs.getInt("quantity");
            lista.add(newBrand.addProduct(product,brand,price,description,quantity));
        }
        this.products = lista;
        setBrand(newBrand);
    return lista;
    }
    public void setBrand(Brand brand) {
        this.brand = brand;
    }
    public Manager getManager(){
        return new Manager();
    }
    public void setSellersExperience(int min, int max) throws SQLException {
        Seller seller = new Seller();
        seller.setExperience(min,max);
    }
    public List<Seller> getSellers(Product product) throws SQLException {
        Seller seller = new Seller();
           List<Seller> sellers  = brand.addSellers(product);
            return sellers;
}
    public boolean checkSellerCountForPromotion(){
        return false;
    }
    public void fireManager(){}
    public void Metoda(){}
}
