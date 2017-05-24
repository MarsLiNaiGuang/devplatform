package com.rs.devplatform.test.framework;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.rs.devplatform.DevPlatformApp;
import com.rs.devplatform.persistent.sample.CgSample;
import com.rs.devplatform.test.base.TestBase;
import com.rs.devplatform.vo.sample.CgSampleVO;
import com.rs.framework.common.RsEntityWrapper;
import com.rs.framework.common.RsGeneralMapper;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = DevPlatformApp.class, webEnvironment = WebEnvironment.RANDOM_PORT)
public class RsGeneralMapperTest extends TestBase{

	@Autowired
	RsGeneralMapper generalMapper;
	
	@Test
	public void testVOSelect(){
		CgSampleVO sampleVO = new CgSampleVO();
		List<CgSampleVO> list = generalMapper.selectList(new RsEntityWrapper<CgSampleVO>(sampleVO));
		for(CgSampleVO cs:list){
			System.out.println(cs);
		}
	}
	
	@Test
	public void testVOInEWWithCondition(){
		CgSampleVO sampleVO = new CgSampleVO();
		sampleVO.setAge(12);
		RsEntityWrapper<CgSampleVO> wrapper = new RsEntityWrapper<>(sampleVO);
//		wrapper.like("sample_name", "a");
		wrapper.and(" sample_cjsj > {0} and sample_cjsj < {1} ", LocalDate.of(2017, 2, 8), LocalDate.of(2017, 2, 15));
		List<CgSampleVO> list = generalMapper.selectList(wrapper);
		Assert.assertNotNull(list);
		for(CgSampleVO cs:list){
			System.err.println(cs);
		}
	}
	
	@Test
	public void testEntity(){
		CgSample sampleVO = new CgSample();
		List<CgSample> list = generalMapper.selectList(new RsEntityWrapper<CgSample>(sampleVO));
		for(CgSample cs:list){
			System.out.println(cs);
		}
	}
	@Test
	public void testInsertEntity(){
		CgSample sample = new CgSample();
		sample.setAge(12);
		sample.setName("testInsertEntity");
		sample.setNkname("testInsertEntity nkname");
		generalMapper.insert(sample);
	}
	@Test
	public void testInsertEntityVO(){
		CgSample sample = new CgSampleVO();
		sample.setAge(12);
		sample.setName("testInsertEntityVO");
		sample.setNkname("testInsertEntityVO nkname");
		generalMapper.insert(sample);
	}
	
	@Test
	public void testBatchDelete(){
		CgSample sample = new CgSampleVO();
		sample.setAge(12);
		sample.setName("testInsertEntityVO"+new Random().nextInt(1000));
		sample.setNkname("testInsertEntityVO nkname");
		generalMapper.insert(sample);
		String id = sample.getId();
		
		List<String> idList = new ArrayList<>(3);
		idList.add(sample.getId());
		sample.setId(null);
		sample.setName("testInsertEntityVO"+new Random().nextInt(1000));
		generalMapper.insert(sample);
		idList.add(sample.getId());
		sample.setId(null);
		sample.setName("testInsertEntityVO"+new Random().nextInt(1000));
		generalMapper.insert(sample);
		idList.add(sample.getId());
		
		sample = generalMapper.selectById(id, CgSample.class);
		Assert.assertNotNull(sample);
		generalMapper.deleteBatchIds(idList, CgSample.class);
		sample = generalMapper.selectById(id, CgSample.class);
		Assert.assertNull(sample);
		
	}
	
	@Test
	public void testDeleteByEw(){
		CgSample insertSample = new CgSample();
		insertSample.setName("a1a1a1");
		generalMapper.insert(insertSample);
		insertSample = new CgSample();
		insertSample.setName("a2a2a2");
		generalMapper.insert(insertSample);
		CgSample sample = new CgSample();
		RsEntityWrapper<CgSample> wrapper = new RsEntityWrapper<>(sample);
		wrapper.where("sample_name={0}", "a1a1a1");
		wrapper.or("sample_name={0}", "a2a2a2");
		int count = generalMapper.deleteByEW(wrapper);
		Assert.assertEquals(2, count);
		
	}
	
	@Test
	public void testAsserFailed(){
		CgSampleVO sample = new CgSampleVO();
//		sample.setAge(12);
		RsEntityWrapper<CgSampleVO> ew = new RsEntityWrapper<>(sample);
		ew.like("sample_name", "a");
		generalMapper.selectList(ew);
		
	}
	
	
	@Test
	public void testEwParam(){
		CgSample sample = new CgSample();
		RsEntityWrapper<CgSample> ew = new RsEntityWrapper<>(sample);
		ew.and("sample_cjsj>#{startDate} and sample_cjsj<#{endDate}", LocalDate.of(2017, 2, 8), LocalDate.of(2017, 2, 15));
		
	}
}
