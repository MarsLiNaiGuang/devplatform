package com.rs.devplatform.service.admin;

import java.util.List;

import com.rs.devplatform.persistent.SysPj;
import com.rs.devplatform.vo.PjDBInfoVO;
import com.rs.devplatform.vo.ProjectInfo;
import com.rs.framework.common.entity.persistent.SysUsers;

public interface ProjectService {

	public List<SysPj> getPjList4User(String userId);
	
	public SysPj createProject(ProjectInfo pjInfo, String userId);
	
	public boolean updateProject(ProjectInfo pjInfo, String userId);
	
	public boolean deleteProject(String pjId, String userId, int version);
	
	public boolean assignManager2Pj(ProjectInfo info, String userId, String optType);
	
	public List<SysUsers> getAssignedUser4Pj(String pjId, String userType);
	
	public List<SysUsers> getAssignedPM4Pj(String pjId);
	
	public boolean testdb(PjDBInfoVO dbInfo);
}
