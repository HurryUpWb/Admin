package Beans;

import java.io.PrintWriter;
import javax.servlet.http.HttpServletResponse;

public class Streaming extends Thread {
	private HttpServletResponse response=null;
	private String msg="";
	
	public Streaming(HttpServletResponse res,String msgs) {
		this.response=res;
		this.msg=msgs;
	}
	
	@Override
	public void run() {
		try{  
            PrintWriter writer = response.getWriter();
            for(int i = 0 ,max = msg.length(); i < max ; i++) {  
                writer.print(msg.substring(i,i+1));  
                writer.flush();  
                sleep(10);  
            }  
            writer.close();  
        }catch (Exception e) {
        	e.printStackTrace();
        }  
	}
}
