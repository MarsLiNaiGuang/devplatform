/**
 *Copyright (c) 2016-2017, Roadshare.com.
 */
<#if subModule?? && subModule!="">
package ${basePackage}.service.${subModule}.impl;
<#else>
package ${basePackage}.service.impl;
</#if>

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

<#if subModule?? && subModule!="">
import ${basePackage}.persistent.${subModule}.${beanName};
import ${basePackage}.service.${subModule}.${beanName}Service;
<#else>
import ${basePackage}.persistent.${beanName};
import ${basePackage}.service.${beanName}Service;
</#if>
import com.rs.framework.common.Constants;
import com.rs.framework.common.RsGeneralMapper;

@Service
public class ${beanName}ServiceImpl implements ${beanName}Service {

	@Autowired
	RsGeneralMapper generalMapper;
	<#assign beanVar="${beanName[0..1]?lower_case}${beanName[2..]}">	
	
	@Transactional
	public boolean add(${beanName} ${beanVar}){
		generalMapper.insert(${beanVar});
		return true;
	}
	
	@Transactional
	public boolean update(${beanName} ${beanVar}){
		String id = ${beanVar}.getId();
		Integer version = ${beanVar}.getVersion();
		if(id==null || version==null){
			return false;
		}
		${beanName} entityWhere = new ${beanName}();
		entityWhere.setId(id);
		entityWhere.setDeleted(Constants.DelInd.FALSE);
		entityWhere.setVersion(version);
		${beanVar}.setVersion(version+1);
		int effRows = generalMapper.updateSelective(${beanVar}, entityWhere);
		if(effRows==0){//update failed
			return false;
		}else{
			return true;
		}
	}
	
	@Transactional
	public boolean delete(${beanName} ${beanVar}){
		String id = ${beanVar}.getId();
		Integer version = ${beanVar}.getVersion();
		if(id==null || version==null){
			return false;
		}
		${beanName} entityWhere = new ${beanName}();
		entityWhere.setId(id);
		entityWhere.setDeleted(Constants.DelInd.FALSE);
		entityWhere.setVersion(version);
		${beanName} entity = new ${beanName}();
		entity.setDeleted(Constants.DelInd.TRUE);
		entity.setVersion(version+1);
		int effRows = generalMapper.updateSelective(entity, entityWhere);
		if(effRows==0){//update failed
			return false;
		}else{
			return true;
		}
	}
	
	@Transactional
	public boolean deleteBatch(String[] ids){
		List<String> idList = Arrays.asList(ids);
		int effRow = generalMapper.deleteBatchIds(idList, ${beanName}.class);
		if(effRow!=0){
			return true;
		}
		return false;
	}
	
}
