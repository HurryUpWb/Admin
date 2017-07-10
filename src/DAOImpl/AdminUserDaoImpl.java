package DAOImpl;

import java.util.Map;

import Beans.Admin_user;
import DAO.Admin_userDao;
import DAO.Dao;

public class AdminUserDaoImpl extends Dao implements Admin_userDao {

	@Override
	public Admin_user getAdmin_userById(Integer id) {
		StringBuffer sql=new StringBuffer();
		sql.append("select * from admin_user where id=? ");
		Admin_user user=get(Admin_user.class, sql.toString(),id );
		return user;
	}
	
	@Override
	public Admin_user getAdmin_userByName(String name) {
		StringBuffer sb=new StringBuffer();
		sb.append("select * from admin_user where name=?");
		Admin_user user=get(Admin_user.class,sb.toString(),name);
		return user;
	}

	@Override
	public boolean UpdateAdmin_user(Admin_user user) {
		StringBuffer sb=new StringBuffer();
		sb.append("update admin_user set name=? ,password=?, authority=? where id=? ");
		boolean b=update(sb.toString(), user.getName(),user.getPassword(),user.getAuthority(),user.getId());
		return b;
	}
	
	public boolean SaveAdmLogInfo(Map<String,String> map){
		StringBuffer sb=new StringBuffer();
		sb.append("insert into admlog(a_ip,a_time,a_user) values(?,?,?)");
		boolean b=update(sb.toString(), map.get("ip"),map.get("date"),map.get("username"));
		return b;
	}


}
