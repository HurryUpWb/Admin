package DAOImpl;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import Beans.Book;
import Beans.NumOfType;
import DAO.BookDao;
import DAO.Dao;

public class BookDaoImpl extends Dao implements BookDao{

	private static Logger log=Logger.getLogger(BookDaoImpl.class);
	@Override
	public Book getBookById(Integer id) {
		StringBuffer sb=new StringBuffer();
		sb.append("select * from bookinfo where b_id=?");
		Book b=get(Book.class, sb.toString(),id);
		return b;
	}
	

	@Override
	public boolean UpdadeBook(Book b) {
		StringBuffer sb=new StringBuffer();
		sb.append("update bookinfo set ");
		sb.append("b_booktype=?,b_pubs=?,b_bookname=?,b_author=?,b_price=?,b_description=?,b_pic=?,b_pic_large=?,b_show=?");
		sb.append(" where b_id=?");
		log.info("SQL 语句:"+sb.toString());
		boolean flag=update(sb.toString(), b.getB_booktype(),b.getB_pubs(),b.getB_bookname(),b.getB_author(),b.getB_price()
														,b.getB_description(),b.getB_pic(),b.getB_pic_large(),b.getB_show(),b.getB_id());
		return flag;
	}

	@Override
	public boolean DeleteBookById(Integer id) {
		StringBuffer sb=new StringBuffer();
		sb.append("delete from bookinfo where b_id=?");
		log.info("SQL 语句:"+sb.toString());
		boolean b=update(sb.toString(),id);
		return b;
	}

	@Override
	public List<Book> getAllBook() {
		String sql="select * from bookinfo";
		List<Book> list=getAll(Book.class, sql);
		return list;
	}

	/**
	 * 根据不同需求获取
	 */
	@Override
	public List<Book> getAllBook(String sql, Object... objects) {
		List<Book> list=getAll(Book.class, sql, objects);
		return list;
	}

	/**
	 * 新增书籍
	 */
	@Override
	public boolean SaveBook(Book b) {
		StringBuffer sb=new StringBuffer();
		sb.append("insert into bookinfo(b_booktype,b_pubs,b_bookname,b_author,b_price,b_description,b_pic,b_pic_large,b_show) ");
		sb.append(" values(?,?,?,?,?,?,?,?,?)");
		log.info("SQL 语句:"+sb.toString());
		boolean bs=update(sb.toString(),b.getB_booktype(),b.getB_pubs(),b.getB_bookname(),b.getB_author(),b.getB_price(),b.getB_description(),b.getB_pic(),b.getB_pic_large(),b.getB_show());
		return bs;
	}
	
	/**
	 * 获取各个类别的书籍数目
	 * @return
	 */
	public List<NumOfType> getNumOfType(){
		List<NumOfType> list=new ArrayList<>();
		String sql="SELECT COUNT(b.b_id) Num, t.TYPEINFO as TYPEINFO FROM bookinfo b "
				+ " LEFT JOIN TYPE t "
				+ " on b.b_booktype=t.TYPENO"
				+ " GROUP BY t.TYPEINFO "
				+ " ORDER BY Num desc";
		list=getAll(NumOfType.class, sql, null);
		return list;
	}
	
	
}
