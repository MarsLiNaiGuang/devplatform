package com.rs.devplatform.service.common.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.MessageFormat;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;

import com.rs.devplatform.persistent.SysCdval;
import com.rs.devplatform.service.common.CodeService;
import com.rs.framework.common.Constants;
import com.rs.framework.common.RsEntityWrapper;
import com.rs.framework.common.RsGeneralMapper;

@Service
public class CodeServiceImpl implements CodeService {

	@Autowired
	RsGeneralMapper generalMapper;
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	private static final String QUERY_CODE_FROM_DICT_TABLE_SQL = "select distinct {0} as CDVAL,{1} as CDDESCP from {2} order by CDVAL";
	
	@Override
//	@Cacheable(value="codeCache", key="#root.args[0]")
	public List<SysCdval> getCodeListByCodeType(String codeType) {
		SysCdval cdvalParm = new SysCdval();
		cdvalParm.setCdtype(codeType);
		cdvalParm.setDeleted(Constants.DelInd.FALSE);
		return generalMapper.selectList(new RsEntityWrapper<>(cdvalParm));
	}

	@Override
	public List<SysCdval> getCodeListByDistTable(String dictTable, String cdvalCol, String cddescpCol) {
		String sql = MessageFormat.format(QUERY_CODE_FROM_DICT_TABLE_SQL, cdvalCol,cddescpCol, dictTable);
		return jdbcTemplate.query(sql, new CodeValRowMapper());
	}

}

class CodeValRowMapper implements RowMapper<SysCdval>{

	@Override
	public SysCdval mapRow(ResultSet rs, int rowNum) throws SQLException {
		SysCdval code = new SysCdval();
		code.setCdval(rs.getString("CDVAL"));
		code.setCddescp(rs.getString("CDDESCP"));
		return code;
	}
	
}
