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
    if (wx.getStorageSync('battleChange')) {
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
    if (app.globalData.userInfo.point < 3) {
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
  },

  accept: function (event) {
    //判断是否可以应战（当前约战人员不能应战，未约战成功的且是参与者的约战，不能应战)
    var canAccept = true;
    if (app.globalData.userInfo.point < 3) {
      wx.showToast({
        title: '积分不够',
        icon: 'error'
      });
      canAccept = false;
    } else {
      var that = this;
      var postion = event.currentTarget.dataset.postion;
      var battleId = event.currentTarget.dataset.battleid;
      var userId = this.data.userInfo.memberId;
      var userName = this.data.userInfo.memberName;
      var battle = {};
      for (var i = 0; i < this.data.battles.length; i++) {
        var tempBattle = this.data.battles[i];
        if (tempBattle.battleState == 1) {
          if (tempBattle.a1 == userId || tempBattle.a2 == userId || tempBattle.b1 == userId || tempBattle.b2 == userId) {
            wx.showToast({
              title: '无法应战',
              icon: 'error'
            });
            canCall = false;
            break;
          }
        }
      }
      if (canAccept) {
        wx.showModal({
          content: '确定应战？',
          confirmColor: '#2EA7E0',
          success(res) {
            if (res.confirm) {
              battle.battleId = battleId;
              if (postion == 'a2') {
                battle.a2 = userId;
                battle.a2Name = userName;
              } else if (postion == 'b1') {
                battle.b1 = userId;
                battle.b1Name = userName;
              } else if (postion == 'b2') {
                battle.b2 = userId;
                battle.b2Name = userName;
              }
              wx.request({
                url: app.globalData.APIUrl + '/club/battle/accept',
                method: 'POST',
                data: battle,
                success: function (res) {
                  if (res.data.code == 1) {
                    that.data.userInfo.point = that.data.userInfo.point - 3;
                    app.globalData.userInfo.point = app.globalData.userInfo.point - 3;
                    wx.setStorageSync('pointChange', 1);
                    that.setData({
                      battles: res.data.data
                    });
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
    }
  },

  doCall: function (event) {
    //判断是否可以打CALL(当前约战人员不能打CALL，已打CALL不能打CALL)
    var canCall = true;
    if (this.data.userInfo.point <= 0) {
      wx.showToast({
        title: '积分不够',
        icon: 'error'
      });
      canCall = false;
    } else {
      var that = this;
      var vote = event.currentTarget.dataset.vote;
      var battleId = event.currentTarget.dataset.battleid;
      var userId = this.data.userInfo.memberId;
      for (var i = 0; i < this.data.battles.length; i++) {
        var battle = this.data.battles[i];
        if (battleId == battle.battleId) {
          if (battle.a1 == userId || battle.a2 == userId || battle.b1 == userId || battle.b2 == userId) {
            wx.showToast({
              title: '约战人员不能打CALL',
              icon: 'error'
            });
            canCall = false;
            break;
          }
          for (var j = 0; j < battle.votes.length; j++) {
            if (userId == battle.votes[j].memberId) {
              wx.showToast({
                title: '您已打过CALL',
                icon: 'error'
              });
              canCall = false;
              break;
            }
          }
        }
      }
    }
    if (canCall) {
      wx.showModal({
        content: '确定支持' + (vote == 1 ? 'A' : 'B') + '队？',
        confirmColor: '#2EA7E0',
        success(res) {
          if (res.confirm) {
            wx.request({
              url: app.globalData.APIUrl + '/club/battle/doCall',
              method: 'POST',
              data: {
                "battleId": battleId,
                "memberId": that.data.userInfo.memberId,
                "vote": vote
              },
              success: function (res) {
                if (res.data.code == 1) {
                  that.data.userInfo.point = that.data.userInfo.point - 1;
                  app.globalData.userInfo.point = app.globalData.userInfo.point - 1;
                  wx.setStorageSync('pointChange', 1);
                  that.setData({
                    battles: res.data.data
                  });
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
  }
})