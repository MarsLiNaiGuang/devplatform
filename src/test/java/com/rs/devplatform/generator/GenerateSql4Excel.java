package com.rs.devplatform.generator;

public class GenerateSql4Excel {

	/**
	 * Generate create script for mysql
	 * @param args
	 */
	public static void main(String[] args) {
		StringBuilder sb = new StringBuilder();
		String columnName = "B";
		int startPosition = 34;
		int size = 12;
		sb.append("=CONCATENATE(\"CREATE TABLE \","+columnName+(startPosition-1)+",\"(\"&CHAR(10),");
		int endSize = startPosition+size;
		for(int i=startPosition;i<endSize;++i){
			String flag = columnName+i;
			String str = "%s&IF(OR(ISNUMBER(SEARCH(Sheet2!$B$3,%s)),ISNUMBER(SEARCH(Sheet2!$B$6,%s))),\" DATETIME NULL,\", IF(ISNUMBER(SEARCH(Sheet2!$B$8,%s)),\" INT(10),\", \" VARCHAR("+(i==size-1?1:50)+") "+(i==startPosition?"NOT ":"")+"NULL,\"))&CHAR(10),";
			sb.append(str.replace("%s", flag));
		}
		sb.append("\"PRIMARY KEY (\"&"+columnName+startPosition+"&\")\"&CHAR(10)&\")\"");
		sb.append(")");
		System.out.println(sb.toString());
	}
}
