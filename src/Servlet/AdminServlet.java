package Servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import Beans.Admin_user;
import Beans.SessionVo;
import Beans.Streaming;
import service.Adminservice;

public class AdminServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String submethod=request.getServletPath();
		String m=submethod.substring(1, submethod.length()-4);
	    try {
			 Method usemethod=getClass().getDeclaredMethod(m, HttpServletRequest.class,HttpServletResponse.class);
			 usemethod.invoke(this, request,response);
		} catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException
				| InvocationTargetException e) {
			e.printStackTrace();
		}
	}
	
	@SuppressWarnings("unused")
	private void Changepwd(HttpServletRequest request,HttpServletResponse response){
		SessionVo vo=(SessionVo) request.getSession().getAttribute("sessionVo");
		if(vo==null){
			
		}
		Admin_user user=Adminservice.getAdminfo(vo.getUserName());
		request.setAttribute("user", user);
		request.setAttribute("item","修改密码");
		try {
			request.getRequestDispatcher("/jsp/admview/changepwd.jsp").forward(request, response);
		} catch (ServletException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	@SuppressWarnings("unused")
	private void Chage(HttpServletRequest request,HttpServletResponse response){
		String id=request.getParameter("id");
		String npwd=request.getParameter("npwd");
		boolean b=Adminservice.chagepwd(id, npwd);
		if(!b){
			response.setContentType("text/html;charset=UTF-8");
			try {
				response.getWriter().write("<script>alert('修改成功！请重新登陆')</script>");
				response.getWriter().write("<script>window.location.href='"+request.getContextPath()+"/index.jsp'</script>");
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	@SuppressWarnings("unused")
	private void EditAuth(HttpServletRequest request,HttpServletResponse response){
		List<Admin_user> list=Adminservice.getAllAdminUser();
		request.setAttribute("aduser", list);
		request.setAttribute("item", "权限修改");
		try {
			request.getRequestDispatcher("/jsp/admview/authority.jsp").forward(request, response);
		} catch (ServletException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@SuppressWarnings("unused")
	private void streaming(HttpServletRequest request,HttpServletResponse response){
		String str="AAAA";
		Streaming streaming=new Streaming(response,str);
		streaming.run();
	}
	
	@SuppressWarnings("unused")
	private void longPolling(HttpServletRequest request,HttpServletResponse response){
		String str=Adminservice.getmsg();
		PrintWriter writer=null;
		try {
			writer=response.getWriter();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		Thread thread=new Thread(){
			@Override
			public void run() {
				try {
					sleep(5000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			};
		};

		if(str!=null){
			writer.print("we have "+str);
			writer.flush();
			writer.close();
		}
	}
}
