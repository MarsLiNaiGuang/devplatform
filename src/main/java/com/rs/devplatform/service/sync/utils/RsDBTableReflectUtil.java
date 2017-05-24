package com.rs.devplatform.service.sync.utils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.rs.framework.common.enums.RsDBColumnTypes;
import com.rs.framework.common.utils.RsJdbcReflectionUtil;
import com.rs.framework.common.utils.RsJdbcReflectionUtil.DBType;
import com.rs.framework.common.utils.RsJdbcReflectionUtil.QuerySQL;
import com.rs.framework.common.utils.convert.ITypeConvert;

public abstract class RsDBTableReflectUtil {
	
	public static List<ColumnInfo> getTableColumnInfoFromDB(Connection conn, String tableName) throws SQLException{
		DBType dbType = RsJdbcReflectionUtil.getDbType(conn.getMetaData().getURL());
		QuerySQL querySQL = dbType.getQuerySQL();
		ITypeConvert typeConvert = dbType.getTypeConvert();
		String tableFieldsSql = String.format(querySQL.getTableFieldsSql(), tableName);
		try(
				PreparedStatement ps = conn.prepareStatement(tableFieldsSql);
				){
			ResultSet results = ps.executeQuery();
			List<ColumnInfo> columnList = new ArrayList<>();
			while (results.next()) {
				String key = results.getString(querySQL.getFieldKey());
				String fieldType = results.getString(querySQL.getFieldType());
				if ("PRI".equalsIgnoreCase(key)) {
					columnList.add(
						new ColumnInfo(
							results.getString(querySQL.getFieldName()),
							typeConvert.processTypeConvert(fieldType), 
							results.getString(querySQL.getFieldComment()), 
							true,//pk
							typeConvert.processNullable(results.getString(querySQL.getFieldNullable())),
							typeConvert.getTypeLenth(fieldType))
					);
				}else{
					columnList.add(
						new ColumnInfo(
							results.getString(querySQL.getFieldName()),
							typeConvert.processTypeConvert(fieldType), 
							results.getString(querySQL.getFieldComment()), 
							false,
							typeConvert.processNullable(results.getString(querySQL.getFieldNullable())),
							typeConvert.getTypeLenth(fieldType))
					);
				}
			}
			return columnList;
		}
	}
	
	public static TableInfo getTableInfoFromDB(Connection conn, String table) throws SQLException{
		String dbURL = conn.getMetaData().getURL();
		DBType dbType = RsJdbcReflectionUtil.getDbType(dbURL);
		QuerySQL querySQL = dbType.getQuerySQL();
		try(
				PreparedStatement ps = conn.prepareStatement(String.format(querySQL.getTableCommentSingleSql(), table));
				){
			ResultSet results = ps.executeQuery();
			TableInfo tableInfo = null;
			while (results.next()) {
				String tableName = results.getString(querySQL.getTableName());
				String tableComment = results.getString(querySQL.getTableComment());
				tableInfo = new TableInfo();
				tableInfo.setName(tableName);
				tableInfo.setComment(tableComment);
				return tableInfo;
			}
			return null;
		}
	}
	
	public static class ColumnInfo{
		private String columnName;
		private RsDBColumnTypes columnType;
		private String columnComment;
		private boolean pk;
		private boolean nullable;
		private Integer columnLength;
		
		
		public ColumnInfo() {
			super();
		}
		public ColumnInfo(String columnName, RsDBColumnTypes columnType, String columnComment, boolean pk, boolean nullable, Integer columnLen) {
			super();
			this.columnName = columnName;
			this.columnType = columnType;
			this.columnComment = columnComment;
			this.pk = pk;
			this.nullable = nullable;
			this.columnLength = columnLen;
		}
		public String getColumnName() {
			return columnName;
		}
		public void setColumnName(String columnName) {
			this.columnName = columnName;
		}
		public RsDBColumnTypes getColumnType() {
			return columnType;
		}
		public void setColumnType(RsDBColumnTypes columnType) {
			this.columnType = columnType;
		}
		public String getColumnComment() {
			return columnComment;
		}
		public void setColumnComment(String columnComment) {
			this.columnComment = columnComment;
		}
		public boolean isPk() {
			return pk;
		}
		public void setPk(boolean pk) {
			this.pk = pk;
		}
		public boolean isNullable() {
			return nullable;
		}
		public void setNullable(boolean nullable) {
			this.nullable = nullable;
		}
		public Integer getColumnLength() {
			return columnLength;
		}
		public void setColumnLength(Integer columnLength) {
			this.columnLength = columnLength;
		}
	}
	
	public static class TableInfo{
		private String name;
		private String comment;
		
		public TableInfo() {
			super();
		}
		public TableInfo(String name, String comment) {
			super();
			this.name = name;
			this.comment = comment;
		}
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		public String getComment() {
			return comment;
		}
		public void setComment(String comment) {
			this.comment = comment;
		}
	}
}
