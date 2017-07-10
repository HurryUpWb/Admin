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
	 * ����FilterConfig����
	 */
	private FilterConfig fconfig;

	/**
	 * @return
	 * ����FilterConfig�Ķ���
	 */
	public FilterConfig getFconfig() {
		return fconfig;
	}
	
	/**
	 * ����������ֱ�Ӹ��ǣ�ֱ�Ӹ��ǻᵼ��fconfig������ʼ��
	 */

	@Override
	public void init(FilterConfig filterconfig) throws ServletException {
		this.fconfig=filterconfig;
		init();
	}

	/**
	 * ���̳еĳ�ʼ������������ͨ��getFconfig()���FilterConfig����
	 */
	protected void init() {
		
	}
	
	/**
	 *  ԭ����doFilter()�������ڷ����ڲ��� ServletRequest request��ServletResponse response ת��Ϊ
	 *  	HttpServletRequest ��HttpServletResponse
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
	 * ��������д�ĳ��󷽷���ΪHttp�����ƣ�����ʵ�ֵķ���
	 * @throws Exception 
	 */
	public abstract void doFilter(HttpServletRequest req,HttpServletResponse resp,FilterChain filterchain) throws Exception;

	@Override
	public void destroy() {
		
	}

}
