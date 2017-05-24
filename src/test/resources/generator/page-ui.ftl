${r"<#assign base=request.contextPath />"}
<!DOCTYPE html>
<html>

<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <base id="base" href="${r"${base}"}">
    <title>${tableComments}管理</title>
    <link rel="stylesheet" href="${r"${base}"}/lib/jquery-easyui/themes/default/easyui.css">
    <link rel="stylesheet" href="${r"${base}"}/lib/jquery-easyui/themes/icon.css">
    <script src="${r"${base}"}/lib/jquery-easyui/jquery.min.js"></script>
    <script src="${r"${base}"}/lib/jquery-easyui/jquery.easyui.min.js"></script>
    <script src="${r"${base}"}/js/rs-constant.js"></script>
    <script src="${r"${base}"}/js/rs-util.js"></script>
    <script src="${r"${base}"}/js/rs-http.js"></script>
    <!-- CSS -->
    <style type="text/css">
    #fm {
        margin: 0;
        padding: 10px 30px;
    }
    
    .ftitle {
        font-size: 14px;
        font-weight: bold;
        padding: 5px 0;
        margin-bottom: 10px;
        border-bottom: 1px solid #ccc;
    }
    
    .fitem {
        margin-bottom: 5px;
    }
    
    .fitem label {
        display: inline-block;
        width: 80px;
    }
    
    .fitem input {
        width: 160px;
    }
    </style>
</head>
<#assign beanVar="${beanName[0..1]?lower_case}${beanName[2..]}">	
<#assign baseEntityPath="/${beanVar}">	
<#if subModule?? && subModule!="">
<#assign baseEntityPath="/${subModule}/${beanVar}">	
</#if>
<body>
    <!-- DataGrid -->
    <table id="dg" title="${tableComments}列表" class="easyui-datagrid" style="width:700px;height:250px" 
    	url="${baseEntityPath}/list" 
    	toolbar="#toolbar" pagination="true" rownumbers="true" fitColumns="true" singleSelect="true">
        <thead>
            <tr>
            <#list entityFields as field>
				<#if !field.pk && field.formData && field.beanPropertyName!="version">
                <th field="${field.beanPropertyName}" width="auto">${field.formPropertyName}</th>
				</#if>
			</#list>
            </tr>
        </thead>
    </table>
    <div id="toolbar">
        <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-add" plain="true" onclick="add${beanName}()">新增</a>
        <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-edit" plain="true" onclick="edit${beanName}()">修改</a>
        <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-remove" plain="true" onclick="delete${beanName}()">删除</a>
    </div>
    <!-- Dialog -->
    <div id="dlg" class="easyui-dialog" style="width:370px;height:300px;padding:10px 20px" closed="true" buttons="#dlg-buttons">
        <div class="ftitle">${tableComments}</div>
        <form id="fm" method="post" novalidate>
        	<div class="fitem" id="id">
                <label>ID：</label>
                <input name="id" class="easyui-textbox">
            </div>
        	<#list entityFields as field>
				<#if !field.pk && field.formData && field.beanPropertyName!="version">
            <div class="fitem">
                <label>${field.formPropertyName}：</label>
                <input name="${field.beanPropertyName}"" class="easyui-textbox" ${field.nullable?string('','required="true"')}>
            </div>
            	<#elseif field.beanPropertyName=="version">
            	<input name="${field.beanPropertyName}" type="hidden">
				</#if>
			</#list>
        </form>
    </div>
    <div id="dlg-buttons">
        <a href="javascript:void(0)" class="easyui-linkbutton c6" iconCls="icon-ok" onclick="save${beanName}()" style="width:90px">Save</a>
        <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:$('#'+dialogId).dialog('close')" style="width:90px">Cancel</a>
    </div>
    <!-- JavaScript -->
    <script type="text/javascript">
	var url="${baseEntityPath}/list" 
    var requestMethod;
    var datagridId = 'dg';
    var formId = 'fm';
    var dialogId = 'dlg';
    $('#id').hide();
    
    var popup_url;

    function add${beanName}() {
        $('#' + dialogId).dialog('open').dialog('setTitle', '新增${tableComments}');
        $('#' + formId).form('clear');
        requestMethod = 'POST';
        popup_url = "${baseEntityPath}";
    }

    function edit${beanName}() {
        var row = $('#' + datagridId).datagrid('getSelected');
        if (row) {
            $('#' + dialogId).dialog('open').dialog('setTitle', '修改${tableComments}');
            $('#' + formId).form('load', row);
            requestMethod = 'PUT';
            popup_url = "${baseEntityPath}/"+row.id;
        }
    }

    function save${beanName}() {
        if ($('#' + datagridId).form('validate')) {
            var formData = RsUtil.formGetData(formId);
            RsHttp.ajaxJson({
            	url : popup_url,
                method: requestMethod,
                data: formData,
                success: function(result) {
                    if (result.errorMsg) {
                        $.messager.show({
                            title: 'Error',
                            msg: result.errorMsg
                        });
                    } else {
                        $('#' + dialogId).dialog('close'); // close the dialog
                        $('#' + datagridId).datagrid('reload'); // reload the user data
                    }
                },
                error:function(error){
                	var errorStatus = error.status;
                	if(302==errorStatus){
                		alert("Duplicate record existed");
                	}else if(400==errorStatus){
                		alert("Bad Request");
                	}else if(409==errorStatus){
                		alert("Modified by others");
                	}else if(500==errorStatus){
                		alert(error.error);
                	}else{
                		alert("System unavailable, please try later.");
                	}
                	console.log(error);
                }
            });
        }
    }

    function delete${beanName}() {
        var row = $('#' + datagridId).datagrid('getSelected');
        if (row) {
            $.messager.confirm('提醒', '确认删除此${tableComments}？', function(r) {
                if (r) {
                    requestMethod = 'DELETE';
                    RsHttp.ajaxJson({
                        url: "${baseEntityPath}",
                        method: requestMethod,
                        data: {"ids":[row.id]},
                        success: function(result) {
                            if (result.error) {
                                $.messager.show({ // show error message
                                    title: 'Error',
                                    msg: result.error
                                });
                            } else {
                                $('#' + datagridId).datagrid('reload'); // reload the user data
                            }
                        }
                    });
                }
            });
        }
    }
    </script>
</body>

</html>
