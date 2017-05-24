package com.rs.devplatform.controller;

import java.util.List;

import com.rs.framework.wf.entity.persistent.WfVersion;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import com.rs.framework.common.RsEntityWrapper;
import com.rs.framework.common.controller.base.BaseController;

@Controller
public class IndexController extends BaseController {
	
	@RequestMapping("/")
	String home() {
		return "forward:/login";
	}
	
	@GetMapping("/sample")
	String sample() {
		return "sample/dev-sample";
	}
	
	@GetMapping("/sample/signature")
	String sampleSignature() {
		return "sample/signature";
	}
	
	@RequestMapping("/test")
	@ResponseBody
	Object restHome() {
		RsEntityWrapper<WfVersion> wrapper = new RsEntityWrapper<>(new WfVersion());
		wrapper.like("REF_MKID", "123");
		wrapper.orderBy("REF_MKID", false);
		List<WfVersion> list = generalMapper.selectList(wrapper);

		WfVersion wfByPk = generalMapper.selectById("11", WfVersion.class);
		System.out.println("Before update:"+ wfByPk.getRefMkid());
		String refMkid = wfByPk.getRefMkid();
		String lastChar = refMkid.substring(refMkid.length()-1);
		Integer lastCharInt = 0;
		try{
			lastCharInt = Integer.parseInt(lastChar);
		}catch(Exception e){
		}
		lastCharInt +=1;
		wfByPk.setRefMkid(refMkid.substring(0, refMkid.length()-1)+lastCharInt.toString());
		generalMapper.updateById(wfByPk);
		System.out.println("after update:"+wfByPk.getRefMkid());
		wfByPk.setRefMkid(null);
		generalMapper.updateSelectiveById(wfByPk);
		wfByPk = generalMapper.selectById("11", WfVersion.class);
		System.out.println("after updateSelectiveById update:"+wfByPk.getRefMkid());

		wfByPk = generalMapper.selectById("11", WfVersion.class);

		List<WfVersion> listPage = generalMapper.selectPage(new Pagination(1,5), new RsEntityWrapper<>(new WfVersion()));
		for(WfVersion wf:listPage){
			System.out.println(wf.toString());
		}
		listPage = generalMapper.selectPage(new Pagination(2,5), new RsEntityWrapper<>(new WfVersion()));
		for(WfVersion wf:listPage){
			System.out.println(wf.toString());
		}

		int sizeBefore = list.size();
		WfVersion wf = new WfVersion();
		wf.setRefMkid("12344");
		wf.setVERSION(1);
		generalMapper.insert(wf);
		list = generalMapper.selectList(new RsEntityWrapper<>(new WfVersion()));
		int sizeAfter = list.size();
		System.err.println("sizeBefore="+sizeBefore+". sizeAfter="+sizeAfter);
		Page<WfVersion> page = new Page<WfVersion>(1,10);
		RsEntityWrapper<WfVersion> ew = new RsEntityWrapper<>(new WfVersion());
		page.setRecords(generalMapper.selectPage(page, ew));
		page.setTotal(generalMapper.selectCountByEW(ew));
		return page;
	}

	/**
	 * 测试服务器是否存活的api
	 * @return
	 */
	@RequestMapping("/ping")
	@ResponseBody
	public String ping(){
		return "pong";
	}
}
