package com.rs.devplatform.service.cg;

import com.rs.devplatform.vo.onlinefc.OnlineFuncVO;

public interface OnlineFuncService {
	
	public boolean saveOnlineFunc(OnlineFuncVO funcVO);
	
	public boolean updateOnlineFunc(OnlineFuncVO funcVO);

}
