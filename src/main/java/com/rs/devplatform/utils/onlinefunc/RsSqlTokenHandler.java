package com.rs.devplatform.utils.onlinefunc;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;
import java.util.concurrent.atomic.AtomicInteger;

import org.apache.ibatis.parsing.TokenHandler;

/**
 * 用来解析SQL是否包含mybatis语法的参数：#{param}
 * 
 * 
 * @author yuxiaobin
 *
 */
public class RsSqlTokenHandler implements TokenHandler {
	
	public static final String OPEN_TOKEN = "#{";
	public static final String CLOSE_TOKEN = "}";
	
	public static final String PARAM_NAME = "param";
	public static final String PARAM_EXPRESSION = "expr";
	
	private static final String GENERAL_PARAMNAME = "GENVAL";
	
	private Map<String,String> paramNameValuePairs = new HashMap<>(4);
	private AtomicInteger paramNameSeq = new AtomicInteger(0);
	
	private List<String> paramNames = new ArrayList<>(4);

	@Override
	public String handleToken(String content) {
		paramNames.add(content);
		String replacekey = GENERAL_PARAMNAME+paramNameSeq.getAndIncrement();
		paramNameValuePairs.put(replacekey, OPEN_TOKEN+content+CLOSE_TOKEN);
		return replacekey;
	}
	
	public List<String> getParamNames() {
		return paramNames;
	}

	public boolean hasParam(){
		return !paramNames.isEmpty();
	}
	
	public String revertExp(String replacedExpStr){
		Set<Entry<String, String>> set = paramNameValuePairs.entrySet();
		for(Entry<String, String> ety : set){
			String key = ety.getKey();
			if(replacedExpStr.contains(key)){
				replacedExpStr = replacedExpStr.replace(key, ety.getValue());
			}
		}
		return replacedExpStr;
	}

	public Map<String, String> getParamNameValuePairs() {
		return paramNameValuePairs;
	}
	
}
