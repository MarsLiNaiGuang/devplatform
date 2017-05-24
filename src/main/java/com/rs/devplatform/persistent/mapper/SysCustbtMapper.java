package com.rs.devplatform.persistent.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.rs.devplatform.persistent.SysCustbt;

public interface SysCustbtMapper {
	
	
	@Deprecated
	//根据表id获取工具栏按钮信息
	@Select( " select CUSTBT_BDID, CUSTBT_CODE, CUSTBT_ICON, CUSTBT_NAME, CUSTBT_ZT, "
		    +"   CUSTBT_YS, CUSTBT_EXP, CUSTBT_BDID, CUSTBT_CZLX, CUSTBT_XH, CUSTBT_WHR, "
		    +"   CUSTBT_WHRID, CUSTBT_WHSJ, CUSTBT_CJR, CUSTBT_CJSJ, CUSTBT_CJRID, "
		    +"   CUSTBT_VERSION, CUSTBT_DELETED"
		    +" from SYS_CUSTBT "
		    +" where CUSTBT_BDID = #{bdId} and CUSTBT_DELETED = #{delInd}")
	public List<SysCustbt> getCustomButton(@Param("bdId") String bdId, @Param("delInd") String delInd);


}
