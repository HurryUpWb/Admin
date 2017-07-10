package Filter;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public abstract class HttpFilter implements Filter{
	
	/**
	 * 保存FilterConfig对象
	 */
	private FilterConfig fconfig;

	/**
	 * @return
	 * 返回FilterConfig的对象
	 */
	public FilterConfig getFconfig() {
		return fconfig;
	}
	
	/**
	 * 不建议子类直接覆盖，直接覆盖会导致fconfig不被初始化
	 */

	@Override
	public void init(FilterConfig filterconfig) throws ServletException {
		this.fconfig=filterconfig;
		init();
	}

	/**
	 * 供继承的初始化方法，可以通过getFconfig()获得FilterConfig对象
	 */
	protected void init() {
		
	}
	
	/**
	 *  原生的doFilter()方法，在方法内部把 ServletRequest request，ServletResponse response 转化为
	 *  	HttpServletRequest 和HttpServletResponse
	 *  
	 */

	@Override
	public void doFilter(ServletRequest servletrequest, ServletResponse servletresponse, FilterChain filterchain)
			throws IOException, ServletException {
			HttpServletRequest req=(HttpServletRequest) servletrequest;
			HttpServletResponse resp=(HttpServletResponse) servletresponse;
			try {
				doFilter(req, resp, filterchain);
			} catch (Exception e) {
				e.printStackTrace();
			}
	}
	/**
	 * 
	 * @param req
	 * @param resp
	 * @param filterchain
	 * 供子类重写的抽象方法，为Http请求定制，必须实现的方法
	 * @throws Exception 
	 */
	public abstract void doFilter(HttpServletRequest req,HttpServletResponse resp,FilterChain filterchain) throws Exception;

	@Override
	public void destroy() {
		
	}

}
