package com.example.csseproject.Model;

public class OrderItem {
    private String id;
    private String orderId;
    private String itemId;
    private String qty;
    private String subTotal;
    private String itemName;
    private String itemPic;
    private String received ;

    public OrderItem(){

    }

    public OrderItem(String id, String orderId, String itemId, String qty, String subTotal, String itemName, String itemPic, String received) {
        this.id = id;
        this.orderId = orderId;
        this.itemId = itemId;
        this.qty = qty;
        this.subTotal = subTotal;
        this.itemName = itemName;
        this.itemPic = itemPic;
        this.received = received;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    public String getQty() {
        return qty;
    }

    public void setQty(String qty) {
        this.qty = qty;
    }

    public String getSubTotal() {
        return subTotal;
    }

    public void setSubTotal(String subTotal) {
        this.subTotal = subTotal;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getItemPic() {
        return itemPic;
    }

    public void setItemPic(String itemPic) {
        this.itemPic = itemPic;
    }

    public String getReceived() {
        return received;
    }

    public void setReceived(String received) {
        this.received = received;
    }
}
