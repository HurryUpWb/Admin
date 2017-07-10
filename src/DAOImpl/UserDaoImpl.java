package DAOImpl;

import java.util.List;
import org.apache.log4j.Logger;
import Beans.User;
import DAO.Dao;
import DAO.UserDao;

public class UserDaoImpl extends Dao implements UserDao {
	private static Logger log=Logger.getLogger(UserDao.class);
	
	@Override
	public User getUserById(Integer id) {
		StringBuffer sql=new StringBuffer();
		sql.append("select u_id,u_account,u_pwd,u_name,u_balance,u_authority,u_email,u_telephone from userinfo where u_id=? ");
		User u=get(User.class, sql.toString(), id);
		return u;
	}

	@Override
	public boolean DeleteUserById(Integer id) {
		String sql="delete from userinfo where u_id=?";
		boolean b=update(sql, id);
		return b;
	}

	@Override
	public boolean UpdateUser(User u) {
		StringBuffer sql=new StringBuffer();
		sql.append("update userinfo set ");
		sql.append("u_account=?,u_pwd=?,u_name=?,u_balance=?,u_authority=?,u_email=?,u_telephone=? ");
		sql.append("where u_id=?");
		log.info("SQL语句："+sql.toString());
		boolean b=update(sql.toString(),u.getU_account(),u.getU_pwd(),u.getU_name(),u.getU_balance(),u.getU_authority(),u.getU_email(),u.getU_telephone(),u.getU_id());
		return b;
	}

	@Override
	public List<User> getAllUser() {
		List<User> list=null;
//		select u_id,u_account,u_pwd,u_name,u_balance,u_authority,u_email,u_telephone from userinfo
		String sql="select * from userinfo";
		list=getAll(User.class, sql);
		return list;
	}
	
	public boolean hideComment(String flag,int cid){
		StringBuffer sb=new StringBuffer();
		if(flag!=null && flag!="" && "shw".equals(flag)){
			sb.append("UPDATE comment SET ISSHOW=1 WHERE cid=?");
		}else{
			sb.append("UPDATE comment SET ISSHOW=0 WHERE cid=?");
		}
		return update(sb.toString(),cid);
	}

}
