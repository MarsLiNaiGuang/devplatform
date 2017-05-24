'use strict';

var rs = (function(rs, $, window) {

  /**
   * 常量
   */
  rs.constant = rs.constant || {
    // 视图模式
    VIEWMODE_VIEW: 0,
    VIEWMODE_QUERY: 1,
    VIEWMODE_ADD: 2,
    VIEWMODE_EDIT: 3,
    VIEWMODE_DELETE: 4,
    VIEWMODE_SAVE: 5,
    VIEWMODE_COPY: 6,

    // 提示信息
    MESSAGE_VIEW: '请先选择要查看的记录',
    MESSAGE_EDIT: '请先选择要修改的记录',
    MESSAGE_DELETE: '请先选择要删除的记录',
    MESSAGE_SYNC: '请先选择要同步的记录',
    MESSAGE_TEST: '请先选择要测试的记录',
    MESSAGE_OPERATION: '请先选择要操作的记录',
    MESSAGE_SINGLE: '请只选择要操作的单条记录',

    //文件最大值
    MAX_FILE_SIZE: 1024 * 100,
  };

  /**
   * HTTP辅助API
   */
  rs.http = rs.http || {
    ajax: function(obj,server) {
      var url;
      if(server === undefined){
        url = window.rsContextPath + obj.url;
      }else if(server == "wf"){
        url = "http://localhost:9090/wf" + obj.url;
      }
      return $.ajax({
        url: url,
        method: obj.method || 'GET',
        data: obj.data,
        contentType: obj.contentType || 'application/x-www-form-urlencoded; charset=UTF-8'
      }).done(obj.success || function() {
        //
      }).fail(obj.error || function(jqXHR, textStatus) {
        if (jqXHR.status === 500) {
          rs.util.messageError(jqXHR.responseJSON.error || '服务器出错，请检查菜单URL是否配置正确');
        } else if (jqXHR.status === 302) {
          rs.util.messageError('该记录已存在，请检查重试');
        } else if (jqXHR.status === 400) {
          if (jqXHR.responseJSON.verrors && jqXHR.responseJSON.verrors.length > 0) {
            var errorMsg = "";
            $.each(jqXHR.responseJSON.verrors, function(index, verror) {
              // errorMsg = errorMsg + verror.errorMsg + '\n';
              errorMsg +=  verror.errorMsg + '\n';
            })
          }
          rs.util.messageError(errorMsg || jqXHR.responseJSON.error || '有无效参数，请刷新数据后检查重试');
        } else {
          rs.util.messageError(jqXHR.responseJSON.error || '服务器出错，请检查重试');
        }
      });
    },
    get: function(obj) {
      return rs.http.ajax({
        url: obj.url,
        method: 'GET',
        success: obj.success,
        error: obj.error
      });
    },
    ajaxJson: function(obj, server) {
      return rs.http.ajax({
        url: obj.url,
        method: obj.method,
        data: JSON.stringify(obj.data),
        contentType: 'application/json; charset=UTF-8',
        success: obj.success,
        error: obj.error
      }, server);
    },
    postJson: function(obj) {
      return rs.http.ajax({
        url: obj.url,
        method: 'POST',
        data: JSON.stringify(obj.data),
        contentType: 'application/json; charset=UTF-8',
        success: obj.success,
        error: obj.error
      });
    }
  };

  /**
   * 业务开发
   */
  rs.dev = rs.dev || {
    // 默认方法实现
    default: {
      query: function() {
        var datagrids = $('#contentTabs').tabs('getSelected').find('.easyui-datagrid');
        if (datagrids && datagrids.length > 0) {
          $(datagrids[0]).datagrid('reload');
        }
      },
      reset: function() {
        var forms = $('#contentTabs').tabs('getSelected').find('form');
        if (forms && forms.length > 0) {
          $(forms[0]).form('reset');
        }
      },
      showPropMessage: function() {
        rs.util.messageWarning("您没有操作此按钮的权限，请联系管理员进行分配！");
      }
    },

    autolist: (function() {

      function dialog(settings) {
        var targetConfigId = settings;
        var menuId;
        var action;
        var index;
        if ($.isPlainObject(settings)) {
          targetConfigId = settings.form || settings.config;
          action = settings.action;
          index = settings.index;
          menuId = settings.menu;
        } else if (arguments.length > 1) {
          menuId = arguments[1];
        }

        if (action === 'view') {
          getAutoListData(targetConfigId).done(function(targetConfigData) {
            configBackendUrl(targetConfigData);
            rs.global['data_' + targetConfigId] = targetConfigData;
            viewMaster(targetConfigId);
          });
        } else if (action === 'add') {
          getAutoListData(targetConfigId).done(function(targetConfigData) {
            configBackendUrl(targetConfigData);
            rs.global['data_' + targetConfigId] = targetConfigData;
            addMaster(targetConfigData);
          });
        } else {
          var sourceConfigId = rs.global.currentConfigId;
          var sourceRows = $('#dg' + sourceConfigId, rs.util.getSelectedTab()).datagrid('getSelections');
          if (sourceRows.length !== 1) {
            rs.util.messageWarning(rs.constant.MESSAGE_SINGLE);
            return;
          }

          getAutoListData(targetConfigId).done(function(targetConfigData) {
            configBackendUrl(targetConfigData);
            targetConfigData.menuId = menuId;
            rs.global['data_' + targetConfigId] = targetConfigData;
            var d1 = getDataDictBySql(targetConfigData);
            var d2 = getVisibleAndOptArray(targetConfigData);
            var d3 = executeJsList(targetConfigData.master.id);
            var d4 = getCustomerButtons(targetConfigData);
            $.when(d1, d2, d3, d4).done(function() {
              var sourceConfigData = rs.global['data_' + sourceConfigId];
              var sourceTableName = sourceConfigData.master.bm;
              var sourceIdFieldName = rs.util.getTableId(sourceTableName).toUpperCase();
              var sourceRecordId = sourceRows[0][sourceIdFieldName];
              var targetConfigId = targetConfigData.master.id;
              var relateData = {
                sourceTable: sourceConfigId,
                targetTable: targetConfigId
              };
              relateData[sourceIdFieldName] = sourceRecordId;
              getRelatedRowData(relateData).done(function(data) {
                if (data && data.rows) {
                  if (data.rows.length > 0) {
                    var d1 = createAllCustomeButtonHandlerBatch(targetConfigData);
                    $.when(d1).done(function() {
                      customerMaster(targetConfigId, data.rows[0]);
                    });
                  } else {
                    addMaster(targetConfigData);
                  }
                }
              });
            });
          });
        }
      }

      function getAutoListData(configId) {
        return rs.http.get({
          url: '/admin/moduleTest/module/' + configId
        });
      }

      /* 后台API配置 */
      function configBackendUrl(configData) {
        configData.queryUrl = '/admin/moduleTest/query';
        configData.queryMethod = 'POST';
        configData.addUrl = '/admin/moduleTest/add';
        configData.addMethod = 'POST';
        configData.updateUrl = '/admin/moduleTest/update';
        configData.updateMethod = 'PUT';
        configData.deleteUrl = '/admin/moduleTest';
        configData.deleteMethod = 'DELETE';
        configData.relateTableUrl = '/admin/moduleTest/query/relationtable';
        configData.relateTableMethod = 'POST';

        var configId = configData.master.id;
        if (!configId) {
          console.error('invalid configId');
        } else {
          configData.buttonUrl = '/cg/form/' + configId + '/buttons';
          configData.buttonMethod = 'GET';
          configData.jsUrl = '/cg/form/js/getlist/' + configId;
          configData.jsMethod = 'POST';
          configData.sqlGetUrl = '/cg/form/sql/getlist/' + configId;
          configData.sqlGetMethod = 'POST';
          configData.sqlExecuteUrl = '/admin/moduleTest/sql/execute/' + configId;
          configData.sqlExecuteMethod = 'POST';
        }
      }

      function getRelatedRowData(relateData) {
        return rs.http.ajaxJson({
          url: relateTableUrl,
          method: relateTableMethod,
          data: relateData
        });
      }

      // function autoCreatePage(configData) {
      //   var configMaster = configData.master;
      //   var configId = configMaster.id;
      //   var d1 = getDataDictBySql(configData);
      //   var d2 = getVisibleAndOptArray(configData);
      //   var d3 = getFormJsList(configData);
      //   var d4 = getCustomerButtons(configData);
      //   $.when(d1, d2, d3, d4).done(function() {
      //     getToolbarButtons(configData).done(function() {
      //       if (configMaster.istree === "Y") {
      //         createTreeGrid(configData);
      //       } else {
      //         createDataGrid(configData);
      //       }
      //       addToolbarButtons($('#toolbarBtn'+configId), configData);
      //       createSearchForm(configData);
      //       refreshForm(configData);
      //     })
      //   })
      // }

      function checkDatagridSelections() {
        var configId = rs.global.currentConfigId;
        var rows = $('#dg' + configId, rs.util.getSelectedTab()).datagrid('getSelections');
        if (!configId || rows.length === 0) {
          rs.util.messageWarning(rs.constant.MESSAGE_OPERATION);
          return false;
        }
        return true;
      }

      function createSearchForm(configData) {
        var configMaster = configData.master;
        var configId = configMaster.id;
        var dataDict = configData.datadict;
        var ziduanList = configMaster.ziduanList;
        var visibleArray = configData.visibleArray;
        var optArray = configData.optArray;
        var $element;
        var $elementB;
        var $elementE;
        var count = 0;
        var searchZiduanList = [];
        var searchRangeZiduanList = [];
        if (ziduanList && ziduanList.length > 0) {
          $.each(ziduanList, function(index, ziduan) {
            var isShow = (visibleArray.length == 0) || ($.inArray(ziduan.name, visibleArray) >= 0);
            if (ziduan.isquery == 'Y' && isShow) {
              if (ziduan.cxms == 'normal' || ziduan.cxms == 'fuzzy') {
                searchZiduanList.push(ziduan);
              } else if (ziduan.cxms == 'range') {
                searchRangeZiduanList.push(ziduan);
              }
            }
          });
          $.merge(searchZiduanList, searchRangeZiduanList);
        }

        if (searchZiduanList && searchZiduanList.length > 0) {
          $('<div id="searchPnl' + configId + '" class="easyui-panel rs-searchpanel"></div>')
            .append($('<form id="searchfm' + configId + '" class="form-inline rs-searchbox" method="post"></form>'))
            .appendTo($('#search'+ configId, rs.util.getSelectedTab()));

          $.each(searchZiduanList, function(index, ziduan) {
            if (ziduan.cxms == 'normal' || ziduan.cxms == 'fuzzy') {
              rs.util.addSearchDivByZiduan($('#searchfm' + configId), ziduan);
              $element = $('#' + ziduan.id, rs.util.getSelectedTab());
              rs.util.addComponentByField($element, ziduan, dataDict);
              count = count + 1;
            } else if (ziduan.cxms == 'range') {
              if ((count + 1) % 3 == 0) {
                $('<div/>').addClass('form-group').css('width', '33%')
                  .appendTo($('#searchfm' + configId));
                count = count + 1;
              }
              rs.util.addSearchDivByZiduan($('#searchfm' + configId), ziduan);
              $elementB = $('#' + ziduan.id + 'B', rs.util.getSelectedTab());
              rs.util.addComponentByField($elementB, ziduan, dataDict);
              $elementE = $('#' + ziduan.id + 'E', rs.util.getSelectedTab());
              rs.util.addComponentByField($elementE, ziduan, dataDict);
              count = count + 2;
            }
          });

          var selector = '#searchfm' + configId + ' input.textbox-text';
          $(selector, rs.util.getSelectedTab()).keypress(function(e) {
            // enter key press handler
            if (e.which === 13) {
              $('#query', rs.util.getSelectedTab()).click(); // click query button
            }
          });
        }
      }

      function getVisibleAndOptArray(configData) {
        configData.visibleArray = [];
        configData.optArray = [];
        return rs.http.get({
          url: '/auth/r2m/operation/' + (configData.menuId || rs.global.currentMenuId),
          success: function(result) {
            if (result.contents && result.contents.length > 0) {
              $.each(result.contents, function(contentIndex, content) {
                $.each(content, function(itemIndex, item) {
                  if (item.S == "true" && $.inArray(item.id, configData.visibleArray) < 0) {
                    configData.visibleArray.push(item.id);
                  }
                  if (item.E == "true" && $.inArray(item.id, configData.optArray) < 0) {
                    configData.optArray.push(item.id);
                  }
                });
              });
            }
          }
        });
      }

      function executeJsList(configData) {
        return rs.http.ajaxJson({
          url: configData.jsUrl,
          method: configData.jsMethod,
          data: {
            jslx: 'list'
          },
          success: function(result) {
            $.each(result.rows, function(index, row) {
              rs.util.executeScript(row.js);
            });
          }
        });
      }

      function refreshForm(configData) {
        var configMaster = configData.master;
        var configId = configMaster.id;
        $('#mainPnl' + configId, rs.util.getSelectedTab()).panel('resize');
        return executeJsForm(configData);
      }

      function createDataGrid(configData) {
        var configMaster = configData.master;
        var configId = configMaster.id;
        var dataDict = configData.datadict;
        var visibleArray = configData.visibleArray;
        var columns = rs.util.getColumnsByZiduan(configMaster.ziduanList, dataDict);

        var tableName = configMaster.bm;
        var versionColumn = getVersionColumn(tableName, configData);
        columns.push(versionColumn);
        var idColumn = getIdColumns(tableName, configData);
        columns.push(idColumn);
        var columnsCount = -2;

        //visibleColumn
        $.each(columns, function(index, column) {
          if ((visibleArray.length > 0) && ($.inArray(column.field, visibleArray) == -1)) {
            column.hidden = true;
          }
          columnsCount = columnsCount + 1;
        });

        // 显示复选框
        if (configMaster.iscbox === 'Y') {
          columns.unshift({
            field: 'ck',
            checkbox: true
          });
        }

        $('#dg' + configId, rs.util.getSelectedTab()).datagrid({
          // toolbar: configData.toolbarButtons,
          striped: true,
          autoRowHeight: true,
          pagination: configMaster.ispage === 'Y',
          rownumbers: true,
          fitColumns: columnsCount < 10,
          singleSelect: configMaster.iscbox !== 'Y',
          fit: true,
          url: configData.baseUrl + configData.queryUrl,
          method: configData.queryMethod,
          columns: [
            columns
          ],
          onBeforeLoad: configData.onBeforeLoad||function (param)  {
            var $formSearch = $('#searchfm' + configId, rs.util.getSelectedTab());
            var searchparam = getFormattedFormData($formSearch, configMaster.ziduanList);
            $.extend(param, searchparam);
            param.id = configId;        
          },
          onClickRow: function(index, row) {
            rs.global.datagrid.clickedRow = row;
          }, 
          onLoadSuccess: function(data){
            $('#dg' + configId, rs.util.getSelectedTab()).datagrid('selectRow', 0);
          }
        });
      }

      function createTreeGrid(configData) {
        var configMaster = configData.master;
        var configId = configMaster.id;
        var dataDict = configData.datadict;
        var visibleArray = configData.visibleArray;
        var tableName = configMaster.bm;
        var idField = getIdField(tableName, configData);
        var fidField = configMaster.parent;
        var columns = rs.util.getColumnsByZiduan(configMaster.ziduanList, dataDict);
        var versionColumn = getVersionColumn(tableName, configData);
        columns.push(versionColumn);
        var idColumn = getIdColumns(tableName, configData);
        columns.push(idColumn)

        //visibleColumn
        $.each(columns, function(index, column) {
          if ((visibleArray.length > 0) && ($.inArray(column.field, visibleArray) == -1)) {
            column.hidden = true;
          }
        });

        // 显示复选框
        // if (configMaster.iscbox === 'Y') {
        //   columns.unshift({
        //     field: 'ck',
        //     checkbox: true
        //   });
        // }

        $('#dg' + configId, rs.util.getSelectedTab()).treegrid({
          idField: idField,
          treeField: configMaster.child,
          // toolbar: configData.toolbarButtons,
          // striped: true,
          // autoRowHeight: true,
          // pagination: configMaster.ispage === 'Y',
          rownumbers: true,
          fitColumns: true,
          // singleSelect: configMaster.iscbox !== 'Y',
          fit: true,
          url: configData.baseUrl + configData.queryUrl,
          method: configData.queryMethod,
          columns: [
            columns
          ],
          onBeforeLoad: configData.onBeforeLoad||function (row, param)  {
            var $formSearch = $('#searchfm' + configId, rs.util.getSelectedTab());
            var searchparam = getFormattedFormData($formSearch, configMaster.ziduanList);
            $.extend(param, searchparam);
            param.id = configId;        
          },

          loadFilter: function(data) {
            var treeData = rs.util.transFormTreeData(idField, fidField, data.rows);
            return treeData;
          },

          onClickRow: function(row) {
            rs.global.datagrid.clickedRow = row;
          }
        });
      }

      function getDataDictBySql(configData) {
        var deferred = jQuery.Deferred();
        var configMaster = configData.master;
        var dataDict = configData.datadict;
        try {
          $.each(configMaster.ziduanList, function(indx, ziduanItem) {
            if (ziduanItem.zdbm != null && ziduanItem.zdbm != "") {
              var bmindex = ziduanItem.zdbm.lastIndexOf('_');
              var cdvalCol = ziduanItem.zdzd;
              var cddescpCol = ziduanItem.zdwb;
              if (bmindex >= 0) {
                cdvalCol = ziduanItem.zdbm.substring(bmindex + 1) + "_" + cdvalCol;
                cddescpCol = ziduanItem.zdbm.substring(bmindex + 1) + "_" + cddescpCol;
              }
              var urlCustomDataDict = '/common/code/values?dictTable=' + ziduanItem.zdbm + '&cdvalCol=' + cdvalCol + '&cddescpCol=' + cddescpCol;

              rs.http.get({
                url: urlCustomDataDict
              }).done(function(result) {
                dataDict[ziduanItem.zdzd] = result.cdvals;
              });
            }
          });
          deferred.resolve();
        } catch (e) {
          deferred.reject();
        }
        return deferred.promise();
      }

      function executeZqSQLFunc(code, configData) {
        getZqSQL(code, configData).done(function(result) {
          if (result.rows.length > 0) {
            executeZqSQL(code, configData);
          }
        });
      }

      function getZqSQL(code, configData) {
        return rs.http.ajaxJson({
          url: configData.sqlGetUrl,
          method: configData.sqlGetMethod,
          data: {
            code: code
          }
        });
      }

      function executeZqSQL(code, configData) {
        var configMaster = configData.master;
        var configId = configMaster.id;
        var tableName = configMaster.bm;
        var executeUrl;
        if(configData.queryUrl.indexOf('/admin/moduleTest')!=-1){
          executeUrl = configData.sqlExecuteUrl;
        }else{
          executeUrl = configData.sqlExecuteUrl + '/' + code;
        }

        if ($('#dg' + configId).length > 0) {
          var items = $('#dg' + configId).datagrid('getSelections');
          if (items) {
            var param = {};
            param.rows = items;
            param.code = code;
            rs.http.ajaxJson({
              url: executeUrl,
              method: configData.sqlExecuteMethod,
              data: param,
              success: function(result) {
                if (result.errorMsg) {
                  rs.util.messageError(result.errorMsg);
                } else {
                  rs.util.messageInfo("执行成功！");
                  $('#dg' + configId).datagrid('reload');
                }
              }
            });
          } else {
            rs.util.messageWarning("请选择需要操作的数据！");
          }
        } else if ($('#fm' + configId).length > 0) {
          var item = getFormattedFormData($('#fm' + configId), configData.master.ziduanList);
          if (item) {
            var rows = [];
            rows.push(item);
            var param = {};
            param.rows = rows;
            param.code = code;

            rs.http.ajaxJson({
              url: sqlExecuteUrl,
              method: 'POST',
              data: param,
              success: function(result) {
                if (result.errorMsg) {
                  rs.util.messageError(result.errorMsg);
                } else {
                  $('#dlg' + configId).dialog('destroy');
                  $('#dg' + rs.global.currentConfigId).datagrid('reload');
                  rs.util.messageInfo("执行成功！");
                }
              }
            });
          }
        }
      }

      function getNormalButtons(configData) {
        return configData.normalButtons = [{
          id: 'add',
          text: '新增',
          iconCls: 'rsutiltree glyphicon glyphicon-plus'
        },{
          id: 'edit',
          text: '修改',
          iconCls: 'rsutiltree glyphicon glyphicon-edit'
        }, {
          id: 'view',
          text: '查看',
          iconCls: 'rsutiltree glyphicon glyphicon-zoom-in'
        },{
          id: 'delete',
          text: '删除',
          iconCls: 'rsutiltree glyphicon glyphicon-trash'
        }, {
          id: 'search',
          text: '查询',
          iconCls: 'rsutiltree glyphicon glyphicon-search'
        }, {
          id: 'reset',
          text: '重置',
          iconCls: 'rsutiltree glyphicon glyphicon-refresh'
        }, {
          id: 'downloadTemplet',
          text: '下载模板',
          iconCls: 'rsutiltree glyphicon glyphicon-download'
        }, {
          id: 'downloadData',
          text: '下载数据',
          iconCls: 'rsutiltree glyphicon glyphicon-download'
        }, {
          id: 'uploadData',
          text: '上传数据',
          iconCls: 'rsutiltree glyphicon glyphicon-upload'
        }, {
          id: 'uploadFile',
          text: '附件管理',
          iconCls: 'rsutiltree glyphicon glyphicon-upload'
        }, {
          id: 'workFlow',
          text: '工作流',
          iconCls: 'rsutiltree fa fa-bars'
        }];
      }

      function getCustomerButtons(configData) {
        configData.customeButtons = [];
        var configId = configData.master.id;
        return rs.http.ajax({
          url: configData.buttonUrl,
          method: configData.buttonMethod,
          success: function(result) {
            result.rows.sort(function(a, b) {
              return a.xh - b.xh;
            });
            $.each(result.rows, function(index, row) {
              configData.customeButtons.push({
                id: row.code,
                text: row.name,
                iconCls: row.icon
              });
            });
          }
        });
      }

      function getToolbarButtons(configData) {
        var deferred = jQuery.Deferred();
        try {
          var normalButtons = getNormalButtons(configData);
          var customeButtons = configData.customeButtons;
          var visibleArray = configData.visibleArray;
          var optArray = configData.optArray;
          var userNormalButtons = configData.userNormalButtons = [];
          var userCustomerButtons = configData.userCustomerButtons = [];
          $.each(normalButtons, function(index, buttonObj) {
            if (visibleArray.length == 0 || $.inArray(buttonObj.id, visibleArray) >= 0) {
              if (optArray.length == 0 || $.inArray(buttonObj.id, optArray) >= 0) {
                createNormalButtonsHandler(buttonObj, configData);
              } else {
                buttonObj.handler = rs.dev.autolist.showPropMessage;
              }
              userNormalButtons.push(buttonObj);
            }
          });
          $.each(customeButtons, function(index, buttonObj) {
            if (visibleArray.length == 0 || $.inArray(buttonObj.id, visibleArray) >= 0) {
              if (optArray.length == 0 || $.inArray(buttonObj.id, optArray) >= 0) {
                createCustomeButtonsHandler(buttonObj, configData);
              } else {
                buttonObj.handler = rs.dev.autolist.showPropMessage;
              }
              userCustomerButtons.push(buttonObj);
            }
          })
          deferred.resolve();
        } catch (e) {
          deferred.reject();
        }
        return deferred.promise();
      }

      function createAllCustomeButtonHandlerBatch(configData) {
        var deferred = jQuery.Deferred();
        try {
          var customeButtons = configData.customeButtons;
          var visibleArray = configData.visibleArray;
          var optArray = configData.optArray;

          $.each(customeButtons, function(index, buttonObj) {
            if (visibleArray.length == 0 || $.inArray(buttonObj.id, visibleArray) >= 0) {
              if (optArray.length == 0 || $.inArray(buttonObj.id, optArray) >= 0) {
                createCustomeButtonsHandler(buttonObj, configData);
              } else {
                buttonObj.handler = rs.dev.autolist.showPropMessage;
              }
            }
          })
          deferred.resolve();
        } catch (e) {
          deferred.reject();
        }
        return deferred.promise();
      }

      function createCustomeButtonsHandler(buttonObj, configData) {
        return buttonObj.handler = function() {
          if (typeof(window[buttonObj.id]) === "function") {
            eval(buttonObj.id + '()');
          }
          // execute Customer Button ZqSQL
          executeZqSQLFunc(buttonObj.id, configData);
        }
      }

      function createNormalButtonsHandler(buttonObj, configData) {
        var configMaster = configData.master;
        var configId = configMaster.id;
        return buttonObj.handler = function() {
          if (buttonObj.id === "add") {
            addMaster(configData);
          } else if (buttonObj.id === "edit") {
            editMaster(configId);
          } else if(buttonObj.id === "view"){
            viewMaster(configId);
          }else if (buttonObj.id === "delete") {
            deleteMaster(configData);
          } else if (buttonObj.id === "search") {
            queryMaster(configData);
          } else if (buttonObj.id === "reset") {
            rs.dev.default.reset();
          } else if (buttonObj.id === "downloadTemplet") {
            downloadExcelTemplet(configId);
          } else if (buttonObj.id === "downloadData") {
            downloadExcelData(configId);
          } else if (buttonObj.id === "uploadData") {
            uploadExcelData(configData);
          } else if (buttonObj.id === 'uploadFile') {
            uploadAttachement(configData);
          }
        }
      }

      function getFormJsList(configData) {
        return rs.http.ajaxJson({
          url: configData.jsUrl,
          method: configData.jsMethod,
          data: {
            jslx: 'list'
          },
          success: function(result) {
            $.each(result.rows, function(index, row) {
              rs.util.executeScript(row.js);
            });
          }
        });
      }

      function queryMaster(configData) {
        var configMaster = configData.master;
        var configId = configMaster.id;
        if (configMaster.istree !== "Y") {
          $('#dg' + configId, rs.util.getSelectedTab()).datagrid('reload');
        } else {
          $('#dg' + configId, rs.util.getSelectedTab()).treegrid('reload');
        }
        // $('#dg' + configId, rs.util.getSelectedTab()).datagrid('reload');
      }

      function addMaster(configData) {
        var configId = configData.master.id;
        var configDetail = configData.detail;
        configData.viewMode = rs.constant.VIEWMODE_ADD;

        createDialog(configData).done(function() {
          $('#modal'+configId,rs.util.getSelectedTab()).modal('show');
          $('#fm' + configId).form('clear');
          $('h4', $('#modal'+configId)).text('新增');
          if (configDetail && configDetail.length > 0) {
            clearDetailTabData(configData);
          }
          configData.urlMaster = configData.addUrl;
          configData.methodHttp = configData.addMethod;
          executeCallback();
        });
      }

      /**
       * 查看表单记录
       */
      function viewMaster(configId, row) {
        setTimeout(function() {
          configId = configId || rs.global.currentConfigId;
          if (row === undefined) {
            var rows = $('#dg' + configId, rs.util.getSelectedTab()).datagrid('getSelections');
            if (!configId || rows.length === 0) {
              rs.util.messageWarning(rs.constant.MESSAGE_EDIT);
              return;
            } else if (rows.length !== 1) {
              rs.util.messageWarning(rs.constant.MESSAGE_SINGLE);
              return;
            }
            row = rows[0];
          }
          var configData = rs.global['data_' + configId];
          var ziduanList = configData.master.ziduanList;
          configData.viewMode = rs.constant.VIEWMODE_VIEW;
          configData.row = row;
          createDialog(configData).done(function() {
            parseFormData(row, ziduanList);
            // $('#dlg' + configId).dialog('open').dialog('setTitle', '查看');
            $('#modal'+configId,rs.util.getSelectedTab()).modal('show');
            $('h4', $('#modal'+configId)).text('查看');
            $('#fm' + configId).form('clear');
            $('#fm' + configId).form('load', row);
            //获取明细表数据
            if (configData.detail && configData.detail.length > 0) {
              getDetailTableData(row, configData);
            }
            var _row = row;
            $.each(ziduanList, function(index, field) {
              if ('checkbox' === field.xslx) {
                var values = _row[field.name].split(',');
                $.each(values, function(index, value) {
                  value && $('#fm' + configId + ' #' + value).prop('checked', true);
                });
              }
            });

            executeCallback();
          });
        }, 10);
      }

      /**
       * 编辑表单记录
       * @param  {string} configId  表单ID（可选，默认为当前Tab中表单ID）
       * @param  {object} row       记录值（可选，默认为当前Tab中DataGrid选中记录）
       */
      function editMaster(configId, row) {
        configId = configId || rs.global.currentConfigId;
        if (row === undefined) {
          var rows = $('#dg' + configId, rs.util.getSelectedTab()).datagrid('getSelections');
          if (!configId || rows.length === 0) {
            rs.util.messageWarning(rs.constant.MESSAGE_EDIT);
            return;
          } else if (rows.length !== 1) {
            rs.util.messageWarning(rs.constant.MESSAGE_SINGLE);
            return;
          }
          row = rows[0];
        }

        var configData = rs.global['data_' + configId];
        var ziduanList = configData.master.ziduanList;
        configData.viewMode = rs.constant.VIEWMODE_EDIT;
        configData.row = row;
        createDialog(configData).done(function() {
          parseFormData(row, ziduanList);
          $('#modal'+configId,rs.util.getSelectedTab()).modal('show');
          $('h4', $('#modal'+configId)).text('修改');
          $('#fm' + configId).form('clear');
          $('#fm' + configId).form('load', row);
          //获取明细表数据
          if (configData.detail && configData.detail.length > 0) {
            getDetailTableData(row, configData);
          }
          configData.urlMaster = configData.updateUrl;
          configData.methodHttp = configData.updateMethod;
          var _row = row;
          $.each(ziduanList, function(index, field) {
            if ('checkbox' === field.xslx) {
              var values = _row[field.name].split(',');
              $.each(values, function(index, value) {
                value && $('#fm' + configId + ' #' + value).prop('checked', true);
              });
            }
          });

          executeCallback();
        });
      }

      function customerMaster(configId, row) {
        configId = configId || rs.global.currentConfigId;
        if (row === undefined) {
          var rows = $('#dg' + configId, rs.util.getSelectedTab()).datagrid('getSelections');
          if (!configId || rows.length === 0) {
            rs.util.messageWarning(rs.constant.MESSAGE_EDIT);
            return;
          } else if (rows.length !== 1) {
            rs.util.messageWarning(rs.constant.MESSAGE_SINGLE);
            return;
          }
          row = rows[0];
        }

        var configData = rs.global['data_' + configId];
        var ziduanList = configData.master.ziduanList;
        configData.viewMode = rs.constant.VIEWMODE_EDIT;
        configData.row = row;
        createCustomerDialog(configData).done(function() {
          parseFormData(row, ziduanList);
          $('#dlg' + configId).dialog('open').dialog('setTitle', '自定义行为');
          $('#fm' + configId).form('clear');
          $('#fm' + configId).form('load', row);
          //获取明细表数据
          if (configData.detail && configData.detail.length > 0) {
            getDetailTableData(row, configData);
          }
          configData.urlMaster = configData.updateUrl;
          configData.methodHttp = configData.updateMethod;
          var _row = row;
          $.each(ziduanList, function(index, field) {
            if ('checkbox' === field.xslx) {
              var values = _row[field.name].split(',');
              $.each(values, function(index, value) {
                value && $('#fm' + configId + ' #' + value).prop('checked', true);
              });
            }
          });

          executeCallback();
        });
      }

      function createDialog(configData) {
        var configMaster = configData.master;
        var configId = configMaster.id;
        $('#fm' + configId, rs.util.getSelectedTab()).empty();
        var count = createMainForm(configData);

        if(count>10){
          $('.form-group', $('#fm'+configId)).addClass('col-md-6');
          $('.modal-dialog').css('width','700px');
        }

        if (configData.detail && configData.detail.length > 0) {
          $('.form-group', $('#fm'+configId)).addClass('col-md-6');
          $('.modal-dialog').css('width','700px');

          $('#tab'+configId).empty();
          $('<div id="divtabs' + configId + '" style="width:680px;height:220px;"></div>')
            .appendTo($('#tab' + configId));
          createDetailForm(configData);
        }

        var dialogButtons = getDialogButton(configData);
        rs.util.createButtons($('#dlgButtons'+configId), dialogButtons, true);

        return executeJsForm(configData);
      }

      function getDialogButton(configData) {
        var dialogButtons = [];
        var configId = configData.master.id;
        if (configData.viewMode === rs.constant.VIEWMODE_VIEW) {
          dialogButtons = [{
            iconCls: 'icon-cancel',
            text: '关闭',
            handler: function() {
              $('#modal'+configId,rs.util.getSelectedTab()).modal('hide');
            }
          }];
        } else {
          dialogButtons = [{
            iconCls: 'icon-ok',
            text: '保存',
            handler: function() {
              saveMaster(this, configData);
            }
          }, {
            iconCls: 'icon-cancel',
            text: '取消',
            handler: function() {
              $('#modal'+configId,rs.util.getSelectedTab()).modal('hide');
            }
          }];
        }
        return dialogButtons;
      }

      function createCustomerDialog(configData) {
        var configMaster = configData.master;
        var configId = configMaster.id;
        $('<div id="dlg' + configId + '" class="rs-dialog"></div>')
          .append(
            $('<div id="divform' + configId + '"></div>')
            .append($('<form id="fm' + configId + '" class="rs-form" method="post"></form>'))
          )
          // .append($('<div id="divtabs' + configId + '" style="width:100%;height:220px;"></div>'))
          .appendTo($('body'));
        createMainForm(configData);

        if (configData.detail && configData.detail.length > 0) {
          $('<div id="divtabs' + configId + '" style="width:100%;height:220px;"></div>').appendTo($('#dlg' + configId));
          createDetailForm(configData);
        }

        var buttons = configData.customeButtons;
        if (buttons.length <= 0) {
          if (configData.viewMode === rs.constant.VIEWMODE_VIEW) {
            buttons = [{
              iconCls: 'icon-cancel',
              text: '关闭',
              handler: function() {
                $('#dlg' + configId).dialog('destroy');
              }
            }];
          } else {
            buttons = [{
              iconCls: 'icon-ok',
              text: '保存',
              handler: function() {
                saveMaster(this, configData);
              }
            }, {
              iconCls: 'icon-cancel',
              text: '取消',
              handler: function() {
                $('#dlg' + configId).dialog('destroy');
              }
            }];
          }
        }

        $('#dlg' + configId).dialog({
          modal: true,
          closed: true,
          resizable: true,
          width: 800,
          height: 600,
          buttons: buttons,
          onClose: function() {
            $(this).dialog('destroy');
          }
        });
        return executeJsForm(configData);
      }

      function getDetailTableData(row, configData) {
        var configId = configData.master.id;
        var configDetail = configData.detail;
        var tableRelations = JSON.parse(configData.master.zb);
        var url;
        var id;
        $('#divtabs' + configId).tabs('select', 0);
        $.each(configDetail, function(index, item) {
          var bm = item.bm;
          var bid = item.id;
          if(configData.queryUrl.indexOf('/admin/moduleTest')!=-1){
            id = item.id;
            url = configData.queryUrl;
          }else{
            id = row.id;
            url = configData.queryUrl + '/' + bm; 
          }
          // var bm = item.bm;
          var param = {
            id: id
          };

          $.each(tableRelations, function(index, tableRelation) {
            if (tableRelation.tablename == bm) {
              param[tableRelation.colName] = row[tableRelation.mainColName];
            }
          });

          var detailDataId = configId + '_' + bid;
          var readonly = (configData.viewMode === rs.constant.VIEWMODE_VIEW);
          loadDetailData(url, detailDataId, param, item.gxlx, item.ziduanList, readonly);
        });
      }

      function saveMaster(button, configData) {
        var configMaster = configData.master;
        var configId = configMaster.id;
        var configDetail = configData.detail;
        var tableName = configMaster.bm;

        if ($('#fm' + configId).form('validate')) {
          $(button).attr('disabled',"true");
          var formData = {};
          formData.id = configId;
          formData.tablename = tableName;
          var $form = $('#fm' + configId);
          formData.master = getFormattedFormData($form, configMaster.ziduanList);
          formData.master.tag = 'A';
          if (configDetail && configDetail.length > 0) {
            formData.detail = getDetailData(configId, configDetail);
          }
          rs.http.ajaxJson({
            url: configData.urlMaster,
            method: configData.methodHttp,
            data: formData,
            success: function(result) {
              if (result.errorMsg) {
                rs.util.messageError(result.errorMsg);
              } else {
                $('#modal'+configId,rs.util.getSelectedTab()).modal('hide');
                queryMaster(configData);
              }
            }
          }).fail(function() {
            var configId = configData.master.id;
            var configDetail = configData.detail;
            $.each(configDetail, function(index, item) {
              var bid = item.id;
              var detailDataId = configId + '_' + bid;
              var readonly = (configData.viewMode === rs.constant.VIEWMODE_VIEW);
              if (readonly !== true) {
                rs.util.datagridBeginEditAll(detailDataId);
              }
            });
          }).always(function() {
            $(button).removeAttr('disabled');
          });
        }
      }

      function getDetailData(configId, configDetail) {
        var detailarray = [];
        $.each(configDetail, function(index, row) {
          var record = {};
          var records = [];
          var oneDetailObj = {};
          if (row.gxlx === undefined || row.gxlx == 1) {
            var $form = $('#fm' + configId + '_' + row.id);
            record = getFormattedFormData($form, row.ziduanList);
            record.tag = 'A';
            records.push(record);
            oneDetailObj.id = row.id;
            oneDetailObj.records = records;
            detailarray.push(oneDetailObj);
          } else {
            rs.util.datagridEndEditAll(configId + '_' + row.id);

            var nowDatas = $('#' + configId + '_' + row.id).datagrid('getData').rows;
            var insertData = $('#' + configId + '_' + row.id).datagrid('getChanges', 'inserted');
            $.each(nowDatas, function(index, data) {
              if (!($.inArray(data, insertData) >= 0)) {
                data.tag = 'U';
                records.push(data);
              }
            });

            $.each(insertData, function(index, item) {
              item.tag = 'A';
              records.push(item);
            });

            var deleteData = $('#' + configId + '_' + row.id).datagrid('getChanges', 'deleted');
            $.each(deleteData, function(index, item) {
              item.tag = 'D';
              records.push(item);
            });

            if (records.length > 0) {
              oneDetailObj.id = row.id;
              oneDetailObj.records = parseDatagridData(records, row.ziduanList);
              detailarray.push(oneDetailObj);
            }
          }
        });
        return detailarray;
      }

      function createMainForm(configData) {
        var configMaster = configData.master;
        var configId = configMaster.id;
        var isShow;
        var isIdOrVersion;
        var rows = configMaster.ziduanList;
        var count = 0;
        if (rows && rows.length > 0) {
          var tableName = configMaster.bm;
          var versionField = getVersionField(tableName, configData);
          var idField = getIdField(tableName, configData);
          $.each(rows, function(index, row) {
            isShow = (configData.visibleArray.length == 0) || ($.inArray(row.name, configData.visibleArray) >= 0);
            isIdOrVersion = (row.name == idField || row.name == versionField);
            if (isShow || isIdOrVersion) {
              var readonly = (configData.viewMode === rs.constant.VIEWMODE_VIEW) || ((configData.optArray.length > 0) && ($.inArray(row.name, configData.optArray) == -1));
              if (row.isshow == 'Y') {
                count = count + 1;
                rs.util.addMainFormDivByZiduan($('#fm' + configId), row, true);
                rs.util.addComponentByZiduan('fm' + row.id, row, configData.datadict, readonly, configData);
              } else if (isIdOrVersion) {
                rs.util.addMainFormDivByZiduan($('#fm' + configId), row, false);
                rs.util.addComponentByZiduan('fm' + row.id, row, configData.datadict, readonly);
              }
            }
          });
        }
        return count;
      }

      function createDetailForm(configData) {
        var configId = configData.master.id;
        var configDetail = configData.detail;
        if (configDetail && configDetail.length > 0) {
          $('#divtabs' + configId).tabs({});
          $('#divtabs' + configId).tabs('close', 0);
          $.each(configDetail, function(index, row) {
            createDatagridByDetailData(row, configData);
          });
        }
      }

      function executeJsForm(configData) {
        return rs.http.ajaxJson({
          url: configData.jsUrl,
          method: configData.jsMethod,
          data: {
            jslx: 'form'
          },
          success: function(result) {
            $.each(result.rows, function(index, row) {
              rs.util.executeScript(row.js);
            });
          }
        });
      }

      function createDatagridByDetailData(row, configData) {
        var configId = configData.master.id;
        var readonly = (configData.viewMode === rs.constant.VIEWMODE_VIEW);
        var content = '<table id="' + configId + '_' + row.id + '"></table>';
        $('#divtabs' + configId).tabs('add', {
          title: row.nr,
          closable: false,
          content: content
        });
        var columns = rs.util.getColumnsByZiduan(row.ziduanList, configData.datadict);
        var versionColumn = getVersionColumn(row.bm, configData);
        columns.push(versionColumn);

        // 显示复选框
        if (row.iscbox === 'Y' && readonly !== true) {
          columns.unshift({
            field: 'ck',
            checkbox: true
          });
        }

        $('#' + configId + '_' + row.id).datagrid({
          idField: 'id',
          striped: true,
          autoRowHeight: true,
          pagination: false,
          rownumbers: true,
          fitColumns: true,
          singleSelect: row.iscbox !== 'Y',
          fit: true,
          columns: [
            columns
          ],
          toolbar: [{
            iconCls: 'icon-add',
            disabled: readonly,
            handler: function() {
              $('#' + configId + '_' + row.id).datagrid('appendRow', {});
              var index = $('#' + configId + '_' + row.id).datagrid('getRows').length - 1;
              $('#' + configId + '_' + row.id).datagrid('beginEdit', index);
            }
          }, {
            iconCls: 'icon-remove',
            disabled: readonly,
            handler: function() {
              var items = $('#' + configId + '_' + row.id).datagrid('getSelections');
              var dataGridItems = $.extend({}, items);
              if (dataGridItems) {
                $.each(dataGridItems, function(index, item) {
                  var dataIndex = $('#' + configId + '_' + row.id).datagrid('getRowIndex', item);
                  $('#' + configId + '_' + row.id).datagrid('deleteRow', dataIndex);
                });
              }
            }
          }]
        });
      }

      function clearDetailTabData(configData) {
        var configId = configData.master.id;
        var configDetail = configData.detail;
        $('#divtabs' + configId).tabs('select', 0);
        $.each(configDetail, function(index, row) {
          if (row.gxlx === undefined || row.gxlx == 1) {
            $('#fm' + configId + '_' + row.id).form('clear');
          } else {
            $('#' + configId + '_' + row.id).datagrid('loadData', []);
          }
        });
      }

      function getVersionColumn(tableName, configData) {
        var versionField = getVersionField(tableName, configData);
        var versionColumn = {
          field: versionField,
          title: "版本号",
          halign: "center",
          width: 100,
          hidden: true
        }
        return versionColumn;
      }

      function getIdColumns(tableName, configData) {
        var idField = getIdField(tableName, configData);
        var idColumn = {
          field: idField,
          title: "ID",
          halign: "center",
          width: 100,
          hidden: true
        }
        return idColumn;
      }

      function getIdField(tableName, configData) {
        var idField;
        if(configData.queryUrl.indexOf('/admin/moduleTest')!=-1){
          var zbindex = tableName.lastIndexOf('_');
          if (zbindex >= 0) {
            idField = tableName.substring(zbindex + 1).toUpperCase() + "_ID";
          } else {
            idField = tableName.toUpperCase() + "_ID";
          }
        }else{
          idField = "id";
        }
        return idField;
      }

      function getVersionField(tableName, configData) {
        var versionField;
        if(configData.queryUrl.indexOf('/admin/moduleTest')!=-1){
          var zbindex = tableName.lastIndexOf('_');
          if (zbindex >= 0) {
            versionField = tableName.substring(zbindex + 1).toUpperCase() + "_VERSION";
          } else {
            versionField = tableName.toUpperCase() + "_VERSION";
          }
        }else{
          versionField = 'version';
        }
        return versionField;
      }

      function formatDatagridData(records, fieldList) {
        $.each(fieldList, function(index, field) {
          if (field.xslx === 'date') {
            $.each(records, function(index, record) {
              var name = field.name;
              var value = record[name];
              if (value !== undefined) {
                record[name] = rs.util.formatDate(value);
              }
            });
          } else if (field.xslx === 'datetime') {
            $.each(records, function(index, record) {
              var name = field.name;
              var value = record[name];
              if (value !== undefined) {
                record[name] = rs.util.formatDateTime(value);
              }
            });
          }
        });
        return records;
      }

      function parseDatagridData(records, fieldList) {
        $.each(fieldList, function(index, field) {
          if (field.xslx === 'date' || field.xslx === 'datetime') {
            $.each(records, function(index, record) {
              var name = field.name;
              var value = record[name];
              if (value !== undefined) {
                record[name] = Date.parse(value);
              }
            });
          }
        });
        return records;
      }

      // 执行callback
      function executeCallback() {
        try {
          if (window.callback && $.isFunction(callback)) {
            callback();
          }
        } catch (e) {
          console.warn('::execute callback error::', e);
        }
      }

      function parseFormData(data, fieldList) {
        $.each(fieldList, function(index, field) {
          var fieldName = field.name;
          var fieldValue = data[fieldName];
          if ('date' === field.xslx) {
            // 时间戳 > 日期字符串
            data[fieldName] = rs.util.formatDate(fieldValue);
          } else if ('datetime' === field.xslx) {
            // 时间戳 > 日期字符串
            data[fieldName] = rs.util.formatDateTime(fieldValue);
          }
        });
      }

      function loadDetailData(url, id, param, gxlx, fieldList, readonly) {
        rs.http.postJson({
          url: url,
          data: param,
          success: function(result) {
            // if (gxlx == 0) {
            rs.util.datagridSetData(id, formatDatagridData(result.rows, fieldList));
            rs.util.datagridUnselectAll(id);
            if (readonly !== true) {
              rs.util.datagridBeginEditAll(id);
            }
            // } else {
            //   $('#fm' + id).form('load', result.rows[0]);
            // }
          }
        });
      }

      function getFormattedFormData($form, ziduanList) {
        var formData = {};
        var array = $form.serializeArray();
        $.each(array, function(index, item) {
          var fieldName = item.name;
          var fieldValue = item.value;
          var xslx = getFieldXslx(fieldName, ziduanList);
          if ('date' === xslx || 'datetime' === xslx) {
            formData[fieldName] = Date.parse(fieldValue);
          } else if ('checkbox' === xslx) {
            if (!formData[fieldName]) {
              formData[fieldName] = fieldValue;
            } else {
              formData[fieldName] += ',' + fieldValue;
            }
          } else if ('combobox' === xslx) {
            // multiple combobox -> value string
            formData[fieldName] = rs.util.formComboboxGetValuesString($form, fieldName);
          } else {
            var lx = getFieldLx(fieldName, ziduanList);
            if (lx === 'int') {
              formData[fieldName] = (fieldValue !== '') ? parseInt(fieldValue) : null;
            } else {
              formData[fieldName] = fieldValue;
            }
          }
        });
        return formData;
      }

      // 获取表单字段的显示类型
      function getFieldXslx(fieldName, ziduanList) {
        var len = ziduanList.length;
        for (var i = 0; i < len; ++i) {
          var field = ziduanList[i];
          if (field.name === fieldName) {
            return field.xslx;
          }
        };
      }

      // 获取表单字段的字段类型
      function getFieldLx(fieldName, ziduanList) {
        var len = ziduanList.length;
        for (var i = 0; i < len; ++i) {
          var field = ziduanList[i];
          if (field.name === fieldName) {
            return field.lx;
          }
        };
      }

      /**
       * 删除表单选中记录
       * @param  {string} configId  表单ID（可选，默认为当前Tab中表单ID）
       */
      function deleteMaster(configData) {
        var configMaster = configData.master;
        var configId = configMaster.id;
        configId = configId || rs.global.currentConfigId;
        var rows = $('#dg' + configId, rs.util.getSelectedTab()).datagrid('getSelections');
        if (!configId || !rows || rows.length === 0) {
          rs.util.messageWarning(rs.constant.MESSAGE_DELETE);
          return;
        }

        var configData = rs.global['data_' + configId];
        var ziduanList = configData.master.ziduanList;
        var tableName = configData.master.bm;
        var fieldId = getIdField(tableName, configData);
        var idList = [];
        for (var i = 0; i < rows.length; ++i) {
          idList.push(rows[i][fieldId]);
        }
        rs.util.messageConfirm('确认删除选中记录？', '提醒', function(r) {
          configData.viewMode = rs.constant.VIEWMODE_DELETE;
          rs.http.ajaxJson({
            url: configData.deleteUrl,
            method: configData.deleteMethod,
            data: {
              id: configId,
              tableName: tableName,
              rows: idList
            },
            success: function(result) {
              // $('#dg' + configId, rs.util.getSelectedTab()).datagrid('reload');
              queryMaster(configData);
            }
          });
        });
      }

      function downloadExcelTemplet(configId) {
        var url = window.rsContextPath + '/poi/export/templet/' + configId + '/' + rs.global.currentMenuId;
        window.location.href = url;
      }

      function downloadExcelData(configId) {
        var url = window.rsContextPath + '/poi/export/data/' + configId + '/' + rs.global.currentMenuId;
        window.location.href = url;
      }

      function uploadExcelData(configData) {
        var configMaster = configData.master;
        var configId = configMaster.id;
        $('#uploadModal'+configId,rs.util.getSelectedTab()).modal('show');
      }

      function saveUploadData(configData) {
        var configMaster = configData.master;
        var configId = configMaster.id;
        var tableName = configMaster.bm;
        if ($('#fmUpload' + configId).form('validate')) {
          var configMaster = configData.master;
          var configId = configMaster.id;
          var tableName = configMaster.bm;
          var formData = new FormData($('#fmUpload' + configId)[0]);
          formData.append('tableId', configId);
          formData.append('tableName', tableName);
          $.ajax({
            url: window.rsContextPath + '/poi/import/importfile',
            method: 'POST',
            data: formData,
            processData: false,
            contentType: false,
            cache: false,
            success: function(result) {
              $('#uploadModal'+configId,rs.util.getSelectedTab()).modal('hide');
              $('#dg' + configId).datagrid('reload');
            },
            error: function(jqXHR) {
              if (jqXHR.responseJSON.verrors && jqXHR.responseJSON.verrors.length > 0) {
                var errorMsg = "";
                $.each(jqXHR.responseJSON.verrors, function(index, verror) {
                  errorMsg = errorMsg + verror.errorMsg + '\n';
                })
              }
              rs.util.messageError(errorMsg || jqXHR.responseJSON.error || '有无效参数，请刷新数据后检查重试');
            }
          });
        }
      }

      //附件管理弹框
      function uploadAttachement(configData) {
        var configMaster = configData.master;
        var configId = configMaster.id;
        configId = configId || rs.global.currentConfigId;
        var rows = $('#dg' + configId, rs.util.getSelectedTab()).datagrid('getSelections');
        if (!configId || !rows || rows.length === 0) {
          rs.util.messageWarning('请先选择需要操作的记录');
          return;
        }

        getAttachementFile(rows[0], configId).done(function(data){
          $('#listAttachement'+configId,rs.util.getSelectedTab()).modal('show');
        })
      }

      function getRecordId(rows){
         for(var key in rows){
            if(rows.hasOwnProperty(key) && key.indexOf('ID') !== -1){
              return rows[key];
            }
         }
      }
      function getAttachementFile(rows,configId){
        var id = getRecordId(rows);
        return rs.http.ajaxJson({
          url:'/fs/file/list/',
          data:{"path":rs.global.currentMenuId + '/' + id},
          method:'POST',
          success: function(data){
            $('#attachementList'+configId,rs.util.getSelectedTab()).datagrid('loadData',data.rows);
          }  
        });
      }

      function openDlgAttachementUpload(index) {
        var configId = rs.global.currentConfigId;
        var configData = rs.global['data_' + configId];
        configData.viewMode = rs.constant.VIEWMODE_EDIT
        $('#attachementList' + configId).datagrid('unselectAll');
        $('#attachementList' + configId).datagrid('selectRow', index);
        var row = $('#attachementList' + configId).datagrid('getSelected');
        rs.global.fsFileId = row.id;
        $('#updateAttachement'+configId,rs.util.getSelectedTab()).modal('show');
        $('#fmUploadAttachement' + configId).form('load', row);
      }

      //保存上传的附件
      function saveAddAttachement(configData,rows,button){
        var configMaster = configData.master;
        var configId = configMaster.id;
        var menuId = rs.global.currentMenuId;
        var fsUrl;
        var sendUrl;
        var msg;
        var $fm;
        var $modal;
        var $files;
        if(configData.viewMode === rs.constant.VIEWMODE_ADD){
          sendUrl = window.rsContextPath + '/fs/file/addFile';
          msg = '附件上传成功';
          $fm = $('#fmAddAttachement'+configId);
          $modal = $('#optAttachement'+configId);
          $files = $fm.find('[name="addfile"]')[0];
        }else{
          sendUrl = window.rsContextPath + '/fs/file/updateFile';
          msg = '附件更新成功';
          $fm = $('#fmUploadAttachement'+configId);
          $modal = $('#updateAttachement'+configId);
          $files = $fm.find('[name="updatefile"]')[0];
        }
        if (rows === undefined)
          rows = $('#dg' + configId, rs.util.getSelectedTab()).datagrid('getSelections');
        if($fm.form('validate')){
          // var files = $fm.find('[name="addfile"]')[0];
          if(fileSizeIsOverMax($files)){
            rs.util.messageWarning('文件大小不能超过' + rs.constant.MAX_FILE_SIZE/1000 + 'M');
            return;
          }
          $(button).attr("disabled","true");
          var formData = new FormData($fm[0]);
          var id = getRecordId(rows[0]);
          formData.append('path', menuId + '/' + id);
          formData.append('folderid','');
          if(configData.viewMode !== rs.constant.VIEWMODE_ADD){
            formData.append('fileid',rs.global.fsFileId);
          }
          
          $.ajax({
            url: sendUrl,
            method: 'POST',
            data: formData,
            processData: false,
            contentType: false,
            cache: false,
            success: function(result) {
              $modal.modal('hide');
              getAttachementFile(rows[0], configId);
              rs.util.messageInfo(msg);
              $('#dgFSFolderList').treegrid('reload');
            },
            error: function(jqXHR) {
              if (jqXHR.responseJSON.verrors && jqXHR.responseJSON.verrors.length > 0) {
                var errorMsg = "";
                $.each(jqXHR.responseJSON.verrors, function(index, verror) {
                  errorMsg = errorMsg + verror.errorMsg + '\n';
                })
              }
              rs.util.messageError(errorMsg || jqXHR.responseJSON.error || '有无效参数，请刷新数据后检查重试');
            }
          })
          .always(function(){
            $(button).removeAttr('disabled');
          });
        }else{
          rs.util.messageWarning('请先选择需要上传的附件');
        }
      }
      //判断上传的文件大小是否超出限制
      function fileSizeIsOverMax(target) {
        var fileSize = 0;
        var isIE = /msid/i.test(navigator.userAgent) && !window.opera;
        if (isIE && !target.files) {
          var filePath = target.value;
          var fileSystem = new ActiveXObject("Scripting.FileSystemObject");
          var file = fileSystem.GetFile(filePath);
          fileSize = file.size;
        } else {
          fileSize = target.files[0].size / 1024;
        }
        if (fileSize > rs.constant.MAX_FILE_SIZE) {
          return true;
        } else {
          return false;
        }
      }

      function deleteFile(index) {
        var configId = rs.global.currentConfigId;
        $('#attachementList' + configId).datagrid('unselectAll');
        $('#attachementList' + configId).datagrid('selectRow', index);
        var row = $('#attachementList' + configId).datagrid('getSelected');
        var rows = $('#dg' + configId, rs.util.getSelectedTab()).datagrid('getSelections');
        if (row === null) {
          rs.util.messageWarning(rs.constant.MESSAGE_DELETE);
        } else {
          var fileId = [row.id];
          rs.util.messageConfirm('确认删除', '提醒', function() {
            var d2 = rs.http.ajaxJson({
              url: '/fs/file',
              method: 'DELETE',
              data: {
                ids: fileId
              }
            });
            $.when(d2).done(function() {
              getAttachementFile(rows[0], configId);
              rs.util.messageInfo('删除成功');
              $('#dgFSFolderList').treegrid('reload');
            }).fail(function() {
              rs.util.messageError('删除失败，请刷新后重试');
            });
          });
        }
      }

      function addToolbarButtons($fid, configData){
        if(configData.userNormalButtons&&configData.userNormalButtons.length>0){
          var $normalButtonsFid = $('<div class="btn-group"></div>').appendTo($fid);
          $.each(configData.userNormalButtons, function(index, button){
            if(button.id=="workFlow"){
              createWorkFlowButton($fid, configData);
            }else{
              var $normalButtons = $('<button type="button" class="btn btn-primary" width="50px">'
                                      +'<span class="'+button.iconCls+'" aria-hidden="true"> '+button.text+'</span>'
                                    +'</button>');
              $normalButtons.on('click', button.handler).appendTo($normalButtonsFid);
            }
          })
        }

        if(configData.userCustomerButtons&&configData.userCustomerButtons.length>0){
          var $customerButtonsFid = $('<div class="btn-group"></div>').appendTo($fid);
          $.each(configData.userCustomerButtons, function(index, button){
            var $customerButtons = $('<button type="button" class="btn btn-primary" width="50px">'
                                    +'<span class="'+button.iconCls+'" aria-hidden="true"> '+button.text+'</span>'
                                  +'</button>');
            $customerButtons.on('click', button.handler).appendTo($customerButtonsFid);
          })
        }
      }

      function createWorkFlowButton($fid, configData){
        var configMaster = configData.master;
        var configId = configMaster.id;
        $('<div class="btn-group" role="group">'
            +'<button type="button" class="btn btn-primary" onclick="rs.dev.autolist.startWf()">启动工作流</button>'
            +'<button type="button" class="btn btn-primary dropdown-toggle" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false" onclick="rs.dev.autolist.getWfOptions()">'
              +'<span class="fa fa-refresh"></span>'
              +'工作流'
              +'<span class="caret"></span>'
            +'</button>'
            +'<ul class="dropdown-menu" id="wfMenu'+configId+'">'
            +'</ul>'
          +'</div>')
        .appendTo($fid);
      }

      function startWf(){
        var configId = rs.global.currentConfigId;
        var rows = $('#dg'+configId).datagrid('getSelections');
        if (!configId || rows.length === 0) {
          rs.util.messageWarning(rs.constant.MESSAGE_VIEW);
          return;
        } else if (rows.length !== 1) {
          rs.util.messageWarning(rs.constant.MESSAGE_SINGLE);
          return;
        }

        var url = '/wfapi/start';
        var refMkid = 'erpTest';
        var userId= window.localStorage.userId;
        var reqData = {
          "refMkid":refMkid,
          "userId":userId
        }
        rs.http.ajaxJson({
          url:url,
          data: reqData,
          method: 'POST',
          success:function(ret){
            console.log(ret);
          }
        },'wf');
      }

      function getWfOptions(){
        var configId = rs.global.currentConfigId;
        var rows = $('#dg'+configId).datagrid('getSelections');
        if (!configId || rows.length === 0) {
          rs.util.messageWarning(rs.constant.MESSAGE_VIEW);
          return;
        } else if (rows.length !== 1) {
          rs.util.messageWarning(rs.constant.MESSAGE_SINGLE);
          return;
        }

        var refMkid = 'erpTest';
        var wfInstNum = 8;
        var userId = window.localStorage.userId;
        
        $('#wfMenu'+configId).empty();
        if(refMkid=="" || wfInstNum==""){
          alert("请在输入框中指定refMkid&wfInstNum");
          return;
        }
        var reqData = {
          "refMkid":refMkid,
          "wfInstNum":parseInt(wfInstNum),
          "userId":userId
        };
        var url = "/wfapi/options";
        rs.http.ajaxJson({
          url:url,
          data: reqData,
          method: 'POST',
          success:function(ret){
            if(ret&&ret.records&&ret.records.length>0){
              $.each(ret.records, function(index, record){
                addWfMenu(record, configId);
              });
            }
          }
        },'wf')
      }

      function addWfMenu(record, configId){
        var spanClass = "fa fa-list";
        if(record.value === "C"){
          spanClass="fa fa-arrow-down";
        }else if(record.value === "RJ"){
          spanClass="fa fa-arrow-up";
        }else if(record.value === "RC"){
          spanClass="fa fa-arrow-left";
        }else if(record.value === "LMD"){
          spanClass="fa fa-arrow-right";
        }else if(record.value === "F"){
          spanClass="fa fa-arrows";
        }else if(record.value === "TK"){
          spanClass="fa fa-arrows-alt";
        }

        if(record.disflag){
          $('<li class="disabled"><a href="javascript:void(0)"'
            +'<span class="'+spanClass+'"></span>'+record.descp
            +'</a></li>').appendTo($('#wfMenu'+configId));
        }else{
          $('<li><a href="javascript:void(0)"'
            +'onclick="rs.dev.autolist.optTask(\''+record.value+'\')">'
            +'<span class="'+spanClass+'"></span>'+record.descp
            +'</a></li>').appendTo($('#wfMenu'+configId));
        }
      }

      function optTask(optCode){
        var refMkid = 'erpTest';
        var userId = window.localStorage.userId;
        var wfInstNum = 8;
        if(optCode=="" || refMkid=="" || wfInstNum==""){
          alert("refMkid,wfInstNum,optCode is required");
          return;
        }
        var url = "http://localhost:9090/wf"+"/task/loadprocess.do?instNum="+ wfInstNum + "&refMkid="+refMkid+ "&optCode="+optCode;
        window.open(url)
      }

      return {
        viewMaster: viewMaster,
        dialog: dialog,
        getAutoListData: getAutoListData,
        // autoCreatePage: autoCreatePage,
        openDlg: openDlgAttachementUpload,
        deleteFile: deleteFile,
        saveUploadData: saveUploadData,
        saveAddAttachement:saveAddAttachement,
        startWf: startWf,
        getWfOptions: getWfOptions,
        optTask: optTask,
        getDataDictBySql: getDataDictBySql,
        getVisibleAndOptArray: getVisibleAndOptArray,
        getFormJsList: getFormJsList,
        getCustomerButtons: getCustomerButtons,
        getToolbarButtons: getToolbarButtons,
        createTreeGrid: createTreeGrid,
        createDataGrid: createDataGrid,
        addToolbarButtons: addToolbarButtons,
        createSearchForm: createSearchForm,
        refreshForm: refreshForm,
        getFormattedFormData: getFormattedFormData
      };
    })()
  };

  /**
   * 全局变量
   */
  rs.global = rs.global || {
    adminTabsId: 'contentTabs',
    navTreeId: 'mainFormTree',
    currentMenuId: null,
    tabIndexConfigIdAdmin: [],
    tabIndexConfigIdUser: [],
    datagrid: {},
    currentUser: window.localStorage.getItem('username')
  };

  /**
   * 对外API
   */
  rs.api = rs.api || {
    rename: function(buttonId, newName) {
      var r = $('#' + buttonId, rs.util.getSelectedTab()).linkbutton({
        text: newName
      });
      if (!r.length || r.length === 0) {
        console.warn('Failed to rename button [' + buttonId + '] with new name [' + newName + ']');
      }
    },

    view: rs.dev.autolist.viewMaster,

    dialog: rs.dev.autolist.dialog,

    user: rs.global.currentUser
  };

  return rs;
})(window.rs || {}, jQuery, window);
