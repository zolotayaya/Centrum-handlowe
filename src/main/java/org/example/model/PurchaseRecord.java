package org.example.model;

/**
 * Klasa PurchaseRecord reprezentuje pojedynczy rekord zakupu.
 * Zawiera informacje o produkcie, sprzedawcy, ilości, kupującym oraz cenie w momencie zakupu.
 */
public class PurchaseRecord {
    private Product product;
    private Seller seller;
    private int buyerID;
    private int quantity;
    private float price;

    /**
     * Konstruktor tworzący rekord zakupu.
     *
     * @param product  zakupiony produkt
     * @param seller   sprzedawca dokonujący sprzedaży
     * @param quantity ilość zakupionych produktów
     * @param buyerID  identyfikator kupującego
     */
    public PurchaseRecord(Product product, Seller seller, int quantity, int buyerID) {
        this.product = product;
        this.seller = seller;
        this.buyerID = buyerID;
        this.quantity = quantity;
        this.price = product.getPrice();
    }

    /**
     * Zwraca nazwę produktu powiązanego z rekordem zakupu.
     *
     * @return nazwa produktu
     */
    public String getProductName() {
        return product.getName();
    }

    /**
     * Zwraca nazwę sprzedawcy powiązanego z rekordem zakupu.
     *
     * @return nazwa sprzedawcy
     */
    public String getSellerName() {
        return seller.getName();
    }

    /**
     * Zwraca ilość zakupionych produktów.
     *
     * @return ilość produktów
     */
    public int getQuantity() {
        return quantity;
    }

    /**
     * Zwraca identyfikator kupującego.
     *
     * @return ID kupującego
     */
    public int getBuyerID() {
        return buyerID;
    }

    /**
     * Zwraca cenę produktu w momencie zakupu.
     *
     * @return cena produktu
     */
    public float getPrice() {
        return price;
    }

    /**
     * Zwraca obiekt sprzedawcy powiązanego z rekordem zakupu.
     *
     * @return sprzedawca
     */
    public Seller getSeller() {
        return seller;
    }
}
