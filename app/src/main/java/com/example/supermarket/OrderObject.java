package com.example.supermarket;

import com.bumptech.glide.util.Util;

import java.util.ArrayList;

public class OrderObject {
    private int order_id;
    private ArrayList<groceryItems> items;
    private String Address;
    private String ZipCode;
    private String PhoneNumber;
    private String EmailId;
    private String PaymentMethod;
    private boolean Success;

    public OrderObject(ArrayList<groceryItems> items, String address, String zipCode, String phoneNumber, String emailId, String paymentMethod, boolean success) {
        this.order_id = Utils.getOrderId();
        this.items = items;
        this.Address = address;
        this.ZipCode = zipCode;
        this.PhoneNumber = phoneNumber;
        this.EmailId = emailId;
        this.PaymentMethod = paymentMethod;
        this.Success = success;
    }

    public OrderObject() {

    }

    @Override
    public String toString() {
        return "OrderObject{" +
                "order_id=" + order_id +
                ", items=" + items +
                ", Address='" + Address + '\'' +
                ", ZipCode='" + ZipCode + '\'' +
                ", PhoneNumber='" + PhoneNumber + '\'' +
                ", EmailId='" + EmailId + '\'' +
                ", PaymentMethod='" + PaymentMethod + '\'' +
                ", Success=" + Success +
                '}';
    }

    public int getOrder_id() {
        return order_id;
    }

    public void setOrder_id(int order_id) {
        this.order_id = order_id;
    }

    public ArrayList<groceryItems> getItems() {
        return items;
    }

    public void setItems(ArrayList<groceryItems> items) {
        this.items = items;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public String getZipCode() {
        return ZipCode;
    }

    public void setZipCode(String zipCode) {
        ZipCode = zipCode;
    }

    public String getPhoneNumber() {
        return PhoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        PhoneNumber = phoneNumber;
    }

    public String getEmailId() {
        return EmailId;
    }

    public void setEmailId(String emailId) {
        EmailId = emailId;
    }

    public String getPaymentMethod() {
        return PaymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        PaymentMethod = paymentMethod;
    }

    public boolean isSuccess() {
        return Success;
    }

    public void setSuccess(boolean success) {
        Success = success;
    }
}
