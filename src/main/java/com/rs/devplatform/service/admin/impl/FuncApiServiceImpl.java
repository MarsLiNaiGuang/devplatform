package com.rs.devplatform.service.admin.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rs.devplatform.persistent.SysBxx;
import com.rs.devplatform.persistent.SysColumns;
import com.rs.devplatform.persistent.SysCustbt;
import com.rs.devplatform.persistent.SysZdxx;
import com.rs.devplatform.persistent.mapper.AuthDataBxxMaper;
import com.rs.devplatform.service.admin.FuncApiService;
import com.rs.devplatform.service.cg.CGService;
import com.rs.framework.common.RsEntityWrapper;
import com.rs.framework.common.RsGeneralMapper;
import com.rs.framework.common.SysConstants;
import com.rs.framework.common.entity.persistent.SysFuncgp;
import com.rs.framework.common.entity.persistent.SysGp2tb;
import com.rs.framework.common.entity.persistent.SysMenus;
import com.rs.framework.common.entity.vo.FuncEntry;

@Service
public class FuncApiServiceImpl implements FuncApiService {
	
	@Autowired
	RsGeneralMapper generalMapper;
	@Autowired
	AuthDataBxxMaper authDataBxxMaper;
	
	@Autowired
	CGService cgService;

	@Deprecated
	@Override
	public List<FuncEntry> getColumnsByFuncGroupId(String funcGroupId) {
		List<FuncEntry> entryList = new ArrayList<>();
		SysGp2tb tableParm = new SysGp2tb();
		tableParm.setGpid(funcGroupId);
		List<SysGp2tb> tableList = generalMapper.selectList(new RsEntityWrapper<>(tableParm));
		if(tableList==null || tableList.isEmpty()){
			return entryList;
		}
		for(SysGp2tb tb:tableList){
			//TODO: here need switch to check if can get from bxx,zdxx2017-1-4
//			List<Map<String,Object>> list = authDataBxxMaper.getColumnsFromBxxByTableName();
			List<SysColumns> columnList = cgService.getCurrentAvailableColumns(tb.getTbname());
			if(columnList!=null){
				columnList.forEach((x)->{
					String columnName =x.getName();
					String propertyName = null;
					String name = x.getComment();
					entryList.add(new FuncEntry(columnName, name, columnName, propertyName));
				});
			}
		}
		return entryList;
	}
	
	@Deprecated
	@Override
	public List<FuncEntry> getButtonsByFuncGroupId(String funcGroupId) {
		List<FuncEntry> buttons = new ArrayList<>();
		FuncEntry[] array = SysConstants.DEFAULT_BUTTONS;
		for(int i=0;i<array.length;++i){
			buttons.add(array[i]);
		}
		SysGp2tb tableParm = new SysGp2tb();
		tableParm.setGpid(funcGroupId);
		List<SysGp2tb> tableList = generalMapper.selectList(new RsEntityWrapper<>(tableParm));
		if(tableList==null || tableList.isEmpty()){
			return buttons;
		}
		for(SysGp2tb tb:tableList){
			List<Map<String,Object>> custBtns = authDataBxxMaper.getCustButtonsFromDBByTableName(tb.getTbname());
			if(custBtns!=null && !custBtns.isEmpty()){
				custBtns.stream().map((btn)->{
					FuncEntry ety = new FuncEntry();
					ety.setName((String)btn.get("BTN_NAME"));
					ety.setId((String)btn.get("BTN_CODE"));
					return ety;
				}).forEach(buttons::add);
			}
		}
		return buttons;
	}

	@Deprecated
	@Override
	public List<FuncEntry> getSearchCriteriasByFuncGroupId(String funcGroupId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<SysFuncgp> getAllFunctions() {
		SysFuncgp parm = new SysFuncgp();
		return generalMapper.selectList(new RsEntityWrapper<>(parm));
	}

	@Override
	public List<FuncEntry> getColumnsByMenuId(String menuId) {
		SysMenus menu = generalMapper.selectById(menuId, SysMenus.class);
		if(menu!=null){
			String funcId = menu.getFuncId();
			if(!StringUtils.isBlank(funcId)){
				SysBxx bxxParm = new SysBxx();
				bxxParm.setFcflag(funcId);
				SysBxx bxx = generalMapper.selectOne(bxxParm);
				if(bxx!=null){
					return getColumnsByBxx(bxx);
				}
			}
		}
		return null;
	}

	private List<FuncEntry> getColumnsByBxx(SysBxx bxx){
		String sql = bxx.getIsql();
		if(StringUtils.isBlank(sql)){
			List<SysColumns> columnList = cgService.getCurrentAvailableColumns(bxx.getBm());
			if(columnList==null || columnList.isEmpty()){
				return null;
			}
			List<FuncEntry> result = new ArrayList<>(columnList.size());
			columnList.stream().map((col)->{
				FuncEntry ety = new FuncEntry();
				ety.setId(col.getName());
				ety.setColumnName(col.getName());
				ety.setName(col.getComment());
				return ety;
			}).forEach(result::add);
			return result;
		}else{
			SysZdxx zd = new SysZdxx();
			zd.setBid(bxx.getId());
			RsEntityWrapper<SysZdxx> ew = new RsEntityWrapper<>(zd);
			ew.andNew("ZDXX_CONDFLAG is null or ZDXX_CONDFLAG!='C' ");
			List<SysZdxx> columnList = generalMapper.selectList(ew);
			if(columnList==null || columnList.isEmpty()){
				return null;
			}
			List<FuncEntry> result = new ArrayList<>(columnList.size());
			columnList.stream().map((col)->{
				FuncEntry ety = new FuncEntry();
				ety.setId(col.getName());
				ety.setColumnName(col.getName());
				ety.setName(col.getNr());
				return ety;
			}).forEach(result::add);
			return result;
		}
	}

	@Override
	public List<FuncEntry> getButtonsByMenuId(String menuId) {
		List<FuncEntry> buttons = new ArrayList<>();
		FuncEntry[] array = SysConstants.DEFAULT_BUTTONS;
		for(int i=0;i<array.length;++i){
			buttons.add(array[i]);
		}
		SysMenus menu = generalMapper.selectById(menuId, SysMenus.class);
		if(menu!=null){
			String funcId = menu.getFuncId();
			if(!StringUtils.isBlank(funcId)){
				SysBxx bxxParm = new SysBxx();
				bxxParm.setFcflag(funcId);
				SysBxx bxx = generalMapper.selectOne(bxxParm);
				if(bxx!=null){
					List<FuncEntry> custButtons = getCustomButtonsByBxx(bxx);
					if(custButtons!=null){
						buttons.addAll(custButtons);
					}
				}
			}
		}
		return buttons;
	}
	
	private List<FuncEntry> getCustomButtonsByBxx(SysBxx bxx){
		SysCustbt btParm = new SysCustbt();
		btParm.setBdid(bxx.getId());
		List<SysCustbt> list = generalMapper.selectList(btParm);
		if(list!=null && !list.isEmpty()){
			List<FuncEntry> result = new ArrayList<>(list.size());
			list.stream().map((bt)->{
				FuncEntry ety = new FuncEntry();
				ety.setName(bt.getName());
				ety.setId(bt.getCode());
				return ety;
			}).forEach(result::add);
			return result;
		}
		return null;
	}
	
}
