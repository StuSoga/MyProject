/**
 * Created by zero on 15/12/31.
 */

var $ = require('browserify-zepto'),
    Constants = require('../constants');

var post=function(url,data,success){

    $.ajax({
        type:'POST',
        url:Constants.SERVICE_URL+url,
        dataType:'json',
        data:data,
        cache:false,
        context:this,
        success:function(res){
            success.call(this,res);
        },
        error:function(xhr,stauts,err){
            console.error(url, status, err.toString());
        }
    });
};

var get=function(url,data,success){
    $.ajax({
        type:'GET',
        url:Constants.SERVICE_URL+url,
        dataType:'json',
        data:data,
        cache:false,
        context:this,
        success:function(data){
            success.call(this,data);
        },
        error:function(xhr,stauts,err){
            console.error(url, status, err.toString());
        }
    });
};

module.exports = {
    post: post,
    get: get
};