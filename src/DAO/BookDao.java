package DAO;

import java.util.List;

import Beans.Book;

public interface BookDao {
	/**
	 * 查找书籍
	 * @param id
	 * @return
	 */
	public Book getBookById(Integer id);
	
	/**
	 * 修改书籍信息
	 * @param b
	 * @return
	 */
	public boolean UpdadeBook(Book b);
	
	/**
	 * 根据ID删除书籍
	 * @param id
	 * @return
	 */
	public boolean DeleteBookById(Integer id);
	
	/**
	 * 保存书籍
	 * @param b
	 * @return
	 */
	public boolean SaveBook(Book b);
	
	/**
	 * 查找Book List
	 * @return
	 */
	public List<Book> getAllBook();
	
	public List<Book> getAllBook(String sql,Object...objects);
}
