package cn.devmgr.example.springjersey.config;

import java.util.HashMap;
import java.util.Map;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.context.SecurityContextHolder;

public class ThrowableMapper implements ExceptionMapper<Throwable> {
	private static final Log log = LogFactory.getLog(ThrowableMapper.class);
	
	@Override
	public Response toResponse(Throwable ex) {
		Map<String, Object> messageMap = null;
		int responseStatus;
		if(ex instanceof AccessDeniedException){
			responseStatus = 401;
			messageMap = new HashMap<String, Object>();
			messageMap.put("message", ex.getMessage());
			messageMap.put("responseCode", 401);
			if(log.isTraceEnabled()){
				log.trace("权限不足[" + SecurityContextHolder.getContext().getAuthentication().getPrincipal() + "]", ex);
			}
		}else if(ex instanceof WebApplicationException){
			WebApplicationException wae = (WebApplicationException) ex;
			responseStatus = wae.getResponse().getStatus();
			messageMap = new HashMap<String, Object>();
			messageMap.put("message", wae.getMessage());
		}else{
			responseStatus = 500;
			messageMap = new HashMap<String, Object>();
			messageMap.put("message", ex.getMessage());
			messageMap.put("responseCode", 500);
			//这种错误是意外发生的，需要记录日志；前两种是程序主动抛出，不记录日志（如果需要抛异常处会记录）
			if(log.isErrorEnabled()){
				log.error("检测到出错", ex);
			}
		}
		return Response.status(responseStatus).entity(messageMap).type(MediaType.APPLICATION_JSON).build();
	}

}
