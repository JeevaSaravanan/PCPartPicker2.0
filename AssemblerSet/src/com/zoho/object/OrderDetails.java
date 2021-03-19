package com.zoho.object;

public class OrderDetails {
	public String orderDetailsID;
	public String orderID;
	public String uid;
	public String Product;
	public String ProductModel;
	public float rate;
	
	public void setOrderDetailsID(String orderDetailsID) {
		this.orderDetailsID=orderDetailsID;
	}
	public void setOrderID(String orderID) {
		this.orderID=orderID;
	}
	public void setUID(String uid) {
		this.uid=uid;
	}
	public void setProduct(String Product) {
		this.Product=Product;
	}
	public void setProductModel(String ProductModel) {
		this.ProductModel=ProductModel;
	}
	public void setRate(float rate) {
		this.rate=rate;
	}
	
	public String getOrderDetailsID() {
		return orderDetailsID;
	}
	public String getOrderID() {
		return orderID;
	}
	public String getUID() {
		return uid;
	}
	public String getProduct() {
		return Product;
	}
	public String getProductModel() {
		return ProductModel;
	}
	public float getRate() {
		return rate;
	}
	
}
