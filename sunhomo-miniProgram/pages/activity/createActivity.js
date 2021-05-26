const app = getApp();
var util = require('../../utils/util.js');
Page({

  /**
   * 页面的初始数据
   */
  data: {
    activityDate: null
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    this.setData({
      activityDate: util.formatDate(new Date())
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

  bindDateChange: function (event) {
    var activityDate = event.detail.value;
    this.setData({
      activityDate: activityDate
    });
  },

  bindSubmit: function (event) {
    var that = this;
   
    wx.showModal({
      content: '确认创建活动',
      confirmColor: '#2EA7E0',
      success(res) {
        if (res.confirm) {
          wx.request({
            url: app.globalData.APIUrl + '/club/activity/createActivity',
            method: 'POST',
            data: that.data.activityDate,
            success: function (res) {
              if (res.data.code == 1) {
                wx.showToast({
                  title: '添加成功',
                  icon: 'success'
                });
                wx.setStorageSync('activitysChange', 1);
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
})