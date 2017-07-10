package Utils;

import java.util.List;

import DAO.Dao;
/***
 * 翻页操作，使用泛型提高重用性
 * @author Administrator
 *
 * @param <T>
 */
public class TurnPage<T> {
	List<T> list=null;
	Page page=null;
	
	
	public TurnPage(Page page) {
		this.page=page;
	}

	@SuppressWarnings("unchecked")
	public  List<T> Turn(String sql,T t){
//		System.out.println(sql);
//		System.out.println(t);
		list=(List<T>) Dao.getAll(t.getClass(),sql, (page.getpageIndex()-1)*page.getPerPageNo(),page.getPerPageNo().intValue());
		return list;
	}
}
