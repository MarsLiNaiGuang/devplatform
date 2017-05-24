package com.rs.devplatform.service.admin.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.rs.devplatform.service.admin.MenuService;
import com.rs.framework.common.Constants;
import com.rs.framework.common.RsEntityWrapper;
import com.rs.framework.common.RsGeneralMapper;
import com.rs.framework.common.entity.persistent.Authr2m;
import com.rs.framework.common.entity.persistent.SysMenus;
import com.rs.framework.common.entity.vo.SysMenuVO;
import com.rs.framework.common.service.Role2MenuService;

@Service
public class MenuServiceImpl implements MenuService {

	@Autowired
	RsGeneralMapper generalMapper;
	
	@Autowired(required=false)
	Role2MenuService r2mService;
	
	@Override
	@Transactional
	//@Cacheable(value="menuCache", key="#root.args[0] + #root.args[1]")
	public List<SysMenuVO> getSideMenus(String userId, String pjId) {
		List<SysMenuVO> allmenuList;
		SysMenuVO cdtypeParm = new SysMenuVO();
		cdtypeParm.setDeleted(Constants.DelInd.FALSE);
		RsEntityWrapper<SysMenuVO> wrapper = new RsEntityWrapper<SysMenuVO>(cdtypeParm);
		wrapper.andNew("menus_type = 'A' or menus_type = 'B' ");
		wrapper.orderBy("menus_pid, menus_sequence, menus_cjsj");
		allmenuList = generalMapper.selectList(wrapper);
		if(r2mService==null || "admin".equalsIgnoreCase(userId)){
			return allmenuList;
		}else{
			List<SysMenus> menuList = r2mService.getSideMenusByUserId(userId);
			if(menuList!=null){
				List<SysMenuVO> voList = new ArrayList<>();
				for(SysMenus menu:menuList){
					SysMenuVO vo = new SysMenuVO();
					BeanUtils.copyProperties(menu, vo);
					voList.add(vo);
				}
				voList = getMenuList(allmenuList, voList);
				return voList;
			}
			return null;
		}
	}
	
	private List<SysMenuVO> getMenuList(List<SysMenuVO> allMenus, List<SysMenuVO> selectedMenus){
		for(int i = 0; i< selectedMenus.size(); i++){
			for(int j = 0; j < allMenus.size(); j++){
				if(allMenus.get(j).getId().equals(selectedMenus.get(i).getPid())){
					if(!selectedMenus.contains(allMenus.get(j))){
						selectedMenus.add(allMenus.get(j));
					}
					continue;
				}
			}
		}
		return selectedMenus;
	}

	@Override
	@Transactional
	@CacheEvict(cacheNames="menuCache", allEntries=true)
	public void addMenu(SysMenus menu) {
		generalMapper.insert(menu);
		
	}

	@Override
	@Transactional
//	@CacheEvict(cacheNames="menuCache", allEntries=true)
	public void updateMenu(SysMenus menu) {
		SysMenus menuDB = generalMapper.selectById(menu.getId(), SysMenus.class);
		if(menuDB.getVersion() == null){
			menuDB.setVersion(0);
		}
		menu.setVersion(menuDB.getVersion()+1);
		generalMapper.updateSelectiveById(menu);
	}

	@Override
	@Transactional
//	@CacheEvict(cacheNames="menuCache", allEntries=true)
	public void deleteMenu(String menuId) {
		generalMapper.deleteById(menuId, SysMenus.class);	
		Authr2m r2m = new Authr2m();
		r2m.setMenuId(menuId);
		generalMapper.deleteSelective(r2m);
	}

	@Override
	@Transactional
	public List<SysMenuVO> getMenus(String userId) {	
		List<SysMenuVO> allmenuList;
		SysMenuVO cdtypeParm = new SysMenuVO();
		cdtypeParm.setDeleted(Constants.DelInd.FALSE);
		RsEntityWrapper<SysMenuVO> wrapper = new RsEntityWrapper<SysMenuVO>(cdtypeParm);
		wrapper.orderBy("menus_pid, menus_sequence, menus_cjsj");
		allmenuList = generalMapper.selectList(wrapper);
		if(r2mService==null || "admin".equalsIgnoreCase(userId)){
			return allmenuList;
		}else{
			List<SysMenus> menuList = r2mService.getMenusByUserId(userId);
			if(menuList!=null){
				List<SysMenuVO> voList = new ArrayList<>();
				for(SysMenus menu:menuList){
					SysMenuVO vo = new SysMenuVO();
					BeanUtils.copyProperties(menu, vo);
					voList.add(vo);
				}
				voList = getMenuList(allmenuList, voList);
				return voList;
			}
			return null;
		}
	}

}
