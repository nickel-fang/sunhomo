// pages/mall/mall.js
const app = getApp();
Page({

  /**
   * 页面的初始数据
   */
  data: {
    allGoods: [],
    userInfo: {}
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    this.setData({
      userInfo: app.globalData.userInfo
    });
    this.getData();
  },
  getData() {
    var that = this;
    //初始化this.data.activities
    wx.request({
      url: app.globalData.APIUrl + '/club/good/list',
      method: 'POST',
      data: app.globalData.userInfo,
      success: function (res) {
        that.setData({
          allGoods: res.data
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

  redeem: function (event) {
    var goods = event.currentTarget.dataset.goods;
    if (this.data.userInfo.point >= goods.value) {
      wx.showModal({
        content: '兑换' + goods.goodName,
        confirmColor: '#2EA7E0',
        success(res) {
          if (res.confirm) {
            wx.request({
              url: app.globalData.APIUrl + '/club/good/redeem/' + goods.goodId,
              method: 'POST',
              data: app.globalData.userInfo,
              success:function(res){
                if(res.data.code==1){
                  wx.showToast({
                    title: '兑换成功',
                    icon: 'success'
                  })
                }else{
                  wx.showToast({
                    title: '兑换失败',
                    icon: 'error'
                  })
                }
              }
            })
          } else if (res.cancel) {

          }
        }
      })
    } else {
      wx.showToast({
        title: '积分不够',
        icon: 'error'
      })
    }
  }
})