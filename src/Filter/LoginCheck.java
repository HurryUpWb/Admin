package Filter;

import java.util.Arrays;
import java.util.List;

import javax.servlet.FilterChain;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class LoginCheck  extends HttpFilter {
	private String SessionVo;
	private String redirectPage;
	private String UncheckedUrls;
	
	/**
	 * 若有参数配置 则重写此方法
	 */
	@Override
	protected void init() {
		ServletContext config= getFconfig().getServletContext();
		SessionVo=config.getInitParameter("sessionVo");
		redirectPage=config.getInitParameter("redirectPage");
		UncheckedUrls=config.getInitParameter("UncheckedUrls");
	}

	@Override
	public void doFilter(HttpServletRequest request, HttpServletResponse response, FilterChain filterchain) throws Exception {
		HttpSession session=request.getSession();
		String str=request.getServletPath();
		List<String> urls=Arrays.asList(UncheckedUrls.split(","));
		if(urls.contains(str)){
			filterchain.doFilter(request, response);
			return;
		}else{
			Object obj=session.getAttribute(SessionVo);
			if(obj==null){
				response.setCharacterEncoding("utf8");
				response.getWriter().write("<script>alert('登录超时！')</script>");
				response.sendRedirect(request.getContextPath()+redirectPage);
				return;
			}else{
				filterchain.doFilter(request, response);
			}
		}
	}
	
	

	
}
