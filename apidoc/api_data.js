define({ "api": [
  {
    "type": "GET",
    "url": "/admin/func",
    "title": "转到功能组管理页面",
    "group": "Admin_Func_Manager",
    "success": {
      "fields": {
        "Success 200": [
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "html",
            "description": "<p>对应数据字典管理页面</p>"
          }
        ]
      },
      "examples": [
        {
          "title": "页面命名:",
          "content": "{\"页面\":\"/manage/func.html\"}",
          "type": "json"
        }
      ]
    },
    "version": "0.0.0",
    "filename": "src/main/java/com/rs/devplatform/controller/admin/FuncController.java",
    "groupTitle": "Admin_Func_Manager",
    "name": "GetAdminFunc"
  },
  {
    "type": "GET",
    "url": "/admin/func/button/{menuId}",
    "title": "获取菜单的按钮",
    "group": "Admin_Func_Manager",
    "parameter": {
      "fields": {
        "Parameter": [
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "menuId",
            "description": "<p>菜单ID</p>"
          }
        ]
      }
    },
    "success": {
      "fields": {
        "Success 200": [
          {
            "group": "Success 200",
            "type": "JSONArray",
            "optional": false,
            "field": "rows",
            "description": "<p>按钮</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "rows.id",
            "description": "<p>按钮ID</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "rows.name",
            "description": "<p>按钮名称</p>"
          }
        ]
      },
      "examples": [
        {
          "title": "Success-Response:",
          "content": "\t\tHTTP/1.1 200 OK\n{\n\t\t  \"total\": 3,\n\t\t  \"rows\": [\n\t\t    {\n\t\t      \"id\": \"add\",\n\t\t      \"name\": \"add\",\n\t\t      \"propertyName\": null,\n\t\t      \"columnName\": null\n\t\t    },\n\t\t    {\n\t\t      \"id\": \"edit\",\n\t\t      \"name\": \"edit\",\n\t\t      \"propertyName\": null,\n\t\t      \"columnName\": null\n\t\t    },\n\t\t    {\n\t\t      \"id\": \"delete\",\n\t\t      \"name\": \"delete\",\n\t\t      \"propertyName\": null,\n\t\t      \"columnName\": null\n\t\t    }\n\t\t  ]\n\t\t}",
          "type": "json"
        }
      ]
    },
    "version": "0.0.0",
    "filename": "src/main/java/com/rs/devplatform/controller/admin/FuncController.java",
    "groupTitle": "Admin_Func_Manager",
    "name": "GetAdminFuncButtonMenuid"
  },
  {
    "type": "GET",
    "url": "/admin/func/column/{menuId}",
    "title": "获取功能组下所有表字段",
    "group": "Admin_Func_Manager",
    "parameter": {
      "fields": {
        "Parameter": [
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "menuId",
            "description": "<p>菜单ID</p>"
          }
        ]
      },
      "examples": [
        {
          "title": "Request-Example: ",
          "content": "{json example}",
          "type": "json"
        }
      ]
    },
    "success": {
      "fields": {
        "Success 200": [
          {
            "group": "Success 200",
            "type": "JSONArray",
            "optional": false,
            "field": "rows",
            "description": "<p>表字段信息</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "rows.id",
            "description": "<p>表字段ID</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "rows.name",
            "description": "<p>表字段名称</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "rows.propertyName",
            "description": "<p>属性名</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "rows.columnName",
            "description": "<p>列名</p>"
          }
        ]
      },
      "examples": [
        {
          "title": "Success-Response:",
          "content": "\t\tHTTP/1.1 200 OK\n{\n\t\t  \"total\": 3,\n\t\t  \"rows\": [\n\t\t    {\n\t\t      \"id\": \"Stu_name\",\n\t\t      \"name\": \"员工姓名\",\n\t\t      \"propertyName\": null,\n\t\t      \"columnName\": \"Stu_name\"\n\t\t    },\n\t\t    {\n\t\t      \"id\": \"Stu_age\",\n\t\t      \"name\": \"年龄\",\n\t\t      \"propertyName\": null,\n\t\t      \"columnName\": \"Stu_age\"\n\t\t    },\n\t\t    {\n\t\t      \"id\": \"Stu_cjr\",\n\t\t      \"name\": \"创建人\",\n\t\t      \"propertyName\": null,\n\t\t      \"columnName\": \"Stu_cjr\"\n\t\t    }\n\t\t  ]\n\t\t}",
          "type": "json"
        }
      ]
    },
    "version": "0.0.0",
    "filename": "src/main/java/com/rs/devplatform/controller/admin/FuncController.java",
    "groupTitle": "Admin_Func_Manager",
    "name": "GetAdminFuncColumnMenuid"
  },
  {
    "type": "POST",
    "url": "/admin/func/list",
    "title": "获取所有的功能组",
    "group": "Admin_Func_Manager",
    "parameter": {
      "fields": {
        "Parameter": [
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "current",
            "description": "<p>当前页数</p>"
          },
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "size",
            "description": "<p>每页的行数</p>"
          },
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "orderBy",
            "description": "<p>按照什么字段进行排序</p>"
          },
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "asc",
            "description": "<p>是否从小到大排序（true--是；false--否）</p>"
          }
        ]
      },
      "examples": [
        {
          "title": "Request-Example: ",
          "content": "\t{\n\t\t\"current\":1, \n\t\t\"size\":10, \n\t\t\"orderBy\":\"name\", \n\t\t\"asc\":\"true\",\n\t\t\"name\":\"功能名称模糊查询\"\n}",
          "type": "json"
        }
      ]
    },
    "success": {
      "fields": {
        "Success 200": [
          {
            "group": "Success 200",
            "type": "JSONArray",
            "optional": false,
            "field": "funcs",
            "description": "<p>功能列表</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "funcs.name",
            "description": "<p>功能名称</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "funcs.id",
            "description": "<p>功能ID</p>"
          }
        ]
      },
      "examples": [
        {
          "title": "Success-Response:",
          "content": "\t\tHTTP/1.1 200 OK\n{\n\t\t\t\"total\": 6,\n\t\t\t\"current\": 1,\n\t\t\t\"pages\": 2,\n\t\t\t\"size\": 5,\n\t\t\t\"rows\": [{\n\t\t\t\t\"name\": \"a\",\n\t\t\t\t\"id\": \"1\",\n\t\t\t\t\"version\": 1\n\t\t\t},\n\t\t\t{\n\t\t\t\t\"name\": \"x2\",\n\t\t\t\t\"id\": \"x2\",\n\t\t\t\t\"version\": 1\n\t\t\t}]\n\t\t}",
          "type": "json"
        }
      ]
    },
    "version": "0.0.0",
    "filename": "src/main/java/com/rs/devplatform/controller/admin/FuncController.java",
    "groupTitle": "Admin_Func_Manager",
    "name": "PostAdminFuncList"
  },
  {
    "type": "GET",
    "url": "/admin/group",
    "title": "转到用户组管理页面",
    "group": "Admin_Group",
    "success": {
      "fields": {
        "Success 200": [
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "html",
            "description": "<p>对应用户组管理页面</p>"
          }
        ]
      },
      "examples": [
        {
          "title": "页面命名:",
          "content": "{\"页面\":\"/manage/group.html\"}",
          "type": "json"
        }
      ]
    },
    "version": "0.0.0",
    "filename": "src/main/java/com/rs/devplatform/controller/admin/GroupController.java",
    "groupTitle": "Admin_Group",
    "name": "GetAdminGroup"
  },
  {
    "type": "DELETE",
    "url": "/admin/group",
    "title": "删除用户组（单个/多个删除）",
    "group": "Admin_Group_Manager",
    "parameter": {
      "fields": {
        "Parameter": [
          {
            "group": "Parameter",
            "type": "JSONArray",
            "optional": false,
            "field": "ids",
            "description": "<p>用户组ID</p>"
          }
        ]
      },
      "examples": [
        {
          "title": "Request-Example: ",
          "content": "{\"ids\":[\"1\",\"2\",\"3\"]}",
          "type": "json"
        }
      ]
    },
    "success": {
      "fields": {
        "Success 200": [
          {
            "group": "Success 200",
            "type": "String/int/json/JSONArray",
            "optional": false,
            "field": "descp",
            "description": "<p>描述(多个)</p>"
          }
        ]
      },
      "examples": [
        {
          "title": "Success-Response:",
          "content": "HTTP/1.1 200 OK",
          "type": "json"
        }
      ]
    },
    "error": {
      "examples": [
        {
          "title": "Error-Response:",
          "content": "HTTP/1.1 409 Conflict",
          "type": "json"
        },
        {
          "title": "Error-Response:",
          "content": "HTTP/1.1 400 Bad Request",
          "type": "json"
        }
      ]
    },
    "version": "0.0.0",
    "filename": "src/main/java/com/rs/devplatform/controller/admin/GroupController.java",
    "groupTitle": "Admin_Group_Manager",
    "name": "DeleteAdminGroup"
  },
  {
    "type": "GET",
    "url": "/admin/group/list",
    "title": "获取所有的用户组",
    "group": "Admin_Group_Manager",
    "success": {
      "fields": {
        "Success 200": [
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "id",
            "description": "<p>用户组ID</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "name",
            "description": "<p>用户组名称</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "actor",
            "description": "<p>用户组参与人员id</p>"
          }
        ]
      },
      "examples": [
        {
          "title": "Success-Response:",
          "content": "\t\tHTTP/1.1 200 OK\n{\n\t\t  \"total\": 2,\n\t\t  \"rows\": [\n\t\t    {\n\t\t      \"id\": \"01\",\n\t\t      \"name\": \"test1\",\n\t\t      \"actor\": \"1,4,admin,\",\n\t\t      \"version\": null\n\t\t    },\n\t\t    {\n\t\t      \"id\": \"80b66b2220064ff3a7fa7997fa51edc0\",\n\t\t      \"name\": \"test3\",\n\t\t      \"actor\": \"1,4,admin,\",\n\t\t      \"version\": 1\n\t\t    }\n\t\t  ]\n\t\t}",
          "type": "json"
        }
      ]
    },
    "version": "0.0.0",
    "filename": "src/main/java/com/rs/devplatform/controller/admin/GroupController.java",
    "groupTitle": "Admin_Group_Manager",
    "name": "GetAdminGroupList"
  },
  {
    "type": "POST",
    "url": "/admin/group",
    "title": "新增用户组",
    "group": "Admin_Group_Manager",
    "parameter": {
      "fields": {
        "Parameter": [
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "name",
            "description": "<p>组名称</p>"
          },
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "actor",
            "description": "<p>用户IDs</p>"
          }
        ]
      },
      "examples": [
        {
          "title": "Request-Example: ",
          "content": "{\"name\":\"test2\",\"actor\":\"1,4,a,\"}",
          "type": "json"
        }
      ]
    },
    "success": {
      "fields": {
        "Success 200": [
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "id",
            "description": "<p>用户组ID</p>"
          }
        ]
      },
      "examples": [
        {
          "title": "Success-Response:",
          "content": "\t\tHTTP/1.1 200 OK\n{\n\t\t  \"id\": \"80b66b2220064ff3a7fa7997fa51edc0\"\n\t\t}",
          "type": "json"
        }
      ]
    },
    "error": {
      "examples": [
        {
          "title": "Error-Response:",
          "content": "HTTP/1.1 302 Found",
          "type": "json"
        },
        {
          "title": "Error-Response:",
          "content": "HTTP/1.1 409 Conflict",
          "type": "json"
        },
        {
          "title": "Error-Response:",
          "content": "HTTP/1.1 400 Bad Request",
          "type": "json"
        }
      ]
    },
    "version": "0.0.0",
    "filename": "src/main/java/com/rs/devplatform/controller/admin/GroupController.java",
    "groupTitle": "Admin_Group_Manager",
    "name": "PostAdminGroup"
  },
  {
    "type": "PUT",
    "url": "/admin/group/{groupId}",
    "title": "修改group",
    "group": "Admin_Group_Manager",
    "parameter": {
      "fields": {
        "Parameter": [
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "groupId",
            "description": "<p>用户组ID</p>"
          },
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "name",
            "description": "<p>组名称</p>"
          },
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "actor",
            "description": "<p>用户IDs</p>"
          }
        ]
      },
      "examples": [
        {
          "title": "Request-Example: ",
          "content": "{\"name\":\"test1\",\"actor\":\"1,4,admin,\"}",
          "type": "json"
        }
      ]
    },
    "success": {
      "examples": [
        {
          "title": "Success-Response:",
          "content": "HTTP/1.1 200 OK",
          "type": "json"
        }
      ]
    },
    "error": {
      "examples": [
        {
          "title": "Error-Response:",
          "content": "HTTP/1.1 302 Found",
          "type": "json"
        },
        {
          "title": "Error-Response:",
          "content": "HTTP/1.1 409 Conflict",
          "type": "json"
        },
        {
          "title": "Error-Response:",
          "content": "HTTP/1.1 400 Bad Request",
          "type": "json"
        }
      ]
    },
    "version": "0.0.0",
    "filename": "src/main/java/com/rs/devplatform/controller/admin/GroupController.java",
    "groupTitle": "Admin_Group_Manager",
    "name": "PutAdminGroupGroupid"
  },
  {
    "type": "DELETE",
    "url": "/admin/db/table/{tableId}",
    "title": "5.删除数据表",
    "group": "Admin_Maintain_Table",
    "parameter": {
      "fields": {
        "Parameter": [
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "tableId",
            "description": "<p>Table ID</p>"
          },
          {
            "group": "Parameter",
            "type": "Integer",
            "optional": false,
            "field": "version",
            "description": "<p>Table version</p>"
          },
          {
            "group": "Parameter",
            "type": "Boolean",
            "optional": false,
            "field": "forceDrop",
            "description": "<p>是否删除物理表, true:删除物理表，false/null表示不删除</p>"
          }
        ]
      },
      "examples": [
        {
          "title": "Request-Example: ",
          "content": "{\n \"version\":1\n \"forceDrop\":true\n}",
          "type": "json"
        }
      ]
    },
    "success": {
      "examples": [
        {
          "title": "Success-Response:",
          "content": "HTTP/1.1 200 OK",
          "type": "json"
        }
      ]
    },
    "error": {
      "examples": [
        {
          "title": "Error-Response:",
          "content": "HTTP/1.1 400 Bad Request\n{\n  \"verror\":[\n    { \"errCode\": \"\", \"errorMsg\": \"version不允许为空\" },\n    { \"errCode\": \"\", \"errorMsg\": \"系统表不允许删除\" },\n    { \"errCode\": \"\", \"errorMsg\": \"记录不存在\" },\n   ]\n}",
          "type": "json"
        },
        {
          "title": "Error-Response:",
          "content": "HTTP/1.1 409 Conflict\n {\n   \"verror\":[\n       { \"errCode\": \"\", \"errorMsg\": \"删除失败\" },\n    ]\n }",
          "type": "json"
        }
      ]
    },
    "version": "0.0.0",
    "filename": "src/main/java/com/rs/devplatform/controller/admin/DBMaintainController.java",
    "groupTitle": "Admin_Maintain_Table",
    "name": "DeleteAdminDbTableTableid"
  },
  {
    "type": "GET",
    "url": "/admin/db",
    "title": "1.转到数据表维护页面",
    "group": "Admin_Maintain_Table",
    "success": {
      "fields": {
        "Success 200": [
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "html",
            "description": "<p>数据表维护页面</p>"
          }
        ]
      },
      "examples": [
        {
          "title": "页面命名:",
          "content": "{\"页面\":\"/manage/tablelist.html\"}",
          "type": "json"
        }
      ]
    },
    "version": "0.0.0",
    "filename": "src/main/java/com/rs/devplatform/controller/admin/DBMaintainController.java",
    "groupTitle": "Admin_Maintain_Table",
    "name": "GetAdminDb"
  },
  {
    "type": "GET",
    "url": "/admin/db/table/{tableId}",
    "title": "3.查询表详情",
    "group": "Admin_Maintain_Table",
    "success": {
      "fields": {
        "Success 200": [
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "id",
            "description": "<p>表id</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "tablename",
            "description": "<p>表名字</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "tabledescp",
            "description": "<p>表描述</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "pktype",
            "description": "<p>PK类型(UUID:32位唯一编码)</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "pkseq",
            "description": "<p>PK Seq</p>"
          },
          {
            "group": "Success 200",
            "type": "JSONArray",
            "optional": false,
            "field": "columns",
            "description": "<p>字段</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "columns.id",
            "description": "<p>字段id</p>"
          },
          {
            "group": "Success 200",
            "type": "Integer",
            "optional": false,
            "field": "columns.colSeq",
            "description": "<p>字段序号</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "columns.colName",
            "description": "<p>字段名称</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "columns.colMark",
            "description": "<p>字段备注</p>"
          },
          {
            "group": "Success 200",
            "type": "Integer",
            "optional": false,
            "field": "columns.colLen",
            "description": "<p>字段长度</p>"
          },
          {
            "group": "Success 200",
            "type": "Integer",
            "optional": false,
            "field": "columns.colDecpoint",
            "description": "<p>字段小数点位数</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "columns.colDefval",
            "description": "<p>字段默认值</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "columns.colType",
            "description": "<p>字段类型</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "columns.colIspk",
            "description": "<p>字段是否主键，Y:是主键</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "columns.colIsnull",
            "description": "<p>字段是否允许空值，Y:可为空</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "columns.version",
            "description": "<p>字段version</p>"
          },
          {
            "group": "Success 200",
            "type": "JSONArray",
            "optional": false,
            "field": "indexs",
            "description": "<p>索引</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "indexs.id",
            "description": "<p>索引id</p>"
          },
          {
            "group": "Success 200",
            "type": "Integer",
            "optional": false,
            "field": "indexs.indexSeq",
            "description": "<p>索引序号</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "indexs.colName",
            "description": "<p>索引字段</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "indexs.indexType",
            "description": "<p>索引类型</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "indexs.indexName",
            "description": "<p>索引名称</p>"
          }
        ]
      },
      "examples": [
        {
          "title": "Sample:",
          "content": "\tHTTP/1.1 200 OK\n{\n \"tabledescp\": \"tbl_ju98889888\",\n  \"columns\": [{\n    \"colSeq\": 1,\n    \"colName\": \"name_0\",\n    \"colIspk\": \"Y\",\n    \"deleted\": \"F\",\n    \"colMark\": \"mark_0\",\n    \"colDecpoint\": 0,\n    \"colIsnull\": \"\",\n    \"colType\": \"string\",\n    \"colLen\": 10,\n    \"id\": \"84573ec295a14426bf5147d33bce8c4f\",\n    \"colDefval\": \"\"\n  }],\n  \"indexs\": [{\n    \"colName\": \"name_0\",\n    \"indexType\": \"normal\",\n    \"indexName\": \"name_0_index\",\n    \"indexSeq\": 1,\n    \"id\": \"564144e1c378401a932aaf6430587e47\"\n  }],\n  \"id\": \"9cfa6836297d4c95924b7974a6eb8877\",\n  \"tablename\": \"tbl_ju9888\",\n  \"pktype\": \"UUID\",\n  \"version\": 1\n}",
          "type": "json"
        }
      ]
    },
    "version": "0.0.0",
    "filename": "src/main/java/com/rs/devplatform/controller/admin/DBMaintainController.java",
    "groupTitle": "Admin_Maintain_Table",
    "name": "GetAdminDbTableTableid"
  },
  {
    "type": "POST",
    "url": "/admin/db/table",
    "title": "4.新增数据表",
    "group": "Admin_Maintain_Table",
    "parameter": {
      "fields": {
        "Parameter": [
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "tablename",
            "description": "<p>表名字(必填)</p>"
          },
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "tabledescp",
            "description": "<p>表描述(必填)</p>"
          },
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "pktype",
            "description": "<p>主键类型(必填)</p>"
          },
          {
            "group": "Parameter",
            "type": "JSONArray",
            "optional": false,
            "field": "columns",
            "description": "<p>字段(必填)</p>"
          },
          {
            "group": "Parameter",
            "type": "Integer",
            "optional": false,
            "field": "columns.colSeq",
            "description": "<p>字段序号</p>"
          },
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "columns.colName",
            "description": "<p>字段名称</p>"
          },
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "columns.colMark",
            "description": "<p>字段备注</p>"
          },
          {
            "group": "Parameter",
            "type": "Integer",
            "optional": false,
            "field": "columns.coLen",
            "description": "<p>字段长度</p>"
          },
          {
            "group": "Parameter",
            "type": "Integer",
            "optional": false,
            "field": "columns.colDecpoint",
            "description": "<p>字段小数点位数</p>"
          },
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "columns.colDefval",
            "description": "<p>字段默认值</p>"
          },
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "columns.colType",
            "description": "<p>字段类型(string/int/double/date/decimal/text/blob)</p>"
          },
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "columns.colIspk",
            "description": "<p>字段是否主键，Y:是主键</p>"
          },
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "columns.colIsnull",
            "description": "<p>字段是否允许空值，Y:可为空</p>"
          },
          {
            "group": "Parameter",
            "type": "JSONArray",
            "optional": false,
            "field": "indexs",
            "description": "<p>索引</p>"
          },
          {
            "group": "Parameter",
            "type": "Integer",
            "optional": false,
            "field": "indexs.indexSeq",
            "description": "<p>索引序号</p>"
          },
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "indexs.colName",
            "description": "<p>索引字段</p>"
          },
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "indexs.indexType",
            "description": "<p>索引类型</p>"
          },
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "indexs.indexName",
            "description": "<p>索引名称</p>"
          }
        ]
      },
      "examples": [
        {
          "title": "Request-Example: ",
          "content": "\t{\n   \"tablename\": \"tbl_ju-8793\",\n   \"tabledescp\": \"tbl_ju-8793-8793\",\n   \"pktype\": \"UUID\",\n   \"columns\": [{\n     \"colSeq\": 1,\n     \"colName\": \"name_0\",\n     \"colIsnull\": \"\",\n     \"colLen\": 10,\n     \"colDecpoint\": 0,\n     \"colIspk\": \"Y\",\n     \"colMark\": \"mark_0\",\n     \"colType\": \"string\",\n     \"colDefval\": \"\"\n     },\n     {\n     \"colSeq\": 2,\n     \"colName\": \"name_1\",\n     \"colIsnull\": \"Y\",\n     \"colLen\": 11,\n     \"colDecpoint\": 0,\n     \"colIspk\": \"\",\n     \"colMark\": \"mark_1\",\n     \"colType\": \"string\",\n     \"colDefval\": \"\"\n     }],\n   \"indexs\": [{\n     \"colName\": \"name_0\",\n     \"indexType\": \"normal\",\n     \"indexName\": \"name_0_index\",\n     \"indexSeq\": 1\n     }]\n }",
          "type": "json"
        }
      ]
    },
    "success": {
      "fields": {
        "Success 200": [
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "id",
            "description": "<p>表id</p>"
          },
          {
            "group": "Success 200",
            "type": "Integer",
            "optional": false,
            "field": "version",
            "description": "<p>表version</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "isdbsyn",
            "description": "<p>是否同步数据库（Y：已经同步数据库，N:没有同步）</p>"
          }
        ]
      },
      "examples": [
        {
          "title": "Sample:",
          "content": "\tHTTP/1.1 200 OK\n{\"id\":\"1234567asdf\",\"version\":1, \"isdbsyn\":\"N\"}",
          "type": "json"
        }
      ]
    },
    "error": {
      "examples": [
        {
          "title": "Error-Response:",
          "content": "HTTP/1.1 400 Bad Request\n{\n  \"verrors\":[\n    { \"errCode\": \"pktype\", \"errorMsg\": \"主键类型不能为空\" },\n    { \"errCode\": \"tablename\", \"errorMsg\": \"表名不能为空\" },\n    { \"errCode\": \"tabledescp\", \"errorMsg\": \"表描述不能为空\" },\n    { \"errCode\": \"columns\", \"errorMsg\": \"字段不能为空\" }\n  ]\n}",
          "type": "json"
        },
        {
          "title": "Error-Response:",
          "content": "HTTP/1.1 302 Found",
          "type": "json"
        },
        {
          "title": "Error-Response:",
          "content": "HTTP/1.1 409 Conflict",
          "type": "json"
        }
      ]
    },
    "version": "0.0.0",
    "filename": "src/main/java/com/rs/devplatform/controller/admin/DBMaintainController.java",
    "groupTitle": "Admin_Maintain_Table",
    "name": "PostAdminDbTable"
  },
  {
    "type": "POST",
    "url": "/admin/db/tables",
    "title": "2.查询数据库表list",
    "group": "Admin_Maintain_Table",
    "parameter": {
      "fields": {
        "Parameter": [
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "tablename",
            "description": "<p>表名字(模糊查询)</p>"
          },
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "tabledescp",
            "description": "<p>表描述(模糊查询)</p>"
          },
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "isdbsyn",
            "description": "<p>是否同步数据库(Y/N/empty)</p>"
          }
        ]
      },
      "examples": [
        {
          "title": "Request-Example: ",
          "content": "{\"tablename\":\"test\", \"isdbsyn\":\"\", \"tabledescp\":\"\"}",
          "type": "json"
        }
      ]
    },
    "success": {
      "fields": {
        "Success 200": [
          {
            "group": "Success 200",
            "type": "Integer",
            "optional": false,
            "field": "total",
            "description": "<p>总记录条数</p>"
          },
          {
            "group": "Success 200",
            "type": "Integer",
            "optional": false,
            "field": "current",
            "description": "<p>当前页码</p>"
          },
          {
            "group": "Success 200",
            "type": "Integer",
            "optional": false,
            "field": "size",
            "description": "<p>每页显示记录条数</p>"
          },
          {
            "group": "Success 200",
            "type": "Integer",
            "optional": false,
            "field": "pages",
            "description": "<p>总页数</p>"
          },
          {
            "group": "Success 200",
            "type": "JsonArray",
            "optional": false,
            "field": "rows",
            "description": "<p>JSONArray</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "rows.id",
            "description": "<p>id主键</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "rows.tablename",
            "description": "<p>数据库表名</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "rows.tabledescp",
            "description": "<p>数据库表描述</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "rows.isdbsyn",
            "description": "<p>是否同步（Y:已同步，其他未同步）</p>"
          },
          {
            "group": "Success 200",
            "type": "Integer",
            "optional": false,
            "field": "rows.version",
            "description": "<p>版本</p>"
          }
        ]
      },
      "examples": [
        {
          "title": "Sample:",
          "content": "\tHTTP/1.1 200 OK\n\t{\n   \"total\":2,\n   \"records\":[\n     {\"tabledescp\":\"ju table 2674\", \"id\":\"ec9ec852205c4c288e320ff6f029fc6f\",\"isdbsyn\":\"N\",\"tablename\":\"tbl_ju_2674\",\"version\":1},\n     {\"tabledescp\":\"ju table 1970\", \"id\":\"fd5a58d8f09c41549f4bbd4131a5d990\",\"isdbsyn\":\"N\",\"tablename\":\"tbl_ju_1970\",\"version\":1}\n    ]\n  }",
          "type": "json"
        }
      ]
    },
    "version": "0.0.0",
    "filename": "src/main/java/com/rs/devplatform/controller/admin/DBMaintainController.java",
    "groupTitle": "Admin_Maintain_Table",
    "name": "PostAdminDbTables"
  },
  {
    "type": "PUT",
    "url": "/admin/db/table/{tableId}",
    "title": "7.更新表",
    "description": "<p>更新表改动到数据库（不同步）, 支持2种数据格式，1）表信息、字段索引信息，2）confirm的数据结构。 支持第二种，为了当用户确认的时候，发现问题，可以直接修改这个格式，然后提交</p>",
    "group": "Admin_Maintain_Table",
    "parameter": {
      "fields": {
        "Parameter": [
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "tabledescp",
            "description": "<p>表描述(必填)</p>"
          },
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "pktype",
            "description": "<p>PK类型(UUID:32位唯一编码)(必填)</p>"
          },
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "pkseq",
            "description": "<p>PK SEQ</p>"
          },
          {
            "group": "Parameter",
            "type": "Integer",
            "optional": false,
            "field": "version",
            "description": "<p>Version(必填)</p>"
          },
          {
            "group": "Parameter",
            "type": "JSONArray",
            "optional": false,
            "field": "columns",
            "description": "<p>字段</p>"
          },
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "columns.id",
            "description": "<p>字段id,新增的为空</p>"
          },
          {
            "group": "Parameter",
            "type": "Integer",
            "optional": false,
            "field": "columns.colSeq",
            "description": "<p>字段序号</p>"
          },
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "columns.colName",
            "description": "<p>字段名称</p>"
          },
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "columns.colMark",
            "description": "<p>字段备注</p>"
          },
          {
            "group": "Parameter",
            "type": "Integer",
            "optional": false,
            "field": "columns.colLen",
            "description": "<p>字段长度</p>"
          },
          {
            "group": "Parameter",
            "type": "Integer",
            "optional": false,
            "field": "columns.colDecpoint",
            "description": "<p>字段小数点位数</p>"
          },
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "columns.colDefval",
            "description": "<p>字段默认值</p>"
          },
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "columns.colType",
            "description": "<p>字段类型(string/int/double/date/decimal/text/blob)</p>"
          },
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "columns.colIspk",
            "description": "<p>字段是否主键，Y:是主键</p>"
          },
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "columns.colIsnull",
            "description": "<p>字段是否允许空值，Y:可为空</p>"
          },
          {
            "group": "Parameter",
            "type": "JSONArray",
            "optional": false,
            "field": "indexs",
            "description": "<p>索引</p>"
          },
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "indexs.id",
            "description": "<p>字段id,新增的为空</p>"
          },
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "indexs.indexName",
            "description": "<p>字段序号</p>"
          },
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "indexs.colName",
            "description": "<p>字段名称</p>"
          },
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "indexs.indexType",
            "description": "<p>字段备注</p>"
          },
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "indexs.indexSeq",
            "description": "<p>字段长度</p>"
          },
          {
            "group": "Parameter",
            "type": "<ul> <li></li> </ul>",
            "optional": false,
            "field": "ORORORORORORORORORORROROROROR",
            "description": "<p><strong><strong><strong><strong><strong>或者Confirm的数据结构</strong></strong></strong></strong></strong></p>"
          },
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "id",
            "description": "<p>表id</p>"
          },
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "tablename",
            "description": "<p>表名</p>"
          },
          {
            "group": "Parameter",
            "type": "JSONArray",
            "optional": false,
            "field": "tableChgs",
            "description": "<p>表改动</p>"
          },
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "tableChgs.name",
            "description": "<p>表改动属性（哪个属性改了）</p>"
          },
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "tableChgs.descp",
            "description": "<p>表改动属性描述（这个属性的中文描述）</p>"
          },
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "tableChgs.bf",
            "description": "<p>属性改动前的值</p>"
          },
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "tableChgs.af",
            "description": "<p>属性改动后的值</p>"
          },
          {
            "group": "Parameter",
            "type": "JSONArray",
            "optional": false,
            "field": "columnChgs",
            "description": "<p>表字段改动</p>"
          },
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "columnChgs.colName",
            "description": "<p>字段名</p>"
          },
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "columnChgs.id",
            "description": "<p>字段ID</p>"
          },
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "columnChgs.chgFlag",
            "description": "<p>改变类型，A：新增，U：更新，D:删除</p>"
          },
          {
            "group": "Parameter",
            "type": "JSONArray",
            "optional": false,
            "field": "columnChgs.chgs",
            "description": "<p>具体改变项</p>"
          },
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "columnChgs.chgs.name",
            "description": "<p>改动属性（哪个属性改了）</p>"
          },
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "columnChgs.chgs.descp",
            "description": "<p>表改动属性描述（这个属性的中文描述）</p>"
          },
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "columnChgs.chgs.bf",
            "description": "<p>属性改动前的值</p>"
          },
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "columnChgs.chgs.af",
            "description": "<p>属性改动后的值</p>"
          },
          {
            "group": "Parameter",
            "type": "JSONArray",
            "optional": false,
            "field": "indexChgs",
            "description": "<p>表索引改动</p>"
          },
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "indexChgs.indexName",
            "description": "<p>索引名</p>"
          },
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "indexChgs.id",
            "description": "<p>索引ID</p>"
          },
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "indexChgs.chgFlag",
            "description": "<p>改变类型，A：新增，U：更新，D:删除</p>"
          },
          {
            "group": "Parameter",
            "type": "JSONArray",
            "optional": false,
            "field": "indexChgs.chgs",
            "description": "<p>具体改变项</p>"
          },
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "indexChgs.chgs.name",
            "description": "<p>改动属性（哪个属性改了）</p>"
          },
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "indexChgs.chgs.descp",
            "description": "<p>改动属性描述（这个属性的中文描述）</p>"
          },
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "indexChgs.chgs.bf",
            "description": "<p>属性改动前的值</p>"
          },
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "indexChgs.chgs.af",
            "description": "<p>属性改动后的值</p>"
          }
        ]
      },
      "examples": [
        {
          "title": "Request-Example: ",
          "content": "参考6.确认表改动",
          "type": "json"
        }
      ]
    },
    "success": {
      "examples": [
        {
          "title": "Success-Response:",
          "content": "\tHTTP/1.1 200 OK\n {\n   \"version\":2\n }",
          "type": "json"
        }
      ]
    },
    "error": {
      "examples": [
        {
          "title": "Error-Response:",
          "content": "HTTP/1.1 400 Bad Request\n{\n  \"verrors\":[\n    { \"errCode\": \"pktype\", \"errorMsg\": \"主键类型不能为空\" },\n    { \"errCode\": \"tablename\", \"errorMsg\": \"表名不能为空\" },\n    { \"errCode\": \"tabledescp\", \"errorMsg\": \"表描述不能为空\" },\n    { \"errCode\": \"columns\", \"errorMsg\": \"字段不能为空\" }\n    { \"errCode\": \"\", \"errorMsg\": \"更新失败\" }\n  ]\n}",
          "type": "json"
        }
      ]
    },
    "version": "0.0.0",
    "filename": "src/main/java/com/rs/devplatform/controller/admin/DBMaintainController.java",
    "groupTitle": "Admin_Maintain_Table",
    "name": "PutAdminDbTableTableid"
  },
  {
    "type": "PUT",
    "url": "/admin/db/table/{tableId}/confirm",
    "title": "6.确认表改动",
    "description": "<p>给用户确认一下表改动</p>",
    "group": "Admin_Maintain_Table",
    "parameter": {
      "fields": {
        "Parameter": [
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "tabledescp",
            "description": "<p>表描述(必填)</p>"
          },
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "pktype",
            "description": "<p>PK类型(UUID:32位唯一编码)(必填)</p>"
          },
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "pkseq",
            "description": "<p>PK SEQ</p>"
          },
          {
            "group": "Parameter",
            "type": "Integer",
            "optional": false,
            "field": "version",
            "description": "<p>Version(必填)</p>"
          },
          {
            "group": "Parameter",
            "type": "JSONArray",
            "optional": false,
            "field": "columns",
            "description": "<p>字段</p>"
          },
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "columns.id",
            "description": "<p>字段id,新增的为空</p>"
          },
          {
            "group": "Parameter",
            "type": "Integer",
            "optional": false,
            "field": "columns.colSeq",
            "description": "<p>字段序号</p>"
          },
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "columns.colName",
            "description": "<p>字段名称</p>"
          },
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "columns.colMark",
            "description": "<p>字段备注</p>"
          },
          {
            "group": "Parameter",
            "type": "Integer",
            "optional": false,
            "field": "columns.colLen",
            "description": "<p>字段长度</p>"
          },
          {
            "group": "Parameter",
            "type": "Integer",
            "optional": false,
            "field": "columns.colDecpoint",
            "description": "<p>字段小数点位数</p>"
          },
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "columns.colDefval",
            "description": "<p>字段默认值</p>"
          },
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "columns.colType",
            "description": "<p>字段类型(string/int/double/date/decimal/text/blob)</p>"
          },
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "columns.colIspk",
            "description": "<p>字段是否主键，Y:是主键</p>"
          },
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "columns.colIsnull",
            "description": "<p>字段是否允许空值，Y:可为空</p>"
          },
          {
            "group": "Parameter",
            "type": "JSONArray",
            "optional": false,
            "field": "indexs",
            "description": "<p>索引</p>"
          },
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "indexs.id",
            "description": "<p>字段id,新增的为空</p>"
          },
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "indexs.indexName",
            "description": "<p>字段序号</p>"
          },
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "indexs.colName",
            "description": "<p>字段名称</p>"
          },
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "indexs.indexType",
            "description": "<p>字段备注</p>"
          },
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "indexs.indexSeq",
            "description": "<p>字段长度</p>"
          }
        ]
      },
      "examples": [
        {
          "title": "Request-Example: ",
          "content": "{\n\"tablename\": \"tbl_ju-6846\",\n\"pktype\": \"UUID\",\n\"version\": 1,\n\"tabledescp\": \"upd tabledescp\",\n\"columns\": [{\n \"colDecpoint\": 0,\n \"colDefval\": \"abc\",\n \"colIsnull\": \"\",\n \"colIspk\": \"Y\",\n \"colLen\": 10,\n \"colMark\": \"mark_0\",\n \"colName\": \"name_0\",\n \"colSeq\": 1,\n \"colType\": \"string\",\n \"deleted\": \"F\",\n \"id\": \"f7f7a87f19174ba98da0c5084f02123e\"\n},\n{\n \"colSeq\": 6,\n \"colName\": \"name_0\",\n \"colIspk\": \"\",\n \"colMark\": \"mark_0\",\n \"colDecpoint\": 0,\n \"colIsnull\": \"\",\n \"colType\": \"string\",\n \"colLen\": 10,\n \"colDefval\": \"\"\n}],\n\"indexs\": [{\n \"colName\": \"name_0\",\n \"id\": \"cc98be1af0974ff6afa69e08adaea23f\",\n \"indexName\": \"name_0_index\",\n \"indexSeq\": 1,\n \"indexType\": \"normal\"\n},\n{\n \"colName\": \"name_2\",\n \"indexName\": \"name_2_index_new\",\n \"indexSeq\": 3,\n \"indexType\": \"normal\"\n}],\n}",
          "type": "json"
        }
      ]
    },
    "success": {
      "fields": {
        "Success 200": [
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "id",
            "description": "<p>表id</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "tablename",
            "description": "<p>表名</p>"
          },
          {
            "group": "Success 200",
            "type": "Integer",
            "optional": false,
            "field": "version",
            "description": "<p>表version</p>"
          },
          {
            "group": "Success 200",
            "type": "JSONArray",
            "optional": false,
            "field": "tableChgs",
            "description": "<p>表改动</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "tableChgs.name",
            "description": "<p>表改动属性（哪个属性改了）</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "tableChgs.descp",
            "description": "<p>表改动属性描述（这个属性的中文描述）</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "tableChgs.bf",
            "description": "<p>属性改动前的值</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "tableChgs.af",
            "description": "<p>属性改动后的值</p>"
          },
          {
            "group": "Success 200",
            "type": "JSONArray",
            "optional": false,
            "field": "columnChgs",
            "description": "<p>表字段改动</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "columnChgs.colName",
            "description": "<p>字段名</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "columnChgs.id",
            "description": "<p>字段ID</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "columnChgs.chgFlag",
            "description": "<p>改变类型，A：新增，U：更新，D:删除</p>"
          },
          {
            "group": "Success 200",
            "type": "JSONArray",
            "optional": false,
            "field": "columnChgs.chgs",
            "description": "<p>具体改变项</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "columnChgs.chgs.name",
            "description": "<p>改动属性（哪个属性改了）</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "columnChgs.chgs.descp",
            "description": "<p>表改动属性描述（这个属性的中文描述）</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "columnChgs.chgs.bf",
            "description": "<p>属性改动前的值</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "columnChgs.chgs.af",
            "description": "<p>属性改动后的值</p>"
          },
          {
            "group": "Success 200",
            "type": "JSONArray",
            "optional": false,
            "field": "indexChgs",
            "description": "<p>表索引改动</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "indexChgs.indexName",
            "description": "<p>索引名</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "indexChgs.id",
            "description": "<p>索引ID</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "indexChgs.chgFlag",
            "description": "<p>改变类型，A：新增，U：更新，D:删除</p>"
          },
          {
            "group": "Success 200",
            "type": "JSONArray",
            "optional": false,
            "field": "indexChgs.chgs",
            "description": "<p>具体改变项</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "indexChgs.chgs.name",
            "description": "<p>改动属性（哪个属性改了）</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "indexChgs.chgs.descp",
            "description": "<p>改动属性描述（这个属性的中文描述）</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "indexChgs.chgs.bf",
            "description": "<p>属性改动前的值</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "indexChgs.chgs.af",
            "description": "<p>属性改动后的值</p>"
          }
        ]
      },
      "examples": [
        {
          "title": "Success-Response:",
          "content": "\tHTTP/1.1 200 OK\n{\n \"id\": \"57d936ff2bff48f9860914811aaf9b69\",\n \"tablename\": \"tbl_ju8521\",\n \"version\":1,\n \"chgFlag\":\"U\",\n \"tableChgs\": [\n   {\"bf\": \"tbl_ju85218521\",\"af\": \"upd tabledescp\",\"name\": \"tabledescp\",\"descp\": \"表描述\"}\n ],\n \"indexChgs\": [\n  {\n   \"chgFlag\": \"A\",\"indexName\": \"name_2_index_new\",\"id\": \"\",\n   \"chgs\": [\n    {\"bf\": \"\", \"af\": \"name_2_index_new\", \"name\": \"indexName\",\"descp\": \"索引名称\"},\n    {\"bf\": \"\",\"af\": \"name_2\",\"name\": \"colName\",\"descp\": \"索引栏位\"},\n    {\"bf\": \"\",\"af\": \"normal\",\"name\": \"indexType\",\"descp\": \"索引类型\"}\n   ]\n  },\n  {\n   \"chgFlag\": \"U\",\"indexName\": \"name_0_index\",\"id\": \"12b94c85392c4affb73653b18f77e29e\",\n   \"chgs\": [\n    {\"bf\": \"ju8521_name_0\",\"af\": \"name_0\",\"name\": \"colName\",\"descp\": \"索引栏位\"}\n   ]\n  },\n  {\n   \"chgFlag\": \"D\",\"indexName\": \"name_1_index\",\"id\": \"7cf20e394117489fa83dc15a326e1a89\",\n   \"chgs\":[\n    {\"bf\": \"name_1_index\",\"af\": \"\",\"name\": \"indexName\",\"descp\": \"索引名称\" },\n    {\"bf\": \"ju8521_name_1\", \"af\": \"\", \"name\": \"colName\", \"descp\": \"索引栏位\"},\n    {\"bf\": \"unique\", \"af\": \"\", \"name\": \"indexType\", \"descp\": \"索引类型\"}\n   ]\n  }\n ],\n \"columnChgs\": [\n  {\n   \"colName\": \"name_0\",  \"chgFlag\": \"A\",  \"id\": \"\",\n   \"chgs\": [\n    {\"bf\": \"\",\"af\": \"name_0\",\"name\": \"colName\",\"descp\": \"字段名称\"},\n    {\"bf\": \"\",\"af\": \"mark_0\",\"name\": \"colMark\",\"descp\": \"字段备注\"},\n    {\"bf\": \"\",\"af\": 10,\"name\": \"colLen\",\"descp\": \"字段长度\"},\n    {\"bf\": \"\",\"af\": 0,\"name\": \"colDecpoint\",\"descp\": \"小数点\"},\n    {\"bf\": \"\",\"af\": \"\",\"name\": \"colDefval\",\"descp\": \"默认值\"},\n    {\"bf\": \"\",\"af\": \"string\",\"name\": \"colType\",\"descp\": \"字段类型\"},\n    {\"bf\": \"\",\"af\": \"\",\"name\": \"colIspk\",\"descp\": \"主键\"},\n    {\"bf\": \"\",\"af\": \"\",\"name\": \"colIsnull\",\"descp\": \"允许空值\"}\n   ]\n  },\n  {\n   \"colName\": \"ju8521_name_0\",\"chgFlag\": \"U\",\"id\": \"620b9d891b4141d8868f5d3c179e8855\",\n   \"chgs\": [\n     {\"bf\": \"ju8521_name_0\",\"af\": \"name_0\",\"name\": \"colName\",\"descp\": \"字段名称\"},\n\t    {\"bf\": \"\",\"af\": \"abc\",\"name\": \"colDefval\",\"descp\": \"默认值\"}\n   ]\n  },\n  {\n   \"colName\": \"ju8521_name_3\",\"chgFlag\": \"D\",\"id\": \"b3904af705954fd3ab36c88f15109798\",\n   \"chgs\": [\n     {\"bf\": \"ju8521_name_3\",\"af\": \"\",\"name\": \"colName\",\"descp\": \"字段名称\"},\n     {\"bf\": \"mark_3\",\"af\": \"\",\"name\": \"colMark\",\"descp\": \"字段备注\"},\n\t    {\"bf\": 13,\"af\": \"\",\"name\": \"colLen\",\"descp\": \"字段长度\"},\n\t    {\"bf\": 0,\"af\": \"\",\"name\": \"colDecpoint\",\"descp\": \"小数点\"},\n\t    {\"bf\": \"\",\"af\": \"\",\"name\": \"colDefval\",\"descp\": \"默认值\"},\n\t    {\"bf\": \"string\",\"af\": \"\",\"name\": \"colType\",\"descp\": \"字段类型\"},\n\t    {\"bf\": \"\",\"af\": \"\",\"name\": \"colIspk\",\"descp\": \"主键\"},\n\t    {\"bf\": \"Y\",\"af\": \"\",\"name\": \"colIsnull\",\"descp\": \"允许空值\"}\n   ]\n  },\n ]\n}",
          "type": "json"
        }
      ]
    },
    "version": "0.0.0",
    "filename": "src/main/java/com/rs/devplatform/controller/admin/DBMaintainController.java",
    "groupTitle": "Admin_Maintain_Table",
    "name": "PutAdminDbTableTableidConfirm"
  },
  {
    "type": "PUT",
    "url": "/admin/db/table/{tableId}/sync",
    "title": "8.同步表改动到数据库",
    "group": "Admin_Maintain_Table",
    "parameter": {
      "fields": {
        "Parameter": [
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "tableId",
            "description": "<p>Table ID</p>"
          },
          {
            "group": "Parameter",
            "type": "Integer",
            "optional": false,
            "field": "version",
            "description": "<p>Table version</p>"
          },
          {
            "group": "Parameter",
            "type": "Boolean",
            "optional": false,
            "field": "forceDrop",
            "description": "<p>是否强制同步:true/false/不传(默认false:不强制同步,同步可能会失败)</p>"
          }
        ]
      },
      "examples": [
        {
          "title": "Request-Example: ",
          "content": "{\"version\":1, \"forceDrop\": false}",
          "type": "json"
        }
      ]
    },
    "success": {
      "examples": [
        {
          "title": "Success-Response:",
          "content": "HTTP/1.1 200 OK\n{ \"version\":2 }",
          "type": "json"
        }
      ]
    },
    "error": {
      "examples": [
        {
          "title": "Error-Response:",
          "content": "HTTP/1.1 400 Bad Request\n{\n  \"verrors\":[\n    { \"errCode\": \"\", \"errorMsg\": \"version不允许为空\" },\n    { \"errCode\": \"\", \"errorMsg\": \"记录不存在或者已经被更新\" },\n    { \"errCode\": \"\", \"errorMsg\": \"已经同步过了\" },\n    { \"errCode\": \"\", \"errorMsg\": \"同步失败\" }\n    { \"errCode\": \"\", \"errorMsg\": \"同步失败,请保存好数据,并尝试强制同步\" }\n  ]\n}",
          "type": "json"
        },
        {
          "title": "Error-Response:",
          "content": "HTTP/1.1 400 Bad Request",
          "type": "json"
        },
        {
          "title": "Error-Response:",
          "content": "HTTP/1.1 302 Found",
          "type": "json"
        },
        {
          "title": "Error-Response:",
          "content": "HTTP/1.1 409 Conflict",
          "type": "json"
        }
      ]
    },
    "version": "0.0.0",
    "filename": "src/main/java/com/rs/devplatform/controller/admin/DBMaintainController.java",
    "groupTitle": "Admin_Maintain_Table",
    "name": "PutAdminDbTableTableidSync"
  },
  {
    "type": "DELETE",
    "url": "/admin/menu/{menuId}",
    "title": "删除Menu",
    "group": "Admin_Menu",
    "parameter": {
      "examples": [
        {
          "title": "Request-Example: ",
          "content": "DELETE /admin/menu/1",
          "type": "json"
        }
      ]
    },
    "success": {
      "examples": [
        {
          "title": "Success-Response:",
          "content": "HTTP/1.1 200 OK",
          "type": "json"
        }
      ]
    },
    "version": "0.0.0",
    "filename": "src/main/java/com/rs/devplatform/controller/admin/MenuController.java",
    "groupTitle": "Admin_Menu",
    "name": "DeleteAdminMenuMenuid"
  },
  {
    "type": "GET",
    "url": "/admin/menu",
    "title": "转到Menu管理页面",
    "group": "Admin_Menu",
    "success": {
      "fields": {
        "Success 200": [
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "html",
            "description": "<p>对应数据字典管理页面</p>"
          }
        ]
      },
      "examples": [
        {
          "title": "页面命名:",
          "content": "{\"页面\":\"/manage/menu.html\"}",
          "type": "json"
        }
      ]
    },
    "version": "0.0.0",
    "filename": "src/main/java/com/rs/devplatform/controller/admin/MenuController.java",
    "groupTitle": "Admin_Menu",
    "name": "GetAdminMenu"
  },
  {
    "type": "GET",
    "url": "/admin/menu/list",
    "title": "获取Menu List",
    "description": "<p>所有有权限的菜单的显示</p>",
    "group": "Admin_Menu",
    "success": {
      "fields": {
        "Success 200": [
          {
            "group": "Success 200",
            "type": "JSONArray",
            "optional": false,
            "field": "rows",
            "description": "<p>返回Menu</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "rows.id",
            "description": "<p>菜单ID</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "rows.name",
            "description": "<p>菜单名称</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "rows.url",
            "description": "<p>菜单url地址</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "rows.pid",
            "description": "<p>菜单父节点ID</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "rows.type",
            "description": "<p>菜单类型（A——文件夹类型，B——url节点,C——访问类型）</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "rows.funcId",
            "description": "<p>功能组ID</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "rows.icon",
            "description": "<p>菜单图标</p>"
          }
        ]
      },
      "examples": [
        {
          "title": "Success-Response:",
          "content": "{\n\t\t\"total\": 2,\n\t\t\"rows\": [\n\t\t\t{\n\t\t\t\t\"id\": \"c5a298c0bcb84733aa68413a7da9715c\",\n\t\t\t\t\"name\": \"测试文件夹\",\n\t\t\t\t\"url\": \"\",\n\t\t\t\t\"pid\": \"\",\n\t\t\t\t\"type\": \"A\",\n\t\t\t\t\"funcId\": null,\n\t\t\t\t\"version\": null\n\t\t\t\t\"sequence\":1\n\t\t\t\t\"icon\":\"1.jpg\"\n\t\t\t},\n\t\t\t{\n\t\t\t\t\"id\": \"fd0ab70a24ab44219d4ebba5f8117ad4\",\n\t\t\t\t\"name\": \"dddsa\",\n\t\t\t\t\"url\": \"/test\",\n\t\t\t\t\"pid\": \"c5a298c0bcb84733aa68413a7da9715c\",\n\t\t\t\t\"type\": \"B\",\n\t\t\t\t\"funcId\": null,\n\t\t\t\t\"version\": 1,\n\t\t\t\t\"sequence\":2,\n\t\t\t\t\"icon\":null\n\t\t\t}\n\t\t]\n\t}",
          "type": "json"
        }
      ]
    },
    "version": "0.0.0",
    "filename": "src/main/java/com/rs/devplatform/controller/admin/MenuController.java",
    "groupTitle": "Admin_Menu",
    "name": "GetAdminMenuList"
  },
  {
    "type": "GET",
    "url": "/admin/menu/sidelist",
    "title": "获取左侧列表Menu List",
    "description": "<p>左边栏的菜单显示（不显示访问类型）</p>",
    "group": "Admin_Menu",
    "success": {
      "fields": {
        "Success 200": [
          {
            "group": "Success 200",
            "type": "JSONArray",
            "optional": false,
            "field": "rows",
            "description": "<p>返回Menu</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "rows.id",
            "description": "<p>菜单ID</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "rows.name",
            "description": "<p>菜单名称</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "rows.url",
            "description": "<p>菜单url地址</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "rows.pid",
            "description": "<p>菜单父节点ID</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "rows.type",
            "description": "<p>菜单类型（A——文件夹类型，B——url节点）</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "rows.funcId",
            "description": "<p>功能组ID</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "rows.icon",
            "description": "<p>菜单图标</p>"
          }
        ]
      },
      "examples": [
        {
          "title": "Success-Response:",
          "content": "{\n\t\t\"total\": 2,\n\t\t\"rows\": [\n\t\t\t{\n\t\t\t\t\"id\": \"c5a298c0bcb84733aa68413a7da9715c\",\n\t\t\t\t\"name\": \"测试文件夹\",\n\t\t\t\t\"url\": \"\",\n\t\t\t\t\"pid\": \"\",\n\t\t\t\t\"type\": \"A\",\n\t\t\t\t\"funcId\": null,\n\t\t\t\t\"version\": null\n\t\t\t\t\"sequence\":1\n\t\t\t\t\"icon\":\"1.jpg\"\n\t\t\t},\n\t\t\t{\n\t\t\t\t\"id\": \"fd0ab70a24ab44219d4ebba5f8117ad4\",\n\t\t\t\t\"name\": \"dddsa\",\n\t\t\t\t\"url\": \"/test\",\n\t\t\t\t\"pid\": \"c5a298c0bcb84733aa68413a7da9715c\",\n\t\t\t\t\"type\": \"B\",\n\t\t\t\t\"funcId\": null,\n\t\t\t\t\"version\": 1,\n\t\t\t\t\"sequence\":2,\n\t\t\t\t\"icon\":null\n\t\t\t}\n\t\t]\n\t}",
          "type": "json"
        }
      ]
    },
    "version": "0.0.0",
    "filename": "src/main/java/com/rs/devplatform/controller/admin/MenuController.java",
    "groupTitle": "Admin_Menu",
    "name": "GetAdminMenuSidelist"
  },
  {
    "type": "POST",
    "url": "/admin/menu",
    "title": "增加Menu",
    "description": "<p>type 菜单类型为A时，不填url,不填funcid;菜单类型为B时，必填url</p>",
    "group": "Admin_Menu",
    "parameter": {
      "fields": {
        "Parameter": [
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "id",
            "description": "<p>菜单ID（八位数字）</p>"
          },
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "name",
            "description": "<p>菜单名称</p>"
          },
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "url",
            "description": "<p>菜单Url（菜单类型为B时必填，为A是不能填）</p>"
          },
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "type",
            "description": "<p>菜单类型（A——文件夹类型，B——url节点,C——访问类型）</p>"
          },
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "pid",
            "description": "<p>菜单父节点ID</p>"
          },
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "icon",
            "description": "<p>菜单图标</p>"
          }
        ]
      },
      "examples": [
        {
          "title": "Request-Example: ",
          "content": "{\"id\":\"12345678\", \"name\":\"新建菜单\", \"pid\":\"1c5f8cdf97af43cda857e4cbcb9a9417\", \"url\":\"/admin/new\", \"type\":\"B\", \"icon\": \"a.png\"}",
          "type": "json"
        }
      ]
    },
    "success": {
      "fields": {
        "Success 200": [
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "id",
            "description": "<p>Menu id</p>"
          }
        ]
      },
      "examples": [
        {
          "title": "Success-Response:",
          "content": "HTTP/1.1 200 OK\n{\"id\":\"0f077145cc894f7990387c9458091e1b\"}",
          "type": "json"
        }
      ]
    },
    "error": {
      "examples": [
        {
          "title": "Error-Response:",
          "content": "HTTP/1.1 400 Bad Request",
          "type": "json"
        },
        {
          "title": "Error-Response:",
          "content": "HTTP/1.1 302 Found",
          "type": "json"
        },
        {
          "title": "Error-Response:",
          "content": "HTTP/1.1 409 Conflict",
          "type": "json"
        }
      ]
    },
    "version": "0.0.0",
    "filename": "src/main/java/com/rs/devplatform/controller/admin/MenuController.java",
    "groupTitle": "Admin_Menu",
    "name": "PostAdminMenu"
  },
  {
    "type": "PUT",
    "url": "/admin/menu/{menuId}",
    "title": "修改Menu",
    "description": "<p>type 菜单类型为A时，不填url,不填funcid;菜单类型为B时，必填url</p>",
    "group": "Admin_Menu",
    "parameter": {
      "fields": {
        "Parameter": [
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "id",
            "description": "<p>菜单ID（八位数字）</p>"
          },
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "name",
            "description": "<p>菜单名称</p>"
          },
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "url",
            "description": "<p>菜单Url（菜单类型为B时必填，为A是不能填）</p>"
          },
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "type",
            "description": "<p>菜单类型（A——文件夹类型，B——url节点,C——访问类型）</p>"
          },
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "pid",
            "description": "<p>菜单父节点ID</p>"
          },
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "icon",
            "description": "<p>菜单图标</p>"
          }
        ]
      },
      "examples": [
        {
          "title": "Request-Example: ",
          "content": "{\"id\":\"12345678\", \"name\":\"test2\",\"pid\":\"0e6f6534d9424f569ffb5276ad939ce6\", \"url\":\"/test1/2\", \"type\":\"B\", \"icon\":\"1.jpg\"}",
          "type": "json"
        }
      ]
    },
    "success": {
      "fields": {
        "Success 200": [
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "id",
            "description": "<p>Menu id</p>"
          }
        ]
      },
      "examples": [
        {
          "title": "Success-Response:",
          "content": "HTTP/1.1 200 OK\n{\"id\":\"0f077145cc894f7990387c9458091e1b\"}",
          "type": "json"
        }
      ]
    },
    "error": {
      "examples": [
        {
          "title": "Error-Response:",
          "content": "HTTP/1.1 400 Bad Request",
          "type": "json"
        },
        {
          "title": "Error-Response:",
          "content": "HTTP/1.1 302 Found",
          "type": "json"
        },
        {
          "title": "Error-Response:",
          "content": "HTTP/1.1 409 Conflict",
          "type": "json"
        }
      ]
    },
    "version": "0.0.0",
    "filename": "src/main/java/com/rs/devplatform/controller/admin/MenuController.java",
    "groupTitle": "Admin_Menu",
    "name": "PutAdminMenuMenuid"
  },
  {
    "type": "DELETE",
    "url": "/admin/project/{pjId}",
    "title": "删除项目",
    "group": "Admin_Project_Manager",
    "parameter": {
      "fields": {
        "Parameter": [
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "id",
            "description": "<p>项目ID</p>"
          },
          {
            "group": "Parameter",
            "type": "Integer",
            "optional": false,
            "field": "version",
            "description": "<p>项目version</p>"
          }
        ]
      },
      "examples": [
        {
          "title": "Request-Example: ",
          "content": "{\"version\":1}",
          "type": "json"
        }
      ]
    },
    "success": {
      "examples": [
        {
          "title": "Success-Response:",
          "content": "HTTP/1.1 200 OK",
          "type": "json"
        }
      ]
    },
    "version": "0.0.0",
    "filename": "src/main/java/com/rs/devplatform/controller/admin/ProjectController.java",
    "groupTitle": "Admin_Project_Manager",
    "name": "DeleteAdminProjectPjid"
  },
  {
    "type": "DELETE",
    "url": "/admin/project/{pjId}/managers",
    "title": "项目删除managers",
    "group": "Admin_Project_Manager",
    "parameter": {
      "fields": {
        "Parameter": [
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "pjId",
            "description": "<p>项目ID</p>"
          },
          {
            "group": "Parameter",
            "type": "JSONArray",
            "optional": false,
            "field": "managers",
            "description": "<p>项目manager ids</p>"
          },
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "managers._",
            "description": "<p>项目manager id</p>"
          }
        ]
      },
      "examples": [
        {
          "title": "Request-Example: ",
          "content": "{\n\t\"managers\":[ \"m1\",\"m2\"]\n}",
          "type": "json"
        }
      ]
    },
    "success": {
      "examples": [
        {
          "title": "Success-Response:",
          "content": "HTTP/1.1 200 OK\n{\"id\":\"12345\", \"version\":2}",
          "type": "json"
        }
      ]
    },
    "error": {
      "examples": [
        {
          "title": "Error-Response:",
          "content": "HTTP/1.1 400 Bad Request",
          "type": "json"
        },
        {
          "title": "Error-Response:",
          "content": "HTTP/1.1 302 Found",
          "type": "json"
        },
        {
          "title": "Error-Response:",
          "content": "HTTP/1.1 409 Conflict",
          "type": "json"
        }
      ]
    },
    "version": "0.0.0",
    "filename": "src/main/java/com/rs/devplatform/controller/admin/ProjectController.java",
    "groupTitle": "Admin_Project_Manager",
    "name": "DeleteAdminProjectPjidManagers"
  },
  {
    "type": "GET",
    "url": "/admin/project",
    "title": "转到项目管理页面",
    "group": "Admin_Project_Manager",
    "success": {
      "fields": {
        "Success 200": [
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "html",
            "description": "<p>对应项目管理页面</p>"
          }
        ]
      },
      "examples": [
        {
          "title": "页面命名:",
          "content": "{\"页面\":\"manage/projects.html\"}",
          "type": "json"
        }
      ]
    },
    "version": "0.0.0",
    "filename": "src/main/java/com/rs/devplatform/controller/admin/ProjectController.java",
    "groupTitle": "Admin_Project_Manager",
    "name": "GetAdminProject"
  },
  {
    "type": "GET",
    "url": "/admin/project/{pjId}",
    "title": "查询项目详情",
    "group": "Admin_Project_Manager",
    "parameter": {
      "fields": {
        "Parameter": [
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "id",
            "description": "<p>项目ID</p>"
          }
        ]
      },
      "examples": [
        {
          "title": "Request-Example: ",
          "content": "GET /admin/project/abc",
          "type": "json"
        }
      ]
    },
    "success": {
      "fields": {
        "Success 200": [
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "id",
            "description": "<p>返回项目id</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "name",
            "description": "<p>返回项目name</p>"
          },
          {
            "group": "Success 200",
            "type": "Integer",
            "optional": false,
            "field": "version",
            "description": "<p>返回项目version</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "init",
            "description": "<p>返回项目是否已经初始化（Y:表示已经初始化，其他表示未初始化）</p>"
          },
          {
            "group": "Success 200",
            "type": "JSONArray",
            "optional": false,
            "field": "resList",
            "description": "<p>返回项目资源</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "resList.dbType",
            "description": "<p>项目资源dbType</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "resList.dbUser",
            "description": "<p>项目资源dbUser</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "resList.dbPwd",
            "description": "<p>项目资源dbPwd</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "resList.dbUrl",
            "description": "<p>项目资源dbUrl</p>"
          }
        ]
      },
      "examples": [
        {
          "title": "Success-Response:",
          "content": "HTTP/1.1 200 OK\n{\n\t\t\t\"init\": \"Y\",\n\t\t\t\"resList\": [{\n\t\t\t\t\"pjId\": \"a405ce0f3d934caea84ed190a7eededb\",\n\t\t\t\t\"dbPwd\": \"\",\n\t\t\t\t\"dbUser\": \"root\",\n\t\t\t\t\"dbType\": \"mysql\",\n\t\t\t\t\"id\": \"c12e146c8f1949a3b444adf89ecb63d1\",\n\t\t\t\t\"version\": 2,\n\t\t\t\t\"dbUrl\": \"127.0.0.1:3306/rs_test\"\n\t\t\t}],\n\t\t\t\"name\": \"ju-pj--21\",\n\t\t\t\"id\": \"a405ce0f3d934caea84ed190a7eededb\",\n\t\t\t\"version\": 6\n\t\t}",
          "type": "json"
        }
      ]
    },
    "version": "0.0.0",
    "filename": "src/main/java/com/rs/devplatform/controller/admin/ProjectController.java",
    "groupTitle": "Admin_Project_Manager",
    "name": "GetAdminProjectPjid"
  },
  {
    "type": "GET",
    "url": "/admin/project/{pjId}/managers",
    "title": "获取项目分配的managers",
    "group": "Admin_Project_Manager",
    "parameter": {
      "fields": {
        "Parameter": [
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "pjId",
            "description": "<p>项目ID</p>"
          }
        ]
      },
      "examples": [
        {
          "title": "Request-Example: ",
          "content": "{\"预留\":\"查询过滤\"}",
          "type": "json"
        }
      ]
    },
    "success": {
      "fields": {
        "Success 200": [
          {
            "group": "Success 200",
            "type": "JSONArray",
            "optional": false,
            "field": "records",
            "description": "<p>返回项目Records</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "records.id",
            "description": "<p>项目ManagerID</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "records.name",
            "description": "<p>项目ManagerName</p>"
          }
        ]
      },
      "examples": [
        {
          "title": "Success-Response:",
          "content": "{\n\t\"records\":[\n\t\t{\"name\":\"test-77\",\"id\":\"0f4934679ab24a1a80316a801f56e1b8\"},\n\t\t{\"name\":\"test-198\",\"id\":\"088b1c492e584cda89950de98965fdbb\"}\n\t],\n\t\"total\":2\n}",
          "type": "json"
        }
      ]
    },
    "version": "0.0.0",
    "filename": "src/main/java/com/rs/devplatform/controller/admin/ProjectController.java",
    "groupTitle": "Admin_Project_Manager",
    "name": "GetAdminProjectPjidManagers"
  },
  {
    "type": "POST",
    "url": "/admin/project",
    "title": "增加项目",
    "group": "Admin_Project_Manager",
    "parameter": {
      "fields": {
        "Parameter": [
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "name",
            "description": "<p>项目名</p>"
          },
          {
            "group": "Parameter",
            "type": "JSONObject",
            "optional": false,
            "field": "db",
            "description": "<p>项目db(不是必须的)</p>"
          },
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "db.dbType",
            "description": "<p>项目对应数据库类型：oracle,mysql</p>"
          },
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "db.dbUrl",
            "description": "<p>项目对应数据库地址</p>"
          },
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "db.dbUser",
            "description": "<p>项目对应数据库用户名</p>"
          },
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "db.dbPwd",
            "description": "<p>项目对应数据库密码</p>"
          }
        ]
      },
      "examples": [
        {
          "title": "Request-Example: ",
          "content": "{\"name\":\"测试项目1：只建项目，不建DB资源\"}\n{\"name\":\"测试项目2\",\"db\":{\"dbPwd\":\"123456\",\"dbUser\":\"root\",\"dbType\":\"mysql\",\"dbUrl\":\"192.168.10.64:3306/test_db930\"}}\n{\"name\":\"测试项目3\",\"db\":{\"dbPwd\":\"123456\",\"dbUser\":\"root\",\"dbType\":\"oracle\",\"dbUrl\":\"192.168.10.64:1521/test_db930\"}}",
          "type": "json"
        }
      ]
    },
    "success": {
      "fields": {
        "Success 200": [
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "id",
            "description": "<p>返回项目id</p>"
          },
          {
            "group": "Success 200",
            "type": "Integer",
            "optional": false,
            "field": "version",
            "description": "<p>返回项目version</p>"
          }
        ]
      },
      "examples": [
        {
          "title": "Success-Response:",
          "content": "HTTP/1.1 200 OK\n{\"id\":\"0f077145cc894f7990387c9458091e1b\", \"version\":1}",
          "type": "json"
        }
      ]
    },
    "error": {
      "examples": [
        {
          "title": "Error-Response:",
          "content": "HTTP/1.1 400 Bad Request",
          "type": "json"
        },
        {
          "title": "Error-Response:",
          "content": "HTTP/1.1 302 Found",
          "type": "json"
        },
        {
          "title": "Error-Response:",
          "content": "HTTP/1.1 409 Conflict",
          "type": "json"
        }
      ]
    },
    "version": "0.0.0",
    "filename": "src/main/java/com/rs/devplatform/controller/admin/ProjectController.java",
    "groupTitle": "Admin_Project_Manager",
    "name": "PostAdminProject"
  },
  {
    "type": "POST",
    "url": "/admin/project/list",
    "title": "获取项目列表",
    "group": "Admin_Project_Manager",
    "parameter": {
      "examples": [
        {
          "title": "Request-Example: ",
          "content": "{\n\t    \"current\": 1,\n\t    \"size\": 10,\n\t    \"comments\": \"easyUI分页参数为page/rows, form提交方式server端做了转换，自动mapping到current/size\",\n\t    \"name\":\"projectName模糊匹配\"\n\t}",
          "type": "json"
        }
      ]
    },
    "success": {
      "fields": {
        "Success 200": [
          {
            "group": "Success 200",
            "type": "JSONArray",
            "optional": false,
            "field": "records",
            "description": "<p>返回项目Records</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "records.name",
            "description": "<p>项目名</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "records.init",
            "description": "<p>是否初始化标记(Y : 表示已经初始化过，N : 没有初始化)</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "records.version",
            "description": "<p>项目version</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "records.cjr",
            "description": "<p>创建人</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "records.whr",
            "description": "<p>维护人</p>"
          }
        ]
      },
      "examples": [
        {
          "title": "Success-Response:",
          "content": "HTTP/1.1 200 OK\n{\n\t\t\"total\": 42,\n\t\t\"current\": 1,\n\t\t\"pages\": 9,\n\t\t\"size\": 5,\n\t\t\"rows\": [{\n\t\t\t\"whr\": \"aaa\",\n\t\t\t\"init\": \"N\",\n\t\t\t\"name\": \"ju-pj--152\",\n\t\t\t\"cjr\": \"system\",\n\t\t\t\"id\": \"04a01896f8124937bd01224359ffb7fa\",\n\t\t\t\"version\": 1\n\t\t},\n\t\t{\n\t\t\t\"whr\": \"aaa\",\n\t\t\t\"init\": \"N\",\n\t\t\t\"name\": \"ju-pj--118\",\n\t\t\t\"cjr\": \"system\",\n\t\t\t\"id\": \"1795fbba854e44dd8873bc2911a3e684\",\n\t\t\t\"version\": 1\n\t\t}]\n\t}",
          "type": "json"
        }
      ]
    },
    "version": "0.0.0",
    "filename": "src/main/java/com/rs/devplatform/controller/admin/ProjectController.java",
    "groupTitle": "Admin_Project_Manager",
    "name": "PostAdminProjectList"
  },
  {
    "type": "POST",
    "url": "/admin/project/{pjId}/managers",
    "title": "项目新增managers",
    "group": "Admin_Project_Manager",
    "parameter": {
      "fields": {
        "Parameter": [
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "pjId",
            "description": "<p>项目ID</p>"
          },
          {
            "group": "Parameter",
            "type": "JSONArray",
            "optional": false,
            "field": "managers",
            "description": "<p>项目manager ids</p>"
          },
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "managers._",
            "description": "<p>项目manager id</p>"
          }
        ]
      },
      "examples": [
        {
          "title": "Request-Example: ",
          "content": "{\n\t\"managers\":[ \"m1\",\"m2\"]\n}",
          "type": "json"
        }
      ]
    },
    "success": {
      "examples": [
        {
          "title": "Success-Response:",
          "content": "HTTP/1.1 200 OK",
          "type": "json"
        }
      ]
    },
    "version": "0.0.0",
    "filename": "src/main/java/com/rs/devplatform/controller/admin/ProjectController.java",
    "groupTitle": "Admin_Project_Manager",
    "name": "PostAdminProjectPjidManagers"
  },
  {
    "type": "POST",
    "url": "/admin/project/testdb",
    "title": "测试数据库连接",
    "group": "Admin_Project_Manager",
    "parameter": {
      "fields": {
        "Parameter": [
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "dbType",
            "description": "<p>项目对应数据库类型：oracle,mysql</p>"
          },
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "dbUrl",
            "description": "<p>项目对应数据库地址: 192.168.10.64:3306/test_db-446</p>"
          },
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "dbUser",
            "description": "<p>项目对应数据库用户名(不能为空)</p>"
          },
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "dbPwd",
            "description": "<p>项目对应数据库密码（不能为null，没有密码就传空string“”，否则校验不通过）</p>"
          }
        ]
      },
      "examples": [
        {
          "title": "Request-Example: ",
          "content": "{\"dbType\":\"mysql\", \"dbUrl\":\"192.168.10.64:3306/test_db1\", \"dbUser\":\"root\", \"dbPwd\":\"\"}\n{\"dbType\":\"mysql\", \"dbUrl\":\"192.168.10.64:3306/test_db1\", \"dbUser\":\"root\", \"dbPwd\":\"123456\"}",
          "type": "json"
        }
      ]
    },
    "success": {
      "examples": [
        {
          "title": "Success-Response:",
          "content": "HTTP/1.1 200 OK - 测试连接通过",
          "type": "json"
        }
      ]
    },
    "error": {
      "examples": [
        {
          "title": "Error-Response:",
          "content": "HTTP/1.1 400 Bad Request - 测试连接不通过",
          "type": "json"
        }
      ]
    },
    "version": "0.0.0",
    "filename": "src/main/java/com/rs/devplatform/controller/admin/ProjectController.java",
    "groupTitle": "Admin_Project_Manager",
    "name": "PostAdminProjectTestdb"
  },
  {
    "type": "PUT",
    "url": "/admin/project/{pjId}",
    "title": "更新项目",
    "group": "Admin_Project_Manager",
    "parameter": {
      "fields": {
        "Parameter": [
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "pjId",
            "description": "<p>项目ID</p>"
          },
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "name",
            "description": "<p>项目name</p>"
          },
          {
            "group": "Parameter",
            "type": "Integer",
            "optional": false,
            "field": "version",
            "description": "<p>项目version</p>"
          },
          {
            "group": "Parameter",
            "type": "JSONObject",
            "optional": false,
            "field": "db",
            "description": "<p>项目db(不是必须的)</p>"
          },
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "db.dbType",
            "description": "<p>项目对应数据库类型：oracle,mysql</p>"
          },
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "db.dbUrl",
            "description": "<p>项目对应数据库地址</p>"
          },
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "db.dbUser",
            "description": "<p>项目对应数据库用户名</p>"
          },
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "db.dbPwd",
            "description": "<p>项目对应数据库密码</p>"
          }
        ]
      },
      "examples": [
        {
          "title": "Request-Example: ",
          "content": "{ \"name\":\"只更新项目名称，不修改db资源\", \"version\":1}\n{ \"name\":\"更新项目名和db资源\", \"version\":1, \"db\":{\"dbType\":\"mysql\", \"dbUrl\":\"192.168.10.64:3306/test_db1\", \"dbUser\":\"root\", \"dbPwd\":\"123456\"}}",
          "type": "json"
        }
      ]
    },
    "success": {
      "fields": {
        "Success 200": [
          {
            "group": "Success 200",
            "type": "Integer",
            "optional": false,
            "field": "version",
            "description": "<p>项目version</p>"
          }
        ]
      },
      "examples": [
        {
          "title": "Success-Response:",
          "content": "HTTP/1.1 200 OK\n{\"id\":\"12345\", \"version\":2}",
          "type": "json"
        }
      ]
    },
    "error": {
      "examples": [
        {
          "title": "Error-Response:",
          "content": "HTTP/1.1 400 Bad Request",
          "type": "json"
        },
        {
          "title": "Error-Response:",
          "content": "HTTP/1.1 302 Found",
          "type": "json"
        },
        {
          "title": "Error-Response:",
          "content": "HTTP/1.1 409 Conflict",
          "type": "json"
        }
      ]
    },
    "version": "0.0.0",
    "filename": "src/main/java/com/rs/devplatform/controller/admin/ProjectController.java",
    "groupTitle": "Admin_Project_Manager",
    "name": "PutAdminProjectPjid"
  },
  {
    "type": "DELETE",
    "url": "/admin/role",
    "title": "删除角色(单个和批量)",
    "group": "Admin_Role_Manager",
    "parameter": {
      "fields": {
        "Parameter": [
          {
            "group": "Parameter",
            "type": "JsonArray",
            "optional": false,
            "field": "_",
            "description": "<p>角色ID</p>"
          }
        ]
      },
      "examples": [
        {
          "title": "Request-Example: ",
          "content": "[\"1\",\"2\",\"3\"]",
          "type": "json"
        }
      ]
    },
    "success": {
      "examples": [
        {
          "title": "Success-Response:",
          "content": "HTTP/1.1 200 OK",
          "type": "json"
        }
      ]
    },
    "error": {
      "examples": [
        {
          "title": "Error-Response:",
          "content": "HTTP/1.1 400 Bad Request",
          "type": "json"
        },
        {
          "title": "Error-Response:",
          "content": "HTTP/1.1 302 Found",
          "type": "json"
        },
        {
          "title": "Error-Response:",
          "content": "HTTP/1.1 409 Conflict",
          "type": "json"
        }
      ]
    },
    "version": "0.0.0",
    "filename": "src/main/java/com/rs/devplatform/controller/admin/RoleController.java",
    "groupTitle": "Admin_Role_Manager",
    "name": "DeleteAdminRole"
  },
  {
    "type": "DELETE",
    "url": "/admin/role/{roleId}/users",
    "title": "为角色删除用户",
    "group": "Admin_Role_Manager",
    "parameter": {
      "fields": {
        "Parameter": [
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "roleId",
            "description": "<p>角色ID</p>"
          },
          {
            "group": "Parameter",
            "type": "JSONArray",
            "optional": false,
            "field": "users",
            "description": "<p>User ID</p>"
          }
        ]
      },
      "examples": [
        {
          "title": "Request-Example: ",
          "content": "{\"users\":\"['U1','U2','U3']\"}",
          "type": "json"
        }
      ]
    },
    "success": {
      "examples": [
        {
          "title": "Success-Response:",
          "content": "HTTP/1.1 200 OK",
          "type": "json"
        }
      ]
    },
    "error": {
      "examples": [
        {
          "title": "Error-Response:",
          "content": "HTTP/1.1 400 Bad Request",
          "type": "json"
        }
      ]
    },
    "version": "0.0.0",
    "filename": "src/main/java/com/rs/devplatform/controller/admin/RoleController.java",
    "groupTitle": "Admin_Role_Manager",
    "name": "DeleteAdminRoleRoleidUsers"
  },
  {
    "type": "GET",
    "url": "/admin/role",
    "title": "转到角色管理页面",
    "group": "Admin_Role_Manager",
    "success": {
      "fields": {
        "Success 200": [
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "html",
            "description": "<p>对应角色管理页面</p>"
          }
        ]
      },
      "examples": [
        {
          "title": "页面命名:",
          "content": "{\"页面\":\"manage/roles.html\"}",
          "type": "json"
        }
      ]
    },
    "version": "0.0.0",
    "filename": "src/main/java/com/rs/devplatform/controller/admin/RoleController.java",
    "groupTitle": "Admin_Role_Manager",
    "name": "GetAdminRole"
  },
  {
    "type": "GET",
    "url": "/admin/role/list",
    "title": "获取所有角色（不分页）",
    "group": "Admin_Role_Manager",
    "success": {
      "fields": {
        "Success 200": [
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "id",
            "description": "<p>角色ID</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "name",
            "description": "<p>角色名称</p>"
          }
        ]
      },
      "examples": [
        {
          "title": "Success-Response:",
          "content": "\t\tHTTP/1.1 200 OK\n{\n\t\t  \"total\": 18,\n\t\t  \"rows\": [\n\t\t    {\n\t\t      \"id\": \"0f07cc04b62f419da0a13b0301856896\",\n\t\t      \"name\": \"销售业务员\",\n\t\t      \"version\": 1\n\t\t    },\n\t\t    {\n\t\t      \"id\": \"22149f1cee43432abae40fd48298964e\",\n\t\t      \"name\": \"HR\",\n\t\t      \"version\": 1\n\t\t    }]\n\t\t}",
          "type": "json"
        }
      ]
    },
    "version": "0.0.0",
    "filename": "src/main/java/com/rs/devplatform/controller/admin/RoleController.java",
    "groupTitle": "Admin_Role_Manager",
    "name": "GetAdminRoleList"
  },
  {
    "type": "GET",
    "url": "/admin/role/{roleId}/userlist",
    "title": "获取角色的用户",
    "group": "Admin_Role_Manager",
    "parameter": {
      "fields": {
        "Parameter": [
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "roleId",
            "description": "<p>角色ID</p>"
          }
        ]
      }
    },
    "success": {
      "fields": {
        "Success 200": [
          {
            "group": "Success 200",
            "type": "JsonArray",
            "optional": false,
            "field": "rows",
            "description": "<p>JSONArray</p>"
          },
          {
            "group": "Success 200",
            "optional": false,
            "field": "rows.name",
            "description": "<p>角色名</p>"
          },
          {
            "group": "Success 200",
            "optional": false,
            "field": "rows.id",
            "description": "<p>角色ID</p>"
          }
        ]
      },
      "examples": [
        {
          "title": "Success-Response:",
          "content": " \tHTTP/1.1 200 OK\n\t\t{\"rows\":[\n\t\t\t\t{\"name\":\"test923\",\"id\":\"b0e8c62b236a4cb6b121f165c6915f44\"},\n\t\t\t\t{\"name\":\"test-791\",\"id\":\"6a6dd2c8360648b9acbb619bcf45c8f0\"},\n\t\t\t\t{\"name\":\"test407\",\"id\":\"9c5f0af9d3554cc3bb2905200fa1c393\"}],\n\t\t\"total\": 3}",
          "type": "json"
        }
      ]
    },
    "version": "0.0.0",
    "filename": "src/main/java/com/rs/devplatform/controller/admin/RoleController.java",
    "groupTitle": "Admin_Role_Manager",
    "name": "GetAdminRoleRoleidUserlist"
  },
  {
    "type": "POST",
    "url": "/admin/role",
    "title": "增加角色",
    "group": "Admin_Role_Manager",
    "parameter": {
      "fields": {
        "Parameter": [
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "name",
            "description": "<p>角色名</p>"
          }
        ]
      },
      "examples": [
        {
          "title": "Request-Example: ",
          "content": "{\"name\":\"销售部\"}",
          "type": "json"
        }
      ]
    },
    "success": {
      "fields": {
        "Success 200": [
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "id",
            "description": "<p>返回角色id</p>"
          }
        ]
      },
      "examples": [
        {
          "title": "Success-Response:",
          "content": "HTTP/1.1 200 OK\n{\"id\":\"0f077145cc894f7990387c9458091e1b\"}",
          "type": "json"
        }
      ]
    },
    "error": {
      "examples": [
        {
          "title": "Error-Response:",
          "content": "HTTP/1.1 400 Bad Request",
          "type": "json"
        },
        {
          "title": "Error-Response:",
          "content": "HTTP/1.1 302 Found",
          "type": "json"
        },
        {
          "title": "Error-Response:",
          "content": "HTTP/1.1 409 Conflict",
          "type": "json"
        }
      ]
    },
    "version": "0.0.0",
    "filename": "src/main/java/com/rs/devplatform/controller/admin/RoleController.java",
    "groupTitle": "Admin_Role_Manager",
    "name": "PostAdminRole"
  },
  {
    "type": "POST",
    "url": "/admin/role/list",
    "title": "获取角色列表",
    "group": "Admin_Role_Manager",
    "parameter": {
      "examples": [
        {
          "title": "Request-Example: ",
          "content": "{\n\t    \"current\": 1,\n\t    \"size\": 10,\n\t    \"comments\": \"easyUI分页参数为page/rows, form提交方式server端做了转换，自动mapping到current/size\",\n\t    \"name\":\"roleName模糊匹配\"\n\t}",
          "type": "json"
        }
      ]
    },
    "success": {
      "fields": {
        "Success 200": [
          {
            "group": "Success 200",
            "type": "JsonArray",
            "optional": false,
            "field": "rows",
            "description": "<p>JSONArray</p>"
          },
          {
            "group": "Success 200",
            "optional": false,
            "field": "rows.name",
            "description": "<p>角色名</p>"
          },
          {
            "group": "Success 200",
            "optional": false,
            "field": "rows.id",
            "description": "<p>角色ID</p>"
          },
          {
            "group": "Success 200",
            "optional": false,
            "field": "rows.version",
            "description": "<p>角色version</p>"
          }
        ]
      },
      "examples": [
        {
          "title": "Success-Response:",
          "content": "HTTP/1.1 200 OK\n{\"rows\":[\n\t\t\t{\"name\":\"dept50\",\"id\":\"271123425f994616ae1b7edaceb70070\",\"version\":1},\n\t\t\t{\"name\":\"dept1\",\"id\":\"a98f0e3d5fd84c059b364a9bf0d0c587\",\"version\":2}\n\t\t],\n\"total\":\"2\"}",
          "type": "json"
        }
      ]
    },
    "version": "0.0.0",
    "filename": "src/main/java/com/rs/devplatform/controller/admin/RoleController.java",
    "groupTitle": "Admin_Role_Manager",
    "name": "PostAdminRoleList"
  },
  {
    "type": "POST",
    "url": "/admin/role/{roleId}/users",
    "title": "为角色添加用户",
    "group": "Admin_Role_Manager",
    "parameter": {
      "fields": {
        "Parameter": [
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "roleId",
            "description": "<p>角色ID</p>"
          },
          {
            "group": "Parameter",
            "type": "JSONArray",
            "optional": false,
            "field": "users",
            "description": "<p>User ID</p>"
          }
        ]
      },
      "examples": [
        {
          "title": "Request-Example: ",
          "content": "{\"users\":\"['U1','U2','U3']\"}",
          "type": "json"
        }
      ]
    },
    "success": {
      "examples": [
        {
          "title": "Success-Response:",
          "content": "HTTP/1.1 200 OK",
          "type": "json"
        }
      ]
    },
    "error": {
      "examples": [
        {
          "title": "Error-Response:",
          "content": "HTTP/1.1 400 Bad Request",
          "type": "json"
        },
        {
          "title": "Error-Response:",
          "content": "HTTP/1.1 302 Found",
          "type": "json"
        },
        {
          "title": "Error-Response:",
          "content": "HTTP/1.1 409 Conflict",
          "type": "json"
        }
      ]
    },
    "version": "0.0.0",
    "filename": "src/main/java/com/rs/devplatform/controller/admin/RoleController.java",
    "groupTitle": "Admin_Role_Manager",
    "name": "PostAdminRoleRoleidUsers"
  },
  {
    "type": "PUT",
    "url": "/admin/role/{roleId}",
    "title": "更新角色",
    "group": "Admin_Role_Manager",
    "parameter": {
      "fields": {
        "Parameter": [
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "roleId",
            "description": "<p>角色ID</p>"
          },
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "name",
            "description": "<p>角色名</p>"
          }
        ]
      },
      "examples": [
        {
          "title": "Request-Example: ",
          "content": "{\"name\":\"销售一部\"}",
          "type": "json"
        }
      ]
    },
    "success": {
      "examples": [
        {
          "title": "Success-Response:",
          "content": "HTTP/1.1 200 OK",
          "type": "json"
        }
      ]
    },
    "error": {
      "examples": [
        {
          "title": "Error-Response:",
          "content": "HTTP/1.1 400 Bad Request",
          "type": "json"
        },
        {
          "title": "Error-Response:",
          "content": "HTTP/1.1 302 Found",
          "type": "json"
        },
        {
          "title": "Error-Response:",
          "content": "HTTP/1.1 409 Conflict",
          "type": "json"
        }
      ]
    },
    "version": "0.0.0",
    "filename": "src/main/java/com/rs/devplatform/controller/admin/RoleController.java",
    "groupTitle": "Admin_Role_Manager",
    "name": "PutAdminRoleRoleid"
  },
  {
    "type": "DELETE",
    "url": "/admin/user",
    "title": "删除用户(单个/批量)",
    "group": "Admin_User_Manager",
    "parameter": {
      "fields": {
        "Parameter": [
          {
            "group": "Parameter",
            "type": "JSONArray",
            "optional": false,
            "field": "-",
            "description": "<p>用户IDs</p>"
          }
        ]
      },
      "examples": [
        {
          "title": "Request-Example: ",
          "content": "[\"1\",\"2\",\"3\"]",
          "type": "json"
        }
      ]
    },
    "success": {
      "examples": [
        {
          "title": "Success-Response:",
          "content": "HTTP/1.1 200 OK",
          "type": "json"
        }
      ]
    },
    "error": {
      "examples": [
        {
          "title": "Error-Response:",
          "content": "HTTP/1.1 400 Bad Request",
          "type": "json"
        },
        {
          "title": "Error-Response:",
          "content": "HTTP/1.1 302 Found",
          "type": "json"
        },
        {
          "title": "Error-Response:",
          "content": "HTTP/1.1 409 Conflict",
          "type": "json"
        }
      ]
    },
    "version": "0.0.0",
    "filename": "src/main/java/com/rs/devplatform/controller/admin/UserController.java",
    "groupTitle": "Admin_User_Manager",
    "name": "DeleteAdminUser"
  },
  {
    "type": "GET",
    "url": "/admin/user",
    "title": "转到用户管理页面",
    "group": "Admin_User_Manager",
    "success": {
      "fields": {
        "Success 200": [
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "html",
            "description": "<p>对应用户管理页面</p>"
          }
        ]
      },
      "examples": [
        {
          "title": "页面命名:",
          "content": "{\"页面\":\"manage/user.html\"}",
          "type": "json"
        }
      ]
    },
    "version": "0.0.0",
    "filename": "src/main/java/com/rs/devplatform/controller/admin/UserController.java",
    "groupTitle": "Admin_User_Manager",
    "name": "GetAdminUser"
  },
  {
    "type": "GET",
    "url": "/admin/user/list",
    "title": "获取所有用户（不分页）",
    "group": "Admin_User_Manager",
    "success": {
      "fields": {
        "Success 200": [
          {
            "group": "Success 200",
            "type": "JsonArray",
            "optional": false,
            "field": "rows",
            "description": "<p>JSONArray</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "records.name",
            "description": "<p>用户名</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "records.nkname",
            "description": "<p>昵称</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "records.tel",
            "description": "<p>电话号码</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "records.id",
            "description": "<p>用户ID</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "records.email",
            "description": "<p>用户邮箱</p>"
          }
        ]
      },
      "examples": [
        {
          "title": "Success-Response:",
          "content": "\t\tHTTP/1.1 200 OK\n{\n\t\t  \"total\": 2,\n\t\t  \"rows\": [\n\t\t    {\n\t\t      \"id\": \"085a3347a44a44de828a74b3ee269db8\",\n\t\t      \"name\": \"tang1\",\n\t\t      \"nkname\": \"tang1\",\n\t\t      \"email\": \"abc@qq.com\",\n\t\t      \"tel\": \"\",\n\t\t      \"pwdConfirm\": null,\n\t\t      \"userType\": null,\n\t\t      \"icon\": null,\n\t\t      \"version\": 1\n\t\t    },\n\t\t    {\n\t\t      \"id\": \"0bd498b5fd274a62a2dc9484fc953511\",\n\t\t      \"name\": \"test-8877\",\n\t\t      \"nkname\": \"nk_test-8877\",\n\t\t      \"email\": \"test-8877@123.com\",\n\t\t      \"tel\": null,\n\t\t      \"pwdConfirm\": null,\n\t\t      \"userType\": null,\n\t\t      \"icon\": null,\n\t\t      \"version\": 1\n\t\t    }]",
          "type": "json"
        }
      ]
    },
    "version": "0.0.0",
    "filename": "src/main/java/com/rs/devplatform/controller/admin/UserController.java",
    "groupTitle": "Admin_User_Manager",
    "name": "GetAdminUserList"
  },
  {
    "type": "POST",
    "url": "/admin/user",
    "title": "增加用户",
    "group": "Admin_User_Manager",
    "parameter": {
      "fields": {
        "Parameter": [
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "name",
            "description": "<p>用户名</p>"
          },
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "nkname",
            "description": "<p>用户昵称（可以是中文名等）</p>"
          },
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "pwd",
            "description": "<p>密码</p>"
          },
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "pwdConfirm",
            "description": "<p>确认密码</p>"
          },
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "email",
            "description": "<p>邮箱</p>"
          },
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "tel",
            "description": "<p>电话</p>"
          }
        ]
      },
      "examples": [
        {
          "title": "Request-Example: ",
          "content": "{\"name\":\"zhangsan\", \"nkname\":\"张三\", \"pwd\":\"123456\", \"pwdConfirm\":\"123456\", \"email\":\"123@abc.com\", \"tel\":\"133133131313\"}",
          "type": "json"
        }
      ]
    },
    "success": {
      "fields": {
        "Success 200": [
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "id",
            "description": "<p>返回用户id</p>"
          }
        ]
      },
      "examples": [
        {
          "title": "Success-Response:",
          "content": "HTTP/1.1 200 OK\n{\"id\":\"0f077145cc894f7990387c9458091e1b\"}",
          "type": "json"
        }
      ]
    },
    "error": {
      "examples": [
        {
          "title": "Error-Response:",
          "content": "HTTP/1.1 400 Bad Request\n{\n\t\t\"verrors\": [\n\t\t{\n\t\t\t\"errCode\": \"email\",\n\t\t\t\"errorMsg\": \"不是一个合法的电子邮件地址\"\n\t\t},\n\t\t{\n\t\t\t\"errCode\": \"pwd\",\n\t\t\t\"errorMsg\": \"不能为空\"\n\t\t},\n\t\t{\n\t\t\t\"errCode\": \"pwd\",\n\t\t\t\"errorMsg\": \"长度需要在6和50之间\"\n\t\t}\n\t\t]\n\t}",
          "type": "json"
        },
        {
          "title": "Error-Response:",
          "content": "HTTP/1.1 302 Found",
          "type": "json"
        },
        {
          "title": "Error-Response:",
          "content": "HTTP/1.1 409 Conflict",
          "type": "json"
        }
      ]
    },
    "version": "0.0.0",
    "filename": "src/main/java/com/rs/devplatform/controller/admin/UserController.java",
    "groupTitle": "Admin_User_Manager",
    "name": "PostAdminUser"
  },
  {
    "type": "POST",
    "url": "/admin/user/list",
    "title": "获取用户列表",
    "group": "Admin_User_Manager",
    "parameter": {
      "examples": [
        {
          "title": "Request-Example: ",
          "content": "{\n\t    \"current\": 1,\n\t    \"size\": 10,\n\t    \"comments\": \"easyUI分页参数为page/rows, form提交方式server端做了转换，自动mapping到current/size\",\n\t    \"name\":\"用户名模糊匹配\",\n\t    \"nkname\":\"昵称模糊匹配\",\n\t}",
          "type": "json"
        }
      ]
    },
    "success": {
      "fields": {
        "Success 200": [
          {
            "group": "Success 200",
            "type": "JsonArray",
            "optional": false,
            "field": "rows",
            "description": "<p>JSONArray</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "records.name",
            "description": "<p>用户名</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "records.nkname",
            "description": "<p>昵称</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "records.tel",
            "description": "<p>电话号码</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "records.id",
            "description": "<p>用户ID</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "records.email",
            "description": "<p>用户邮箱</p>"
          }
        ]
      },
      "examples": [
        {
          "title": "Success-Response:",
          "content": " \tHTTP/1.1 200 OK\n\t\t{\n\t\t\t\t\"total\": 1,\n\t\t\t\t\"current\": 1,\n\t\t\t\t\"pages\": 1,\n\t\t\t\t\"size\": 5,\n\t\t\t\t\"rows\": [{\n\t\t\t\t\t\"nkname\": \"admin\",\n\t\t\t\t\t\"name\": \"admin\",\n\t\t\t\t\t\"tel\": \"1234546\",\n\t\t\t\t\t\"id\": \"123456\",\n\t\t\t\t\t\"email\": \"admin@123.com\"\n\t\t\t\t}]\n\t\t\t}",
          "type": "json"
        }
      ]
    },
    "version": "0.0.0",
    "filename": "src/main/java/com/rs/devplatform/controller/admin/UserController.java",
    "groupTitle": "Admin_User_Manager",
    "name": "PostAdminUserList"
  },
  {
    "type": "PUT",
    "url": "/admin/user/{userId}",
    "title": "更新用户",
    "group": "Admin_User_Manager",
    "parameter": {
      "fields": {
        "Parameter": [
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "id",
            "description": "<p>用户ID</p>"
          },
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "email",
            "description": "<p>email</p>"
          },
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "tel",
            "description": "<p>电话</p>"
          }
        ]
      },
      "examples": [
        {
          "title": "Request-Example: ",
          "content": "{\"id\":\"1234566\", \"email\":\"abc@123.com\", \"tel\":\"123456\"}",
          "type": "json"
        }
      ]
    },
    "success": {
      "examples": [
        {
          "title": "Success-Response:",
          "content": "HTTP/1.1 200 OK",
          "type": "json"
        }
      ]
    },
    "error": {
      "examples": [
        {
          "title": "Error-Response:",
          "content": "HTTP/1.1 400 Bad Request",
          "type": "json"
        },
        {
          "title": "Error-Response:",
          "content": "HTTP/1.1 302 Found",
          "type": "json"
        },
        {
          "title": "Error-Response:",
          "content": "HTTP/1.1 409 Conflict",
          "type": "json"
        }
      ]
    },
    "version": "0.0.0",
    "filename": "src/main/java/com/rs/devplatform/controller/admin/UserController.java",
    "groupTitle": "Admin_User_Manager",
    "name": "PutAdminUserUserid"
  },
  {
    "type": "DELETE",
    "url": "/admin/datadict/cdval/{cdvalId}",
    "title": "CodeVal删除",
    "group": "Admin_datadict",
    "parameter": {
      "fields": {
        "Parameter": [
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "cdvalId",
            "description": "<p>CodeVal ID</p>"
          }
        ]
      }
    },
    "success": {
      "examples": [
        {
          "title": "Success-Response:",
          "content": "HTTP/1.1 200 OK",
          "type": "json"
        }
      ]
    },
    "error": {
      "examples": [
        {
          "title": "Error-Response:",
          "content": "HTTP/1.1 400 Bad Request",
          "type": "json"
        },
        {
          "title": "Error-Response:",
          "content": "HTTP/1.1 302 Found",
          "type": "json"
        },
        {
          "title": "Error-Response:",
          "content": "HTTP/1.1 409 Conflict",
          "type": "json"
        }
      ]
    },
    "version": "0.0.0",
    "filename": "src/main/java/com/rs/devplatform/controller/admin/DataDictController.java",
    "groupTitle": "Admin_datadict",
    "name": "DeleteAdminDatadictCdvalCdvalid"
  },
  {
    "type": "DELETE",
    "url": "/admin/datadict/{typeId}",
    "title": "CodeType删除",
    "group": "Admin_datadict",
    "parameter": {
      "fields": {
        "Parameter": [
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "typeId",
            "description": "<p>数据字典TypeID</p>"
          },
          {
            "group": "Parameter",
            "type": "Integer",
            "optional": false,
            "field": "version",
            "description": "<p>数据字典Type version</p>"
          }
        ]
      },
      "examples": [
        {
          "title": "Request-Example: ",
          "content": "{\"version\":1}",
          "type": "json"
        }
      ]
    },
    "success": {
      "examples": [
        {
          "title": "Success-Response:",
          "content": "HTTP/1.1 200 OK",
          "type": "json"
        }
      ]
    },
    "error": {
      "examples": [
        {
          "title": "Error-Response:",
          "content": "HTTP/1.1 409 Conflict (Modified by others)",
          "type": "json"
        }
      ]
    },
    "version": "0.0.0",
    "filename": "src/main/java/com/rs/devplatform/controller/admin/DataDictController.java",
    "groupTitle": "Admin_datadict",
    "name": "DeleteAdminDatadictTypeid"
  },
  {
    "type": "GET",
    "url": "/admin/datadict",
    "title": "转到数据字典管理页面",
    "group": "Admin_datadict",
    "success": {
      "fields": {
        "Success 200": [
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "html",
            "description": "<p>对应数据字典管理页面</p>"
          }
        ]
      },
      "examples": [
        {
          "title": "页面命名:",
          "content": "{\"页面\":\"manage/datadict.html\"}",
          "type": "json"
        }
      ]
    },
    "version": "0.0.0",
    "filename": "src/main/java/com/rs/devplatform/controller/admin/DataDictController.java",
    "groupTitle": "Admin_datadict",
    "name": "GetAdminDatadict"
  },
  {
    "type": "GET",
    "url": "/admin/datadict/{typeId}",
    "title": "CodeType根据typeId查询",
    "group": "Admin_datadict",
    "success": {
      "fields": {
        "Success 200": [
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "id",
            "description": "<p>数据字典Typeid</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "name",
            "description": "<p>数据字典Type name</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "cdtype",
            "description": "<p>数据字典cdtype</p>"
          },
          {
            "group": "Success 200",
            "type": "Integer",
            "optional": false,
            "field": "version",
            "description": "<p>数据字典Type version</p>"
          },
          {
            "group": "Success 200",
            "type": "JSONArray",
            "optional": false,
            "field": "cdvals",
            "description": "<p>CodeType下对应的CodeValues</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "cdvals.id",
            "description": "<p>CodeVal ID</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "cdvals.cdval",
            "description": "<p>CodeVal</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "cdvals.cddescp",
            "description": "<p>CodeVal Descp</p>"
          }
        ]
      },
      "examples": [
        {
          "title": "Success-Response:",
          "content": "HTTP/1.1 200 OK\n{\n\t\"id\":\"123\", \"name\":\"abc\", \"cdtype\":\"test_type\", \"version\":1,\n\t\"cdvals\":[\n\t{\"cddescp\":\"value3\",\"cdval\":\"3\",\"id\":\"06f60e31bb9b45a4b19638a4055aa5c7\"},\n\t{\"cddescp\":\"value1\",\"cdval\":\"1\",\"id\":\"bf42bd9b261140a2ae3b26be34936827\"},\n\t{\"cddescp\":\"value2\",\"cdval\":\"2\",\"id\":\"c252232850c242d295859afac318dffc\"}\n\t]\n}",
          "type": "json"
        }
      ]
    },
    "error": {
      "examples": [
        {
          "title": "Error-Response:",
          "content": "HTTP/1.1 409 Conflict (Modified by others)",
          "type": "json"
        }
      ]
    },
    "version": "0.0.0",
    "filename": "src/main/java/com/rs/devplatform/controller/admin/DataDictController.java",
    "groupTitle": "Admin_datadict",
    "name": "GetAdminDatadictTypeid"
  },
  {
    "type": "GET",
    "url": "/admin/datadict/{typeId}/cdval",
    "title": "CodeValues获取指定typeId的valList",
    "group": "Admin_datadict",
    "parameter": {
      "fields": {
        "Parameter": [
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "typeId",
            "description": "<p>数据字典TypeID</p>"
          }
        ]
      }
    },
    "success": {
      "fields": {
        "Success 200": [
          {
            "group": "Success 200",
            "type": "JSONArray",
            "optional": false,
            "field": "records",
            "description": "<p>返回数据字典CodeVal Records</p>"
          },
          {
            "group": "Success 200",
            "type": "Integer",
            "optional": false,
            "field": "total",
            "description": "<p>返回数据字典CodeVal count</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "records.id",
            "description": "<p>数据字典CodeValID</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "records.cdval",
            "description": "<p>数据字典CodeVal</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "records.cddescp",
            "description": "<p>数据字典CodeValDescp</p>"
          }
        ]
      },
      "examples": [
        {
          "title": "Success-Response:",
          "content": "HTTP/1.1 200 OK\n\t\t{\n\t\t\t\"records\":[\n\t\t\t\t{\"cdval\":\"PM\", \"cddescp\":\"Project Manager\",\"id\":\"abc1\"},\n\t\t\t\t{\"cdval\":\"TM\", \"cddescp\":\"Team Member\", \"id\":\"cdf1\"}\n\t\t\t],\n\t\t\t\"total\":2\n\t\t}",
          "type": "json"
        }
      ]
    },
    "version": "0.0.0",
    "filename": "src/main/java/com/rs/devplatform/controller/admin/DataDictController.java",
    "groupTitle": "Admin_datadict",
    "name": "GetAdminDatadictTypeidCdval"
  },
  {
    "type": "POST",
    "url": "/admin/datadict",
    "title": "CodeType增加",
    "group": "Admin_datadict",
    "parameter": {
      "fields": {
        "Parameter": [
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "name",
            "description": "<p>数据字典名</p>"
          },
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "cdtype",
            "description": "<p>数据字典type</p>"
          }
        ]
      },
      "examples": [
        {
          "title": "Request-Example: ",
          "content": "{\"name\":\"测试类型1\",\"cdtype\":\"test_type\"}",
          "type": "json"
        }
      ]
    },
    "success": {
      "fields": {
        "Success 200": [
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "id",
            "description": "<p>返回数据字典Type id</p>"
          },
          {
            "group": "Success 200",
            "type": "Integer",
            "optional": false,
            "field": "version",
            "description": "<p>返回数据字典Typeversion</p>"
          }
        ]
      },
      "examples": [
        {
          "title": "Success-Response:",
          "content": "HTTP/1.1 200 OK\n{\"id\":\"0f077145cc894f7990387c9458091e1b\", \"version\":1}",
          "type": "json"
        }
      ]
    },
    "error": {
      "examples": [
        {
          "title": "Error-Response:",
          "content": "HTTP/1.1 400 Bad Request",
          "type": "json"
        },
        {
          "title": "Error-Response:",
          "content": "HTTP/1.1 302 Found",
          "type": "json"
        },
        {
          "title": "Error-Response:",
          "content": "HTTP/1.1 409 Conflict",
          "type": "json"
        }
      ]
    },
    "version": "0.0.0",
    "filename": "src/main/java/com/rs/devplatform/controller/admin/DataDictController.java",
    "groupTitle": "Admin_datadict",
    "name": "PostAdminDatadict"
  },
  {
    "type": "POST",
    "url": "/admin/datadict/list",
    "title": "CodeType获取List",
    "group": "Admin_datadict",
    "parameter": {
      "fields": {
        "Parameter": [
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "name",
            "description": "<p>数据字典名(模糊查询)</p>"
          },
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "cdtype",
            "description": "<p>数据字典type（模糊查询）</p>"
          }
        ]
      },
      "examples": [
        {
          "title": "Request-Example: ",
          "content": "{\n\t    \"current\": 1,\n\t    \"size\": 10,\n\t    \"comments\": \"easyUI分页参数为page/rows, form提交方式server端做了转换，自动mapping到current/size\",\n\t    \"name\":\"字典类型描述\",\n\t    \"cdtype\":\"字典类型\"\n\t}",
          "type": "json"
        }
      ]
    },
    "success": {
      "fields": {
        "Success 200": [
          {
            "group": "Success 200",
            "type": "JSONArray",
            "optional": false,
            "field": "records",
            "description": "<p>返回数据字典Records</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "records.id",
            "description": "<p>CodeType ID</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "records.name",
            "description": "<p>CodeType</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "records.cdtype",
            "description": "<p>CodeType</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "records.version",
            "description": "<p>CodeType version</p>"
          }
        ]
      },
      "examples": [
        {
          "title": "Success-Response:",
          "content": "HTTP/1.1 200 OK\n{\"records\":[\n\t{\"name\":\"示例数据字典名称\",\"cdtype\":\"sample_type\", \"id\":\"29bb254e7ddd4c65a4c2c8ee4f91b66d\", \"version\":1},\n\t{\"name\":\"示例数据字典名称\",\"cdtype\":\"sample_type\", \"id\":\"29bb254e7ddd4c65a4c2c8ee4f91b66d\", \"version\":1}\n],\"total\":2}",
          "type": "json"
        }
      ]
    },
    "version": "0.0.0",
    "filename": "src/main/java/com/rs/devplatform/controller/admin/DataDictController.java",
    "groupTitle": "Admin_datadict",
    "name": "PostAdminDatadictList"
  },
  {
    "type": "POST",
    "url": "/admin/datadict/{typeId}",
    "title": "CodeVal新增",
    "group": "Admin_datadict",
    "parameter": {
      "fields": {
        "Parameter": [
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "typeId",
            "description": "<p>数据字典TypeID</p>"
          },
          {
            "group": "Parameter",
            "type": "JSONArray",
            "optional": false,
            "field": "_",
            "description": "<p>Code Vals</p>"
          }
        ]
      },
      "examples": [
        {
          "title": "Request-Example:",
          "content": "\t{\"cdvals\": \n\t\t[\n\t\t\t{\"cdval\":\"PM\", \"cddescp\":\"Project Manager\"},\n\t\t\t{\"cdval\":\"TM\", \"cddescp\":\"Team Member\"}\n\t\t]\n}",
          "type": "json"
        }
      ]
    },
    "success": {
      "examples": [
        {
          "title": "Success-Response:",
          "content": "\tHTTP/1.1 200 OK\n\t{\"cdvals\": \n\t\t[\n\t\t\t{\"cdval\":\"PM\", \"cddescp\":\"Project Manager\",\"id\":\"abc1\"},\n\t\t\t{\"cdval\":\"TM\", \"cddescp\":\"Team Member\", \"id\":\"cdf1\"}\n\t\t]\n}",
          "type": "json"
        }
      ]
    },
    "error": {
      "examples": [
        {
          "title": "Error-Response:",
          "content": "HTTP/1.1 400 Bad Request",
          "type": "json"
        }
      ]
    },
    "version": "0.0.0",
    "filename": "src/main/java/com/rs/devplatform/controller/admin/DataDictController.java",
    "groupTitle": "Admin_datadict",
    "name": "PostAdminDatadictTypeid"
  },
  {
    "type": "PUT",
    "url": "/admin/datadict/cdval/{cdvalId}",
    "title": "CodeVal更新",
    "group": "Admin_datadict",
    "parameter": {
      "fields": {
        "Parameter": [
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "cdvalId",
            "description": "<p>CodeVal ID</p>"
          },
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "cdval",
            "description": "<p>CodeVal</p>"
          },
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "cddescp",
            "description": "<p>CodeVal Description</p>"
          }
        ]
      },
      "examples": [
        {
          "title": "Request-Example: ",
          "content": "{\"cdval\":\"PM\", \"cddescp\":\"Project Manager\"}",
          "type": "json"
        }
      ]
    },
    "success": {
      "examples": [
        {
          "title": "Success-Response:",
          "content": "HTTP/1.1 200 OK",
          "type": "json"
        }
      ]
    },
    "error": {
      "examples": [
        {
          "title": "Error-Response:",
          "content": "HTTP/1.1 400 Bad Request",
          "type": "json"
        },
        {
          "title": "Error-Response:",
          "content": "HTTP/1.1 302 Found",
          "type": "json"
        },
        {
          "title": "Error-Response:",
          "content": "HTTP/1.1 409 Conflict",
          "type": "json"
        }
      ]
    },
    "version": "0.0.0",
    "filename": "src/main/java/com/rs/devplatform/controller/admin/DataDictController.java",
    "groupTitle": "Admin_datadict",
    "name": "PutAdminDatadictCdvalCdvalid"
  },
  {
    "type": "PUT",
    "url": "/admin/datadict/{typeId}",
    "title": "CodeType更新",
    "group": "Admin_datadict",
    "description": "<p>只更新类型描述(name)，不更新codeType</p>",
    "parameter": {
      "fields": {
        "Parameter": [
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "typeId",
            "description": "<p>数据字典TypeID</p>"
          },
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "name",
            "description": "<p>数据字典Type name</p>"
          },
          {
            "group": "Parameter",
            "type": "Integer",
            "optional": false,
            "field": "version",
            "description": "<p>数据字典Type version</p>"
          }
        ]
      },
      "examples": [
        {
          "title": "Request-Example: ",
          "content": "{\"name\":\"测试类型1\", \"version\":1}",
          "type": "json"
        }
      ]
    },
    "success": {
      "fields": {
        "Success 200": [
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "id",
            "description": "<p>数据字典Typeid</p>"
          },
          {
            "group": "Success 200",
            "type": "Integer",
            "optional": false,
            "field": "version",
            "description": "<p>数据字典Typeversion</p>"
          }
        ]
      },
      "examples": [
        {
          "title": "Success-Response:",
          "content": "HTTP/1.1 200 OK\n{\"version\":2}",
          "type": "json"
        }
      ]
    },
    "error": {
      "examples": [
        {
          "title": "Error-Response:",
          "content": "HTTP/1.1 400 Bad Request",
          "type": "json"
        },
        {
          "title": "Error-Response:",
          "content": "HTTP/1.1 302 Found",
          "type": "json"
        },
        {
          "title": "Error-Response:",
          "content": "HTTP/1.1 409 Conflict",
          "type": "json"
        }
      ]
    },
    "version": "0.0.0",
    "filename": "src/main/java/com/rs/devplatform/controller/admin/DataDictController.java",
    "groupTitle": "Admin_datadict",
    "name": "PutAdminDatadictTypeid"
  },
  {
    "type": "DELETE",
    "url": "/cg/form/custbt删除自定义按钮（单个/批量）",
    "title": "",
    "group": "CG",
    "parameter": {
      "fields": {
        "Parameter": [
          {
            "group": "Parameter",
            "type": "JSONArray",
            "optional": false,
            "field": "-",
            "description": "<p>自定义按钮ids</p>"
          }
        ]
      },
      "examples": [
        {
          "title": "Request-Example: ",
          "content": "[\"1\",\"2\",\"3\"]",
          "type": "json"
        }
      ]
    },
    "success": {
      "examples": [
        {
          "title": "Success-Response:",
          "content": "HTTP/1.1 200 OK",
          "type": "json"
        }
      ]
    },
    "error": {
      "examples": [
        {
          "title": "Error-Response:",
          "content": "HTTP/1.1 400 Bad Request",
          "type": "json"
        },
        {
          "title": "Error-Response:",
          "content": "HTTP/1.1 302 Found",
          "type": "json"
        },
        {
          "title": "Error-Response:",
          "content": "HTTP/1.1 409 Conflict",
          "type": "json"
        }
      ]
    },
    "version": "0.0.0",
    "filename": "src/main/java/com/rs/devplatform/controller/cg/CGController.java",
    "groupTitle": "CG",
    "name": "DeleteCgFormCustbt"
  },
  {
    "type": "DELETE",
    "url": "/cg/form/js/{id}删除增强JS单个",
    "title": "",
    "group": "CG",
    "success": {
      "examples": [
        {
          "title": "Success-Response:",
          "content": "HTTP/1.1 200 OK",
          "type": "json"
        }
      ]
    },
    "error": {
      "examples": [
        {
          "title": "Error-Response:",
          "content": "HTTP/1.1 400 Bad Request",
          "type": "json"
        },
        {
          "title": "Error-Response:",
          "content": "HTTP/1.1 302 Found",
          "type": "json"
        },
        {
          "title": "Error-Response:",
          "content": "HTTP/1.1 409 Conflict",
          "type": "json"
        }
      ]
    },
    "version": "0.0.0",
    "filename": "src/main/java/com/rs/devplatform/controller/cg/CGController.java",
    "groupTitle": "CG",
    "name": "DeleteCgFormJsIdJs"
  },
  {
    "type": "DELETE",
    "url": "/cg/form/{tableId}",
    "title": "6.删除表单",
    "group": "CG",
    "parameter": {
      "fields": {
        "Parameter": [
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "tableId",
            "description": "<p>Table ID</p>"
          },
          {
            "group": "Parameter",
            "type": "Integer",
            "optional": false,
            "field": "version",
            "description": "<p>Table version</p>"
          }
        ]
      },
      "examples": [
        {
          "title": "Request-Example: ",
          "content": "{\n  \"version\":1\n}",
          "type": "json"
        }
      ]
    },
    "success": {
      "examples": [
        {
          "title": "Success-Response:",
          "content": "HTTP/1.1 200 OK",
          "type": "json"
        }
      ]
    },
    "error": {
      "examples": [
        {
          "title": "Error-Response:",
          "content": "HTTP/1.1 400 Bad Request\n{\n  \"verror\":[\n  \t{\"errCode\":\"\",\"errorMsg\":\"系统表不允许删除\"},\n  ]\n}",
          "type": "json"
        },
        {
          "title": "Error-Response:",
          "content": "HTTP/1.1 302 Found",
          "type": "json"
        },
        {
          "title": "Error-Response:",
          "content": "HTTP/1.1 409 Conflict",
          "type": "json"
        }
      ]
    },
    "version": "0.0.0",
    "filename": "src/main/java/com/rs/devplatform/controller/cg/CGController.java",
    "groupTitle": "CG",
    "name": "DeleteCgFormTableid"
  },
  {
    "type": "GET",
    "url": "/cg/form/basecolumns",
    "title": "获取表基础字段s",
    "group": "CG",
    "parameter": {
      "examples": [
        {
          "title": "Request-Example: ",
          "content": "GET /cg/form/basecolumns",
          "type": "json"
        }
      ]
    },
    "success": {
      "fields": {
        "Success 200": [
          {
            "group": "Success 200",
            "type": "Integer",
            "optional": false,
            "field": "colSeq",
            "description": ""
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "colName",
            "description": ""
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "colIspk",
            "description": ""
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "colMark",
            "description": ""
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "colIsnull",
            "description": ""
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "inputLen",
            "description": ""
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "isGriddisp",
            "description": ""
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "isFormdisp",
            "description": ""
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "colType",
            "description": ""
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "colLen",
            "description": ""
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "isSearch",
            "description": ""
          }
        ]
      },
      "examples": [
        {
          "title": "Success-Response:",
          "content": " HTTP/1.1 200 OK\n{\n \"rows\": [{\n  \"colSeq\": 1,\n  \"colName\": \"id\",\n  \"colIspk\": \"Y\",\n  \"colMark\": \"主键\",\n  \"colIsnull\": \"N\",\n  \"inputLen\": 50,\n  \"isGriddisp\": \"N\",\n  \"isFormdisp\": \"N\",\n  \"colLen\": 50,\n  \"colDecpoint\": 0,\n  \"isSearch\": \"N\",\n  \"colType\": \"string\",\n  \"inputType\": \"text\"\n },\n {\n  \"colSeq\": 2,\n  \"colName\": \"whr\",\n  \"colIspk\": \"N\",\n  \"colMark\": \"维护人\",\n  \"colIsnull\": \"N\",\n  \"inputLen\": 50,\n  \"isGriddisp\": \"N\",\n  \"isFormdisp\": \"N\",\n  \"colLen\": 50,\n  \"colDecpoint\": 0,\n  \"isSearch\": \"N\",\n  \"colType\": \"string\",\n  \"inputType\": \"text\"\n },\n {\n  \"colSeq\": 3,\n  \"colName\": \"whrid\",\n  \"colIspk\": \"N\",\n  \"colMark\": \"维护人ID\",\n  \"colIsnull\": \"N\",\n  \"inputLen\": 50,\n  \"isGriddisp\": \"N\",\n  \"isFormdisp\": \"N\",\n  \"colLen\": 50,\n  \"colDecpoint\": 0,\n  \"isSearch\": \"N\",\n  \"colType\": \"string\",\n  \"inputType\": \"text\"\n },\n {\n  \"colSeq\": 4,\n  \"colName\": \"whsj\",\n  \"colIspk\": \"N\",\n  \"colMark\": \"维护时间\",\n  \"colIsnull\": \"N\",\n  \"inputLen\": 50,\n  \"isGriddisp\": \"N\",\n  \"isFormdisp\": \"N\",\n  \"colLen\": 50,\n  \"colDecpoint\": 0,\n  \"isSearch\": \"N\",\n  \"colType\": \"datetime\",\n  \"inputType\": \"datetime\"\n },\n {\n  \"colSeq\": 5,\n  \"colName\": \"cjr\",\n  \"colIspk\": \"N\",\n  \"colMark\": \"创建人\",\n  \"colIsnull\": \"N\",\n  \"inputLen\": 50,\n  \"isGriddisp\": \"N\",\n  \"isFormdisp\": \"N\",\n  \"colLen\": 50,\n  \"colDecpoint\": 0,\n  \"isSearch\": \"N\",\n  \"colType\": \"string\",\n  \"inputType\": \"text\"\n },\n {\n  \"colSeq\": 6,\n  \"colName\": \"cjrid\",\n  \"colIspk\": \"N\",\n  \"colMark\": \"创建人ID\",\n  \"colIsnull\": \"N\",\n  \"inputLen\": 50,\n  \"isGriddisp\": \"N\",\n  \"isFormdisp\": \"N\",\n  \"colLen\": 50,\n  \"colDecpoint\": 0,\n  \"isSearch\": \"N\",\n  \"colType\": \"string\",\n  \"inputType\": \"text\"\n },\n {\n  \"colSeq\": 7,\n  \"colName\": \"cjsj\",\n  \"colIspk\": \"N\",\n  \"colMark\": \"创建时间\",\n  \"colIsnull\": \"N\",\n  \"inputLen\": 50,\n  \"isGriddisp\": \"N\",\n  \"isFormdisp\": \"N\",\n  \"colLen\": 50,\n  \"colDecpoint\": 0,\n  \"isSearch\": \"N\",\n  \"colType\": \"datetime\",\n  \"inputType\": \"datetime\"\n },\n {\n  \"colSeq\": 8,\n  \"colName\": \"version\",\n  \"colIspk\": \"N\",\n  \"colMark\": \"版本\",\n  \"colIsnull\": \"N\",\n  \"inputLen\": 50,\n  \"isGriddisp\": \"N\",\n  \"isFormdisp\": \"N\",\n  \"colLen\": 50,\n  \"colDecpoint\": 0,\n  \"isSearch\": \"N\",\n  \"colType\": \"int\",\n  \"inputType\": \"text\"\n },\n {\n  \"colSeq\": 9,\n  \"colName\": \"deleted\",\n  \"colIspk\": \"N\",\n  \"colMark\": \"逻辑删除标记\",\n  \"colIsnull\": \"N\",\n  \"inputLen\": 50,\n  \"isGriddisp\": \"N\",\n  \"isFormdisp\": \"N\",\n  \"colLen\": 50,\n  \"colDecpoint\": 0,\n  \"isSearch\": \"N\",\n  \"colType\": \"string\",\n  \"inputType\": \"text\"\n }]\n}",
          "type": "json"
        }
      ]
    },
    "error": {
      "examples": [
        {
          "title": "Error-Response:",
          "content": "HTTP/1.1 400 Bad Request",
          "type": "json"
        }
      ]
    },
    "version": "0.0.0",
    "filename": "src/main/java/com/rs/devplatform/controller/cg/CGController.java",
    "groupTitle": "CG",
    "name": "GetCgFormBasecolumns"
  },
  {
    "type": "GET",
    "url": "/cg/form/js/getlist/{id}",
    "title": "获取表单的增强js",
    "group": "CG",
    "parameter": {
      "examples": [
        {
          "title": "Request-Example: ",
          "content": "{\"jslx\":\"form\"}",
          "type": "json"
        }
      ]
    },
    "success": {
      "fields": {
        "Success 200": [
          {
            "group": "Success 200",
            "type": "Integer",
            "optional": false,
            "field": "total",
            "description": "<p>总记录条数</p>"
          },
          {
            "group": "Success 200",
            "type": "Integer",
            "optional": false,
            "field": "current",
            "description": "<p>当前页码</p>"
          },
          {
            "group": "Success 200",
            "type": "Integer",
            "optional": false,
            "field": "size",
            "description": "<p>每页显示记录条数</p>"
          },
          {
            "group": "Success 200",
            "type": "Integer",
            "optional": false,
            "field": "pages",
            "description": "<p>总页数</p>"
          },
          {
            "group": "Success 200",
            "type": "JsonArray",
            "optional": false,
            "field": "rows",
            "description": "<p>JSONArray</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "rows.id",
            "description": "<p>增强js主键</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "rows.js",
            "description": "<p>增强js</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "rows.jslx",
            "description": "<p>增强js类型  form/list</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "rows.nr",
            "description": "<p>增强js内容</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "rows.bdid",
            "description": "<p>表单id</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "rows.whr",
            "description": "<p>维护人名字</p>"
          },
          {
            "group": "Success 200",
            "type": "Date",
            "optional": false,
            "field": "rows.whsj",
            "description": "<p>维护时间</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "rows.whrid",
            "description": "<p>维护人ID</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "rows.cjr",
            "description": "<p>创建人名字</p>"
          },
          {
            "group": "Success 200",
            "type": "Date",
            "optional": false,
            "field": "rows.cjsj",
            "description": "<p>创建时间</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "rows.cjrid",
            "description": "<p>创建人ID</p>"
          },
          {
            "group": "Success 200",
            "type": "Integer",
            "optional": false,
            "field": "rows.version",
            "description": "<p>数据记录版本</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "rows.deleted",
            "description": "<p>逻辑删除标记位</p>"
          }
        ]
      },
      "examples": [
        {
          "title": "Success-Response:",
          "content": " HTTP/1.1 200 OK\n\t{\"total\",25, \"current\":1, \"size\":10, \"pages\":3,\"rows\":[\n\t\t{\"whr\":\"system\",\"cjsj\":1486697241000,\"whrid\":\"system\",\"cjrid\":\"system\",\n\t\t \"bdid\":\"1\",\"jslx\":\"form\",\"nr\":\"unr\",\"js\":\"12313\",\"cjr\":\"system\",\n\t\t \"id\":\"fd9a95bf50864ef39c9366beab1d5369\",\"version\":1,\n      \"whsj\":1486701551000}\n ]}",
          "type": "json"
        }
      ]
    },
    "version": "0.0.0",
    "filename": "src/main/java/com/rs/devplatform/controller/cg/CGController.java",
    "groupTitle": "CG",
    "name": "GetCgFormJsGetlistId"
  },
  {
    "type": "GET",
    "url": "/cg/form/{tableId}",
    "title": "3.查询表单详情",
    "group": "CG",
    "success": {
      "fields": {
        "Success 200": [
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "id",
            "description": "<p>表id</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "tablename",
            "description": "<p>表名字</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "tabledescp",
            "description": "<p>表描述</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "fcflag",
            "description": "<p>功能编码</p>"
          },
          {
            "group": "Success 200",
            "type": "Integer",
            "optional": false,
            "field": "tabletype",
            "description": "<p>表类型(1:单表，2：主表，3：附表)</p>"
          },
          {
            "group": "Success 200",
            "type": "Integer",
            "optional": false,
            "field": "relationType",
            "description": "<p>附表类型(0：一对多，1：一对一)</p>"
          },
          {
            "group": "Success 200",
            "type": "Integer",
            "optional": false,
            "field": "relationSeq",
            "description": "<p>附表顺序号</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "pktype",
            "description": "<p>PK类型(UUID:32位唯一编码)</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "multiselect",
            "description": "<p>是否允许复选(Y: Yes, N: No)</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "ispage",
            "description": "<p>是否分页(Y: Yes, N: No)</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "istree",
            "description": "<p>是否树(Y: Yes, N: No)</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "sql",
            "description": "<p>SQL</p>"
          },
          {
            "group": "Success 200",
            "type": "JSONArray",
            "optional": false,
            "field": "columns",
            "description": "<p>字段</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "columns.id",
            "description": "<p>字段id</p>"
          },
          {
            "group": "Success 200",
            "type": "Integer",
            "optional": false,
            "field": "columns.colSeq",
            "description": "<p>字段序号</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "columns.colName",
            "description": "<p>字段名称</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "columns.colMark",
            "description": "<p>字段备注</p>"
          },
          {
            "group": "Success 200",
            "type": "Integer",
            "optional": false,
            "field": "columns.colLen",
            "description": "<p>字段长度</p>"
          },
          {
            "group": "Success 200",
            "type": "Integer",
            "optional": false,
            "field": "columns.colDecpoint",
            "description": "<p>字段小数点位数</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "columns.colDefval",
            "description": "<p>字段默认值</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "columns.colType",
            "description": "<p>字段类型</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "columns.colIspk",
            "description": "<p>字段是否主键，Y:是主键</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "columns.colIsnull",
            "description": "<p>字段是否允许空值，Y:可为空</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "columns.isFormdisp",
            "description": "<p>字段是否表单显示，Y:为显示在表单</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "columns.isGriddisp",
            "description": "<p>字段是否列表显示，Y:为显示在列表</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "columns.inputType",
            "description": "<p>字段控件类型</p>"
          },
          {
            "group": "Success 200",
            "type": "Integer",
            "optional": false,
            "field": "columns.inputLen",
            "description": "<p>字段控件长度</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "columns.isSearch",
            "description": "<p>字段是否查询，Y:该字段可作为查询条件</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "columns.searchType",
            "description": "<p>字段查询类型，normal:普通查询，range:范围查询</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "columns.extjson",
            "description": "<p>字段扩展参数</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "columns.version",
            "description": "<p>字段version</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "columns.colHref",
            "description": "<p>字段Href</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "columns.validType",
            "description": "<p>验证规则</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "columns.dictTable",
            "description": "<p>字典Table</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "columns.dictCode",
            "description": "<p>字典Code</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "columns.dictText",
            "description": "<p>字典Text</p>"
          },
          {
            "group": "Success 200",
            "type": "JSONArray",
            "optional": false,
            "field": "conditions",
            "description": "<p>条件（只有SQL的情况下才有）</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "conditions.id",
            "description": "<p>条件字段id</p>"
          },
          {
            "group": "Success 200",
            "type": "Integer",
            "optional": false,
            "field": "conditions.colSeq",
            "description": "<p>条件字段序号</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "conditions.colName",
            "description": "<p>条件字段名称</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "conditions.colMark",
            "description": "<p>条件字段备注</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "conditions.colType",
            "description": "<p>条件字段类型</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "conditions.inputType",
            "description": "<p>字段控件类型</p>"
          },
          {
            "group": "Success 200",
            "type": "Integer",
            "optional": false,
            "field": "conditions.inputLen",
            "description": "<p>字段控件长度</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "conditions.isSearch",
            "description": "<p>字段是否查询，Y</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "conditions.searchType",
            "description": "<p>字段查询类型，normal:普通查询，range:范围查询</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "conditions.version",
            "description": "<p>字段version</p>"
          }
        ]
      },
      "examples": [
        {
          "title": "Sample:",
          "content": "\tHTTP/1.1 200 OK\n{\n  \"multiselect\":\"Y\",\"tabletype\":1,\"tabledescp\":\"ju table -2657\",\"fcflag\": \"tbl_ju9272\",\n  \"id\":\"1e5b01331128477bb21fa917b4c52ea8\",\"isdbsyn\":\"Y\",\"pktype\":\"UUID\",\"tablename\":\"tbl_ju-2657\",\"version\":1,\"istree\":\"N\",\n  \"columns\":[\n    {\"colSeq\":1,\"colIspk\":\"Y\",\"validType\":\"validType\",\"colIsnull\":\"\",\"isGriddisp\":\"\",\"isFormdisp\":\"\",\"colLen\":10,\"dictCode\":\"dictCode\",\"isSearch\":\"\",\"colType\":\"string\",\"inputType\":\"text\",\"id\":\"e5824ff05de6449290448742e714d3cb\",\"dictText\":\"dictText\",\"colDefval\":\"\",\"colName\":\"name_0\",\"colMark\":\"mark_0\",\"inputLen\":100,\"searchType\":\"normal\",\"dictTable\":\"dictTable\",\"deleted\":\"F\",\"colDecpoint\":0},\n    {\"colSeq\":2,\"colIspk\":\"\",\"validType\":\"validType\",\"colIsnull\":\"Y\",\"isGriddisp\":\"Y\",\"isFormdisp\":\"Y\",\"colLen\":11,\"dictCode\":\"dictCode\",\"isSearch\":\"Y\",\"colType\":\"string\",\"inputType\":\"text\",\"id\":\"e911e5efe0a54e829939e691e3880e4d\",\"dictText\":\"dictText\",\"colDefval\":\"\",\"colName\":\"name_1\",\"colMark\":\"mark_1\",\"inputLen\":100,\"searchType\":\"range\",\"dictTable\":\"dictTable\",\"deleted\":\"F\",\"colDecpoint\":0},\n  ],\n  \"conditions\": [{\n  \"colSeq\": 1,\n  \"isGriddisp\": \"N\",\n  \"isFormdisp\": \"N\",\n  \"issearch\": \"Y\",\n  \"colType\": \"string\",\n  \"inputType\": \"text\",\n  \"id\": \"f4b4de173a7e4611a1843009f1a2b112\",\n  \"colName\": \"abc\",\n  \"colMark\": \"mark0\",\n  \"searchType\": \"fuzzy\"\n },\n {\n  \"colSeq\": 2,\n  \"isGriddisp\": \"Y\",\n  \"isFormdisp\": \"Y\",\n  \"issearch\": \"Y\",\n  \"colType\": \"string\",\n  \"inputType\": \"text\",\n  \"id\": \"00239a735d984fd48674200d6c63b28a\",\n  \"colName\": \"password\",\n  \"colMark\": \"mark1\",\n  \"searchType\": \"fuzzy\"\n },\n {\n  \"colSeq\": 3,\n  \"isGriddisp\": \"N\",\n  \"isFormdisp\": \"N\",\n  \"issearch\": \"Y\",\n  \"colType\": \"int\",\n  \"inputType\": \"text\",\n  \"id\": \"a3ec22545d79444290d3da9e50319135\",\n  \"colName\": \"newColumn\",\n  \"colMark\": \"newColumn mark\",\n  \"inputLen\": 10,\n  \"searchType\": \"range\"\n }],\n  }",
          "type": "json"
        }
      ]
    },
    "version": "0.0.0",
    "filename": "src/main/java/com/rs/devplatform/controller/cg/CGController.java",
    "groupTitle": "CG",
    "name": "GetCgFormTableid"
  },
  {
    "type": "GET",
    "url": "/cg/form/{tableId}/buttons",
    "title": "获取表单自定义按钮",
    "group": "CG",
    "success": {
      "fields": {
        "Success 200": [
          {
            "group": "Success 200",
            "type": "JsonArray",
            "optional": false,
            "field": "rows",
            "description": "<p>JSONArray</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "rows.id",
            "description": "<p>自定义按钮主键</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "rows.code",
            "description": "<p>自定义按钮编码</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "rows.icon",
            "description": "<p>自定义按钮图标</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "rows.name",
            "description": "<p>自定义按钮名字</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "rows.zt",
            "description": "<p>自定义按钮状态</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "rows.ys",
            "description": "<p>自定义按钮样式</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "rows.exp",
            "description": "<p>自定义按钮过期时间</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "rows.bdid",
            "description": "<p>表单ID</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "rows.czlx",
            "description": "<p>按钮操作类型</p>"
          },
          {
            "group": "Success 200",
            "type": "Integer",
            "optional": false,
            "field": "rows.xh",
            "description": "<p>按钮序号</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "rows.whr",
            "description": "<p>维护人名字</p>"
          },
          {
            "group": "Success 200",
            "type": "Date",
            "optional": false,
            "field": "rows.whsj",
            "description": "<p>维护时间</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "rows.whrid",
            "description": "<p>维护人ID</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "rows.cjr",
            "description": "<p>创建人名字</p>"
          },
          {
            "group": "Success 200",
            "type": "Date",
            "optional": false,
            "field": "rows.cjsj",
            "description": "<p>创建时间</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "rows.cjrid",
            "description": "<p>创建人ID</p>"
          },
          {
            "group": "Success 200",
            "type": "Integer",
            "optional": false,
            "field": "rows.version",
            "description": "<p>数据记录版本</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "rows.deleted",
            "description": "<p>逻辑删除标记位</p>"
          }
        ]
      },
      "examples": [
        {
          "title": "Success-Response:",
          "content": " HTTP/1.1 200 OK\n\t{\"rows\":[\n\t \t\t{\n\t\t\t\"id\" : \"自定义按钮主键\",\t\n\t\t\t\"code\" : \"自定义按钮编码\",\t\n\t\t\t\"icon\" : \"自定义按钮图标\",\t\n\t\t\t\"name\" : \"自定义按钮名字\",\t\n\t\t\t\"zt\" : \"自定义按钮状态\",\t\n\t\t\t\"ys\" : \"自定义按钮样式\",\t\n\t\t\t\"exp\" : \"自定义按钮过期时间\",\t\n\t\t\t\"bdid\" : \"表单ID\",\t\n\t\t\t\"czlx\" : \"按钮操作类型\",\t\n\t\t\t\"xh\" : \"按钮序号\",\t\n\t\t\t\"whr\" : \"维护人名字\",\t\n\t\t\t\"whsj\" : \"维护时间\",\t\n\t\t\t\"whrid\" : \"维护人ID\",\t\n\t\t\t\"cjr\" : \"创建人名字\",\t\n\t\t\t\"cjsj\" : \"创建时间\",\t\n\t\t\t\"cjrid\" : \"创建人ID\",\t\n\t\t\t\"version\" : \"数据记录版本\",\t\n\t\t\t\"deleted\" : \"逻辑删除标记位\",\t\n\t\t\t}\n\t \t]}",
          "type": "json"
        }
      ]
    },
    "version": "0.0.0",
    "filename": "src/main/java/com/rs/devplatform/controller/cg/CGController.java",
    "groupTitle": "CG",
    "name": "GetCgFormTableidButtons"
  },
  {
    "type": "GET",
    "url": "/cg/form/{tablename}/columns",
    "title": "7.根据表名获取字段",
    "group": "CG",
    "description": "<p>ID和VERSION会被过滤掉</p>",
    "success": {
      "examples": [
        {
          "title": "Success-Response:",
          "content": " HTTP/1.1 200 OK\n{\n \"total\": 5,\n \"rows\": [{\n  \"colName\": \"COLUMNS_NAME_1\",\n  \"colMark\": \"mark_1\",\n  \"colType\": \"string\"\n  \"colIsnull\": \"\"\n },\n {\n  \"colName\": \"COLUMNS_NAME_4\",\n  \"colMark\": \"mark_4\",\n  \"colType\": \"string\"\n },\n {\n  \"colName\": \"COLUMNS_NAME_3\",\n  \"colMark\": \"mark_3\",\n  \"colType\": \"string\"\n },\n {\n  \"colName\": \"COLUMNS_NAME_2\",\n  \"colMark\": \"mark_2\",\n  \"colType\": \"string\"\n },\n {\n  \"colName\": \"COLUMNS_NAME_0\",\n  \"colMark\": \"mark_0\",\n  \"colType\": \"string\"\n }]\n}",
          "type": "json"
        }
      ]
    },
    "version": "0.0.0",
    "filename": "src/main/java/com/rs/devplatform/controller/cg/CGController.java",
    "groupTitle": "CG",
    "name": "GetCgFormTablenameColumns"
  },
  {
    "type": "GET",
    "url": "/cg/formlist",
    "title": "1.跳转表单list页面",
    "group": "CG",
    "success": {
      "examples": [
        {
          "title": "Sample:",
          "content": "{\"page\":\"cg/formlist.html\"}",
          "type": "json"
        }
      ]
    },
    "version": "0.0.0",
    "filename": "src/main/java/com/rs/devplatform/controller/cg/CGController.java",
    "groupTitle": "CG",
    "name": "GetCgFormlist"
  },
  {
    "type": "POST",
    "url": "/cg/form",
    "title": "4.新增表单",
    "description": "<p>当用树形展示时必须填入父/子节点对应的ID，并且不能相同，父/子节点的来源是columns</p>",
    "group": "CG",
    "parameter": {
      "fields": {
        "Parameter": [
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "sql",
            "description": "<p>SQL语句（不是必须的）</p>"
          },
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "tablename",
            "description": "<p>主表名称（<strong>必填</strong>）</p>"
          },
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "tabledescp",
            "description": "<p>主表名称（<strong>必填</strong>）</p>"
          },
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "pktype",
            "description": "<p>主表主键类型（<strong>必填</strong>）</p>"
          },
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "fcflag",
            "description": "<p>功能编码（<strong>必填</strong>）</p>"
          },
          {
            "group": "Parameter",
            "type": "Integer",
            "optional": false,
            "field": "relationType",
            "description": "<p>附表类型(0：一对多，1：一对一)</p>"
          },
          {
            "group": "Parameter",
            "type": "Integer",
            "optional": false,
            "field": "relationSeq",
            "description": "<p>附表顺序号</p>"
          },
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "multiselect",
            "description": "<p>是否允许复选(Y: Yes, N: No)</p>"
          },
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "ispage",
            "description": "<p>是否分页(Y: Yes, N: No)</p>"
          },
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "istree",
            "description": "<p>是否树(Y: Yes, N: No)</p>"
          },
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "child",
            "description": "<p>子节点对应的字段</p>"
          },
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "parent",
            "description": "<p>父节点对应的字段</p>"
          },
          {
            "group": "Parameter",
            "type": "JSONArray",
            "optional": false,
            "field": "columns",
            "description": "<p>查询字段列表</p>"
          },
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "columns.column",
            "description": "<p>查询字段名</p>"
          },
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "columns.alias",
            "description": "<p>查询字段别名,SQL中出现的AS后面的值</p>"
          },
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "columns.descp",
            "description": "<p>查询字段描述(数据列名)</p>"
          },
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "columns.colName",
            "description": "<p>字段名称（<strong>必填</strong>）</p>"
          },
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "columns.colMark",
            "description": "<p>字段备注（<strong>必填</strong>）</p>"
          },
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "columns.colDefval",
            "description": "<p>字段默认值</p>"
          },
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "columns.colType",
            "description": "<p>字段类型(string/int/double/date/decimal/text/blob)（<strong>必填</strong>）</p>"
          },
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "columns.colIspk",
            "description": "<p>字段是否主键，Y:是主键</p>"
          },
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "columns.isFormdisp",
            "description": "<p>字段是否表单显示，Y:为显示在表单</p>"
          },
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "columns.isGriddisp",
            "description": "<p>字段是否列表显示，<strong>默认Y</strong>:为显示在列表</p>"
          },
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "columns.colIsnull",
            "description": "<p>字段是否允许空值，Y:可为空</p>"
          },
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "columns.inputType",
            "description": "<p>字段控件类型(text/password/radio/checkbox/date/datetime/file/textarea/list/popup/image/umeditor)</p>"
          },
          {
            "group": "Parameter",
            "type": "Integer",
            "optional": false,
            "field": "columns.inputLen",
            "description": "<p>字段控件长度</p>"
          },
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "columns.isSearch",
            "description": "<p>字段是否查询，Y:该字段可作为查询条件</p>"
          },
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "columns.searchType",
            "description": "<p>字段查询类型，normal:普通查询，range:范围查询，fuzzy:模糊查询</p>"
          },
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "columns.validType",
            "description": "<p>验证规则</p>"
          },
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "columns.dictTable",
            "description": "<p>字典Table</p>"
          },
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "columns.dictCode",
            "description": "<p>字典Code</p>"
          },
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "columns.dictText",
            "description": "<p>字典Text</p>"
          },
          {
            "group": "Parameter",
            "type": "JSONArray",
            "optional": false,
            "field": "subtables",
            "description": "<p>子表管理关系列表</p>"
          },
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "subtables.tablename",
            "description": "<p>子表名称（<strong>subtables有记录时必填</strong>）</p>"
          },
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "subtables.colName",
            "description": "<p>子表关联字段（<strong>subtables有记录时必填</strong>）</p>"
          },
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "subtables.mainColName",
            "description": "<p>主表关联字段（<strong>subtables有记录时必填</strong>）</p>"
          },
          {
            "group": "Parameter",
            "type": "JSONArray",
            "optional": false,
            "field": "conditions",
            "description": "<p>查询条件(SQL)</p>"
          },
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "conditions.colName",
            "description": "<p>字段名称（<strong>必填</strong>）</p>"
          },
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "conditions.colMark",
            "description": "<p>字段备注（<strong>必填</strong>）</p>"
          },
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "conditions.colType",
            "description": "<p>字段类型(string/int/double/date/decimal/text/blob)（<strong>必填</strong>）</p>"
          },
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "conditions.inputType",
            "description": "<p>字段控件类型(text/password/radio/checkbox/date/datetime/file/textarea/list/popup/image/umeditor)</p>"
          },
          {
            "group": "Parameter",
            "type": "Integer",
            "optional": false,
            "field": "conditions.inputLen",
            "description": "<p>字段控件长度</p>"
          },
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "conditions.searchType",
            "description": "<p>字段查询类型，normal:普通查询，range:范围查询，fuzzy:模糊查询</p>"
          },
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "conditions.expr",
            "description": "<p>条件表达式</p>"
          }
        ]
      },
      "examples": [
        {
          "title": "Request-Example: ",
          "content": "{\n \"tabledescp\": \"test\",\n \"fcflag\": \"unionFuncID\",\n \"istree\":\"Y\",\n \"child\":\"username\", \n \"parent\":\"password\",\n \"columns\": [{\n  \"colIspk\": \"Y\",\n  \"colIsnull\": \"N\",\n  \"isFormdisp\": \"N\",\n  \"isgriddisp\": \"Y\",\n  \"isSearch\": \"N\",\n  \"colType\": \"string\",\n  \"alias\": \"name\",\n  \"inputType\": \"text\",\n  \"colName\": \"username\",\n  \"colMark\": \"mark0\"\n },\n {\n  \"colIsnull\": \"Y\",\n  \"isFormdisp\": \"Y\",\n  \"isgriddisp\": \"Y\",\n  \"isSearch\": \"Y\",\n  \"colType\": \"string\",\n  \"alias\": \"\",\n  \"inputType\": \"text\",\n  \"colName\": \"password\",\n  \"colMark\": \"mark1\",\n  \"searchType\": \"fuzzy\"\n },\n {\n  \"colIsnull\": \"Y\",\n  \"isFormdisp\": \"Y\",\n  \"isgriddisp\": \"Y\",\n  \"isSearch\": \"Y\",\n  \"colType\": \"string\",\n  \"alias\": \"\",\n  \"inputType\": \"text\",\n  \"colName\": \"dept.dept_name\",\n  \"colMark\": \"mark2\",\n  \"searchType\": \"fuzzy\"\n }],\n \"subtables\":[{\"colName\":\"subcolname_0\",\"mainColName\":\"maincol1\",\"tablename\":\"subtable0\"},{\"colName\":\"subcolname_1\",\"mainColName\":\"main_col2\",\"tablename\":\"subtable0\"}],\n \"conditions\": [{\n  \"issearch\": \"Y\",\n  \"expr\": \"username = #{abc}\",\n  \"inputType\": \"text\",\n  \"colName\": \"abc\",\n  \"colMark\": \"mark0\",\n  \"searchType\": \"fuzzy\"\n }],\n \"pktype\": \"UUID\",\n \"tablename\": \"user\",\n \"sql\": \"select username as name, password, dept.dept_name from user, sys_dept dept where dept.dept_id=user.dept_id and  username=#{abc} and password='123456'\"\n}",
          "type": "json"
        }
      ]
    },
    "success": {
      "fields": {
        "Success 200": [
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "id",
            "description": "<p>表ID</p>"
          },
          {
            "group": "Success 200",
            "type": "Integer",
            "optional": false,
            "field": "version",
            "description": "<p>表version</p>"
          }
        ]
      },
      "examples": [
        {
          "title": "Sample:",
          "content": "HTTP/1.1 200 OK\n{\n  \"id\":\"1234567\",\n  \"version\":1\n}",
          "type": "json"
        }
      ]
    },
    "error": {
      "examples": [
        {
          "title": "Error-Response:",
          "content": "HTTP/1.1 400 Bad Request\n{\n  \"verrors\":[\n    {\"errCode\":\"sql\",\"errorMsg\":\"SQL不能为空\"},\n    {\"errCode\":\"sql\",\"errorMsg\":\"SQL格式错误\"},\n    {\"errCode\":\"tablename\",\"errorMsg\":\"表名不能为空\"},\n    {\"errCode\":\"tabledescp\",\"errorMsg\":\"表描述不能为空\"},\n    {\"errCode\":\"pktype\",\"errorMsg\":\"主键类型不能为空\"},\n    {\"errCode\":\"columns\",\"errorMsg\":\"数据列不能为空\"},\n    {\"errCode\":\"tree\",\"errorMsg\":\"树形展示不能不设置父节点和子节点\"},\n    {\"errCode\":\"tree\",\"errorMsg\":\"父子节点不能相同\"},\n    {\"errCode\":\"child\",\"errorMsg\":\"子节点在字段中不存在\"},\n    {\"errCode\":\"parent\",\"errorMsg\":\"父节点在字段中不存在\"}\n    ]\n}",
          "type": "json"
        },
        {
          "title": "Error-Response:",
          "content": "HTTP/1.1 302 Duplicate record\n{\n  \"verrors\":[\n    {\"errCode\":\"fcflag\",\"errorMsg\":\"功能编码重复\"},\n    ]\n}",
          "type": "json"
        }
      ]
    },
    "version": "0.0.0",
    "filename": "src/main/java/com/rs/devplatform/controller/cg/CGController.java",
    "groupTitle": "CG",
    "name": "PostCgForm"
  },
  {
    "type": "POST",
    "url": "/cg/form/custbt",
    "title": "增加自定义按钮",
    "group": "CG",
    "parameter": {
      "fields": {
        "Parameter": [
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "code",
            "description": "<p>自定义按钮编码</p>"
          },
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "icon",
            "description": "<p>自定义按钮图标</p>"
          },
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "name",
            "description": "<p>自定义按钮名字</p>"
          },
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "zt",
            "description": "<p>自定义按钮状态</p>"
          },
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "ys",
            "description": "<p>自定义按钮样式</p>"
          },
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "exp",
            "description": "<p>自定义按钮过期时间</p>"
          },
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "bdid",
            "description": "<p>表单ID</p>"
          },
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "czlx",
            "description": "<p>操作类型  js/action</p>"
          },
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "xh",
            "description": "<p>序号</p>"
          }
        ]
      },
      "examples": [
        {
          "title": "Request-Example: ",
          "content": "{\"xh\":1,\"code\":\"1\",\"bdid\":\"1\",\"icon\":\"icon1\",\"name\":\"name1\",\"zt\":\"zt\",\"ys\":\"zt\",\"exp\":1486704078740,\"czlx\":\"js\"}",
          "type": "json"
        }
      ]
    },
    "success": {
      "fields": {
        "Success 200": [
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "id",
            "description": "<p>返回增强JS id</p>"
          }
        ]
      },
      "examples": [
        {
          "title": "Success-Response:",
          "content": "HTTP/1.1 200 OK\n{\"id\":\"5179ff002fe6456b866bed0f25943faf\"}",
          "type": "json"
        }
      ]
    },
    "error": {
      "examples": [
        {
          "title": "Error-Response:",
          "content": "HTTP/1.1 302 Found",
          "type": "json"
        },
        {
          "title": "Error-Response:",
          "content": "HTTP/1.1 409 Conflict",
          "type": "json"
        }
      ]
    },
    "version": "0.0.0",
    "filename": "src/main/java/com/rs/devplatform/controller/cg/CGController.java",
    "groupTitle": "CG",
    "name": "PostCgFormCustbt"
  },
  {
    "type": "POST",
    "url": "/cg/form/custbt",
    "title": "增加自定义按钮",
    "group": "CG",
    "parameter": {
      "fields": {
        "Parameter": [
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "code",
            "description": "<p>自定义按钮编码</p>"
          },
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "icon",
            "description": "<p>自定义按钮图标</p>"
          },
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "name",
            "description": "<p>自定义按钮名字</p>"
          },
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "zt",
            "description": "<p>自定义按钮状态</p>"
          },
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "ys",
            "description": "<p>自定义按钮样式</p>"
          },
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "exp",
            "description": "<p>自定义按钮过期时间</p>"
          },
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "bdid",
            "description": "<p>表单ID</p>"
          },
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "czlx",
            "description": "<p>操作类型  js/action</p>"
          },
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "xh",
            "description": "<p>序号</p>"
          }
        ]
      },
      "examples": [
        {
          "title": "Request-Example: ",
          "content": "{\"xh\":1,\"code\":\"1\",\"bdid\":\"1\",\"icon\":\"icon1\",\"name\":\"update\",\"zt\":\"zt\",\"ys\":\"ys\",\"exp\":1486704365030,\"czlx\":\"js\"}",
          "type": "json"
        }
      ]
    },
    "success": {
      "fields": {
        "Success 200": [
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "id",
            "description": "<p>返回增强JS id</p>"
          }
        ]
      },
      "examples": [
        {
          "title": "Success-Response:",
          "content": "HTTP/1.1 200 OK\n{\"id\":\"5179ff002fe6456b866bed0f25943faf\"}",
          "type": "json"
        }
      ]
    },
    "error": {
      "examples": [
        {
          "title": "Error-Response:",
          "content": "HTTP/1.1 302 Found",
          "type": "json"
        },
        {
          "title": "Error-Response:",
          "content": "HTTP/1.1 409 Conflict",
          "type": "json"
        }
      ]
    },
    "version": "0.0.0",
    "filename": "src/main/java/com/rs/devplatform/controller/cg/CGController.java",
    "groupTitle": "CG",
    "name": "PostCgFormCustbt"
  },
  {
    "type": "POST",
    "url": "/cg/form/js",
    "title": "增加增强js",
    "group": "CG",
    "parameter": {
      "fields": {
        "Parameter": [
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "bdid",
            "description": "<p>表单id</p>"
          },
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "js",
            "description": "<p>增强js代码</p>"
          },
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "jslx",
            "description": "<p>增强js类型</p>"
          },
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "nr",
            "description": "<p>内容</p>"
          }
        ]
      },
      "examples": [
        {
          "title": "Request-Example: ",
          "content": "{\"bdid\":\"1\",\"jslx\":\"form\",\"nr\":\"zqjsnr\",\"js\":\"zqjs\"}",
          "type": "json"
        }
      ]
    },
    "success": {
      "fields": {
        "Success 200": [
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "id",
            "description": "<p>返回增强JS id</p>"
          }
        ]
      },
      "examples": [
        {
          "title": "Success-Response:",
          "content": "HTTP/1.1 200 OK\n{\"id\":\"5179ff002fe6456b866bed0f25943faf\"}",
          "type": "json"
        }
      ]
    },
    "error": {
      "examples": [
        {
          "title": "Error-Response:",
          "content": "HTTP/1.1 302 Found",
          "type": "json"
        },
        {
          "title": "Error-Response:",
          "content": "HTTP/1.1 409 Conflict",
          "type": "json"
        }
      ]
    },
    "version": "0.0.0",
    "filename": "src/main/java/com/rs/devplatform/controller/cg/CGController.java",
    "groupTitle": "CG",
    "name": "PostCgFormJs"
  },
  {
    "type": "POST",
    "url": "/cg/form/list",
    "title": "2.查询表单list",
    "group": "CG",
    "parameter": {
      "fields": {
        "Parameter": [
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "tablename",
            "description": "<p>表名字(模糊查询)</p>"
          },
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "fcflag",
            "description": "<p>功能编码(模糊查询)</p>"
          },
          {
            "group": "Parameter",
            "type": "Integer",
            "optional": false,
            "field": "tabletype",
            "description": "<p>表单类型(1:单表，2：主表，3：附表)</p>"
          }
        ]
      },
      "examples": [
        {
          "title": "Request-Example: ",
          "content": "{\"tablename\":\"test\", \"tabletype\":1}",
          "type": "json"
        }
      ]
    },
    "success": {
      "fields": {
        "Success 200": [
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "id",
            "description": "<p>表单id</p>"
          },
          {
            "group": "Success 200",
            "type": "Integer",
            "optional": false,
            "field": "version",
            "description": "<p>表单version</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "tablename",
            "description": "<p>表名称</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "tabledescp",
            "description": "<p>表描述</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "fcflag",
            "description": "<p>功能编码</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "tabletype",
            "description": "<p>表单类型(1:单表，2：主表，3：附表)</p>"
          }
        ]
      },
      "examples": [
        {
          "title": "Sample:",
          "content": "HTTP/1.1 200 OK\n{\n\t\"total\":2,\n\t\t\"records\":[\n\t\t\t{\"tabletype\":1,\"tabledescp\":\"ju table 2674\",\"fcflag\":\"tbl_ju1510\",\"formtype\":\"N\",\"id\":\"ec9ec852205c4c288e320ff6f029fc6f\",\"tablename\":\"tbl_ju_2674\",\"version\":1},\n\t\t\t{\"tabletype\":1,\"tabledescp\":\"ju table 1970\",\"fcflag\":\"tbl_ju1510\",\"formtype\":\"N\",\"id\":\"fd5a58d8f09c41549f4bbd4131a5d990\",\"tablename\":\"tbl_ju_1970\",\"version\":1}\n\t\t]\n\t}",
          "type": "json"
        }
      ]
    },
    "version": "0.0.0",
    "filename": "src/main/java/com/rs/devplatform/controller/cg/CGController.java",
    "groupTitle": "CG",
    "name": "PostCgFormList"
  },
  {
    "type": "PUT",
    "url": "/cg/form/js",
    "title": "更新增强js",
    "group": "CG",
    "parameter": {
      "fields": {
        "Parameter": [
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "bdid",
            "description": "<p>表单id</p>"
          },
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "js",
            "description": "<p>增强js代码</p>"
          },
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "jslx",
            "description": "<p>增强js类型</p>"
          },
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "nr",
            "description": "<p>内容</p>"
          }
        ]
      },
      "examples": [
        {
          "title": "Request-Example: ",
          "content": "{\"bdid\":\"1\",\"jslx\":\"form\",\"nr\":\"zqjsnr\",\"js\":\"zqjs\"}",
          "type": "json"
        }
      ]
    },
    "success": {
      "fields": {
        "Success 200": [
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "id",
            "description": "<p>返回增强JS id</p>"
          }
        ]
      },
      "examples": [
        {
          "title": "Success-Response:",
          "content": "HTTP/1.1 200 OK\n{\"id\":\"5179ff002fe6456b866bed0f25943faf\"}",
          "type": "json"
        }
      ]
    },
    "error": {
      "examples": [
        {
          "title": "Error-Response:",
          "content": "HTTP/1.1 302 Found",
          "type": "json"
        },
        {
          "title": "Error-Response:",
          "content": "HTTP/1.1 409 Conflict",
          "type": "json"
        }
      ]
    },
    "version": "0.0.0",
    "filename": "src/main/java/com/rs/devplatform/controller/cg/CGController.java",
    "groupTitle": "CG",
    "name": "PutCgFormJs"
  },
  {
    "type": "PUT",
    "url": "/cg/form/{tableId}",
    "title": "5.更新表单",
    "description": "<p>当用树形展示时必须填入父/子节点对应的ID，并且不能相同，父/子节点的来源是columns</p>",
    "group": "CG",
    "parameter": {
      "fields": {
        "Parameter": [
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "tabledescp",
            "description": "<p>表描述(<strong>必填</strong>)</p>"
          },
          {
            "group": "Parameter",
            "type": "Integer",
            "optional": false,
            "field": "tabletype",
            "description": "<p>表类型(1:单表，2：主表，3：附表)(<strong>必填</strong>)</p>"
          },
          {
            "group": "Parameter",
            "type": "Integer",
            "optional": false,
            "field": "relationType",
            "description": "<p>附表类型(0：一对多，1：一对一)(<strong>必填</strong>)</p>"
          },
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "pktype",
            "description": "<p>PK类型(UUID:32位唯一编码)(<strong>必填</strong>)</p>"
          },
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "multiselect",
            "description": "<p>是否允许复选(Y: Yes, N: No)</p>"
          },
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "ispage",
            "description": "<p>是否分页(Y: Yes, N: No)</p>"
          },
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "istree",
            "description": "<p>是否树(Y: Yes, N: No)</p>"
          },
          {
            "group": "Parameter",
            "type": "Integer",
            "optional": false,
            "field": "version",
            "description": "<p>表version(<strong>必填</strong>)</p>"
          },
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "child",
            "description": "<p>子节点对应的字段</p>"
          },
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "parent",
            "description": "<p>父节点对应的字段</p>"
          },
          {
            "group": "Parameter",
            "type": "JSONArray",
            "optional": false,
            "field": "columns",
            "description": "<p>字段(<strong>必填</strong>)</p>"
          },
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "columns.id",
            "description": "<p>字段id,新增的为空</p>"
          },
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "columns.colName",
            "description": "<p>字段名称(<strong>必填</strong>)</p>"
          },
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "columns.colMark",
            "description": "<p>字段备注(<strong>必填</strong>)</p>"
          },
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "columns.colType",
            "description": "<p>字段类型(<strong>必填</strong>)</p>"
          },
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "columns.colIspk",
            "description": "<p>字段是否主键，Y:是主键</p>"
          },
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "columns.colIsnull",
            "description": "<p>字段是否允许空值，Y:可为空</p>"
          },
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "columns.isFormdisp",
            "description": "<p>字段是否表单显示，Y:为显示在表单</p>"
          },
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "columns.isGriddisp",
            "description": "<p>字段是否列表显示，Y:为显示在列表</p>"
          },
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "columns.inputType",
            "description": "<p>字段控件类型</p>"
          },
          {
            "group": "Parameter",
            "type": "Integer",
            "optional": false,
            "field": "columns.inputLen",
            "description": "<p>字段控件长度</p>"
          },
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "columns.isSearch",
            "description": "<p>字段是否查询，Y:该字段可作为查询条件</p>"
          },
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "columns.searchType",
            "description": "<p>字段查询类型，normal:普通查询，range:范围查询</p>"
          },
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "columns.validType",
            "description": "<p>验证规则</p>"
          },
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "columns.dictTable",
            "description": "<p>字典Table</p>"
          },
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "columns.dictCode",
            "description": "<p>字典Code</p>"
          },
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "columns.dictText",
            "description": "<p>字典Text</p>"
          },
          {
            "group": "Parameter",
            "type": "JSONArray",
            "optional": false,
            "field": "subtables",
            "description": "<p>子表管理关系列表</p>"
          },
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "subtables.tablename",
            "description": "<p>子表名称（<strong>subtables有记录时必填</strong>）</p>"
          },
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "subtables.colName",
            "description": "<p>子表关联字段（<strong>subtables有记录时必填</strong>）</p>"
          },
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "subtables.mainColName",
            "description": "<p>主表关联字段（<strong>subtables有记录时必填</strong>）</p>"
          },
          {
            "group": "Parameter",
            "type": "JSONArray",
            "optional": false,
            "field": "conditions",
            "description": "<p>查询条件(SQL情况)</p>"
          },
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "conditions.colName",
            "description": "<p>字段名称(<strong>必填</strong>)</p>"
          },
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "conditions.colMark",
            "description": "<p>字段备注(<strong>必填</strong>)</p>"
          },
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "conditions.colType",
            "description": "<p>字段类型(string/int/double/date/decimal/text/blob)(<strong>必填</strong>)</p>"
          },
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "conditions.inputType",
            "description": "<p>字段控件类型(text/password/radio/checkbox/date/datetime/file/textarea/list/popup/image/umeditor)</p>"
          },
          {
            "group": "Parameter",
            "type": "Integer",
            "optional": false,
            "field": "conditions.inputLen",
            "description": "<p>字段控件长度</p>"
          },
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "conditions.searchType",
            "description": "<p>字段查询类型，normal:普通查询，range:范围查询，fuzzy:模糊查询</p>"
          }
        ]
      },
      "examples": [
        {
          "title": "Request-Example: ",
          "content": "PUT /cg/form/abc\n{\n\"tabledescp\":\"upd tabledescp\",\"version\":1,\n  \"columns\":[\n    {\"colIsnull\":\"\",\"colIspk\":\"Y\",\"colLen\":10,\"colMark\":\"mark_0\",\"colName\":\"name_0\", \"colType\":\"string\",\"deleted\":\"F\",\"dictCode\":\"dictCode\",\"dictTable\":\"dictTable\",\"dictText\":\"dictText\",\"id\":\"3db92d77287c46bfb3bb44d753c8d32b\",\"inputLen\":100,\"inputType\":\"text\",\"isFormdisp\":\"\",\"isGriddisp\":\"\",\"isSearch\":\"\",\"searchType\":\"normal\",\"validType\":\"validType\"},\n    {\"colIsnull\":\"Y\",\"colIspk\":\"\",\"colLen\":12,\"colMark\":\"mark_2\",\"colName\":\"name_2\", \"colType\":\"string\",\"deleted\":\"F\",\"dictCode\":\"dictCode\",\"dictTable\":\"dictTable\",\"dictText\":\"dictText\",\"id\":\"cc5d50274b8d44039131131eba59c825\",\"inputLen\":100,\"inputType\":\"text\",\"isFormdisp\":\"Y\",\"isGriddisp\":\"Y\",\"isSearch\":\"Y\",\"searchType\":\"normal\",\"validType\":\"validType\"},\n    {\"colIsnull\":\"Y\",\"colIspk\":\"\",\"colLen\":14,\"colMark\":\"mark_4\",\"colName\":\"name_4\",  \"colType\":\"string\",\"deleted\":\"F\",\"dictCode\":\"dictCode\",\"dictTable\":\"dictTable\",\"dictText\":\"dictText\",\"id\":\"b45b25543d5c427192ccaf092efc1fa8\",\"inputLen\":100,\"inputType\":\"text\",\"isFormdisp\":\"Y\",\"isGriddisp\":\"Y\",\"isSearch\":\"Y\",\"searchType\":\"normal\",\"validType\":\"validType\"},\n    {\"colName\":\"name_0\",\"colIspk\":\"Y\",\"validType\":\"validType2\",\"colMark\":\"mark_0\",\"colIsnull\":\"\",\"inputLen\":100,\"searchType\":\"normal\",\"isGriddisp\":\"\",\"extjson\":\"\",\"dictTable\":\"dictTable2\",\"isFormdisp\":\"\", \"isSearch\":\"\",\"colType\":\"string\",\"inputType\":\"text\",\"dictText\":\"dictText2\",\"colDefval\":\"\"},\n    {\"colName\":\"name_1\",\"colIspk\":\"\",\"validType\":\"validType2\",\"colMark\":\"mark_1\",\"colIsnull\":\"Y\",\"inputLen\":100,\"searchType\":\"range\",\"isGriddisp\":\"Y\",\"extjson\":\"\",\"dictTable\":\"dictTable2\",\"isFormdisp\":\"Y\", \"isSearch\":\"Y\",\"colType\":\"string\",\"inputType\":\"text\",\"dictText\":\"dictText2\",\"colDefval\":\"\"},\n    {\"colName\":\"name_2\",\"colIspk\":\"\",\"validType\":\"validType2\",\"colMark\":\"mark_2\",\"colIsnull\":\"Y\",\"inputLen\":100,\"searchType\":\"normal\",\"isGriddisp\":\"Y\",\"extjson\":\"\",\"dictTable\":\"dictTable2\",\"isFormdisp\":\"Y\", \"isSearch\":\"Y\",\"colType\":\"string\",\"inputType\":\"text\",\"dictText\":\"dictText2\",\"colDefval\":\"\"}\n  ],\n  \"conditions\": [{\n   \"issearch\": \"Y\",\n   \"expr\": \"username = #{abc}\",\n   \"inputType\": \"text\",\n  \"colName\": \"abc\",\n   \"colMark\": \"mark0\",\n   \"searchType\": \"fuzzy\"\n  }],\n}",
          "type": "json"
        }
      ]
    },
    "success": {
      "examples": [
        {
          "title": "Sample:",
          "content": "\tHTTP/1.1 200 OK\n {\n \"version\":2\n }",
          "type": "json"
        }
      ]
    },
    "error": {
      "examples": [
        {
          "title": "Error-Response:",
          "content": "HTTP/1.1 400 Bad Request\n{\n  \"verrors\":[\n    {\"errCode\":\"sql\",\"errorMsg\":\"SQL不能为空\"},\n    {\"errCode\":\"sql\",\"errorMsg\":\"SQL格式错误\"},\n    {\"errCode\":\"tablename\",\"errorMsg\":\"表名不能为空\"},\n    {\"errCode\":\"tabledescp\",\"errorMsg\":\"表描述不能为空\"},\n    {\"errCode\":\"pktype\",\"errorMsg\":\"主键类型不能为空\"},\n    {\"errCode\":\"columns\",\"errorMsg\":\"数据列不能为空\"},\n    {\"errCode\":\"tree\",\"errorMsg\":\"树形展示不能不设置父节点和子节点\"},\n    {\"errCode\":\"tree\",\"errorMsg\":\"父子节点不能相同\"},\n    {\"errCode\":\"child\",\"errorMsg\":\"子节点在字段中不存在\"},\n    {\"errCode\":\"parent\",\"errorMsg\":\"父节点在字段中不存在\"}\n    ]\n}",
          "type": "json"
        },
        {
          "title": "Error-Response:",
          "content": "HTTP/1.1 409 Conflict - 被人修改了或者表记录不存在",
          "type": "json"
        }
      ]
    },
    "version": "0.0.0",
    "filename": "src/main/java/com/rs/devplatform/controller/cg/CGController.java",
    "groupTitle": "CG",
    "name": "PutCgFormTableid"
  },
  {
    "type": "GET",
    "url": "/common/code/values",
    "title": "获取字典values",
    "description": "<p>根据dictTable, cdvalCol,cddescpCol查询某个【自定义字典类型】所有codeValues；或者根据cdtype获取字典表单codeValues</p>",
    "group": "Common_Code",
    "parameter": {
      "fields": {
        "Parameter": [
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "dictTable",
            "description": "<p>字典Table（从【字典表】中取，无需指定dictTable/cdvalCol/cddescpCol）</p>"
          },
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "cdvalCol",
            "description": "<p>字典Code</p>"
          },
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "cddescpCol",
            "description": "<p>字典Value</p>"
          },
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "cdtype",
            "description": "<p>数据字典里的类型（从【自定义字典表】获取不需要指定该值）</p>"
          }
        ]
      }
    },
    "examples": [
      {
        "title": "Example usage:",
        "content": "curl -i http://localhost/common/code/values?dictTable=xx_table&cdvalCol=xx_col&cddescpCol=xx_descp\ncurl -i http://localhost/common/code/values?cdtype=GENDER\t到字典表获取类型为GENDER的所有字典values",
        "type": "curl"
      }
    ],
    "success": {
      "fields": {
        "Success 200": [
          {
            "group": "Success 200",
            "type": "JSONArray",
            "optional": false,
            "field": "cdvals",
            "description": "<p>CodeType下对应的CodeValues</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "cdvals.cdval",
            "description": "<p>CodeVal</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "cdvals.cddescp",
            "description": "<p>CodeVal Descp</p>"
          }
        ]
      },
      "examples": [
        {
          "title": "Success-Response:",
          "content": "HTTP/1.1 200 OK\n{\n\t\"cdvals\":[\n\t{\"cddescp\":\"value3\",\"cdval\":\"3\"},\n\t{\"cddescp\":\"value1\",\"cdval\":\"1\"},\n\t{\"cddescp\":\"value2\",\"cdval\":\"2\"}\n\t]\n}",
          "type": "json"
        }
      ]
    },
    "version": "0.0.0",
    "filename": "src/main/java/com/rs/devplatform/controller/common/CodeController.java",
    "groupTitle": "Common_Code",
    "name": "GetCommonCodeValues"
  },
  {
    "type": "GET",
    "url": "/projects",
    "title": "获取想要登录的项目列表",
    "group": "Login",
    "description": "<p>获取想要登录的项目列表</p>",
    "success": {
      "fields": {
        "Success 200": [
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "id",
            "description": "<p>项目ID</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "name",
            "description": "<p>项目名称</p>"
          }
        ]
      },
      "examples": [
        {
          "title": "Success-Response:",
          "content": "HTTP/1.1 200 OK\n{\n  \"rows\":[\n    {\"id\":\"04fda30ffaa04b8b97c217c43342b04d\", \"name\":\"projectA\"},\n    {\"id\":\"04fda30ffaa04b8b97c217c43342sssss\", \"name\":\"projectB\"},\n  ]\n}",
          "type": "json"
        }
      ]
    },
    "error": {
      "examples": [
        {
          "title": "Error-Response:",
          "content": "HTTP/1.1 400 Bad Request",
          "type": "json"
        }
      ]
    },
    "version": "0.0.0",
    "filename": "src/main/java/com/rs/devplatform/controller/LoginController.java",
    "groupTitle": "Login",
    "name": "GetProjects"
  },
  {
    "type": "post",
    "url": "/login",
    "title": "页面提交登录",
    "group": "Login",
    "parameter": {
      "fields": {
        "Parameter": [
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "username",
            "description": "<p>用户名</p>"
          },
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "password",
            "description": "<p>密码</p>"
          },
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "projectId",
            "description": "<p>项目ID(可以为空，不为空时，userFlag不用传值，传值也会忽略)</p>"
          },
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "userFlag",
            "description": "<p>用户类型: A:admin, U:user</p>"
          }
        ]
      }
    },
    "success": {
      "fields": {
        "Success 200": [
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "html",
            "description": "<p>登录成功跳转页面,页面名称见下面example</p>"
          }
        ]
      },
      "examples": [
        {
          "title": "页面命名:",
          "content": "{\"admin的页面\":\"admin-index.html\"}\n{\"user的页面\":\"user-index.html\"}\n{\"测试人员对应项目的页面\":\"user-index.html\"}",
          "type": "json"
        }
      ]
    },
    "error": {
      "examples": [
        {
          "title": "Error-Response:",
          "content": "login.html\n{\"verror\":\"用户名密码不正确\"}",
          "type": "json"
        }
      ]
    },
    "version": "0.0.0",
    "filename": "src/main/java/com/rs/devplatform/controller/LoginController.java",
    "groupTitle": "Login",
    "name": "PostLogin"
  },
  {
    "type": "post",
    "url": "/loginrest",
    "title": "ajax提交登录",
    "group": "Login",
    "description": "<p>根据用户类型，转到不同的主页.</p>",
    "parameter": {
      "fields": {
        "Parameter": [
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "username",
            "description": "<p>用户名</p>"
          },
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "password",
            "description": "<p>密码</p>"
          },
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "projectId",
            "description": "<p>项目ID(可以为空，不为空时，userFlag不用传值，传值也会忽略)</p>"
          },
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "userFlag",
            "description": "<p>用户类型: A:admin, U:user</p>"
          }
        ]
      },
      "examples": [
        {
          "title": "Request-Example: ",
          "content": "{\"username\":\"admin\", \"password\":\"123456\", \"userFlag\":\"A\"}\n{\"username\":\"admin\", \"password\":\"123456\", \"projectId\":\"123123123\"}",
          "type": "json"
        }
      ]
    },
    "success": {
      "fields": {
        "Success 200": [
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "userFlag",
            "description": "<p>用户类型, A:Admin, U:User</p>"
          }
        ]
      },
      "examples": [
        {
          "title": "Success-Response:",
          "content": "HTTP/1.1 200 OK\n  {\"userFlag\":\"A\"}\n  {\"userFlag\":\"U\"}",
          "type": "json"
        }
      ]
    },
    "error": {
      "examples": [
        {
          "title": "Error-Response:",
          "content": "HTTP/1.1 400 Bad Request\n{\"verrors\":[{\"errCode\":\"\",\"errorMsg\":\"用户名密码不正确\"}]}",
          "type": "json"
        }
      ]
    },
    "version": "0.0.0",
    "filename": "src/main/java/com/rs/devplatform/controller/LoginController.java",
    "groupTitle": "Login",
    "name": "PostLoginrest"
  },
  {
    "type": "DELETE",
    "url": "/admin/moduleTest",
    "title": "功能测试删除",
    "group": "Module_Test",
    "parameter": {
      "fields": {
        "Parameter": [
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "id",
            "description": "<p>表id</p>"
          },
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "tablename",
            "description": "<p>表名</p>"
          }
        ]
      },
      "examples": [
        {
          "title": "Request-Example: ",
          "content": "{\"id\":\"adabcbc62c0841b5a320182e24bbee33\",\"tablename\":\"baoguo_stu\",\"rows\":\"[\\\"a243d30041d84fd7a0dfc73d14fcd436\\\"]\"}",
          "type": "json"
        }
      ]
    },
    "success": {
      "fields": {
        "Success 200": [
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "id",
            "description": "<p>表id</p>"
          }
        ]
      },
      "examples": [
        {
          "title": "Sample:",
          "content": "\tHTTP/1.1 200 OK\n{\"rows\":[\"a243d30041d84fd7a0dfc73d14fcd436\"]}",
          "type": "json"
        }
      ]
    },
    "version": "0.0.0",
    "filename": "src/main/java/com/rs/devplatform/controller/admin/ModuleTestController.java",
    "groupTitle": "Module_Test",
    "name": "DeleteAdminModuletest"
  },
  {
    "type": "GET",
    "url": "/admin/moduleTest/getForm/{id}",
    "title": "获取sys_bxx里的表单列表",
    "group": "Module_Test",
    "parameter": {
      "examples": [
        {
          "title": "Request-Example: ",
          "content": "\t{\"current\":1, \"size\":10, \"orderBy\":\"name\", \"asc\":\"true\"}\n \t\t{\"name\":\"123\", \"orderby\":\"name\", \"comments\":\"不分页,current为null\"}",
          "type": "json"
        }
      ]
    },
    "success": {
      "fields": {
        "Success 200": [
          {
            "group": "Success 200",
            "type": "Integer",
            "optional": false,
            "field": "total",
            "description": "<p>总记录条数</p>"
          },
          {
            "group": "Success 200",
            "type": "Integer",
            "optional": false,
            "field": "current",
            "description": "<p>当前页码</p>"
          },
          {
            "group": "Success 200",
            "type": "Integer",
            "optional": false,
            "field": "size",
            "description": "<p>每页显示记录条数</p>"
          },
          {
            "group": "Success 200",
            "type": "Integer",
            "optional": false,
            "field": "pages",
            "description": "<p>总页数</p>"
          },
          {
            "group": "Success 200",
            "type": "JsonArray",
            "optional": false,
            "field": "rows",
            "description": "<p>JSONArray</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "rows.id",
            "description": "<p>表明细主键</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "rows.nr",
            "description": "<p>表明细内容</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "rows.iscbox",
            "description": "<p>是否是多选框</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "rows.isdbsyn",
            "description": "<p>是否数据库同步</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "rows.ispage",
            "description": "<p>是否分页</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "rows.istree",
            "description": "<p>是否显示为树结构</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "rows.pkseq",
            "description": "<p>表单主键序列</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "rows.pklx",
            "description": "<p>主键类型</p>"
          },
          {
            "group": "Success 200",
            "type": "Integer",
            "optional": false,
            "field": "rows.lx",
            "description": "<p>表类型：1：单表，2：主表，3：子表</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "rows.cxms",
            "description": "<p>查询模式</p>"
          },
          {
            "group": "Success 200",
            "type": "Integer",
            "optional": false,
            "field": "rows.gxlx",
            "description": "<p>关系类型</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "rows.zb",
            "description": "<p>子表字符串sub_table_str</p>"
          },
          {
            "group": "Success 200",
            "type": "Integer",
            "optional": false,
            "field": "rows.tabxh",
            "description": "<p>tab序号</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "rows.bm",
            "description": "<p>表名</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "rows.sfidzdm",
            "description": "<p>树型结构中的父节点字段名字</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "rows.sidzdm",
            "description": "<p>树结构id字段名字</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "rows.szdm",
            "description": "<p>树字段名</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "rows.lb",
            "description": "<p>表单类别</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "rows.mb",
            "description": "<p>表单模板</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "rows.mbm",
            "description": "<p>表单模板样式(移动端)</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "rows.whr",
            "description": "<p>维护人名字</p>"
          },
          {
            "group": "Success 200",
            "type": "Date",
            "optional": false,
            "field": "rows.whsj",
            "description": "<p>维护时间</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "rows.whrid",
            "description": "<p>维护人ID</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "rows.cjr",
            "description": "<p>创建人名字</p>"
          },
          {
            "group": "Success 200",
            "type": "Date",
            "optional": false,
            "field": "rows.cjsj",
            "description": "<p>创建时间</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "rows.cjrid",
            "description": "<p>创建人ID</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "rows.deleted",
            "description": ""
          },
          {
            "group": "Success 200",
            "type": "Integer",
            "optional": false,
            "field": "rows.version",
            "description": "<p>表单版本</p>"
          }
        ]
      },
      "examples": [
        {
          "title": "Success-Response:",
          "content": " HTTP/1.1 200 OK\n\t{\"total\",25, \"current\":1, \"size\":10, \"pages\":3, \"rows\":[\n\t \t\t{\n\t\t\t\"id\" : \"表明细主键\",\t\n\t\t\t\"nr\" : \"表明细内容\",\t\n\t\t\t\"iscbox\" : \"是否是多选框\",\t\n\t\t\t\"isdbsyn\" : \"是否数据库同步\",\t\n\t\t\t\"ispage\" : \"是否分页\",\t\n\t\t\t\"istree\" : \"是否显示为树结构\",\t\n\t\t\t\"pkseq\" : \"表单主键序列\",\t\n\t\t\t\"pklx\" : \"主键类型\",\t\n\t\t\t\"lx\" : \"表类型：1：单表，2：主表，3：子表\",\t\n\t\t\t\"cxms\" : \"查询模式\",\t\n\t\t\t\"gxlx\" : \"关系类型\",\t\n\t\t\t\"zb\" : \"子表字符串sub_table_str\",\t\n\t\t\t\"tabxh\" : \"tab序号\",\t\n\t\t\t\"bm\" : \"表名\",\t\n\t\t\t\"sfidzdm\" : \"树型结构中的父节点字段名字\",\t\n\t\t\t\"sidzdm\" : \"树结构id字段名字\",\t\n\t\t\t\"szdm\" : \"树字段名\",\t\n\t\t\t\"lb\" : \"表单类别\",\t\n\t\t\t\"mb\" : \"表单模板\",\t\n\t\t\t\"mbm\" : \"表单模板样式(移动端)\",\t\n\t\t\t\"whr\" : \"维护人名字\",\t\n\t\t\t\"whsj\" : \"维护时间\",\t\n\t\t\t\"whrid\" : \"维护人ID\",\t\n\t\t\t\"cjr\" : \"创建人名字\",\t\n\t\t\t\"cjsj\" : \"创建时间\",\t\n\t\t\t\"cjrid\" : \"创建人ID\",\t\n\t\t\t\"deleted\" : \"\",\t\n\t\t\t\"version\" : \"表单版本\",\t\n\t \t\t}\n\t \t]}\n\t{\"rows\":[\n\t \t\t{\n\t\t\t\"id\" : \"表明细主键\",\t\n\t\t\t\"nr\" : \"表明细内容\",\t\n\t\t\t\"iscbox\" : \"是否是多选框\",\t\n\t\t\t\"isdbsyn\" : \"是否数据库同步\",\t\n\t\t\t\"ispage\" : \"是否分页\",\t\n\t\t\t\"istree\" : \"是否显示为树结构\",\t\n\t\t\t\"pkseq\" : \"表单主键序列\",\t\n\t\t\t\"pklx\" : \"主键类型\",\t\n\t\t\t\"lx\" : \"表类型：1：单表，2：主表，3：子表\",\t\n\t\t\t\"cxms\" : \"查询模式\",\t\n\t\t\t\"gxlx\" : \"关系类型\",\t\n\t\t\t\"zb\" : \"子表字符串sub_table_str\",\t\n\t\t\t\"tabxh\" : \"tab序号\",\t\n\t\t\t\"bm\" : \"表名\",\t\n\t\t\t\"sfidzdm\" : \"树型结构中的父节点字段名字\",\t\n\t\t\t\"sidzdm\" : \"树结构id字段名字\",\t\n\t\t\t\"szdm\" : \"树字段名\",\t\n\t\t\t\"lb\" : \"表单类别\",\t\n\t\t\t\"mb\" : \"表单模板\",\t\n\t\t\t\"mbm\" : \"表单模板样式(移动端)\",\t\n\t\t\t\"whr\" : \"维护人名字\",\t\n\t\t\t\"whsj\" : \"维护时间\",\t\n\t\t\t\"whrid\" : \"维护人ID\",\t\n\t\t\t\"cjr\" : \"创建人名字\",\t\n\t\t\t\"cjsj\" : \"创建时间\",\t\n\t\t\t\"cjrid\" : \"创建人ID\",\t\n\t\t\t\"deleted\" : \"\",\t\n\t\t\t\"version\" : \"表单版本\",\t\n\t\t\t}\n\t \t]}",
          "type": "json"
        }
      ]
    },
    "version": "0.0.0",
    "filename": "src/main/java/com/rs/devplatform/controller/admin/ModuleTestController.java",
    "groupTitle": "Module_Test",
    "name": "GetAdminModuletestGetformId"
  },
  {
    "type": "GET",
    "url": "/admin/moduleTest/getToolButton/{id}",
    "title": "获取sys_bxx里的表单所含有的index列表",
    "group": "Module_Test",
    "parameter": {
      "examples": [
        {
          "title": "Request-Example: ",
          "content": "\t{\"current\":1, \"size\":10, \"orderBy\":\"name\", \"asc\":\"true\"}\n \t\t{\"name\":\"123\", \"orderby\":\"name\", \"comments\":\"不分页,current为null\"}",
          "type": "json"
        }
      ]
    },
    "success": {
      "fields": {
        "Success 200": [
          {
            "group": "Success 200",
            "type": "Integer",
            "optional": false,
            "field": "total",
            "description": "<p>总记录条数</p>"
          },
          {
            "group": "Success 200",
            "type": "Integer",
            "optional": false,
            "field": "current",
            "description": "<p>当前页码</p>"
          },
          {
            "group": "Success 200",
            "type": "Integer",
            "optional": false,
            "field": "size",
            "description": "<p>每页显示记录条数</p>"
          },
          {
            "group": "Success 200",
            "type": "Integer",
            "optional": false,
            "field": "pages",
            "description": "<p>总页数</p>"
          },
          {
            "group": "Success 200",
            "type": "JsonArray",
            "optional": false,
            "field": "rows",
            "description": "<p>JSONArray</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "rows.id",
            "description": "<p>自定义按钮主键</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "rows.code",
            "description": "<p>自定义按钮编码</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "rows.icon",
            "description": "<p>自定义按钮图标</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "rows.name",
            "description": "<p>自定义按钮名字</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "rows.zt",
            "description": "<p>自定义按钮状态</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "rows.ys",
            "description": "<p>自定义按钮样式</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "rows.exp",
            "description": "<p>自定义按钮过期时间</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "rows.bdid",
            "description": "<p>表单ID</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "rows.czlx",
            "description": "<p>按钮操作类型</p>"
          },
          {
            "group": "Success 200",
            "type": "Integer",
            "optional": false,
            "field": "rows.xh",
            "description": "<p>按钮序号</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "rows.whr",
            "description": "<p>维护人名字</p>"
          },
          {
            "group": "Success 200",
            "type": "Date",
            "optional": false,
            "field": "rows.whsj",
            "description": "<p>维护时间</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "rows.whrid",
            "description": "<p>维护人ID</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "rows.cjr",
            "description": "<p>创建人名字</p>"
          },
          {
            "group": "Success 200",
            "type": "Date",
            "optional": false,
            "field": "rows.cjsj",
            "description": "<p>创建时间</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "rows.cjrid",
            "description": "<p>创建人ID</p>"
          },
          {
            "group": "Success 200",
            "type": "Integer",
            "optional": false,
            "field": "rows.version",
            "description": "<p>数据记录版本</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "rows.deleted",
            "description": "<p>逻辑删除标记位</p>"
          }
        ]
      },
      "examples": [
        {
          "title": "Success-Response:",
          "content": " HTTP/1.1 200 OK\n\t{\"total\",25, \"current\":1, \"size\":10, \"pages\":3, \"rows\":[\n\t \t\t{\n\t\t\t\"id\" : \"自定义按钮主键\",\t\n\t\t\t\"code\" : \"自定义按钮编码\",\t\n\t\t\t\"icon\" : \"自定义按钮图标\",\t\n\t\t\t\"name\" : \"自定义按钮名字\",\t\n\t\t\t\"zt\" : \"自定义按钮状态\",\t\n\t\t\t\"ys\" : \"自定义按钮样式\",\t\n\t\t\t\"exp\" : \"自定义按钮过期时间\",\t\n\t\t\t\"bdid\" : \"表单ID\",\t\n\t\t\t\"czlx\" : \"按钮操作类型\",\t\n\t\t\t\"xh\" : \"按钮序号\",\t\n\t\t\t\"whr\" : \"维护人名字\",\t\n\t\t\t\"whsj\" : \"维护时间\",\t\n\t\t\t\"whrid\" : \"维护人ID\",\t\n\t\t\t\"cjr\" : \"创建人名字\",\t\n\t\t\t\"cjsj\" : \"创建时间\",\t\n\t\t\t\"cjrid\" : \"创建人ID\",\t\n\t\t\t\"version\" : \"数据记录版本\",\t\n\t\t\t\"deleted\" : \"逻辑删除标记位\",\t\n\t \t\t}\n\t \t]}",
          "type": "json"
        }
      ]
    },
    "version": "0.0.0",
    "filename": "src/main/java/com/rs/devplatform/controller/admin/ModuleTestController.java",
    "groupTitle": "Module_Test",
    "name": "GetAdminModuletestGettoolbuttonId"
  },
  {
    "type": "GET",
    "url": "/admin/moduleTest/getZqjs/{id}",
    "title": "获取sys_bxx里的表单的增强js",
    "group": "Module_Test",
    "parameter": {
      "examples": [
        {
          "title": "Request-Example: ",
          "content": "\t{\"current\":1, \"size\":10, \"orderBy\":\"name\", \"asc\":\"true\"}\n \t\t{\"name\":\"123\", \"orderby\":\"name\", \"comments\":\"不分页,current为null\"}",
          "type": "json"
        }
      ]
    },
    "success": {
      "fields": {
        "Success 200": [
          {
            "group": "Success 200",
            "type": "Integer",
            "optional": false,
            "field": "total",
            "description": "<p>总记录条数</p>"
          },
          {
            "group": "Success 200",
            "type": "Integer",
            "optional": false,
            "field": "current",
            "description": "<p>当前页码</p>"
          },
          {
            "group": "Success 200",
            "type": "Integer",
            "optional": false,
            "field": "size",
            "description": "<p>每页显示记录条数</p>"
          },
          {
            "group": "Success 200",
            "type": "Integer",
            "optional": false,
            "field": "pages",
            "description": "<p>总页数</p>"
          },
          {
            "group": "Success 200",
            "type": "JsonArray",
            "optional": false,
            "field": "rows",
            "description": "<p>JSONArray</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "rows.id",
            "description": "<p>增强js主键</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "rows.js",
            "description": "<p>增强js</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "rows.jslx",
            "description": "<p>增强js类型  form/list</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "rows.nr",
            "description": "<p>增强js内容</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "rows.bdid",
            "description": "<p>表单id</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "rows.whr",
            "description": "<p>维护人名字</p>"
          },
          {
            "group": "Success 200",
            "type": "Date",
            "optional": false,
            "field": "rows.whsj",
            "description": "<p>维护时间</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "rows.whrid",
            "description": "<p>维护人ID</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "rows.cjr",
            "description": "<p>创建人名字</p>"
          },
          {
            "group": "Success 200",
            "type": "Date",
            "optional": false,
            "field": "rows.cjsj",
            "description": "<p>创建时间</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "rows.cjrid",
            "description": "<p>创建人ID</p>"
          },
          {
            "group": "Success 200",
            "type": "Integer",
            "optional": false,
            "field": "rows.version",
            "description": "<p>数据记录版本</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "rows.deleted",
            "description": "<p>逻辑删除标记位</p>"
          }
        ]
      },
      "examples": [
        {
          "title": "Success-Response:",
          "content": " HTTP/1.1 200 OK\n\t{\"total\",25, \"current\":1, \"size\":10, \"pages\":3, \"rows\":[\n\t \t\t{\n\t\t\t\"id\" : \"增强js主键\",\t\n\t\t\t\"js\" : \"增强js\",\t\n\t\t\t\"jslx\" : \"增强js类型\",\t\n\t\t\t\"nr\" : \"增强js内容\",\t\n\t\t\t\"bdid\" : \"表单ID\",\t\n\t\t\t\"whr\" : \"维护人名字\",\t\n\t\t\t\"whsj\" : \"维护时间\",\t\n\t\t\t\"whrid\" : \"维护人ID\",\t\n\t\t\t\"cjr\" : \"创建人名字\",\t\n\t\t\t\"cjsj\" : \"创建时间\",\t\n\t\t\t\"cjrid\" : \"创建人ID\",\t\n\t\t\t\"version\" : \"数据记录版本\",\t\n\t\t\t\"deleted\" : \"逻辑删除标记位\",\t\n\t \t\t}\n\t \t]}",
          "type": "json"
        }
      ]
    },
    "version": "0.0.0",
    "filename": "src/main/java/com/rs/devplatform/controller/admin/ModuleTestController.java",
    "groupTitle": "Module_Test",
    "name": "GetAdminModuletestGetzqjsId"
  },
  {
    "type": "POST",
    "url": "/admin/moduleTest/add",
    "title": "功能测试增加",
    "group": "Module_Test",
    "parameter": {
      "fields": {
        "Parameter": [
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "id",
            "description": "<p>表id</p>"
          },
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "tablename",
            "description": "<p>表名</p>"
          }
        ]
      },
      "examples": [
        {
          "title": "Request-Example: ",
          "content": "{\"id\":\"ddaaf72259c84a5f81f6ad9b13b39086\",\"detail\":[{\"records\":[{\"tag\":\"A\",\"TBLMX_fid\":\"1\",\"TBLMX_name\":\"1\"},{\"tag\":\"A\",\"TBLMX_fid\":\"1\",\"TBLMX_name\":\"1\"},{\"tag\":\"A\",\"TBLMX_fid\":\"1\",\"TBLMX_name\":\"1\"}],\"id\":\"870b1424b1fb4a2483b7bb3dd0f7c951\"}],\"tablename\":\"TBL\",\"master\":{\"TBL_name\":\"1\",\"tag\":\"A\"}}",
          "type": "json"
        }
      ]
    },
    "success": {
      "fields": {
        "Success 200": [
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "id",
            "description": "<p>表id</p>"
          }
        ]
      },
      "examples": [
        {
          "title": "Sample:",
          "content": "\tHTTP/1.1 200 OK\n{}",
          "type": "json"
        }
      ]
    },
    "version": "0.0.0",
    "filename": "src/main/java/com/rs/devplatform/controller/admin/ModuleTestController.java",
    "groupTitle": "Module_Test",
    "name": "PostAdminModuletestAdd"
  },
  {
    "type": "POST",
    "url": "/admin/moduleTest/getCondition/{id}",
    "title": "获取sys_zdxx里所有可以作为查询条件的字段列表",
    "group": "Module_Test",
    "parameter": {
      "examples": [
        {
          "title": "Request-Example: ",
          "content": "\t{\"current\":1, \"size\":10, \"orderBy\":\"name\", \"asc\":\"true\"}\n \t\t{\"name\":\"123\", \"orderby\":\"name\", \"comments\":\"不分页,current为null\"}",
          "type": "json"
        }
      ]
    },
    "success": {
      "fields": {
        "Success 200": [
          {
            "group": "Success 200",
            "type": "Integer",
            "optional": false,
            "field": "total",
            "description": "<p>总记录条数</p>"
          },
          {
            "group": "Success 200",
            "type": "Integer",
            "optional": false,
            "field": "current",
            "description": "<p>当前页码</p>"
          },
          {
            "group": "Success 200",
            "type": "Integer",
            "optional": false,
            "field": "size",
            "description": "<p>每页显示记录条数</p>"
          },
          {
            "group": "Success 200",
            "type": "Integer",
            "optional": false,
            "field": "pages",
            "description": "<p>总页数</p>"
          },
          {
            "group": "Success 200",
            "type": "JsonArray",
            "optional": false,
            "field": "rows",
            "description": "<p>JSONArray</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "rows.id",
            "description": "<p>字段明细主键</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "rows.nr",
            "description": "<p>字段内容</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "rows.zdzd",
            "description": "<p>字典字段</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "rows.zdbm",
            "description": "<p>字典表名</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "rows.zdwb",
            "description": "<p>字典文本</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "rows.mr",
            "description": "<p>字段默认值</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "rows.href",
            "description": "<p>字段超链接</p>"
          },
          {
            "group": "Success 200",
            "type": "Integer",
            "optional": false,
            "field": "rows.cd",
            "description": "<p>字段长度</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "rows.name",
            "description": "<p>字段名字</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "rows.yzlx",
            "description": "<p>字段验证类型</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "rows.iskey",
            "description": "<p>是否主键</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "rows.isnull",
            "description": "<p>是否允许为空</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "rows.isquery",
            "description": "<p>是否允许查询</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "rows.isshow",
            "description": "<p>是否显示在表单</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "rows.isshowlb",
            "description": "<p>是否显示为列表</p>"
          },
          {
            "group": "Success 200",
            "type": "Integer",
            "optional": false,
            "field": "rows.len",
            "description": "<p>字段显示长度</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "rows.zzd",
            "description": "<p>主表字段</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "rows.zb",
            "description": "<p>主表</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "rows.jzdm",
            "description": "<p>旧字段名字</p>"
          },
          {
            "group": "Success 200",
            "type": "Integer",
            "optional": false,
            "field": "rows.xh",
            "description": "<p>序号</p>"
          },
          {
            "group": "Success 200",
            "type": "Integer",
            "optional": false,
            "field": "rows.plen",
            "description": "<p>小数点位数</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "rows.cxms",
            "description": "<p>查询模式</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "rows.xslx",
            "description": "<p>显示类型</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "rows.lx",
            "description": "<p>字段类型</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "rows.bid",
            "description": "<p>表ID</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "rows.kzjson",
            "description": "<p>扩展JSON</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "rows.whr",
            "description": "<p>维护人名字</p>"
          },
          {
            "group": "Success 200",
            "type": "Date",
            "optional": false,
            "field": "rows.whsj",
            "description": "<p>维护时间</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "rows.whrid",
            "description": "<p>维护人ID</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "rows.cjr",
            "description": "<p>创建人名字</p>"
          },
          {
            "group": "Success 200",
            "type": "Date",
            "optional": false,
            "field": "rows.cjsj",
            "description": "<p>创建时间</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "rows.cjrid",
            "description": "<p>创建人ID</p>"
          },
          {
            "group": "Success 200",
            "type": "Integer",
            "optional": false,
            "field": "rows.version",
            "description": "<p>数据记录版本</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "rows.deleted",
            "description": "<p>逻辑删除标记位</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "rows.chgtype",
            "description": "<p>编辑表单时记录字段动态, A:Add,U:Update,D:Delete</p>"
          }
        ]
      },
      "examples": [
        {
          "title": "Success-Response:",
          "content": " HTTP/1.1 200 OK\n\t{\"total\",25, \"current\":1, \"size\":10, \"pages\":3, \"records\":[\n\t \t\t{\n\t\t\t\"id\" : \"字段明细主键\",\t\n\t\t\t\"nr\" : \"字段内容\",\t\n\t\t\t\"zdzd\" : \"字典字段\",\t\n\t\t\t\"zdbm\" : \"字典表名\",\t\n\t\t\t\"zdwb\" : \"字典文本\",\t\n\t\t\t\"mr\" : \"字段默认值\",\t\n\t\t\t\"href\" : \"字段超链接\",\t\n\t\t\t\"cd\" : \"字段长度\",\t\n\t\t\t\"name\" : \"字段名字\",\t\n\t\t\t\"yzlx\" : \"字段验证类型\",\t\n\t\t\t\"iskey\" : \"是否主键\",\t\n\t\t\t\"isnull\" : \"是否允许为空\",\t\n\t\t\t\"isquery\" : \"是否允许查询\",\t\n\t\t\t\"isshow\" : \"是否显示在表单\",\t\n\t\t\t\"isshowlb\" : \"是否显示为列表\",\t\n\t\t\t\"len\" : \"字段显示长度\",\t\n\t\t\t\"zzd\" : \"主表字段\",\t\n\t\t\t\"zb\" : \"主表\",\t\n\t\t\t\"jzdm\" : \"旧字段名字\",\t\n\t\t\t\"xh\" : \"序号\",\t\n\t\t\t\"plen\" : \"小数点位数\",\t\n\t\t\t\"cxms\" : \"查询模式\",\t\n\t\t\t\"xslx\" : \"显示类型\",\t\n\t\t\t\"lx\" : \"字段类型\",\t\n\t\t\t\"bid\" : \"表ID\",\t\n\t\t\t\"kzjson\" : \"扩展JSON\",\t\n\t\t\t\"whr\" : \"维护人名字\",\t\n\t\t\t\"whsj\" : \"维护时间\",\t\n\t\t\t\"whrid\" : \"维护人ID\",\t\n\t\t\t\"cjr\" : \"创建人名字\",\t\n\t\t\t\"cjsj\" : \"创建时间\",\t\n\t\t\t\"cjrid\" : \"创建人ID\",\t\n\t\t\t\"version\" : \"数据记录版本\",\t\n\t\t\t\"deleted\" : \"逻辑删除标记位\",\t\n\t\t\t\"chgtype\" : \"编辑表单时记录字段动态, A:Add,U:Update,D:Delete\",\t\n\t \t\t}\n\t \t]}",
          "type": "json"
        }
      ]
    },
    "version": "0.0.0",
    "filename": "src/main/java/com/rs/devplatform/controller/admin/ModuleTestController.java",
    "groupTitle": "Module_Test",
    "name": "PostAdminModuletestGetconditionId"
  },
  {
    "type": "POST",
    "url": "/admin/moduleTest/getEdit/{id}",
    "title": "获取sys_zdxx里所有在表单中显示的的字段列表",
    "group": "Module_Test",
    "parameter": {
      "examples": [
        {
          "title": "Request-Example: ",
          "content": "\t{\"current\":1, \"size\":10, \"orderBy\":\"name\", \"asc\":\"true\"}\n \t\t{\"name\":\"123\", \"orderby\":\"name\", \"comments\":\"不分页,current为null\"}",
          "type": "json"
        }
      ]
    },
    "success": {
      "fields": {
        "Success 200": [
          {
            "group": "Success 200",
            "type": "Integer",
            "optional": false,
            "field": "total",
            "description": "<p>总记录条数</p>"
          },
          {
            "group": "Success 200",
            "type": "Integer",
            "optional": false,
            "field": "current",
            "description": "<p>当前页码</p>"
          },
          {
            "group": "Success 200",
            "type": "Integer",
            "optional": false,
            "field": "size",
            "description": "<p>每页显示记录条数</p>"
          },
          {
            "group": "Success 200",
            "type": "Integer",
            "optional": false,
            "field": "pages",
            "description": "<p>总页数</p>"
          },
          {
            "group": "Success 200",
            "type": "JsonArray",
            "optional": false,
            "field": "rows",
            "description": "<p>JSONArray</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "rows.id",
            "description": "<p>字段明细主键</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "rows.nr",
            "description": "<p>字段内容</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "rows.zdzd",
            "description": "<p>字典字段</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "rows.zdbm",
            "description": "<p>字典表名</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "rows.zdwb",
            "description": "<p>字典文本</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "rows.mr",
            "description": "<p>字段默认值</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "rows.href",
            "description": "<p>字段超链接</p>"
          },
          {
            "group": "Success 200",
            "type": "Integer",
            "optional": false,
            "field": "rows.cd",
            "description": "<p>字段长度</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "rows.name",
            "description": "<p>字段名字</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "rows.yzlx",
            "description": "<p>字段验证类型</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "rows.iskey",
            "description": "<p>是否主键</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "rows.isnull",
            "description": "<p>是否允许为空</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "rows.isquery",
            "description": "<p>是否允许查询</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "rows.isshow",
            "description": "<p>是否显示在表单</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "rows.isshowlb",
            "description": "<p>是否显示为列表</p>"
          },
          {
            "group": "Success 200",
            "type": "Integer",
            "optional": false,
            "field": "rows.len",
            "description": "<p>字段显示长度</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "rows.zzd",
            "description": "<p>主表字段</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "rows.zb",
            "description": "<p>主表</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "rows.jzdm",
            "description": "<p>旧字段名字</p>"
          },
          {
            "group": "Success 200",
            "type": "Integer",
            "optional": false,
            "field": "rows.xh",
            "description": "<p>序号</p>"
          },
          {
            "group": "Success 200",
            "type": "Integer",
            "optional": false,
            "field": "rows.plen",
            "description": "<p>小数点位数</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "rows.cxms",
            "description": "<p>查询模式</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "rows.xslx",
            "description": "<p>显示类型</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "rows.lx",
            "description": "<p>字段类型</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "rows.bid",
            "description": "<p>表ID</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "rows.kzjson",
            "description": "<p>扩展JSON</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "rows.whr",
            "description": "<p>维护人名字</p>"
          },
          {
            "group": "Success 200",
            "type": "Date",
            "optional": false,
            "field": "rows.whsj",
            "description": "<p>维护时间</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "rows.whrid",
            "description": "<p>维护人ID</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "rows.cjr",
            "description": "<p>创建人名字</p>"
          },
          {
            "group": "Success 200",
            "type": "Date",
            "optional": false,
            "field": "rows.cjsj",
            "description": "<p>创建时间</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "rows.cjrid",
            "description": "<p>创建人ID</p>"
          },
          {
            "group": "Success 200",
            "type": "Integer",
            "optional": false,
            "field": "rows.version",
            "description": "<p>数据记录版本</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "rows.deleted",
            "description": "<p>逻辑删除标记位</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "rows.chgtype",
            "description": "<p>编辑表单时记录字段动态, A:Add,U:Update,D:Delete</p>"
          }
        ]
      },
      "examples": [
        {
          "title": "Success-Response:",
          "content": " HTTP/1.1 200 OK\n\t{\"total\",25, \"current\":1, \"size\":10, \"pages\":3, \"rows\":[\n\t \t\t{\n\t\t\t\"id\" : \"字段明细主键\",\t\n\t\t\t\"nr\" : \"字段内容\",\t\n\t\t\t\"zdzd\" : \"字典字段\",\t\n\t\t\t\"zdbm\" : \"字典表名\",\t\n\t\t\t\"zdwb\" : \"字典文本\",\t\n\t\t\t\"mr\" : \"字段默认值\",\t\n\t\t\t\"href\" : \"字段超链接\",\t\n\t\t\t\"cd\" : \"字段长度\",\t\n\t\t\t\"name\" : \"字段名字\",\t\n\t\t\t\"yzlx\" : \"字段验证类型\",\t\n\t\t\t\"iskey\" : \"是否主键\",\t\n\t\t\t\"isnull\" : \"是否允许为空\",\t\n\t\t\t\"isquery\" : \"是否允许查询\",\t\n\t\t\t\"isshow\" : \"是否显示在表单\",\t\n\t\t\t\"isshowlb\" : \"是否显示为列表\",\t\n\t\t\t\"len\" : \"字段显示长度\",\t\n\t\t\t\"zzd\" : \"主表字段\",\t\n\t\t\t\"zb\" : \"主表\",\t\n\t\t\t\"jzdm\" : \"旧字段名字\",\t\n\t\t\t\"xh\" : \"序号\",\t\n\t\t\t\"plen\" : \"小数点位数\",\t\n\t\t\t\"cxms\" : \"查询模式\",\t\n\t\t\t\"xslx\" : \"显示类型\",\t\n\t\t\t\"lx\" : \"字段类型\",\t\n\t\t\t\"bid\" : \"表ID\",\t\n\t\t\t\"kzjson\" : \"扩展JSON\",\t\n\t\t\t\"whr\" : \"维护人名字\",\t\n\t\t\t\"whsj\" : \"维护时间\",\t\n\t\t\t\"whrid\" : \"维护人ID\",\t\n\t\t\t\"cjr\" : \"创建人名字\",\t\n\t\t\t\"cjsj\" : \"创建时间\",\t\n\t\t\t\"cjrid\" : \"创建人ID\",\t\n\t\t\t\"version\" : \"数据记录版本\",\t\n\t\t\t\"deleted\" : \"逻辑删除标记位\",\t\n\t\t\t\"chgtype\" : \"编辑表单时记录字段动态, A:Add,U:Update,D:Delete\",\t\n\t \t\t}\n\t \t]}\n\t{\"rows\":[\n\t \t\t{\n\t\t\t\"id\" : \"字段明细主键\",\t\n\t\t\t\"nr\" : \"字段内容\",\t\n\t\t\t\"zdzd\" : \"字典字段\",\t\n\t\t\t\"zdbm\" : \"字典表名\",\t\n\t\t\t\"zdwb\" : \"字典文本\",\t\n\t\t\t\"mr\" : \"字段默认值\",\t\n\t\t\t\"href\" : \"字段超链接\",\t\n\t\t\t\"cd\" : \"字段长度\",\t\n\t\t\t\"name\" : \"字段名字\",\t\n\t\t\t\"yzlx\" : \"字段验证类型\",\t\n\t\t\t\"iskey\" : \"是否主键\",\t\n\t\t\t\"isnull\" : \"是否允许为空\",\t\n\t\t\t\"isquery\" : \"是否允许查询\",\t\n\t\t\t\"isshow\" : \"是否显示在表单\",\t\n\t\t\t\"isshowlb\" : \"是否显示为列表\",\t\n\t\t\t\"len\" : \"字段显示长度\",\t\n\t\t\t\"zzd\" : \"主表字段\",\t\n\t\t\t\"zb\" : \"主表\",\t\n\t\t\t\"jzdm\" : \"旧字段名字\",\t\n\t\t\t\"xh\" : \"序号\",\t\n\t\t\t\"plen\" : \"小数点位数\",\t\n\t\t\t\"cxms\" : \"查询模式\",\t\n\t\t\t\"xslx\" : \"显示类型\",\t\n\t\t\t\"lx\" : \"字段类型\",\t\n\t\t\t\"bid\" : \"表ID\",\t\n\t\t\t\"kzjson\" : \"扩展JSON\",\t\n\t\t\t\"whr\" : \"维护人名字\",\t\n\t\t\t\"whsj\" : \"维护时间\",\t\n\t\t\t\"whrid\" : \"维护人ID\",\t\n\t\t\t\"cjr\" : \"创建人名字\",\t\n\t\t\t\"cjsj\" : \"创建时间\",\t\n\t\t\t\"cjrid\" : \"创建人ID\",\t\n\t\t\t\"version\" : \"数据记录版本\",\t\n\t\t\t\"deleted\" : \"逻辑删除标记位\",\t\n\t\t\t\"chgtype\" : \"编辑表单时记录字段动态, A:Add,U:Update,D:Delete\",\t\n\t\t\t}\n\t \t]}",
          "type": "json"
        }
      ]
    },
    "version": "0.0.0",
    "filename": "src/main/java/com/rs/devplatform/controller/admin/ModuleTestController.java",
    "groupTitle": "Module_Test",
    "name": "PostAdminModuletestGeteditId"
  },
  {
    "type": "POST",
    "url": "/admin/moduleTest/getList/{id}获取sys_zdxx里所有列表中显示的字段列表",
    "title": "",
    "group": "Module_Test",
    "parameter": {
      "examples": [
        {
          "title": "Request-Example: ",
          "content": "\t{\"current\":1, \"size\":10, \"orderBy\":\"name\", \"asc\":\"true\"}\n \t\t{\"name\":\"123\", \"orderby\":\"name\", \"comments\":\"不分页,current为null\"}",
          "type": "json"
        }
      ]
    },
    "success": {
      "fields": {
        "Success 200": [
          {
            "group": "Success 200",
            "type": "Integer",
            "optional": false,
            "field": "total",
            "description": "<p>总记录条数</p>"
          },
          {
            "group": "Success 200",
            "type": "Integer",
            "optional": false,
            "field": "current",
            "description": "<p>当前页码</p>"
          },
          {
            "group": "Success 200",
            "type": "Integer",
            "optional": false,
            "field": "size",
            "description": "<p>每页显示记录条数</p>"
          },
          {
            "group": "Success 200",
            "type": "Integer",
            "optional": false,
            "field": "pages",
            "description": "<p>总页数</p>"
          },
          {
            "group": "Success 200",
            "type": "JsonArray",
            "optional": false,
            "field": "rows",
            "description": "<p>JSONArray</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "rows.id",
            "description": "<p>字段明细主键</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "rows.nr",
            "description": "<p>字段内容</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "rows.zdzd",
            "description": "<p>字典字段</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "rows.zdbm",
            "description": "<p>字典表名</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "rows.zdwb",
            "description": "<p>字典文本</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "rows.mr",
            "description": "<p>字段默认值</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "rows.href",
            "description": "<p>字段超链接</p>"
          },
          {
            "group": "Success 200",
            "type": "Integer",
            "optional": false,
            "field": "rows.cd",
            "description": "<p>字段长度</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "rows.name",
            "description": "<p>字段名字</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "rows.yzlx",
            "description": "<p>字段验证类型</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "rows.iskey",
            "description": "<p>是否主键</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "rows.isnull",
            "description": "<p>是否允许为空</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "rows.isquery",
            "description": "<p>是否允许查询</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "rows.isshow",
            "description": "<p>是否显示在表单</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "rows.isshowlb",
            "description": "<p>是否显示为列表</p>"
          },
          {
            "group": "Success 200",
            "type": "Integer",
            "optional": false,
            "field": "rows.len",
            "description": "<p>字段显示长度</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "rows.zzd",
            "description": "<p>主表字段</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "rows.zb",
            "description": "<p>主表</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "rows.jzdm",
            "description": "<p>旧字段名字</p>"
          },
          {
            "group": "Success 200",
            "type": "Integer",
            "optional": false,
            "field": "rows.xh",
            "description": "<p>序号</p>"
          },
          {
            "group": "Success 200",
            "type": "Integer",
            "optional": false,
            "field": "rows.plen",
            "description": "<p>小数点位数</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "rows.cxms",
            "description": "<p>查询模式</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "rows.xslx",
            "description": "<p>显示类型</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "rows.lx",
            "description": "<p>字段类型</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "rows.bid",
            "description": "<p>表ID</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "rows.kzjson",
            "description": "<p>扩展JSON</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "rows.whr",
            "description": "<p>维护人名字</p>"
          },
          {
            "group": "Success 200",
            "type": "Date",
            "optional": false,
            "field": "rows.whsj",
            "description": "<p>维护时间</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "rows.whrid",
            "description": "<p>维护人ID</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "rows.cjr",
            "description": "<p>创建人名字</p>"
          },
          {
            "group": "Success 200",
            "type": "Date",
            "optional": false,
            "field": "rows.cjsj",
            "description": "<p>创建时间</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "rows.cjrid",
            "description": "<p>创建人ID</p>"
          },
          {
            "group": "Success 200",
            "type": "Integer",
            "optional": false,
            "field": "rows.version",
            "description": "<p>数据记录版本</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "rows.deleted",
            "description": "<p>逻辑删除标记位</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "rows.chgtype",
            "description": "<p>编辑表单时记录字段动态, A:Add,U:Update,D:Delete</p>"
          }
        ]
      },
      "examples": [
        {
          "title": "Success-Response:",
          "content": " HTTP/1.1 200 OK\n\t{\"total\",25, \"current\":1, \"size\":10, \"pages\":3, \"rows\":[\n\t \t\t{\n\t\t\t\"id\" : \"字段明细主键\",\t\n\t\t\t\"nr\" : \"字段内容\",\t\n\t\t\t\"zdzd\" : \"字典字段\",\t\n\t\t\t\"zdbm\" : \"字典表名\",\t\n\t\t\t\"zdwb\" : \"字典文本\",\t\n\t\t\t\"mr\" : \"字段默认值\",\t\n\t\t\t\"href\" : \"字段超链接\",\t\n\t\t\t\"cd\" : \"字段长度\",\t\n\t\t\t\"name\" : \"字段名字\",\t\n\t\t\t\"yzlx\" : \"字段验证类型\",\t\n\t\t\t\"iskey\" : \"是否主键\",\t\n\t\t\t\"isnull\" : \"是否允许为空\",\t\n\t\t\t\"isquery\" : \"是否允许查询\",\t\n\t\t\t\"isshow\" : \"是否显示在表单\",\t\n\t\t\t\"isshowlb\" : \"是否显示为列表\",\t\n\t\t\t\"len\" : \"字段显示长度\",\t\n\t\t\t\"zzd\" : \"主表字段\",\t\n\t\t\t\"zb\" : \"主表\",\t\n\t\t\t\"jzdm\" : \"旧字段名字\",\t\n\t\t\t\"xh\" : \"序号\",\t\n\t\t\t\"plen\" : \"小数点位数\",\t\n\t\t\t\"cxms\" : \"查询模式\",\t\n\t\t\t\"xslx\" : \"显示类型\",\t\n\t\t\t\"lx\" : \"字段类型\",\t\n\t\t\t\"bid\" : \"表ID\",\t\n\t\t\t\"kzjson\" : \"扩展JSON\",\t\n\t\t\t\"whr\" : \"维护人名字\",\t\n\t\t\t\"whsj\" : \"维护时间\",\t\n\t\t\t\"whrid\" : \"维护人ID\",\t\n\t\t\t\"cjr\" : \"创建人名字\",\t\n\t\t\t\"cjsj\" : \"创建时间\",\t\n\t\t\t\"cjrid\" : \"创建人ID\",\t\n\t\t\t\"version\" : \"数据记录版本\",\t\n\t\t\t\"deleted\" : \"逻辑删除标记位\",\t\n\t\t\t\"chgtype\" : \"编辑表单时记录字段动态, A:Add,U:Update,D:Delete\",\t\n\t \t\t}\n\t \t]}\n\t{\"rows\":[\n\t \t\t{\n\t\t\t\"id\" : \"字段明细主键\",\t\n\t\t\t\"nr\" : \"字段内容\",\t\n\t\t\t\"zdzd\" : \"字典字段\",\t\n\t\t\t\"zdbm\" : \"字典表名\",\t\n\t\t\t\"zdwb\" : \"字典文本\",\t\n\t\t\t\"mr\" : \"字段默认值\",\t\n\t\t\t\"href\" : \"字段超链接\",\t\n\t\t\t\"cd\" : \"字段长度\",\t\n\t\t\t\"name\" : \"字段名字\",\t\n\t\t\t\"yzlx\" : \"字段验证类型\",\t\n\t\t\t\"iskey\" : \"是否主键\",\t\n\t\t\t\"isnull\" : \"是否允许为空\",\t\n\t\t\t\"isquery\" : \"是否允许查询\",\t\n\t\t\t\"isshow\" : \"是否显示在表单\",\t\n\t\t\t\"isshowlb\" : \"是否显示为列表\",\t\n\t\t\t\"len\" : \"字段显示长度\",\t\n\t\t\t\"zzd\" : \"主表字段\",\t\n\t\t\t\"zb\" : \"主表\",\t\n\t\t\t\"jzdm\" : \"旧字段名字\",\t\n\t\t\t\"xh\" : \"序号\",\t\n\t\t\t\"plen\" : \"小数点位数\",\t\n\t\t\t\"cxms\" : \"查询模式\",\t\n\t\t\t\"xslx\" : \"显示类型\",\t\n\t\t\t\"lx\" : \"字段类型\",\t\n\t\t\t\"bid\" : \"表ID\",\t\n\t\t\t\"kzjson\" : \"扩展JSON\",\t\n\t\t\t\"whr\" : \"维护人名字\",\t\n\t\t\t\"whsj\" : \"维护时间\",\t\n\t\t\t\"whrid\" : \"维护人ID\",\t\n\t\t\t\"cjr\" : \"创建人名字\",\t\n\t\t\t\"cjsj\" : \"创建时间\",\t\n\t\t\t\"cjrid\" : \"创建人ID\",\t\n\t\t\t\"version\" : \"数据记录版本\",\t\n\t\t\t\"deleted\" : \"逻辑删除标记位\",\t\n\t\t\t\"chgtype\" : \"编辑表单时记录字段动态, A:Add,U:Update,D:Delete\",\t\n\t\t\t}\n\t \t]}",
          "type": "json"
        }
      ]
    },
    "version": "0.0.0",
    "filename": "src/main/java/com/rs/devplatform/controller/admin/ModuleTestController.java",
    "groupTitle": "Module_Test",
    "name": "PostAdminModuletestGetlistIdSys_zdxx"
  },
  {
    "type": "POST",
    "url": "/admin/moduleTest/query",
    "title": "功能测试查询",
    "group": "Module_Test",
    "parameter": {
      "fields": {
        "Parameter": [
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "bid",
            "description": "<p>表id</p>"
          },
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "bm",
            "description": "<p>表名</p>"
          }
        ]
      },
      "examples": [
        {
          "title": "Request-Example: ",
          "content": "{\"(B)TEST_age\":\"0\",\"(E)TEST_age\":\"10000\",\"TEST_type\":\"1\",\"TEST_sex\":\"1\",\"TEST_name\":\"1\",\"id\":\"c35e5a1bf0504495aaca3d07fce977aa\"}",
          "type": "json"
        }
      ]
    },
    "success": {
      "fields": {
        "Success 200": [
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "id",
            "description": "<p>表id</p>"
          }
        ]
      },
      "examples": [
        {
          "title": "Sample:",
          "content": "\tHTTP/1.1 200 OK\n{\"total\":4,\"rows\":[{\"stu_whsj\":1481272012000,\"stu_id\":\"62ca972a13154078ab4ebed391bb023a\",\"stu_version\":1,\"stu_cjsj\":1481272012000,\"stu_whrid\":\"system\",\"stu_age\":1,\"stu_name\":\"1\",\"stu_whr\":\"system\",\"stu_deleted\":\"F\",\"stu_cjrid\":\"system\",\"stu_cjr\":\"system\"},{\"stu_whsj\":1481272012000,\"stu_id\":\"a243d30041d84fd7a0dfc73d14fcd436\",\"stu_version\":3,\"stu_cjsj\":1481258832000,\"stu_whrid\":\"system\",\"stu_age\":100,\"stu_name\":\"test\",\"stu_whr\":\"system\",\"stu_deleted\":\"F\",\"stu_cjrid\":\"system\",\"stu_cjr\":\"system\"},{\"stu_whsj\":1481249289000,\"stu_id\":\"a400bb5016894e79b4286473a08f28bd\",\"stu_version\":1,\"stu_cjsj\":1481249289000,\"stu_whrid\":\"system\",\"stu_age\":1,\"stu_name\":\"1\",\"stu_whr\":\"system\",\"stu_deleted\":\"F\",\"stu_cjrid\":\"system\",\"stu_cjr\":\"system\"},{\"stu_whsj\":1481192389000,\"stu_id\":\"f1526680ffb544feac447663f4589b6a\",\"stu_version\":1,\"stu_cjsj\":1481192389000,\"stu_whrid\":\"system\",\"stu_age\":1,\"stu_name\":\"1\",\"stu_whr\":\"system\",\"stu_deleted\":\"F\",\"stu_cjrid\":\"system\",\"stu_cjr\":\"system\"}]}",
          "type": "json"
        }
      ]
    },
    "version": "0.0.0",
    "filename": "src/main/java/com/rs/devplatform/controller/admin/ModuleTestController.java",
    "groupTitle": "Module_Test",
    "name": "PostAdminModuletestQuery"
  },
  {
    "type": "POST",
    "url": "/admin/moduleTest/query/relationtable",
    "title": "功能测试根据主表查询指定明细表数据",
    "group": "Module_Test",
    "parameter": {
      "fields": {
        "Parameter": [
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "sourceTable",
            "description": "<p>源数据表id</p>"
          },
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "targetTable",
            "description": "<p>目标查询表id</p>"
          }
        ]
      },
      "examples": [
        {
          "title": "Request-Example: ",
          "content": "{\"targetTable\":\"4143ca0e5b6a496487226e95056cfa40\",\"sourceTable\":\"46864e6d14e44f6aa6c6ae415b67a5ed\",\"resume_id\":\"bd0a525b3103487ab2601f8473fe1b27\"}",
          "type": "json"
        }
      ]
    },
    "success": {
      "fields": {
        "Success 200": [
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "id",
            "description": "<p>表id</p>"
          }
        ]
      },
      "examples": [
        {
          "title": "Sample:",
          "content": "\tHTTP/1.1 200 OK\n{\"rows\":[{\"projp_deleted\":\"F\",\"projp_resumeId\":\"bd0a525b3103487ab2601f8473fe1b27\",\"projp_name\":\"项目1\",\"projp_begin\":1477978547000,\"projp_end\":1490938554000,\"projp_whrid\":\"f44ed61e43fd4daaaa13d5aca3b425c6\",\"projp_cjsj\":1488778582000,\"projp_content\":\"经理\",\"projp_whr\":\"f44ed61e43fd4daaaa13d5aca3b425c6\",\"projp_whsj\":1488778582000,\"projp_id\":\"65860e36f5d64c649be31fa81989412d\",\"projp_version\":1,\"projp_cjrid\":\"f44ed61e43fd4daaaa13d5aca3b425c6\",\"projp_cjr\":\"f44ed61e43fd4daaaa13d5aca3b425c6\"}]}",
          "type": "json"
        }
      ]
    },
    "version": "0.0.0",
    "filename": "src/main/java/com/rs/devplatform/controller/admin/ModuleTestController.java",
    "groupTitle": "Module_Test",
    "name": "PostAdminModuletestQueryRelationtable"
  },
  {
    "type": "POST",
    "url": "/admin/moduleTest/sql/execute/{tableid}",
    "title": "自定义按钮调用后台sql",
    "group": "Module_Test",
    "parameter": {
      "fields": {
        "Parameter": [
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "code",
            "description": "<p>按钮编码</p>"
          },
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "id",
            "description": "<p>业务数据id</p>"
          }
        ]
      },
      "examples": [
        {
          "title": "Request-Example: ",
          "content": "{\"code\":\"audit\",\"rows\":[{\"resume_id\":\"xxx\",\"resume_name\":\"admin\",...}，{\"resume_id\":\"yyy\",\"resume_name\":\"admin\",...}]}",
          "type": "json"
        }
      ]
    },
    "success": {
      "examples": [
        {
          "title": "Sample:",
          "content": "HTTP/1.1 200 OK",
          "type": "json"
        }
      ]
    },
    "error": {
      "examples": [
        {
          "title": "Error-Response:",
          "content": "HTTP/1.1 400 Bad Request\n{\n\t\"verror\":\"表单参数不能为空！\"\n}",
          "type": "json"
        }
      ]
    },
    "version": "0.0.0",
    "filename": "src/main/java/com/rs/devplatform/controller/admin/ModuleTestController.java",
    "groupTitle": "Module_Test",
    "name": "PostAdminModuletestSqlExecuteTableid"
  },
  {
    "type": "PUT",
    "url": "/admin/moduleTest/update",
    "title": "功能测试修改",
    "group": "Module_Test",
    "parameter": {
      "fields": {
        "Parameter": [
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "id",
            "description": "<p>表id</p>"
          },
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "tablename",
            "description": "<p>表名</p>"
          }
        ]
      },
      "examples": [
        {
          "title": "Request-Example: ",
          "content": "{\"id\":\"ddaaf72259c84a5f81f6ad9b13b39086\",\"detail\":[{\"records\":[{\"tag\":\"A\",\"TBLMX_fid\":\"1\",\"TBLMX_name\":\"1\",\"TBLMX_id\":\"\"},{\"tag\":\"D\",\"TBLMX_fid\":\"1\",\"TBLMX_name\":\"1\",\"TBLMX_id\":\"a400bb5016894e79b4286473a08f28bd\"},{\"tag\":\"U\",\"TBLMX_fid\":\"1\",\"TBLMX_name\":\"1\",\"TBLMX_id\":\"s400bb5016894edfda08f28bb\"}],\"id\":\"870b1424b1fb4a2483b7bb3dd0f7c951\"}],\"tablename\":\"TBL\",\"master\":{\"TBL_name\":\"1\",\"tag\":\"A\"}}",
          "type": "json"
        }
      ]
    },
    "success": {
      "fields": {
        "Success 200": [
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "id",
            "description": "<p>表id</p>"
          }
        ]
      },
      "examples": [
        {
          "title": "Sample:",
          "content": "\tHTTP/1.1 200 OK\n{\"id\":\"ddaaf72259c84a5f81f6ad9b13b39086\"}",
          "type": "json"
        }
      ]
    },
    "version": "0.0.0",
    "filename": "src/main/java/com/rs/devplatform/controller/admin/ModuleTestController.java",
    "groupTitle": "Module_Test",
    "name": "PutAdminModuletestUpdate"
  },
  {
    "type": "DELETE",
    "url": "/cg/form/{tableId}",
    "title": "5.删除表单(SQL)",
    "description": "<p>删除表单，请参考CG：查询表单详情{DELETE} /cg/form/{tableId}</p>",
    "group": "OnlineFunc",
    "parameter": {
      "fields": {
        "Parameter": [
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "tableId",
            "description": "<p>表ID</p>"
          }
        ]
      }
    },
    "version": "0.0.0",
    "filename": "src/main/java/com/rs/devplatform/controller/cg/OnlineFuncController.java",
    "groupTitle": "OnlineFunc",
    "name": "DeleteCgFormTableid"
  },
  {
    "type": "GET",
    "url": "/cg/form/{tableId}",
    "title": "3.获取在线功能详情",
    "description": "<p>获取在线功能详情，请参考CG：查询表单详情{GET} /cg/form/{tableId}</p>",
    "group": "OnlineFunc",
    "version": "0.0.0",
    "filename": "src/main/java/com/rs/devplatform/controller/cg/OnlineFuncController.java",
    "groupTitle": "OnlineFunc",
    "name": "GetCgFormTableid"
  },
  {
    "type": "POST",
    "url": "/cg/form",
    "title": "2.保存在线功能数据",
    "description": "<p>保存数据,参考{POST} /cg/form</p>",
    "group": "OnlineFunc",
    "version": "0.0.0",
    "filename": "src/main/java/com/rs/devplatform/controller/cg/OnlineFuncController.java",
    "groupTitle": "OnlineFunc",
    "name": "PostCgForm"
  },
  {
    "type": "POST",
    "url": "/cg/onlinefc/sql",
    "title": "1.解析sql",
    "description": "<p>解析sql并返回字段和条件参数</p>",
    "group": "OnlineFunc",
    "parameter": {
      "fields": {
        "Parameter": [
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "sql",
            "description": "<p>SQL语句</p>"
          }
        ]
      },
      "examples": [
        {
          "title": "Request-Example: ",
          "content": "{\"sql\":\"select username as name, password from user,test_group where username=#{abc} and password='123456'\"}",
          "type": "json"
        }
      ]
    },
    "success": {
      "fields": {
        "Success 200": [
          {
            "group": "Success 200",
            "type": "Array",
            "optional": false,
            "field": "tables",
            "description": "<p>sql出现的表名列表</p>"
          },
          {
            "group": "Success 200",
            "type": "JSONArray",
            "optional": false,
            "field": "columns",
            "description": "<p>查询字段列表</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "columns.colName",
            "description": "<p>查询字段名</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "columns.alias",
            "description": "<p>查询字段别名,SQL中出现的AS后面的值</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "columns.isgriddisp",
            "description": "<p>是否表格显示，<strong>默认Y</strong>为显示</p>"
          },
          {
            "group": "Success 200",
            "type": "JSONArray",
            "optional": false,
            "field": "conditions",
            "description": "<p>查询条件</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "conditions.colName",
            "description": "<p>条件变量名(SQL变量统一命名规则username=#{var}, 变量名:var)</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "conditions.expr",
            "description": "<p>条件表达式(SQL变量统一命名规则: username=#{var})</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "conditions.issearch",
            "description": "<p>是否作为查询条件，<strong>默认Y</strong></p>"
          }
        ]
      },
      "examples": [
        {
          "title": "Sample:",
          "content": "HTTP/1.1 200 OK\n{\n \"tables\": [\"user\"],\n \"columns\": [{\n  \"isgriddisp\": \"Y\",\n  \"alias\": \"name\",\n  \"colName\": \"username\"\n },\n {\n  \"isgriddisp\": \"Y\",\n  \"alias\": \"\",\n  \"colName\": \"password\"\n }],\n \"conditions\": [{\n  \"issearch\": \"Y\",\n  \"expr\": \"username = #{abc}\",\n  \"colName\": \"abc\"\n }]\n}",
          "type": "json"
        },
        {
          "title": "Sample:",
          "content": "\tHTTP/1.1 200 OK\n{\"id\":\"9ae3951049e145a2a50cb351497aad5c\",\"version\":1}",
          "type": "json"
        }
      ]
    },
    "error": {
      "examples": [
        {
          "title": "Error-Response:",
          "content": "HTTP/1.1 400 Bad Request\n{\n  \"verrors\":[\n    {\"errCode\":\"\",\"errorMsg\":\"SQL不能为空\"},\n    {\"errCode\":\"\",\"errorMsg\":\"SQL格式错误\"},\n    {\"errCode\":\"tree\",\"errorMsg\":\"树形展示不能不设置父节点和子节点\"},\n    {\"errCode\":\"tree\",\"errorMsg\":\"父子节点不能相同\"},\n    {\"errCode\":\"child\",\"errorMsg\":\"子节点在字段中不存在\"},\n    {\"errCode\":\"parent\",\"errorMsg\":\"父节点在字段中不存在\"}\n    ]\n}",
          "type": "json"
        }
      ]
    },
    "version": "0.0.0",
    "filename": "src/main/java/com/rs/devplatform/controller/cg/OnlineFuncController.java",
    "groupTitle": "OnlineFunc",
    "name": "PostCgOnlinefcSql"
  },
  {
    "type": "PUT",
    "url": "/cg/form/{tableId}",
    "title": "4.更新表单(SQL)",
    "description": "<p>更新表单，请参考CG：查询表单详情{PUT} /cg/form/{tableId}</p>",
    "group": "OnlineFunc",
    "parameter": {
      "fields": {
        "Parameter": [
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "tableId",
            "description": "<p>表ID</p>"
          }
        ]
      }
    },
    "version": "0.0.0",
    "filename": "src/main/java/com/rs/devplatform/controller/cg/OnlineFuncController.java",
    "groupTitle": "OnlineFunc",
    "name": "PutCgFormTableid"
  },
  {
    "type": "GET",
    "url": "/manager/project/{pjId}/test",
    "title": "测试项目切换api",
    "group": "Project_Manager",
    "permission": [
      {
        "name": "Project Manager"
      }
    ],
    "parameter": {
      "fields": {
        "Parameter": [
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "id",
            "description": "<p>项目ID</p>"
          }
        ]
      },
      "examples": [
        {
          "title": "Request-Example: ",
          "content": "GET /manager/project/{pjId}/test",
          "type": "json"
        }
      ]
    },
    "success": {
      "examples": [
        {
          "title": "Success-Response:",
          "content": "HTTP/1.1 200 OK\n{\n\"pjId\":\"1234“\n}",
          "type": "json"
        }
      ]
    },
    "version": "0.0.0",
    "filename": "src/main/java/com/rs/devplatform/controller/manager/ManagerController.java",
    "groupTitle": "Project_Manager",
    "name": "GetManagerProjectPjidTest"
  },
  {
    "type": "GET",
    "url": "/manager/project/{pjId}/users",
    "title": "查询项目分配的人员",
    "group": "Project_Manager",
    "permission": [
      {
        "name": "Project Manager"
      }
    ],
    "parameter": {
      "fields": {
        "Parameter": [
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "userType",
            "description": "<p>项目人员Type(PM:Project Manager/TM:Team Member/Empty-All)</p>"
          }
        ]
      }
    },
    "sampleRequest": [
      {
        "url": "/manager/project/da2f0108587c476e9f3cf4f8466c2779/users"
      },
      {
        "url": "/manager/project/da2f0108587c476e9f3cf4f8466c2779/users?userType=PM"
      }
    ],
    "success": {
      "fields": {
        "Success 200": [
          {
            "group": "Success 200",
            "type": "JSONArray",
            "optional": false,
            "field": "records",
            "description": "<p>返回项目Records</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "records.id",
            "description": "<p>项目名</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "records.name",
            "description": "<p>项目version</p>"
          }
        ]
      },
      "examples": [
        {
          "title": "Success-Response:",
          "content": "HTTP/1.1 200 OK\n\t\t{\n\t\t    \"records\": [\n\t\t        {\n\t\t            \"name\": \"test-77\",\n\t\t            \"id\": \"0f4934679ab24a1a80316a801f56e1b8\",\n\t\t            \"userType\": \"PM\"\n\t\t        },\n\t\t        {\n\t\t            \"name\": \"test-198\",\n\t\t            \"id\": \"088b1c492e584cda89950de98965fdbb\",\n\t\t            \"userType\": \"TM\"\n\t\t        }\n\t\t    ],\n\t\t    \"total\": 2\n\t\t}",
          "type": "json"
        }
      ]
    },
    "version": "0.0.0",
    "filename": "src/main/java/com/rs/devplatform/controller/manager/ManagerController.java",
    "groupTitle": "Project_Manager",
    "name": "GetManagerProjectPjidUsers"
  },
  {
    "type": "POST",
    "url": "/manager/project/{pjId}/init",
    "title": "初始化新项目的基础表",
    "description": "<p>暂只支持项目数据库第一次新建同步(&quot;sys_bxx&quot;, &quot;sys_zdxx&quot;, &quot;sys_cdtype&quot;, &quot;sys_cdval&quot;, &quot;sys_custbt&quot;, &quot;sys_menus&quot;).TODO:@20161205</p>",
    "group": "Project_Manager",
    "permission": [
      {
        "name": "Project Manager"
      }
    ],
    "parameter": {
      "fields": {
        "Parameter": [
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "pjId",
            "description": "<p>项目ID</p>"
          },
          {
            "group": "Parameter",
            "type": "Integer",
            "optional": false,
            "field": "version",
            "description": "<p>项目版本</p>"
          }
        ]
      },
      "examples": [
        {
          "title": "Request-Example: ",
          "content": "POST /manager/project/abc1234/init\n\t\t{\"version\":1}",
          "type": "json"
        }
      ]
    },
    "success": {
      "examples": [
        {
          "title": "Success-Response:",
          "content": "HTTP/1.1 200 OK\n{\n\"version\":2\n}",
          "type": "json"
        }
      ]
    },
    "version": "0.0.0",
    "filename": "src/main/java/com/rs/devplatform/controller/manager/ManagerController.java",
    "groupTitle": "Project_Manager",
    "name": "PostManagerProjectPjidInit"
  },
  {
    "type": "POST",
    "url": "/manager/project/{pjId}/users",
    "title": "增加/修改/删除项目人员",
    "group": "Project_Manager",
    "permission": [
      {
        "name": "Project Manager"
      }
    ],
    "parameter": {
      "fields": {
        "Parameter": [
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "id",
            "description": "<p>项目ID</p>"
          },
          {
            "group": "Parameter",
            "type": "JSONArray",
            "optional": false,
            "field": "users",
            "description": "<p>新增的项目人员数组</p>"
          },
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "users.id",
            "description": "<p>项目人员ID</p>"
          },
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "users.userType",
            "description": "<p>项目人员类型,PM:Project Manager, TM:Team Member, &quot;&quot;：为空表示该人员不是项目成员/或者不传（以前是成员就删除）</p>"
          }
        ]
      },
      "examples": [
        {
          "title": "Request-Example: ",
          "content": "{\n\"id\":\"123\", \"users\":[{\"id\":\"u1\", \"userType\":\"PM\"},{\"id\":\"u2\", \"userType\":\"TM\"},{\"id\":\"u3\", \"userType\":\"\"}]\n}",
          "type": "json"
        }
      ]
    },
    "success": {
      "examples": [
        {
          "title": "Success-Response:",
          "content": "HTTP/1.1 200 OK",
          "type": "json"
        }
      ]
    },
    "error": {
      "examples": [
        {
          "title": "Error-Response:",
          "content": "HTTP/1.1 400 Bad Request",
          "type": "json"
        }
      ]
    },
    "version": "0.0.0",
    "filename": "src/main/java/com/rs/devplatform/controller/manager/ManagerController.java",
    "groupTitle": "Project_Manager",
    "name": "PostManagerProjectPjidUsers"
  },
  {
    "type": "POST",
    "url": "/manager/projects",
    "title": "查询当前用户的项目列表",
    "description": "<p>查询当前用户所分配的项目列表</p>",
    "group": "Project_Manager",
    "permission": [
      {
        "name": "Project Manager"
      }
    ],
    "parameter": {
      "examples": [
        {
          "title": "Request-Example: ",
          "content": "{\"预留\":\"查询过滤\"}",
          "type": "json"
        }
      ]
    },
    "success": {
      "fields": {
        "Success 200": [
          {
            "group": "Success 200",
            "type": "JSONArray",
            "optional": false,
            "field": "records",
            "description": "<p>返回项目Records</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "records.name",
            "description": "<p>项目名</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "records.cjr",
            "description": "<p>创建人</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "records.whr",
            "description": "<p>维护人</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "records.cjsj",
            "description": "<p>创建时间</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "records.whsj",
            "description": "<p>维护时间</p>"
          }
        ]
      },
      "examples": [
        {
          "title": "Success-Response:",
          "content": "HTTP/1.1 200 OK \n{\"records\":[\n{\"cjsj\":1478156290000,\"name\":\"ju-pj-432\",\"cjr\":\"system\",\"id\":\"7d22f1be89f34f6b8ed8f7709c91bc08\"},\n{\"cjsj\":1478153396000,\"name\":\"ju-pj--771\",\"cjr\":\"system\",\"id\":\"1505dc9adb5541e5b641f0a09affe8e8\"}\n],\n\"total\":2}",
          "type": "json"
        }
      ]
    },
    "version": "0.0.0",
    "filename": "src/main/java/com/rs/devplatform/controller/manager/ManagerController.java",
    "groupTitle": "Project_Manager",
    "name": "PostManagerProjects"
  },
  {
    "type": "PUT",
    "url": "/manager/project/{pjId}",
    "title": "更新项目",
    "group": "Project_Manager",
    "parameter": {
      "fields": {
        "Parameter": [
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "name",
            "description": "<p>项目name</p>"
          },
          {
            "group": "Parameter",
            "type": "Integer",
            "optional": false,
            "field": "version",
            "description": "<p>项目version</p>"
          },
          {
            "group": "Parameter",
            "type": "JSONObject",
            "optional": false,
            "field": "db",
            "description": "<p>项目db(不是必须的)</p>"
          },
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "db.dbType",
            "description": "<p>项目对应数据库类型：oracle,mysql</p>"
          },
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "db.dbUrl",
            "description": "<p>项目对应数据库地址</p>"
          },
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "db.dbUser",
            "description": "<p>项目对应数据库用户名</p>"
          },
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "db.dbPwd",
            "description": "<p>项目对应数据库密码</p>"
          }
        ]
      },
      "examples": [
        {
          "title": "Request-Example: ",
          "content": "{\"id\":\"123456\", \"name\":\"只更新项目名称，不修改db资源\", \"version\":1}\n{\"id\":\"123456\", \"name\":\"更新项目名和db资源\", \"version\":1, \"db\":{\"dbType\":\"mysql\", \"dbUrl\":\"192.168.10.64:3306/test_db1\", \"dbUser\":\"root\", \"dbPwd\":\"123456\"}}",
          "type": "json"
        }
      ]
    },
    "success": {
      "fields": {
        "Success 200": [
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "id",
            "description": "<p>项目id</p>"
          },
          {
            "group": "Success 200",
            "type": "Integer",
            "optional": false,
            "field": "version",
            "description": "<p>项目version</p>"
          }
        ]
      },
      "examples": [
        {
          "title": "Success-Response:",
          "content": "HTTP/1.1 200 OK\n{\"id\":\"12345\", \"version\":2}",
          "type": "json"
        }
      ]
    },
    "error": {
      "examples": [
        {
          "title": "Error-Response:",
          "content": "HTTP/1.1 400 Bad Request",
          "type": "json"
        },
        {
          "title": "Error-Response:",
          "content": "HTTP/1.1 302 Found",
          "type": "json"
        },
        {
          "title": "Error-Response:",
          "content": "HTTP/1.1 409 Conflict",
          "type": "json"
        }
      ]
    },
    "version": "0.0.0",
    "filename": "src/main/java/com/rs/devplatform/controller/manager/ManagerController.java",
    "groupTitle": "Project_Manager",
    "name": "PutManagerProjectPjid"
  },
  {
    "type": "DELETE",
    "url": "/cg/form/js/{id}删除增强sql单个",
    "title": "",
    "group": "cg_Manager",
    "success": {
      "examples": [
        {
          "title": "Success-Response:",
          "content": "HTTP/1.1 200 OK",
          "type": "json"
        }
      ]
    },
    "error": {
      "examples": [
        {
          "title": "Error-Response:",
          "content": "HTTP/1.1 400 Bad Request",
          "type": "json"
        },
        {
          "title": "Error-Response:",
          "content": "HTTP/1.1 302 Found",
          "type": "json"
        },
        {
          "title": "Error-Response:",
          "content": "HTTP/1.1 409 Conflict",
          "type": "json"
        }
      ]
    },
    "version": "0.0.0",
    "filename": "src/main/java/com/rs/devplatform/controller/cg/CGController.java",
    "groupTitle": "cg_Manager",
    "name": "DeleteCgFormJsIdSql"
  },
  {
    "type": "POST",
    "url": "/cg/form/sql",
    "title": "增加增强sql",
    "group": "cg_Manager",
    "parameter": {
      "fields": {
        "Parameter": [
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "bdid",
            "description": "<p>表单id</p>"
          },
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "sql",
            "description": "<p>增强sql代码</p>"
          },
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "code",
            "description": "<p>增强sql关联的控件代码</p>"
          },
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "nr",
            "description": "<p>sql说明</p>"
          }
        ]
      },
      "examples": [
        {
          "title": "Request-Example: ",
          "content": "{\"bdid\":\"1\",\"code\":\"add\",\"nr\":\"nr\",\"sql\":\"select * from users\"}",
          "type": "json"
        }
      ]
    },
    "success": {
      "fields": {
        "Success 200": [
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "id",
            "description": "<p>返回增强JS id</p>"
          }
        ]
      },
      "examples": [
        {
          "title": "Success-Response:",
          "content": "HTTP/1.1 200 OK\n{\"id\":\"5179ff002fe6456b866bed0f25943faf\"}",
          "type": "json"
        }
      ]
    },
    "error": {
      "examples": [
        {
          "title": "Error-Response:",
          "content": "HTTP/1.1 302 Found",
          "type": "json"
        },
        {
          "title": "Error-Response:",
          "content": "HTTP/1.1 409 Conflict",
          "type": "json"
        }
      ]
    },
    "version": "0.0.0",
    "filename": "src/main/java/com/rs/devplatform/controller/cg/CGController.java",
    "groupTitle": "cg_Manager",
    "name": "PostCgFormSql"
  },
  {
    "type": "PUT",
    "url": "/cg/form/sql",
    "title": "更新增强sql",
    "group": "cg_Manager",
    "parameter": {
      "fields": {
        "Parameter": [
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "bdid",
            "description": "<p>表单id</p>"
          },
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "sql",
            "description": "<p>增强sql代码</p>"
          },
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "code",
            "description": "<p>增强sql关联按钮</p>"
          },
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "nr",
            "description": "<p>内容</p>"
          }
        ]
      },
      "examples": [
        {
          "title": "Request-Example: ",
          "content": "{\"code\":\"update\",\"bdid\":\"1\",\"nr\":\"unr\",\"sql\":\"select * from users for update\"}",
          "type": "json"
        }
      ]
    },
    "success": {
      "fields": {
        "Success 200": [
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "id",
            "description": "<p>返回增强JS id</p>"
          }
        ]
      },
      "examples": [
        {
          "title": "Success-Response:",
          "content": "HTTP/1.1 200 OK\n{\"id\":\"5179ff002fe6456b866bed0f25943faf\"}",
          "type": "json"
        }
      ]
    },
    "error": {
      "examples": [
        {
          "title": "Error-Response:",
          "content": "HTTP/1.1 302 Found",
          "type": "json"
        },
        {
          "title": "Error-Response:",
          "content": "HTTP/1.1 409 Conflict",
          "type": "json"
        }
      ]
    },
    "version": "0.0.0",
    "filename": "src/main/java/com/rs/devplatform/controller/cg/CGController.java",
    "groupTitle": "cg_Manager",
    "name": "PutCgFormSql"
  },
  {
    "type": "GET",
    "url": "/cg/form/sql/getlist/{id}",
    "title": "获取表单的增强sql",
    "group": "doc",
    "parameter": {
      "examples": [
        {
          "title": "Request-Example: ",
          "content": "{\"code\":\"add\"}",
          "type": "json"
        }
      ]
    },
    "success": {
      "fields": {
        "Success 200": [
          {
            "group": "Success 200",
            "type": "Integer",
            "optional": false,
            "field": "total",
            "description": "<p>总记录条数</p>"
          },
          {
            "group": "Success 200",
            "type": "Integer",
            "optional": false,
            "field": "current",
            "description": "<p>当前页码</p>"
          },
          {
            "group": "Success 200",
            "type": "Integer",
            "optional": false,
            "field": "size",
            "description": "<p>每页显示记录条数</p>"
          },
          {
            "group": "Success 200",
            "type": "Integer",
            "optional": false,
            "field": "pages",
            "description": "<p>总页数</p>"
          },
          {
            "group": "Success 200",
            "type": "JsonArray",
            "optional": false,
            "field": "rows",
            "description": "<p>JSONArray</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "rows.id",
            "description": "<p>增强sql主键</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "rows.sql",
            "description": "<p>增强sql</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "rows.code",
            "description": "<p>增强sql关联的控件编码</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "rows.nr",
            "description": "<p>增强sql内容</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "rows.bdid",
            "description": "<p>表单id</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "rows.whr",
            "description": "<p>维护人名字</p>"
          },
          {
            "group": "Success 200",
            "type": "Date",
            "optional": false,
            "field": "rows.whsj",
            "description": "<p>维护时间</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "rows.whrid",
            "description": "<p>维护人ID</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "rows.cjr",
            "description": "<p>创建人名字</p>"
          },
          {
            "group": "Success 200",
            "type": "Date",
            "optional": false,
            "field": "rows.cjsj",
            "description": "<p>创建时间</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "rows.cjrid",
            "description": "<p>创建人ID</p>"
          },
          {
            "group": "Success 200",
            "type": "Integer",
            "optional": false,
            "field": "rows.version",
            "description": "<p>数据记录版本</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "rows.deleted",
            "description": "<p>逻辑删除标记位</p>"
          }
        ]
      },
      "examples": [
        {
          "title": "Success-Response:",
          "content": " HTTP/1.1 200 OK\n\t{\"total\":1,\"rows\":[{\"cjsj\":1487038184000,\"cjrid\":\"system\",\"code\":\"add\",\"bdid\":\"1\",\"nr\":\"nr\",\"cjr\":\"system\",\"id\":\"8be490227ab842f089086338de00d0b4\",\"version\":1,\"sql\":\"select * from users\"}]}",
          "type": "json"
        }
      ]
    },
    "version": "0.0.0",
    "filename": "src/main/java/com/rs/devplatform/controller/cg/CGController.java",
    "groupTitle": "doc",
    "name": "GetCgFormSqlGetlistId"
  },
  {
    "type": "DELETE",
    "url": "/sample/cgSample",
    "title": "删除示例",
    "group": "sample_cgSample",
    "parameter": {
      "fields": {
        "Parameter": [
          {
            "group": "Parameter",
            "type": "StringArray",
            "optional": false,
            "field": "ids",
            "description": "<p>IDs,id数组</p>"
          }
        ]
      },
      "examples": [
        {
          "title": "Request-Example: ",
          "content": " DELETE /sample/cgSample\n\t{\n\t\t \tids : [\n\t\t \t\t\"id1\", \"id3\", \"id8\"\n\t\t \t]\n\t \t}",
          "type": "json"
        }
      ]
    },
    "success": {
      "examples": [
        {
          "title": "Success-Response:",
          "content": "HTTP/1.1 200 OK",
          "type": "json"
        }
      ]
    },
    "error": {
      "examples": [
        {
          "title": "Error-Response:",
          "content": "HTTP/1.1 400 Bad Request",
          "type": "json"
        },
        {
          "title": "Error-Response:",
          "content": "HTTP/1.1 302 Found",
          "type": "json"
        },
        {
          "title": "Error-Response:",
          "content": "HTTP/1.1 409 Conflict",
          "type": "json"
        }
      ]
    },
    "version": "0.0.0",
    "filename": "src/main/java/com/rs/devplatform/controller/sample/CgSampleController.java",
    "groupTitle": "sample_cgSample",
    "name": "DeleteSampleCgsample"
  },
  {
    "type": "GET",
    "url": "/sample/cgSample/{id}",
    "title": "获取示例详细",
    "group": "sample_cgSample",
    "parameter": {
      "fields": {
        "Parameter": [
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "id",
            "description": "<p>ID,是路径上的参数id</p>"
          }
        ]
      },
      "examples": [
        {
          "title": "Request-Example: ",
          "content": "GET /sample/cgSample/0f077145cc894f7990387c9458091e1b",
          "type": "json"
        }
      ]
    },
    "success": {
      "fields": {
        "Success 200": [
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "id",
            "description": "<p>id主键</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "name",
            "description": "<p>Username用户名</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "nkname",
            "description": "<p>NickName昵称</p>"
          },
          {
            "group": "Success 200",
            "type": "Integer",
            "optional": false,
            "field": "age",
            "description": "<p>年龄</p>"
          },
          {
            "group": "Success 200",
            "type": "Date",
            "optional": false,
            "field": "dt",
            "description": "<p>测试日期</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "whr",
            "description": "<p>whr</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "whrid",
            "description": "<p>whrid</p>"
          },
          {
            "group": "Success 200",
            "type": "Date",
            "optional": false,
            "field": "whsj",
            "description": "<p>whsj</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "cjr",
            "description": "<p>cjr</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "cjrid",
            "description": "<p>cjrid</p>"
          },
          {
            "group": "Success 200",
            "type": "Date",
            "optional": false,
            "field": "cjsj",
            "description": "<p>cjsj</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "deleted",
            "description": "<p>deleted</p>"
          },
          {
            "group": "Success 200",
            "type": "Integer",
            "optional": false,
            "field": "version",
            "description": "<p>version</p>"
          }
        ]
      },
      "examples": [
        {
          "title": "Success-Response:",
          "content": "\t\tHTTP/1.1 200 OK\n {\n\t\t\"id\":\"id主键\",\t\n\t\t\"name\":\"Username用户名\",\t\n\t\t\"nkname\":\"NickName昵称\",\t\n\t\t\"age\":\"年龄\",\t\n\t\t\"dt\":\"测试日期\",\t\n\t\t\"whr\":\"whr\",\t\n\t\t\"whrid\":\"whrid\",\t\n\t\t\"whsj\":\"whsj\",\t\n\t\t\"cjr\":\"cjr\",\t\n\t\t\"cjrid\":\"cjrid\",\t\n\t\t\"cjsj\":\"cjsj\",\t\n\t\t\"deleted\":\"deleted\",\t\n\t\t\"version\":\"version\",\t\n\t \t }",
          "type": "json"
        }
      ]
    },
    "error": {
      "examples": [
        {
          "title": "Error-Response:",
          "content": "HTTP/1.1 400 Bad Request",
          "type": "json"
        }
      ]
    },
    "version": "0.0.0",
    "filename": "src/main/java/com/rs/devplatform/controller/sample/CgSampleController.java",
    "groupTitle": "sample_cgSample",
    "name": "GetSampleCgsampleId"
  },
  {
    "type": "POST",
    "url": "/sample/cgSample",
    "title": "增加示例",
    "group": "sample_cgSample",
    "parameter": {
      "fields": {
        "Parameter": [
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "name",
            "description": "<p>Username用户名</p>"
          },
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "nkname",
            "description": "<p>NickName昵称</p>"
          },
          {
            "group": "Parameter",
            "type": "Integer",
            "optional": false,
            "field": "age",
            "description": "<p>年龄</p>"
          },
          {
            "group": "Parameter",
            "type": "Date",
            "optional": false,
            "field": "dt",
            "description": "<p>测试日期,1488344117269,13位时间戳</p>"
          },
          {
            "group": "Parameter",
            "type": "Date",
            "optional": false,
            "field": "dt2",
            "description": "<p>测试日期,1488344117269,13位时间戳</p>"
          }
        ]
      },
      "examples": [
        {
          "title": "Request-Example: ",
          "content": "{\n  \"name\":\"abc\",\n  \"nkname\":\"abc\",\n  \"age\":123,\t \t\t\t\t\n  \"dt\":1488344117269,\n  \"dt2\":1488344117269,\n}",
          "type": "json"
        }
      ]
    },
    "success": {
      "fields": {
        "Success 200": [
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "id",
            "description": "<p>返回id</p>"
          },
          {
            "group": "Success 200",
            "type": "Integer",
            "optional": false,
            "field": "version",
            "description": "<p>返回version</p>"
          }
        ]
      },
      "examples": [
        {
          "title": "Success-Response:",
          "content": "HTTP/1.1 200 OK\n{\"id\":\"0f077145cc894f7990387c9458091e1b\", \"version\":1}",
          "type": "json"
        }
      ]
    },
    "error": {
      "examples": [
        {
          "title": "Error-Response:",
          "content": "HTTP/1.1 400 Bad Request\n{\n  \"verrors\":[\n    {\"errCode\":\"name\",\"errorMsg\":\"Username用户名不能为空\"},\n  ]\n}",
          "type": "json"
        },
        {
          "title": "Error-Response:",
          "content": "HTTP/1.1 302 Found",
          "type": "json"
        },
        {
          "title": "Error-Response:",
          "content": "HTTP/1.1 409 Conflict",
          "type": "json"
        }
      ]
    },
    "version": "0.0.0",
    "filename": "src/main/java/com/rs/devplatform/controller/sample/CgSampleController.java",
    "groupTitle": "sample_cgSample",
    "name": "PostSampleCgsample"
  },
  {
    "type": "POST",
    "url": "/sample/cgSample/list",
    "title": "获取示例列表",
    "group": "sample_cgSample",
    "parameter": {
      "fields": {
        "Parameter": [
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "name",
            "description": "<p>Username用户名,模糊查询</p>"
          },
          {
            "group": "Parameter",
            "type": "Integer",
            "optional": false,
            "field": "ageFrom",
            "description": "<p>年龄范围开始</p>"
          },
          {
            "group": "Parameter",
            "type": "Integer",
            "optional": false,
            "field": "ageTo",
            "description": "<p>年龄范围结束</p>"
          },
          {
            "group": "Parameter",
            "type": "Date",
            "optional": false,
            "field": "dtFrom",
            "description": "<p>测试日期范围开始,1488344117269, 13位时间戳(前端补齐13位)</p>"
          },
          {
            "group": "Parameter",
            "type": "Date",
            "optional": false,
            "field": "dtTo",
            "description": "<p>测试日期范围结束,1488344127269, 13位时间戳(前端补齐13位)</p>"
          }
        ]
      },
      "examples": [
        {
          "title": "Request-Example: ",
          "content": "{\n \"current\":1, \"size\":10, \"orderBy\":\"name\", \"asc\":\"true\",\n \"name\":\"abc\",\n \"ageFrom\": 18,\t\"ageTo\": 28,\n \"dtFrom\": 1488344117269, \"dtTo\": 1488344127269,\n }\n {\"name\":\"123\", \"orderby\":\"name\", \"comments\":\"不分页,current为null\"}",
          "type": "json"
        }
      ]
    },
    "success": {
      "fields": {
        "Success 200": [
          {
            "group": "Success 200",
            "type": "Integer",
            "optional": false,
            "field": "total",
            "description": "<p>总记录条数</p>"
          },
          {
            "group": "Success 200",
            "type": "Integer",
            "optional": false,
            "field": "current",
            "description": "<p>当前页码</p>"
          },
          {
            "group": "Success 200",
            "type": "Integer",
            "optional": false,
            "field": "size",
            "description": "<p>每页显示记录条数</p>"
          },
          {
            "group": "Success 200",
            "type": "Integer",
            "optional": false,
            "field": "pages",
            "description": "<p>总页数</p>"
          },
          {
            "group": "Success 200",
            "type": "JsonArray",
            "optional": false,
            "field": "rows",
            "description": "<p>JSONArray</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "rows.id",
            "description": "<p>id主键</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "rows.name",
            "description": "<p>Username用户名</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "rows.nkname",
            "description": "<p>NickName昵称</p>"
          },
          {
            "group": "Success 200",
            "type": "Integer",
            "optional": false,
            "field": "rows.age",
            "description": "<p>年龄</p>"
          },
          {
            "group": "Success 200",
            "type": "Date",
            "optional": false,
            "field": "rows.dt",
            "description": "<p>测试日期</p>"
          }
        ]
      },
      "examples": [
        {
          "title": "Success-Response:",
          "content": "HTTP/1.1 200 OK\n{\"total\",25, \"current\":1, \"size\":10, \"pages\":3, \"rows\":[\n  {\n    \"id\" : \"id主键\",\t\n    \"name\" : \"Username用户名\",\t\n    \"nkname\" : \"NickName昵称\",\t\n    \"age\" : \"年龄\",\t\n    \"dt\" : \"测试日期\",\t\n  }\n]}",
          "type": "json"
        }
      ]
    },
    "version": "0.0.0",
    "filename": "src/main/java/com/rs/devplatform/controller/sample/CgSampleController.java",
    "groupTitle": "sample_cgSample",
    "name": "PostSampleCgsampleList"
  },
  {
    "type": "PUT",
    "url": "/sample/cgSample/{id}",
    "title": "更新示例",
    "group": "sample_cgSample",
    "parameter": {
      "fields": {
        "Parameter": [
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "id",
            "description": "<p>ID,是路径上的参数id</p>"
          },
          {
            "group": "Parameter",
            "type": "Integer",
            "optional": false,
            "field": "version",
            "description": "<p>Version</p>"
          },
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "name",
            "description": "<p>Username用户名</p>"
          },
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "nkname",
            "description": "<p>NickName昵称</p>"
          },
          {
            "group": "Parameter",
            "type": "Integer",
            "optional": false,
            "field": "age",
            "description": "<p>年龄</p>"
          },
          {
            "group": "Parameter",
            "type": "Date",
            "optional": false,
            "field": "dt",
            "description": "<p>测试日期</p>"
          }
        ]
      },
      "examples": [
        {
          "title": "Request-Example: ",
          "content": " PUT /sample/cgSample/0f077145cc894f7990387c9458091e1b\n\t{\n\t \t\t\"version\":1,\n\t\t\"name\":\"Username用户名\",\t\n\t\t\"nkname\":\"NickName昵称\",\t\n\t\t\"age\":\"年龄\",\t\n\t\t\"dt\":\"测试日期\",\t\n\t \t }",
          "type": "json"
        }
      ]
    },
    "success": {
      "fields": {
        "Success 200": [
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "id",
            "description": "<p>返回id</p>"
          },
          {
            "group": "Success 200",
            "type": "Integer",
            "optional": false,
            "field": "version",
            "description": "<p>返回version</p>"
          }
        ]
      },
      "examples": [
        {
          "title": "Success-Response:",
          "content": "HTTP/1.1 200 OK\n{\"id\":\"0f077145cc894f7990387c9458091e1b\", \"version\":2}",
          "type": "json"
        }
      ]
    },
    "error": {
      "examples": [
        {
          "title": "Error-Response:",
          "content": "HTTP/1.1 400 Bad Request",
          "type": "json"
        },
        {
          "title": "Error-Response:",
          "content": "HTTP/1.1 302 Found",
          "type": "json"
        },
        {
          "title": "Error-Response:",
          "content": "HTTP/1.1 409 Conflict",
          "type": "json"
        }
      ]
    },
    "version": "0.0.0",
    "filename": "src/main/java/com/rs/devplatform/controller/sample/CgSampleController.java",
    "groupTitle": "sample_cgSample",
    "name": "PutSampleCgsampleId"
  }
] });
