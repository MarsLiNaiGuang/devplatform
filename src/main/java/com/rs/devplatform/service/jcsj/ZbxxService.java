/**
 *Copyright (c) 2016-2017, Roadshare.com.
 */
package com.rs.devplatform.service.jcsj;

import com.rs.devplatform.persistent.jcsj.Zbxx;
import java.util.Map;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import java.util.List;


public interface ZbxxService {

	public boolean add(Zbxx zbxx);
	
	public boolean update(Zbxx zbxx);
	
	public boolean delete(Zbxx zbxx);
	
	

	public boolean deleteBatch(String[] ids);
}
