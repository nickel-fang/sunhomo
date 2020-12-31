// pages/my/redeemManagement.js
const app = getApp();
Page({

  /**
   * 页面的初始数据
   */
  data: {
    redeems: []
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    this.getData();
  },

  getData() {
    var that = this;
    //初始化this.data.activities
    wx.request({
      url: app.globalData.APIUrl + '/club/good/getRedeems/1',
      method: 'POST',
      data: null,
      success: function (res) {
        console.log(res.data);
        that.setData({
          redeems: res.data
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
    this.getData();
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
  consign: function (event) {
    var that = this;
    wx.showModal({
      content: '确定交付？',
      confirmColor: '#2EA7E0',
      success(res) {
        if (res.confirm) {
          wx.request({
            url: app.globalData.APIUrl + '/club/good/consign/' + event.currentTarget.dataset.transactionid,
            method: 'POST',
            data: null,
            success: function (res) {
              if (res.data.code == 1) {
                wx.showToast({
                  title: '交付成功',
                  icon: 'success'
                });
                that.setData({
                  redeems: res.data.data
                })
              } else {
                wx.showToast({
                  title: '交付失败',
                  icon: 'error'
                })
              }
            }
          })
        } else if (res.cancel) {

        }
      }
    });
  }
})