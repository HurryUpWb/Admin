package service;

import java.util.List;
import java.util.Map;

import Beans.Book;
import DAOImpl.BookDaoImpl;
import DAOImpl.OrderDaoImpl;
import Utils.Page;

public class OrderService {
	private static OrderDaoImpl impl=new OrderDaoImpl();
	
	/**
	 * 按类别获取
	 * @param page
	 * @param category
	 * @param val
	 * @return
	 */
	public static List<Map<String,Object>> ListAllOrder(Page page,String category,String val){
		StringBuffer sb=new StringBuffer();
		sb.append("SELECT o.*,u.u_account,p.p_state FROM orders o "
				+ "LEFT JOIN userinfo u "
				+ "ON o.u_id=u.u_id "
				+ "LEFT JOIN payment p "
				+ "ON o.o_id=p.o_id ");
		if(category!=null && !category.equals("") && val!=null && !val.equals("")){
			if(category.equals("o.o_isdeliver")){
				if(val.equals("是")){
					sb.append(" where "+category+"=1");
				}else{
					sb.append(" where "+category+"=0");
				}
			}else{
				sb.append(" where "+category+"='"+val+"'");
			}
		}
		sb.append(" limit ?,?");
		List<Map<String,Object>> list=impl.getInfo(sb.toString(),(page.getpageIndex()-1)*page.getPerPageNo(),page.getPerPageNo().intValue());
		BookDaoImpl bkimpl=new BookDaoImpl();
		for(Map<String,Object> map:list){
			String bookname="";
			String str=(String)map.get("o_booksname");
			String[]bks=str.split(",");
			for(String ss:bks){
				if(ss!=null && !ss.equals("")){
					Book b=bkimpl.getBookById(Integer.parseInt(ss));
					bookname=bookname+",《"+b.getB_bookname()+"》";
				}else{
					continue;
				}
			}
			map.put("bkname", bookname.substring(1));
		}
		return list;
	}
	
	/**
	 * 获取总数
	 * @return
	 */
	public static Integer getSumOfTab(String category,String val){
		StringBuffer sb=new StringBuffer();
		sb.append("SELECT count(o.o_id) sum FROM orders o "
				+ "LEFT JOIN userinfo u "
				+ "ON o.u_id=u.u_id "
				+ "LEFT JOIN payment p "
				+ "ON o.o_id=p.o_id");
		if(category!=null && !category.equals("") && val!=null && !val.equals("")){
			if(category.equals("o.o_isdeliver")){
				if(val.equals("是")){
					sb.append(" where "+category+"=1");
				}else{
					sb.append(" where "+category+"=0");
				}
			}else{
				sb.append(" where "+category+"='"+val+"'");
			}
		}
		return impl.getNumOfTab(sb.toString(),null);
	}
}
