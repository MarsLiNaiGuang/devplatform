package com.rs.devplatform.aop;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.rs.devplatform.conf.db.DynamicDataSource;
import com.rs.framework.common.Constants;

@Aspect
@Component
public class ProjectDsAspect {

	@Before("within(@org.springframework.stereotype.Controller *)")
	public void checkFunctionAuth(final JoinPoint joinPoint) {
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
		HttpSession session = request.getSession();
		String url = request.getRequestURI();
		if(url.contains("/manager/project/") && url.endsWith("/switch")){
			//switch project, set default datasource
			session.removeAttribute(Constants.PJID);
		}
		DynamicDataSource.setDs4Project((String) session.getAttribute(Constants.PJID));
	}

}
