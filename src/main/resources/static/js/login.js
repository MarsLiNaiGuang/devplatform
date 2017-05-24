$(function() {
  var $submitBtn = $('#btnSubmit'),
    $userName = $('#username'),
    $passWord = $('#password');

  $userName.on('keyup', function(e) {
    if (e.keyCode === 13) $passWord.focus();
  }).focus()

  $passWord.on('keyup', function(e) {
    if (e.keyCode === 13) $submitBtn.trigger('click');
  })

  $submitBtn.click(checkoutLogin);

  //检查用户登录方法
  function checkoutLogin() {
    if (validateLoginForm()) {
      var formData = rs.util.formGetData('formLogin');
      rs.http.postJson({
        url: '/loginrest',
        data: formData
      }).then(function(data) {
        if (data && data.userFlag) {
          formData.username && localStorage.setItem('username', formData.username);
          data.id && localStorage.setItem('userId', data.id);
          location.href = '/index';
        } else {
          rs.util.messageError('登录失败，请检查重试', function() {
            $('input:first').focus();
          });
        }
      });
    }
  }

  function validateLoginForm() {
    if (valRegExp($userName) == 0) {
      rs.util.messageWarning('请输入用户名');
      $userName.val('').focus();
      return false;
    } else {
      if (valRegExp($passWord) == 0) {
        rs.util.messageWarning('请输入密码');
        $passWord.val('').focus();
        return false;
      } else {
        return true;
      }
    }
  }

  function valRegExp(Val) {
    return Val.val().replace(/(^\s*)|(\s*$)/g, "").length;
  }
});
