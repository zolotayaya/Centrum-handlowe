package org.example;

public class PurchaseRecord {
    private Product product;
    private Seller seller;
    private int buyerID;
    private int quantity;

    // private LocalDateTime date;
    // private int reviewScore;
    public PurchaseRecord(Product product, Seller seller, int quantity, int buyerID) {
        this.product = product;
        this.seller = seller;
        this.buyerID = buyerID;
        this.quantity = quantity;
    }
    public String getProductName() {
        String name = product.getName();
        return name;
    }
    public int getQuantity() {
        return quantity;
    }
}
