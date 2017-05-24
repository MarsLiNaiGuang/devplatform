package com.rs.devplatform.persistent;

import java.io.Serializable;
import java.util.Date;

import com.baomidou.mybatisplus.annotations.IdType;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.rs.framework.common.entity.base.CUBaseTO;

/**
 *
 * 字段信息表
 * 
 * 用来记录某一张表的所有字段(每条记录代表一个表字段)。
 *
 */
@TableName("sys_zdxx")
public class SysZdxx extends CUBaseTO implements Serializable {

	@TableField(exist = false)
	private static final long serialVersionUID = 1L;

	/** 字段明细主键 */
	@TableId(value = "ZDXX_ID", type = IdType.UUID)
	private String id;

	/** 字段内容 */
	@TableField(value = "ZDXX_NR")
	private String nr;

	/** 字典字段 */
	@TableField(value = "ZDXX_ZDZD")
	private String zdzd;

	/** 字典表名 */
	@TableField(value = "ZDXX_ZDBM")
	private String zdbm;

	/** 字典文本 */
	@TableField(value = "ZDXX_ZDWB")
	private String zdwb;

	/** 字段默认值 */
	@TableField(value = "ZDXX_MR")
	private String mr;

	/** 字段超链接 */
	@TableField(value = "ZDXX_HREF")
	private String href;

	/** 字段长度 */
	@TableField(value = "ZDXX_CD")
	private Integer cd;

	/** 字段名字 */
	@TableField(value = "ZDXX_NAME")
	private String name;

	/** 字段验证类型 */
	@TableField(value = "ZDXX_YZLX")
	private String yzlx;

	/** 是否主键 */
	@TableField(value = "ZDXX_ISKEY")
	private String iskey;

	/** 是否允许为空 */
	@TableField(value = "ZDXX_ISNULL")
	private String isnull;

	/** 是否允许查询 */
	@TableField(value = "ZDXX_ISQUERY")
	private String isquery;

	/** 是否显示 */
	@TableField(value = "ZDXX_ISSHOW")
	private String isshow;

	/** 是否显示为列表 */
	@TableField(value = "ZDXX_ISSHOWLB")
	private String isshowlb;

	/** 字段显示长度 */
	@TableField(value = "ZDXX_LEN")
	private Integer len;

	/** 主表字段 */
	@TableField(value = "ZDXX_ZZD")
	private String zzd;

	/** 主表 */
	@TableField(value = "ZDXX_ZB")
	private String zb;

	/** 旧字段名字 */
	@TableField(value = "ZDXX_JZDM")
	private String jzdm;

	/** 序号 */
	@TableField(value = "ZDXX_XH")
	private Integer xh;

	/** Point Length */
	@TableField(value = "ZDXX_PLEN")
	private Integer plen;

	/** 查询模式 */
	@TableField(value = "ZDXX_CXMS")
	private String cxms;

	/** 显示类型 */
	@TableField(value = "ZDXX_XSLX")
	private String xslx;

	/** 字段类型 */
	@TableField(value = "ZDXX_LX")
	private String lx;

	/** 表ID */
	@TableField(value = "ZDXX_BID")
	private String bid;

	/** 扩展JSON, 目前用来存储条件表达式 */
	@TableField(value = "ZDXX_KZJSON")
	private String kzjson;

	/** 维护人名字 */
	@TableField(value = "ZDXX_WHR")
	private String whr;

	/** 维护时间 */
	@TableField(value = "ZDXX_WHSJ")
	private Date whsj;

	/** 维护人ID */
	@TableField(value = "ZDXX_WHRID")
	private String whrid;

	/** 创建人名字 */
	@TableField(value = "ZDXX_CJR")
	private String cjr;

	/** 创建时间 */
	@TableField(value = "ZDXX_CJSJ")
	private Date cjsj;

	/** 创建人ID */
	@TableField(value = "ZDXX_CJRID")
	private String cjrid;

	/** 数据记录版本 */
	@TableField(value = "ZDXX_VERSION")
	private Integer version;

	/** 逻辑删除标记位 */
	@TableField(value = "ZDXX_DELETED")
	private String deleted;
	/** 条件标记，C:表示仅作为查询条件,B:Both,即作为数据列，又作为查询条件。仅SQL情况下使用*/
	@TableField(value = "ZDXX_CONDFLAG")
	private String condflag;
	
	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getNr() {
		return this.nr;
	}

	public void setNr(String nr) {
		this.nr = nr;
	}

	public String getZdzd() {
		return this.zdzd;
	}

	public void setZdzd(String zdzd) {
		this.zdzd = zdzd;
	}

	public String getZdbm() {
		return this.zdbm;
	}

	public void setZdbm(String zdbm) {
		this.zdbm = zdbm;
	}

