#RS Dev Plateform

##项目依赖配置2016-12-28
* 在maven安装目录/conf/settings > <mirrors>标签添加如下：（如果已经配置了alimaven, 请删除）

	<mirror>
		<id>rsmaven</id>
		<name>rs maven</name>
		<url>http://192.168.10.68:8081/nexus/content/groups/public/</url>
		<mirrorOf>*</mirrorOf>        
	</mirror>

##对单表CRUD的增强
项目启动后输入localhost:8080/sample

简单几步就可以实现对单表的CRUD，实现快速开发

##如何生成apiDoc
cd D:\RS\git\rs-dev-platform

apidoc -i src\main\java\com\rs\devplatform\controller\ -o apidoc\

##关于不同数据库：mysql, oracle的一些使用区别
LINUX下的MYSQL默认是要区分表名大小写的：
让MYSQL不区分表名大小写的方法其实很简单：
* 1.用ROOT登录，修改/etc/my.cnf
* 2.在[mysqld]下加入一行：lower_case_table_names=1
* 3.重新启动数据库即可

## nginx配置

    location / {
        proxy_pass  http://localhost:8080;
    }
    location /images/ {
        proxy_pass  http://localhost:8080/images/;
    }
    location /imserver {
        proxy_pass  http://localhost:8282;
    }
    location /im {
        proxy_pass  http://localhost:8282;
    }
