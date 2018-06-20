package com.raahim.domain;



import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;


public class Customer {
	
	
	private int cid;
	
	private String name;
	
	private String address;
	
	private String phone;
	
	private String email;
	 
	public int getCID(){
	  	return cid;
	}
	    
	public void setCID(int cid){
		this.cid = cid;
	}
	    
	    public String getName() {
	        return name;
	    }
	 
	    public void setName(String name) {
	        this.name = name;
	        
	    }
	    
	    public String getAddress() {
	        return address;
	    }
	 
	    public void setAddress(String address) {
	        this.address = address;
	    }
	 
	    public String getPhone() {
	        return phone;
	    }
	 
	    public void setPhone(String phone) {
	        this.phone = phone;
	    }
	    
	    public String getEmail() {
	        return email;
	    }
	 
	    public void setEmail(String email) {
	        this.email = email;
	    }
}