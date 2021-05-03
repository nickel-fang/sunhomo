//index.js
//获取应用实例
const app = getApp()

Page({
  data: {
    userInfo: {},
    hasUserInfo: false,
    canIUseGetUserProfile: false,
  },

  onLoad: function () {
    var that = this;
    if (wx.getUserProfile) {
      that.setData({
        canIUseGetUserProfile: true
      })
    }
    if (app.globalData.userInfo) {
      that.setData({
        userInfo: app.globalData.userInfo,
        hasUserInfo: true
      })
    } else if (that.data.canIUseGetUserProfile) {
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

  onShow: function () {
    if (app.globalData.userInfo && wx.getStorageSync('pointChange')) {
      this.setData({
        userInfo: app.globalData.userInfo
      });
      wx.removeStorageSync('pointChange');
    }
  },

  getUserProfile: function (e) {
    var that = this;
    wx.getUserProfile({
      desc: '微信昵称用于活动报名',
      success: (res) => {
        that.insertMember(res.userInfo);
      }
    })
  },

  getUserInfo: function (e) {
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