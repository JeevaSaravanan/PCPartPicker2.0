package com.zoho.object;

public class MotherBoard {
	public String PID;
	public String Model;
	public String brand;
	public float rate;
	public int qty;
	public int seriesid;
	public int actualQuantity;
	public int schemaID;
	public String img;
	
	public void setPID(String PID) {
		this.PID=PID;
	}
	public void setModel(String Model) {
		this.Model=Model;
	}
	public void setRate(float rate) {
		this.rate=rate;
	}
	public void setQty(int qty) {
		this.qty=qty;
	}
	public void setSeriesId(int seriesid) {
		this.seriesid=seriesid;
	}
	public void setSchemaID(int schemaID) {
		this.schemaID=schemaID;
	}
	public void setActualQuantity(int qty){
		actualQuantity=qty;
	}
	public void setImage(String img) {
		this.img=img;
	}
	public void setBrand(String brand) {
		this.brand=brand;
	}
	
	public String getPID() {
		return PID;
	}
	public String getModel() {
		return Model;
	}
	public float getRate() {
		return rate;
	}
	public int getQty() {
		return qty;
	}
	public int getSeriesId() {
		return seriesid;
	}
	public int getSchemaID() {
		return schemaID;
	}
	public int getActualQuantity(){
		return actualQuantity;
	}
	public String getImage() {
		return img;
	}
	public String getBrand() {
		return brand;
	}

	public boolean checkAvailability(){
		if(qty==0) {
			return false;
		}
		else {
			return true;
		}
	}
}
