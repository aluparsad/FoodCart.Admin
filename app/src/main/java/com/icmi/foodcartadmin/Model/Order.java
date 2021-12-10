package com.icmi.foodcartadmin.Model;


public class Order {

    private String
            address;

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    private String clientId;
    private String orderId;
    private int
            quantity,
            price;
    private Food food;
    private Status status;


    public Order() {
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }


    public int getPrice() {
        return price;
    }

    private void setPrice(int price, int offerPrice) {

        price = (price * quantity);
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public Food getFood() {
        return food;
    }

    public void setFood(Food food) {
        this.food = food;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
