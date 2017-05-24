package com.rs.devplatform.aop;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.rs.devplatform.vo.UserInfo;
import com.rs.framework.common.Constants;
import com.rs.framework.common.entity.base.CUBaseTO;
import com.rs.framework.common.utils.UserSessionUtil;

@Aspect
@Component
public class CRUDAop {
	
	private Logger logger = LoggerFactory.getLogger(getClass());

	@Pointcut("execution(public * com.github.yuxiaobin.mybatis.gm.GeneralMapper.insert*(..))")
	private void baseMapInsert(){
		
	}
	@Pointcut("execution(public * com.github.yuxiaobin.mybatis.gm.GeneralMapper.update*(..))")
	private void baseMapUpdate(){
		
	}
	@Pointcut("execution(public * com.github.yuxiaobin.mybatis.gm.GeneralMapper.delete*(..))")
	private void baseMapDelete(){
		
	}
	
	@Pointcut("baseMapInsert() ||baseMapUpdate() || baseMapDelete()")//|| insertService()
	public void embeddedPt(){
		
	}

	@SuppressWarnings({"unchecked" })
	@Before("embeddedPt()")
	public void doBeforeModify(JoinPoint joinPoint) throws Throwable {
		RequestAttributes attr = RequestContextHolder.getRequestAttributes();
		String methodName = joinPoint.getSignature().getName();
		Object[] args = joinPoint.getArgs();
		if(attr==null){
			logger.warn("RequestAttributes is null for methodName="+methodName+",args[0]="+args[0]);
			return;
		}
		HttpServletRequest request = ((ServletRequestAttributes) attr).getRequest();
		CUBaseTO baseTO = UserSessionUtil.getUserSession();
		if(baseTO==null){
			Map<String,Object> userinfo = (Map<String, Object>) request.getSession().getAttribute(Constants.USERINFO);
			baseTO = new UserInfo();
			String username = null;
			String nickname = null;
			if(userinfo==null || userinfo.get(Constants.ID) ==null){
				username = "system";
				nickname = username;
			}else{
				username = (String)userinfo.get(Constants.USERNAME);
				nickname = (String)userinfo.get(Constants.NICKNAME);
			}
			baseTO.setCjrid(username);
			baseTO.setWhrid(username);
			baseTO.setCjr(nickname);
			baseTO.setWhr(nickname);
			UserSessionUtil.setUserSession(baseTO);
		}
		
		boolean isInsert = false;
		boolean isUpdate = false;
		if(methodName.startsWith("insert") ){
			isInsert = true;
		}
		if(methodName.startsWith("update")){
			isUpdate = true;
		}
		if(isInsert||isUpdate){
			if(args!=null && args.length!=0){
				Object arg0 = args[0];
				if(arg0 instanceof CUBaseTO){
					CUBaseTO baseArg = (CUBaseTO)arg0;
					if(isInsert){
						baseArg.setCjrid(baseTO.getCjrid());
						baseArg.setCjr(baseTO.getCjr());
						if(baseArg.getCjsj()==null){
							baseArg.setCjsj(new Date());
						}
						baseArg.setDeleted(Constants.DelInd.FALSE);
						baseArg.setVersion(1);
					}else if(isUpdate){
						baseArg.setWhrid(baseTO.getWhrid());
						baseArg.setWhr(baseTO.getWhr());
						baseArg.setWhsj(new Date());
					}
				}
				else if(arg0 instanceof List){
					List<Object> list = (List<Object>)arg0;
					for(Object obj:list){
						if(obj instanceof CUBaseTO){
							CUBaseTO baseArg = (CUBaseTO)obj;
							if(isInsert){
								baseArg.setCjrid(baseTO.getCjrid());
								baseArg.setCjr(baseTO.getCjr());
								if(baseArg.getCjsj()==null){
									baseArg.setCjsj(new Date());
								}
								baseArg.setDeleted(Constants.DelInd.FALSE);
								baseArg.setVersion(1);
							}else if(isUpdate){
								baseArg.setWhrid(baseTO.getWhrid());
								baseArg.setWhr(baseTO.getWhr());
								baseArg.setWhsj(new Date());
							}
						}
					}
				}
			}
			return;
		}
	}

}
