package com.rs.devplatform.persistent.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.rs.devplatform.persistent.SysPj;
import com.rs.framework.common.entity.persistent.SysUsers;

public interface SysProjectMapper {
	
	@Select(" select PJS_ID as id, PJS_NAME as name, PJS_CJR as cjr, PJS_WHR as whr, PJS_CJSJ as cjsj, PJS_WHSJ as whsj "
			+ " from sys_pjs,sys_pj2u where PJS_DELETED=#{delInd} and PJS_ID=PJ2U_PJ_ID and PJ2U_USER_ID=#{userId}")
	/*@Results({
        @Result(id=true,property="instId",column="INST_ID",javaType=String.class),
        @Result(property="currAssigners",column="CURR_ASSIGNERS",javaType=String.class),
        @Result(property="optUsersPre",column="OPT_USERS_PRE",javaType=String.class)
    })*/
	public List<SysPj> getProjects4User(@Param("userId") String userId, @Param("delInd") String delInd);

	/**
	 * 
	 * @param pjId
	 * @param userType  PM/TM/null:all
	 * @param filteredUserId 
	 * @return
	 */
	public List<SysUsers> getAssignedUser4Pj(@Param("pjId") String pjId, @Param("userType") String userType, @Param("filteredUserId") String filteredUserId);
	
	
}
