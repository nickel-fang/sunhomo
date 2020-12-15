//index.js
//获取应用实例
const app = getApp()

Page({
  data: {
    motto: '更多功能开发中...',
    userInfo: {},
    hasUserInfo: false,
    canIUse: wx.canIUse('button.open-type.getUserInfo')
  },

  onLoad: function () {
    var that = this;
    if (app.globalData.userInfo) {
      that.setData({
        userInfo: app.globalData.userInfo,
        hasUserInfo: true
      })
    } else if (that.data.canIUse) {
      // 由于 getUserInfo 是网络请求，可能会在 Page.onLoad 之后才返回
      // 所以此处加入 callback 以防止这种情况
      app.userInfoReadyCallback = res => {
        // this.setData({
        //   userInfo: res.userInfo,
        //   hasUserInfo: true
        // })
        that.insertMember(res.userInfo);
      }
    } else {
      // 在没有 open-type=getUserInfo 版本的兼容处理
      wx.getUserInfo({
        success: res => {
          // app.globalData.userInfo = res.userInfo
          // this.setData({
          //   userInfo: res.userInfo,
          //   hasUserInfo: true
          // })
          that.insertMember(res.userInfo);
        }
      })
    }
  },
  getUserInfo: function (e) {
    // console.log(e)
    // app.globalData.userInfo = e.detail.userInfo
    // this.setData({
    //   userInfo: e.detail.userInfo,
    //   hasUserInfo: true
    // })
    this.insertMember(e.detail.userInfo);
  },
  insertMember: function (userInfo) {
    var that = this;
    var member = {};
    member.openid = app.globalData.openId;
    member.memberName = userInfo.nickName;
    member.memberPhoto = userInfo.avatarUrl;

    wx.request({
      url: app.globalData.APIUrl + '/club/member/insertMember',
      method: 'POST',
      data: member,
      success: res => {
        app.globalData.userInfo = res.data.data;
        that.setData({
          userInfo: res.data.data,
          hasUserInfo: true
        })
      }
    });
  }
})