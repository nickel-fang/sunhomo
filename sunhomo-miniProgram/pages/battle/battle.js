// pages/battle/battle.js
const app = getApp();
var util = require('../../utils/util.js');
Page({

  /**
   * 页面的初始数据
   */
  data: {
    userInfo: null,
    activities: [],
    members: [],
    activityId: null,
    activityName: null,
    battleType: null,
    a1: null,
    a1Name: null,
    a2: null,
    a2Name: null,
    b1: null,
    b1Name: null,
    b2: null,
    b2Name: null,
    battleDate: null,
    battlePoint: null,
    battleState: 1,
    isPeak: -1,
    isBlind: -1,
    canBlind: null,
    blindCheck: false
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    var tempActivities = JSON.parse(options.activities);
    if (tempActivities.length > 1) {
      this.setData({
        userInfo: app.globalData.userInfo,
        activities: tempActivities
      });
    } else {
      var activity = tempActivities[0];
      this.setData({
        userInfo: app.globalData.userInfo,
        activities: tempActivities,
        activityId: activity.activityId,
        activityName: activity.activityName,
        battleDate: activity.activityDate,
        members: activity.members,
        //活动当天不能发起盲盒约战
        canBlind: (activity.activityDate == util.formatDate(new Date())) ? false : true
      });
    }

    //A1默认是自己
    this.setData({
      a1: this.data.userInfo.memberId,
      a1Name: this.data.userInfo.memberName
    });
  },

  /**
   * 生命周期函数--监听页面初次渲染完成
   */
  onReady: function () {

  },

  /**
   * 生命周期函数--监听页面显示
   */
  onShow: function () {

  },

  /**
   * 生命周期函数--监听页面隐藏
   */
  onHide: function () {

  },

  /**
   * 生命周期函数--监听页面卸载
   */
  onUnload: function () {

  },

  /**
   * 页面相关事件处理函数--监听用户下拉动作
   */
  onPullDownRefresh: function () {

  },

  /**
   * 页面上拉触底事件的处理函数
   */
  onReachBottom: function () {

  },

  /**
   * 用户点击右上角分享
   */
  onShareAppMessage: function () {

  },

  bindActivityChange: function (event) {
    var activity = this.data.activities[event.detail.value];
    this.setData({
      activityId: activity.activityId,
      activityName: activity.activityName,
      members: activity.members,
      battleDate: activity.activityDate,
      //活动当天不能发起盲盒约战
      canBlind: (activity.activityDate == util.formatDate(new Date())) ? false : true,

      //重置
      a1: this.data.userInfo.memberId,
      a1Name: this.data.userInfo.memberName,
      a2: null,
      a2Name: null,
      b1: null,
      b1Name: null,
      b2: null,
      b2Name: null,
      isBlind: -1,
      blindCheck: false
    });
  },

  bindA1Change: function (event) {
    var member = this.data.members[event.detail.value];
    this.setData({
      a1: member.memberId,
      a1Name: member.memberName
    });
  },
  bindA2Change: function (event) {
    var member = this.data.members[event.detail.value];
    this.setData({
      a2: member.memberId,
      a2Name: member.memberName
    });
  },
  bindB1Change: function (event) {
    var member = this.data.members[event.detail.value];
    this.setData({
      b1: member.memberId,
      b1Name: member.memberName
    });
  },
  bindB2Change: function (event) {
    var member = this.data.members[event.detail.value];
    this.setData({
      b2: member.memberId,
      b2Name: member.memberName
    });
  },

  bindIsBlindChange: function (event) {
    if (event.detail.value == 1) {
      this.setData({
        isBlind: 1
      });
    } else {
      this.setData({
        isBlind: -1
      });
    }
  },

  bindSubmit: function (event) {
    var that = this;
    var battle = {
      "activityId": this.data.activityId,
      "battleType": 2,
      "battlePoint": 3,
      "battleState": 1,
      "a1": this.data.a1,
      "a1Name": this.data.a1Name,
      "b1": this.data.b1,
      "b1Name": this.data.b1Name,
      "a2": this.data.a2,
      "a2Name": this.data.a2Name,
      "b2": this.data.b2,
      "b2Name": this.data.b2Name,
      "battleDate": this.data.battleDate,
      "isPeak": this.data.isPeak,
      "isBlind": this.data.isBlind
    };
    if (battle.a1 && battle.a2 && battle.b1 && battle.b2) {
      battle.battleState = 2;
    }
    if (battle.isBlind == 1) {
      battle.battlePoint = 1;
    }
    //有效性校验
    /**if (!battle.a1 || !battle.a2 || !battle.b1 || !battle.b2) {
      wx.showToast({
        title: '请选择人员',
        icon: 'error'
      });
    } else **/
    if(!battle.activityId){
      wx.showToast({
        title: '请选择活动',
        icon: 'error'
      });
      return;
    }
    if ((battle.b1 && battle.a1 == battle.b1) || (battle.b2 && battle.a1 == battle.b2) || (battle.a2 && battle.b1 && battle.a2 == battle.b1) ||
      (battle.a2 && battle.b2 && battle.a2 == battle.b2) || (battle.a2Name && battle.a1Name == battle.a2Name) || (battle.b1Name && battle.b2Name && battle.b1Name == battle.b2Name)) {
      wx.showToast({
        title: '人员选择有误',
        icon: 'error'
      });
    } else {
      wx.showModal({
        content: '确认发起约战？',
        confirmColor: '#2EA7E0',
        success(res) {
          if (res.confirm) {
            wx.request({
              url: app.globalData.APIUrl + '/club/battle/battle',
              method: 'POST',
              data: battle,
              success: function (res) {
                if (res.data.code == 1) {
                  wx.showToast({
                    title: '约战成功',
                    icon: 'success'
                  });
                  //A1为本人发起的约战，需要更新userInfo
                  if (res.data.data.memberId == that.data.userInfo.memberId) {
                    app.globalData.userInfo = res.data.data;
                    that.setData({
                      userInfo: app.globalData.userInfo
                    });
                  }
                  //跳转
                  wx.setStorageSync('pointChange', 1);
                  wx.setStorageSync('battleChange', 1);
                  wx.navigateBack();
                } else if (res.data.code) {
                  wx.showToast({
                    title: res.data.msg,
                    icon: 'error'
                  })
                } else {
                  wx.showToast({
                    title: '系统错误',
                    icon: 'error'
                  })
                }
              }
            })
          }
        }
      });
    }
  },
})