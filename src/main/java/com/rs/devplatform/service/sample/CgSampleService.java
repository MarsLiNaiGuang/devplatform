/**
 *Copyright (c) 2016-2017, Roadshare.com.
 */
package com.rs.devplatform.service.sample;

import com.rs.devplatform.persistent.sample.CgSample;

public interface CgSampleService {

	public boolean add(CgSample cgSample);
	
	public boolean update(CgSample cgSample);
	
	public boolean delete(CgSample cgSample);
	
	public boolean deleteBatch(String[] ids);
	
	
}
