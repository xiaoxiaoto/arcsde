package org.aoto;

import com.esri.sde.sdk.client.SeConnection;
import com.esri.sde.sdk.client.SeException;
import com.esri.sde.sdk.client.SeRelease;

public class ArcSDE {
	 private static SeConnection conn = null;    
     
	    private static String server = "127.0.0.1";    
	    //如果输入sde:oracle11g  
	    private static String instance = "sde:oracle11g:arcsde";    
	    private static String database = "";    
	    private static String username = "sde";    
	    //密码需要输入sde@orcl  
	    private static String password = "sde";    
	    //获得ArcSDE连接    
	    private static SeConnection getConn() {    
	        if (conn == null) {    
	            try {    
	                conn = new SeConnection(server, instance, database, username,    
	                        password);    
	            } catch (SeException ex) {    
	                ex.printStackTrace();    
	            }      
	        }    
	            
	        return conn;    
	    }    
	  
	    /** 
	     * @param args 
	     */  
	    public static void main(String[] args) {  
	        // TODO Auto-generated method stub  
	        SeConnection conn =getConn();    
	        SeRelease release=conn.getRelease();   
	        System.out.println(release.getBugFix());    
	        System.out.println(release.getDesc());    
	        System.out.println(release.getRelease());    
	        System.out.println(release.getMajor());    
	        System.out.println(release.getMinor());    
	    }  
  
}
