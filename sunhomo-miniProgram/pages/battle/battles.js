const app = getApp();
Page({

  /**
   * 页面的初始数据
   */
  data: {
    battles: [],
    userInfo: null,
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
    //TODO 获取约战列表
    var that = this;
    wx.request({
      url: app.globalData.APIUrl + '/club/battle/list',
      method: 'POST',
      data: null,
      success: function (res) {
        that.setData({
          battles: res.data
        });
        //隐藏loading 提示框
        wx.hideLoading();
        //隐藏导航条加载动画
        wx.hideNavigationBarLoading();
        //停止下拉刷新
        wx.stopPullDownRefresh();
      }
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
    if(wx.getStorageSync('battleChange')){
      this.getData();
      wx.removeStorageSync('battleChange');
    }
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

  confirmAndCancelAndWin: function (event) {
    var that = this;
    var action = event.currentTarget.dataset.action;
    var battleId = event.currentTarget.dataset.battleid;
    if (action == 1) {
      wx.showModal({
        content: '确认该约战？',
        confirmColor: '#2EA7E0',
        success(res) {
          if (res.confirm) {
            that.goon(battleId, 2, null);
          }
        }
      });
    } else if (action == -1) {
      wx.showModal({
        content: '取消该约战？',
        confirmColor: '#2EA7E0',
        success(res) {
          if (res.confirm) {
            that.goon(battleId, -1, null);
          }
        }
      });
    } else if (action == 2) {
      wx.showModal({
        content: '确认A队获胜？',
        confirmColor: '#2EA7E0',
        success(res) {
          if (res.confirm) {
            that.goon(battleId, null, 1);
          }
        }
      });
    } else if (action == 3) {
      wx.showModal({
        content: '确认B队获胜？',
        confirmColor: '#2EA7E0',
        success(res) {
          if (res.confirm) {
            that.goon(battleId, null, -1);
          }
        }
      });
    }
  },

  goon: function (battleId, battleState, battleResult) {
    var that = this;
    wx.request({
      url: app.globalData.APIUrl + '/club/battle/confirmAndCancelAndWin',
      method: 'POST',
      data: {
        "battleId": battleId,
        "battleState": battleState,
        "battleResult": battleResult
      },
      success: function (res) {
        that.setData({
          battles: res.data.data
        });
      }
    });
  },

  battle: function () {
    if (this.data.userInfo.point < 3) {
      wx.showToast({
        title: '积分不够',
        icon: 'error'
      });
    } else {
      //先判断当前用户是否报了近期活动，报了活动才可以发起约战
      wx.request({
        url: app.globalData.APIUrl + '/club/activity/getActivitiesForBattle',
        method: 'POST',
        data: this.data.userInfo.memberId,
        success: function (res) {
          if (res.data.length > 0) {
            wx.navigateTo({
              url: 'battle?activities=' + JSON.stringify(res.data)
            })
          } else {
            wx.showToast({
              title: '您未报名活动',
              icon: 'error'
            });
          }
        }
      })
    }
  }
})