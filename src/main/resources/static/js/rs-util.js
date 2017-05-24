rs.util || (function(rs, window, $) {
  'use strict';

  rs.util = {};

  rs.util.test = function() {
    console.log('for test');
  };

  rs.util.clone = function(obj) {
    return JSON.parse(JSON.stringify(obj));
  };
  //导航树数据更新
  rs.util.navTreeRefresh = function(navTreeId) {
    navTreeId = navTreeId || 'navTree';
    rs.http.ajaxJson({
      url: '/admin/menu/sidelist',
      success: function(result) {
        if (result.errorMsg) {
          $.messager.show({
            title: 'Error',
            msg: result.errorMsg
          });
        } else {
          var treeData = rs.util.transFormTreeData('id', 'pid', result.rows);
          rs.util.formateTreeDataSuitTree(treeData);
          rs.util.sortTreeDataArray(treeData);
          $('#' + navTreeId).tree({
            data: treeData
          });
        }
      }
    });
  };

  rs.util.formateTreeDataSuitTree = function(treeData, textField) {
    if (treeData && treeData.length > 0) {
      $.each(treeData, function(index, item) {
        if (textField === undefined) {
          item.text = item.name;
        } else {
          item.text = item[textField];
        }
        // if(item.children){
        //   item.iconCls = 'rsutiltree glyphicon glyphicon-folder-open';
        // }else{
        //   item.iconCls = 'rsutiltree glyphicon glyphicon-plus';
        // }
        if (item.children && item.children.length > 0) {
          rs.util.formateTreeDataSuitTree(item.children, textField);
        }
      });
    }
  }

  rs.util.formateTreeDataNameToText = function(treeData, idField, nameField) {
      if (treeData && treeData.length > 0) {
        $.each(treeData, function(index, item) {
          item.text = item[nameField];
          item.id = item[idField];
          if (item.children && item.children.length > 0) {
            rs.util.formateTreeDataNameToText(item.children, idField, nameField);
          }
        });
      }
    }
    //树形数据数组排序
  rs.util.sortTreeDataArray = function(treeData) {
    if (treeData && treeData.length > 0) {
      treeData.sort(function(a, b) {
        return a.sequence - b.sequence;
      });

      $.each(treeData, function(index, item) {
        if (item.children && item.children.length > 0) {
          rs.util.sortTreeDataArray(item.children);
        }
      });
    }
  }

  rs.util.navTabsAddInAdminPage = function(options) {
    var tab = $('#' + rs.global.adminTabsId).tabs('getTab', options.title);
    if (!tab) {
      $('#' + rs.global.adminTabsId).tabs('add', {
        title: options.title,
        href: window.rsContextPath + options.href,
        closable: (undefined === options.closable) ? true : options.closable
      });
    } else {
      $('#' + rs.global.adminTabsId).tabs('select', options.title);
    }
  };

  rs.util.navTabsAddInUserPage = function(options) {
    var tab = $('#' + gUserTabsId).tabs('getTab', options.title);
    if (!tab) {
      $('#' + gUserTabsId).tabs('add', {
        title: options.title,
        href: window.rsContextPath + options.href,
        closable: (undefined === options.closable) ? true : options.closable
      });
    } else {
      $('#' + gUserTabsId).tabs('select', options.title);
    }
  };

  rs.util.tabsSelectByIndex = function(tabsId, index) {
    $('#' + tabsId).tabs('select', index);
  };

  rs.util.tabsCloseAllButFirstTab = function(tabsId) {
    var count = $('#' + tabsId).tabs('tabs').length;
    for (var i = count - 1; i > 0; i--) {
      $('#' + tabsId).tabs('close', i);
    }
  };

  rs.util.getSelectedTab = function() {
    var selectedTab;
    if (window.rs.global.adminTabsId !== undefined) {
      selectedTab = $('#' + rs.global.adminTabsId).tabs('getSelected');
    } else if (window.gUserTabsId !== undefined) {
      selectedTab = $('#' + gUserTabsId).tabs('getSelected');
    }
    return selectedTab;
  };

  rs.util.formGetData = function(formId) {
    var $form = formId;
    if (typeof formId === 'string') {
      $form = $('#' + formId);
    }
    var formData = {};
    var array = $form.serializeArray();
    $.each(array, function(index, item) {
      if (!formData[item.name]) {
        formData[item.name] = item.value;
      } else {
        formData[item.name] += ',' + item.value;
      }
    });
    return formData;
  };

  rs.util.formSetData = function(formId, data) {
    $('#' + formId).form('load', data);
  };

  rs.util.formComboboxGetValuesString = function($form, fieldName) {
    var selector = '.easyui-combobox[comboname="' + fieldName + '"]';
    return $form.find(selector).combobox('getValues').join(',');
  };

  rs.util.formComboboxClear = function(formId, fieldName) {
    var selector = '#' + formId + ' .easyui-combobox[comboname="' + fieldName + '"]';
    return $(selector).combobox('clear');
  };

  rs.util.datagridGetData = function(datagridId) {
    var data = $('#' + datagridId).datagrid('getData');
    if (data) {
      return data.rows || [];
    }
  };

  rs.util.datagridSetData = function(datagridId, data) {
    $('#' + datagridId).datagrid('loadData', data || []);
  };

  rs.util.datagridReload = function(datagridId, param) {
    param = (undefined !== param) ? param : {};
    $('#' + datagridId).datagrid('reload', param);
  };

  rs.util.datagridGetSelected = function(datagridId) {
    return $('#' + datagridId).datagrid('getSelected');
  };

  rs.util.datagridUnselectAll = function(datagridId) {
    return $('#' + datagridId).datagrid('unselectAll');
  };

  rs.util.datagridGetRowIndex = function(datagridId, id) {
    return $('#' + datagridId).datagrid('getRowIndex', id);
  };

  rs.util.datagridGetColumnMaxValue = function(datagridId, field) {
    var data = $('#' + datagridId).datagrid('getData');
    if (data && data.rows && data.rows.length > 0) {
      var rows = data.rows;
      var maxValue = rows[0][field];
      $.each(rows, function(index, row) {
        if (row[field] > maxValue) maxValue = row[field];
      });
      return maxValue;
    }
    return 0;
  };

  rs.util.datagridGetColumnValueList = function(datagridId, field) {
    var list = [];
    var data = $('#' + datagridId).datagrid('getData');
    if (data && data.rows && data.rows.length > 0) {
      var rows = data.rows;
      $.each(rows, function(index, row) {
        list.push(row[field]);
      });
    }
    return list;
  };

  rs.util.datagridDeleteSelections = function(datagridId) {
    var selections = $('#' + datagridId).datagrid('getSelections');
    $.each(selections, function(index, row) {
      $('#' + datagridId).datagrid('deleteRow', $('#' + datagridId).datagrid('getRowIndex', row));
    });
  };

  rs.util.datagridBeginEditAll = function(datagridId) {
    try {
      var data = $('#' + datagridId).datagrid('getData');
      var len = data.rows.length;
      for (var i = 0; i < len; i++) {
        $('#' + datagridId).datagrid('beginEdit', i);
      }
    } catch (exception) {
      console.warn('datagridBeginEditAll(): ', exception);
    }
  };

  rs.util.datagridEndEditByCondition = function(datagridId, fieldName, fieldValue) {
    try {
      var data = $('#' + datagridId).datagrid('getData');
      var len = data.rows.length;
      for (var i = 0; i < len; ++i) {
        var row = data.rows[i];
        if (fieldName && fieldValue && row[fieldName] === fieldValue) {
          $('#' + datagridId).datagrid('endEdit', i);
          return;
        }
      }
    } catch (exception) {
      console.warn('datagridEndEditByCondition(): ', exception);
    }
  };

  rs.util.datagridEndEditAll = function(datagridId) {
    try {
      var data = $('#' + datagridId).datagrid('getData');
      var len = data.rows.length;
      for (var i = 0; i < len; ++i) {
        $('#' + datagridId).datagrid('endEdit', i);
      }
    } catch (exception) {
      console.warn('datagridEndEditByCondition(): ', exception);
    }
  };

  rs.util.datagridAcceptChanges = function(datagridId) {
    try {
      $('#' + datagridId).datagrid('acceptChanges');
    } catch (exception) {
      console.warn('acceptChangesDatagrid(): ', exception);
    }
  };

  rs.util.datagridAutoSelectFirstRow = function(datagridId) {
    $('#' + datagridId).datagrid({
      onLoadSuccess: function() {
        $('#' + datagridId).datagrid('selectRow', 0);
      }
    });
  };

  // rs.util.messageInfo = function(msg, title) {
  //   $.messager.show({
  //     title: title || '提示信息',
  //     msg: msg || '操作成功',
  //     timeout: 5000,
  //     showType: 'slide'
  //   });
  // };

  //信息展示
  rs.util.messageInfo = function(msg, callback) {
    swal({
      title: msg,
      timer: 2000,
      text: '（2秒后自动关闭）',
      type: 'success',
      animation: 'slide',
      confirmButtonText: '确定'
    }, callback);
  };

  rs.util.messageWarning = function(msg, callback) {
    swal({
      title: msg,
      timer: 2000,
      text: '（2秒后自动关闭）',
      type: 'warning',
      animation: 'slide',
      confirmButtonText: '确定'
    }, callback);
  };

  // rs.util.messageError = function(msg, title, fn) {
  //   $.messager.alert({
  //     title: ($.isPlainObject(msg) ? msg.title : title) || '错误',
  //     msg: ($.isPlainObject(msg) ? msg.msg : msg) || '操作失败',
  //     icon: 'error',
  //     fn: ($.isPlainObject(msg) ? msg.fn : fn)
  //   });
  // };

  rs.util.messageError = function(msg, callback) {
    swal({
      title: msg,
      type: "error",
      confirmButtonText: '确定'
    }, callback);
  };

  // rs.util.messageConfirm = function(msg, title, callback) {
  //   $.messager.confirm({
  //     ok: '确认',
  //     cancel: '取消',
  //     title: title || '确认',
  //     msg: msg || '确认此操作？',
  //     fn: function(r) {
  //       if (r) {
  //         callback();
  //       }
  //     }
  //   });
  // };

  rs.util.messageConfirm = function(msg, title, callback) {
    swal({
        title: msg || '确认此操作？',
        // text: "You will not be able to recover this imaginary file!",
        type: "warning",
        showCancelButton: true,
        confirmButtonClass: "btn-danger",
        confirmButtonText: "确认",
        cancelButtonText: "取消",
        closeOnConfirm: false,
        closeOnCancel: true
      },
      function(isConfirm) {
        if (isConfirm) {
          swal.close();
          setTimeout(function() { callback() }, 100);
          // callback()
        }
      });
  };

  rs.util.formatterBoolean = function(value) {
    return ('Y' === value) ? '是' : '否';
  };

  rs.util.formatterBooleanToYN = function(value) {
    return (true === value) ? '是' : '否';
  };

  rs.util.formatDateTime = function(value) {
    if (value !== undefined)
      return moment(value).format('YYYY-MM-DD HH:mm:ss');
  };

  rs.util.formatDate = function(value) {
    return moment(value).format('YYYY-MM-DD');
  };
  //获取默认tabID
  rs.util.getDefaultTableId = function(tableName) {
    var index = tableName.lastIndexOf('_');
    if (index >= 0) {
      return tableName.substring(index + 1) + '_id';
    }
    return 'id';
  };

  rs.util.getTableId = function(tableName) {
    var index = tableName.lastIndexOf('_');
    if (index >= 0) {
      return tableName.substring(index + 1) + '_id';
    }
    return tableName + '_id';
  };

  // 用于生成Datagrid中的列
  rs.util.getColumnsByZiduan = function(rows, datadict) {
    var columns = [], //列数据集合
      ziduanColumn;
    $.each(rows, function(index, row) {
      // if (row.chgType == null) {
      if (row.isshowlb == "Y") {
        if (row.xslx == "datetime") {
          ziduanColumn = {
            field: row.name,
            title: row.nr,
            width: 200,
            halign: "center",
            formatter: rs.util.formatDateTime,
            editor: "datetimebox"
          }
        } else if (row.xslx == "date") {
          ziduanColumn = {
            field: row.name,
            title: row.nr,
            width: 100,
            halign: "center",
            formatter: rs.util.formatDate,
            editor: "datebox"
          }
        } else if (row.xslx == "checkbox") {
          ziduanColumn = {
            field: row.name,
            title: row.nr,
            width: 200,
            halign: "center",
            formatter: function(value, data, index) {
              if (row.zdzd && value) {
                var dataDictsArray = datadict[row.zdzd];
                if (row.xslx == 'checkbox') {
                  // 多选框
                  $.each(dataDictsArray, function(index, dataDictArray) {
                    value = value.replace(dataDictArray['cdval'], dataDictArray['cddescp']);
                  });
                } else {
                  // 非多选框
                  $.each(dataDictsArray, function(index, dataDictArray) {
                    if (dataDictArray['cdval'] == value) {
                      value = dataDictArray['cddescp'];
                      return false;
                    }
                  });
                }
                return value;
              }
            },
            editor: {
              type: "checkbox",
              options: {
                on: "Y",
                off: "N"
              }
            }
          }
        } else if (row.xslx == "list") {
          ziduanColumn = {
            field: row.name,
            title: row.nr,
            width: 100,
            halign: "center",
            formatter: function(value, data, index) {
              if (row.zdzd && value) {
                var dataDictsArray = datadict[row.zdzd];
                $.each(dataDictsArray, function(index, dataDictArray) {
                  if (dataDictArray['cdval'] == value) {
                    value = dataDictArray['cddescp'];
                    return false;
                  }
                });
                return value;
              }
            }
          }
        } else {
          ziduanColumn = {
            field: row.name,
            title: row.nr,
            width: 100,
            halign: "center",
            editor: "textbox"
          };
        }
        columns.push(ziduanColumn);
      } else if (row.iskey == "Y") {
        ziduanColumn = {
          field: row.name,
          title: row.nr,
          hidden: true
        };
        columns.push(ziduanColumn);
      }
    });
    return columns;
  }

  rs.util.addSearchDivByZiduan = function($fid, ziduan) {
    if (ziduan.cxms == 'normal' || ziduan.cxms == 'fuzzy') {
      $('<div style="padding: 3px 0 3px 16px;"/>').addClass('form-group').css('width', '33%')
        .append($('<label/>').text(ziduan.nr + '：').css('width', '100px').css('text-align', 'right'))
        .append($('<input>', {
          name: ziduan.name,
          id: ziduan.id,
        }))
        .appendTo($fid);
    } else if (ziduan.cxms == 'range') {
      $('<div style="padding: 3px 0 3px 16px;"/>').addClass('form-group').css('width', '33%')
        .append($('<label/>').text(ziduan.nr + '：').css('width', '100px').css('text-align', 'right'))
        .append($('<input>', {
          name: '(B)' + ziduan.name,
          id: ziduan.id + 'B'
        }))
        .appendTo($fid);
      $('<div style="padding: 3px 0 3px 16px;"/>').addClass('form-group').css('width', '33%')
        .append($('<label/>').text('至').css('width', '100px').css('text-align', 'center'))
        .append($('<input>', {
          name: '(E)' + ziduan.name,
          id: ziduan.id + 'E'
        }))
        .appendTo($fid);
    }
  }

  rs.util.addMainFormDivByZiduan = function($fid, ziduan, isShow) {
    if (isShow) {
      $('<div/>').addClass('form-group')
        .append($('<label/>').text(ziduan.nr + '：').css('width', '100px').css('text-align', 'right'))
        .append($('<input>', {
          name: ziduan.name,
          id: 'fm' + ziduan.id,
          required: isShow && ziduan.isnull === 'N',
          width: '200px'
        }))
        // .show()
        .appendTo($fid);
    } else {
      $('<div/>').addClass('form-group')
        .append($('<label/>').text(ziduan.nr + '：').css('width', '100px').css('text-align', 'right'))
        .append($('<input>', {
          name: ziduan.name,
          id: 'fm' + ziduan.id,
          required: isShow && ziduan.isnull === 'N',
          width: '200px'
        }))
        .hide()
        .appendTo($fid);
    }
  }

  // 用于创建对话框表单中的EasyUi控件
  rs.util.addComponentByZiduan = function(id, row, datadict, readonly, configData) {
    if (readonly === undefined) readonly = false; // 默认值为false
    if (row.xslx == 'text') {
      if (row.lx == 'int' || row.lx == 'float') {
        $('#' + id).numberbox({
          readonly: readonly,
          width: 200
        });
      } else {
        $('#' + id).textbox({
          readonly: readonly,
          width: 200
        });
      }
    } else if (row.xslx == 'multiText') {
      $('#' + id).textbox({
        readonly: readonly,
        width: 200,
        multiline: true
      });
    } else if (row.xslx == 'password') {
      $('#' + id).passwordbox({
        readonly: readonly,
        width: 200
      });
    } else if (row.xslx == 'radio') {
      $('#' + id).combobox({
        readonly: readonly,
        width: 200,
        valueField: 'cdval',
        textField: 'cddescp',
        data: datadict[row.zdzd],
        panelHeight: 'auto'
      });
    } else if (row.xslx == 'checkbox') {
      $('#' + id).combobox({
        readonly: readonly,
        width: 200,
        valueField: 'cdval',
        textField: 'cddescp',
        data: datadict[row.zdzd],
        multiple: true,
        panelHeight: 'auto'
      });
    } else if (row.xslx == 'list') {
      $('#' + id).combobox({
        readonly: readonly,
        width: 200,
        valueField: 'cdval',
        textField: 'cddescp',
        data: datadict[row.zdzd],
        panelHeight: 'auto'
      });
    } else if (row.xslx == 'date') {
      $('#' + id).datebox({
        readonly: readonly
      });
    } else if (row.xslx == 'datetime') {
      $('#' + id).datetimebox({
        readonly: readonly
      });
    } else if (row.xslx == 'combotree') {
      var master = configData.master;
      var configId = master.id;
      var idField = rs.util.getIdField(master.bm, configData);
      $('#' + id).combotree({
        url: configData.baseUrl + '/admin/moduleTest/query',
        method: 'POST',
        panelHeight: 'auto',
        // method: 'GET',
        loadFilter: function(data) {
          var treeData = rs.util.transFormTreeData(idField, master.parent, data.rows);
          rs.util.formateTreeDataNameToText(treeData, idField, master.child);
          return treeData;
        },
        onBeforeLoad: function(node, param) {
          param.id = configId;
        }
      });
    } else {
      $('#' + id).textbox({
        readonly: readonly,
        width: 200
      });
    }
  };

  rs.util.getIdField = function getIdField(tableName, configData) {
    var idField;
    if (configData.queryUrl.indexOf('/admin/moduleTest') != -1) {
      var zbindex = tableName.lastIndexOf('_');
      if (zbindex >= 0) {
        idField = tableName.substring(zbindex + 1).toUpperCase() + "_ID";
      } else {
        idField = tableName.toUpperCase() + "_ID";
      }
    } else {
      idField = 'id';
    }
    return idField;
  }

  /**
   * 用于创建搜索表单的EasyUi控件
   * @param {object} $element jQuery对象
   * @param {object} field    字段对象
   * @param {object} datadict 字典对象
   */
  rs.util.addComponentByField = function($element, field, datadict) {
    if (field.xslx == 'text') {
      if (field.lx == 'int' || field.lx == 'float') {
        $element.numberbox({
          width: 160
        });
      } else {
        $element.textbox({
          width: 160
        });
      }
    } else if (field.xslx == 'multiText') {
      $element.textbox({
        width: 160,
        multiline: true
      });
    } else if (field.xslx == 'password') {
      $element.passwordbox({
        width: 160
      });
    } else if (field.xslx == 'radio') {
      $element.combobox({
        width: 160,
        valueField: 'cdval',
        textField: 'cddescp',
        data: datadict[field.zdzd],
        panelHeight: 'auto',
        onSelect: function() {
          setTimeout(function() {
            rs.util.getSelectedTab().find('#query').click(); // click query button
          }, 10);
        },
        onChange: function(newValue, oldValue) {
          if (newValue === '') {
            setTimeout(function() {
              rs.util.getSelectedTab().find('#query').click(); // click query button
            }, 10);
          }
        }

      });
    } else if (field.xslx == 'checkbox') {
      $element.combobox({
        width: 160,
        valueField: 'cdval',
        textField: 'cddescp',
        data: datadict[field.zdzd],
        panelHeight: 'auto',
        onSelect: function() {
          setTimeout(function() {
            rs.util.getSelectedTab().find('#query').click(); // click query button
          }, 10);
        },
        onChange: function(newValue, oldValue) {
          if (newValue === '') {
            setTimeout(function() {
              rs.util.getSelectedTab().find('#query').click(); // click query button
            }, 10);
          }
        }
      });
    } else if (field.xslx == 'list') {
      $element.combobox({
        width: 160,
        valueField: 'cdval',
        textField: 'cddescp',
        data: datadict[field.zdzd],
        panelHeight: 'auto',
        onSelect: function() {
          setTimeout(function() {
            rs.util.getSelectedTab().find('#query').click(); // click query button
          }, 10);
        },
        onChange: function(newValue, oldValue) {
          if (newValue === '') {
            setTimeout(function() {
              rs.util.getSelectedTab().find('#query').click(); // click query button
            }, 10);
          }
        }
      });
    } else if (field.xslx == 'date') {
      $element.datebox({
        width: 160
      });
    } else if (field.xslx == 'datetime') {
      $element.datetimebox({
        width: 160
      });
    } else {
      $element.textbox({
        width: 160
      });
    }
  };

  rs.util.textboxSetPrompt = function(textboxId, prompt) {
    var textbox = $('#' + textboxId);
    if (textbox) {
      textbox.attr('prompt', prompt);
      var span = textbox.siblings('span')[0];
      var input = $(span).find('input:first');
      if (input) $(input).attr('placeholder', prompt);
    }
  }

  rs.util.mapTabTitleDialogId = function(dialogId) {
    if (window.rs.global.adminTabsId !== undefined) {
      window.tabDialogMapAdmin = window.tabDialogMapAdmin || {};
      var titleAdmin = $('#' + rs.global.adminTabsId).tabs('getSelected').panel('options').title;
      window.tabDialogMapAdmin[titleAdmin] = dialogId;
    }

    if (window.gUserTabsId !== undefined) {
      window.tabDialogMapUser = window.tabDialogMapUser || {};
      var titleUser = $('#' + gUserTabsId).tabs('getSelected').panel('options').title;
      window.tabDialogMapUser[titleUser] = dialogId;
    }
  };

  rs.util.mapTabIndexConfigId = function(configId) {
    if (window.rs.global.adminTabsId !== undefined) {
      var tabAdmin = $('#' + rs.global.adminTabsId).tabs('getSelected');
      var indexAdmin = $('#' + rs.global.adminTabsId).tabs('getTabIndex', tabAdmin);
      rs.global.tabIndexConfigIdAdmin[indexAdmin] = configId;
    }

    if (window.gUserTabsId !== undefined) {
      var tabUser = $('#' + gUserTabsId).tabs('getSelected');
      var indexUser = $('#' + gUserTabsId).tabs('getTabIndex', tabUser);
      rs.global.tabIndexConfigIdUser[indexUser] = configId;
    }
  };

  rs.util.getSearchParam = function(formId) {
    var formData = {};
    var array = $('#' + formId).serializeArray();
    $.each(array, function(index, item) {
      var fieldName = item.name;
      var fieldValue = item.value;
      formData[fieldName] = fieldValue;
    });
    return formData;
  }

  rs.util.transFormTreeData = function(id, pid, data) {
    var i, l;
    var treeData = [];
    var tmpMap = [];
    for (i = 0, l = data.length; i < l; i++) {
      tmpMap[data[i][id]] = data[i];
    }

    for (i = 0, l = data.length; i < l; i++) {
      if (tmpMap[data[i][pid]] && data[i][id] != data[i][pid]) {
        if (!tmpMap[data[i][pid]]['children'])
          tmpMap[data[i][pid]]['children'] = [];
        tmpMap[data[i][pid]]['children'].push(data[i]);
      } else {
        treeData.push(data[i]);
      }
    }

    rs.util.sortTreeDataArray(treeData);
    return treeData;
  }

  /**
   * 获取并执行JS增强
   * @param  {string} configId 表单ID
   * @param  {string} jslx     JS增强类型：form, list
   * @return {Promise}         
   */
  rs.util.fetchAndExecuteJs = function(configId, jslx) {
    return rs.http.postJson({
      url: '/cg/form/js/getlist/' + configId,
      data: {
        jslx: jslx
      },
      success: function(result) {
        $.each(result.rows, function(index, row) {
          rs.util.executeScript(row.js);
        });
      }
    });
  }

  rs.util.executeScript = function(text) {
    try {
      // 间接调用，使用全局作用域
      var geval = eval;
      geval(text);
    } catch (e) {
      console.warn('::execute script error::', e);
    }
  };

  rs.util.createButtons = function($fid, buttons, isEmptyButtons) {
    if (isEmptyButtons) $fid.empty();
    $.each(buttons, function(index, button) {
      $('<button>', {
        type: button.type || 'button',
        text: button.text,
        class: button.class || 'btn btn-primary',
        click: button.handler
      }).appendTo($fid)
    });
  }

  rs.util.navTreeGetTreeView = function(navTreeId) {
    navTreeId = navTreeId || 'navTree';
    rs.http.ajaxJson({
      url: '/admin/menu/sidelist',
      success: function(result) {
        if (result.errorMsg) {
          $.messager.show({
            title: 'Error',
            msg: result.errorMsg
          });
        } else {
          var treeData = rs.util.transFormTreeData('id', 'pid', result.rows);
          rs.util.formateTreeDataSuitTree(treeData);
          rs.util.sortTreeDataArray(treeData);
          rs.util.createTreeView($('#' + navTreeId), treeData);
        }
      }
    });
  };

  rs.util.createTreeView = function($fid, treeData) {
    $fid.empty();
    $('<li class="treeview menu-toggle-center" id="menu-toggle"> ' + '<a><i id="menu-toggle-arrow" class="fa fa-arrow-circle-left"></i></a>' + '</li>')
      .appendTo($fid);

    rs.util.createTreeLeaf($fid, treeData);

    $fid.on("click", "a.navNode", function(event) {
      var url = $(this).data('url');
      var title = $(this).data('title');
      var menuId = $(this).data('menuid');
      if (url) {
        rs.global.currentMenuId = menuId;
        rs.util.navTabsAddInAdminPage({
          title: title,
          href: url
        });
      }
      return false;
    });

    $(document).on('click', '#menu-toggle', function(e) {
      var screenSizes = $.AdminLTE.options.screenSizes;
      e.preventDefault();

      //Enable sidebar push menu
      if ($(window).width() > (screenSizes.sm - 1)) {
        if ($("body").hasClass('sidebar-collapse')) {
          $("body").removeClass('sidebar-collapse').trigger('expanded.pushMenu');
          $('#menu-toggle').addClass('menu-toggle-center');
          $('#menu-toggle-arrow').removeClass('fa fa-arrow-circle-right').addClass('fa fa-arrow-circle-left');
        } else {
          $("body").addClass('sidebar-collapse').trigger('collapsed.pushMenu');
          $('#menu-toggle').removeClass('menu-toggle-center');
          $('#menu-toggle-arrow').removeClass('fa fa-arrow-circle-left').addClass('fa fa-arrow-circle-right');
        }
      }
      //Handle sidebar push menu for small screens
      else {
        if ($("body").hasClass('sidebar-open')) {
          $("body").removeClass('sidebar-open').removeClass('sidebar-collapse').trigger('collapsed.pushMenu');
        } else {
          $("body").addClass('sidebar-open').trigger('expanded.pushMenu');
        }
      }

      setTimeout(function() {
        $('#contentTabs').tabs('resize');
      }, 300);
    });
  }

  rs.util.createTreeLeaf = function($fid, treeData) {
    $.each(treeData, function(index, data) {
      if (data.children) {
        $('<li class="treeview">' + '<a href="#">' + '<i class="' + data.icon + '"></i> <span>' + data.text + '</span>' + '<span class="pull-right-container">' + '<i class="fa fa-angle-left pull-right"></i>' + '</span>' + '</a>' + '<ul class="treeview-menu" id ="' + data.id + '">' + '</ul>' + '</li>').appendTo($fid);
        rs.util.createTreeLeaf($('#' + data.id), data.children);
      } else {
        $('<li><a class="navNode" href="#" data-url=' + data.url + ' data-title=' + data.name + ' data-menuId=' + data.id + '>' + '<i class="' + data.icon + '"></i>' + data.text + '</a>' + '</li>').appendTo($fid);
      }
    })
  }

})(window.rs, window, jQuery);
