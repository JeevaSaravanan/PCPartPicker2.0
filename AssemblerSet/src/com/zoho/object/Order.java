package com.zoho.object;

import java.util.Random;

public class Order {
	public String orderID;
	public String userID;
	public float total;
	public Order(String userID,float total){
		this.userID=userID;
		this.total=total;
	}
	public void generateOrderID() {
		 Random r = new Random( System.currentTimeMillis() );
		   orderID="O"+((1 + r.nextInt(2)) * 10000 + r.nextInt(10000));
	}

}
