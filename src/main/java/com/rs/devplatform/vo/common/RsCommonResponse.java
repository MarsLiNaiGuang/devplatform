package com.rs.devplatform.vo.common;

import java.util.List;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

public class RsCommonResponse{

	public static final String MASTER = "master";
	public static final String DETAIL = "detail";
	public static final String DETAIL_ID = "id";
	public static final String DETAIL_RECORDS = "records";
	public static final String DATADICT = "datadict";
	
	private JSONObject master;
	private String id;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}

	private Detail[] detail;
	
	public static class Detail{
		private String id;
		private JSONArray records;
		public String getId() {
			return id;
		}
		public void setId(String id) {
			this.id = id;
		}
		public JSONArray getRecords() {
			return records;
		}
		public void setRecords(JSONArray records) {
			this.records = records;
		}
		@Override
		public String toString() {
			return toJSONString();
		}
		public String toJSONString(){
			return JSON.toJSONString(this);
		}
	}
	public JSONObject getMaster() {
		return master;
	}
	public void setMaster(JSONObject master) {
		this.master = master;
	}
	public Detail[] getDetail() {
		return detail;
	}
	public void setDetail(Detail[] detail) {
		this.detail = detail;
	}
	public String toString(){
		return toJSONString();
	}
	public String toJSONString(){
		return JSON.toJSONString(this);
	}
	
	public RsCommonResponse addMaster(Object obj){
		this.master = (JSONObject) JSON.toJSON(obj);
		return this;
	}
	
	public RsCommonResponse addDetail(String id, List<Object> records){
		Detail dtl = new Detail();
		dtl.setId(id);
		if(records!=null){
			dtl.setRecords((JSONArray) JSON.toJSON(records));
		}
		if(detail==null){
			detail = new Detail[]{dtl};
		}else{
			int len = detail.length;
			Detail[] updArray =  new Detail[len+1];
			for(int i=0;i<len;++i){
				updArray[i] = detail[i];
			}
			updArray[len] = dtl;
			detail = updArray;
		}
		return this;
	}
	
	public static void main(String[] args) {
		/*JSONObject master = new JSONObject();
		master.put("name", "master name");

		String detailId = "dtl1";
		List<Object> records = new ArrayList<>();
		JSONObject record = new JSONObject();
		record.put("id", "dtl1-record1");
		records.add(record);
		record = new JSONObject();
		record.put("id", "dtl1-record2");
		records.add(record);
		
		String detailId2 = "dtl2";
		List<Object> records2 = new ArrayList<>();
		JSONObject record2 = new JSONObject();
		record2.put("id", "dtl2-record1");
		records2.add(record2);
		record2 = new JSONObject();
		record2.put("id", "record2");
		records2.add(record2);
		
		RsCommonResponse resp = new RsCommonResponse();
		resp.addMaster(master).addDetail(detailId, records).addDetail(detailId2, records2);
		String jsonStr = resp.toJSONString();
		System.out.println("==========toJSONString:");
		System.out.println(jsonStr);
		System.out.println("");
		System.out.println("==========parseJSONString:");
		JSONObject parseJson = JSONObject.parseObject(jsonStr);
		parseJson.put("id", "adabcbc62c0841b5a320182e24bbee33");
		parseJson.put("tablename", "baoguo_stu");
		System.out.println(parseJson.toJSONString());
		RsCommonResponse respParse = parseJson.toJavaObject(RsCommonResponse.class);
		System.out.println(respParse.getMaster().toJSONString());
		Detail[] details = respParse.getDetail();
		for(Detail d:details){
			System.out.println(d);
		}
		System.out.println(respParse.toJSONString());*/
	}
	
}
