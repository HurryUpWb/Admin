package Servlet;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONException;
import org.json.JSONObject;

import Beans.Comment;
import Beans.User;
import Utils.Page;
import Utils.utils;
import service.UserService;

public class UserServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String submethod=request.getServletPath();
		String method=submethod.substring(1, submethod.length()-5);
		try {
			Method m=getClass().getDeclaredMethod(method, HttpServletRequest.class,HttpServletResponse.class);
			m.invoke(this,request,response);
		} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchMethodException | SecurityException e) {
			e.printStackTrace();
		}
	}
	/**
	 * 获取用户
	 * @param request
	 * @param response
	 */
	@SuppressWarnings("unused")
	private void getusers(HttpServletRequest request,HttpServletResponse response){
		Page p=new Page();
		p.setTotalNum(UserService.getSumOfTab());
		p.setPerPageNo(7);
		p.setPageIndex(1);
		List<User> list=UserService.TurnUserPages(p,null,null);
		request.setAttribute("page",p);
		if(list!=null){
			request.setAttribute("ulist",list);
		}
		try {
			request.getRequestDispatcher("/jsp/userview/ListAllUser.jsp").forward(request, response);
		} catch (ServletException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 根据条件筛选User
	 * @param request
	 * @param response
	 */
	@SuppressWarnings("unused")
	private void SortUserBycategory(HttpServletRequest request,HttpServletResponse response){
		String category=request.getParameter("category");
		String val=request.getParameter("val");
		String sql="select count(u_id) sum from userinfo where ?=?";
		Page p=utils.getpage(request,sql);
		List<User> list=null;
		if(!utils.IsNull(category) && !utils.IsNull(val)){
			list=UserService.TurnUserPages(p,category,val);
		}
		else{
			list=UserService.TurnUserPages(p, null, null);
		}
		request.setAttribute("ulist",list);
		request.setAttribute("page",p);
		try {
			request.getRequestDispatcher("/jsp/userview/ListAllUser.jsp").forward(request, response);
		} catch (ServletException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 根据用户ID获取订单
	 * @param request
	 * @param response
	 */
	@SuppressWarnings("unused")
	private void getOrders(HttpServletRequest request,HttpServletResponse response){
		String temp=request.getParameter("uid");
		List<Map<String,Object>> list=new ArrayList<>();
		if(!utils.IsNull(temp)){
			list=UserService.getOinfo(Integer.parseInt(temp));
		}
		request.setAttribute("list", list);
		request.setAttribute("size", list.size());
		try {
			request.getRequestDispatcher("/jsp/userview/ListUserOrderInfo.jsp").forward(request, response);
		} catch (ServletException | IOException e) {
			e.printStackTrace();
		}
	}
	
	
	@SuppressWarnings("unused")
	private void getcomments(HttpServletRequest request,HttpServletResponse response){
		Page p=new Page();
		p.setTotalNum(UserService.getsumofComments());
		p.setPerPageNo(7);
		p.setPageIndex(1);
		List<Comment> list=UserService.TurnCommPages(p,null,null);
		request.setAttribute("page",p);
		if(list!=null){
			request.setAttribute("clist",list);
		}
		try {
			request.getRequestDispatcher("/jsp/userview/ListAllComment.jsp").forward(request, response);
		} catch (ServletException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@SuppressWarnings("unused")
	private void SortCommentBycategory(HttpServletRequest request,HttpServletResponse response){
		String category=request.getParameter("category");
		String val=request.getParameter("val");
		StringBuffer sql=new StringBuffer("select count(cid) sum from comment ");
		if(!utils.IsNull(category) && !utils.IsNull(val)){
			sql.append(" where ?=?");
		}
		Page p=utils.getpage(request,sql.toString());
		List<Comment> list=null;
		if(!utils.IsNull(category) && !utils.IsNull(val)){
			list=UserService.TurnCommPages(p, category, val);
		}
		else{
			list=UserService.TurnCommPages(p, null, null);
		}
		request.setAttribute("clist",list);
		request.setAttribute("page",p);
		try {
			request.getRequestDispatcher("/jsp/userview/ListAllComment.jsp").forward(request, response);
		} catch (ServletException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 隐藏或显示评论
	 * @param request
	 * @param response
	 * @throws JSONException
	 */
	@SuppressWarnings("unused")
	private void HideComment(HttpServletRequest request,HttpServletResponse response) throws JSONException{
		JSONObject jsonObject=new JSONObject();
		String cid=request.getParameter("cid");
		String flag=request.getParameter("flag");
		Integer it=Integer.parseInt(cid);
		boolean b=UserService.hideComment(flag,it.intValue());
		if(!b){
			jsonObject.append("msg","success");
		}else{
			jsonObject.append("msg","fail");
		}
		try {
			response.getWriter().write(jsonObject.toString());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}

