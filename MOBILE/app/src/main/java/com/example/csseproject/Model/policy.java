package com.example.csseproject.Model;

public class policy {

    private String id,policyOnePrice;

    public policy() {
    }

    public policy(String id, String policyOnePrice) {
        this.id = id;
        this.policyOnePrice = policyOnePrice;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPolicyOnePrice() {
        return policyOnePrice;
    }

    public void setPolicyOnePrice(String policyOnePrice) {
        this.policyOnePrice = policyOnePrice;
    }
}
