package com.rs.devplatform.conf.converter;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.net.URLDecoder;

import org.apache.commons.io.IOUtils;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.MediaType;
import org.springframework.http.converter.AbstractHttpMessageConverter;
import org.springframework.http.converter.GenericHttpMessageConverter;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.web.util.HtmlUtils;

import com.alibaba.fastjson.JSONObject;
import com.rs.framework.common.controller.base.BaseController;

public class FormData2JsonHttpConverter extends AbstractHttpMessageConverter<Object>implements GenericHttpMessageConverter<Object> {

	private static final String PAGE = "page";
	private static final String ROWS = "rows";
	@Override
	public boolean canRead(Type type, Class<?> contextClass, MediaType mediaType) {
		if(mediaType.includes(MediaType.APPLICATION_FORM_URLENCODED)){
			return true;
		}
		return false;
	}

	@Override
	public Object read(Type type, Class<?> contextClass, HttpInputMessage inputMessage)
			throws IOException, HttpMessageNotReadableException {
		InputStream in = inputMessage.getBody();
		String body = IOUtils.toString(in,"utf-8");
		body = URLDecoder.decode(HtmlUtils.htmlUnescape(body), "utf-8");
		System.err.println("FormData2JsonHttpConverter body="+body);
		JSONObject json = new JSONObject();
		String[] array = body.split("&");//TODO: 20161108: if form data is : user.id=123&user.name=abc, should mapping to json: {user:{id:123,name:abc}}
		for(String str:array){
			String[] kv = str.split("=");
			if(kv.length!=2){
				continue;
			}
			Object value = null;
			String parmName = kv[0];
			if(PAGE.equals(parmName)){
				parmName = BaseController.CURRENT;
				value = Integer.parseInt(kv[1]);
			}else if(ROWS.equals(parmName)){
				parmName = BaseController.SIZE;
				value = Integer.parseInt(kv[1]);
			}else{
				value = kv[1];
			}
			json.put(parmName, value);
		}
		System.err.println("FormData2JsonHttpConverter json="+json.toJSONString());
		return json;
	}

	@Override
	public boolean canWrite(Type type, Class<?> clazz, MediaType mediaType) {
//		return super.canWrite(clazz, mediaType);
		return false;
	}

	@Override
	public void write(Object t, Type type, MediaType contentType, HttpOutputMessage outputMessage)
			throws IOException, HttpMessageNotWritableException {
		
	}

	@Override
	protected boolean supports(Class<?> clazz) {
		return false;
	}

	@Override
	protected Object readInternal(Class<? extends Object> clazz, HttpInputMessage inputMessage)
			throws IOException, HttpMessageNotReadableException {
		return null;
	}

	@Override
	protected void writeInternal(Object t, HttpOutputMessage outputMessage)
			throws IOException, HttpMessageNotWritableException {
		
	}

	

}
