${r"<#assign base=request.contextPath />"}
<!DOCTYPE html>
<html>

<head>
  
  <link rel="stylesheet" href="${r"${base}"}/lib/bootstrap/css/bootstrap.min.css">
  <link rel="stylesheet" href="${r"${base}"}/lib/jquery-easyui/themes/bootstrap/easyui.css">
  <link rel="stylesheet" href="${r"${base}"}/lib/jquery-easyui/themes/icon.css">
  
  <link rel="stylesheet" href="${r"${base}"}/css/rs-common.css">
  <link rel="stylesheet" href="${r"${base}"}/css/rs-formlist.css">
  <link rel="stylesheet" href="${r"${base}"}/css/rs-autolist.css">
  <link rel="stylesheet" href="${r"${base}"}/css/rs-manage.css">
  <link rel="stylesheet" href="${r"${base}"}/css/layim.css">
  <link rel="stylesheet" href="${r"${base}"}/lib/layim/css/layui.css">

</head>
<div id="mainPnl46864e6d14e44f6aa6c6ae415b67a5ed" class="easyui-panel" fit="true">
  <div class="easyui-layout" fit="true" border="false">
    <div class="easyui-panel" data-options="region:'center'">
      <table id="dg46864e6d14e44f6aa6c6ae415b67a5ed"></table>
    </div>
  </div>
</div>

    <script src="${r"${base}"}/lib/jquery-easyui/jquery.min.js"></script>
    <script src="${r"${base}"}/lib/jquery-easyui/jquery.easyui.min.js"></script>
    <script src="${r"${base}"}/lib/jquery-easyui/extension/datagrid-dnd.js"></script>
    <script src="${r"${base}"}/lib/moment.min.js"></script>
    
    <script src="${r"${base}"}/lib/clipboard.min.js"></script>
    
    <script src="${r"${base}"}/lib/bootstrap/js/bootstrap.min.js"></script>
    <script src="${r"${base}"}/lib/sockJs/sockJs.js"></script>
    
    <script src="${r"${base}"}/lib/layer/layer.min.js"></script>
    
    <script src="${r"${base}"}/js/rs.js"></script>
    <script src="${r"${base}"}/js/rs-util.js"></script>
    <script src="${r"${base}"}/js/rs-easyui.js"></script>
    <script src="${r"${base}"}/js/rs-im.js"></script>

<script type="text/javascript">
window.rsContextPath = '${r"${base}"}';

rs.util.getSelectedTab = function() {
  return $('body');
};

rs.dev.autolist_46864e6d14e44f6aa6c6ae415b67a5ed = jQuery.extend({}, rs.dev.default, (function($) {

  // 初始化
  var configId = '46864e6d14e44f6aa6c6ae415b67a5ed';
  <#assign beanVar="${beanName[0..1]?lower_case}${beanName[2..]}">
  <#assign baseEntityPath="/${beanVar}">
  <#if subModule?? && subModule!="">
    <#assign baseEntityPath="/${subModule}/${beanVar}">	
  </#if>
  
  var urlConfig = '${baseEntityPath}/'+  configId; 
  rs.http.get({
    url: urlConfig
  }).done(function(configData) {
    configData.baseUrl = '';

    // 后台API配置
    configData.queryUrl = '${baseEntityPath}/query';
    configData.queryMethod = 'POST';
    configData.addUrl = '${baseEntityPath}/add';
    configData.addMethod = 'POST';
    configData.updateUrl = '${baseEntityPath}/update';
    configData.updateMethod = 'PUT';
    configData.deleteUrl = '${baseEntityPath}';
    configData.deleteMethod = 'DELETE';
    configData.relateTableUrl = '${baseEntityPath}/query/relationtable';
    configData.relateTableMethod = 'POST';
    configData.buttonUrl = '${baseEntityPath}/buttons/' + configData.master.id ;
    configData.buttonMethod = 'GET';
    configData.jsUrl = '${baseEntityPath}/js/' + configData.master.id;
    configData.jsMethod = 'POST';
    configData.sqlGetUrl = '${baseEntityPath}/sql/' + configData.master.id;
    configData.sqlGetMethod = 'POST';
    configData.sqlExecuteUrl = '${baseEntityPath}/sql/execute/' + configData.master.id;
    configData.sqlExecuteMethod = 'POST';

    rs.global.currentConfigId = configId;
    rs.global['data_' + configId] = configData;
    rs.dev.autolist.autoCreatePage(configData);
  });

})(jQuery));
</script>

<body>
 
</body>


</html>