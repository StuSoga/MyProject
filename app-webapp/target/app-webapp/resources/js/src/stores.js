/**
 * Created by zero on 15/12/26.
 */
var assign = require("object-assign"),
    Book=require('./services/book'),
    App = require("./services/app");

var stores = {};
assign(stores, App.stores);
assign(stores, Book.stores);

module.exports = stores;