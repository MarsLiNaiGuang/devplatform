rs.im = rs.im || (function($, window) {

  //检查登录状态
  function login(name, password) {
    return rs.http.postJson({
      url: '/im/login',
      data: {
        id: window.localStorage.getItem('userId'),
        name: name,
        password: password
      }
    });
  }

  //获取用户列表
  function getUserList() {
    return rs.http.ajax({
      url: '/im/login/userlist'
    });
  }
  //和某个人聊天
  function chatWithOne(obj) {
    return rs.http.postJson({
      url: '/im/chat/user/' + obj.id,
      data: {
        contype: obj.contype,
        content: obj.content
      },
      success: obj.success
    });
  }
  //获取和某人的聊天历史记录
  function getUserHis(obj) {
    return rs.http.postJson({
      url: '/im/log/userhis/' + obj.oppoUserId + '/' + obj.beginTime + '/' + obj.endTime,
      data: obj.data
    });
  }
  //获取和某人的未读信息
  function getUserUnread(obj) {
    return rs.http.postJson({
      url: '/im/log/userunread/',
      data: obj.data,
    });
  }
  //创建群组
  function createTopic(obj) {
    return $.ajax({
      url: window.rsContextPath + '/im/topic',
      type: 'POST',
      data: JSON.stringify(obj),
      contentType: 'application/json; charset=UTF-8'
    });
  }

  //删除群组
  function deleteTopic(obj) {
    return $.ajax({
      url: window.rsContextPath + '/im/topic',
      type: 'DELETE',
      data: JSON.stringify(obj),
      contentType: 'application/json; charset=UTF-8'
    });
  }
  function editTopic(obj) {
    return $.ajax({
      url: window.rsContextPath + '/im/topic/'+obj.id,
      type: 'PUT',
      data: JSON.stringify(obj.data),
      contentType: 'application/json; charset=UTF-8'
    });
  }


  //获取群组信息
  function getTopicList() {
    return rs.http.ajax({
      url: '/im/login/topiclist',
      method: 'GET'
    })
  }

  //获取群组的聊天记录
  function getGroupHis(obj) {
    return rs.http.postJson({
      url: '/im/log/grouphis/' + obj.oppoUserId + '/' + obj.beginTime + '/' + obj.endTime,
      data: obj.data,
    });
  }

  //获取群组的未读信息
  function getGroupUnread(obj) {
    return rs.http.postJson({
      url: '/im/log/groupunread/' + obj.oppoUserId,
      data: obj.data,
    });
  }


  //在群组中发言
  function chatInTopic(obj) {
    return rs.http.postJson({
      url: '/im/chat/topic/' + obj.id,
      data: {
        contype: obj.contype,
        content: obj.content
      },
      success: obj.success
    });
  }

  //获取群内成员信息
  
  function getTopicInfo(id){
    return rs.http.get({
      url:'/im/topic/' + id
    });
  }

  //发送给所有人
  function sendToAll(obj) {
    return rs.http.postJson({
      url: '/im/chat/all',
      data: {
        contype: obj.contype,
        content: obj.content
      }
    });
  }
  //文件上传
  function fileUpload(obj) {
    return $.ajax({
      url: window.rsContextPath + obj.url,
      type: obj.method,
      data: obj.data,
      enctype: 'multipart/form-data',
      processData: false,
      contentType: false
    });
  }
  //判断一个对象是否为空 
  function objectIsEmpty(obj) {
    for (var key in obj) {
      return false;
    }
    return true;
  }
  //按发送时间对去到的聊天记录进行升序排列
  function sortByTime(arr, key) {
    var temp = [],
      temp1 = [];
    arr.forEach(function(item) {
      temp.push(item.sendTime);
    });
    temp = temp.sort(function(a, b) {
      return a - b
    });
    for (var i = 0; i < temp.length; i++) {
      for (var j = 0; j < temp.length; j++) {
        if (temp[i] == arr[j].sendTime) {
          temp1.push(arr[j]);
        }
      }
    }
    return temp1;
  }
  function fileSizeIsOverMax(target,maxFileSize) {
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
    if (fileSize > maxFileSize) {
      return true;
    } else {
      return false;
    }
  }
  return {
    login: login,
    getUserList: getUserList,
    getTopicList: getTopicList,
    getUserHis: getUserHis,
    getUserUnread: getUserUnread,
    getGroupUnread: getGroupUnread,
    chatWithOne: chatWithOne,
    sortByTime: sortByTime,
    fileUpload: fileUpload,
    objectIsEmpty: objectIsEmpty,
    createTopic: createTopic,
    getGroupHis: getGroupHis,
    chatInTopic: chatInTopic,
    sendToAll: sendToAll,
    deleteTopic: deleteTopic,
    editTopic:editTopic,
    getTopicInfo:getTopicInfo,
    fileSizeIsOverMax:fileSizeIsOverMax
  };
})(jQuery, window);
