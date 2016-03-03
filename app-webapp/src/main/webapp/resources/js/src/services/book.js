/**
 * Created by zero on 16/1/4.
 */
var Fluxxor=require('fluxxor'),
    Http = require('../services/http'),
    Constants = require('../constants');

Constants['BOOK_LIST_SUCCESS']="BOOK_LIST_SUCCESS";
Constants['BOOK_LIST_FAILURE']="BOOK_LIST_FAILURE";

var actions={
    getBooks:function(){
        Http.get.call(this,'/index.json',{},function(res){
            this.dispatch(Constants.BOOK_LIST_SUCCESS,res);
        });
    }
};

var BookStore=Fluxxor.createStore({
    initialize:function(){
        this.books=[];
        this.bindActions(
            Constants.BOOK_LIST_SUCCESS,this.OnGetBooks
        )
    },
    OnGetBooks:function(res){
        this.books=res.books;
        this.emit('change');
    },
    getBooks:function(){
        return this.books;
    }
});

var stores={
    BookStore:new BookStore()
};
module.exports={
    actions:actions,
    stores:stores
};