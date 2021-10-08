package com.example.csseproject.Model;

public class Item {
    private String id;
    private String itemName;
    private String itemPic;
    private String itemPolicyFlag;
    private String itemPrice;
    private String itemSupplierId;

    public Item(){

    }

    public Item(String id, String itemName, String itemPic, String itemPolicyFlag, String itemPrice, String itemSupplierId) {
        this.id = id;
        this.itemName = itemName;
        this.itemPic = itemPic;
        this.itemPolicyFlag = itemPolicyFlag;
        this.itemPrice = itemPrice;
        this.itemSupplierId = itemSupplierId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public String getItemPolicyFlag() {
        return itemPolicyFlag;
    }

    public void setItemPolicyFlag(String itemPolicyFlag) {
        this.itemPolicyFlag = itemPolicyFlag;
    }

    public String getItemPrice() {
        return itemPrice;
    }

    public void setItemPrice(String itemPrice) {
        this.itemPrice = itemPrice;
    }

    public String getItemSupplierId() {
        return itemSupplierId;
    }

    public void setItemSupplierId(String itemSupplierId) {
        this.itemSupplierId = itemSupplierId;
    }
}
