package org.example.model;

public class PurchaseRecord {
    private Product product;
    private Seller seller;
    private int buyerID;
    private int quantity;
    private float price;

    // private LocalDateTime date;
    // private int reviewScore;
    public PurchaseRecord(Product product, Seller seller, int quantity, int buyerID) {
        this.product = product;
        this.seller = seller;
        this.buyerID = buyerID;
        this.quantity = quantity;
        this.price = product.getPrice();
    }
    public String getProductName() {
        String name = product.getName();
        return name;
    }
    public String getSellerName() {
        return seller.getName();
    }
    public int getQuantity() {
        return quantity;
    }
    public int getBuyerID() {
        return buyerID;
    }
    public float getPrice() {return price;}
}
