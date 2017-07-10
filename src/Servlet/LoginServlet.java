package Servlet;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import Beans.SessionVo;
import service.LoginService;

public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String submethod=request.getServletPath();
		String m=submethod.substring(1, submethod.length()-6);
		try {
			 Method usemethod=getClass().getDeclaredMethod(m, HttpServletRequest.class,HttpServletResponse.class);
			 usemethod.invoke(this, request,response);
		} catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException
				| InvocationTargetException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doPost(req, resp);
	}
	
	@SuppressWarnings("unused")
	private void logon(HttpServletRequest request,HttpServletResponse response){
		HttpSession session=request.getSession();
		String str=request.getContextPath()+"/index.jsp";
		boolean b = false;
		if(session==null){
			response.setContentType("text/html ; charset=UTF-8");
			try {
				response.getWriter().write("<script>alert('请先登录！')</script>");
				response.getWriter().write("<script>window.location.href='"+str+"'</script>");
			} catch (IOException e) {
				e.printStackTrace();
			}
		}else{
			String uname=request.getParameter("username");
			String pass=request.getParameter("pass");
			if(uname!=null && pass !=null){
				b=LoginService.IsAdminUser(request.getParameter("username"),request.getParameter("pass") );
			}else{
				try {
					response.setContentType("text/html ; charset=UTF-8");
					response.getWriter().write("<script>alert('请先登录！')</script>");
					response.getWriter().write("<script>window.location.href='"+str+"'</script>");
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		
		if(b){
			SessionVo sessionvo=new SessionVo();
			sessionvo.setUserName(request.getParameter("username"));
			sessionvo.setAuthority(LoginService.getUserAuthority(request.getParameter("username")));
			session.setAttribute("sessionVo",sessionvo);
			session.setMaxInactiveInterval(1200);
//			记录登录信息
			LoginService.SaveLoginInfo(LoginService.getMap(request));
			try {
				request.getRequestDispatcher("/jsp/index.jsp").forward(request, response);
			}catch (IOException | ServletException e) {
				e.printStackTrace();
			}
		}else{
			try {
//				StringBuffer url=request.getRequestURL();
//				String str=url.toString().substring(0, url.toString().length()-6);
				response.setContentType("text/html ; charset=UTF-8");
				response.getWriter().write("<script>alert('用户名或密码错误！')</script>");
				response.getWriter().write("<script>history.go(-1);</script>");
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	@SuppressWarnings("unused")
	private void Quit(HttpServletRequest request,HttpServletResponse response){
		request.getSession().removeAttribute("state");
		try {
			response.sendRedirect(request.getContextPath()+"/index.jsp");
		} catch (IOException e) {
			e.printStackTrace();
		}	
	}

}
