package DAO;

import java.util.List;

import Beans.User;

public interface UserDao {
	/**
	 * 新增用户
	 * @param u
	 * @return
	 */
//	public boolean AddUser(User u);
	
	/**
	 * 根据ID获取用户
	 * @param id
	 * @return
	 */
	public User getUserById(Integer id);
	
	/**
	 * 根据ID删除用户
	 * @param id
	 * @return
	 */
	public boolean DeleteUserById(Integer id);
	
	/**
	 * 更新用户信息
	 * @param id
	 * @return
	 */
	public boolean UpdateUser(User u);
	
	/**
	 * 获取用户List
	 * @return
	 */
	public List<User> getAllUser();

}
