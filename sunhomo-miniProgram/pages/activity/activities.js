// pages/activity/activities.js
const app = getApp();
Page({

  /**
   * 页面的初始数据
   */
  data: {
    activities:[]
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    this.getData();
  },

  getData(){
    var that = this;
    //初始化this.data.activities
    wx.request({
      url: app.globalData.APIUrl+'/club/activity/list',
      method: 'POST',
      data: {
        "activityState" : 1
      },
      success: function(res){
        that.setData({
          activities: res.data
        })
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

  //报名
  enroll:function(event){
    //console.log(event.currentTarget.dataset.activityid);
    wx.navigateTo({
      url: 'activity?activityId='+event.currentTarget.dataset.activityid
    })
  }
})