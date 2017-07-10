package Utils;

import java.io.UnsupportedEncodingException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import service.UserService;

public class utils {

	/**
	 * 判断某个对象是否为空 String、List
	 * 
	 * @param b
	 * @return
	 */
	public static boolean IsNull(Object b) {
		if (b instanceof String) {
			if (b != null && !b.equals("")) {
				return false;
			}
		}
		if (b instanceof List) {
			if (b != null && ((List) b).size() > 0) {
				return false;
			}
		}
		return true;
	}

	public static Page getpage(HttpServletRequest request,String sql) {
		// 判断是否带条件
		Page p = (Page) request.getSession().getAttribute("page");
		String category = request.getParameter("category");
		String val = request.getParameter("val");

		if (category != null && !category.equals("") && val != null && !val.equals("")) {
			try {
				val = new String(val.getBytes("iso8859-1"), "utf8");
				category = new String(category.getBytes("iso8859-1"), "utf8");
			} catch (UnsupportedEncodingException e1) {
				e1.printStackTrace();
			}
			request.setAttribute("category", category);
			request.setAttribute("val", val);
			p.setTotalNum(DAO.Dao.getNumOfTab(sql,category,val));
		} else {
			p.setTotalNum(DAO.Dao.getNumOfTab(sql, null));
		}

		// 翻页的操作
		String index = request.getParameter("C");
		String pageindex = request.getParameter("index");
		if (index != null) {
			switch (index) {
			case "N":
				p.setPageIndex(p.getpageIndex() + 1);
				break;
			case "P":
				p.setPageIndex(p.getpageIndex() - 1);
				break;
			case "F":
				p.setPageIndex(1);
				break;
			case "L":
				p.setPageIndex(p.getTotalPage());
				break;
			}
		} else if (!utils.IsNull(pageindex)) {
			Integer pgindex=Integer.parseInt(pageindex);
			p.setPageIndex(pgindex.intValue());
		}
		return p;
	}

}
