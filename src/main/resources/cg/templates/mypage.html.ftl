${r"<#assign base=request.contextPath />"}
<div class="easyui-panel" fit="true">
  <table id="dg${tableId}" toolbar="#toolbarPnl${tableId}"></table>
</div>

<div id="toolbarPnl${tableId}">
  <div id="toolbarBtn${tableId}" class="rs-datagrid-toolbar btn-toolbar" role="toolbar">

  </div>
  <div id="search${tableId}">

  </div>
</div>

<div class="modal fade" tabindex="-1" role="dialog" id="modal${tableId}">
  <div class="modal-dialog" role="document">
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
        <h4 class="modal-title">Modal title</h4>
      </div>
      <div class="modal-body">
        <form id="fm${tableId}" method="post" class="row">
        </form>
        <div id="tab${tableId}">
        </div>
      </div>
      <div class="modal-footer" id="dlgButtons${tableId}">
      </div>
    </div>
  </div>
</div>

<!-- 上传数据dialog -->
<div class="modal fade" tabindex="-1" role="dialog" id="uploadModal${tableId}">
  <div class="modal-dialog" role="document">
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
        <h4 class="modal-title">上传数据</h4>
      </div>
      <div class="modal-body">
        <form id="fmUpload${tableId}" method="post"">
          <div>
            <label>选择文件:</label>
            <input id="inputUpload${tableId}" name="importfile" class="easyui-filebox" style="width: 250px" data-options="buttonText: '请选择'" required="true">
          </div>
        </form>
      </div>
      <div class="modal-footer" id="uploadButtons${tableId}">
        <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
        <button type="button" class="btn btn-primary" id="uploadConfirmBtn${tableId}">确认</button>
      </div>
    </div>
  </div>
</div>

<!-- 附件管理dialog -->
<div class="modal fade" tabindex="-1" role="dialog" id="listAttachement${tableId}">
  <div class="modal-dialog modal-lg" role="document">
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
        <h4 class="modal-title">附件管理</h4>
      </div>
      <div class="modal-body" style="height: 300px">
        <table id="attachementList${tableId}" class="easyui-datagrid" pagination="true" rownumbers="true" fitColumns="true" singleSelect="false" fit="true" toolbar="#fileAttachToolbar${tableId}">
          <thead>
            <tr>
              <th data-options="field:'ck',checkbox:true"></th>
              <th field="name" width="200" halign="center">文件名</th>
              <th field="remark" width="200" halign="center">备注</th>
              <th field="verno" width="300" halign="center" data-options="                formatter: function(value, row, index) {
                  if (value == null) {
                    return '--';
                  } else {
                    return value;
                  }
                }">版本</th>
              <th field="categoryid" width="300" halign="center" data-options="            formatter: function(value, data) {
                  switch (value) {
                    case 'A':
                      {
                        return '重要';
                      };
                      break;
                    case 'B':
                      {
                        return '次要';
                      }
                      break;
                    case 'C':
                      {
                        return '一般';
                      };
                      break;
                    default:
                      {
                        return '--';
                      }
                  }
                }">重要性</th>
              <th field="opt" width="200" halign="center" data-options="formatter: function(value, data, index){
              return '[<li style=\'display:inline\' onclick=\'rs.dev.autolist.openDlg('+index+')\'><span>修改</span></li>]'
              +'[<li style=\'display:inline\' onclick=\'rs.dev.autolist.deleteFile('+index+')\'><span>删除</span></li>]'
              }">操作</th>
            </tr>
          </thead>
        </table>
      </div>
    </div>
  </div>
</div>

<div id="fileAttachToolbar${tableId}">
  <a id="addFileAttach${tableId}" href="javascript:void(0)" class="easyui-linkbutton" iconCls="rsutiltree glyphicon glyphicon-plus" plain="true">新增</a>
</div>

<div class="modal fade" tabindex="-1" role="dialog" id="optAttachement${tableId}">
  <div class="modal-dialog" role="document">
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
        <h4 class="modal-title">上传附件</h4>
      </div>
      <div class="modal-body">
        <form id="fmAddAttachement${tableId}" method="post"">
          <div class="fitem">
            <label style="text-align: right">选择文件：</label>
            <input name="addfile" class="easyui-filebox" required="true" data-options="buttonText:'请选择', onChange: function(newVal, oldVal) {
            var name = newVal.split('.')[0];
              $('#attachementName${tableId}').textbox('setValue', name);
              rs.dev.autolist_${tableId}.onFileChange(newVal);
            }">
          </div>
          <div class="fitem">
            <label style="text-align: right">自定义文件名：</label>
            <input name="name" class="easyui-textbox" required="true" id="attachementName${tableId}">
          </div>
          <div class="fitem">
            <label style="text-align: right">重要性：</label>
            <input name="category" editable="false" class="easyui-combobox" panelHeight="auto" data-options="value:'A', textField: 'text', valueField: 'value', data: [{value: 'A', text: '重要'},{value: 'B', text: '次要'},{value: 'C', text: '一般'}]" required="true">
          </div>
          <div class="fitem">
            <label style="text-align: right">备注：</label>
            <input name="remark" class="easyui-textbox" data-options="multiline: true">
          </div>
        </form>
      </div>
      <div class="modal-footer" id="uploadButtons${tableId}">
        <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
        <button type="button" class="btn btn-primary" id="optAttachConfirmBtn${tableId}">确认</button>
      </div>
    </div>
  </div>
