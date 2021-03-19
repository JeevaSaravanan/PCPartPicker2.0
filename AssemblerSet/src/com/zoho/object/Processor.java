package com.zoho.object;

public class Processor {
	public String PID;
	public String Model;
	public String Details;
	public float rate;
	public int qty;
	public int gc;
	public int gid;
	public int actualQuantity;
	public int schemaID;
	public String img;
	public String brand;
	
	public void setPID(String PID) {
		this.PID=PID;
	}
	public void setModel(String Model) {
		this.Model=Model;
	}
	public void setDetails(String Details) {
		this.Details=Details;
	}
	public void setRate(float rate) {
		this.rate=rate;
	}
	public void setQty(int qty) {
		this.qty=qty;
	}
	public void setGc(int gc) {
		this.gc=gc;
	}
	public void setGid(int gid) {
		this.gid=gid;
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
	public String getDetails() {
		return Details;
	}
	public float getRate() {
		return rate;
	}
	public int getQty() {
		return qty;
	}
	public int getGc() {
		return gc;
	}
	public int getGid() {
		return gid;
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
