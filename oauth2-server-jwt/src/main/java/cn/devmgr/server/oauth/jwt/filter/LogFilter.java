package cn.devmgr.server.oauth.jwt.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.core.annotation.Order;


/**
 * 仅仅是为了显示些日志，以便观察Resource Server/Client都对AuthServer做了哪些调用。
 *
 */
@Order(1)
@WebFilter(filterName = "logfilter", urlPatterns = "/*")
public class LogFilter implements Filter {
	private final static Log log = LogFactory.getLog(LogFilter.class);
	
	@Override
	public void destroy() {
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest) request;
		if(log.isTraceEnabled()){
			log.trace("\r\n\r\n-----------\r\nURI:" + req.getRequestURI() + 
					"\r\nURL:" + req.getRequestURL() + "\r\n\r\n----------");
		}
		chain.doFilter(req, response);
		
		// HttpServletResponse resp = (HttpServletResponse) response;
		
	}

	@Override
	public void init(FilterConfig config) throws ServletException {

	}

}
