package Servlet;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import Beans.Book;
import Beans.NumOfType;
import Beans.Type;
import Utils.Page;
import service.BookService;

public class BookServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String submethod=request.getServletPath();
		String m=submethod.substring(1, submethod.length()-5);
	    try {
			 Method usemethod=getClass().getDeclaredMethod(m, HttpServletRequest.class,HttpServletResponse.class);
			 usemethod.invoke(this, request,response);
		} catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException
				| InvocationTargetException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doPost(req, resp);
	}
	
	
	/**
	 * 获取所有类型 
	 * @param request
	 * @param response
	 */
	@SuppressWarnings("unused")
	private void gettype(HttpServletRequest request,HttpServletResponse response){
			List<Type> list=BookService.getAllType();
			String str=request.getParameter("id");
			request.setAttribute("types", list);
			if(str!=null && str.equals("all")){
				request.getSession().setAttribute("item", "批量新增");
				try {
					request.getRequestDispatcher("/jsp/bookview/AddNewBooks.jsp").forward(request, response);
				} catch (ServletException | IOException e) {
					e.printStackTrace();
				}
			}else{
				request.setAttribute("item", "新增图书");
				try {
					request.getRequestDispatcher("/jsp/bookview/AddNewBook.jsp").forward(request, response);
				} catch (ServletException | IOException e) {
					e.printStackTrace();
				}
			}
	}
	
	/**
	 * 添加新书
	 * @param request
	 * @param response
	 */
	@SuppressWarnings("unused")
	private void AddBook(HttpServletRequest request,HttpServletResponse response){
		Map<String,String> map;
		map=BookService.UploadPic(request);
		boolean b=BookService.SaveBook(map);
		
		response.setCharacterEncoding("utf8");
		response.setContentType("text/html ; charset=UTF-8");
		try {
			if(!b){
				response.getWriter().write("<script>alert('添加成功！')</script>");
				response.getWriter().write("<script>window.location.href='/Admin/gettype.show'</script>");
			}else{
				response.getWriter().write("<script>alert('添加失败！')</script>");
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 查看所有书
	 * @param request
	 * @param response
	 */
	@SuppressWarnings("unused")
	private void ShowBook(HttpServletRequest request,HttpServletResponse response){
		Page p=new Page();
		p.setTotalNum(BookService.getSumOfTab());
		p.setPerPageNo(7);
		p.setPageIndex(1);
		List<Book> list=BookService.TurnPages(p,null,null);
		request.setAttribute("blist",list);
		request.setAttribute("page",p);
		try {
			request.getRequestDispatcher("/jsp/bookview/ShowBooks.jsp").forward(request, response);
		} catch (ServletException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 翻页操作
	 * @param request
	 * @param response
	 */
	@SuppressWarnings("unused")
	private void Turn(HttpServletRequest request,HttpServletResponse response){
		//判断是否带条件
		Page p=(Page) request.getSession().getAttribute("page");
		String category=request.getParameter("category");
		String val=request.getParameter("val");
		
		if(category!=null && !category.equals("") && val!=null && !val.equals("")){
			try {
				val=new String(val.getBytes("iso8859-1"),"utf8");
				category=new String(category.getBytes("iso8859-1"),"utf8");
			} catch (UnsupportedEncodingException e1) {
				e1.printStackTrace();
			}
			request.setAttribute("category", category);
			request.setAttribute("val",val);
			p.setTotalNum(BookService.getSumOfTabByCategory(category, val));
		}else{
			p.setTotalNum(BookService.getSumOfTab());
		}
		
		//翻页的操作
		String index=request.getParameter("C");
		String pageindex=request.getParameter("index");
		if(index!=null){
			switch (index) {
			case "N":
				p.setPageIndex(p.getpageIndex()+1);
				break;
			case "P":
				p.setPageIndex(p.getpageIndex()-1);
				break;
			case "F":
				p.setPageIndex(1);
				break;
			case "L":
				p.setPageIndex(p.getTotalPage());
				break;
			}
		}else if(pageindex!=null && !pageindex.equals("")){
			p.setPageIndex(Integer.parseInt(pageindex));
		}
		List<Book> list=null;
		if(category!=null && !category.equals("") && val!=null && !val.equals("")){
			list=BookService.TurnPages(p,category,val);
		}
		else{
			list=BookService.TurnPages(p, null, null);
		}
		request.setAttribute("blist",list);
		request.setAttribute("page",p);
		try {
			request.getRequestDispatcher("/jsp/bookview/ShowBooks.jsp").forward(request, response);
		} catch (ServletException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 修改
	 * @param request
	 * @param response
	 */
	@SuppressWarnings("unused")
	private void EditBook(HttpServletRequest request,HttpServletResponse response){
		String b_id=request.getParameter("id");
		List<Type> list=BookService.getAllType();
		Book b=new Book();
		if(b_id!=null && !b_id.equals("")){
			b=BookService.getBookById(Integer.parseInt(b_id));
		}
		request.setAttribute("book", b);
		request.setAttribute("types", list);
		request.setAttribute("item", "修改图书信息");
		try {
			request.getRequestDispatcher("/jsp/bookview/AddNewBook.jsp").forward(request, response);
		} catch (ServletException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	@SuppressWarnings("unused")
	private void SaveEdit(HttpServletRequest request,HttpServletResponse response){
		boolean b=BookService.SaveEdit(request);
		response.setCharacterEncoding("utf8");
		response.setContentType("text/html ; charset=UTF-8");
		if(!b){
			try {
				response.getWriter().write("<script>alert('修改成功！');</script>");
				response.getWriter().write("<script>window.location.href='/Admin/ShowBook.show'</script>");
			} catch (IOException e) {
				e.printStackTrace();
			}
		}else{
			try {
				response.getWriter().write("<script>alert('修改失败！');</script>");
				response.getWriter().write("<script>history.go(-1);</script>");
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	@SuppressWarnings("unused")
	private void Delete(HttpServletRequest request,HttpServletResponse response){
		String bid=request.getParameter("bid");
		boolean b=BookService.DeleteBook(Integer.parseInt(bid));
		response.setCharacterEncoding("utf8");
		response.setContentType("text/html ; charset=UTF-8");
		if(!b){
			try {
				response.getWriter().write("<script>alert('删除成功！');</script>");
				response.getWriter().write("<script>window.location.href='/Admin/ShowBook.show';</script>");
			} catch (IOException e) {
				e.printStackTrace();
			}
		}else{
			try {
				response.getWriter().write("<script>alert('删除失败！');</script>");
				response.getWriter().write("<script>window.location.href='/Admin/ShowBook.show';</script>");
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	/**
	 * 分类统计
	 * @param request
	 * @param response
	 */
	@SuppressWarnings("unused")
	private void Classify(HttpServletRequest request,HttpServletResponse response){
		List<NumOfType> list=BookService.getNumOfType();
		int sum=BookService.getSumOfBooks().intValue();
		request.setAttribute("list",list);
		request.setAttribute("sum", sum);
		try {
			request.getRequestDispatcher("/jsp/bookview/ShowNumOfType.jsp").forward(request, response);
		} catch (ServletException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 下载模板xlsx
	 * @param request
	 * @param response
	 */
	@SuppressWarnings("unused")
	private void downloadModel(HttpServletRequest request,HttpServletResponse response){
		BookService.DownLoadModel(request, response);
	}
	
	/**
	 *  批量上传
	 * @param request
	 * @param response
	 */
	@SuppressWarnings("unused")
	private void AddBooksFromFile(HttpServletRequest request,HttpServletResponse response){
		String path=BookService.UploadAllFile(request);
		BookService.UploadBooks(BookService.ReadExcle(path));
		response.setContentType("text/html ; charset=UTF-8");
		try {
			response.getWriter().write("<script>alert('添加成功！')</script>");
			response.getWriter().write("<script>window.location.href='/Admin/ShowBook.show'</script>");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
