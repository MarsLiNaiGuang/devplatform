-- --------------------------------------------------------
-- 主机:                           192.168.10.64
-- 服务器版本:                        5.6.32 - MySQL Community Server (GPL)
-- 服务器操作系统:                      Linux
-- HeidiSQL 版本:                  9.3.0.4984
-- --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8mb4 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;

-- 导出  表 dev-platform.auth_dtrule 结构
CREATE TABLE IF NOT EXISTS `auth_dtrule` (
  `DTRULE_ID` varchar(36) NOT NULL,
  `DTRULE_NAME` varchar(100) DEFAULT NULL,
  `DTRULE_DESCP` varchar(200) DEFAULT NULL COMMENT '描述',
  `DTRULE_TJ` varchar(2000) DEFAULT NULL COMMENT '条件',
  `DTRULE_MENUID` varchar(45) DEFAULT NULL COMMENT '菜单ID',
  `DTRULE_WHRID` varchar(45) DEFAULT NULL,
  `DTRULE_WHR` varchar(45) DEFAULT NULL,
  `DTRULE_WHSJ` datetime DEFAULT NULL,
  `DTRULE_CJRID` varchar(45) DEFAULT NULL,
  `DTRULE_CJR` varchar(45) DEFAULT NULL,
  `DTRULE_CJSJ` datetime DEFAULT NULL,
  `DTRULE_VERSION` int(11) DEFAULT NULL,
  `DTRULE_DELETED` varchar(1) DEFAULT NULL,
  PRIMARY KEY (`DTRULE_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 数据导出被取消选择。


-- 导出  表 dev-platform.auth_operation 结构
CREATE TABLE IF NOT EXISTS `auth_operation` (
  `OPERATION_ID` varchar(36) NOT NULL,
  `OPERATION_NAME` varchar(100) DEFAULT NULL COMMENT '被权限控制的资源编码',
  `OPERATION_DESCP` varchar(500) DEFAULT NULL COMMENT '描述',
  `OPERATION_MENUID` varchar(45) DEFAULT NULL COMMENT '功能ID',
  `OPERATION_CONTENT` varchar(2000) DEFAULT NULL COMMENT '内容',
  `OPERATION_WHRID` varchar(50) DEFAULT NULL,
  `OPERATION_WHR` varchar(50) DEFAULT NULL,
  `OPERATION_WHSJ` datetime DEFAULT NULL,
  `OPERATION_CJRID` varchar(50) DEFAULT NULL,
  `OPERATION_CJR` varchar(50) DEFAULT NULL,
  `OPERATION_CJSJ` datetime DEFAULT NULL,
  `OPERATION_VERSION` int(11) DEFAULT NULL,
  `OPERATION_DELETED` varchar(1) DEFAULT NULL,
  PRIMARY KEY (`OPERATION_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='功能权限表';

-- 数据导出被取消选择。


-- 导出  表 dev-platform.auth_r2dt 结构
CREATE TABLE IF NOT EXISTS `auth_r2dt` (
  `R2DT_ID` varchar(50) NOT NULL,
  `R2DT_ROLEID` varchar(50) DEFAULT NULL,
  `R2DT_DTID` varchar(50) DEFAULT NULL,
  `R2DT_WHRID` varchar(45) DEFAULT NULL,
  `R2DT_WHR` varchar(45) DEFAULT NULL,
  `R2DT_WHSJ` datetime DEFAULT NULL,
  `R2DT_CJRID` varchar(45) DEFAULT NULL,
  `R2DT_CJR` varchar(45) DEFAULT NULL,
  `R2DT_CJSJ` datetime DEFAULT NULL,
  `R2DT_DELETED` varchar(45) DEFAULT NULL,
  `R2DT_VERSION` int(11) DEFAULT NULL,
  PRIMARY KEY (`R2DT_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 数据导出被取消选择。


-- 导出  表 dev-platform.auth_r2m 结构
CREATE TABLE IF NOT EXISTS `auth_r2m` (
  `R2M_ID` varchar(50) NOT NULL,
  `R2M_ROLEID` varchar(50) DEFAULT NULL COMMENT '角色ID',
  `R2M_MENUID` varchar(50) DEFAULT NULL COMMENT 'menuid',
  `R2M_OPERATION` varchar(2000) DEFAULT NULL COMMENT '存储控件权限',
  `R2M_CJRID` varchar(45) DEFAULT NULL,
  `R2M_CJR` varchar(45) DEFAULT NULL,
  `R2M_CJSJ` datetime DEFAULT NULL,
  `R2M_WHRID` varchar(45) DEFAULT NULL,
  `R2M_WHR` varchar(45) DEFAULT NULL,
  `R2M_WHSJ` datetime DEFAULT NULL,
  `R2M_VERSION` int(11) DEFAULT NULL,
  `R2M_DELETED` varchar(1) DEFAULT NULL,
  PRIMARY KEY (`R2M_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 数据导出被取消选择。


-- 导出  表 dev-platform.databasechangelog 结构
CREATE TABLE IF NOT EXISTS `databasechangelog` (
  `ID` varchar(255) NOT NULL,
  `AUTHOR` varchar(255) NOT NULL,
  `FILENAME` varchar(255) NOT NULL,
  `DATEEXECUTED` datetime NOT NULL,
  `ORDEREXECUTED` int(11) NOT NULL,
  `EXECTYPE` varchar(10) NOT NULL,
  `MD5SUM` varchar(35) DEFAULT NULL,
  `DESCRIPTION` varchar(255) DEFAULT NULL,
  `COMMENTS` varchar(255) DEFAULT NULL,
  `TAG` varchar(255) DEFAULT NULL,
  `LIQUIBASE` varchar(20) DEFAULT NULL,
  `CONTEXTS` varchar(255) DEFAULT NULL,
  `LABELS` varchar(255) DEFAULT NULL,
  `DEPLOYMENT_ID` varchar(10) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 数据导出被取消选择。


-- 导出  表 dev-platform.databasechangeloglock 结构
CREATE TABLE IF NOT EXISTS `databasechangeloglock` (
  `ID` int(11) NOT NULL,
  `LOCKED` bit(1) NOT NULL,
  `LOCKGRANTED` datetime DEFAULT NULL,
  `LOCKEDBY` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 数据导出被取消选择。


-- 导出  表 dev-platform.fs_auth 结构
CREATE TABLE IF NOT EXISTS `fs_auth` (
  `auth_id` varchar(45) NOT NULL,
  `auth_menutype` varchar(45) DEFAULT NULL,
  `auth_menuid` varchar(45) DEFAULT NULL,
  `auth_objid` varchar(45) DEFAULT NULL,
  `auth_objtype` varchar(45) DEFAULT NULL,
  `auth_content` int(11) DEFAULT NULL,
  `auth_cjrid` varchar(45) DEFAULT NULL,
  `auth_cjr` varchar(45) DEFAULT NULL,
  `auth_cjsj` datetime DEFAULT NULL,
  `auth_whrid` varchar(45) DEFAULT NULL,
  `auth_whr` varchar(45) DEFAULT NULL,
  `auth_whsj` datetime DEFAULT NULL,
  `auth_version` int(11) DEFAULT NULL,
  `auth_deleted` varchar(1) DEFAULT NULL,
  PRIMARY KEY (`auth_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 数据导出被取消选择。


-- 导出  表 dev-platform.fs_file 结构
CREATE TABLE IF NOT EXISTS `fs_file` (
  `file_id` varchar(45) NOT NULL,
  `file_name` varchar(45) DEFAULT NULL,
  `file_folderid` varchar(45) DEFAULT NULL,
  `file_path` varchar(1000) DEFAULT NULL,
  `file_category` varchar(45) DEFAULT NULL,
  `file_mime` varchar(1000) DEFAULT NULL,
  `file_size` int(11) DEFAULT NULL,
  `file_remark` varchar(1000) DEFAULT NULL,
  `file_cjrid` varchar(45) DEFAULT NULL,
  `file_cjr` varchar(45) DEFAULT NULL,
  `file_cjsj` datetime DEFAULT NULL,
  `file_whrid` varchar(45) DEFAULT NULL,
  `file_whr` varchar(45) DEFAULT NULL,
  `file_whsj` datetime DEFAULT NULL,
  `file_version` int(11) DEFAULT NULL,
  `file_deleted` varchar(1) DEFAULT NULL,
  PRIMARY KEY (`file_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 数据导出被取消选择。


-- 导出  表 dev-platform.fs_folder 结构
CREATE TABLE IF NOT EXISTS `fs_folder` (
  `folder_id` varchar(45) NOT NULL,
  `folder_name` varchar(45) DEFAULT NULL,
  `folder_pid` varchar(45) DEFAULT NULL,
  `folder_res` varchar(45) DEFAULT NULL,
  `folder_remark` varchar(45) DEFAULT NULL,
  `folder_cjrid` varchar(45) DEFAULT NULL,
  `folder_cjr` varchar(45) DEFAULT NULL,
  `folder_cjsj` datetime DEFAULT NULL,
  `folder_whrid` varchar(45) DEFAULT NULL,
  `folder_whr` varchar(45) DEFAULT NULL,
  `folder_whsj` datetime DEFAULT NULL,
  `folder_version` int(11) DEFAULT NULL,
  `folder_deleted` varchar(1) DEFAULT NULL,
  PRIMARY KEY (`folder_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 数据导出被取消选择。


-- 导出  表 dev-platform.im_file 结构
CREATE TABLE IF NOT EXISTS `im_file` (
  `FILE_ID` varchar(45) NOT NULL,
  `FILE_NAME` varchar(45) DEFAULT NULL COMMENT '文件名',
  `FILE_PATH` varchar(1000) DEFAULT NULL COMMENT '存储路径',
  `FILE_CJRID` varchar(45) DEFAULT NULL,
  `FILE_CJR` varchar(45) DEFAULT NULL,
  `FILE_CJSJ` datetime DEFAULT NULL,
  `FILE_WHRID` varchar(45) DEFAULT NULL,
  `FILE_WHR` varchar(45) DEFAULT NULL,
  `FILE_WHSJ` datetime DEFAULT NULL,
  `FILE_VERSION` int(11) DEFAULT NULL,
  `FILE_DELETED` varchar(1) DEFAULT NULL,
  PRIMARY KEY (`FILE_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='传输文件表';

-- 数据导出被取消选择。


-- 导出  表 dev-platform.im_log 结构
CREATE TABLE IF NOT EXISTS `im_log` (
  `LOG_ID` varchar(45) NOT NULL,
  `LOG_SENDER` varchar(45) DEFAULT NULL COMMENT '发送人',
  `LOG_RECVER` varchar(45) DEFAULT NULL COMMENT '接受者（接收人userid或topicid）',
  `LOG_SENDTIME` datetime DEFAULT NULL COMMENT '信息的发送时间',
  `LOG_TYPE` varchar(1) DEFAULT NULL COMMENT '聊天类型（U--私聊，G--群聊）',
  `LOG_CONTYPE` varchar(1) DEFAULT NULL COMMENT '记录类型（M--文本消息；F--文件消息）',
  `LOG_CONTENT` varchar(1000) DEFAULT NULL COMMENT '聊天内容',
  `LOG_CJRID` varchar(45) DEFAULT NULL,
  `LOG_CJR` varchar(45) DEFAULT NULL,
  `LOG_CJSJ` datetime DEFAULT NULL,
  `LOG_WHRID` varchar(45) DEFAULT NULL,
  `LOG_WHR` varchar(45) DEFAULT NULL,
  `LOG_WHSJ` datetime DEFAULT NULL,
  `LOG_VERSION` int(11) DEFAULT '0',
  `LOG_DELETED` varchar(1) DEFAULT NULL,
  PRIMARY KEY (`LOG_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='log表';

-- 数据导出被取消选择。


-- 导出  表 dev-platform.im_topic 结构
CREATE TABLE IF NOT EXISTS `im_topic` (
  `TOPIC_ID` varchar(45) NOT NULL,
  `TOPIC_NAME` varchar(45) DEFAULT NULL COMMENT '名称',
  `TOPIC_ACTOR` varchar(2000) DEFAULT NULL,
  `TOPIC_CJRID` varchar(45) DEFAULT NULL,
  `TOPIC_CJR` varchar(45) DEFAULT NULL,
  `TOPIC_CJSJ` datetime DEFAULT NULL,
  `TOPIC_WHRID` varchar(45) DEFAULT NULL,
  `TOPIC_WHR` varchar(45) DEFAULT NULL,
  `TOPIC_WHSJ` datetime DEFAULT NULL,
  `TOPIC_VERSION` int(11) DEFAULT '0',
  `TOPIC_DELETED` varchar(1) DEFAULT NULL,
  PRIMARY KEY (`TOPIC_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='主题表';

-- 数据导出被取消选择。


-- 导出  表 dev-platform.im_user 结构
CREATE TABLE IF NOT EXISTS `im_user` (
  `USER_ID` varchar(45) NOT NULL,
  `USER_NAME` varchar(45) DEFAULT NULL COMMENT '名称',
  `USER_NKNAME` varchar(45) DEFAULT NULL COMMENT '昵称',
  `USER_PASSWORD` varchar(45) DEFAULT NULL COMMENT '密码',
  `USER_OFFTIME` datetime DEFAULT NULL COMMENT '上次下线时间',
  `USER_CJRID` varchar(45) DEFAULT NULL,
  `USER_CJR` varchar(45) DEFAULT NULL,
  `USER_CJSJ` datetime DEFAULT NULL,
  `USER_WHRID` varchar(45) DEFAULT NULL,
  `USER_WHR` varchar(45) DEFAULT NULL,
  `USER_WHSJ` datetime DEFAULT NULL,
  `USER_VERSION` int(11) DEFAULT '1',
  `USER_DELETED` varchar(45) DEFAULT 'F',
  PRIMARY KEY (`USER_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 数据导出被取消选择。


-- 导出  表 dev-platform.li_test 结构
CREATE TABLE IF NOT EXISTS `li_test` (
  `TEST_WHR` varchar(50) DEFAULT NULL COMMENT '维护人',
  `TEST_CJSJ` datetime DEFAULT NULL COMMENT '创建时间',
  `TEST_WHSJ` datetime DEFAULT NULL COMMENT '维护时间',
  `TEST_NAME` varchar(32) DEFAULT NULL COMMENT 'name',
  `TEST_VERSION` int(11) DEFAULT NULL COMMENT '版本',
  `TEST_CJRID` varchar(50) DEFAULT NULL COMMENT '创建人ID',
  `TEST_ID` varchar(50) NOT NULL,
  `TEST_DELETED` varchar(50) DEFAULT NULL COMMENT '逻辑删除标记',
  `TEST_WHRID` varchar(50) DEFAULT NULL COMMENT '维护人ID',
  `TEST_CJR` varchar(50) DEFAULT NULL COMMENT '创建人',
  PRIMARY KEY (`TEST_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='li_test';

-- 数据导出被取消选择。


-- 导出  表 dev-platform.sys_bxx 结构
CREATE TABLE IF NOT EXISTS `sys_bxx` (
  `BXX_ID` varchar(32) NOT NULL COMMENT '表明细主键',
  `BXX_NR` varchar(200) NOT NULL COMMENT '表明细内容',
  `BXX_FCFLAG` varchar(20) NOT NULL COMMENT '功能编号',
  `BXX_ISCBOX` varchar(5) DEFAULT NULL COMMENT '是否是多选框',
  `BXX_ISDBSYN` varchar(20) DEFAULT NULL COMMENT '是否数据库同步',
  `BXX_ISPAGE` varchar(5) DEFAULT NULL COMMENT '是否分页',
  `BXX_ISTREE` varchar(5) DEFAULT NULL COMMENT '是否显示为树结构',
  `BXX_PKSEQ` varchar(200) DEFAULT NULL COMMENT '表单主键序列',
  `BXX_PKLX` varchar(100) DEFAULT NULL COMMENT '主键类型',
  `BXX_LX` int(11) DEFAULT '1' COMMENT '表类型：1：单表，2：主表，3：子表',
  `BXX_CXMS` varchar(10) DEFAULT NULL COMMENT '查询模式',
  `BXX_GXLX` int(11) DEFAULT NULL COMMENT '关系类型: 附表类型(0：一对多，1：一对一)',
  `BXX_ZB` varchar(1000) DEFAULT NULL COMMENT '子表字符串sub_table_str',
  `BXX_TABXH` int(11) DEFAULT NULL COMMENT 'tab序号(子表序号，关系到子表显示顺序)',
  `BXX_BM` varchar(50) NOT NULL COMMENT '表名',
  `BXX_ISQL` varchar(2000) DEFAULT NULL COMMENT '自定义功能SQL',
  `BXX_SFIDZDM` varchar(50) DEFAULT NULL COMMENT '树型结构中的父节点字段名字',
  `BXX_SIDZDM` varchar(50) DEFAULT NULL COMMENT '树结构id字段名字',
  `BXX_SZDM` varchar(50) DEFAULT NULL COMMENT '树字段名',
  `BXX_LB` varchar(50) DEFAULT 'bdfl_ptbd' COMMENT '表单类别',
  `BXX_MB` varchar(50) DEFAULT NULL COMMENT '表单模板',
  `BXX_MBM` varchar(50) DEFAULT NULL COMMENT '表单模板样式(移动端)',
  `BXX_WHR` varchar(50) DEFAULT NULL COMMENT '维护人名字',
  `BXX_WHSJ` datetime DEFAULT NULL COMMENT '维护时间',
  `BXX_WHRID` varchar(50) DEFAULT NULL COMMENT '维护人ID',
  `BXX_CJR` varchar(50) DEFAULT NULL COMMENT '创建人名字',
  `BXX_CJSJ` datetime DEFAULT NULL COMMENT '创建时间',
  `BXX_CJRID` varchar(50) DEFAULT NULL COMMENT '创建人ID',
  `BXX_DELETED` varchar(1) DEFAULT NULL,
  `BXX_VERSION` int(11) NOT NULL COMMENT '表单版本',
  PRIMARY KEY (`BXX_ID`),
  UNIQUE KEY `BXX_FCFLAG` (`BXX_FCFLAG`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 数据导出被取消选择。


-- 导出  表 dev-platform.sys_cdtype 结构
CREATE TABLE IF NOT EXISTS `sys_cdtype` (
  `CDTYPE_ID` varchar(50) NOT NULL,
  `CDTYPE_NAME` varchar(50) DEFAULT NULL,
  `CDTYPE_CDTYPE` varchar(50) DEFAULT NULL,
  `CDTYPE_WHR` varchar(50) DEFAULT NULL,
  `CDTYPE_WHSJ` datetime DEFAULT NULL,
  `CDTYPE_WHRID` varchar(50) DEFAULT NULL,
  `CDTYPE_CJR` varchar(50) DEFAULT NULL,
  `CDTYPE_CJSJ` datetime DEFAULT NULL,
  `CDTYPE_CJRID` varchar(50) DEFAULT NULL,
  `CDTYPE_VERSION` int(10) DEFAULT NULL,
  `CDTYPE_DELETED` varchar(1) DEFAULT NULL,
  PRIMARY KEY (`CDTYPE_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 数据导出被取消选择。


-- 导出  表 dev-platform.sys_cdval 结构
CREATE TABLE IF NOT EXISTS `sys_cdval` (
  `CDVAL_ID` varchar(50) NOT NULL,
  `CDVAL_CDTYPE` varchar(50) DEFAULT NULL,
  `CDVAL_CDVAL` varchar(50) DEFAULT NULL,
  `CDVAL_CDDESCP` varchar(50) DEFAULT NULL,
  `CDVAL_WHR` varchar(50) DEFAULT NULL,
  `CDVAL_WHSJ` datetime DEFAULT NULL,
  `CDVAL_WHRID` varchar(50) DEFAULT NULL,
  `CDVAL_CJR` varchar(50) DEFAULT NULL,
  `CDVAL_CJSJ` datetime DEFAULT NULL,
  `CDVAL_CJRID` varchar(50) DEFAULT NULL,
  `CDVAL_VERSION` int(10) DEFAULT NULL,
  `CDVAL_DELETED` varchar(1) DEFAULT NULL,
  PRIMARY KEY (`CDVAL_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 数据导出被取消选择。


-- 导出  表 dev-platform.sys_columns 结构
CREATE TABLE IF NOT EXISTS `sys_columns` (
  `COLUMNS_ID` varchar(50) NOT NULL COMMENT '字段记录主键',
  `COLUMNS_TABLEID` varchar(50) DEFAULT NULL COMMENT '对应表的ID',
  `COLUMNS_NAME` varchar(50) DEFAULT NULL COMMENT '字段名',
  `COLUMNS_COMMENT` varchar(50) DEFAULT NULL COMMENT '字段备注',
  `COLUMNS_ISPK` varchar(1) DEFAULT NULL COMMENT '是否主键，Y:主键',
  `COLUMNS_TYPE` varchar(30) DEFAULT NULL COMMENT '字段类型',
  `COLUMNS_LENGTH` int(3) DEFAULT NULL COMMENT '字段长度',
  `COLUMNS_DECPOINT` int(1) DEFAULT NULL COMMENT '小数点后的位数',
  `COLUMNS_ISNULL` varchar(1) DEFAULT NULL COMMENT '是否允许为空',
  `COLUMNS_DEFVAL` varchar(50) DEFAULT NULL COMMENT '字段默认值',
  `COLUMNS_SEQ` int(2) DEFAULT NULL COMMENT '字段序号',
  `COLUMNS_CHGTYPE` varchar(1) DEFAULT NULL COMMENT '字段更改类型，A:新增字段，U:字段更新，D:删除字段',
  `COLUMNS_OLDNAME` varchar(50) DEFAULT NULL COMMENT '修改字段名，老的字段名',
  `COLUMNS_WHR` varchar(50) DEFAULT NULL COMMENT '维护人名称',
  `COLUMNS_WHSJ` datetime DEFAULT NULL COMMENT '维护时间',
  `COLUMNS_WHRID` varchar(50) DEFAULT NULL COMMENT '维护人ID',
  `COLUMNS_CJR` varchar(50) DEFAULT NULL COMMENT '创建人名称',
  `COLUMNS_CJSJ` datetime DEFAULT NULL COMMENT '创建时间',
  `COLUMNS_CJRID` varchar(50) DEFAULT NULL COMMENT '创建人ID',
  `COLUMNS_VERSION` int(10) NOT NULL DEFAULT '1' COMMENT '版本号',
  `COLUMNS_DELETED` varchar(1) NOT NULL DEFAULT 'F' COMMENT '逻辑删除标识符',
  PRIMARY KEY (`COLUMNS_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='数据库表维护-字段';

-- 数据导出被取消选择。


-- 导出  表 dev-platform.sys_custbt 结构
CREATE TABLE IF NOT EXISTS `sys_custbt` (
  `CUSTBT_ID` varchar(32) NOT NULL COMMENT '自定义按钮主键',
  `CUSTBT_CODE` varchar(50) DEFAULT NULL COMMENT '自定义按钮编码',
  `CUSTBT_ICON` varchar(20) DEFAULT NULL COMMENT '自定义按钮图标',
  `CUSTBT_NAME` varchar(50) DEFAULT NULL COMMENT '自定义按钮名字',
  `CUSTBT_ZT` varchar(2) DEFAULT NULL COMMENT '自定义按钮状态',
  `CUSTBT_YS` varchar(20) DEFAULT NULL COMMENT '自定义按钮样式',
  `CUSTBT_EXP` varchar(255) DEFAULT NULL COMMENT '自定义按钮过期时间',
  `CUSTBT_BDID` varchar(32) DEFAULT NULL COMMENT '表单ID',
  `CUSTBT_CZLX` varchar(20) DEFAULT NULL COMMENT '按钮操作类型',
  `CUSTBT_XH` int(11) DEFAULT NULL COMMENT '按钮序号',
  `CUSTBT_WHR` varchar(50) DEFAULT NULL COMMENT '维护人名字',
  `CUSTBT_WHSJ` datetime DEFAULT NULL COMMENT '维护时间',
  `CUSTBT_WHRID` varchar(50) DEFAULT NULL COMMENT '维护人ID',
  `CUSTBT_CJR` varchar(50) DEFAULT NULL COMMENT '创建人名字',
  `CUSTBT_CJSJ` datetime DEFAULT NULL COMMENT '创建时间',
  `CUSTBT_CJRID` varchar(50) DEFAULT NULL COMMENT '创建人ID',
  `CUSTBT_VERSION` int(10) DEFAULT NULL COMMENT '数据记录版本',
  `CUSTBT_DELETED` varchar(1) DEFAULT NULL COMMENT '逻辑删除标记位',
  PRIMARY KEY (`CUSTBT_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 数据导出被取消选择。


-- 导出  表 dev-platform.sys_funcgp 结构
CREATE TABLE IF NOT EXISTS `sys_funcgp` (
  `FUNCGP_ID` varchar(50) NOT NULL DEFAULT '' COMMENT '功能ID，一个功能可以有很多资源（url）',
  `FUNCGP_NAME` varchar(50) DEFAULT NULL COMMENT '功能描述',
  `FUNCGP_WHR` varchar(50) DEFAULT NULL,
  `FUNCGP_WHSJ` datetime DEFAULT NULL,
  `FUNCGP_WHRID` varchar(50) DEFAULT NULL,
  `FUNCGP_CJR` varchar(50) DEFAULT NULL,
  `FUNCGP_CJSJ` datetime DEFAULT NULL,
  `FUNCGP_CJRID` varchar(50) DEFAULT NULL,
  `FUNCGP_VERSION` int(10) NOT NULL DEFAULT '1',
  `FUNCGP_DELETED` varchar(1) NOT NULL DEFAULT 'F',
  PRIMARY KEY (`FUNCGP_ID`),
  UNIQUE KEY `FUNCGP_NAME` (`FUNCGP_NAME`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 数据导出被取消选择。


-- 导出  表 dev-platform.sys_gp2tb 结构
CREATE TABLE IF NOT EXISTS `sys_gp2tb` (
  `GP2TB_ID` varchar(50) NOT NULL DEFAULT '',
  `GP2TB_GPID` varchar(50) DEFAULT NULL,
  `GP2TB_TBNAME` varchar(50) DEFAULT NULL,
  `GP2TB_WHR` varchar(50) DEFAULT NULL,
  `GP2TB_WHSJ` datetime DEFAULT NULL,
  `GP2TB_WHRID` varchar(50) DEFAULT NULL,
  `GP2TB_CJR` varchar(50) DEFAULT NULL,
  `GP2TB_CJSJ` datetime DEFAULT NULL,
  `GP2TB_CJRID` varchar(50) DEFAULT NULL,
  `GP2TB_VERSION` int(10) NOT NULL DEFAULT '1',
  `GP2TB_DELETED` varchar(1) NOT NULL DEFAULT 'F',
  PRIMARY KEY (`GP2TB_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 数据导出被取消选择。


-- 导出  表 dev-platform.sys_group 结构
CREATE TABLE IF NOT EXISTS `sys_group` (
  `group_id` varchar(45) NOT NULL,
  `group_name` varchar(45) DEFAULT NULL,
  `group_actor` varchar(200) DEFAULT NULL,
  `group_cjrid` varchar(45) DEFAULT NULL,
  `group_cjr` varchar(45) DEFAULT NULL,
  `group_cjsj` datetime DEFAULT NULL,
  `group_whrid` varchar(45) DEFAULT NULL,
  `group_whr` varchar(45) DEFAULT NULL,
  `group_whsj` datetime DEFAULT NULL,
  `group_version` int(11) DEFAULT NULL,
  `group_deleted` varchar(1) DEFAULT NULL,
  PRIMARY KEY (`group_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 数据导出被取消选择。


-- 导出  表 dev-platform.sys_indexs 结构
CREATE TABLE IF NOT EXISTS `sys_indexs` (
  `INDEXS_ID` varchar(50) NOT NULL,
  `INDEXS_TABLEID` varchar(50) DEFAULT NULL COMMENT '表单ID',
  `INDEXS_TYPE` varchar(50) DEFAULT NULL COMMENT 'normal,unique',
  `INDEXS_FIELD` varchar(50) DEFAULT NULL COMMENT '字段名',
  `INDEXS_NAME` varchar(50) DEFAULT NULL COMMENT 'index的名称',
  `INDEXS_SEQ` int(2) DEFAULT NULL,
  `INDEXS_WHR` varchar(50) DEFAULT NULL,
  `INDEXS_WHSJ` datetime DEFAULT NULL,
  `INDEXS_WHRID` varchar(50) DEFAULT NULL,
  `INDEXS_CJR` varchar(50) DEFAULT NULL,
  `INDEXS_CJSJ` datetime DEFAULT NULL,
  `INDEXS_CJRID` varchar(50) DEFAULT NULL,
  `INDEXS_VERSION` int(10) DEFAULT NULL,
  `INDEXS_DELETED` varchar(1) DEFAULT NULL,
  PRIMARY KEY (`INDEXS_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='sys_bxx里的表单所含有的index';

-- 数据导出被取消选择。


-- 导出  表 dev-platform.sys_mb 结构
CREATE TABLE IF NOT EXISTS `sys_mb` (
  `MB_ID` varchar(36) NOT NULL,
  `MB_XTZZDM` varchar(50) DEFAULT NULL COMMENT '所属部门',
  `MB_XTGSDM` varchar(50) DEFAULT NULL COMMENT '所属公司',
  `MB_NAME` varchar(100) DEFAULT NULL COMMENT '模板名称',
  `MB_CODE` varchar(50) DEFAULT NULL COMMENT '模板编码',
  `MB_LX` varchar(32) DEFAULT NULL COMMENT '模板类型',
  `MB_FX` varchar(10) DEFAULT NULL COMMENT '是否共享',
  `MB_PIC` varchar(100) DEFAULT NULL COMMENT '预览图',
  `MB_NR` varchar(200) DEFAULT NULL COMMENT '模板描述',
  `MB_LBMZ` varchar(200) DEFAULT NULL COMMENT '列表模板名称\r\n',
  `MB_TJMZ` varchar(200) DEFAULT NULL COMMENT '录入模板名称',
  `MB_GXMZ` varchar(200) DEFAULT NULL COMMENT '编辑模板名\r\n称',
  `MB_MXMZ` varchar(200) DEFAULT NULL COMMENT '查看页面模\r\n板名称',
  `MB_WHR` varchar(50) DEFAULT NULL COMMENT '维护人名字',
  `MB_WHSJ` datetime DEFAULT NULL COMMENT '维护时间',
  `MB_WHRID` varchar(50) DEFAULT NULL COMMENT '维护人ID',
  `MB_CJR` varchar(50) DEFAULT NULL COMMENT '创建人名字',
  `MB_CJSJ` datetime DEFAULT NULL COMMENT '创建时间',
  `MB_CJRID` varchar(50) DEFAULT NULL COMMENT '创建人ID',
  `MB_VERSION` int(10) DEFAULT NULL COMMENT '数据记录版本',
  `MB_DELETED` varchar(1) DEFAULT NULL COMMENT '逻辑删除标记位',
  PRIMARY KEY (`MB_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 数据导出被取消选择。


-- 导出  表 dev-platform.sys_menus 结构
CREATE TABLE IF NOT EXISTS `sys_menus` (
  `MENUS_ID` varchar(50) NOT NULL,
  `MENUS_NAME` varchar(50) DEFAULT NULL,
  `MENUS_URL` varchar(200) DEFAULT NULL,
  `menus_pid` varchar(45) DEFAULT NULL,
  `menus_type` varchar(1) DEFAULT NULL COMMENT 'A——url类型\nB——文件夹类型',
  `menus_funcid` varchar(50) DEFAULT NULL COMMENT '功能编号，refer to sys_bxx.bxx_fcflag',
  `menus_sequence` int(11) DEFAULT NULL,
  `MENUS_WHR` varchar(50) DEFAULT NULL,
  `MENUS_WHSJ` datetime DEFAULT NULL,
  `MENUS_WHRID` varchar(50) DEFAULT NULL,
  `MENUS_CJR` varchar(50) DEFAULT NULL,
  `MENUS_CJSJ` datetime DEFAULT NULL,
  `MENUS_CJRID` varchar(50) DEFAULT NULL,
  `MENUS_VERSION` int(10) DEFAULT NULL,
  `MENUS_DELETED` varchar(1) DEFAULT NULL,
  PRIMARY KEY (`MENUS_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 数据导出被取消选择。


-- 导出  表 dev-platform.sys_pj2res 结构
CREATE TABLE IF NOT EXISTS `sys_pj2res` (
  `PJ2RES_ID` varchar(50) NOT NULL DEFAULT '',
  `PJ2RES_PJ_ID` varchar(50) DEFAULT NULL,
  `PJ2RES_DB_TYPE` varchar(50) DEFAULT NULL,
  `PJ2RES_DB_URL` varchar(100) DEFAULT NULL,
  `PJ2RES_DB_USER` varchar(50) DEFAULT NULL,
  `PJ2RES_DB_PWD` varchar(50) DEFAULT NULL,
  `PJ2RES_WHR` varchar(50) DEFAULT NULL COMMENT '维护人名字',
  `PJ2RES_WHSJ` datetime DEFAULT NULL COMMENT '维护时间',
  `PJ2RES_WHRID` varchar(50) DEFAULT NULL COMMENT '维护人ID',
  `PJ2RES_CJR` varchar(50) DEFAULT NULL COMMENT '创建人名字',
  `PJ2RES_CJSJ` datetime DEFAULT NULL COMMENT '创建时间',
  `PJ2RES_CJRID` varchar(50) DEFAULT NULL COMMENT '创建人ID',
  `PJ2RES_VERSION` int(10) NOT NULL DEFAULT '1' COMMENT '数据记录版本',
  `PJ2RES_DELETED` varchar(1) DEFAULT NULL COMMENT '逻辑删除标记位',
  PRIMARY KEY (`PJ2RES_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 数据导出被取消选择。


-- 导出  表 dev-platform.sys_pj2u 结构
CREATE TABLE IF NOT EXISTS `sys_pj2u` (
  `PJ2U_ID` varchar(50) NOT NULL,
  `PJ2U_PJ_ID` varchar(50) DEFAULT NULL,
  `PJ2U_USER_ID` varchar(50) DEFAULT NULL,
  `PJ2U_USER_TYPE` varchar(5) DEFAULT NULL,
  `PJ2U_WHR` varchar(50) DEFAULT NULL COMMENT '维护人名字',
  `PJ2U_WHSJ` datetime DEFAULT NULL COMMENT '维护时间',
  `PJ2U_WHRID` varchar(50) DEFAULT NULL COMMENT '维护人ID',
  `PJ2U_CJR` varchar(50) DEFAULT NULL COMMENT '创建人名字',
  `PJ2U_CJSJ` datetime DEFAULT NULL COMMENT '创建时间',
  `PJ2U_CJRID` varchar(50) DEFAULT NULL COMMENT '创建人ID',
  `PJ2U_VERSION` int(10) DEFAULT NULL COMMENT '数据记录版本',
  `PJ2U_DELETED` varchar(1) DEFAULT NULL COMMENT '逻辑删除标记位',
  PRIMARY KEY (`PJ2U_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 数据导出被取消选择。


-- 导出  表 dev-platform.sys_pjs 结构
CREATE TABLE IF NOT EXISTS `sys_pjs` (
  `PJS_ID` varchar(50) NOT NULL,
  `PJS_NAME` varchar(50) DEFAULT NULL,
  `PJS_WHR` varchar(50) DEFAULT NULL COMMENT '维护人名字',
  `PJS_WHSJ` datetime DEFAULT NULL COMMENT '维护时间',
  `PJS_WHRID` varchar(50) DEFAULT NULL COMMENT '维护人ID',
  `PJS_CJR` varchar(50) DEFAULT NULL COMMENT '创建人名字',
  `PJS_CJSJ` datetime DEFAULT NULL COMMENT '创建时间',
  `PJS_CJRID` varchar(50) DEFAULT NULL COMMENT '创建人ID',
  `PJS_VERSION` int(10) NOT NULL DEFAULT '1' COMMENT '数据记录版本',
  `PJS_DELETED` varchar(1) DEFAULT NULL COMMENT '逻辑删除标记位',
  `PJS_INIT` varchar(1) DEFAULT NULL COMMENT '是否初始化标记,Y:初始化过',
  PRIMARY KEY (`PJS_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 数据导出被取消选择。


-- 导出  表 dev-platform.sys_roles 结构
CREATE TABLE IF NOT EXISTS `sys_roles` (
  `ROLES_ID` varchar(36) NOT NULL,
  `ROLES_NAME` varchar(50) NOT NULL,
  `ROLES_WHR` varchar(100) DEFAULT NULL COMMENT '维护人名字',
  `ROLES_WHSJ` datetime DEFAULT NULL COMMENT '维护时间',
  `ROLES_WHRID` varchar(100) DEFAULT NULL COMMENT '维护人ID',
  `ROLES_CJR` varchar(50) DEFAULT NULL COMMENT '创建人名字',
  `ROLES_CJSJ` datetime DEFAULT NULL COMMENT '创建时间',
  `ROLES_CJRID` varchar(100) DEFAULT NULL COMMENT '创建人ID',
  `ROLES_VERSION` int(10) DEFAULT NULL COMMENT '数据记录版本',
  `ROLES_DELETED` varchar(1) DEFAULT NULL COMMENT '逻辑删除标记位',
  PRIMARY KEY (`ROLES_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 数据导出被取消选择。


-- 导出  表 dev-platform.sys_tables 结构
CREATE TABLE IF NOT EXISTS `sys_tables` (
  `TABLES_ID` varchar(50) NOT NULL COMMENT '主键',
  `TABLES_NAME` varchar(50) DEFAULT NULL COMMENT '表名',
  `TABLES_COMMENT` varchar(50) DEFAULT NULL COMMENT '表注解',
  `TABLES_SYNC` varchar(1) DEFAULT NULL COMMENT '是否同步，Y:已同步，N:未同步',
  `TABLES_PKTYPE` varchar(10) DEFAULT NULL COMMENT '注解类型：UUID...',
  `TABLES_PKSEQ` varchar(50) DEFAULT NULL COMMENT '主键seq（适用于oracle等）',
  `TABLES_WHR` varchar(50) DEFAULT NULL COMMENT '维护人名',
  `TABLES_WHSJ` datetime DEFAULT NULL COMMENT '维护时间',
  `TABLES_WHRID` varchar(50) DEFAULT NULL COMMENT '维护人ID',
  `TABLES_CJR` varchar(50) DEFAULT NULL COMMENT '创建人名',
  `TABLES_CJSJ` datetime DEFAULT NULL COMMENT '创建时间',
  `TABLES_CJRID` varchar(50) DEFAULT NULL COMMENT '创建人ID',
  `TABLES_VERSION` int(10) NOT NULL DEFAULT '1' COMMENT '版本',
  `TABLES_DELETED` varchar(1) NOT NULL DEFAULT 'F' COMMENT '逻辑删除标识符',
  PRIMARY KEY (`TABLES_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='数据库表维护-表';

-- 数据导出被取消选择。


-- 导出  表 dev-platform.sys_u2r 结构
CREATE TABLE IF NOT EXISTS `sys_u2r` (
  `U2R_ID` varchar(36) NOT NULL,
  `U2R_USERID` varchar(36) NOT NULL,
  `U2R_ROLEID` varchar(36) NOT NULL,
  `U2R_WHR` varchar(50) DEFAULT NULL COMMENT '维护人名字',
  `U2R_WHSJ` datetime DEFAULT NULL COMMENT '维护时间',
  `U2R_WHRID` varchar(50) DEFAULT NULL COMMENT '维护人ID',
  `U2R_CJR` varchar(50) DEFAULT NULL COMMENT '创建人名字',
  `U2R_CJSJ` datetime DEFAULT NULL COMMENT '创建时间',
  `U2R_CJRID` varchar(50) DEFAULT NULL COMMENT '创建人ID',
  `U2R_VERSION` int(10) DEFAULT NULL COMMENT '数据记录版本',
  `U2R_DELETED` varchar(1) DEFAULT NULL COMMENT '逻辑删除标记位',
  PRIMARY KEY (`U2R_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 数据导出被取消选择。


-- 导出  表 dev-platform.sys_users 结构
CREATE TABLE IF NOT EXISTS `sys_users` (
  `USERS_ID` varchar(36) NOT NULL,
  `USERS_NAME` varchar(50) DEFAULT NULL,
  `USERS_NKNAME` varchar(100) DEFAULT NULL,
  `USERS_PWD` varchar(100) DEFAULT NULL,
  `USERS_EMAIL` varchar(100) DEFAULT NULL,
  `USERS_TEL` varchar(100) DEFAULT NULL,
  `USERS_ICON` varchar(200) DEFAULT NULL,
  `USERS_WHR` varchar(50) DEFAULT NULL COMMENT '维护人名字',
  `USERS_WHSJ` datetime DEFAULT NULL COMMENT '维护时间',
  `USERS_WHRID` varchar(50) DEFAULT NULL COMMENT '维护人ID',
  `USERS_CJR` varchar(50) DEFAULT NULL COMMENT '创建人名字',
  `USERS_CJSJ` datetime DEFAULT NULL COMMENT '创建时间',
  `USERS_CJRID` varchar(50) DEFAULT NULL COMMENT '创建人ID',
  `USERS_VERSION` int(10) DEFAULT NULL COMMENT '数据记录版本',
  `USERS_DELETED` varchar(1) DEFAULT NULL COMMENT '逻辑删除标记位',
  PRIMARY KEY (`USERS_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 数据导出被取消选择。


-- 导出  表 dev-platform.sys_zdxx 结构
CREATE TABLE IF NOT EXISTS `sys_zdxx` (
  `ZDXX_ID` varchar(32) NOT NULL COMMENT '字段明细主键',
  `ZDXX_NR` varchar(200) NOT NULL COMMENT '字段内容',
  `ZDXX_ZDZD` varchar(100) DEFAULT NULL COMMENT '字典字段',
  `ZDXX_ZDBM` varchar(100) DEFAULT NULL COMMENT '字典表名',
  `ZDXX_ZDWB` varchar(100) DEFAULT NULL COMMENT '字典文本',
  `ZDXX_MR` varchar(20) DEFAULT NULL COMMENT '字段默认值',
  `ZDXX_HREF` varchar(200) DEFAULT NULL COMMENT '字段超链接',
  `ZDXX_CD` int(11) DEFAULT NULL COMMENT '字段长度',
  `ZDXX_NAME` varchar(32) NOT NULL COMMENT '字段名字',
  `ZDXX_YZLX` varchar(10) DEFAULT NULL COMMENT '字段验证类型',
  `ZDXX_ISKEY` varchar(2) DEFAULT NULL COMMENT '是否主键',
  `ZDXX_ISNULL` varchar(5) DEFAULT NULL COMMENT '是否允许为空',
  `ZDXX_ISQUERY` varchar(5) DEFAULT NULL COMMENT '是否允许查询',
  `ZDXX_ISSHOW` varchar(5) DEFAULT NULL COMMENT '是否显示在表单',
  `ZDXX_ISSHOWLB` varchar(5) DEFAULT NULL COMMENT '是否显示为列表',
  `ZDXX_LEN` int(11) DEFAULT NULL COMMENT '字段显示长度',
  `ZDXX_ZZD` varchar(100) DEFAULT NULL COMMENT '主表字段',
  `ZDXX_ZB` varchar(100) DEFAULT NULL COMMENT '主表',
  `ZDXX_JZDM` varchar(32) DEFAULT NULL COMMENT '旧字段名字',
  `ZDXX_XH` int(11) DEFAULT NULL COMMENT '序号',
  `ZDXX_PLEN` int(11) DEFAULT NULL COMMENT '小数点位数',
  `ZDXX_CXMS` varchar(10) DEFAULT NULL COMMENT '查询模式',
  `ZDXX_XSLX` varchar(10) DEFAULT NULL COMMENT '显示类型',
  `ZDXX_LX` varchar(32) DEFAULT NULL COMMENT '字段类型',
  `ZDXX_BID` varchar(32) NOT NULL COMMENT '表ID',
  `ZDXX_KZJSON` varchar(500) DEFAULT NULL COMMENT '扩展JSON',
  `ZDXX_WHR` varchar(50) DEFAULT NULL COMMENT '维护人名字',
  `ZDXX_WHSJ` datetime DEFAULT NULL COMMENT '维护时间',
  `ZDXX_WHRID` varchar(50) DEFAULT NULL COMMENT '维护人ID',
  `ZDXX_CJR` varchar(50) DEFAULT NULL COMMENT '创建人名字',
  `ZDXX_CJSJ` datetime DEFAULT NULL COMMENT '创建时间',
  `ZDXX_CJRID` varchar(50) DEFAULT NULL COMMENT '创建人ID',
  `ZDXX_VERSION` int(10) DEFAULT NULL COMMENT '数据记录版本',
  `ZDXX_DELETED` varchar(1) NOT NULL DEFAULT 'F' COMMENT '逻辑删除标记位',
  `ZDXX_CONDFLAG` varchar(1) DEFAULT NULL COMMENT '条件标记，C:表示仅作为查询条件,B:Both,即作为数据列，又作为查询条件。仅SQL情况下使用',
  PRIMARY KEY (`ZDXX_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 数据导出被取消选择。


-- 导出  表 dev-platform.sys_zqjava 结构
CREATE TABLE IF NOT EXISTS `sys_zqjava` (
  `ZQJAVA_ID` varchar(36) NOT NULL COMMENT '增强Java主键',
  `ZQJAVA_CODE` varchar(32) DEFAULT NULL COMMENT '按钮编码',
  `ZQJAVA_JAVALX` varchar(32) NOT NULL COMMENT '类型',
  `ZQJAVA_JAVAZ` varchar(200) NOT NULL COMMENT '数值',
  `ZQJAVA_BDID` varchar(32) NOT NULL COMMENT '表单ID',
  `ZQJAVA_WHR` varchar(50) DEFAULT NULL COMMENT '维护人名字',
  `ZQJAVA_WHSJ` datetime DEFAULT NULL COMMENT '维护时间',
  `ZQJAVA_WHRID` varchar(50) DEFAULT NULL COMMENT '维护人ID',
  `ZQJAVA_CJR` varchar(50) DEFAULT NULL COMMENT '创建人名字',
  `ZQJAVA_CJSJ` datetime DEFAULT NULL COMMENT '创建时间',
  `ZQJAVA_CJRID` varchar(50) DEFAULT NULL COMMENT '创建人ID',
  `ZQJAVA_VERSION` int(10) DEFAULT NULL COMMENT '数据记录版本',
  `ZQJAVA_DELETED` varchar(1) DEFAULT NULL COMMENT '逻辑删除标记位',
  PRIMARY KEY (`ZQJAVA_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 数据导出被取消选择。


-- 导出  表 dev-platform.sys_zqjs 结构
CREATE TABLE IF NOT EXISTS `sys_zqjs` (
  `ZQJS_ID` varchar(32) NOT NULL COMMENT '增强JS主键',
  `ZQJS_JS` blob COMMENT '增强JS',
  `ZQJS_JSLX` varchar(20) DEFAULT NULL COMMENT '增强JS类型',
  `ZQJS_NR` text COMMENT '增强JS内容',
  `ZQJS_BDID` varchar(32) DEFAULT NULL COMMENT '表单ID',
  `ZQJS_WHR` varchar(50) DEFAULT NULL COMMENT '维护人名字',
  `ZQJS_WHSJ` datetime DEFAULT NULL COMMENT '维护时间',
  `ZQJS_WHRID` varchar(50) DEFAULT NULL COMMENT '维护人ID',
  `ZQJS_CJR` varchar(50) DEFAULT NULL COMMENT '创建人名字',
  `ZQJS_CJSJ` datetime DEFAULT NULL COMMENT '创建时间',
  `ZQJS_CJRID` varchar(50) DEFAULT NULL COMMENT '创建人ID',
  `ZQJS_VERSION` int(10) DEFAULT NULL COMMENT '数据记录版本',
  `ZQJS_DELETED` varchar(1) DEFAULT NULL COMMENT '逻辑删除标记位',
  PRIMARY KEY (`ZQJS_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 数据导出被取消选择。


-- 导出  表 dev-platform.sys_zqsql 结构
CREATE TABLE IF NOT EXISTS `sys_zqsql` (
  `ZQSQL_ID` varchar(32) NOT NULL COMMENT '增强SQL主键',
  `ZQSQL_CODE` varchar(50) DEFAULT NULL COMMENT '增强SQL编码',
  `ZQSQL_SQL` blob COMMENT '增强SQL',
  `ZQSQL_SQLNAME` varchar(50) DEFAULT NULL COMMENT '增强SQL名字',
  `ZQSQL_NR` longtext COMMENT '内容',
  `ZQSQL_BDID` varchar(32) DEFAULT NULL COMMENT '表单ID',
  `ZQSQL_WHR` varchar(50) DEFAULT NULL COMMENT '维护人名字',
  `ZQSQL_WHSJ` datetime DEFAULT NULL COMMENT '维护时间',
  `ZQSQL_WHRID` varchar(50) DEFAULT NULL COMMENT '维护人ID',
  `ZQSQL_CJR` varchar(50) DEFAULT NULL COMMENT '创建人名字',
  `ZQSQL_CJSJ` datetime DEFAULT NULL COMMENT '创建时间',
  `ZQSQL_CJRID` varchar(50) DEFAULT NULL COMMENT '创建人ID',
  `ZQSQL_VERSION` int(10) DEFAULT NULL COMMENT '数据记录版本',
  `ZQSQL_DELETED` varchar(1) DEFAULT NULL COMMENT '逻辑删除标记位',
  PRIMARY KEY (`ZQSQL_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 数据导出被取消选择。


-- 导出  表 dev-platform.tbl_ju9182 结构
CREATE TABLE IF NOT EXISTS `tbl_ju9182` (
  `JU9182_NAME_3` varchar(13) DEFAULT NULL COMMENT 'mark_3',
  `JU9182_NAME_4` varchar(14) DEFAULT NULL COMMENT 'mark_4',
  `JU9182_NAME_0` varchar(10) NOT NULL,
  `JU9182_NAME_2` varchar(12) DEFAULT NULL COMMENT 'mark_2',
  `JU9182_NAME_1` varchar(11) DEFAULT NULL COMMENT 'mark_1',
  PRIMARY KEY (`JU9182_NAME_0`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='tbl_ju91829182';

-- 数据导出被取消选择。


-- 导出  表 dev-platform.tbl_sample 结构
CREATE TABLE IF NOT EXISTS `tbl_sample` (
  `SAMPLE_VERSION` int(11) DEFAULT NULL COMMENT '版本',
  `SAMPLE_PRICE` decimal(32,0) DEFAULT NULL COMMENT 'price',
  `SAMPLE_CJSJ` datetime DEFAULT NULL COMMENT '创建时间',
  `SAMPLE_WHSJ` datetime DEFAULT NULL COMMENT '维护时间',
  `SAMPLE_WHR` varchar(50) DEFAULT NULL COMMENT '维护人',
  `SAMPLE_CJRID` varchar(50) DEFAULT NULL COMMENT '创建人ID',
  `SAMPLE_AGE` int(11) DEFAULT NULL COMMENT 'age',
  `SAMPLE_CJR` varchar(50) DEFAULT NULL COMMENT '创建人',
  `SAMPLE_WHRID` varchar(50) DEFAULT NULL COMMENT '维护人ID',
  `SAMPLE_NAME` varchar(32) DEFAULT NULL COMMENT 'name',
  `SAMPLE_NKNAME` varchar(32) DEFAULT NULL COMMENT 'nkname',
  `SAMPLE_DELETED` varchar(50) DEFAULT NULL COMMENT '逻辑删除标记',
  `SAMPLE_ID` varchar(50) NOT NULL,
  `SAMPLE_AAA` varchar(32) DEFAULT NULL COMMENT 'aaa',
  PRIMARY KEY (`SAMPLE_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='SAMPLE';

-- 数据导出被取消选择。


-- 导出  表 dev-platform.tbl_samplechild 结构
CREATE TABLE IF NOT EXISTS `tbl_samplechild` (
  `SAMPLECHILD_ID` varchar(50) NOT NULL,
  `SAMPLECHILD_NAME` varchar(50) DEFAULT NULL,
  `SAMPLECHILD_FK` varchar(50) NOT NULL,
  `SAMPLECHILD_WHR` varchar(50) DEFAULT NULL,
  `SAMPLECHILD_WHSJ` datetime DEFAULT NULL,
  `SAMPLECHILD_WHRID` varchar(50) DEFAULT NULL,
  `SAMPLECHILD_CJR` varchar(50) DEFAULT NULL,
  `SAMPLECHILD_CJSJ` datetime DEFAULT NULL,
  `SAMPLECHILD_CJRID` varchar(50) DEFAULT NULL,
  `SAMPLECHILD_VERSION` int(10) DEFAULT NULL,
  `SAMPLECHILD_DELETED` varchar(1) DEFAULT NULL,
  PRIMARY KEY (`SAMPLECHILD_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 数据导出被取消选择。


-- 导出  表 dev-platform.wf_version 结构
CREATE TABLE IF NOT EXISTS `wf_version` (
  `WF_VERSION_ID` varchar(50) NOT NULL,
  `REF_MKID` varchar(50) NOT NULL,
  `VERSION` int(10) NOT NULL,
  `CREATED_BY` varchar(50) DEFAULT NULL,
  `UPDATED_BY` varchar(50) DEFAULT NULL,
  `CREATED_DT` datetime DEFAULT NULL,
  `UPDATED_DT` datetime DEFAULT NULL,
  PRIMARY KEY (`WF_VERSION_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 数据导出被取消选择。


-- 导出  表 dev-platform.xb_test 结构
CREATE TABLE IF NOT EXISTS `xb_test` (
  `TEST_CJR` varchar(50) DEFAULT NULL COMMENT '创建人',
  `TEST_DELETED` varchar(50) DEFAULT NULL COMMENT '逻辑删除标记',
  `TEST_CJRID` varchar(50) DEFAULT NULL COMMENT '创建人ID',
  `TEST_ID` varchar(50) NOT NULL,
  `TEST_WHR` varchar(50) DEFAULT NULL COMMENT '维护人',
  `TEST_WHRID` varchar(50) DEFAULT NULL COMMENT '维护人ID',
  `TEST_CJSJ` datetime DEFAULT NULL COMMENT '创建时间',
  `TEST_VERSION` int(11) DEFAULT NULL COMMENT '版本',
  `TEST_WHSJ` datetime DEFAULT NULL COMMENT '维护时间',
  PRIMARY KEY (`TEST_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='test';

-- 数据导出被取消选择。


-- 导出  表 dev-platform.xs_so 结构
CREATE TABLE IF NOT EXISTS `xs_so` (
  `SO_CJSJ` datetime DEFAULT NULL COMMENT '创建时间',
  `SO_WHR` varchar(50) DEFAULT NULL COMMENT '维护人',
  `SO_ID` varchar(50) NOT NULL,
  `SO_WHSJ` datetime DEFAULT NULL COMMENT '维护时间',
  `SO_GENDER` varchar(32) DEFAULT NULL COMMENT '性别',
  `SO_VERSION` int(11) DEFAULT NULL COMMENT '版本',
  `SO_CJRID` varchar(50) DEFAULT NULL COMMENT '创建人ID',
  `SO_DELETED` varchar(50) DEFAULT NULL COMMENT '逻辑删除标记',
  `SO_WHRID` varchar(50) DEFAULT NULL COMMENT '维护人ID',
  `SO_NAME` varchar(32) DEFAULT NULL COMMENT '姓名',
  `SO_CJR` varchar(50) DEFAULT NULL COMMENT '创建人',
  `SO_AGE` int(11) DEFAULT NULL COMMENT '年龄',
  PRIMARY KEY (`SO_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='销售订单';

-- 数据导出被取消选择。


-- 导出  表 dev-platform.zp_grade 结构
CREATE TABLE IF NOT EXISTS `zp_grade` (
  `GRADE_WHSJ` datetime DEFAULT NULL COMMENT '维护时间',
  `GRADE_RESUMEID` varchar(32) DEFAULT NULL COMMENT '简历id',
  `GRADE_CJR` varchar(50) DEFAULT NULL COMMENT '创建人',
  `GRADE_CJRID` varchar(50) DEFAULT NULL COMMENT '创建人ID',
  `GRADE_STATUS` varchar(32) DEFAULT NULL COMMENT '录用状态',
  `GRADE_CJSJ` datetime DEFAULT NULL COMMENT '创建时间',
  `GRADE_WHR` varchar(50) DEFAULT NULL COMMENT '维护人',
  `GRADE_DELETED` varchar(50) DEFAULT NULL COMMENT '逻辑删除标记',
  `GRADE_ID` varchar(50) NOT NULL,
  `GRADE_GRADE1` varchar(50) DEFAULT NULL COMMENT '技术面试成绩',
  `GRADE_NAME` varchar(32) DEFAULT NULL COMMENT '姓名',
  `GRADE_GRADE2` varchar(32) DEFAULT NULL COMMENT '综合面试成绩',
  `GRADE_VERSION` int(11) DEFAULT NULL COMMENT '版本',
  `GRADE_WHRID` varchar(50) DEFAULT NULL COMMENT '维护人ID',
  PRIMARY KEY (`GRADE_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='面试评分表';

-- 数据导出被取消选择。


-- 导出  表 dev-platform.zp_job 结构
CREATE TABLE IF NOT EXISTS `zp_job` (
  `JOB_DELETED` varchar(50) DEFAULT NULL COMMENT '逻辑删除标记',
  `JOB_WHRID` varchar(50) DEFAULT NULL COMMENT '维护人ID',
  `JOB_WHR` varchar(50) DEFAULT NULL COMMENT '维护人',
  `JOB_DETAIL` varchar(250) DEFAULT NULL COMMENT '岗位描述',
  `JOB_NAME` varchar(32) DEFAULT NULL COMMENT '岗位名称',
  `JOB_CJSJ` datetime DEFAULT NULL COMMENT '创建时间',
  `JOB_ID` varchar(50) NOT NULL,
  `JOB_WHSJ` datetime DEFAULT NULL COMMENT '维护时间',
  `JOB_VERSION` int(11) DEFAULT NULL COMMENT '版本',
  `JOB_CJRID` varchar(50) DEFAULT NULL COMMENT '创建人ID',
  `JOB_TREATMENT` int(11) NOT NULL COMMENT '岗位待遇',
  `JOB_REQUIRE` varchar(32) NOT NULL COMMENT '岗位要求',
  `JOB_CJR` varchar(50) DEFAULT NULL COMMENT '创建人',
  `JOB_CODE` varchar(32) NOT NULL COMMENT '岗位code',
  PRIMARY KEY (`JOB_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='招聘岗位表';

-- 数据导出被取消选择。


-- 导出  表 dev-platform.zp_msg 结构
CREATE TABLE IF NOT EXISTS `zp_msg` (
  `MSG_WHSJ` datetime DEFAULT NULL COMMENT '维护时间',
  `MSG_CJRID` varchar(50) DEFAULT NULL COMMENT '创建人ID',
  `MSG_CJSJ` datetime DEFAULT NULL COMMENT '创建时间',
  `MSG_RESUMEID` varchar(32) DEFAULT NULL COMMENT '简历id',
  `MSG_CJR` varchar(50) DEFAULT NULL COMMENT '创建人',
  `MSG_VERSION` int(11) DEFAULT NULL COMMENT '版本',
  `MSG_PHONE` varchar(32) DEFAULT NULL COMMENT '电话',
  `MSG_EMAIL` varchar(32) DEFAULT NULL COMMENT '邮箱',
  `MSG_NAME` varchar(32) DEFAULT NULL COMMENT '姓名',
  `MSG_WHRID` varchar(50) DEFAULT NULL COMMENT '维护人ID',
  `MSG_DELETED` varchar(50) DEFAULT NULL COMMENT '逻辑删除标记',
  `MSG_ID` varchar(50) NOT NULL,
  `MSG_WHR` varchar(50) DEFAULT NULL COMMENT '维护人',
  PRIMARY KEY (`MSG_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='通知表';

-- 数据导出被取消选择。


-- 导出  表 dev-platform.zp_process 结构
CREATE TABLE IF NOT EXISTS `zp_process` (
  `PROCESS_WHRID` varchar(50) DEFAULT NULL COMMENT '维护人ID',
  `PROCESS_ID` varchar(50) NOT NULL,
  `PROCESS_CODE` varchar(32) DEFAULT NULL COMMENT '状态code',
  `PROCESS_CJRID` varchar(50) DEFAULT NULL COMMENT '创建人ID',
  `PROCESS_CJSJ` datetime DEFAULT NULL COMMENT '创建时间',
  `PROCESS_WHR` varchar(50) DEFAULT NULL COMMENT '维护人',
  `PROCESS_NAME` varchar(50) DEFAULT NULL COMMENT '状态名称',
  `PROCESS_VERSION` int(11) DEFAULT NULL COMMENT '版本',
  `PROCESS_WHSJ` datetime DEFAULT NULL COMMENT '维护时间',
  `PROCESS_VALUE` varchar(10) DEFAULT NULL COMMENT '状态值',
  `PROCESS_DELETED` varchar(50) DEFAULT NULL COMMENT '逻辑删除标记',
  `PROCESS_CJR` varchar(50) DEFAULT NULL COMMENT '创建人',
  PRIMARY KEY (`PROCESS_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='招聘过程状态表';

-- 数据导出被取消选择。


-- 导出  表 dev-platform.zp_projp 结构
CREATE TABLE IF NOT EXISTS `zp_projp` (
  `PROJP_DELETED` varchar(50) DEFAULT NULL COMMENT '逻辑删除标记',
  `PROJP_RESUMEID` varchar(32) DEFAULT NULL COMMENT '简历id',
  `PROJP_NAME` varchar(32) DEFAULT NULL COMMENT '项目名称',
  `PROJP_BEGIN` datetime DEFAULT NULL COMMENT '项目起期',
  `PROJP_END` datetime DEFAULT NULL COMMENT '项目止期',
  `PROJP_WHRID` varchar(50) DEFAULT NULL COMMENT '维护人ID',
  `PROJP_CJSJ` datetime DEFAULT NULL COMMENT '创建时间',
  `PROJP_CONTENT` varchar(32) DEFAULT NULL COMMENT '负责内容',
  `PROJP_WHR` varchar(50) DEFAULT NULL COMMENT '维护人',
  `PROJP_WHSJ` datetime DEFAULT NULL COMMENT '维护时间',
  `PROJP_ID` varchar(50) NOT NULL,
  `PROJP_VERSION` int(11) DEFAULT NULL COMMENT '版本',
  `PROJP_CJRID` varchar(50) DEFAULT NULL COMMENT '创建人ID',
  `PROJP_CJR` varchar(50) DEFAULT NULL COMMENT '创建人',
  PRIMARY KEY (`PROJP_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='项目经历';

-- 数据导出被取消选择。


-- 导出  表 dev-platform.zp_resume 结构
CREATE TABLE IF NOT EXISTS `zp_resume` (
  `RESUME_EMAIL` varchar(32) DEFAULT NULL COMMENT '邮箱',
  `RESUME_JOB` varchar(32) DEFAULT NULL COMMENT '申请职位',
  `RESUME_NAME` varchar(32) DEFAULT NULL COMMENT '姓名',
  `RESUME_AGE` int(11) DEFAULT NULL COMMENT '年龄',
  `RESUME_ID` varchar(50) NOT NULL,
  `RESUME_WHR` varchar(50) DEFAULT NULL COMMENT '维护人',
  `RESUME_CODE` varchar(32) DEFAULT NULL COMMENT '简历code',
  `RESUME_CJSJ` datetime DEFAULT NULL COMMENT '创建时间',
  `RESUME_SCHOOL` varchar(32) DEFAULT NULL COMMENT '学校',
  `RESUME_WHRID` varchar(50) DEFAULT NULL COMMENT '维护人ID',
  `RESUME_CJR` varchar(50) DEFAULT NULL COMMENT '创建人',
  `RESUME_PIC` varchar(32) DEFAULT NULL COMMENT '头像',
  `RESUME_DELETED` varchar(50) DEFAULT NULL COMMENT '逻辑删除标记',
  `RESUME_WHSJ` datetime DEFAULT NULL COMMENT '维护时间',
  `RESUME_VERSION` int(11) DEFAULT NULL COMMENT '版本',
  `RESUME_ATTS` varchar(100) DEFAULT NULL COMMENT '附件简历',
  `RESUME_PHONE` varchar(32) DEFAULT NULL COMMENT '电话',
  `RESUME_STATUS` varchar(32) DEFAULT NULL COMMENT '状态',
  `RESUME_CJRID` varchar(50) DEFAULT NULL COMMENT '创建人ID',
  `RESUME_BIRTHDAY` date DEFAULT NULL COMMENT '出生日期',
  PRIMARY KEY (`RESUME_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='简历表';

-- 数据导出被取消选择。


-- 导出  表 dev-platform.zp_workp 结构
CREATE TABLE IF NOT EXISTS `zp_workp` (
  `WORKP_DELETED` varchar(50) DEFAULT NULL COMMENT '逻辑删除标记',
  `WORKP_ID` varchar(50) NOT NULL,
  `WORKP_RESUMEID` varchar(32) DEFAULT NULL COMMENT '简历id',
  `WORKP_WHR` varchar(50) DEFAULT NULL COMMENT '维护人',
  `WORKP_WHRID` varchar(50) DEFAULT NULL COMMENT '维护人ID',
  `WORKP_COMPANY` varchar(32) DEFAULT NULL COMMENT '工作单位',
  `WORKP_WHSJ` datetime DEFAULT NULL COMMENT '维护时间',
  `WORKP_CJSJ` datetime DEFAULT NULL COMMENT '创建时间',
  `WORKP_VERSION` int(11) DEFAULT NULL COMMENT '版本',
  `WORKP_CJR` varchar(50) DEFAULT NULL COMMENT '创建人',
  `WORKP_JOB` varchar(32) DEFAULT NULL COMMENT '任职',
  `WORKP_BEGIN` varchar(32) DEFAULT NULL COMMENT '工作起期',
  `WORKP_END` varchar(32) DEFAULT NULL COMMENT '工作止期',
  `WORKP_CJRID` varchar(50) DEFAULT NULL COMMENT '创建人ID',
  PRIMARY KEY (`WORKP_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='工作经历';

-- 数据导出被取消选择。
/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IF(@OLD_FOREIGN_KEY_CHECKS IS NULL, 1, @OLD_FOREIGN_KEY_CHECKS) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
