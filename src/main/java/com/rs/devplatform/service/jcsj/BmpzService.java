/**
 *Copyright (c) 2016-2017, Roadshare.com.
 */
package com.rs.devplatform.service.jcsj;

import com.rs.devplatform.persistent.jcsj.Bmpz;
import java.util.Map;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import java.util.List;


public interface BmpzService {

	public boolean add(Bmpz bmpz);
	
	public boolean update(Bmpz bmpz);
	
	public boolean delete(Bmpz bmpz);
	
	

	public boolean deleteBatch(String[] ids);
}
