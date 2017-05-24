$(function() {
  rs.dev.adminLTE = jQuery.extend({}, rs.dev.default, (function($, window) {
  	var url = '/admin/moduleTest/query';
    var param = 'page=1&rows=10&id=09ca535fbc974ffea0206adc5e457f5f';
    var method = 'POST';
    var id = '09ca535fbc974ffea0206adc5e457f5f';

    //创建首页datagrid
    function createDataGrid(targetId,url,param,method,id) {
	    var d1 = fetchDataGridData(url, param, method);
	    var d2 = fetchZiDuanList(id);
	    $.when(d1,d2).done(function(res1,res2){
	    	var data = res1[0].rows;
	    	var columns = rs.util.getColumnsByZiduan(res2[0].master.ziduanList,res2[0].datadict);
	    	$('#' + targetId).datagrid({
		        data: data,
		        columns: [columns],
		        loadMsg: '加载中...',
		        fitColumns: true,
		        fit: true,
		        striped: true
		    })
		  })
    }

    //获取datagrid需要的数据
    function fetchDataGridData(url, param, method) {
      return rs.http.ajax({
        url: url,
        data: param,
        method: method
      })
    }

    //获取字段列表
    function fetchZiDuanList(id){
     	return rs.http.get({
     		url:'/admin/moduleTest/module/' + id
     	})
    }

    createDataGrid('jxSetting',url,param,method,id);

    $('body').on('click', '.tabItem', function() {
      var $this = $(this);
      var title = $this.data('title');
      var url = $this.data('url');
      var menuId = url.split('?')[1].split('=')[1];
      var currentUrl = url.split('?')[0];
      if (url) {
        rs.global.currentMenuId = menuId;
        rs.util.navTabsAddInAdminPage({
          title: title,
          href: currentUrl
        });
      }
    })
  })(jQuery, window));
})
