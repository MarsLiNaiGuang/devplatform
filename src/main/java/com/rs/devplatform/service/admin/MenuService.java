package com.rs.devplatform.service.admin;

import java.util.List;

import com.rs.framework.common.entity.persistent.SysMenus;
import com.rs.framework.common.entity.vo.SysMenuVO;

public interface MenuService {
	
	public List<SysMenuVO> getSideMenus(String userId, String pjId); 
	
	public List<SysMenuVO> getMenus(String userId); 
	
	public void addMenu(SysMenus menu);
	
	public void updateMenu(SysMenus menu);
	
	public void deleteMenu(String menuId);

}
