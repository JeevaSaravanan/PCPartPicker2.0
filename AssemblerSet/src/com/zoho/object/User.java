package com.zoho.object;
public class User {
	public String uid;
	public String firstName;
	public String lastName;
	public String email;
	public String psw;
	public String phone;
	public String address;
	public String city;
	public String state;
	public String zip;
	public String country;   
	boolean active=false;
	int emailVerfication=1;
	int phoneVerfication=1;
	
	public void setUid(String uid){
		this.uid=uid;
	}
	public void setFirstName(String firstName) {
		this.firstName=firstName;
	}
	public void setLastName(String lastName) {
		this.lastName=lastName;
	}
	public void setEmail(String email) {
		this.email=email;
	}
	public void setPassword(String psw) {
		this.psw=psw;
	}
	public void setPhone(String phone) {
		this.phone=phone;
	}
	public void setAddress(String address) {
		this.address=address;
	}
	public void setCity(String city) {
		this.city=city;
	}
	public void setState(String state) {
		this.state=state;
	}
	public void setZip(String zip) {
		this.zip=zip;
	}
	public void setCountry(String country) {
		this.country=country;
	}
	
	public String getFirstName() {
		return firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public String getEmail() {
		return email;
	}
	public String getPassword() {
		return psw;
	}
	public String getPhone() {
		return phone;
	}
	public String getAddress() {
		return address;
	}
	public String getCity() {
		return city;
	}
	public String getState() {
		return state;
	}
	public String getZip() {
		return zip;
	}
	public String getCountry() {
		return country;
	}
	public String getUid() {
		return uid;
	}
	public void generateUid(){
		uid=firstName.charAt(0)+""+lastName.charAt(0)+""+phone.substring(0, 4);
	}
	
}

