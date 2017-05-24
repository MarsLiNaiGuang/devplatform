package com.rs.devplatform.service.common;

import java.util.List;

import com.rs.devplatform.persistent.SysCdval;

public interface CodeService {
	
	public List<SysCdval> getCodeListByCodeType(String codeType);
	
	public List<SysCdval> getCodeListByDistTable(String dictTable, String cdvalCol, String cddescpCol);

}
