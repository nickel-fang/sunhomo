// pages/activity/activity.js
const app = getApp();
Page({

  /**
   * 页面的初始数据
   */
  data: {
    activity: {},
    userInfo: null
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
        } else if (res.data.code == 80003) {
          wx.showToast({
            title: '活动已开始',
            icon: 'none'
          })
        } else {
          wx.showToast({
            title: '系统错误',
            icon: 'none'
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
    wx.request({
      url: app.globalData.APIUrl + '/club/activity/quit/' + this.data.activity.activityId + "/" + event.currentTarget.dataset.master,
      method: 'POST',
      data: app.globalData.userInfo,
      success: function (res) {
        if (res.data.code == 1) {
          wx.showToast({
            title: '退报成功',
            icon: 'success'
          })
        } else if (res.data.code == 80002) {
          wx.showToast({
            title: '请联系群委会退报',
            icon: 'none'
          })
        } else if (res.data.code == 80001) {
          wx.showToast({
            title: '已过退报时间',
            icon: 'none'
          })
        } else {
          wx.showToast({
            title: '系统错误',
            icon: 'none'
          })
        }
        that.setData({
          activity: res.data.data
        })
      }
    })
  },

  copyEnroll: function(event){
    wx.setClipboardData({
      data: this.data.activity.content,
      success: function(res){
        wx.showToast({
          title: '复制成功',
          icon: 'success'
        })
      }
    })
  }
})