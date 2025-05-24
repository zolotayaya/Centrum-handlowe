package org.example;

import java.time.LocalDateTime;

public class PurchaseRecord {
    private Product product;
    private Seller seller;
    private int buyerID;
    // private LocalDateTime date;
    // private int reviewScore;
    public PurchaseRecord(Product product,Seller seller,int buyerID){
        this.product= product;
        this.seller = seller;
        this.buyerID= buyerID;
    }
}
