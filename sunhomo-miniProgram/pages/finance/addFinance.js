// pages/finance/addFinance.js
const app = getApp();
var util = require('../../utils/util.js');
Page({

  /**
   * 页面的初始数据
   */
  data: {
    finDate: null,
    finType: null,
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    this.setData({
      finDate: util.formatDate(new Date())
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
    var finDate = event.detail.value;
    this.setData({
      finDate: finDate
    });
  },

  bindTypeChange: function (event) {
    var finType = ['收入', '支出'][event.detail.value];
    this.setData({
      finType: finType
    });
  },

  bindSubmit: function (event) {
    var finType = this.data.finType;
    var finance = {};
    finance.finDate = this.data.finDate;
    finance.finType = finType == '收入' ? 1 : (finType == '支出' ? 2 : 3);
    finance.finMemo = event.detail.value.finMemo;
    finance.finValue = event.detail.value.finValue;
    if (!finance.finMemo) {
      wx.showToast({
        title: '请填写交易明细',
        icon: 'error'
      })
      return
    }
    if (!finance.finValue) {
      wx.showToast({
        title: '请填写交易金额',
        icon: 'error'
      })
      return
    }
    wx.showModal({
      content: '确认交易明细',
      confirmColor: '#2EA7E0',
      success(res) {
        if (res.confirm) {
          wx.request({
            url: app.globalData.APIUrl + '/club/finance/insertFinance',
            method: 'POST',
            data: finance,
            success: function (res) {
              if (res.data.code == 1) {
                wx.showToast({
                  title: '添加成功',
                  icon: 'success'
                });
                wx.setStorageSync('financeChange', 1);
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