package DAOImpl;

import java.util.List;

import Beans.Type;
import DAO.Dao;
import DAO.TypeDao;

public class TypeDaoImpl extends Dao implements TypeDao {

	@Override
	public  Type getTypeByNo(String No) {
		StringBuffer sql=new StringBuffer();
		sql.append("select * from TYPE where TYPENO=?");
		Type t=get(Type.class, sql.toString(),No);
		return t;
	}

	@Override
	public  List<Type> getAllType() {
		List<Type> list=null;
		String sql="select * from TYPE";
		list=getAll(Type.class, sql);
		return list;
	}
	
	public Type getTypeByInfo(String str){
		StringBuffer sb=new StringBuffer();
		sb.append("select * from TYPE where TYPEINFO=?");
		Type t=get(Type.class, sb.toString(),str);
		return t;
	}

}
