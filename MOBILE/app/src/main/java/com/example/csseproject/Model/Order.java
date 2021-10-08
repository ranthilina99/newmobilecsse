package com.example.csseproject.Model;

public class Order {
    private String id;
    private String Total;
    private String deliveryStatus;
    private String orderName;
    private String status;
    private String supplierName;
    private String confirmation;
    private String supplierId;

    public Order(){

    }

    public Order(String id, String total, String deliveryStatus, String orderName, String status, String supplierName, String confirmation, String supplierId) {
        this.id = id;
        Total = total;
        this.deliveryStatus = deliveryStatus;
        this.orderName = orderName;
        this.status = status;
        this.supplierName = supplierName;
        this.confirmation = confirmation;
        this.supplierId = supplierId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTotal() {
        return Total;
    }

    public void setTotal(String total) {
        Total = total;
    }

    public String getDeliveryStatus() {
        return deliveryStatus;
    }

    public void setDeliveryStatus(String deliveryStatus) {
        this.deliveryStatus = deliveryStatus;
    }

    public String getOrderName() {
        return orderName;
    }

    public void setOrderName(String orderName) {
        this.orderName = orderName;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getSupplierName() {
        return supplierName;
    }

    public void setSupplierName(String supplierName) {
        this.supplierName = supplierName;
    }

    public String getConfirmation() {
        return confirmation;
    }

    public void setConfirmation(String confirmation) {
        this.confirmation = confirmation;
    }

    public String getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(String supplierId) {
        this.supplierId = supplierId;
    }
}
