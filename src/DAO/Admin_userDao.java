package DAO;

import Beans.Admin_user;

public interface Admin_userDao {
	
	/**
	 * 获得admin用户
	 * @param id
	 * @return
	 */
	public Admin_user getAdmin_userById(Integer id);
	
	public Admin_user getAdmin_userByName(String name);
	
	/**
	 * 更新Admin用户
	 * @param user
	 * @return
	 */
	public boolean UpdateAdmin_user(Admin_user user);
	
}
