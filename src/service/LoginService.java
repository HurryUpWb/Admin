package service;

import java.text.DateFormat;
import java.text.FieldPosition;
import java.text.Format;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import Beans.Admin_user;
import DAOImpl.AdminUserDaoImpl;

public class LoginService {
	private static Logger log=Logger.getLogger(LoginService.class);
	
	/**
	 * 判断是否存在该管理员
	 * @param username
	 * @param password
	 * @return
	 */
	public static boolean IsAdminUser(String username,String password){
		AdminUserDaoImpl impl=new AdminUserDaoImpl();
		Admin_user user=impl.getAdmin_userByName(username);
		boolean b=true;
		if(user.getName()==null && user.getPassword()==null){
			b=false;
		}else{
			log.info("管理员用户："+username+"登录成功！");
			b=true;
		}
		return b;
	}
	
	/**
	 * 根据姓名获取管理员的权限
	 * @param name
	 * @return
	 */
	public static Integer getUserAuthority(String name){
		AdminUserDaoImpl impl=new AdminUserDaoImpl();
		Integer i=impl.getAdmin_userByName(name).getAuthority();
		return i.intValue();
	}
	
	/**
	 * 获取登录IP
	 * @param request
	 * @return
	 */
	public static Map<String,String> getMap(HttpServletRequest request){
		Map<String,String> map=new HashMap<>();
		String username=request.getParameter("username");
		String ip=request.getRemoteHost();
		log.info("当前登录IP地址："+ip);
		Date date=new Date();
		DateFormat format=new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		String str=format.format(date);
		map.put("username",username);
		map.put("ip", ip);
		map.put("date",str);
		return map;
	}
	
	/**
	 * 存储管理员用户登录信息
	 * @param map
	 * @return
	 */
	public static boolean SaveLoginInfo(Map<String,String> map){
		AdminUserDaoImpl impl=new AdminUserDaoImpl();
		boolean b=impl.SaveAdmLogInfo(map);
		return b;
	}
}