</div>
<!-- 修改附件管理 -->
<div class="modal fade" tabindex="-1" role="dialog" id="updateAttachement${tableId}">
  <div class="modal-dialog" role="document">
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
        <h4 class="modal-title">修改附件</h4>
      </div>
      <div class="modal-body">
        <form id="fmUploadAttachement${tableId}" method="post"">
          <div class="fitem">
            <label style="text-align: right">选择文件：</label>
            <input name="updatefile" class="easyui-filebox" required="true" data-options="onChange:rs.dev.autolist_${tableId}.onFileChange">
          </div>
          <div class="fitem">
            <label style="text-align: right">重要性：</label>
            <input name="category" editable="false" class="easyui-combobox" panelHeight="auto" data-options="value:'A', textField: 'text', valueField: 'value', data: [{value: 'A', text: '重要'},{value: 'B', text: '次要'},{value: 'C', text: '一般'}]" required="true">
          </div>
          <div class="fitem">
            <label style="text-align: right">备注：</label>
            <input name="remark" class="easyui-textbox" data-options="multiline: true">
          </div>
        </form>
      </div>
      <div class="modal-footer" id="uploadButtons${tableId}">
        <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
        <button type="button" class="btn btn-primary" id="updateAttachConfirmBtn${tableId}">确认</button>
      </div>
    </div>
  </div>
</div>

  <#assign beanVar="${beanName[0..1]?lower_case}${beanName[2..]}">
  <#assign baseEntityPath="/${beanVar}">
  <#if subModule?? && subModule!="">
    <#assign baseEntityPath="/${subModule}/${beanVar}">	
  </#if>
  
<script type="text/javascript">
rs.dev.autolist_${tableId} = jQuery.extend({}, rs.dev.default, (function($) {

  // 初始化
  var configId = '${tableId}';
  var urlConfig = '${baseEntityPath}/' + configId; // 后台API配置
  rs.util.mapTabIndexConfigId(configId);
  rs.http.get({
    url: urlConfig
  }).done(function(configData) {
    configData.baseUrl = '${r"${base}"}';

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
    configData.sqlExecuteUrl = '${baseEntityPath}/sql/execute' ;
    configData.sqlExecuteMethod = 'POST';

    rs.global.currentConfigId = configId;
    rs.global['data_' + configId] = configData;
    rs.dev.autolist.autoCreatePage(configData);

    $('#uploadConfirmBtn'+ configId).on('click', function(){
      rs.dev.autolist.saveUploadData(configData);
    })

    $('#addFileAttach'+configId).on('click',function(){
      $('#optAttachement'+configId,rs.util.getSelectedTab()).modal('show');
      $('#fmAddAttachement' + configId).form('clear');
      // $('h4', $('#optAttachement'+configId)).text('新增');
      configData.viewMode = rs.constant.VIEWMODE_ADD;
    })

    $('#optAttachConfirmBtn'+configId).on('click', function(){
      rs.dev.autolist.saveAddAttachement(configData, undefined, this);
    })

    $('#updateAttachConfirmBtn'+configId).on('click', function(){
      rs.dev.autolist.saveAddAttachement(configData, undefined, this);
    })

    setTimeout(function(){
      $('#attachementList'+configId,rs.util.getSelectedTab()).datagrid('resize');
    },200) 

    $('#modal'+configId).on('shown.bs.modal', function (e) {
      var configDetail = configData.detail;
      if (configDetail && configDetail.length > 0) {
        $('#'+configId + '_'+ configDetail[0].id).datagrid('resize');
      }
    })

    $('#listAttachement'+configId).on('shown.bs.modal', function (e) {
      $('#attachementList'+configId).datagrid('resize');
    })

  });
  //获取文件扩展配置
    function fetchFileExtSupport(){
      return rs.http.postJson({
        url:'/admin/datadict/list',
        data:{
          "name": "扩展名",
          "cdtype": ""
        }
      })
    }

    //选择文件完成
    function onFileChange(newVal){
      fetchFileExtSupport().done(function(res){
        if(res && res.total === 0){
          return;
        }
        rs.http.get({
          url:'/admin/datadict/' + res.rows[0].id
        }).done(function(res){
          if(res && res.cdvals.length >0){
            var ext = newVal.split('.')[1];
            var temp = [];
            $.each(res.cdvals,function(index,item){
              temp.push(item.cdval);
            })
            if(temp.indexOf(ext) === -1){
              rs.util.messageError('该文件格式不支持');
              $('.sweet-alert').css('zIndex',10000);
              $('#fileUploadBtn${r"${FOLDER_ID!}"}').linkbutton('disable');
              $('#saveFileEdit${r"${FOLDER_ID!}"}').linkbutton('disable');
            }else{
              $('#fileUploadBtn${r"${FOLDER_ID!}"}').linkbutton('enable');
              $('#saveFileEdit${r"${FOLDER_ID!}"}').linkbutton('enable');
            }
          }
        })
      })
    }
    return {
      onFileChange:onFileChange
    }

})(jQuery));
</script>
