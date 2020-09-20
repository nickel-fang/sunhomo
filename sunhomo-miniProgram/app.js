//app.js
App({
  onLaunch: function () {
    // 展示本地存储能力
    var logs = wx.getStorageSync('logs') || []
    logs.unshift(Date.now())
    wx.setStorageSync('logs', logs)

    // 登录
    wx.login({
      success: res => {
        // 发送 res.code 到后台换取 openId, sessionKey, unionId
        wx.request({
          url: this.globalData.APIUrl + '/enroll/member/getOpenId',
          method: 'POST',
          data: res.code,
          success: res => {
            if (res.data.code == 0) {
              var msg = res.data.msg;
              if (msg == '-1') {
                this.globalData.openId = res.data.data;
                //获取用户信息，并插入数据库
                wx.getSetting({
                  success: res => {
                    if (res.authSetting['scope.userInfo']) {
                      // 已经授权，可以直接调用 getUserInfo 获取头像昵称，不会弹框
                      wx.getUserInfo({
                        success: res => {
                          // 可以将 res 发送到后台，插入会员
                          var member = {};
                          member.openid = this.globalData.openId;
                          member.memberName = res.userInfo.nickName;
                          member.memberPhoto = res.userInfo.avatarUrl;

                          wx.request({
                            url: this.globalData.APIUrl + '/enroll/member/insertMember',
                            method: 'POST',
                            data: member,
                            success:res=>{
                              this.globalData.userInfo = res.data.data;
                            }
                          })

                          // 由于 getUserInfo 是网络请求，可能会在 Page.onLoad 之后才返回
                          // 所以此处加入 callback 以防止这种情况
                          if (this.userInfoReadyCallback) {
                            this.userInfoReadyCallback(res)
                          }
                        }
                      })
                    }
                  }
                })
              } else if (msg == '1') {
                this.globalData.userInfo = res.data.data;
              }
            } else if (res.data.code == 500) {
              console.log("wx.login失败！");
            }
          }
        })
      }
    })

  },
  globalData: {
    userInfo: null,
    openId: null,
    //APIUrl: "https://www.sunhomo.cn/api"
    APIUrl: "http://localhost/api"
  }
})