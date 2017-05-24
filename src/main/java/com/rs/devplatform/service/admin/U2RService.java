package com.rs.devplatform.service.admin;

import java.util.List;

import com.rs.framework.common.entity.persistent.SysU2r;
import com.rs.framework.common.entity.persistent.SysUsers;

public interface U2RService {
	public boolean addU2r(String roleId, List<String> userIdList);
	public boolean deleteU2r(String roleId, List<String> userIdList);
	public List<SysUsers> getU2R(String roleId);
	public boolean updateu2r(SysU2r u2r);
}
