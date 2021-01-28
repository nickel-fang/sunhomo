// pages/activity/activity.js
const app = getApp();
Page({

  /**
   * 页面的初始数据
   */
  data: {
    activity: {},
    userInfo: null,
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
          activity: res.data,
        });
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
        })
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
              })
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
  }
})