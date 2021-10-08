package com.example.csseproject.Model;

public class Supplier {
    private String id;
    private String supplierName;
    private String supplierCompany ;
    private String supplierSpeciality ;
    private String supplierPic;

    public Supplier(){

    }

    public Supplier(String id, String supplierName, String supplierCompany, String supplierSpeciality, String supplierPic) {
        this.id = id;
        this.supplierName = supplierName;
        this.supplierCompany = supplierCompany;
        this.supplierSpeciality = supplierSpeciality;
        this.supplierPic = supplierPic;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSupplierName() {
        return supplierName;
    }

    public void setSupplierName(String supplierName) {
        this.supplierName = supplierName;
    }

    public String getSupplierCompany() {
        return supplierCompany;
    }

    public void setSupplierCompany(String supplierCompany) {
        this.supplierCompany = supplierCompany;
    }

    public String getSupplierSpeciality() {
        return supplierSpeciality;
    }

    public void setSupplierSpeciality(String supplierSpeciality) {
        this.supplierSpeciality = supplierSpeciality;
    }

    public String getSupplierPic() {
        return supplierPic;
    }

    public void setSupplierPic(String supplierPic) {
        this.supplierPic = supplierPic;
    }
}
