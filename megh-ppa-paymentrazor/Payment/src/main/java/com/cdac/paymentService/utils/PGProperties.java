package com.cdac.paymentService.utils;

public class PGProperties {
	
	 public String getClientId() {
	        return ClientId;
	    }

	    public void setClientId(String ClientId) {
	        this.ClientId = ClientId;
	    }

	    public String getReturnURL() {
	        return ReturnURL;
	    }

	    public void setReturnURL(String ReturnURL) {
	        this.ReturnURL = ReturnURL;
	    }
	    String ClientId;
	    String ReturnURL;
	    String orderID;

	    public String getOrderID() {
	        return orderID;
	    }

	    public void setOrderID(String orderID) {
	        this.orderID = orderID;
	    }
}
