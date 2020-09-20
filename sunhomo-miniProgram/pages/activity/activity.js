// pages/activity/activity.js
const app = getApp();
Page({

  /**
   * 页面的初始数据
   */
  data: {
    activity:{}
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    var that = this;
    //初始化this.data.activities
    wx.request({
      url: app.globalData.APIUrl+'/enroll/activity/getActivity',
      method: 'POST',
      data: options.activityId,
      success: function(res){
        console.log(res.data);
        that.setData({
          activity: res.data
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

  enroll: function(){
    var that = this;
    console.log(app.globalData.userInfo);
    wx.request({
      url: app.globalData.APIUrl+'/enroll/activity/enroll/'+this.data.activity.activityId,
      method: 'POST',
      data: app.globalData.userInfo,
      success: function(res){
        if(res.data.msg=='1'){
          wx.showToast({
            title: '报名成功',
            icon: 'success'
          });
          that.setData({
            activity: res.data.data
          })
        }else if(res.data.msg=='0'){
          wx.showToast({
            title: '报名失败，请联系管理员！',
          })
        }
      }
    })
  }
})