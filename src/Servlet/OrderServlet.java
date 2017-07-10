package Servlet;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import Utils.Page;
import service.OrderService;

public class OrderServlet extends HttpServlet {
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
	
	/**
	 * 查询所有订单
	 * @param request
	 * @param response
	 */
	@SuppressWarnings("unused")
	private void ListOrders(HttpServletRequest request,HttpServletResponse response){
		Page p=new Page();
		p.setTotalNum(OrderService.getSumOfTab(null,null));
		p.setPerPageNo(7);
		p.setPageIndex(1);
		request.setAttribute("page",p);
		request.setAttribute("list",OrderService.ListAllOrder(p,null,null));
		try {
			request.getRequestDispatcher("/jsp/orderview/ListAllOrder.jsp").forward(request, response);
		} catch (ServletException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 翻页操作
	 * @param request
	 * @param response
	 */
	@SuppressWarnings("unused")
	private void Turn(HttpServletRequest request,HttpServletResponse response){
		//判断是否带条件
		Page p=(Page) request.getSession().getAttribute("page");
		String category=request.getParameter("category");
		String val=request.getParameter("val");
		
		if(category!=null && !category.equals("") && val!=null && !val.equals("")){
			try {
				val=new String(val.getBytes("iso8859-1"),"utf8");
				category=new String(category.getBytes("iso8859-1"),"utf8");
			} catch (UnsupportedEncodingException e1) {
				e1.printStackTrace();
			}
			request.setAttribute("category", category);
			request.setAttribute("val",val);
			p.setTotalNum(OrderService.getSumOfTab(category, val));
		}else{
			p.setTotalNum(OrderService.getSumOfTab(null,null));
		}
		
		//翻页的操作
		String index=request.getParameter("C");
		String pageindex=request.getParameter("index");
		if(index!=null){
			switch (index) {
			case "N":
				p.setPageIndex(p.getpageIndex()+1);
				break;
			case "P":
				p.setPageIndex(p.getpageIndex()-1);
				break;
			case "F":
				p.setPageIndex(1);
				break;
			case "L":
				p.setPageIndex(p.getTotalPage());
				break;
			}
		}else if(pageindex!=null && !pageindex.equals("")){
			p.setPageIndex(Integer.parseInt(pageindex));
		}
		List<Map<String,Object>> list=null;
		if(category!=null && !category.equals("") && val!=null && !val.equals("")){
			list=OrderService.ListAllOrder(p, category, val);
		}
		else{
			list=OrderService.ListAllOrder(p, null, null);
		}
		request.setAttribute("list",list);
		request.setAttribute("page",p);
		try {
			request.getRequestDispatcher("/jsp/orderview/ListAllOrder.jsp").forward(request, response);
		} catch (ServletException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
