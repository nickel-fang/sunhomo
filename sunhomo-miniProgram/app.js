//app.js
App({
  onLaunch: function () {
    // 展示本地存储能力
    var logs = wx.getStorageSync('logs') || []
    logs.unshift(Date.now())
    wx.setStorageSync('logs', logs)

    // // 登录
    // wx.login({
    //   success: res => {
    //     // 发送 res.code 到后台换取 openId, sessionKey, unionId
    //     wx.request({
    //       url: this.globalData.APIUrl + '/club/member/getOpenId',
    //       method: 'POST',
    //       data: res.code,
    //       success: res => {
    //         var code = res.data.code;
    //         if (code == -1) {
    //           this.globalData.openId = res.data.data;
    //           wx.switchTab({
    //             url: '/pages/my/my',
    //           })
    //         } else if (code == 1) {
    //           this.globalData.userInfo = res.data.data;
    //         } else if (code == 500) {
    //           console.log("wx.login失败！");
    //         }
    //       }
    //     })
    //   }
    // })

  },
  globalData: {
    userInfo: null,
    openId: null,
    APIUrl: "https://www.sunhomo.cn/api"
    // APIUrl: "http://localhost:443/api"
  }
})