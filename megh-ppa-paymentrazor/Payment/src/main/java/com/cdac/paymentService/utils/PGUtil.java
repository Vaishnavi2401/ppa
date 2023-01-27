package com.cdac.paymentService.utils;

import com.razorpay.Order;
import com.razorpay.RazorpayClient;
import com.razorpay.RazorpayException;
import com.razorpay.Utils;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletContext;
import org.json.JSONObject;

public class PGUtil {
	
	private String crncy = "INR";
    private String razorID = null;
    private String secretKey = null;
    Properties propertiesService = null;    
    private String callBackURL = null;    
    
    public PGUtil(ServletContext scon) {
        propertiesService = new Properties();
        InputStream in = null;
        try 
        {
            in = this.getClass().getResourceAsStream("/application.properties");            
            propertiesService.load(in);                
            razorID = propertiesService.getProperty("ClientId");
            secretKey = propertiesService.getProperty("EncryptionKey");
            callBackURL = propertiesService.getProperty("ReturnURL");
            System.out.println("secretKey "+secretKey);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(PGUtil.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(PGUtil.class.getName()).log(Level.SEVERE, null, ex);
        } finally{
            if(in != null){
                try {
                    in.close();
                } catch (IOException ex) {
                    Logger.getLogger(PGUtil.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }

    public String getRazorID(){
        return razorID;
    }
    public String getSecretKey(){
        return secretKey;
    }
    public String getCallBackURL(){
        return callBackURL;
    }
    public PGProperties generateOrderID(int courseFee, long megh_orderID) {
        RazorpayClient razorpay = null;
        Order order = null;
        String orderID = null;
        JSONObject orderRequest = null;
        PGProperties pgp = null;
        try {
            System.out.println("=====2222");
            razorpay = new RazorpayClient(razorID, secretKey);
            orderRequest = new JSONObject();
            orderRequest.put("amount", courseFee);
            orderRequest.put("currency", crncy);
            orderRequest.put("receipt", "order_rcptid_"+megh_orderID);

            order = razorpay.orders.create(orderRequest);
            orderID = order.get("id");
            System.out.println("===1 " + order);
            System.out.println("===" + order.get("id"));
            pgp = new PGProperties();
            pgp.setClientId(getRazorID());
            pgp.setOrderID(orderID);
            pgp.setReturnURL(getCallBackURL());
            
        } catch (RazorpayException e) {  
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
        return pgp;
    }
    
    
    public boolean verifyPaymentSignature(String paymentId, String razorOrderId, String paymentSignature){
        JSONObject options = null;
        boolean status = false;
        options = new JSONObject();
                    options.put("razorpay_payment_id", paymentId);
                    options.put("razorpay_order_id", razorOrderId);
                    options.put("razorpay_signature", paymentSignature);
                    
                    System.out.println("data -- " + paymentId +" -  "+razorOrderId +" - "+ paymentSignature);
        try {
            status = Utils.verifyPaymentSignature(options, getSecretKey());
        } catch (RazorpayException ex) {
            Logger.getLogger(PGUtil.class.getName()).log(Level.SEVERE, null, ex);
        }
        return status;
    }

}
