package com.rs.devplatform.aop;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.rs.devplatform.vo.UserInfo;
import com.rs.framework.common.Constants;
import com.rs.framework.common.entity.base.CUBaseTO;
import com.rs.framework.common.utils.UserSessionUtil;

@Aspect
@Component
public class UserInfoAspect {

	@Before("within(@org.springframework.stereotype.Controller *)")
	public void setUserInfo(final JoinPoint joinPoint) {
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
		Object userInfoSession = request.getSession().getAttribute(Constants.USERINFO);
		if(userInfoSession!=null){
			@SuppressWarnings("unchecked")
			Map<String, Object> userInfoMap = (Map<String, Object>) userInfoSession;
			String userId = (String) userInfoMap.get(Constants.ID);
			String username = (String) userInfoMap.get(Constants.USERNAME);
//			String nkname = (String) userInfoMap.get(Constants.NICKNAME);
//			String userFlag = (String) userInfoMap.get(Constants.USER_FLAG);//admin or normal user
			CUBaseTO user = new UserInfo();
			user.setCjrid(userId);
			user.setCjr(username);
			user.setWhrid(userId);
			user.setWhr(username);
			
			UserSessionUtil.setUserSession(user);
		}
	}

}
