package DAO;

import java.util.List;
import Beans.Type;

public interface TypeDao {
	/**
	 * 根据No获取Type
	 * @param No
	 * @return
	 */
	public  Type getTypeByNo(String No);
	
	/**
	 * 获得Type List
	 * @return
	 */
	public List<Type> getAllType();
}
