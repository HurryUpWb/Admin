package service;

import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Map;

import Beans.Book;
import Beans.Comment;
import Beans.User;
import DAO.Dao;
import DAOImpl.BookDaoImpl;
import DAOImpl.UserDaoImpl;
import Utils.Page;
import Utils.TurnPage;
import Utils.utils;

public class UserService {
	private static UserDaoImpl impl=new UserDaoImpl();
	
	/**
	 * 获取所有用户
	 * @return
	 */
	public static List<User> getAllUsers(){
		return impl.getAllUser();
	}
	
	public static int getSumOfTab(){
		return impl.getNumOfTab("select count(u_id) sum from userinfo",null);
	}
	/**
	 * 获取评论总条数
	 * @return
	 */
	public static int getsumofComments(){
		return impl.getNumOfTab("select count(cid) sum from comment",null);
	}
	
	/**
	 * 翻页操作
	 * @param page
	 * @param category
	 * @param val
	 * @return
	 */
	public static List<User> TurnUserPages(Page page,String category,String val){
		StringBuffer sql=new StringBuffer();
		List<User> list=null;
		if(category!=null && !category.equals("") && val!=null && !val.equals("")){
			if(category.equals("u_authority")){
				if(val.equals("普通会员")){
					sql.append("select * from userinfo where "+category+"='1' limit ?,?");
				}else{
					sql.append("select * from userinfo where "+category+"='2' limit ?,?");
				}
			}else{
				sql.append("select * from userinfo where "+category+"='"+val+"' limit ?,?");
			}
		}else{
			sql.append("select * from userinfo limit ?,?");
		}
		TurnPage<User> turn=new TurnPage<>(page);
		User u=new User();
		list=turn.Turn(sql.toString(), u);
		return list;
	}
	
	public static List<Comment> TurnCommPages(Page p,String category,String val){
		StringBuffer sb=new StringBuffer();
		List<Comment> list=null;
		if(!utils.IsNull(category) && !utils.IsNull(val)){
			try {
				val = new String(val.getBytes("iso8859-1"), "utf8");
				category = new String(category.getBytes("iso8859-1"), "utf8");
			} catch (UnsupportedEncodingException e1) {
				e1.printStackTrace();
			}
		}
		sb.append("SELECT c.cid,b.b_bookname ,u.u_account ,c.time ,c.content,c.isshow  "
				+ " FROM comment c "
				+ " LEFT JOIN bookinfo b ON c.bid=b.b_id "
				+ " LEFT JOIN userinfo u ON c.uid=u.u_id");
		if(!utils.IsNull(category) && !utils.IsNull(val)){
			if(category.equals("isshow")){
				if(val.equals("是")){
					sb.append(" where "+category+"=1 limit ?,?");
				}else{
					sb.append(" where "+category+"=0 limit ?,?");
				}
			}else{
				sb.append(" where "+category+"='"+val+"' limit ?,?");
			}
		}else{
			sb.append(" limit ?,?");
		}
		TurnPage<Comment> turn=new TurnPage<>(p);
		Comment comm=new Comment();
		list=turn.Turn(sb.toString(), comm);
		return list;
	}
 	
	/**
	 * 按条件获取userinfo表的记录数
	 * @param category
	 * @param val
	 * @return
	 */
	public static int getSumOfTabByCategory(String category,String val){
		Integer i;
		int temp=0;
		StringBuffer sql=new StringBuffer();
		sql.append("select count(u_id) sum from userinfo ");
		if(category.equals("u_authority")){
			if(val.equals("普通会员")){
				temp=1;
			}else{
				temp=2;
			}
			sql.append(" where "+category+"='"+temp+"'");
		}else{
			sql.append(" where "+category+"='"+val+"'");
		}
		i=Dao.getNumOfTab(sql.toString(),null);
		return i.intValue();
	}
	
	public static List<Map<String,Object>> getOinfo(int i){
		StringBuffer sb=new StringBuffer();
		sb.append("SELECT o.*,p.p_state,p.p_code FROM orders o "
				+ "LEFT JOIN payment p "
				+ "ON o.o_id=p.o_id "
				+ "WHERE o.u_id=? "
				+ " ORDER BY o.o_orderdate DESC ");
		List<Map<String,Object>> list=impl.getInfo(sb.toString(),i);
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
	
	public static boolean hideComment(String flag,int cid){
		return impl.hideComment(flag,cid);
	}
	
}
