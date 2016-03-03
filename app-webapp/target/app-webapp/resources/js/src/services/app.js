/**
 * Created by zero on 15/12/26.
 */
var Fluxxor = require('fluxxor'),
    LocalDb = require('localdb'),
    UUID = require('node-uuid'),
    Constants = require('../constants');

var actions = {}

/**
 *
 */
var AppStore = Fluxxor.createStore({

    initialize: function () {

        //初始化数据库
        this.db = new LocalDb(Constants.DB_NAME, 'Object');
        //设置版本
        var currentVersion = '0.0.1';
        this.version = this.db.get(Constants.VERSION);
        if (!this.version) {
            this.db.set('version', currentVersion);
        } else {
            if (this.version != currentVersion) {
                this.db.set(Constants.VERSION, currentVersion);
                //TODO:现在不知道要干嘛
            }
        }
        //初始化deviceId
        if (!this.db.get(Constants.DEVICEID)) {
            this.db.set(Constants.DEVICEID, UUID.v4());
        }

        this.authFlag = false;
        if (this.db.get(Constants.TOKEN)) {
            this.authFlag = true;
        }

        this.authFailureFlag = false;

        this.bindActions(
            Constants.NETWORK_ERROR, this.handleNetworkError,
            Constants.API_RETURN_ERROR, this.handleApiReturnError,
            Constants.APP_AUTH_SUCCESS, this.handleAuthSuccess,
            Constants.APP_AUTH_FAILURE, this.handleAuthFailure
        );
    },

    //处理网络请求错误
    handleNetworkError: function (message) {
        alert(message);
    },

    handleApiReturnError: function (message) {
        alert(message);
    },

    handleAuthFailure: function () {
        //删除本地token
        this.db.set(Constants.TOKEN, '');
        this.authFlag = false;
        this.authFailureFlag = true;
        this.emit('change');
    },

    handleAuthSuccess: function (data) {
        this.db.set(Constants.TOKEN, data.token);
        this.authFailureFlag = false;
        this.authFlag = true;
    },

    /**
     * 获取是否授权标识
     * @returns {boolean}
     */
    isAuth: function () {
        return this.authFlag;
    },

    isAuthFailure: function () {
        return this.authFailureFlag;
    }

});

var stores = {
    AppStore: new AppStore()
};


module.exports = {
    actions: actions,
    stores: stores
};