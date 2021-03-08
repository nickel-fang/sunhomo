// pages/activity/activity.js
const app = getApp();
var util = require('../../utils/util.js');
Page({

  /**
   * 页面的初始数据
   */
  data: {
    activity: {},
    userInfo: null,
    canBlind: false,
    hasAttend: false
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    this.setData({
      userInfo: app.globalData.userInfo
    });
    this.getData(options.activityId);
  },

  getData(activityId) {
    var that = this;
    //初始化this.data.activities
    wx.request({
      url: app.globalData.APIUrl + '/club/activity/getActivity',
      method: 'POST',
      data: activityId,
      success: function (res) {
        that.setData({
          activity: res.data
        });
        that.setCanBlind(that.data.activity);
        //隐藏loading 提示框
        wx.hideLoading();
        //隐藏导航条加载动画
        wx.hideNavigationBarLoading();
        //停止下拉刷新
        wx.stopPullDownRefresh();
      }
    })
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
    wx.showNavigationBarLoading();
    wx.showLoading({
      title: '刷新中...'
    });
    this.getData(this.data.activity.activityId);
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

  enroll: function () {
    var that = this;
    wx.request({
      url: app.globalData.APIUrl + '/club/activity/enroll/' + this.data.activity.activityId,
      method: 'POST',
      data: app.globalData.userInfo,
      success: function (res) {
        if (res.data.code == 1) {
          wx.showToast({
            title: '报名成功',
            icon: 'success'
          })
        } else if (res.data.code != 1) {
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
        that.setData({
          activity: res.data.data
        });
        that.setCanBlind(that.data.activity);
      }
    })
  },

  quit: function (event) {
    var that = this;
    wx.showModal({
      content: '确定退报？',
      confirmColor: '#2EA7E0',
      success(res) {
        if (res.confirm) {
          wx.request({
            url: app.globalData.APIUrl + '/club/activity/quit/' + that.data.activity.activityId + "/" + event.currentTarget.dataset.master + "/" + that.data.userInfo.isAdmin,
            method: 'POST',
            data: event.currentTarget.dataset.memberid,
            success: function (res) {
              if (res.data.code == 1) {
                wx.showToast({
                  title: '退报成功',
                  icon: 'success'
                })
              } else if (res.data.code != 1) {
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
              that.setData({
                activity: res.data.data
              });
              that.setCanBlind(that.data.activity);
            }
          })
        } else if (res.cancel) {

        }
      }
    })

  },

  copyEnroll: function (event) {
    wx.setClipboardData({
      data: this.data.activity.content,
      success: function (res) {
        wx.showToast({
          title: '复制成功',
          icon: 'success'
        })
      }
    })
  },
  draw: function (event) {
    var that = this;
    wx.request({
      url: app.globalData.APIUrl + '/club/activity/draw/' + this.data.activity.activityId,
      method: 'POST',
      data: app.globalData.userInfo,
      success: function (res) {
        if (res.data.code == 1) {
          wx.showToast({
            title: '抽签成功',
            icon: 'success'
          });
          //跳转
          wx.navigateTo({
            url: 'divisions?activityId=' + that.data.activity.activityId
          })
        } else if (res.data.code == 80005 || res.data.code == 80008 || res.data.code == 80009) {
          wx.showToast({
            title: res.data.msg,
            icon: 'error'
          })
        } else if (res.data.code == 80006 || res.data.code == 80007) {
          //队长或已抽过签，直接跳转
          ///跳转
          wx.navigateTo({
            url: 'divisions?activityId=' + that.data.activity.activityId
          })
        } else {
          wx.showToast({
            title: '系统错误',
            icon: 'error'
          })
        }
      }
    })
  },

  blindBattle: function (event) {
    var that = this;
    wx.showModal({
      content: '确定报名盲盒约战？',
      confirmColor: '#2EA7E0',
      success(res) {
        if (res.confirm) {
          wx.request({
            url: app.globalData.APIUrl + '/club/activity/blindBattle/' + that.data.activity.activityId,
            method: 'POST',
            data: app.globalData.userInfo,
            success: function (res) {
              if (res.data.code == 1) {
                wx.showToast({
                  title: '报名盲盒成功',
                  icon: 'success'
                });
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
              that.setData({
                activity: res.data.data
              });
              that.setCanBlind(that.data.activity);
            }
          })
        }else{

        }
      }
    })
  },

  setCanBlind: function (activity) {
    var canBlind = false;
    var hasAttend = false; //是否已报名盲盒
    for (var i = 0; i < activity.blindMembers.length; i++) {
      if (activity.blindMembers[i].memberId == this.data.userInfo.memberId) {
        hasAttend = true;
        break;
      }
    }
    //普通活动 && 活动日期不等于当天
    if (activity.activityDate != util.formatDate(new Date()) && activity.activityType == 1) {
      if (!hasAttend) {
        for (var i = 0; i < activity.members.length; i++) {
          if (activity.members[i].isMaster == 0 && activity.members[i].memberId == this.data.userInfo.memberId) {
            canBlind = true;
            break;
          }
        }
      }
    }
    this.setData({
      canBlind: canBlind,
      hasAttend: hasAttend
    })
  }
})