'use strict';

(function($) {

    $.fn.passwordbox.defaults.missingMessage = $.fn.textbox.defaults.missingMessage = $.fn.validatebox.defaults.missingMessage = '必填项';
    $.fn.passwordbox.defaults.rules.length.message = $.fn.textbox.defaults.rules.length.message = $.fn.validatebox.defaults.rules.length.message = '字符串长度（{0}-{1}）';
    $.fn.passwordbox.defaults.rules.email.message = $.fn.textbox.defaults.rules.email.message = $.fn.validatebox.defaults.rules.email.message = '无效的邮箱地址';
    $.fn.passwordbox.defaults.rules.url.message = $.fn.textbox.defaults.rules.url.message = $.fn.validatebox.defaults.rules.url.message = '无效的URL地址';

    $.fn.datebox.defaults.currentText = '今天';
    $.fn.datebox.defaults.closeText = '取消';
    $.fn.datebox.defaults.okText = '确认';
    $.fn.datebox.defaults.width = 200;

    $.fn.datetimebox.defaults.currentText = '今天';
    $.fn.datetimebox.defaults.closeText = '取消';
    $.fn.datetimebox.defaults.okText = '确认';
    $.fn.datetimebox.defaults.width = 200;

    $.fn.pagination.defaults.beforePageText = '第';
    $.fn.pagination.defaults.afterPageText = '/{pages}页';
    $.fn.pagination.defaults.displayMsg = '{from}-{to}共 {total}条';
    
    $.fn.datagrid.defaults.pageSize = 20;
    
    $.extend($.fn.textbox.methods, {
      addClearBtn: function(jq, iconCls) {
        return jq.each(function() {
          var t = $(this);
          var opts = t.textbox('options');
          opts.icons = opts.icons || [];
          opts.icons.unshift({
            iconCls: iconCls,
            handler: function(e) {
              $(e.data.target).textbox('clear').textbox('textbox').focus();
              $(this).css('visibility', 'hidden');
            }
          });
          t.textbox();
          if (!t.textbox('getText')) {
            t.textbox('getIcon', 0).css('visibility', 'hidden');
          }
          t.textbox('textbox').bind('keyup', function() {
            var icon = t.textbox('getIcon', 0);
            if ($(this).val()) {
              icon.css('visibility', 'visible');
            } else {
              icon.css('visibility', 'hidden');
            }
          });
        });
      }
    });
    
})(jQuery);
