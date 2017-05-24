package com.rs.devplatform.vo;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;

/**
 * 项目数据库连接信息
 * 
 * @author yuxiaobin
 *
 */
public class PjDBInfoVO {
	
	@NotEmpty
	private String dbType;
	@NotEmpty
	private String dbUrl;
	@NotEmpty
	private String dbUser;
	@NotNull
	private String dbPwd;//允许数据库密码为空
	
	public String getDbType() {
		return dbType;
	}
	public void setDbType(String dbType) {
		this.dbType = dbType;
	}
	public String getDbUrl() {
		return dbUrl;
	}
	public void setDbUrl(String dbUrl) {
		this.dbUrl = dbUrl;
	}
	public String getDbUser() {
		return dbUser;
	}
	public void setDbUser(String dbUser) {
		this.dbUser = dbUser;
	}
	public String getDbPwd() {
		return dbPwd;
	}
	public void setDbPwd(String dbPwd) {
		this.dbPwd = dbPwd;
	}
	@Override
	public String toString() {
		return "DBInfoVO [dbType=" + dbType + ", dbUrl=" + dbUrl + ", dbUser=" + dbUser + ", dbPwd=" + dbPwd + "]";
	}

}
