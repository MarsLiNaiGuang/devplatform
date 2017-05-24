package com.rs.devplatform.service.admin;

import java.util.List;
import com.rs.devplatform.persistent.SysCdtype;
import com.rs.devplatform.persistent.SysCdval;

public interface DataDictService {

	public SysCdtype createCodeType(SysCdtype cdtype);

	public List<SysCdval> getValListByCdtype(String cdtype);
	
	public boolean updateCodeType(SysCdtype cdtype);
	
	public boolean deleteCodeType(SysCdtype cdtype);
	
	public boolean addCodeVal4Type(String typeId, SysCdval[] valArray);
	
	public boolean deleteCodeVal(String cdvalId);
	
	public boolean updateCodeVal(SysCdval cdval);
}
