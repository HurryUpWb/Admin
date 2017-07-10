package service;

import java.util.List;

import org.apache.log4j.Logger;

import Beans.Admin_user;
import DAOImpl.AdminUserDaoImpl;
import DAOImpl.BookDaoImpl;

public class Adminservice {
	private static Logger log=Logger.getLogger(Adminservice.class);
	private static AdminUserDaoImpl impl=new AdminUserDaoImpl();
	
	/**
	 * 根据用户名获取管理员用户信息
	 * @param name
	 * @return
	 */
	public static Admin_user getAdminfo(String name){
		return impl.getAdmin_userByName(name);
	}
	
	/**
	 * 修改密码
	 * @param id
	 * @param npwd
	 * @return
	 */
	public static boolean chagepwd(String id,String npwd){
		Admin_user user=impl.getAdmin_userById(Integer.parseInt(id));
		user.setPassword(npwd);
		return impl.UpdateAdmin_user(user);
	}
	
	/**
	 * 获取所有管理员
	 * @return
	 */
	public static List<Admin_user> getAllAdminUser(){
		List<Admin_user> list=null;
		String sql="select * from admin_user";
		list=impl.getAll(Admin_user.class, sql, null);
		return list;
	}
	
//	private static int NumOfBooks=7;
//	
//	private static int getNum(){
//		BookDaoImpl impl=new BookDaoImpl();
//		Integer i=impl.getNumOfTab("select count(b_id) sum from bookinfo",null);
//		return i.intValue();
//	}
	
	public static String getmsg(){
		return "消息推送";
	}
}
