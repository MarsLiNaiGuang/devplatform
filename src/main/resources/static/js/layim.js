(function(rs, window, undefined) {
  var MAX_FILE_SIZE = 1024 * 100; //100M
  var config = {
      andTime: 200, //聊天主面板的显示和隐藏需要的时间，
      right: -232, //主面板隐藏时的位置,
      api: {
        friend: '/im/login/userlist', //好友列表接口
        group: '/im/login/topiclist', //群组列表接口 
      },
      user: { //当前用户的信息
        name: window.localStorage.getItem('username'),
        id: window.localStorage.getItem('userId'),
        face: rsContextPath + '/images/1.png',
        nkname: '我'
      },
      /*autoReplay: [
        '您好，我现在有事不在，一会再和您联系。',
        '你没发错吧？',
        '洗澡中，请勿打扰，偷窥请购票，个体四十，团体八折，订票电话：一般人我不告诉他！',
        '你好，我是主人的美女秘书，有什么事就跟我说吧，等他回来我会转告他的。',
        '我正在拉磨，没法招呼您，因为我们家毛驴去动物保护协会把我告了，说我剥夺它休产假的权利。',
        '<（@￣︶￣@）>',
        '你要和我说话？你真的要和我说话？你确定自己想说吗？你一定非说不可吗？那你说吧，这是自动回复。',
        '主人正在开机自检，键盘鼠标看好机会出去凉快去了，我是他的电冰箱，我打字比较慢，你慢慢说，别急……',
        '(*^__^*) 嘻嘻，是贤心吗？'
      ],*/
      chating: { //打开的聊天框

      },
      stopMP: function(e) { //阻止事件冒泡
        e ? e.stopPropagation() : e.cancelBubble = true;
      }
    },
    dom = [$(window), $(document), $('html'), $('body'), $('#rs-im')],
    xxim = {};
  xxim.current = 1;
  xxim.getUserInfo = function(arr) {
    var userInfo = {};
    arr.forEach(function(item, key) {
      if (item.hasOwnProperty('nkname')) {
        userInfo[item.id] = {};
        userInfo[item.id]["name"] = item.name;
        userInfo[item.id]["nkname"] = item.nkname;
      }
    });
    userInfo[config.user.id] = {}
    userInfo[config.user.id]['name'] = config.user.name;
    userInfo[config.user.id]["nkname"] = config.user.nkname;
    return userInfo;
  }

  // 设置未读消息状态
  xxim.setUnreadMark = function(target, num) {
      target.find('.unread').text(num).removeClass('nocount').addClass('count');
    }
    //主界面
  xxim.sock = new SockJS(window.rsContextPath + '/rs-websocket');
  xxim.sock.onerror = function(event) {
      rs.util.messageError('服务断开，请重新登录');
    }
    //监听sockJs连接打开
  xxim.sock.onopen = function() {
    rs.util.messageInfo('用户：' + config.user.name + '<br>登录成功');
  }

  //监听sockJs连接关闭
  xxim.sock.onclose = function() {
    rs.util.messageInfo('用户:' + config.user.name + '<br>已下线');
    $('#rs-im').empty();
    $('#rs-im-control').show();
    $('.xubox_layer').remove();
  }

  //sockJs 接收到消息
  xxim.sock.onmessage = function(event) {
    var response = JSON.parse(event.data);
    var type = response.type === 'G' ? 'group' : 'one';
    xxim.unread = {};
    xxim.unread[type + 'count'] = parseInt($('.tab' + type).find('.unread').text() || 0) + 1;
    if (type === 'group') {
      xxim.unread[response.recver] = parseInt($('#' + type + response.recver).find('.unread ').text() || 0) + 1;
      var messageKeys = type + response.recver;
    } else {
      xxim.unread[response.sender] = parseInt($('#' + type + response.sender).find('.unread ').text() || 0) + 1;
      var messageKeys = type + response.sender;
    }
    if (!rs.im.objectIsEmpty(config.chating)) {
      var nowkeys = xxim.nowchat.type + xxim.nowchat.id
      if (nowkeys !== messageKeys) {
        xxim.setUnreadMark($('#' + type + response.sender), xxim.unread[response.sender]);
        xxim.setUnreadMark($('#layim_user' + type + response.sender), xxim.unread[response.sender]);
        xxim.setUnreadMark($('#layim_user' + type + response.recver), xxim.unread[response.recver]);
        xxim.setUnreadMark($('#' + type + response.recver), xxim.unread[response.recver]);
        xxim.setUnreadMark($('.tab' + type), xxim.unread[type + 'count']);
      }
      var imarea = xxim.chatbox.find('#layim_area' + messageKeys);
      $('#' + nowkeys).find('.unread').text(xxim.unread[response.sender]);
      imarea.append(xxim.loghtml({
        time: new Date(response.sendTime).toLocaleString(),
        name: xxim.userInfo[response.sender].nkname,
        face: config.user.face,
        content: response.content,
        contype: response.contype,
        fileName: response.fileName
      }));
      imarea.scrollTop(imarea[0].scrollHeight);

    } else {
      xxim.setUnreadMark($('#' + type + response.sender), xxim.unread[response.sender]);
      xxim.setUnreadMark($('#' + type + response.recver), xxim.unread[response.recver]);
      xxim.setUnreadMark($('.tab' + type), xxim.unread[type + 'count']);
    }
  }

  xxim.tab = function(index) {
    //index:表示第几个tab，0：好友列表；1：群组列表；2：消息记录
    var node = xxim.node;
    node.tabs.eq(index).show().addClass('xxim_tabnow').siblings().removeClass('xxim_tabnow'); //设置当前所在tab的状态
    node.list.eq(index).show().siblings('.xxim_list').hide(); //设置当前tab对应的list显示
    if (node.list.eq(index).find('li').length === 0) { //当list中内容为空时，调用接口获取数据
      xxim.getDatas(index);
    }
  }

  xxim.getDatas = function(index) {
      var dataApi = [config.api.friend, config.api.group];
      var node = xxim.node,
        list = node.list.eq(index);
      xxim.async = rs.http.ajax({
        url: dataApi[index],
        method: 'GET',
        success: function(res) {
          xxim.unread = {};
          if (index === 0) {
            xxim.userInfo = xxim.getUserInfo(res.rows);
          }
          //res = JSON.parse(res);
          var str = '';
          if (res.total > 0) { //判断渠道的数据是否为空
            $.each(res.rows, function(key, citem) {
              if (index === 0) {
                xxim.unread[citem.id] = citem.count;
                str += '<li class="xxim_childnode useritem"><div type="' + (index === 0 ? 'one' : 'group') + '" id="' + (index === 0 ? 'one' : 'group') + citem.id + '" data-id="' + citem.id + '" class="userinfo"><img src="http://tp1.sinaimg.cn/1571889140/180/40030060651/1" class="xxim_oneface"><span class="xxim_onename">' + citem.name + '</span><span class="fr unread' + (citem.count === 0 ? ' nocount' : ' count') + '">' + citem.count + '</span></div></li>';
              } else {
                xxim.unread[citem.id] = citem.count;
                str += '<input type="checkbox" data-id="' + citem.id + '" class="fr delete-select"><li class="xxim_childnode useritem"><span class="edit-topic" data-id="' + citem.id + '">修改</span><div type="' + (index === 0 ? 'one' : 'group') + '" id="' + (index === 0 ? 'one' : 'group') + citem.id + '" data-id="' + citem.id + '" class="userinfo"><img src="http://tp1.sinaimg.cn/1571889140/180/40030060651/1" class="xxim_oneface"><span class="xxim_onename">' + citem.name + '</span><span class="fr unread' + (citem.count === 0 ? ' nocount' : ' count') + '">' + citem.count + '</span></div></li>';
              }
            });

            list.append(str);
          } else {
            list.empty().append('<li class="xxim_errormsg">没有任何数据</li>');
          }
          list.removeClass('loading');

          if (index === 1) {
            //编辑讨论组信息
            $('.edit-topic').on('click', function(e) {
              var _this = $(this);
              var topicId = _this.attr('data-id');
              var topicName = $('#group' + topicId).find('.xxim_onename').text();
              xxim.editTopic(topicId, topicName);
            });
          }
        },
        error: function() {
          list.empty().append('<li class="xxim_errormsg">请求失败</li>');
        }
      });
    }
    //编辑讨论组信息
  xxim.editTopic = function(topicId, topicName) {
      $('#edittopic .topic-title').find('input').val(topicName);
      rs.im.getTopicInfo(topicId).done(function(res) {
        if (res.total) {
          var str = '<input type="hidden" id="topicId" value="' + topicId + '">';
          var actors = [];
          //取出讨论组中现有的成员
          $.each(res.rows, function(index, item) {
              actors.push(item.id);
            })
            //根据讨论组现有成员和userInfo生成成员选项
          for (var key in xxim.userInfo) {
            if (xxim.userInfo.hasOwnProperty(key)) {
              if (key === config.user.id) continue;
              if (actors.indexOf(key) !== -1) {
                str += '<li class="actor-item"><label><input type="checkbox" checked id="actor-name" data-id = "' + key + '" />' + xxim.userInfo[key].name + '</label></li>';
              } else {
                str += '<li class="actor-item"><label><input type="checkbox" id="actor-name" data-id = "' + key + '" />' + xxim.userInfo[key].name + '</label></li>';
              }
            }
          }
          //将成员选项插入到编辑框
          $('#edittopic .actor').append(str);
          //设置对话框属性对话框
          $('#edittopic').dialog({
            title: '修改群组信息',
            buttons: '#edit',
            onClose: function() {
              $('#edittopic input').val('');
              $('#edittopic .actor').empty();
            }
          });
          //打开对话框
          $('#edittopic').dialog('open');
        }
      })
    }
    //获取相关的节点
  xxim.renode = function() {
      var node = xxim.node = {
        tabs: $('#xxim_tabs>span'),
        list: $('.xxim_list'),
        layimMin: $('#layim_min'),
        layimNode: $('#xximmm'),
        online: $('.xxim_online'),
        setonline: $('.xxim_setonline'),
        onlinetex: $('#xxim_onlinetex'),
        xximon: $('#xxim_on'),
        layimFooter: $('#xxim_bottom'),
        xximHide: $('#xxim_hide'),
        xximSearch: $('#xxim_searchkey'),
        searchMian: $('#xxim_searchmain'),
        closeSearch: $('#xxim_closesearch')
      }
    }
    //主面板展开关闭
  xxim.expend = function() {
      var node = xxim.node;
      if (node.layimNode.attr('state') !== '1') {
        node.layimNode.stop().animate({ right: config.right }, config.andTime, function() {
          node.xximon.addClass('xxim_off');

          try {
            localStorage.layimState = 1;
          } catch (e) {}
          node.layimNode.attr({ state: 1 });
          node.layimFooter.addClass('xxim_expend').stop().animate({ marginLeft: 0 }, config.andTime / 2);
          node.xximHide.addClass('xxim_show');
        });
      } else {
        node.layimNode.stop().animate({ right: 1 }, config.andTime, function() {
          node.xximon.removeClass('xxim_off');
          try {
            localStorage.layimState = 2;
          } catch (e) {}
          node.layimNode.removeAttr('state');
          node.layimFooter.removeClass('xxim_expend');
          node.xximHide.removeClass('xxim_show');
        });
        node.layimFooter.stop().animate({ marginLeft: 0 }, config.andTime);
      }
    }
    //初始化窗口格局
  xxim.layinit = function() {
    var node = xxim.node;

    //主界面
    try {
      if (!localStorage.layimState) {
        config.aniTime = 0;
        localStorage.layimState = 1;
      }
      if (localStorage.layimState === '1') {
        node.layimNode.attr({ state: 1 }).css({ right: config.right });
        node.xximon.addClass('xxim_off');
        node.layimFooter.addClass('xxim_expend').css({ marginLeft: 0 });
        node.xximHide.addClass('xxim_show');
      }
    } catch (e) {
      layer.msg(e.message, 5, -1);
    }
  };

  //渲染聊天内容到ChatBox
  xxim.renderMessageToChatBox = function(obj, target, type) {
    target.append(xxim.loghtml({
      time: obj.time,
      name: obj.name,
      face: obj.face,
      content: obj.content,
      contype: obj.contype,
      fileName: obj.fileName
    }, type));
  }

  //聊天记录回显
  xxim.renderLog = function(arr) {
      obj = rs.im.sortByTime(arr, 'sendTime');
      var keys = xxim.nowchat.type + xxim.nowchat.id;
      var imarea = xxim.chatbox.find('#layim_area' + keys);
      $('#' + keys).find('.unread').removeClass('count').addClass('nocount').text(xxim.unread[xxim.nowchat.id]);
      $('#layim_user' + keys).find('.unread').removeClass('count').addClass('nocount').text(xxim.unread[xxim.nowchat.id]);
      $.each(obj, function(key, item) {
        var renderData = {
          time: new Date(item.sendTime).toLocaleString(),
          name: config.user.nkname,
          face: config.user.face,
          content: item.content,
          contype: item.contype
        }
        if (item.contype === 'M') {
          if (item.sender === config.user.id) {
            xxim.renderMessageToChatBox(renderData, imarea, 'me');

          } else {
            renderData.name = xxim.userInfo[item.sender].nkname;
            xxim.renderMessageToChatBox(renderData, imarea, '');
          }
        } else {
          renderData.fileName = item.fileName;
          if (item.sender === config.user.id) {
            renderData.name = config.user.nkname;
            xxim.renderMessageToChatBox(renderData, imarea, 'me');

          } else {
            renderData.name = xxim.userInfo[item.sender].nkname;
            xxim.renderMessageToChatBox(renderData, imarea, '');
          }
        }
      });
      imarea.scrollTop(imarea[0].scrollHeight);
    }
    //显示右侧聊天记录框
  xxim.showChatLogBox = function() {
      $('.layim_chatlog').show();
      $('.layim_groups').hide();
      $('.layim_chatbox').addClass('w720');
      if ($('.layim_chatlist li').length == 1) {
        $('.layim_chat').addClass('w520');
      } else {
        $('.layim_chat').addClass('w390');
      }
    }
    //隐藏右侧聊天记录框
  xxim.hideChatLogBox = function() {

      $('.layim_chatlog').hide();
      $('.layim_chatlog_content').empty()
      $('.layim_chatbox').removeClass('w720');
      $('.layim_chat').removeClass('w520 w390');
    }
    //渲染右侧聊天记录
  xxim.renderChatLogFun = function(res) {
    var obj = rs.im.sortByTime(res.rows, 'sendTime');
    var imarea = $('.layim_chatlog_content');
    if (res.total) {
      imarea.empty();
      $.each(obj, function(key, item) {
        var renderData = {
          time: new Date(item.sendTime).toLocaleString(),
          name: xxim.userInfo[item.sender].nkname || xxim.userInfo[item.sender].name,
          face: config.user.face,
          content: item.content,
          contype: item.contype,
          fileName: item.fileName
        }
        xxim.renderMessageToChatBox(renderData, imarea, '');
        imarea.scrollTop(imarea[0].scrollHeight);
      })
      $('.current').text(xxim.current)
    } else {
      console.log('没有更多的聊天记录了');
      if (xxim.current > 1) {
        xxim.current = xxim.current - 1;
      }
    }
  }
  xxim.renderChatLog = function(currentPage) {
      reqData = {
        oppoUserId: xxim.nowchat.id,
        data: {
          current: currentPage,
          size: 20
        },
        beginTime: new Date(2017, 0, 1).getTime(),
        endTime: new Date().getTime()
      }
      if (xxim.nowchat.type === 'one' && typeof reqData === 'object') {
        rs.im.getUserHis(reqData).done(function(res) {
          if (res.total) {
            xxim.renderChatLogFun(res);
          } else {
            $('.layim_chatlog_content').html('<div style="text-align:center;margin-top:20px;">暂无数据</div>');
          }
        });
      } else if (xxim.nowchat.type === 'group' && typeof reqData === 'object') {
        rs.im.getGroupHis(reqData).done(function(res) {
          if (res.total) {
            xxim.renderChatLogFun(res);
          } else {
            $('.layim_chatlog_content').html('<div style="text-align:center;margin-top:20px;">暂无数据</div>');
          }

        });
      }
    }
    //聊天窗口
  xxim.popchat = function(param) {
      var node = xxim.node,
        log = {};
      log.success = function(layero) {
        layer.setMove();
        xxim.chatbox = layero.find('#layim_chatbox');
        log.chatlist = xxim.chatbox.find('.layim_chatmore>ul');
        log.chatlist.html('<li data-id="' + param.id + '" type="' + param.type + '"  id="layim_user' + param.type + param.id + '"><span>' + param.name + '</span><span class="unread"></span><em>×</em></li>');
        xxim.tabchat(param, xxim.chatbox);

        //关闭窗口        
        xxim.chatbox.find('.layim_close').on('click', function() {
            var indexs = layero.attr('times');
            layer.close(indexs);
            xxim.chatbox = null;
            config.chating = {};
            config.chatings = 0;
          })
          //关闭某一个窗口
          //鼠标滑过显示关闭按钮
        log.chatlist.on('mouseenter', 'li', function() {
            $(this).find('em').show();
          }).on('mouseleave', 'li', function() {
            $(this).find('em').hide();
          })
          //点击按钮关闭对应的窗口
        log.chatlist.on('click', 'li em', function(e) {
          xxim.hideChatLogBox();
          var parents = $(this).parent(),
            dataType = parents.attr('type'),
            dataId = parents.attr('data-id'),
            index = parents.index(),
            chatlist = log.chatlist.find('li'),
            indexs;
          config.stopMP(e);
          delete config.chating[dataType + dataId];
          config.chatings--;
          parents.remove();
          $('#layim_area' + dataType + dataId).remove();
          if (dataType === 'group') {
            $('#layim_group' + dataType + dataId).remove();
          }
          if (parents.hasClass('layim_chatnow')) { //判断关闭的是否是当前的窗口，如果是，再判断当前窗口是不是最下面一个，如果是则将切换到上一个，如果不是则切换到下一个
            if (index === config.chatings) {
              indexs = index - 1;
            } else {
              indexs = index + 1;
            }
            xxim.tabchat(config.chating[chatlist.eq(indexs).attr('type') + chatlist.eq(indexs).attr('data-id')]);
          }
          if (log.chatlist.find('li').length === 1) {
            log.chatlist.parent().hide();
          }
        })

        //窗口选项卡
        log.chatlist.on('click', 'li', function() {
          xxim.hideChatLogBox();
          var _this = $(this),
            dataType = _this.attr('type'),
            dataId = _this.attr('data-id');
          xxim.tabchat(config.chating[dataType + dataId]);
          $('.xxim_tabs').find('.unread').remove('count').addClass('nocount').text(0);
        })



        //点击最小化窗口
        xxim.chatbox.find('.layer_setmin').on('click', function() {
          var indexs = layero.attr('times');
          layero.hide();
          node.layimMin.text(xxim.nowchat.name).show();
        })

        //点击打开文件选择框
        xxim.chatbox.find('.layim_addfile').on('click', function() {
          $(this).next().trigger('click');
        })

        //上传文件
        $('.layim_chat').find('.uploader' + xxim.nowchat.id).on('change', function() {
          xxim.fileUpload(this, node);
        });

        //右侧聊天记录框
        $('.layim_seechatlog').on('click', function() {
          var oppoUserId = $(this).attr('data-id');
          xxim.showChatLogBox();
          xxim.renderChatLog(1);
        });
        //聊天记录翻页
        //上一页
        $('.prev').on('click', function() {
          if (xxim.current === 1) {
            console.log('已经是第一页了');
            return;
          }
          xxim.current -= 1;
          xxim.renderChatLog(xxim.current)

        });
        //下一页
        $('.next').on('click', function() {
          xxim.current += 1;
          xxim.renderChatLog(xxim.current)
        });

        //关闭聊天记录
        $('.layim_chatlog_close').on('click', function() {
          xxim.hideChatLogBox();
        });

        xxim.transmit();
      };
      log.html = '<div class="layim_chatbox" id="layim_chatbox">' + '<h6>' + '<span class="layim_move"></span>' +
        '<a class="layim_face"><img src="' + param.face + '"></a>' +
        '<a class="layim_names">' + param.name + '</a>' +
        '<span class="layim_rightbtn"><i class="layer_setmin"></i><i class="layim_close"></i></span>' + '</h6>' +
        '<div class="layim_chatmore" id="layim_chatmore"><ul class="layim_chatlist">' +
        '</ul></div>' + '<div class="layim_groups" id="layim_groups"></div>' +
        '<div class="layim_chat">' +
        '<div class="layim_chatarea" id="layim_chatarea"><ul class="layim_chatview layim_chatthis" id="layim_area' + param.type + param.id + '"></ul></div>' +
        '    <div class="layim_tool">' +
        '        <a href="javascript:;"><form id="upload-file-form' + param.id + '" style="display:inline-block"><i class="layim_addfile" title="上传附件"></i><input id="upload-file-input" type="file" class="uploader uploader' + param.id + '" name="uploadfile" accept="*" /></form></a>' +
        '        <a class="layim_seechatlog"><i></i>聊天记录</a>' +
        '    </div>' +
        '    <textarea class="layim_write" id="layim_write"></textarea>' +
        '    <div class="layim_send">' +
        '        <div class="layim_sendbtn" id="layim_sendbtn">发送</div>' +
        '        <div class="layim_sendtype" id="layim_sendtype">' +
        '            <span><i>√</i>按Enter键发送</span>' +
        '            <span><i></i>按Ctrl+Enter键发送</span>' +
        '        </div>' +
        '    </div>' + '</div>' + '<div class="layim_chatlog"><div class="layim_chatlog_title">聊天记录<span class="layim_chatlog_close">X</span></div><div class="layim_chatlog_content" style="height: 430px;overflow-y: scroll;padding-bottom:25px;"></div><div class="layim_pagination"><a class="prev">上一页</a>第<span class="current">1</span>页<a class="next">下一页</a></div></div></div>';
      if (config.chatings < 1) {
        $.layer({
          type: 1,
          border: [0],
          title: false,
          shade: [0],
          area: ['620px', '493px'],
          move: ['.layim_chatbox .layim_move', true],
          moveType: 1,
          closeBtn: false,
          offset: [(($(window).height() - 493) / 2) + 'px', ''],
          page: {
            html: log.html
          },
          success: function(layero) {
            log.success(layero);
          }
        });
      } else {

        log.chatmore = xxim.chatbox.find('#layim_chatmore');
        log.chatarea = xxim.chatbox.find('#layim_chatarea');

        log.chatmore.show();

        log.chatmore.find('ul>li').removeClass('layim_chatnow');
        log.chatmore.find('ul').append('<li data-id="' + param.id + '" type="' + param.type + '" id="layim_user' + param.type + param.id + '" class="layim_chatnow"><span>' + param.name + '</span><span class="unread"></span><em>×</em></li>');

        log.chatarea.find('.layim_chatview').removeClass('layim_chatthis');
        log.chatarea.append('<ul class="layim_chatview layim_chatthis" id="layim_area' + param.type + param.id + '"></ul>');

        xxim.tabchat(param);
        $('#layim_min').hide();
      }
      //群组
      log.chatgroup = xxim.chatbox.find('#layim_groups');
      if (param.type === 'group') {
        log.chatgroup.find('ul').removeClass('layim_groupthis');
        log.chatgroup.append('<ul class="layim_groupthis" id="layim_group' + param.type + param.id + '"></ul>');
        xxim.getGroups(param);
      }
      //点击群员切换聊天窗
      log.chatgroup.on('click', 'ul>li', function() {
        xxim.popChatBox($(this));
      });
    }
    //弹出聊天窗口
  xxim.popChatBox = function(_this) {
      var node = xxim.node,
        dataId = _this.attr('data-id'),
        param = {
          id: dataId,
          type: _this.attr('type'),
          name: _this.find('.xxim_onename').text(),
          face: _this.find('.xxim_oneface').attr('src'),
        },
        key = param.type + dataId;
      if (!config.chating[key]) { //判断该用户的对话框是否已经打开，
        xxim.popchat(param);
        config.chatings++;
      } else {
        xxim.tabchat(param);
      }
      config.chating[key] = param;
      xxim.unread[dataId] = 0;
      var chatbox = $('#layim_chatbox');
      if (chatbox[0]) {
        node.layimMin.hide();
        chatbox.parents('.xubox_layer').show();
      }
    }
    //定位到某个聊天窗口
  xxim.tabchat = function(param) {
    xxim.hideChatLogBox();
    var node = xxim.node,
      log = {},
      keys = param.type + param.id;
    xxim.nowchat = param;
    var reqData = {
      oppoUserId: param.id,
      data: {
        current: 1,
        size: 20
      },
      endTime: new Date().getTime(),
      beginTime: new Date().getTime() - 1000 * 60 * 60 * 24 * 3
    }
    xxim.chatbox.find('#layim_user' + keys).addClass('layim_chatnow').siblings().removeClass('layim_chatnow');
    xxim.chatbox.find('#layim_area' + keys).addClass('layim_chatthis').siblings().removeClass('layim_chatthis');
    xxim.chatbox.find('#layim_group' + keys).addClass('layim_groupthis').siblings().removeClass('layim_groupthis');

    xxim.chatbox.find('.layim_face>img').attr('src', param.face);
    xxim.chatbox.find('.layim_face, .layim_names').attr('href', param.href);
    xxim.chatbox.find('.layim_names').text(param.name);

    xxim.chatbox.find('.layim_seechatlog').attr('data-id', param.id);

    log.groups = xxim.chatbox.find('.layim_groups');
    $('#' + keys).find('.unread').removeClass('count').addClass('nocount').text(xxim.unread[xxim.nowchat.id]);
    $('#layim_user' + keys).find('.unread').removeClass('count').addClass('nocount').text(xxim.unread[xxim.nowchat.id]);
    $('.xxim_tabnow').find('.unread').remove('count').addClass('nocount').text(0);
    if (param.type === 'group') {
      log.groups.show();
    } else {
      log.groups.hide();
    }
    if (param.type === 'one') {
      $.when(rs.im.getUserHis(reqData)).done(function(res) {
        if (res.total) {
          $('.layim_chatthis').empty();
          xxim.renderLog(res.rows);
        }
      })
    } else {
      $.when(rs.im.getGroupHis(reqData)).done(function(res) {
        if (res.total) {
          $('.layim_chatthis').empty();
          xxim.renderLog(res.rows);
        }
      })
    }


    $('#layim_write').focus();
  };
  //请求群员
  xxim.getGroups = function(param) {
    var keys = param.type + param.id,
      str = '',
      groupss = xxim.chatbox.find('#layim_group' + keys);
    groupss.addClass('loading');
    rs.http.ajax({
      url: '/im/topic/' + param.id,
      success: function(datas) {
        if (datas.total) {
          var ii = 0,
            lens = datas.total;
          if (lens > 0) {
            for (; ii < lens; ii++) {
              str += '<li data-id="' + datas.rows[ii].id + '" type="one"><img src="' + config.user.face + '"><span class="xxim_onename">' + datas.rows[ii].name + '</span></li>';
            }
          }
        } else {
          str = '<li class="layim_errors">' + datas.msg + '</li>';
        }
        groupss.removeClass('loading');
        groupss.html(str);
      },
      error: function() {
        groupss.removeClass('loading');
        groupss.html('<li class="layim_errors">请求异常</li>');
      }
    });
  };

  // 文件上传
  xxim.fileUpload = function(target, node) {
    if (xxim.fileSizeIsOverMax(target)) {
      rs.util.messageInfo('文件大小不能超过100M');
      return;
    }
    var temparr = $(target).val().split('\\');
    var fileName = temparr[temparr.length - 1];
    if (fileName.length > 30) {
      rs.util.messageInfo('文件名过长，无法上传');
      return;
    }
    var keys = xxim.nowchat.type + xxim.nowchat.id;
    var imarea = xxim.chatbox.find('#layim_area' + keys);
    var renderData = {
      time: new Date().toLocaleString(),
      name: config.user.nkname,
      face: config.user.face,
      content: 'tempId',
      contype: 'F',
      fileName: fileName

    }
    imarea.append(xxim.uploadhtml(renderData));
    imarea.scrollTop(imarea[0].scrollHeight);

    rs.im.fileUpload(xxim.nowchat.id).done(function(res) {
      $('.uploadhtml').remove();
      if (xxim.nowchat.type === 'one') {
        rs.im.chatWithOne({
          content: res.id,
          contype: 'F',
          id: xxim.nowchat.id,
          success: function() {
            renderData.content = res.id;
            xxim.renderMessageToChatBox(renderData, imarea, 'me');
            node.imwrite.val('').focus();
            imarea.scrollTop(imarea[0].scrollHeight);
            rs.util.messageInfo('文件上传成功');
          }
        })
      } else {
        rs.im.chatInTopic({
          content: res.id,
          contype: 'F',
          id: xxim.nowchat.id,
          success: function() {
            var keys = xxim.nowchat.type + xxim.nowchat.id;
            var imarea = xxim.chatbox.find('#layim_area' + keys);
            renderData.content = res.id;
            xxim.renderMessageToChatBox(renderData, imarea, 'me');
            node.imwrite.val('').focus();
            imarea.scrollTop(imarea[0].scrollHeight);
          }
        })
      }
    }).fail(function() {
      $('.uploadhtml').remove();
      rs.util.messageError('文件发送失败，请重试');
    })
  }

  //判断上传的文件大小是否超出限制
  xxim.fileSizeIsOverMax = function(target) {
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
    if (fileSize > MAX_FILE_SIZE) {
      return true;
    } else {
      return false;
    }
  }

  //事件
  xxim.event = function() {
      var node = xxim.node;
      //tabs 切换
      node.tabs.eq(0).addClass('xxim_tabnow');
      node.tabs.on('click', function() {
          var _this = $(this),
            _index = _this.index();
          xxim.tab(_index);
          /*if ($(this).find('.unread').hasClass('count')) {
            $(this).find('.unread').removeClass('count').addClass('nocount').text(0);
          }*/
        })
        //设置在线下线
      node.online.on('click', function(e) {
        config.stopMP(e);
        node.setonline.show();
      });
      node.setonline.find('span').on('click', function(e) {
        var index = $(this).index();
        config.stopMP(e);
        if (index === 0) {
          node.onlinetex.html('在线');
          node.online.removeClass('xxim_offline');
        } else if (index === 1) {
          node.onlinetex.html('下线');
          node.online.addClass('xxim_offline');
          xxim.sock.close();
        }
        node.setonline.hide();
      });
      //列表展开收起
      node.list.on('click', 'h5', function() {
          var _this = $(this),
            _chat = _this.siblings('.xxim_chatlist'),
            _parentss = _this.parent();
          if (_parentss.hasClass('xxim_liston')) {
            _chat.hide();
            _parentss.removeClass('xxim_liston');
          } else {
            _chat.show();
            _parentss.addClass('xxim_liston');
          }
        })
        //弹出聊天窗口
      config.chatings = 0;
      node.list.on('click', '.xxim_childnode .userinfo', function() {
          var _this = $(this);
          xxim.popChatBox(_this);
        })
        //点击还原最小化的窗口
      node.layimMin.on('click', function() {
        $(this).hide();
        $('#layim_chatbox').parents('.xubox_layer').show();
      })

      node.xximon.on('click', xxim.expend);
      node.xximHide.on('click', xxim.expend);

      //创建讨论组
      $('.xxim_bottom').on('click', '#createGroup', function() {
        rs.im.getUserList().done(function(res) {
          if (res.total) {
            var str = '';
            $.each(res.rows, function(key, item) {
              str += '<li class="actor-item"><label><input type="checkbox" id="actor-name" data-id = "' + item.id + '" />' + item.name + '</label></li>';
            })
            $('#createtopic .actor').append(str);
            $('#createtopic').dialog({
              title: '创建讨论组',
              buttons: '#add',
              onClose: function() {
                $('#createtopic input').val('');
                $('#createtopic .actor').empty();
              }
            });
            $('#createtopic').dialog('open');
          }
        });
      })

      $('#confirm-add').on('click', function(e) {
        var e = e || window.event;
        var actor = [config.user.id];
        var topicName = $('#createtopic .topic-title input').val();
        var actors = $('#createtopic .actor-item').find('input:checked');
        if ($.trim(topicName) === '') {
          rs.util.messageInfo('请填写讨论组名称');
          return;
        }
        if (actors.length < 2) {
          rs.util.messageInfo('请至少选择两名讨论组成员');
          return;
        };
        for (var i = 0; i < actors.length; i++) {
          actor.push($(actors[i]).attr('data-id'));
        }
        rs.im.createTopic({
          name: topicName,
          actor: actor.join(',')
        }).done(function(res) {
          $('#createtopic').dialog('close');
          rs.util.messageInfo('讨论组创建成功');
          $('.xxim_list_group li').remove();
          $('.xxim_list_group input').remove();
          xxim.getDatas(1);
        }).fail(function(jqXHR, textStatus) {
          if (jqXHR.status === 302) {
            rs.util.messageInfo('该讨论组名称已存在，请更换后重试');
          }
        });
        e.preventDefault();
      })

      //保存讨论组修改信息
      $('#confirm-edit').on('click', function(e) {
        var topicId = $('#topicId').val();
        var e = e || window.event;
        var actor = [config.user.id];
        var topicName = $('#edittopic .topic-title input').val();
        var actors = $('#edittopic .actor-item').find('input:checked');
        if ($.trim(topicName) === '') {
          rs.util.messageInfo('请填写讨论组名称');
          return;
        }
        if (actors.length < 2) {
          rs.util.messageInfo('请至少选择两名讨论组成员');
          return;
        };
        for (var i = 0; i < actors.length; i++) {
          actor.push($(actors[i]).attr('data-id'));
        }
        rs.im.editTopic({
          id: topicId,
          data: {
            name: topicName,
            actor: actor.join(',')
          }
        }).done(function(res) {
          $('#edittopic').dialog('close');
          rs.util.messageInfo('讨论组修改成功');
          $('.xxim_list_group li').remove();
          $('.xxim_list_group input').remove();
          xxim.getDatas(1);
        }).fail(function(jqXHR, textStatus) {
          if (jqXHR.status === 302) {
            rs.util.messageInfo('该讨论组名称已存在，请更换后重试');
          }
        });
        e.preventDefault();
      })


      //删除讨论组
      $('.xxim_list .delete').on('click', function() {
        $(this).hide();
        $('.edit-topic').css('font-size', '0');
        $('.delete-select').show();
        $('.confirm-delete').css('display', 'table-cell');
        $('.cancle-delete').css('display', 'table-cell');
      })
      $('.xxim_list .confirm-delete').on('click', function() {
        var deleteItem = $('.delete-select:checked'),
          deletes = [];
        if (deleteItem.length === 0) {
          rs.util.messageInfo('请先选择要删除的讨论组');
          return;
        }
        for (var i = 0, len = deleteItem.length; i < len; i++) {
          deletes.push($(deleteItem[i]).attr('data-id'));
        }
        rs.im.deleteTopic({ ids: deletes }).done(function() {
          rs.util.messageInfo('讨论组删除成功');
          clearDeletedTopic(deletes);
          $('.xxim_list .delete').css('display', 'table-cell');;
          $('.delete-select').hide();
          $('.confirm-delete').hide();
          $('.cancle-delete').hide();
          $('.edit-topic').css('font-size', '12px');

        }).fail(function(jqXHR) {
          rs.util.messageError('删除失败，请刷新后重试');
          $('.edit-topic').css('font-size', '12px');
        })
      })

      //取消删除
      $('.cancle-delete').on('click', function() {
        $('.xxim_list .delete').css('display', 'table-cell');;
        $('.delete-select').hide().removeAttr('checked');
        $('.confirm-delete').hide();
        $('.cancle-delete').hide();
        $('.edit-topic').css('font-size', '12px');
      });
      //讨论组删除成功之后，在群组列表清除已经删除的讨论组
      function clearDeletedTopic(arr) {
        $.each(arr, function(index, item) {
          $('#group' + item).parent().next().remove();
          $('#group' + item).parent().remove();


        })
      }
      //打开群发对话框
      $('#xxim_toall').on('click', function() {
          xxim.popChatBox($(this));
        })
        //document事件
      dom[1].on('click', function() {
        node.setonline.hide();
        $('#layim_sendtype').hide();
      });
    }
    //消息模板

  xxim.loghtml = function(param, type) {
    return '<li class="clearfix ' + (type === 'me' ? 'layim_chateme' : '') + '">' + '<div class="layim_chatuser">' + function() {
      if (type === 'me') {
        return '<span class="layim_chattime">' + param.time + '</span>' + '<span class="layim_chatname">' + param.name + '</span>' + '<img src="' + param.face + '" >';
      } else {
        return '<img src="' + param.face + '" >' + '<span class="layim_chatname">' + param.name + '</span>' + '<span class="layim_chattime">' + param.time + '</span>';
      }
    }() + '</div>' + '<div class="layim_chatsay">' + function() {
      if (param.contype === "M") {
        return param.content;
      } else {
        return '<i class="icon-download"></i><a class="file" href="'+window.rsContextPath+'/im/file/download/' + param.content + '" target="_self">' + param.fileName + '</a>'
      }
    }() + '<em class="layim_zero"></em></div>' + '</li>';
  };
  xxim.uploadhtml = function(param) {
      return '<li class="clearfix layim_chateme uploadhtml">' + '<div class="layim_chatuser"><span class="layim_chattime">' + param.time + '</span>' + '<span class="layim_chatname">' + param.name + '</span>' + '<img src="' + param.face + '" ></div>' + '<div class="layim_chatsay">' + param.fileName + '</a><em class="layim_zero"></em><p>正在上传中....</p></div></li>';
    }
    //消息发送
  xxim.transmit = function() {
    var node = xxim.node,
      log = {};
    node.sendbtn = $('#layim_sendbtn');
    node.imwrite = $('#layim_write');
    log.send = function() {
      var data = {
        content: xxim.replaceEnterToBr(node.imwrite.val()),
        id: xxim.nowchat.id,
        sign_key: '',
        _: new Date()
      };
      var renderData = {
        time: new Date().toLocaleString(),
        name: config.user.nkname,
        face: config.user.face,
        content: data.content,
        contype: 'M'
      }
      if (data.content.replace(/\s/g, '') === '') {
        layer.tips('说点啥呗！', '#layim_write', 2);
        node.imwrite.focus();
      } else {
        if (xxim.nowchat.type === 'one') {
          rs.im.chatWithOne({
            content: data.content,
            contype: 'M',
            id: data.id,
            success: function() {
              var keys = xxim.nowchat.type + xxim.nowchat.id;
              log.imarea = xxim.chatbox.find('#layim_area' + keys);
              xxim.renderMessageToChatBox(renderData, log.imarea, 'me')
              node.imwrite.val('').focus();
              log.imarea.scrollTop(log.imarea[0].scrollHeight);
            }
          })
        } else if (xxim.nowchat.type === 'group') {
          rs.im.chatInTopic({
            content: data.content,
            contype: 'M',
            id: data.id,
            success: function() {
              var keys = xxim.nowchat.type + xxim.nowchat.id;
              log.imarea = xxim.chatbox.find('#layim_area' + keys);
              xxim.renderMessageToChatBox(renderData, log.imarea, 'me')
              node.imwrite.val('').focus();
              log.imarea.scrollTop(log.imarea[0].scrollHeight);
            }
          })
        } else {
          rs.im.sendToAll({
            content: data.content,
            contype: 'M',
            success: function() {
              var keys = xxim.nowchat.type + xxim.nowchat.id;
              log.imarea = xxim.chatbox.find('#layim_area' + keys);
              xxim.renderMessageToChatBox(renderData, log.imarea, 'me')
              node.imwrite.val('').focus();
              log.imarea.scrollTop(log.imarea[0].scrollHeight);
            }
          })
        }

      }
    }
    node.sendbtn.on('click', log.send);
    node.imwrite.keyup(function(e) {
      if (e.keyCode === 13) {
        if (!e.shiftKey) {
          log.send();
        }
      }
    })
  }

  xxim.replaceEnterToBr = function(str) {
    return str.replace(/\n/g, '<br/>');
  }

  //设置未读消息的状态
  xxim.setTabsUnreadFlag = function(ele, obj) {
    var unread = 0,
      unreadList = {};
    $.each(obj, function(key, item) {
      unread += item.count;
      unreadList[item.id] = item.count;
    });
    if (unread > 0) {
      ele.find('.unread').removeClass('nocount').addClass('count').text(unread);
    }
  }

  //初始化时设置未读消息的标志
  xxim.getUnreadInfo = function() {
    rs.im.getTopicList().done(function(res) {
      xxim.setTabsUnreadFlag($('.xxim_tabgroup'), res.rows)
    });
    rs.im.getUserList().done(function(res) {
      xxim.setTabsUnreadFlag($('.xxim_tabfriend'), res.rows)
    });
  }

  //im初始化
  xxim.init = function() {
    xxim.renode();
    xxim.tab(0);
    xxim.event();
    xxim.layinit();
    xxim.getUnreadInfo();
  }
  xxim.init();
})(rs, window);
