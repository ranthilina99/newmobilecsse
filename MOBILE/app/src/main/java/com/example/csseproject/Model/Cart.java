package com.example.csseproject.Model;

public class Cart {

    private String id;
    private String cartItemID,cartItemName,cartItemPrice,cartItemQty,cartItemTotal,cartItemSupplierID,flag;
//    private int cartItemTotal;


    public Cart(){

    }

    public Cart(String id, String cartItemID, String cartItemName, String cartItemPrice, String cartItemQty, String cartItemSupplierID, String cartItemTotal, String flag) {
        this.id = id;
        this.cartItemID = cartItemID;
        this.cartItemName = cartItemName;
        this.cartItemPrice = cartItemPrice;
        this.cartItemQty = cartItemQty;
        this.cartItemSupplierID = cartItemSupplierID;
        this.cartItemTotal = cartItemTotal;
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCartItemTotal() {
        return cartItemTotal;
    }

    public void setCartItemTotal(String cartItemTotal) {
        this.cartItemTotal = cartItemTotal;
    }

    public String getCartItemID() {
        return cartItemID;
    }

    public void setCartItemID(String cartItemID) {
        this.cartItemID = cartItemID;
    }

    public String getCartItemName() {
        return cartItemName;
    }

    public void setCartItemName(String cartItemName) {
        this.cartItemName = cartItemName;
    }

    public String getCartItemPrice() {
        return cartItemPrice;
    }

    public void setCartItemPrice(String cartItemPrice) {
        this.cartItemPrice = cartItemPrice;
    }

    public String getCartItemQty() {
        return cartItemQty;
    }

    public void setCartItemQty(String cartItemQty) {
        this.cartItemQty = cartItemQty;
    }

    public String getCartItemSupplierID() {
        return cartItemSupplierID;
    }

    public void setCartItemSupplierID(String cartItemSupplierID) {
        this.cartItemSupplierID = cartItemSupplierID;
    }
}