	public String getZdwb() {
		return this.zdwb;
	}

	public void setZdwb(String zdwb) {
		this.zdwb = zdwb;
	}

	public String getMr() {
		return this.mr;
	}

	public void setMr(String mr) {
		this.mr = mr;
	}

	public String getHref() {
		return this.href;
	}

	public void setHref(String href) {
		this.href = href;
	}

	public Integer getCd() {
		return this.cd;
	}

	public void setCd(Integer cd) {
		this.cd = cd;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getYzlx() {
		return this.yzlx;
	}

	public void setYzlx(String yzlx) {
		this.yzlx = yzlx;
	}

	public String getIskey() {
		return this.iskey;
	}

	public void setIskey(String iskey) {
		this.iskey = iskey;
	}

	public String getIsnull() {
		return this.isnull;
	}

	public void setIsnull(String isnull) {
		this.isnull = isnull;
	}

	public String getIsquery() {
		return this.isquery;
	}

	public void setIsquery(String isquery) {
		this.isquery = isquery;
	}

	public String getIsshow() {
		return this.isshow;
	}

	public void setIsshow(String isshow) {
		this.isshow = isshow;
	}

	public String getIsshowlb() {
		return this.isshowlb;
	}

	public void setIsshowlb(String isshowlb) {
		this.isshowlb = isshowlb;
	}

	public Integer getLen() {
		return this.len;
	}

	public void setLen(Integer len) {
		this.len = len;
	}

	public String getZzd() {
		return this.zzd;
	}

	public void setZzd(String zzd) {
		this.zzd = zzd;
	}

	public String getZb() {
		return this.zb;
	}

	public void setZb(String zb) {
		this.zb = zb;
	}

	public String getJzdm() {
		return this.jzdm;
	}

	public void setJzdm(String jzdm) {
		this.jzdm = jzdm;
	}

	public Integer getXh() {
		return this.xh;
	}

	public void setXh(Integer xh) {
		this.xh = xh;
	}

	public Integer getPlen() {
		return this.plen;
	}

	public void setPlen(Integer plen) {
		this.plen = plen;
	}

	public String getCxms() {
		return this.cxms;
	}

	public void setCxms(String cxms) {
		this.cxms = cxms;
	}

	public String getXslx() {
		return this.xslx;
	}

	public void setXslx(String xslx) {
		this.xslx = xslx;
	}

	public String getLx() {
		return this.lx;
	}

	public void setLx(String lx) {
		this.lx = lx;
	}

	public String getBid() {
		return this.bid;
	}

	public void setBid(String bid) {
		this.bid = bid;
	}

	public String getKzjson() {
		return this.kzjson;
	}

	public void setKzjson(String kzjson) {
		this.kzjson = kzjson;
	}

	public String getWhr() {
		return this.whr;
	}

	public void setWhr(String whr) {
		this.whr = whr;
	}

	public Date getWhsj() {
		return this.whsj;
	}

	public void setWhsj(Date whsj) {
		this.whsj = whsj;
	}

	public String getWhrid() {
		return this.whrid;
	}

	public void setWhrid(String whrid) {
		this.whrid = whrid;
	}

	public String getCjr() {
		return this.cjr;
	}

	public void setCjr(String cjr) {
		this.cjr = cjr;
	}

	public Date getCjsj() {
		return this.cjsj;
	}

	public void setCjsj(Date cjsj) {
		this.cjsj = cjsj;
	}

	public String getCjrid() {
		return this.cjrid;
	}

	public void setCjrid(String cjrid) {
		this.cjrid = cjrid;
	}

	public Integer getVersion() {
		return this.version;
	}

	public void setVersion(Integer version) {
		this.version = version;
	}

	public String getDeleted() {
		return this.deleted;
	}

	public void setDeleted(String deleted) {
		this.deleted = deleted;
	}

	public String getCondflag() {
		return condflag;
	}

	public void setCondflag(String condflag) {
		this.condflag = condflag;
	}

	/**
	 * 
	 * @param zd
	 * @return boolean  true:same, false: changed
	 */
	public boolean compareNonDBChange(SysZdxx zd){
		return  (this.cxms==null?(zd.getCxms()==null):this.cxms.equals(zd.getCxms()))
				&& (this.isquery==null?(zd.getIsquery()==null):this.isquery.equals(zd.getIsquery()))
				&& (this.isshow==null?(zd.getIsshow()==null):this.isshow.equals(zd.getIsshow()))
				&& (this.isshowlb==null?(zd.getIsshowlb()==null):this.isshowlb.equals(zd.getIsshowlb()))
				&& (this.len==null?(zd.getLen()==null):this.len.equals(zd.getLen()))
				&& (this.xslx==null?(zd.getXslx()==null):this.xslx.equals(zd.getXslx()))
				&& (this.zdbm==null?(zd.getZdbm()==null):this.zdbm.equals(zd.getZdbm()))
				&& (this.zdwb==null?(zd.getZdwb()==null):this.zdwb.equals(zd.getZdwb()))
				&& (this.zdzd==null?(zd.getZdzd()==null):this.zdzd.equals(zd.getZdzd()))
				&& (this.zzd==null?(zd.getZzd()==null):this.zzd.equals(zd.getZzd()))
				&& (this.href==null?(zd.getHref()==null):this.href.equals(zd.getHref()))
				&& (this.yzlx==null?(zd.getYzlx()==null):this.yzlx.equals(zd.getYzlx()))
				
				&& (this.iskey==null?(zd.getIskey()==null):this.iskey.equals(zd.getIskey()))
				&& (this.isnull==null?(zd.getIsnull()==null):this.isnull.equals(zd.getIsnull()))
				&& (this.lx==null?(zd.getLx()==null):this.lx.equals(zd.getLx()))
				&& (this.nr==null?(zd.getNr()==null):this.nr.equals(zd.getNr()))
				&& (this.name==null?(zd.getName()==null):this.name.equals(zd.getName()))
				&& (this.xh==null?(zd.getXh()==null):this.xh.equals(zd.getXh()))
				&& (this.kzjson==null?(zd.getKzjson()==null):this.kzjson.equals(zd.getKzjson()))
//				&& (this.zb==null?(zd.getZb()==null):this.zb.equals(zd.getZb()))
				;
	}
	
	public static void copyChange(SysZdxx source, SysZdxx target){
		target.setCxms(source.getCxms());
		target.setIsquery(source.getIsquery());
		target.setIsshow(source.getIsshow());
		target.setIsshowlb(source.getIsshowlb());
		target.setLen(source.getLen());
		target.setXslx(source.getXslx());
		target.setZdbm(source.getZdbm());
		target.setZdwb(source.getZdwb());
		target.setZdzd(source.getZdzd());
		target.setZzd(source.getZzd());
		target.setHref(source.getHref());
		target.setYzlx(source.getYzlx());
		target.setIskey(source.getIskey());
		target.setIsnull(source.getIsnull());
		target.setLx(source.getLx());
		target.setNr(source.getNr());
		target.setName(source.getName());
		
		target.setMr(source.getMr());
		target.setPlen(source.getPlen());
		target.setXh(source.getXh());
		target.setZb(source.getZb());
	}
	
	public static void copyNonDBChange(SysZdxx source, SysZdxx target){
		target.setCxms(source.getCxms());
		target.setIsquery(source.getIsquery());
		target.setIsshow(source.getIsshow());
		target.setIsshowlb(source.getIsshowlb());
		target.setLen(source.getLen());
		target.setXslx(source.getXslx());
		target.setZdbm(source.getZdbm());
		target.setZdwb(source.getZdwb());
		target.setZdzd(source.getZdzd());
		target.setZzd(source.getZzd());
		target.setHref(source.getHref());
		target.setYzlx(source.getYzlx());
		target.setIskey(source.getIskey());
		target.setIsnull(source.getIsnull());
		target.setLx(source.getLx());
		target.setNr(source.getNr());
		target.setName(source.getName());
		target.setXh(source.getXh());
	}
	
	public static void copyDBChange(SysZdxx source, SysZdxx target){
		target.setCd(source.getCd());
		target.setIskey(source.getIskey());
		target.setIsnull(source.getIsnull());
		target.setLx(source.getLx());
		target.setMr(source.getMr());
		target.setName(source.getName());
		target.setNr(source.getNr());
		target.setPlen(source.getPlen());
	}
	
	@Override
	public String toString() {
		return "SysZdxx [id=" + id + ", nr=" + nr + ", name=" + name+ ", zdzd=" + zdzd + ", zdbm=" + zdbm + ", zdwb=" + zdwb + ", mr="
				+ mr + ", href=" + href + ", cd=" + cd  + ", yzlx=" + yzlx + ", iskey=" + iskey
				+ ", isnull=" + isnull + ", isquery=" + isquery + ", isshow=" + isshow + ", isshowlb=" + isshowlb
				+ ", len=" + len + ", zzd=" + zzd + ", zb=" + zb + ", jzdm=" + jzdm + ", xh=" + xh + ", plen=" + plen
				+ ", cxms=" + cxms + ", xslx=" + xslx + ", lx=" + lx + ", bid=" + bid + ", kzjson=" + kzjson + ", whr="
				+ whr + ", whsj=" + whsj + ", whrid=" + whrid + ", cjr=" + cjr + ", cjsj=" + cjsj + ", cjrid=" + cjrid
				+ ", version=" + version + ", deleted=" + deleted + "]";
	}
	
	
}
