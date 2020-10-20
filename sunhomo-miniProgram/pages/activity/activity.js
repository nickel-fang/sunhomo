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
    this.getData(options.activityId);
  },

  getData(activityId){
    var that = this;
    //初始化this.data.activities
    wx.request({
      url: app.globalData.APIUrl+'/club/activity/getActivity',
      method: 'POST',
      data:activityId,
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

  enroll: function(){
    var that = this;
    wx.request({
      url: app.globalData.APIUrl+'/club/activity/enroll/'+this.data.activity.activityId,
      method: 'POST',
      data: app.globalData.userInfo,
      success: function(res){
        if(res.data.code==1){
          wx.showToast({
            title: '报名成功',
            icon: 'success'
          });
          that.setData({
            activity: res.data.data
          })
        }else if(res.data.code!=1){
          wx.showToast({
            title: '报名失败，请联系管理员！',
          })
        }
      }
    })
  },

  quit: function(event){
    var that = this;
    wx.request({
      url: app.globalData.APIUrl+'/club/activity/quit/'+this.data.activity.activityId+"/"+event.currentTarget.dataset.master,
      method: 'POST',
      data: app.globalData.userInfo,
      success: function(res){
        if(res.data.code==1){
          wx.showToast({
            title: '取消报名成功',
            icon: 'success'
          });
          that.setData({
            activity: res.data.data
          })
        }else if(res.data.code!=1){
          wx.showToast({
            title: '取消报名失败，请联系管理员！',
          })
        }
      }
    })
  }
})